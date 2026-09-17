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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.parts;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.*;
import cn.kuzuanpa.ktfruaddon.api.tile.part.IMultiBlockPart;
import cn.kuzuanpa.ktfruaddon.api.tile.util.kTileNBT;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import gregapi.GT_API;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.LH;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants.STATE_NORMAL;
import static cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants.STATE_OFFLINE;
import static gregapi.data.CS.*;

/**
 * A compute node that owns no hardware, it rents Compute Power from the cluster it is bound to and
 * presents it to the host multiblock as if it was local. Type and amounts come from the registry NBT
 * only, so a placed part is fixed to the model it was built as.
 */
public class WirelessComputePart extends TileEntityBase09FacingSingle implements IMultiTileEntity.IMTE_SyncDataByteArray, IMultiTileEntity.IMTE_AddToolTips, IMultiBlockPart, IComputerClusterUser, IComputePart {
    public ComputePower mType = ComputePower.Normal;
    /**Maximum amount this part can provide to the host multiblock.**/
    public long mMaxProvided = 0;
    /**Extra fraction consumed from the bound cluster in addition to the amount actually provided.**/
    public float mLossRate = 0F;
    /**Amount currently held in the bound cluster. This tracks the active request, not the configured maximum.**/
    public long mRequested = 0;
    public boolean mRunning = false;
    /**Whether the cluster currently holds power for this part. Keeps stop() and the release callback idempotent.**/
    protected boolean mAllocated = false;

    public UUID myUUID = null;
    public IComputerClusterController mController = null;
    public List<IComputerClusterController> mBackupControllers = new ArrayList<>();

