package cn.kuzuanpa.ktfruaddon.tile.research;

import cn.kuzuanpa.ktfruaddon.api.research.ResearchProject;
import cn.kuzuanpa.ktfruaddon.api.tile.IResearchDatabase;
import gregapi.render.ITexture;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ResearchDatabaseOnline extends ResearchTableBase implements IResearchDatabase {

    public boolean isItemUnlocked(ItemStack singleItem) {
        if (getMonitor() == null)return false;
        for (ResearchProject project : getMonitor().theTree.allResearch.values()){
            if (!project.isUnlocked || !project.unlockItems.contains(singleItem))continue;
            return true;
        }
        return false;
    }

    @Override
    public ITexture getTexture2(Block block, int i, byte b, boolean[] booleans) {
        return null;
    }

    @Override public String getTileEntityName() {return "ktfru.multitileentity.research.database.online";}

}
