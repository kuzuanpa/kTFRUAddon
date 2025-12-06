package cn.kuzuanpa.ktfruaddon.api.tile;

import cn.kuzuanpa.ktfruaddon.api.code.SingleItemStack;

public interface IResearchDatabase {
    public boolean isItemUnlocked(SingleItemStack stack);
}
