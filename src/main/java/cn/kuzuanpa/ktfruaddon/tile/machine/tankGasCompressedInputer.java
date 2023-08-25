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

package cn.kuzuanpa.ktfruaddon.tile.machine;

import cn.kuzuanpa.ktfruaddon.tile.multiblock.tankGasCompressed;
import gregapi.data.FL;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.tileentity.ITileEntityAdjacentInventoryUpdatable;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.UT;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

import java.util.List;

import static gregapi.data.CS.*;

public class tankGasCompressedInputer extends MultiTileEntityBasicMachine {
    @Override
    public void onTickFirst2(boolean aIsServerSide) {}
    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        addToolTipsSided(aList, aStack, aF3_H);

        aList.add(LH.Chat.DGRAY    + LH.get(LH.TOOL_TO_TOGGLE_SCREWDRIVER));
        if (SIDES_VALID[mFluidAutoInput])
            aList.add(LH.Chat.DGRAY    + LH.get(LH.TOOL_TO_TOGGLE_AUTO_INPUTS_MONKEY_WRENCH));
        if (SIDES_VALID[mFluidAutoOutput])
            aList.add(LH.Chat.DGRAY    + LH.get(LH.TOOL_TO_TOGGLE_AUTO_OUTPUTS_MONKEY_WRENCH));
        aList.add(LH.Chat.DGRAY    + LH.get(LH.TOOL_TO_RESET_SOFT_HAMMER));
        aList.add(LH.Chat.DGRAY    + LH.get(LH.TOOL_TO_DETAIL_MAGNIFYINGGLASS));

    }
    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if (aIsServerSide) {
            if (mBlockUpdated) updateAdjacentToggleableEnergySources();
            if (!mStopped) {
                if (mEnergyTypeAccepted == TD.Energy.TU) mEnergy++;
                if (mChargeRequirement > 0 && mEnergyTypeCharged == TD.Energy.TU) mChargeRequirement--;
            }
            doWork(aTimer);

            if (!mDisabledFluidOutput && SIDES_VALID[mFluidAutoOutput]&&mTanksOutput[0].has()) doOutputFluids();

            if (mTimer % 600 == 5 && mRunning) doDefaultStructuralChecks();

            for (int i = 0; i < mTanksInput .length; i++) slot(mRecipes.mInputItemsCount + mRecipes.mOutputItemsCount + 1 + i                       , FL.display(mTanksInput [i], T, T));
            for (int i = 0; i < mTanksOutput.length; i++) slot(mRecipes.mInputItemsCount + mRecipes.mOutputItemsCount + 1 + i + mTanksInput.length  , FL.display(mTanksOutput[i], T, T));

        }
    }

    @Override
    public void doOutputFluids() {
        DelegatorTileEntity<IFluidHandler> aTo = getFluidOutputTarget(FACING_TO_SIDE[mFacing][mFluidAutoOutput], mTanksOutput[0].fluid());
        if (aTo == null)return;
        long aMaxMoved = Long.MAX_VALUE;
        FluidStack tDrained = mTanksOutput[0].drain(UT.Code.bindInt(Long.MAX_VALUE), F);
        if (tDrained == null || tDrained.amount <= 0) return;
        tankGasCompressed target = null;
        if (aTo.mTileEntity instanceof tankGasCompressed) target= (tankGasCompressed) aTo.mTileEntity;
        if ((aTo.mTileEntity instanceof MultiTileEntityMultiBlockPart)&& ((MultiTileEntityMultiBlockPart)aTo.mTileEntity).getTarget(F) instanceof tankGasCompressed) target=(tankGasCompressed)((MultiTileEntityMultiBlockPart)aTo.mTileEntity).getTarget(F);
        if (target==null) return;
        tDrained.amount = UT.Code.bindInt((target.forceFill(tDrained.copy())));
        if (tDrained.amount <= 0) return;
        mTanksOutput[0].drain(tDrained.amount, T);
        updateInventory();
    }

    @Override
    public boolean doActive(long aTimer, long aEnergy) {
        boolean rActive = F;

        if (mMaxProgress <= 0) {
            // Successfully produced something or just got ignited || Some Inventory Stuff changes || The Machine has just been turned ON || Check once every Minute
            if ((mIgnited > 0 || mInventoryChanged || !mRunning || aTimer%1200 == 5) && checkRecipe(!mStopped, T) == FOUND_AND_SUCCESSFULLY_USED_RECIPE) {
                onProcessStarted();
            } else {
                mProgress = 0;
            }
        }

        mSuccessful = F;

        if (!(mSpecialIsStartEnergy && mChargeRequirement > 0)) {
            if ((mTanksOutput[0].contains(mTanksInput[0].fluid())&&(mTanksInput[0].capacity() >= mTanksOutput[0].amount())||mTanksOutput[0].isEmpty()))
            {
                rActive=true;
                int moveRate = (int)aEnergy*10;
                if (mTanksOutput[0].contains(mTanksInput[0].fluid()) && (mTanksInput[0].capacity() >= mTanksOutput[0].amount())) {
                    updateInventory();
                    mTanksOutput[0].add(moveRate);
                    mSuccessful = T;
                    mIgnited = 40;
                    mTanksInput[0].remove(moveRate);
                }
                if (mTanksOutput[0].isEmpty()) {
                    mTanksOutput[0].setFluid(mTanksInput[0].drain(moveRate));
                    mSuccessful = T;
                    mIgnited = 40;
                }

                if (UT.Code.containsSomething(mOutputItems) || UT.Code.containsSomething(mOutputFluids)) {
                    mMinEnergy = 0;
                    mOutputEnergy = 0;
                    mChargeRequirement = 0;
                } else {
                    mProgress -= mMaxProgress; // this way the leftover energy can be used on the next processed thing, unless it gets stuck on an output.
                    mMinEnergy = 0;
                    mMaxProgress = 0;
                    mOutputEnergy = 0;
                    mChargeRequirement = 0;
                    mOutputItems = ZL_IS;
                    mOutputFluids = ZL_FS;
                    mSuccessful = T;
                    mIgnited = 40;

                    for (byte tSide : ALL_SIDES_VALID_FIRST[FACING_TO_SIDE[mFacing][mItemAutoOutput]])
                        if (FACE_CONNECTED[FACING_ROTATIONS[mFacing][tSide]][mItemOutputs]) {
                            DelegatorTileEntity<TileEntity> tDelegator = getItemOutputTarget(tSide);
                            if (tDelegator != null && tDelegator.mTileEntity instanceof ITileEntityAdjacentInventoryUpdatable) {
                                ((ITileEntityAdjacentInventoryUpdatable) tDelegator.mTileEntity).adjacentInventoryUpdated(tDelegator.mSideOfTileEntity, this);
                            }
                        }

                    onProcessFinished();
                }
            }
        }

        mStateOld = mStateNew;

        if (!mDisabledItemOutput && SIDES_VALID[mItemAutoOutput]) {
            boolean tOutputEmpty = T;
            for (int i = mRecipes.mInputItemsCount, j = i + mRecipes.mOutputItemsCount; i < j; i++) if (slotHas(i)) {tOutputEmpty = F; break;}

            // Output not Empty && (Successfully produced something or just got ignited || Some Inventory Stuff changes || The Machine has just been turned ON || Output has been blocked since 256 active ticks || Check once every 10 Seconds)
            if (!tOutputEmpty && (mIgnited > 0 || mInventoryChanged || !mRunning || mOutputBlocked == 1 || aTimer%200 == 5)) {
                boolean tInventoryChanged = mInventoryChanged;
                mInventoryChanged = F;
                doOutputItems();
                if (mInventoryChanged) mOutputBlocked = 0; else mInventoryChanged |= tInventoryChanged;
            }

            tOutputEmpty = T;
            for (int i = mRecipes.mInputItemsCount, j = i + mRecipes.mOutputItemsCount; i < j; i++) if (slotHas(i)) {tOutputEmpty = F; mOutputBlocked++; break;}

            if (tOutputEmpty) mOutputBlocked = 0;
        }

        return rActive;
    }

    @Override
    public IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {
        if (!mDisabledFluidOutput && SIDES_VALID[mFluidAutoOutput] && FACING_TO_SIDE[mFacing][mFluidAutoOutput] == aSide) return null;
        if (!FACE_CONNECTED[FACING_ROTATIONS[mFacing][aSide]][mFluidInputs]) return null;
        for (gregapi.fluid.FluidTankGT fluidTankGT : mTanksInput) if (fluidTankGT.contains(aFluidToFill)) return fluidTankGT;
        for (gregapi.fluid.FluidTankGT fluidTankGT : mTanksInput) if (fluidTankGT.isEmpty()) return fluidTankGT;
        return null;
    }

    @Override public String getTileEntityName() {return "ktfru.multitileentity.machine.inputer.tankgascompressed";}
}
