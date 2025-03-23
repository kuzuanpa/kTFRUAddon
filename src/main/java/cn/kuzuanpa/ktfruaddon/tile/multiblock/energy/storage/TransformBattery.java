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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.storage;

import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.ICustomPartValidator;
import cn.kuzuanpa.ktfruaddon.api.tile.IMappedStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import cn.kuzuanpa.ktfruaddon.item.items.itemFlywheel;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.TransformerPart;
import gregapi.block.multitileentity.IWailaTile;
import gregapi.code.TagData;
import gregapi.data.IL;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
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
import net.minecraft.util.ChunkCoordinates;

import java.util.ArrayList;
import java.util.List;

import static gregapi.data.CS.*;

public class TransformBattery extends MultiBatteryBase implements IMappedStructure, ICustomPartValidator, IWailaTile {
    public List<TileEntity> partList = new ArrayList<>();

    public boolean sealed = false;

    public final short sizeX = 4, sizeY = 7, sizeZ = 4;
    public final short xMapOffset = 0, zMapOffset = 0;
    public int invSize = 8;

    public int mWall = 18006, mCoil = 18041, mCond = 31040, mBatt = 31041, trans = 32767;
    public int[][][] blockIDMap;
    public void initBlockIDMap(){
        blockIDMap = new int[][][] {{
                {  0  , trans, trans, trans},
                {trans, mCond, mCond, trans},
                {trans, mCond, mCond, trans},
                {trans, trans, trans, trans},
        },{
                {trans, trans, trans, trans},
                {trans, mCond, mCond, trans},
                {trans, mCond, mCond, trans},
                {trans, trans, trans, trans},
        },{
                {mCoil, mCoil, mCoil, mCoil},
                {mCoil, mCond, mCond, mCoil},
                {mCoil, mCond, mCond, mCoil},
                {mCoil, mCoil, mCoil, mCoil},
        },{
                {mCoil, mCoil, mCoil, mCoil},
                {mCoil, mCond, mCond, mCoil},
                {mCoil, mCond, mCond, mCoil},
                {mCoil, mCoil, mCoil, mCoil},
        },{
                {mCoil, mCoil, mCoil, mCoil},
                {mCoil, mCond, mCond, mCoil},
                {mCoil, mCond, mCond, mCoil},
                {mCoil, mCoil, mCoil, mCoil},
        },{
                {mWall, mWall, mWall, mWall},
                {mWall, mBatt, mBatt, mWall},
                {mWall, mBatt, mBatt, mWall},
                {mWall, mWall, mWall, mWall},
        },{
                {mWall, mWall, mWall, mWall},
                {mWall, mBatt, mBatt, mWall},
                {mWall, mBatt, mBatt, mWall},
                {mWall, mWall, mWall, mWall},
        }};
    }
    short k = GTTileEntityRegistry.ktfruaddon;
    short g = GTTileEntityRegistry.gregtech;

    @Override
    public TileDesc[] getTileDescs(int mapX, int mapY, int mapZ) {
        return new TileDesc[]{ new TileDesc(getRegistryID(mapX, mapY, mapZ), getBlockID(mapX, mapY, mapZ),getUsage(mapX, mapY, mapZ))};
    }

