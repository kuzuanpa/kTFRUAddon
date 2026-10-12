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
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureChangeList;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.PartKeys;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import cn.kuzuanpa.ktfruaddon.client.kTFRUAddonARProjectorCompact;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.block.multitileentity.MultiTileEntityContainer;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import net.minecraft.nbt.NBTTagCompound;
import zmaster587.libVulpes.block.BlockMeta;

import java.util.ArrayList;
import java.util.List;

public class PartPredicate implements IStructurePredicate {
    @Override public boolean supportsAsync() {return true;}
    /**异步: 只比部分 key(不碰方块实体); 需要动目标就写进 ctx.changes。**/
    @Override public boolean checkAsync(StructureContext ctx, int x, int y, int z) {
        int key = PartKeys.cellKey(ctx, x, y, z);
        if (key == PartKeys.UNKNOWN) return false;
        for (TileDesc desc : expected) {
            int expectedKey = PartKeys.pack(desc.aRegistry.currentID(), desc.aRegistryMeta);
            if ((key & PartKeys.MASK) == expectedKey) {
                if (!PartKeys.isTargeted(key)) ctx.changes.add(x, y, z, StructureChangeList.KIND_SET_PART, expectedKey, desc.aDesign, desc.aUsage | (allowPartShare ? PartKeys.FLAG_ALLOW_SHARE : 0));
                return true;
            }
        }
        return false;
    }
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
    public boolean check(StructureContext ctx, int x, int y, int z) {
        return utils.checkAndSetTarget(ctx.controller, x,y,z, null, null, null, expected, allowPartShare);
    }
    @Override
    public boolean set(StructureContext ctx, int x, int y, int z) {
        return utils.checkAndSetTarget(ctx.controller, x,y,z, null, ctx.player, ctx.inventory, expected, allowPartShare);
    }

    @Override
    public boolean reset(StructureContext ctx, int x, int y, int z) {
        return utils.resetTarget(ctx.controller, x,y, z, 0);
    }
    @Override
    public boolean project(StructureContext ctx, int x, int y, int z) {
        if(expected.length == 1) kTFRUAddonARProjectorCompact.projectBlock(ctx.world, x,y,z,  getMetaBlockForGTTile(expected[0].aRegistry, expected[0].aRegistryMeta));
        else {
            List<BlockMeta> list = new ArrayList<>();
            for(TileDesc desc : expected)list.add(getMetaBlockForGTTile(desc.aRegistry, desc.aRegistryMeta));
            kTFRUAddonARProjectorCompact.projectBlock(ctx.world, x,y,z, list);
        }
        return true;
    }

    public static BlockMeta getMetaBlockForGTTile(MultiTileEntityRegistry registry, int id){
        MultiTileEntityContainer container = registry.getNewTileEntityContainer(id, new NBTTagCompound());
        ((IMultiTileEntity) container.mTileEntity).setShouldRefresh(false);
        return new BlockMeta(container.mBlock,container.mTileEntity);
    }
}