    public IIconContainer sTextureCommon, sOverlayFront, sOverlayFrontActive;

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_TARGET)) mTargetPos = IMultiBlockPart.readTargetPosFromNBT(aNBT);
        if (aNBT.hasKey(NBT_DESIGN)) mDesign = UT.Code.unsignB(aNBT.getByte(NBT_DESIGN));
        if (aNBT.hasKey(kTileNBT.COMPUTE_POWER_TYPE)) mType = ComputePower.getType(aNBT.getInteger(kTileNBT.COMPUTE_POWER_TYPE));
        if (aNBT.hasKey(kTileNBT.COMPUTE_POWER_MAX_PROVIDED)) {
            mMaxProvided = Math.max(0L, aNBT.getLong(kTileNBT.COMPUTE_POWER_MAX_PROVIDED));
        } else if (aNBT.hasKey("ktfru.nbt.computePower.provided")) {
            // Keep existing worlds readable while only writing the new maxProvided key.
            mMaxProvided = Math.max(0L, aNBT.getLong("ktfru.nbt.computePower.provided"));
        }
        if (aNBT.hasKey(kTileNBT.COMPUTE_POWER_LOSS_RATE)) {
            mLossRate = normalizeLossRate(aNBT.getFloat(kTileNBT.COMPUTE_POWER_LOSS_RATE));
        } else if (mMaxProvided > 0L && aNBT.hasKey("ktfru.nbt.computePower.requested")) {
            long legacyRequested = Math.max(0L, aNBT.getLong("ktfru.nbt.computePower.requested"));
            mLossRate = normalizeLossRate((float) ((double) legacyRequested / mMaxProvided - 1D));
        }
        IComputerClusterUser.readFromNBT(aNBT, this);

        if (CODE_CLIENT) {
            if (GT_API.sBlockIcons == null && aNBT.hasKey(NBT_TEXTURE)) {
                String tTextureName = aNBT.getString(NBT_TEXTURE);
                sTextureCommon      = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/" + tTextureName + "/background");
                sOverlayFront       = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/" + tTextureName + "/normal/front");
                sOverlayFrontActive = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/" + tTextureName + "/active/front");
            } else {
                TileEntity tCanonicalTileEntity = MultiTileEntityRegistry.getCanonicalTileEntity(getMultiTileEntityRegistryID(), getMultiTileEntityID());
                if (tCanonicalTileEntity instanceof WirelessComputePart) {
                    sTextureCommon      = ((WirelessComputePart) tCanonicalTileEntity).sTextureCommon;
                    sOverlayFront       = ((WirelessComputePart) tCanonicalTileEntity).sOverlayFront;
                    sOverlayFrontActive = ((WirelessComputePart) tCanonicalTileEntity).sOverlayFrontActive;
                } else {
                    sTextureCommon = sOverlayFront = sOverlayFrontActive = L6_IICONCONTAINER[0];
                }
            }
        }
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        IMultiBlockPart.writeToNBT(aNBT, mTargetPos, mDesign);
        aNBT.setInteger(kTileNBT.COMPUTE_POWER_TYPE, mType.ordinal());
        aNBT.setLong(kTileNBT.COMPUTE_POWER_MAX_PROVIDED, mMaxProvided);
        aNBT.setFloat(kTileNBT.COMPUTE_POWER_LOSS_RATE, mLossRate);
        IComputerClusterUser.writeToNBT(aNBT, this);
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.computenode.wireless";
    }

    // IComputePart
    @Override public ComputePower getType() {return mType;}
    /**The capacity, not the current output. Hosts check this before they ask us to start.**/
    @Override public long getComputePower() {return mMaxProvided;}
    @Override public boolean isActive() {return mRunning;}
    @Override public boolean isPartiallyAllocatable() {return true;}

    @Override
    public boolean tryStart(long needed) {
        if (needed < 0L || needed > mMaxProvided) return false;
        long requested = calculateRequested(needed);
        boolean wasRunning = mRunning;
        long previousRequested = mRequested;
        if (wasRunning && mAllocated && requested == previousRequested) return true;

        // Set the requested amount before allocation because the cluster reads getComputePowerNeeded().
        // ComputerCluster only applies the delta, so changing the load neither leaks nor double-charges power.
        mRequested = requested;
        mRunning = true;
        if (!IComputerClusterUser.super.tryStart()) {
            mRunning = wasRunning;
            mRequested = previousRequested;
            return false;
        }
        mAllocated = true;
        if (!wasRunning || previousRequested != requested) updateClientData();
        return true;
    }

    @Override
    public void stop() {
        boolean wasRunning = mRunning;
        boolean wasAllocated = mAllocated;
        mRunning = false;
        mAllocated = false;
        mRequested = 0L;
        if (wasAllocated) IComputerClusterUser.super.tryStop();
        if (wasRunning || wasAllocated) updateClientData();
    }

    // IComputerClusterUser
    @Override public IComputerClusterController getController() {return mController;}
    @Override public void setController(IComputerClusterController controller) {mController = controller;}
    @Override public List<IComputerClusterController> getBackupControllers() {return mBackupControllers;}
    @Override public void setBackupControllers(List<IComputerClusterController> list) {mBackupControllers = list;}
    @Override public byte getState() {return mRunning ? STATE_NORMAL : STATE_OFFLINE;}
    @Override public Map<ComputePower, Long> getComputePowerNeeded() {return mType.asMap(mRequested);}
    @Override public UUID getUUID() {return myUUID;}
    @Override public void setUUID(UUID uuid) {myUUID = uuid;}

    /**Called by the cluster when it took the rented power away, the host has to notice through {@link #getComputePower()}.**/
    @Override
    public void onComputerPowerReleased() {
        boolean changed = mRunning || mAllocated || mRequested != 0L;
        mAllocated = false;
        mRunning = false;
        mRequested = 0L;
        if (changed) updateClientData();
    }

    /**Calculates the base request plus the configured wireless overhead, rounded up to a whole unit.**/
    protected long calculateRequested(long needed) {
        if (needed <= 0L) return 0L;
        double extra = Math.ceil(needed * (double) mLossRate);
        if (extra <= 0D) return needed;
        if (extra >= Long.MAX_VALUE - needed) return Long.MAX_VALUE;
        return needed + (long) extra;
    }

    protected static float normalizeLossRate(float lossRate) {
        if (Float.isNaN(lossRate) || lossRate <= 0F) return 0F;
        if (Float.isInfinite(lossRate)) return Float.MAX_VALUE;
        return lossRate;
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (isServerSide()) IComputerClusterUser.bindControllerFromUSB(aPlayer, this);
        return T;
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        //Nothing keeps this part running once the structure fell apart.
        if (aIsServerSide && aTimer % 20 == 0 && getTarget(false) == null) stop();
    }

    @Override
    public boolean breakBlock() {
        if (isServerSide()) stop();
        notifyTarget();
        return super.breakBlock();
    }

    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + mType.prefixedDesc(mMaxProvided));
        aList.add(LH.Chat.DGRAY + String.format("Wireless loss: %.2f%%", mLossRate * 100F));
        aList.add(LH.Chat.DGRAY + LH.get(LH.TOOL_TO_DETAIL_MAGNIFYINGGLASS));
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (aTool.equals(TOOL_magnifyingglass) && aChatReturn != null) {
            aChatReturn.add(LH.get(I18nHandler.COMPUTE_CLUSTER_1) + (mRunning ? LH.get(I18nHandler.NORMAL) : LH.get(I18nHandler.COMPUTE_CLUSTER_3)));
            aChatReturn.add(LH.get(I18nHandler.COMPUTE_CLUSTER_2) + mType.desc(mMaxProvided));
            if (mRunning) aChatReturn.add(LH.get(I18nHandler.COMPUTE_CLUSTER_2) + mType.desc(mRequested));
            aChatReturn.add(LH.get(I18nHandler.COMPUTE_CLUSTER_MSG_JOIN_SUCCESS) + ": " + (mController == null ? LH.get(I18nHandler.COMPUTE_CLUSTER_3) : mController.getPos().toString()));
        }
        if (getFacingTool() != null && aTool.equals(getFacingTool())) {byte aTargetSide = UT.Code.getSideWrenching(aSide, aHitX, aHitY, aHitZ); if (getValidSides()[aTargetSide]) {byte oFacing = mFacing; mFacing = aTargetSide; updateClientData(); causeBlockUpdate(); onFacingChange(oFacing); return 10000;}}
        return 0;
    }

    @Override public boolean[] getValidSides() {return SIDES_HORIZONTAL;}
    @Override public byte getDefaultSide() {return SIDE_FRONT;}
    @Override public boolean canDrop(int aInventorySlot) {return T;}

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (!aShouldSideBeRendered[aSide]) return null;
        if (aSide != mFacing) return BlockTextureDefault.get(sTextureCommon, mRGBa);
        return mRunning
                ? BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon, mRGBa), BlockTextureDefault.get(sOverlayFront), BlockTextureDefault.get(sOverlayFrontActive, T))
                : BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon, mRGBa), BlockTextureDefault.get(sOverlayFront));
    }

    @Override
    public void addCollisionBoxesToList2(AxisAlignedBB aAABB, List<AxisAlignedBB> aList, Entity aEntity) {
        box(aAABB, aList, PX_P[0], PX_P[0], PX_P[0], PX_P[16], PX_P[16], PX_P[16]);
    }

    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        ByteArrayDataOutput aData = ByteStreams.newDataOutput();
        aData.writeByte((byte) UT.Code.getR(mRGBa));
        aData.writeByte((byte) UT.Code.getG(mRGBa));
        aData.writeByte((byte) UT.Code.getB(mRGBa));
        aData.writeByte(getDirectionData());
        aData.writeBoolean(mRunning);
        return getClientDataPacketByteArray(aSendAll, aData.toByteArray());
    }

    @Override
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler) {
        ByteArrayDataInput data = ByteStreams.newDataInput(aData);
        mRGBa = UT.Code.getRGBInt(new short[] {UT.Code.unsignB(data.readByte()), UT.Code.unsignB(data.readByte()), UT.Code.unsignB(data.readByte())});
        setDirectionData(data.readByte());
        mRunning = data.readBoolean();
        return T;
    }

    public ChunkCoordinates mTargetPos = null;
    public ITileEntityMultiBlockController mTarget = null;
    public int mDesign = 0;
    @Override public ITileEntityMultiBlockController getTarget2() {return mTarget;}
    @Override public void setTarget(ITileEntityMultiBlockController target) {mTarget = target;}
    @Override public ChunkCoordinates getTargetPos() {return mTargetPos;}
    @Override public void setTargetPos(ChunkCoordinates aCoords) {mTargetPos = aCoords;}
    @Override public void setDesign(int aDesign) {this.mDesign = aDesign;}
    @Override public int getDesign() {return mDesign;}
}
