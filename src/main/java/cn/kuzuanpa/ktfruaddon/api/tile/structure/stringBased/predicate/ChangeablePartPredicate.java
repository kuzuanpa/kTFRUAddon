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
import cn.kuzuanpa.ktfruaddon.client.kTFRUAddonARProjectorRegister;
import cpw.mods.fml.common.FMLLog;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.block.multitileentity.MultiTileEntityContainer;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.Level;
import zmaster587.libVulpes.block.BlockMeta;

import java.util.ArrayList;
import java.util.List;

public class ChangeablePartPredicate implements IStructurePredicate {
    private TileDesc[] expected = null;
    private boolean allowPartShare = false;

    public ChangeablePartPredicate() {
    }
    public ChangeablePartPredicate allowShare(){
        allowPartShare = true;
        return this;
    }

    @Override
    public void preCheck(StructureContext ctx,char myID) {
        IStructurePredicate.super.preCheck(ctx, myID);
        if(! (ctx.controller instanceof IChangeablePartSupplier)) {
            FMLLog.log(Level.ERROR, ctx.controller.toString()+"not instanceof IChangeablePartSupplier! This is a bug on coding!");
            return;
        }
        expected = ((IChangeablePartSupplier) ctx.controller).getChangeablePartDesc(myID);
    }

    @Override
    public boolean check(StructureContext ctx, int x, int y, int z) {
        if(expected == null)return false;
        return utils.checkAndSetTarget(ctx.controller, x,y,z, null, null, null, expected, allowPartShare);
    }
    @Override
    public boolean set(StructureContext ctx, int x, int y, int z) {
        if(expected == null)return false;
        return utils.checkAndSetTarget(ctx.controller, x,y,z, null, ctx.player, ctx.inventory, expected, allowPartShare);
    }

    @Override
    public boolean reset(StructureContext ctx, int x, int y, int z) {
        if(expected == null)return false;
        return utils.resetTarget(ctx.controller, x,y, z, 0);
    }
    @Override
    public boolean project(StructureContext ctx, int x, int y, int z) {
        if(expected == null)return false;
        if(expected.length == 1) kTFRUAddonARProjectorRegister.setProjectBlock(ctx.world, x,y,z,  getMetaBlockForGTTile(expected[0].aRegistry, expected[0].aRegistryMeta));
        else {
            List<BlockMeta> list = new ArrayList<>();
            for(TileDesc desc : expected)list.add(getMetaBlockForGTTile(desc.aRegistry, desc.aRegistryMeta));
            kTFRUAddonARProjectorRegister.setProjectBlock(ctx.world, x,y,z, list);
        }
        return true;
    }

    public static BlockMeta getMetaBlockForGTTile(MultiTileEntityRegistry registry, int id){
        MultiTileEntityContainer container = registry.getNewTileEntityContainer(id, new NBTTagCompound());
        ((IMultiTileEntity) container.mTileEntity).setShouldRefresh(false);
        return new BlockMeta(container.mBlock,container.mTileEntity);
    }

    public interface IChangeablePartSupplier {
        TileDesc[] getChangeablePartDesc(char identifier);
    }
}

