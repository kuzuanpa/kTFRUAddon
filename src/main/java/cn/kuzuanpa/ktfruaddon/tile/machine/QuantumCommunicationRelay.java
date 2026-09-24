/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 */
package cn.kuzuanpa.ktfruaddon.tile.machine;

import cn.kuzuanpa.ktfruaddon.api.recipe.IKortexHandler;
import cn.kuzuanpa.ktfruaddon.api.recipe.KortexWorker;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.fluid.FluidTankGT;
import gregapi.old.Textures;
import gregapi.recipes.Recipe;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.util.ST;
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

/** Paired quantum-communication relay.  The recipe core owns the R01 materials, timing, energy and output. */
public class QuantumCommunicationRelay extends TileEntityBase09FacingSingle implements IKortexHandler, ITileEntityEnergy {
    public static final long QU_PER_TICK = 128;
    public static final long QU_PER_RECIPE = 65536;
    public static final long ENERGY_CAPACITY = 32768000L;

    private static final String NBT_QU = "ktfru.quantum_relay.qu";
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

    private KortexWorker kortex;
    private long mQUStored;
    /** 0=unpaired/offline, 1=paired idle, 2=running. */
    private byte mState;
    private byte mStateOld;
    private boolean mLinked;
    private boolean mRecipeCompleted;
    private int mPartnerDimension;
    private int mPartnerX;
    private int mPartnerY;
    private int mPartnerZ;

