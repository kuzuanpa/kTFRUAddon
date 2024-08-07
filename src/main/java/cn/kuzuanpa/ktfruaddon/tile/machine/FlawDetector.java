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

import gregapi.data.TD;
import gregapi.tileentity.ITileEntityAdjacentInventoryUpdatable;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import gregapi.util.UT;
import net.minecraft.tileentity.TileEntity;

import static gregapi.data.CS.*;

public class FlawDetector  extends MultiTileEntityBasicMachine {
    //Just limit output to only 1 stack, others will delete
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

        if (mMaxProgress > 0 && !(mSpecialIsStartEnergy && mChargeRequirement > 0)) {
            rActive = T;
            if (mProgress <= mMaxProgress) {
                if (mOutputEnergy > 0) doOutputEnergy();
                mProgress += aEnergy;
            }
            if (mProgress >= mMaxProgress && (mStateOld&&!mStateNew || !TD.Energy.ALL_ALTERNATING.contains(mEnergyTypeAccepted))) {
                if (mOutputItems [0] != null && addStackToSlot(mRecipes.mInputItemsCount, mOutputItems[0])) {mSuccessful = T; mIgnited = 40; mOutputItems = ZL_IS;}
                for (int i = 0; i < mOutputFluids.length; i++) if (mOutputFluids[i] != null) for (int j = 0; j < mTanksOutput.length; j++) {
                    if (mTanksOutput[j].contains(mOutputFluids[i])) {
                        updateInventory();
                        mTanksOutput[j].add(mOutputFluids[i].amount);
                        mSuccessful = T;
                        mIgnited = 40;
                        mOutputFluids[i] = null;
                        break;
                    }
                }
                for (int i = 0; i < mOutputFluids.length; i++) if (mOutputFluids[i] != null) for (int j = 0; j < mTanksOutput.length; j++) {
                    if (mTanksOutput[j].isEmpty()) {
                        mTanksOutput[j].setFluid(mOutputFluids[i]);
                        mSuccessful = T;
                        mIgnited = 40;
                        mOutputFluids[i] = null;
                        break;
                    }
                }

                if (UT.Code.containsSomething(mOutputItems) || UT.Code.containsSomething(mOutputFluids)) {
                    mMinEnergy = 0;
                    mOutputEnergy = 0;
                    mChargeRequirement = 0;
                    mProgress = mMaxProgress;
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

                    for (byte tSide : ALL_SIDES_VALID_FIRST[FACING_TO_SIDE[mFacing][mItemAutoOutput]]) if (FACE_CONNECTED[FACING_ROTATIONS[mFacing][tSide]][mItemOutputs]) {
                        DelegatorTileEntity<TileEntity> tDelegator = getItemOutputTarget(tSide);
                        if (tDelegator != null && tDelegator.mTileEntity instanceof ITileEntityAdjacentInventoryUpdatable) {
                            ((ITileEntityAdjacentInventoryUpdatable)tDelegator.mTileEntity).adjacentInventoryUpdated(tDelegator.mSideOfTileEntity, this);
                        }
                    }
                    if(mOutputCatalyzer) outputCatalyzer();
                    onProcessFinished();
                }
            }
        }

        mStateOld = mStateNew;

        if (!mDisabledItemOutput && SIDES_VALID[mItemAutoOutput]) {
            boolean
                    tOutputEmpty = T;
            for (int i = mRecipes.mInputItemsCount, j = i + mRecipes.mOutputItemsCount; i < j; i++) if (slotHas(i)) {tOutputEmpty = F; break;}

            // Output not Empty && (Successfully produced something or just got ignited || Some Inventory Stuff changes || The Machine has just been turned ON || Output has been blocked since 256 active ticks || Check once every 10 Seconds)
            if (!tOutputEmpty && (mIgnited > 0 || mInventoryChanged || !mRunning || mOutputBlocked == 1 || aTimer%200 == 5)) {
                boolean tInventoryChanged = mInventoryChanged;
                mInventoryChanged = F;
                doOutputItems();
                if (mInventoryChanged) mOutputBlocked = 0; else mInventoryChanged = tInventoryChanged;
            }

            tOutputEmpty = T;
            for (int i = mRecipes.mInputItemsCount, j = i + mRecipes.mOutputItemsCount; i < j; i++) if (slotHas(i)) {tOutputEmpty = F; mOutputBlocked++; break;}

            if (tOutputEmpty) mOutputBlocked = 0;
        }

        return rActive;
    }
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.machine.flawdetector";
    }
}
