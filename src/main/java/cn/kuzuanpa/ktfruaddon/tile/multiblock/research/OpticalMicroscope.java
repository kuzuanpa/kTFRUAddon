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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.research;

import cn.kuzuanpa.ktfruaddon.api.research.task.IResearchTask;
import cn.kuzuanpa.ktfruaddon.api.research.task.ItemConsumeTaskScope;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.BlockPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import net.minecraft.init.Blocks;

public class OpticalMicroscope extends MultiResearchItemScopeBase {
    private static final IStringBaseStructure STRUCTURE = new LayerStructure(StructureContext.Axis.Y).layerRule("ABC")
            .fixedLayer('A',
                    " C",
                    "CC"
            ).fixedLayer('B',
                    "GG",
                    "CC"
            ).fixedLayer('C',
                    "CC",
                    "CC"
            )
            .where('C', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18002, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID_ENERGY_IN)))
            .where('G', new BlockPredicate(Blocks.glass))
            .setOffset(0, 0, 0);

    public OpticalMicroscope() {
        mEnergyPerItem = 256;
        mEnergyCapacity = 16384;
        mInputMin = 16;
        mInputRecommended = 128;
        mInputMax = 512;
    }

    @Override
    protected Class<? extends IResearchTask> getAcceptedTaskType() {
        return ItemConsumeTaskScope.class;
    }

    @Override
    protected IStringBaseStructure getStructure() {
        return STRUCTURE;
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.research.optical_microscope";
    }
}
