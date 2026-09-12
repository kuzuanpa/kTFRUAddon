/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 */
package cn.kuzuanpa.ktfruaddon.tile.machine;

import cn.kuzuanpa.ktfruaddon.api.material.matList;
import cn.kuzuanpa.ktfruaddon.api.material.prefix.prefixList;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.data.TD;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.*;

/**
 * Q1-R01 and Q1-R06. A paired channel consumes a different analytical-pure
 * calibration material at each end. Its first completed observation reveals
 * Mirror Gold, then the captured entanglement is made available as QU.
 */
public class QuantumCommunicationRelay extends TileEntityBase09FacingSingle implements ITileEntityEnergy {
    public static final long EU_PER_TICK = 8192;
    public static final long LU_PER_TICK = 8192;
    public static final long QU_PER_TICK = 128;
    public static final long QU_DISCOVERY_YIELD = 65536;
    public static final int PROCESS_TICKS = 3600;
    public static final long ENERGY_CAPACITY = 32768000L;

    private static final String NBT_LINKED = "ktfru.quantum_relay.linked";
    private static final String NBT_DIM = "ktfru.quantum_relay.dim";
    private static final String NBT_X = "ktfru.quantum_relay.x";
    private static final String NBT_Y = "ktfru.quantum_relay.y";
    private static final String NBT_Z = "ktfru.quantum_relay.z";
    private static final String TOOL_LINKED = "ktfru.quantum_relay.tool.linked";
    private static final String TOOL_DIM = "ktfru.quantum_relay.tool.dim";
    private static final String TOOL_X = "ktfru.quantum_relay.tool.x";
    private static final String TOOL_Y = "ktfru.quantum_relay.tool.y";
    private static final String TOOL_Z = "ktfru.quantum_relay.tool.z";

    private static final IIconContainer TEXTURE_MATERIAL = new Textures.BlockIcons.CustomIcon("machines/voidhopper/colored");
    private static final IIconContainer TEXTURE_FRONT = new Textures.BlockIcons.CustomIcon("machines/voidhopper/front");
    private static final IIconContainer TEXTURE_FRONT_ACTIVE = new Textures.BlockIcons.CustomIcon("machines/voidhopper/front_active");

