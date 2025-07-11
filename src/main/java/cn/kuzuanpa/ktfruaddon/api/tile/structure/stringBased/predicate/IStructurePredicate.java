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
            case CHECK:return check(ctx, x, y, z);
        }
        return false;
    }
    boolean check(StructureContext ctx, int x, int y, int z);
    boolean project(StructureContext ctx, int x, int y, int z);
    boolean set(StructureContext ctx, int x, int y, int z);
}
