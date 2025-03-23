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
import cn.kuzuanpa.ktfruaddon.api.tile.IMappedStructure;
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
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.*;

public class MantleHeater extends HeaterBase implements IMultiBlockFluidHandler, IMappedStructure {
    public ChunkCoordinates lastFailedPos;
    public static final short machineX = 5;
    public static final short machineY = 4;
    public static final short machineZ = 5;
    public TagData mEnergyTypeEmitted=TD.Energy.HU;
    public long mRate=32, mCapacity =5000000, mRateMax =256;
    public boolean clickDoubleCheck=false;

    public short progress = 0;
    public final short xMapOffset = -2, zMapOffset = 0;

    public static int[][][] blockIDMap = {{
            {18004, 18004, 18004, 18004, 18004},
            {18004, 18004, 18004, 18004, 18004},
            {18004, 18004, 31039, 18004, 18004},
            {18004, 18004, 18004, 18004, 18004},
            {18004, 18004, 18004, 18004, 18004},
    },{
            {18004, 18004,   0  , 18004, 18004},
            {18004, 31003, 31003, 31003, 18004},
            {18004, 31003, 31003, 31003, 18004},
            {18004, 31003, 31003, 31003, 18004},
            {18004, 18004, 18004, 18004, 18004},
    },{
            {  0  , 18004, 18004, 18004,   0  },
            {18004, 31003, 31003, 31003, 18004},
            {18004, 31003, 31003, 31003, 18004},
            {18004, 31003, 31003, 31003, 18004},
            {  0  , 18004, 18004, 18004,   0  },
    },{
            {  0  ,   0  , 18004,   0  ,   0  },
            {  0  , 18004, 31004, 18004,   0  },
            {18004, 31004, 31004, 31004, 18004},
            {  0  , 18004, 31004, 18004,   0  },
            {  0  ,   0  , 18004,   0  ,   0  },
    }};
    short k = GTTileEntityRegistry.ktfruaddon;
    short g = GTTileEntityRegistry.gregtech;

    public int getUsage(int mapX, int mapY, int mapZ) {
int registryID = getRegistryID(mapX,mapY,mapZ), blockID = getBlockID(mapX, mapY, mapZ);
        if(blockID==18004)return MultiTileEntityMultiBlockPart.ONLY_IN;
        if(blockID==31004)return MultiTileEntityMultiBlockPart.ONLY_FLUID_OUT;
        return MultiTileEntityMultiBlockPart.NOTHING;
    }

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
    public TileDesc[] getTileDescs(int mapX, int mapY, int mapZ) {
        return new TileDesc[]{ new TileDesc(getRegistryID(mapX, mapY, mapZ), getBlockID(mapX, mapY, mapZ),getUsage(mapX, mapY, mapZ))};
    }

    public int getBlockID(int mapX, int mapY, int mapZ) {
        return blockIDMap[mapY][mapZ][mapX];
    }

    @Override
    public boolean isIgnored(int mapX, int mapY, int mapZ) {
        return getBlockID(mapX,mapY,mapZ)==0;
    }

    public short getRegistryID(int mapX, int mapY, int mapZ) {
        return getBlockID(mapX,mapY,mapZ) == 18004 ? g:k;
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        aList.add(LH.Chat.DRED + LH.get(LH.HAZARD_MELTDOWN));
        aList.add(LH.Chat.DGRAY + LH.get(LH.TOOL_TO_TOGGLE_SCREWDRIVER));
        aList.add(LH.Chat.DGRAY + LH.get(LH.TOOL_TO_MEASURE_THERMOMETER));
        super.addToolTips(aList, aStack, aF3_H);
    }

    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer,aIsServerSide);
        if (aTimer%60==0)clickDoubleCheck=false;
        if(mStructureOkay && progress > 16384) mEnergyStored += (long) ( ((progress - 16384)/16f)* (1.1f - (mEnergyStored*1F/getCapacity())) );
    }

    @Override
    public boolean checkStructure2() {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = checkMappedStructure(lastFailedPos, machineX, machineY, machineZ, xMapOffset,-1,zMapOffset);
        if(lastFailedPos!=null)return false;

        tX = utils.getRealX(getFacing(), tX, xMapOffset, -zMapOffset);
        tY -= 1;
        tZ = utils.getRealZ(getFacing(), tZ, xMapOffset, -zMapOffset);
        int mapX=utils.getRealX(getFacing(), tX, 2, 2), mapZ=utils.getRealZ(getFacing(), tZ, 2, 2);
        for (int i = 1; i < yCoord; i++) {
            int mapY=tY - i;
            if (!utils.checkAndSetTarget(this, mapX, mapY, mapZ, 31039, k, 0, MultiTileEntityMultiBlockPart.NOTHING) && !(worldObj.getBlock(mapX, mapY, mapZ) == Blocks.bedrock)){
                FxRenderBlockOutline.addBlockOutlineToRender(new ChunkCoordinates(mapX, mapY, mapZ),0xff0000,2,System.currentTimeMillis()+8000);
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
    public boolean isInsideStructure(int aX, int aY, int aZ) {return true;}

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
