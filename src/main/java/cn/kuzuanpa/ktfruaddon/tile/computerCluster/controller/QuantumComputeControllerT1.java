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

/** Experimental coherent-ring quantum computer. */
public class QuantumComputeControllerT1 extends QuantumComputeController {
    private static final IStringBaseStructure STRUCTURE = new LayerStructure(StructureContext.Axis.Y).layerRule("ABCDE")
            .fixedLayer('A', "  WWW  ", " WWWWW ", "WW   WW", "WW   WW", "WW   WW", " WWWWW ", "  WWW  ")
            .fixedLayer('B', "  WLW  ", " W   W ", "W     W", "W  Q  W", "W  K  W", " W   W ", "  WRW  ")
            .fixedLayer('C', "  WWW  ", " W A W ", "WW A WW", "W AQA W", "WW A WW", " W A W ", "  WWW  ")
            .fixedLayer('D', "  WLW  ", " W   W ", "W     W", "W  Q  W", "W     W", " W   W ", "  WRW  ")
            .fixedLayer('E', "  WWW  ", " WWWWW ", "WW   WW", "WW Q WW", "WW   WW", " WWWWW ", "  WWW  ")
            .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31065, MultiTileEntityMultiBlockPart.ONLY_ENERGY)))
            .where('Q', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31066)))
            .where('L', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31067)))
            .where('R', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31068)))
            .where('K', new ComputePartPredicate())
            .where('A', new AirPredicate())
            .setOffset(-3, 0, 0);

    public QuantumComputeControllerT1() { super(16777216L, 65536L, 1); }
    @Override public IStringBaseStructure getStructure() { return STRUCTURE; }
    @Override public String getTileEntityName() { return "ktfru.multitileentity.compute.quantum.t1"; }
}
