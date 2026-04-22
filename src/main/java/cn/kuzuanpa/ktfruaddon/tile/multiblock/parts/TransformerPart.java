/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

package cn.kuzuanpa.ktfruaddon.tile.multiblock.parts;

import cn.kuzuanpa.ktfruaddon.api.tile.part.IMultiBlockPart;
import gregapi.GT_API;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.code.TagData;
import gregapi.data.TD;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.machines.ITileEntitySwitchableOnOff;
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

public class TransformerPart extends TileEntityBase09FacingSingle implements IMultiBlockPart, ITileEntityEnergy, ITileEntitySwitchableOnOff {
    public long mEnergy = 0, mOutputVoltage = 1, mOutputAmpere = 1;

    public TagData mEnergyType = TD.Energy.EU;
    public IIconContainer sTextureCommon, sOverlayFront;

    public boolean stopped = false;
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);

        if (aNBT.hasKey(NBT_TARGET)) mTargetPos = IMultiBlockPart.readTargetPosFromNBT(aNBT);
        if (aNBT.hasKey(NBT_DESIGN)) mDesign = UT.Code.unsignB(aNBT.getByte(NBT_DESIGN));
        if (aNBT.hasKey(NBT_OUTPUT)) mOutputVoltage = aNBT.getLong(NBT_OUTPUT);
        if (aNBT.hasKey(NBT_OUTPUT+".amp")) mOutputAmpere = aNBT.getLong(NBT_OUTPUT+".amp");
        if (aNBT.hasKey(NBT_ENERGY_EMITTED)) mEnergyType = TagData.createTagData(aNBT.getString(NBT_ENERGY_EMITTED));

        if (CODE_CLIENT) {
            if (GT_API.sBlockIcons == null && aNBT.hasKey(NBT_TEXTURE)) {
                String tTextureName = aNBT.getString(NBT_TEXTURE);
                sTextureCommon= new Textures.BlockIcons.CustomIcon("machines/multiblockparts/transformer/"+tTextureName+"/common");
                sOverlayFront = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/transformer/"+tTextureName+"/front");


            } else {
                TileEntity tCanonicalTileEntity = MultiTileEntityRegistry.getCanonicalTileEntity(getMultiTileEntityRegistryID(), getMultiTileEntityID());
                if (tCanonicalTileEntity instanceof TransformerPart) {
                    sTextureCommon = ((TransformerPart) tCanonicalTileEntity).sTextureCommon;
                    sOverlayFront = ((TransformerPart) tCanonicalTileEntity).sOverlayFront;

                } else {
                    sTextureCommon = sOverlayFront = L6_IICONCONTAINER[0];
                }
            }
        }
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (aIsServerSide && !stopped) {
            long ampere = Math.min(mOutputAmpere, mEnergy / mOutputVoltage);
            if(ampere != 0)mEnergy -= ITileEntityEnergy.Util.emitEnergyToNetwork(mEnergyType, mOutputVoltage, ampere, this) * mOutputVoltage;
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
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa), aSide==mFacing? BlockTextureDefault.get(sOverlayFront) : null);
    }

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if(stopped || aSide != SIDE_INSIDE || !aEnergyType.equals(mEnergyType))return 0;
        long ampereConsumed = (long) Math.min(aAmount, Math.ceil((mOutputAmpere * mOutputVoltage - mEnergy) * 1F / aSize));
        mEnergy += ampereConsumed * aSize;
        return ampereConsumed;
    }
    @Override public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {return aEnergyType == mEnergyType;}

    @Override public boolean isEnergyAcceptingFrom(TagData aEnergyType, byte aSide, boolean aTheoretical) {return aSide == SIDE_INSIDE; }
    @Override public boolean isEnergyEmittingTo(TagData aEnergyType, byte aSide, boolean aTheoretical) {return aSide == mFacing && super.isEnergyEmittingTo(aEnergyType, aSide, aTheoretical);}
    @Override public long getEnergyOffered(TagData aEnergyType, byte aSide, long aSize) {return mOutputVoltage;}
    @Override public long getEnergySizeOutputRecommended(TagData aEnergyType, byte aSide) {return mOutputVoltage;}
    @Override public long getEnergySizeOutputMin(TagData aEnergyType, byte aSide) {return mOutputVoltage;}
    @Override public long getEnergySizeOutputMax(TagData aEnergyType, byte aSide) {return mOutputVoltage;}
    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return mEnergyType.AS_LIST;}

    public ChunkCoordinates mTargetPos = null;
    public ITileEntityMultiBlockController mTarget = null;
    public int mDesign = 0;
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

    @Override
    public boolean allowCovers(byte aSide) {
        return true;
    }

    @Override
    public boolean getStateOnOff() {
        return stopped;
    }

    @Override
    public boolean setStateOnOff(boolean b) {
        this.stopped=b;
        return stopped;
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        super.addToolTips(aList, aStack, aF3_H);
    }
    @Override
    public int getLightOpacity(){
        return mDesign==1?255:0;
    }
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.part.transformer";
    }

}
