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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.ai;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchProject;
import cn.kuzuanpa.ktfruaddon.api.research.task.IResearchTask;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterController;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterUser;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.research.MultiResearchTableBase;
import cn.kuzuanpa.ktfruaddon.tile.research.ResearchTreeMonitor;
import gregapi.data.LH;
import gregapi.util.UT;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants.STATE_NORMAL;
import static cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants.STATE_OFFLINE;
import static gregapi.data.CS.SIDE_EAST;
import static gregapi.data.CS.SIDE_NORTH;
import static gregapi.data.CS.SIDE_SOUTH;
import static gregapi.data.CS.SIDE_WEST;
import static gregapi.data.CS.TOOL_magnifyingglass;

/** Shared automation controller for the three AI research minigame replacements. */
public abstract class AiResearchControllerBase extends MultiResearchTableBase implements IComputerClusterUser {
    public static final long AI_COMPUTE_PER_TICK = 67108864L;
    public static final String STATUS_IDLE = "ktfru.ai.status.idle";
    public static final String STATUS_UNBOUND = "ktfru.ai.status.unbound";
    public static final String STATUS_OFFLINE = "ktfru.ai.status.offline";
    public static final String STATUS_NO_MONITOR = "ktfru.ai.status.no_monitor";
    public static final String STATUS_NO_TASK = "ktfru.ai.status.no_task";
    public static final String STATUS_INSUFFICIENT = "ktfru.ai.status.insufficient";
    public static final String STATUS_RUNNING = "ktfru.ai.status.running";

    static {
        LH.add(STATUS_IDLE, "Idle");
        LH.add(STATUS_UNBOUND, "No compute cluster bound");
        LH.add(STATUS_OFFLINE, "Compute cluster offline");
        LH.add(STATUS_NO_MONITOR, "No research monitor bound");
        LH.add(STATUS_NO_TASK, "No matching unfinished minigame");
        LH.add(STATUS_INSUFFICIENT, "Insufficient Normal compute");
        LH.add(STATUS_RUNNING, "Running");
        LH.add("ktfru.tooltip.multiblock.ai.0", "Bind a controller with a data stick while sneaking, then bind a research monitor without sneaking.");
        LH.add("ktfru.tooltip.multiblock.ai.1", String.format("Automatically generates matching minigame points while consuming %,d Normal compute/tick.", AI_COMPUTE_PER_TICK));
        LH.add("ktfru.ai.status", "AI research status");
        LH.add("ktfru.ai.rate", "AI output: %,d point(s)/tick");
        LH.add("ktfru.ai.compute", "Normal compute needed: %,d/tick");
    }

    protected @Nullable IComputerClusterController computeController;
    protected List<IComputerClusterController> backupComputeControllers = new ArrayList<>();
    protected @Nullable UUID computeUserUUID;
    protected long requestedComputePower;
    protected boolean computePowerAllocated;
    protected String computeStatus = STATUS_IDLE;
    protected ChunkCoordinates lastFailedPos;
    private static final byte[] MONITOR_SIDES = {SIDE_NORTH, SIDE_SOUTH, SIDE_WEST, SIDE_EAST};

    public abstract IStringBaseStructure getStructure();
    protected abstract Class<? extends IResearchTask> getTaskType();
    protected abstract long getPointsPerTick();
    protected abstract long getComputePerPoint();

