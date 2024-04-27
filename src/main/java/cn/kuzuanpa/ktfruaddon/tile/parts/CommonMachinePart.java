/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.parts;

import gregapi.GT_API;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.code.TagData;
import gregapi.data.FL;
import gregapi.fluid.FluidTankGT;
import gregapi.old.Textures;
import gregapi.render.IIconContainer;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.util.ST;
import gregapi.util.UT;
import gregapi.util.WD;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

import static gregapi.data.CS.*;

public class CommonMachinePart extends MultiTileEntityBasicMachine implements IMultiTileEntity.IMTE_BreakBlock {
    public ChunkCoordinates mTargetPos = null;
    public ITileEntityMultiBlockController mTarget = null;

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        mGUITexture = mRecipes.mGUIPath;
        mEnergy = aNBT.getLong(NBT_ENERGY);
        if (aNBT.hasKey(NBT_ACTIVE)) mCouldUseRecipe = mActive = aNBT.getBoolean(NBT_ACTIVE);
        if (aNBT.hasKey(NBT_STOPPED)) mStopped = aNBT.getBoolean(NBT_STOPPED);
        if (aNBT.hasKey(NBT_RUNNING)) mRunning = aNBT.getBoolean(NBT_RUNNING);
        if (aNBT.hasKey(NBT_STATE+".new")) mStateNew = aNBT.getBoolean(NBT_STATE+".new");
        if (aNBT.hasKey(NBT_STATE+".old")) mStateOld = aNBT.getBoolean(NBT_STATE+".old");
        if (aNBT.hasKey(NBT_NEEDS_IGNITION)) mRequiresIgnition = aNBT.getBoolean(NBT_NEEDS_IGNITION);
        if (aNBT.hasKey(NBT_CHEAP_OVERCLOCKING)) mCheapOverclocking = aNBT.getBoolean(NBT_CHEAP_OVERCLOCKING);
        if (aNBT.hasKey(NBT_NO_CONSTANT_POWER)) mNoConstantEnergy = aNBT.getBoolean(NBT_NO_CONSTANT_POWER);
        if (aNBT.hasKey(NBT_SPECIAL_IS_START_ENERGY)) mSpecialIsStartEnergy = aNBT.getBoolean(NBT_SPECIAL_IS_START_ENERGY);
        if (aNBT.hasKey(NBT_EFFICIENCY)) mEfficiency = (short)UT.Code.bind_(0, 10000, aNBT.getShort(NBT_EFFICIENCY));
        if (aNBT.hasKey(NBT_INPUT)) {mInput = aNBT.getLong(NBT_INPUT); mInputMin = mInput / 2; mInputMax = mInput * 2;}
        if (aNBT.hasKey(NBT_INPUT_MIN)) {mInputMin = aNBT.getLong(NBT_INPUT_MIN);}
        if (aNBT.hasKey(NBT_INPUT_MAX)) {mInputMax = aNBT.getLong(NBT_INPUT_MAX);}
        if (aNBT.hasKey(NBT_MINENERGY)) {mMinEnergy = aNBT.getLong(NBT_MINENERGY);}
        if (aNBT.hasKey(NBT_PARALLEL)) {mParallel = Math.max(1, aNBT.getInteger(NBT_PARALLEL));}
        if (aNBT.hasKey(NBT_PARALLEL_DURATION)) mParallelDuration = aNBT.getBoolean(NBT_PARALLEL_DURATION);
        if (aNBT.hasKey(NBT_USE_OUTPUT_TANK)) mCanUseOutputTanks = aNBT.getBoolean(NBT_USE_OUTPUT_TANK);
        if (aNBT.hasKey(NBT_PROGRESS)) {mProgress = aNBT.getLong(NBT_PROGRESS);}
        if (aNBT.hasKey(NBT_MAXPROGRESS)) {mMaxProgress = aNBT.getLong(NBT_MAXPROGRESS);}
        if (aNBT.hasKey(NBT_MODE)) mMode = aNBT.getByte(NBT_MODE);
        if (aNBT.hasKey(NBT_IGNITION)) mIgnited = aNBT.getByte(NBT_IGNITION);
        if (aNBT.hasKey(NBT_INV_SIDE_IN)) mItemInputs = (byte)(aNBT.getByte(NBT_INV_SIDE_IN) | SBIT_A);
        if (aNBT.hasKey(NBT_INV_SIDE_OUT)) mItemOutputs = (byte)(aNBT.getByte(NBT_INV_SIDE_OUT) | SBIT_A);
        if (aNBT.hasKey(NBT_INV_SIDE_AUTO_IN)) mItemAutoInput = aNBT.getByte(NBT_INV_SIDE_AUTO_IN);
        if (aNBT.hasKey(NBT_INV_SIDE_AUTO_OUT)) mItemAutoOutput = aNBT.getByte(NBT_INV_SIDE_AUTO_OUT);
        if (aNBT.hasKey(NBT_INV_DISABLED_IN)) mDisabledItemInput = aNBT.getBoolean(NBT_INV_DISABLED_IN);
        if (aNBT.hasKey(NBT_INV_DISABLED_OUT)) mDisabledItemOutput = aNBT.getBoolean(NBT_INV_DISABLED_OUT);
        if (aNBT.hasKey(NBT_TANK_SIDE_IN)) mFluidInputs = (byte)(aNBT.getByte(NBT_TANK_SIDE_IN) | SBIT_A);
        if (aNBT.hasKey(NBT_TANK_SIDE_OUT)) mFluidOutputs = (byte)(aNBT.getByte(NBT_TANK_SIDE_OUT) | SBIT_A);
        if (aNBT.hasKey(NBT_TANK_SIDE_AUTO_IN)) mFluidAutoInput = aNBT.getByte(NBT_TANK_SIDE_AUTO_IN);
        if (aNBT.hasKey(NBT_TANK_SIDE_AUTO_OUT)) mFluidAutoOutput = aNBT.getByte(NBT_TANK_SIDE_AUTO_OUT);
        if (aNBT.hasKey(NBT_TANK_DISABLED_IN)) mDisabledFluidInput = aNBT.getBoolean(NBT_TANK_DISABLED_IN);
        if (aNBT.hasKey(NBT_TANK_DISABLED_OUT)) mDisabledFluidOutput = aNBT.getBoolean(NBT_TANK_DISABLED_OUT);
        if (aNBT.hasKey(NBT_ENERGY_ACCEPTED)) mEnergyTypeAccepted = TagData.createTagData(aNBT.getString(NBT_ENERGY_ACCEPTED));
        if (aNBT.hasKey(NBT_ENERGY_ACCEPTED_2)) mEnergyTypeCharged = TagData.createTagData(aNBT.getString(NBT_ENERGY_ACCEPTED_2));
        if (aNBT.hasKey(NBT_ENERGY_ACCEPTED_SIDES)) mEnergyInputs = (byte)(aNBT.getByte(NBT_ENERGY_ACCEPTED_SIDES) | SBIT_A);
        if (aNBT.hasKey(NBT_ENERGY_EMITTED)) mEnergyTypeEmitted = TagData.createTagData(aNBT.getString(NBT_ENERGY_EMITTED));
        if (aNBT.hasKey(NBT_ENERGY_EMITTED_SIDES)) mEnergyOutput = aNBT.getByte(NBT_ENERGY_EMITTED_SIDES);
        if (aNBT.hasKey(NBT_OUTPUT)) mOutputEnergy = aNBT.getLong(NBT_OUTPUT);
        if (aNBT.hasKey(NBT_INPUT_EU)) mChargeRequirement = aNBT.getLong(NBT_INPUT_EU);
        if (aNBT.hasKey(NBT_TARGET)) {mTargetPos = new ChunkCoordinates(UT.Code.bindInt(aNBT.getLong(NBT_TARGET_X)), UT.Code.bindInt(aNBT.getLong(NBT_TARGET_Y)), UT.Code.bindInt(aNBT.getLong(NBT_TARGET_Z)));}
        if (aNBT.hasKey(NBT_DESIGN)) mDesign = UT.Code.unsignB(aNBT.getByte(NBT_DESIGN));

