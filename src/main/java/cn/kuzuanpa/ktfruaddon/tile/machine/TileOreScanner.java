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

package cn.kuzuanpa.ktfruaddon.tile.machine;

import cn.kuzuanpa.ktfruaddon.api.client.fx.FxRenderBlockOutline;
import cn.kuzuanpa.ktfruaddon.api.code.IOreScanner;
import cn.kuzuanpa.ktfruaddon.api.code.OreScanner;
import cn.kuzuanpa.ktfruaddon.api.code.codeUtil;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.util.kTileNBT;
import codechicken.lib.vec.BlockCoord;
import cpw.mods.fml.common.Optional;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.data.TD;
import gregapi.gui.ContainerClientDefault;
import gregapi.gui.ContainerCommonDefault;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.old.Textures;
import gregapi.oredict.OreDictMaterial;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.util.OM;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import vazkii.botania.api.mana.IManaReceiver;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static gregapi.data.CS.*;
import static cn.kuzuanpa.ktfruaddon.api.code.OreScanner.*;

@Optional.Interface(iface = "vazkii.botania.api.mana.IManaReceiver", modid = "Botania")
public class TileOreScanner extends TileEntityBase09FacingSingle implements ITileEntityEnergy, IMultiTileEntity.IMTE_SyncDataByteArray, IOreScanner, IManaReceiver {
    public static IIconContainer mTextureMaterial, mTextureFront, mTextureFrontActive, mTextureFrontFinished;
    public static final byte STATE_IDLE = 0, STATE_RUNNING = 1, STATE_FINISHED = 2;
    public byte mState = STATE_IDLE;
    public short pipeID = 26141, range = 1 ,usedPipes = 0, mana = 0, interval = 10, speed=1;
    public long mEnergyStored =0, mCost=64;
    public TagData mEnergyTypeAccepted = TD.Energy.EU;
    public OreScanner mScanner = null;
    public List<Short> validOreIDs = new ArrayList<>();
    static {
        mTextureMaterial      = new Textures.BlockIcons.CustomIcon("machines/oreScanner/colored");
        mTextureFront         = new Textures.BlockIcons.CustomIcon("machines/oreScanner/front");
        mTextureFrontActive   = new Textures.BlockIcons.CustomIcon("machines/oreScanner/front_active");
        mTextureFrontFinished = new Textures.BlockIcons.CustomIcon("machines/oreScanner/front_finished");
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if(aNBT.hasKey("usedPipes"))usedPipes = aNBT.getShort("usedPipes");
        if(aNBT.hasKey("mana"))mana = aNBT.getShort("mana");
        if(aNBT.hasKey(NBT_ENERGY))mEnergyStored = aNBT.getLong(NBT_ENERGY);
        if(aNBT.hasKey("scanner"))mScanner = OreScanner.deserialize(aNBT.getByteArray("scanner"));
        if(aNBT.hasKey(NBT_DESIGN))pipeID = aNBT.getShort(NBT_DESIGN);
        if(aNBT.hasKey(NBT_INPUT))mCost = aNBT.getShort(NBT_INPUT);
        if(aNBT.hasKey(NBT_ENERGY_ACCEPTED))mEnergyTypeAccepted= TagData.createTagData(aNBT.getString(NBT_ENERGY_ACCEPTED));
        if(aNBT.hasKey(kTileNBT.MAX_RANGE))range = aNBT.getShort(kTileNBT.MAX_RANGE);
        if(aNBT.hasKey(kTileNBT.INTERVAL))interval = aNBT.getShort(kTileNBT.INTERVAL);
        if(aNBT.hasKey(kTileNBT.SPEED))speed = aNBT.getShort(kTileNBT.SPEED);
        if(mScanner!=null)mScanner.setScanner(this);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        aNBT.setShort("usedPipes", usedPipes);
        aNBT.setShort("mana", mana);
        aNBT.setLong(NBT_ENERGY, mEnergyStored);
        if(mScanner!=null)aNBT.setByteArray("scanner", OreScanner.serialize(mScanner));
    }