    @Override
    public boolean isPartValid(ChunkCoordinates realPos, ChunkCoordinates mapPos) {
        if(getBlockID(mapPos.posX,mapPos.posY,mapPos.posZ) == trans){
            TileEntity tile = getTileEntity(realPos);
            if (tile instanceof TransformerPart) {
                return utils.setTarget(this, tile, 0, MultiTileEntityMultiBlockPart.NOTHING, false);
            }else return utils.checkAndSetTarget(this, realPos, new TileDesc[]{new TileDesc(g,mWall,MultiTileEntityMultiBlockPart.NOTHING,0)});
        }
        else return utils.checkAndSetTarget(this, realPos, getTileDescs(mapPos.posX,mapPos.posY,mapPos.posZ));
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        refreshBatteryCapacity();
        if(aNBT.hasKey("ktfru.sealed")) sealed = aNBT.getBoolean("ktfru.sealed");
        if(aNBT.hasKey(NBT_INV_SIZE)) invSize = aNBT.getInteger(NBT_INV_SIZE);
        if(aNBT.hasKey(NBT_DESIGN+".wall")) mWall = aNBT.getInteger(NBT_DESIGN+".wall");
        if(aNBT.hasKey(NBT_DESIGN+".coil")) mCoil = aNBT.getInteger(NBT_DESIGN+".coil");
        if(aNBT.hasKey(NBT_DESIGN+".batt")) mBatt = aNBT.getInteger(NBT_DESIGN+".batt");
        if(aNBT.hasKey(NBT_DESIGN+".cond")) mCond = aNBT.getInteger(NBT_DESIGN+".cond");
        initBlockIDMap();
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setBoolean(aNBT,"ktfru.sealed", sealed);
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(aTool.equals(TOOL_monkeywrench) && mEnergyType != TD.Energy.LU){
            refreshBatteryCapacity();
            sealed = !sealed;
        }
        if(aTool.equals(TOOL_magnifyingglass)){
            if(mCapacity > 0 )aChatReturn.add(String.format("%.4f", mEnergyStored*100F/mCapacity) + " %");
            aChatReturn.add(mEnergyStored + " / " +mCapacity + mEnergyType.getLocalisedChatNameShort());
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    public void refreshBatteryCapacity(){
        mCapacity = 0;
        for (int i = 0; i < invSize; i++) {
            //LU Battery, Sorry but I can't find a better way to do those.
            if(mEnergyType == TD.Energy.LU) {
                mCapacity = 367001600000L; //3276800000L (T5) * 4(Crystal block is larger than T5) * 28(total 28* Crystal block);
                sealed = true;
                break;
            }
            if (!slotHas(i))continue;
            //EU Battery.
            if(mEnergyType == TD.Energy.EU) {
                if (slot(i).getItem().equals(IL.Battery_Lead_Acid_Cell_Filled.getItem())) mCapacity +=   512000L * slot(i).stackSize;
                if (slot(i).getItem().equals(IL.Battery_Alkaline_Cell_Filled .getItem())) mCapacity +=  2048000L * slot(i).stackSize;
                if (slot(i).getItem().equals(IL.Battery_NiCd_Cell_Filled     .getItem())) mCapacity +=  2048000L * slot(i).stackSize;
                if (slot(i).getItem().equals(IL.Battery_LiCoO2_Cell_Filled   .getItem())) mCapacity +=  8192000L * slot(i).stackSize;
                if (slot(i).getItem().equals(IL.Battery_LiMn_Cell_Filled     .getItem())) mCapacity += 32768000L * slot(i).stackSize;
            }
            //RU Battery
            if(mEnergyType == TD.Energy.RU) {
                if(!(slot(i).getItem() instanceof itemFlywheel))continue;
                mCapacity+= (long) (Math.floor(itemFlywheel.getMaxStorage(ST.meta(slot(i)))))*slot(i).stackSize;
            }
            if(mEnergyStored > mCapacity)mEnergyStored=mCapacity;
        }
    }

    public int getUsage(int mapX, int mapY, int mapZ) {
        int blockID = getBlockID(mapX, mapY, mapZ);
        if (blockID == mWall) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
        } else{return MultiTileEntityMultiBlockPart.NOTHING;}
    }

    public int getBlockID(int checkX, int checkY, int checkZ){
        return blockIDMap[checkY][checkZ][checkX];
    }

    public  boolean isIgnored(int checkX, int checkY, int checkZ){
        return false;
    }
    public short getRegistryID(int checkX, int checkY, int checkZ){
        int blockID = getBlockID(checkX, checkY, checkZ);
        return blockID == mCond || blockID == mBatt || blockID == 0? k: g;
    }

    ChunkCoordinates lastFailedPos=null;
    @Override
    public boolean checkStructure2() {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = checkMappedStructure(null, sizeX, sizeY, sizeZ,xMapOffset,0,zMapOffset);

        return lastFailedPos==null;
    }

    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (aIsServerSide && sealed && checkStructure(false)) {
            partList.stream().filter(tile -> tile instanceof TransformerPart).map(tile -> (TransformerPart) tile).filter(tile-> mEnergyStored > tile.mOutputVoltage * tile.mOutputAmpere).forEach(tile-> mEnergyStored -= tile.mOutputVoltage * tile.doInject(mEnergyTypeOut, SIDE_INSIDE, tile.mOutputVoltage, tile.mOutputAmpere,true));
        }
    }

    @Override
    public boolean isEnergyAcceptingFrom(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return sealed && super.isEnergyAcceptingFrom(aEnergyType, aSide, aTheoretical);
    }

    //这是设置主方块的物品提示
    //controls tooltip of controller block
    static {
        LH.add("gt.tooltip.multiblock.example.complex.1", "5x5x2 of Stainless Steel Walls");
        LH.add("gt.tooltip.multiblock.example.complex.2", "Main Block centered on Side-Bottom and facing outwards");
        LH.add("gt.tooltip.multiblock.example.complex.3", "Input and Output at any Blocks");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.1"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.2"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.3"));
        super.addToolTips(aList, aStack, aF3_H);
    }
    //这里是设置该机器的内部区域
    //controls areas inside the machine
    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return new BoundingBox(utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset),yCoord,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset),utils.getRealX(mFacing,utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset), sizeX, sizeZ),yCoord+ sizeY,utils.getRealZ(mFacing,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset), sizeX, sizeZ)).isXYZInBox(aX,aY,aZ);
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(isServerSide())return openGUI(aPlayer, aSide);
        return false;
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.storage.transformer";
    }
    @Override
    public boolean isPartSpecial(TileEntity tile){return tile instanceof TransformerPart;}

    @Override
    public void receiveSpecialBlockList(List<TileEntity> list) {
        partList = list;
    }

    // Inventory Stuff
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[invSize];}


    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return UT.Code.getAscendingArray(invSize);}


    @Override
    public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {
        if (aSlot >= invSize || sealed) return F;
        return T;
    }

    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {
        return !sealed;
    }

    @Override
    public boolean isUseableByPlayerGUI(EntityPlayer aPlayer) {
        return super.isUseableByPlayerGUI(aPlayer) && !sealed;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    @Override public boolean isInput(byte aSide) {return true;}

    @Override
    public boolean[] getValidSides() {
        return SIDES_HORIZONTAL;
    }

    public static IIconContainer sTextureCommon= new Textures.BlockIcons.CustomIcon("machines/multiblockmains/transformer/common"),
            sOverlayFront= new Textures.BlockIcons.CustomIcon("machines/multiblockmains/transformer/front");
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa), aSide==mFacing? BlockTextureDefault.get(sOverlayFront) : null);
    }

    @Override
    public IWailaInfoProvider[] getWailaInfos() {
        return instanceInfoEnergyIORange.asArray();
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
}
