package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock;

import cpw.mods.fml.common.FMLLog;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.LH;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
import gregapi.util.ST;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;
import org.apache.logging.log4j.Level;

import java.util.List;

import static gregapi.data.CS.*;

public class exampleMachineComplex extends TileEntityBase10MultiBlockMachine {
    public final short machineX = 5, machineY = 1, machineZ = 5;
   //This controls where is the start point to check structure,Default is the position of controller block
    public final short xMapOffset = -2, zMapOffset = 0;
    //Map direction:
    //                 |
    //                 |
    //            ( tX ,tZ-1)
    //(tX-1, tZ );( tX , tZ );(tX+1, tZ )---->
    //            ( tX ,tZ+1)             axisX
    //                 | axisZ
    //                 v
    //In default (didn't modify offset),main block is on tX,tZ.For example:
    //{main,part},
    //{part,part}
    public static int[][][] blockIDMap = {{
            {18002, 18006,   0  , 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
    }};
    short k = getMultiTileEntityRegistryID();
    short g = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);
    public int[][][] registryIDMap = {{
            {g, g, k, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
    }};
    //T = do ignore block ,F = normally check
    public static boolean[][][] ignoreMap = {{
            {F, F, T, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
    }};
    @Override
    public boolean checkStructure2() {
        int tX = getOffsetXN(mFacing), tY = yCoord, tZ = getOffsetZN(mFacing);
        if (worldObj.blockExists(tX , tY, tZ )) {
            boolean tSuccess = T;
            if (getFacing() == (short) 2) {
                tZ -= 1-zMapOffset;
                tX += xMapOffset;
                int checkX, checkY, checkZ;
                for (checkY = 0; checkY < machineY; checkY++) {
                    for (checkZ = 0; checkZ < machineZ; checkZ++) {
                        for (checkX = 0; checkX < machineX; checkX++) {
                            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + checkX, tY + checkY, tZ + checkZ, blockIDMap[checkY][checkZ][checkX], registryIDMap[checkY][checkZ][checkX], 0, MultiTileEntityMultiBlockPart.NOTHING)) {
                                tSuccess = ignoreMap[checkY][checkZ][checkX];
                                FMLLog.log(Level.FATAL,"failed");
                            }
                            FMLLog.log(Level.FATAL,"Checkpo"+mFacing+"/"+ tX+"/"+tY+"/"+tZ+"/"+checkX+"/"+checkY+"/"+checkZ+"/"+ignoreMap[checkY][checkZ][checkX]);

                        }
                    }
                }
            } else if (getFacing() == (short) 3) {
                tZ += 1-zMapOffset;
                tX -= xMapOffset ;
                int checkX, checkY, checkZ;
                for (checkY = 0; checkY < machineY; checkY++) {
                    for (checkZ = 0; checkZ < machineZ; checkZ++) {
                        for (checkX = 0; checkX < machineX; checkX++) {
                            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX - checkX, tY + checkY, tZ - checkZ, blockIDMap[checkY][checkZ][checkX], registryIDMap[checkY][checkZ][checkX], 0, MultiTileEntityMultiBlockPart.NOTHING)) {
                                tSuccess = ignoreMap[checkY][checkZ][checkX];
                                FMLLog.log(Level.FATAL,"failed");
                            }
                            FMLLog.log(Level.FATAL,"Checkpo"+mFacing+"/"+ tX+"/"+tY+"/"+tZ+"/"+checkX+"/"+checkY+"/"+checkZ+"/"+ignoreMap[checkY][checkZ][checkX]);

                        }
                    }
                }
            } else if (getFacing() == (short) 4) {
                tX -= 1-zMapOffset;
                tZ -= xMapOffset;
                int checkX, checkY, checkZ;
                for (checkY = 0; checkY < machineY; checkY++) {
                    for (checkX = 0; checkX < machineX; checkX++) {
                        for (checkZ = machineZ - 1; checkZ >= 0; checkZ--) {
                            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + checkX, tY + checkY, tZ - checkZ, blockIDMap[checkY][checkX][checkZ], registryIDMap[checkY][checkX][checkZ], 0, MultiTileEntityMultiBlockPart.NOTHING)) {
                                tSuccess = ignoreMap[checkY][checkX][checkZ];
                                FMLLog.log(Level.FATAL,"failed");
                            }
                            FMLLog.log(Level.FATAL,"Checkpo"+mFacing+"/"+ tX+"/"+tY+"/"+tZ+"/"+checkX+"/"+checkY+"/"+checkZ+"/"+ignoreMap[checkY][checkX][checkZ]);

                        }
                    }
                }
            } else if (getFacing() == (short) 5) {
                tX += 1-zMapOffset;
                tZ += xMapOffset;
                int checkX, checkY, checkZ;
                for (checkY = 0; checkY < machineY; checkY++) {
                    for (checkX = 0; checkX < machineX; checkX++) {
                        for (checkZ = 0 ; checkZ < machineZ; checkZ++) {
                            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX - checkX, tY + checkY, tZ + checkZ, blockIDMap[checkY][checkX][checkZ], registryIDMap[checkY][checkX][checkZ], 0, MultiTileEntityMultiBlockPart.NOTHING)) {
                                tSuccess = ignoreMap[checkY][checkX][checkZ];
                                FMLLog.log(Level.FATAL,"failed");
                            }
                            FMLLog.log(Level.FATAL,"Checkpo"+mFacing+"/"+ tX+"/"+tY+"/"+tZ+"/"+checkX+"/"+checkY+"/"+checkZ+"/"+ignoreMap[checkY][checkX][checkZ]);
                        }
                    }
                }
            }
            return tSuccess;
        }
        return mStructureOkay;
    }


    static {
        LH.add("gt.tooltip.multiblock.bath.1", "5x5x2 of Stainless Steel Walls");
        LH.add("gt.tooltip.multiblock.bath.2", "Main Block centered on Side-Bottom and facing outwards");
        LH.add("gt.tooltip.multiblock.bath.3", "Input and Output at any Blocks");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.bath.1"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.bath.2"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.bath.3"));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        int tX = getOffsetXN(mFacing), tY = yCoord, tZ = getOffsetZN(mFacing);
        return aX >= tX - 2 && aY >= tY && aZ >= tZ - 2 && aX <= tX + 2 && aY <= tY && aZ <= tZ + 2;
    }

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte aSide, Fluid aOutput) {
        return getAdjacentTank(SIDE_BOTTOM);
    }

    @Override
    public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) {
        return getAdjacentTileEntity(SIDE_BOTTOM);
    }

    @Override
    public DelegatorTileEntity<IInventory> getItemInputTarget(byte aSide) {
        return null;
    }

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte aSide) {
        return null;
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.bath";
    }
}