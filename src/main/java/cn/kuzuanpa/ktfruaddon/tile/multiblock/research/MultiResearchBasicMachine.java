package cn.kuzuanpa.ktfruaddon.tile.multiblock.research;

import cn.kuzuanpa.ktfruaddon.api.tile.IResearchDatabase;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Objects;

public abstract class MultiResearchBasicMachine extends MultiTileEntityBasicMachine {
    public ItemStack lastItemStack=null;
    @Override
    public int checkRecipe(boolean aApplyRecipe, boolean aUseAutoIO) {
        int result =super.checkRecipe(false, aUseAutoIO);
        if(result == 0 ||mLastRecipe == null)return 0;
        if (!aApplyRecipe)return result;

        ItemStack singleTargetItem = mLastRecipe.getOutput(0).copy();
        singleTargetItem.stackSize = 1;
        if (Objects.equals(lastItemStack, singleTargetItem))return super.checkRecipe(true, aUseAutoIO);

        if (getDatabases().stream().anyMatch(database -> database.isItemUnlocked(singleTargetItem))){
            lastItemStack = singleTargetItem;
            return super.checkRecipe(true, aUseAutoIO);
        }
        lastItemStack = null;
        return 0;
    }

    public abstract List<IResearchDatabase> getDatabases();
}
