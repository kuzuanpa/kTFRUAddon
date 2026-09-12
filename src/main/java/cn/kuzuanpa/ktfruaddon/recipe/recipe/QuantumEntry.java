/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 */
package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.api.material.matList;
import cn.kuzuanpa.ktfruaddon.api.material.prefix.prefixList;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.FL;
import gregapi.data.IL;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.data.CS;
import gregapi.util.ST;

/**
 * Q1-R02 through Q1-R06.  R01 is deliberately implemented by the paired
 * quantum relay: observing a live entanglement channel is what discovers
 * Mirror Gold, rather than letting an ordinary furnace create it first.
 */
public final class QuantumEntry {
    private QuantumEntry() { }

    public static void init() {
        MultiTileEntityRegistry registry = MultiTileEntityRegistry.getRegistry("ktfru.multitileentity");

        // Q1-R02: an expensive, low-yield reconstruction of the discovered sample.
        recipeMaps.MirrorGoldSynthesis.addRecipeX(CS.F, 8192, 2400,
                ST.array(prefixList.AnalyticalPureDust.mat(matList.Ij.mat, 4),
                        prefixList.AnalyticalPureDust.mat(MT.Nq_522, 4),
                        prefixList.CommercialPureDust.mat(MT.Pt, 4),
                        OP.nugget.mat(matList.Ij.mat, 8)),
                CS.ZL_FS, CS.ZL_FS, OP.ingot.mat(matList.MirrorGold.mat, 1));

        // Q1-R03: production-grade Mirror Gold plates have an absolute-purity hard gate.
        recipeMaps.MirrorGoldSynthesis.addRecipeX(CS.F, 32768, 4800,
                ST.array(prefixList.AbsolutelyPureDust.mat(matList.Ij.mat, 8),
                        prefixList.AbsolutelyPureDust.mat(MT.Nq_522, 4),
                        prefixList.AbsolutelyPureDust.mat(MT.Pt, 4),
                        OP.nugget.mat(matList.Ij.mat, 16),
                        OP.ingot.mat(matList.MirrorGold.mat, 1)),
                CS.ZL_FS, CS.ZL_FS, OP.plate.mat(matList.MirrorGold.mat, 2));

        // Q1-R04: analytical purity is sufficient for a first substrate; the
        // absolute-purity Ij core is reserved for the coherence-critical parts.
        recipeMaps.Assembler.addRecipeX(CS.F, 4096, 1200,
                ST.array(prefixList.AnalyticalPureDust.mat(MT.Pt, 4),
                        OP.foil.mat(matList.MirrorGold.mat, 4),
                        OP.wireFine.mat(MT.Naquadah, 16),
                        IL.Circuit_Elite.get(1)),
                FL.array(MT.SolderingAlloy.liquid(CS.U2, CS.F)), CS.ZL_FS,
                registry.getItem(31112));

        // Q1-R05: the chamber wall contains the first non-negotiable absolute-purity Ij core.
        recipeMaps.CNC.addRecipeX(CS.F, 8192, 1600,
                ST.array(OP.plate.mat(matList.MirrorGold.mat, 2), registry.getItem(31112),
                        prefixList.AbsolutelyPureDust.mat(matList.Ij.mat, 2),
                        OP.plateDouble.mat(MT.Ir, 1)),
                FL.array(FL.DistW.make(1000)), CS.ZL_FS, registry.getItem(31111));

        // Q1-R06 components.  The relay itself remains buildable before the
        // discovery event; these are the formal, stable-channel upgrades that
        // consume its newly discovered material and make QU operation durable.
        recipeMaps.Assembler.addRecipeX(CS.F, 8192, 2400,
                ST.array(OP.plate.mat(matList.MirrorGold.mat, 1),
                        prefixList.AbsolutelyPureDust.mat(matList.Ij.mat, 4),
                        IL.Emitter_LuV.get(1), IL.Circuit_Ultimate.get(1)),
                FL.array(MT.SolderingAlloy.liquid(CS.U4, CS.F)), CS.ZL_FS,
                registry.getItem(31113));
        recipeMaps.Assembler.addRecipeX(CS.F, 4096, 1600,
                ST.array(OP.foil.mat(matList.MirrorGold.mat, 2),
                        prefixList.AnalyticalPureDust.mat(matList.Ij.mat, 2),
                        OP.wireFine.mat(MT.Naquadah, 16), IL.Circuit_Elite.get(1)),
                FL.array(MT.SolderingAlloy.liquid(CS.U2, CS.F)), CS.ZL_FS,
                registry.getItem(31114));

        // Q2-R01: quantumization is a hard absolute-purity gate; this is the
        // first process that turns preserved coherence into a driven degree of freedom.
        recipeMaps.MirrorGoldSynthesis.addRecipeX(CS.F, 16384, 2400,
                ST.array(OP.plate.mat(matList.MirrorGold.mat, 1),
                        prefixList.AbsolutelyPureDust.mat(matList.Ij.mat, 8),
                        prefixList.AbsolutelyPureDust.mat(MT.Nq_522, 4)),
                CS.ZL_FS, CS.ZL_FS, OP.plate.mat(matList.QuantumGold.mat, 1));

        // Q2-R02: Naquadria supplies the anomalous nuclear precursor while
        // absolute-purity Intellite selects the macroscopic quantum lattice.
        recipeMaps.MirrorGoldSynthesis.addRecipeX(CS.F, 65536, 4800,
                ST.array(OP.plate.mat(matList.QuantumGold.mat, 1),
                        OP.dustTiny.mat(MT.Naquadria, 8),
                        OP.nugget.mat(matList.Ij.mat, 16),
                        prefixList.AbsolutelyPureDust.mat(matList.Ij.mat, 8)),
                CS.ZL_FS, CS.ZL_FS, OP.ingot.mat(matList.QuantumGold.mat, 1));

        // Q2-R03/Q2-R04.  The required pure inputs are all covered by
        // PurityRefining: Ij and Nq_522 have commercial -> analytical -> absolute routes.
        recipeMaps.Assembler.addRecipeX(CS.F, 32768, 3200,
                ST.array(OP.plate.mat(matList.QuantumGold.mat, 2),
                        prefixList.AbsolutelyPureDust.mat(matList.Ij.mat, 4),
                        OP.foil.mat(matList.MirrorGold.mat, 4),
                        IL.Emitter_LuV.get(2), IL.Circuit_Ultimate.get(1)),
                FL.array(MT.SolderingAlloy.liquid(CS.U4, CS.F)), CS.ZL_FS,
                registry.getItem(31115));
        recipeMaps.Assembler.addRecipeX(CS.F, 32768, 3200,
                ST.array(OP.plate.mat(matList.QuantumGold.mat, 1),
                        prefixList.AbsolutelyPureDust.mat(MT.Nq_522, 4),
                        registry.getItem(31112), OP.wireFine.mat(MT.Naquadria, 16),
                        IL.Circuit_Ultimate.get(1)),
                FL.array(MT.SolderingAlloy.liquid(CS.U4, CS.F)), CS.ZL_FS,
                registry.getItem(31116));
    }
}
