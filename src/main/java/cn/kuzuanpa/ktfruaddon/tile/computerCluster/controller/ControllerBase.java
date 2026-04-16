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
package cn.kuzuanpa.ktfruaddon.tile.computerCluster.controller;

import cn.kuzuanpa.ktfruaddon.api.code.SingleEntry;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.*;
import cn.kuzuanpa.ktfruaddon.client.gui.computerCluster.ContainerClientClusterController;
import cn.kuzuanpa.ktfruaddon.client.gui.computerCluster.ContainerCommonClusterController;
import codechicken.lib.vec.BlockCoord;
import gregapi.data.LH;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase07Paintable;
import gregapi.util.OM;
import gregapi.util.UT;
import gregapi.util.WD;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants.STATE_NORMAL;
import static cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants.STATE_OFFLINE;
import static gregapi.data.CS.*;

public class ControllerBase extends TileEntityBase07Paintable implements IComputerClusterController {
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.computecluster.controller.base";
    }
    UUID myUUID = null,clusterUUID = null;
    ComputerCluster cluster = null;
    List<ControllerData> clusterControllers = new ArrayList<>();
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        IComputerClusterController.readFromNBT(aNBT,this);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        IComputerClusterController.writeToNBT(aNBT,this);
    }

    @Override
    public boolean canDrop(int aSlot) {
        return false;
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(!isServerSide())return 0;
        if(aTool.equals(TOOL_plunger)) cluster = ComputerCluster.create(worldObj, new BlockCoord(xCoord,yCoord,zCoord));
        if(aTool.equals(TOOL_chisel)) cluster.join(worldObj,new BlockCoord(xCoord,yCoord+1,zCoord), (IComputerClusterController) WD.te(worldObj,xCoord,yCoord+1,zCoord,false));

        return 10000;
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if(!aIsServerSide)return;
        if(clusterControllers != null && aTimer%100 == 1){
            ComputerCluster.recoverOrJoin(clusterControllers, clusterUUID);
            if(cluster != null)clusterControllers=null;
        }
        if(cluster!=null && aTimer%40 == 0)cluster.update();
    }

    @Override
    public boolean onTickCheck(long aTimer) {
        if(aTimer%10 == 0)return true;
        return false;
    }
    public boolean clickDoubleCheck=false;

    public void writePosToUSB(EntityPlayer aPlayer){
        ItemStack equippedItem=aPlayer.getCurrentEquippedItem();
        if (!(OM.is(OD_USB_STICKS[0],equippedItem))) return;
        NBTTagCompound aNBT = UT.NBT.make();
        aNBT.setInteger("worldID", this.worldObj.provider.dimensionId);
        aNBT.setInteger(NBT_TARGET_X, this.xCoord);
        aNBT.setInteger(NBT_TARGET_Y, this.yCoord);
        aNBT.setInteger(NBT_TARGET_Z, this.zCoord);

        if (equippedItem.hasTagCompound()) {
            if (clickDoubleCheck) {
                equippedItem.getTagCompound().setTag(NBT_USB_DATA, aNBT);
                equippedItem.getTagCompound().setByte(NBT_USB_TIER, (byte)1);
                aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN+LH.get(I18nHandler.DATA_WRITE_TO_USB)));
                clickDoubleCheck=false;
            } else {
                aPlayer.addChatMessage(new ChatComponentText(LH.Chat.YELLOW+LH.get(I18nHandler.USB_ALREADY_HAVE_DATA)));
                clickDoubleCheck=true;
            }
        }
        if (!equippedItem.hasTagCompound()){
            equippedItem.setTagCompound(UT.NBT.make());
            equippedItem.getTagCompound().setTag(NBT_USB_DATA, aNBT);
            equippedItem.getTagCompound().setByte(NBT_USB_TIER, (byte)1);
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN+LH.get(I18nHandler.DATA_WRITE_TO_USB)));
        }
    }

    boolean ClusterRequestClicked = false;

    public boolean addControllerFromUSB(EntityPlayer aPlayer) {
        ItemStack equippedItem = aPlayer.getCurrentEquippedItem();
        if (!(OM.is(OD_USB_STICKS[0], equippedItem))) return false;

        if (!equippedItem.hasTagCompound() || !equippedItem.getTagCompound().hasKey(NBT_USB_DATA)) return false;
        NBTTagCompound aNBT = equippedItem.getTagCompound().getCompoundTag(NBT_USB_DATA);

        if (!aNBT.hasKey("worldID") || !aNBT.hasKey(NBT_TARGET_X) || !aNBT.hasKey(NBT_TARGET_Y) || !aNBT.hasKey(NBT_TARGET_Z)) {
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN + LH.get("Join Failed: USB data not valid")));
            ClusterRequestClicked = false;
            return true;
        }

        ChunkCoordinates coord = new ChunkCoordinates();
        World world = DimensionManager.getWorld(aNBT.getInteger("worldID"));

        if (world == null) {
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN + LH.get("Join Failed: World ID" + aNBT.getInteger("worldID") + " not exists")));
            ClusterRequestClicked = false;
            return true;
        }

        coord.posX = aNBT.getInteger(NBT_TARGET_X);
        coord.posY = aNBT.getInteger(NBT_TARGET_Y);
        coord.posZ = aNBT.getInteger(NBT_TARGET_Z);
        TileEntity tile = WD.te(world, coord, false);
        String err = joinController(tile);
        if (err != null) {
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN + LH.get("Join Failed: "+ err)));
            return true;
        }
        else {
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN + LH.get("Join Success")));
            ClusterRequestClicked = false;
            return true;
        }
    }

    public String joinController(TileEntity tile) {
        if (!(tile instanceof IComputerClusterController) || ((IComputerClusterController) tile).getCluster() == null) {
            ClusterRequestClicked = false;
            return "target not exist or not loaded";
        }
        if (!canReachController(((IComputerClusterController) tile))) {
            if (!ClusterRequestClicked) {
                ClusterRequestClicked = true;
                return "cannot reach target, click again to try join with ALL controller in cluster";
            } else {
                ClusterRequestClicked = false;
                for (ControllerData onlineController : ((IComputerClusterController) tile).getCluster().getOnlineControllers()) {
                    IComputerClusterController controller = ComputerCluster.getControllerFromData(onlineController);
                    if (controller == null || !canReachController(controller)) continue;
                    return controller.getCluster().join(getWorld(), new BlockCoord(xCoord, yCoord, zCoord), this);
                }
                return "cannot reach any target";
            }
        }
        return ((IComputerClusterController) tile).getCluster().join(getWorld(), new BlockCoord(xCoord, yCoord, zCoord), this);
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(isServerSide()) {
            if(aPlayer.isSneaking())writePosToUSB(aPlayer);
            else if(!addControllerFromUSB(aPlayer))openGUI(aPlayer, aSide);
            return true;
        }
        return false;
    }

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return null;
    }

    @Override
    public UUID getUUID() {
        return myUUID;
    }

    @Override
    public void setUUID(UUID uuid) {
        myUUID=uuid;
    }

    @Override
    public UUID getSavedClusterUUID() {
        return clusterUUID;
    }
    @Override
    public void setSavedClusterUUID(UUID uuid) {
        clusterUUID = uuid;
    }

    @Override
    public void setSavedClusterControllers(List<ControllerData> data) {
        clusterControllers = data;
    }

    public byte mState = STATE_NORMAL;
    @Override
    public byte getState() {
        return mState;
    }

    @Override
    public Map.Entry<ComputePower, Long> getComputePower() {
        return new SingleEntry<>(ComputePower.Normal, 1000L);
    }

    @Override
    public void notifyControllerEvent(short event) {
        switch (event){
            case Constants.EVENT_WRONG_UUID: myUUID =UUID.randomUUID();
            case Constants.EVENT_CLUSTER_DESTROY: cluster=null;
        }
    }

    @Override
    public boolean setCluster(ComputerCluster cluster) {
        if(getState() == STATE_OFFLINE|| this.cluster!=null)return false;
        this.cluster=cluster;
        return true;
    }
    
    //inventory
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[1];}

    private static final int[] ACCESSIBLE_SLOTS = new int[] {0};

    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}

    @Override
    public ComputerCluster getCluster() {
        return cluster;
    }

    @Override
    public boolean canReachController(IComputerClusterController controller) {
        return true;
    }

    @Override
    public boolean canReachUser(IComputerClusterUser user) {
        return true;
    }

    @Override
    public BlockCoord getPos() {
        return new BlockCoord(xCoord,yCoord,zCoord);
    }

    @Override
    public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerClientClusterController(aPlayer.inventory, this, aGUIID,"");
    }
    @Override
    public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerCommonClusterController(aPlayer.inventory, this, aGUIID);
    }
}
