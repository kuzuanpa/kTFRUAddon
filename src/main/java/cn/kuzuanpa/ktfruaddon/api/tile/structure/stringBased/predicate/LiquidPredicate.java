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
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import zmaster587.libVulpes.block.BlockMeta;

public class LiquidPredicate implements IStructurePredicate {
    @Override
    public boolean check(StructureContext ctx, int x, int y, int z) {
        Block block = ctx.world.getBlock(x,y, z);
        return block instanceof BlockLiquid;
    }
    @Override
    public boolean set(StructureContext ctx, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean reset(StructureContext ctx, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean project(StructureContext ctx, int x, int y, int z) {
        if(!(ctx.world.getBlock(x,y,z) instanceof BlockLiquid))kTFRUAddonARProjectorCompact.projectBlock(ctx.world, x,y,z,  new BlockMeta(Blocks.water, 0, "ktfru.part.liquid"));
        return true;
    }
}
