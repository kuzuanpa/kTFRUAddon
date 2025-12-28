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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.energy;

import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import gregapi.data.LH;
import gregapi.tileentity.ITileEntityUnloadable;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

import java.util.List;

import static gregapi.data.CS.*;

public class MultiDynamo extends MultiTransformerBase{
    public short mDynamoWalls = 18022;

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_DESIGN)) mDynamoWalls = aNBT.getShort(NBT_DESIGN);
    }

    @Override
    public boolean checkStructure2(ChunkCoordinates aCoordinates, Entity aPlayer, IInventory aInventory) {
        int
                tMinX = xCoord-(SIDE_X_NEG==mFacing?0:SIDE_X_POS==mFacing?3:1),
                tMinY = yCoord-(SIDE_Y_NEG==mFacing?0:SIDE_Y_POS==mFacing?3:1),
                tMinZ = zCoord-(SIDE_Z_NEG==mFacing?0:SIDE_Z_POS==mFacing?3:1),
                tMaxX = xCoord+(SIDE_X_POS==mFacing?0:SIDE_X_NEG==mFacing?3:1),
                tMaxY = yCoord+(SIDE_Y_POS==mFacing?0:SIDE_Y_NEG==mFacing?3:1),
                tMaxZ = zCoord+(SIDE_Z_POS==mFacing?0:SIDE_Z_NEG==mFacing?3:1),
                tOutX = getOffsetXN(mFacing, 3),
                tOutY = getOffsetYN(mFacing, 3),
                tOutZ = getOffsetZN(mFacing, 3);

        if (worldObj.blockExists(tMinX, tMinY, tMinZ) && worldObj.blockExists(tMaxX, tMaxY, tMaxZ)) {
            mEmitter = null;
            boolean tSuccess = T;
            for (int tX = tMinX; tX <= tMaxX; tX++) for (int tY = tMinY; tY <= tMaxY; tY++) for (int tZ = tMinZ; tZ <= tMaxZ; tZ++) if (!utils.checkAndSetTarget(this, tX, tY, tZ, aCoordinates, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (SIDES_AXIS_X[mFacing]?tX!=tMinX&&tX!=tMaxX:SIDES_AXIS_Z[mFacing]?tZ!=tMinZ&&tZ!=tMaxZ:tY!=tMinY&&tY!=tMaxY) ? 18040 : mDynamoWalls, tX == tOutX && tY == tOutY && tZ == tOutZ ? 2 : 0, tX == tOutX && tY == tOutY && tZ == tOutZ ? MultiTileEntityMultiBlockPart.ONLY_ENERGY_OUT : MultiTileEntityMultiBlockPart.NOTHING)) tSuccess = F;
            return tSuccess;
        }
        return mStructureOkay;
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return
                aX >= xCoord-(SIDE_X_NEG==mFacing?0:SIDE_X_POS==mFacing?3:1) &&
                        aY >= yCoord-(SIDE_Y_NEG==mFacing?0:SIDE_Y_POS==mFacing?3:1) &&
                        aZ >= zCoord-(SIDE_Z_NEG==mFacing?0:SIDE_Z_POS==mFacing?3:1) &&
                        aX <= xCoord+(SIDE_X_POS==mFacing?0:SIDE_X_NEG==mFacing?3:1) &&
                        aY <= yCoord+(SIDE_Y_POS==mFacing?0:SIDE_Y_NEG==mFacing?3:1) &&
                        aZ <= zCoord+(SIDE_Z_POS==mFacing?0:SIDE_Z_NEG==mFacing?3:1);
    }

    static {
        LH.add("gt.tooltip.multiblock.dynamo.1", "Two 3x3s with 2m inbetween made of the Block you crafted this of");
        LH.add("gt.tooltip.multiblock.dynamo.2", "a 3x3x2 of 18 Large Copper Coils inbetween");
        LH.add("gt.tooltip.multiblock.dynamo.3", "Main centered on one of the 3x3s facing outwards");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN     + LH.get(LH.STRUCTURE) + ":");
        aList.add(LH.Chat.WHITE    + LH.get("gt.tooltip.multiblock.dynamo.1"));
        aList.add(LH.Chat.WHITE    + LH.get("gt.tooltip.multiblock.dynamo.2"));
        aList.add(LH.Chat.WHITE    + LH.get("gt.tooltip.multiblock.dynamo.3"));
        super.addToolTips(aList, aStack, aF3_H);
    }

    public void addEnergyToolTips(List<String> aList, ItemStack aStack, boolean aF3_H){
        aList.add(LH.Chat.GREEN + LH.get(LH.ENERGY_INPUT)  + ": " + LH.Chat.WHITE + mInputMin  + " - " +mInputMax  + mEnergyType.getLocalisedChatNameShort() + LH.Chat.WHITE + "/A * max " + LH.Chat.CYAN + mMaxAmpere + "A/t");
        if(mOutput > 0)aList.add(LH.Chat.RED   + LH.get(LH.ENERGY_OUTPUT) + ": " + LH.Chat.WHITE + mOutput + mEnergyTypeOut.getLocalisedChatNameShort() + LH.Chat.WHITE + "/A * max " + LH.Chat.CYAN + mMaxAmpere + "A/t");
    }
    public ITileEntityUnloadable mEmitter = null;

    @Override public TileEntity getEmittingTileEntity() {if (mEmitter == null || mEmitter.isDead()) {mEmitter = null; TileEntity tTileEntity = getTileEntityAtSideAndDistance(OPOS[mFacing], 3); if (tTileEntity instanceof ITileEntityUnloadable) mEmitter = (ITileEntityUnloadable)tTileEntity;} return mEmitter == null ? this : (TileEntity)mEmitter;}
    @Override public byte getEmittingSide() {return OPOS[mFacing];}
    @Override public boolean isInput (byte aSide) {return aSide == mFacing;}
    @Override public boolean isOutput(byte aSide) {return aSide == OPOS[mFacing];}

    @Override public byte getDefaultSide() {return SIDE_FRONT;}
    @Override public boolean[] getValidSides() {return SIDES_VALID;}

    @Override public String getTileEntityName() {return "ktfru.multitileentity.multiblock.dynamo";}
}
