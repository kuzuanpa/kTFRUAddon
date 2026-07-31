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
import cn.kuzuanpa.ktfruaddon.api.network.PacketUUIDAssignedData;
import codechicken.lib.vec.BlockCoord;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.player.EntityPlayerMP;
import gregapi.util.WD;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.kNetworkHandler;

public class ComputerCluster {
    public static final byte PACKET_TYPE = 1;
    public static final byte PACKET_REQUEST_SYNC = 0;
    public static final byte PACKET_SUBSCRIBE = 1;
    public static final byte PACKET_UNSUBSCRIBE = 2;
    public static final byte PACKET_SYNC_DATA = -1;
    public static final byte PACKET_NOT_FOUND = -2;
    public static final int MAX_CLUSTER_EVENTS = 32;
    public static final int MAX_CONTROLLER_EVENTS = 24;
    public static final int MAX_USER_EVENTS = 16;

    public static final Map<UUID, ComputerCluster> allClusterUUIDsServer = new HashMap<>();
    public static final Map<UUID, ComputerClusterClientData.ClusterSnapshot> allClusterUUIDsClient = new HashMap<>();

    public Map<UUID, ControllerData> controllerList = new HashMap<>();
    @NotNull public Map<UUID, Integer> controllerReachableUpdateCache = new HashMap<>();
    public Map<UUID, UserData> userList = new HashMap<>();
    @NotNull public Map<ComputePower, Long> totalComputePower = new HashMap<>();
    public Map<ComputePower, Long> usedComputePower = new HashMap<>();
    public Queue<Byte> events = new ArrayDeque<>();
    public Queue<String> eventExtra = new ArrayDeque<>();
    public Map<EntityPlayerMP, UUID> portableViewerPlayers = new HashMap<>();
    public byte reachableCheckState = 0;

    public long lastUpdateTime = -1;
    public long lastClientSyncTick = -1;
    public UUID clusterUUID;
    public byte state = Constants.STATE_OFFLINE;

    public ComputerCluster(UUID uuid) {
        if(uuid != null)this.clusterUUID = uuid;
        else this.clusterUUID = UUID.randomUUID();
        allClusterUUIDsServer.put(this.clusterUUID, this);
        pushClusterEvent(Constants.EVENT_CLUSTER_CREATED, "cluster=" + shortUUID(this.clusterUUID));
        //Avoid NPE
        totalComputePower.put(ComputePower.Normal, 0L);
        totalComputePower.put(ComputePower.Biology, 0L);
        totalComputePower.put(ComputePower.Quantum, 0L);
        totalComputePower.put(ComputePower.Spacetime, 0L);
    }
    public static ComputerCluster create(World initialControllerWorld, BlockCoord initialControllerPos) {
        ComputerCluster cluster = new ComputerCluster(null);
        if (cluster.join(initialControllerWorld,initialControllerPos) != null)return null;
        cluster.update();
        return cluster;
    }

    public void update(){
        if(MinecraftServer.getServer().getTickCounter() <= lastUpdateTime)return;
        byte oldClusterState = state;
        if(reachableCheckState == 2){
        }
        if(reachableCheckState == 1){
            controllerList.forEach(((uuid, data) ->  {
                IComputerClusterController controller = getControllerFromData(data);
                if(controller!=null)controller.updateReachable();
            }));
            controllerList.forEach((uuid,data)-> {
                IComputerClusterController controller = getControllerFromData(data);
                if(controller!=null)controller.checkUpdatedReachable();
            });
            controllerReachableUpdateCache.clear();
            reachableCheckState = 0;
        }
        lastUpdateTime= MinecraftServer.getServer().getTickCounter();
        Map<ComputePower, Long> totalComputePowerMap = new HashMap<>();
        //Avoid NPE
        totalComputePowerMap.put(ComputePower.Normal, 0L);
        totalComputePowerMap.put(ComputePower.Biology, 0L);
        totalComputePowerMap.put(ComputePower.Quantum, 0L);
        totalComputePowerMap.put(ComputePower.Spacetime, 0L);
        controllerList.forEach((uuid,data) -> {
            ControllerData oldData = data.copy();
            updateControllerData(uuid,data,totalComputePowerMap);
            if(!oldData.equals(data)) data.needToSendToClient=true;
        });
        state = computeClusterState();
        if (oldClusterState != state) pushClusterEvent(Constants.EVENT_STATE_CHANGED, oldClusterState + " -> " + state);
        totalComputePower = totalComputePowerMap;
        syncViewerPlayers();
    }

