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
import net.minecraft.util.AxisAlignedBB;

import java.util.ArrayList;

public abstract class TileEntityBaseRoom extends TileEntityBase10MultiBlockMachine {
    private ArrayList<BlockCoord> roomSpace;
    private final static utils.GTTileEntity[] availableTiles={new utils.GTTileEntity(1,2,0)};
    private final static boolean startFromTopOrBack=false;
    private final static int[] checkRange2= {-1,-1,-1,1,1,1};
    private final AxisAlignedBB checkRange = AxisAlignedBB.getBoundingBox(this.getOffsetX(this.mFacing,checkRange2[1]),this.getOffsetY(mFacing,checkRange2[2]),this.getOffsetZ(this.mFacing,checkRange2[3]),this.getOffsetX(this.mFacing,checkRange2[4]),this.getOffsetY(mFacing,checkRange2[5]),this.getOffsetZ(this.mFacing,checkRange2[6]));
    public boolean checkStructure2(){
        roomSpace = utils.checkAndGetRoom(availableTiles,this,startFromTopOrBack,checkRange);
        return roomSpace != null;
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return roomSpace.contains(new BlockCoord(aX,aY,aZ));
    }
}
