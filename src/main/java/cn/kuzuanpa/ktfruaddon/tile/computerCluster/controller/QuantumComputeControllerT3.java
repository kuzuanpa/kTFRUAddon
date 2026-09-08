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

/** Interwoven topological loops make a fault-tolerant logical quantum processor. */
public class QuantumComputeControllerT3 extends QuantumComputeController {
    private static final IStringBaseStructure STRUCTURE = new LayerStructure(StructureContext.Axis.Y).layerRule("ABCDEFGHI")
            .fixedLayer('A', "    WWW    ", "  WWWWWWW  ", " WWW   WWW ", " WW     WW ", "WW       WW", "WW       WW", "WW       WW", " WW     WW ", " WWW   WWW ", "  WWWWWWW  ", "    WWW    ")
            .fixedLayer('B', "    WQW    ", "  WW   WW  ", " WW     WW ", " W       W ", "W K     K W", "W         W", "W K       W", " W       W ", " WW     WW ", "  WW   WW  ", "    WQW    ")
            .fixedLayer('C', "    WTW    ", "  W A A W  ", " W A   A W ", "W A  T  A W", "T   Q Q   T", "W A  Q  A W", "T   Q Q   T", "W A  T  A W", " W A   A W ", "  W A A W  ", "    WTW    ")
            .fixedLayer('D', "    WTW    ", "  W A A W  ", " T A   A T ", "W A  E  A W", "T  E Q E  T", "W A QQQ A W", "T  E Q E  T", "W A  E  A W", " T A   A T ", "  W A A W  ", "    WTW    ")
            .fixedLayer('E', "    WTW    ", "  T A A T  ", " W A   A W ", "T A  E  A T", "W  E Q E  W", "T A QQQ A T", "W  E Q E  W", "T A  E  A T", " W A   A W ", "  T A A T  ", "    WTW    ")
            .fixedLayer('F', "    WTW    ", "  W A A W  ", " T A   A T ", "W A  E  A W", "T  E Q E  T", "W A QQQ A W", "T  E Q E  T", "W A  E  A W", " T A   A T ", "  W A A W  ", "    WTW    ")
            .fixedLayer('G', "    WTW    ", "  W A A W  ", " W A   A W ", "W A  T  A W", "T   Q Q   T", "W A  Q  A W", "T   Q Q   T", "W A  T  A W", " W A   A W ", "  W A A W  ", "    WTW    ")
            .fixedLayer('H', "    WQW    ", "  WW   WW  ", " WW     WW ", " W       W ", "W         W", "W         W", "W         W", " W       W ", " WW     WW ", "  WW   WW  ", "    WQW    ")
            .fixedLayer('I', "    WWW    ", "  WWWWWWW  ", " WWW   WWW ", " WW     WW ", "WW       WW", "WW       WW", "WW       WW", " WW     WW ", " WWW   WWW ", "  WWWWWWW  ", "    WWW    ")
            .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31072, MultiTileEntityMultiBlockPart.ONLY_ENERGY)))
            .where('T', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31073)))
            .where('E', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31074)))
            .where('Q', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31075)))
            .where('K', new ComputePartPredicate())
            .where('A', new AirPredicate())
            .setOffset(-5, 0, 0);

    public QuantumComputeControllerT3() { super(1073741824L, 1048576L, 3); }
    @Override public IStringBaseStructure getStructure() { return STRUCTURE; }
    @Override public String getTileEntityName() { return "ktfru.multitileentity.compute.quantum.t3"; }
}
