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

import codechicken.lib.vec.BlockCoord;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IComputerClusterController {
    UUID getUUID();

    /**Designed for writeToNBT to save data, used for cluster recovery**/
    UUID getSavedClusterUUID();
    /**Designed for writeToNBT to save data, used for cluster recovery**/
    void setSavedClusterUUID(UUID uuid);
    /**Designed for writeToNBT to save data, used for cluster recovery, data is simplified that only contains worldID and pos**/
    void setSavedClusterControllers(List<ControllerData> data);

    void setUUID(UUID uuid);
    byte getState();
    Map.Entry<ComputePower,Long> getComputePower();
    void notifyControllerEvent(short event);
    boolean setCluster(ComputerCluster cluster);
    ComputerCluster getCluster();
    boolean canReachController(IComputerClusterController controller);
    boolean canReachUser(IComputerClusterUser user);
    default void updateReachable() {};
    default void checkUpdatedReachable() {};
    World getWorld();
    BlockCoord getPos();
    default boolean allocateUserComputePower(IComputerClusterUser user){
        if(getCluster() == null || !canReachUser(user))return false;
        return getCluster().allocateUserComputePower(user);
    }
    default boolean freeUserComputePower(IComputerClusterUser user){
        if(getCluster() == null)return false;
        return getCluster().freeUserComputePower(user);
    }
    static void writeToNBT(NBTTagCompound nbt, IComputerClusterController controller){
        if(controller.getUUID() !=null){
            nbt.setLong("myUUIDHigh", controller.getUUID().getMostSignificantBits());
            nbt.setLong("myUUIDLow", controller.getUUID().getLeastSignificantBits());
        }
        if(controller.getCluster() !=null){
            nbt.setLong("clusterUUIDHigh", controller.getCluster().clusterUUID.getMostSignificantBits());
            nbt.setLong("clusterUUIDLow", controller.getCluster().clusterUUID.getLeastSignificantBits());
            NBTTagList controllers = new NBTTagList();
            for (Map.Entry<UUID, ControllerData> entry : controller.getCluster().controllerList.entrySet()) {
                ControllerData data = entry.getValue();
                controllers.appendTag(new NBTTagIntArray(new int[] {data.world.provider.dimensionId, data.pos.x, data.pos.y, data.pos.z}));
            }
            nbt.setTag("clusterControllers", controllers);
        }
    }
    static void readFromNBT(NBTTagCompound nbt, IComputerClusterController controller){
        if(nbt.hasKey("myUUIDHigh") && nbt.hasKey("myUUIDLow")) controller.setUUID(new UUID(nbt.getLong("myUUIDHigh"), nbt.getLong("myUUIDLow")));
        else controller.setUUID(UUID.randomUUID());
        if(nbt.hasKey("clusterUUIDHigh") && nbt.hasKey("clusterUUIDLow")){
            controller.setSavedClusterUUID(new UUID(nbt.getLong("clusterUUIDHigh"), nbt.getLong("clusterUUIDLow")));
            List<ControllerData> datas = new ArrayList<>();
            NBTTagList list = nbt.getTagList("clusterControllers", 11);
            for (int i = 0; i < list.tagCount(); i++) {
                int[] data = list.func_150306_c(i);
                datas.add(new ControllerData(DimensionManager.getWorld(data[0]), new BlockCoord(data[1],data[2],data[3])));
            }
            controller.setSavedClusterControllers(datas);
        }
    }
}