    protected byte computeClusterState() {
        boolean hasOnline = false;
        boolean hasWarning = false;
        for (ControllerData data : controllerList.values()) {
            if (data.state == Constants.STATE_ERROR || data.state == Constants.STATE_BELONG_ERR) return Constants.STATE_ERROR;
            if (data.state == Constants.STATE_WARNING) hasWarning = true;
            if (data.state == Constants.STATE_NORMAL) hasOnline = true;
        }
        if (hasWarning) return Constants.STATE_WARNING;
        if (hasOnline) return Constants.STATE_NORMAL;
        return Constants.STATE_OFFLINE;
    }

    protected static void pushEvent(Queue<Byte> events, Queue<String> eventExtra, short event, String extra, int maxSize) {
        if (events.size() >= maxSize) {
            events.poll();
            eventExtra.poll();
        }
        events.add((byte) event);
        eventExtra.add(extra == null ? "" : extra);
    }

    protected void pushClusterEvent(short event, String extra) {
        pushEvent(events, eventExtra, event, extra, MAX_CLUSTER_EVENTS);
    }

    protected void pushControllerEvent(UUID controllerUUID, short event, String extra) {
        ControllerData controllerData = controllerList.get(controllerUUID);
        if (controllerData == null) return;
        pushEvent(controllerData.events, controllerData.eventExtra, event, extra, MAX_CONTROLLER_EVENTS);
    }

    protected void pushUserEvent(UserData userData, short event, String extra) {
        if (userData == null) return;
        pushEvent(userData.events, userData.eventExtra, event, extra, MAX_USER_EVENTS);
    }

    protected static String shortUUID(UUID uuid) {
        if (uuid == null) return "null";
        String str = uuid.toString();
        return str.substring(0, 8);
    }

    public void updateControllerData(UUID uuid, ControllerData data, Map<ComputePower, Long> totalComputePowerMap){
        IComputerClusterController controller = getControllerFromData(data);
        byte oldState = data.state;
        if(controller == null){
            data.state = Constants.STATE_OFFLINE;
            data.power = new SingleEntry<>(ComputePower.Normal, 0L);
            return;
        }

        if(controller.getCluster() == null){
            if(Objects.equals(uuid, controller.getUUID())){
                controller.setCluster(this);
                controller.updateReachable();
            }
        }
        else if(controller.getCluster() != this) {
            data.state = Constants.STATE_BELONG_ERR;
            return;
        }

        if(!Objects.equals(controller.getUUID(), uuid)){
            data.state = Constants.STATE_ERROR;
            controller.notifyControllerEvent(Constants.EVENT_WRONG_UUID);
            return;
        }
        data.state = controller.getState();
        if (oldState != data.state) pushControllerEvent(uuid, Constants.EVENT_STATE_CHANGED, oldState + " -> " + data.state);
        if(data.state == Constants.STATE_NORMAL) {
            data.power = controller.getComputePower();
            totalComputePowerMap.merge(data.power.getKey(), data.power.getValue(), Long::sum);
        }
        else data.power = new SingleEntry<>(ComputePower.Normal, 0L);
    }
    public void joinUser(IComputerClusterUser user){
        if(user == null || userList.get(user.getUUID()) != null)return;
        UserData data = new UserData(user);
        userList.put(user.getUUID(), data);
        pushClusterEvent(Constants.EVENT_USER_JOINED, "user=" + shortUUID(user.getUUID()));
        pushUserEvent(data, Constants.EVENT_USER_JOINED, "controller=" + shortUUID(user.getController() == null ? null : user.getController().getUUID()));
        updateUserData(user);
    }

    public UserData getUserData(UUID uuid){
        return userList.get(uuid);
    }

    public void updateUserData(IComputerClusterUser user){
        if(user == null)return;
        UserData data = userList.get(user.getUUID());
        if(data == null)return;

        if(user.getController() == null || user.getController().getCluster() != this){
            data.state = Constants.STATE_BELONG_ERR;
            pushUserEvent(data, Constants.EVENT_USER_LEFT, "controller lost");
            return;
        }
        data.lastUpdated = (short) (MinecraftServer.getServer().getTickCounter() % 16384);
        data.state = user.getState();
        if(data.state != Constants.STATE_NORMAL && data.state != Constants.STATE_WARNING){
            freeUserComputePower(user);
        }
    }

