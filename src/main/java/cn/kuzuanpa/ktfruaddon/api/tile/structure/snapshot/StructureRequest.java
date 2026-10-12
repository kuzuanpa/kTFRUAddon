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

import net.minecraft.world.World;

/**
 * 调度器内部的一次请求状态机。<b>机器不应直接使用本类</b>，通过 {@link StructureScheduler} 交互。
 *
 * <p>生命周期：QUEUED →(主线程按预算)→ BUILDING → COMPUTING(worker) → APPLYING(主线程按预算)→ DONE；
 * 结果为 DEFER 时回到 QUEUED 并带退避延迟。World 引用只在本对象里出现，且只在主线程被访问。</p>
 */
final class StructureRequest {
    static final byte S_QUEUED = 0, S_BUILDING = 1, S_COMPUTING = 2, S_APPLYING = 3, S_DONE = 4, S_CANCELLED = 5;

    final IStructureMachine machine;
    final World world;
    final StructureBox box;
    final StructureParams params;
    final String desc;
    final long requestId;

    final SnapshotBuilder.Cursor cursor = new SnapshotBuilder.Cursor();
    final StructureChangeList changes = new StructureChangeList();
    final StructureScratch scratch;

    StructureSnapshot snapshot;
    byte state = S_QUEUED;
    byte outcome = StructureStatus.DEFER;
    int applyCursor;
    int failedCount;
    int attempts;
    long retryAtTick;
    /**worker 线程写入，主线程读取（跨线程通过 ConcurrentLinkedQueue 发布）。**/
    volatile long computeNanos;
    long snapshotNanos;

    StructureRequest(IStructureMachine aMachine, World aWorld, StructureBox aBox, StructureParams aParams, String aDesc, long aRequestId) {
        machine = aMachine; world = aWorld; box = aBox; params = aParams; desc = aDesc; requestId = aRequestId;
        scratch = new StructureScratch(aBox.volume());
    }

    /**重试前复位所有分帧状态（保留请求身份与世界引用）。**/
    void resetForRetry() {
        snapshot = null;
        cursor.reset();
        changes.clear();
        applyCursor = 0;
        failedCount = 0;
        outcome = StructureStatus.DEFER;
        state = S_QUEUED;
    }
}