    @Override public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return aShouldSideBeRendered[aSide] ? BlockTextureMulti.get(BlockTextureDefault.get(mTextureMaterial, mRGBa), aSide==mFacing? BlockTextureDefault.get(mState==STATE_IDLE? mTextureFront: mState==STATE_FINISHED? mTextureFrontFinished : mTextureFrontActive): null) : null;
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if(!aIsServerSide && mState == STATE_RUNNING && aTimer%100==0)  UT.Sounds.play("ktfruaddon:tile.noise.n",5,1.0F, 1.0F,new ChunkCoordinates(xCoord,yCoord,zCoord));
        if(!aIsServerSide)return;
        mState=STATE_IDLE;
        if(mScanner==null)return;
        if(mEnergyStored<mCost){
            mEnergyStored=0;
            return;
        }
        mEnergyStored-=mCost;
        if(mana<16)return;
        mState=STATE_RUNNING;
        if(!mScanner.finished && aTimer % interval == 0)mScanner.startOrContinueScanOres();
        if( mScanner.finished)mState=STATE_FINISHED;
    }
    public static MultiTileEntityRegistry gRegistry = MultiTileEntityRegistry.getRegistry("gt.multitileentity");
    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(aTool.equals(TOOL_softhammer)){
            if(isClientSide())cachedFoundOres.forEach((pos,data)->FxRenderBlockOutline.removeBlockOutlineToRender(codeUtil.CCCoord2MCCoord(pos)));
            return 1;
        }
        if(aTool.equals(TOOL_screwdriver)){
            stateSyncRequired=true;
            //Try to consume pipe and start scan
            if(mState==STATE_IDLE && usedPipes == 0 && (slotHas(0) && gRegistry.getItem(pipeID).getItem().equals(slot(0).getItem()) && slot(0).getItemDamage() == pipeID && slot(0).stackSize == 64)) {
                usedPipes = (short) slot(0).stackSize;
                slotKill(0);
                if(mScanner==null) mScanner = new OreScanner(range,xCoord,yCoord,zCoord,worldObj,true,true).setSpeed(speed).setScanner(this);
                else mScanner.resetScanOres();
                aChatReturn.add("Started a OreScanning process");
            }
            return 1;
        }
        if(aTool.equals(TOOL_magnifyingglass)){
            if(isClientSide())cachedFoundOres.forEach((pos,data)->{
                FxRenderBlockOutline.removeBlockOutlineToRender(codeUtil.CCCoord2MCCoord(pos));
                FxRenderBlockOutline.addBlockOutlineToRender(codeUtil.CCCoord2MCCoord(pos), UT.Code.getRGBInt(OreDictMaterial.get(data.materialID).fRGBaSolid), data.type == ORE_TYPE_GT_BEDROCK || data.type == ORE_TYPE_GT_BEDROCK_SMALL ? 4 : 1, System.currentTimeMillis() + 1800000);
            });

            aChatReturn.add(LH.Chat.CYAN+LH.get("known Ores:"));
            aChatReturn.add(validOreIDs.stream().map(this::getOreName).collect(Collectors.joining(", ")));
            return 1;
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        LH.addEnergyToolTips(this, aList, mEnergyTypeAccepted,null, LH.get(LH.FACE_ANY),null);
        aList.add(LH.Chat.WHITE + LH.get(I18nHandler.ORE_SCANNER_REQUIRE_PIPES)+": "+gRegistry.getItem(pipeID).getDisplayName());
        aList.add(LH.Chat.WHITE + LH.get(I18nHandler.REQUIRE_MANA_BURST));
        aList.add(LH.Chat.BLINKING_CYAN + LH.get(I18nHandler.HINT_NEED_MULTI_AMPERE_INPUT));
        aList.add(LH.Chat.DGRAY + LH.get(LH.TOOL_TO_TOGGLE_SCREWDRIVER));
        aList.add(LH.Chat.DGRAY + LH.get(LH.TOOL_TO_RESET_SOFT_HAMMER));
        aList.add(LH.Chat.DGRAY + LH.get(I18nHandler.HAS_USB_IO_CLICK));
        aList.add(LH.Chat.DGRAY + LH.get(LH.TOOL_TO_DETAIL_MAGNIFYINGGLASS));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public byte getDefaultSide() {
        return SIDE_FRONT;
    }

    @Override
    public boolean[] getValidSides() {
        return SIDES_HORIZONTAL;
    }

    @Override
    public boolean canDrop(int aSlot) {
        return true;
    }

    @Override
    public boolean breakBlock() {
        onFinished();
        cachedFoundOres.forEach((pos,data)->FxRenderBlockOutline.removeBlockOutlineToRender(codeUtil.CCCoord2MCCoord(pos)));
        super.breakBlock();
        return false;
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.machine.oreScanner";
    }

    Map<BlockCoord, OreData> cachedFoundOres = new HashMap<>();
    @Override
    public void onOreFind(int x, int y, int z, short materialID, byte type) {
        if(validOreIDs.contains(materialID) || type == ORE_TYPE_FLUID_SPRING)cachedFoundOres.put(new BlockCoord(x,y,z), new OreData(materialID, type));
        else cachedFoundOres.put(new BlockCoord(x,y,z), new OreData(MT.ElvenElementium.mID, type));
        mana-=4;
    }

    @Override
    public void onFinished() {
        setInventorySlotContents(0,gRegistry.getItem(pipeID, usedPipes));
        usedPipes=0;
        stateSyncRequired =true;
    }
    //Energy
    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        aSize = Math.abs(aSize);
        if (aSize > getEnergySizeInputMax(aEnergyType, aSide) && !TD.Energy.ALL_SIZE_IRRELEVANT.contains(mEnergyTypeAccepted)) {
            if (aDoInject) overcharge(aSize, aEnergyType);
            return aAmount;
        }
        if(mState == STATE_FINISHED)return 0;
        if (aEnergyType == mEnergyTypeAccepted) {
            long tInput = Math.min(mCost - mEnergyStored, aSize * aAmount), tConsumed = Math.min(aAmount, (tInput/aSize) + (tInput%aSize!=0?1:0));
            if (aDoInject) mEnergyStored += tConsumed * aSize;
            return tConsumed;
        }
        return 0;
    }
    @Override
    public boolean isEnergyAcceptingFrom(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return aEnergyType.equals(mEnergyTypeAccepted);
    }

    @Override
    public long getEnergySizeInputMin(TagData aEnergyType, byte aSide) {
        return mCost/4;
    }

    @Override
    public long getEnergySizeInputRecommended(TagData aEnergyType, byte aSide) {
        return mCost;
    }

    @Override
    public long getEnergySizeInputMax(TagData aEnergyType, byte aSide) {
        return mCost;
    }

    boolean stateSyncRequired = false;
    //Client Sync
    @Override
    public boolean onTickCheck(long aTimer) {
        return super.onTickCheck(aTimer) || !cachedFoundOres.isEmpty() || stateSyncRequired;
    }

    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        Map<BlockCoord, OreData> subOreMap = new HashMap<>();
        int putData = 0;
        //Limit pack size
        for(Map.Entry<BlockCoord, OreData> entry : cachedFoundOres.entrySet()){
            if(putData>8)break;
            putData ++;
            subOreMap.put(entry.getKey(),entry.getValue());
        }
        subOreMap.forEach((k,v)->cachedFoundOres.remove(k));
        byte[] data = serializeOreData(subOreMap);
        if(data == null)return super.getClientDataPacket(aSendAll);
        try{
            if(aSendAll){
                byte[] combineData = new byte[data.length+6];
                combineData[0] = -1;
                combineData[1] = (byte) UT.Code.getR(mRGBa);
                combineData[2] = (byte) UT.Code.getG(mRGBa);
                combineData[3] = (byte) UT.Code.getB(mRGBa);
                combineData[4] =  getVisualData();
                combineData[5] = getDirectionData();
                System.arraycopy(data, 0, combineData, 6, data.length);
                stateSyncRequired =false;
                return getClientDataPacketByteArray(true, combineData);
            }
            else {
                byte[] combineData = new byte[data.length+2];
                combineData[0] = -2;
                combineData[1] =  getVisualData();
                System.arraycopy(data, 0, combineData, 2, data.length);
                stateSyncRequired =false;
                return getClientDataPacketByteArray(false, combineData);
            }
        }catch (Exception ignored) {}
        //Not clear cachedOres when error happened
        return super.getClientDataPacket(aSendAll);
    }

    @Override
    public byte getVisualData() {
        return mState;
    }

    @Override
    public void setVisualData(byte aData) {
        mState=aData;
    }

    @Override
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler) {
        if(aData[0] > 0)return super.receiveDataByteArray(aData, aNetworkHandler);
        else if (aData[0] == -1) {
            byte[] superData = Arrays.copyOfRange(aData, 1, 6);
            super.receiveDataByteArray(superData, aNetworkHandler);
            byte[] receivedOreData = Arrays.copyOfRange(aData, 6, aData.length);
            Map<BlockCoord,OreData> deserializedOreData = deserializeOreData(receivedOreData);
            cachedFoundOres.putAll(deserializedOreData);
            deserializedOreData.forEach((pos,data)->FxRenderBlockOutline.addBlockOutlineToRender(codeUtil.CCCoord2MCCoord(pos), UT.Code.getRGBInt(OreDictMaterial.get(data.materialID).fRGBaSolid), data.type == ORE_TYPE_GT_BEDROCK || data.type == ORE_TYPE_GT_BEDROCK_SMALL ? 4 : 1, System.currentTimeMillis() + 3600000));
        } else if (aData[0] == -2) {
            super.receiveDataByte(aData[1], aNetworkHandler);
            byte[] receivedOreData = Arrays.copyOfRange(aData, 2, aData.length);
            Map<BlockCoord,OreData> deserializedOreData = deserializeOreData(receivedOreData);

            cachedFoundOres.putAll(deserializedOreData);
            deserializedOreData.forEach((pos,data)->FxRenderBlockOutline.addBlockOutlineToRender(codeUtil.CCCoord2MCCoord(pos), UT.Code.getRGBInt(OreDictMaterial.get(data.materialID).fRGBaSolid), data.type == ORE_TYPE_GT_BEDROCK || data.type == ORE_TYPE_GT_BEDROCK_SMALL ? 4 : 1, System.currentTimeMillis() + 3600000));
        }
        return true;
    }

    boolean clickDoubleCheck = false;
    public boolean writeIDsToUSB(EntityPlayer aPlayer){
        ItemStack equippedItem=aPlayer.getCurrentEquippedItem();
        if (!(OM.is(OD_USB_STICKS[0],equippedItem))) return false;
        NBTTagCompound aNBT = UT.NBT.make();
        aNBT.setIntArray("ktfru.oreScanner.knownOreList", validOreIDs.stream().mapToInt(n->n).toArray());

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
            return true;
        }

        equippedItem.setTagCompound(UT.NBT.make());
        equippedItem.getTagCompound().setTag(NBT_USB_DATA, aNBT);
        equippedItem.getTagCompound().setByte(NBT_USB_TIER, (byte)1);
        aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN+LH.get(I18nHandler.DATA_WRITE_TO_USB)));

        return true;
    }

    public boolean addIDsFromUSB(EntityPlayer aPlayer){
        ItemStack equippedItem=aPlayer.getCurrentEquippedItem();
        if (!(OM.is(OD_USB_STICKS[0],equippedItem))) return false;

        if (equippedItem.hasTagCompound() && equippedItem.getTagCompound().hasKey(NBT_USB_DATA)) {
            NBTTagCompound aNBT = equippedItem.getTagCompound().getCompoundTag(NBT_USB_DATA);

            if(!aNBT.hasKey("ktfru.oreScanner.knownOreList")){
                aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN+LH.get("Failed: USB data not for oreScanner")));
                return false;
            }

            int[] array = aNBT.getIntArray("ktfru.oreScanner.knownOreList");

            if(array == null){
                aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN+LH.get("Failed: USB data corrupted")));
                return false;
            }

            Arrays.stream(array).filter(n->!validOreIDs.contains((short)n)).forEach(n->validOreIDs.add((short)n));

            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN+LH.get("Successfully read ore data from USB, known Ores:")));
            aPlayer.addChatMessage(new ChatComponentText(validOreIDs.stream().map(this::getOreName).collect(Collectors.joining(", "))));
        }

        return true;
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(isServerSide()){
            if(aPlayer.getCurrentEquippedItem() != null && (OP.dustSmall.contains(aPlayer.getCurrentEquippedItem()) || OP.dustTiny.contains(aPlayer.getCurrentEquippedItem()))){
                short materialID = (short) aPlayer.getCurrentEquippedItem().getItemDamage();
                if(!validOreIDs.contains(materialID))validOreIDs.add(materialID);
                aPlayer.addChatComponentMessage(new ChatComponentText("Successfully Scanned Material "+getOreName(materialID)+", will display correct info if found"));
                return true;
            }
            if(aPlayer.isSneaking()? writeIDsToUSB(aPlayer) : addIDsFromUSB(aPlayer)) return true;
            return openGUI(aPlayer,aSide);
        }
        return false;
    }

    public String getOreName(short id){
        return OreDictMaterial.get(id).mNameLocal;
    }

    @Override
    public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerCommonDefault(aPlayer.inventory, this, aGUIID,0,1);
    }

    @Override
    public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerClientDefault(aPlayer.inventory, this, aGUIID);
    }

    //inventory
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[1];}

    private static final int[] ACCESSIBLE_SLOTS = new int[] {0};

    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}

    //mana
    @Override
    public boolean isFull() {
        return mana >= 16384;
    }

    @Override
    public void recieveMana(int i) {
        if(i <= 32767) mana+= (short) i;
        else mana = 32767;
    }

    @Override
    public boolean canRecieveManaFromBursts() {
        return mState!=STATE_FINISHED;
    }

    @Override
    public int getCurrentMana() {
        return mana;
    }

    public static class OreData{
        public short materialID;
        public byte type;
        public OreData(short materialID, byte type) {
            this.materialID = materialID;
            this.type = type;
        }
    }
    public static byte[] serializeOreData(Map<BlockCoord,OreData> list) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            DataOutputStream dos = new DataOutputStream(bos);

            dos.writeInt(list.size());

            list.forEach((coord, data) -> {
                try {
                    dos.writeInt(coord.x);
                    dos.writeInt(coord.y);
                    dos.writeInt(coord.z);
                    dos.writeShort(data.materialID);
                    dos.writeByte(data.type);
                }catch (Exception ignored){}
            });

            dos.flush();
            return bos.toByteArray();
        }catch (Exception e){e.printStackTrace();}
        return null;
    }
    public static Map<BlockCoord,OreData> deserializeOreData(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
            DataInputStream dis = new DataInputStream(bis);
            Map<BlockCoord,OreData> list = new HashMap<>();
            int size = dis.readInt();
            for (int i = 0; i < size; i++) list.put(new BlockCoord(dis.readInt(), dis.readInt(), dis.readInt()), new OreData(dis.readShort(), dis.readByte()));
            dis.close();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
