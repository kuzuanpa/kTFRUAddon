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
import codechicken.lib.vec.BlockCoord;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

import java.util.ArrayList;

public abstract class TileEntityBaseRoom extends TileEntityBase10MultiBlockMachine {
    public TileEntityBaseRoom(){
    }
    private ArrayList<BlockCoord> roomSpace;
    private final utils.GTTileEntity[] availableTiles=getAvailableTiles();
    public abstract utils.GTTileEntity[] getAvailableTiles();
    private final static boolean startFromTopOrBack=false;
    public abstract int[] getCheckRange2();
    public boolean checkStructure2(){
        final int[] checkRange2= getCheckRange2();
        final BlockCoord StartPoi=utils.getRealCoord(this.mFacing,this.xCoord,this.yCoord,this.zCoord,checkRange2[0],checkRange2[1],checkRange2[2]);
        final BlockCoord EndPoi=utils.getRealCoord(this.mFacing,this.xCoord,this.yCoord,this.zCoord,checkRange2[3],checkRange2[4],checkRange2[5]);
        AxisAlignedBB checkRange= AxisAlignedBB.getBoundingBox(StartPoi.x,StartPoi.y,StartPoi.z,EndPoi.x,EndPoi.y,EndPoi.z);
        roomSpace = utils.checkAndGetRoom(availableTiles,this,startFromTopOrBack,checkRange);
        worldObj.setBlock(StartPoi.x,StartPoi.y,StartPoi.z, Block.getBlockById(0));
        worldObj.setBlock(EndPoi.x,EndPoi.y,EndPoi.z, Block.getBlockById(1));

        return roomSpace != null;
    }
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.room.base";
    }
    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return roomSpace.contains(new BlockCoord(aX,aY,aZ));
    }
}
