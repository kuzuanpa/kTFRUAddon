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

package cn.kuzuanpa.ktfruaddon.tile.research;

import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import cn.kuzuanpa.ktfruaddon.api.tile.util.kTileNBT;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.block.multitileentity.IWailaTile;
import gregapi.gui.ContainerClientDefault;
import gregapi.gui.ContainerCommonDefault;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.util.UT;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

import static gregapi.data.CS.T;

public class ResearchTablePrinter extends ResearchTableBase implements IMultiTileEntity.IMTE_SyncDataByteArray, IWailaTile {
    public int interval=100, progress;

    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerClientDefault(aPlayer.inventory, this, aGUIID);
    }
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerCommonDefault(aPlayer.inventory, this,aGUIID);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT, aNBT, progress);
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if(aNBT.hasKey(kTileNBT.INTERVAL))interval = aNBT.getInteger(kTileNBT.INTERVAL);
        if(aNBT.hasKey("progress"))progress = aNBT.getInteger("progress");
    }

    @Override public String getTileEntityName() {return "ktfru.multitileentity.research.table.printer";}

    @Override
    public boolean allowInteraction(Entity aEntity) {
        return super.allowInteraction(aEntity);
    }
    // Icons
    public final static IIconContainer
            sTextureSides     = new Textures.BlockIcons.CustomIcon("machines/research/table/printer/base"),
            sOverlayStop      = new Textures.BlockIcons.CustomIcon("machines/research/table/printer/front");


    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (!aShouldSideBeRendered[aSide]) return null;
        if(aSide==mFacing) return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides, mRGBa),BlockTextureDefault.get(sOverlayStop ));
        return BlockTextureDefault.get(sTextureSides, mRGBa);
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if(aIsServerSide && !slotHas(0) && getCurrentProject()!= null && getCurrentProject().isCompleted) {
            progress ++;
            if(progress < interval)return;
            setInventorySlotContents(0,  new ItemStack(ItemList.ResearchItem.getItem(), 1, getCurrentProject().iconItemMeta));
            progress = 0;
            return;
        }
        progress = 0;
    }

    //Inventory
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[1];}
    @Override public boolean canDrop(int aInventorySlot) {return T;}

    private static final int[] ACCESSIBLE_SLOTS = new int[] {0};

    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}

    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {return true;}

    @Override
    public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {return true;}


    @Override
    public NBTTagCompound getWailaNBT(TileEntity te, NBTTagCompound aNBT) {
        IWailaTile.super.getWailaNBT(te, aNBT);
        UT.NBT.setNumber(aNBT, "progress", progress);
        return aNBT;
    }

    @Override
    public List<String> getWailaBody(List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        IWailaTile.super.getWailaBody(currentTip, accessor, config);
        currentTip.add("Progress: " + accessor.getNBTData().getLong("progress")*100F/interval + "%");
        return currentTip;
    }
}
