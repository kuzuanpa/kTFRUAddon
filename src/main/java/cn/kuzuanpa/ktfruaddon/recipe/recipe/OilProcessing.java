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
import cn.kuzuanpa.ktfruaddon.material.matList;
import cn.kuzuanpa.ktfruaddon.recipe.recipeManager;
import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.data.RM;
import gregapi.recipes.Recipe;
import gregapi.util.OM;
import gregapi.util.ST;

import static gregapi.data.CS.*;
import static gregapi.data.OP.dustTiny;

public class OilProcessing {
    public OilProcessing() {
//Oil
        RM.Mixer.addRecipe0(false,64,400,FL.array(FL.Hydrogen.make(200),flList.CarbonMonoxide.make(100)),FL.array(flList.Methanol.make(120)));
        RM.Mixer.addRecipeX(false,64,640, ST.array(OM.dust(MT.NaOH,U)), FL.array(flList.Methanol.make(500), FL.Water.make(1500)),FL.array(flList.OilDesulphurizationAgent.make(2000)));
        RM.Mixer.addRecipeX(false,64,640, ST.array(OM.dust(MT.KOH,U)), FL.array(flList.Methanol.make(500), FL.Water.make(1500)),FL.array(flList.OilDesulphurizationAgent.make(2000)));

        RM.Mixer.addRecipe0(false,16,40,FL.array(FL.Oil_Light.make(150),FL.Water.make(20)), FL.array(FL.Saltwater.make(20),flList.DesaltOilLight.make(150)) );
        RM.Mixer.addRecipe0(false,16,40,FL.array(FL.Oil_Normal.make(140),FL.Water.make(20)), FL.array(FL.Saltwater.make(20),flList.DesaltOilNormal.make(140)));
        RM.Mixer.addRecipe0(false,16,40,FL.array(FL.Oil_Medium.make(130),FL.Water.make(20)), FL.array(FL.Saltwater.make(20),flList.DesaltOilMedium.make(130)));
        RM.Mixer.addRecipe0(false,16,40,FL.array(FL.Oil_Heavy.make(115),FL.Water.make(20)), FL.array(FL.Saltwater.make(20),flList.DesaltOilHeavy.make(115)) );
        RM.Mixer.addRecipe0(false,16,40,FL.array(FL.Oil_ExtraHeavy.make(115),FL.Water.make(20)), FL.array(FL.Saltwater.make(20),flList.DesaltOilExtraHeavy.make(100)));

        recipeManager.HeatMixer.addRecipeX(false,130,50, ST.array(OP.dust.mat(MT.Ni,0)), FL.array(FL.Hydrogen.make(5),flList.OilDesulphurizationAgent.make(1),flList.DesaltOilLight.make(150)),FL.array(flList.CleanedOilLight.make(150)), OM.dust(MT.PetCoke,U9));
        recipeManager.HeatMixer.addRecipeX(false,130,50, ST.array(OP.dust.mat(MT.Ni,0)), FL.array(FL.Hydrogen.make(5),flList.OilDesulphurizationAgent.make(1),flList.DesaltOilNormal.make(140)),FL.array(flList.CleanedOilNormal.make(140)), OM.dust(MT.PetCoke,U9));
        recipeManager.HeatMixer.addRecipeX(false,130,50, ST.array(OP.dust.mat(MT.Ni,0)), FL.array(FL.Hydrogen.make(5),flList.OilDesulphurizationAgent.make(1),flList.DesaltOilMedium.make(130)),FL.array(flList.CleanedOilMedium.make(130)), OM.dust(MT.PetCoke,U9));
        recipeManager.HeatMixer.addRecipeX(false,130,50, ST.array(OP.dust.mat(MT.Ni,0)), FL.array(FL.Hydrogen.make(5),flList.OilDesulphurizationAgent.make(1),flList.DesaltOilHeavy.make(120)),FL.array(flList.CleanedOilHeavy.make(120)), OM.dust(MT.PetCoke,U9));
        recipeManager.HeatMixer.addRecipeX(false,130,50, ST.array(OP.dust.mat(MT.Ni,0)), FL.array(FL.Hydrogen.make(5),flList.OilDesulphurizationAgent.make(1),flList.DesaltOilExtraHeavy.make(100)),FL.array(flList.CleanedOilExtraHeavy.make(110)), OM.dust(MT.PetCoke,U9));

        recipeManager.TinyDistillTower.addRecipe0(false,128,100,FL.array(flList.CleanedOilLight.make(800)),FL.array(flList.InitalBottomOil.make(400),flList.Naphtha.make(300),flList.OilGas.make(200)),ZL_IS);
        recipeManager.TinyDistillTower.addRecipe0(false,128,100,FL.array(flList.CleanedOilMedium.make(800)),FL.array(flList.InitalBottomOil.make(425),flList.Naphtha.make(325),flList.OilGas.make(175)), ZL_IS);
        recipeManager.TinyDistillTower.addRecipe0(false,128,100,FL.array(flList.CleanedOilNormal.make(800)),FL.array(flList.InitalBottomOil.make(450),flList.Naphtha.make(350),flList.OilGas.make(150)), ZL_IS);
        recipeManager.TinyDistillTower.addRecipe0(false,128,100,FL.array(flList.CleanedOilHeavy.make(800)),FL.array(flList.InitalBottomOil.make(475),flList.Naphtha.make(375),flList.OilGas.make(125)), ZL_IS);
        recipeManager.TinyDistillTower.addRecipe0(false,128,100,FL.array(flList.CleanedOilExtraHeavy.make(800)),FL.array(flList.InitalBottomOil.make(500),flList.Naphtha.make(400),flList.OilGas.make(100)), ZL_IS);

        recipeManager.SmallDistillTower.addRecipe0(false,128,75,FL.array(flList.CleanedOilLight.make(800)),FL.array(flList.InitalBottomOil.make(400),flList.Naphtha.make(300),flList.OilGas.make(200)), ZL_IS);
        recipeManager.SmallDistillTower.addRecipe0(false,128,75,FL.array(flList.CleanedOilMedium.make(800)),FL.array(flList.InitalBottomOil.make(425),flList.Naphtha.make(325),flList.OilGas.make(175)), ZL_IS);
        recipeManager.SmallDistillTower.addRecipe0(false,128,75,FL.array(flList.CleanedOilNormal.make(800)),FL.array(flList.InitalBottomOil.make(450),flList.Naphtha.make(350),flList.OilGas.make(150)), ZL_IS);
        recipeManager.SmallDistillTower.addRecipe0(false,128,75,FL.array(flList.CleanedOilHeavy.make(800)),FL.array(flList.InitalBottomOil.make(475),flList.Naphtha.make(375),flList.OilGas.make(125)), ZL_IS);
        recipeManager.SmallDistillTower.addRecipe0(false,128,75,FL.array(flList.CleanedOilExtraHeavy.make(800)),FL.array(flList.InitalBottomOil.make(500),flList.Naphtha.make(400),flList.OilGas.make(100)), ZL_IS);

        recipeManager.DistillTower.addRecipe0(false,128,50,FL.array(flList.CleanedOilLight.make(800)),FL.array(flList.InitalBottomOil.make(400),flList.Naphtha.make(300),flList.OilGas.make(200)), ZL_IS);
        recipeManager.DistillTower.addRecipe0(false,128,50,FL.array(flList.CleanedOilMedium.make(800)),FL.array(flList.InitalBottomOil.make(425),flList.Naphtha.make(325),flList.OilGas.make(175)), ZL_IS);
        recipeManager.DistillTower.addRecipe0(false,128,50,FL.array(flList.CleanedOilNormal.make(800)),FL.array(flList.InitalBottomOil.make(450),flList.Naphtha.make(350),flList.OilGas.make(150)), ZL_IS);
        recipeManager.DistillTower.addRecipe0(false,128,50,FL.array(flList.CleanedOilHeavy.make(800)),FL.array(flList.InitalBottomOil.make(475),flList.Naphtha.make(375),flList.OilGas.make(125)), ZL_IS);
        recipeManager.DistillTower.addRecipe0(false,128,50,FL.array(flList.CleanedOilExtraHeavy.make(800)),FL.array(flList.InitalBottomOil.make(500),flList.Naphtha.make(400),flList.OilGas.make(100)), ZL_IS);

        recipeManager.SmallDistillTower.addRecipe0(F,256,80,FL.array(flList.InitalBottomOil.make(400)),FL.array(FL.Diesel.make(100),FL.Kerosine.make(200),flList.Naphtha.make(100),FL.Petrol.make(300),flList.OilGas.make(100)), OM.dust(matList.OilScarp.mat,U4));
        recipeManager.DistillTower.addRecipe0(F,256,60,FL.array(flList.InitalBottomOil.make(400)),FL.array(FL.Diesel.make(100),FL.Kerosine.make(200),flList.Naphtha.make(100),FL.Petrol.make(300),flList.OilGas.make(100)), OM.dust(matList.OilScarp.mat,U4));

        RM.Centrifuge.addRecipeX(F,64,80,new long[]{8000,8000,6000} , ST.array(OM.dust(matList.OilScarp.get(),U4)),ZL_FS,FL.array(FL.Kerosine.make(40),FL.Lubricant.make(60)), dustTiny.mat(MT.WaxParaffin, 1), dustTiny.mat(MT.Asphalt, 1));

        recipeManager.DistillTower.addRecipe0(F, 64,  16, new long[] { 500,  500,  500}, FL.array(FL.Biomass       .make( 80)), FL.array(FL.Reikanol.make(20, FL.BioEthanol), MT.Glycerol.liquid(U50, F), FL.Methane.make(4), FL.DistW.make(50)), ZL_IS);
        recipeManager.DistillTower.addRecipe0(F, 64,  16, new long[] { 500,  500,  500}, FL.array(FL.BiomassIC2    .make( 80)), FL.array(FL.Reikanol.make(20, FL.BioEthanol), MT.Glycerol.liquid(U50, F), FL.Methane.make(4), FL.DistW.make(50)), ZL_IS);
        recipeManager.DistillTower.addRecipe0(F, 64,  64, new long[] {1000, 1000, 1000}, FL.array(FL.Oil_Soulsand  .make( 25)), FL.array(FL.Diesel.make( 5), FL.Kerosine.make( 5), FL.Petrol.make( 5), FL.Propane.make( 5), FL.Butane.make( 5), FL.lube(40)), dustTiny.mat(MT.WaxParaffin, 1), dustTiny.mat(MT.Asphalt, 1), dustTiny.mat(MT.PetCoke, 1));
//Natural Gas
        RM.Centrifuge.addRecipe0(F,80,100,FL.array(FL.Gas_Natural.make(400)),FL.array(FL.CarbonDioxide.make(8),FL.Water.make(1),FL.Methane.make(380),FL.Nitrogen.make(8)),ZL_IS);

        //Coal Dry Carbonization

        RM.BurnMixer.addRecipe0(false,64,400,FL.array(FL.Methane.make(100),FL.Water.make(100)),FL.array(flList.CarbonMonoxide.make(40),FL.Hydrogen.make(280),FL.CarbonDioxide.make(30)));
        RM.BurnMixer.addRecipe0(false,64,400,FL.array(flList.Naphtha.make(100),FL.Water.make(100)),FL.array(flList.CarbonMonoxide.make(50),FL.Hydrogen.make(250),FL.CarbonDioxide.make(30)));

        RM.Mixer.addRecipe1(false,64,80,OP.dust.mat(MT.Pt,0),FL.array(FL.Nitrogen.make(400),FL.Oxygen.make(500)),FL.array(MT.NO.fluid(4*U10,false),FL.Water.make(600)));

        recipeManager.HeatMixer.addRecipe2(false,64,200,OP.dust.mat(MT.Fe,0),OP.dust.mat(MT.Mo,0),FL.array(FL.Methane.make(100),FL.Oxygen.make(100)),FL.array(flList.Formaldehyde.make(100),FL.Water.make(100)));

        RM.Mixer.addRecipe0(false,64,20,FL.array(flList.Propanol.make(100),MT.H2SO4.liquid(2000,false)),FL.array(FL.Propylene.make(100),MT.H2SO4.liquid(1000,false)));
        RM.Mixer.addRecipe0(false,64,20,FL.array(FL.BioEthanol.make(100),MT.H2SO4.liquid(2000,false)),FL.array(FL.Ethylene.make(100),MT.H2SO4.liquid(1000,false)));

        //RM.CokeOven
    }
}

