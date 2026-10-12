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
import org.jetbrains.annotations.Nullable;

/**
 * 机器侧契约。实现在不同的线程域里被调用，注释里标明了每一边。
 *
 * <p>调用顺序（一次请求）：主线程 {@link #structureBox} → {@link #freezeParams} →
 * [主线程分帧构建快照, 期间对每个有方块实体的位置调用 {@link #encodePart}] →
 * worker {@link #check} → 主线程分帧 {@link #applyChange} → 主线程 {@link #onCheckFinished}。</p>
 */
public interface IStructureMachine {
    /**
     * <b>worker 线程</b>。纯函数：只读 view/params/scratch，只往 out 里写变更，不碰世界、不碰机器字段。
     *
     * @return {@link StructureStatus#FORM} / {@link StructureStatus#NOT_FORM} / {@link StructureStatus#DEFER}
     */
    byte check(StructureView aView, StructureParams aParams, StructureScratch aScratch, StructureChangeList aOut);

    /**
     * <b>主线程</b>。声明本次检查要覆盖的世界盒子（必须包住 check 会读到的所有位置，含泛洪的 y-1 层）。
     * 返回 null 表示本轮跳过（例如机器还没初始化好）。
     */
    @Nullable StructureBox structureBox(World aWorld);

    /**
     * <b>主线程</b>。把机器参数（facing/尺寸/偏移…）冻结成一份不可变值；worker 只看得到它。
     * 需要额外字段的机器自己实现 {@link StructureParams}。
     */
    StructureParams freezeParams();

    /**
     * <b>主线程</b>，快照构建期调用。把该位置的方块实体编码成部件 key（0 = 不是部件）。
     * 典型实现：{@code te instanceof MultiTileEntityMultiBlockPart} → 打包 (registryID, MTE id)；
     * 返回 0 的位置在快照里就是"没有部件"，worker 侧不需要任何 TileEntity。
     */
    default int encodePart(TileEntity aTileEntity) {return 0;}

    /**
     * <b>主线程</b>。应用一条变更（{@code aChanges} 的第 {@code aIndex} 条）。这是唯一允许调
     * {@code utils.setTarget} / {@code part.setTarget} 的地方，而且必须在这里<b>复核</b>：
     * 区块仍加载、方块实体还是快照里那个、期望值仍匹配；失效就返回 false（跳过这条）。
     *
     * @return true = 已应用，false = 该条已失效/失败（会计入 failedCount）
     */
    boolean applyChange(World aWorld, StructureChangeList aChanges, int aIndex);

    /**
     * <b>主线程</b>。本轮定稿：写 {@code mStructureOkay}、{@code onStructureChange()} 等。
     * 若 {@code aFailedCount > 0}（有必需变更没落地），机器应当判为未成形。
     */
    void onCheckFinished(World aWorld, byte aOutcome, StructureChangeList aChanges, int aFailedCount);

    /**<b>主线程</b>。方块实体是否还有效（尺寸被拆/区块卸载/被替换时应返回 false，调度器会丢弃结果）。**/
    default boolean isStillValid(World aWorld) {return true;}

    /**
     * <b>调用线程</b>（{@link StructureScheduler#checkLocally} 专用）。本地同步检查跑完后回调，
     * 此时 {@code aView} 只有当前线程访问过，可以直接拿它算那些"只用于本地渲染/调试"的派生数据。
     * 注意：本地模式<b>不会</b>应用变更，也不会改机器状态。
     */
    default void onLocalCheckFinished(World aWorld, byte aOutcome, StructureView aView) {}

    /**日志用名字。**/
    default String describe() {return getClass().getSimpleName();}
}
