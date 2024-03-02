/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.energy.generator;

import cn.kuzuanpa.ktfruaddon.tile.util.kTileNBT;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.behavior.TE_Behavior_Active_Trinary;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.machines.ITileEntityAdjacentOnOff;
import gregapi.tileentity.machines.ITileEntityRunningActively;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.*;

public class ManualGeneratorRU extends TileEntityBase09FacingSingle implements ITileEntityEnergy, ITileEntityRunningActively, ITileEntityAdjacentOnOff {
    public boolean mStopped = F;
    public byte vProgressLevel;
    public long mEnergy = 0, mOutputMin = 16, mOutputMax = 64,timeRemaining=0,timeGainRate=8,maxTime=800;
    public static TagData mEnergyTypeEmitted = TD.Energy.RU;
    public TE_Behavior_Active_Trinary mActivity = null;

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        mEnergy = aNBT.getLong(NBT_ENERGY);
        mActivity = new TE_Behavior_Active_Trinary(this, aNBT);
        if (aNBT.hasKey(NBT_STOPPED)) mStopped = aNBT.getBoolean(NBT_STOPPED);
        if (aNBT.hasKey(NBT_OUTPUT_MIN)) mOutputMin = aNBT.getLong(NBT_OUTPUT_MIN);
        if (aNBT.hasKey(NBT_OUTPUT_MAX)) mOutputMax = aNBT.getLong(NBT_OUTPUT_MAX);
        if (aNBT.hasKey(NBT_ENERGY_EMITTED)) mEnergyTypeEmitted = TagData.createTagData(aNBT.getString(NBT_ENERGY_EMITTED));
        if (aNBT.hasKey(kTileNBT.Generator.MAX_TIME)) maxTime = aNBT.getLong(kTileNBT.Generator.MAX_TIME);
        if (aNBT.hasKey(kTileNBT.Generator.TIME_GAIN_RATE)) timeGainRate = aNBT.getLong(kTileNBT.Generator.TIME_GAIN_RATE);
        if (aNBT.hasKey(kTileNBT.Generator.TIME_REMAINING)) timeRemaining = aNBT.getLong(kTileNBT.Generator.TIME_REMAINING);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergy);
        UT.NBT.setBoolean(aNBT, NBT_STOPPED, mStopped);
        UT.NBT.setNumber(aNBT,NBT_OUTPUT_MIN, mOutputMin);
        UT.NBT.setNumber(aNBT,NBT_OUTPUT_MAX, mOutputMax);
        UT.NBT.setNumber(aNBT,kTileNBT.Generator.TIME_REMAINING,timeRemaining);
        mActivity.save(aNBT);
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        LH.addEnergyToolTips(this, aList, null, mEnergyTypeEmitted, null, LH.get(LH.FACE_FRONT));
        aList.add(LH.Chat.DGRAY + LH.get(LH.TOOL_TO_DETAIL_MAGNIFYINGGLASS));
        super.addToolTips(aList, aStack, aF3_H);
    }

    public byte getProgressLevel() {
        if(isServerSide()) {
            float temp = ((float) timeRemaining / (float) maxTime);
            return (byte) (temp > 0.8 ? 5 : temp > 0.6 ? 4 : temp > 0.4 ? 3 : temp > 0.2 ? 2 : 1);
        }
        return 0;
    }

    public long getRate(){
        byte temp = getProgressLevel();
        long outputRange = mOutputMax - mOutputMin;
        switch (temp){
            case 5 :return mOutputMax;
            case 4 :return (long) (mOutputMax -outputRange*0.25);
            case 3 :return (long) (mOutputMax -outputRange*0.5);
            case 2 :return (long) (mOutputMax -outputRange*0.75);
            case 1 :return mOutputMin;
        }
        return 0;
    }
    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if (aIsServerSide) {
            if (mEnergy >= getRate()) {
                ITileEntityEnergy.Util.emitEnergyToNetwork(mEnergyTypeEmitted, getRate(), 1, this);
                mEnergy -= getRate();
            }
            if (timeRemaining>0) {
                mActivity.mActive = T;
                mEnergy+=getRate();
                timeRemaining--;
            }else mActivity.mActive = F;
            if (mEnergy < 0) mEnergy = 0;
        }
    }
    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (isServerSide()&&aPlayer.isEntityAlive()) {
            timeRemaining+=timeGainRate;
        }
        return T;
    }
    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        long rReturn = super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
        if (rReturn > 0) return rReturn;
