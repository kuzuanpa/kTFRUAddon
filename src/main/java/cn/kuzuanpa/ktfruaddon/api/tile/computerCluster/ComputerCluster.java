/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */
package cn.kuzuanpa.ktfruaddon.api.tile.computerCluster;

import cn.kuzuanpa.ktfruaddon.api.code.SingleEntry;
import cn.kuzuanpa.ktfruaddon.api.code.WorldPos;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.network.PacketUUIDAssignedData;
import cpw.mods.fml.common.FMLLog;
import gregapi.util.WD;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.kNetworkHandler;

public class ComputerCluster {
    public static final byte PACKET_TYPE = 1;
    public static final byte PACKET_REQUEST_SYNC = 0;
    public static final byte PACKET_SUBSCRIBE = 1;
    public static final byte PACKET_UNSUBSCRIBE = 2;
    public static final byte PACKET_SYNC_DATA = -1;
    public static final byte PACKET_NOT_FOUND = -2;
    public static final int MAX_CLUSTER_EVENTS = 320;
    public static final int MAX_CONTROLLER_EVENTS = 240;
    public static final int MAX_USER_EVENTS = 160;

    /**Ticks between two forced pushes to the viewing clients, used as a keep alive when nothing changed.**/
    public static final int CLIENT_SYNC_HEARTBEAT_TICKS = 100;
    /**Minimum ticks between two pushes to the same viewing client.**/
    public static final int CLIENT_SYNC_MIN_INTERVAL_TICKS = 20;
    /**Requests waiting for the server thread, anything above this is dropped so a spamming client cannot fill the heap.**/
    public static final int MAX_PENDING_REQUESTS = 256;

    public static final Map<UUID, ComputerCluster> allClusterUUIDsServer = new HashMap<>();
    /**Written from the network thread, read from the client thread, so both levels have to be concurrent.**/
    public static final Map<UUID, Map<UUID, ComputerClusterClientData.ClusterSnapshot>> allClusterUUIDsClient = new ConcurrentHashMap<>();
    private static final UUID NO_CONTROLLER_UUID = new UUID(0L, 0L);
    private static final Queue<PendingRequest> pendingRequests = new ConcurrentLinkedQueue<>();

    public Map<UUID, ControllerData> controllerList = new HashMap<>();

    public Map<UUID, UserData> userList = new HashMap<>();
    @NotNull public Map<ComputePower, Long> totalComputePower = new HashMap<>();
    public Map<ComputePower, Long> usedComputePower = new HashMap<>();
    public Queue<Byte> events = new ArrayDeque<>();
    public Queue<String> eventExtra = new ArrayDeque<>();
    /**Player name to the controller UUID that player is looking at. Names are used because player instances get replaced on respawn or dimension change.**/
    public Map<String, UUID> viewerPlayers = new HashMap<>();
    /**Set whenever anything a client can see changed, cleared once the viewers got the new snapshot.**/
    public boolean clientDataDirty = true;

    public long lastUpdateTime = -1;
    public long lastClientSyncTick = -1;
    public UUID clusterUUID;
    public byte state = Constants.STATE_OFFLINE;

    public ComputerCluster(UUID uuid) {
        if(uuid != null)this.clusterUUID = uuid;
        else this.clusterUUID = UUID.randomUUID();
        allClusterUUIDsServer.put(this.clusterUUID, this);
        pushClusterEvent(Constants.EVENT_CLUSTER_CREATED, "cluster=" + shortUUID(this.clusterUUID));
        for (ComputePower type : ComputePower.values()) totalComputePower.put(type, 0L);//Avoid NPE
    }
    public static ComputerCluster create(World initialControllerWorld, WorldPos initialControllerPos) {
        ComputerCluster cluster = new ComputerCluster(null);
        if (cluster.join(initialControllerWorld,initialControllerPos) != null) {
            //init controller broken
            cluster.destroy();
            return null;
        }
        cluster.update();
        return cluster;
    }

