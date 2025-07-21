/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */


package cn.kuzuanpa.ktfruaddon.tile.machine;

import gregapi.data.FL;
import gregapi.data.TD;
import gregapi.fluid.FluidTankGT;
import gregapi.recipes.Recipe;
import gregapi.tileentity.ITileEntityAdjacentInventoryUpdatable;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import static gregapi.data.CS.*;
import static gregapi.data.CS.T;

public class FlawDetector extends MultiTileEntityBasicMachine {
    public int checkRecipe(boolean aApplyRecipe, boolean aUseAutoIO) {
        mCouldUseRecipe = F;
        if (mRecipes == null) return DID_NOT_FIND_RECIPE;

        if (aUseAutoIO) doInputItems();

        int tInputItemsCount = 0, tInputFluidsCount = 0;
        ItemStack[] tInputs = new ItemStack[getInputItemsCount()];
        for (int i = 0; i < getInputItemsCount(); i++) {
            tInputs[i] = slot(i);
            if (ST.valid(tInputs[i])) tInputItemsCount++;
        }

        byte tAutoInput = FACING_TO_SIDE[mFacing][mFluidAutoInput];
        if (aUseAutoIO && !mDisabledFluidInput && SIDES_VALID[tAutoInput]) {
            DelegatorTileEntity<IFluidHandler> tTileEntity = getFluidInputTarget(tAutoInput);
            if (tTileEntity != null && tTileEntity.mTileEntity != null) {
                FluidTankInfo[] tInfos = tTileEntity.mTileEntity.getTankInfo(FORGE_DIR[tTileEntity.mSideOfTileEntity]);
                if (tInfos != null) for (FluidTankInfo tInfo : tInfos) if (tInfo != null && tInfo.fluid != null && tInfo.fluid.amount > 0 && getFluidTankFillable(SIDE_ANY, tInfo.fluid) != null) {
                    if (FL.move_(tTileEntity, delegator(tAutoInput), tInfo.fluid) > 0) updateInventory();
                }
            }
        }
        for (FluidTankGT tTank : mTanksInput) if (tTank.has()) tInputFluidsCount++;

        if (tInputItemsCount                     < mRecipes.mMinimalInputItems ) return DID_NOT_FIND_RECIPE;
        if (tInputFluidsCount                    < mRecipes.mMinimalInputFluids) return DID_NOT_FIND_RECIPE;
        if (tInputItemsCount + tInputFluidsCount < mRecipes.mMinimalInputs     ) return DID_NOT_FIND_RECIPE;

        Recipe tRecipe = mRecipes.findRecipe(this, mLastRecipe, F, mEnergyTypeAccepted == TD.Energy.RF ? mInputMax / RF_PER_EU : mInputMax, slot(getInputItemsCount()+getOutputItemsCount()), mTanksInput, tInputs);

        int tMaxProcessCount = 0;

        if (tRecipe == null) {
            if (!mCanUseOutputTanks) return DID_NOT_FIND_RECIPE;
            tRecipe = mRecipes.findRecipe(this, mLastRecipe, F, mEnergyTypeAccepted == TD.Energy.RF ? mInputMax / RF_PER_EU : mInputMax, slot(getInputItemsCount()+getOutputItemsCount()), mTanksOutput, tInputs);
            if (tRecipe == null) return DID_NOT_FIND_RECIPE;

            if (tRecipe.mCanBeBuffered) mLastRecipe = tRecipe;
            tMaxProcessCount = canOutput(tRecipe);
            if (tMaxProcessCount <= 0) return FOUND_RECIPE_BUT_DID_NOT_MEET_REQUIREMENTS;
            if (aApplyRecipe) aApplyRecipe = (!mRequiresIgnition || mIgnited > 0 || mActive);
            if (!tRecipe.isRecipeInputEqual(aApplyRecipe, F, mTanksOutput, tInputs)) return FOUND_RECIPE_BUT_DID_NOT_MEET_REQUIREMENTS;
            mCouldUseRecipe = T;
            if (!aApplyRecipe) return FOUND_AND_COULD_HAVE_USED_RECIPE;

            if (tMaxProcessCount > 1) {
                if (!mParallelDuration && mEnergyTypeAccepted != TD.Energy.TU) tMaxProcessCount = (int) UT.Code.bind(1, tMaxProcessCount, mInput / Math.max(1, (mEnergyTypeAccepted == TD.Energy.RF ? tRecipe.mEUt * RF_PER_EU : tRecipe.mEUt)));
                tMaxProcessCount = 1+tRecipe.isRecipeInputEqual(tMaxProcessCount-1, mTanksOutput, tInputs);
            }
        } else {
            if (tRecipe.mCanBeBuffered) mLastRecipe = tRecipe;
            tMaxProcessCount = canOutput(tRecipe);
            if (tMaxProcessCount <= 0) return FOUND_RECIPE_BUT_DID_NOT_MEET_REQUIREMENTS;
            if (aApplyRecipe) aApplyRecipe = (!mRequiresIgnition || mIgnited > 0 || mActive);
            if (!tRecipe.isRecipeInputEqual(aApplyRecipe, F, mTanksInput, tInputs)) return FOUND_RECIPE_BUT_DID_NOT_MEET_REQUIREMENTS;
            mCouldUseRecipe = T;
            if (!aApplyRecipe) return FOUND_AND_COULD_HAVE_USED_RECIPE;

            if (tMaxProcessCount > 1) {
                if (!mParallelDuration && mEnergyTypeAccepted != TD.Energy.TU) tMaxProcessCount = (int)UT.Code.bind(1, tMaxProcessCount, mInput / Math.max(1, (mEnergyTypeAccepted == TD.Energy.RF ? tRecipe.mEUt * RF_PER_EU : tRecipe.mEUt)));
                tMaxProcessCount = 1+tRecipe.isRecipeInputEqual(tMaxProcessCount-1, mTanksInput, tInputs);
            }
        }

        for (byte tSide : ALL_SIDES_VALID_FIRST[FACING_TO_SIDE[mFacing][mItemAutoInput]]) if (FACE_CONNECTED[FACING_ROTATIONS[mFacing][tSide]][mItemInputs]) {
            DelegatorTileEntity<IInventory> tDelegator = getItemInputTarget(tSide);
            if (tDelegator != null && tDelegator.mTileEntity instanceof ITileEntityAdjacentInventoryUpdatable) {
                ((ITileEntityAdjacentInventoryUpdatable)tDelegator.mTileEntity).adjacentInventoryUpdated(tDelegator.mSideOfTileEntity, this);
            }
        }

        if (mSpecialIsStartEnergy && (!mActive || (mCurrentRecipe != null && mCurrentRecipe != tRecipe))) mChargeRequirement = tRecipe.mSpecialValue;

        mCurrentRecipe = tRecipe;
        mOutputItems   = new ItemStack[]{RNGSUS.nextInt(tRecipe.getMaxChance(0)) < tRecipe.getOutputChance(0) ? tRecipe.getOutput(0) : tRecipe.getOutput(1)};
        mOutputFluids  = tRecipe.getFluidOutputs(RNGSUS, tMaxProcessCount);

        if (tRecipe.mEUt < 0) {
            mOutputEnergy = -tRecipe.mEUt;
            mMaxProgress = tRecipe.mDuration;
            mMinEnergy = 0;
        } else {
            if (mParallelDuration) {
                mMinEnergy = Math.max(1, (mEnergyTypeAccepted == TD.Energy.RF ? tRecipe.mEUt * RF_PER_EU : tRecipe.mEUt));
                mMaxProgress = Math.max(1, UT.Code.units(mMinEnergy * Math.max(1, tRecipe.mDuration) * tMaxProcessCount, mEfficiency, 10000, T));
            } else {
                mMinEnergy = Math.max(1, (mEnergyTypeAccepted == TD.Energy.RF ? tRecipe.mEUt * RF_PER_EU * tMaxProcessCount : mEnergyTypeAccepted == TD.Energy.TU ? tRecipe.mEUt : tRecipe.mEUt * tMaxProcessCount));
                mMaxProgress = Math.max(1, UT.Code.units(mMinEnergy * Math.max(1, tRecipe.mDuration), mEfficiency, 10000, T));
            }
            if (!mCheapOverclocking) while (mMinEnergy < mInputMin && mMinEnergy * 4 <= mInputMax) {mMinEnergy *= 4; mMaxProgress *= 2;}
        }

        removeAllDroppableNullStacks();
        return FOUND_AND_SUCCESSFULLY_USED_RECIPE;
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.machine.flawdetector";
    }
}
