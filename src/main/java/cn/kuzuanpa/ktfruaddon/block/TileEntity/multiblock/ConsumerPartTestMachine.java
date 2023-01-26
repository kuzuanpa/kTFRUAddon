/**
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock;
import cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.base.TileEntityBase11MultiInputMachine;
import cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.base.TileEntityBase12MultiInputMachineComplex;
import cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.specialPart.MultiTileEntityMultiBlockPartEnergyConsumer;
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

public class ConsumerPartTestMachine extends TileEntityBase12MultiInputMachineComplex {
    public final static short machineX = 3, machineY = 2, machineZ = 2;
    public final static short xMapOffset = -1, zMapOffset = 0;
    @Override
    public short getRegistryID(int x, int y, int z){
        return registryIDMap[y][z][x];
    }
    @Override
    public int getBlockID(int x, int y, int z){
        return blockIDMap[y][z][x];
    }
    @Override
    public  boolean isIgnored(int checkX, int checkY, int checkZ){
        return false;
    }
    @Override
    public boolean isSubSource(int blockID){
        return blockID == 31000;
    }
        public static final int[][][] blockIDMap = {{
                {0, 0, 18002},
                {31000, 18002, 18002},
        },{
                {18002, 0, 18002},
                {18002, 18002, 18002},
        }};
        public final short[][][] registryIDMap = {{
                {k, k, g},
                {k, k, g}
        },{
                {g, k, g},
                {g, k, g}
        }};


           @Override
           public int getUsage(int blockID ,short registryID){
            if (blockID == 18002&&registryID==k) {
                return MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
            }
            if (blockID==18022&&registryID==k) {
                return MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID;
            }
            return MultiTileEntityMultiBlockPart.NOTHING;
        }

        //这是设置主方块的物品提示
        //controls tooltip of controller block
        static {
            LH.add("gt.tooltip.multiblock.maskaligner.uv.1", "5x5x2 of Stainless Steel Walls");
            LH.add("gt.tooltip.multiblock.maskaligner.uv.2", "Main Block centered on Side-Bottom and facing outwards");
            LH.add("gt.tooltip.multiblock.maskaligner.uv.3", "Input and Output at any Blocks");
        }

        @Override
        public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
            aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
            aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.maskaligner.uv.1"));
            aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.maskaligner.uv.2"));
            aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.maskaligner.uv.3"));
            super.addToolTips(aList, aStack, aF3_H);
        }
        //这里设置该机器的内部区域
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
            return getAdjacentInventory(SIDE_ANY);
        }

        @Override
        public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte aSide) {
            return getAdjacentTank(SIDE_ANY);
        }
        //这里填写多方块结构的名称
        @Override
        public String getTileEntityName() {
            return "ktfru.multitileentity.testmachine.multiinput";
        }

}
