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

import gregapi.code.ArrayListNoNulls;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.gui.ContainerClientDefault;
import gregapi.gui.ContainerCommonDefault;
import gregapi.tileentity.data.ITileEntityProgress;
import gregapi.tileentity.energy.IMeterDetectable;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.energy.ITileEntityEnergyDataCapacitor;
import gregapi.tileentity.machines.ITileEntityRunningActively;
import gregapi.tileentity.machines.ITileEntitySwitchableMode;
import gregapi.tileentity.machines.ITileEntitySwitchableOnOff;
import gregapi.tileentity.multiblocks.IMultiBlockEnergy;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import gregapi.util.UT;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static cn.kuzuanpa.ktfruaddon.tile.util.kTileNBT.LOSS_PERCENT;
import static cn.kuzuanpa.ktfruaddon.tile.util.kTileNBT.MAX_AMPERE;
import static gregapi.data.CS.*;
import static gregapi.data.CS.T;

public abstract class MultiBatteryBase extends TileEntityBase10MultiBlockBase implements ITileEntityEnergy, IMultiBlockEnergy, ITileEntityEnergyDataCapacitor, ITileEntityRunningActively, ITileEntitySwitchableOnOff, ITileEntitySwitchableMode, ITileEntityProgress, IMeterDetectable {
    public long mEnergyStored=0, mCapacity=0, mMaxAmpere=1, mInputMin, mInputMax, mOutput,mOutputAmpereLast=0;
    public float mLossPercent =0;
    public byte mMode = 0;
    public boolean mActive=false, mForceStopped=false;
    public ArrayList<MeterData> receivedEnergy=new ArrayList<>(),receivedEnergyLast = new ArrayList<>();

