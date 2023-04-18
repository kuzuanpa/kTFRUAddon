
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
import codechicken.lib.vec.Vector3;
import cpw.mods.fml.common.FMLLog;
import gregapi.tileentity.base.TileEntityBase04MultiTileEntities;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.apache.logging.log4j.Level;

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
                FMLLog.log(Level.FATAL,"Out of range:"+aX+aY+aZ+aRegistryMeta);
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
    //todo: allow getCheckX&Z can be used in controller blocks that facing up and down
@SuppressWarnings("currently can't be used in controller blocks that facing up and down")
    public static int getCheckX(int Facing, int tX, int addX, int addZ) {
        int[] result = {0, 0, tX - addX, tX + addX, tX + addZ, tX - addZ, 0, 0};
        return result[Facing];
    }
    @SuppressWarnings("currently can't be used in controller blocks that facing up and down")

    public static int getCheckZ(int Facing, int tZ, int addX, int addZ) {
        int[] result = {0, 0, tZ + addZ, tZ - addZ, tZ + addX, tZ - addX, 0, 0};
        return result[Facing];
    }
    /**
     * @author kuzuanpa
     *
     * ABC about Real Reflection
     * Currently not be fully done.
     *   1: cannot be used in machines that main block facing up and down.
     *   2: Could anyone tell me is this have any negatives such as proformance loss or something?
     * Checking Multiblock structure is sometimes difficult, using this can help to create structures more directly and easy.
     *
     * You can also use Dummy Structure Map,please turn to exampleMachine class to get a look into that.
     *
     * getRealCoord(...) needs to input a coord that didn't depend on Facing. addX,Y,Z just the dummy coord.
     * For example ,assuming that C=Controller, First number is addX,Another is addZ
     * 01 11 21 31 41 51
     * C  10 20 30 40 50
     * C is facing down in dummy coord, this could keep every block exractly same whether Facing.
     * You should always give those num to getRealCoord(...), It will return a Real World Coord that could be used in checkAndSetTarget and so on.
     *
    **/
    private static void dontSpamMyIDE(){/*long JavaDoc can be annoying because it can fill whole screen when you hovered your mouse on it...*/};

    /**
     * Detailed help can be found in utils.java
     * @param Facing controllerBlock's facing,usually this.mFacing
     * @param oX X Coord of the Start point
     * @param oZ Z Coord of the Start point
     * @param addX X offset in Dummy Coord
     * @param addZ Z offset in Dummy Coord
     * @return an int[] contains X,Y,Z in real world.
     */
    public static BlockCoord getRealCoord(byte Facing, int oX, int oY, int oZ, int addX, int addY, int addZ) {
        int[] resultX = {0, 0, oX - addX, oX + addX, oX + addZ, oX - addZ, 0, 0};
        int[] resultZ = {0, 0, oZ + addZ, oZ - addZ, oZ + addX, oZ - addX, 0, 0};

        return new BlockCoord(resultX[Facing],oY +addY,resultZ[Facing]);
    }

    public static class GTTileEntity {
        public  int aRegistryMeta;
        public int aRegistryID;
        public int aDesign;
        public int aUsage;
        public GTTileEntity( int aRegistryID,int aRegistryMeta, int aDesign,int aUsage){
            this.aDesign=aDesign;
            this.aRegistryID=aRegistryID;
            this.aRegistryMeta=aRegistryMeta;
            this.aUsage=aUsage;
        }
    }
    public static ArrayList<BlockCoord> checkAndGetRoom(GTTileEntity[] availableTiles, ITileEntityMultiBlockController aController, boolean startFromTopOrBack,AxisAlignedBB checkRange){
        ArrayList<BlockCoord> checkingBlockCoords = new ArrayList<BlockCoord>() {};
        //Starting from TOP
        if (startFromTopOrBack) checkingBlockCoords.add(new BlockCoord(aController.getX(),aController.getY()+1,aController.getZ()));
        //Starting from Back
        if (!startFromTopOrBack) checkingBlockCoords.add(getRealCoord(((TileEntityBase09FacingSingle)aController).mFacing,aController.getX(),aController.getY(),aController.getZ(),0,0,1));
        final byte[] forX ={1,-1,0,0,0,0};
        final byte[] forY ={0,0,1,-1,0,0};
        final byte[] forZ ={0,0,0,0,1,-1};
        for (BlockCoord coord : checkingBlockCoords) {//Check blocks at every side
            for (int i = 0; i < 6; i++) {
                BlockCoord checkingCoord=new BlockCoord(coord.x+forX[i],coord.y+forY[i],coord.z+forZ[i]);
                if (Arrays.stream(availableTiles).noneMatch(availTile -> checkAndSetTarget(aController,checkingCoord.x,checkingCoord.y,checkingCoord.z,availTile.aRegistryMeta,availTile.aRegistryID,availTile.aDesign, availTile.aUsage))){
                    if (!checkRange.isVecInside(checkingCoord.toVector3Centered().toVec3D())) {
                        FMLLog.log(Level.INFO,"Err: Out of range:"+checkingCoord.x+checkingCoord.y+checkingCoord.z);
                        return null;
                    }
                    if (!checkingBlockCoords.contains(checkingCoord)){
                        FMLLog.log(Level.INFO,"will check block:"+checkingCoord.x+checkingCoord.y+checkingCoord.z);

                        checkingBlockCoords.add(checkingCoord);}
                }
            }
        }
        return checkingBlockCoords;
    }
}

