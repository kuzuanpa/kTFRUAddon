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

/**
 * worker 线程能看到的全部世界信息。这是唯一允许在 {@code check()} 里读世界的入口。
 *
 * <p>坐标一律用世界坐标。盒子（{@link #minX()}..{@link #minX()}+{@link #sizeX()}-1 等）之外的读取返回
 * {@link StructureStatus#NO_DATA}，机器不应该依赖这个行为，而应该把要用到的范围都包进请求盒子。</p>
 *
 * <p>访问顺序建议按 {@link #index} 递增（区块内 x→z→y 的线性序），对 CPU 预取最友好。</p>
 */
public interface StructureView {
    int minX();
    int minY();
    int minZ();
    int sizeX();
    int sizeY();
    int sizeZ();

    /**规范线性索引 {@code ((z-minZ)*sizeY + (y-minY))*sizeX + (x-minX)}。供机器自己的 visited 数组对齐使用。**/
    int index(int aX, int aY, int aZ);

    boolean inBox(int aX, int aY, int aZ);

    /**
     * 该位置的状态：{@code 0..4095} = 方块 id，或负数的哨兵（见 {@link StructureStatus}）。
     * 未加载区块返回 {@link StructureStatus#UNLOADED}，y 越界返回 {@link StructureStatus#OUT_OF_WORLD}。
     */
    int statusAt(int aX, int aY, int aZ);

    /**同 {@link #statusAt}，语义化别名（机器按"这里是什么方块"来读时更顺）。**/
    int blockIdAt(int aX, int aY, int aZ);

    /**方块 metadata（0..15）。请求未包含 metadata 时恒为 0。**/
    int metaAt(int aX, int aY, int aZ);

    /**
     * 该位置方块实体编码出来的部件 key（由 {@link IStructureMachine#encodePart} 产生），0 = 没有部件。
     * 请求未包含部件数据时恒为 0。这样 worker 不需要、也没法去拿 TileEntity。
     */
    int partKeyAt(int aX, int aY, int aZ);

    /**请求盒子里未加载的区块数量。0 表示这次快照覆盖的区域都是已知的。**/
    int unknownChunkCount();

    boolean isChunkUnknown(int aChunkX, int aChunkZ);

    /**
     * 把未知区块坐标写进 {@code out}（成对 [cx, cz, cx, cz ...]），返回写入的<b>区块个数</b>。
     * 用于机器判断"我真正需要的区域是否落在未知里"。
     */
    int collectUnknownChunks(int[] aOut);

    /**子盒子是否与未知区域相交。O(子盒子的区块数)，机器用它做廉价的"要不要 DEFER"判断。**/
    boolean subBoxHasUnknown(int aX, int aY, int aZ, int aSizeX, int aSizeY, int aSizeZ);

    /**发起请求时的服务端 tick（{@code world.getTotalWorldTime()} 口径由调度器统一）。用于机器判断快照新鲜度。**/
    long requestedTick();

    /**请求序号，单调递增，可用于日志与去重。**/
    long requestId();
}
