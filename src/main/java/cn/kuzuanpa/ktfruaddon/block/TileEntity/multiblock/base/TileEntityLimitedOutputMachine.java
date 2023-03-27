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

import cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.maskAlignerUV;
import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.CS;
import gregapi.data.FL;
import gregapi.fluid.FluidTankGT;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import gregapi.util.ST;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import static gregapi.data.CS.FACING_TO_SIDE;

public class TileEntityLimitedOutputMachine extends MultiTileEntityBasicMachine {
    MultiTileEntityRegistry kRegistry = MultiTileEntityRegistry.getRegistry("ktfru.multitileentity");

    public boolean canLimitedItemOutput(@NotNull ItemStack stack, DelegatorTileEntity<TileEntity> target){
        return stack.getItem() == ItemList.Alpha_Particle.getItem()&&target.mTileEntity == kRegistry.getNewTileEntity(30000) ;
    }
    /*
    @Override
    public void doOutputItems() {
        byte tAutoOutput = FACING_TO_SIDE[mFacing][mItemAutoOutput];
        for (int i=0;i < mOutputItems.length;i++) if (canLimitedItemOutput(mOutputItems[i],getItemOutputTarget(tAutoOutput))) ST.move(
                delegator(tAutoOutput), getItemOutputTarget(tAutoOutput),i,getItemOutputTarget(tAutoOutput).getAdjacentInventory(tAutoOutput)
        );
    }

     */
    @Override
    public FluidStack tapDrain(byte aSide, int aMaxDrain, boolean aDoDrain) {
        for (FluidTankGT tTank : mTanksOutput) if (tTank.has() && !FL.gas(tTank)) {
            updateInventory();
            return tTank.drain(aMaxDrain, aDoDrain);
        }
        for (FluidTankGT tTank : mTanksInput) if (tTank.has() && !FL.gas(tTank)) {
            updateInventory();
            return tTank.drain(aMaxDrain, aDoDrain);
        }
        for (FluidTankGT tTank : mTanksOutput) if (tTank.has()) {
            updateInventory();
            return tTank.drain(aMaxDrain, aDoDrain);
        }
        for (FluidTankGT tTank : mTanksInput) if (tTank.has()) {
            updateInventory();
            return tTank.drain(aMaxDrain, aDoDrain);
        }
        return null;
    }

    @Override
    public FluidStack nozzleDrain(byte aSide, int aMaxDrain, boolean aDoDrain) {
        for (FluidTankGT tTank : mTanksOutput) if (tTank.has() && FL.gas(tTank)) {
            updateInventory();
            return tTank.drain(aMaxDrain, aDoDrain);
        }
        for (FluidTankGT tTank : mTanksInput) if (tTank.has() && FL.gas(tTank)) {
            updateInventory();
            return tTank.drain(aMaxDrain, aDoDrain);
        }
        for (FluidTankGT tTank : mTanksOutput) if (tTank.has()) {
            updateInventory();
            return tTank.drain(aMaxDrain, aDoDrain);
        }
        for (FluidTankGT tTank : mTanksInput) if (tTank.has()) {
            updateInventory();
            return tTank.drain(aMaxDrain, aDoDrain);
        }
        return null;
    }

    @Override
    public void doOutputFluids() {
        for (FluidTankGT tCheck : mTanksOutput) if (tCheck.has()) if (getFluidOutputTarget(FACING_TO_SIDE[mFacing][mFluidAutoOutput],tCheck.fluid()).mTileEntity == kRegistry.getNewTileEntity(4466)) {
            if (FL.move(tCheck, getFluidOutputTarget(FACING_TO_SIDE[mFacing][mFluidAutoOutput], tCheck.fluid())) > 0) updateInventory();
        }
    }



}
