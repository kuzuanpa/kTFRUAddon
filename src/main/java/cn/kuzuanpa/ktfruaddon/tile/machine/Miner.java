/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.machine;

import cn.kuzuanpa.ktfruaddon.client.gui.ContainerClientMiner;
import cn.kuzuanpa.ktfruaddon.client.gui.ContainerCommonMiner;
import cn.kuzuanpa.ktfruaddon.code.NetServerHandlerFake;
import cn.kuzuanpa.ktfruaddon.i18n.texts.kTooltips;
import cn.kuzuanpa.ktfruaddon.tile.util.kTileNBT;
import com.mojang.authlib.GameProfile;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.item.multiitem.tools.IToolStats;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static gregapi.data.CS.*;

public class Miner extends TileEntityBase09FacingSingle implements ITileEntityEnergy, IFluidHandler, IMultiTileEntity.IMTE_SyncDataByteArray {
    public TagData mEnergyTypeAccepted = TD.Energy.RF;
    public long mEnergyStored=0, mInput=0,mInputMin=0,mInputMax=0;
    public int mRange=0, mSpeed=2,mMaxHardness=10, BreakingPosX=Integer.MIN_VALUE, BreakingPosY=Integer.MIN_VALUE, BreakingPosZ=Integer.MIN_VALUE, BreakingRemain=0;
    public FakePlayer fakePlayer = null;
    public final static byte STATE_IDLE=0,STATE_FINDING=1,STATE_BREAKING=2, STATE_FINISHED =3, STATE_NOT_INIT=4;
    public byte mState=STATE_NOT_INIT;
    public static IIconContainer mTextureMaterial, mTextureFront, mTextureFrontActive, mTextureFrontFinished, mTextureAutoInput;
    public float powerOverclock=1.0F;
    static {
        mTextureMaterial      = new Textures.BlockIcons.CustomIcon("machines/miner/colored");
        mTextureFront         = new Textures.BlockIcons.CustomIcon("machines/miner/front");
        mTextureFrontActive   = new Textures.BlockIcons.CustomIcon("machines/miner/front_active");
        mTextureFrontFinished = new Textures.BlockIcons.CustomIcon("machines/miner/front_finished");
        mTextureAutoInput     = new Textures.BlockIcons.CustomIcon("machines/miner/side_in");
    }
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);

        if (aNBT.hasKey(NBT_INPUT)) {mInput = aNBT.getLong(NBT_INPUT); mInputMin = mInput / 2; mInputMax = mInput * 2;}
        if (aNBT.hasKey(NBT_INPUT_MIN)) {mInputMin = aNBT.getLong(NBT_INPUT_MIN);}
        if (aNBT.hasKey(NBT_INPUT_MAX)) {mInputMax = aNBT.getLong(NBT_INPUT_MAX);}
        if (aNBT.hasKey(NBT_ENERGY_ACCEPTED)) mEnergyTypeAccepted = TagData.createTagData(aNBT.getString(NBT_ENERGY_ACCEPTED));
        if (aNBT.hasKey(kTileNBT.MAX_RANGE)) {mRange = aNBT.getInteger(kTileNBT.MAX_RANGE);}
        if (aNBT.hasKey(kTileNBT.MINER_SPEED)) {mSpeed = aNBT.getInteger(kTileNBT.MINER_SPEED);}
        if (aNBT.hasKey(kTileNBT.MINER_MAX_HARDNESS)) {mMaxHardness = aNBT.getInteger(kTileNBT.MINER_MAX_HARDNESS);}

        if (aNBT.hasKey(NBT_ENERGY)) {mEnergyStored = aNBT.getLong(NBT_ENERGY);}
        if (aNBT.hasKey(NBT_PROGRESS+".x")) {BreakingPosX = aNBT.getInteger(NBT_PROGRESS+".x");}
        if (aNBT.hasKey(NBT_PROGRESS+".y")) {BreakingPosY = aNBT.getInteger(NBT_PROGRESS+".y");}
        if (aNBT.hasKey(NBT_PROGRESS+".z")) {BreakingPosZ = aNBT.getInteger(NBT_PROGRESS+".z");}
    }
    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT,NBT_ENERGY,mEnergyStored);
        UT.NBT.setNumber(aNBT,NBT_PROGRESS+".x",BreakingPosX);
        UT.NBT.setNumber(aNBT,NBT_PROGRESS+".y",BreakingPosY);
        UT.NBT.setNumber(aNBT,NBT_PROGRESS+".z",BreakingPosZ);
    }
    public void init(){
        if(BreakingPosX == Integer.MIN_VALUE)BreakingPosX=-mRange;
        if(BreakingPosZ == Integer.MIN_VALUE)BreakingPosZ=-mRange;
        if(BreakingPosY == Integer.MIN_VALUE)BreakingPosY=-1;
        BreakingRemain=0;
        if(isServerSide() && getWorldObj()!=null && fakePlayer==null){
            fakePlayer = new FakePlayer((WorldServer) getWorldObj(),new GameProfile(new UUID(0,0),"ktfruaddon Miner Fake Player"));
            fakePlayer.playerNetServerHandler = new NetServerHandlerFake(MinecraftServer.getServer(),fakePlayer);
        }
        mState=STATE_IDLE;
    }

    public void nextTarget(){
        if(yCoord+BreakingPosY < 1) {
            mState=STATE_FINISHED;
            updateClientData();
        }
        BreakingPosX++;
        if(BreakingPosX>=mRange){
            BreakingPosX=-mRange;
            BreakingPosZ++;
        }
        if(BreakingPosZ>=mRange){
            BreakingPosX=-mRange;
            BreakingPosZ=-mRange;
            BreakingPosY-=1;
        }
    }

    public void absorbItemAroundPos(int x,int y,int z){
        AxisAlignedBB bound = AxisAlignedBB.getBoundingBox(x-1.5,y-1.5,z-1.5,x+1.5,y+1.5,z+1.5);
        worldObj.getEntitiesWithinAABB(EntityItem.class, bound).forEach(itemEntity -> {
            if(TryInsertIntoNextFreeSlot(((EntityItem) itemEntity).getEntityItem())) ((EntityItem) itemEntity).setDead();
        });
    }

    public boolean TryInsertIntoNextFreeSlot(ItemStack stack){
        for (int i = 1; i < invsize(); i++) if(addStackToSlot(i, stack)) return true;
        return false;
    }
    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if(!aIsServerSide || mState==STATE_FINISHED)return;

        if(mState == STATE_NOT_INIT) init();
        if((mEnergyStored < mInputMin || mCovers!=null&&mCovers.mStopped || fakePlayer==null || !slotHas(0))) {
            if(mState!=STATE_IDLE){mState=STATE_IDLE;updateClientData();}
        } else if(mState==STATE_IDLE) {mState=STATE_FINDING;updateClientData();}
        if(mEnergyStored<0)mEnergyStored=0;

        if(mState==STATE_IDLE)return;
        try {
            powerOverclock = Math.min(mEnergyStored,mInputMax) * 1F / mInputMin;
            mEnergyStored-=Math.min(mEnergyStored,mInputMax);
            //Sync Item: Tile -> Player
            if (fakePlayer.getCurrentEquippedItem() != slot(0)) fakePlayer.inventory.setInventorySlotContents(fakePlayer.inventory.currentItem, slot(0));
            if (mState == STATE_FINDING) {
                for (int i = 0; i < mSpeed; i++) {
                    Block block = worldObj.getBlock(xCoord + BreakingPosX, yCoord + BreakingPosY, zCoord + BreakingPosZ);
                    byte metaData = (byte) worldObj.getBlockMetadata(xCoord + BreakingPosX, yCoord + BreakingPosY, zCoord + BreakingPosZ);
                    float hardness = block == null ? -1 : block.getBlockHardness(worldObj, xCoord + BreakingPosX, yCoord + BreakingPosY, zCoord + BreakingPosZ);
                    //When block cannot harvest
                    if (block == null || block.equals(Blocks.air) || hardness < 0 || hardness > mMaxHardness || !fakePlayer.canHarvestBlock(block)) {
                        nextTarget();
                        continue;
                    }

                    float speed = !slotHas(0) ? 1 : slot(0).getItem() instanceof IToolStats ? /*GT Impl*/((IToolStats) slot(0).getItem()).getMiningSpeed(block, metaData) : /*Default Impl*/slot(0).getItem().getDigSpeed(slot(0), block, metaData);
                    if (speed <= 0.1F) {
                        nextTarget();
                        continue;
                    }
                    mState = STATE_BREAKING;
                    BreakingRemain = (int) (hardness * (4096F/mSpeed) * (1 / speed));
                }
            }
            if (mState == STATE_BREAKING) {
                BreakingRemain -= (int) Math.floor(mSpeed * powerOverclock);

                if (BreakingRemain > 0) return;
                //Break Block
                fakePlayer.theItemInWorldManager.tryHarvestBlock(xCoord + BreakingPosX, yCoord + BreakingPosY, zCoord + BreakingPosZ);
                absorbItemAroundPos(xCoord + BreakingPosX, yCoord + BreakingPosY, zCoord + BreakingPosZ);
                BreakingRemain = 0;
                mState = STATE_FINDING;
                nextTarget();
            }
        }catch (Exception e){
            mState = STATE_FINDING;
            nextTarget();
        }
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        LH.addEnergyToolTips(this, aList, mEnergyTypeAccepted, null, null, null);
        aList.add(LH.Chat.CYAN    + LH.get(kTooltips.MINER));
        aList.add(LH.Chat.DGRAY    + LH.get(LH.TOOL_TO_RESET_SOFT_HAMMER));
        aList.add(LH.Chat.DGRAY    + LH.get(LH.TOOL_TO_DETAIL_MAGNIFYINGGLASS));
    }

    static {
        LH.add(kTooltips.MINER,"");
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        return openGUI(aPlayer);
    }

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        aSize = Math.abs(aSize);
        if (aSize > getEnergySizeInputMax(aEnergyType, aSide) && !TD.Energy.RF.equals(mEnergyTypeAccepted)) {
            if (aDoInject) overcharge(aSize, aEnergyType);
            return aAmount;
        }
        if(mState == STATE_FINISHED)return 0;
        if (aEnergyType == mEnergyTypeAccepted) {
            long tInput = Math.min(mInputMax - mEnergyStored, aSize * aAmount), tConsumed = Math.min(aAmount, (tInput/aSize) + (tInput%aSize!=0?1:0));
            if (aDoInject) mEnergyStored += tConsumed * aSize;
            return tConsumed;
        }
        return 0;
    }

    @Override public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return aShouldSideBeRendered[aSide] ? BlockTextureMulti.get(BlockTextureDefault.get(mTextureMaterial, mRGBa), aSide==mFacing? BlockTextureDefault.get(mState==STATE_IDLE || mState == STATE_NOT_INIT? mTextureFront: mState==STATE_FINISHED? mTextureFrontFinished : mTextureFrontActive): aSide==SIDE_TOP ? BlockTextureDefault.get(mTextureAutoInput) : null) : null;
    }

    @Override
    public byte getDefaultSide() {return SIDE_SOUTH;}
    public boolean[] getValidSides() {return SIDES_HORIZONTAL;}
    @Override public boolean isEnergyType                   (TagData aEnergyType, byte aSide, boolean aEmitting) {return  aEnergyType == mEnergyTypeAccepted;}
    @Override public long getEnergySizeInputMin             (TagData aEnergyType, byte aSide) {return mInputMin;}
    @Override public long getEnergySizeInputRecommended     (TagData aEnergyType, byte aSide) {return mInput;}
    @Override public long getEnergySizeInputMax             (TagData aEnergyType, byte aSide) {return mInputMax;}

    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return mEnergyTypeAccepted.AS_LIST;}

    @Override public String getTileEntityName() {return "ktfru.multitileentity.machine.miner";}
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[10];}

    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return aSide==SIDE_TOP?new int[] {0} : new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};}

    @Override
    public boolean canDrop(int aSlot) {
        return true;
    }
    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        return getClientDataPacketByteArray(aSendAll, (byte)UT.Code.getR(mRGBa), (byte)UT.Code.getG(mRGBa), (byte)UT.Code.getB(mRGBa),getDirectionData(),getDirectionData(),mState);
    }
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler){
        super.receiveDataByteArray(aData,aNetworkHandler);
        mState=aData[5];
        return true;
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(aTool.equals(TOOL_softhammer)){
            mState=STATE_NOT_INIT;
            BreakingPosX=Integer.MIN_VALUE;
            BreakingPosY=Integer.MIN_VALUE;
            BreakingPosZ=Integer.MIN_VALUE;
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerClientMiner(aPlayer.inventory, this, aGUIID, "");
    }
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerCommonMiner(aPlayer.inventory, this, aGUIID);
    }
}
