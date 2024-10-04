/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.multiblock;

import cn.kuzuanpa.ktfruaddon.client.render.FxRenderBlockOutline;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.IComputeNode;
import cn.kuzuanpa.ktfruaddon.tile.util.utils;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.util.WD;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IMappedStructure extends ITileEntityMultiBlockController {
    /**@return null = structure complete**/
    @Nullable
    default ChunkCoordinates checkMappedStructure(ChunkCoordinates lastFailedPos, int machineX, int machineY, int machineZ, int xMapOffset, int yMapOffset, int zMapOffset){
        if (lastFailedPos != null) FxRenderBlockOutline.removeBlockOutlineToRender(lastFailedPos);
        int tX = getX(), tY = getY(), tZ = getZ();
        if (!getWorld().blockExists(tX, tY, tZ)) return null;
        tX = utils.getRealX(getFacing(), tX, xMapOffset, -zMapOffset);
        tY += yMapOffset;
        tZ = utils.getRealZ(getFacing(), tZ, xMapOffset, -zMapOffset);
        int mapX, mapY, mapZ;
        for (mapY = 0; mapY < machineY; mapY++) for (mapZ = 0; mapZ < machineZ ; mapZ++) for (mapX = 0; mapX < machineX; mapX++) {
            int realX=utils.getRealX(getFacing(), tX, mapX, mapZ),realY=tY + mapY,realZ=utils.getRealZ(getFacing(), tZ, mapX, mapZ);
            if (isIgnored(mapX,mapY,mapZ)) continue;
            if (utils.checkAndSetTarget(this, realX, realY, realZ, getBlockID(mapX, mapY, mapZ), getRegistryID(mapX,mapY,mapZ), getDesign(mapX,mapY,mapZ, getBlockID(mapX, mapY, mapZ), getRegistryID(mapX,mapY,mapZ) ), getUsage(mapX,mapY,mapZ, getBlockID(mapX, mapY, mapZ), getRegistryID(mapX,mapY,mapZ) ))) {
                if (getControllerPosList()!=null && isController(mapX,mapY,mapZ, getBlockID(mapX, mapY, mapZ), getRegistryID(mapX,mapY,mapZ))) getControllerPosList().add(new ChunkCoordinates(realX,realY,realZ));
                if (getComputeNodesCoordList()!=null&&WD.te(getWorld(),new ChunkCoordinates(realX,realY,realZ),false) instanceof IComputeNode) getComputeNodesCoordList().add(new ChunkCoordinates(realX,realY,realZ));
            } else if(!onCheckFailed(mapX,mapY,mapZ))return new ChunkCoordinates(realX,realY,realZ);
        }
        return null;
    }

    /**@return will we ignore this error and continue check**/
    default boolean onCheckFailed(int mapX,int mapY,int mapZ){return false;}

    /**These are made for TileEntityBaseControlledMachine**/
    default boolean isController(int mapX,int mapY,int mapZ, int registryID, int blockID) {return false;}
    default List<ChunkCoordinates> getControllerPosList(){return null;}

    int getDesign(int mapX, int mapY, int mapZ, int blockId, int registryID);
    int getUsage(int mapX, int mapY, int mapZ, int registryID, int blockID);
    int getBlockID(int mapX,int mapY,int mapZ);
    boolean isIgnored(int mapX,int mapY,int mapZ);
    short getRegistryID(int mapX,int mapY,int mapZ);
    short getFacing();
    int getX();
    int getY();
    int getZ();
    World getWorld();
     
    List<ChunkCoordinates> getComputeNodesCoordList();
}
