/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

package cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate;

import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;

public interface IStructurePredicate {
    default boolean validate(StructureContext ctx, int x, int y, int z){
        switch (ctx.mode){
            case SET:return set(ctx, x, y, z);
            case PROJECT:return project(ctx, x, y, z);
            case CHECK_ASYNC:return checkAsync(ctx, x, y, z);
            case RESET:return reset(ctx, x, y, z);
            case CHECK:default:return check(ctx, x, y, z);
        }
    }
    /**这个谓词是否已经支持异步(快照)模式; 只要有一个不支持, 整个结构就退回同步检查。**/
    default boolean supportsAsync() {return false;}
    /**
     * 异步模式: 只允许读 {@link StructureContext#view}、只允许往 {@link StructureContext#changes} 里写,
     * <b>不要碰世界、不要改部件</b>。数据看不到(未加载区块)就 {@link StructureContext#defer()} 后返回 false。
     * 默认实现故意抛异常: 漏迁的谓词会在异步道路上立刻报错(worker 捕获后降级成 DEFER), 而不是偷偷碰世界。
     */
    default boolean checkAsync(StructureContext ctx, int x, int y, int z) {
        throw new UnsupportedOperationException(getClass().getName() + " 还没有实现 checkAsync");
    }
    default void preCheck(StructureContext ctx, char myID){}
    default void afterCheck(StructureContext ctx, char myID){}
    boolean check(StructureContext ctx, int x, int y, int z);
    boolean project(StructureContext ctx, int x, int y, int z);
    boolean set(StructureContext ctx, int x, int y, int z);
    boolean reset(StructureContext ctx, int x, int y, int z);
}
