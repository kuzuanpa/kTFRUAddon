/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */


package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import cn.kuzuanpa.ktfruaddon.api.material.matList;
import cn.kuzuanpa.ktfruaddon.api.material.prefix.prefixList;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import gregapi.data.FL;
import gregapi.data.IL;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.util.ST;

import static gregapi.data.CS.*;

public class Fusion {
    public static void init(){
        recipeMaps.FusionTokamak .addRecipe1(F, 2048,400, ST.tag(0), FL.array(MT.D .gas (U, T),MT.T.gas(U, T)), FL.array(MT.He.gas (23*U100, F), FL.MatterNeutral.make(10*U100) )  ).setSpecialNumber(1024L*1024L);
        recipeMaps.FusionTokamakExperimental.addRecipe1(F, 2048,400, new long[]{5000}, ST.tag(0),
                FL.array(MT.D.gas(U, T), MT.T.gas(U, T)), FL.array(MT.He.gas(23*U100, F), FL.MatterNeutral.make(10*U100)),
                ItemList.TokamakNeutronData.get(1)).setSpecialNumber(1024L*1024L);
        recipeMaps.FusionTokamakExperimental.addRecipe1(F, 2048,400, new long[]{5000}, ST.tag(0),
                FL.array(MT.D.gas(2*U, T)), FL.array(MT.He_3.gas(U100, F), FL.MatterNeutral.make(U100)),
                ItemList.TokamakPlasmaData.get(1)).setSpecialNumber(1024L*1024L);


        recipeMaps.FusionTokamak.addRecipe0(F, 8192, 400,
                FL.array(MT.D.gas(4 * U, T), MT.T.gas(4 * U, T)),
                FL.array(MT.He.gas(2 * U, F)), ItemList.Neutron.get(2)).setSpecialNumber(1024L * 1024L);
        recipeMaps.FusionTokamak.addRecipe0(F, 16384, 600,
                FL.array(MT.D.gas(2 * U, T), MT.He_3.gas(U, T)),
                FL.array(MT.He.gas(2 * U, F)), ItemList.Neutron.get(1)).setSpecialNumber(4L * 1024L * 1024L);

        recipeMaps.FusionTokamakExperimental.addRecipe0(F, 8192, 400, new long[]{5000},
                FL.array(MT.D.gas(4 * U, T), MT.T.gas(4 * U, T)),
                FL.array(MT.He.gas(2 * U, F)), ItemList.TokamakNeutronData.get(1)).setSpecialNumber(1024L * 1024L);
        recipeMaps.FusionTokamakExperimental.addRecipe0(F, 16384, 600, new long[]{7500},
                FL.array(MT.D.gas(2 * U, T), MT.He_3.gas(U, T)),
                FL.array(MT.He.gas(2 * U, F)), ItemList.TokamakPlasmaData.get(1)).setSpecialNumber(4L * 1024L * 1024L);

        // Laser fusion is intentionally a net energy sink. SpecialNumber is the
        // complete LU pulse needed by a single target, never a continuous EU/t cost.
        recipeMaps.LaserFusion.addRecipeX(F, 2048, 1, ST.array(ItemList.LaserTargetDT.get(1)),
                FL.array(MT.D.gas(4 * U, T)), FL.array(MT.He.gas(2 * U, F), MT.T.gas(U100, F)), ItemList.Neutron.get(4)).setSpecialNumber(10000000L);
        recipeMaps.LaserFusion.addRecipeX(F, 4096, 1, ST.array(ItemList.LaserTargetLi6.get(1)),
                FL.array(MT.D.gas(2 * U, T)), FL.array(MT.T.gas(2 * U, F), MT.He.gas(U100, F)), ItemList.Neutron.get(1)).setSpecialNumber(12000000L);
        recipeMaps.LaserFusionExperimental.addRecipeX(F, 2048, 1, new long[]{5000}, ST.array(ItemList.LaserTargetDT.get(1)),
                FL.array(MT.D.gas(4 * U, T)), FL.array(MT.He.gas(2 * U, F), MT.T.gas(U100, F)),
                ItemList.LaserFusionCompressionData.get(1)).setSpecialNumber(10000000L);
        recipeMaps.LaserFusionExperimental.addRecipeX(F, 4096, 1, new long[]{5000}, ST.array(ItemList.LaserTargetLi6.get(1)),
                FL.array(MT.D.gas(2 * U, T)), FL.array(MT.T.gas(2 * U, F), MT.He.gas(U100, F)),
                ItemList.LaserFusionNeutronData.get(1)).setSpecialNumber(12000000L);

        // R03: industrial irradiation produces artificial intermediates rather
        // than ordinary metals. They feed the dedicated nuclear-material chain.
        recipeMaps.LaserFusion.addRecipeX(F, 8192, 1, ST.array(ItemList.LaserTargetLead.get(1)),
                FL.array(MT.D.gas(4 * U, T)), FL.array(MT.He.gas(U100, F)), ItemList.NeutronRichBismuth.get(2)).setSpecialNumber(100000000L);
        recipeMaps.LaserFusion.addRecipeX(F, 8192, 1, ST.array(ItemList.LaserTargetTantalum.get(1)),
                FL.array(MT.D.gas(4 * U, T)), FL.array(MT.He.gas(U100, F)), ItemList.MetastableTantalum.get(2)).setSpecialNumber(120000000L);
        recipeMaps.LaserFusion.addRecipeX(F, 8192, 1, ST.array(ItemList.LaserTargetGraphite.get(1)),
                FL.array(MT.D.gas(4 * U, T)), FL.array(MT.He.gas(U100, F)), ItemList.DenseGraphenePrecursor.get(1)).setSpecialNumber(150000000L);

        // R04: 900M LU stays below the industrial chamber's 983,040,000 LU
        // peak while remaining exclusive to that chamber. The alpha particle is
        // a non-recoverable trigger, not a source of energy or common matter.
        recipeMaps.LaserFusion.addRecipeX(F, 32768, 1, ST.array(ItemList.LaserTargetBismuth.get(1), ItemList.Alpha_Particle.get(1)),
                FL.array(MT.D.gas(8 * U, T)), FL.array(MT.He.gas(U100, F)), ItemList.SuperheavyNuclidePrecursor.get(1)).setSpecialNumber(900000000L);

        // R03/R04 refinement loop. Each fusion product has an explicit, limited
        // downstream use; no recipe returns a target pellet, energy, or an ore.
        recipeMaps.NeutronAbsorption.addRecipe1(F, 8192, 600, ItemList.NeutronRichBismuth.get(2),
                FL.array(FL.MatterNeutral.make(U100)), ZL_FS, ItemList.NuclearTargetSubstrate.get(1));
        recipeMaps.Assembler.addRecipeX(F, 8192, 600,
                ST.array(ItemList.MetastableTantalum.get(2), OP.wireFine.mat(MT.Pt, 16), ItemList.ComputerUltimateCircuits.get(1)),
                FL.array(MT.SolderingAlloy.liquid(U4, F)), ZL_FS, ItemList.QuantumControlElement.get(1));
        recipeMaps.CVD.addRecipeX(F, 8192, 1200, ST.array(ItemList.DenseGraphenePrecursor.get(1)),
                FL.array(MT.H.gas(4 * U, T)), ZL_FS, OP.foil.mat(MT.Graphene, 1));
        recipeMaps.Assembler.addRecipeX(F, 32768, 1200,
                ST.array(ItemList.SuperheavyNuclidePrecursor.get(1), ItemList.NuclearTargetSubstrate.get(1), ItemList.MetastableTantalum.get(1)),
                FL.array(MT.SolderingAlloy.liquid(U8, F)), ZL_FS, ItemList.NaquadriaPrecursor.get(1));
        // The final refinement is deliberately expensive and produces only a
        // tiny unit. It closes the chain into existing Naquadria-based recipes
        // without making the laser chamber a general-purpose matter source.
        recipeMaps.HeatMixer.addRecipeX(F, 32768, 2400,
                ST.array(ItemList.NaquadriaPrecursor.get(1), ItemList.QuantumControlElement.get(1)),
                FL.array(MT.He.gas(4 * U, T)), ZL_FS, OP.dustTiny.mat(MT.Nq_522, 1));

        // Target pellets and both chamber controllers are regular manufacturing
        // products; the reactor never creates its own fuel or structure parts.
        // Targets use analytical-pure feedstock so isotope purity is decided here.
        recipeMaps.Assembler.addRecipeX(F, 2048, 200,
                ST.array(prefixList.AnalyticalPureDust.mat(MT.Ti, 2),
                        prefixList.AnalyticalPureDust.mat(MT.D, 1),
                        prefixList.AnalyticalPureDust.mat(MT.T, 1)),
                ZL_FS, ZL_FS, ItemList.LaserTargetDT.get(1));
        recipeMaps.Assembler.addRecipeX(F, 2048, 200,
                ST.array(prefixList.AnalyticalPureDust.mat(MT.Li, 2),
                        prefixList.AnalyticalPureDust.mat(MT.D, 1)),
                ZL_FS, ZL_FS, ItemList.LaserTargetLi6.get(1));
        recipeMaps.Assembler.addRecipeX(F, 4096, 300,
                ST.array(prefixList.AnalyticalPureDust.mat(MT.Pb, 2),
                        prefixList.AnalyticalPureDust.mat(MT.D, 1)),
                ZL_FS, ZL_FS, ItemList.LaserTargetLead.get(1));
        recipeMaps.Assembler.addRecipeX(F, 4096, 300,
                ST.array(prefixList.AnalyticalPureDust.mat(MT.Ta, 2),
                        prefixList.AnalyticalPureDust.mat(MT.D, 1)),
                ZL_FS, ZL_FS, ItemList.LaserTargetTantalum.get(1));
        recipeMaps.Assembler.addRecipeX(F, 4096, 300,
                ST.array(prefixList.AnalyticalPureDust.mat(MT.C, 4),
                        prefixList.AnalyticalPureDust.mat(MT.D, 1)),
                ZL_FS, ZL_FS, ItemList.LaserTargetGraphite.get(1));
        recipeMaps.Assembler.addRecipeX(F, 8192, 600,
                ST.array(prefixList.AnalyticalPureDust.mat(MT.Bi, 2),
                        prefixList.AnalyticalPureDust.mat(MT.D, 2)),
                ZL_FS, ZL_FS, ItemList.LaserTargetBismuth.get(1));

        recipeMaps.Assembler.addRecipeX(F, 8192, 600, ST.array(OP.plateDense.mat(MT.TungstenSteel, 4), OP.plate.mat(MT.Glass, 8), OP.wireFine.mat(MT.Pt, 16)),
                FL.array(MT.SolderingAlloy.liquid(U4, F)), ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(31060));
        recipeMaps.Assembler.addRecipeX(F, 8192, 600, ST.array(OP.casingMachine.mat(MT.Ti, 2), OP.pipeSmall.mat(MT.Ti, 4), OP.plate.mat(MT.Ta, 2)),
                FL.array(MT.SolderingAlloy.liquid(U4, F)), ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(31061));
        recipeMaps.Assembler.addRecipeX(F, 32768, 1200, ST.array(OP.casingMachine.mat(MT.Os, 2), OP.plateDense.mat(MT.Os, 2), OP.wireFine.mat(MT.Pt, 32)),
                FL.array(MT.SolderingAlloy.liquid(U8, F)), ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(31062));
        recipeMaps.Assembler.addRecipeX(F, 8192, 600, ST.array(OP.plateDense.mat(MT.Pb, 4), OP.plate.mat(MT.TungstenSteel, 2), OP.foil.mat(MT.Li, 8)),
                FL.array(MT.SolderingAlloy.liquid(U4, F)), ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(31063));
        recipeMaps.ResearchAssembler.addRecipeX(F, 32768, 2400, ST.array(GTTileEntityRegistry.ktfruaddon.getItem(31060, 8), GTTileEntityRegistry.ktfruaddon.getItem(31061), GTTileEntityRegistry.ktfruaddon.getItem(31062, 2)),
                FL.array(MT.SolderingAlloy.liquid(U16, F)), ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(30071));
        recipeMaps.ResearchAssembler.addRecipeX(F, 131072, 4800, ST.array(GTTileEntityRegistry.ktfruaddon.getItem(31060, 24), GTTileEntityRegistry.ktfruaddon.getItem(31062, 8), GTTileEntityRegistry.ktfruaddon.getItem(31063, 4)),
                FL.array(MT.SolderingAlloy.liquid(U64, F)), ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(30072));

        // Tokamak controllers and their dedicated structural parts follow the
        // same assembly pattern as the laser-fusion chambers above.
        recipeMaps.Assembler.addRecipeX(F, 16384, 1200, ST.array(OP.plateDense.mat(MT.Ti, 4), OP.wireGt04.mat(MT.Cu, 8), OP.plate.mat(MT.StainlessSteel, 2), IL.Circuit_Elite.get(1)),
                FL.array(MT.SolderingAlloy.liquid(U4, F)), ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(31016, 2));
        recipeMaps.Assembler.addRecipeX(F, 32768, 1600, ST.array(OP.wireGt08.mat(MT.Cu, 16), OP.plateDense.mat(MT.StainlessSteel, 4), OP.plate.mat(matList.PEEK.mat, 2), IL.Circuit_Elite.get(1)),
                FL.array(MT.SolderingAlloy.liquid(U8, F)), ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(31017));
        recipeMaps.Assembler.addRecipeX(F, 32768, 1600, ST.array(OP.plateDense.mat(MT.StainlessSteel, 4), OP.plate.mat(MT.Ti, 4), OP.plate.mat(matList.PEEK.mat, 2), IL.Circuit_Elite.get(1)),
                FL.array(MT.SolderingAlloy.liquid(U8, F)), ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(31018, 2));
        recipeMaps.ResearchAssembler.addRecipeX(F, 65536, 3200, ST.array(OP.casingMachineDouble.mat(MT.StainlessSteel, 1), GTTileEntityRegistry.ktfruaddon.getItem(31016, 8), GTTileEntityRegistry.ktfruaddon.getItem(31017, 4), GTTileEntityRegistry.ktfruaddon.getItem(31018, 4), IL.Circuit_Elite.get(2), IL.SENSORS[4].get(2), ItemList.ComputerGT3660.get(1)),
                FL.array(MT.SolderingAlloy.liquid(U16, F)), ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(30014));

        recipeMaps.Assembler.addRecipeX(F, 65536, 1800, ST.array(OP.plateDense.mat(MT.TungstenSteel, 4), OP.wireGt04.mat(MT.Graphene, 8), OP.plate.mat(matList.PEEK.mat, 2), IL.Circuit_Master.get(1)),
                FL.array(MT.SolderingAlloy.liquid(U8, F)), ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(31025, 2));
        recipeMaps.Assembler.addRecipeX(F, 131072, 2400, ST.array(OP.wireGt08.mat(MT.Graphene, 16), OP.plateDense.mat(MT.TungstenSteel, 4), OP.plate.mat(matList.PEEK.mat, 4), IL.Circuit_Master.get(1)),
                FL.array(MT.SolderingAlloy.liquid(U16, F)), ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(31026));
        recipeMaps.Assembler.addRecipeX(F, 131072, 2400, ST.array(OP.plateDense.mat(MT.TungstenSteel, 4), OP.plate.mat(MT.Trinaquadalloy, 4), OP.plate.mat(matList.PEEK.mat, 4), IL.Circuit_Master.get(1)),
                FL.array(MT.SolderingAlloy.liquid(U16, F)), ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(31027, 2));
        recipeMaps.ResearchAssembler.addRecipeX(F, 262144, 4800, ST.array(OP.casingMachineDouble.mat(MT.TungstenSteel, 1), GTTileEntityRegistry.ktfruaddon.getItem(31025, 16), GTTileEntityRegistry.ktfruaddon.getItem(31026, 8), GTTileEntityRegistry.ktfruaddon.getItem(31027, 8), IL.Circuit_Master.get(4), IL.SENSORS[5].get(2), ItemList.ComputerGT3699.get(2)),
                FL.array(MT.SolderingAlloy.liquid(U32, F)), ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(30015));

        recipeMaps.NeutronAbsorption.addRecipe0(false, 160,1, MT.Li_6.liquid(U144, true), FL.array(MT.He.gas(U20, false), MT.D.gas(U200,false)));
        recipeMaps.NeutronAbsorption.addRecipe0(false, 160,1, MT.F.liquid(U1000, true), FL.array(MT.Ne.gas(U100, false)));
        recipeMaps.NeutronAbsorption.addRecipe0(false,  80,1, MT.Cl.liquid(U1000, true), FL.array(MT.Ar.gas(U100, false)));
        recipeMaps.NeutronAbsorption.addRecipe0(false,  80,1, MT.Br.liquid(U1000, true), FL.array(MT.Kr.gas(U100, false)));
        recipeMaps.NeutronAbsorption.addRecipe0(false,  80,1, MT.I.liquid(U1000, true), FL.array(MT.Xe.gas(U100, false)));
        recipeMaps.NeutronAbsorption.addRecipe0(false,  80,1, MT.Ra.liquid(U1000, true), FL.array(MT.Rn.gas(U100, false)));
    }
}
