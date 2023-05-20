/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */


package cn.kuzuanpa.ktfruaddon.tile.parts;

import cn.kuzuanpa.ktfruaddon.item.items.itemComputer;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.data.LH;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.util.ST;
import gregapi.util.UT;
import gregapi.util.WD;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;

import java.util.List;

import static gregapi.data.CS.*;

public class MultiBlockPartComputeClusterSimple extends MultiBlockPartComputeCluster implements IMultiTileEntity.IMTE_SyncDataByteArray, IMultiTileEntity.IMTE_AddToolTips {
    public ChunkCoordinates mTargetPos = null;
    public ITileEntityMultiBlockController mTarget = null;

    protected IIconContainer[][] mTextures;
    public short mDesign;
    public boolean isRunning;
    public long ComputePower;
    public byte[] mDisplaySlot = {0,0};
    @Override
    public void updateComputePower() {
        ComputePower =0;
        for (ItemStack stack:getInventory()) if (stack != null) {
            long toAdd = itemComputer.getComputePowerFromID(stack.getItemDamage());
            if (toAdd < 150) ComputePower += toAdd;
        }
    }
    public long getComputePower(){return ComputePower;}
    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (isServerSide()&&!isRunning&&aSide!=SIDE_BOTTOM&&aSide!=SIDE_TOP) {
            byte tSlot = (byte)(aHitY >0.5?0:1);
            ItemStack aStack = aPlayer.getCurrentEquippedItem();
            if (ST.valid(aStack) && aStack.getUnlocalizedName().contains("ktfru.item.it.computer")&&slot(tSlot)==null)
                    if (ST.move(aPlayer.inventory, this, aPlayer.inventory.currentItem, tSlot,1) > 0) {
                        playClick();
                        mDisplaySlot[tSlot] = 1;
                        updateClientData();
                    return T;
                }
            if (slotHas(tSlot) && aStack == null && UT.Inventories.addStackToPlayerInventoryOrDrop(aPlayer, slot(tSlot), T, worldObj, xCoord + 0.5, yCoord + 1.2, zCoord + 0.5)) {
                slotKill(tSlot);
                updateInventory();
                mDisplaySlot[tSlot] = 0;
                updateClientData();
                return T;
            }
        }

        return T;
    }
@Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.ORANGE   + LH.get(LH.NO_GUI_CLICK_TO_INTERACT)   + " (" + LH.get(LH.FACE_SIDES) + ")");
        aList.add(LH.Chat.DGRAY    + LH.get(LH.TOOL_TO_DETAIL_MAGNIFYINGGLASS));
    }
    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (aTool.equals(TOOL_magnifyingglass)) {
    if (aChatReturn != null) {
        boolean saidSomething = F;
        for (int i=0;i < 2;i++) if (slot(i) != null) {
            saidSomething = T;
            aChatReturn.add("Slot"+i+" has " + slot(i).getDisplayName());
        }
        if (!saidSomething) aChatReturn.add("Contains no Compute Node");
        updateComputePower();
        aChatReturn.add("Total Computing Power:"+getComputePower());
        aChatReturn.add("Is this cluster Running:"+isRunning);
    }
}
    return 0;}

    // Inventory Stuff
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[2];}
    @Override public boolean canDrop(int aInventorySlot) {return T;}
    private static final int[] ACCESSIBLE_SLOTS = new int[] {0, 1};

    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}

    public void onTick2(long aTimer, boolean aIsServerSide) {
        if (aIsServerSide) {
        for (int i=0;i<2;i++){
            if (slot(i)!=null)mDisplaySlot[i]=1;
            else mDisplaySlot[i]=0;
        }
        }
    }
    @Override
    public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {
        if (aSlot >= 2||isRunning) return F;
        for (int i = 0; i < 2; i++) if (slot(i)== null&&aStack.getItem().getUnlocalizedName().contains("ktfru.item.it.computer")) {
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
            UT.NBT.setNumber(aNBT, "gt.target.z", this.mTargetPos.posZ);
        }
    }
    public static IIconContainer
            sTextureSides       = new Textures.BlockIcons.CustomIcon("machines/test/colored/sides"),
            sTextureTop         = new Textures.BlockIcons.CustomIcon("machines/test/colored/top"),
            sTextureBottom      = new Textures.BlockIcons.CustomIcon("machines/test/colored/bottom"),
            sTextureNodeSide = new Textures.BlockIcons.CustomIcon("machines/test/colored/nodeside"),
            sOverlaySides       = new Textures.BlockIcons.CustomIcon("machines/test/overlay/sides"),
    sOverlayTop         = new Textures.BlockIcons.CustomIcon("machines/test/overlay/top"),
    sOverlayBottom      = new Textures.BlockIcons.CustomIcon("machines/test/overlay/bottom"),
    sOverlayNodeSide = new Textures.BlockIcons.CustomIcon("machines/test/overlay/nodeside");
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        switch(aRenderPass) {
            case 0: return aShouldSideBeRendered[aSide] ?SIDE_TOP == aSide?BlockTextureMulti.get(BlockTextureDefault.get(sTextureTop, mRGBa), BlockTextureDefault.get(sOverlayTop)):SIDE_BOTTOM==aSide?BlockTextureMulti.get(BlockTextureDefault.get(sTextureBottom, mRGBa), BlockTextureDefault.get(sOverlayBottom)): BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides, mRGBa), BlockTextureDefault.get(sOverlaySides)) : null;
            case 1: return aShouldSideBeRendered[aSide]&&mDisplaySlot[1] ==1&&SIDE_TOP!=aSide&&SIDE_BOTTOM!=aSide?BlockTextureMulti.get(BlockTextureDefault.get(sTextureNodeSide, mRGBa), BlockTextureDefault.get(sOverlayNodeSide)): null;
            case 2: return aShouldSideBeRendered[aSide]&&mDisplaySlot[0] ==1&&SIDE_TOP!=aSide&&SIDE_BOTTOM!=aSide?BlockTextureMulti.get(BlockTextureDefault.get(sTextureNodeSide, mRGBa), BlockTextureDefault.get(sOverlayNodeSide)):null;
        }
        return null;
    }
    @Override
    public int getRenderPasses2(Block aBlock, boolean[] aShouldSideBeRendered) {
        return 3;
    }
    @Override
    public void addCollisionBoxesToList2(AxisAlignedBB aAABB, List<AxisAlignedBB> aList, Entity aEntity) {
       box(aAABB,aList, PX_P[ 0], PX_P[ 0], PX_P[ 0], PX_P[16], PX_P[ 16], PX_P[ 16]);
       box(aAABB,aList, PX_P[ 0]-0.0001F, PX_P[ 5], PX_P[ 0]-0.0001F, PX_P[16]+0.0001F, PX_P[ 7], PX_P[16]+0.0001F);
       box(aAABB,aList, PX_P[ 0]-0.0001F, PX_P[13], PX_P[ 0]-0.0001F, PX_P[16]+0.0001F, PX_P[15], PX_P[16]+0.0001F);
    }
    @Override
    public boolean setBlockBounds2(Block aBlock, int aRenderPass, boolean[] aShouldSideBeRendered) {
        switch(aRenderPass) {
            case  0: return box(aBlock, PX_P[ 0], PX_P[ 0], PX_P[ 0], PX_P[16], PX_P[ 16], PX_P[ 16]);
            case  1: return box(aBlock, PX_P[ 0]-0.0001F, PX_P[ 5], PX_P[ 0]-0.0001F, PX_P[16]+0.0001F, PX_P[ 7], PX_P[16]+0.0001F);
            case  2: return box(aBlock, PX_P[ 0]-0.0001F, PX_P[13], PX_P[ 0]-0.0001F, PX_P[16]+0.0001F, PX_P[15], PX_P[16]+0.0001F);
        }
        return F;
    }


