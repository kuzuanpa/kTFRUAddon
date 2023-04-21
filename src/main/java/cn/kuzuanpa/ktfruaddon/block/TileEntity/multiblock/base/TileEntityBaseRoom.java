/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.base;

import cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.util.utils;
import cn.kuzuanpa.ktfruaddon.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.code.codeUtil;
import codechicken.lib.vec.BlockCoord;
import cpw.mods.fml.common.FMLLog;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public abstract class TileEntityBaseRoom extends TileEntityBase10MultiBlockMachine {
    public TileEntityBaseRoom(){
    }
    private static ArrayList<BlockCoord> insideSpace;
    private static ArrayList<BlockCoord> roomSpace;
    private static ConcurrentHashMap<Integer,BlockCoord> checkingBlockCoords = new ConcurrentHashMap<Integer,BlockCoord>(){};
    private static ArrayList<BlockCoord> walls = new ArrayList<BlockCoord>();

    /**
     * @param availableTiles all available TileEntity in GregTech
     * @param aController The ControllerBlock
     * @param startFromTopOrBack Start Point,true=Top,false=Back
     * @param checkRange Max range when checking
     * @param shouldCornerBeSealed Should every corner be filled,or just the blocks next to RoomSpace
     */
    public static void checkAndGetRoom(utils.GTTileEntity[] availableTiles, ITileEntityMultiBlockController aController, boolean startFromTopOrBack, BoundingBox checkRange, boolean shouldCornerBeSealed){
        checkingBlockCoords  = new ConcurrentHashMap<Integer,BlockCoord>(){};
        roomSpace = new ArrayList<BlockCoord>();
        walls = new ArrayList<BlockCoord>();
        //Starting from TOP
        if (startFromTopOrBack) checkingBlockCoords.put(0,new BlockCoord(aController.getX(),aController.getY()+1,aController.getZ()));
        //Starting from Back
        if (!startFromTopOrBack) checkingBlockCoords.put(0, codeUtil.MCCoord2CCCoord(utils.getRealCoord(((TileEntityBase09FacingSingle)aController).mFacing,aController.getX(),aController.getY(),aController.getZ(),0,0,1)));
        byte[] fX = {1, -1, 0, 0, 0, 0};
        byte[] fY = {0, 0, 1, -1, 0, 0};
        byte[] fZ = {0, 0, 0, 0, 1, -1};
        byte fi =6;
        if (shouldCornerBeSealed){
            fX = new byte[]{-1, 0, 1,-1, 0, 1,-1, 0, 1,-1, 0, 1,-1, 1,-1, 0, 1,-1, 0, 1,-1, 0, 1,-1, 0, 1};
            fY = new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1};
            fZ = new byte[]{-1,-1,-1, 0, 0, 0, 1, 1, 1,-1,-1,-1, 0, 0, 1, 1, 1,-1,-1,-1, 0, 0, 0, 1, 1, 1};
            fi=26;
        }
        final byte[] forX = fX;
        final byte[] forY = fY;
        final byte[] forZ = fZ;
        final byte fori =fi;
        checkingBlockCoords.values().forEach(coord ->{
            //Check blocks at every side
            for (int i = 0; i < fori; i++) {
                BlockCoord checkingCoord=new BlockCoord(coord.x+forX[i],coord.y+forY[i],coord.z+forZ[i]);
                if (Arrays.stream(availableTiles).noneMatch(availTile -> utils.checkAndSetTarget(aController,checkingCoord.x,checkingCoord.y,checkingCoord.z,availTile.aRegistryMeta,availTile.aRegistryID,availTile.aDesign, availTile.aUsage))){
                    if (!checkRange.isCoordInBox(checkingCoord)) {
                        FMLLog.log(Level.INFO,"Err: Out of range:"+checkingCoord.x+checkingCoord.y+checkingCoord.z);
                        checkingBlockCoords.clear();
                        walls.clear();
                        roomSpace.clear();
                        break;
                    }
                    if (!checkingBlockCoords.contains(checkingCoord)){
                        FMLLog.log(Level.INFO,"will check block:"+checkingCoord.x+checkingCoord.y+checkingCoord.z);
                      checkingBlockCoords.put(checkingBlockCoords.size(),checkingCoord);

                    }
                }else walls.add(checkingCoord);
            }
        });
        checkingBlockCoords.forEachValue(0, roomSpace::add);
        checkingBlockCoords.clear();
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        ChunkCoordinates test = utils.getRealCoord(this.mFacing,this.xCoord,this.yCoord,this.zCoord,1,0,1);
        aPlayer.worldObj.setBlock(test.posX,test.posY,test.posZ,Block.getBlockById(6));
        return true;
    }

    public abstract utils.GTTileEntity[] getAvailableTiles();
    public final static boolean startFromTopOrBack=false;
    public final static boolean shouldCornerBeSealed=true;

    public abstract int[] getCheckRange2();
    public boolean checkStructure2(){
        final int[] checkRange2= getCheckRange2();
        final BlockCoord StartPoi= codeUtil.MCCoord2CCCoord(utils.getRealCoord(this.mFacing,this.xCoord,this.yCoord,this.zCoord,checkRange2[0],checkRange2[1],checkRange2[2]));
        final BlockCoord EndPoi= codeUtil.MCCoord2CCCoord(utils.getRealCoord(this.mFacing,this.xCoord,this.yCoord,this.zCoord,checkRange2[3],checkRange2[4],checkRange2[5]));
        BoundingBox checkRange=new BoundingBox(StartPoi,EndPoi);
        checkAndGetRoom(getAvailableTiles(),this,startFromTopOrBack,checkRange,shouldCornerBeSealed);
        return !walls.isEmpty();
    }
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.room.base";
    }
    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return walls!=null&&roomSpace!=null && (roomSpace.contains(new BlockCoord(aX, aY, aZ))||walls.contains(new BlockCoord(aX,aY,aZ)));
    }
}
