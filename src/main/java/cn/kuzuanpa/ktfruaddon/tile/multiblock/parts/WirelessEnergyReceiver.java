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

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.part.IMultiBlockPart;
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
import gregapi.tileentity.machines.ITileEntitySwitchableOnOff;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.util.OM;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static gregapi.data.CS.*;

public class WirelessEnergyReceiver extends TileEntityBase09FacingSingle implements IMultiBlockPart, ITileEntityEnergy, ITileEntitySwitchableOnOff {
    public static final String NBT_LINK_DIM = "ktfru.target.dimension";
    public static final String NBT_LINK_KIND = "ktfru.target.kind";
    public static final String NBT_STORED_ENERGY = "ktfru.wireless.receiver.energy";
    public static final String NBT_STORED_ENERGY_TYPE = "ktfru.wireless.receiver.energyType";

    public long mEnergy = 0, mOutputVoltage = 1, mOutputAmpere = 1;
    public TagData mEnergyType = TD.Energy.EU;
    public boolean mUniversal = false;
    public IIconContainer sTextureCommon, sOverlayFront;
    public boolean stopped = false;

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_TARGET)) mTargetPos = IMultiBlockPart.readTargetPosFromNBT(aNBT);
        if (aNBT.hasKey(NBT_DESIGN)) mDesign = UT.Code.unsignB(aNBT.getByte(NBT_DESIGN));
        if (aNBT.hasKey(NBT_OUTPUT)) mOutputVoltage = aNBT.getLong(NBT_OUTPUT);
        if (aNBT.hasKey(NBT_OUTPUT + ".amp")) mOutputAmpere = aNBT.getLong(NBT_OUTPUT + ".amp");
        if (aNBT.hasKey(NBT_ENERGY_EMITTED)) mEnergyType = TagData.createTagData(aNBT.getString(NBT_ENERGY_EMITTED));
        mUniversal = mDesign == 1;
        if (aNBT.hasKey(NBT_STORED_ENERGY)) mEnergy = aNBT.getLong(NBT_STORED_ENERGY);
        if (aNBT.hasKey(NBT_STORED_ENERGY_TYPE)) mEnergyType = TagData.createTagData(aNBT.getString(NBT_STORED_ENERGY_TYPE));
        else if (mUniversal) mEnergyType = null;

        if (CODE_CLIENT) {
            if (GT_API.sBlockIcons == null && aNBT.hasKey(NBT_TEXTURE)) {
                String texture = aNBT.getString(NBT_TEXTURE);
                sTextureCommon = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/transformer/" + texture + "/common");
                sOverlayFront = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/transformer/" + texture + "/front");
            } else {
                TileEntity canonical = MultiTileEntityRegistry.getCanonicalTileEntity(getMultiTileEntityRegistryID(), getMultiTileEntityID());
                if (canonical instanceof WirelessEnergyReceiver) {
                    sTextureCommon = ((WirelessEnergyReceiver)canonical).sTextureCommon;
                    sOverlayFront = ((WirelessEnergyReceiver)canonical).sOverlayFront;
                } else {
                    sTextureCommon = sOverlayFront = L6_IICONCONTAINER[0];
                }
            }
        }
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        IMultiBlockPart.writeToNBT(aNBT, mTargetPos, mDesign);
        UT.NBT.setNumber(aNBT, NBT_STORED_ENERGY, mEnergy);
        if (mEnergyType != null) aNBT.setString(NBT_STORED_ENERGY_TYPE, mEnergyType.mName);
    }

    @Override
    public boolean canDrop(int i) {
        return true;
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        return (!worldObj.isRemote && readDataFromPlayer(aPlayer)) || super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
    }

    public boolean readDataFromPlayer(EntityPlayer aPlayer) {
        ItemStack equippedItem = aPlayer.getCurrentEquippedItem();
        if (!OM.is(OD_USB_STICKS[0], equippedItem)) return false;
        NBTTagCompound data = UT.NBT.make();
        UT.NBT.setNumber(data, NBT_TARGET_X, xCoord);
        UT.NBT.setNumber(data, NBT_TARGET_Y, yCoord);
        UT.NBT.setNumber(data, NBT_TARGET_Z, zCoord);
        data.setInteger(NBT_LINK_DIM, worldObj.provider.dimensionId);
        data.setBoolean(NBT_LINK_KIND, mUniversal);

        equippedItem.setTagCompound(UT.NBT.make());
        equippedItem.getTagCompound().setTag(NBT_USB_DATA, data);
        equippedItem.getTagCompound().setByte(NBT_USB_TIER, (byte)1);
        aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN + LH.get(I18nHandler.DATA_WRITE_TO_USB)));
        return true;
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (aIsServerSide && !stopped && mEnergyType != null && mOutputVoltage > 0) {
            long ampere = Math.min(mOutputAmpere, mEnergy / mOutputVoltage);
            if (ampere != 0) mEnergy -= Util.emitEnergyToNetwork(mEnergyType, mOutputVoltage, ampere, this) * mOutputVoltage;
        }
    }

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if (stopped || aSide != SIDE_INSIDE || aSize <= 0) return 0;
        if (mUniversal) {
            if (mEnergy > 0 && !aEnergyType.equals(mEnergyType)) return 0;
        } else if (!aEnergyType.equals(mEnergyType)) return 0;

        long capacity = mOutputAmpere * mOutputVoltage;
        long free = capacity - mEnergy;
        if (free <= 0) return 0;
        long ampereConsumed = Math.min(aAmount, free / aSize + (free % aSize == 0 ? 0 : 1));
        if (aDoInject && ampereConsumed > 0) {
            if (mEnergy == 0) mEnergyType = aEnergyType;
            mEnergy += ampereConsumed * aSize;
        }
        return ampereConsumed;
    }

    @Override public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {
        return mUniversal ? aEnergyType != null && TD.Energy.ALL.contains(aEnergyType) : aEnergyType == mEnergyType;
    }
    @Override public boolean isEnergyAcceptingFrom(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        if (aSide != SIDE_INSIDE) return false;
        return mUniversal ? mEnergy == 0 || aEnergyType.equals(mEnergyType) : aEnergyType.equals(mEnergyType);
    }
    @Override public boolean isEnergyEmittingTo(TagData aEnergyType, byte aSide, boolean aTheoretical) {return aSide == mFacing && isEnergyType(aEnergyType, aSide, true) && super.isEnergyEmittingTo(aEnergyType, aSide, aTheoretical);}
    @Override public long getEnergyOffered(TagData aEnergyType, byte aSide, long aSize) {return mOutputVoltage;}
    @Override public long getEnergySizeOutputRecommended(TagData aEnergyType, byte aSide) {return mOutputVoltage;}
    @Override public long getEnergySizeOutputMin(TagData aEnergyType, byte aSide) {return mOutputVoltage;}
    @Override public long getEnergySizeOutputMax(TagData aEnergyType, byte aSide) {return mOutputVoltage;}
    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return mUniversal ? TD.Energy.ALL : mEnergyType == null ? Collections.<TagData>emptyList() : mEnergyType.AS_LIST;}

    public ChunkCoordinates mTargetPos = null;
    public ITileEntityMultiBlockController mTarget = null;
    public int mDesign = 0;
    @Override public ITileEntityMultiBlockController getTarget2() {return mTarget;}
    @Override public void setTarget(ITileEntityMultiBlockController target) {mTarget = target;}
    @Override public ChunkCoordinates getTargetPos() {return mTargetPos;}
    @Override public void setTargetPos(ChunkCoordinates aCoords) {mTargetPos = aCoords;}
    @Override public void setDesign(int aDesign) {this.mDesign = aDesign;}
    @Override public int getDesign() {return mDesign;}

    @Override
    public boolean breakBlock() {
        notifyTarget();
        return super.breakBlock();
    }

    @Override public boolean allowCovers(byte aSide) {return true;}
    @Override public boolean getStateOnOff() {return !stopped;}
    @Override public boolean setStateOnOff(boolean aState) {stopped = !aState; return !stopped;}

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        super.addToolTips(aList, aStack, aF3_H);
        aList.add(LH.Chat.CYAN + LH.get("ktfru.wireless.receiver.tooltip.1"));
    }

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon, mRGBa), aSide == mFacing ? BlockTextureDefault.get(sOverlayFront) : null);
    }

    @Override public int getLightOpacity() {return mDesign == 1 ? 255 : 0;}
    @Override public String getTileEntityName() {return "ktfru.multitileentity.part.wireless.receiver";}
}
