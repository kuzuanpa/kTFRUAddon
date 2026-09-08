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

public class BiologyComputeControllerT4 extends BiologyComputeController {
    private static final IStringBaseStructure STRUCTURE = new LayerStructure(StructureContext.Axis.Y).layerRule("ABCDEFGH")
            .fixedLayer('A', "WWWWWWWWWWW", "WWW     WWW", "WW       WW", "W         W", "WWWWWWWWWWW")
            .fixedLayer('B', "WNNNW WNNNW", "N A     A N", "N A AAA A N", "N K AAA K N", "WNNNW WNNNW")
            .fixedLayer('C', "WNWNW WNWNW", "N A     A N", "W A AAA A W", "N A AAA A N", "WNWNW WNWNW")
            .fixedLayer('D', "WNWNW WNWNW", "N A     A N", "W A AAA A W", "N A AAA A N", "WNWNW WNWNW")
            .fixedLayer('E', "WNWNW WNWNW", "N A     A N", "W A AAA A W", "N A AAA A N", "WNWNW WNWNW")
            .fixedLayer('F', "WNWNW WNWNW", "N A     A N", "W A AAA A W", "N A AAA A N", "WNWNW WNWNW")
            .fixedLayer('G', "WNNNW WNNNW", "N K     K N", "N K AAA K N", "N N AAA N N", "WNNNW WNNNW")
            .fixedLayer('H', "WWWWWWWWWWW", "WNNNNNNNNNW", "WNNNNNNNNNW", "WNNNNNNNNNW", "WWWWWWWWWWW")
            .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31060, MultiTileEntityMultiBlockPart.ONLY_ENERGY)))
            .where('N', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31064)))
            .where('K', new ComputePartPredicate())
            .where('A', new AirPredicate())
            .setOffset(-2, 0, 0);

    public BiologyComputeControllerT4() {
        super(67108864L, 16777216L, 8);
    }

    @Override public IStringBaseStructure getStructure() { return STRUCTURE; }
    @Override public String getTileEntityName() { return "ktfru.multitileentity.compute.biology.t4"; }
}