    public void update(){
        long tick = getServerTick();
        if(tick < 0 || tick <= lastUpdateTime)return;
        byte oldClusterState = state;
        lastUpdateTime = tick;
        Map<ComputePower, Long> totalComputePowerMap = new HashMap<>();
        for (ComputePower type : ComputePower.values()) totalComputePowerMap.put(type, 0L);//Avoid NPE
        controllerList.forEach((uuid,data) -> updateControllerData(uuid,data,totalComputePowerMap));
        state = computeClusterState();
        if (oldClusterState != state) pushClusterEvent(Constants.EVENT_STATE_CHANGED, oldClusterState + " -> " + state);
        if (!totalComputePowerMap.equals(totalComputePower)) clientDataDirty = true;
        totalComputePower = totalComputePowerMap;
        enforceComputePowerLimit();
        updateUsers();
        syncViewerPlayers();
    }

    /**@return the current server tick, or -1 when there is no server running (client only session).**/
    protected static long getServerTick(){
        MinecraftServer server = MinecraftServer.getServer();
        return server == null ? -1 : server.getTickCounter();
    }

    protected byte computeClusterState() {
        boolean hasError = false;
        boolean hasOnline = false;
        boolean hasWarning = false;
        for (ControllerData data : controllerList.values()) {
            if (data.state == Constants.STATE_ERROR || data.state == Constants.STATE_BELONG_ERR) hasError = true;
            if (data.state == Constants.STATE_WARNING) hasWarning = true;
            if (data.state == Constants.STATE_NORMAL) hasOnline = true;
        }
        if (!hasOnline)return Constants.STATE_ERROR;
        if (hasWarning || hasError) return Constants.STATE_WARNING;
        return Constants.STATE_NORMAL;
    }

    protected static void pushEvent(Queue<Byte> events, Queue<String> eventExtra, byte event, String extra, int maxSize) {
        if (events.size() >= maxSize) {
            events.poll();
            eventExtra.poll();
        }
        events.add(event);
        String timestamp = new SimpleDateFormat("[HH:mm:ss]").format(new Date());
        eventExtra.add(extra == null ? "" : timestamp + " " +extra);
    }

    protected void pushClusterEvent(byte event, String extra) {
        pushEvent(events, eventExtra, event, extra, MAX_CLUSTER_EVENTS);
        clientDataDirty = true;
    }

    public void pushControllerEvent(UUID controllerUUID, byte event, String extra) {
        ControllerData controllerData = controllerList.get(controllerUUID);
        if (controllerData == null) return;
        pushEvent(controllerData.events, controllerData.eventExtra, event, extra, MAX_CONTROLLER_EVENTS);
        clientDataDirty = true;
    }

    protected void pushUserEvent(UserData userData, byte event, String extra) {
        if (userData == null) return;
        pushEvent(userData.events, userData.eventExtra, event, extra, MAX_USER_EVENTS);
        clientDataDirty = true;
    }

    protected static String shortUUID(UUID uuid) {
        if (uuid == null) return "null";
        String str = uuid.toString();
        return str.substring(0, 8);
    }

    public void updateControllerData(UUID uuid, ControllerData data, Map<ComputePower, Long> totalComputePowerMap){
        IComputerClusterController controller = getControllerFromData(data);
        byte oldState = data.state;
        Map.Entry<ComputePower, Long> oldPower = data.power;
        data.state = updateControllerState(uuid, controller);
        Map.Entry<ComputePower, Long> provided = data.state == Constants.STATE_NORMAL && controller != null ? controller.getComputePower() : null;
        if(provided != null && provided.getKey() != null && provided.getValue() != null) {
            data.power = provided;
            totalComputePowerMap.merge(data.power.getKey(), data.power.getValue(), Long::sum);
        }
        else data.power = new SingleEntry<>(ComputePower.Normal, 0L);

        if (oldState != data.state) pushControllerEvent(uuid, Constants.EVENT_STATE_CHANGED, oldState + " -> " + data.state);
        if (oldState != data.state || !Objects.equals(oldPower.getKey(), data.power.getKey()) || !Objects.equals(oldPower.getValue(), data.power.getValue())) clientDataDirty = true;
    }
    protected byte updateControllerState(UUID uuid, @Nullable IComputerClusterController controller){
        if(controller == null) return Constants.STATE_OFFLINE;

        if(controller.getCluster() == null){
            if(Objects.equals(uuid, controller.getUUID())){
                controller.setCluster(this);
            }
        }
        else if(controller.getCluster() != this) return Constants.STATE_BELONG_ERR;

        if(!Objects.equals(controller.getUUID(), uuid)){
            controller.notifyControllerEvent(Constants.EVENT_WRONG_UUID);
            return Constants.STATE_ERROR;
        }
        return controller.getState();
    }
    public void joinUser(IComputerClusterUser user){
        if(user == null || userList.get(user.getUUID()) != null)return;
        UserData data = new UserData(user);
        userList.put(user.getUUID(), data);
        pushClusterEvent(Constants.EVENT_USER_JOINED, "user=" + shortUUID(user.getUUID()));
        pushUserEvent(data, Constants.EVENT_USER_JOINED, "controller=" + shortUUID(user.getController() == null ? null : user.getController().getUUID()));
        updateUserData(user);
    }

