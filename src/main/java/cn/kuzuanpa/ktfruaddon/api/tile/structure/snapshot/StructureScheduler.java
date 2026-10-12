/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import org.apache.logging.log4j.Level;

import java.util.ArrayDeque;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 结构检查调度器：把"快照（主线程、有预算）→ 计算（worker、不限速）→ 应用（主线程、有预算）"串成一条流水线。
 *
 * <p>主线程每 tick 只做三件有界的事（顺序与预算都在 {@code onServerTick} 里）：</p>
 * <ol>
 *   <li>取回 worker 结果，按 {@link #applyMaxEntriesPerTick}/{@link #applyBudgetNanos} 分帧应用（可能跨 tick）；</li>
 *   <li>按 {@link #snapshotBudgetNanos} 分帧复制区块快照（多个请求均分，避免一个请求饿死其它）；</li>
 *   <li>接收新请求（只入队，O(1)）。</li>
 * </ol>
 *
 * <p>worker 侧：固定线程池 + 有界队列，只执行 {@link IStructureMachine#check}；结果通过无锁队列回主线程。
 * 池子大小与单个任务耗时都不影响主线程预算——只要上层实现别疯狂分配（GC 停顿会打在主线程上）。</p>
 *
 * <p>注册：{@link #init()} 需要在 mod 初始化时调用一次（commonProxy.preInit）。</p>
 */
public final class StructureScheduler {
    // ---- 可调预算 ----
    /**每 tick 主线程用于复制快照的时间上限（纳秒）。默认 2ms ≈ 250 个区块。**/
    public static long snapshotBudgetNanos = 2_000_000L;
    /**每 tick 主线程用于应用变更的时间上限（纳秒）。**/
    public static long applyBudgetNanos = 1_000_000L;
    /**每 tick 主线程最多应用多少条变更（防止"一次成形一万个部件 + 每个部件一个同步包"砸在同一个 tick）。**/
    public static int applyMaxEntriesPerTick = 256;
    /**同时交给 worker 的请求数上限。**/
    public static int maxConcurrentComputes = 4;
    /**同时在构建快照的请求数上限（限制主线程分配量）。**/
    public static int maxBuildingRequests = 8;
    /**DEFER 之后的基础退避（tick），会随尝试次数线性增长到 8 倍。**/
    public static int retryBackoffTicks = 20;
    /**单个请求盒子的体积上限，超过直接拒绝并记日志（防止上层实现申请 GB 级数组）。**/
    public static int maxBoxVolume = 1_500_000;
    /**worker 单次计算超过这个耗时（纳秒）就记一条日志，便于定位"上层实现太慢"。**/
    public static long slowComputeWarnNanos = 500_000_000L;

    /**统计口径，供 {@code /debug} 或日志查看 API 自身与上层实现的耗时分布。**/
    public static final class Stats {
        public long requests, finished, duplicates, rejected, offThread, defers, appliedEntries, snapshotChunks, droppedResults, slowComputes;
        public long snapshotNanos, computeNanos, applyNanos;
        public void reset() {
            requests = finished = duplicates = rejected = offThread = defers = appliedEntries = snapshotChunks = droppedResults = slowComputes = 0;
            snapshotNanos = computeNanos = applyNanos = 0;
        }
        @Override public String toString() {
            return "requests=" + requests + " finished=" + finished + " dup=" + duplicates + " rejected=" + rejected + " offThread=" + offThread
                 + " defer=" + defers + " dropped=" + droppedResults + " chunks=" + snapshotChunks
                 + " snapshot=" + (snapshotNanos / 1_000_000L) + "ms compute=" + (computeNanos / 1_000_000L) + "ms apply=" + (applyNanos / 1_000_000L) + "ms";
        }
    }
    public static final Stats STATS = new Stats();

    private static boolean sInitialized;
    private static Thread sServerThread;
    private static long sTick;
    private static long sNextRequestId = 1L;
    private static ExecutorService sWorkers;
    private static int sComputingCount;

    /**主线程独占：machine → 当前在飞请求（同一机器同时只允许一个）。**/
    private static final Map<IStructureMachine, StructureRequest> sInFlight = new IdentityHashMap<>();
    private static final ArrayDeque<StructureRequest> sIncoming = new ArrayDeque<>();
    private static final ArrayDeque<StructureRequest> sBuilding = new ArrayDeque<>();
    private static final ArrayDeque<StructureRequest> sWaiting = new ArrayDeque<>();
    private static final ArrayDeque<StructureRequest> sApplying = new ArrayDeque<>();
    /**worker → 主线程的结果队列。**/
    private static final ConcurrentLinkedQueue<StructureRequest> sComputed = new ConcurrentLinkedQueue<>();

    private StructureScheduler() {
    }

    /**在 mod 初始化时调用一次。TickEvent 走 FML 总线，Chunk/World 事件走 Forge 总线。**/
    public static void init() {
        if (sInitialized) return;
        sInitialized = true;
        int threads = Math.max(2, Runtime.getRuntime().availableProcessors() - 1);
        ThreadFactory factory = new ThreadFactory() {
            private final AtomicInteger counter = new AtomicInteger();
            @Override public Thread newThread(Runnable aRunnable) {
                Thread thread = new Thread(aRunnable, "ktfru-structure-" + counter.incrementAndGet());
                thread.setDaemon(true);
                thread.setPriority(Thread.MIN_PRIORITY);
                return thread;
            }
        };
        sWorkers = new ThreadPoolExecutor(threads, threads, 30L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(32), factory, new ThreadPoolExecutor.AbortPolicy());
        ((ThreadPoolExecutor) sWorkers).allowCoreThreadTimeOut(true);

        StructureScheduler handler = new StructureScheduler();
        cpw.mods.fml.common.FMLCommonHandler.instance().bus().register(handler);
        MinecraftForge.EVENT_BUS.register(handler);
    }

    /**
     * 发起一次结构检查。<b>必须在服务端主线程调用</b>（机器在自己的 tick 里调）。
     * 只做入队和少量校验，真正的快照复制发生在随后的预算阶段。
     *
     * @return true = 已受理；false = 已有在飞请求 / 盒子非法 / 非主线程（机器下一 tick 再试即可）
     */
    public static boolean request(IStructureMachine aMachine, World aWorld, String aDesc) {
        if (aMachine == null || aWorld == null || aWorld.isRemote) return false;
        if (sServerThread != null && Thread.currentThread() != sServerThread) {STATS.offThread++; return false;}
        if (sInFlight.containsKey(aMachine)) {STATS.duplicates++; return false;}

        StructureBox box = aMachine.structureBox(aWorld);
        if (box == null || box.volume() <= 0) return false;
        if (box.volume() > maxBoxVolume) {
            STATS.rejected++;
            FMLLog.log(Level.ERROR, "[kTFRU] structure box too large: %d blocks (%s)", box.volume(), aMachine.describe());
            return false;
        }

        StructureRequest request = new StructureRequest(aMachine, aWorld, box, aMachine.freezeParams(), aDesc, sNextRequestId++);
        sInFlight.put(aMachine, request);
        sIncoming.add(request);
        STATS.requests++;
        return true;
    }

    /**机器被拆/卸载时调用（主线程），丢弃在飞请求。**/
    public static void cancel(IStructureMachine aMachine) {
        StructureRequest request = sInFlight.remove(aMachine);
        if (request != null) request.state = StructureRequest.S_CANCELLED;
    }

    public static boolean isBusy(IStructureMachine aMachine) {return sInFlight.containsKey(aMachine);}

    /**
     * 本地同步检查：在<b>当前线程</b>直接构建快照并跑一次 {@link IStructureMachine#check}，不走线程池、不应用变更、
     * 不写机器状态。用于客户端渲染数据、调试、单机自检——调用者必须是这个世界的主线程
     * （服务端主线程，或客户端线程）。
     *
     * <p>开销与盒子体积成正比（典型机器几毫秒），所以别每 tick 调，绑定到"结构可能变了"的事件上。
     * 结果通过 {@link IStructureMachine#onLocalCheckFinished} 回给机器，变更列表被丢弃。</p>
     *
     * @return 三态结果；盒子非法时返回 {@link StructureStatus#DEFER}
     */
    public static byte checkLocally(IStructureMachine aMachine, World aWorld) {
        if (aMachine == null || aWorld == null) return StructureStatus.DEFER;
        if (sServerThread != null && !aWorld.isRemote && Thread.currentThread() != sServerThread) {
            STATS.offThread++;
            return StructureStatus.DEFER;
        }
        StructureBox box = aMachine.structureBox(aWorld);
        if (box == null || box.volume() <= 0 || box.volume() > maxBoxVolume) return StructureStatus.DEFER;
        try {
            StructureSnapshot snapshot = StructureSnapshot.allocate(box, aWorld.provider.dimensionId, 0L, sTick);
            SnapshotBuilder.step(aWorld, snapshot, new SnapshotBuilder.Cursor(), 1L << 60, aMachine);
            if (!snapshot.isComplete()) return StructureStatus.DEFER;      // 快照不完整就不许拿它做判断(未复制的段会被读成空气)
            StructureChangeList discarded = new StructureChangeList();
            byte outcome = aMachine.check(snapshot, aMachine.freezeParams(), new StructureScratch(box.volume()), discarded);
            aMachine.onLocalCheckFinished(aWorld, outcome, snapshot);
            return outcome;
        } catch (Throwable t) {
            FMLLog.log(Level.ERROR, t, "[kTFRU] local structure check failed: " + aMachine.describe());
            return StructureStatus.DEFER;
        }
    }

    /**某个世界卸载时丢弃它的所有请求（避免持有 World 引用）。**/
    private static void cancelWorld(World aWorld) {
        for (Iterator<Map.Entry<IStructureMachine, StructureRequest>> it = sInFlight.entrySet().iterator(); it.hasNext(); ) {
            StructureRequest request = it.next().getValue();
            if (request.world == aWorld) {request.state = StructureRequest.S_CANCELLED; it.remove();}
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent aEvent) {
        if (aEvent.phase != TickEvent.Phase.END) return;
        Thread thread = Thread.currentThread();
        if (sServerThread == null) sServerThread = thread;
        else if (sServerThread != thread) return;

        sTick++;
        wakeRetries();
        collectComputed();
        applyPending();
        advanceBuilding();
    }

    /**区块被加载时叫醒等待中的请求，避免 DEFER 只能靠退避慢慢等。**/
    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load aEvent) {
        if (aEvent.getChunk().worldObj.isRemote) return;                            // 客户端世界的加载事件别去碰服务端队列
        if (sServerThread != null && Thread.currentThread() != sServerThread) return;
        int cx = aEvent.getChunk().xPosition, cz = aEvent.getChunk().zPosition;
        for (StructureRequest request : sWaiting) {
            if (request.retryAtTick <= sTick) continue;
            if (cx < (request.box.minX >> 4) || cx > (request.box.maxX() >> 4)) continue;
            if (cz < (request.box.minZ >> 4) || cz > (request.box.maxZ() >> 4)) continue;
            request.retryAtTick = sTick;
        }
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload aEvent) {
        if (aEvent.world.isRemote) return;
        cancelWorld(aEvent.world);
    }

    // ---- 主线程流水线各段 ----

    private static void wakeRetries() {
        for (Iterator<StructureRequest> it = sWaiting.iterator(); it.hasNext(); ) {
            StructureRequest request = it.next();
            if (request.retryAtTick > sTick) continue;
            it.remove();
            request.resetForRetry();
            sIncoming.add(request);
        }
    }

    private static void collectComputed() {
        StructureRequest request;
        while ((request = sComputed.poll()) != null) {
            sComputingCount--;
            STATS.computeNanos += request.computeNanos;
            if (request.computeNanos > slowComputeWarnNanos) {
                STATS.slowComputes++;
                FMLLog.log(Level.WARN, "[kTFRU] slow structure check: %s took %dms (%d chunks, %d changes)",
                        request.desc, request.computeNanos / 1_000_000L, request.snapshot.chunkCount(), request.changes.size());
            }
            if (sInFlight.get(request.machine) != request) {STATS.droppedResults++; continue;}
            if (!request.machine.isStillValid(request.world)) {cancel(request.machine); continue;}
            if (request.outcome == StructureStatus.DEFER) {STATS.defers++; scheduleRetry(request); continue;}
            if (request.changes.overflowed()) {
                FMLLog.log(Level.WARN, "[kTFRU] structure change list overflowed: %s (%d)", request.desc, request.changes.size());
                request.failedCount++;
            }
            request.state = StructureRequest.S_APPLYING;
            sApplying.add(request);
        }
    }

    private static void applyPending() {
        if (sApplying.isEmpty()) return;
        long deadline = System.nanoTime() + applyBudgetNanos;
        int entries = 0;
        for (Iterator<StructureRequest> it = sApplying.iterator(); it.hasNext(); ) {
            StructureRequest request = it.next();
            while (request.applyCursor < request.changes.size()) {
                if (entries >= applyMaxEntriesPerTick || System.nanoTime() >= deadline) return;   // 预算用完: 保留在队列里, 下 tick 续做
                if (request.machine.applyChange(request.world, request.changes, request.applyCursor)) STATS.appliedEntries++;
                else request.failedCount++;
                request.applyCursor++;
                entries++;
            }
            it.remove();
            finish(request);
        }
    }

    private static void advanceBuilding() {
        while (!sIncoming.isEmpty() && sBuilding.size() < maxBuildingRequests) {
            StructureRequest request = sIncoming.poll();
            if (sInFlight.get(request.machine) != request) continue;
            if (request.snapshot == null) request.snapshot = StructureSnapshot.allocate(request.box, request.world.provider.dimensionId, request.requestId, sTick);
            request.state = StructureRequest.S_BUILDING;
            sBuilding.add(request);
        }
        if (sBuilding.isEmpty()) return;

        long slice = Math.max(50_000L, snapshotBudgetNanos / Math.max(1, sBuilding.size()));
        for (Iterator<StructureRequest> it = sBuilding.iterator(); it.hasNext(); ) {
            StructureRequest request = it.next();
            if (sInFlight.get(request.machine) != request) {it.remove(); continue;}
            long start = System.nanoTime();
            boolean done = SnapshotBuilder.step(request.world, request.snapshot, request.cursor, slice, request.machine);
            request.snapshotNanos += System.nanoTime() - start;
            STATS.snapshotNanos += System.nanoTime() - start;
            if (!done) continue;
            if (sComputingCount >= maxConcurrentComputes) continue;      // 等 worker 空闲, 快照保持 complete 状态
            it.remove();
            submit(request);
        }
    }

    private static void submit(StructureRequest request) {
        request.state = StructureRequest.S_COMPUTING;
        sComputingCount++;
        STATS.snapshotChunks += request.snapshot.chunkCount();
        try {
            sWorkers.execute(() -> {
                long start = System.nanoTime();
                try {
                    request.outcome = request.machine.check(request.snapshot, request.params, request.scratch, request.changes);
                } catch (Throwable t) {
                    request.outcome = StructureStatus.DEFER;
                    request.changes.clear();
                    FMLLog.log(Level.ERROR, t, "[kTFRU] structure check threw: " + request.desc);
                } finally {
                    request.computeNanos = System.nanoTime() - start;
                    sComputed.add(request);
                }
            });
        } catch (Throwable t) {
            // 队列满/池子关闭: 退回去下个 tick 再提交
            sComputingCount--;
            request.state = StructureRequest.S_BUILDING;
            sBuilding.add(request);
        }
    }

    private static void scheduleRetry(StructureRequest request) {
        request.attempts++;
        request.resetForRetry();
        request.retryAtTick = sTick + (long) retryBackoffTicks * Math.min(request.attempts, 8);
        sWaiting.add(request);
    }

    private static void finish(StructureRequest request) {
        request.state = StructureRequest.S_DONE;
        try {
            request.machine.onCheckFinished(request.world, request.outcome, request.changes, request.failedCount);
        } catch (Throwable t) {
            FMLLog.log(Level.ERROR, t, "[kTFRU] structure onCheckFinished threw: " + request.desc);
        }
        sInFlight.remove(request.machine);
        STATS.finished++;
    }
}
