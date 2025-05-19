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
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;

public class PartPredicate implements IStructurePredicate {
    private final TileDesc[] expected;
    private boolean allowPartShare = false;

    public PartPredicate(TileDesc expected) {
        this.expected = new TileDesc[]{expected};
    }
    public PartPredicate(TileDesc... expected) {
        this.expected = expected;
    }
    public PartPredicate allowShare(){
        allowPartShare = true;
        return this;
    }

    @Override
    public boolean matches(StructureContext ctx, int x, int y, int z) {
        return utils.checkAndSetTarget(ctx.controller, x,y,z, null, null, null, expected, allowPartShare);
    }
    @Override
    public boolean set(StructureContext ctx, int x, int y, int z) {
        return utils.checkAndSetTarget(ctx.controller, x,y,z, null, ctx.player, ctx.inventory, expected, allowPartShare);
    }
}