@Override
public IPacket getClientDataPacket(boolean aSendAll) {
        return getClientDataPacketByteArray(aSendAll,mDisplaySlot[0], mDisplaySlot[1]);
        }

@Override
public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler) {
        mDisplaySlot=aData;
        return T;
        }
    //Every thing from MultiBlockPart
    public ITileEntityMultiBlockController getTarget(boolean aCheckValidity) {
        if (this.mTargetPos == null) {
            return null;
        } else {
            if (this.mTarget == null || this.mTarget.isDead()) {
                this.mTarget = null;
                if (this.worldObj.blockExists(this.mTargetPos.posX, this.mTargetPos.posY, this.mTargetPos.posZ)) {
                    TileEntity tTarget = WD.te(this.worldObj, this.mTargetPos, true);
                    if (tTarget instanceof ITileEntityMultiBlockController && ((ITileEntityMultiBlockController)tTarget).isInsideStructure(this.xCoord, this.yCoord, this.zCoord)) {
                        this.mTarget = (ITileEntityMultiBlockController)tTarget;
                    } else {
                        this.mTargetPos = null;
                    }
                }
            }

            return aCheckValidity && this.mTarget != null && !this.mTarget.checkStructure(false) ? null : this.mTarget;
        }
    }
@Override
    public void setTarget(ITileEntityMultiBlockController aTarget, int aDesign, int aMode) {
        this.mTarget = aTarget;
        this.mTargetPos = this.mTarget == null ? null : this.mTarget.getCoords();
    }
    @Override
    public boolean breakBlock(){
//todo: These code are from various abstract class ,and It should be directly inherit from upper class...but I really didn't know how can this be applied in java..
//MultiBlockBase
        ITileEntityMultiBlockController tTarget = this.getTarget(false);
        if (tTarget != null) {
            this.mTargetPos = null;
            this.mTarget = null;
            tTarget.onStructureChange();
        }
//Base05Inventories
        if (isServerSide()) for (short i = 0; i < invsize(); i++) if (slot(i) != null && canDrop(i) && !ST.debug(slot(i)) && breakDrop(i)) {
            ItemStack tDumpedStack = ST.amount(UT.Code.bind_(0, 512L * Math.max(1, slot(i).getMaxStackSize()), slot(i).stackSize), slot(i));
            int tMaxSize = Math.max(1, slot(i).getMaxStackSize());

            while (tDumpedStack.stackSize > tMaxSize) {
                ST.drop(worldObj, getCoords(), ST.amount(tMaxSize, tDumpedStack));
                tDumpedStack.stackSize -= tMaxSize;
                slot(i).stackSize -= tMaxSize;
            }
            if (tDumpedStack.stackSize > 0) {
                slot(i).stackSize -= tDumpedStack.stackSize;
                ST.drop(worldObj, getCoords(), ST.copy(tDumpedStack));
            }

            GarbageGT.trash(slot(i));
        }
        return F;
    }
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.computenode.simple";
    }
    @Override
    public int getLightOpacity(){
        return mDesign==7?0:255;
    }
}
