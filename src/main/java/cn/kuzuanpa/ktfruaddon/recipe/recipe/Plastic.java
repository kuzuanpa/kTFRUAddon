/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.fluid.flList;
import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import cn.kuzuanpa.ktfruaddon.material.matList;
import cn.kuzuanpa.ktfruaddon.recipe.recipeManager;
import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.data.RM;

import static gregapi.data.CS.*;
public class Plastic {
    public static void init(){
        recipeManager.LightMixer         .addRecipe0(F,32 ,120,FL.array(FL.Methane.make(300),MT.Cl.gas(6*U10,false)),FL.array(flList.Chloromethane.make(100),flList.Dichloromethane.make(100),flList.Chloroform.make(100),MT.HCl.gas(6*U10,false)),ZL_IS);

        recipeManager.HeatMixer          .addRecipe1(F,64 ,80, OP.dust.mat(MT.CaCO3,1), flList.GlacialAceticAcid.make(100), FL.CarbonDioxide.make(1000), matList.CalciumAcetate.getDust(1));
        recipeManager.HeatMixer          .addRecipe1(F,64 ,80,OP.dust.mat(matList.ZincChromate.mat, 0),FL.array(FL.BioEthanol.make(100)),FL.array(flList.Acetone.make(45),FL.CarbonDioxide.make(50)),ZL_IS);
        RM.           Mixer              .addRecipe1(F,64 ,80,OP.dust.mat(matList.Zincoxide.mat, 0),FL.array(flList.Acetylene.make(200),FL.Water.make(300)),FL.array(flList.Acetone.make(100),FL.CarbonDioxide.make(100),FL.Hydrogen.make(200)),ZL_IS);
        RM.           Mixer              .addRecipe0(F,140,200,FL.array(FL.Propylene.make(100),flList.Benzene.make(100),FL.Oxygen.make(50)),FL.array(flList.Phenol.make(100),flList.Acetone.make(100)),ZL_IS);
        RM.           Mixer              .addRecipe0(F,140,120,FL.array(MT.Cl.gas(U10,false),flList.CarbonMonoxide.make(100)),FL.array(flList.Phosgene.make(100)),ZL_IS);
        RM.           Mixer              .addRecipe0(F,140,120,FL.array(flList.Phosgene.make(100),flList.Phenol.make(100)),ZL_FS,matList.DiphenylCarbonate.getDust(1));
        recipeManager.HeatMixer          .addRecipe2(F,140,110,matList.DiphenylCarbonate.getDust(1),matList.BPA.getDust(1),ZL_FS,ZL_FS,matList.Polycarbonate.getDust(1));
        recipeManager.HeatMixer          .addRecipe1(F,64 ,82,OP.dust.mat(MT.Al,1),FL.array(flList.Ethane.make(1500)),ZL_FS,matList.TriethylAluminium.getDust(1));
        RM.           Mixer              .addRecipe0(F,32 ,100,FL.array(MT.Cl.gas(U10,false),FL.Water.make(100)),FL.array(flList.HypochlorousAcid.make(100),FL.make("hydrochloricacid",100)),ZL_IS);
        RM.           Mixer              .addRecipe0(F,140,120,FL.array(flList.Methanol.make(200),MT.H2SO4.liquid(0,false)),FL.array(flList.Methoxymethane.make(200),FL.Water.make(100)),ZL_IS);
        RM.           Mixer              .addRecipe1(F,64 ,112,ItemList.ProtonExchangeMembrane.get(0),FL.array(flList.Phenol.make(200),flList.Acetone.make(100),MT.H2SO4.liquid(0,false),MT.HCl.gas(0,false)),FL.array(FL.Water.make(100)),matList.BPA.getDust(1));
        recipeManager.HeatMixer          .addRecipe1(F,256,10,OP.dust.mat(matList.CalciumAcetate.mat, 1),ZL_FS,flList.Acetone.make(500),OP.dust.mat(MT.CaCO3,1));
        RM.           Mixer              .addRecipe0(F,80 ,120,FL.array(FL.Propylene.make(100),MT.Cl.gas(U10,false)),FL.array(flList.AllylChloride.make(100),MT.HCl.gas(U10,false)),ZL_IS);
        RM.           Mixer              .addRecipe0(F,100,120,FL.array(flList.AllylChloride.make(100),flList.HypochlorousAcid.make(100)),FL.array(flList.Dichloromethane.make(100)),ZL_IS);
        RM.           Mixer              .addRecipe0(F,140,100,FL.array(MT.Glycerol.liquid(U10,false),MT.HCl.gas(U5,false),flList.GlacialAceticAcid.make(0)),FL.array(flList.Dichloromethane.make(100)),ZL_IS);
        recipeManager.HeatMixer          .addRecipe1(F,256,80,OP.dust.mat(MT.Ag, 0),FL.array(FL.Propylene.make(100),FL.Oxygen.make(100),FL.Hydrogen.make(100)),flList.AllylAlcohol.make(100),ZL_IS);
        RM.           Mixer              .addRecipe1(F,256,80,OP.dust.mat(MT.Pb, 0),FL.array(FL.Propylene.make(100),FL.Oxygen.make(100),flList.GlacialAceticAcid.make(40)),flList.AllylAcetate.make(100),ZL_IS);
        RM.           Mixer              .addRecipe0(F,140,10,FL.array(flList.AllylAcetate.make(100),FL.Water.make(100)),FL.array(flList.AllylAlcohol.make(100),flList.GlacialAceticAcid.make(40)),ZL_IS);
        RM.           Mixer              .addRecipe0(F,140,40,FL.array(flList.AllylAlcohol.make(100),MT.Cl.gas(U5,false)),FL.array(flList.DichloroPropanol.make(100),MT.HCl.gas(U5,false)),ZL_IS);
        recipeManager.HeatMixer          .addRecipe1(F,148,120,OP.dust.mat(MT.NaOH,1),FL.array(flList.DichloroPropanol.make(100)),FL.array(flList.Epichlorohydrin.make(100)),ZL_IS);
        recipeManager.HeatMixer          .addRecipe0(F,110,110,FL.array(flList.CarbonMonoxide.make(100),FL.Propylene.make(100),MT.HF.gas(0,false)),FL.array(flList.MethacrylicAcid.make(100)),ZL_IS);

        RM           .Bath               .addRecipe2(F,0,80,OP.dust.mat(MT.NaOH,4),matList.BPA.getDust(4),FL.array(FL.Water.make(1000)),FL.array(flList.SolutionBPASodium.make(1000)),ZL_IS);
        recipeManager.HeatMixer          .addRecipe0(F,110,110,FL.array(flList.SolutionBPASodium.make(1000),flList.Phosgene.make(200),flList.Dichloromethane.make(200)),ZL_FS,matList.Polycarbonate.getDust(4));

        recipeManager.HeatMixer          .addRecipe1(F,142,380,OP.dust.mat(MT.Zn,1),FL.array(MT.HF.gas(16*U10,false),flList.Chloroform.make(800)),FL.array(flList.Tetrafluoroethylene.make(400),MT.HCl.gas(2*U,false)),matList.ZincChloride.getDust(1));

        RM.           Mixer              .addRecipe1(F,256,120,OP.dust.mat(MT.K2S2O7,0),FL.array(flList.Tetrafluoroethylene.make(1000),FL.Water.make(0)),ZL_FS,OP.dust.mat(MT.Teflon,7));

        recipeManager.HeatMixer          .addRecipe2(F,320,120,matList.BPA.getDust(1),OP.dust.mat(MT.NaOH,2),FL.array(flList.Epichlorohydrin.make(1000)),FL.array(FL.Saltwater.make(800)),matList.EpoxyResin.getDust(6));

        RM.           Mixer              .addRecipe1(F,148,87 ,matList.TriethylAluminium.getDust(0),FL.array(FL.Ethylene.make(100),MT.TiCl4.liquid(0,false)),ZL_FS,OP.dust.mat(MT.Plastic,1));

        RM.           Mixer              .addRecipe0(F,320,120,FL.array(MT.H2SO4.liquid(0,false),flList.Phenol.make(1000),flList.Formaldehyde.make(1000)),ZL_FS,OP.dust.mat(MT.Bakelite,8));
        recipeManager.HeatMixer          .addRecipe1(F,320,120,OP.dust.mat(MT.NaOH,0),FL.array(flList.Phenol.make(1000),flList.Formaldehyde.make(1000)),ZL_FS,OP.dust.mat(MT.Bakelite,8));

        recipeManager.HeatMixer          .addRecipe1(F,210,100,OP.dust.mat(MT.Mg,1),FL.array(flList.Chloromethane.make(300),flList.Methoxymethane.make(0)),FL.array(flList.MethylmagnesiumChloride.make(300)),ZL_IS);

        //recipeManager.HeatMixer          .addRecipe0(F,210,100,FL.array(),FL.array(),ZL_IS);
        //recipeManager.HeatMixer          .addRecipe1(F,210,100,OP.dust.mat(MT.Mg,4),FL.array(),FL.array(),ZL_IS);
        RM.           Mixer              .addRecipe0(F,210,100,FL.array(flList.MethylmagnesiumChloride.make(100),flList.Tetrachorosilane.make(100)),FL.array(flList.Methyltrichlorosilane.make(100)),OP.dust.mat(MT.MgCl2,1));

        recipeManager.HeatMixer          .addRecipe0(F,168,100,FL.array(flList.Methyltrichlorosilane.make(100),flList.MethylmagnesiumChloride.make(100)),FL.array(flList.Dichlorodimethylsilane.make(100)),OP.dust.mat(MT.MgCl2,1));
        recipeManager.HeatMixer          .addRecipe2(F,256,120,OP.dust.mat(MT.Si,1),OP.dust.mat(MT.Brass,0),FL.array(flList.Chloromethane.make(200)),FL.array(flList.Dichlorodimethylsilane.make(100)),ZL_IS);

        recipeManager.HeatMixer          .addRecipe0(F,146,120,FL.array(flList.Dichlorodimethylsilane.make(100),FL.BioEthanol.make(100),MT.H2SO4.liquid(0,false)),FL.array(flList.LiquidSiliconeRubber.make(100)),ZL_IS);

        RM.           Mixer              .addRecipe2(F,256,120,OP.dust.mat(MT.C,1),OP.dust.mat(MT.CaCO3,1),ZL_FS,FL.array(FL.CarbonDioxide.make(100)),matList.CalciumCarbide.getDust(1));

        RM.           Mixer              .addRecipe1(F,256,120,matList.CalciumCarbide.getDust(1),FL.array(FL.Water.make(200)),FL.array(flList.Acetylene.make(100)),matList.CalciumHydroxide.getDust(1));

        RM.           Mixer              .addRecipe1(F,256,120,matList.MercuryIIChloride.getDust(0),FL.array(MT.HCl.gas(U10,false),flList.Acetylene.make(100)),FL.array(flList.VinylChloride.make(100)),ZL_IS);

        RM.           Mixer              .addRecipe0(F,256,120,FL.array(FL.BioEthanol.make(100),MT.Cl.gas(U10,false),FL.Oxygen.make(50)),FL.array(flList.VinylChloride.make(200),FL.Water.make(100)),ZL_IS);

        RM.           Mixer              .addRecipe0(F,256,120,FL.array(FL.Ethylene.make(100),MT.Cl.gas(U10,false)),FL.array(flList.DichloroEthane.make(100)),ZL_IS);

        recipeManager.PressureHeatMixer  .addRecipe0(F,256,120,FL.array(flList.DichloroEthane.make(100)),FL.array(flList.VinylChloride.make(100),MT.HCl.gas(U10,false)),ZL_IS);

        recipeManager.HeatMixer          .addRecipe0(F,256,120,FL.array(flList.VinylChloride.make(100),FL.Water.make(0)),ZL_FS,OP.dust.mat(MT.PVC,1));

        RM.           Mixer              .addRecipe0(F,256,120,FL.array(flList.Ethylbenzene.make(100),MT.Cl.gas(U10,false)),FL.array(MT.HCl.gas(U10,false),flList.ChloroPhenylethane.make(100)),ZL_IS);

        RM.           Mixer              .addRecipe1(F,512,120,OP.dust.mat(MT.NaOH,1),FL.array(flList.ChloroPhenylethane.make(100)),FL.array(flList.Styrene.make(100),FL.Water.make(100)),OP.dust.mat(MT.NaCl,1));

        RM.           Mixer              .addRecipe0(F,784,120,FL.array(flList.Styrene.make(100),flList.Butadiene.make(100)),FL.array(flList.SBR.make(100)),ZL_IS);

        recipeManager.HeatMixer          .addRecipe1(F,120,120,matList.TriethylAluminium.getDust(0),FL.array(MT.TiCl4.liquid(0,false),flList.Isoprene.make(144)),FL.array(FL.Latex.make(144)),ZL_IS);

        recipeManager.HeatMixer          .addRecipe0(F,210,100,FL.array(flList.Tetrafluoroethylene.make(100),MT.SO3.gas(U10,false)),FL.array(flList.TFES.make(100)),ZL_IS);

        recipeManager.HeatMixer          .addRecipe0(F,256,120,FL.array(flList.Tetrafluoroethylene.make(700)),FL.array(flList.HexaFluoroPropylene.make(200),flList.Perfluorocyclobutane.make(200)),ZL_IS);

        RM.           Electrolyzer       .addRecipe1(F,320,40,ItemList.ProtonExchangeMembrane.get(0),FL.array(flList.HexaFluoroPropylene.make(200),MT.Br.liquid(U5,false)),FL.array(flList.HFPO.make(200),flList.HydrobromicAcid.make(200),FL.Hydrogen.make(100)),ZL_IS);

        recipeManager.PressureHeatMixer  .addRecipe0(F,0,1600,FL.array(flList.HexaFluoroPropylene.make(100),FL.Oxygen.make(100),flList.Benzene.make(0)),FL.array(flList.HFPO.make(100)),ZL_IS);

        recipeManager.HeatMixer          .addRecipe1(F,210,100,OP.dust.mat(MT.Na2CO3,1),FL.array(flList.TFES.make(100),flList.HFPO.make(100),flList.Methoxymethane.make(0)),FL.array(flList.PrecursorPSVE.make(100)),ZL_IS);

        recipeManager.HeatMixer          .addRecipe1(F,210,100,OP.dust.mat(MT.NaOH,1),FL.array(flList.PrecursorPSVE.make(100)),FL.array(flList.PSVE.make(100)),OP.dust.mat(MT.NaHCO3,1));

        recipeManager.HeatMixer          .addRecipe0(F,210,100,FL.array(flList.PSVE.make(100),flList.HexaFluoroPropylene.make(100),flList.Tetrafluoroethylene.make(100)),ZL_FS,OP.dust.mat(matList.PSVE.get(), 1));

        RM.           Electrolyzer       .addRecipe0(F,16,18000,FL.array(flList.PSVE.make(36),flList.Acetone.make(0)),ZL_FS,ItemList.ProtonExchangeMembrane.get(1));

        RM.           Mixer              .addRecipe0(F,512,120,FL.array(flList.MethacrylicAcid.make(100),flList.Methanol.make(100)),FL.array(FL.Water.make(100)),OP.dust.mat(matList.PolymethylMethacrylate.get(),1));

        recipeManager.HeatMixer          .addRecipe2(F,310,120,OP.dust.mat(MT.Au,0),OP.dust.mat(MT.Pb,0),FL.array(FL.Ethylene.make(100),flList.GlacialAceticAcid.make(100),FL.Oxygen.make(100)),FL.array(FL.CarbonDioxide.make(100),FL.Water.make(100)),OP.dust.mat(matList.VinylAcetate.get(),1));

        recipeManager.HeatMixer          .addRecipe0(F,262,140,FL.array(flList.Acetylene.make(100),flList.GlacialAceticAcid.make(100)),FL.array(flList.VinylAcetate.make(100)),ZL_IS);

        recipeManager.HeatMixer          .addRecipe1(F,262,140,matList.VinylAcetate.getDust(1),FL.array(MT.H2O2.liquid(5*U100,false),flList.Methanol.make(0)),FL.array(FL.Water.make(50)),OP.dust.mat(matList.PolyVinylAcetate.get(),1));

        RM.           Mixer              .addRecipe2(F,140,320,matList.PolyVinylAcetate.getDust(1),OP.dust.mat(MT.NaOH,1),FL.array(flList.Methanol.make(0)),FL.array(flList.GlacialAceticAcid.make(100)),OP.dust.mat(matList.PVA.get(),1));

    }
}
