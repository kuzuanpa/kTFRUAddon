/*
 * Part of kTFRUAddon. Distributed under the AGPLv3.
 */
package cn.kuzuanpa.ktfruaddon.tile.multiblock.machine.laser;

import cn.kuzuanpa.ktfruaddon.api.code.StateMgr;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.recipe.IKortexHandler;
import cn.kuzuanpa.ktfruaddon.api.recipe.KortexWorker;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.fluid.FluidTankGT;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.old.Textures;
import gregapi.recipes.Recipe;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.multiblocks.IMultiBlockEnergy;
import gregapi.tileentity.multiblocks.IMultiBlockFluidHandler;
import gregapi.tileentity.multiblocks.IMultiBlockInventory;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.*;

/**
 * Shared discrete-pulse reactor logic. LU enters through stock GT multiblock
 * parts assigned ONLY_ENERGY_IN by the structure predicates. One accepted
 * recipe uses exactly one stored pulse, then enters mandatory cooling.
 */
public abstract class LaserFusionControllerBase extends TileEntityBase10MultiBlockBase implements
        IMultiTileEntity.IMTE_SyncDataByteArray, ITileEntityEnergy, IMultiBlockEnergy,
        IMultiBlockFluidHandler, IMultiBlockInventory, IKortexHandler {
    public static final byte STATE_IDLE = 0, STATE_CHARGING = 1, STATE_COOLING = 2, STATE_ERROR = 3;
    private static final String NBT_PULSE_ENERGY = "ktfru.laser.pulse_energy";
    private static final String NBT_COOLDOWN = "ktfru.laser.cooldown";
    private static final String NBT_STATE = "ktfru.laser.state";

    protected final StateMgr mState = new StateMgr(STATE_IDLE);
    protected long mPulseEnergy;
    protected int mCooldown;
    /** Recipe core; the target pellet, the gases and the shot transaction all live in it. */
    protected KortexWorker kortex;
    protected Recipe mLastRecipe;
    protected ChunkCoordinates mLastFailedPos;
    protected final FluidTankGT[] mTanks = {new FluidTankGT(8000), new FluidTankGT(8000), new FluidTankGT(8000), new FluidTankGT(8000)};
    protected final FluidTankGT[] mInputTanks = {mTanks[0], mTanks[1]};
    protected final FluidTankGT[] mOutputTanks = {mTanks[2], mTanks[3]};

    protected abstract IStringBaseStructure getStructure();
    /** Exact structural counts; port capabilities themselves are defined by GT usage flags. */
    protected abstract int laserArrayCount();
    protected abstract int pulseStorageCount();
    protected abstract long pulseCapacityPerStorage();
    protected abstract long peakEnergyPerLaserArray();
    protected abstract long maxPulseInputPacket();
    protected abstract int cooldownTicks();
    protected abstract String texturePath();

    @Override public void readFromNBT2(NBTTagCompound nbt) {
        super.readFromNBT2(nbt);
        if (nbt.hasKey(NBT_PULSE_ENERGY)) mPulseEnergy = nbt.getLong(NBT_PULSE_ENERGY);
        if (nbt.hasKey(NBT_COOLDOWN)) mCooldown = nbt.getInteger(NBT_COOLDOWN);
        if (nbt.hasKey(NBT_STATE)) mState.set(nbt.getByte(NBT_STATE));
        Recipe.RecipeMap mRecipes = recipeMaps.LaserFusion;
        if (nbt.hasKey(NBT_FUELMAP)) mRecipes = Recipe.RecipeMap.RECIPE_MAPS.get(nbt.getString(NBT_FUELMAP));
        for (int i = 0; i < mTanks.length; i++) mTanks[i].readFromNBT(nbt, NBT_TANK + "." + i).setCapacity(8000);
        kortex = new KortexWorker(this, 0, mRecipes, T);
        kortex.fluidInputs = mInputTanks;
        kortex.readFromNBT(nbt);
    }

    @Override public void writeToNBT2(NBTTagCompound nbt) {
        super.writeToNBT2(nbt);
        UT.NBT.setNumber(nbt, NBT_PULSE_ENERGY, mPulseEnergy);
        nbt.setInteger(NBT_COOLDOWN, mCooldown);
        nbt.setByte(NBT_STATE, mState.get());
        for (int i = 0; i < mTanks.length; i++) mTanks[i].writeToNBT(nbt, NBT_TANK + "." + i);
        kortex.writeToNBT(nbt);
    }

    protected final long getPulseCapacity() { return (long) pulseStorageCount() * pulseCapacityPerStorage(); }
    protected final long getPeakEnergy() { return (long) laserArrayCount() * peakEnergyPerLaserArray(); }

    @Override public void onTick2(long timer, boolean serverSide) {
        if (!serverSide) return;
        if (!mStructureOkay) {
            mState.set(STATE_ERROR);
            return;
        }
        if (mCooldown > 0) {
            mCooldown--;
            mState.set(STATE_COOLING);
            return;
        }
        tryFirePulse();
        if (mState.is(STATE_COOLING) || mState.is(STATE_ERROR)) return;
        mState.set(mPulseEnergy > 0 ? STATE_CHARGING : STATE_IDLE);
    }

    /** Fires at most one shot per call; the worker consumes the pellet and gases once the machine cleared the pulse. */
    private void tryFirePulse() {
        if (kortex.itemInputs.length > 0) kortex.itemInputs[0] = slot(0) == null ? null : ST.copy(slot(0));
        kortex.mEnergyStored = 0;
        kortex.mEnergyCapacity = Long.MAX_VALUE;
        kortex.run();
        kortex.mEnergyStored = 0;
    }

    // Recipe bridge
    /** The pulse is a machine resource: a shot needs its full SpecialNumber, and every output must fit first. */
    @Override
    public int getMaxParallel(int aKortexID, long aEUT, long aDuration) {
        Recipe recipe = kortex.findRecipe();
        if (recipe == null) return 0;
        long requiredPulse = recipe.mSpecialValue;
        if (mPulseEnergy < requiredPulse || getPeakEnergy() < requiredPulse) return 0;
        if (!canStoreOutputs(recipe)) return 0;
        mLastRecipe = recipe;
        return 1;
    }

    @Override
    public void onRecipeStart(int aKortexID, long aEUT, long aDuration, long aParallel) {
        if (mLastRecipe != null) mPulseEnergy -= mLastRecipe.mSpecialValue;
        mCooldown = cooldownTicks();
        mState.set(STATE_COOLING);
    }

    @Override
    public void receiveOutputs(FluidTankGT[] fluidTanks, ItemStack[] items) {
        if (kortex.itemInputs.length > 0) {
            if (kortex.itemInputs[0] == null) slotKill(0); else slot(0, kortex.itemInputs[0]);
        }
        for (FluidTankGT tTank : fluidTanks) if (tTank.has()) {
            for (FluidTankGT tTarget : mOutputTanks) if (tTarget.fillAll(tTank.get())) {tTank.setEmpty(); break;}
        }
        for (int i = 0; i < items.length; i++) {
            ItemStack tOutput = items[i];
            if (tOutput == null) continue;
            items[i] = null;
            if (mLastRecipe != null && getRandomNumber(10000) > mLastRecipe.getOutputChance(i)) continue;
            if (slot(1) == null) slot(1, tOutput.copy()); else slot(1).stackSize += tOutput.stackSize;
        }
    }

    private boolean canStoreOutputs(Recipe recipe) {
        for (FluidStack output : recipe.mFluidOutputs) {
            if (output == null) continue;
            boolean fits = false;
            for (FluidTankGT tank : mOutputTanks) if ((tank.isEmpty() || tank.contains(output)) && tank.amount() + output.amount <= tank.getCapacity()) { fits = true; break; }
            if (!fits) return false;
        }
        for (ItemStack output : recipe.mOutputs) {
            if (output == null) continue;
            if (slot(1) != null && !ST.equal(slot(1), output, T)) return false;
            if (slot(1) != null && slot(1).stackSize + output.stackSize > getInventoryStackLimit()) return false;
        }
        return true;
    }

    @Override public ItemStack[] getDefaultInventory(NBTTagCompound nbt) { return new ItemStack[2]; }
    @Override protected IFluidTank getFluidTankFillable2(byte side, FluidStack fluid) {
        for (FluidTankGT tank : mInputTanks) if (tank.isEmpty() || tank.contains(fluid)) return tank;
        return null;
    }
    @Override protected IFluidTank getFluidTankDrainable2(byte side, FluidStack fluid) {
        for (FluidTankGT tank : mTanks) if (!tank.isEmpty() && (fluid == null || tank.contains(fluid))) return tank;
        return null;
    }
    @Override protected IFluidTank[] getFluidTanks2(byte side) { return mTanks; }

    /** LU arrives through FUS-03 parts whose usage is ONLY_ENERGY_IN. */
    @Override public boolean isEnergyType(TagData type, byte side, boolean emitting) { return !emitting && type == TD.Energy.LU; }
    @Override public long doInject(TagData type, byte side, long size, long amount, boolean inject) {
        size = Math.abs(size);
        if (type != TD.Energy.LU || size == 0 || size > maxPulseInputPacket() || amount <= 0 || !mStructureOkay || mCooldown > 0) return 0;
        long accepted = Math.min(amount, Math.max(0L, getPulseCapacity() - mPulseEnergy) / size);
        if (inject && accepted > 0) {
            mPulseEnergy += accepted * size;
            mState.set(STATE_CHARGING);
        }
        return accepted;
    }
    @Override public long getEnergySizeInputMin(TagData type, byte side) { return 1; }
    @Override public long getEnergySizeInputRecommended(TagData type, byte side) { return maxPulseInputPacket(); }
    @Override public long getEnergySizeInputMax(TagData type, byte side) { return maxPulseInputPacket(); }
    @Override public Collection<TagData> getEnergyTypes(byte side) { return TD.Energy.LU.AS_LIST; }

    @Override public boolean checkStructure2(ChunkCoordinates clicked, Entity player, IInventory inventory) {
        if (!worldObj.blockExists(xCoord, yCoord, zCoord)) return mStructureOkay;
        mLastFailedPos = getStructure().checkStructure(new StructureContext(this,
                (player != null || inventory != null) ? StructureContext.StringBaseMode.SET : StructureContext.StringBaseMode.CHECK,
                worldObj, xCoord, yCoord, zCoord, mFacing, player, inventory));
        return mLastFailedPos == null;
    }

    @Override public boolean isInsideStructure(int x, int y, int z) { return getStructure().isInsideStructure(this, mFacing, x, y, z); }
    @Override public boolean onTickCheck(long timer) { return super.onTickCheck(timer) || mState.isChangedAndClear(); }
    @Override public void addToolTips(List<String> tips, ItemStack stack, boolean f3) {
        tips.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        tips.add(LH.Chat.WHITE + LH.get(I18nHandler.LASER_FUSION_TOOLTIP_PULSE));
        tips.add(LH.Chat.WHITE + LH.get(I18nHandler.LASER_FUSION_TOOLTIP_PEAK));
        super.addToolTips(tips, stack, f3);
    }
    @Override public boolean onBlockActivated3(EntityPlayer player, byte side, float hitX, float hitY, float hitZ) {
        if (!isServerSide()) return true;
        if (!mStructureOkay) player.addChatMessage(new ChatComponentText(LH.Chat.RED + LH.get(I18nHandler.STRUCTURE_ERR)));
        if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemProjector) {
            getStructure().checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT, worldObj, xCoord, yCoord, zCoord, mFacing, player, null));
            return true;
        }
        return super.onBlockActivated3(player, side, hitX, hitY, hitZ);
    }

    private IIconContainer baseIcon() { return new Textures.BlockIcons.CustomIcon(texturePath() + "/base"); }
    @Override public ITexture getTexture2(Block block, int pass, byte side, boolean[] render) {
        if (!render[side]) return null;
        IIconContainer base = baseIcon();
        if (side != mFacing) return BlockTextureDefault.get(base);
        String state = mState.is(STATE_COOLING) ? "cooling" : mState.is(STATE_CHARGING) ? "charging" : mState.is(STATE_ERROR) ? "error" : "idle";
        return BlockTextureMulti.get(BlockTextureDefault.get(base), BlockTextureDefault.get(new Textures.BlockIcons.CustomIcon(texturePath() + "/overlay_" + state)));
    }

    @Override public IPacket getClientDataPacket(boolean aSendAll) {
        return aSendAll ?
                this.getClientDataPacketByteArray(aSendAll, (byte) UT.Code.getR(this.mRGBa), (byte) UT.Code.getG(this.mRGBa), (byte) UT.Code.getB(this.mRGBa), this.getVisualData(), this.getDirectionData(), mState.get()) :
                this.getClientDataPacketByteArray(aSendAll, this.getVisualData(), mState.get());
    }
    @Override public boolean receiveDataByteArray(byte[] data, INetworkHandler network) {
        if (data.length > 2) {
            byte[] array = Arrays.copyOf(data, 5);
            super.receiveDataByteArray(array, network);
            mState.set(data[5]);
        }else{
            super.receiveDataByte(data[0], network);
            mState.set(data[1]);
        }
        return true;
    }
}
