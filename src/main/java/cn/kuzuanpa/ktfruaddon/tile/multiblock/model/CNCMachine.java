/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */
package cn.kuzuanpa.ktfruaddon.tile.multiblock.model;
import cn.kuzuanpa.ktfruaddon.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.base.ModelRenderBaseMultiBlockMachine;
import cn.kuzuanpa.ktfruaddon.tile.util.utils;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.LH;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.ST;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;

import static gregapi.data.CS.*;

public class CNCMachine extends ModelRenderBaseMultiBlockMachine {

    public final short machineX = 5, machineY = 3, machineZ = 3;
    public final short xMapOffset = 0,yMapOffset=0,zMapOffset = 0;
    public static int[][][] blockIDMap = {{
            {0    , 31002, 31002,31002,31002 },
            {31002, 31002, 31002,31002,31002 },
            {31002, 31002, 31002,31002,31002 }           
    },{
            {31002, 31002, 31002,31002,31002 },
            {31002, 31002, 31002,31002,31002 },
            {31002, 31002, 31002,31002,31002 }
    },{
            {31002, 31002, 31002,31002,31002 },
            {31002, 31002, 31002,31002,31002 },
            {31002, 31002, 31002,31002,31002 }
    }};
    short k = ST.id(MultiTileEntityRegistry.getRegistry("ktfru.multitileentity").mBlock);
    short g = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);
    public static boolean[][][] ignoreMap = {{
            {T, T, T, T, T},
            {T, T, T, T, T},
            {T, T, T, T, T}
    },{
            {T, T, T, T, T},
            {T, T, T, T, T},
            {T, T, T, T, T}
    },{
            {T, T, T, T, T},
            {T, T, T, T, T},
            {T, T, T, T, T}
    }};
    public int getUsage(int blockID ,short registryID){
        if (blockID == 31003&&registryID==k) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
        } else if (blockID == 31003||blockID==18022&&registryID==g) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_OUT;
        }else{return MultiTileEntityMultiBlockPart.NOTHING;}
    }
    public int getBlockID(int checkX, int checkY, int checkZ){
        return blockIDMap[checkY][checkZ][checkX];
    }

    public  boolean isIgnored(int checkX, int checkY, int checkZ){ return ignoreMap[checkY][checkZ][checkX];}
    public short getRegistryID(int x,int y,int z){return k;}

    @Override
    public boolean checkStructure3(boolean shouldPartsTransparent) {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (worldObj.blockExists(tX, tY, tZ)) {
            boolean tSuccess = T;
            tX=utils.offsetX(mFacing,tX,tZ,xMapOffset,zMapOffset);
            tY+=yMapOffset;
            tZ=utils.offsetZ(mFacing,tX,tZ,xMapOffset,zMapOffset);
            int cX, cY, cZ;
            for (cY  = 0; cY < machineY&&tSuccess; cY++) {
                for (cZ = 0; cZ < machineZ&&tSuccess; cZ++) {
                    for (cX = 0; cX < machineX&&tSuccess; cX++) {
                        if(!isIgnored(cX,cY,cZ))if (!utils.checkAndSetTarget(this, utils.getCheckX(mFacing, tX, cX, cZ), tY + cY, utils.getCheckZ(mFacing, tZ, cX, cZ),getBlockID(cX,cY,cZ), getRegistryID(cX,cY,cZ), shouldPartsTransparent?1:0, getUsage( getBlockID(cX,cY,cZ), getRegistryID(cX,cY,cZ)))) {
                            tSuccess = F;
                        }
                    }
                }
            }
            return tSuccess;
        }
        return mStructureOkay;
    }
    @Override
    public void resetParts() {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (worldObj.blockExists(tX, tY, tZ)) {
            tX=utils.offsetX(mFacing,tX,tZ,xMapOffset,zMapOffset);
            tY+=yMapOffset;
            tZ=utils.offsetZ(mFacing,tX,tZ,xMapOffset,zMapOffset);
            int cX, cY, cZ;
            for (cY  = 0; cY < machineY; cY++) {
                for (cZ = 0; cZ < machineZ; cZ++) {
                    for (cX = 0; cX < machineX; cX++) {
                        if(!isIgnored(cX,cY,cZ))utils.resetTarget(this, utils.getCheckX(mFacing, tX, cX, cZ), tY + cY, utils.getCheckZ(mFacing, tZ, cX, cZ), 0, getUsage( getBlockID(cX,cY,cZ), getRegistryID(cX,cY,cZ)));
                    }
                }
            }
        }
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
        return new BoundingBox(utils.offsetX(mFacing,xCoord,zCoord,xMapOffset,zMapOffset),yCoord,utils.offsetZ(mFacing,xCoord,zCoord,xMapOffset,zMapOffset),utils.getRealX(mFacing,utils.offsetX(mFacing,xCoord,zCoord,xMapOffset,zMapOffset),machineX,machineZ),yCoord+machineY,utils.getRealZ(mFacing,utils.offsetZ(mFacing,xCoord,zCoord,xMapOffset,zMapOffset),machineX,machineZ)).isXYZInBox(aX,aY,aZ);}
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
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.model.cncmachine";
    }
}