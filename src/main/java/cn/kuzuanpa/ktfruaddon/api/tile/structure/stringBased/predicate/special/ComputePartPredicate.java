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

package cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.special;

import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputePart;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.SpecialPartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import cn.kuzuanpa.ktfruaddon.client.kTFRUAddonARProjectorCompact;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import zmaster587.libVulpes.block.BlockMeta;

public class ComputePartPredicate extends SpecialPartPredicate {
    public ComputePartPredicate(TileDesc... fallbacks){
        super(fallbacks);
    }
    @Override
    public boolean check(StructureContext ctx, int x, int y, int z) {
        TileEntity part = ctx.controller.getTileEntity(x,y,z);
        if(part instanceof IComputePart) {
            utils.setTarget(ctx.controller, part, 0, MultiTileEntityMultiBlockPart.ONLY_ENERGY, false);
            ((IReceiveSpecialPart)ctx.controller).receiveSpecialPart(new ChunkCoordinates(x,y,z), part);
            return true;
        }
        return utils.checkAndSetTarget(ctx.controller, x, y, z, null, null, null, expected, allowPartShare);
    }

    @Override
    public boolean set(StructureContext ctx, int x, int y, int z) {
        if(ctx.controller.getTileEntity(x,y,z) instanceof IComputePart) {
            return true;
        }
        return super.set(ctx, x, y, z);
    }

    @Override
    public boolean project(StructureContext ctx, int x, int y, int z) {
        BlockMeta meta = getMetaBlockForGTTile(GTTileEntityRegistry.ktfruaddon, 32005);
        meta.overrideName = "ktfru.part.compute";
        return kTFRUAddonARProjectorCompact.projectBlock(ctx.world, x,y,z,  meta);
    }
}
