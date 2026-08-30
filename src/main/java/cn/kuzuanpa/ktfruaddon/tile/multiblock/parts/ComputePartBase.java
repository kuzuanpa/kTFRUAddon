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


import cn.kuzuanpa.ktfruaddon.api.code.SingleEntry;
import cn.kuzuanpa.ktfruaddon.api.code.StateMgr;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.item.IComputerItem;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputeNode;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputeUser;
import cn.kuzuanpa.ktfruaddon.api.tile.part.IMultiBlockPart;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.data.LH;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static gregapi.data.CS.*;

public abstract class ComputePartBase extends TileEntityBase09FacingSingle implements IMultiTileEntity.IMTE_SyncDataByteArray, IMultiTileEntity.IMTE_AddToolTips, IMultiBlockPart, IComputeUser, IComputeNode {
    public StateMgr mState = new StateMgr();
    protected long mComputePower = 0;
    public final byte nodeCount;
    public final byte[] mDisplaySlot;

    public ComputePartBase(byte nodeCount){
        this.nodeCount = nodeCount;
        mDisplaySlot = new byte[nodeCount];
    }

    public void updateComputePower() {
        long amount = 0;
        for (ItemStack stack:getInventory()) if (stack != null) {
            Map.Entry<ComputePower, Long> entry =  IComputerItem.getComputePower(stack, this) ;
            ComputePower type = entry.getKey();
            if(type != getType()) continue;
            amount += entry.getValue();
        }
        mComputePower = amount;
    }

    @Override
    public boolean tryStart(long needed) {
        if (needed > mComputePower || mState.get() == 1)return false;
        IComputerItem.tryStartAll(Arrays.asList(getInventory()), this, true);
        mState.set(1);
        return true;
    }

    @Override
    public void stop(){
        IComputerItem.stopAll(Arrays.asList(getInventory()), this);
        mState.set(0);
    }

    @Override
    public boolean isActive() {
        return mState.get() == 1;
    }

