/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.fluid.flList;
import cn.kuzuanpa.ktfruaddon.material.matList;
import cn.kuzuanpa.ktfruaddon.recipe.recipeManager;
import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.data.RM;
import gregapi.util.OM;
import gregapi.util.ST;

import static gregapi.data.CS.*;

public class OreProcessing {
    public OreProcessing() {
  //Cr processing
        RM.Bath.addRecipeX(T,0, 512 , ST.array(OM.dust(MT.OREMATS.Chromite, U*2), OM.dust(matList.Sulfanilamide.get(), U*9)), FL.array(MT.H2SO4.liquid(U*6,F)), FL.array(FL.Water.make(4000)), OM.dust(matList.AmmoniumChromicSulfate.get(), U*6), OM.dust(matList.AmmoniumIronIIISulfate.mat, U*3));
        RM.Mixer.addRecipeX(T,64,82,ST.array(ZL_IS),FL.array(MT.NH3.gas(2*U,T),MT.H2SO4.liquid(U , T)), FL.array(ZL_FS),OM.dust(matList.Sulfanilamide.mat,U*9));
        RM.Electrolyzer.addRecipe2(F,120,360,matList.AmmoniumChromicSulfate.getDust(3),ST.tag(0),ZL_FS, FL.array(MT.H2SO4.liquid(U+U2,T)),OP.dust.mat(MT.Cr,1),matList.AmmoniumDichromate.getDust(2));
        RM.Electrolyzer.addRecipe2(F,64,320,matList.AmmoniumIronIIISulfate.getDust(3),ST.tag(0),ZL_FS, FL.array(MT.H2SO4.liquid(U*3,T)),OP.dust.mat(MT.Fe,2),matList.Sulfanilamide.getDust(4));
        recipeManager.HeatMixer.addRecipeX(T,256,160,ST.array(OM.dust(matList.AmmoniumDichromate.get())),FL.array(MT.O.gas(U *4,T )),FL.array(FL.Nitrogen.make(250)),OM.dust(MT.CrO2, U * 2));

   //Al processing
        //Step1:Bauxide process
        recipeManager.HeatMixer.addRecipeX(T,120,80,ST.array(OM.dust(MT.CaCO3,U),OM.dust(MT.Na2CO3,U *2), OM.dust(MT.OREMATS.Bauxite,U)),ZL_FS,FL.CarbonDioxide.make(3000),OM.dust(matList.CookedBauxide.mat,U*2));
        RM.Bath.addRecipeX(T, 0, 80, ST.array(OM.dust(MT.OREMATS.Bauxite,U)),FL.array(MT.H2SO4.liquid(U *4 ,T)),FL.array(flList.AcidPickledBauxide.make(2000)),OM.dust(MT.SiO2,U));
        RM.Bath.addRecipeX(T, 0, 180, ST.array(OM.dust(MT.OREMATS.Bauxite,U),OM.dust(MT.NaOH,U*2)),FL.array(MT.H2O.liquid(1000 ,T)),FL.array(flList.SodiumAluminate.make(2000)),OM.dust(MT.SiO2,U4),OM.crushedCentrifugedTiny(MT.OREMATS.Ilmenite,1));
        //Step2
        RM.Bath.addRecipeX(T, 0, 180, ST.array(OM.dust(matList.CookedBauxide.mat,U*2),OM.dust(MT.NaOH,U*2)),FL.array(MT.H2O.liquid(1000 ,T)),FL.array(flList.SodiumAluminate.make(2000)),OM.dust(MT.SiO2,U4),OM.dust(matList.CookedBauxide.mat,U));
        recipeManager.HeatMixer.addRecipeX(T,120,60,ST.array(OM.dust(MT.NaOH,U * 4)),FL.array(flList.AcidPickledBauxide.make(2000)),FL.array(flList.SodiumAluminate.make(2000)),OM.dust(MT.Na2SO4,4*U),OM.dust(MT.Fe2O3,U));
        RM.Mixer.addRecipeX(T,0, 800,ST.array(ZL_IS),FL.array(flList.SodiumAluminate.make(1000),FL.Soda.make(1000)),FL.array(flList.SodiumCarbonate.make(2000)),OM.dust(MT.AlO3H3,U*4));
        //Step3: Centrifuge for mixtures
        RM.Centrifuge.addRecipeX(T, 64, 40,ST.array(OM.dust(matList.CookedBauxide.mat,U)),FL.array(ZL_FS),FL.array(ZL_FS),OM.dust(MT.Fe2O3,U),OM.crushedCentrifugedTiny(MT.TiO2,1),OM.dust(MT.SiO2,U4));
      //Use K for some recipe
        //Step1
        RM.Bath.addRecipeX(T, 0, 180, ST.array(OM.dust(MT.OREMATS.Bauxite,U),OM.dust(MT.KOH,U*2)),FL.array(MT.H2O.liquid(1000 ,T)),FL.array(flList.PotassiumAluminate.make(2000)),OM.dust(MT.SiO2,U4),OM.crushedCentrifugedTiny(MT.OREMATS.Ilmenite,1));
        //Step2
        recipeManager.HeatMixer.addRecipeX(T,120,60,ST.array(OM.dust(MT.KOH,U * 4)),FL.array(flList.AcidPickledBauxide.make(2000)),FL.array(flList.PotassiumAluminate.make(2000)),OM.dust(MT.K2SO4,4*U),OM.dust(MT.Fe2O3,U));
        RM.Mixer.addRecipeX(T,0, 800,ST.array(ZL_IS),FL.array(flList.PotassiumAluminate.make(1000),FL.Soda.make(1000)),FL.array(flList.SodiumCarbonate.make(2000)),OM.dust(MT.AlO3H3,U*4));
     //W Process
        RM.Autoclave.addRecipe2(T, 0, 400,new long[] {10000} ,OP.dust.mat(MT.OREMATS.Wolframite,1),OP.dust.mat(MT.NaOH,4),FL.Steam.make(20000),flList.SodiumHeterotungstate.make(2000),OM.dust(MT.MgCO3, U));
        RM.Autoclave.addRecipe2(T, 0, 400,new long[]{10000},OP.dust.mat(MT.OREMATS.Huebnerite,1),OP.dust.mat(MT.NaOH,4),FL.Steam.make(20000),flList.SodiumHeterotungstate.make(2000),OM.dust(MT.MnO2, U));
        RM.Autoclave.addRecipe2(T, 0, 400,new long[] {10000} ,OP.dust.mat(MT.OREMATS.Scheelite,1),OP.dust.mat(MT.Na2CO3,2),FL.Steam.make(20000),flList.SodiumHeterotungstate.make(2000),OM.dust(MT.CaCO3, U));
        RM.Autoclave.addRecipe2(T, 0, 400,new long[]{10000},OP.dust.mat(MT.OREMATS.Tungstate,1),OP.dust.mat(MT.Na2CO3,2),FL.Steam.make(20000),flList.SodiumHeterotungstate.make(2000),OM.dust(matList.LithiumCarbonate.mat, U));
        RM.Mixer.addRecipeX(T,64, 100, ST.array(ZL_IS),FL.array(flList.SodiumHeterotungstate.make(4000),MT.HCl.fluid(4000,F)),FL.array(FL.Saltwater.make(8000)),OM.dust(MT.H2WO4,3*U));
        RM.Mixer.addRecipeX(T,64, 100, ST.array(ZL_IS),FL.array(flList.SodiumHeterotungstate.make(4000),MT.NH3.gas(4000,F)),FL.array(flList.AmmoniumTungstate.make(2000)),OM.dust(MT.NaOH,4*U));
        RM.Drying.addRecipeX(T,32,40, ST.array(ZL_IS),FL.array(flList.SodiumHeterotungstate.make(1000)),FL.array(FL.DistW.make(700)),OM.dust(MT.WO3,U));
      //Ti Process
        RM.Bath.addRecipeX(T,0,120,ST.array(OM.dust(MT.OREMATS.Ilmenite,2*U)),FL.array(MT.H2SO4.liquid(7*U,T)),FL.array(MT.MartianVitriol.fluid(6000,F)),OM.dust(matList.MetatitanicAcid.mat,4*U));
        RM.Drying.addRecipeX(T,32,40, ST.array(OM.dust(matList.MetatitanicAcid.mat,2*U)),FL.array(ZL_FS),FL.array(FL.DistW.make(200)),OM.dust(MT.TiO2,U));
        recipeManager.HeatMixer.addRecipeX(T,128,60,ZL_IS,FL.array(MT.TiCl4.liquid(5*U,T),MT.Ar.gas(1,T),MT.Mg.liquid(U*2,T)),FL.array(MT.Ti.liquid(6*U4,T)),OM.dust(MT.MgCl2,6*U));

        //K2CO3
        RM.Drying.addRecipeX(T,32,40, ST.array(ZL_IS),FL.array(flList.PotassiumCarbonate.make(1000)),FL.array(FL.DistW.make(750)),OM.dust(MT.K2CO3,U));
        RM.Bath.addRecipeX(T,0,20, ST.array(OM.dust(MT.K2CO3,U)),FL.array(MT.H2O.liquid(U , T)),FL.array(flList.PotassiumCarbonate.make(1000)),ZL_IS);
        //NaCO3
        RM.Drying.addRecipeX(T,32,40, ST.array(ZL_IS),FL.array(flList.SodiumCarbonate.make(1000)),FL.array(FL.DistW.make(750)),OM.dust(MT.Na2CO3,U));
        RM.Bath.addRecipeX(T,0,20, ST.array(OM.dust(MT.Na2CO3,U)),FL.array(MT.H2O.liquid(U , T)),FL.array(flList.SodiumCarbonate.make(1000)),ZL_IS);

    }
}
