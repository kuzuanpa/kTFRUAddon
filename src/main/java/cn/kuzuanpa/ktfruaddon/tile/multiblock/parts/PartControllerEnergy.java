/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.multiblock.parts;

import gregapi.GT_API;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.*;

public class PartControllerEnergy extends TileEntityBase09FacingSingle implements ITileEntityEnergy, IMachineRunController, IMultiBlockPart{
    boolean isEnergyEnough;
    long mInputMin=-1,mInputMax=-1,mEnergy=0;
    TagData mEnergyTypeAccepted;
    public byte mEnergyInputs = 127;
    public IIconContainer[] mTexturesMaterial = L6_IICONCONTAINER, mTexturesInactive = L6_IICONCONTAINER, mTexturesActive = L6_IICONCONTAINER, mTexturesActiveGlow = L6_IICONCONTAINER;

    public ChunkCoordinates mTargetPos = null;
    public ITileEntityMultiBlockController mTarget = null;
    public int mDesign = 0;

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        String tSideNames = "";
        if (mEnergyTypeAccepted != TD.Energy.TU) {
            if (mEnergyInputs != 127) {
                for (byte tSide : ALL_SIDES_VALID) if (FACE_CONNECTED[tSide][mEnergyInputs]) {tSideNames += (UT.Code.stringValid(tSideNames)?", ":"")+LH.get(LH.FACES[tSide]);}
            }
            LH.addEnergyToolTips(this, aList, mEnergyTypeAccepted, null, tSideNames, null);
        }
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        aSize = Math.abs(aSize);
        if (aSize > getEnergySizeInputMax(aEnergyType, aSide)) {
            if (aDoInject) overcharge(aSize, aEnergyType);
            return aAmount;
        }
        if (aEnergyType == mEnergyTypeAccepted) {
            long tInput = Math.min(mInputMax - mEnergy, aSize * aAmount), tConsumed = Math.min(aAmount, (tInput/aSize) + (tInput%aSize!=0?1:0));
            if (aDoInject) mEnergy += tConsumed * aSize;
            return tConsumed;
        }
        return 0;
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        mEnergy = aNBT.getLong(NBT_ENERGY);

        if (aNBT.hasKey(NBT_TARGET)) mTargetPos = IMultiBlockPart.readTargetPosFromNBT(aNBT);
        if (aNBT.hasKey(NBT_DESIGN)) mDesign = UT.Code.unsignB(aNBT.getByte(NBT_DESIGN));

        if (aNBT.hasKey(NBT_INPUT)) {mInputMin = aNBT.getLong(NBT_INPUT);}
        if (aNBT.hasKey(NBT_INPUT_MIN)) {mInputMin = aNBT.getLong(NBT_INPUT_MIN);}
        if (aNBT.hasKey(NBT_INPUT_MAX)) {mInputMax = aNBT.getLong(NBT_INPUT_MAX);}
        if (aNBT.hasKey(NBT_ENERGY_ACCEPTED)) mEnergyTypeAccepted = TagData.createTagData(aNBT.getString(NBT_ENERGY_ACCEPTED));
        if (aNBT.hasKey(NBT_ENERGY_ACCEPTED_SIDES)) mEnergyInputs = (byte)(aNBT.getByte(NBT_ENERGY_ACCEPTED_SIDES) | SBIT_A);

