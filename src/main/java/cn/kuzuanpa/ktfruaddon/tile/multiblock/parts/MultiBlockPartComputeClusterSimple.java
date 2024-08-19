/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */


package cn.kuzuanpa.ktfruaddon.tile.multiblock.parts;

import cn.kuzuanpa.ktfruaddon.i18n.texts.kMessages;
import cn.kuzuanpa.ktfruaddon.item.items.random.itemComputer;
import gregapi.block.multitileentity.IMultiTileEntity;
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
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;

import java.util.List;

import static gregapi.data.CS.*;
public class MultiBlockPartComputeClusterSimple extends TileEntityBase09FacingSingle implements IMultiTileEntity.IMTE_SyncDataByteArray, IMultiTileEntity.IMTE_AddToolTips, IMultiBlockPart, IComputeNode {

    protected IIconContainer[][] mTextures;
    public boolean isRunning,isActive;
    public byte mState;
    public long ComputePower;
    public byte[] mDisplaySlot = {0,0};
    public MultiBlockPartComputeClusterSimple(){}
    public void updateComputePower() {
        ComputePower =0;
        for (ItemStack stack:getInventory()) if (stack != null) {
            long toAdd = itemComputer.getComputePowerFromID(stack.getItemDamage());
            if (toAdd < 150) ComputePower += toAdd;
        }
    }
    public long getComputePower(){return ComputePower;}
    public void updateState(){
        isRunning=mState==1;
        isActive =mState==2;
    }
    public void active(){
        isRunning=true;
        isActive=true;
        mState=2;
    }
    public void run(){
        isRunning=true;
        mState=1;
    }
    public void stop(){
        isActive=false;
        isRunning=false;
        mState=0;
    }
    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (isServerSide()&&!isActive&&aSide!=SIDE_BOTTOM&&aSide!=SIDE_TOP){
            byte tSlot = (byte)(aHitY >0.5?0:1);
            ItemStack aStack = aPlayer.getCurrentEquippedItem();
            if (ST.valid(aStack) && aStack.getUnlocalizedName().contains("ktfru.item.it.computer")&&slot(tSlot)==null)
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
                    aChatReturn.add(LH.get(kMessages.SLOT)+i+": " + slot(i).getDisplayName());
                }
                if (!saidSomething) aChatReturn.add(LH.get(kMessages.COMPUTE_CLUSTER_0));
                updateComputePower();
                aChatReturn.add(LH.get(kMessages.COMPUTE_CLUSTER_1)+(isRunning?LH.get(kMessages.NORMAL):LH.get(kMessages.COMPUTE_CLUSTER_3)));
                aChatReturn.add(LH.get(kMessages.COMPUTE_CLUSTER_2)+getComputePower());
            }
        }
        if (getFacingTool() != null && aTool.equals(getFacingTool())) {byte aTargetSide = UT.Code.getSideWrenching(aSide, aHitX, aHitY, aHitZ); if (getValidSides()[aTargetSide]) {byte oFacing = mFacing; mFacing = aTargetSide; updateClientData(); causeBlockUpdate(); onFacingChange(oFacing); return 10000;}}
        return 0;
    }

    // Inventory Stuff
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[2];}
    @Override public boolean canDrop(int aInventorySlot) {return T;}
    private static final int[] ACCESSIBLE_SLOTS = new int[] {0, 1};

    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}

    public void onTick2(long aTimer, boolean aIsServerSide) {
        if (aIsServerSide) {
            for (int i=0;i<2;i++)if (slot(i)!=null)mDisplaySlot[i]=1;
                                 else mDisplaySlot[i]=0;
            if(aTimer%20==0&&getTarget(true)==null)stop();
            if(aTimer%10==0)updateComputePower();
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
        updateComputePower();
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
    public static IIconContainer
            sTextureCommon= new Textures.BlockIcons.CustomIcon("machines/multiblockparts/computeclustersimple/background"),
            sTextureSides = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/computeclustersimple/normal/sides"),
            sTextureFront = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/computeclustersimple/normal/front"),
            sTextureNode  = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/computeclustersimple/normal/nodes"),
            sRunningSide = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/computeclustersimple/running/sides"),
            sRunningFront = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/computeclustersimple/running/front"),
            sRunningNode  = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/computeclustersimple/running/nodes"),
            sActiveFront = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/computeclustersimple/active/front"),
            sActiveNode  = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/computeclustersimple/active/nodes");
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if(isActive) switch(aRenderPass) {
            case 0: return !aShouldSideBeRendered[aSide]?null: aSide==SIDE_TOP||aSide==SIDE_BOTTOM?BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa)): aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa),BlockTextureDefault.get(sTextureFront),BlockTextureDefault.get(sActiveFront,true)):BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa),BlockTextureDefault.get(sTextureSides),BlockTextureDefault.get(sRunningSide,true));
            case 1: return !aShouldSideBeRendered[aSide]?null: mDisplaySlot[1] ==1&&aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureNode),BlockTextureDefault.get(sActiveNode,true)):null;
            case 2: return !aShouldSideBeRendered[aSide]?null: mDisplaySlot[0] ==1&&aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureNode),BlockTextureDefault.get(sActiveNode,true)):null;
        }
        if(!isActive&&isRunning) switch(aRenderPass) {
            case 0: return !aShouldSideBeRendered[aSide]?null: aSide==SIDE_TOP||aSide==SIDE_BOTTOM?BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa)): aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa),BlockTextureDefault.get(sTextureFront),BlockTextureDefault.get(sRunningFront,true)):BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa),BlockTextureDefault.get(sTextureSides),BlockTextureDefault.get(sRunningSide,true));
            case 1: return !aShouldSideBeRendered[aSide]?null: mDisplaySlot[1] ==1&&aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureNode),BlockTextureDefault.get(sRunningNode,true)):null;
            case 2: return !aShouldSideBeRendered[aSide]?null: mDisplaySlot[0] ==1&&aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureNode),BlockTextureDefault.get(sRunningNode,true)):null;
        }
        switch(aRenderPass) {
            case 0: return !aShouldSideBeRendered[aSide]?null: aSide==SIDE_TOP||aSide==SIDE_BOTTOM?BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa)): aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa),BlockTextureDefault.get(sTextureFront)):BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa),BlockTextureDefault.get(sTextureSides));
            case 1: return !aShouldSideBeRendered[aSide]?null: mDisplaySlot[1] ==1&&aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureNode)):null;
            case 2: return !aShouldSideBeRendered[aSide]?null: mDisplaySlot[0] ==1&&aSide==mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureNode)):null;
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
    }
    @Override
    public boolean setBlockBounds2(Block aBlock, int aRenderPass, boolean[] aShouldSideBeRendered) {
        switch(aRenderPass) {
            case  0: return box(aBlock, PX_P[ 0], PX_P[ 0], PX_P[ 0], PX_P[16], PX_P[ 16], PX_P[ 16]);
            case  1: return box(aBlock, PX_P[ 0]-0.0001F, PX_P[ 2], PX_P[ 0]-0.0001F, PX_P[16]+0.0001F, PX_P[ 7], PX_P[16]+0.0001F);
            case  2: return box(aBlock, PX_P[ 0]-0.0001F, PX_P[ 9], PX_P[ 0]-0.0001F, PX_P[16]+0.0001F, PX_P[14], PX_P[16]+0.0001F);
        }
        return F;
    }

    @Override public boolean[] getValidSides() {return SIDES_HORIZONTAL;}
    @Override public byte getDefaultSide() {return SIDE_FRONT;}

    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        return getClientDataPacketByteArray(aSendAll,(byte)UT.Code.getR(mRGBa), (byte)UT.Code.getG(mRGBa), (byte)UT.Code.getB(mRGBa), getDirectionData(),mDisplaySlot[0], mDisplaySlot[1],mState);
    }

    @Override
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler) {
        mRGBa = UT.Code.getRGBInt(new short[] {UT.Code.unsignB(aData[0]), UT.Code.unsignB(aData[1]), UT.Code.unsignB(aData[2])});
        setDirectionData(aData[3]);
        for (short i=0;i<2;i++)mDisplaySlot[i]=aData[i+4];
        mState=aData[6];
        updateState();
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
        return "ktfru.multitileentity.computenode.simple";
    }
    @Override
    public int getLightOpacity(){
        return mDesign==7?0:255;
    }
}
