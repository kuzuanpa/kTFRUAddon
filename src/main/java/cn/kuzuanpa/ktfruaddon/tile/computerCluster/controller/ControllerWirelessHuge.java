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

import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.layerType.ExpandableLayer;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.special.ComputePartPredicate;

/**
 * 7x6x7 wireless controller, eighteen compute node slots spread over two inner layers. Everything
 * besides the layout and the node count is inherited from {@link ControllerWireless}.
 */
public class ControllerWirelessHuge extends ControllerWireless {
    static final IStringBaseStructure structure = new LayerStructure(StructureContext.Axis.Y).layerRule("ANA")
            .fixedLayer('A',
                    "WWWWWWW",
                    "WWGWGWW",
                    "WGGGGGW",
                    "WWGGGWW",
                    "WGGGGGW",
                    "WWGWGWW",
                    "WWWWWWW"
            ).layer('N',new ExpandableLayer(8).variation(
                    "WNNWNNW",
                    "NW W WN",
                    "N     N",
                    "WW W WW",
                    "N     N",
                    "NW W WN",
                    "WNNWNNW"
            ))
            .where('W', new PartPredicate(WALL))
            .where('G', new PartPredicate(GLASS))
            .where('N', new ComputePartPredicate(WALL))
            .setOffset(-3, 0, 0);

    @Override public IStringBaseStructure getStructure() {return structure;}

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.computecluster.controller.wireless.huge";
    }
}
