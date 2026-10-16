/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 */
package cn.kuzuanpa.ktfruaddon.tile.multiblock.machine;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
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
import gregapi.data.LH;
import gregapi.gui.ContainerClientDefault;
import gregapi.gui.ContainerCommonDefault;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.WD;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import static gregapi.data.CS.*;

/** Q3-01. Fixed 3x3x5 observation tower; the controller occupies K in the middle layer. */
public class QuantumObservationTower extends TileEntityBaseControlledMachine implements SpecialPartPredicate.IReceiveSpecialPart {
    private ChunkCoordinates lastFailedPos;
    private final List<ChunkCoordinates> computeNodesCoord = new ArrayList<>();
    private long computePowerNeeded = 65536L;
    private boolean computePowerEnough;

    static {
        LH.add("ktfru.tooltip.multiblock.quantumobservation.4", "Needs a compute part in the floor slot and 65,536 Normal compute/tick while running.");
        LH.add("ktfru.tooltip.multiblock.quantumobservation.5", "Energy port accepts 16,384 EU/t; observation data leaves through the item/fluid port.");
        LH.add("ktfru.observation.status.compute", "Normal compute per tick:");
        LH.add("ktfru.observation.status.insufficient", "Insufficient Normal compute");
        LH.add("ktfru.observation.status.ready", "Structure complete, compute available");
    }

    private static final IStringBaseStructure STRUCTURE = new LayerStructure(StructureContext.Axis.Y).layerRule("ABCDE")
            .fixedLayer('A',
                    "WWW",
                    "WXW",
                    "WEW")
            .fixedLayer('B',
                    "WIW",
                    "BSB",
                    "WIW")
            .fixedLayer('C',
                    "RCR",
                    "DKS",
                    "RCR")
            .fixedLayer('D',
                    "WIW",
                    "BSB",
                    "WIW")
            .fixedLayer('E',
                    "WWW",
                    "WEW",
                    "WWW")
            .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31111, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('B', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31112, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('C', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31113, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('R', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31114, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('D', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31115, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('S', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31116, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('E', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31000, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN, 0)))
            .where('I', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31000, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID, 0)))
            .where('X', new ComputePartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31111, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('K', new ControllerPredicate())
            .setOffset(-1, -2, -1);

    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        if (!worldObj.blockExists(xCoord, yCoord, zCoord)) return mStructureOkay;
        boolean building = aPlayer != null || aInventory != null;
        computeNodesCoord.clear();
        lastFailedPos = STRUCTURE.checkStructure(new StructureContext(this,
                building ? StructureContext.StringBaseMode.SET : StructureContext.StringBaseMode.CHECK,
                worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        if (lastFailedPos != null && building) resetParts();
        return lastFailedPos == null;
    }

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

    @Override
    public void readFromNBT2(net.minecraft.nbt.NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        Map<ComputePower, Long> requiredCompute = ICircuitChangeableTileEntity.readRequiredCompute(aNBT);
        if (requiredCompute.containsKey(ComputePower.Normal)) computePowerNeeded = requiredCompute.get(ComputePower.Normal);
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (!aIsServerSide) return;
        computePowerEnough = mStructureOkay && IComputePart.tryStartForDemand(getComputeNodes(), ComputePower.Normal, computePowerNeeded);
        if (!computePowerEnough) getComputeNodes().forEach(IComputePart::stop);
        setStateOnOff(isControllerAllValid() && computePowerEnough);
    }

    @Override
    public void onMagnifyingGlass2(List<String> aChatReturn) {
        super.onMagnifyingGlass2(aChatReturn);
        aChatReturn.add(LH.get("ktfru.observation.status.compute") + " " + computePowerNeeded + "/t");
        aChatReturn.add(computePowerEnough ? LH.get("ktfru.observation.status.ready") : LH.get("ktfru.observation.status.insufficient"));
        if (lastFailedPos != null) aChatReturn.add(LH.get(I18nHandler.STRUCTURE_LAST_FAILED_POS) + " " + lastFailedPos);
    }

    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) { return new ContainerClientDefault(aPlayer.inventory, this, aGUIID); }
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) { return new ContainerCommonDefault(aPlayer.inventory, this, aGUIID); }

    private void resetParts() {
        if (!worldObj.blockExists(xCoord, yCoord, zCoord)) return;
        STRUCTURE.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.RESET,
                worldObj, xCoord, yCoord, zCoord, mFacing, null, null));
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide()) return true;
        if (!mStructureOkay) aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED + LH.get(I18nHandler.STRUCTURE_ERR)));
        ItemStack equipped = aPlayer.getCurrentEquippedItem();
        if (equipped != null && equipped.getItem() instanceof ItemProjector) {
            STRUCTURE.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT,
                    worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }
        return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.quantumobservation.1"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.quantumobservation.2"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.quantumobservation.3"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.quantumobservation.4"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.quantumobservation.5"));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return STRUCTURE.isInsideStructure(this, mFacing, aX, aY, aZ);
    }

    /**The basic-machine base only accepts energy on configured faces; the tower always takes its EU through the structure port.**/
    @Override public boolean isEnergyAcceptingFrom(gregapi.code.TagData aEnergyType, byte aSide, boolean aTheoretical) {return isEnergyType(aEnergyType, aSide, F);}

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte aSide, Fluid aOutput) {
        return null;
    }

    @Override
    public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) {
        return delegator(SIDE_BOTTOM);
    }

    @Override
    public DelegatorTileEntity<IInventory> getItemInputTarget(byte aSide) {
        return new DelegatorTileEntity<IInventory>(this, SIDE_FRONT);
    }

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte aSide) {
        return null;
    }

    @Override
    public boolean breakBlock() {
        setStateOnOff(T);
        GarbageGT.trash(mTanksInput);
        GarbageGT.trash(mTanksOutput);
        GarbageGT.trash(mOutputItems);
        GarbageGT.trash(mOutputFluids);
        resetParts();
        return super.breakBlock();
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.30080";
    }
}
