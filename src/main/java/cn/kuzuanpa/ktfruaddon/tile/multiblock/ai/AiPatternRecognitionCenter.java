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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.ai;

import cn.kuzuanpa.ktfruaddon.api.research.task.IResearchTask;
import cn.kuzuanpa.ktfruaddon.api.research.task.minigame.MiniGameIdentifyTask;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;

/** AI replacement for the Identify game. */
public class AiPatternRecognitionCenter extends AiResearchControllerBase {
    private static final IStringBaseStructure STRUCTURE = new LayerStructure(StructureContext.Axis.Y).layerRule("ABCDE")
            .fixedLayer('A', "WWWWW", "WWWWW", " WWWW", "WWWWW", "WWWWW")
            .fixedLayer('B', "WWWWW", "W M W", "W   W", "W M W", "WWWWW")
            .fixedLayer('C', "WWWWW", "W R W", "W M W", "W R W", "WWWWW")
            .fixedLayer('D', "WWWWW", "W M W", "W R W", "W M W", "WWWWW")
            .fixedLayer('E', "WWWWW", "WWWWW", "WWWWW", "WWWWW", "WWWWW")
            .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31091)))
            .where('M', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31092)))
            .where('R', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31095)))
            .setOffset(-2, 0, 0);

    @Override public IStringBaseStructure getStructure() {return STRUCTURE;}
    @Override protected Class<? extends IResearchTask> getTaskType() {return MiniGameIdentifyTask.class;}
    @Override protected long getPointsPerTick() {return 2L;}
    @Override protected long getComputePerPoint() {return AI_COMPUTE_PER_TICK / 2L;}
    @Override public String getTileEntityName() {return "ktfru.multitileentity.ai.identify";}
}