    public TagData mEnergyType = TD.Energy.QU;
    public TagData mEnergyTypeOut = TD.Energy.QU;

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        super.addToolTips(aList, aStack, aF3_H);
        LH.addEnergyToolTips(this, aList, mEnergyType, mEnergyTypeOut, LH.get(LH.FACE_ANYBUT_FRONT), LH.get(LH.FACE_FRONT));
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(MAX_AMPERE)) mMaxAmpere = aNBT.getLong(MAX_AMPERE);
        if (aNBT.hasKey(LOSS_PERCENT)) mLossPercent = aNBT.getFloat(LOSS_PERCENT);
        if (aNBT.hasKey(NBT_OUTPUT)) mOutput = aNBT.getLong(NBT_OUTPUT);
        if (aNBT.hasKey(NBT_INPUT_MIN)) mInputMin = aNBT.getLong(NBT_INPUT_MIN);
        if (aNBT.hasKey(NBT_INPUT_MAX)) mInputMax = aNBT.getLong(NBT_INPUT_MAX);

        if (aNBT.hasKey(NBT_CAPACITY)) mCapacity = aNBT.getLong(NBT_CAPACITY);
        if (aNBT.hasKey(NBT_ENERGY)) mEnergyStored = aNBT.getLong(NBT_ENERGY);
    }

    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT, NBT_CAPACITY, mCapacity);
        UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergyStored);
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (aTool.equals(TOOL_unimeter) && isServerSide() && aChatReturn!=null) {
            IMeterDetectable.sendReceiveEmitMessage(receivedEnergyLast,mEnergyTypeOut,mOutput,mOutputAmpereLast,aChatReturn);
            return 1;
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if (!aIsServerSide) return;
        if(mEnergyStored > mCapacity) mEnergyStored=mCapacity;
        mActive = (mEnergyStored > mOutput);

        mOutputAmpereLast=0;
        receivedEnergyLast = receivedEnergy;
        receivedEnergy = new ArrayList<>();

        if (mTimer % 600 == 5) doDefaultStructuralChecks();

        if (!mActive) return;

        if (!mForceStopped) doOutputEnergy();
        if (mTimer % 200 == 5) mEnergyStored=(long)Math.floor(mEnergyStored*(1.0D-(mLossPercent /100D)));
    }

    protected void doOutputEnergy(){
        long amount = (long) Math.ceil(mOutput*1F/getEnergySizeOutputMax(mEnergyTypeOut,mFacing));
        long outputVoltage = (long) Math.floor(mOutput*1F/amount);
        long outputAmpere = (mMode == 0 ? amount : Math.min(mMode, amount));
        outputAmpere = Math.min(mMaxAmpere, outputAmpere);
        if (outputAmpere > 0 && outputVoltage > mOutput) {
            long tAmountUsed = ITileEntityEnergy.Util.emitEnergyToNetwork(mEnergyTypeOut, outputVoltage, outputAmpere, this);
            mOutputAmpereLast = tAmountUsed;
            mOutput = outputVoltage;
            mEnergyStored -= outputVoltage * tAmountUsed;
        }else mOutputAmpereLast=0;
    }

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if(mCapacity==0)return 0;
        aSize = Math.abs(aSize);
        if (aSize > getEnergySizeInputMax(aEnergyType, aSide)) {
            if (aDoInject) overcharge(aSize, aEnergyType);
            return aAmount;
        }
        long canReceiveAmount = Math.min( (long) Math.ceil((mCapacity-mEnergyStored)*1F/aSize) , mMaxAmpere);
        long receiveAmount = Math.min(canReceiveAmount,aAmount);
        if (aDoInject) {
            mEnergyStored += receiveAmount * aSize;
            this.receivedEnergy.add(new MeterData(aEnergyType, aSize, receiveAmount));
            if(mEnergyStored>mCapacity){
                mEnergyStored=mCapacity;
            }
        }
        return receiveAmount;
    }

    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {return new ContainerClientDefault(aPlayer.inventory, this, aGUIID);}
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {return new ContainerCommonDefault(aPlayer.inventory, this, aGUIID);}

    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return UT.Code.getAscendingArray(invsize());}

    @Override public boolean isEnergyType                   (TagData aEnergyType, byte aSide, boolean aEmitting) {return aEnergyType == (aEmitting ? mEnergyTypeOut : mEnergyType);}
    @Override public boolean isEnergyCapacitorType          (TagData aEnergyType, byte aSide) {return aEnergyType == mEnergyType;}
    @Override public boolean isEnergyAcceptingFrom          (TagData aEnergyType, byte aSide, boolean aTheoretical) {return                                      (SIDES_INVALID[aSide] || isInput (aSide)) && super.isEnergyAcceptingFrom(aEnergyType, aSide, aTheoretical);}
    @Override public boolean isEnergyEmittingTo             (TagData aEnergyType, byte aSide, boolean aTheoretical) {return (aTheoretical || !mForceStopped) &&  (SIDES_INVALID[aSide] || isOutput(aSide)) && super.isEnergyEmittingTo   (aEnergyType, aSide, aTheoretical);}

    @Override public long getEnergySizeOutputMin            (TagData aEnergyType, byte aSide) {return mOutput;}
    @Override public long getEnergySizeOutputRecommended    (TagData aEnergyType, byte aSide) {return mOutput;}
    @Override public long getEnergySizeOutputMax            (TagData aEnergyType, byte aSide) {return mOutput;}
    @Override public long getEnergySizeInputMin(TagData aEnergyType, byte aSide) {return mInputMin;}
    @Override public long getEnergySizeInputRecommended     (TagData aEnergyType, byte aSide) {return (mInputMin+mInputMax)/2;}
    @Override public long getEnergySizeInputMax(TagData aEnergyType, byte aSide) {return mInputMax;}

    @Override public long getEnergyStored                   (TagData aEnergyType, byte aSide) {return mEnergyStored;}
    @Override public long getEnergyCapacity                 (TagData aEnergyType, byte aSide) {return mCapacity;}
    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return new ArrayListNoNulls<>(F, mEnergyType, mEnergyTypeOut);}
    @Override public Collection<TagData> getEnergyCapacitorTypes(byte aSide) {return mEnergyType.AS_LIST;}

    @Override public long getProgressValue(byte aSide) {return mEnergyStored;}
    @Override public long getProgressMax(byte aSide) {return mCapacity;}

    @Override public int getInventoryStackLimit() {return 1;}
    @Override public int getMinimumInventorySize() {return 1;}
    @Override public boolean canDrop(int aInventorySlot) {return T;}

    @Override public boolean getStateRunningPossible() {return mEnergyStored > mOutput;}
    @Override public boolean getStateRunningPassively() {return mActive;}
    @Override public boolean getStateRunningActively() {return mActive;}
    @Override public boolean setStateOnOff(boolean aOnOff) {mForceStopped = !aOnOff; return !mForceStopped;}
    @Override public boolean getStateOnOff() {return !mForceStopped;}

    @Override public byte setStateMode(byte aMode) {mMode = aMode; return mMode;}
    @Override public byte getStateMode() {return mMode;}

    public boolean isInput (byte aSide) {return aSide != mFacing;}
    public boolean isOutput(byte aSide) {return aSide == mFacing;}
}
