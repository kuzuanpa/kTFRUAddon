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
package cn.kuzuanpa.ktfruaddon.api.tile.computerCluster;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.item.IComputerItem;
import gregapi.data.LH;
import gregapi.util.ST;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

/**User side skeleton every machine and part delegates to: binding, persistence, cached board aggregation, throttled request.**/
public class ComputeUserComponent {
    public static final byte STATUS_NO_DEMAND = 0;
    public static final byte STATUS_UNBOUND = 1;
    public static final byte STATUS_OFFLINE = 2;
    public static final byte STATUS_INSTALLED_INSUFFICIENT = 3;
    public static final byte STATUS_TYPE_MISMATCH = 4;
    public static final byte STATUS_CLUSTER_INSUFFICIENT = 5;
    public static final byte STATUS_RUNNING = 6;
    public static final byte STATUS_WAITING = 7;

    public static final int RETRY_INTERVAL_TICKS = 20;
    public static final int RELOAD_GRACE_TICKS = 100;

    protected final IComputerClusterUser owner;
    protected final IComputeItemUser itemHost;
    protected final Supplier<List<ItemStack>> boards;

    protected UUID myUUID;
    protected IComputerClusterController controller;
    protected List<IComputerClusterController> backupControllers = new ArrayList<>();

    protected boolean allocated;
    protected byte status = STATUS_NO_DEMAND;
    protected long lastAttemptTick = Long.MIN_VALUE;
    protected Map<ComputePower, Long> lastRequest = Collections.emptyMap();
    protected long graceUntilTick = Long.MIN_VALUE;

    protected Map<ComputePower, Long> cachedInstalled;
    protected Map<ComputePower, Long> cachedNeeded;

    public ComputeUserComponent(IComputerClusterUser owner, IComputeItemUser itemHost, Supplier<List<ItemStack>> boards) {
        this.owner = owner;
        this.itemHost = itemHost;
        this.boards = boards;
    }

    public ComputeUserComponent(IComputerClusterUser owner) {
        this(owner, null, Collections::emptyList);
    }

    public Map<ComputePower, Long> getInstalled() {return getInstalled(null);}

    public Map<ComputePower, Long> getInstalled(@Nullable Map<ComputePower, Long> required) {
        if (cachedInstalled == null) cachedInstalled = sum(false);
        return filterByRequired(cachedInstalled, required);
    }

    public Map<ComputePower, Long> getNeeded() {return getNeeded(null);}

    public Map<ComputePower, Long> getNeeded(@Nullable Map<ComputePower, Long> required) {
        if (cachedNeeded == null) cachedNeeded = sum(true);
        return filterByRequired(cachedNeeded, required);
    }

    public void invalidate() {
        cachedInstalled = null;
        cachedNeeded = null;
    }

    protected Map<ComputePower, Long> sum(boolean clusterBackedOnly) {
        Map<ComputePower, Long> totals = new HashMap<>();
        List<ItemStack> stacks = boards.get();
        if (stacks == null) return totals;
        for (ItemStack stack : stacks) {
            if (!ST.valid(stack)) continue;
            if (IComputerItem.isClusterBackedStack(stack) != clusterBackedOnly) continue;
            Map.Entry<ComputePower, Long> entry = clusterBackedOnly
                    ? IComputerItem.getRequestedComputePower(stack, stack.stackSize, itemHost)
                    : IComputerItem.getProvidedComputePower(stack, stack.stackSize, itemHost);
            if (entry.getKey() == null || entry.getValue() == null || entry.getValue() <= 0L) continue;
            totals.merge(entry.getKey(), entry.getValue(), Long::sum);
        }
        return totals;
    }

    protected static Map<ComputePower, Long> filterByRequired(Map<ComputePower, Long> totals, @Nullable Map<ComputePower, Long> required) {
        if (required == null) return new HashMap<>(totals);
        Map<ComputePower, Long> filtered = new HashMap<>();
        for (Map.Entry<ComputePower, Long> entry : required.entrySet()) {
            Long amount = totals.get(entry.getKey());
            if (amount != null && amount > 0L) filtered.put(entry.getKey(), amount);
        }
        return filtered;
    }

    public boolean verifyInstalled(Map<ComputePower, Long> installed, @Nullable Map<ComputePower, Long> required) {
        if (required == null || required.isEmpty()) {
            if (status == STATUS_INSTALLED_INSUFFICIENT || status == STATUS_TYPE_MISMATCH) status = STATUS_NO_DEMAND;
            return true;
        }
        boolean anythingInstalled = false;
        for (Map.Entry<ComputePower, Long> entry : required.entrySet()) {
            Long available = installed.get(entry.getKey());
            if (available != null && available > 0L) anythingInstalled = true;
            if (available == null || available < entry.getValue()) {
                status = anythingInstalled ? STATUS_INSTALLED_INSUFFICIENT : STATUS_TYPE_MISMATCH;
                return false;
            }
        }
        return true;
    }

