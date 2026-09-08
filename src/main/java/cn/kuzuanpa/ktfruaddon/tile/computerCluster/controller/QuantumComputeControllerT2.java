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

/** Two offset coherence rings joined by four error-correction readout spines. */
public class QuantumComputeControllerT2 extends QuantumComputeController {
    private static final IStringBaseStructure STRUCTURE = new LayerStructure(StructureContext.Axis.Y).layerRule("ABCDEFG")
            .fixedLayer('A', "   WWW   ", " WWWWWWW ", " WW   WW ", "WW     WW", "WW     WW", "WW     WW", " WW   WW ", " WWWWWWW ", "   WWW   ")
            .fixedLayer('B', "   WQW   ", " WW   WW ", " W     W ", "W       W", "W K   K W", "W       W", " W     W ", " WW   WW ", "   WQW   ")
            .fixedLayer('C', "   WRW   ", " W A A W ", "W A   A W", "R   Q   R", "W A   A W", "R   Q   R", "W A   A W", " W A A W ", "   WRW   ")
            .fixedLayer('D', "   WRW   ", " W A A W ", "R A   A R", "W   QA  W", "W  QQQ  W", "W   QA  W", "R A   A R", " W A A W ", "   WRW   ")
            .fixedLayer('E', "   WRW   ", " W A A W ", "W A   A W", "R   Q   R", "W A   A W", "R   Q   R", "W A   A W", " W A A W ", "   WRW   ")
            .fixedLayer('F', "   WQW   ", " WW   WW ", " W     W ", "W       W", "W       W", "W       W", " W     W ", " WW   WW ", "   WQW   ")
            .fixedLayer('G', "   WWW   ", " WWWWWWW ", " WW   WW ", "WW     WW", "WW     WW", "WW     WW", " WW   WW ", " WWWWWWW ", "   WWW   ")
            .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31069, MultiTileEntityMultiBlockPart.ONLY_ENERGY)))
            .where('Q', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31070)))
            .where('R', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31071)))
            .where('K', new ComputePartPredicate())
            .where('A', new AirPredicate())
            .setOffset(-4, 0, 0);

    public QuantumComputeControllerT2() { super(134217728L, 262144L, 2); }
    @Override public IStringBaseStructure getStructure() { return STRUCTURE; }
    @Override public String getTileEntityName() { return "ktfru.multitileentity.compute.quantum.t2"; }
}
