/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.storage;

import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.tileentity.energy.IMeterDetectable;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.util.UT;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

import static gregapi.data.CS.*;
import static gregapi.data.CS.TOOL_unimeter;

public abstract class MultiAdaptiveOutputBattery extends MultiBatteryBase {

    public long mOutputMin, mCurrentOutput, mOutputMax ,mOutputVoltageLast=0,mOutputAmpereLast=0;

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_OUTPUT_MIN)) mOutputMin = aNBT.getLong(NBT_OUTPUT_MIN);
        if (aNBT.hasKey(NBT_OUTPUT_MAX)) mOutputMax = aNBT.getLong(NBT_OUTPUT_MAX);

        mOutput = getEnergySizeOutputRecommended(mEnergyTypeOut,SIDE_FRONT);
        updateCurrentOutput();
    }

    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT, NBT_CAPACITY, mCapacity);
        UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergyStored);
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (aTool.equals(TOOL_unimeter) && isServerSide() && aChatReturn!=null) {
            aChatReturn.add("Capacity: "+mCapacity+" StoredEnergy: "+mEnergyStored);
            IMeterDetectable.sendReceiveEmitMessage(receivedEnergyLast,mEnergyTypeOut,mOutputVoltageLast,mOutputAmpereLast,aChatReturn);
            return 1;
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    protected void doOutputEnergy(){
        long amount = (long) Math.ceil(mCurrentOutput*1F/getEnergySizeOutputMax(mEnergyTypeOut,mFacing));
        long outputVoltage = (long) Math.floor(mCurrentOutput*1F/amount);
        long outputAmpere = (mMode == 0 ? amount : Math.min(mMode, amount));
        outputAmpere = Math.min(mMaxAmpere, outputAmpere);
        if (outputAmpere > 0 && outputVoltage > mOutputMin) {
            long tAmountUsed = ITileEntityEnergy.Util.emitEnergyToNetwork(mEnergyTypeOut, outputVoltage, outputAmpere, this);
            mOutputAmpereLast = tAmountUsed;
            mOutputVoltageLast = outputVoltage;
            mEnergyStored -= outputVoltage * tAmountUsed;
            updateCurrentOutput();
        }else mOutputAmpereLast=mOutputVoltageLast=0;
    }

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        long l1 = super.doInject(aEnergyType, aSide, aSize, aAmount, aDoInject);
        updateCurrentOutput();
        return l1;
    }

    protected void updateCurrentOutput(){
        mCurrentOutput = (long) Math.floor(mOutputMin + (mEnergyStored*1F/mCapacity)*(mInputMax-mOutputMin));
    }

    @Override public long getEnergySizeOutputMin            (TagData aEnergyType, byte aSide) {return mOutputMin;}
    @Override public long getEnergySizeOutputRecommended    (TagData aEnergyType, byte aSide) {return (mOutputMin+mOutputMax)/2;}
    @Override public long getEnergySizeOutputMax            (TagData aEnergyType, byte aSide) {return mOutputMax;}
}