    public boolean isComputePowerSufficient(Map<ComputePower, Long> additions){
        return additions.entrySet().stream().allMatch(this::isComputePowerSufficient);
    }

    public boolean isComputePowerSufficient(Map.Entry<ComputePower, Long> power){
        long used = usedComputePower.get(power.getKey()) == null ? 0: usedComputePower.get(power.getKey());
        return totalComputePower.get(power.getKey()) >= (used + power.getValue());
    }

    public boolean allocateUserComputePower(IComputerClusterUser user){
        if(user == null)return false;
        if(getUserData(user.getUUID()) == null)joinUser(user);
        UserData data = getUserData(user.getUUID());
        if(data == null)return false;
        if(!data.consumingPower.isEmpty()) return true;

        if(Math.abs((MinecraftServer.getServer().getTickCounter() % 16384) - data.lastUpdated) > 5)updateUserData(user);
        if(data.state == Constants.STATE_NORMAL || data.state == Constants.STATE_WARNING){
            if (!isComputePowerSufficient(user.getComputePowerNeeded())) {
                pushClusterEvent(Constants.EVENT_POWER_ALLOCATE_FAILED, "user=" + shortUUID(user.getUUID()) + " insufficient " + ComputePower.getDescOneLine(user.getComputePowerNeeded()));
                pushUserEvent(data, Constants.EVENT_POWER_ALLOCATE_FAILED, "insufficient " + ComputePower.getDescOneLine(user.getComputePowerNeeded()));
                return false;
            }
            user.getComputePowerNeeded().forEach((k,v)->usedComputePower.merge(k, v, Long::sum));
            data.consumingPower = user.getComputePowerNeeded();
            pushClusterEvent(Constants.EVENT_POWER_ALLOCATED, "user=" + shortUUID(user.getUUID()) + " " + ComputePower.getDescOneLine(user.getComputePowerNeeded()));
            pushUserEvent(data, Constants.EVENT_POWER_ALLOCATED, ComputePower.getDescOneLine(user.getComputePowerNeeded()));
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
            data.consumingPower.forEach((k,v) -> usedComputePower.merge(k, v,(a, b) -> a-b));
            data.consumingPower.clear();
            user.onComputerPowerReleased();
            pushClusterEvent(Constants.EVENT_POWER_RELEASED, "user=" + shortUUID(user.getUUID()) + " " + released);
            pushUserEvent(data, Constants.EVENT_POWER_RELEASED, released);
        }
        return true;
    }

    public List<ControllerData> getOnlineControllers(){
        return controllerList.values().stream().filter(data-> data.state == Constants.STATE_NORMAL || data.state == Constants.STATE_WARNING).collect(Collectors.toList());
    }

    public static void recoverOrJoin(List<ControllerData> controllerList, UUID clusterUUID){
        ComputerCluster cluster = null;
        for (ControllerData data : controllerList) {
            IComputerClusterController controller = getControllerFromData(data);
            if(controller==null)continue;

            if(cluster != null && cluster.join(data.world,data.pos,controller) == null)continue;//Try join existing Cluster

            cluster = controller.getCluster();
            if(cluster == null && Objects.equals(controller.getSavedClusterUUID(), clusterUUID)) {//Create new Cluster
                cluster = new ComputerCluster(clusterUUID);
                cluster.join(data.world,data.pos,controller);
            }
        }
    }

    public String join(World world, BlockCoord pos){
        TileEntity tile = world.getTileEntity(pos.x,pos.y,pos.z);
        if(!(tile instanceof IComputerClusterController))return "ktfru.compute_cluster.msg.join.not_controller";
        return join(world,pos, (IComputerClusterController) tile);
    }

