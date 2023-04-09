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

import cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.util.utilLimitedOutputTarget.matchT_F;
import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.FL;
import gregapi.fluid.FluidTankGT;
import gregapi.tileentity.base.TileEntityBase04MultiTileEntities;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static gregapi.data.CS.FACING_TO_SIDE;

public abstract class  TileEntityLimitedOutputMachine extends TileEntityBase10MultiBlockMachine {
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
        for (FluidTankGT tCheck : mTanksOutput) if (tCheck.has())
            if (canOutputFluid((TileEntityBase04MultiTileEntities)getTileEntity(getFluidOutputTarget(FACING_TO_SIDE[mFacing][mFluidAutoOutput],tCheck.fluid()).getCoords()), tCheck.fluid())) {
            if (FL.move(tCheck, getFluidOutputTarget(FACING_TO_SIDE[mFacing][mFluidAutoOutput], tCheck.fluid())) > 0) updateInventory();
        }
    }
    public static matchT_F[] availMatches = {
                new matchT_F("target",new String[]{"fluid","fluidA"}),
                new matchT_F("targetB",new String[]{"fluidB","fluidAB"})
    };
    public static boolean canOutputFluid(TileEntityBase04MultiTileEntities Target, Fluid fluidToOutput) {
        if (Target==null||fluidToOutput==null) return false;
        return Arrays.stream(availMatches).anyMatch(match -> Target.getTileEntityName().contains(match.targetName) && Arrays.stream(match.fluidNames).anyMatch(fluidName -> fluidToOutput.getUnlocalizedName().contains(fluidName)));
    }
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.base.limitedoutput";
    }
}
