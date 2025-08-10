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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.miner;

import cn.kuzuanpa.ktfruaddon.api.code.StateMgr;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.IMeterDetectable;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.kTileNBT;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import cn.kuzuanpa.ktfruaddon.item.items.random.itemDevice;
import gregapi.block.multitileentity.IWailaTile;
import gregapi.code.ArrayListNoNulls;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.gui.ContainerClientDefault;
import gregapi.gui.ContainerCommonDefault;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.multiblocks.IMultiBlockEnergy;
import gregapi.tileentity.multiblocks.IMultiBlockInventory;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import gregapi.util.OM;
import gregapi.util.ST;
import gregapi.util.UT;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import zmaster587.advancedRocketry.api.Configuration;
import zmaster587.advancedRocketry.util.AsteroidSmall;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static gregapi.data.CS.*;

public class AsteroidMiner extends TileEntityBase10MultiBlockBase implements ITileEntityEnergy, IMultiBlockInventory, IMultiBlockEnergy, IWailaTile {
    protected static final List<UUID> minedAsteroids = new ArrayList<>();
    public static final byte STATE_WAIT =0, STATE_WAIT_ROCKET =1, STATE_MINING=2, STATE_OUTPUTTING=3, EVENT_ROCKET_LAUNCH=1, EVENT_ROCKET_ARRIVE=2, EVENT_INV_EMPTY=3;
    public StateMgr mState = new StateMgr();
    public byte recentEvent = 0;
    public boolean mStopped = false, isEnergyEnough=true, clientIsSlotHas=false;
    public long mEnergy = 0, mInput = 256, mInputMax = 1024;
    public int interval = 400, progress = 0, /**<0: miner is going to asteroid, >0: miner is backing**/distanceMiner=0, miningTime = 20, clientRocketSendTimer = 0;
    public List<IMeterDetectable.MeterData> receivedEnergy = new ArrayList<>(), receivedEnergyLast = new ArrayList<>();
    public TagData mEnergyType = TD.Energy.EU;
    public ItemStack sendedRocket = null;
    public List<ItemStack> harvest = new ArrayList<>();
    public AsteroidSmall findedAsteroid;
    public UUID findedAsteroidUUID;

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if(aNBT.hasKey(NBT_ENERGY))mEnergy = aNBT.getLong(NBT_ENERGY);
        if(aNBT.hasKey(NBT_INPUT)) mInput = aNBT.getLong(NBT_INPUT);
        if(aNBT.hasKey(NBT_INPUT_MAX)) mInputMax = aNBT.getLong(NBT_INPUT_MAX);
        if(aNBT.hasKey(kTileNBT.INTERVAL)) interval = aNBT.getInteger(kTileNBT.INTERVAL);

