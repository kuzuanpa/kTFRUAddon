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

public class BiologyComputeControllerT2 extends BiologyComputeController {
    private static final IStringBaseStructure STRUCTURE = new LayerStructure(StructureContext.Axis.Y).layerRule("ABCDEF")
            .fixedLayer('A', "WWWWWWW", "WW   WW", "W     W", "WW   WW", "WWWWWWW")
            .fixedLayer('B', "WNN NNW", "N A AN", "N A AN", "N K KN", "WNN NNW")
            .fixedLayer('C', "WNW WNW", "N A AN", "W A AW", "N A AN", "WNW WNW")
            .fixedLayer('D', "WNW WNW", "N A AN", "W A AW", "N A AN", "WNW WNW")
            .fixedLayer('E', "WNN NNW", "N A AN", "N A AN", "N N NN", "WNN NNW")
            .fixedLayer('F', "WWWWWWW", "WNNNNNW", "WNNNNNW", "WNNNNNW", "WWWWWWW")
            .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31621, MultiTileEntityMultiBlockPart.NOTHING)))
            .where('N', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31625)))
            .where('K', new ComputePartPredicate())
            .where('A', new AirPredicate())
            .setOffset(-3, 0, 0);

    public BiologyComputeControllerT2() {
        super(2097152L, 524288L, 2);
    }

    @Override public IStringBaseStructure getStructure() { return STRUCTURE; }
    @Override public String getTileEntityName() { return "ktfru.multitileentity.compute.biology.t2"; }
}
