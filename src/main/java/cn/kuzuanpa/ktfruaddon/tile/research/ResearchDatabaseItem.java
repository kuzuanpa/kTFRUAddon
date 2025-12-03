package cn.kuzuanpa.ktfruaddon.tile.research;

import cn.kuzuanpa.ktfruaddon.api.research.ResearchItemManager;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchProject;
import cn.kuzuanpa.ktfruaddon.api.tile.IResearchDatabase;
import cn.kuzuanpa.ktfruaddon.item.items.research.itemResearch;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static gregapi.data.CS.T;

public class ResearchDatabaseItem extends TileEntityBase09FacingSingle implements IResearchDatabase {
    public List<ItemStack> cachedItems = new ArrayList<>();
    @Override
    public boolean isItemUnlocked(ItemStack singleItem) {
        return cachedItems.contains(singleItem);
    }
    @Override public String getTileEntityName() {return "ktfru.multitileentity.research.database.item";}

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        updateUnlockedItemCache();
    }

    @Override
    public ITexture getTexture2(Block block, int i, byte b, boolean[] booleans) {
        return null;
    }

    protected void updateUnlockedItemCache(){
        cachedItems.clear();
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack stack = slot(i);
            if (!(stack.getItem() instanceof itemResearch))continue;
            List<ItemStack> list = ResearchItemManager.getItems(((itemResearch) stack.getItem()).getTreeId(), ((short) stack.getItemDamage()));
            if (list != null)cachedItems.addAll(list);
        }
    }
    @Override
    public void onTickResetChecks(long aTimer, boolean aIsServerSide) {
        updateUnlockedItemCache();
        super.onTickResetChecks(aTimer, aIsServerSide);
    }

    //Inventory
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[9];}
    @Override public boolean canDrop(int aInventorySlot) {return T;}

    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return UT.Code.getAscendingArray(9);}

    @Override
    public boolean isItemValidForSlot(int aSlot, ItemStack aStack) {
        return aStack.getItem() instanceof itemResearch;
    }
}
