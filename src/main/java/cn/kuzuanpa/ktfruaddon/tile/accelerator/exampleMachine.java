/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.accelerator;

import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.render.ITexture;
import gregapi.tileentity.ITileEntityFunnelAccessible;
import gregapi.tileentity.ITileEntityTapAccessible;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.behavior.TE_Behavior_Active_Trinary;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.machines.ITileEntityAdjacentOnOff;
import gregapi.tileentity.machines.ITileEntityRunningActively;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.*;

public class exampleMachine extends TileEntityBase09FacingSingle implements IFluidHandler, ITileEntityTapAccessible, ITileEntityFunnelAccessible, ITileEntityEnergy, ITileEntityRunningActively, ITileEntityAdjacentOnOff, IMultiTileEntity.IMTE_SyncDataByteArray {

    public boolean mStopped = F;
    public byte mItemAutoInputA,mItemAutoInputB,mItemAutoOutput;
    public long mEnergy = 0, mInput = 32, mInputMin = 16, mInputMax = 64;
    public static TagData mEnergyTypeAccepted = TD.Energy.KU;
    public TE_Behavior_Active_Trinary mActivity = null;

    @Override
    public ITexture getTexture2(Block block, int i, byte b, boolean[] booleans) {
        return null;
    }
    //NBT
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        mEnergy = aNBT.getLong(NBT_ENERGY);
        mActivity = new TE_Behavior_Active_Trinary(this, aNBT);
        if (aNBT.hasKey(NBT_STOPPED)) mStopped = aNBT.getBoolean(NBT_STOPPED);
        if (aNBT.hasKey(NBT_INPUT)) mInput = aNBT.getLong(NBT_INPUT);
        if (aNBT.hasKey(NBT_INPUT_MIN)) mInputMin = aNBT.getLong(NBT_INPUT_MIN);
        if (aNBT.hasKey(NBT_INPUT_MAX)) mInputMax = aNBT.getLong(NBT_INPUT_MAX);
        if (aNBT.hasKey(NBT_INV_SIDE_AUTO_IN)) mItemAutoInputA = aNBT.getByte(NBT_INV_SIDE_AUTO_IN);
        //Use tank auto input as inv input side B
        if (aNBT.hasKey(NBT_TANK_SIDE_AUTO_IN)) mItemAutoInputB = aNBT.getByte(NBT_TANK_SIDE_AUTO_IN);
        if (aNBT.hasKey(NBT_INV_SIDE_AUTO_OUT)) mItemAutoOutput = aNBT.getByte(NBT_INV_SIDE_AUTO_OUT);
        if (aNBT.hasKey(NBT_ENERGY_EMITTED)) mEnergyTypeAccepted = TagData.createTagData(aNBT.getString(NBT_ENERGY_EMITTED));
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergy);
        UT.NBT.setBoolean(aNBT, NBT_STOPPED, mStopped);
        mActivity.save(aNBT);
    }
    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.ORANGE   + LH.get(LH.NO_GUI_FUNNEL_TAP_TO_TANK));
        super.addToolTips(aList, aStack, aF3_H);
    }
    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if (aIsServerSide) {
            //AutoInput
            doInputItems();
            doOutputItems();
        }
    }

    public DelegatorTileEntity<IInventory> getItemInputTarget(byte aSide) {
        return getAdjacentInventory(aSide);
    }

    public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) {
        return getAdjacentTileEntity(aSide);
    }

    public void doInputItems() {
        byte tAutoInput = FACING_TO_SIDE[mFacing][mItemAutoInputA];
        if (SIDES_VALID[tAutoInput]) ST.moveAll(getItemInputTarget(tAutoInput), delegator(tAutoInput));
        byte tAutoInputB = FACING_TO_SIDE[mFacing][mItemAutoInputB];
        if (SIDES_VALID[tAutoInputB]) ST.moveAll(getItemInputTarget(tAutoInputB), delegator(tAutoInput));
    }

    public void doOutputItems() {
        byte tAutoOutput = FACING_TO_SIDE[mFacing][mItemAutoOutput];
        if (SIDES_VALID[tAutoOutput]) ST.moveAll(delegator(tAutoOutput), getItemOutputTarget(tAutoOutput));
    }
    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        long rReturn = super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
        if (rReturn > 0) return rReturn;

        if (isClientSide()) return 0;

        if (aTool.equals(TOOL_magnifyingglass)) {
            if (aChatReturn != null) {
                aChatReturn.add("INV:");
                aChatReturn.add("0: "+(slot(0)==null?"empty":slot(0).getUnlocalizedName()));
                aChatReturn.add("1: "+(slot(1)==null?"empty":slot(1).getUnlocalizedName()));
                aChatReturn.add("2: "+(slot(2)==null?"empty":slot(2).getUnlocalizedName()));
                aChatReturn.add("3: "+(slot(3)==null?"empty":slot(3).getUnlocalizedName()));
            }
            return 1;
        }
        if(aTool.equals(TOOL_monkeywrench)){
            ST.move(this,0,2);
            ST.move(this,1,3);
        }
        if (getFacingTool() != null && aTool.equals(getFacingTool())) {byte aTargetSide = UT.Code.getSideWrenching(aSide, aHitX, aHitY, aHitZ); if (getValidSides()[aTargetSide]) {byte oFacing = mFacing; mFacing = aTargetSide; updateClientData(); causeBlockUpdate(); onFacingChange(oFacing); return 10000;}}
        return 0;
    }
    public boolean onTickCheck(long aTimer) {
        return mActivity.check(mStopped) || super.onTickCheck(aTimer);
    }
    @Override public byte getDefaultSide() {return SIDE_FRONT;}
    @Override public boolean[] getValidSides() {return SIDES_HORIZONTAL;}

    //Energy
    @Override public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {return !aEmitting && aEnergyType == mEnergyTypeAccepted;}
    public long getEnergySizeInputMin(TagData aEnergyType, byte aSide) {
        return this.mInputMin;
    }
    public long getEnergySizeInputRecommended(TagData aEnergyType, byte aSide) {return this.mInput;}
    public long getEnergySizeInputMax(TagData aEnergyType, byte aSide) {return this.mInputMax;}
    @Override public Collection<TagData> getEnergyTypes(byte aSide) {
        return mEnergyTypeAccepted.AS_LIST;
    }
    @Override public boolean getStateRunningPassively() {
        return mActivity.mActive;
    }
    @Override public boolean getStateRunningPossible() {
        return mActivity.mActive;
    }
    @Override public boolean getStateRunningActively() {
        return mActivity.mActive;
    }
    @Override public boolean setAdjacentOnOff(boolean aOnOff) {mStopped = !aOnOff;return !mStopped;}
    @Override public boolean setStateOnOff(boolean aOnOff) {mStopped = !aOnOff;return !mStopped;}
    @Override public boolean getStateOnOff() {return !mStopped;}

    //Inventory
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[4];}
    @Override public boolean canDrop(int aInventorySlot) {
        return T;
    }
    private static final int[] ACCESSIBLE_SLOTS = new int[] {0, 1,2,3};
    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}
    @Override
    public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {
        if (aSlot >= 2) return F;
        for (int i = 0; i < 2; i++) if ((slot(i)==null||ST.equal(aStack, slot(i)))&&i==aSlot) return T;
        return F;
    }
    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {
        return aSlot>1&&!mActivity.mActive;
    }

    @Override public String getTileEntityName() {return "ktfru.multitileentity.accelerator.example";}

}