        if(aNBT.hasKey(NBT_STATE)) mState.set(aNBT.getByte(NBT_STATE));
        if(aNBT.hasKey("findedAsteroid")) findedAsteroid = Configuration.asteroidTypes.get(aNBT.getString("findedAsteroid"));
        if(aNBT.hasKey("findedAsteroidUUID")) findedAsteroidUUID = UUID.fromString(aNBT.getString("findedAsteroidUUID"));
        if(aNBT.hasKey("progress")) progress = aNBT.getInteger("progress");
        if(aNBT.hasKey("distanceMiner")) distanceMiner = aNBT.getInteger("distanceMiner");
        if(aNBT.hasKey("sendedRocket")) sendedRocket = ST.load(aNBT,"sendedRocket");
        if(aNBT.hasKey("harvests")){
            harvest.clear();
            for (int i = 0; i < aNBT.getInteger("harvests"); i++) harvest.add(ST.load(aNBT,"harvest."+i));
        }
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergy);

        aNBT.setByte(NBT_STATE, mState.get());
        if(findedAsteroid != null)aNBT.setString("findedAsteroid", findedAsteroid.ID);
        if(findedAsteroidUUID != null)aNBT.setString("findedAsteroidUUID", findedAsteroidUUID.toString());
        aNBT.setInteger("progress", progress);
        aNBT.setInteger("distanceMiner", distanceMiner);
        if(sendedRocket != null)ST.save(aNBT, "sendedRocket",sendedRocket);
        if(!harvest.isEmpty()) {
            aNBT.setInteger("harvests", harvest.size());
            for (int i = 0; i < harvest.size(); i++) ST.save(aNBT, "harvest." + i, harvest.get(i));
        }
    }

    @Override
    public void onMagnifyingGlass2(List<String> aChatReturn) {
        aChatReturn.add("Current Target: "+(findedAsteroid==null? null : findedAsteroid.ID));
        aChatReturn.add("State: "+( mState.is(STATE_WAIT) || mState.is(STATE_WAIT_ROCKET)? "Waiting":mState.is(STATE_MINING)?(distanceMiner<miningTime?"Sending":distanceMiner<0?"Mining":"Backing"):"Outputting"));
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if(!aIsServerSide)return;

        receivedEnergyLast=receivedEnergy;
        receivedEnergy=new ArrayList<>();

        if(!mStructureOkay)return;
        if(mEnergy < mInput){
            isEnergyEnough=false;
            mState.markChanged();
            return;
        }
        if(!isEnergyEnough) mState.markChanged();
        isEnergyEnough=true;
        mEnergy -= mInput;

        if(findedAsteroid != null && mState.is(STATE_WAIT))mState.set(STATE_WAIT_ROCKET);

        if(mState.is(STATE_WAIT) || mState.is(STATE_WAIT_ROCKET)){
            if (OM.is(OD_USB_STICKS[0],slot(0)))tryReadAsteroidFromUSB(slot(0), null);

            if(findedAsteroid == null || !itemDevice.isDeviceAsteroidMinerRocket(slot(0)) || findedAsteroid.getMinLevel() > itemDevice.getAsteroidMinerRocketLevel(slot(0))) return;

            minedAsteroids.add(findedAsteroidUUID);
            if(minedAsteroids.size() > 256)minedAsteroids.remove(0);
            sendedRocket = slot(0);
            slotKill(0);
            mState.set(STATE_MINING);
            recentEvent = EVENT_ROCKET_LAUNCH;
            return;
        }

        if(mState.is(STATE_OUTPUTTING)){
            if(slotHas(0))return;

            if(sendedRocket != null){
                setInventorySlotContents(0, itemDevice.getDeprecatedAsteroidMinerRocket(sendedRocket));
                sendedRocket = null;
                return;
            }
            if(harvest.isEmpty()){
                mState.set(STATE_WAIT);
                return;
            }
            ItemStack stack = harvest.get(harvest.size() - 1);
            if(stack.stackSize > 64){
                setInventorySlotContents(0, new ItemStack(stack.getItem(), 64, stack.getItemDamage()));
                stack.stackSize-=64;
            }
            else {
                setInventorySlotContents(0, new ItemStack(stack.getItem(), stack.stackSize, stack.getItemDamage()));
                harvest.remove(harvest.size() - 1);
            }
            if(harvest.isEmpty())recentEvent = EVENT_INV_EMPTY;
            return;
        }

        progress++;
        if(progress < interval)return;
        distanceMiner += itemDevice.getAsteroidMinerRocketSpeed(sendedRocket);

        if(distanceMiner > 0 && harvest.isEmpty()) harvest = findedAsteroid.getHarvest(worldObj.getSeed()+aTimer, 0.5f).stream().map(entry-> entry.stack).collect(Collectors.toList());

        if(distanceMiner > findedAsteroid.distance){
            mState.set(STATE_OUTPUTTING);
            recentEvent = EVENT_ROCKET_ARRIVE;
            findedAsteroid = null;
            findedAsteroidUUID = null;
        }

        progress =0;
    }

    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide())return true;

        if(!mStructureOkay)aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED+LH.get(I18nHandler.STRUCTURE_ERR)));

        ItemStack equippedItem=aPlayer.getCurrentEquippedItem();
        if (OM.is(OD_USB_STICKS[0],equippedItem)) tryReadAsteroidFromUSB(equippedItem, aPlayer);
        if (equippedItem!=null && equippedItem.getItem() instanceof ItemProjector) {
            structure.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }
        openGUI(aPlayer, aSide);
        return true;
    }

    public void tryReadAsteroidFromUSB(ItemStack stack, EntityPlayer aPlayer){
        NBTTagCompound aNBT = stack.getTagCompound();
        if(aNBT == null || !aNBT.hasKey("findedAsteroid"))return;
        findedAsteroid = Configuration.asteroidTypes.get(aNBT.getString("findedAsteroid"));
        findedAsteroidUUID = UUID.fromString(aNBT.getString("findedAsteroidUUID"));
        if(findedAsteroid == null){
            if(aPlayer!=null) aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED+LH.get(I18nHandler.ERROR)));
            return;
        }
        if(minedAsteroids.contains(findedAsteroidUUID)) {
            if(aPlayer!=null) aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED + LH.get(I18nHandler.ERROR) + " Asteroid Already Mined!"));
            findedAsteroid = null;
            findedAsteroidUUID = null;
            return;
        }
        aNBT.removeTag("findedAsteroid");
        aNBT.removeTag("findedAsteroidUUID");
        distanceMiner = -findedAsteroid.distance - miningTime;
        if(aPlayer!=null) aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN+LH.get(I18nHandler.DATA_READ_FROM_USB)));
    }

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if (mStopped) return 0;
        aSize = Math.abs(aSize);
        if (aEnergyType == mEnergyType) {
            aSize = Math.abs(aSize);
            if (aSize > getEnergySizeInputMax(aEnergyType, aSide)) {
                if (aDoInject) overcharge(aSize, aEnergyType);
                return aAmount;
            }
            long canReceiveAmount = (long) Math.ceil((mInputMax -mEnergy)*1F/aSize);
            long receiveAmount = Math.min(canReceiveAmount,aAmount);
            if (aDoInject) {
                mEnergy += receiveAmount * aSize;
                this.receivedEnergy.add(new IMeterDetectable.MeterData(aEnergyType, aSize, receiveAmount));
            }
        }
        return 0;
    }

    @Override public boolean isEnergyType (TagData aEnergyType, byte aSide, boolean aEmitting) {return !aEmitting && aEnergyType == mEnergyType;}

    @Override public long getEnergySizeInputMin(TagData aEnergyType, byte aSide) {return mInput;}
    @Override public long getEnergySizeInputRecommended (TagData aEnergyType, byte aSide) {return mInput /2 + mInputMax /2;}
    @Override public long getEnergySizeInputMax(TagData aEnergyType, byte aSide) {return mInputMax;}

    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return new ArrayListNoNulls<>(F, mEnergyType);}

    //Structure
    ChunkCoordinates lastFailedPos=null;
    static final IStringBaseStructure structure = new LayerStructure(StructureContext.Axis.Y).layerRule("ABBBCBBD")
            .fixedLayer('A',
                    "  XX ",
                    " XAAX",
                    "XXAAX",
                    "  XX "
            ).fixedLayer('B',
                    "     ",
                    "XX   ",
                    "XX   ",
                    "     "
            ).fixedLayer('C',
                    " XXX ",
                    "XX   ",
                    "XX   ",
                    " XXX "
            ).fixedLayer('D',
                    "     ",
                    " X   ",
                    " X   ",
                    "     "
            )
            .where('X', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18002, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID_ENERGY_IN)))
            .where('A', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18006, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID_ENERGY_IN)))
            .setOffset(-1,0,0);
    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = structure.checkStructure(new StructureContext(this, (aPlayer != null || aInventory != null)? StructureContext.StringBaseMode.SET: StructureContext.StringBaseMode.CHECK, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        return lastFailedPos==null;
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return true;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        ChunkCoordinates startPos = utils.getRealCoord(mFacing, xCoord, yCoord, zCoord, 0, 1, 1);
        ChunkCoordinates endPos = utils.getRealCoord(mFacing, xCoord, yCoord, zCoord, 1, 50, 5);
        return AxisAlignedBB.getBoundingBox(startPos.posX, startPos.posY,startPos.posZ, endPos.posX,endPos.posY,endPos.posZ);
    }

    @Override
    public boolean[] getValidSides() {
        return SIDES_HORIZONTAL;
    }

    @Override
    public byte getDefaultSide() {
        return SIDE_FRONT;
    }

    //inventory
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[1];}
    private static final int[] ACCESSIBLE_SLOTS = new int[] {0};
    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}
    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {return true;}
    @Override public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {return true;}


    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {return new ContainerClientDefault(aPlayer.inventory, this, aGUIID);}
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {return new ContainerCommonDefault(aPlayer.inventory, this, aGUIID);}

    @Override
    public boolean onTickCheck(long aTimer) {
        return super.onTickCheck(aTimer) || mState.isChangedAndClear() || recentEvent!=0;
    }

    @Override
    public NBTTagCompound getWailaNBT(TileEntity te, NBTTagCompound aNBT) {
        IWailaTile.super.getWailaNBT(te, aNBT);
        return aNBT;
    }

    @Override
    public List<String> getWailaBody(List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        IWailaTile.super.getWailaBody(currentTip, accessor, config);
        return currentTip;
    }

    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        byte isSlotRocket = (byte) (slotHas(0)?1:0);
        IPacket result = aSendAll ? getClientDataPacketByteArray(aSendAll, getDirectionData(), getClientState(), recentEvent, isSlotRocket, (byte)UT.Code.getR(mRGBa), (byte)UT.Code.getG(mRGBa), (byte)UT.Code.getB(mRGBa)) : getClientDataPacketByteArray(aSendAll, getDirectionData(), getClientState(), recentEvent, isSlotRocket);
        recentEvent =0;
        return result;
    }

    @Override
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler) {
        if(aData.length == 7)mRGBa = UT.Code.getRGBInt(new short[] {UT.Code.unsignB(aData[4]), UT.Code.unsignB(aData[5]), UT.Code.unsignB(aData[6])});
        setDirectionData(aData[0]);
        mState.set(aData[1]);
        switch (aData[2]){
            case EVENT_ROCKET_LAUNCH: clientRocketSendTimer = 1; break;
            case EVENT_ROCKET_ARRIVE: clientRocketSendTimer = -1; break;
            case EVENT_INV_EMPTY: clientRocketSendTimer = 0; break;
        }
        clientIsSlotHas=aData[3]==1;
        return T;
    }

    // Icons
    public final static IIconContainer
            sTextureSides         = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/asteroidMiner/base"),
            sOverlayBase          = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/asteroidMiner/front/base"),
            sOverlayWaitEmpty     = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/asteroidMiner/front/wait/empty"),
            sOverlayWaitRocket    = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/asteroidMiner/front/wait/rocket"),
            sOverlayMining        = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/asteroidMiner/front/mining"),
            sOverlayOutput        = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/asteroidMiner/front/output"),
            sOverlayErrStructure  = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/err/structure"),
            sOverlayErrEnergy     = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/err/energy");

    public byte getClientState(){
        if(!isEnergyEnough)return -1;
        return mState.get();
    }

    public IIconContainer getFrontOverlay(){
        if(!mStructureOkay) return sOverlayErrStructure;
        switch (mState.get()){
            case -1: return sOverlayErrEnergy;
            case 1:  return sOverlayWaitRocket;
            case 2:  return sOverlayMining;
            case 3:  return sOverlayOutput;

            case 0:
            default: return sOverlayWaitEmpty ;
        }
    }
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (!aShouldSideBeRendered[aSide]) return null;
        if(aSide==mFacing) return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides, mRGBa), BlockTextureDefault.get(sOverlayBase), BlockTextureDefault.get(getFrontOverlay(), true));
        return BlockTextureDefault.get(sTextureSides, mRGBa);
    }
    @Override public String getTileEntityName() {return "ktfru.multitileentity.multiblock.asteroid.miner";}
}
