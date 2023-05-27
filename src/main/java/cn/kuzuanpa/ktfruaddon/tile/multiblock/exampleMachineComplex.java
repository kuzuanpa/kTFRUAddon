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
//This is an example machine used to learn structures, grammars etc. It's based on large bath vat in gregtech6
//这是一个示例机器，用于学习多方块机器的结构，语法等，这个机器是基于gregtech6中的大浸洗器创建的

import cn.kuzuanpa.ktfruaddon.tile.multiblock.base.TileEntityBaseLimitedOutputMachine;
import cn.kuzuanpa.ktfruaddon.tile.util.utils;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.LH;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.ST;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;

import static gregapi.data.CS.*;

public class exampleMachineComplex extends TileEntityBaseLimitedOutputMachine {

    //决定机器大小
    //this controls the size of machine.
    public final short machineX = 5, machineY = 1, machineZ = 4;
    //决定结构检测的起始位置，默认情况下是从主方块起始
    //This controls where is the start point to check structure,Default is the position of controller block
    public final short xMapOffset = -2, zMapOffset = 0;
    //映射表方向:
    //                 |
    //                 |
    //            ( tX ,tZ-1)
    //(tX-1, tZ );( tX , tZ );(tX+1, tZ )---->
    //            ( tX ,tZ+1)             x轴
    //                 | z轴
    //                 v
    //y轴为一张新的表格，代码中位于最上的表是最底下一层
    //默认情况(不改动偏移量)下主方块位于tX,tZ,如下所示
    //{main,part},
    //{part,part}
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
    //这里决定每个参与构成本机器的方块的子id
    //Controls every block needed to build the machine
    public static int[][][] blockIDMap = {{
            {18002, 18006, 0, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
    }};
    //这是决定物品注册库（即来源mod）k是本mod,g是gregtech
    short k = ST.id(MultiTileEntityRegistry.getRegistry("ktfru.multitileentity").mBlock);
    short g = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);
    public short[][][] registryIDMap = {{
            {g, g, k, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
    }};
    //T是忽略此位置的方块 ,F是正常检测
    //T = ignore ,F = normally check
    public static boolean[][][] ignoreMap = {{
            {T, T, T, T, T},
            {F, F, F, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
    }};

    //change value there to set usage of every block.
    public int getUsage(int blockID ,short registryID){
        if (blockID == 18002&&registryID==k) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
        } else if (blockID == 18002||blockID==18022&&registryID==g) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_OUT;
        }else{return MultiTileEntityMultiBlockPart.NOTHING;}
    }

    @Override
    public boolean checkStructure2() {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (worldObj.blockExists(tX, tY, tZ)) {
            boolean tSuccess = T;
            if (getFacing() == (short) 2) {
                tZ += zMapOffset;
                tX -= xMapOffset;
            } else if (getFacing() == (short) 3) {
                tZ -= zMapOffset;
                tX += xMapOffset;
            } else if (getFacing() == (short) 4) {
                tX += zMapOffset;
                tZ += xMapOffset;
            } else if (getFacing() == (short) 5) {
                tX -= zMapOffset;
                tZ -= xMapOffset;
            } else {
                tSuccess = F;
            }
            int checkX, checkY, checkZ;
            for (checkY  = 0; checkY < machineY&&tSuccess; checkY++) {
                for (checkZ = 0; checkZ < machineZ&&tSuccess; checkZ++) {
                    for (checkX = 0; checkX < machineX&&tSuccess; checkX++) {
                        if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, utils.getCheckX(mFacing, tX, checkX, checkZ), tY + checkY, utils.getCheckZ(mFacing, tZ, checkX, checkZ), blockIDMap[checkY][checkZ][checkX], registryIDMap[checkY][checkZ][checkX], 0, getUsage( blockIDMap[checkY][checkZ][checkX], registryIDMap[checkY][checkZ][checkX]))) {
                            tSuccess = ignoreMap[checkY][checkZ][checkX];
                            //FMLLog.log(Level.FATAL, "failed");
                        }
                      //  FMLLog.log(Level.FATAL, "Checkpos" + mFacing + "/" + tX + "/" + tY + "/" + tZ + "/" + getCheckX(mFacing,tX,checkX,checkZ) + "/" + checkY + "/" +  getCheckZ(mFacing,tZ,checkX,checkZ)  + "/" + ignoreMap[checkY][checkZ][checkX]);
                    }
                }
            }
            return tSuccess;
        }
        return mStructureOkay;
    }

    //这是设置主方块的物品提示
    //controls tooltip of controller block
    static {
        LH.add("gt.tooltip.multiblock.example.complex.1", "5x5x2 of Stainless Steel Walls");
        LH.add("gt.tooltip.multiblock.example.complex.2", "Main Block centered on Side-Bottom and facing outwards");
        LH.add("gt.tooltip.multiblock.example.complex.3", "Input and Output at any Blocks");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.1"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.2"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.3"));
        super.addToolTips(aList, aStack, aF3_H);
    }
    //这里是设置该机器的内部区域
    //controls areas inside the machine
    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
return aX >= xCoord - (SIDE_X_NEG == mFacing ? 0 : SIDE_X_POS == mFacing ? 3 : machineX) &&
        aY >= yCoord - (SIDE_Y_NEG == mFacing ? 0 : SIDE_Y_POS == mFacing ? 3 : machineY) &&
        aZ >= zCoord - (SIDE_Z_NEG == mFacing ? 0 : SIDE_Z_POS == mFacing ? 3 : machineZ) &&
        aX <= xCoord + (SIDE_X_POS == mFacing ? 0 : SIDE_X_NEG == mFacing ? 3 : machineX) &&
        aY <= yCoord + (SIDE_Y_POS == mFacing ? 0 : SIDE_Y_NEG == mFacing ? 3 : machineX) &&
        aZ <= zCoord + (SIDE_Z_POS == mFacing ? 0 : SIDE_Z_NEG == mFacing ? 3 : machineZ);
    }
    //下面四个是设置输入输出的地方,return null是任意面
    //controls where to I/O, return null=any side
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
    //这里填写多方块结构的名称
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.example.complex";
    }
}