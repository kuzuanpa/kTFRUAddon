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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.research;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchProject;
import cn.kuzuanpa.ktfruaddon.api.research.task.DataAnalysisTask;
import cn.kuzuanpa.ktfruaddon.api.research.task.IResearchTask;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
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
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.WD;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import org.jetbrains.annotations.Nullable;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static gregapi.data.CS.*;

/**
 * 30081. Quantum state analysis station: turns raw observation samples into research progress
 * on the current project's DataAnalysisTask. Samples are the fuel, cluster compute via the
 * structure's compute slot is the running cost.
 */
public class QuantumStateAnalysisStation extends MultiResearchTableBase implements SpecialPartPredicate.IReceiveSpecialPart {
    public static final long COMPUTE_PER_TICK = 65536L;
    public static final int SAMPLES_PER_CYCLE = 1;
    public static final int CYCLE_TICKS = 20;

    public static final String STATUS_NO_MONITOR = "ktfru.analysis.status.no_monitor";
    public static final String STATUS_NO_TASK = "ktfru.analysis.status.no_task";
    public static final String STATUS_NO_SAMPLE = "ktfru.analysis.status.no_sample";
    public static final String STATUS_NO_COMPUTE = "ktfru.analysis.status.no_compute";
    public static final String STATUS_RUNNING = "ktfru.analysis.status.running";
    public static final String STATUS_UNFORMED = "ktfru.analysis.status.unformed";

    static {
        LH.add(STATUS_UNFORMED, "Structure incomplete");
        LH.add(STATUS_NO_MONITOR, "No research monitor bound");
        LH.add(STATUS_NO_TASK, "No pending analysis task in the current project");
        LH.add(STATUS_NO_SAMPLE, "No quantum observation sample in the input slot");
        LH.add(STATUS_NO_COMPUTE, "Insufficient Normal compute");
        LH.add(STATUS_RUNNING, "Analysing samples");
        LH.add("ktfru.analysis.status.compute", "Normal compute per tick:");
        LH.add("ktfru.analysis.status.rate", "Analyses one sample every %d ticks");
        LH.add("ktfru.tooltip.multiblock.quantumstateanalysis.1", "3x3x3 analysis station: 19 coherence walls, 4 quantum readout coils, 1 stabilization substrate, one compute slot and one item/fluid port. Place the controller in the bottom centre.");
        LH.add("ktfru.tooltip.multiblock.quantumstateanalysis.2", "Feed it quantum observation samples (or bind its GUI), bind a research monitor with a data stick, then it turns samples into progress on the current project's analysis task.");
        LH.add("ktfru.tooltip.multiblock.quantumstateanalysis.3", "Consumes one sample and %d Normal compute per tick while running.");
    }

    private static final IStringBaseStructure STRUCTURE = new LayerStructure(StructureContext.Axis.Y).layerRule("ABC")
            .fixedLayer('A',
                    "QIQ",
                    "QKQ",
                    "QCQ")
            .fixedLayer('B',
                    "QRQ",
                    "QGQ",
                    "QRQ")
            .fixedLayer('C',
                    "QQQ",
                    "QQQ",
                    "QQQ")
            .where('Q', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31111, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('R', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31114, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('G', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31112, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('I', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31000, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID, 0)))
            .where('C', new ComputePartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31111, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('K', new ControllerPredicate())
            .setOffset(-1, 0, -1);

    private final List<ChunkCoordinates> computeNodesCoord = new ArrayList<>();
    private long computePowerNeeded = COMPUTE_PER_TICK;
    private boolean computePowerEnough;
    private ChunkCoordinates lastFailedPos;
    private String status = STATUS_UNFORMED;

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

    @Override public boolean isInsideStructure(int aX, int aY, int aZ) {return STRUCTURE.isInsideStructure(this, mFacing, aX, aY, aZ);}

    @Override
    public void receiveSpecialPart(ChunkCoordinates aPartPos, TileEntity aPart) {
        if (aPart instanceof IComputePart) computeNodesCoord.add(aPartPos);
    }

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
        Map<ComputePower, Long> requiredCompute = ICircuitChangeableTileEntity.readRequiredCompute(aNBT);
        if (requiredCompute.containsKey(ComputePower.Normal)) computePowerNeeded = requiredCompute.get(ComputePower.Normal);
    }

    // Tick
    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (!aIsServerSide) return;
        if (aTimer % 20 == 0 || monitor == null) updateMonitor();
        if (!mStructureOkay) {
            computePowerEnough = false;
            status = STATUS_UNFORMED;
            return;
        }
        ResearchProject project = getCurrentProject();
        if (project == null || project.isCompleted) {
            computePowerEnough = false;
            status = STATUS_NO_MONITOR;
            return;
        }
        IResearchTask task = getCurrentTask(project);
        if (task == null) {
            computePowerEnough = false;
            status = STATUS_NO_TASK;
            return;
        }
        computePowerEnough = IComputePart.tryStartForDemand(getComputeNodes(), ComputePower.Normal, computePowerNeeded);
        if (!computePowerEnough) {
            getComputeNodes().forEach(IComputePart::stop);
            status = STATUS_NO_COMPUTE;
            return;
        }
        if (!hasSample()) {
            status = STATUS_NO_SAMPLE;
            return;
        }
        if (aTimer % CYCLE_TICKS != 0) {
            status = STATUS_RUNNING;
            return;
        }
        if (consumeSample(SAMPLES_PER_CYCLE)) {
            tryPromoteCurrentProjectProgress(DataAnalysisTask.class, (long) SAMPLES_PER_CYCLE, false);
        }
        status = STATUS_RUNNING;
    }

