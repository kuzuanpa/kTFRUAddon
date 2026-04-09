package cn.kuzuanpa.ktfruaddon.tile.research;

import cn.kuzuanpa.ktfruaddon.api.code.SingleItemStack;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchItemManager;
import cn.kuzuanpa.ktfruaddon.api.tile.IResearchDatabase;
import cn.kuzuanpa.ktfruaddon.item.items.research.ItemResearchBase;
import gregapi.gui.ContainerClientDefault;
import gregapi.gui.ContainerCommonDefault;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

import static gregapi.data.CS.T;

public class ResearchDatabaseItem extends TileEntityBase09FacingSingle implements IResearchDatabase {
    public List<SingleItemStack> cachedItems = new ArrayList<>();
    @Override
    public boolean isItemUnlocked(SingleItemStack singleItem) {
        return cachedItems.contains(singleItem);
    }
    @Override public String getTileEntityName() {return "ktfru.multitileentity.research.database.item";}

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        updateUnlockedItemCache();
    }

    // Icons
    public final static IIconContainer
            sTextureSides     = new Textures.BlockIcons.CustomIcon("machines/research/table/item/base"),
            sOverlayStop      = new Textures.BlockIcons.CustomIcon("machines/research/table/item/front");


    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (!aShouldSideBeRendered[aSide]) return null;
        if(aSide==mFacing) return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides, mRGBa),BlockTextureDefault.get(sOverlayStop ));
        return BlockTextureDefault.get(sTextureSides, mRGBa);
    }
    protected void updateUnlockedItemCache(){
        cachedItems.clear();
        for (int i = 0; i < getSizeInventory(); i++) {
            if(!slotHas(i))continue;
            ItemStack stack = slot(i);
            if (!(stack.getItem() instanceof ItemResearchBase))continue;
            List<SingleItemStack> list = ResearchItemManager.getItems(((ItemResearchBase) stack.getItem()).getTreeId(), ((short) stack.getItemDamage()));
            if (list != null)cachedItems.addAll(list);
        }
    }
    @Override
    public void onTickResetChecks(long aTimer, boolean aIsServerSide) {
        if(mInventoryChanged)updateUnlockedItemCache();
        super.onTickResetChecks(aTimer, aIsServerSide);
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (isServerSide()) {
            openGUI(aPlayer, aSide);
            return true;
        }
        return false;
    }
    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {return new ContainerClientDefault(aPlayer.inventory, this, aGUIID);}
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {return new ContainerCommonDefault(aPlayer.inventory, this, aGUIID);}

    //Inventory
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[9];}
    @Override public boolean canDrop(int aInventorySlot) {return T;}

    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return UT.Code.getAscendingArray(9);}

    @Override
    public boolean isItemValidForSlot(int aSlot, ItemStack aStack) {
        return aStack.getItem() instanceof ItemResearchBase;
    }
}