    public long getComputePower(){return mComputePower;}

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide() || isActive() || aSide==SIDE_BOTTOM || aSide==SIDE_TOP) return F;

        byte tSlot = getSlotClicked(aSide, aHitX, aHitY, aHitZ);
        ItemStack aStack = aPlayer.getCurrentEquippedItem();
        if (ST.valid(aStack) && slot(tSlot)==null && aStack.getItem() instanceof IComputerItem)
            if (ST.move(aPlayer.inventory, this, aPlayer.inventory.currentItem, tSlot,1) > 0) {
                playClick();
                mDisplaySlot[tSlot] = 1;
                updateClientData();
                updateComputePower();
                return T;
               }

        if (slotHas(tSlot) && aStack == null && UT.Inventories.addStackToPlayerInventoryOrDrop(aPlayer, slot(tSlot), T, worldObj, xCoord + 0.5, yCoord + 1.2, zCoord + 0.5)) {
            slotKill(tSlot);
            updateInventory();
            mDisplaySlot[tSlot] = 0;
            updateClientData();
            updateComputePower();
            return T;
        }
        return T;
    }

    public abstract byte getSlotClicked(byte aSide, float aHitX, float aHitY, float aHitZ);

    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.ORANGE   + LH.get(LH.NO_GUI_CLICK_TO_INTERACT)   + " (" + LH.get(LH.FACE_SIDES) + ")");
        aList.add(LH.Chat.DGRAY    + LH.get(LH.TOOL_TO_DETAIL_MAGNIFYINGGLASS));
    }
    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (aTool.equals(TOOL_magnifyingglass)) {
            if (aChatReturn != null) {
                boolean saidSomething = F;
                for (int i=0;i < nodeCount;i++) if (slot(i) != null) {
                    aChatReturn.add(LH.get(I18nHandler.SLOT)+i+": " + slot(i).getDisplayName());
                    saidSomething = T;
                }
                if (!saidSomething) aChatReturn.add(LH.get(I18nHandler.COMPUTE_CLUSTER_0));
                updateComputePower();
                aChatReturn.add(LH.get(I18nHandler.COMPUTE_CLUSTER_1)+(isActive()?LH.get(I18nHandler.NORMAL):LH.get(I18nHandler.COMPUTE_CLUSTER_3)));
                aChatReturn.add(LH.get(I18nHandler.COMPUTE_CLUSTER_2)+ComputePower.getDescOneLine(new SingleEntry<>(getType(), getComputePower())));
            }
        }
        if (getFacingTool() != null && aTool.equals(getFacingTool())) {byte aTargetSide = UT.Code.getSideWrenching(aSide, aHitX, aHitY, aHitZ); if (getValidSides()[aTargetSide]) {byte oFacing = mFacing; mFacing = aTargetSide; updateClientData(); causeBlockUpdate(); onFacingChange(oFacing); return 10000;}}
        return 0;
    }

    // Inventory Stuff
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[nodeCount];}
    @Override public boolean canDrop(int aInventorySlot) {return T;}

    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return UT.Code.getAscendingArray(nodeCount);}

    public void onTick2(long aTimer, boolean aIsServerSide) {
        if (aIsServerSide) {
            for (int i=0;i<nodeCount;i++)if (slot(i)!=null)mDisplaySlot[i]=1;
            else mDisplaySlot[i]=0;
            if(aTimer%20==0&&getTarget(true)==null){stop();}
            if(aTimer%100==0)updateComputePower();
        }
    }
    @Override
    public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {
        if (aSlot >= nodeCount||isActive()) return F;
        for (int i = 0; i < nodeCount; i++) if (slot(i)== null&&aStack.getItem().getUnlocalizedName().contains("ktfru.item.it.computer")) {
            if (i == aSlot) {
                mDisplaySlot[i] = 1;
                updateClientData();
                return T;
            }
        }
        return F;
    }

    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {
        return F;
    }
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey("gt.target")) {
            this.mTargetPos = new ChunkCoordinates(UT.Code.bindInt(aNBT.getLong("gt.target.x")), UT.Code.bindInt(aNBT.getLong("gt.target.y")), UT.Code.bindInt(aNBT.getLong("gt.target.z")));
        }

    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        if (mDesign != 0) aNBT.setByte(NBT_DESIGN, (byte)mDesign);
        if (this.mTargetPos != null) {
            UT.NBT.setBoolean(aNBT, "gt.target", true);
            UT.NBT.setNumber(aNBT, "gt.target.x", this.mTargetPos.posX);
            UT.NBT.setNumber(aNBT, "gt.target.y", this.mTargetPos.posY);
            UT.NBT.setNumber(aNBT, "gt.target.z", this.mTargetPos.posZ);
        }

    }
    @Override public boolean[] getValidSides() {return SIDES_HORIZONTAL;}
    @Override public byte getDefaultSide() {return SIDE_FRONT;}


    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        ByteArrayDataOutput aData = ByteStreams.newDataOutput();
        aData.writeByte((byte)UT.Code.getR(mRGBa));
        aData.writeByte((byte)UT.Code.getG(mRGBa));
        aData.writeByte((byte)UT.Code.getB(mRGBa));
        aData.writeByte(getDirectionData());
        aData.writeByte(mState.get());
        aData.write(mDisplaySlot);
        return getClientDataPacketByteArray(aSendAll,aData.toByteArray());
    }

    @Override
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler) {
        ByteArrayDataInput data = ByteStreams.newDataInput(aData);

        mRGBa = UT.Code.getRGBInt(new short[] {UT.Code.unsignB(data.readByte()), UT.Code.unsignB(data.readByte()), UT.Code.unsignB(data.readByte())});
        setDirectionData(data.readByte());
        mState.set(data.readByte());
        data.readFully(mDisplaySlot);
        return T;
    }

    public ChunkCoordinates mTargetPos = null;
    public ITileEntityMultiBlockController mTarget = null;
    public int mDesign = 0;
    @Override public ITileEntityMultiBlockController getTarget2() {return mTarget;}
    @Override public void setTarget(ITileEntityMultiBlockController target) {mTarget = target;}
    @Override public ChunkCoordinates getTargetPos() {return mTargetPos;}
    @Override public void setTargetPos(ChunkCoordinates aCoords){mTargetPos=aCoords;}
    @Override public void setDesign(int aDesign) {this.mDesign = aDesign;}
    @Override public int getDesign(){return mDesign;}
    @Override
    public boolean breakBlock(){
        notifyTarget();
        return super.breakBlock();
    }
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.computenode.base";
    }
    @Override
    public int getLightOpacity(){
        return mDesign==7?0:255;
    }
}