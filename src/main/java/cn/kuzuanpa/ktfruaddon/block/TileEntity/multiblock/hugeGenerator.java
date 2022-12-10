package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock;

import cpw.mods.fml.common.FMLLog;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.CS;
import gregapi.data.LH;
import gregapi.tileentity.ITileEntityUnloadable;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.machines.ITileEntitySwitchableOnOff;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
import gregapi.tileentity.multiblocks.TileEntityBase11MultiBlockConverter;
import gregapi.util.ST;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;
import org.apache.logging.log4j.Level;

import java.util.List;

import static gregapi.data.CS.*;

public class hugeGenerator extends TileEntityBase11MultiBlockConverter  {
    public ITileEntityUnloadable mEmitter = null;
    public final short machineX = 7, machineY = 7, machineZ = 9;
    //This controls where is the start point to check structure,Default is the position of controller block

    public final short xMapOffset = -3, zMapOffset = 0;
    public static int[][][] blockIDMap = {{
            {18002, 18022, 18002, 0, 18002, 18022, 18002},
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
            {18002, 18022, 18022,  30101, 18022, 18022, 18002},
            {18002, 18040, 18040,  30101, 18040, 18040, 18002},
            {18002, 18040, 18040,  30101, 18040, 18040, 18002},
            {18002, 18040, 18040,  30101, 18040, 18040, 18002},
            {18022, 18040, 18040,  30101, 18040, 18040, 18022},
            {18002, 18040, 18040,  30101, 18040, 18040, 18002},
            {18002, 18040, 18040,  30101, 18040, 18040, 18002},
            {18002, 18040, 18040,  30101, 18040, 18040, 18002},
            {18002, 18022, 18022,  30101, 18022, 18022, 18002}
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
            {18002, 18022, 18002, 18002, 18002, 18022, 18002}
    }
    };