    /**@return ERROR message, null if successful**/
    public String join(World world, BlockCoord pos, IComputerClusterController controller){
        if(controller.getUUID()!=null && controllerList.containsKey(controller.getUUID())){
            UUID duplicatedUUID = controller.getUUID();
            if(world.equals(controllerList.get(duplicatedUUID).world) && pos.equals(controllerList.get(duplicatedUUID).pos)) return "ktfru.compute_cluster.msg.join.already_exist";
            controller.notifyControllerEvent(Constants.EVENT_WRONG_UUID);
            return "ktfru.compute_cluster.msg.join.duplicate_uuid";
        }
        if((controller.getCluster() != null && controller.getCluster() != this)|| (controller.getSavedClusterUUID() != null && !Objects.equals(controller.getSavedClusterUUID(), this.clusterUUID)))return "ktfru.compute_cluster.msg.join.belong_other";
        else if(controller.getCluster() == null && !controller.setCluster(this)) return "ktfru.compute_cluster.msg.join.belong_other";
        controllerList.put(controller.getUUID(),new ControllerData(world,pos));
        allClusterUUIDsServer.put(clusterUUID, this);
        String extra = "controller=" + shortUUID(controller.getUUID()) + " dim=" + world.provider.dimensionId + " pos=" + pos.x + "," + pos.y + "," + pos.z;
        pushClusterEvent(Constants.EVENT_CONTROLLER_JOINED, extra);
        pushControllerEvent(controller.getUUID(), Constants.EVENT_CONTROLLER_JOINED, "cluster=" + shortUUID(clusterUUID));
        return null;
    }

    public String kick(World world, BlockCoord pos){
        UUID uuid = null;
        for (Map.Entry<UUID, ControllerData> entry : controllerList.entrySet()) {
            UUID id = entry.getKey();
            ControllerData data = entry.getValue();
            if (data.world.equals(world) && data.pos.equals(pos)) {
                uuid = id;
                break;
            }
        }
        return kick(uuid);
    }

    public String kick(UUID uuid) {
        if (uuid == null || controllerList.get(uuid) == null) return "ktfru.compute_cluster.msg.kick.not_found";
        ControllerData data = controllerList.get(uuid);
        IComputerClusterController controller = getControllerFromData(data);
        if(controller == null) return "ktfru.compute_cluster.msg.kick.not_loaded";
        if(controller.getCluster() != this)return "ktfru.compute_cluster.msg.kick.not_belong_me";
        pushClusterEvent(Constants.EVENT_A_CONTROLLER_LEFT, "controller=" + shortUUID(uuid));
        remove0(uuid);
        controller.notifyControllerEvent(Constants.EVENT_KICKING_FROM_CLUSTER);
        return null;
    }

    public String quit(UUID uuid, IComputerClusterController controller){
        pushClusterEvent(Constants.EVENT_A_CONTROLLER_LEFT, "controller=" + shortUUID(uuid));
        remove0(uuid);
        if (controller != null) controller.notifyControllerEvent(Constants.EVENT_KICKING_FROM_CLUSTER);
        return null;
    }

    protected void remove0(UUID uuid){
        controllerList.remove(uuid);
        if (controllerList.isEmpty()) {
            portableViewerPlayers.clear();
            allClusterUUIDsServer.remove(clusterUUID);
        }
        postEventToAllControllers(Constants.EVENT_A_CONTROLLER_LEFT);
    }

    public void postEventToAllControllers(short event){
        controllerList.forEach(((uuid, data) ->  {
            IComputerClusterController controller = getControllerFromData(data);
            if(controller!=null)controller.notifyControllerEvent(event);
            pushControllerEvent(uuid, event, "");
        }));
    }
    public void updateAllControllerState(){
        reachableCheckState = 1;
    }
    public static IComputerClusterController getControllerFromData(ControllerData data){
        TileEntity te = WD.te(data.world,data.pos.x, data.pos.y, data.pos.z,false);
        if(te instanceof IComputerClusterController)return (IComputerClusterController) te;
        return null;
    }
    public void destroy(){
        postEventToAllControllers(Constants.EVENT_CLUSTER_DESTROY);
        pushClusterEvent(Constants.EVENT_CLUSTER_DESTROY, "cluster=" + shortUUID(clusterUUID));
        portableViewerPlayers.clear();
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
        return new ComputerClusterClientData.ControllerDetail(controllerData.state, (byte) controllerData.power.getKey().ordinal(), controllerData.power.getValue(), totalComputePower.get(controllerData.power.getKey()), controllerData.events.toArray(), controllerData.eventExtra.toArray(new String[0]));
    }

