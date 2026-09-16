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

public class BiologyComputeControllerT1 extends BiologyComputeController {
    private static final IStringBaseStructure STRUCTURE = new LayerStructure(StructureContext.Axis.Y).layerRule("ABCD")
            .fixedLayer('A', "WWWWW", "W A W", "WWWWW")
            .fixedLayer('B', "W N W", "W A W", "W K W")
            .fixedLayer('C', "W N W", "W A W", "WWWWW")
            .fixedLayer('D', "WWWWW", "WNNNW", "WWWWW")
            .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31057, MultiTileEntityMultiBlockPart.ONLY_ENERGY)))
            .where('N', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31131)))
            .where('K', new ComputePartPredicate())
            .where('A', new AirPredicate())
            .setOffset(-2, 0, 0);

    public BiologyComputeControllerT1() {
        super(262144L, 65536L, 1);
    }

    @Override public IStringBaseStructure getStructure() { return STRUCTURE; }
    @Override public String getTileEntityName() { return "ktfru.multitileentity.compute.biology.t1"; }
}
