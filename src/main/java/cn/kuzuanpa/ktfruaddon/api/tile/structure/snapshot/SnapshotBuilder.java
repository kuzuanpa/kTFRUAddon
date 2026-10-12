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

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

/**
 * 主线程快照构建器：把请求盒子里的区块数据复制进 {@link StructureSnapshot}，可分帧续做。
 *
 * <p>关键点：</p>
 * <ol>
 *   <li>用 {@code chunkProvider.chunkExists} 判断是否加载（只查表，不触发加载）；未加载的区块只置一个位图标记，
 *       不复制任何数据，于是在 worker 侧表现为 {@link StructureStatus#UNLOADED}。</li>
 *   <li>只复制与盒子 y 范围相交的 y 段，而且是原样整段 {@code System.arraycopy}（实测 40ns/4KB，
 *       比逐格 {@code getBlock} 快 5~10 倍，也不做任何解码）；段本身不存在
 *       （{@code getBlockStorageArray()[section] == null}）就一个数组都不分配，直接记作"已知空气"。</li>
 *   <li>部件 key 走 {@code chunk.chunkTileEntityMap} <b>整体遍历一次</b>，不是逐格查方块实体
 *       （逐格 {@code getTileEntity} 在 1.7.10 是 20~46ns/次且每次都会 new ChunkPosition）。</li>
 * </ol>
 *
 * <p>依赖 {@code ktfruaddon_at.cfg} 里的三条 AT（{@code field_76680_d/field_76681_e/field_76678_f}）。
 * 改完 AT 必须用 Java 8 重跑 {@code ./gradlew setupDecompWorkspace} 才会对编译期生效。</p>
 *
 * <p>布局前提：本 fork 的 {@code ExtendedBlockStorage} 与原版一致（id = 低 8 位 byte + 高 4 位 nibble，
 * 12 bit 空间）。所以方块 id 只能表示"是哪种方块"，表示不了 GT6 的 MTE 登记号（如 18002）——
 * 部件身份一律走 {@link IStructureMachine#encodePart}。</p>
 */
public final class SnapshotBuilder {
    private SnapshotBuilder() {
    }

    /**构建进度：已经处理过多少个区块。**/
    public static final class Cursor {
        int mChunk;
        public boolean isDone() {return mChunk < 0;}
        public void reset() {mChunk = 0;}
    }

    /**
     * 推进构建，最多花 {@code aBudgetNanos}（按区块粒度检查预算，所以可能略微超出）。
     *
     * @return true = 快照已完整（{@link StructureSnapshot#isComplete()}），可以交给 worker
     */
    public static boolean step(World aWorld, StructureSnapshot aSnapshot, Cursor aCursor, long aBudgetNanos, IStructureMachine aMachine) {
        if (aSnapshot.isComplete()) return true;
        long now = System.nanoTime();
        long deadline = aBudgetNanos > Long.MAX_VALUE - now ? Long.MAX_VALUE : now + aBudgetNanos;   // 防溢出, 否则"无限预算"会立刻判定超时
        int total = aSnapshot.chunkCount();
        while (aCursor.mChunk < total) {
            copyChunk(aWorld, aSnapshot, aCursor.mChunk, aMachine);
            aCursor.mChunk++;
            if (System.nanoTime() >= deadline) return false;
        }
        aSnapshot.markComplete();
        return true;
    }

    private static void copyChunk(World aWorld, StructureSnapshot aSnapshot, int aChunkIndex, IStructureMachine aMachine) {
        int cx = aSnapshot.mMinChunkX + aChunkIndex % aSnapshot.mChunksX;
        int cz = aSnapshot.mMinChunkZ + aChunkIndex / aSnapshot.mChunksX;
        if (!aWorld.getChunkProvider().chunkExists(cx, cz)) {
            aSnapshot.markChunkUnloaded(aChunkIndex);
            return;
        }
        Chunk chunk = aWorld.getChunkFromChunkCoords(cx, cz);
        if (chunk == null) {aSnapshot.markChunkUnloaded(aChunkIndex); return;}

        ExtendedBlockStorage[] storage = chunk.getBlockStorageArray();
        // 只处理真正分配过的段; 盒子 y 范围内不存在的段保持 null = 已知空气
        for (int s = 0; s < aSnapshot.mSections; s++) {
            int section = aSnapshot.mMinSection + s;
            if (section >= storage.length || storage[section] == null) continue;
            ExtendedBlockStorage ebs = storage[section];
            int slot = aSnapshot.chunkSectionIndex(aChunkIndex, s);

            byte[] lsb = new byte[4096];
            System.arraycopy(ebs.blockLSBArray, 0, lsb, 0, 4096);
            aSnapshot.mLsb[slot] = lsb;
            NibbleArray msbSource = ebs.blockMSBArray;
            if (msbSource != null && msbSource.data != null) {
                byte[] msb = new byte[2048];
                System.arraycopy(msbSource.data, 0, msb, 0, 2048);
                aSnapshot.mMsb[slot] = msb;
            }
            if (aSnapshot.mMeta != null) {
                byte[] meta = new byte[2048];
                NibbleArray metaSource = ebs.blockMetadataArray;
                if (metaSource != null && metaSource.data != null) System.arraycopy(metaSource.data, 0, meta, 0, 2048);
                aSnapshot.mMeta[slot] = meta;
            }
        }
        if (aSnapshot.mParts != null) copyParts(chunk, aSnapshot, aChunkIndex, aMachine);
    }

    /**按 TE 表整体遍历编码部件 key（TE 数量远小于格子数量）。**/
    private static void copyParts(Chunk aChunk, StructureSnapshot aSnapshot, int aChunkIndex, IStructureMachine aMachine) {
        if (aChunk.chunkTileEntityMap.isEmpty()) return;
        for (Object value : aChunk.chunkTileEntityMap.values()) {
            if (!(value instanceof TileEntity)) continue;
            TileEntity te = (TileEntity) value;
            if (!aSnapshot.inBox(te.xCoord, te.yCoord, te.zCoord)) continue;
            int key = aMachine.encodePart(te);
            if (key == 0) continue;
            int slot = aSnapshot.chunkSectionIndex(aChunkIndex, aSnapshot.sectionIndexInChunk(te.yCoord));
            short[] parts = aSnapshot.mParts[slot];
            if (parts != null) parts[StructureSnapshot.local(te.xCoord, te.yCoord, te.zCoord)] = (short) key;
        }
    }
}
