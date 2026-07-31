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

import gregapi.util.WD;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.DimensionManager;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IComputerClusterUser {
    IComputerClusterController getController();
    void setController(IComputerClusterController controller);
    List<IComputerClusterController> getBackupControllers();
    @Nullable default IComputerClusterUserContainer getContainer() {return null;}
    void setBackupControllers(List<IComputerClusterController> list);
    /**@return state, note state except NORMAL and WARNING will interrupt Compute Power consume.**/
    byte getState();
    void onComputerPowerReleased();
    default boolean tryStart(){
        if (getController() == null)return false;
        return getController().allocateUserComputePower(this);
    }
    default boolean tryStop(){
        if (getController() == null)return false;
        return getController().freeUserComputePower(this);
    }
    Map<ComputePower, Long> getComputePowerNeeded();
    UUID getUUID();
    void setUUID(UUID uuid);

    static void writeToNBT(NBTTagCompound nbt, IComputerClusterUser user){
        if(user.getUUID() !=null){
            nbt.setLong("myUUIDHigh", user.getUUID().getMostSignificantBits());
            nbt.setLong("myUUIDLow", user.getUUID().getLeastSignificantBits());
        }
        if(user.getController() == null)return;
        nbt.setIntArray("controller",new int[]{user.getController().getWorld().provider.dimensionId, user.getController().getPos().x, user.getController().getPos().y, user.getController().getPos().z});

        if(user.getController().getCluster() == null)return;
        List<ControllerData> list = user.getController().getCluster().getOnlineControllers();
        if(list.size() < 2)return;
        int[] ints = new int[list.size()*4];
        for (int i = 0; i < list.size(); i++) {
            ints[i*4] = list.get(i).world.provider.dimensionId;
            ints[i*4+1] = list.get(i).pos.x;
            ints[i*4+2] = list.get(i).pos.y;
            ints[i*4+3] = list.get(i).pos.z;
        }
        nbt.setIntArray("bkupControllers",ints);
    }
    static void readFromNBT(NBTTagCompound nbt, IComputerClusterUser user){
        if(nbt.hasKey("myUUIDHigh") && nbt.hasKey("myUUIDLow")) user.setUUID(new UUID(nbt.getLong("myUUIDHigh"), nbt.getLong("myUUIDLow")));
        else user.setUUID(UUID.randomUUID());
        if(nbt.hasKey("controller")){
            int[] controllerData = nbt.getIntArray("controller");
            TileEntity tile = WD.te(DimensionManager.getWorld(controllerData[0]),controllerData[1],controllerData[2],controllerData[3],false);
            if(tile instanceof IComputerClusterController)user.setController(((IComputerClusterController) tile));
        }
        if(nbt.hasKey("bkupControllers")){
            int[] data = nbt.getIntArray("bkupControllers");
            List<IComputerClusterController> bkupControllers = new ArrayList<>();
            for (int i = 0; i < data.length/4; i++) {
                TileEntity tile = WD.te(DimensionManager.getWorld(data[i*4]),data[i*4+1],data[i*4+2],data[i*4+3],false);
                if(tile instanceof IComputerClusterController)bkupControllers.add(((IComputerClusterController) tile));
            }
            user.setBackupControllers(bkupControllers);
        }
    }
}
