/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 */
package cn.kuzuanpa.ktfruaddon.tile.machine;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.recipe.IKortexHandler;
import cn.kuzuanpa.ktfruaddon.api.recipe.KortexWorker;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.base.TileEntityBaseControlledMachine;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ICircuitChangeableTileEntity;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputePart;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.ControllerPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.SpecialPartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.special.ComputePartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.fluid.FluidTankGT;
import gregapi.gui.ContainerClientDefault;
import gregapi.gui.ContainerCommonDefault;
import gregapi.recipes.Recipe;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.ST;
import gregapi.util.UT;
import gregapi.util.WD;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static gregapi.data.CS.*;

/**
 * 30074. Paired relay: two 3x3x3 relays in the same dimension form one entanglement channel.
 * The primary end (lowest dimension/x/y/z) runs the producing recipe and owns the QU output,
 * the other end only supplies its own analytical-pure material and LU.
 */
public class QuantumCommunicationRelay extends TileEntityBaseControlledMachine implements IKortexHandler, ITileEntityEnergy, SpecialPartPredicate.IReceiveSpecialPart {
    public static final long QU_PER_TICK = 128;
    public static final long QU_PER_RECIPE = 65536;
    public static final long ENERGY_CAPACITY = 32768000L;
    public static final long DEFAULT_COMPUTE = 65536L;

    public static final String STATUS_UNFORMED = "ktfru.quantum_relay.status.unformed";
    public static final String STATUS_UNPAIRED = "ktfru.quantum_relay.status.unpaired";
    public static final String STATUS_IDLE = "ktfru.quantum_relay.status.idle";
    public static final String STATUS_RUNNING = "ktfru.quantum_relay.status.running";
    public static final String STATUS_NO_COMPUTE = "ktfru.quantum_relay.status.no_compute";

    static {
        LH.add(STATUS_UNFORMED, "Structure incomplete");
        LH.add(STATUS_UNPAIRED, "No partner relay paired");
        LH.add(STATUS_IDLE, "Paired, waiting for materials/energy");
        LH.add(STATUS_RUNNING, "Channel running");
        LH.add(STATUS_NO_COMPUTE, "Insufficient Normal compute");
        LH.add("ktfru.quantum_relay.info.energy", "Energy per tick (primary EU / other end LU):");
        LH.add("ktfru.quantum_relay.info.compute", "Normal compute per tick:");
        LH.add("ktfru.quantum_relay.info.qu", "QU buffered / per completed run:");
        LH.add("ktfru.tooltip.multiblock.quantumrelay.1", "3x3x3 relay: coherence walls with two entanglement channel modules, one compute slot, one energy port and one item/fluid port. Place the controller in the bottom centre.");
        LH.add("ktfru.tooltip.multiblock.quantumrelay.2", "Pair two relays in the same dimension with a screwdriver: the lower one runs the producing recipe on EU, the other one supplies its own analytical-pure material on LU.");
        LH.add("ktfru.tooltip.multiblock.quantumrelay.3", "A completed run consumes both materials and outputs 1 Mirror Gold ingot plus 65,536 QU at the controller.");
    }

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

