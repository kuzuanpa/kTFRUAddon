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
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

package cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.storage;

import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.SpecialPartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.special.TransformerPartPredicate;
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
import gregapi.util.WD;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static gregapi.data.CS.*;

public class TransformBattery extends MultiBatteryBase implements SpecialPartPredicate.IReceiveSpecialPart, IWailaTile {
    public List<ChunkCoordinates> partPosList = new ArrayList<>();

    public boolean sealed = false;

    public int invSize = 8;

    public int mWall = 18006, mCoil = 18041, mCond = 31040, mBatt = 31041;

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
        structure = new LayerStructure(StructureContext.Axis.Y).layerRule("AABBBCC")
                .fixedLayer('A',
                        "TTTT",
                        "TCCT",
                        "TCCT",
                        "TTTT"
                ).fixedLayer('B',
                        "OOOO",
                        "OCCO",
                        "OCCO",
                        "OOOO"
                ).fixedLayer('C',
                        "WWWW",
                        "WBBW",
                        "WBBW",
                        "WWWW"
                )
                .where('T', new TransformerPartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, mWall, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN)))
                .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, mWall, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN)))
                .where('C', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, mCond)))
                .where('O', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, mCoil)))
                .where('B', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, mBatt)))
                .setOffset(0,0,0) ;
    }

    //Structure
    ChunkCoordinates lastFailedPos=null;
    IStringBaseStructure structure;
    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = structure.checkStructure(new StructureContext(this, (aPlayer != null || aInventory != null)? StructureContext.StringBaseMode.SET: StructureContext.StringBaseMode.CHECK, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        return lastFailedPos==null;
    }
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide())return true;

        if(!mStructureOkay)aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED+LH.get(I18nHandler.STRUCTURE_ERR)));

        ItemStack equippedItem=aPlayer.getCurrentEquippedItem();
        if (equippedItem!=null && equippedItem.getItem() instanceof ItemProjector) {
            structure.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }

        openGUI(aPlayer, aSide);
        return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
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

    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (aIsServerSide && sealed && checkStructure(false)) {
            partPosList.stream().map(pos-> (TransformerPart)WD.te(worldObj, pos,false)).filter(Objects::nonNull).filter(tile-> mEnergyStored > tile.mOutputVoltage * tile.mOutputAmpere).forEach(tile-> mEnergyStored -= tile.mOutputVoltage * tile.doInject(mEnergyTypeOut, SIDE_INSIDE, tile.mOutputVoltage, tile.mOutputAmpere,true));
        }
    }

    @Override
    public boolean isEnergyAcceptingFrom(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return sealed && super.isEnergyAcceptingFrom(aEnergyType, aSide, aTheoretical);
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        super.addToolTips(aList, aStack, aF3_H);
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
    }

    public static final short sizeX = 4, sizeY = 7, sizeZ = 4;
    public static final short xMapOffset = 0, zMapOffset = 0;
    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return new BoundingBox(utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset),yCoord,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset),utils.getRealX(mFacing,utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset), sizeX, sizeZ),yCoord+ sizeY,utils.getRealZ(mFacing,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset), sizeX, sizeZ)).isXYZInBox(aX,aY,aZ);
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.storage.transformer";
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

    @Override
    public void receiveSpecialPart(ChunkCoordinates partPos, TileEntity part) {
        partPosList.add(partPos);
    }
}