    protected @Nullable IResearchTask getCurrentTask(ResearchProject aProject) {
        for (IResearchTask task : aProject.getTasks()) if (task instanceof DataAnalysisTask && !task.isCompleted()) return task;
        return null;
    }

    private boolean hasSample() {
        ItemStack sample = slot(0);
        if (sample == null) return false;
        ItemStack expected = ItemList.QuantumObservationData.get(1);
        return expected != null && sample.getItem() == expected.getItem() && sample.getItemDamage() == expected.getItemDamage();
    }

    private boolean consumeSample(int aAmount) {
        if (!hasSample()) return false;
        if (slot(0).stackSize <= aAmount) slotKill(0);
        else slot(0).stackSize -= aAmount;
        return true;
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide()) return true;
        if (!mStructureOkay) aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED + LH.get(I18nHandler.STRUCTURE_ERR)));
        ItemStack equippedItem = aPlayer.getCurrentEquippedItem();
        if (equippedItem != null && equippedItem.getItem() instanceof ItemProjector) {
            STRUCTURE.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }
        if (equippedItem != null && bindMonitorFromUSB(aPlayer)) return true;
        return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public void onMagnifyingGlass2(List<String> aChatReturn) {
        super.onMagnifyingGlass2(aChatReturn);
        aChatReturn.add(LH.get(status));
        aChatReturn.add(LH.get("ktfru.analysis.status.compute") + " " + computePowerNeeded + "/t");
        aChatReturn.add(String.format(LH.get("ktfru.analysis.status.rate"), CYCLE_TICKS));
        if (lastFailedPos != null) aChatReturn.add(LH.get(I18nHandler.STRUCTURE_LAST_FAILED_POS) + " " + lastFailedPos);
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.quantumstateanalysis.1"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.quantumstateanalysis.2"));
        aList.add(LH.Chat.WHITE + String.format(LH.get("ktfru.tooltip.multiblock.quantumstateanalysis.3"), computePowerNeeded));
        super.addToolTips(aList, aStack, aF3_H);
    }

    // Inventory / GUI
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) { return new ItemStack[1]; }
    @Override public boolean canDrop(int aSlot) { return true; }
    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) { return new int[]{0}; }
    @Override public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {
        if (aSlot != 0) return false;
        ItemStack expected = ItemList.QuantumObservationData.get(1);
        return expected != null && aStack.getItem() == expected.getItem() && aStack.getItemDamage() == expected.getItemDamage();
    }
    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) { return false; }
    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) { return new ContainerClientDefault(aPlayer.inventory, this, aGUIID); }
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) { return new ContainerCommonDefault(aPlayer.inventory, this, aGUIID); }

    @Override
    public boolean breakBlock() {
        if (isServerSide()) {
            getComputeNodes().forEach(IComputePart::stop);
            STRUCTURE.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.RESET, worldObj, xCoord, yCoord, zCoord, mFacing, null, null));
        }
        return super.breakBlock();
    }

    @Override public String getTileEntityName() { return "ktfru.multitileentity.30081"; }
}