    public ComputerClusterClientData.ClusterSnapshot fetchClientSnapshot(UUID controllerID) {
        return new ComputerClusterClientData.ClusterSnapshot(
                fetchClientDataControllerList(),
                fetchClientDataUserList(),
                fetchClientDataClusterDetail(),
                fetchClientDataControllerDetail(controllerID)
        );
    }

    protected void syncViewerPlayers() {
        long tick = MinecraftServer.getServer().getTickCounter();
        if (portableViewerPlayers.isEmpty() || tick - lastClientSyncTick < 20) return;
        portableViewerPlayers.entrySet().removeIf(entry -> entry.getKey() == null || entry.getKey().isDead);
        if (portableViewerPlayers.isEmpty()) return;
        lastClientSyncTick = tick;
        for (Map.Entry<EntityPlayerMP, UUID> portableViewerPlayer : portableViewerPlayers.entrySet()) {
            sendClusterData(portableViewerPlayer.getKey(), this, portableViewerPlayer.getValue());
        }
    }

    public static @Nullable ComputerClusterClientData.ClusterSnapshot getClientSnapshot(UUID clusterUUID) {
        return allClusterUUIDsClient.get(clusterUUID);
    }

    public static void receiveUUIDAssignedData(UUID uuid, byte @Nullable [] data) {
        if (data == null) return;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             DataInputStream dis = new DataInputStream(bis)) {
            byte packetType = dis.readByte();
            if (packetType == PACKET_SYNC_DATA) {
                byte[] snapshotData = new byte[data.length - 1];
                System.arraycopy(data, 1, snapshotData, 0, snapshotData.length);
                allClusterUUIDsClient.put(uuid, ComputerClusterClientData.ClusterSnapshot.deserialize(snapshotData));
                return;
            }
            if (packetType == PACKET_NOT_FOUND) {
                allClusterUUIDsClient.remove(uuid);
                return;
            }

            String playerID = dis.readUTF();
            UUID controllerUUID = dis.readBoolean() ? new UUID(dis.readLong(), dis.readLong()) : null;
            EntityPlayerMP playerMP = (EntityPlayerMP) MinecraftServer.getServer().getConfigurationManager().playerEntityList.stream()
                    .filter(p -> playerID.equals(((EntityPlayerMP) p).getCommandSenderName()))
                    .findFirst().orElse(null);
            if (playerMP == null) return;

            ComputerCluster cluster = allClusterUUIDsServer.get(uuid);
            if (cluster == null) {
                kNetworkHandler.sendToPlayer(new PacketUUIDAssignedData(PACKET_TYPE, uuid, PACKET_NOT_FOUND), playerMP);
                return;
            }
            if (packetType == PACKET_REQUEST_SYNC) {
                sendClusterData(playerMP, cluster, controllerUUID);
                return;
            }
            if (packetType == PACKET_SUBSCRIBE) {
                cluster.portableViewerPlayers.put(playerMP, controllerUUID);
                sendClusterData(playerMP, cluster, controllerUUID);
                return;
            }
            if (packetType == PACKET_UNSUBSCRIBE) {
                cluster.portableViewerPlayers.remove(playerMP);
            }
        } catch (IOException e) {
            FMLLog.log(Level.ERROR, "ComputerCluster packet decode failed", e);
        }
    }

    public static void sendClusterData(EntityPlayerMP playerMP, ComputerCluster cluster, @Nullable UUID controllerUUID) {
        if (playerMP == null || cluster == null) return;
        try {
            if (controllerUUID == null && !cluster.controllerList.isEmpty()) controllerUUID = cluster.controllerList.keySet().iterator().next();

            byte[] snapshotBytes = ComputerClusterClientData.ClusterSnapshot.serialize(cluster.fetchClientSnapshot(controllerUUID));
            byte[] packetBytes = new byte[snapshotBytes.length + 1];
            packetBytes[0] = PACKET_SYNC_DATA;
            System.arraycopy(snapshotBytes, 0, packetBytes, 1, snapshotBytes.length);
            kNetworkHandler.sendToPlayer(new PacketUUIDAssignedData(PACKET_TYPE, cluster.clusterUUID, packetBytes), playerMP);
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

    protected void recordClusterEvent(short event, String extra) {
        pushClusterEvent(event, extra);
    }
}
