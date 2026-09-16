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

/** 7x7x7 low-energy target chamber; eight symmetric beam interfaces. */
public final class LaserFusionExperimental extends LaserFusionControllerBase {
    private static final IStringBaseStructure STRUCTURE = new LayerStructure(StructureContext.Axis.Y).layerRule("0123456")
            .fixedLayer('0', "   S   ", "  PPP  ", " PPPPP ", "PPP PPP", " PPPPP ", "  PPP  ", "   P   ")
            .fixedLayer('1', "  PPP  ", " P   P ", "P  L  P", "P I I P", "P  L  P", " P   P ", "  PPP  ")
            .fixedLayer('2', " PPPPP ", "P     P", "P  L  P", "P  A  P", "P  L  P", "P     P", " PPPPP ")
            .fixedLayer('3', "PPP PPP", "P  L  P", "  LAL  ", "P A A P", "  LAL  ", "P  L  P", "PPP PPP")
            .fixedLayer('4', " PPPPP ", "P     P", "P  L  P", "P  C  P", "P  L  P", "P     P", " PPPPP ")
            .fixedLayer('5', "  PPP  ", " P   P ", "P  L  P", "P C C P", "P  L  P", " P   P ", "  PPP  ")
            .fixedLayer('6', "   S   ", "  PPP  ", " PPPPP ", "PPP PPP", " PPPPP ", "  PPP  ", "   P   ")
            .where('P', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18002, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN)))
            .where('S', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31062)))
            .where('L', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31060, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN)))
            .where('I', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31061, MultiTileEntityMultiBlockPart.ONLY_ITEM_IN)))
            .where('A', new AirPredicate())
            .where('C', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31063, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID_OUT)))
            .setOffset(-3, 0, -3);

    @Override protected IStringBaseStructure getStructure() { return STRUCTURE; }
    @Override protected int laserArrayCount() { return 14; }
    @Override protected int pulseStorageCount() { return 2; }
    @Override protected long pulseCapacityPerStorage() { return 8L * 1024L * 1024L; }
    @Override protected long peakEnergyPerLaserArray() { return 2L * 1024L * 1024L; }
    @Override protected long maxPulseInputPacket() { return 131072L; }
    @Override protected int cooldownTicks() { return 20 * 12; }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.laser_fusion.experimental";
    }

    @Override protected String texturePath() { return "machines/fusion/laser/experimental"; }
}
