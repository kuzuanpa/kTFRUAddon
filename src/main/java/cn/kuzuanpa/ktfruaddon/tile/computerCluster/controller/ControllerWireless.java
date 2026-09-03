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

package cn.kuzuanpa.ktfruaddon.tile.computerCluster.controller;

import cn.kuzuanpa.ktfruaddon.api.code.WorldPos;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.layerType.ExpandableLayer;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.special.ComputePartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;

/**
 * The smallest wireless controller, a 3x3x3 box of stainless steel walls with a single compute node
 * slot in the middle of the top layer. Bigger models only differ in the layout and in how many nodes
 * they can hold, see {@link ControllerWirelessLarge} and {@link ControllerWirelessHuge}.
 */
public class ControllerWireless extends ControllerBase {
    /**In blocks. TileEntity.getDistanceFrom returns the squared distance, so it gets squared before comparing.**/
    public static final int MAX_CONNECT_DISTANCE = 512;

    /**Stainless Steel Wall, the shell of every wireless controller model.**/
    protected static final TileDesc WALL = new TileDesc(GTTileEntityRegistry.gregtech, 18002);
    protected static final TileDesc GLASS = new TileDesc(GTTileEntityRegistry.ktfruaddon, 31056);

    static final IStringBaseStructure structure = new LayerStructure(StructureContext.Axis.Y).layerRule("ABA")
            .fixedLayer('A',
                    "WWW",
                    "WWW",
                    "WWW"
            ).layer('B',new ExpandableLayer(4).variation(
                    "WWN",
                    "W N",
                    "WWN"
            ))
            .where('W', new PartPredicate(WALL))
            .where('N', new ComputePartPredicate(WALL))
            .setOffset(-1, 0, 0);

    @Override public IStringBaseStructure getStructure() {return structure;}
    @Override public short getSizeX() {return 3;}
    @Override public short getSizeY() {return 3;}
    @Override public short getSizeZ() {return 3;}
    @Override public short getMapOffsetX() {return -1;}

    @Override
    public boolean canReachPos(WorldPos pos) {
        if (pos == null) return false;
        return isInRange(pos);
    }

    protected boolean isInRange(WorldPos target) {
        if (target == null) return false;
        return getDistanceFrom(target.x, target.y, target.z) <= (double) MAX_CONNECT_DISTANCE * MAX_CONNECT_DISTANCE;
    }

    public String getTileEntityName() {
        return "ktfru.multitileentity.computecluster.controller.wireless";
    }

}
