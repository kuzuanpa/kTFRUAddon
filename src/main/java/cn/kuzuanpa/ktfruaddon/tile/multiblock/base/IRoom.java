/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.multiblock.base;

import cn.kuzuanpa.ktfruaddon.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.code.codeUtil;
import cn.kuzuanpa.ktfruaddon.tile.util.utils;
import codechicken.lib.vec.BlockCoord;
import cpw.mods.fml.common.FMLLog;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public interface IRoom extends ITileEntityMultiBlockController{
    /**
     * @param availableTiles       all available TileEntity in GregTech
     * @param aController          The ControllerBlock
     * @param startFromTopOrBack   Start Point,true=Top,false=Back
     * @param checkRange           Max range when checking
     * @param shouldCornerBeSealed Should every corner be filled,or just the blocks next to RoomSpace
     */
    static List<BlockCoord> checkAndGetRoom(utils.GTTileEntity[] availableTiles, ITileEntityMultiBlockController aController, boolean startFromTopOrBack, BoundingBox checkRange, boolean shouldCornerBeSealed) {
        ConcurrentHashMap<Integer, BlockCoord> checkingBlockCoords = new ConcurrentHashMap<Integer, BlockCoord>();
        ArrayList<BlockCoord> roomSpace = new ArrayList<BlockCoord>();
        //Starting from TOP
        if (startFromTopOrBack)
            checkingBlockCoords.put(0, new BlockCoord(aController.getX(), aController.getY() + 1, aController.getZ()));
        //Starting from Back
        if (!startFromTopOrBack)
            checkingBlockCoords.put(0, codeUtil.MCCoord2CCCoord(utils.getRealCoord(((TileEntityBase09FacingSingle) aController).mFacing, aController.getX(), aController.getY(), aController.getZ(), 0, 0, 1)));
        byte[] forX = shouldCornerBeSealed? new byte[]{-1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1, -1, 0, 1} : new byte[]{1, -1, 0, 0, 0, 0};
        byte[] forY = shouldCornerBeSealed? new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1} : new byte[]{0, 0, 1, -1, 0, 0};
        byte[] forZ = shouldCornerBeSealed? new byte[]{-1, -1, -1, 0, 0, 0, 1, 1, 1, -1, -1, -1, 0, 0, 1, 1, 1, -1, -1, -1, 0, 0, 0, 1, 1, 1} : new byte[]{0, 0, 0, 0, 1, -1};
        byte forCount = (byte) (shouldCornerBeSealed?26:6);
        checkingBlockCoords.values().forEach(coord -> {
            //Check blocks at every side
            for (int i = 0; i < forCount; i++) {
                BlockCoord checkingCoord = new BlockCoord(coord.x + forX[i], coord.y + forY[i], coord.z + forZ[i]);
                if (Arrays.stream(availableTiles).noneMatch(availTile -> utils.checkAndSetTarget(aController, checkingCoord.x, checkingCoord.y, checkingCoord.z, availTile.aRegistryMeta, availTile.aRegistryID, availTile.aDesign, availTile.aUsage))) {
                    if (!checkRange.isCoordInBox(checkingCoord)) {
                        FMLLog.log(Level.INFO, "Err: Out of range:" + checkingCoord.x + checkingCoord.y + checkingCoord.z);
                        checkingBlockCoords.clear();
                        break;
                    }
                    if (!checkingBlockCoords.contains(checkingCoord)) {
                        FMLLog.log(Level.INFO, "will check block:" + checkingCoord.x + checkingCoord.y + checkingCoord.z);
                        checkingBlockCoords.put(checkingBlockCoords.size(), checkingCoord);

                    }
                }
            }
        });
        checkingBlockCoords.forEachValue(0, roomSpace::add);
        checkingBlockCoords.clear();
        return roomSpace;
    }

    default void checkRoomDefault(boolean startFromTopOrBack, int[] checkRange2, boolean shouldCornerBeSealed){
        final BlockCoord StartPoi = codeUtil.MCCoord2CCCoord(utils.getRealCoord((byte)getFacing(), getX(), getY(), getZ(), checkRange2[0], checkRange2[1], checkRange2[2]));
        final BlockCoord EndPoi = codeUtil.MCCoord2CCCoord(utils.getRealCoord((byte)getFacing(), getX(), getY(), getZ(), checkRange2[3], checkRange2[4], checkRange2[5]));
        BoundingBox checkRange = new BoundingBox(StartPoi, EndPoi);
        checkAndGetRoom(getAvailableTiles(), this, startFromTopOrBack, checkRange, shouldCornerBeSealed);
    }
    utils.GTTileEntity[] getAvailableTiles();
    short getFacing();
    int getX();
    int getY();
    int getZ();
    World getWorld();

}