    private long mEUStored;
    private long mLUStored;
    private long mQUStored;
    private int mProgress;
    /** 0=unpaired/offline, 1=paired idle, 2=running. */
    private byte mState;
    private byte mStateOld;
    private boolean mLinked;
    private int mPartnerDimension;
    private int mPartnerX;
    private int mPartnerY;
    private int mPartnerZ;

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        mEUStored = aNBT.getLong("ktfru.quantum_relay.eu");
        mLUStored = aNBT.getLong("ktfru.quantum_relay.lu");
        mQUStored = aNBT.getLong("ktfru.quantum_relay.qu");
        mProgress = aNBT.getInteger(NBT_PROGRESS);
        mLinked = aNBT.getBoolean(NBT_LINKED);
        mPartnerDimension = aNBT.getInteger(NBT_DIM);
        mPartnerX = aNBT.getInteger(NBT_X);
        mPartnerY = aNBT.getInteger(NBT_Y);
        mPartnerZ = aNBT.getInteger(NBT_Z);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT, "ktfru.quantum_relay.eu", mEUStored);
        UT.NBT.setNumber(aNBT, "ktfru.quantum_relay.lu", mLUStored);
        UT.NBT.setNumber(aNBT, "ktfru.quantum_relay.qu", mQUStored);
        aNBT.setInteger(NBT_PROGRESS, mProgress);
        aNBT.setBoolean(NBT_LINKED, mLinked);
        aNBT.setInteger(NBT_DIM, mPartnerDimension);
        aNBT.setInteger(NBT_X, mPartnerX);
        aNBT.setInteger(NBT_Y, mPartnerY);
        aNBT.setInteger(NBT_Z, mPartnerZ);
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if (!aIsServerSide) return;
        if (mQUStored >= QU_PER_TICK) {
            mQUStored -= QU_PER_TICK * ITileEntityEnergy.Util.emitEnergyToNetwork(TD.Energy.QU, QU_PER_TICK, 1, this);
        }
        if (aTimer % 10 != 0) return;

        QuantumCommunicationRelay partner = getPartner();
        if (partner == null) {
            setState((byte) 0);
            return;
        }
        if (!isPrimary()) {
            setState(partner.mState == 2 ? (byte) 2 : (byte) 1);
            return;
        }
        if (!hasIjCalibration() || !partner.hasNqCalibration() || !canOutputMirrorGold()
                || mEUStored < EU_PER_TICK * 10 || partner.mLUStored < LU_PER_TICK * 10) {
            setState((byte) 1);
            partner.setState((byte) 1);
            return;
        }

        mEUStored -= EU_PER_TICK * 10;
        partner.mLUStored -= LU_PER_TICK * 10;
        mProgress += 10;
        setState((byte) 2);
        partner.setState((byte) 2);
        if (mProgress >= PROCESS_TICKS) {
            consumeCalibration();
            partner.consumeCalibration();
            mQUStored = Math.min(ENERGY_CAPACITY, mQUStored + QU_DISCOVERY_YIELD);
            ItemStack output = OP.ingot.mat(matList.MirrorGold.mat, 1);
            if (slot(1) == null) slot(1, output); else slot(1).stackSize += output.stackSize;
            mProgress = 0;
        }
    }

    private boolean hasIjCalibration() {
        return slot(0) != null && slot(0).stackSize >= 4
                && gregapi.util.OM.is(prefixList.AnalyticalPureDust.mat(matList.Ij.mat, 1), slot(0));
    }

    private boolean hasNqCalibration() {
        return slot(0) != null && slot(0).stackSize >= 4
                && gregapi.util.OM.is(prefixList.AnalyticalPureDust.mat(MT.Nq_522, 1), slot(0));
    }

    private void consumeCalibration() {
        slot(0).stackSize -= 4;
        if (slot(0).stackSize <= 0) slotKill(0);
    }

    private boolean canOutputMirrorGold() {
        ItemStack output = OP.ingot.mat(matList.MirrorGold.mat, 1);
        return slot(1) == null || (gregapi.util.OM.is(output, slot(1))
                && slot(1).stackSize + output.stackSize <= getInventoryStackLimit());
    }

    private boolean isPrimary() {
        int thisDimension = worldObj.provider.dimensionId;
        if (thisDimension != mPartnerDimension) return thisDimension < mPartnerDimension;
        if (xCoord != mPartnerX) return xCoord < mPartnerX;
        if (yCoord != mPartnerY) return yCoord < mPartnerY;
        return zCoord < mPartnerZ;
    }

    private QuantumCommunicationRelay getPartner() {
        if (!mLinked || worldObj == null || worldObj.provider.dimensionId != mPartnerDimension) return null;
        if (!worldObj.blockExists(mPartnerX, mPartnerY, mPartnerZ)) return null;
        TileEntity tile = worldObj.getTileEntity(mPartnerX, mPartnerY, mPartnerZ);
        if (!(tile instanceof QuantumCommunicationRelay)) return null;
        QuantumCommunicationRelay partner = (QuantumCommunicationRelay) tile;
        return partner.mLinked && partner.mPartnerDimension == worldObj.provider.dimensionId
                && partner.mPartnerX == xCoord && partner.mPartnerY == yCoord && partner.mPartnerZ == zCoord ? partner : null;
    }

    private void setPartner(QuantumCommunicationRelay partner) {
        mLinked = true;
        mPartnerDimension = partner.worldObj.provider.dimensionId;
        mPartnerX = partner.xCoord;
        mPartnerY = partner.yCoord;
        mPartnerZ = partner.zCoord;
        mProgress = 0;
        setState((byte) 1);
    }

    private void setState(byte state) {
        if (mState != state) {
            mState = state;
            updateClientData();
        }
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn,
                             IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (TOOL_screwdriver.equals(aTool) && aPlayer instanceof EntityPlayer && aStack != null && isServerSide()) {
            NBTTagCompound tag = aStack.getTagCompound();
            if (tag == null) {
                tag = new NBTTagCompound();
                aStack.setTagCompound(tag);
            }
            if (!tag.getBoolean(TOOL_LINKED)) {
                tag.setBoolean(TOOL_LINKED, true);
                tag.setInteger(TOOL_DIM, worldObj.provider.dimensionId);
                tag.setInteger(TOOL_X, xCoord);
                tag.setInteger(TOOL_Y, yCoord);
                tag.setInteger(TOOL_Z, zCoord);
                aChatReturn.add(LH.Chat.CYAN + "Quantum relay recorded. Use the screwdriver on a second relay to pair them.");
                return 100;
            }
            if (tag.getInteger(TOOL_DIM) != worldObj.provider.dimensionId) {
                aChatReturn.add(LH.Chat.RED + "Quantum relays must be paired in the same dimension.");
                return 0;
            }
            TileEntity tile = worldObj.getTileEntity(tag.getInteger(TOOL_X), tag.getInteger(TOOL_Y), tag.getInteger(TOOL_Z));
            if (!(tile instanceof QuantumCommunicationRelay) || tile == this) {
                aChatReturn.add(LH.Chat.RED + "The recorded quantum relay is unavailable.");
                return 0;
            }
            QuantumCommunicationRelay first = (QuantumCommunicationRelay) tile;
            first.setPartner(this);
            setPartner(first);
            tag.removeTag(TOOL_LINKED);
            tag.removeTag(TOOL_DIM);
            tag.removeTag(TOOL_X);
            tag.removeTag(TOOL_Y);
            tag.removeTag(TOOL_Z);
            aChatReturn.add(LH.Chat.GREEN + "Quantum channel established.");
            return 100;
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + "Pair two relays with a screwdriver to calibrate an entanglement channel.");
        aList.add(LH.Chat.DGRAY + "Primary: 4 analytical-pure Ij dust + " + EU_PER_TICK + " EU/t");
        aList.add(LH.Chat.DGRAY + "Partner: 4 analytical-pure Nq_522 dust + " + LU_PER_TICK + " LU/t; duration: " + (PROCESS_TICKS / 20) + " s");
        aList.add(LH.Chat.DGRAY + "Discovery yields one Mirror Gold ingot and " + QU_DISCOVERY_YIELD + " QU at the primary relay.");
    }

    @Override public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return aShouldSideBeRendered[aSide] ? BlockTextureMulti.get(BlockTextureDefault.get(TEXTURE_MATERIAL, mRGBa),
                aSide == mFacing ? BlockTextureDefault.get(mState == 2 ? TEXTURE_FRONT_ACTIVE : TEXTURE_FRONT) : null) : null;
    }
    @Override public byte getDefaultSide() { return SIDE_SOUTH; }
    @Override public boolean[] getValidSides() { return SIDES_HORIZONTAL; }
    @Override public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {
        return aEmitting ? aEnergyType == TD.Energy.QU : (aEnergyType == TD.Energy.EU || aEnergyType == TD.Energy.LU);
    }
    @Override public boolean isEnergyEmittingTo(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return aEnergyType == TD.Energy.QU && aSide == SIDE_TOP;
    }
    @Override public long getEnergyOffered(TagData aEnergyType, byte aSide, long aSize) { return aEnergyType == TD.Energy.QU ? mQUStored : 0; }
    @Override public long getEnergySizeOutputRecommended(TagData aEnergyType, byte aSide) { return QU_PER_TICK; }
    @Override public long getEnergySizeOutputMin(TagData aEnergyType, byte aSide) { return QU_PER_TICK; }
    @Override public long getEnergySizeOutputMax(TagData aEnergyType, byte aSide) { return QU_PER_TICK; }
    @Override public long getEnergySizeInputMin(TagData aEnergyType, byte aSide) { return aEnergyType == TD.Energy.EU ? EU_PER_TICK : LU_PER_TICK; }
    @Override public long getEnergySizeInputRecommended(TagData aEnergyType, byte aSide) { return getEnergySizeInputMin(aEnergyType, aSide); }
    @Override public long getEnergySizeInputMax(TagData aEnergyType, byte aSide) { return 32768; }
    @Override public Collection<TagData> getEnergyTypes(byte aSide) { return new gregapi.code.ArrayListNoNulls<TagData>(F, TD.Energy.EU, TD.Energy.LU, TD.Energy.QU); }
    @Override public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if (aEnergyType == TD.Energy.QU) return 0;
        aSize = Math.abs(aSize);
        if (aSize > getEnergySizeInputMax(aEnergyType, aSide)) {
            if (aDoInject) overcharge(aSize, aEnergyType);
            return aAmount;
        }
        long stored = aEnergyType == TD.Energy.EU ? mEUStored : aEnergyType == TD.Energy.LU ? mLUStored : -1;
        if (stored < 0) return 0;
        long acceptedEnergy = Math.min(ENERGY_CAPACITY - stored, aSize * aAmount);
        long acceptedPackets = Math.min(aAmount, (acceptedEnergy / aSize) + (acceptedEnergy % aSize == 0 ? 0 : 1));
        if (aDoInject) {
            if (aEnergyType == TD.Energy.EU) mEUStored += acceptedPackets * aSize;
            else mLUStored += acceptedPackets * aSize;
        }
        return acceptedPackets;
    }
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) { return new ItemStack[2]; }
    @Override public boolean canDrop(int aSlot) { return true; }
    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) { return aSide == SIDE_TOP ? new int[]{0} : new int[]{1}; }
    @Override public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {
        return aSlot == 0 && (gregapi.util.OM.is(prefixList.AnalyticalPureDust.mat(matList.Ij.mat, 1), aStack)
                || gregapi.util.OM.is(prefixList.AnalyticalPureDust.mat(MT.Nq_522, 1), aStack));
    }
    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) { return aSlot == 1; }
    @Override public boolean onTickCheck(long aTimer) { if (mStateOld != mState) { mStateOld = mState; return true; } return false; }
    @Override public byte getVisualData() { return mState; }
    @Override public void setVisualData(byte aData) { mState = aData; }
    @Override public String getTileEntityName() { return "ktfru.multitileentity.machine.quantum_communication_relay"; }
}