    /**
     * Refreshes every known user and drops the ones whose tile is gone, unloaded or bound elsewhere,
     * giving back whatever they were holding. Without this the cluster would keep users forever and
     * leak both the tile reference and the allocated Compute Power.
     */
    protected void updateUsers(){
        Iterator<Map.Entry<UUID, UserData>> iterator = userList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, UserData> entry = iterator.next();
            UserData data = entry.getValue();
            IComputerClusterUser user = data.user;
            boolean gone = user == null || !Objects.equals(user.getUUID(), entry.getKey())
                    || (user instanceof TileEntity && ((TileEntity) user).isInvalid())
                    || user.getController() == null || user.getController().getCluster() != this;
            if (gone) {
                releaseHeldPower(data);
                iterator.remove();
                pushClusterEvent(Constants.EVENT_USER_LEFT, "user=" + shortUUID(entry.getKey()));
                continue;
            }
            updateUserData(user);
        }
    }

    public UserData getUserData(UUID uuid){
        return userList.get(uuid);
    }
    public void updateUserData(IComputerClusterUser user){
        if(user == null)return;
        UserData data = userList.get(user.getUUID());
        if(data == null)return;

        byte oldState = data.state;
        updateUserPos(user, data);
        if(user.getController() == null || user.getController().getCluster() != this){
            data.state = Constants.STATE_BELONG_ERR;
            if (oldState != data.state) pushUserEvent(data, Constants.EVENT_USER_LEFT, "controller lost");
            freeUserComputePower(user);
            markUserDirty(data, oldState);
            return;
        }
        data.lastUpdatedTick = getServerTick();
        data.state = user.getState();
        if(data.state != Constants.STATE_NORMAL && data.state != Constants.STATE_WARNING) freeUserComputePower(user);
        markUserDirty(data, oldState);
    }

    private void markUserDirty(UserData data, byte oldState) {
        if (oldState == data.state) return;
        clientDataDirty = true;
    }

    /**Keeps the position shown in the user list in sync, the client cannot resolve it from the tile.**/
    private void updateUserPos(IComputerClusterUser user, UserData data) {
        WorldPos pos = user.getUserPos();
        if (Objects.equals(pos, data.pos)) return;
        data.pos = pos;
        clientDataDirty = true;
    }


    /**Gives the amounts held by this user back to the cluster without touching the user itself.**/
    protected void releaseHeldPower(UserData data){
        if (data.consumingPower.isEmpty()) return;
        data.consumingPower.forEach(this::returnHeldComputePower);
        data.consumingPower.clear();
        clientDataDirty = true;
    }

    protected void returnHeldComputePower(ComputePower type, long amount){
        if (type == null || amount <= 0L) return;
        usedComputePower.compute(type, (ignored, used) -> Math.max(0L, (used == null ? 0L : used) - amount));
    }

    public boolean isComputePowerSufficient(Map<ComputePower, Long> additions){
        return additions.entrySet().stream().allMatch(this::isComputePowerSufficient);
    }

    protected void enforceComputePowerLimit(){
        for (Map.Entry<ComputePower, Long> entry : totalComputePower.entrySet()) {
            ComputePower type = entry.getKey();
            long deficit = usedComputePower.getOrDefault(type, 0L) - entry.getValue();
            if (deficit <= 0) continue;
            pushClusterEvent(Constants.EVENT_POWER_ALLOCATE_FAILED, "oversubscribed " + type.desc(deficit));
            for (UserData data : userList.values()) {
                if (deficit <= 0) break;
                long held = data.consumingPower.getOrDefault(type, 0L);
                if (held <= 0) continue;
                deficit -= held;
                if (data.user == null) releaseHeldPower(data);
                else freeUserComputePower(data.user);
            }
        }
    }

    public boolean isComputePowerSufficient(Map.Entry<ComputePower, Long> power){
        if (power.getValue() == null || power.getValue() <= 0) return true;
        long used = usedComputePower.getOrDefault(power.getKey(), 0L);
        long total = totalComputePower.getOrDefault(power.getKey(), 0L);
        return total >= used + power.getValue();
    }

    public boolean allocateUserComputePower(IComputerClusterUser user){
        if(user == null)return false;
        if(getUserData(user.getUUID()) == null)joinUser(user);
        UserData data = getUserData(user.getUUID());
        if(data == null)return false;

        if(data.lastUpdatedTick < 0 || getServerTick() - data.lastUpdatedTick > 5)updateUserData(user);
        if(data.state == Constants.STATE_NORMAL || data.state == Constants.STATE_WARNING){
            Map<ComputePower, Long> needed = new HashMap<>(user.getComputePowerNeeded());
            needed.values().removeIf(amount -> amount == null || amount <= 0);
            Map<ComputePower, Long> held = new HashMap<>(data.consumingPower);
            Map<ComputePower, Long> additions = new HashMap<>();
            needed.forEach((type, amount) -> {
                long delta = amount - held.getOrDefault(type, 0L);
                if (delta > 0L) additions.put(type, delta);
            });

            if (!isComputePowerSufficient(additions)) {
                pushClusterEvent(Constants.EVENT_POWER_ALLOCATE_FAILED, "user=" + shortUUID(user.getUUID()) + " insufficient " + ComputePower.getDescOneLine(additions));
                pushUserEvent(data, Constants.EVENT_POWER_ALLOCATE_FAILED, "insufficient " + ComputePower.getDescOneLine(additions));
                return false;
            }

            if (needed.equals(held)) return true;

            held.forEach((type, oldAmount) -> {
                long newAmount = needed.getOrDefault(type, 0L);
                if (newAmount < oldAmount) returnHeldComputePower(type, oldAmount - newAmount);
            });
            needed.forEach((type, newAmount) -> {
                long oldAmount = held.getOrDefault(type, 0L);
                if (newAmount > oldAmount) usedComputePower.merge(type, newAmount - oldAmount, Long::sum);
            });
            data.consumingPower = needed;
            clientDataDirty = true;
            pushClusterEvent(Constants.EVENT_POWER_ALLOCATED, "user=" + shortUUID(user.getUUID()) + " " + ComputePower.getDescOneLine(needed));
            pushUserEvent(data, Constants.EVENT_POWER_ALLOCATED, ComputePower.getDescOneLine(needed));
            return true;
        }
        pushClusterEvent(Constants.EVENT_POWER_ALLOCATE_FAILED, "user=" + shortUUID(user.getUUID()) + " state=" + data.state);
        pushUserEvent(data, Constants.EVENT_POWER_ALLOCATE_FAILED, "state=" + data.state);
        return false;
    }

    public boolean freeUserComputePower(IComputerClusterUser user){
        if(user == null || getUserData(user.getUUID()) == null)return false;
        UserData data = getUserData(user.getUUID());
        if(data == null)return false;
        if(!data.consumingPower.isEmpty()){
            String released = ComputePower.getDescOneLine(data.consumingPower);
            releaseHeldPower(data);
            user.onComputerPowerReleased();
            pushClusterEvent(Constants.EVENT_POWER_RELEASED, "user=" + shortUUID(user.getUUID()) + " " + released);
            pushUserEvent(data, Constants.EVENT_POWER_RELEASED, released);
        }
        return true;
    }

    public List<ControllerData> getOnlineControllers(){
        return controllerList.values().stream().filter(data-> data.state == Constants.STATE_NORMAL || data.state == Constants.STATE_WARNING).collect(Collectors.toList());
    }

    /**
     * Rebuilds the cluster {@code clusterUUID} from the controller positions saved in NBT. Reuses the
     * live instance when another controller already recovered it, and only creates a new one when
     * nothing of that cluster exists yet.
     */
    public static void recoverOrJoin(List<ControllerData> controllerList, UUID clusterUUID){
        if (clusterUUID == null || controllerList == null) return;
        ComputerCluster cluster = allClusterUUIDsServer.get(clusterUUID);
        for (ControllerData data : controllerList) {
            if(data == null || data.world == null)continue;
            IComputerClusterController controller = getControllerFromData(data);
            if(controller == null)continue;
            //A controller that was saved with a different cluster must not be dragged into this one.
            if(!Objects.equals(controller.getSavedClusterUUID(), clusterUUID))continue;

            if(controller.getCluster() != null){
                if(cluster == null)cluster = controller.getCluster();
                continue;
            }
            if(cluster == null)cluster = new ComputerCluster(clusterUUID);
            cluster.join(data.world, data.pos, controller);
        }
        //A cluster nobody could join would linger in the registry forever.
        if(cluster != null && cluster.controllerList.isEmpty())allClusterUUIDsServer.remove(cluster.clusterUUID);
    }

    /**Drops every server side cluster, called when the server shuts down so a new world starts clean.**/
    public static void clearServerData(){
        allClusterUUIDsServer.clear();
        pendingRequests.clear();
    }

    /**Drops the client side snapshots, called when the client leaves a server or world.**/
    public static void clearClientData(){
        allClusterUUIDsClient.clear();
    }

    public String join(World world, WorldPos pos){
        if(world == null || pos == null)return I18nHandler.COMPUTE_CLUSTER_MSG_JOIN_NOT_CONTROLLER;
        TileEntity tile = world.getTileEntity(pos.x,pos.y,pos.z);
        if(!(tile instanceof IComputerClusterController))return I18nHandler.COMPUTE_CLUSTER_MSG_JOIN_NOT_CONTROLLER;
        return join(world,pos, (IComputerClusterController) tile);
    }

    /**@return ERROR message, null if successful**/
    public String join(World world, WorldPos pos, IComputerClusterController controller){
        if(world == null || pos == null || controller == null)return I18nHandler.COMPUTE_CLUSTER_MSG_JOIN_NOT_CONTROLLER;
        if(controller.getUUID() == null){
            controller.notifyControllerEvent(Constants.EVENT_WRONG_UUID);//makes the controller assign itself a fresh UUID
            if(controller.getUUID() == null)return I18nHandler.COMPUTE_CLUSTER_MSG_JOIN_DUPLICATE_UUID;
        }
        if(controllerList.containsKey(controller.getUUID())){
            UUID duplicatedUUID = controller.getUUID();
            if(pos.equals(controllerList.get(duplicatedUUID).pos)) return I18nHandler.COMPUTE_CLUSTER_MSG_JOIN_ALREADY_EXIST;
            controller.notifyControllerEvent(Constants.EVENT_WRONG_UUID);
            return I18nHandler.COMPUTE_CLUSTER_MSG_JOIN_DUPLICATE_UUID;
        }
        if((controller.getCluster() != null && controller.getCluster() != this)|| (controller.getSavedClusterUUID() != null && !Objects.equals(controller.getSavedClusterUUID(), this.clusterUUID)))return I18nHandler.COMPUTE_CLUSTER_MSG_JOIN_BELONG_OTHER;
        else if(controller.getCluster() == null && !controller.setCluster(this)) return I18nHandler.COMPUTE_CLUSTER_MSG_JOIN_BELONG_OTHER;
        controllerList.put(controller.getUUID(),new ControllerData(world,pos));
        allClusterUUIDsServer.put(clusterUUID, this);
        String extra = "controller=" + shortUUID(controller.getUUID()) + " " + pos;
        pushClusterEvent(Constants.EVENT_CONTROLLER_JOINED, extra);
        pushControllerEvent(controller.getUUID(), Constants.EVENT_CONTROLLER_JOINED, "cluster=" + shortUUID(clusterUUID));
        return null;
    }

    public String kick(World world, WorldPos pos){
        if(world == null || pos == null)return I18nHandler.COMPUTE_CLUSTER_MSG_KICK_NOT_FOUND;
        UUID uuid = null;
        for (Map.Entry<UUID, ControllerData> entry : controllerList.entrySet()) {
            UUID id = entry.getKey();
            ControllerData data = entry.getValue();
            if (data.pos.equals(pos)) {
                uuid = id;
                break;
            }
        }
        return kick(uuid);
    }

    public String kick(UUID uuid) {
        if (uuid == null || controllerList.get(uuid) == null) return I18nHandler.COMPUTE_CLUSTER_MSG_KICK_NOT_FOUND;
        ControllerData data = controllerList.get(uuid);
        IComputerClusterController controller = getControllerFromData(data);
        if(controller == null) return I18nHandler.COMPUTE_CLUSTER_MSG_KICK_NOT_LOADED;
        if(controller.getCluster() != this)return I18nHandler.COMPUTE_CLUSTER_MSG_KICK_NOT_BELONG_ME;
        pushClusterEvent(Constants.EVENT_A_CONTROLLER_LEFT, "controller=" + shortUUID(uuid));
        remove0(uuid);
        controller.notifyControllerEvent(Constants.EVENT_KICKING_FROM_CLUSTER);
        return null;
    }

    public String quit(UUID uuid, IComputerClusterController controller){
        if (uuid == null || !controllerList.containsKey(uuid)) return I18nHandler.COMPUTE_CLUSTER_MSG_KICK_NOT_FOUND;
        pushClusterEvent(Constants.EVENT_A_CONTROLLER_LEFT, "controller=" + shortUUID(uuid));
        remove0(uuid);
        if (controller != null) controller.notifyControllerEvent(Constants.EVENT_KICKING_FROM_CLUSTER);
        return null;
    }

    protected void remove0(UUID uuid){
        controllerList.remove(uuid);
        clientDataDirty = true;
        if (controllerList.isEmpty()) {
            destroy();
            return;
        }
        postEventToAllControllers(Constants.EVENT_A_CONTROLLER_LEFT);
    }

    public void postEventToAllControllers(byte event){
        controllerList.forEach(((uuid, data) ->  {
            IComputerClusterController controller = getControllerFromData(data);
            if(controller!=null)controller.notifyControllerEvent(event);
            pushControllerEvent(uuid, event, "");
        }));
    }

    public static IComputerClusterController getControllerFromData(ControllerData data){
        if(data == null || data.world == null)return null;
        TileEntity te = WD.te(data.world,data.pos.x, data.pos.y, data.pos.z,false);
        if(te instanceof IComputerClusterController)return (IComputerClusterController) te;
        return null;
    }
    public void destroy(){
        postEventToAllControllers(Constants.EVENT_CLUSTER_DESTROY);
        pushClusterEvent(Constants.EVENT_CLUSTER_DESTROY, "cluster=" + shortUUID(clusterUUID));
        userList.values().forEach(data -> {
            releaseHeldPower(data);
            if (data.user != null) data.user.onComputerPowerReleased();
        });
        userList.clear();
        notifyViewersClusterGone();
        viewerPlayers.clear();
        allClusterUUIDsServer.remove(clusterUUID);
    }

    public ComputerClusterClientData.ControllerList fetchClientDataControllerList() {
        return new ComputerClusterClientData.ControllerList(controllerList.values());
    }

    public ComputerClusterClientData.UserList fetchClientDataUserList() {
        return new ComputerClusterClientData.UserList(userList.values());
    }

    public ComputerClusterClientData.ClusterDetail fetchClientDataClusterDetail() {
        return new ComputerClusterClientData.ClusterDetail(state,controllerList.size(), userList.size(),totalComputePower,usedComputePower, events.toArray(), eventExtra.toArray(new String[0]));
    }

    public ComputerClusterClientData.ControllerDetail fetchClientDataControllerDetail(UUID controllerID) {
        ControllerData controllerData = controllerList.get(controllerID);
        if (controllerData == null) return new ComputerClusterClientData.ControllerDetail(Constants.STATE_OFFLINE, (byte) 0, 0, 0, new byte[0], new String[0]);
        return new ComputerClusterClientData.ControllerDetail(controllerData.state, (byte) controllerData.power.getKey().ordinal(), controllerData.power.getValue(), totalComputePower.getOrDefault(controllerData.power.getKey(), 0L), controllerData.events.toArray(), controllerData.eventExtra.toArray(new String[0]));
    }

    public ComputerClusterClientData.ClusterSnapshot fetchClientSnapshot(UUID controllerID) {
        return new ComputerClusterClientData.ClusterSnapshot(
                fetchClientDataControllerList(),
                fetchClientDataUserList(),
                fetchClientDataClusterDetail(),
                fetchClientDataControllerDetail(controllerID)
        );
    }

    /**
     * Pushes a snapshot to the subscribed players, but only when something changed or the heartbeat
     * interval elapsed. Viewers whose player left the server are dropped here.
     */
    protected void syncViewerPlayers() {
        long tick = getServerTick();
        if (tick < 0 || viewerPlayers.isEmpty()) return;
        long sinceLastSync = tick - lastClientSyncTick;
        if (sinceLastSync < CLIENT_SYNC_MIN_INTERVAL_TICKS) return;
        if (!clientDataDirty && sinceLastSync < CLIENT_SYNC_HEARTBEAT_TICKS) return;

        lastClientSyncTick = tick;
        clientDataDirty = false;
        Iterator<Map.Entry<String, UUID>> iterator = viewerPlayers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, UUID> viewer = iterator.next();
            EntityPlayerMP playerMP = findPlayer(viewer.getKey());
            if (playerMP == null) {
                iterator.remove();
                continue;
            }
            sendClusterData(playerMP, this, viewer.getValue());
        }
    }

    /**Tells every subscriber that this cluster no longer exists, so their GUI stops showing stale data.**/
    protected void notifyViewersClusterGone() {
        for (String playerID : viewerPlayers.keySet()) {
            EntityPlayerMP playerMP = findPlayer(playerID);
            if (playerMP != null) kNetworkHandler.sendToPlayer(new PacketUUIDAssignedData(PACKET_TYPE, clusterUUID, PACKET_NOT_FOUND), playerMP);
        }
    }

    protected static @Nullable EntityPlayerMP findPlayer(String playerID) {
        MinecraftServer server = MinecraftServer.getServer();
        if (server == null || playerID == null) return null;
        return (EntityPlayerMP) server.getConfigurationManager().playerEntityList.stream()
                .filter(p -> p instanceof EntityPlayerMP && playerID.equals(((EntityPlayerMP) p).getCommandSenderName()))
                .findFirst().orElse(null);
    }

    private static UUID controllerKey(@Nullable UUID controllerUUID) {
        return controllerUUID == null ? NO_CONTROLLER_UUID : controllerUUID;
    }

    public static @Nullable ComputerClusterClientData.ClusterSnapshot getClientSnapshot(UUID clusterUUID, @Nullable UUID controllerUUID) {
        Map<UUID, ComputerClusterClientData.ClusterSnapshot> controllerSnapshots = allClusterUUIDsClient.get(clusterUUID);
        if (controllerSnapshots == null) return null;
        return controllerSnapshots.get(controllerKey(controllerUUID));
    }

    public static void receiveUUIDAssignedData(UUID uuid, byte @Nullable [] data) {
        if (data == null || uuid == null || data.length == 0) return;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             DataInputStream dis = new DataInputStream(bis)) {
            byte packetType = dis.readByte();
            if (packetType == PACKET_SYNC_DATA) {
                UUID controllerUUID = dis.readBoolean() ? new UUID(dis.readLong(), dis.readLong()) : null;
                byte[] snapshotData = new byte[bis.available()];
                dis.readFully(snapshotData);
                ComputerClusterClientData.ClusterSnapshot snapshot = ComputerClusterClientData.ClusterSnapshot.deserialize(snapshotData);
                allClusterUUIDsClient.computeIfAbsent(uuid, k -> new ConcurrentHashMap<>()).put(controllerKey(controllerUUID), snapshot);
                return;
            }
            if (packetType == PACKET_NOT_FOUND) {
                allClusterUUIDsClient.remove(uuid);
                return;
            }

            String playerID = dis.readUTF();
            UUID controllerUUID = dis.readBoolean() ? new UUID(dis.readLong(), dis.readLong()) : null;
            //This runs on the netty thread, the cluster state may only be touched from the server thread.
            if (pendingRequests.size() >= MAX_PENDING_REQUESTS) return;
            pendingRequests.add(new PendingRequest(packetType, uuid, playerID, controllerUUID));
        } catch (IOException e) {
            FMLLog.log(Level.ERROR, "ComputerCluster packet decode failed", e);
        }
    }

    /**A client request parked by the netty thread, see {@link #processPendingRequests()}.**/
    private static final class PendingRequest {
        final byte packetType;
        final UUID clusterUUID;
        final String playerID;
        final @Nullable UUID controllerUUID;

        PendingRequest(byte packetType, UUID clusterUUID, String playerID, @Nullable UUID controllerUUID) {
            this.packetType = packetType;
            this.clusterUUID = clusterUUID;
            this.playerID = playerID;
            this.controllerUUID = controllerUUID;
        }
    }

    /**Handles the requests parked by the netty thread. Must be called from the server thread once per tick.**/
    public static void processPendingRequests() {
        PendingRequest request;
        while ((request = pendingRequests.poll()) != null) {
            EntityPlayerMP playerMP = findPlayer(request.playerID);
            if (playerMP == null) continue;
            ComputerCluster cluster = allClusterUUIDsServer.get(request.clusterUUID);
            if (cluster == null) {
                kNetworkHandler.sendToPlayer(new PacketUUIDAssignedData(PACKET_TYPE, request.clusterUUID, PACKET_NOT_FOUND), playerMP);
                continue;
            }
            if (request.packetType == PACKET_SUBSCRIBE) cluster.viewerPlayers.put(request.playerID, request.controllerUUID);
            if (request.packetType == PACKET_UNSUBSCRIBE) {
                cluster.viewerPlayers.remove(request.playerID);
                continue;
            }
            sendClusterData(playerMP, cluster, request.controllerUUID);
        }
    }

    public static void sendClusterData(EntityPlayerMP playerMP, ComputerCluster cluster, @Nullable UUID controllerUUID) {
        if (playerMP == null || cluster == null) return;
        try {
            if (controllerUUID == null && !cluster.controllerList.isEmpty()) controllerUUID = cluster.controllerList.keySet().iterator().next();

            byte[] snapshotBytes = ComputerClusterClientData.ClusterSnapshot.serialize(cluster.fetchClientSnapshot(controllerUUID));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeByte(PACKET_SYNC_DATA);
            dos.writeBoolean(controllerUUID != null);
            if (controllerUUID != null) {
                dos.writeLong(controllerUUID.getMostSignificantBits());
                dos.writeLong(controllerUUID.getLeastSignificantBits());
            }
            dos.write(snapshotBytes);
            dos.flush();
            kNetworkHandler.sendToPlayer(new PacketUUIDAssignedData(PACKET_TYPE, cluster.clusterUUID, bos.toByteArray()), playerMP);
        } catch (IOException e) {
            FMLLog.log(Level.ERROR, "ComputerCluster client snapshot encode failed", e);
        }
    }

    public static void sendGetClusterDataPacket(String playerID, UUID clusterUUID, @Nullable UUID controllerUUID, byte type) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             DataOutputStream dos = new DataOutputStream(bos)) {
            dos.writeByte(type);
            dos.writeUTF(playerID);
            dos.writeBoolean(controllerUUID != null);
            if (controllerUUID != null) {
                dos.writeLong(controllerUUID.getMostSignificantBits());
                dos.writeLong(controllerUUID.getLeastSignificantBits());
            }
            dos.flush();
            kNetworkHandler.sendToServer(new PacketUUIDAssignedData(PACKET_TYPE, clusterUUID, bos.toByteArray()));
        } catch (IOException e) {
            FMLLog.log(Level.ERROR, "ComputerCluster request packet encode failed", e);
        }
    }
}
