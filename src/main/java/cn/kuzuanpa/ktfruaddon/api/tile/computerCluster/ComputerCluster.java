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
import codechicken.lib.vec.BlockCoord;
import gregapi.util.WD;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;


public class ComputerCluster {
    public Map<UUID, ControllerData> controllerList = new HashMap<>();
    @NotNull public Map<UUID, Integer> controllerReachableUpdateCache = new HashMap<>();
    public Map<UUID, UserData> userList = new HashMap<>();
    @NotNull public Map<ComputePower, Long> totalComputePower = new HashMap<>();
    public Map<ComputePower, Long> usedComputePower = new HashMap<>();
    public Queue<Byte> events = new ArrayDeque<>();
    public byte reachableCheckState = 0;

    public long lastUpdateTime = -1;
    public UUID clusterUUID;
    public byte state = Constants.STATE_OFFLINE;

    public ComputerCluster(UUID uuid) {
        if(uuid != null)this.clusterUUID = uuid;
        else this.clusterUUID = UUID.randomUUID();
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
        totalComputePower = totalComputePowerMap;
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

    public void updateControllerData(UUID uuid, ControllerData data, Map<ComputePower, Long> totalComputePowerMap){
        IComputerClusterController controller = getControllerFromData(data);
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
            if (!isComputePowerSufficient(user.getComputePowerNeeded()))return false;
            user.getComputePowerNeeded().forEach((k,v)->usedComputePower.merge(k, v, Long::sum));
            data.consumingPower = user.getComputePowerNeeded();
            return true;
        }
        return false;
    }

    public boolean freeUserComputePower(IComputerClusterUser user){
        if(user == null || getUserData(user.getUUID()) == null)return false;
        UserData data = getUserData(user.getUUID());
        if(data == null)return false;
        if(!data.consumingPower.isEmpty()){
            data.consumingPower.forEach((k,v) -> usedComputePower.merge(k, v,(a, b) -> a-b));
            data.consumingPower.clear();
            user.onComputerPowerReleased();
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
        else if(controller.getCluster() == null)controller.setCluster(this);
        controllerList.put(controller.getUUID(),new ControllerData(world,pos));
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
        controller.notifyControllerEvent(Constants.EVENT_KICKING_FROM_CLUSTER);
        remove0(uuid);
        return null;
    }

    public String quit(UUID uuid, IComputerClusterController controller){
        remove0(uuid);
        return null;
    }

    protected void remove0(UUID uuid){
        controllerList.remove(uuid);
        postEventToAllControllers(Constants.EVENT_A_CONTROLLER_LEFT);
    }

    public void postEventToAllControllers(short event){
        controllerList.forEach(((uuid, data) ->  {
            IComputerClusterController controller = getControllerFromData(data);
            if(controller!=null)controller.notifyControllerEvent(event);
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
    }

    public ComputerClusterClientData.ControllerList fetchClientDataControllerList() {
        return new ComputerClusterClientData.ControllerList(controllerList.values());
    }

    public ComputerClusterClientData.UserList fetchClientDataUserList() {
        return new ComputerClusterClientData.UserList(userList.values());
    }

    public ComputerClusterClientData.ClusterDetail fetchClientDataClusterDetail() {
        return new ComputerClusterClientData.ClusterDetail(state,controllerList.size(), userList.size(),totalComputePower,usedComputePower, events.toArray());
    }

    public ComputerClusterClientData.ControllerDetail fetchClientDataControllerDetail(UUID controllerID) {
        ControllerData controllerData = controllerList.get(controllerID);
        if (controllerData == null) return new ComputerClusterClientData.ControllerDetail(Constants.STATE_OFFLINE, (byte) 0, 0, 0, new byte[0]);
        return new ComputerClusterClientData.ControllerDetail(controllerData.state, (byte) controllerData.power.getKey().ordinal(), controllerData.power.getValue(), totalComputePower.get(controllerData.power.getKey()), controllerData.events.toArray());
    }
}