    // NBT
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        IComputerClusterUser.readFromNBT(aNBT, this);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        IComputerClusterUser.writeToNBT(aNBT, this);
    }

    // Structure
    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        if (!worldObj.blockExists(xCoord, yCoord, zCoord)) return mStructureOkay;
        lastFailedPos = getStructure().checkStructure(new StructureContext(this, (aPlayer != null || aInventory != null) ? StructureContext.StringBaseMode.SET : StructureContext.StringBaseMode.CHECK, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        return lastFailedPos == null;
    }

    @Override public boolean isInsideStructure(int aX, int aY, int aZ) {return getStructure().isInsideStructure(this, mFacing, aX, aY, aZ);}

    @Override
    public void onStructureChange() {
        super.onStructureChange();
        if (isServerSide()) stopCompute();
    }

    // Tick
    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (!aIsServerSide) return;
        if (aTimer % 20 == 0 || monitor == null) updateResearchMonitor();
        updateResearchAutomation();
    }

    protected void updateResearchMonitor() {
        updateMonitor();
        if (monitor != null && !monitor.isInvalid()) return;
        if (worldObj == null) return;
        for (byte side : MONITOR_SIDES) {
            TileEntity tile = getTileEntityAtSideAndDistance(side, 1);
            if (!(tile instanceof ResearchTreeMonitor)) continue;
            monitor = (ResearchTreeMonitor) tile;
            monitorWorldID = tile.getWorldObj().provider.dimensionId;
            monitorCoord = new ChunkCoordinates(tile.xCoord, tile.yCoord, tile.zCoord);
            markDirty();
            return;
        }
    }

    protected void updateResearchAutomation() {
        if (!mStructureOkay) {
            stopWithStatus(STATUS_OFFLINE);
            return;
        }
        ResearchProject project = getCurrentProject();
        if (project == null || project.isCompleted) {
            stopWithStatus(STATUS_NO_MONITOR);
            return;
        }
        IResearchTask task = getCurrentTask(project);
        if (task == null) {
            stopWithStatus(STATUS_NO_TASK);
            return;
        }

        long remaining = Math.max(0L, task.getRequiredProgress() - task.getProgress());
        if (remaining <= 0L) {
            stopWithStatus(STATUS_NO_TASK);
            return;
        }
        long points = Math.min(getPointsPerTick(), remaining);
        requestedComputePower = safeMultiply(points, getComputePerPoint());
        computeStatus = STATUS_RUNNING;

        if (computeController == null) {
            stopWithStatus(STATUS_UNBOUND);
            return;
        }
        if (computeController.getCluster() == null) {
            stopWithStatus(STATUS_OFFLINE);
            return;
        }
        if (!tryAllocateCompute()) {
            stopWithStatus(STATUS_INSUFFICIENT);
            return;
        }

        long consumed = tryPromoteCurrentProjectProgress(getTaskType(), points, false);
        if (consumed <= 0L || task.isCompleted()) stopCompute();
    }

    protected @Nullable IResearchTask getCurrentTask(ResearchProject project) {
        for (IResearchTask task : project.getTasks()) {
            if (getTaskType().isInstance(task) && !task.isCompleted()) return task;
        }
        return null;
    }

    protected boolean tryAllocateCompute() {
        if (!computePowerAllocated) {
            if (!IComputerClusterUser.super.tryStart()) return false;
            computePowerAllocated = true;
            return true;
        }
        return IComputerClusterUser.super.tryStart();
    }

    protected static long safeMultiply(long amount, long multiplier) {
        if (amount <= 0L || multiplier <= 0L) return 0L;
        if (amount > Long.MAX_VALUE / multiplier) return Long.MAX_VALUE;
        return amount * multiplier;
    }

    protected void stopWithStatus(String status) {
        stopCompute();
        computeStatus = status;
    }

    protected void stopCompute() {
        if (computePowerAllocated) IComputerClusterUser.super.tryStop();
        computePowerAllocated = false;
        requestedComputePower = 0L;
    }

    @Override
    public boolean breakBlock() {
        if (isServerSide()) stopCompute();
        return super.breakBlock();
    }

    // GUI / Tooltip
    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide()) return true;
        if (!mStructureOkay) aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED + LH.get(I18nHandler.STRUCTURE_ERR)));
        ItemStack equippedItem = aPlayer.getCurrentEquippedItem();
        if (equippedItem != null && equippedItem.getItem() instanceof ItemProjector) {
            getStructure().checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }
        if (aPlayer.isSneaking()) IComputerClusterUser.bindControllerFromUSB(aPlayer, this);
        else bindMonitorFromUSB(aPlayer);
        return true;
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (TOOL_magnifyingglass.equals(aTool) && aChatReturn != null) {
            aChatReturn.add(LH.get("ktfru.ai.status") + ": " + LH.get(computeStatus));
            aChatReturn.add(String.format(LH.get("ktfru.ai.rate"), getPointsPerTick()));
            aChatReturn.add(String.format(LH.get("ktfru.ai.compute"), safeMultiply(getPointsPerTick(), getComputePerPoint())));
            return 1;
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.ai.0"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.ai.1"));
        super.addToolTips(aList, aStack, aF3_H);
    }

    // Compute user
    @Override public @Nullable IComputerClusterController getController() {return computeController;}
    @Override public void setController(IComputerClusterController controller) {computeController = controller;}
    @Override public List<IComputerClusterController> getBackupControllers() {return backupComputeControllers;}
    @Override public void setBackupControllers(List<IComputerClusterController> list) {backupComputeControllers = list == null ? new ArrayList<IComputerClusterController>() : list;}
    @Override public byte getState() {return requestedComputePower > 0L ? STATE_NORMAL : STATE_OFFLINE;}
    @Override public Map<ComputePower, Long> getComputePowerNeeded() {return ComputePower.Normal.asMap(requestedComputePower);}
    @Override public @Nullable UUID getUUID() {return computeUserUUID;}
    @Override public void setUUID(UUID uuid) {computeUserUUID = uuid;}
    @Override public void onComputerPowerReleased() {computePowerAllocated = false; requestedComputePower = 0L; computeStatus = STATUS_INSUFFICIENT;}
}
