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
import cn.kuzuanpa.ktfruaddon.recipe.recipeMaps;
import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.data.RM;
import gregapi.util.OM;
import gregapi.util.ST;

import static gregapi.data.CS.*;

public class OreProcessing {
    public static void init(){
  //Cr processing
        recipeMaps.HeatMixer.addRecipe1(F,100,400,OP.dust.mat(MT.Fe,0),FL.array(FL.Nitrogen.make(2000),FL.Hydrogen.make(3000)),FL.array(MT.NH3.gas(2*U,F)));
        RM.Bath.addRecipeX(T,0, 512 , ST.array(OP.dust.mat(MT.OREMATS.Chromite, 4), OP.dust.mat(matList.AmmoniumSulfate.get(), 9)), FL.array(MT.H2SO4.liquid(6*U,F)), FL.array(FL.Water.make(1500)), OP.dust.mat(matList.AmmoniumChromicSulfate.get(), 6), OP.dust.mat(matList.AmmoniumIronIIISulfate.mat, 3));
        RM.Mixer.addRecipeX(T,64,82,ST.array(ZL_IS),FL.array(MT.NH3.gas(2*U,T),MT.H2SO4.liquid(U , T)), FL.array(ZL_FS),OP.dust.mat(matList.AmmoniumSulfate.mat,9));
        RM.Electrolyzer.addRecipe2(F,120,360,matList.AmmoniumChromicSulfate.getDust(3),ST.tag(0),ZL_FS, FL.array(MT.H2SO4.liquid(U+U2,T)),OP.dust.mat(MT.Cr,1),matList.AmmoniumDichromate.getDust(2));
        RM.Electrolyzer.addRecipe2(F,64,320,matList.AmmoniumIronIIISulfate.getDust(3),ST.tag(0),ZL_FS, FL.array(MT.H2SO4.liquid(U*3,T), FL.Nitrogen.make(1000)),OP.dust.mat(MT.Fe,2));
        recipeMaps.HeatMixer.addRecipeX(T,320,160,ST.array(OP.dust.mat(matList.AmmoniumDichromate.get(),1)),FL.array(MT.O.gas(U *4,T )),FL.array(FL.Nitrogen.make(250)),OP.dust.mat(MT.CrO2, 2));

   //Al processing
        RM.Mixer.addRecipe0(F,16,40,FL.array(FL.DistW.make(1000),FL.CarbonDioxide.make(1000)),FL.array(FL.Soda.make(1000)));
        //Step1:Bauxide process
        recipeMaps.HeatMixer.addRecipeX(T,120,80,ST.array(OP.dust.mat(MT.CaCO3,1),OP.dust.mat(MT.Na2CO3,2), OP.dust.mat(MT.OREMATS.Bauxite,1)),ZL_FS,FL.CarbonDioxide.make(3000),OP.dust.mat(matList.CookedBauxide.mat, 2));
        RM.Bath.addRecipeX(T, 0, 80, ST.array(OP.dust.mat(MT.OREMATS.Bauxite,1)),FL.array(MT.H2SO4.liquid(U *4 ,T)),FL.array(flList.AcidPickledBauxide.make(2000)),OP.dustSmall.mat(MT.SiO2,1));
        RM.Bath.addRecipeX(T, 0, 180, ST.array(OP.dust.mat(MT.OREMATS.Bauxite,1),OP.dust.mat(MT.NaOH,2)),FL.array(MT.H2O.liquid(1000 ,T)),FL.array(flList.SodiumAluminate.make(2000)),OP.dustSmall.mat(MT.SiO2,1),OP.crushedCentrifugedTiny.mat(MT.OREMATS.Ilmenite,1));
        //Step2
        RM.Bath.addRecipeX(T, 0, 180, ST.array(OP.dust.mat(matList.CookedBauxide.mat,2),OP.dust.mat(MT.NaOH,2)),FL.array(MT.H2O.liquid(1000 ,T)),FL.array(flList.SodiumAluminate.make(2000)),OP.dustSmall.mat(MT.SiO2,1),OP.dust.mat(matList.BauxiteRedMud.mat,1));
        recipeMaps.HeatMixer.addRecipeX(T,120,60,ST.array(OP.dust.mat(MT.NaOH, 4)),FL.array(flList.AcidPickledBauxide.make(2000)),FL.array(flList.SodiumAluminate.make(2000)),OP.dust.mat(MT.Na2SO4,4 ),OP.dust.mat(MT.Fe2O3,1));
        RM.Mixer.addRecipeX(T,0, 800,ST.array(ZL_IS),FL.array(flList.SodiumAluminate.make(1000),FL.Soda.make(1000)),FL.array(flList.SodiumCarbonate.make(2000)),OP.dust.mat(MT.AlO3H3,4));
        //Step3: Centrifuge for mixtures
        RM.Centrifuge.addRecipeX(T, 64, 40,ST.array(OP.dust.mat(matList.BauxiteRedMud.mat,1)),FL.array(ZL_FS),FL.array(ZL_FS),OP.dust.mat(MT.Fe2O3,1),OM.crushedCentrifugedTiny(MT.TiO2,1),OP.dustSmall.mat(MT.SiO2,1));
      //Use Potassium for some recipe
        //Step1
        RM.Bath.addRecipeX(T, 0, 180, ST.array(OP.dust.mat(MT.OREMATS.Bauxite,1),OP.dust.mat(MT.KOH,2)),FL.array(MT.H2O.liquid(1000 ,T)),FL.array(flList.PotassiumAluminate.make(2000)),OP.dustSmall.mat(MT.SiO2,U4),OM.crushedCentrifugedTiny(MT.OREMATS.Ilmenite,1));
        //Step2
        recipeMaps.HeatMixer.addRecipeX(T,120,60,ST.array(OP.dust.mat(MT.KOH,  4)),FL.array(flList.AcidPickledBauxide.make(2000)),FL.array(flList.PotassiumAluminate.make(2000)),OP.dust.mat(MT.K2SO4,4 ),OP.dust.mat(MT.Fe2O3,1));
        RM.Mixer.addRecipeX(T,0, 800,ST.array(ZL_IS),FL.array(flList.PotassiumAluminate.make(1000),FL.Soda.make(1000)),FL.array(flList.PotassiumCarbonate.make(1000),flList.SodiumCarbonate.make(1000)),OP.dust.mat(MT.AlO3H3, 4));
     //W Process
        RM.Autoclave.addRecipe2(T, 0, 400,new long[] {10000},OP.dust.mat(MT.OREMATS.Wolframite,1),OP.dust.mat(MT.NaOH,4),FL.Steam.make(20000),flList.SodiumHeterotungstate.make(2000),OP.dust.mat(MT.MgCO3, 1));
        RM.Autoclave.addRecipe2(T, 0, 400,new long[] {10000},OP.dust.mat(MT.OREMATS.Huebnerite,1),OP.dust.mat(MT.NaOH,4),FL.Steam.make(20000),flList.SodiumHeterotungstate.make(2000),OP.dust.mat(MT.MnO2, 1));
        RM.Autoclave.addRecipe2(T, 0, 400,new long[] {10000},OP.dust.mat(MT.OREMATS.Scheelite,1),OP.dust.mat(MT.Na2CO3,2),FL.Steam.make(20000),flList.SodiumHeterotungstate.make(2000),OP.dust.mat(MT.CaCO3, 1));
        RM.Autoclave.addRecipe2(T, 0, 400,new long[] {10000},OP.dust.mat(MT.OREMATS.Tungstate,1),OP.dust.mat(MT.Na2CO3,2),FL.Steam.make(20000),flList.SodiumHeterotungstate.make(2000),OP.dust.mat(matList.LithiumCarbonate.mat,1));
        RM.Mixer.addRecipeX(T,64, 100, ST.array(ZL_IS),FL.array(flList.SodiumHeterotungstate.make(4000),MT.HCl.fluid(4000,F)),FL.array(FL.Saltwater.make(8000)),OP.dust.mat(MT.H2WO4,3 ));
        RM.Mixer.addRecipeX(T,64, 100, ST.array(ZL_IS),FL.array(flList.SodiumHeterotungstate.make(4000),MT.NH3.gas(4000,F)),FL.array(flList.AmmoniumTungstate.make(2000)),OP.dust.mat(MT.NaOH,4 ));
        RM.Drying.addRecipeX(T,32,40, ST.array(ZL_IS),FL.array(flList.AmmoniumTungstate.make(1000)),FL.array(FL.DistW.make(700)),OP.dust.mat(MT.WO3,1));
        recipeMaps.HeatMixer.addRecipe1(T, 320, 160, OM.dust(MT.WO3, U * 4), MT.H.gas(U * 6, T), MT.H2O.liquid(U * 9, F), OM.dust(MT.W, U));

        //Ti Process
        RM.Bath.addRecipeX(T,0,200,ST.array(OP.dust.mat(MT.OREMATS.Ilmenite,2)),FL.array(MT.H2SO4.liquid(7*U,T)),FL.array(MT.MartianVitriol.fluid(6000,F)),OP.dust.mat(matList.MetatitanicAcid.mat,3));
        RM.Drying.addRecipeX(T,32,40, ST.array(OP.dust.mat(matList.MetatitanicAcid.mat,2)),FL.array(ZL_FS),FL.array(FL.DistW.make(200)),OP.dust.mat(MT.TiO2,1));
        recipeMaps.HeatMixer.addRecipeX(T,96,60,ZL_IS,FL.array(MT.TiCl4.liquid(5*U,T),MT.He.gas(10,T),MT.Mg.liquid(U*2,T)),ZL_FS,OP.dustSmall.mat(MT.Ti,6),OP.dust.mat(MT.MgCl2,6));
        recipeMaps.HeatMixer.addRecipe1(T,128,60,OP.dust.mat(MT.Mg,2),FL.array(MT.TiCl4.liquid(5*U,T),MT.He.gas(10,T)),ZL_FS,OP.dustSmall.mat(MT.Ti,6),OP.dust.mat(MT.MgCl2,6));
        recipeMaps.HeatMixer.addRecipeX(T,96,60,ZL_IS,FL.array(MT.TiCl4.liquid(5*U,T),MT.He.gas(10,T),MT.Na.liquid(U*2,T)),ZL_FS,OP.dustSmall.mat(MT.Ti,6),OP.dust.mat(MT.NaCl,6));
        recipeMaps.HeatMixer.addRecipe1(T,128,60,OP.dust.mat(MT.Na,2),FL.array(MT.TiCl4.liquid(5*U,T),MT.He.gas(10,T)),ZL_FS,OP.dustSmall.mat(MT.Ti,6),OP.dust.mat(MT.NaCl,6));
        recipeMaps.HeatMixer.addRecipeX(T,96,60,ZL_IS,FL.array(MT.TiCl4.liquid(5*U,T),MT.Ar.gas(1,T),MT.Mg.liquid(U*2,T)),ZL_FS,OP.dustSmall.mat(MT.Ti,6),OP.dust.mat(MT.MgCl2,6));
        recipeMaps.HeatMixer.addRecipe1(T,128,60,OP.dust.mat(MT.Mg,2),FL.array(MT.TiCl4.liquid(5*U,T),MT.Ar.gas(1,T)),ZL_FS,OP.dustSmall.mat(MT.Ti,6),OP.dust.mat(MT.MgCl2,6));
        recipeMaps.HeatMixer.addRecipeX(T,96,60,ZL_IS,FL.array(MT.TiCl4.liquid(5*U,T),MT.Ar.gas(1,T),MT.Na.liquid(U*2,T)),ZL_FS,OP.dustSmall.mat(MT.Ti,6),OP.dust.mat(MT.NaCl,6));
        recipeMaps.HeatMixer.addRecipe1(T,128,60,OP.dust.mat(MT.Na,2),FL.array(MT.TiCl4.liquid(5*U,T),MT.Ar.gas(1,T)),ZL_FS,OP.dustSmall.mat(MT.Ti,6),OP.dust.mat(MT.NaCl,6));

        //K2CO3
        RM.Drying.addRecipeX(T,32,40, ST.array(ZL_IS),FL.array(flList.PotassiumCarbonate.make(1000)),FL.array(FL.DistW.make(750)),OP.dust.mat(MT.K2CO3,1));
        RM.Bath.addRecipeX(T,0,20, ST.array(OP.dust.mat(MT.K2CO3,1)),FL.array(MT.H2O.liquid(U , T)),FL.array(flList.PotassiumCarbonate.make(1000)),ZL_IS);
        //NaCO3
        RM.Drying.addRecipeX(T,32,40, ST.array(ZL_IS),FL.array(flList.SodiumCarbonate.make(1000)),FL.array(FL.DistW.make(750)),OP.dust.mat(MT.Na2CO3,1));
        RM.Bath.addRecipeX(T,0,20, ST.array(OP.dust.mat(MT.Na2CO3,1)),FL.array(MT.H2O.liquid(U , T)),FL.array(flList.SodiumCarbonate.make(1000)),ZL_IS);
      //U Process
        RM.Bath.addRecipeX(F,0,200,ST.array(OP.dust.mat(MT.OREMATS.Uraninite,2)),FL.array(flList.SodiumCarbonate.make(2000)),FL.array(flList.UranylCarbonateSolution.make(2000)),matList.UraniniteCinder.getDust(1));
        RM.Bath.addRecipeX(F,0,200,ST.array(OP.dust.mat(MT.OREMATS.Pitchblende,2)),FL.array(flList.SodiumCarbonate.make(1000)),FL.array(flList.UranylCarbonateSolution.make(1000)),matList.PitchblendeCinder.getDust(2));

        RM.Mixer.addRecipe0(F,128,80,FL.array(MT.NH3.gas(U,false),flList.Methanol.make(1000)),FL.array(flList.MethylTertiaryAmine.make(1000),FL.Water.make(1000)),ZL_IS);
        RM.Mixer.addRecipe1(F,500,20,OP.dust.mat(matList.OleicAcid.get(),1),FL.array(FL.BioEthanol.make(1000),MT.H2SO4.liquid(0,false)),FL.array(flList.EthylOleate.make(1000)));
        RM.Mixer.addRecipe0(F,962,40,FL.array(flList.MethylTertiaryAmine.make(1000),flList.EthylOleate.make(1000),FL.Petrol.make(1000)),FL.array(flList.UraniumExtractant.make(1000)),ZL_IS);

        RM.Mixer.addRecipe0(F,128,20,FL.array(flList.UranylCarbonateSolution.make(1000),flList.UraniumExtractant.make(1000)),FL.array(flList.ExtractedUranium.make(1000),MT.H2S.gas(U,false)),ZL_IS);
        RM.Mixer.addRecipe0(F,128,20,FL.array(flList.ExtractedUranium.make(1000),flList.TributylPhosphate.make(1000)),FL.array(flList.UraniumExtractant.make(1000),flList.ExtractedUranium2.make(1000)),ZL_IS);
        RM.Mixer.addRecipe0(F,128,20,FL.array(flList.ExtractedUranium2.make(1000),MT.AquaRegia.liquid(2*U,false)),ZL_FS,matList.UraniumNitrateHexahydrate.getDust(3),OP.dust.mat(MT.NaCl,2));

        recipeMaps.HeatMixer.addRecipe1(F,1428,20,matList.UraniumNitrateHexahydrate.getDust(1),FL.array(MT.HF.gas(4*U,false)),ZL_FS,OP.dust.mat(MT.UF4,1));

        recipeMaps.HeatMixer.addRecipe1(F,468,20,OP.dust.mat(MT.PO4,1),FL.array(flList.Butanol.make(3000)),FL.array(flList.TributylPhosphate.make(1000),FL.Water.make(1000)),ZL_IS);

        recipeMaps.HeatMixer.addRecipe2(F,465,100,OP.dust.mat(MT.WaxParaffin,1),OP.dust.mat(MT.MnO2,0),FL.array(FL.Oxygen.make(1000)),ZL_FS,matList.OleicAcid.getDust(1));
        recipeMaps.HeatMixer.addRecipe1(F,120,600,OP.ingot.mat(MT.Butter,2),FL.array(MT.HCl.gas(0,false)),FL.array(MT.Glycerol.liquid(U,false)),matList.OleicAcid.getDust(3));
        recipeMaps.HeatMixer.addRecipe1(F,120,600,OP.ingot.mat(MT.Butter,2),FL.array(MT.H2SO4.liquid(0,false)),FL.array(MT.Glycerol.liquid(U,false)),matList.OleicAcid.getDust(3));

        RM.Centrifuge.addRecipe1(F,200,80,matList.PitchblendeCinder.getDust(2),ZL_FS,ZL_FS,OP.dust.mat(MT.Th,1),OP.dust.mat(MT.Pb,1),OP.dust.mat(MT.Na2SO4,2));
        RM.Centrifuge.addRecipe1(F,200,80,matList.UraniniteCinder.getDust(1),ZL_FS,ZL_FS,OP.dustSmall.mat(MT.Th,1),OP.dustTiny.mat(MT.Pb,3));

    }
}
