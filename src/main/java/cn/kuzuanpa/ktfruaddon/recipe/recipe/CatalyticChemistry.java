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

import cn.kuzuanpa.ktfruaddon.api.fluid.flList;
import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import cn.kuzuanpa.ktfruaddon.api.material.matList;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.util.ST;

import static gregapi.data.CS.*;
import static gregapi.data.OP.*;

public class CatalyticChemistry {
    public static void init() {
        catalystClusters();
        earlyPlasticShortcuts();
        peekChain();
    }

    private static void earlyPlasticShortcuts() {
        // 甲烷直接偶联聚合为聚乙烯类通用塑料。
        recipeMaps.CatalyticReactor.addRecipeX(F, 1024, 320,
                ST.array(ItemList.NickelCatalystCluster.get(0)),
                FL.array(FL.Methane.make(3000), FL.Oxygen.make(1500)),
                ZL_FS, dust.mat(MT.Plastic, 4));

        // 甲烷氯化后在同一催化床中完成聚合，直接得到PVC。
        recipeMaps.CatalyticReactor.addRecipeX(F, 1024, 360,
                ST.array(ItemList.ZeoliteCatalystCluster.get(0)),
                FL.array(FL.Methane.make(3000), MT.Cl.gas(2 * U, false)),
                ZL_FS, dust.mat(MT.PVC, 3));

        // 苯、甲烷和氧气经分子筛路径直接合成丁苯橡胶。
        recipeMaps.CatalyticReactor.addRecipeX(F, 1536, 480,
                ST.array(ItemList.ZeoliteCatalystCluster.get(0)),
                FL.array(flList.Benzene.make(1000), FL.Methane.make(2500), FL.Oxygen.make(1500)),
                FL.array(FL.Water.make(1500)), dust.mat(matList.SBR.mat, 2));

        // 苯和丙烯在分子筛上直接完成环氧化与缩聚，得到环氧树脂。
        recipeMaps.CatalyticReactor.addRecipeX(F, 1536, 520,
                ST.array(ItemList.ZeoliteCatalystCluster.get(0)),
                FL.array(flList.Benzene.make(1000), FL.Propylene.make(1500), FL.Oxygen.make(1800)),
                FL.array(FL.Water.make(1200)), dust.mat(matList.EpoxyResin.mat, 4));

        // 甲烷氧化产物与甲醇经齐格勒-纳塔路径直接聚合为PMMA。
        recipeMaps.CatalyticReactor.addRecipeX(F, 1280, 420,
                ST.array(ItemList.ZieglerNattaCatalystCluster.get(0)),
                FL.array(FL.Methane.make(2500), FL.Oxygen.make(1200), flList.Methanol.make(1000)),
                FL.array(FL.Water.make(1000)), dust.mat(matList.PolymethylMethacrylate.get(), 2));

        // 苯与光气在铂钯催化下直接构建聚碳酸酯链段。
        recipeMaps.CatalyticReactor.addRecipeX(F, 1536, 500,
                ST.array(ItemList.PlatinumPalladiumCatalystCluster.get(0)),
                FL.array(flList.Benzene.make(1000), flList.Phosgene.make(750), FL.Oxygen.make(1000)),
                FL.array(FL.Hydrogen.make(240)), dust.mat(MT.Polycarbonate, 3));
    }

    private static void catalystClusters() {
        recipeMaps.CatalyticReactor.addRecipeX(F, 256, 240,
                ST.array(dust.mat(MT.Ni, 4), dust.mat(MT.Al2O3, 4), dust.mat(MT.SiO2, 2)),
                ZL_FS, ZL_FS, ItemList.NickelCatalystCluster.get(1));

        recipeMaps.CatalyticReactor.addRecipeX(F, 512, 300,
                ST.array(dustTiny.mat(MT.Pt, 4), dustTiny.mat(MT.Pd, 4), dust.mat(MT.C, 4), dust.mat(MT.Al2O3, 2)),
                ZL_FS, ZL_FS, ItemList.PlatinumPalladiumCatalystCluster.get(1));

        recipeMaps.CatalyticReactor.addRecipeX(F, 512, 360,
                ST.array(dust.mat(MT.SiO2, 4), dust.mat(MT.Al2O3, 2), dust.mat(MT.Na, 1)),
                ZL_FS, ZL_FS, ItemList.ZeoliteCatalystCluster.get(1));

        recipeMaps.CatalyticReactor.addRecipeX(F, 1024, 400,
                ST.array(matList.TriethylAluminium.getDust(1), dust.mat(MT.MgCl2, 4)),
                FL.array(MT.TiCl4.liquid(U, false)), ZL_FS, ItemList.ZieglerNattaCatalystCluster.get(1));

        recipeMaps.CatalyticReactor.addRecipeX(F, 2048, 600,
                ST.array(dust.mat(MT.K2CO3, 4), dust.mat(MT.TiO2, 2), dust.mat(MT.SiO2, 2)),
                ZL_FS, ZL_FS, ItemList.PolycondensationCatalystCluster.get(1));
    }

    private static void peekChain() {
        recipeMaps.CatalyticReactor.addRecipeX(F, 256, 80,
                ST.array(ItemList.ZeoliteCatalystCluster.get(0)),
                FL.array(flList.Benzene.make(1000), MT.F.gas(U, false)),
                FL.array(flList.Fluorobenzene.make(1000)), ZL_IS);

        recipeMaps.CatalyticReactor.addRecipeX(F, 256, 80,
                ST.array(ItemList.ZeoliteCatalystCluster.get(0)),
                FL.array(flList.Toluene.make(1000), MT.F.gas(U, false)),
                FL.array(flList.Fluorotoluene.make(1000)), ZL_IS);

        recipeMaps.CatalyticReactor.addRecipeX(F, 384, 140,
                ST.array(ItemList.NickelCatalystCluster.get(0)),
                FL.array(flList.Fluorotoluene.make(1000), FL.Oxygen.make(1000)),
                ZL_FS, matList.FluorobenzoicAcid.getDust(1));

        recipeMaps.CatalyticReactor.addRecipeX(F, 512, 160,
                ST.array(ItemList.PlatinumPalladiumCatalystCluster.get(0), matList.FluorobenzoicAcid.getDust(1)),
                FL.array(flList.Phosgene.make(1000)),
                FL.array(flList.FluorobenzoylChloride.make(1000), FL.CarbonDioxide.make(1000), MT.HCl.gas(U, false)), ZL_IS);

        recipeMaps.CatalyticReactor.addRecipeX(F, 768, 240,
                ST.array(ItemList.PlatinumPalladiumCatalystCluster.get(0)),
                FL.array(flList.Fluorobenzene.make(1000), flList.FluorobenzoylChloride.make(1000)),
                FL.array(MT.HCl.gas(U, false)), matList.Difluorobenzophenone.getDust(1));

        recipeMaps.CatalyticReactor.addRecipeX(F, 512, 180,
                ST.array(ItemList.NickelCatalystCluster.get(0)),
                FL.array(flList.Phenol.make(1000), MT.H2O2.liquid(U, false)),
                FL.array(FL.Water.make(500)), matList.Hydroquinone.getDust(1));

        recipeMaps.CatalyticReactor.addRecipeX(F, 8192, 1200,
                ST.array(ItemList.PolycondensationCatalystCluster.get(0), matList.Difluorobenzophenone.getDust(1), matList.Hydroquinone.getDust(1), dust.mat(MT.K2CO3, 2)),
                ZL_FS, FL.array(FL.Water.make(1000)), dust.mat(matList.PEEK.mat, 2));
    }
}
