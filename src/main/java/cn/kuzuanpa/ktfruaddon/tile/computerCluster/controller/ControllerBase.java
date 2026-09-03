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
import cn.kuzuanpa.ktfruaddon.api.code.WorldPos;
import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.*;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.SpecialPartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import cn.kuzuanpa.ktfruaddon.client.gui.computerCluster.ContainerClientClusterController;
import cn.kuzuanpa.ktfruaddon.client.gui.computerCluster.ContainerCommonClusterController;
import cpw.mods.fml.common.FMLLog;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.data.LH;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import gregapi.util.OM;
import gregapi.util.UT;
import gregapi.util.WD;
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
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;
import zmaster587.libVulpes.items.ItemProjector;

import java.io.*;
import java.util.*;

import static cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants.STATE_NORMAL;
import static cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants.STATE_OFFLINE;
import static gregapi.data.CS.*;

public class ControllerBase extends TileEntityBase10MultiBlockBase implements IReachabilityLimitedController, IMultiTileEntity.IMTE_SyncDataByteArray, SpecialPartPredicate.IReceiveSpecialPart {
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.computecluster.controller.base";
    }
    UUID myUUID = null,clusterUUID = null;
    ComputerCluster cluster = null;
    List<ControllerData> clusterControllers = new ArrayList<>();
    /**Set while {@link #clusterControllers} still has to be replayed to rebuild the cluster after a world load.**/
    boolean clusterRecoveryPending = false;
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        IComputerClusterController.readFromNBT(aNBT,this);
        clusterRecoveryPending = !clusterControllers.isEmpty();
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
        if(aTool.equals(TOOL_plunger)) {
            if(cluster != null) return 0;
            cluster = ComputerCluster.create(worldObj, new WorldPos(xCoord,yCoord,zCoord,worldObj.provider.dimensionId));
            if(aChatReturn != null) aChatReturn.add(LH.get(cluster == null ? I18nHandler.COMPUTE_CLUSTER_MSG_CLUSTER_CREATE_FAILED : I18nHandler.COMPUTE_CLUSTER_MSG_CLUSTER_CREATED));
            return 10000;
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if(!aIsServerSide)return;
        if(clusterRecoveryPending && !clusterControllers.isEmpty() && aTimer%100 == 1){
            ComputerCluster.recoverOrJoin(clusterControllers, clusterUUID);
            if(cluster != null){
                clusterRecoveryPending = false;
                clusterControllers.clear();
            }
        }
        if(aTimer%CLUSTER_UPDATE_INTERVAL == 0)updateProvidedComputePower();
        if(cluster!=null && aTimer%CLUSTER_UPDATE_INTERVAL == 0)cluster.update();
    }

    /**Only one controller of a cluster has to drive the update, the cluster itself skips duplicated calls in the same tick.**/
    public static final int CLUSTER_UPDATE_INTERVAL = 20;
    /**Reach results are cached for this many ticks, walking a wire or cable network per query is far too costly.**/
    public static final int REACH_CACHE_TICKS = 40;

    private final Map<WorldPos, Boolean> reachCache = new HashMap<>();
    private long reachCacheTick = -1;

    /**@return the cached answer for that target, null when there is none for the current cache window.**/
    protected @Nullable Boolean getCachedReach(WorldPos target) {
        long tick = getWorld() == null ? -1 : getWorld().getTotalWorldTime();
        if (tick < 0) return null;
        if (tick - reachCacheTick >= REACH_CACHE_TICKS) {
            reachCache.clear();
            reachCacheTick = tick;
            return null;
        }
        return reachCache.get(target);
    }

    /**@return {@code result}, so callers can tail call this.**/
    protected boolean putCachedReach(WorldPos target, boolean result) {
        reachCache.put(target, result);
        return result;
    }

    /**Called whenever the medium changed, the next query has to walk the network again.**/
    protected void clearReachCache() {
        reachCache.clear();
        reachCacheTick = getWorld() == null ? -1 : getWorld().getTotalWorldTime();
    }

    @Override
    public boolean breakBlock() {
        if(isServerSide() && cluster != null && myUUID != null) cluster.quit(myUUID, this);
        return super.breakBlock();
    }

    @Override
    public boolean onTickCheck(long aTimer) {
        //Client data is pushed by updateClientData() whenever something visible actually changed.
        return false;
    }

    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            //The colour and direction bytes are always written, the directional base reads them unconditionally.
            dos.writeByte((byte) UT.Code.getR(this.mRGBa));
            dos.writeByte((byte) UT.Code.getG(this.mRGBa));
            dos.writeByte((byte) UT.Code.getB(this.mRGBa));
            dos.writeByte(this.getVisualData());
            dos.writeByte(this.getDirectionData());
            writeUUIDSyncData(dos, myUUID, clusterUUID);
            dos.flush();
            return getClientDataPacketByteArray(aSendAll, bos.toByteArray());
        } catch (IOException e) {
            FMLLog.log(Level.ERROR, "Cluster controller client data encode failed", e);
            return super.getClientDataPacket(aSendAll);
        }
    }

    @Override
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler) {
        if (aData == null || aData.length < 5) return false;
        super.receiveDataByteArray(aData, aNetworkHandler);
        readUUIDSyncData(aData);
        return true;
    }

    /**One presence flag plus two longs per UUID, for both myUUID and clusterUUID.**/
    private static final int UUID_SYNC_DATA_LENGTH = 2 * (1 + 2 * 8);

    private static void writeUUIDSyncData(DataOutputStream dos, UUID myUUID, UUID clusterUUID) throws IOException {
        dos.writeBoolean(myUUID != null);
        dos.writeLong(myUUID == null ? 0L : myUUID.getMostSignificantBits());
        dos.writeLong(myUUID == null ? 0L : myUUID.getLeastSignificantBits());
        dos.writeBoolean(clusterUUID != null);
        dos.writeLong(clusterUUID == null ? 0L : clusterUUID.getMostSignificantBits());
        dos.writeLong(clusterUUID == null ? 0L : clusterUUID.getLeastSignificantBits());
    }

    /**The UUID block is always the tail of the packet, whatever the paintable base wrote in front of it.**/
    private void readUUIDSyncData(byte[] aData) {
        if (aData == null || aData.length < UUID_SYNC_DATA_LENGTH) return;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(aData, aData.length - UUID_SYNC_DATA_LENGTH, UUID_SYNC_DATA_LENGTH);
             DataInputStream dis = new DataInputStream(bis)) {
            if (dis.readBoolean()) setUUID(new UUID(dis.readLong(), dis.readLong()));
            else setUUID(null);
            if (dis.readBoolean()) setSavedClusterUUID(new UUID(dis.readLong(), dis.readLong()));
            else setSavedClusterUUID(null);
        } catch (IOException e) {
            FMLLog.log(Level.ERROR, "Cluster controller UUID sync decode failed", e);
        }
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


    public boolean addControllerFromUSB(EntityPlayer aPlayer) {
        ItemStack equippedItem = aPlayer.getCurrentEquippedItem();
        if (!(OM.is(OD_USB_STICKS[0], equippedItem))) return false;

        if (!equippedItem.hasTagCompound() || !equippedItem.getTagCompound().hasKey(NBT_USB_DATA)) return false;
        NBTTagCompound aNBT = equippedItem.getTagCompound().getCompoundTag(NBT_USB_DATA);

        if (!aNBT.hasKey("worldID") || !aNBT.hasKey(NBT_TARGET_X) || !aNBT.hasKey(NBT_TARGET_Y) || !aNBT.hasKey(NBT_TARGET_Z)) {
            sendJoinFailed(aPlayer, LH.get(I18nHandler.COMPUTE_CLUSTER_MSG_USB_DATA_INVALID));
            return true;
        }

        ChunkCoordinates coord = new ChunkCoordinates();
        World world = DimensionManager.getWorld(aNBT.getInteger("worldID"));

        if (world == null) {
            sendJoinFailed(aPlayer, LH.get(I18nHandler.COMPUTE_CLUSTER_MSG_USB_WORLD_MISSING) + " " + aNBT.getInteger("worldID"));
            return true;
        }

        coord.posX = aNBT.getInteger(NBT_TARGET_X);
        coord.posY = aNBT.getInteger(NBT_TARGET_Y);
        coord.posZ = aNBT.getInteger(NBT_TARGET_Z);
        TileEntity tile = WD.te(world, coord, false);
        String err = joinController(tile);
        if (err != null) {
            sendJoinFailed(aPlayer, LH.get(err));
            return true;
        }
        aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN + LH.get(I18nHandler.COMPUTE_CLUSTER_MSG_JOIN_SUCCESS)));
        return true;
    }

    protected void sendJoinFailed(EntityPlayer aPlayer, String reason) {
        aPlayer.addChatMessage(new ChatComponentText(LH.Chat.YELLOW + LH.get(I18nHandler.COMPUTE_CLUSTER_MSG_JOIN_FAILED) + " " + reason));
    }

    /**@return an i18n key describing why the join failed, null when it succeeded.**/
    public String joinController(TileEntity tile) {
        if (!(tile instanceof IComputerClusterController) || ((IComputerClusterController) tile).getCluster() == null) {
            return I18nHandler.COMPUTE_CLUSTER_MSG_JOIN_NOT_LOADED;
        }
        IComputerClusterController target = (IComputerClusterController) tile;
        if (canReachPos(target.getPos())) return target.getCluster().join(getWorld(), getPos(), this);

        //direct reach failed, try every controller
        return joinCluster(target.getCluster());
    }

    public String joinCluster(ComputerCluster cluster){
        for (ControllerData onlineController : cluster.getOnlineControllers()) {
            IComputerClusterController controller = ComputerCluster.getControllerFromData(onlineController);
            if (controller == null || controller.getCluster() == null || !canReachPos(controller.getPos())) continue;
            return controller.getCluster().join(getWorld(), getPos(), this);
        }
        return I18nHandler.COMPUTE_CLUSTER_MSG_JOIN_UNREACHABLE_ANY;
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(isServerSide()) {
            if(!mStructureOkay) aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED+LH.get(I18nHandler.STRUCTURE_ERR)));

            ItemStack equippedItem = aPlayer.getCurrentEquippedItem();
            if (equippedItem != null && equippedItem.getItem() instanceof ItemProjector) {
                getStructure().checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
                return true;
            }
            if(aPlayer.isSneaking())writePosToUSB(aPlayer);
            else if(!addControllerFromUSB(aPlayer))openGUI(aPlayer, aSide);
            return true;
        }
        return false;
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
        clusterControllers = data == null ? new ArrayList<>() : data;
        clusterRecoveryPending = !clusterControllers.isEmpty();
    }

    @Override
    public List<ControllerData> getSavedClusterControllers() {
        return clusterControllers;
    }

    public byte mState = STATE_NORMAL;
    @Override
    public byte getState() {
        return mState;
    }

    public ComputePower mProvidedType = ComputePower.Normal;
    public long mProvidedAmount = 0L;

    /**
     * The nodes feeding this controller, collected by the structure check from the
     * {@link IComputePart} part slots of the multiblock. Positions whose tile is gone or not loaded
     * are skipped, so a partially unloaded structure simply provides less.
     */
    protected List<IComputePart> getComputeNodes() {
        List<IComputePart> nodes = new ArrayList<>();
        for (ChunkCoordinates coord : computeNodesCoord) {
            TileEntity tile = WD.te(worldObj, coord, true);
            if (tile instanceof IComputePart) nodes.add((IComputePart) tile);
        }
        return nodes;
    }

    /**Recomputes what this controller offers to its cluster, keeping the single type rule.**/
    public void updateProvidedComputePower() {
        long amount = 0L;
        //A broken structure must not keep feeding the cluster.
        if (mStructureOkay) for (IComputePart node : getComputeNodes()) {
            if(!node.getType().equals(mProvidedType))continue;
            amount += node.getComputePower();
        }
        if (amount == mProvidedAmount) return;
        mProvidedAmount = amount;
    }

    @Override
    public Map.Entry<ComputePower, Long> getComputePower() {
        return new SingleEntry<>(mProvidedType, mProvidedAmount);
    }

    @Override
    public void notifyControllerEvent(byte event) {
        switch (event){
            case Constants.EVENT_WRONG_UUID:
                myUUID = UUID.randomUUID();
                updateClientData();
                break;
            case Constants.EVENT_KICKING_FROM_CLUSTER:
            case Constants.EVENT_CLUSTER_DESTROY:
                detachFromCluster();
                break;
        }
    }

    protected void detachFromCluster() {
        cluster = null;
        clusterUUID = null;
        clusterControllers = new ArrayList<>();
        clusterRecoveryPending = false;
        mState = STATE_NORMAL;
        updateClientData();
    }

    @Override
    public boolean setCluster(ComputerCluster cluster) {
        if(getState() == STATE_OFFLINE|| this.cluster!=null)return false;
        this.cluster=cluster;
        this.clusterUUID = cluster == null ? null : cluster.clusterUUID;
        updateClientData();//the GUI needs the cluster UUID to be able to subscribe
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
    public boolean canReachPos(WorldPos coord) {
        return true;
    }

    @Override
    public WorldPos getPos() {
        return new WorldPos(xCoord,yCoord,zCoord, worldObj.provider.dimensionId);
    }

    @Override
    public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerClientClusterController(aPlayer.inventory, this, aGUIID,"");
    }
    @Override
    public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerCommonClusterController(aPlayer.inventory, this, aGUIID);
    }

    //Structure
    /**The compute node part positions found by the last structure check.**/
    protected final List<ChunkCoordinates> computeNodesCoord = new ArrayList<>();
    protected ChunkCoordinates lastFailedPos = null;

    /**@return the layout of this controller model, subclasses only differ in this and in the node count.**/
    public IStringBaseStructure getStructure() {
        return null;
    }

    @Override
    public void receiveSpecialPart(ChunkCoordinates partPos, TileEntity part) {
        computeNodesCoord.add(partPos);
    }

    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        if (getStructure() == null) return true;
        if (!worldObj.blockExists(xCoord, yCoord, zCoord)) return mStructureOkay;
        computeNodesCoord.clear();
        lastFailedPos = getStructure().checkStructure(new StructureContext(this, (aPlayer != null || aInventory != null) ? StructureContext.StringBaseMode.SET : StructureContext.StringBaseMode.CHECK, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        return lastFailedPos == null;
    }

    @Override
    public void onMagnifyingGlass(List<String> aChatReturn) {
        super.onMagnifyingGlass(aChatReturn);
        if (lastFailedPos != null) aChatReturn.add(LH.get(I18nHandler.STRUCTURE_LAST_FAILED_POS) + " " + lastFailedPos);
    }

    @Override
    public void onMagnifyingGlass2(List<String> aChatReturn) {
        aChatReturn.add(LH.get(I18nHandler.STRUCTURE_FORMED));
        aChatReturn.add(LH.get(I18nHandler.COMPUTE_CLUSTER_2) + ComputePower.getDescOneLine(getComputePower()));
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        super.addToolTips(aList, aStack, aF3_H);
    }

    /**The bounding box of this model, {@code x/y/zSize} count blocks, the offsets are the map origin.**/
    public short getSizeX() {return 1;}
    public short getSizeY() {return 1;}
    public short getSizeZ() {return 1;}
    public short getMapOffsetX() {return 0;}
    public short getMapOffsetZ() {return 0;}

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return new BoundingBox(
                utils.getRealX(mFacing, xCoord, getMapOffsetX(), getMapOffsetZ()), yCoord, utils.getRealZ(mFacing, zCoord, getMapOffsetX(), getMapOffsetZ()),
                utils.getRealX(mFacing, utils.getRealX(mFacing, xCoord, getMapOffsetX(), getMapOffsetZ()), getSizeX(), getSizeZ()), yCoord + getSizeY(), utils.getRealZ(mFacing, utils.getRealZ(mFacing, zCoord, getMapOffsetX(), getMapOffsetZ()), getSizeX(), getSizeZ())
        ).isXYZInBox(aX, aY, aZ);
    }

    @Override public byte getDefaultSide() {return SIDE_FRONT;}
    @Override public boolean[] getValidSides() {return SIDES_HORIZONTAL;}
}