    short k = getMultiTileEntityRegistryID();
    short g = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);
    public int[][][] registryIDMap = {{
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

    @Override
    public boolean checkStructure2() {
        int tX = getOffsetXN(mFacing), tY = yCoord, tZ = getOffsetZN(mFacing);
        if (worldObj.blockExists(tX, tY, tZ)) {
            boolean tSuccess = T;
            if (getFacing() == (short) 2) {
                tZ -= 1 - zMapOffset;
                tX += xMapOffset;
                int checkX, checkY, checkZ;
                for (checkY = 0; checkY < machineY; checkY++) {
                    for (checkZ = 0; checkZ < machineZ; checkZ++) {
                        for (checkX = 0; checkX < machineX; checkX++) {
                            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + checkX, tY + checkY, tZ + checkZ, blockIDMap[checkY][checkZ][checkX], registryIDMap[checkY][checkZ][checkX], 0, MultiTileEntityMultiBlockPart.NOTHING)) {
                                tSuccess = F;
                                FMLLog.log(Level.FATAL, "failed");
                            }
                            FMLLog.log(Level.FATAL, "Checkpo" + mFacing + "/" + tX + "/" + tY + "/" + tZ + "/" + checkX + "/" + checkY + "/" + checkZ + "/");

                        }
                    }
                }
            } else if (getFacing() == (short) 3) {
                tZ += 1 - zMapOffset;
                tX -= xMapOffset;
                int checkX, checkY, checkZ;
                for (checkY = 0; checkY < machineY; checkY++) {
                    for (checkZ = 0; checkZ < machineZ; checkZ++) {
                        for (checkX = 0; checkX < machineX; checkX++) {
                            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX - checkX, tY + checkY, tZ - checkZ, blockIDMap[checkY][checkZ][checkX], registryIDMap[checkY][checkZ][checkX], 0, MultiTileEntityMultiBlockPart.NOTHING)) {
                                tSuccess = F;
                                FMLLog.log(Level.FATAL, "failed");
                            }
                            FMLLog.log(Level.FATAL, "Checkpo" + mFacing + "/" + tX + "/" + tY + "/" + tZ + "/" + checkX + "/" + checkY + "/" + checkZ + "/" + F);

                        }
                    }
                }
            } else if (getFacing() == (short) 4) {
                tX -= 1 - zMapOffset;
                tZ -= xMapOffset;
                int checkX, checkY, checkZ;
                for (checkY = 0; checkY < machineY; checkY++) {
                    for (checkX = 0; checkX < machineX; checkX++) {
                        for (checkZ = machineZ - 1; checkZ >= 0; checkZ--) {
                            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + checkX, tY + checkY, tZ - checkZ, blockIDMap[checkY][checkX][checkZ], registryIDMap[checkY][checkX][checkZ], 0, MultiTileEntityMultiBlockPart.NOTHING)) {
                                tSuccess = F;
                                FMLLog.log(Level.FATAL, "failed");
                            }
                            FMLLog.log(Level.FATAL, "Checkpo" + mFacing + "/" + tX + "/" + tY + "/" + tZ + "/" + checkX + "/" + checkY + "/" + checkZ + "/" + F);

                        }
                    }
                }
            } else if (getFacing() == (short) 5) {
                tX += 1 - zMapOffset;
                tZ += xMapOffset;
                int checkX, checkY, checkZ;
                for (checkY = 0; checkY < machineY; checkY++) {
                    for (checkX = 0; checkX < machineX; checkX++) {
                        for (checkZ = 0; checkZ < machineZ; checkZ++) {
                            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX - checkX, tY + checkY, tZ + checkZ, blockIDMap[checkY][checkX][checkZ], registryIDMap[checkY][checkX][checkZ], 0, MultiTileEntityMultiBlockPart.NOTHING)) {
                                tSuccess = F;
                                FMLLog.log(Level.FATAL, "failed");
                            }
                            FMLLog.log(Level.FATAL, "Checkpo" + mFacing + "/" + tX + "/" + tY + "/" + tZ + "/" + checkX + "/" + checkY + "/" + checkZ + "/" + F);
                        }
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
                aX >= xCoord-(SIDE_X_NEG==mFacing?0:SIDE_X_POS==mFacing?3:1) &&
                        aY >= yCoord-(SIDE_Y_NEG==mFacing?0:SIDE_Y_POS==mFacing?3:1) &&
                        aZ >= zCoord-(SIDE_Z_NEG==mFacing?0:SIDE_Z_POS==mFacing?3:1) &&
                        aX <= xCoord+(SIDE_X_POS==mFacing?0:SIDE_X_NEG==mFacing?3:1) &&
                        aY <= yCoord+(SIDE_Y_POS==mFacing?0:SIDE_Y_NEG==mFacing?3:1) &&
                        aZ <= zCoord+(SIDE_Z_POS==mFacing?0:SIDE_Z_NEG==mFacing?3:1);
    }

    static {
        LH.add("gt.tooltip.multiblock.dynamo.1", "Two 3x3s with 2m inbetween made of the Block you crafted this of");
        LH.add("gt.tooltip.multiblock.dynamo.2", "a 3x3x2 of 18 Large Copper Coils inbetween");
        LH.add("gt.tooltip.multiblock.dynamo.3", "Main centered on one of the 3x3s facing outwards");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN     + LH.get(LH.STRUCTURE) + ":");
        aList.add(LH.Chat.WHITE    + LH.get("gt.tooltip.multiblock.dynamo.1"));
        aList.add(LH.Chat.WHITE    + LH.get("gt.tooltip.multiblock.dynamo.2"));
        aList.add(LH.Chat.WHITE    + LH.get("gt.tooltip.multiblock.dynamo.3"));
        super.addToolTips(aList, aStack, aF3_H);
    }


    @Override public TileEntity getEmittingTileEntity() {if (mEmitter == null || mEmitter.isDead()) {mEmitter = null; TileEntity tTileEntity = getTileEntityAtSideAndDistance(OPOS[mFacing], 3); if (tTileEntity instanceof ITileEntityUnloadable) mEmitter = (ITileEntityUnloadable)tTileEntity;} return mEmitter == null ? this : (TileEntity)mEmitter;}
    @Override public byte getEmittingSide() {return OPOS[mFacing];}
    @Override public boolean isInput (byte aSide) {return aSide == mFacing;}
    @Override public boolean isOutput(byte aSide) {return aSide == OPOS[mFacing];}

    @Override public byte getDefaultSide() {return SIDE_FRONT;}
    @Override public boolean[] getValidSides() {return SIDES_VALID;}

    @Override public String getTileEntityName() {return "ktfru.multitileentity.multiblock.dynamo";}
}