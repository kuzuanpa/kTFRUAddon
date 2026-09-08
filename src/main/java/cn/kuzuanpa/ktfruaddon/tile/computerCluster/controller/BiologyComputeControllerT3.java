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

import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.AirPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.special.ComputePartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;

public class BiologyComputeControllerT3 extends BiologyComputeController {
    private static final IStringBaseStructure STRUCTURE = new LayerStructure(StructureContext.Axis.Y).layerRule("ABCDEFG")
            .fixedLayer('A', "WWWWWWWWW", "WW     WW", "W       W", "W       W", "WWWWWWWWW")
            .fixedLayer('B', "WNNW WNNW", "N A   A N", "N A A A N", "N K A K N", "WNNW WNNW")
            .fixedLayer('C', "WNNW WNNW", "N A   A N", "W A A A W", "N A   A N", "WNNW WNNW")
            .fixedLayer('D', "WNW   WNW", "N A   A N", "W A A A W", "N A   A N", "WNW   WNW")
            .fixedLayer('E', "WNNW WNNW", "N A   A N", "W A A A W", "N A   A N", "WNNW WNNW")
            .fixedLayer('F', "WNNW WNNW", "N K   K N", "N A A A N", "N N A N N", "WNNW WNNW")
            .fixedLayer('G', "WWWWWWWWW", "WNNNNNNNW", "WNNNNNNNW", "WNNNNNNNW", "WWWWWWWWW")
            .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31059, MultiTileEntityMultiBlockPart.ONLY_ENERGY)))
            .where('N', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31063)))
            .where('K', new ComputePartPredicate())
            .where('A', new AirPredicate())
            .setOffset(-2, 0, 0);

    public BiologyComputeControllerT3() {
        super(16777216L, 4194304L, 4);
    }

    @Override public IStringBaseStructure getStructure() { return STRUCTURE; }
    @Override public String getTileEntityName() { return "ktfru.multitileentity.compute.biology.t3"; }
}
