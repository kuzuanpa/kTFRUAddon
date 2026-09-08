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

package cn.kuzuanpa.ktfruaddon.tile.research;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchProject;
import cn.kuzuanpa.ktfruaddon.api.research.task.ComputeTask;
import cn.kuzuanpa.ktfruaddon.api.research.task.IResearchTask;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputerCluster;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterController;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterUser;
import gregapi.data.CS;
import gregapi.data.LH;
import gregapi.gui.ContainerClientDefault;
import gregapi.gui.ContainerCommonDefault;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants.STATE_NORMAL;
import static cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants.STATE_OFFLINE;

/**Consumes the matching type of cluster compute power to advance ComputeTask research.*/
public class ResearchTableCompute extends ResearchTableBase implements IComputerClusterUser {
    private @Nullable IComputerClusterController computeController;
    private List<IComputerClusterController> backupComputeControllers = new ArrayList<>();
    private @Nullable UUID computeUserUUID;
    private @Nullable ComputeTask activeComputeTask;
    private long requestedComputePower;
    private boolean computePowerAllocated;
    private String computeStatus = I18nHandler.RESEARCH_COMPUTE_STATUS_IDLE;

    @Override public String getTileEntityName() {return "ktfru.multitileentity.research.table.compute";}
    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {return new ContainerClientDefault(aPlayer.inventory, this, aGUIID);}
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {return new ContainerCommonDefault(aPlayer.inventory, this, aGUIID);}

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (isServerSide() && aPlayer.isSneaking()) return IComputerClusterUser.bindControllerFromUSB(aPlayer, this);
        return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
    }

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

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (aIsServerSide) updateComputeResearch();
    }

    @Override
    public boolean breakBlock() {
        if (isServerSide()) stopComputeResearch();
        return super.breakBlock();
    }

    private void updateComputeResearch() {
        ComputeTask task = getCurrentComputeTask(getCurrentProject());
        if (task == null) {
            stopComputeResearch();
            computeStatus = I18nHandler.RESEARCH_COMPUTE_STATUS_IDLE;
            return;
        }
        if (activeComputeTask != task) {
            stopComputeResearch();
            activeComputeTask = task;
        }
        if (computePowerAllocated) {
            long consumed = tryPromoteCurrentProjectProgress(ComputeTask.class, task.type.asEntry(requestedComputePower), false);
            if (consumed <= 0 || task.isCompleted()) stopComputeResearch();
            else computeStatus = I18nHandler.RESEARCH_COMPUTE_STATUS_RUNNING;
            return;
        }
        if (computeController == null) {
            computeStatus = I18nHandler.RESEARCH_COMPUTE_STATUS_UNBOUND;
            return;
        }
        ComputerCluster cluster = computeController.getCluster();
        if (cluster == null) {
            computeStatus = I18nHandler.RESEARCH_COMPUTE_STATUS_OFFLINE;
            return;
        }
        long available = cluster.totalComputePower.getOrDefault(task.type, 0L) - cluster.usedComputePower.getOrDefault(task.type, 0L);
        requestedComputePower = Math.min(Math.max(0L, available), task.getRequiredProgress() - task.getProgress());
        if (requestedComputePower <= 0) {
            computeStatus = I18nHandler.RESEARCH_COMPUTE_STATUS_INSUFFICIENT;
            return;
        }
        if (IComputerClusterUser.super.tryStart()) {
            computePowerAllocated = true;
            computeStatus = I18nHandler.RESEARCH_COMPUTE_STATUS_RUNNING;
        } else {
            requestedComputePower = 0;
            computeStatus = I18nHandler.RESEARCH_COMPUTE_STATUS_INSUFFICIENT;
        }
    }

    @Nullable
    private static ComputeTask getCurrentComputeTask(@Nullable ResearchProject project) {
        if (project == null || project.isCompleted) return null;
        for (IResearchTask task : project.getTasks()) {
            if (task instanceof ComputeTask && !task.isCompleted()) return (ComputeTask) task;
        }
        return null;
    }

    private void stopComputeResearch() {
        if (computePowerAllocated) IComputerClusterUser.super.tryStop();
        computePowerAllocated = false;
        requestedComputePower = 0;
        activeComputeTask = null;
    }

    public String getComputeStatus() {
        if (I18nHandler.RESEARCH_COMPUTE_STATUS_RUNNING.equals(computeStatus) && activeComputeTask != null) {
            return String.format(LH.get(computeStatus), activeComputeTask.type.desc(requestedComputePower));
        }
        if (I18nHandler.RESEARCH_COMPUTE_STATUS_INSUFFICIENT.equals(computeStatus) && activeComputeTask != null) {
            return String.format(LH.get(computeStatus), LH.get(I18nHandler.COMPUTE_POWER + "." + activeComputeTask.type.ordinal()));
        }
        return LH.get(computeStatus);
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (CS.TOOL_magnifyingglass.equals(aTool) && aChatReturn != null) {
            aChatReturn.add(LH.get(I18nHandler.RESEARCH_COMPUTE_STATUS) + ": " + getComputeStatus());
            return 1;
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override public @Nullable IComputerClusterController getController() {return computeController;}
    @Override public void setController(IComputerClusterController controller) {computeController = controller;}
    @Override public List<IComputerClusterController> getBackupControllers() {return backupComputeControllers;}
    @Override public void setBackupControllers(List<IComputerClusterController> list) {backupComputeControllers = list == null ? new ArrayList<IComputerClusterController>() : list;}
    @Override public byte getState() {return requestedComputePower > 0 ? STATE_NORMAL : STATE_OFFLINE;}
    @Override public Map<ComputePower, Long> getComputePowerNeeded() {return activeComputeTask == null || requestedComputePower <= 0 ? new HashMap<ComputePower, Long>() : activeComputeTask.type.asMap(requestedComputePower);}
    @Override public @Nullable UUID getUUID() {return computeUserUUID;}
    @Override public void setUUID(UUID uuid) {computeUserUUID = uuid;}

    @Override
    public void onComputerPowerReleased() {
        computePowerAllocated = false;
        requestedComputePower = 0;
        computeStatus = I18nHandler.RESEARCH_COMPUTE_STATUS_INSUFFICIENT;
    }

    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[0];}
    @Override public boolean canDrop(int aInventorySlot) {return false;}

    public static final IIconContainer
            sTextureSides = new Textures.BlockIcons.CustomIcon("machines/research/table/compute/base"),
            sOverlayStop = new Textures.BlockIcons.CustomIcon("machines/research/table/compute/front");

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (!aShouldSideBeRendered[aSide]) return null;
        if (aSide == mFacing) return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides, mRGBa), BlockTextureDefault.get(sOverlayStop));
        return BlockTextureDefault.get(sTextureSides, mRGBa);
    }
}
