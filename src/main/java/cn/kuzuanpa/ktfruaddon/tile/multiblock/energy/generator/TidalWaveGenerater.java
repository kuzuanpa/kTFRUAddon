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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.generator;

import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.ICustomPartValidator;
import cn.kuzuanpa.ktfruaddon.api.tile.IMappedStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import cpw.mods.fml.common.FMLLog;
import gregapi.block.multitileentity.IWailaTile;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.machines.ITileEntityRunningActively;
import gregapi.tileentity.multiblocks.IMultiBlockEnergy;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import org.apache.logging.log4j.Level;

import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.*;

public class TidalWaveGenerater extends TileEntityBase10MultiBlockBase implements ITileEntityEnergy, IMultiBlockEnergy, ITileEntityRunningActively, IMappedStructure, ICustomPartValidator, IWailaTile {
    private TagData mEnergyTypeEmitted=TD.Energy.RU;


    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (!aIsServerSide || !mStructureOkay) return;
        if (checkStructure(false) && getAdjacentTileEntity(SIDE_TOP) != null && getAdjacentTileEntity(SIDE_TOP).mTileEntity instanceof ITileEntityEnergy) {
            int mRate = (int) (Math.sin(aTimer/31.4f)*96) + 32;
            TileEntity tileToEmit = getAdjacentTileEntity(SIDE_TOP).mTileEntity;
            if (tileToEmit instanceof ITileEntityEnergy) ITileEntityEnergy.Util.insertEnergyInto(mEnergyTypeEmitted, SIDE_BOTTOM, mRate, 1, this, tileToEmit);
        }

    }

    @Override public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {
        return aEmitting && aEnergyType == mEnergyTypeEmitted;
    }
    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return mEnergyTypeEmitted.AS_LIST;}

    @Override public boolean getStateRunningPassively() {return checkStructure(false);}
    @Override public boolean getStateRunningActively() {return checkStructure(false);}
    @Override public boolean getStateRunningPossible() {return checkStructure(false);}


    //Structure
    public ChunkCoordinates lastFailedPos;
    public static final short machineX = 3;
    public static final short machineY = 3;
    public static final short machineZ = 5;

    public final short xMapOffset = -1, zMapOffset = 0;

    public static int[][][] blockIDMap = {{
            {18002, 18002, 18002},
            {18002, 18002, 18002},
            {31045, 31045, 31045},
            {31045, 31045, 31045},
            {31045, 31045, 31045},
    },{
            {18002, 18002, 18002},
            {18002,   0  , 18002},
            {18002,   0  , 18002},
            {18002,   0  , 18002},
            {31045, 31045, 31045},
    },{
            {18002,   0  , 18002},
            {18002,   0  , 18002},
            {18002,   0  , 18002},
            {18002,   0  , 18002},
            {31045, 31045, 31045},
    }};
    short k = GTTileEntityRegistry.ktfruaddon;
    short g = GTTileEntityRegistry.gregtech;

    @Override
    public boolean[] getValidSides() {
        return SIDES_HORIZONTAL;
    }

    @Override
    public TileDesc[] getTileDescs(int mapX, int mapY, int mapZ) {
        return new TileDesc[]{ new TileDesc(getRegistryID(mapX, mapY, mapZ), getBlockID(mapX, mapY, mapZ),MultiTileEntityMultiBlockPart.NOTHING)};
    }

    public int getBlockID(int mapX, int mapY, int mapZ) {
        return blockIDMap[mapY][mapZ][mapX];
    }

    @Override
    public boolean isIgnored(int mapX, int mapY, int mapZ) {
        return getBlockID(mapX,mapY,mapZ)==0;
    }

    public short getRegistryID(int mapX, int mapY, int mapZ) {
        return getBlockID(mapX,mapY,mapZ) == 18002 ? g:k;
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.ALLOW_PART_SHARE));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public boolean checkStructure2() {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = checkMappedStructure(lastFailedPos, machineX, machineY + 2, machineZ + 2, xMapOffset ,-3,zMapOffset + 1);
        if(lastFailedPos!=null)return false;

        return true;
    }


    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.tidalwave_generator";
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return new BoundingBox(utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset),yCoord,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset),utils.getRealX(mFacing,utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset),machineX,machineZ),yCoord+machineY,utils.getRealZ(mFacing,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset),machineX,machineZ)).isXYZInBox(aX,aY,aZ);
    }

    @Override
    public boolean isPartValid(ChunkCoordinates realPos, ChunkCoordinates mapPos) {
        if(mapPos.posZ == 0) return mapPos.posY == 0 || mapPos.posY == machineY + 1 || worldObj.getBlock(realPos.posX, realPos.posY, realPos.posZ).isOpaqueCube();
        else if(mapPos.posY == machineY + 1) return mapPos.posZ <= 1 || worldObj.getBlock(realPos.posX, realPos.posY, realPos.posZ).equals(Blocks.air);
        else if (mapPos.posY == 0 || mapPos.posZ == machineZ + 1)return (worldObj.getBlock(realPos.posX, realPos.posY, realPos.posZ) instanceof BlockLiquid);
        else {
            log(realPos+", mapPos:"+mapPos+", Should be part at"+new ChunkCoordinates(mapPos.posX  , mapPos.posY -1, mapPos.posZ -1));
            return isIgnored(mapPos.posX , mapPos.posY - 1, mapPos.posZ - 1) || utils.checkAndSetTarget(this, realPos, getTileDescs(mapPos.posX , mapPos.posY - 1, mapPos.posZ - 1), true);
        }
    }
    public void log(String msg){
        FMLLog.log(Level.FATAL, "Checking block: " + msg);
    }

    @Override
    public IWailaInfoProvider[] getWailaInfos() {
        return instanceInfoState.asArray();
    }
}