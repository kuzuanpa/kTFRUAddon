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

package cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased;

import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.IStructurePredicate;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import net.minecraft.util.ChunkCoordinates;

import java.util.Map;

public interface IStringBaseStructure {
    ChunkCoordinates checkStructure(StructureContext context);
    Map<Character, IStructurePredicate> getPredicates();
    /**expandable structure should return the max size of structure.**/
    ChunkCoordinates getSize();
    Map<Character, String> getExtraDataDesc();
    void setExtraData(char id, String data);

    default ChunkCoordinates getOffset() {return new ChunkCoordinates(0, 0, 0);}
    default IStringBaseStructure setOffset(int aX, int aY, int aZ) {
        throw new UnsupportedOperationException(getClass().getName() + " does not support setOffset()");
    }
    default boolean supportsAsync() {
        for (IStructurePredicate predicate : getPredicates().values()) if (!predicate.supportsAsync()) return false;
        return true;
    }


    /**
     * 这个结构占据的世界包围盒。
     * <b>必须覆盖 checkStructure 会读到的所有位置</b>。
     *
     * <p>默认实现视为机器返回的getSize()和getOffset()是按xyz坐标轴正常放置的，<b>放置规则不同的结构必须覆写此方法</b>
     */
    default BoundingBox getWorldBounds(ITileEntityMultiBlockController aController, byte aFacing) {
        ChunkCoordinates tSize = getSize(), tOffset = getOffset();
        int[] tCorner1 = StructureContext.convertCoord(aFacing, aController.getX(), aController.getY(), aController.getZ(), tOffset.posX, tOffset.posY, tOffset.posZ);
        int[] tCorner2 = StructureContext.convertCoord(aFacing, aController.getX(), aController.getY(), aController.getZ(), tOffset.posX + tSize.posX - 1, tOffset.posY + tSize.posY - 1, tOffset.posZ + tSize.posZ - 1);
        return new BoundingBox(tCorner1[0], tCorner1[1], tCorner1[2], tCorner2[0], tCorner2[1], tCorner2[2]);
    }

    default boolean isInsideStructure(ITileEntityMultiBlockController aController, byte aFacing, int aX, int aY, int aZ) {
        return getWorldBounds(aController, aFacing).isXYZInBox(aX, aY, aZ);
    }
}

