package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.data.RM;
import gregapi.oredict.OreDictMaterial;
import gregapi.util.OM;
import gregapi.util.ST;

import static cn.kuzuanpa.ktfruaddon.recipe.recipeManager.HeatMixer;
import static gregapi.data.CS.*;

public class OreProcessing {
    public OreProcessing() {
        //Cr processing
        RM.Bath.addRecipeX(T,0, 512 , ST.array(OM.dust(OreDictMaterial.get(9113), U*2), OM.dust(OreDictMaterial.get(30014), U*9)), FL.array(MT.H2SO4.liquid(6000,T)), FL.array(FL.Water.make(4000)), OM.dust(OreDictMaterial.get(30012), U*6), OM.dust(OreDictMaterial.get(30013), U*3));
        RM.Mixer.addRecipeX(T,64,82,ST.array(ZL_IS),FL.array(MT.NH3.liquid(2000,T),MT.H2SO4.liquid(U , T)), FL.array(ZL_FS),OM.dust(OreDictMaterial.get(30014),U*9));
        HeatMixer.addRecipeX(T,256,160,ST.array(OM.dust(OreDictMaterial.get(30011))),FL.array(MT.O.gas(U *4,T )),FL.array(FL.Nitrogen.make(250)),OM.dust(MT.CrO2, U));
  //Al processing
        //Step1:Bauxide process
        HeatMixer.addRecipeX(T,120,80,ST.array(OM.dust(MT.CaCO3,U),OM.dust(MT.Na2CO3,U *2), OM.dust(OreDictMaterial.get(9105),U)),ZL_FS,FL.CarbonDioxide.make(3000),OM.dust(OreDictMaterial.get(30020),U*2));
        RM.Bath.addRecipeX(T, 0, 80, ST.array(OM.dust(OreDictMaterial.get(9105),U)),FL.array(MT.H2SO4.liquid(U *9 ,T)),FL.array(FL.make("acidpickledbauxide",2000)),OM.dust(MT.SiO2,U));
        RM.Bath.addRecipeX(T, 0, 180, ST.array(OM.dust(OreDictMaterial.get(9105),U),OM.dust(MT.NaOH,U*2)),FL.array(MT.H2O.liquid(1000 ,T)),FL.array(FL.make("sodiumaluminate",2000)),OM.dust(MT.SiO2,U4),OM.crushedCentrifugedTiny(OreDictMaterial.get(9120),1));
        //Step2
        RM.Bath.addRecipeX(T, 0, 180, ST.array(OM.dust(OreDictMaterial.get(30020),U*2),OM.dust(MT.NaOH,U*2)),FL.array(MT.H2O.liquid(1000 ,T)),FL.array(FL.make("sodiumaluminate",2000)),OM.dust(MT.SiO2,U4),OM.dust(OreDictMaterial.get(30022),U));
        HeatMixer.addRecipeX(T,120,60,ST.array(OM.dust(MT.NaOH,U * 4)),FL.array(FL.make("acidpickledbauxide",2000)),FL.array(FL.make("mixtureoffe2o3na2so4",1000),FL.make("sodiumaluminate",2000)),ZL_IS);
        RM.Mixer.addRecipeX(T,0, 800,ST.array(ZL_IS),FL.array(FL.make("sodiumaluminate",1000),FL.Soda.make(1000)),FL.array(FL.make("asodiumcarbonate",2000)),OM.dust(MT.AlO3H3,U*4));
        //Step3: Centrifuge for mixtures
        RM.Centrifuge.addRecipeX(T, 64, 40,ST.array(OM.dust(OreDictMaterial.get(30022),U)),FL.array(ZL_FS),FL.array(ZL_FS),OM.dust(MT.Fe2O3,U),OM.crushedCentrifugedTiny(MT.TiO2,1),OM.dust(MT.SiO2,U4));
        RM.Centrifuge.addRecipeX(T, 32, 20, ST.array(ZL_IS),FL.array(FL.make("mixtureoffe2o3na2so4",1000)),FL.array(ZL_FS),OM.dust(MT.Na2CO3,U),OM.dust(MT.Fe2O3,U));
      //Use K for sone recipe
        //Step1
        RM.Bath.addRecipeX(T, 0, 180, ST.array(OM.dust(OreDictMaterial.get(9105),U),OM.dust(MT.KOH,U*2)),FL.array(MT.H2O.liquid(1000 ,T)),FL.array(FL.make("potassiumaluminate",2000)),OM.dust(MT.SiO2,U4),OM.crushedCentrifugedTiny(OreDictMaterial.get(9120),1));
        //Step2
        HeatMixer.addRecipeX(T,120,60,ST.array(OM.dust(MT.KOH,U * 4)),FL.array(FL.make("acidpickledbauxide",2000)),FL.array(FL.make("mixtureoffe2o3k2so4",1000),FL.make("potassiumaluminate",2000)),ZL_IS);
        RM.Mixer.addRecipeX(T,0, 800,ST.array(ZL_IS),FL.array(FL.make("potassiumaluminate",1000),FL.Soda.make(1000)),FL.array(FL.make("asodiumcarbonate",2000)),OM.dust(MT.AlO3H3,U*4));
        //Step3
        RM.Centrifuge.addRecipeX(T, 32, 20, ST.array(ZL_IS),FL.array(FL.make("mixtureoffe2o3k2so4",1000)),FL.array(ZL_FS),OM.dust(MT.K2CO3,U),OM.dust(MT.Fe2O3,U));
      //transformation between solution and dusts
        //NaAlO2
        RM.Drying.addRecipeX(T,32,40, ST.array(ZL_IS),FL.array(FL.make("sodiumaluminate",1000)),FL.array(FL.Water_Boiling.make(500)),OM.dust(MT.NaAlO2,U));
        RM.Mixer.addRecipeX(T,16,10, ST.array(OM.dust(MT.NaAlO2,U)),FL.array(MT.H2O.liquid(U , T)),FL.array(FL.make("sodiumaluminate",1000)),ZL_IS);
        RM.Bath.addRecipeX(T,0,20, ST.array(OM.dust(MT.NaAlO2,U)),FL.array(MT.H2O.liquid(U , T)),FL.array(FL.make("sodiumaluminate",1000)),ZL_IS);
        //KAlO2
        RM.Drying.addRecipeX(T,32,40, ST.array(ZL_IS),FL.array(FL.make("potassiumaluminate",1000)),FL.array(FL.Water_Boiling.make(500)),OM.dust(MT.KAlO2,U));
        RM.Mixer.addRecipeX(T,16,10, ST.array(OM.dust(MT.KAlO2,U)),FL.array(MT.H2O.liquid(U , T)),FL.array(FL.make("potassiumaluminate",1000)),ZL_IS);
        RM.Bath.addRecipeX(T,0,20, ST.array(OM.dust(MT.KAlO2,U)),FL.array(MT.H2O.liquid(U , T)),FL.array(FL.make("potassiumaluminate",1000)),ZL_IS);
        //K2CO3
        RM.Drying.addRecipeX(T,32,40, ST.array(ZL_IS),FL.array(FL.make("potassiumcarbonate",1000)),FL.array(FL.Water_Boiling.make(500)),OM.dust(MT.K2CO3,U));
        RM.Mixer.addRecipeX(T,16,10, ST.array(OM.dust(MT.K2CO3,U)),FL.array(MT.H2O.liquid(U , T)),FL.array(FL.make("potassiumcarbonate",1000)),ZL_IS);
        RM.Bath.addRecipeX(T,0,20, ST.array(OM.dust(MT.K2CO3,U)),FL.array(MT.H2O.liquid(U , T)),FL.array(FL.make("potassiumcarbonate",1000)),ZL_IS);
        //NaCO3
        RM.Drying.addRecipeX(T,32,40, ST.array(ZL_IS),FL.array(FL.make("asodiumcarbonate",1000)),FL.array(FL.Water_Boiling.make(500)),OM.dust(MT.Na2CO3,U));
        RM.Mixer.addRecipeX(T,16,10, ST.array(OM.dust(MT.Na2CO3,U)),FL.array(MT.H2O.liquid(U , T)),FL.array(FL.make("asodiumcarbonate",1000)),ZL_IS);
        RM.Bath.addRecipeX(T,0,20, ST.array(OM.dust(MT.Na2CO3,U)),FL.array(MT.H2O.liquid(U , T)),FL.array(FL.make("asodiumcarbonate",1000)),ZL_IS);
      //W Process
        RM.Autoclave.addRecipeX(T, 0, 400,ST.array(OM.dust(OreDictMaterial.get("wolframite"),U),OM.dust(MT.NaOH,U*4)),FL.array(MT.H2O.gas(20000,T)));








    }
}
