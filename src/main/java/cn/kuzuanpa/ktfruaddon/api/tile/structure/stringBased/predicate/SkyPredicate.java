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
import cn.kuzuanpa.ktfruaddon.client.kTFRUAddonARProjectorCompact;
import gregapi.util.WD;
import net.minecraft.init.Blocks;
import zmaster587.libVulpes.block.BlockMeta;

public class SkyPredicate implements IStructurePredicate {
    public SkyPredicate() {
    }

    @Override
    public boolean check(StructureContext ctx, int x, int y, int z) {
        return ctx.world.canBlockSeeTheSky(x,y, z);
    }
    @Override
    public boolean set(StructureContext ctx, int x, int y, int z) {
        if(WD.easyRep(ctx.world, x, y, z))ctx.world.setBlock(x, y, z, Blocks.air);
        return true;
    }

    @Override
    public boolean reset(StructureContext ctx, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean project(StructureContext ctx, int x, int y, int z) {
        kTFRUAddonARProjectorCompact.projectBlock(ctx.world, x,y,z,  new BlockMeta(Blocks.glass, 0, "Must See Sky"));
        return true;
    }
}

