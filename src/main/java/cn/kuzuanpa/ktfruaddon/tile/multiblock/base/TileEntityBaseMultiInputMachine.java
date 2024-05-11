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

import cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.MultiBlockPartEnergyConsumer;
import gregapi.data.FL;
import gregapi.data.TD;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;

import java.util.ArrayList;

import static gregapi.data.CS.SIDES_VALID;
import static gregapi.data.CS.T;

public abstract class TileEntityBaseMultiInputMachine extends TileEntityBase10MultiBlockMachine {
    public ArrayList<MultiBlockPartEnergyConsumer> MultiInputSubBlocks = new ArrayList<>();
    public boolean subSourceRunning =true;


    public void addInputSubSource(MultiBlockPartEnergyConsumer p){
        this.MultiInputSubBlocks.add(p);
    }
    public ArrayList<MultiBlockPartEnergyConsumer> getInputSubSource(){
      return this.MultiInputSubBlocks;
    }
    public void isSubSourceRunning(MultiBlockPartEnergyConsumer subSource){
        if (subSource.isInvalid()) subSourceRunning = false;
        subSourceRunning = subSourceRunning && (subSource.getStateRunningPassively() || subSource.getStateRunningActively());
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if (aIsServerSide) {
            if (mBlockUpdated) updateAdjacentToggleableEnergySources();
            if (!mStopped) {
                if (mEnergyTypeAccepted == TD.Energy.TU) mEnergy++;
                if (mChargeRequirement > 0 && mEnergyTypeCharged == TD.Energy.TU) mChargeRequirement--;
            }

            if (!mDisabledFluidOutput && SIDES_VALID[mFluidAutoOutput]) doOutputFluids();
            if (this.mStructureOkay) {
                subSourceRunning = true;
                if (this.MultiInputSubBlocks.isEmpty()) {
                    this.setStateOnOff(false);
                    return;
                }
                this.MultiInputSubBlocks.forEach(this::isSubSourceRunning);
                if (this.getStateOnOff() != subSourceRunning) {
                    this.setStateOnOff(subSourceRunning);
                    updateClientData();
                    return;
                }
            }
            doWork(aTimer);

            if (mTimer % 600 == 5 && mRunning) doDefaultStructuralChecks();

            for (int i = 0; i < mTanksInput.length; i++)
                slot(mRecipes.mInputItemsCount + mRecipes.mOutputItemsCount + 1 + i, FL.display(mTanksInput[i], T, T));
            for (int i = 0; i < mTanksOutput.length; i++)
                slot(mRecipes.mInputItemsCount + mRecipes.mOutputItemsCount + 1 + i + mTanksInput.length, FL.display(mTanksOutput[i], T, T));
        }
    }
}