        long tCapacity = 1000;
        if (aNBT.hasKey(NBT_TANK_CAPACITY)) tCapacity = UT.Code.bindInt(aNBT.getLong(NBT_TANK_CAPACITY));
        mTanksInput = new FluidTankGT[mRecipes.mInputFluidCount];
        for (int i = 0; i < mTanksInput.length; i++) mTanksInput[i] = new FluidTankGT(tCapacity).setCapacity(mRecipes, mParallel * 2L).readFromNBT(aNBT, NBT_TANK+".in."+i);
        mTanksOutput = new FluidTankGT[mRecipes.mOutputFluidCount];
        for (int i = 0; i < mTanksOutput.length; i++) mTanksOutput[i] = new FluidTankGT().readFromNBT(aNBT, NBT_TANK+".out."+i);

        mOutputFluids = new FluidStack[mRecipes.mOutputFluidCount];
        for (int i = 0; i < mOutputFluids.length; i++) mOutputFluids[i] = FL.load(aNBT, NBT_TANK_OUT+"."+i);
        mOutputItems = new ItemStack[mRecipes.mOutputItemsCount];
        for (int i = 0; i < mOutputItems.length; i++) mOutputItems[i] = ST.load(aNBT, NBT_INV_OUT+"."+i);

        if (CODE_CLIENT) {
            if (aNBT.hasKey(NBT_GUI)) {
                mGUITexture = aNBT.getString(NBT_GUI);
                if (!mGUITexture.endsWith(".png")) mGUITexture += ".png";
            }
            if (GT_API.sBlockIcons == null && aNBT.hasKey(NBT_TEXTURE)) {
                String tTextureName = aNBT.getString(NBT_TEXTURE);
                mTexturesMaterial = new IIconContainer[] {
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/colored/bottom"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/colored/top"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/colored/left"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/colored/front"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/colored/right"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/colored/back")};
                mTexturesInactive = new IIconContainer[] {
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/overlay/bottom"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/overlay/top"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/overlay/left"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/overlay/front"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/overlay/right"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/overlay/back")};
                mTexturesActive = new IIconContainer[] {
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/overlay_active/bottom"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/overlay_active/top"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/overlay_active/left"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/overlay_active/front"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/overlay_active/right"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/overlay_active/back")};
                mTexturesRunning = new IIconContainer[] {
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/overlay_running/bottom"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/overlay_running/top"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/overlay_running/left"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/overlay_running/front"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/overlay_running/right"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+mDesign+"/overlay_running/back")};
            } else {
                TileEntity tCanonicalTileEntity = MultiTileEntityRegistry.getCanonicalTileEntity(getMultiTileEntityRegistryID(), getMultiTileEntityID());
                if (tCanonicalTileEntity instanceof MultiTileEntityBasicMachine) {
                    mTexturesMaterial = ((MultiTileEntityBasicMachine)tCanonicalTileEntity).mTexturesMaterial;
                    mTexturesInactive = ((MultiTileEntityBasicMachine)tCanonicalTileEntity).mTexturesInactive;
                    mTexturesRunning  = ((MultiTileEntityBasicMachine)tCanonicalTileEntity).mTexturesRunning;
                    mTexturesActive   = ((MultiTileEntityBasicMachine)tCanonicalTileEntity).mTexturesActive;
                } else {
                    mTexturesMaterial = mTexturesInactive = mTexturesRunning = mTexturesActive = L6_IICONCONTAINER;
                }
            }
        }

        updateAccessibleSlots();
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergy);
        UT.NBT.setNumber(aNBT, NBT_MINENERGY, mMinEnergy);
        UT.NBT.setNumber(aNBT, NBT_PROGRESS, mProgress);
        UT.NBT.setNumber(aNBT, NBT_MAXPROGRESS, mMaxProgress);
        UT.NBT.setNumber(aNBT, NBT_OUTPUT, mOutputEnergy);
        UT.NBT.setNumber(aNBT, NBT_INPUT_EU, mChargeRequirement);

        UT.NBT.setBoolean(aNBT, NBT_ACTIVE, mActive);
        UT.NBT.setBoolean(aNBT, NBT_RUNNING, mRunning);
        UT.NBT.setBoolean(aNBT, NBT_STOPPED, mStopped);
        UT.NBT.setBoolean(aNBT, NBT_STATE+".new", mStateNew);
        UT.NBT.setBoolean(aNBT, NBT_STATE+".old", mStateOld);

        UT.NBT.setNumber(aNBT, NBT_MODE, mMode);
        UT.NBT.setNumber(aNBT, NBT_IGNITION, mIgnited);
        UT.NBT.setBoolean(aNBT, NBT_INV_DISABLED_IN, mDisabledItemInput);
        UT.NBT.setBoolean(aNBT, NBT_INV_DISABLED_OUT, mDisabledItemOutput);
        UT.NBT.setBoolean(aNBT, NBT_TANK_DISABLED_IN, mDisabledFluidInput);
        UT.NBT.setBoolean(aNBT, NBT_TANK_DISABLED_OUT, mDisabledFluidOutput);

        for (int i = 0; i < mTanksInput  .length; i++) mTanksInput [i].writeToNBT(aNBT, NBT_TANK+".in." +i);
        for (int i = 0; i < mTanksOutput .length; i++) mTanksOutput[i].writeToNBT(aNBT, NBT_TANK+".out."+i);
        for (int i = 0; i < mOutputFluids.length; i++) FL.save(aNBT, NBT_TANK_OUT+"."+i, mOutputFluids[i]);
        for (int i = 0; i < mOutputItems .length; i++) ST.save(aNBT, NBT_INV_OUT +"."+i, mOutputItems [i]);
        if (mDesign != 0) aNBT.setByte(NBT_DESIGN, (byte)mDesign);
        if (this.mTargetPos != null) {
            UT.NBT.setBoolean(aNBT, "gt.target", true);
            UT.NBT.setNumber(aNBT, "gt.target.x", (long)this.mTargetPos.posX);
            UT.NBT.setNumber(aNBT, "gt.target.y", (long)this.mTargetPos.posY);
            UT.NBT.setNumber(aNBT, "gt.target.z", (long)this.mTargetPos.posZ);
        }
    }

    public int mDesign = 0;

    public ITileEntityMultiBlockController getTarget(boolean aCheckValidity) {
        if (this.mTargetPos == null) {
            return null;
        } else {
            if (this.mTarget == null || this.mTarget.isDead()) {
                this.mTarget = null;
                if (this.worldObj.blockExists(this.mTargetPos.posX, this.mTargetPos.posY, this.mTargetPos.posZ)) {
                    TileEntity tTarget = WD.te(this.worldObj, this.mTargetPos, true);
                    if (tTarget instanceof ITileEntityMultiBlockController && ((ITileEntityMultiBlockController)tTarget).isInsideStructure(this.xCoord, this.yCoord, this.zCoord)) {
                        this.mTarget = (ITileEntityMultiBlockController)tTarget;
                    } else {
                        this.mTargetPos = null;
                    }
                }
            }

            return aCheckValidity && this.mTarget != null && !this.mTarget.checkStructure(false) ? null : this.mTarget;
        }
    }

    public void setTarget(ITileEntityMultiBlockController aTarget, int aDesign, int aMode) {
        this.mTarget = aTarget;
        this.mTargetPos = this.mTarget == null ? null : this.mTarget.getCoords();
        this.mDesign = aDesign;
    }
    public void setDesign(int aDesign) {
        this.mDesign = aDesign;
    }
    public boolean breakBlock() {
        ITileEntityMultiBlockController tTarget = this.getTarget(false);
        if (tTarget != null) {
            this.mTargetPos = null;
            this.mTarget = null;
            tTarget.onStructureChange();
        }
        return false;
    }
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        super.addToolTips(aList, aStack, aF3_H);
    }
    @Override
    public int getLightOpacity(){
        return mDesign==1?255:0;
    }
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.machine.part";
    }

}
