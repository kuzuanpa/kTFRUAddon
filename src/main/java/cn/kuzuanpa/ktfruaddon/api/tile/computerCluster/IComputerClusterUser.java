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

import cn.kuzuanpa.ktfruaddon.api.code.WorldPos;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import gregapi.data.LH;
import gregapi.util.OM;
import gregapi.util.WD;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static gregapi.data.CS.*;

public interface IComputerClusterUser {
    IComputerClusterController getController();
    void setController(IComputerClusterController controller);
    List<IComputerClusterController> getBackupControllers();
    @Nullable default IComputerClusterUserContainer getContainer() {return null;}
    void setBackupControllers(List<IComputerClusterController> list);
    /**@return state, note state except NORMAL and WARNING will interrupt Compute Power consume.**/
    byte getState();
    void onComputerPowerReleased();

    /**
     * Where this user sits in the world, used by controllers to decide whether they can reach it.
     * Resolved from the container when there is one, otherwise from the TileEntity itself.
     * @return null when the position cannot be determined, reach checks then have to fail closed.
     */
    default WorldPos getUserPos() {
        IComputerClusterUserContainer container = getContainer();
        if (container != null) return new WorldPos(container.getX(), container.getY(), container.getZ(), container.getWorld().provider.dimensionId);
        if (this instanceof TileEntity) return new WorldPos(((TileEntity) this).xCoord, ((TileEntity) this).yCoord, ((TileEntity) this).zCoord, ((TileEntity) this).getWorldObj().provider.dimensionId);
        return null;
    }

    /**@return the world this user sits in, null when unknown, see {@link #getUserPos()}.**/
    @Nullable default World getUserWorld() {
        IComputerClusterUserContainer container = getContainer();
        if (container != null) return container.getWorld();
        if (this instanceof TileEntity) return ((TileEntity) this).getWorldObj();
        return null;
    }

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

    /**
     * Binds this user to the controller written on the USB stick the player is holding, telling the
     * player why it did not work. Does nothing when the held item is not a USB stick.
     * @return true when a controller got assigned.
     */
    static boolean bindControllerFromUSB(EntityPlayer aPlayer, IComputerClusterUser user){
        ItemStack equippedItem = aPlayer.getCurrentEquippedItem();
        if (!OM.is(OD_USB_STICKS[0], equippedItem)) return false;
        if (!equippedItem.hasTagCompound() || !equippedItem.getTagCompound().hasKey(NBT_USB_DATA)) return false;
        NBTTagCompound aNBT = equippedItem.getTagCompound().getCompoundTag(NBT_USB_DATA);

        if(!aNBT.hasKey("worldID") || !aNBT.hasKey(NBT_TARGET_X) || !aNBT.hasKey(NBT_TARGET_Y) || !aNBT.hasKey(NBT_TARGET_Z))
            return bindFailed(aPlayer, LH.get(I18nHandler.COMPUTE_CLUSTER_MSG_USB_DATA_INVALID));

        World world = DimensionManager.getWorld(aNBT.getInteger("worldID"));
        if(world == null)
            return bindFailed(aPlayer, LH.get(I18nHandler.COMPUTE_CLUSTER_MSG_USB_WORLD_MISSING) + " " + aNBT.getInteger("worldID"));

        TileEntity tile = WD.te(world, new ChunkCoordinates(aNBT.getInteger(NBT_TARGET_X), aNBT.getInteger(NBT_TARGET_Y), aNBT.getInteger(NBT_TARGET_Z)), false);
        if(!(tile instanceof IComputerClusterController) || ((IComputerClusterController) tile).getCluster() == null)
            return bindFailed(aPlayer, LH.get(I18nHandler.COMPUTE_CLUSTER_MSG_JOIN_NOT_LOADED));

        IComputerClusterController target = (IComputerClusterController) tile;
        if(!IReachabilityLimitedController.isUserReachable(target, user))
            return bindFailed(aPlayer, LH.get(I18nHandler.COMPUTE_CLUSTER_MSG_JOIN_UNREACHABLE_ANY));

        user.setController(target);
        aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN + LH.get(I18nHandler.COMPUTE_CLUSTER_MSG_JOIN_SUCCESS)));
        return true;
    }

    static boolean bindFailed(EntityPlayer aPlayer, String reason){
        aPlayer.addChatMessage(new ChatComponentText(LH.Chat.YELLOW + LH.get(I18nHandler.COMPUTE_CLUSTER_MSG_JOIN_FAILED) + " " + reason));
        return false;
    }

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
            ints[i*4] = list.get(i).pos.dim;
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
            if(controllerData.length >= 4){
                World world = DimensionManager.getWorld(controllerData[0]);
                TileEntity tile = world == null ? null : WD.te(world,controllerData[1],controllerData[2],controllerData[3],false);
                if(tile instanceof IComputerClusterController)user.setController(((IComputerClusterController) tile));
            }
        }
        if(nbt.hasKey("bkupControllers")){
            int[] data = nbt.getIntArray("bkupControllers");
            List<IComputerClusterController> bkupControllers = new ArrayList<>();
            for (int i = 0; i < data.length/4; i++) {
                World world = DimensionManager.getWorld(data[i*4]);
                if(world == null)continue;
                TileEntity tile = WD.te(world,data[i*4+1],data[i*4+2],data[i*4+3],false);
                if(tile instanceof IComputerClusterController)bkupControllers.add(((IComputerClusterController) tile));
            }
            user.setBackupControllers(bkupControllers);
        }
    }
}