    // NBT
    @Override public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        Recipe.RecipeMap recipes = recipeMaps.QuantumCommunication;
        if (aNBT.hasKey(NBT_RECIPEMAP)) {
            Recipe.RecipeMap configured = Recipe.RecipeMap.RECIPE_MAPS.get(aNBT.getString(NBT_RECIPEMAP));
            if (configured != null) recipes = configured;
        }
        kortex = new KortexWorker(this, recipes).setEnergyCapacity(ENERGY_CAPACITY).setMaxProgressPerTick(1).consumeInputsOnFinish().dontResetProgressWhenPowerLost();
        kortex.readFromNBT(aNBT);
        mQUStored = aNBT.getLong(NBT_QU);
        mLinked = aNBT.getBoolean(NBT_LINKED);
        mPartnerDimension = aNBT.getInteger(NBT_DIM);
        mPartnerX = aNBT.getInteger(NBT_X);
        mPartnerY = aNBT.getInteger(NBT_Y);
        mPartnerZ = aNBT.getInteger(NBT_Z);
    }

    @Override public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        if (kortex != null) kortex.writeToNBT(aNBT);
        UT.NBT.setNumber(aNBT, NBT_QU, mQUStored);
        aNBT.setBoolean(NBT_LINKED, mLinked);
        aNBT.setInteger(NBT_DIM, mPartnerDimension);
        aNBT.setInteger(NBT_X, mPartnerX);
        aNBT.setInteger(NBT_Y, mPartnerY);
        aNBT.setInteger(NBT_Z, mPartnerZ);
    }

    // Tick
    @Override public void onTick2(long aTimer, boolean aIsServerSide) {
        if (!aIsServerSide) return;
        emitQu();
        if (kortex == null) return;

        QuantumCommunicationRelay partner = getPartner();
        if (partner == null) {
            setState((byte) 0);
            return;
        }
        if (!isPrimary()) {
            setState(partner.mState == 2 ? (byte) 2 : (byte) 1);
            return;
        }
        tickPrimary(partner);
    }

    private void tickPrimary(QuantumCommunicationRelay aPartner) {
        syncWorkerInput();
        aPartner.syncWorkerInput();

        boolean thisActive = kortex.hasActiveRecipe();
        boolean partnerActive = aPartner.kortex.hasActiveRecipe();
        if (thisActive != partnerActive) {
            resetWorkers(aPartner);
            setPairState((byte) 1);
            return;
        }
        if (!thisActive && (!canStartPairedRecipe(aPartner) || !canAcceptPotentialOutputs())) {
            setPairState((byte) 1);
            return;
        }
        if (thisActive && (kortex.mProgress != aPartner.kortex.mProgress || kortex.mMaxProgress != aPartner.kortex.mMaxProgress
                || !canAdvance(aPartner) || !canAcceptCachedOutputs())) {
            setPairState((byte) 1);
            return;
        }

        kortex.run();
        aPartner.kortex.run();
        if (!kortex.hasActiveRecipe() || !aPartner.kortex.hasActiveRecipe()) {
            mRecipeCompleted = false;
            setPairState((byte) 1);
            return;
        }
        if (!canAdvance(aPartner) || !canAcceptCachedOutputs()) {
            setPairState((byte) 1);
            return;
        }
        setPairState((byte) 2);
    }

    private boolean canAdvance(QuantumCommunicationRelay aPartner) {
        return kortex.hasActiveRecipeInputs() && aPartner.kortex.hasActiveRecipeInputs()
                && kortex.hasEnergyForNextTick() && aPartner.kortex.hasEnergyForNextTick();
    }

    private boolean canStartPairedRecipe(QuantumCommunicationRelay aPartner) {
        if (!kortex.canStartRecipe() || !aPartner.kortex.canStartRecipe()) return false;
        Recipe primaryRecipe = kortex.findRecipe();
        Recipe partnerRecipe = aPartner.kortex.findRecipe();
        return hasItemOutput(primaryRecipe) && !hasItemOutput(partnerRecipe);
    }

    private boolean hasItemOutput(Recipe aRecipe) {
        if (aRecipe == null || aRecipe.mOutputs == null) return false;
        for (ItemStack item : aRecipe.mOutputs) if (item != null) return true;
        return false;
    }

    private void resetWorkers(QuantumCommunicationRelay aPartner) {
        kortex.resetStatus();
        aPartner.kortex.resetStatus();
        mRecipeCompleted = false;
    }

    private void syncWorkerInput() {
        if (kortex.itemInputs.length == 0) return;
        kortex.itemInputs[0] = slot(0) == null ? null : ST.copy(slot(0));
    }

    private void emitQu() {
        if (mQUStored >= QU_PER_TICK) mQUStored -= QU_PER_TICK * ITileEntityEnergy.Util.emitEnergyToNetwork(TD.Energy.QU, QU_PER_TICK, 1, this);
    }

    // Pairing
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

    private void setPartner(QuantumCommunicationRelay aPartner) {
        mLinked = true;
        mPartnerDimension = aPartner.worldObj.provider.dimensionId;
        mPartnerX = aPartner.xCoord;
        mPartnerY = aPartner.yCoord;
        mPartnerZ = aPartner.zCoord;
        mRecipeCompleted = false;
        if (kortex != null) kortex.resetStatus();
        setState((byte) 1);
    }

    private void setPairState(byte aState) {
        setState(aState);
        QuantumCommunicationRelay partner = getPartner();
        if (partner != null) partner.setState(aState);
    }

    private void setState(byte aState) {
        if (mState != aState) {
            mState = aState;
            updateClientData();
        }
    }

    @Override public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn,
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
                aChatReturn.add(LH.Chat.CYAN + LH.get("ktfru.text.quantum_relay.recorded"));
                return 100;
            }
            if (tag.getInteger(TOOL_DIM) != worldObj.provider.dimensionId) {
                aChatReturn.add(LH.Chat.RED + LH.get("ktfru.text.quantum_relay.same_dimension"));
                return 0;
            }
            TileEntity tile = worldObj.getTileEntity(tag.getInteger(TOOL_X), tag.getInteger(TOOL_Y), tag.getInteger(TOOL_Z));
            if (!(tile instanceof QuantumCommunicationRelay) || tile == this) {
                aChatReturn.add(LH.Chat.RED + LH.get("ktfru.text.quantum_relay.partner_missing"));
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
            aChatReturn.add(LH.Chat.GREEN + LH.get("ktfru.text.quantum_relay.established"));
            return 100;
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    // Recipe bridge
    @Override public int getMaxParallel(int aKortexID, long aEUT, long aDuration) { return 1; }

    @Override public void onRecipeFinish(int aKortexID, long aEUT, long aDuration, long aParallel) {
        if (!isPrimary()) return;
        boolean outputFound = false;
        for (ItemStack item : kortex.getRecipeOutputItems()) if (item != null) {
            outputFound = true;
            break;
        }
        if (!outputFound) return;
        mRecipeCompleted = true;
        mQUStored = Math.min(ENERGY_CAPACITY, mQUStored + QU_PER_RECIPE);
    }

    @Override public void receiveOutputs(FluidTankGT[] aFluidTanks, ItemStack[] aItems) {
        if (kortex != null && kortex.itemInputs.length > 0) {
            if (kortex.itemInputs[0] == null) slotKill(0); else slot(0, kortex.itemInputs[0]);
        }
        for (ItemStack item : aItems) if (item != null) {
            if (slot(1) == null) slot(1, ST.copy(item));
            else slot(1).stackSize += item.stackSize;
        }
    }

    private boolean canAcceptCachedOutputs() {
        for (ItemStack item : kortex.getRecipeOutputItems()) if (item != null && !canAcceptOutput(item)) return false;
        return true;
    }

    private boolean canAcceptPotentialOutputs() {
        for (Recipe recipe : kortex.recipeMap.mRecipeList) if (recipe.mOutputs != null) for (ItemStack item : recipe.mOutputs) if (item != null && !canAcceptOutput(item)) return false;
        return true;
    }

    private boolean canAcceptOutput(ItemStack aOutput) {
        ItemStack current = slot(1);
        return current == null || (ST.equal(current, aOutput, F) && current.stackSize + aOutput.stackSize <= getInventoryStackLimit());
    }

    private long getRecipeEnergyPerTick() {
        if (kortex != null && kortex.mRecipeEUt > 0) return kortex.mRecipeEUt;
        if (kortex != null) for (Recipe recipe : kortex.recipeMap.mRecipeList) if (recipe.mEnabled && recipe.mEUt > 0) return recipe.mEUt;
        return 1L;
    }

    // Energy
    @Override public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {
        if (aEmitting) return aEnergyType == TD.Energy.QU;
        return getPartner() != null && (isPrimary() ? aEnergyType == TD.Energy.EU : aEnergyType == TD.Energy.LU);
    }
    @Override public boolean isEnergyEmittingTo(TagData aEnergyType, byte aSide, boolean aTheoretical) { return aEnergyType == TD.Energy.QU && aSide == SIDE_TOP; }
    @Override public long getEnergyOffered(TagData aEnergyType, byte aSide, long aSize) { return aEnergyType == TD.Energy.QU ? mQUStored : 0; }
    @Override public long getEnergySizeOutputRecommended(TagData aEnergyType, byte aSide) { return QU_PER_TICK; }
    @Override public long getEnergySizeOutputMin(TagData aEnergyType, byte aSide) { return QU_PER_TICK; }
    @Override public long getEnergySizeOutputMax(TagData aEnergyType, byte aSide) { return QU_PER_TICK; }
    @Override public long getEnergySizeInputMin(TagData aEnergyType, byte aSide) { return isEnergyType(aEnergyType, aSide, F) ? getRecipeEnergyPerTick() : 0; }
    @Override public long getEnergySizeInputRecommended(TagData aEnergyType, byte aSide) { return getEnergySizeInputMin(aEnergyType, aSide); }
    @Override public long getEnergySizeInputMax(TagData aEnergyType, byte aSide) { return getEnergySizeInputRecommended(aEnergyType, aSide) * 4; }
    @Override public Collection<TagData> getEnergyTypes(byte aSide) { return new gregapi.code.ArrayListNoNulls<TagData>(F, TD.Energy.EU, TD.Energy.LU, TD.Energy.QU); }
    @Override public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if (kortex == null || !isEnergyType(aEnergyType, aSide, F)) return 0;
        aSize = Math.abs(aSize);
        if (aSize <= 0) return 0;
        if (aSize > getEnergySizeInputMax(aEnergyType, aSide)) {
            if (aDoInject) overcharge(aSize, aEnergyType);
            return aAmount;
        }
        long injectableEnergy = Math.max(0L, ENERGY_CAPACITY - getRecipeEnergyPerTick() - kortex.mEnergyStored);
        long acceptedEnergy = Math.min(injectableEnergy, aSize * aAmount);
        long acceptedPackets = Math.min(aAmount, acceptedEnergy / aSize + (acceptedEnergy % aSize == 0 ? 0 : 1));
        if (aDoInject && acceptedPackets > 0) kortex.injectEnergy(acceptedPackets * aSize);
        return acceptedPackets;
    }

    // Inventory / GUI / Meta
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) { return new ItemStack[2]; }
    @Override public boolean canDrop(int aSlot) { return true; }
    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) { return aSide == SIDE_TOP ? new int[]{0} : new int[]{1}; }
    @Override public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) { return aSlot == 0 && kortex != null && kortex.recipeMap.containsInput(aStack, this, slot(1)); }
    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) { return aSlot == 1; }

    @Override public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get("ktfru.text.quantum_relay.pair"));
        aList.add(LH.Chat.DGRAY + LH.get("ktfru.text.quantum_relay.discovery"));
    }

    @Override public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return aShouldSideBeRendered[aSide] ? BlockTextureMulti.get(BlockTextureDefault.get(TEXTURE_MATERIAL, mRGBa),
                aSide == mFacing ? BlockTextureDefault.get(mState == 2 ? TEXTURE_FRONT_ACTIVE : TEXTURE_FRONT) : null) : null;
    }
    @Override public byte getDefaultSide() { return SIDE_SOUTH; }
    @Override public boolean[] getValidSides() { return SIDES_HORIZONTAL; }
    @Override public boolean onTickCheck(long aTimer) { if (mStateOld != mState) { mStateOld = mState; return true; } return false; }
    @Override public byte getVisualData() { return mState; }
    @Override public void setVisualData(byte aData) { mState = aData; }
    @Override public String getTileEntityName() { return "ktfru.multitileentity.30074"; }
}
