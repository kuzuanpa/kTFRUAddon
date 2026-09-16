/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 */
package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import cn.kuzuanpa.ktfruaddon.api.material.matList;
import cn.kuzuanpa.ktfruaddon.api.material.prefix.prefixList;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.data.RM;
import gregapi.oredict.OreDictMaterial;
import gregapi.util.ST;
import net.minecraftforge.fluids.FluidStack;

import static gregapi.data.CS.*;

/**
 * PUR-R01 through PUR-R03.  Commercial and analytical refinement retain the
 * chemistry of the material family; only the final atomic selection step is
 * deliberately universal and belongs in the particle collider.
 */
public final class PurityRefining {
    private PurityRefining() { }

    public static void init() {
        // Common and interconnect metals: acid leach, then aqueous centrifugation.
        registerAcidMetals(MT.Fe, MT.Ni, MT.Co, MT.Cu, MT.Zn, MT.Ag, MT.Sn, MT.Pb, MT.Bi);
        // Oxophilic / valve metals: alkaline leach and chloride re-distillation.
        registerValveMetals(MT.Al, MT.Ti, MT.Zr, MT.Nb, MT.Ta, MT.Cr);
        // Noble metals: chloride complexation followed by selective reduction.
        registerNobleMetals(MT.Au, MT.Pt, MT.Pd, MT.Rh, MT.Ir, MT.Os, matList.Ij.mat);
        // Refractory metal: hydrogen reduction followed by vacuum-grade degassing.
        registerRefractoryMetal(MT.W);
        // Semiconductor precursors: volatile-halide purification and zone-refining equivalent.
        registerSemiconductors(MT.Si, MT.Ge, MT.Ga, MT.In);
        // Nuclear feedstocks: nitric dissolution / ion separation; no ores or isotopes are created.
        registerNuclearMaterials(MT.U_238, MT.Th, MT.Nq_522);
    }

    private static void registerAcidMetals(OreDictMaterial... materials) {
        for (OreDictMaterial material : materials) {
            commercialBath(material, MT.H2SO4.liquid(U4, T), 128, 400);
            analyticalCentrifuge(material, FL.DistW.make(1000), 512, 800);
            absolutelyPure(material);
        }
    }

    private static void registerValveMetals(OreDictMaterial... materials) {
        for (OreDictMaterial material : materials) {
            commercialBath(material, MT.NaOH.liquid(U4, T), 256, 600);
            analyticalHeatRefine(material, MT.Cl.gas(U4, T), 1024, 1200);
            absolutelyPure(material);
        }
    }

    private static void registerNobleMetals(OreDictMaterial... materials) {
        for (OreDictMaterial material : materials) {
            commercialBath(material, MT.AquaRegia.liquid(U2, T), 512, 800);
            analyticalHeatRefine(material, MT.H.gas(U4, T), 2048, 1600);
            absolutelyPure(material);
        }
    }

    private static void registerRefractoryMetal(OreDictMaterial material) {
        recipeMaps.HeatMixer.addRecipeX(F, 1024, 1200,
                ST.array(OP.dust.mat(material, 4)), FL.array(MT.H.gas(U4, T)),
                FL.array(FL.Water.make(1000)), prefixList.CommercialPureDust.mat(material, 3));
        analyticalHeatRefine(material, MT.Ar.gas(U4, T), 4096, 2400);
        absolutelyPure(material);
    }

    private static void registerSemiconductors(OreDictMaterial... materials) {
        for (OreDictMaterial material : materials) {
            recipeMaps.CVD.addRecipeX(F, 512, 800,
                    ST.array(OP.dust.mat(material, 4)), FL.array(MT.HCl.gas(U4, T)),
                    FL.array(FL.Water.make(1000)), prefixList.CommercialPureDust.mat(material, 3));
            analyticalHeatRefine(material, MT.Ar.gas(U4, T), 2048, 1600);
            absolutelyPure(material);
        }
    }

    private static void registerNuclearMaterials(OreDictMaterial... materials) {
        for (OreDictMaterial material : materials) {
            commercialBath(material, MT.HNO3.liquid(U2, T), 1024, 1200);
            analyticalCentrifuge(material, FL.DistW.make(2000), 4096, 2400);
            absolutelyPure(material);
        }
    }

    /** Fixed 4 -> 3 yield: dissolution and removal of bulk contaminants. */
    private static void commercialBath(OreDictMaterial material, FluidStack reagent, long eut, long duration) {
        RM.Bath.addRecipeX(F, eut, duration, ST.array(OP.dust.mat(material, 4)),
                FL.array(reagent), FL.array(FL.Water.make(1000)), prefixList.CommercialPureDust.mat(material, 3));
    }

    /** Fixed 4 -> 3 yield: the analytical stage deliberately uses purified water. */
    private static void analyticalCentrifuge(OreDictMaterial material, FluidStack reagent, long eut, long duration) {
        RM.Centrifuge.addRecipeX(F, eut, duration, ST.array(prefixList.CommercialPureDust.mat(material, 4)),
                FL.array(reagent), FL.array(FL.Water.make(1000)), prefixList.AnalyticalPureDust.mat(material, 3));
    }

    private static void analyticalHeatRefine(OreDictMaterial material, FluidStack reagent, long eut, long duration) {
        recipeMaps.HeatMixer.addRecipeX(F, eut, duration,
                ST.array(prefixList.CommercialPureDust.mat(material, 4)), FL.array(reagent),
                FL.array(FL.Water.make(1000)), prefixList.AnalyticalPureDust.mat(material, 3));
    }

    /**
     * The collider resolves and selects individual atoms, so this final stage
     * is shared by every material which has a registered chemical route.
     */
    private static void absolutelyPure(OreDictMaterial material) {
        recipeMaps.LaserFusion.addRecipeX(F, 32768, 1200,
                ST.array(prefixList.AnalyticalPureDust.mat(material, 4), ItemList.QuantumControlElement.get(1)),
                FL.array(MT.Xe.gas(U4, T)), FL.array(), prefixList.AbsolutelyPureDust.mat(material, 1))
                .setSpecialNumber(10000000L);
    }
}
