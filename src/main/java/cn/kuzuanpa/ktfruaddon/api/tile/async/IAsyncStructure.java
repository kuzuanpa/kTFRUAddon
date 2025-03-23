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
package cn.kuzuanpa.ktfruaddon.api.tile.async;

import cn.kuzuanpa.ktfruaddon.api.tile.part.IMultiBlockPart;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

public interface IAsyncStructure {

    default void onAsyncCheckStructureCompleted() {};

    boolean asyncCheckStructure(AsyncStructureManager.WorldContainer worldContainer);

    static boolean checkAndSetTarget(AsyncStructureManager.WorldContainer worldContainer, ITileEntityMultiBlockController aController, ChunkCoordinates coord, TileDesc[] availTiles, boolean loadChunk) {
        if (!worldContainer.isBlockExist(coord.posX,coord.posY,coord.posZ) && !loadChunk) return false;
        TileEntity tTileEntity = worldContainer.getTileEntity(coord.posX,coord.posY,coord.posZ);
        if (tTileEntity == aController) {
            return true;
        }
        if (tTileEntity instanceof MultiTileEntityMultiBlockPart) {
            for (TileDesc tTile : availTiles) {
                if (tTile.aRegistryMeta != ((MultiTileEntityMultiBlockPart) tTileEntity).getMultiTileEntityID() || tTile.aRegistryID != ((MultiTileEntityMultiBlockPart) tTileEntity).getMultiTileEntityRegistryID()) continue;
                return utils.setTarget(aController, tTileEntity, tTile.aDesign, tTile.aUsage, false);
            }
        } else if (tTileEntity instanceof IMultiBlockPart) {
            for (TileDesc tTile : availTiles) {
                if (tTile.aRegistryMeta != ((IMultiBlockPart) tTileEntity).getMultiTileEntityID() || tTile.aRegistryID != ((IMultiBlockPart) tTileEntity).getMultiTileEntityRegistryID()) continue;
                return utils.setTarget(aController, tTileEntity, tTile.aDesign, tTile.aUsage, false);
            }
        }
        return false;
    }
}
