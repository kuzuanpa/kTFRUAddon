/* Part of kTFRUAddon. Distributed under the AGPLv3. */
package cn.kuzuanpa.ktfruaddon.tile.multiblock.machine.laser;

import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.AirPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;

/**
 * 11x11x11 industrial chamber. The repeated middle layer represents the
 * double-shell body; it requires 24 arrays, eight stores and four collectors.
 */
public final class LaserFusionIndustrial extends LaserFusionControllerBase {
    private static final IStringBaseStructure STRUCTURE = new LayerStructure(StructureContext.Axis.Y).layerRule("0123456789A")
            .fixedLayer('0', "     S     ", "   PPPPP   ", "  PPPPPPP  ", " PPPPPPPPP ", " PPPPPPPPP ", "PPPPP PPPPP", " PPPPPPPPP ", " PPPPPPPPP ", "  PPPPPPP  ", "   PPPPP   ", "     P     ")
            .fixedLayer('1', "   PPSPP   ", " PP     PP ", " P       P ", "P   L L   P", "P   L L   P", "P  I   I  P", "P   L L   P", "P   L L   P", " P       P ", " PP     PP ", "   PPSPP   ")
            .fixedLayer('2', "  PPPSPPP  ", " P       P ", "P   L L   P", "P  L   L  P", "P         P", "P L   A L P", "P         P", "P  L   L  P", "P   L L   P", " P       P ", "  PPPSPPP  ")
            .fixedLayer('3', " PPPPPPPPP ", "P   L L   P", "P L     L P", "P    A    P", "P L  A  L P", "P    A    P", "P L  A  L P", "P    A    P", "P L     L P", "P   L L   P", " PPPPPPPPP ")
            .fixedLayer('4', " PPPPPPPPP ", "P  L   L  P", "P         P", "P   A A   P", "P   A A   P", "P A A A A P", "P   A A   P", "P   A A   P", "P         P", "P  L   L  P", " PPPPPPPPP ")
            .fixedLayer('5', "PPPPP PPPPP", "P         P", "P L  A  L P", "P   A A   P", "P  A   A  P", "  A  C  A  ", "P  A   A  P", "P   A A   P", "P L  A  L P", "P         P", "PPPPP PPPPP")
            .fixedLayer('6', " PPPPPPPPP ", "P  L   L  P", "P         P", "P   A A   P", "P   A A   P", "P A A A A P", "P   A A   P", "P   A A   P", "P         P", "P  L   L  P", " PPPPPPPPP ")
            .fixedLayer('7', " PPPPPPPPP ", "P   L L   P", "P L     L P", "P    A    P", "P L  A  L P", "P    A    P", "P L  A  L P", "P    A    P", "P L     L P", "P   L L   P", " PPPPPPPPP ")
            .fixedLayer('8', "  PPPPPPP  ", " P       P ", "P   L L   P", "P  L   L  P", "P         P", "P L   C L P", "P         P", "P  L   L  P", "P   L L   P", " P       P ", "  PPPPPPP  ")
            .fixedLayer('9', "   PPSPP   ", " PP     PP ", " P       P ", "P   L L   P", "P   L L   P", "P  C   C  P", "P   L L   P", "P   L L   P", " P       P ", " PP     PP ", "   PPSPP   ")
            .fixedLayer('A', "     S     ", "   PPPPP   ", "  PPPPPPP  ", " PPPPPPPPP ", " PPPPPPPPP ", "PPPPP PPPPP", " PPPPPPPPP ", " PPPPPPPPP ", "  PPPPPPP  ", "   PPPPP   ", "     P     ")
            .where('P', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18002)))
            .where('S', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31062)))
            .where('L', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31060, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN)))
            .where('I', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31061, MultiTileEntityMultiBlockPart.ONLY_ITEM_IN)))
            .where('A', new AirPredicate())
            .where('C', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31063, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID_OUT)))
            .setOffset(-5, 0, -5);

    @Override protected IStringBaseStructure getStructure() { return STRUCTURE; }
    @Override protected int laserArrayCount() { return 72; }
    @Override protected int pulseStorageCount() { return 8; }
    @Override protected long pulseCapacityPerStorage() { return 134217728L; }
    @Override protected long peakEnergyPerLaserArray() { return 41943040L; }
    @Override protected long maxPulseInputPacket() { return 2097152L; }
    @Override protected int cooldownTicks() { return 20 * 4; }
    @Override protected String controllerName() { return "ktfru.multitileentity.multiblock.laser_fusion.industrial"; }
    @Override protected String texturePath() { return "machines/fusion/laser/industrial"; }
}
