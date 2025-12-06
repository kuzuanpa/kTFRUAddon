package cn.kuzuanpa.ktfruaddon.tile.research;

import cn.kuzuanpa.ktfruaddon.api.code.SingleItemStack;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchProject;
import cn.kuzuanpa.ktfruaddon.api.tile.IResearchDatabase;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import net.minecraft.block.Block;

public class ResearchDatabaseOnline extends ResearchTableBase implements IResearchDatabase {

    public boolean isItemUnlocked(SingleItemStack singleItem) {
        if (getMonitor() == null)return false;
        for (ResearchProject project : getMonitor().theTree.allResearch.values()){
            if (!project.isUnlocked || !project.unlockItems.contains(singleItem))continue;
            return true;
        }
        return false;
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

    @Override public String getTileEntityName() {return "ktfru.multitileentity.research.database.online";}

}
