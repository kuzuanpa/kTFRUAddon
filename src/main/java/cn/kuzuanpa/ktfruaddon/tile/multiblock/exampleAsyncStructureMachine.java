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

import cn.kuzuanpa.ktfruaddon.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.base.TileEntityBaseLimitedOutputMachine;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.base.async.AsyncStructureManager;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.base.async.IAsyncMappedStructure;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.base.async.IAsyncStructure;
import cn.kuzuanpa.ktfruaddon.tile.util.utils;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.LH;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.ST;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;
import java.util.UUID;

import static gregapi.data.CS.*;

public class exampleAsyncStructureMachine extends TileEntityBaseLimitedOutputMachine implements IAsyncMappedStructure, IAsyncStructure {

    //决定机器大小
    //this controls the size of machine.
    public final short sizeX = 5, sizeY = 1, sizeZ = 4;
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
    // ^^^^
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
    // ^^^^
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

    @Override
    public int getDesign(int mapX, int mapY, int mapZ, int blockId, int registryID) {
        return 0;
    }

    @Override
    public int getUsage(int mapX, int mapY, int mapZ, int registryID, int blockID) {
        if (blockID == 18002&&registryID==k) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
        } else if (blockID == 18002||blockID==18022&&registryID==g) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_OUT;
        }else{return MultiTileEntityMultiBlockPart.NOTHING;}
    }

    public int getBlockID(int checkX, int checkY, int checkZ){
        return blockIDMap[checkY][checkZ][checkX];
    }

    public  boolean isIgnored(int checkX, int checkY, int checkZ){
        return ignoreMap[checkY][checkZ][checkX];
    }
    public short getRegistryID(int checkX, int checkY, int checkZ){return registryIDMap[checkY][checkZ][checkX];}

    @Override
    public List<ChunkCoordinates> getComputeNodesCoordList() {return null;}
    UUID asyncTaskID = UUID.randomUUID();
    ChunkCoordinates lastFailedPos=null;

    @Override
    public boolean checkStructure2() {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        AsyncStructureManager.addStructureComputeTask(asyncTaskID,worldObj,this);
        return false;
    }

    @Override
    public boolean asyncCheckStructure(AsyncStructureManager.WorldContainer world) {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (world.getBlock(tX, tY, tZ) == null) return mStructureOkay;
        lastFailedPos = checkMappedStructure(world,null, sizeX, sizeY, sizeZ,xMapOffset,0,zMapOffset,false);
        return lastFailedPos==null;
    }

    @Override
    public void onStructureComputeCompleted() {
        try {
            mStructureOkay = AsyncStructureManager.isStructureCompleted(asyncTaskID);
        }catch (AsyncStructureManager.NotCompletedException e){}
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
        return new BoundingBox(utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset),yCoord,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset),utils.getRealX(mFacing,utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset), sizeX, sizeZ),yCoord+ sizeY,utils.getRealZ(mFacing,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset), sizeX, sizeZ)).isXYZInBox(aX,aY,aZ);
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