        if (CODE_CLIENT) {
            if (GT_API.sBlockIcons == null && aNBT.hasKey(NBT_TEXTURE)) {
                String tTextureName = aNBT.getString(NBT_TEXTURE);
                mTexturesMaterial = new IIconContainer[] {
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/colored/bottom"),
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/colored/top"),
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/colored/left"),
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/colored/front"),
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/colored/right"),
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/colored/back")};
                mTexturesInactive = new IIconContainer[] {
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/overlay/bottom"),
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/overlay/top"),
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/overlay/left"),
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/overlay/front"),
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/overlay/right"),
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/overlay/back")};
                mTexturesActive = new IIconContainer[] {
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/overlay_active/bottom"),
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/overlay_active/top"),
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/overlay_active/left"),
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/overlay_active/front"),
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/overlay_active/right"),
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/overlay_active/back")};
                mTexturesActiveGlow = new IIconContainer[] {
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/overlay_active_glowing/bottom"),
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/overlay_active_glowing/top"),
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/overlay_active_glowing/left"),
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/overlay_active_glowing/front"),
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/overlay_active_glowing/right"),
                        new Textures.BlockIcons.CustomIcon("machines/basicmachines/"+tTextureName+"/overlay_active_glowing/back")};
            } else {
                TileEntity tCanonicalTileEntity = MultiTileEntityRegistry.getCanonicalTileEntity(getMultiTileEntityRegistryID(), getMultiTileEntityID());
                if (tCanonicalTileEntity instanceof MultiTileEntityBasicMachine) {
                    mTexturesMaterial = ((MultiTileEntityBasicMachine) tCanonicalTileEntity).mTexturesMaterial;
                    mTexturesInactive = ((MultiTileEntityBasicMachine) tCanonicalTileEntity).mTexturesInactive;
                    mTexturesActive = ((MultiTileEntityBasicMachine) tCanonicalTileEntity).mTexturesActive;

                    mTexturesActiveGlow = ((MultiTileEntityBasicMachine) tCanonicalTileEntity).mTexturesActiveGlow;

                } else {
                    mTexturesMaterial = mTexturesInactive = mTexturesActive = mTexturesActiveGlow = L6_IICONCONTAINER;
                }
            }
        }
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        IMultiBlockPart.writeToNBT(aNBT,mTargetPos,mDesign);
    }

    @Override
    public boolean canDrop(int aSlot) {
        return false;
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if (!aIsServerSide || mEnergy < mInputMin) {
            isEnergyEnough=false;
            return;
        }
        mEnergy-=mInputMin;
        isEnergyEnough=true;
    }

    @Override public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {return aShouldSideBeRendered[aSide] ? BlockTextureMulti.get(BlockTextureDefault.get(mTexturesMaterial[FACING_ROTATIONS[mFacing][aSide]], mRGBa), BlockTextureDefault.get( (isEnergyEnough||worldObj==null?mTexturesActive:mTexturesInactive) [FACING_ROTATIONS[mFacing][aSide]]), (isEnergyEnough) ? BlockTextureDefault.get( mTexturesActiveGlow[FACING_ROTATIONS[mFacing][aSide]],true) : null ) : null;}

    @Override
    public boolean canMachineRun() {
        return isEnergyEnough;
    }
    public boolean isEnergyAcceptingFrom(TagData aEnergyType, byte aSide, boolean aTheoretical) {return aEnergyType.equals(mEnergyTypeAccepted);}
    @Override public long getEnergySizeInputMin             (TagData aEnergyType, byte aSide) {return mInputMin;}
    @Override public long getEnergySizeInputRecommended     (TagData aEnergyType, byte aSide) {return mInputMin;}
    @Override public long getEnergySizeInputMax             (TagData aEnergyType, byte aSide) {return mInputMax;}
    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return mEnergyTypeAccepted.AS_LIST;}

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.part.controller.energy";
    }

    @Override public ITileEntityMultiBlockController getTarget2() {return mTarget;}
    @Override public void setTarget(ITileEntityMultiBlockController target) {mTarget = target;}
    @Override public ChunkCoordinates getTargetPos() {return mTargetPos;}
    @Override public void setTargetPos(ChunkCoordinates aCoords){mTargetPos=aCoords;}
    @Override public void setDesign(int aDesign) {this.mDesign = aDesign;}
    @Override public int getDesign(){return mDesign;}
    @Override
    public boolean breakBlock() {
        notifyTarget();
        return super.breakBlock();
    }
}
