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
        recipeManager.LightMixer.addRecipe0(F,32 ,120,FL.array(FL.Methane.make(300),MT.Cl.gas(6*U10,false)),FL.array(flList.Chloromethane.make(100),flList.Dichloromethane.make(100),flList.Chloroform.make(100),MT.HCl.liquid(6*U10,false)),ZL_IS);

        recipeManager.HeatMixer .addRecipe1(F,64 ,80, OP.dust.mat(MT.CaCO3,1), flList.GlacialAceticAcid.make(100), FL.CarbonDioxide.make(1000), matList.CalciumAcetate.getDust(1));
        recipeManager.HeatMixer .addRecipe1(F,64 ,80,OP.dust.mat(matList.ZincChromate.mat, 0),FL.array(FL.BioEthanol.make(100)),FL.array(flList.Acetone.make(45),FL.CarbonDioxide.make(50)),ZL_IS);
        RM.           Mixer     .addRecipe1(F,64 ,80,OP.dust.mat(matList.Zincoxide.mat, 0),FL.array(flList.Acetylene.make(200),FL.Water.make(300)),FL.array(flList.Acetone.make(100),FL.CarbonDioxide.make(100),FL.Hydrogen.make(200)),ZL_IS);
        RM.           Mixer     .addRecipe0(F,140,200,FL.array(FL.Propylene.make(100),flList.Benzene.make(100),FL.Oxygen.make(50)),FL.array(flList.Phenol.make(100),flList.Acetone.make(100)),ZL_IS);
        RM.           Mixer     .addRecipe0(F,140,120,FL.array(MT.Cl.gas(U10,false),flList.CarbonMonoxide.make(100)),FL.array(flList.Phosgene.make(100)),ZL_IS);
        RM.           Mixer     .addRecipe0(F,140,120,FL.array(flList.Phosgene.make(100),flList.Phenol.make(100)),ZL_FS,matList.DiphenylCarbonate.getDust(1));
        recipeManager.HeatMixer .addRecipe2(F,140,110,matList.DiphenylCarbonate.getDust(1),matList.BPA.getDust(1),ZL_FS,ZL_FS,matList.Polycarbonate.getDust(1));
        recipeManager.HeatMixer .addRecipe1(F,64 ,82,OP.dust.mat(MT.Al,1),FL.array(flList.Ethane.make(1500)),ZL_FS,matList.TriethylAluminium.getDust(1));
        RM.           Mixer     .addRecipe0(F,32 ,100,FL.array(MT.Cl.gas(U10,false),FL.Water.make(100)),FL.array(flList.HypochlorousAcid.make(100),FL.make("hydrochloricacid",100)),ZL_IS);
        RM.           Mixer     .addRecipe0(F,140,120,FL.array(flList.Methanol.make(200),MT.H2SO4.liquid(0,false)),FL.array(flList.Methoxymethane.make(200),FL.Water.make(100)),ZL_IS);
        RM.           Mixer     .addRecipe1(F,64 ,112,ItemList.ProtonExchangeMembrane.get(0),FL.array(flList.Phenol.make(200),flList.Acetone.make(100),MT.H2SO4.liquid(0,false),MT.HCl.gas(0,false)),FL.array(FL.Water.make(100)),matList.BPA.getDust(1));
        recipeManager.HeatMixer .addRecipe1(F,256,10,OP.dust.mat(matList.CalciumAcetate.mat, 1),ZL_FS,flList.Acetone.make(500),OP.dust.mat(MT.CaCO3,1));
        RM.           Mixer     .addRecipe0(F,80 ,120,FL.array(FL.Propylene.make(100),MT.Cl.gas(U10,false)),FL.array(flList.AllylChloride.make(100),MT.HCl.liquid(U10,false)),ZL_IS);
        RM.           Mixer     .addRecipe0(F,100,120,FL.array(flList.AllylChloride.make(100),flList.HypochlorousAcid.make(100)),FL.array(flList.Dichloromethane.make(100)),ZL_IS);
        RM.           Mixer     .addRecipe0(F,140,100,FL.array(MT.Glycerol.liquid(U10,false),MT.HCl.gas(U5,false),flList.GlacialAceticAcid.make(0)),FL.array(flList.Dichloromethane.make(100)),ZL_IS);
        recipeManager.HeatMixer .addRecipe1(F,256,80,OP.dust.mat(MT.Ag, 0),FL.array(FL.Propylene.make(100),FL.Oxygen.make(100),FL.Hydrogen.make(100)),flList.AllylAlcohol.make(100),ZL_IS);
        recipeManager.HeatMixer .addRecipe1(F,256,80,OP.dust.mat(MT.Pd, 0),FL.array(FL.Propylene.make(100),FL.Oxygen.make(100),flList.GlacialAceticAcid.make(40)),flList.AllylAcetate.make(100),ZL_IS);
        recipeManager.HeatMixer .addRecipe0(F,140,10,FL.array(flList.AllylAcetate.make(100),FL.Water.make(100)),FL.array(flList.AllylAlcohol.make(100),flList.GlacialAceticAcid.make(40)),ZL_IS);
        recipeManager.HeatMixer .addRecipe0(F,140,40,FL.array(flList.AllylAlcohol.make(100),MT.Cl.gas(U5,false)),FL.array(flList.DichloroPropanol.make(100),MT.HCl.liquid(U5,false)),ZL_IS);
        recipeManager.HeatMixer .addRecipe1(F,148,120,OP.dust.mat(MT.NaOH,1),FL.array(flList.DichloroPropanol.make(100)),FL.array(flList.Epichlorohydrin.make(100)),ZL_IS);
        recipeManager.HeatMixer .addRecipe0(F,110,110,FL.array(flList.CarbonMonoxide.make(100),FL.Propylene.make(100),MT.HF.gas(0,false)),FL.array(flList.MethacrylicAcid.make(100)),ZL_IS);

        RM           .Bath      .addRecipe2(F,0,80,OP.dust.mat(MT.NaOH,4),matList.BPA.getDust(4),ZL_FS,FL.array(flList.SolutionBPASodium.make(1000)),ZL_IS);
        recipeManager.HeatMixer .addRecipe0(F,110,110,FL.array(flList.SolutionBPASodium.make(1000),flList.Phosgene.make(200),flList.Dichloromethane.make(200)),ZL_FS,matList.Polycarbonate.getDust(4));

        recipeManager.HeatMixer .addRecipe1(F,142,380,OP.dust.mat(MT.Zn,1),FL.array(MT.HF.gas(16*U10,false),flList.Chloroform.make(800)),FL.array(flList.Tetrafluoroethylene.make(400),MT.HCl.liquid(2*U,false)),matList.ZincChloride.getDust(1));

        recipeManager.HeatMixer .addRecipe1(F,256,120,OP.dust.mat(MT.K2S2O7,0),FL.array(flList.Tetrafluoroethylene.make(1000),FL.Water.make(0)),ZL_FS,OP.dust.mat(MT.Teflon,7));

    }
}
