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

import cn.kuzuanpa.ktfruaddon.api.client.fx.FxRenderBlockOutline;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
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

public class MantleHeater extends HeaterBase implements IMultiBlockFluidHandler {
    public ChunkCoordinates lastFailedPos;
    public static final short machineX = 5;
    public static final short machineY = 4;
    public static final short machineZ = 5;
    public TagData mEnergyTypeEmitted=TD.Energy.HU;
    public long mRate=32, mCapacity =5000000, mRateMax =256;
    public boolean clickDoubleCheck=false;

    public short progress = 0;
    /**The Drill Rod in the Center goes all the way down to the Bedrock, see {@link #checkStructure2}. The Controller sits in the Front Middle of the second Layer.**/
    static IStringBaseStructure structure = new LayerStructure(StructureContext.Axis.Y).layerRule("ABCD")
            .fixedLayer('A',
                    "WWWWW",
                    "WWWWW",
                    "WWRWW",
                    "WWWWW",
                    "WWWWW"
            ).fixedLayer('B',
                    "WWWWW",
                    "WPPPW",
                    "KPPPW",
                    "WPPPW",
                    "WWWWW"
            ).fixedLayer('C',
                    " WWW ",
                    "WPPPW",
                    "WPPPW",
                    "WPPPW",
                    " WWW "
            ).fixedLayer('D',
                    "  W  ",
                    " WTW ",
                    "WTTTW",
                    " WTW ",
                    "  W  "
            )
            .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18004, MultiTileEntityMultiBlockPart.ONLY_IN, 0)))
            .where('P', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31003, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('R', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31039, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('T', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31004, MultiTileEntityMultiBlockPart.ONLY_FLUID_OUT, 0)))
            .where('K', new ControllerPredicate())
            .setOffset(-2,-1,0);

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_OUTPUT)) mRate = aNBT.getLong(NBT_OUTPUT);
        if (aNBT.hasKey(NBT_OUTPUT_MAX)) mRateMax = aNBT.getLong(NBT_OUTPUT_MAX);
        if (aNBT.hasKey(NBT_PROGRESS)) progress = aNBT.getShort(NBT_PROGRESS);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        aNBT.setShort(NBT_PROGRESS, progress);
    }
    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        aList.add(LH.Chat.DRED + LH.get(LH.HAZARD_MELTDOWN));
        aList.add(LH.Chat.DGRAY + LH.get(LH.TOOL_TO_TOGGLE_SCREWDRIVER));
        aList.add(LH.Chat.DGRAY + LH.get(LH.TOOL_TO_MEASURE_THERMOMETER));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide()) return true;

        if(!mStructureOkay)aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED+LH.get(I18nHandler.STRUCTURE_ERR)));
        ItemStack equippedItem = aPlayer.getCurrentEquippedItem();
        if (equippedItem != null && equippedItem.getItem() instanceof ItemProjector) {
            structure.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }
        return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
    }

    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer,aIsServerSide);
        if (aTimer%60==0)clickDoubleCheck=false;
        if(mStructureOkay && progress > 16384) mEnergyStored += (long) ( ((progress - 16384)/16f)* (1.1f - (mEnergyStored*1F/getCapacity())) );
    }

    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = structure.checkStructure(new StructureContext(this, (aPlayer != null || aInventory != null)? StructureContext.StringBaseMode.SET: StructureContext.StringBaseMode.CHECK, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        if(lastFailedPos!=null)return false;

        //The Drill Rod Column going down from the Center of the Structure until it reaches the Bedrock.
        int tRodX = utils.getRealX(mFacing, xCoord, 0, 2), tRodZ = utils.getRealZ(mFacing, zCoord, 0, 2);
        for (int tRodY = yCoord - 2; tRodY >= 0; tRodY--) {
            if (!utils.checkAndSetTarget(this, tRodX, tRodY, tRodZ, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.ktfruaddon, (short)31039, 0, MultiTileEntityMultiBlockPart.NOTHING) && !(worldObj.getBlock(tRodX, tRodY, tRodZ) == Blocks.bedrock)){
                FxRenderBlockOutline.addBlockOutlineToRender(new ChunkCoordinates(tRodX, tRodY, tRodZ),0xff0000,2,System.currentTimeMillis()+8000);
                return false;
            }
        }

        return true;
    }
    private static final byte[] forX = {0, 0, 0, 1, -1};
    private static final byte[] forZ = {0, 1, -1, 0, 0};
    @Override
    public void doOutputEnergy() {
        //emitEnergy
        for (int i = 0; i < 5; i++) {
            if (mEnergyStored <= mRate) return;
            mRate = getEmitRate();
            TileEntity tileToEmit = worldObj.getTileEntity(utils.getRealX(mFacing, xCoord, 0, 2)+ forX[i], yCoord - 1 + machineY, utils.getRealZ(mFacing, zCoord, 0, 2)+ forZ[i]);
            if (tileToEmit instanceof ITileEntityEnergy) mEnergyStored -= mRate * ITileEntityEnergy.Util.insertEnergyInto(TD.Energy.HU, SIDE_BOTTOM, mRate, 1, this, tileToEmit);
        }
    }

    @Override
    public void doOutputFluid() {
        for (int i = 0; i < 5; i++) {
            FL.move(mTanks[1], WD.te(worldObj, utils.getRealX(mFacing, xCoord, 0, 2)+ forX[i], yCoord - 1 + machineY, utils.getRealZ(mFacing, zCoord, 0, 2)+ forZ[i], SIDE_BOTTOM, false));
        }
    }

    @Override
    public long getHotLiquidRecipeRate() {
        return mRateMax * 5 * 4;
    }

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if(aEnergyType == TD.Energy.RU && mStructureOkay && aSize*aAmount > 128 && progress < 32767){
            progress = (short) Math.min(32767, progress + Math.sqrt(aSize*aAmount - 128));
            return aAmount;
        }
        return super.doInject(aEnergyType, aSide, aSize, aAmount, aDoInject);
    }

    public void overheat() {
        for (int x = -2; x < machineX-2; x++)for(int z=0;z<machineZ;z++)for(int y=0;y<machineY-1;y++)worldObj.setBlock(utils.getRealX(mFacing,xCoord,x,z),yCoord+y,utils.getRealZ(mFacing,zCoord,x,z), Blocks.flowing_lava);
    }

    @Override
    public long getCapacity() {
        return mCapacity;
    }

    public long getEmitRate(){
        return mEnergyStored > getCapacity() * 0.1 ? mRateMax : (int) ((mEnergyStored / (getCapacity() * 0.1f)) * mRateMax);
    }

    @Override
    public boolean[] getValidSides() {
        return SIDES_HORIZONTAL;
    }
    public float getTemperature(){return ((float)mEnergyStored/(float)getCapacity() *961)+DEFAULT_ENVIRONMENT_TEMPERATURE;}
    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {return structure.isInsideStructure(this, mFacing, aX, aY, aZ);}

    @Override
    public boolean isEnergyAcceptingFrom(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return aEnergyType == TD.Energy.RU;
    }

    @Override public boolean isEnergyEmittingTo(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return aSide == SIDE_TOP && super.isEnergyEmittingTo(aEnergyType, aSide, aTheoretical);}
    @Override public long getEnergyOffered(TagData aEnergyType, byte aSide, long aSize) {
        return Math.min(mRate, mEnergyStored);
    }
    @Override public long getEnergySizeOutputRecommended(TagData aEnergyType, byte aSide) {return mRate;}
    @Override public long getEnergySizeOutputMin(TagData aEnergyType, byte aSide) {return mRate;}
    @Override public long getEnergySizeOutputMax(TagData aEnergyType, byte aSide) {return mRateMax;}
    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return mEnergyTypeEmitted.AS_LIST;}

    // Icons
    public final static IIconContainer
            sTextureSides     = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/mantleHeater/base"),
            sOverlayStop      = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/mantleHeater/front");


    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (!aShouldSideBeRendered[aSide]) return null;
        if(aSide==mFacing) return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides, mRGBa),BlockTextureDefault.get(sOverlayStop ));
        return BlockTextureDefault.get(sTextureSides, mRGBa);
    }
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.mantleheater";
    }
}
