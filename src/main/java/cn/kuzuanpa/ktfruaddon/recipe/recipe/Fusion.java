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
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.util.ST;

import static gregapi.data.CS.*;

public class Fusion {
    public static void init(){
        recipeMaps.FusionTokamak .addRecipe1(F, 2048,400, ST.tag(0), FL.array(MT.D .gas (U, T),MT.T.gas(U, T)), FL.array(MT.He.gas (23*U100, F), MT.n.gas (10*U100, F) )  ).setSpecialNumber(1024L*1024L);

        // Laser fusion is intentionally a net energy sink. SpecialNumber is the
        // complete LU pulse needed by a single target, never a continuous EU/t cost.
        recipeMaps.LaserFusion.addRecipeX(F, 2048, 1, ST.array(ItemList.LaserTargetDT.get(1)),
                FL.array(MT.D.gas(4 * U, T)), FL.array(MT.He.gas(2 * U, F), MT.T.gas(U100, F)), ItemList.Neutron.get(4)).setSpecialNumber(10000000L);
        recipeMaps.LaserFusion.addRecipeX(F, 4096, 1, ST.array(ItemList.LaserTargetLi6.get(1)),
                FL.array(MT.D.gas(2 * U, T)), FL.array(MT.T.gas(2 * U, F), MT.He.gas(U100, F)), ItemList.Neutron.get(1)).setSpecialNumber(12000000L);

        recipeMaps.LaserFusion.addRecipeX(F, 8192, 1, ST.array(ItemList.LaserTargetLead.get(1)),
                FL.array(MT.D.gas(4 * U, T)), FL.array(MT.He.gas(U100, F)), OP.dustTiny.mat(MT.Bi, 2)).setSpecialNumber(100000000L);
        recipeMaps.LaserFusion.addRecipeX(F, 8192, 1, ST.array(ItemList.LaserTargetTantalum.get(1)),
                FL.array(MT.D.gas(4 * U, T)), FL.array(MT.He.gas(U100, F)), OP.dustTiny.mat(MT.W, 2)).setSpecialNumber(120000000L);
        recipeMaps.LaserFusion.addRecipeX(F, 8192, 1, ST.array(ItemList.LaserTargetGraphite.get(1)),
                FL.array(MT.D.gas(4 * U, T)), FL.array(MT.He.gas(U100, F)), OP.dustTiny.mat(MT.Graphene, 1)).setSpecialNumber(150000000L);
        recipeMaps.LaserFusion.addRecipeX(F, 32768, 1, ST.array(ItemList.LaserTargetBismuth.get(1)),
                FL.array(MT.D.gas(8 * U, T)), FL.array(MT.He.gas(U100, F)), OP.dustTiny.mat(MT.Nq, 1)).setSpecialNumber(1000000000L);

        // Target pellets and both chamber controllers are regular manufacturing
        // products; the reactor never creates its own fuel or structure parts.
        recipeMaps.Assembler.addRecipeX(F, 2048, 200, ST.array(OP.plateTiny.mat(MT.Ti, 2)),
                FL.array(MT.D.gas(U, T), MT.T.gas(U, T)), ZL_FS, ItemList.LaserTargetDT.get(1));
        recipeMaps.Assembler.addRecipeX(F, 2048, 200, ST.array(OP.foil.mat(MT.Li, 2)),
                FL.array(MT.D.gas(U, T)), ZL_FS, ItemList.LaserTargetLi6.get(1));
        recipeMaps.Assembler.addRecipeX(F, 4096, 300, ST.array(OP.plateTiny.mat(MT.Pb, 2)),
                FL.array(MT.D.gas(U, T)), ZL_FS, ItemList.LaserTargetLead.get(1));
        recipeMaps.Assembler.addRecipeX(F, 4096, 300, ST.array(OP.foil.mat(MT.Ta, 2)),
                FL.array(MT.D.gas(U, T)), ZL_FS, ItemList.LaserTargetTantalum.get(1));
        recipeMaps.Assembler.addRecipeX(F, 4096, 300, ST.array(OP.foil.mat(MT.C, 4)),
                FL.array(MT.D.gas(U, T)), ZL_FS, ItemList.LaserTargetGraphite.get(1));
        recipeMaps.Assembler.addRecipeX(F, 8192, 600, ST.array(OP.plateTiny.mat(MT.Bi, 2)),
                FL.array(MT.D.gas(2 * U, T)), ZL_FS, ItemList.LaserTargetBismuth.get(1));

        recipeMaps.Assembler.addRecipeX(F, 8192, 600, ST.array(OP.plateDense.mat(MT.TungstenSteel, 4), OP.plate.mat(MT.Glass, 8), OP.wireFine.mat(MT.Pt, 16)),
                FL.array(MT.SolderingAlloy.liquid(U4, F)), ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(31060));
        recipeMaps.Assembler.addRecipeX(F, 8192, 600, ST.array(OP.casingMachine.mat(MT.Ti, 2), OP.pipeSmall.mat(MT.Ti, 4), OP.plate.mat(MT.Ta, 2)),
                FL.array(MT.SolderingAlloy.liquid(U4, F)), ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(31061));
        recipeMaps.Assembler.addRecipeX(F, 32768, 1200, ST.array(OP.casingMachine.mat(MT.Os, 2), OP.plateDense.mat(MT.Os, 2), OP.wireFine.mat(MT.Pt, 32)),
                FL.array(MT.SolderingAlloy.liquid(U8, F)), ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(31062));
        recipeMaps.Assembler.addRecipeX(F, 8192, 600, ST.array(OP.plateDense.mat(MT.Pb, 4), OP.plate.mat(MT.TungstenSteel, 2), OP.foil.mat(MT.Li, 8)),
                FL.array(MT.SolderingAlloy.liquid(U4, F)), ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(31063));
        recipeMaps.Assembler.addRecipeX(F, 32768, 2400, ST.array(GTTileEntityRegistry.ktfruaddon.getItem(31060, 8), GTTileEntityRegistry.ktfruaddon.getItem(31061), GTTileEntityRegistry.ktfruaddon.getItem(31062, 2), GTTileEntityRegistry.ktfruaddon.getItem(31063, 2)),
                FL.array(MT.SolderingAlloy.liquid(U16, F)), ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(30060));
        recipeMaps.Assembler.addRecipeX(F, 131072, 4800, ST.array(GTTileEntityRegistry.ktfruaddon.getItem(31060, 24), GTTileEntityRegistry.ktfruaddon.getItem(31061, 2), GTTileEntityRegistry.ktfruaddon.getItem(31062, 8), GTTileEntityRegistry.ktfruaddon.getItem(31063, 4)),
                FL.array(MT.SolderingAlloy.liquid(U64, F)), ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(30061));

        recipeMaps.NeutronAbsorption.addRecipe0(false, 160,1, MT.Li_6.liquid(U144, true), FL.array(MT.He.gas(U20, false), MT.D.gas(U200,false)));
        recipeMaps.NeutronAbsorption.addRecipe0(false, 160,1, MT.F.liquid(U1000, true), FL.array(MT.Ne.gas(U100, false)));
        recipeMaps.NeutronAbsorption.addRecipe0(false,  80,1, MT.Cl.liquid(U1000, true), FL.array(MT.Ar.gas(U100, false)));
        recipeMaps.NeutronAbsorption.addRecipe0(false,  80,1, MT.Br.liquid(U1000, true), FL.array(MT.Kr.gas(U100, false)));
        recipeMaps.NeutronAbsorption.addRecipe0(false,  80,1, MT.I.liquid(U1000, true), FL.array(MT.Xe.gas(U100, false)));
        recipeMaps.NeutronAbsorption.addRecipe0(false,  80,1, MT.Ra.liquid(U1000, true), FL.array(MT.Rn.gas(U100, false)));
    }
}
