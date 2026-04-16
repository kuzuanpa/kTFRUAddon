package cn.kuzuanpa.ktfruaddon.api.tile;

import cn.kuzuanpa.ktfruaddon.api.code.ItemType;

public interface IResearchDatabase {
    public boolean isItemUnlocked(ItemType stack);
}
