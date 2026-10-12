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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.generator;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.layerType.ExpandableLayer;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.ControllerPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import gregapi.code.TagData;
import gregapi.data.FL;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.multiblocks.IMultiBlockFluidHandler;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.OM;
import gregapi.util.UT;
import gregapi.util.WD;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.*;

public class SunHeater extends HeaterBase implements IMultiBlockFluidHandler, ITileEntityEnergy{
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.sunheater";
    }

    //决定机器大小
    //this controls the size of machine.
    public static final short machineX = 5;
    public static final short machineYmax = 16;
    public static final short machineZ = 5;
    public long mRate=32, maxEnergyStorePerLayer =5000000, maxEmitRatePerLayer =256;
    public TagData mEnergyTypeEmitted=TD.Energy.HU;
    public boolean clickDoubleCheck=false;
    public short machineY=0;
    public ChunkCoordinates lastFailedPos = null;
    /**The Absorb Layers of the Tower repeat up to {@link #machineYmax} Layers, the Top Layer of Heat Pipe Tops caps them off.**/
    private static final int MAX_ABSORB_LAYERS = machineYmax - 2;
    private static final ExpandableLayer absorbLayers = new ExpandableLayer(MAX_ABSORB_LAYERS);
    public static IStringBaseStructure structure = new LayerStructure(StructureContext.Axis.Y).layerRule("ABXD")
            .fixedLayer('A',
                    "WWWWW",
                    "WWWWW",
                    "WWWWW",
                    "WWWWW",
                    "WWWWW"
            ).fixedLayer('B',
                    "WWWWW",
                    "WWWWW",
                    "KWWWW",
                    "WWWWW",
                    "WWWWW"
            ).layer('X', absorbLayers.variation(
                    "SSSSS",
                    "SPPPS",
                    "SPWPS",
                    "SPPPS",
                    "SSSSS"
            )).fixedLayer('D',
                    "HHHHH",
                    "HHHHH",
                    "HHHHH",
                    "HHHHH",
                    "HHHHH"
            )
            .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18002, MultiTileEntityMultiBlockPart.ONLY_FLUID_IN, 0)))
            .where('S', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31002, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('P', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31003, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('H', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31004, MultiTileEntityMultiBlockPart.ONLY_FLUID_OUT, 0)))
            .where('K', new ControllerPredicate())
            .setOffset(-2,-1,0);

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_OUTPUT_MAX)) maxEmitRatePerLayer = aNBT.getLong(NBT_OUTPUT_MAX);
    }
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT, NBT_OUTPUT_MAX, maxEmitRatePerLayer);
    }
    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        if (!worldObj.blockExists(xCoord, yCoord, zCoord)) return mStructureOkay;
        lastFailedPos = structure.checkStructure(new StructureContext(this, (aPlayer != null || aInventory != null)? StructureContext.StringBaseMode.SET: StructureContext.StringBaseMode.CHECK, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        if(lastFailedPos != null) return false;
        //The Absorb Layers are the ones the Machine counts for its Rate and Capacity.
        machineY = (short) absorbLayers.repeatCount;
        return true;
    }

    static {
        LH.add("ktfru.tooltip.multiblock.sunheater.1", "5x2x5 of Stainless Steel Walls");
        LH.add("ktfru.tooltip.multiblock.sunheater.2", "Main Block centered on Side-Top and facing outwards");
        LH.add("ktfru.tooltip.multiblock.sunheater.3", "layers of Absorb Layer:");
        LH.add("ktfru.tooltip.multiblock.sunheater.4", "  5x5 Sunlight Absorber as ring and 3x3 Sun Heator Transmitter as ring, centered stainless steel wall");
        LH.add("ktfru.tooltip.multiblock.sunheater.5", "5x1x5 Sun Heater Top Layer on the top");
        LH.add("ktfru.tooltip.multiblock.sunheater.6", "Emit HU/Output Liquid from 3x3 area of top layer");
        LH.add("ktfru.tooltip.multiblock.sunheater.7", "Rate: 256x(Num of Absorb Layer) HU/t per block (HU mode), 4x faster in Hot Liquid mode");
        LH.add("ktfru.tooltip.multiblock.sunheater.8", "Right click with USB to record coord for mirrors");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.sunheater.1"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.sunheater.2"));
        aList.add(LH.Chat.WHITE + "1 - "+machineYmax+" "+LH.get("ktfru.tooltip.multiblock.sunheater.3"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.sunheater.4"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.sunheater.5"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.sunheater.6"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.sunheater.7"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.sunheater.8"));
        aList.add(LH.Chat.DRED + LH.get(LH.HAZARD_MELTDOWN));
        aList.add(LH.Chat.DGRAY + LH.get(LH.TOOL_TO_TOGGLE_SCREWDRIVER));
        aList.add(LH.Chat.DGRAY + LH.get(LH.TOOL_TO_MEASURE_THERMOMETER));
        super.addToolTips(aList, aStack, aF3_H);
    }
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide())return true;

        if(!mStructureOkay)aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED+LH.get(I18nHandler.STRUCTURE_ERR)));
        ItemStack equippedItem=aPlayer.getCurrentEquippedItem();
        if (equippedItem!=null && equippedItem.getItem() instanceof ItemProjector) {
            structure.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }
        if (!(OM.is(OD_USB_STICKS[0],equippedItem))) return false;
        NBTTagCompound aNBT = UT.NBT.make();
        UT.NBT.setNumber(aNBT, NBT_TARGET_X, this.xCoord);
        UT.NBT.setNumber(aNBT, NBT_TARGET_Y, this.yCoord);
        UT.NBT.setNumber(aNBT, NBT_TARGET_Z, this.zCoord);

        if (equippedItem.hasTagCompound() && !clickDoubleCheck) {
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.YELLOW+LH.get(I18nHandler.USB_ALREADY_HAVE_DATA)));
            clickDoubleCheck=true;
            return true;
        }
        equippedItem.setTagCompound(UT.NBT.make());
        equippedItem.getTagCompound().setTag(NBT_USB_DATA, aNBT);
        equippedItem.getTagCompound().setByte(NBT_USB_TIER, (byte)1);
        aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN+LH.get(I18nHandler.DATA_WRITE_TO_USB)));
        return true;
    }

    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer,aIsServerSide);
        if (aTimer%60==0)clickDoubleCheck=false;
    }

    @Override
    public void doOutputEnergy() {
        //emitEnergy
        if (mEnergyStored <= mRate * 9) return;
        mRate = getEmitRate();
        for (int x = -1; x < 2; x++) for (int z = 1; z < 4; z++) {
            TileEntity tileToEmit = worldObj.getTileEntity(utils.getRealX(mFacing, xCoord, x, z), yCoord + 2 + machineY, utils.getRealZ(mFacing, zCoord, x, z));
            if (tileToEmit instanceof ITileEntityEnergy) mEnergyStored -= mRate * ITileEntityEnergy.Util.insertEnergyInto(TD.Energy.HU, SIDE_BOTTOM, mRate, 1, this, tileToEmit);
        }
    }

    @Override
    public void doOutputFluid() {
        for (int x = -1; x < 2; x++) for (int z = 1; z < 4; z++) {
            FL.move(mTanks[1],WD.te(worldObj,utils.getRealX(mFacing, xCoord, x, z), yCoord + 2 + machineY, utils.getRealZ(mFacing, zCoord, x, z),SIDE_BOTTOM,false));
        }
    }

    @Override
    public long getHotLiquidRecipeRate() {
        return getMaxEmitRate()*36;//9 block output and 4* faster
    }
    public void overheat() {
        for (int x = -2; x < machineX-2; x++)for(int z=0;z<machineZ;z++)for(int y=0;y<machineY+1;y++)worldObj.setBlock(utils.getRealX(mFacing,xCoord,x,z),yCoord+y,utils.getRealZ(mFacing,zCoord,x,z), Blocks.flowing_lava);
    }

    @Override
    public long getCapacity() {
        return mStructureOkay?maxEnergyStorePerLayer * machineY +10000000 :10000000;
    }

    public long getEmitRate(){
        return mEnergyStored > getCapacity() * 0.2 ? getMaxEmitRate() : (int) ((mEnergyStored / (getCapacity() * 0.2f)) * getMaxEmitRate());
    }
    public long getMaxEmitRate(){
        return mStructureOkay ?(maxEmitRatePerLayer * machineY) : 0;
    }

    public float getTemperature(){return ((float)mEnergyStored/(float)getCapacity() *961)+DEFAULT_ENVIRONMENT_TEMPERATURE;}
    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {return structure.isInsideStructure(this, mFacing, aX, aY, aZ);}

    public boolean isEnergyAcceptingFrom(TagData aEnergyType, byte aSide, boolean aTheoretical) {return aEnergyType==TD.Energy.HU&&aSide==SIDE_BOTTOM&&mStructureOkay;}

    @Override public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if (aDoInject) mEnergyStored += (aAmount * aSize);
        return aAmount;
    }

    @Override public boolean isEnergyEmittingTo(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return aSide == SIDE_TOP && super.isEnergyEmittingTo(aEnergyType, aSide, aTheoretical);}
    @Override public long getEnergyOffered(TagData aEnergyType, byte aSide, long aSize) {
        return Math.min(mRate, mEnergyStored);
    }
    @Override public long getEnergySizeOutputRecommended(TagData aEnergyType, byte aSide) {return maxEmitRatePerLayer;}
    @Override public long getEnergySizeOutputMin(TagData aEnergyType, byte aSide) {return maxEmitRatePerLayer;}
    @Override public long getEnergySizeOutputMax(TagData aEnergyType, byte aSide) {return maxEmitRatePerLayer*machineYmax;}
    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return mEnergyTypeEmitted.AS_LIST;}

    @Override
    public boolean[] getValidSides() {
        return SIDES_HORIZONTAL;
    }

    // Icons
    public final static IIconContainer
            sTextureSides     = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/sunHeater/base"),
            sOverlayActive    = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/sunHeater/front");


    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (!aShouldSideBeRendered[aSide]) return null;
        if(aSide==mFacing) return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides, mRGBa),BlockTextureDefault.get(sOverlayActive));
        return BlockTextureDefault.get(sTextureSides, mRGBa);
    }
}
