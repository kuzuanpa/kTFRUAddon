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
import cn.kuzuanpa.ktfruaddon.tile.multiblock.base.TileEntityBaseRoom;
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

public class exampleMachineRoom extends TileEntityBaseRoom {

    short k = ST.id(MultiTileEntityRegistry.getRegistry("ktfru.multitileentity").mBlock);
    short g = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);
    //change value there to set usage of every block.
    public int getUsage(int blockID ,short registryID){
        if (blockID == 18002&&registryID==k) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
        } else if (blockID == 18002||blockID==18022&&registryID==g) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_OUT;
        }else{return MultiTileEntityMultiBlockPart.NOTHING;}
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
        return "ktfru.multitileentity.multiblock.room.test";
    }

    @Override
    public utils.GTTileEntity[] getAvailableTiles() {
        return new utils.GTTileEntity[]{new utils.GTTileEntity(k,30102,0,MultiTileEntityMultiBlockPart.EVERYTHING)};
    }

    @Override
    public int[] getCheckRange2() {
        return new int[] {-4,-5,-6,3,3,6};
}}