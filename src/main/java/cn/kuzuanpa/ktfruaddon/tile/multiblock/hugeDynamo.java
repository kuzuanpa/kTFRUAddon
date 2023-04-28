/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.multiblock;

import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.LH;
import gregapi.tileentity.ITileEntityUnloadable;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase11MultiBlockConverter;
import gregapi.util.ST;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

import static gregapi.data.CS.*;

public class hugeDynamo extends TileEntityBase11MultiBlockConverter {
    public ITileEntityUnloadable mEmitter = null;
    public final short machineX = 7, machineY = 7, machineZ = 9;
    //This controls where is the start point to check structure,Default is the position of controller block
    public final short xMapOffset = -3, zMapOffset = 0;
    public static int[][][] blockIDMap = {{
            {18002, 18022, 18002,   0  , 18002, 18022, 18002},
            {18002, 18022, 18002, 18002, 18002, 18022, 18002},
            {18002, 18022, 18002, 18002, 18002, 18022, 18002},
            {18002, 18022, 18002, 18002, 18002, 18022, 18002},
            {18022, 18022, 18022, 18022, 18022, 18022, 18022},
            {18002, 18022, 18002, 18002, 18002, 18022, 18002},
            {18002, 18022, 18002, 18002, 18002, 18022, 18002},
            {18002, 18022, 18002, 18002, 18002, 18022, 18002},
            {18002, 18022, 18002, 18002, 18002, 18022, 18002}
    }, {
            {18002, 18022, 18299, 18299, 18299, 18022, 18002},
            {18299, 18040, 18040, 18040, 18040, 18040, 18299},
            {18002, 18040, 18040, 18040, 18040, 18040, 18002},
            {18002, 18040, 18040, 18040, 18040, 18040, 18002},
            {18022, 18040, 18040, 18040, 18040, 18040, 18022},
            {18002, 18040, 18040, 18040, 18040, 18040, 18002},
            {18002, 18040, 18040, 18040, 18040, 18040, 18002},
            {18299, 18040, 18040, 18040, 18040, 18040, 18299},
            {18002, 18022, 18299, 18299, 18299, 18022, 18002}
    }, {
            {18002, 18022, 18022, 18022, 18022, 18022, 18002},
            {18002, 18040, 18040, 18040, 18040, 18040, 18002},
            {18002, 18040, 18040, 18040, 18040, 18040, 18002},
            {18002, 18040, 18040, 18040, 18040, 18040, 18002},
            {18022, 18040, 18040, 18040, 18040, 18040, 18022},
            {18002, 18040, 18040, 18040, 18040, 18040, 18002},
            {18002, 18040, 18040, 18040, 18040, 18040, 18002},
            {18002, 18040, 18040, 18040, 18040, 18040, 18002},
            {18002, 18022, 18022, 18022, 18022, 18022, 18002}
    }, {
            {18002, 18022, 18022,  30100, 18022, 18022, 18002},
            {18002, 18040, 18040,  30100, 18040, 18040, 18002},
            {18002, 18040, 18040,  30100, 18040, 18040, 18002},
            {18002, 18040, 18040,  30100, 18040, 18040, 18002},
            {18022, 18040, 18040,  30100, 18040, 18040, 18022},
            {18002, 18040, 18040,  30100, 18040, 18040, 18002},
            {18002, 18040, 18040,  30100, 18040, 18040, 18002},
            {18002, 18040, 18040,  30100, 18040, 18040, 18002},
            {18002, 18022, 18022,  30100, 18022, 18022, 18002}
    }, {
            {18002, 18022, 18022, 18022, 18022, 18022, 18002},
            {18002, 18040, 18040, 18040, 18040, 18040, 18002},
            {18002, 18040, 18040, 18040, 18040, 18040, 18002},
            {18002, 18040, 18040, 18040, 18040, 18040, 18002},
            {18022, 18040, 18040, 18040, 18040, 18040, 18022},
            {18002, 18040, 18040, 18040, 18040, 18040, 18002},
            {18002, 18040, 18040, 18040, 18040, 18040, 18002},
            {18002, 18040, 18040, 18040, 18040, 18040, 18002},
            {18002, 18022, 18022, 18022, 18022, 18022, 18002}
    }, {
            {18002, 18022, 18299, 18299, 18299, 18022, 18002},
            {18299, 18040, 18040, 18040, 18040, 18040, 18299},
            {18002, 18040, 18040, 18040, 18040, 18040, 18002},
            {18002, 18040, 18040, 18040, 18040, 18040, 18002},
            {18022, 18040, 18040, 18040, 18040, 18040, 18022},
            {18002, 18040, 18040, 18040, 18040, 18040, 18002},
            {18002, 18040, 18040, 18040, 18040, 18040, 18002},
            {18299, 18040, 18040, 18040, 18040, 18040, 18299},
            {18002, 18022, 18299, 18299, 18299, 18022, 18002}
    }, {
            {18002, 18022, 18002, 18002, 18002, 18022, 18002},
            {18002, 18022, 18002, 18002, 18002, 18022, 18002},
            {18002, 18022, 18002, 18002, 18002, 18022, 18002},
            {18002, 18022, 18002, 18002, 18002, 18022, 18002},
            {18022, 18022, 18022, 18022, 18022, 18022, 18022},
            {18002, 18022, 18002, 18002, 18002, 18022, 18002},
            {18002, 18022, 18002, 18002, 18002, 18022, 18002},
            {18002, 18022, 18002, 18002, 18002, 18022, 18002},
            {18002, 18022, 18002, 18002, 18002, 18022, 18002},
    }
    };

