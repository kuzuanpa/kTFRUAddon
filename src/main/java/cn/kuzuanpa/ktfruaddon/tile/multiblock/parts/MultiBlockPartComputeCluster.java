/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */



package cn.kuzuanpa.ktfruaddon.tile.multiblock.parts;

import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

import static gregapi.data.CS.*;

public class MultiBlockPartComputeCluster extends ComputePartBase {
    public MultiBlockPartComputeCluster(){
        super((byte) 4);
    }

    @Override
    public ComputePower getType() {
        return ComputePower.Normal;
    }
    public byte getSlotClicked(byte aSide, float aHitX, float aHitY, float aHitZ){
        return (byte)(aHitY >0.75?0:aHitY>0.5?1:aHitY>0.25?2:3);
    }

    public static IIconContainer
            sTextureCommon= new Textures.BlockIcons.CustomIcon("machines/multiblockparts/computecluster/background"),
            sTextureSides = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/computecluster/normal/sides"),
            sTextureFront = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/computecluster/normal/front"),
            sTextureNode  = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/computecluster/normal/nodes"),
            sRunningSide = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/computecluster/running/sides"),
            sRunningFront = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/computecluster/running/front"),
            sRunningNode  = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/computecluster/running/nodes"),
            sActiveFront = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/computecluster/active/front"),
            sActiveNode  = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/computecluster/active/nodes");
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if(isActive()) switch(aRenderPass) {
            case 0: return !aShouldSideBeRendered[aSide]?null: aSide==SIDE_TOP||aSide==SIDE_BOTTOM?BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa)): aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa),BlockTextureDefault.get(sTextureFront),BlockTextureDefault.get(sActiveFront,true)):BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa),BlockTextureDefault.get(sTextureSides),BlockTextureDefault.get(sRunningSide,true));
            case 1: return !aShouldSideBeRendered[aSide]?null: mDisplaySlot[3] ==1&&aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureNode),BlockTextureDefault.get(sActiveNode,true)):null;
            case 2: return !aShouldSideBeRendered[aSide]?null: mDisplaySlot[2] ==1&&aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureNode),BlockTextureDefault.get(sActiveNode,true)):null;
            case 3: return !aShouldSideBeRendered[aSide]?null: mDisplaySlot[1] ==1&&aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureNode),BlockTextureDefault.get(sActiveNode,true)):null;
            case 4: return !aShouldSideBeRendered[aSide]?null: mDisplaySlot[0] ==1&&aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureNode),BlockTextureDefault.get(sActiveNode,true)):null;
        }/* Unused
        if(isRunning) switch(aRenderPass) {
            case 0: return !aShouldSideBeRendered[aSide]?null: aSide==SIDE_TOP||aSide==SIDE_BOTTOM?BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa)): aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa),BlockTextureDefault.get(sTextureFront),BlockTextureDefault.get(sRunningFront,true)):BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa),BlockTextureDefault.get(sTextureSides),BlockTextureDefault.get(sRunningSide,true));
            case 1: return !aShouldSideBeRendered[aSide]?null: mDisplaySlot[3] ==1&&aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureNode),BlockTextureDefault.get(sRunningNode,true)):null;
            case 2: return !aShouldSideBeRendered[aSide]?null: mDisplaySlot[2] ==1&&aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureNode),BlockTextureDefault.get(sRunningNode,true)):null;
            case 3: return !aShouldSideBeRendered[aSide]?null: mDisplaySlot[1] ==1&&aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureNode),BlockTextureDefault.get(sRunningNode,true)):null;
            case 4: return !aShouldSideBeRendered[aSide]?null: mDisplaySlot[0] ==1&&aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureNode),BlockTextureDefault.get(sRunningNode,true)):null;
        }*/
        switch(aRenderPass) {
            case 0: return !aShouldSideBeRendered[aSide]?null: aSide==SIDE_TOP||aSide==SIDE_BOTTOM?BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa)): aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa),BlockTextureDefault.get(sTextureFront)):BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa),BlockTextureDefault.get(sTextureSides));
            case 1: return !aShouldSideBeRendered[aSide]?null: mDisplaySlot[3] ==1&&aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureNode)):null;
            case 2: return !aShouldSideBeRendered[aSide]?null: mDisplaySlot[2] ==1&&aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureNode)):null;
            case 3: return !aShouldSideBeRendered[aSide]?null: mDisplaySlot[1] ==1&&aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureNode)):null;
            case 4: return !aShouldSideBeRendered[aSide]?null: mDisplaySlot[0] ==1&&aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureNode)):null;
        }
        return null;
    }
    @Override
    public int getRenderPasses2(Block aBlock, boolean[] aShouldSideBeRendered) {
        return 5;
    }
    @Override
    public void addCollisionBoxesToList2(AxisAlignedBB aAABB, List<AxisAlignedBB> aList, Entity aEntity) {
       box(aAABB,aList, PX_P[ 0], PX_P[ 0], PX_P[ 0], PX_P[16], PX_P[ 16], PX_P[ 16]);
    }
    @Override
    public boolean setBlockBounds2(Block aBlock, int aRenderPass, boolean[] aShouldSideBeRendered) {
        switch(aRenderPass) {
            case  0: return box(aBlock, PX_P[ 0], PX_P[ 0], PX_P[ 0], PX_P[16], PX_P[ 16], PX_P[ 16]);
            case  1: return box(aBlock, PX_P[ 0]-0.0001F, PX_P[ 2], PX_P[ 0]-0.0001F, PX_P[16]+0.0001F, PX_P[ 4], PX_P[16]+0.0001F);
            case  2: return box(aBlock, PX_P[ 0]-0.0001F, PX_P[ 5], PX_P[ 0]-0.0001F, PX_P[16]+0.0001F, PX_P[ 7], PX_P[16]+0.0001F);
            case  3: return box(aBlock, PX_P[ 0]-0.0001F, PX_P[ 9], PX_P[ 0]-0.0001F, PX_P[16]+0.0001F, PX_P[11], PX_P[16]+0.0001F);
            case  4: return box(aBlock, PX_P[ 0]-0.0001F, PX_P[12], PX_P[ 0]-0.0001F, PX_P[16]+0.0001F, PX_P[14], PX_P[16]+0.0001F);
        }
        return F;
    }
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.computenode.common";
    }

}