return 0;
    }

    @Override
    public boolean onTickCheck(long aTimer) {
        if(vProgressLevel!=getProgressLevel())updateClientData();
        return mActivity.check(mStopped) || super.onTickCheck(aTimer);
    }

    @Override
    public void setVisualData(byte aData) {
        mActivity.mState = (byte) (aData & 127);
    }

    @Override
    public byte getVisualData() {
        return mActivity.mState;
    }

    @Override
    public byte getDefaultSide() {
        return SIDE_FRONT;
    }

    @Override
    public boolean[] getValidSides() {
        return SIDES_VALID;
    }

    @Override
    protected IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {return null;
    }

    @Override
    protected IFluidTank getFluidTankDrainable2(byte aSide, FluidStack aFluidToDrain) {
        return null;
    }

    @Override
    protected IFluidTank[] getFluidTanks2(byte aSide) {
        return null;
    }

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (!aShouldSideBeRendered[aSide]) return null;
        if (aSide == mFacing)
            return BlockTextureMulti.get(BlockTextureDefault.get(sColoreds[0], mRGBa), BlockTextureDefault.get(mActivity.mState > 0 ? (vProgressLevel==5?sOverlaysActiveFrontSpeed[0]:vProgressLevel==4?sOverlaysActiveFrontSpeed[1]:vProgressLevel==3?sOverlaysActiveFrontSpeed[2]:vProgressLevel==2?sOverlaysActiveFrontSpeed[3]:sOverlaysActiveFrontSpeed[4]) : sOverlays[0]));
        if (aSide == OPOS[mFacing])
            return BlockTextureMulti.get(BlockTextureDefault.get(sColoreds[1], mRGBa), BlockTextureDefault.get((mActivity.mState > 0 ? sOverlaysActive : sOverlays)[1]));
        return BlockTextureMulti.get(BlockTextureDefault.get(sColoreds[2], mRGBa), BlockTextureDefault.get(mActivity.mState > 0 ? (vProgressLevel==5?sOverlaysActiveSidesSpeed[0]:vProgressLevel==4?sOverlaysActiveSidesSpeed[1]:vProgressLevel==3?sOverlaysActiveSidesSpeed[2]:vProgressLevel==2?sOverlaysActiveSidesSpeed[3]:sOverlaysActiveSidesSpeed[4]) : sOverlays[2]));
    }

    @Override
    public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {
        return ZL_IS;
    }

    @Override
    public boolean canDrop(int aInventorySlot) {
        return T;
    }

    @Override
    public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {
        return aEmitting && aEnergyType == mEnergyTypeEmitted;
    }

    @Override
    public boolean isEnergyEmittingTo(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return aSide == mFacing && super.isEnergyEmittingTo(aEnergyType, aSide, aTheoretical);
    }

    @Override
    public long getEnergyOffered(TagData aEnergyType, byte aSide, long aSize) {
        return Math.min(getRate(), mEnergy);
    }

    @Override
    public long getEnergySizeOutputRecommended(TagData aEnergyType, byte aSide) {
        return mOutputMin;
    }

    @Override
    public long getEnergySizeOutputMin(TagData aEnergyType, byte aSide) {
        return mOutputMin;
    }

    @Override
    public long getEnergySizeOutputMax(TagData aEnergyType, byte aSide) {
        return mOutputMax;
    }

    @Override
    public Collection<TagData> getEnergyTypes(byte aSide) {
        return mEnergyTypeEmitted.AS_LIST;
    }

    @Override
    public boolean getStateRunningPassively() {
        return mActivity.mActive;
    }

    @Override
    public boolean getStateRunningPossible() {
        return mActivity.mActive;
    }

    @Override
    public boolean getStateRunningActively() {
        return mActivity.mActive;
    }

    @Override
    public boolean setAdjacentOnOff(boolean aOnOff) {
        mStopped = !aOnOff;
        return !mStopped;
    }

    @Override
    public boolean setStateOnOff(boolean aOnOff) {
        mStopped = !aOnOff;
        return !mStopped;
    }

    @Override
    public boolean getStateOnOff() {
        return !mStopped;
    }

    // Icons
    public static IIconContainer[] sColoreds = new IIconContainer[]{
            new Textures.BlockIcons.CustomIcon("machines/generators/motor_manual/RU/colored/front"),
            new Textures.BlockIcons.CustomIcon("machines/generators/motor_manual/RU/colored/back"),
            new Textures.BlockIcons.CustomIcon("machines/generators/motor_manual/RU/colored/sides"),
    }, sOverlays = new IIconContainer[]{
            new Textures.BlockIcons.CustomIcon("machines/generators/motor_manual/RU/overlay/front"),
            new Textures.BlockIcons.CustomIcon("machines/generators/motor_manual/RU/overlay/back"),
            new Textures.BlockIcons.CustomIcon("machines/generators/motor_manual/RU/overlay/sides"),
    }, sOverlaysActive = new IIconContainer[]{
            new Textures.BlockIcons.CustomIcon("machines/generators/motor_manual/RU/overlay_active/back"),
            new Textures.BlockIcons.CustomIcon("machines/generators/motor_manual/RU/overlay_active/back")
    },
            sOverlaysActiveFrontSpeed =new IIconContainer[]{
                    new Textures.BlockIcons.CustomIcon("machines/generators/motor_manual/RU/overlay_active_front/full"),
                    new Textures.BlockIcons.CustomIcon("machines/generators/motor_manual/RU/overlay_active_front/fast"),
                    new Textures.BlockIcons.CustomIcon("machines/generators/motor_manual/RU/overlay_active_front/medium"),
                    new Textures.BlockIcons.CustomIcon("machines/generators/motor_manual/RU/overlay_active_front/slow"),
                    new Textures.BlockIcons.CustomIcon("machines/generators/motor_manual/RU/overlay_active_front/least"),
            },   sOverlaysActiveSidesSpeed =new IIconContainer[]{
                new Textures.BlockIcons.CustomIcon("machines/generators/motor_manual/RU/overlay_active_sides/full"),
                new Textures.BlockIcons.CustomIcon("machines/generators/motor_manual/RU/overlay_active_sides/fast"),
                new Textures.BlockIcons.CustomIcon("machines/generators/motor_manual/RU/overlay_active_sides/medium"),
                new Textures.BlockIcons.CustomIcon("machines/generators/motor_manual/RU/overlay_active_sides/slow"),
                new Textures.BlockIcons.CustomIcon("machines/generators/motor_manual/RU/overlay_active_sides/least"),
    };
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.generator.manual.ru";
    }
    @Override    public IPacket getClientDataPacket(boolean aSendAll) {
        return getClientDataPacketByteArray(aSendAll, (byte)UT.Code.getR(mRGBa), (byte)UT.Code.getG(mRGBa), (byte)UT.Code.getB(mRGBa),getDirectionData(),mActivity.mState,getProgressLevel());
    }

    @Override
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler) {
        mRGBa = UT.Code.getRGBInt(new short[] {UT.Code.unsignB(aData[0]), UT.Code.unsignB(aData[1]), UT.Code.unsignB(aData[2])});
        setDirectionData(aData[3]);
        mActivity.mState=aData[4];
        vProgressLevel=aData[5];
        return T;
    }
}