    short k = ST.id(MultiTileEntityRegistry.getRegistry("ktfru.multitileentity").mBlock);
    short g = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);
    public short[][][] registryIDMap = {{
            {g, g, g, k, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g}
    }, {
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g}
    }, {
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g}
    }, {
            {g, g, g, k, g, g, g},
            {g, g, g, k, g, g, g},
            {g, g, g, k, g, g, g},
            {g, g, g, k, g, g, g},
            {g, g, g, k, g, g, g},
            {g, g, g, k, g, g, g},
            {g, g, g, k, g, g, g},
            {g, g, g, k, g, g, g},
            {g, g, g, k, g, g, g}
    }, {
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g}
    }, {
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g}
    }, {
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g},
            {g, g, g, g, g, g, g}
    }
    };

    public int getCheckX(int Facing, int tX, int addX, int addZ) {
        int[] result = {0, 0, tX - addX, tX + addX, tX + addZ, tX - addZ, 0, 0};
        return result[Facing];
    }

    public int getCheckZ(int Facing, int tZ, int addX, int addZ) {
        //Don't process facings up,down and invalid
        int[] result = {0, 0, tZ + addZ, tZ - addZ, tZ + addX, tZ - addX, 0, 0};
        return result[Facing];
    }
    public int getUsage(int blockID, short registryID) {
        if (blockID == 30100 && registryID == k) {
            return MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
        } else {
            return MultiTileEntityMultiBlockPart.NOTHING;
        }
    }


    @Override
    public boolean checkStructure2() {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (worldObj.blockExists(tX, tY, tZ)) {
            boolean tSuccess = T;
            if (getFacing() == (short) 2) {
                tX -= xMapOffset;
            } else if (getFacing() == (short) 3) {
                tX += xMapOffset;
            } else if (getFacing() == (short) 4) {
                tZ += xMapOffset;
            } else if (getFacing() == (short) 5) {
                tZ -= xMapOffset;
            } else {
                tSuccess = F;
            }
            int checkX, checkY, checkZ;
            for (checkY = 0; checkY < machineY && tSuccess; checkY++) {
                for (checkZ = 0; checkZ < machineZ && tSuccess; checkZ++) {
                    for (checkX = 0; checkX < machineX && tSuccess; checkX++) {
                        if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this,
                                getCheckX(mFacing, tX, checkX, checkZ), tY + checkY, getCheckZ(mFacing, tZ, checkX, checkZ),
                                blockIDMap[checkY][checkZ][checkX], registryIDMap[checkY][checkZ][checkX], 0,
                                getUsage(blockIDMap[checkY][checkZ][checkX], registryIDMap[checkY][checkZ][checkX]))) {
                            tSuccess = F;
                            //FMLLog.log(Level.FATAL, "failed,Detail info see the next line");
                        }
                        //FMLLog.log(Level.FATAL, "Checkpos:/Facing:" + mFacing + "/origin point:" + tX + "," + tY + ","+ tZ + "/Now checking:" + getCheckX(mFacing, tX, checkX, checkZ) + "," + (tY + checkY) + "," + getCheckZ(mFacing, tZ, checkX, checkZ) +"/ID:"+ blockIDMap[checkY][checkZ][checkX]+"/Usage:"+getUsage(blockIDMap[checkY][checkZ][checkX], registryIDMap[checkY][checkZ][checkX]));
                    }
                }
            }
            return tSuccess;
        }
        return mStructureOkay;
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
    }


    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return
                aX >= xCoord - (SIDE_X_NEG == mFacing ? 0 : SIDE_X_POS == mFacing ? 3 : 1) &&
                        aY >= yCoord - (SIDE_Y_NEG == mFacing ? 0 : SIDE_Y_POS == mFacing ? 3 : 1) &&
                        aZ >= zCoord - (SIDE_Z_NEG == mFacing ? 0 : SIDE_Z_POS == mFacing ? 3 : 1) &&
                        aX <= xCoord + (SIDE_X_POS == mFacing ? 0 : SIDE_X_NEG == mFacing ? 3 : 1) &&
                        aY <= yCoord + (SIDE_Y_POS == mFacing ? 0 : SIDE_Y_NEG == mFacing ? 3 : 1) &&
                        aZ <= zCoord + (SIDE_Z_POS == mFacing ? 0 : SIDE_Z_NEG == mFacing ? 3 : 1);
    }

    static {
        LH.add("gt.tooltip.multiblock.dynamo.1", "Two 3x3s with 2m inbetween made of the Block you crafted this of");
        LH.add("gt.tooltip.multiblock.dynamo.2", "a 3x3x2 of 18 Large Copper Coils inbetween");
        LH.add("gt.tooltip.multiblock.dynamo.3", "Main centered on one of the 3x3s facing outwards");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.dynamo.1"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.dynamo.2"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.dynamo.3"));
        super.addToolTips(aList, aStack, aF3_H);
    }


    @Override
    public TileEntity getEmittingTileEntity() {
        if (mEmitter == null || mEmitter.isDead()) {
            mEmitter = null;
            TileEntity tTileEntity = getTileEntityAtSideAndDistance(mFacing, 0);
            if (tTileEntity instanceof ITileEntityUnloadable) mEmitter = (ITileEntityUnloadable) tTileEntity;
        }
        return mEmitter == null ? this : (TileEntity) mEmitter;
    }

    @Override
    public byte getEmittingSide() {
        return mFacing;
    }

    @Override
    public boolean isInput(byte aSide) {
        return aSide == OPOS[mFacing];
    }

    @Override
    public boolean isOutput(byte aSide) {
        return aSide == mFacing;
    }

    @Override
    public byte getDefaultSide() {
        return SIDE_FRONT;
    }

    @Override
    public boolean[] getValidSides() {
        return SIDES_VALID;
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.dynamo";
    }
}