
/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.util;

import cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.specialPart.MultiBlockPartComputeCluster;
import cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.specialPart.MultiTileEntityMultiBlockPartEnergyConsumer;
import codechicken.lib.vec.BlockCoord;
import gregapi.tileentity.base.TileEntityBase04MultiTileEntities;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import java.util.ArrayList;
import java.util.Arrays;

public class utils {
    public utils() {
    }

    public static boolean checkAndSetTarget(ITileEntityMultiBlockController aController, int aX, int aY, int aZ, int aRegistryMeta, int aRegistryID, int aDesign, int aMode) {
        TileEntity tTileEntity = aController.getTileEntity(aX, aY, aZ);
        if (tTileEntity == aController) {
            return true;
        } else if (tTileEntity instanceof MultiTileEntityMultiBlockPart && ((MultiTileEntityMultiBlockPart) tTileEntity).getMultiTileEntityID() == aRegistryMeta && ((MultiTileEntityMultiBlockPart) tTileEntity).getMultiTileEntityRegistryID() == aRegistryID) {
            ITileEntityMultiBlockController tTarget = ((MultiTileEntityMultiBlockPart) tTileEntity).getTarget(false);
            if (tTarget != aController && tTarget != null) {
                return false;
            } else {
                ((MultiTileEntityMultiBlockPart) tTileEntity).setTarget(aController, aDesign, aMode);
                return true;
            }
        } else if (tTileEntity instanceof MultiBlockPartComputeCluster && ((MultiBlockPartComputeCluster) tTileEntity).getMultiTileEntityID() == aRegistryMeta && ((MultiBlockPartComputeCluster) tTileEntity).getMultiTileEntityRegistryID() == aRegistryID) {
            ITileEntityMultiBlockController tTarget = ((MultiBlockPartComputeCluster) tTileEntity).getTarget(false);
            if (tTarget != aController && tTarget != null) {
                return false;
            } else {
                ((MultiBlockPartComputeCluster) tTileEntity).setTarget(aController, aDesign, aMode);
                return true;
            }
        } else {
            return false;
        }
    }

    public static boolean checkAndSetTargetEnergyConsumerPermitted(ITileEntityMultiBlockController aController, int aX, int aY, int aZ, int aRegistryMeta, int aRegistryID, int aDesign, int aMode) {
        TileEntity tTileEntity = aController.getTileEntity(aX, aY, aZ);
        if (tTileEntity == null) return false;
        if (tTileEntity == aController) return true;
        else if (tTileEntity instanceof MultiTileEntityMultiBlockPartEnergyConsumer && ((MultiTileEntityMultiBlockPartEnergyConsumer) tTileEntity).getMultiTileEntityID() == aRegistryMeta && ((MultiTileEntityMultiBlockPartEnergyConsumer) tTileEntity).getMultiTileEntityRegistryID() == aRegistryID) {
            ITileEntityMultiBlockController tTarget = ((MultiTileEntityMultiBlockPartEnergyConsumer) tTileEntity).getTarget(false);
            if (tTarget != aController && tTarget != null) return false;
            else {
                ((MultiTileEntityMultiBlockPartEnergyConsumer) tTileEntity).setTarget(aController, aDesign, aMode);
                return true;
            }
        } else return false;
    }

    public static String getTargetTileEntityName(TileEntity tile) {
        if (tile==null||tile.isInvalid())return "null";
        try {
            return ((TileEntityBase04MultiTileEntities)tile).getTileEntityName();
        }catch (ClassCastException e){
            if (tile.getClass().getName().contains("net.minecraft.tileentity")) return tile.getClass().getName().replace("net.minecraft.tileentity","minecraft");
            return tile.getClass().getName();
        }
    }
    public static class GTTileEntity {
        public  int aRegistryMeta;
        public int aRegistryID;
        public int aDesign;
        public GTTileEntity(int aRegistryMeta, int aRegistryID, int aDesign){
            this.aDesign=aDesign;
            this.aRegistryID=aRegistryID;
            this.aRegistryMeta=aRegistryMeta;
        }
    }
    public static ArrayList<BlockCoord> checkAndGetRoom(GTTileEntity[] availableTiles, ITileEntityMultiBlockController aController, boolean startFromTopOrBack,AxisAlignedBB checkRange){
        ArrayList<BlockCoord> checkingBlockCoords = new ArrayList<BlockCoord>() {};
        //Starting from TOP
        if (startFromTopOrBack) checkingBlockCoords.add(new BlockCoord(aController.getX(),aController.getY()+1,aController.getZ()));
        //Starting from Back
        if (!startFromTopOrBack) checkingBlockCoords.add(new BlockCoord(aController.getOffsetX(((TileEntityBase10MultiBlockBase)aController).mFacing,1),aController.getY(),aController.getZ()));
        final byte[] forX ={1,-1,0,0,0,0};
        final byte[] forY ={0,0,1,-1,0,0};
        final byte[] forZ ={0,0,0,0,1,-1};
        for (BlockCoord coord : checkingBlockCoords) {//Check blocks at every side
            for (int i = 0; i < 6; i++) {
                BlockCoord checkingCoord=new BlockCoord(coord.x+forX[i],coord.y+forY[i],coord.z+forZ[i]);
                if (Arrays.stream(availableTiles).noneMatch(availTile -> checkAndSetTarget(aController,checkingCoord.x,checkingCoord.y,checkingCoord.z,availTile.aRegistryMeta,availTile.aRegistryID,availTile.aDesign,0))){
                    if (!checkRange.isVecInside(checkingCoord.toVector3Centered().toVec3D())) return null;
                    if (!checkingBlockCoords.contains(checkingCoord)) checkingBlockCoords.add(checkingCoord);
                }
            }
        }
        return checkingBlockCoords;
    }
}

