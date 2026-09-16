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

/** A distributed fault-tolerant quantum dome with four independent correction wings. */
public class QuantumComputeControllerT4 extends QuantumComputeController {
    private static final IStringBaseStructure STRUCTURE = new LayerStructure(StructureContext.Axis.Y).layerRule("ABCDEFGHIJK")
            .fixedLayer('A', "      WWW      ", "   WWWWWWWWW   ", "  WWW     WWW  ", " WWW       WWW ", " WW         WW ", "WW           WW", "WW           WW", "WW           WW", " WW         WW ", " WWW       WWW ", "  WWW     WWW  ", "   WWWWWWWWW   ", "      WWW      ")
            .fixedLayer('B', "      WQW      ", "   WW     WW   ", "  W         W  ", " W           W ", "W K         K W", "W             W", "W             W", "W K           W", "W             W", " W           W ", "  W         W  ", "   WW     WW   ", "      WQW      ")
            .fixedLayer('C', "      WUW      ", "   W A A A W   ", "  W A     A W  ", " W A       A W ", "U A    F    A U", "W A   Q Q   A W", "U    Q   Q    U", "W A   Q Q   A W", "U A    F    A U", " W A       A W ", "  W A     A W  ", "   W A A A W   ", "      WUW      ")
            .fixedLayer('D', "      WUW      ", "   W A A A W   ", "  U A     A U  ", " W A   F   A W ", "U A  F Q F  A U", " W A Q   Q A W ", "U   Q Q Q Q   U", " W A Q   Q A W ", "U A  F Q F  A U", " W A   F   A W ", "  U A     A U  ", "   W A A A W   ", "      WUW      ")
            .fixedLayer('E', "      WUW      ", "   U A A A U   ", "  W A     A W  ", " U A   F   A U ", "W A  F Q F  A W", " U A Q   Q A U ", "W  Q QQQQQ Q  W", " U A Q   Q A U ", "W A  F Q F  A W", " U A   F   A U ", "  W A     A W  ", "   U A A A U   ", "      WUW      ")
            .fixedLayer('F', "      WUW      ", "   W A A A W   ", "  U A     A U  ", " W A   F   A W ", "U A  F Q F  A U", " W A Q   Q A W ", "U   Q QQQ Q   U", " W A Q   Q A W ", "U A  F Q F  A U", " W A   F   A W ", "  U A     A U  ", "   W A A A W   ", "      WUW      ")
            .fixedLayer('G', "      WUW      ", "   U A A A U   ", "  W A     A W  ", " U A   F   A U ", "W A  F Q F  A W", " U A Q   Q A U ", "W  Q QQQQQ Q  W", " U A Q   Q A U ", "W A  F Q F  A W", " U A   F   A U ", "  W A     A W  ", "   U A A A U   ", "      WUW      ")
            .fixedLayer('H', "      WUW      ", "   W A A A W   ", "  U A     A U  ", " W A   F   A W ", "U A  F Q F  A U", " W A Q   Q A W ", "U   Q Q Q Q   U", " W A Q   Q A W ", "U A  F Q F  A U", " W A   F   A W ", "  U A     A U  ", "   W A A A W   ", "      WUW      ")
            .fixedLayer('I', "      WUW      ", "   W A A A W   ", "  W A     A W  ", " W A       A W ", "U A    F    A U", "W A   Q Q   A W", "U    Q   Q    U", "W A   Q Q   A W", "U A    F    A U", " W A       A W ", "  W A     A W  ", "   W A A A W   ", "      WUW      ")
            .fixedLayer('J', "      WQW      ", "   WW     WW   ", "  W         W  ", " W           W ", "W             W", "W             W", "W             W", "W             W", "W             W", " W           W ", "  W         W  ", "   WW     WW   ", "      WQW      ")
            .fixedLayer('K', "      WWW      ", "   WWWWWWWWW   ", "  WWW     WWW  ", " WWW       WWW ", " WW         WW ", "WW           WW", "WW           WW", "WW           WW", " WW         WW ", " WWW       WWW ", "  WWW     WWW  ", "   WWWWWWWWW   ", "      WWW      ")
            .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31076, MultiTileEntityMultiBlockPart.ONLY_ENERGY)))
            .where('U', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31077)))
            .where('F', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31078)))
            .where('Q', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31079)))
            .where('K', new ComputePartPredicate())
            .where('A', new AirPredicate())
            .setOffset(-6, 0, 0);

    public QuantumComputeControllerT4() { super(8589934592L, 16777216L, 4); }
    @Override public IStringBaseStructure getStructure() { return STRUCTURE; }
    @Override public String getTileEntityName() { return "ktfru.multitileentity.compute.quantum.t4"; }
}
