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
import gregapi.data.*;
import gregapi.util.OM;
import gregapi.util.ST;

import static gregapi.data.CS.*;
import static gregapi.data.OP.*;

public class OilProcessing {
    public static void init(){
//Oil
        recipeManager.HeatMixer.addRecipeX(false,32,200,ST.array(OP.dust.mat(MT.Cu,0), OP.dustTiny.mat(MT.Zn,0), OP.dustTiny.mat(MT.Cr,0)),FL.array(FL.Hydrogen.make(200),flList.CarbonMonoxide.make(100)),FL.array(flList.Methanol.make(120)));
        RM.Mixer.addRecipeX(false,64,640, ST.array(OM.dust(MT.NaOH,U)), FL.array(flList.Methanol.make(500), FL.Water.make(1500)),FL.array(flList.OilDesulfurizationer.make(2000)));
        RM.Mixer.addRecipeX(false,64,640, ST.array(OM.dust(MT.KOH,U)), FL.array(flList.Methanol.make(500), FL.Water.make(1500)),FL.array(flList.OilDesulfurizationer.make(2000)));

        RM.Mixer.addRecipe0(false,16,40,FL.array(FL.Oil_Light.make(150),FL.Water.make(20)), FL.array(FL.Saltwater.make(20),flList.DesaltOilLight.make(150)) );
        RM.Mixer.addRecipe0(false,16,40,FL.array(FL.Oil_Normal.make(140),FL.Water.make(20)), FL.array(FL.Saltwater.make(20),flList.DesaltOilNormal.make(140)));
        RM.Mixer.addRecipe0(false,16,40,FL.array(FL.Oil_Medium.make(130),FL.Water.make(20)), FL.array(FL.Saltwater.make(20),flList.DesaltOilMedium.make(130)));
        RM.Mixer.addRecipe0(false,16,40,FL.array(FL.Oil_Heavy.make(115),FL.Water.make(20)), FL.array(FL.Saltwater.make(20),flList.DesaltOilHeavy.make(115)) );
        RM.Mixer.addRecipe0(false,16,40,FL.array(FL.Oil_ExtraHeavy.make(115),FL.Water.make(20)), FL.array(FL.Saltwater.make(20),flList.DesaltOilExtraHeavy.make(100)));

        recipeManager.HeatMixer.addRecipeX(false,150,14, ST.array(OP.dust.mat(MT.Ni,0)), FL.array(FL.Hydrogen.make(5),flList.OilDesulfurizationer.make(4),flList.DesaltOilLight.make(100)),FL.array(flList.CleanedOilLight.make(100) ,flList.SulfuredOilDesulfurizationer.make(4)), ZL_IS);
        recipeManager.HeatMixer.addRecipeX(false,150,20, ST.array(OP.dust.mat(MT.Ni,0)), FL.array(FL.Hydrogen.make(5),flList.OilDesulfurizationer.make(6),flList.DesaltOilNormal.make(100)),FL.array(flList.CleanedOilNormal.make(100) ,flList.SulfuredOilDesulfurizationer.make(6)), ZL_IS);
        recipeManager.HeatMixer.addRecipeX(false,150,26, ST.array(OP.dust.mat(MT.Ni,0)), FL.array(FL.Hydrogen.make(5),flList.OilDesulfurizationer.make(8),flList.DesaltOilMedium.make(100)),FL.array(flList.CleanedOilMedium.make(100) ,flList.SulfuredOilDesulfurizationer.make(8)), ZL_IS);
        recipeManager.HeatMixer.addRecipeX(false,150,32, ST.array(OP.dust.mat(MT.Ni,0)), FL.array(FL.Hydrogen.make(5),flList.OilDesulfurizationer.make(10),flList.DesaltOilHeavy.make(100)),FL.array(flList.CleanedOilHeavy.make(100),flList.SulfuredOilDesulfurizationer.make(10)), ZL_IS);
        recipeManager.HeatMixer.addRecipeX(false,150,38, ST.array(OP.dust.mat(MT.Ni,0)), FL.array(FL.Hydrogen.make(5),flList.OilDesulfurizationer.make(12),flList.DesaltOilExtraHeavy.make(100)),FL.array(flList.CleanedOilExtraHeavy.make(100) ,flList.SulfuredOilDesulfurizationer.make(12)), ZL_IS);

        recipeManager.HeatMixer.addRecipe0(false,32,2400,FL.array(flList.SulfuredOilDesulfurizationer.make(1000),FL.Hydrogen.make(200)), FL.array(flList.OilDesulfurizationer.make(1000),MT.H2S.gas(U10,false)));

        recipeManager.TinyDistillTower.addRecipe0(false,128,100,FL.array(flList.CleanedOilLight.make(800)),FL.array(flList.InitalBottomOil.make(400),flList.Naphtha.make(300),flList.OilGas.make(200)),ZL_IS);
        recipeManager.TinyDistillTower.addRecipe0(false,128,100,FL.array(flList.CleanedOilMedium.make(800)),FL.array(flList.InitalBottomOil.make(425),flList.Naphtha.make(325),flList.OilGas.make(175)), ZL_IS);
        recipeManager.TinyDistillTower.addRecipe0(false,128,100,FL.array(flList.CleanedOilNormal.make(800)),FL.array(flList.InitalBottomOil.make(450),flList.Naphtha.make(350),flList.OilGas.make(150)), ZL_IS);
        recipeManager.TinyDistillTower.addRecipe0(false,128,100,FL.array(flList.CleanedOilHeavy.make(800)),FL.array(flList.InitalBottomOil.make(475),flList.Naphtha.make(375),flList.OilGas.make(125)), ZL_IS);
        recipeManager.TinyDistillTower.addRecipe0(false,128,100,FL.array(flList.CleanedOilExtraHeavy.make(800)),FL.array(flList.InitalBottomOil.make(500),flList.Naphtha.make(400),flList.OilGas.make(100)), ZL_IS);

        recipeManager.SmallDistillTower.addRecipe0(false,128,80,FL.array(flList.CleanedOilLight.make(800)),FL.array(flList.InitalBottomOil.make(400),flList.Naphtha.make(300),flList.OilGas.make(200)), ZL_IS);
        recipeManager.SmallDistillTower.addRecipe0(false,128,80,FL.array(flList.CleanedOilMedium.make(800)),FL.array(flList.InitalBottomOil.make(425),flList.Naphtha.make(325),flList.OilGas.make(175)), ZL_IS);
        recipeManager.SmallDistillTower.addRecipe0(false,128,80,FL.array(flList.CleanedOilNormal.make(800)),FL.array(flList.InitalBottomOil.make(450),flList.Naphtha.make(350),flList.OilGas.make(150)), ZL_IS);
        recipeManager.SmallDistillTower.addRecipe0(false,128,80,FL.array(flList.CleanedOilHeavy.make(800)),FL.array(flList.InitalBottomOil.make(475),flList.Naphtha.make(375),flList.OilGas.make(125)), ZL_IS);
        recipeManager.SmallDistillTower.addRecipe0(false,128,80,FL.array(flList.CleanedOilExtraHeavy.make(800)),FL.array(flList.InitalBottomOil.make(500),flList.Naphtha.make(400),flList.OilGas.make(100)), ZL_IS);

        recipeManager.DistillTower.addRecipe0(false,128,65,FL.array(flList.CleanedOilLight.make(800)),FL.array(flList.InitalBottomOil.make(400),flList.Naphtha.make(300),flList.OilGas.make(200)), ZL_IS);
        recipeManager.DistillTower.addRecipe0(false,128,65,FL.array(flList.CleanedOilMedium.make(800)),FL.array(flList.InitalBottomOil.make(425),flList.Naphtha.make(325),flList.OilGas.make(175)), ZL_IS);
        recipeManager.DistillTower.addRecipe0(false,128,65,FL.array(flList.CleanedOilNormal.make(800)),FL.array(flList.InitalBottomOil.make(450),flList.Naphtha.make(350),flList.OilGas.make(150)), ZL_IS);
        recipeManager.DistillTower.addRecipe0(false,128,65,FL.array(flList.CleanedOilHeavy.make(800)),FL.array(flList.InitalBottomOil.make(475),flList.Naphtha.make(375),flList.OilGas.make(125)), ZL_IS);
        recipeManager.DistillTower.addRecipe0(false,128,65,FL.array(flList.CleanedOilExtraHeavy.make(800)),FL.array(flList.InitalBottomOil.make(500),flList.Naphtha.make(400),flList.OilGas.make(100)), ZL_IS);

        recipeManager.SmallDistillTower.addRecipe0(F,196,120,FL.array(flList.InitalBottomOil.make(400)),FL.array(FL.Diesel.make(120),FL.Kerosine.make(200),flList.Naphtha.make(80),FL.Petrol.make(320),flList.OilGas.make(100)), OM.dust(matList.OilScarp.mat,U4));
        recipeManager.DistillTower     .addRecipe0(F,196, 95,FL.array(flList.InitalBottomOil.make(400)),FL.array(FL.Diesel.make(120),FL.Kerosine.make(200),flList.Naphtha.make(80),FL.Petrol.make(320),flList.OilGas.make(100)), OM.dust(matList.OilScarp.mat,U4));
        RM.Centrifuge.addRecipe0(F,80,100,FL.array(FL.Gas_Natural.make(400)),FL.array(FL.CarbonDioxide.make(4),FL.Methane.make(20),FL.Propane.make(160),FL.Butane.make(180)),ZL_IS);

        RM.Centrifuge.addRecipeX(F,64,80,new long[]{8000,8000} , ST.array(OM.dust(matList.OilScarp.get(),U4)),ZL_FS,FL.array(FL.Kerosine.make(20),FL.Lubricant.make(60)), dustTiny.mat(MT.WaxParaffin, 1), dustTiny.mat(MT.Asphalt, 1));
        RM.Centrifuge.addRecipeX(F,64,80,new long[]{8000,8000} , ST.array(OM.dust(matList.OilScarp.get(),U)),ZL_FS,FL.array(FL.Kerosine.make(180),FL.Lubricant.make(540)), dust.mat(MT.WaxParaffin, 1), dust.mat(MT.Asphalt, 1));

        recipeManager.DistillTower.addRecipe0(F, 64,  16, new long[] { 500,  500,  500}, FL.array(FL.Biomass       .make( 80)), FL.array(FL.Reikanol.make(20, FL.BioEthanol), MT.Glycerol.liquid(U50, F), FL.Methane.make(4), FL.DistW.make(50)), ZL_IS);

        //TODO
        recipeManager.DistillTower.addRecipe0(F, 64,  16, new long[] { 500,  500,  500}, FL.array(FL.BiomassIC2    .make( 80)), FL.array(FL.Reikanol.make(20, FL.BioEthanol), MT.Glycerol.liquid(U50, F), FL.Methane.make(4), FL.DistW.make(50)), ZL_IS);
        recipeManager.DistillTower.addRecipe0(F, 64,  64, new long[] {1000, 1000, 1000}, FL.array(FL.Oil_Soulsand  .make( 25)), FL.array(FL.Diesel.make( 5), FL.Kerosine.make( 5), FL.Petrol.make( 5), FL.Propane.make( 5), FL.Butane.make( 5), FL.lube(40)), dustTiny.mat(MT.WaxParaffin, 1), dustTiny.mat(MT.Asphalt, 1), dustTiny.mat(MT.PetCoke, 1));

        //Natural Gas
        RM.Centrifuge.addRecipe0(F,80,100,FL.array(FL.Gas_Natural.make(400)),FL.array(FL.CarbonDioxide.make(8),FL.Water.make(1),FL.Methane.make(380),FL.Nitrogen.make(8)),ZL_IS);

        RM.BurnMixer.addRecipe0(false,64,400,FL.array(FL.Methane.make(100),FL.Water.make(100)),FL.array(flList.CarbonMonoxide.make(40),FL.Hydrogen.make(280),FL.CarbonDioxide.make(30)));
        RM.BurnMixer.addRecipe0(false,64,400,FL.array(flList.Naphtha.make(100),FL.Water.make(100)),FL.array(flList.CarbonMonoxide.make(50),FL.Hydrogen.make(250),FL.CarbonDioxide.make(30)));

        RM.BurnMixer.addRecipe1(false,16,40,OP.dust.mat(MT.C,1),FL.array(FL.Oxygen.make(80)),FL.array(flList.CarbonMonoxide.make(20),FL.CarbonDioxide.make(30)));
        RM.BurnMixer.addRecipe1(false,16,40, gem.mat(MT.CoalCoke,1),FL.array(FL.Oxygen.make(120)),FL.array(flList.CarbonMonoxide.make(30),FL.CarbonDioxide.make(45)));

        RM.Mixer.addRecipe1(false,64,80,OP.dust.mat(MT.Pt,0),FL.array(MT.NH3.gas(400,false),FL.Oxygen.make(500)),FL.array(MT.NO.fluid(4*U10,false),FL.Water.make(600)));

        recipeManager.HeatMixer.addRecipe2(false,64,200,OP.dust.mat(MT.Fe,0),OP.dust.mat(MT.Mo,0),FL.array(FL.Methane.make(100),FL.Oxygen.make(100)),FL.array(flList.Formaldehyde.make(100),FL.Water.make(100)));

        recipeManager.HeatMixer.addRecipe1(false,64,80,OP.dust.mat(MT.Mo,0),FL.array(flList.Methanol.make(100),FL.Oxygen.make(50)),FL.array(flList.Formaldehyde.make(100),FL.Water.make(100)));
        recipeManager.HeatMixer.addRecipe1(false,64,80,OP.dust.mat(MT.Ag,0),FL.array(flList.Methanol.make(100),FL.Oxygen.make(50)),FL.array(flList.Formaldehyde.make(100),FL.Water.make(100)));

        recipeManager.HeatMixer.addRecipe1(false,64,80,OP.dust.mat(MT.Mo,0),FL.array(FL.BioEthanol.make(100),FL.Oxygen.make(50)),FL.array(flList.Acetaldehyde.make(100),FL.Water.make(100)));
        recipeManager.HeatMixer.addRecipe1(false,64,80,OP.dust.mat(MT.Ag,0),FL.array(FL.BioEthanol.make(100),FL.Oxygen.make(50)),FL.array(flList.Acetaldehyde.make(100),FL.Water.make(100)));

        recipeManager.HeatMixer.addRecipe1(false,64,80,OP.dust.mat(MT.Mo,0),FL.array(flList.Propanol.make(100),FL.Oxygen.make(50)),FL.array(flList.Propionaldehyde.make(100),FL.Water.make(100)));
        recipeManager.HeatMixer.addRecipe1(false,64,80,OP.dust.mat(MT.Ag,0),FL.array(flList.Propanol.make(100),FL.Oxygen.make(50)),FL.array(flList.Propionaldehyde.make(100),FL.Water.make(100)));

        RM.Mixer.addRecipe0(false,64,20,FL.array(flList.Propanol.make(100),MT.H2SO4.liquid(2000,false)),FL.array(FL.Propylene.make(100),MT.H2SO4.liquid(1000,false)));


        RM.Mixer.addRecipe0(false,64,20,FL.array(FL.BioEthanol.make(100),MT.H2SO4.liquid(2000,false)),FL.array(FL.Ethylene.make(100),MT.H2SO4.liquid(1000,false)));

        //Coal Boiling
        RM.Centrifuge.addRecipe0(false,64,200,FL.array(flList.CoalTar.make(100)),FL.array(flList.Benzene.make(30),FL.Hydrogen.make(20),FL.Nitrogen.make(10),flList.CarbonMonoxide.make(70)),matList.Naphthalene.getDustTiny(1),matList.Naphthalene.getDustTiny(1));

        RM.Centrifuge.addRecipe0(false,64,100,flList.WoodTar.make(100),FL.array(FL.Methane.make(10),FL.CarbonDioxide.make(55),flList.CarbonMonoxide.make(20),FL.Hydrogen.make(5),FL.Ethylene.make(10),FL.Oil_Olive.make(60)));

        RM.SteamCracking.addRecipe1(F, 16,  64, OP.dust.mat(MT.Kaolinite,0),FL.array(FL.Steam.make(1000), flList.Naphtha.make(100)), FL.array(FL.Hydrogen.make( 10), flList.OilGas.make(22), FL.Petrol.make(47), FL.Lubricant.make(19)), ZL_IS);

        RM.SteamCracking.addRecipe1(F, 16,  64, OP.dust.mat(MT.Kaolinite,0),FL.array(FL.Steam.make(1000), FL.Diesel.make(100)), FL.array(FL.Hydrogen.make( 2), flList.OilGas.make(18), FL.Petrol.make(65), FL.Lubricant.make(9)), ZL_IS);

        RM.Centrifuge.addRecipe0(false,256,200,flList.OilGas.make(100),FL.array(FL.Propane.make(40),FL.Butane.make(40),FL.Nitrogen.make(5),FL.CarbonDioxide.make(5)));

        RM.Mixer.addRecipe0(false,32,40,FL.array(flList.OilGas.make(100),flList.Benzene.make(20),FL.Hydrogen.make(80)),FL.array(FL.Diesel.make(60),flList.CrackedOilGas.make(100)),ZL_IS);

        recipeManager.DistillTower.addRecipe0(false,256,44,FL.array(flList.OilGas.make(1000)),FL.array(FL.Methane.make(372),flList.Ethane.make(262),FL.Propane.make(188),FL.Butane.make(138),FL.CarbonDioxide.make(40)),ZL_IS);

        recipeManager.DistillTower.addRecipe0(false,256,44,FL.array(flList.CrackedOilGas.make(1000)),FL.array(FL.Methane.make(183),flList.Ethane.make(152),FL.Propane.make(133),FL.Butane.make(128),FL.CarbonDioxide.make(134),flList.Butadiene.make(134),flList.Benzene.make(92),flList.Toluene.make(23),flList.Phenol.make(21)),ZL_IS);

        FM.Burn         .addRecipe0(T, - 64,  5, flList.Naphtha.make(1), FL.CarbonDioxide.make(1), ZL_IS);
        FM.Engine       .addRecipe0(T, - 64,  7, flList.Naphtha.make(1), FL.CarbonDioxide.make(1), ZL_IS);

        FM.Burn         .addRecipe0(T, - 58,  5, flList.Toluene.make(1), FL.CarbonDioxide.make(1), ZL_IS);

        FM.Burn         .addRecipe0(T, - 64,  27, flList.OilGas.make(5), FL.Water.make(6), FL.CarbonDioxide.make(3));
        FM.Gas          .addRecipe0(T, - 64,  32, flList.OilGas.make(5), FL.Water.make(6), FL.CarbonDioxide.make(3));

        FM.Burn         .addRecipe0(T, - 64,  9 ,flList.CarbonMonoxide.make(5), FL.CarbonDioxide.make(5), ZL_IS);
        FM.Gas          .addRecipe0(T, - 64,  13,flList.CarbonMonoxide.make(5), FL.CarbonDioxide.make(5), ZL_IS);

        FM.Burn         .addRecipe0(T, - 16, 48, flList.DesaltOilExtraHeavy.make(1)                   , FL.CarbonDioxide.make(1), dustTiny.mat(MT.DarkAsh,1));
        FM.Burn         .addRecipe0(T, - 16, 36, flList.DesaltOilHeavy.make(1)                        , FL.CarbonDioxide.make(1), dustTiny.mat(MT.DarkAsh,1));
        FM.Burn         .addRecipe0(T, - 16, 24, flList.DesaltOilMedium.make(1)                       , FL.CarbonDioxide.make(1), dustTiny.mat(MT.DarkAsh,1));
        FM.Burn         .addRecipe0(T, - 16, 18, flList.DesaltOilLight.make(1)                        , FL.CarbonDioxide.make(1), dustTiny.mat(MT.DarkAsh,1));
        FM.Burn         .addRecipe0(T, - 16, 24, flList.DesaltOilNormal.make(1)                       , FL.CarbonDioxide.make(1), dustTiny.mat(MT.DarkAsh,1));

        FM.Burn         .addRecipe0(T, - 16, 8, flList.WoodTar.make(1)                       , FL.CarbonDioxide.make(1), dustDiv72.mat(MT.DarkAsh,1));
        FM.Burn         .addRecipe0(T, - 16, 8, flList.CoalTar.make(1)                       , FL.CarbonDioxide.make(1), dustDiv72.mat(MT.DarkAsh,1));


        RM.Mixer.addRecipe1(false,64,20,ST.tag(1),FL.array(FL.Petrol.make(80),FL.Diesel.make(20)),FL.array(flList.BlendedFuel1.make(100)),ZL_IS);
        FM.Burn         .addRecipe0(T, - 120,  5, flList.BlendedFuel1.make(1), FL.CarbonDioxide.make(1), ZL_IS);
        FM.Engine       .addRecipe0(T, - 120,  6, flList.BlendedFuel1.make(1), FL.CarbonDioxide.make(1), ZL_IS);

        RM.Mixer.addRecipe1(false,64,20,ST.tag(2),FL.array(flList.Methanol.make(60),FL.Petrol.make(30),flList.Naphtha.make(8)),FL.array(flList.BlendedFuel2.make(70)),ZL_IS);
        FM.Burn         .addRecipe0(T, - 144,  5, flList.BlendedFuel2.make(1), FL.CarbonDioxide.make(1), ZL_IS);
        FM.Engine       .addRecipe0(T, - 144,  6, flList.BlendedFuel2.make(1), FL.CarbonDioxide.make(1), ZL_IS);

        RM.Mixer.addRecipe1(false,64,20,ST.tag(3),FL.array(FL.Petrol.make(70),flList.Propanol.make(25),flList.Methanol.make(7)),FL.array(flList.BlendedFuel3.make(65)),ZL_IS);
        FM.Burn         .addRecipe0(T, - 152,  5, flList.BlendedFuel3.make(1), FL.CarbonDioxide.make(1), ZL_IS);
        FM.Engine       .addRecipe0(T, - 152,  6, flList.BlendedFuel3.make(1), FL.CarbonDioxide.make(1), ZL_IS);

        RM.Mixer.addRecipe1(false,64,20,ST.tag(4),FL.array(flList.Toluene.make(20),FL.Petrol.make(40),flList.Methanol.make(65),flList.Naphtha.make(15)),FL.array(flList.BlendedFuel4.make(70)),ZL_IS);
        FM.Burn         .addRecipe0(T, - 164,  5, flList.BlendedFuel4.make(1), FL.CarbonDioxide.make(1), ZL_IS);
        FM.Engine       .addRecipe0(T, - 164,  6, flList.BlendedFuel4.make(1), FL.CarbonDioxide.make(1), ZL_IS);

        RM.Mixer.addRecipe1(false,64,20,ST.tag(5),FL.array(flList.Toluene.make(20),FL.Petrol.make(40),flList.Methanol.make(65),MT.Glyceryl.fluid(U100, T),flList.Naphtha.make(20)),FL.array(flList.BlendedFuel5.make(55)),ZL_IS);
        FM.Burn         .addRecipe0(T, - 208,  5, flList.BlendedFuel5.make(1), FL.CarbonDioxide.make(1), ZL_IS);
        FM.Engine       .addRecipe0(T, - 208,  6, flList.BlendedFuel5.make(1), FL.CarbonDioxide.make(1), ZL_IS);

        RM.Mixer.addRecipe1(false,64,20,ST.tag(1),FL.array(FL.BioEthanol.make(80),FL.Petrol.make(50)),FL.array(flList.BioFuel1.make(100)),ZL_IS);
        FM.Burn         .addRecipe0(T, - 108,  5, flList.BioFuel1.make(1), FL.CarbonDioxide.make(1), ZL_IS);
        FM.Engine       .addRecipe0(T, - 108,  6, flList.BioFuel1.make(1), FL.CarbonDioxide.make(1), ZL_IS);

        RM.Mixer.addRecipe1(false,64,20,ST.tag(2),FL.array(FL.BioEthanol.make(60),FL.Petrol.make(40),flList.Toluene.make(12)),FL.array(flList.BioFuel2.make(100)),ZL_IS);
        FM.Burn         .addRecipe0(T, - 120,  5, flList.BioFuel2.make(1), FL.CarbonDioxide.make(1), ZL_IS);
        FM.Engine       .addRecipe0(T, - 120,  6, flList.BioFuel2.make(1), FL.CarbonDioxide.make(1), ZL_IS);


    }
}

