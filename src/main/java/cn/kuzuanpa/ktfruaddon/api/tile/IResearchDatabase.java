package cn.kuzuanpa.ktfruaddon.api.tile;

import net.minecraft.item.ItemStack;

public interface IResearchDatabase {
    public boolean isItemUnlocked(ItemStack singleItem);
}
