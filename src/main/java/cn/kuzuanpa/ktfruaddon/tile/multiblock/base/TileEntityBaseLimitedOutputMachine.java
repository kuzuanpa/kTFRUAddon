/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.multiblock.base;

import cn.kuzuanpa.ktfruaddon.code.CodeTranslate;
import cn.kuzuanpa.ktfruaddon.tile.util.utilLimitedOutputTarget.*;
import cn.kuzuanpa.ktfruaddon.tile.util.utils;
import cpw.mods.fml.common.FMLLog;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.code.ItemStackContainer;
import gregapi.code.ItemStackSet;
import gregapi.code.ModData;
import gregapi.data.CS;
import gregapi.data.FL;
import gregapi.data.IL;
import gregapi.data.MT;
import gregapi.fluid.FluidTankGT;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
import gregapi.util.OM;
import gregapi.util.ST;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.apache.logging.log4j.Level;

import java.util.Arrays;

import static gregapi.data.CS.*;

public abstract class TileEntityBaseLimitedOutputMachine extends TileEntityBase10MultiBlockMachine {
    MultiTileEntityRegistry kRegistry = MultiTileEntityRegistry.getRegistry("ktfru.multitileentity");

    public ItemStackSet<ItemStackContainer> getItemFilter(DelegatorTileEntity<TileEntity> target){
        ItemStackSet<ItemStackContainer> filter = new ItemStackSet<>();
        Arrays.stream(availMatchesItem).forEach(match -> {
            if ((utils.getTargetTileEntityName(target.mTileEntity).contains(match.targetName)))
                Arrays.stream(match.stack).forEach(filter::add);
        });
        if (filter.isEmpty())return null;
        return filter;
    }

    public static final matchT_I[] availMatchesItem = {
            new matchT_I("minecraft.hopper",new ItemStack[]{}),
            new matchT_I("gt.multitileentity",new ItemStack[]{OM.crushedCentrifuged(MT.Cu,1),IL.A97_Hammer.get(1),ST.make(ModData.MODS.get(ModIDs.BOTA),"manaResource",1,6)}),
    };
    @Override
    public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {
        if (aStack==null) return false;
        return Arrays.stream(availMatchesItem).anyMatch(match -> match.targetName.equals("minecraft.hopper") && Arrays.stream(match.stack).anyMatch(stack -> /*Ignore StackSize*/aStack.getItem().equals(stack.getItem()) &&aStack.getItemDamage()==stack.getItemDamage()));
    }


    @Override
    public void doOutputItems() {
        FMLLog.log(Level.FATAL, CodeTranslate.itemToCode(slot(0)));
        byte tAutoOutput = CS.FACING_TO_SIDE[this.mFacing][this.mItemAutoOutput];
        ST.moveAll(this.delegator(tAutoOutput), this.getItemOutputTarget(tAutoOutput),getItemFilter(this.getItemOutputTarget(tAutoOutput)),F, F, F, T, 64, 1, 64, 1);
    }


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
            if (canOutputFluid(utils.getTargetTileEntityName(getTileEntity(getFluidOutputTarget(FACING_TO_SIDE[mFacing][mFluidAutoOutput],tCheck.fluid()).getCoords())), tCheck.fluid())) {
            if (FL.move(tCheck, getFluidOutputTarget(FACING_TO_SIDE[mFacing][mFluidAutoOutput], tCheck.fluid())) > 0) updateInventory();
        }
    }
    public static final matchT_F[] availMatchesFluid = {
                new matchT_F("target",new String[]{"fluid","fluidA"}),
                new matchT_F("targetB",new String[]{"fluidB","fluidAB"}),
    };
    public static boolean canOutputFluid(String TileEntityName, Fluid fluidToOutput) {
        if (TileEntityName==null||fluidToOutput==null) return false;
        return Arrays.stream(availMatchesFluid).anyMatch(match -> TileEntityName.contains(match.targetName) && Arrays.stream(match.fluidNames).anyMatch(fluidName -> fluidToOutput.getUnlocalizedName().contains(fluidName)));
    }
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.base.limitedoutput";
    }
}