    private static final IStringBaseStructure STRUCTURE = new LayerStructure(StructureContext.Axis.Y).layerRule("ABC")
            .fixedLayer('A',
                    "QEQ",
                    "QKQ",
                    "QCQ")
            .fixedLayer('B',
                    "QHQ",
                    "QIQ",
                    "QHQ")
            .fixedLayer('C',
                    "QQQ",
                    "QQQ",
                    "QQQ")
            .where('Q', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31111, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('H', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31113, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('E', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31000, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN, 0)))
            .where('I', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31000, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID, 0)))
            .where('C', new ComputePartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31111, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('K', new ControllerPredicate())
            .setOffset(-1, 0, -1);

    private KortexWorker kortex;
    private long mQUStored;
    private long computePowerNeeded = DEFAULT_COMPUTE;
    private boolean computePowerEnough;
    private final List<ChunkCoordinates> computeNodesCoord = new ArrayList<>();
    private ChunkCoordinates lastFailedPos;
    /** 0=unformed/offline, 1=paired idle, 2=running. */
    private byte mState;
    private byte mStateOld;
    private boolean mLinked;
    private int mPartnerDimension;
    private int mPartnerX;
    private int mPartnerY;
    private int mPartnerZ;

    // Structure
    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        if (!worldObj.blockExists(xCoord, yCoord, zCoord)) return mStructureOkay;
        computeNodesCoord.clear();
        lastFailedPos = STRUCTURE.checkStructure(new StructureContext(this,
                (aPlayer != null || aInventory != null) ? StructureContext.StringBaseMode.SET : StructureContext.StringBaseMode.CHECK,
                worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        return lastFailedPos == null;
    }

    private void resetParts() {
        if (!worldObj.blockExists(xCoord, yCoord, zCoord)) return;
        STRUCTURE.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.RESET,
                worldObj, xCoord, yCoord, zCoord, mFacing, null, null));
    }

    @Override public boolean isInsideStructure(int aX, int aY, int aZ) {return STRUCTURE.isInsideStructure(this, mFacing, aX, aY, aZ);}

    @Override
    public void receiveSpecialPart(ChunkCoordinates aPartPos, TileEntity aPart) {
        if (aPart instanceof IComputePart) computeNodesCoord.add(aPartPos);
    }

    /**Resolves the compute node parts of this structure, skipping the ones that are gone or not loaded.**/
    protected List<IComputePart> getComputeNodes() {
        List<IComputePart> nodes = new ArrayList<>();
        for (ChunkCoordinates coord : computeNodesCoord) {
            TileEntity tile = WD.te(worldObj, coord, true);
            if (tile instanceof IComputePart) nodes.add((IComputePart) tile);
        }
        return nodes;
    }

    // NBT
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        Recipe.RecipeMap recipes = recipeMaps.QuantumCommunication;
        if (aNBT.hasKey(NBT_RECIPEMAP)) {
            Recipe.RecipeMap configured = Recipe.RecipeMap.RECIPE_MAPS.get(aNBT.getString(NBT_RECIPEMAP));
            if (configured != null) recipes = configured;
        }
        // The worker refuses to start while its buffer is exactly full (parallel calc divides by the
        // remaining capacity), so the working buffer stops at ENERGY_CAPACITY while the hard limit stays above it.
        kortex = new KortexWorker(this, recipes).setEnergyCapacity(ENERGY_CAPACITY * 2).setMaxProgressPerTick(1).consumeInputsOnFinish().dontResetProgressWhenPowerLost();
        kortex.readFromNBT(aNBT);
        mQUStored = aNBT.getLong(NBT_QU);
        mLinked = aNBT.getBoolean(NBT_LINKED);
        mPartnerDimension = aNBT.getInteger(NBT_DIM);
        mPartnerX = aNBT.getInteger(NBT_X);
        mPartnerY = aNBT.getInteger(NBT_Y);
        mPartnerZ = aNBT.getInteger(NBT_Z);

        Map<ComputePower, Long> requiredCompute = ICircuitChangeableTileEntity.readRequiredCompute(aNBT);
        if (requiredCompute.containsKey(ComputePower.Normal)) computePowerNeeded = requiredCompute.get(ComputePower.Normal);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        if (kortex != null) kortex.writeToNBT(aNBT);
        UT.NBT.setNumber(aNBT, NBT_QU, mQUStored);
        aNBT.setBoolean(NBT_LINKED, mLinked);
        aNBT.setInteger(NBT_DIM, mPartnerDimension);
        aNBT.setInteger(NBT_X, mPartnerX);
        aNBT.setInteger(NBT_Y, mPartnerY);
        aNBT.setInteger(NBT_Z, mPartnerZ);
    }

    /**The recipe core would otherwise run on its own; the relay drives both ends through the pair instead.**/
    @Override public int checkRecipe(boolean aApplyRecipe, boolean aUseAutoIO) {return 0;}

    // Tick
    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (!aIsServerSide) return;
        emitQu();
        if (kortex == null) return;
        if (!mStructureOkay) {
            computePowerEnough = false;
            setState((byte) 0);
            return;
        }
        computePowerEnough = IComputePart.tryStartForDemand(getComputeNodes(), ComputePower.Normal, computePowerNeeded);
        if (!computePowerEnough) {
            getComputeNodes().forEach(IComputePart::stop);
            setPairState((byte) 1);
            return;
        }

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
        // Either end may hold the producing material; exactly one end must own the output recipe.
        return hasItemOutput(primaryRecipe) != hasItemOutput(partnerRecipe);
    }

    private boolean hasItemOutput(Recipe aRecipe) {
        if (aRecipe == null || aRecipe.mOutputs == null) return false;
        for (ItemStack item : aRecipe.mOutputs) if (item != null) return true;
        return false;
    }

    private void resetWorkers(QuantumCommunicationRelay aPartner) {
        kortex.resetStatus();
        aPartner.kortex.resetStatus();
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
        if (TOOL_magnifyingglass.equals(aTool) && aChatReturn != null) {
            addStatus(aChatReturn);
            return 1;
        }
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

    private void addStatus(List<String> aChatReturn) {
        if (!mStructureOkay) {
            aChatReturn.add(LH.Chat.RED + LH.get(STATUS_UNFORMED)
                    + (lastFailedPos == null ? "" : " " + LH.get(I18nHandler.STRUCTURE_LAST_FAILED_POS) + " " + lastFailedPos));
            return;
        }
        QuantumCommunicationRelay partner = getPartner();
        if (partner == null) aChatReturn.add(LH.Chat.YELLOW + LH.get(STATUS_UNPAIRED));
        else if (mState == 2) aChatReturn.add(LH.Chat.GREEN + LH.get(STATUS_RUNNING));
        else aChatReturn.add(LH.Chat.YELLOW + LH.get(STATUS_IDLE) + (computePowerEnough ? "" : " - " + LH.get(STATUS_NO_COMPUTE)));

        aChatReturn.add(LH.Chat.WHITE + "role: " + (partner == null ? "unpaired" : (isPrimary() ? "primary end" : "other end"))
                + ", this end: " + recipeKind(kortex == null ? null : kortex.findRecipe()));
        aChatReturn.add(LH.Chat.WHITE + "compute: " + (computePowerEnough ? "ok" : "LOW") + " " + computePowerNeeded + "/t");
        aChatReturn.add(LH.Chat.WHITE + "energy buffered: " + (kortex == null ? 0 : kortex.mEnergyStored) + " / need " + getRecipeEnergyPerTick() + "/t");
        aChatReturn.add(LH.Chat.WHITE + "input slot: " + describe(slot(0)) + " -> recipe: " + recipeKind(kortex == null ? null : kortex.findRecipe()));
        if (partner != null) {
            aChatReturn.add(LH.Chat.WHITE + "partner slot: " + describe(partner.slot(0)) + " -> recipe: " + recipeKind(partner.kortex == null ? null : partner.kortex.findRecipe()));
            aChatReturn.add(LH.Chat.WHITE + "partner energy: " + (partner.kortex == null ? 0 : partner.kortex.mEnergyStored) + " / need " + partner.getRecipeEnergyPerTick() + "/t");
        }
        aChatReturn.add(LH.Chat.WHITE + LH.get("ktfru.quantum_relay.info.qu") + " " + mQUStored + " / " + QU_PER_RECIPE + " per run");
        aChatReturn.add(LH.Chat.YELLOW + "blocker: " + blockerReason(partner));
    }

    private String describe(ItemStack aStack) {
        return aStack == null ? "-" : aStack.stackSize + "x " + aStack.getDisplayName();
    }

    /**output-run = producing end's recipe, consume-only = the paired end's recipe.**/
    private String recipeKind(Recipe aRecipe) {
        if (aRecipe == null) return "none";
        return hasItemOutput(aRecipe) ? "output-run" : "consume-only";
    }

    private String blockerReason(QuantumCommunicationRelay aPartner) {
        if (kortex == null) return "kortex missing";
        if (aPartner == null) return "not paired";
        if (!computePowerEnough) return "compute (needs " + computePowerNeeded + "/t)";
        if (!kortex.canStartRecipe()) return "this end: no matching recipe "
                + (slot(0) == null ? "(input slot empty)" : "(" + slot(0).stackSize + "x, recipe needs 4 of it)");
        if (!aPartner.kortex.canStartRecipe()) return "partner: no matching recipe "
                + (aPartner.slot(0) == null ? "(partner input slot empty)" : "(" + aPartner.slot(0).stackSize + "x, recipe needs 4 of it)");
        Recipe mine = kortex.findRecipe(), theirs = aPartner.kortex.findRecipe();
        if (mine == null) return "this end recipe null";
        if (theirs == null) return "partner recipe null";
        if (hasItemOutput(mine) == hasItemOutput(theirs)) return "both ends hold the same kind of recipe - one end needs analytical-pure Intellite, the other analytical-pure Nq_522";
        if (!canAcceptPotentialOutputs()) return "this end cannot accept the Mirror Gold output";
        if (!kortex.hasEnergyForNextTick()) return "this end energy (" + kortex.mEnergyStored + " buffered)";
        if (!aPartner.kortex.hasEnergyForNextTick()) return "partner energy (" + aPartner.kortex.mEnergyStored + " buffered)";
        return "none";
    }

    @Override
    public void onMagnifyingGlass2(List<String> aChatReturn) {
        super.onMagnifyingGlass2(aChatReturn);
        addStatus(aChatReturn);
    }

    // Recipe bridge
    @Override public int getMaxParallel(int aKortexID, long aEUT, long aDuration) { return 1; }

    @Override public void onRecipeFinish(int aKortexID, long aEUT, long aDuration, long aParallel) {
        boolean outputFound = false;
        for (ItemStack item : kortex.getRecipeOutputItems()) if (item != null) {
            outputFound = true;
            break;
        }
        if (!outputFound) return;
        mQUStored = Math.min(ENERGY_CAPACITY, mQUStored + QU_PER_RECIPE);
    }

    @Override public void receiveOutputs(FluidTankGT[] aFluidTanks, ItemStack[] aItems) {
        if (kortex != null && kortex.itemInputs.length > 0) {
            if (kortex.itemInputs[0] == null) slotKill(0); else slot(0, kortex.itemInputs[0]);
        }
        for (int i = 0; i < aItems.length; i++) {
            ItemStack item = aItems[i];
            if (item == null) continue;
            // leave the stack in the worker when the output slot cannot take it, it is retried with outputBlocked
            if (!canAcceptOutput(item)) continue;
            if (slot(1) == null) slot(1, ST.copy(item));
            else slot(1).stackSize += item.stackSize;
            aItems[i] = null;
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
    /**The basic-machine base only accepts energy on configured faces; the relay decides by role (primary EU / other end LU) instead.**/
    @Override public boolean isEnergyAcceptingFrom(TagData aEnergyType, byte aSide, boolean aTheoretical) {return isEnergyType(aEnergyType, aSide, F);}
    /**Both ends are equal: either one may be fed EU or LU, the material in the input slot decides who produces.**/
    @Override public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {
        if (aEmitting) return aEnergyType == TD.Energy.QU;
        return aEnergyType == TD.Energy.EU || aEnergyType == TD.Energy.LU;
    }
    @Override public boolean isEnergyEmittingTo(TagData aEnergyType, byte aSide, boolean aTheoretical) { return aEnergyType == TD.Energy.QU; }
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
        long injectableEnergy = Math.max(0L, ENERGY_CAPACITY - kortex.mEnergyStored);
        long acceptedEnergy = Math.min(injectableEnergy, aSize * aAmount);
        long acceptedPackets = Math.min(aAmount, acceptedEnergy / aSize + (acceptedEnergy % aSize == 0 ? 0 : 1));
        if (aDoInject && acceptedPackets > 0) kortex.injectEnergy(acceptedPackets * aSize);
        return acceptedPackets;
    }

    // Inventory / IO / GUI
    @Override public boolean canDrop(int aSlot) { return true; }
    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) { return new int[]{0, 1}; }
    @Override public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) { return aSlot == 0 && kortex != null && kortex.recipeMap.containsInput(aStack, this, slot(1)); }
    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) { return aSlot == 1; }

    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) { return new ContainerClientDefault(aPlayer.inventory, this, aGUIID); }
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) { return new ContainerCommonDefault(aPlayer.inventory, this, aGUIID); }

    @Override public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte aSide, Fluid aOutput) { return null; }
    @Override public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte aSide) { return null; }
    @Override public DelegatorTileEntity<IInventory> getItemInputTarget(byte aSide) { return new DelegatorTileEntity<IInventory>(this, SIDE_FRONT); }
    @Override public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) { return delegator(SIDE_BOTTOM); }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide()) return true;
        ItemStack equippedItem = aPlayer.getCurrentEquippedItem();
        if (equippedItem != null && equippedItem.getItem() instanceof ItemProjector) {
            STRUCTURE.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }
        return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.quantumrelay.1"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.quantumrelay.2"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.quantumrelay.3"));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public boolean breakBlock() {
        if (isServerSide()) {
            setStateOnOff(T);
            getComputeNodes().forEach(IComputePart::stop);
            resetParts();
        }
        return super.breakBlock();
    }

    @Override public boolean onTickCheck(long aTimer) { if (mStateOld != mState) { mStateOld = mState; return true; } return super.onTickCheck(aTimer); }
    @Override public byte getVisualData() { return mState; }
    @Override public void setVisualData(byte aData) { mState = aData; }
    @Override public String getTileEntityName() { return "ktfru.multitileentity.30074"; }
}
