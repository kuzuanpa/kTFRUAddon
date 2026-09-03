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

import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.IStructurePredicate;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import net.minecraft.util.ChunkCoordinates;

import java.util.Map;

public interface IStringBaseStructure {
    ChunkCoordinates checkStructure(StructureContext context);
    Map<Character, IStructurePredicate> getPredicates();
    ChunkCoordinates getSize();
    Map<Character, String> getExtraDataDesc();
    void setExtraData(char id, String data);

    /**
     * The counterpart of {@link ITileEntityMultiBlockController#isInsideStructure}, so a part can ask
     * whether it belongs to a controller without every machine hand writing its own box. The layout is
     * turned into world coordinates the same way the check, build and projection passes do it, so
     * facing and expand direction always agree with what was declared.
     *
     * @param controller the controller of this structure, its position is the origin of the layout.
     * @param facing the same facing that gets passed to {@link #checkStructure}.
     */
    boolean isInsideStructure(ITileEntityMultiBlockController controller, byte facing, int aX, int aY, int aZ);
}