    public boolean requestCompute(Map<ComputePower, Long> demand) {
        Map<ComputePower, Long> needed = sanitize(demand);
        if (needed.isEmpty()) {
            release();
            status = STATUS_NO_DEMAND;
            return true;
        }
        long tick = serverTick();
        if (tick < 0L) {
            status = STATUS_OFFLINE;
            return false;
        }
        if (allocated && needed.equals(lastRequest)) {
            status = STATUS_RUNNING;
            return true;
        }
        if (!retryDue(tick, needed)) return false;
        lastAttemptTick = tick;
        lastRequest = needed;

        IComputerClusterController bound = owner.getController();
        if (bound == null) {
            status = STATUS_UNBOUND;
            return false;
        }
        ComputerCluster cluster = bound.getCluster();
        if (cluster == null) {
            status = STATUS_OFFLINE;
            return false;
        }
        //a read only pre-check keeps requests that are bound to fail out of the cluster event log
        if (!cluster.isComputePowerSufficient(needed)) {
            status = inGrace(tick) ? STATUS_WAITING : STATUS_CLUSTER_INSUFFICIENT;
            return false;
        }
        //the cluster reads getState() while allocating, so the owners "I want compute" flag has to be current
        cluster.updateUserData(owner);
        if (!owner.requestClusterCompute()) {
            status = inGrace(tick) ? STATUS_WAITING : STATUS_CLUSTER_INSUFFICIENT;
            return false;
        }
        allocated = true;
        status = STATUS_RUNNING;
        return true;
    }

    public void release() {
        lastRequest = Collections.emptyMap();
        lastAttemptTick = Long.MIN_VALUE;
        if (!allocated) return;
        allocated = false;
        if (owner.getController() != null) owner.tryStop();
    }

    public void onReleased() {
        allocated = false;
        lastRequest = Collections.emptyMap();
        lastAttemptTick = Long.MIN_VALUE;
        status = STATUS_CLUSTER_INSUFFICIENT;
    }

    public void failWith(byte status) {
        release();
        this.status = status;
    }

    public boolean bindFromUSB(EntityPlayer aPlayer) {
        return IComputerClusterUser.bindControllerFromUSB(aPlayer, owner);
    }

    public void readFromNBT(NBTTagCompound aNBT) {
        IComputerClusterUser.readFromNBT(aNBT, owner);
        graceUntilTick = serverTick() + RELOAD_GRACE_TICKS;
    }

    public void writeToNBT(NBTTagCompound aNBT) {
        IComputerClusterUser.writeToNBT(aNBT, owner);
    }

    public IComputerClusterController getController() {return controller;}
    public void setController(IComputerClusterController controller) {this.controller = controller;}
    public List<IComputerClusterController> getBackupControllers() {return backupControllers;}
    public void setBackupControllers(List<IComputerClusterController> list) {backupControllers = list == null ? new ArrayList<IComputerClusterController>() : list;}
    public boolean isAllocated() {return allocated;}
    public byte getStatus() {return status;}
    public void setStatus(byte status) {this.status = status;}

    public UUID getUUID() {
        if (myUUID == null) myUUID = UUID.randomUUID();
        return myUUID;
    }

    public void setUUID(UUID uuid) {myUUID = uuid;}
    public boolean inGrace(long tick) {return tick < graceUntilTick;}

    protected boolean retryDue(long tick, Map<ComputePower, Long> needed) {
        return lastAttemptTick == Long.MIN_VALUE || tick - lastAttemptTick >= RETRY_INTERVAL_TICKS || !needed.equals(lastRequest);
    }

    public String getStatusText() {
        switch (status) {
            case STATUS_UNBOUND: return LH.get(I18nHandler.COMPUTE_USER_STATUS_UNBOUND);
            case STATUS_OFFLINE: return LH.get(I18nHandler.COMPUTE_USER_STATUS_OFFLINE);
            case STATUS_INSTALLED_INSUFFICIENT: return LH.get(I18nHandler.COMPUTE_USER_STATUS_INSTALLED_INSUFFICIENT);
            case STATUS_TYPE_MISMATCH: return LH.get(I18nHandler.COMPUTE_USER_STATUS_TYPE_MISMATCH);
            case STATUS_CLUSTER_INSUFFICIENT: return LH.get(I18nHandler.COMPUTE_USER_STATUS_CLUSTER_INSUFFICIENT);
            case STATUS_RUNNING: return LH.get(I18nHandler.COMPUTE_USER_STATUS_RUNNING);
            case STATUS_WAITING: return LH.get(I18nHandler.COMPUTE_USER_STATUS_WAITING);
            default: return LH.get(I18nHandler.COMPUTE_USER_STATUS_NO_DEMAND);
        }
    }

    protected static Map<ComputePower, Long> sanitize(@Nullable Map<ComputePower, Long> amounts) {
        Map<ComputePower, Long> clean = new HashMap<>();
        if (amounts == null) return clean;
        for (Map.Entry<ComputePower, Long> entry : amounts.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null || entry.getValue() <= 0L) continue;
            clean.merge(entry.getKey(), entry.getValue(), Long::sum);
        }
        return clean;
    }

    protected static long serverTick() {
        MinecraftServer server = MinecraftServer.getServer();
        return server == null ? -1L : server.getTickCounter();
    }
}
