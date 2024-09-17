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
import cn.kuzuanpa.ktfruaddon.recipe.recipeMaps;
import gregapi.data.*;
import gregapi.util.OM;
import gregapi.util.ST;

import static gregapi.data.CS.*;
import static gregapi.data.OP.*;

public class OilProcessing {
    public static void init(){
//Oil
        recipeMaps.HeatMixer.addRecipeX(false,32,200,ST.array(OP.dust.mat(MT.Cu,0), OP.dustTiny.mat(MT.Zn,0), OP.dustTiny.mat(MT.Cr,0)),FL.array(FL.Hydrogen.make(200),flList.CarbonMonoxide.make(100)),FL.array(flList.Methanol.make(120)));
        RM.Mixer.addRecipeX(false,64,640, ST.array(OM.dust(MT.NaOH,U)), FL.array(flList.Methanol.make(500), FL.Water.make(1500)),FL.array(flList.OilDesulfurizationer.make(2000)));
        RM.Mixer.addRecipeX(false,64,640, ST.array(OM.dust(MT.KOH,U)), FL.array(flList.Methanol.make(500), FL.Water.make(1500)),FL.array(flList.OilDesulfurizationer.make(2000)));

        RM.Centrifuge.addRecipe0(false,32,25,FL.array(flList.AqueousOilLight     .make(100)), FL.array(FL.Saltwater.make(25),flList.DesaltOilLight     .make(75)));
        RM.Centrifuge.addRecipe0(false,32,30,FL.array(flList.AqueousOilNormal    .make(100)), FL.array(FL.Saltwater.make(25),flList.DesaltOilNormal    .make(75)));
        RM.Centrifuge.addRecipe0(false,32,30,FL.array(flList.AqueousOilMedium    .make(100)), FL.array(FL.Saltwater.make(25),flList.DesaltOilMedium    .make(75)));
        RM.Centrifuge.addRecipe0(false,32,35,FL.array(flList.AqueousOilHeavy     .make(100)), FL.array(FL.Saltwater.make(25),flList.DesaltOilHeavy     .make(75)));
        RM.Centrifuge.addRecipe0(false,32,40,FL.array(flList.AqueousOilExtraHeavy.make(100)), FL.array(FL.Saltwater.make(25),flList.DesaltOilExtraHeavy.make(75)));

        RM.Mixer.addRecipe0(false,16,40,FL.array(FL.Oil_Light     .make(150),FL.Water.make(20)), FL.array(FL.Saltwater.make(20),flList.DesaltOilLight.make(150)) );
        RM.Mixer.addRecipe0(false,16,40,FL.array(FL.Oil_Normal    .make(140),FL.Water.make(20)), FL.array(FL.Saltwater.make(20),flList.DesaltOilNormal.make(140)));
        RM.Mixer.addRecipe0(false,16,40,FL.array(FL.Oil_Medium    .make(130),FL.Water.make(20)), FL.array(FL.Saltwater.make(20),flList.DesaltOilMedium.make(130)));
        RM.Mixer.addRecipe0(false,16,40,FL.array(FL.Oil_Heavy     .make(115),FL.Water.make(20)), FL.array(FL.Saltwater.make(20),flList.DesaltOilHeavy.make(115)) );
        RM.Mixer.addRecipe0(false,16,40,FL.array(FL.Oil_ExtraHeavy.make(115),FL.Water.make(20)), FL.array(FL.Saltwater.make(20),flList.DesaltOilExtraHeavy.make(100)));

        recipeMaps.HeatMixer.addRecipeX(false,80,14, ST.array(OP.dust.mat(MT.Ni,0)), FL.array(FL.Hydrogen.make(40),flList.OilDesulfurizationer.make(4),flList.DesaltOilLight.make(100)),FL.array(flList.CleanedOilLight.make(100) ,flList.SulfuredOilDesulfurizationer.make(4)), ZL_IS);
        recipeMaps.HeatMixer.addRecipeX(false,80,20, ST.array(OP.dust.mat(MT.Ni,0)), FL.array(FL.Hydrogen.make(40),flList.OilDesulfurizationer.make(6),flList.DesaltOilNormal.make(100)),FL.array(flList.CleanedOilNormal.make(100) ,flList.SulfuredOilDesulfurizationer.make(6)), ZL_IS);
        recipeMaps.HeatMixer.addRecipeX(false,80,26, ST.array(OP.dust.mat(MT.Ni,0)), FL.array(FL.Hydrogen.make(40),flList.OilDesulfurizationer.make(8),flList.DesaltOilMedium.make(100)),FL.array(flList.CleanedOilMedium.make(100) ,flList.SulfuredOilDesulfurizationer.make(8)), ZL_IS);
        recipeMaps.HeatMixer.addRecipeX(false,80,32, ST.array(OP.dust.mat(MT.Ni,0)), FL.array(FL.Hydrogen.make(40),flList.OilDesulfurizationer.make(10),flList.DesaltOilHeavy.make(100)),FL.array(flList.CleanedOilHeavy.make(100),flList.SulfuredOilDesulfurizationer.make(10)), ZL_IS);
        recipeMaps.HeatMixer.addRecipeX(false,80,38, ST.array(OP.dust.mat(MT.Ni,0)), FL.array(FL.Hydrogen.make(40),flList.OilDesulfurizationer.make(12),flList.DesaltOilExtraHeavy.make(100)),FL.array(flList.CleanedOilExtraHeavy.make(100) ,flList.SulfuredOilDesulfurizationer.make(12)), ZL_IS);

        recipeMaps.HeatMixer.addRecipe0(false,32,2400,FL.array(flList.SulfuredOilDesulfurizationer.make(1000),FL.Hydrogen.make(200)), FL.array(flList.OilDesulfurizationer.make(1000),MT.H2S.gas(U10,false)));

        recipeMaps.TinyDistillTower.addRecipe0(false,128,100,FL.array(flList.CleanedOilLight.make(800)),FL.array(flList.InitalBottomOil.make(400),flList.Naphtha.make(300),flList.OilGas.make(200)),ZL_IS);
        recipeMaps.TinyDistillTower.addRecipe0(false,128,100,FL.array(flList.CleanedOilMedium.make(800)),FL.array(flList.InitalBottomOil.make(425),flList.Naphtha.make(325),flList.OilGas.make(175)), ZL_IS);
        recipeMaps.TinyDistillTower.addRecipe0(false,128,100,FL.array(flList.CleanedOilNormal.make(800)),FL.array(flList.InitalBottomOil.make(450),flList.Naphtha.make(350),flList.OilGas.make(150)), ZL_IS);
        recipeMaps.TinyDistillTower.addRecipe0(false,128,100,FL.array(flList.CleanedOilHeavy.make(800)),FL.array(flList.InitalBottomOil.make(475),flList.Naphtha.make(375),flList.OilGas.make(125)), ZL_IS);
        recipeMaps.TinyDistillTower.addRecipe0(false,128,100,FL.array(flList.CleanedOilExtraHeavy.make(800)),FL.array(flList.InitalBottomOil.make(500),flList.Naphtha.make(400),flList.OilGas.make(100)), ZL_IS);

        recipeMaps.SmallDistillTower.addRecipe0(false,128,80,FL.array(flList.CleanedOilLight.make(800)),FL.array(flList.InitalBottomOil.make(400),flList.Naphtha.make(300),flList.OilGas.make(200)), ZL_IS);
        recipeMaps.SmallDistillTower.addRecipe0(false,128,80,FL.array(flList.CleanedOilMedium.make(800)),FL.array(flList.InitalBottomOil.make(425),flList.Naphtha.make(325),flList.OilGas.make(175)), ZL_IS);
        recipeMaps.SmallDistillTower.addRecipe0(false,128,80,FL.array(flList.CleanedOilNormal.make(800)),FL.array(flList.InitalBottomOil.make(450),flList.Naphtha.make(350),flList.OilGas.make(150)), ZL_IS);
        recipeMaps.SmallDistillTower.addRecipe0(false,128,80,FL.array(flList.CleanedOilHeavy.make(800)),FL.array(flList.InitalBottomOil.make(475),flList.Naphtha.make(375),flList.OilGas.make(125)), ZL_IS);
        recipeMaps.SmallDistillTower.addRecipe0(false,128,80,FL.array(flList.CleanedOilExtraHeavy.make(800)),FL.array(flList.InitalBottomOil.make(500),flList.Naphtha.make(400),flList.OilGas.make(100)), ZL_IS);

        recipeMaps.DistillTower.addRecipe0(false,128,65,FL.array(flList.CleanedOilLight.make(800)),FL.array(flList.InitalBottomOil.make(400),flList.Naphtha.make(300),flList.OilGas.make(200)), ZL_IS);
        recipeMaps.DistillTower.addRecipe0(false,128,65,FL.array(flList.CleanedOilMedium.make(800)),FL.array(flList.InitalBottomOil.make(425),flList.Naphtha.make(325),flList.OilGas.make(175)), ZL_IS);
        recipeMaps.DistillTower.addRecipe0(false,128,65,FL.array(flList.CleanedOilNormal.make(800)),FL.array(flList.InitalBottomOil.make(450),flList.Naphtha.make(350),flList.OilGas.make(150)), ZL_IS);
        recipeMaps.DistillTower.addRecipe0(false,128,65,FL.array(flList.CleanedOilHeavy.make(800)),FL.array(flList.InitalBottomOil.make(475),flList.Naphtha.make(375),flList.OilGas.make(125)), ZL_IS);
        recipeMaps.DistillTower.addRecipe0(false,128,65,FL.array(flList.CleanedOilExtraHeavy.make(800)),FL.array(flList.InitalBottomOil.make(500),flList.Naphtha.make(400),flList.OilGas.make(100)), ZL_IS);

        recipeMaps.SmallDistillTower.addRecipe0(F,196,120,FL.array(flList.InitalBottomOil.make(400)),FL.array(FL.Diesel.make(120),FL.Kerosine.make(200),flList.Naphtha.make(80),FL.Petrol.make(320),flList.OilGas.make(100)), OM.dust(matList.OilScarp.mat,U4));
        recipeMaps.DistillTower     .addRecipe0(F,196, 95,FL.array(flList.InitalBottomOil.make(400)),FL.array(FL.Diesel.make(120),FL.Kerosine.make(200),flList.Naphtha.make(80),FL.Petrol.make(320),flList.OilGas.make(100)), OM.dust(matList.OilScarp.mat,U4));
        RM.Centrifuge.addRecipe0(F,80,100,FL.array(FL.Gas_Natural.make(400)),FL.array(FL.CarbonDioxide.make(4),FL.Methane.make(20),FL.Propane.make(160),FL.Butane.make(180)),ZL_IS);

        RM.Centrifuge.addRecipeX(F,64,80,new long[]{8000,8000} , ST.array(OM.dust(matList.OilScarp.get(),U4)),ZL_FS,FL.array(FL.Kerosine.make(20),FL.Lubricant.make(60)), dustTiny.mat(MT.WaxParaffin, 1), dustTiny.mat(MT.Asphalt, 1));
        RM.Centrifuge.addRecipeX(F,64,80,new long[]{8000,8000} , ST.array(OM.dust(matList.OilScarp.get(),U)),ZL_FS,FL.array(FL.Kerosine.make(180),FL.Lubricant.make(540)), dust.mat(MT.WaxParaffin, 1), dust.mat(MT.Asphalt, 1));

        recipeMaps.DistillTower.addRecipe0(F, 64,  16, new long[] { 500,  500,  500}, FL.array(FL.Biomass       .make( 80)), FL.array(FL.Reikanol.make(20, FL.BioEthanol), MT.Glycerol.liquid(U50, F), FL.Methane.make(4), FL.DistW.make(50)), ZL_IS);

        //TODO
        recipeMaps.DistillTower.addRecipe0(F, 64,  16, new long[] { 500,  500,  500}, FL.array(FL.BiomassIC2    .make( 80)), FL.array(FL.Reikanol.make(20, FL.BioEthanol), MT.Glycerol.liquid(U50, F), FL.Methane.make(4), FL.DistW.make(50)), ZL_IS);
        recipeMaps.DistillTower.addRecipe0(F, 64,  64, new long[] {1000, 1000, 1000}, FL.array(FL.Oil_Soulsand  .make( 25)), FL.array(FL.Diesel.make( 5), FL.Kerosine.make( 5), FL.Petrol.make( 5), FL.Propane.make( 5), FL.Butane.make( 5), FL.lube(40)), dustTiny.mat(MT.WaxParaffin, 1), dustTiny.mat(MT.Asphalt, 1), dustTiny.mat(MT.PetCoke, 1));

        //Natural Gas
        RM.Centrifuge.addRecipe0(F,80,100,FL.array(FL.Gas_Natural.make(400)),FL.array(FL.CarbonDioxide.make(8),FL.Water.make(1),FL.Methane.make(380),FL.Nitrogen.make(8)),ZL_IS);

        RM.BurnMixer.addRecipe0(false,64,400,FL.array(FL.Methane.make(100),FL.Water.make(100)),FL.array(flList.CarbonMonoxide.make(40),FL.Hydrogen.make(280),FL.CarbonDioxide.make(30)));
        RM.BurnMixer.addRecipe0(false,64,400,FL.array(flList.Naphtha.make(100),FL.Water.make(100)),FL.array(flList.CarbonMonoxide.make(50),FL.Hydrogen.make(250),FL.CarbonDioxide.make(30)));

        RM.BurnMixer.addRecipe1(false,16,40,OP.dust.mat(MT.C,1),FL.array(FL.Oxygen.make(80)),FL.array(flList.CarbonMonoxide.make(20),FL.CarbonDioxide.make(30)));
        RM.BurnMixer.addRecipe1(false,16,40, gem.mat(MT.CoalCoke,1),FL.array(FL.Oxygen.make(120)),FL.array(flList.CarbonMonoxide.make(30),FL.CarbonDioxide.make(45)));

        RM.Mixer.addRecipe1(false,64,80,OP.dust.mat(MT.Pt,0),FL.array(MT.NH3.gas(400,false),FL.Oxygen.make(500)),FL.array(MT.NO.fluid(4*U10,false),FL.Water.make(600)));

        recipeMaps.HeatMixer.addRecipe2(false,64,200,OP.dust.mat(MT.Fe,0),OP.dust.mat(MT.Mo,0),FL.array(FL.Methane.make(100),FL.Oxygen.make(100)),FL.array(flList.Formaldehyde.make(100),FL.Water.make(100)));

        recipeMaps.HeatMixer.addRecipe1(false,64,80,OP.dust.mat(MT.Mo,0),FL.array(flList.Methanol.make(100),FL.Oxygen.make(50)),FL.array(flList.Formaldehyde.make(100),FL.Water.make(100)));
        recipeMaps.HeatMixer.addRecipe1(false,64,80,OP.dust.mat(MT.Ag,0),FL.array(flList.Methanol.make(100),FL.Oxygen.make(50)),FL.array(flList.Formaldehyde.make(100),FL.Water.make(100)));

        recipeMaps.HeatMixer.addRecipe1(false,64,80,OP.dust.mat(MT.Mo,0),FL.array(FL.BioEthanol.make(100),FL.Oxygen.make(50)),FL.array(flList.Acetaldehyde.make(100),FL.Water.make(100)));
        recipeMaps.HeatMixer.addRecipe1(false,64,80,OP.dust.mat(MT.Ag,0),FL.array(FL.BioEthanol.make(100),FL.Oxygen.make(50)),FL.array(flList.Acetaldehyde.make(100),FL.Water.make(100)));

        recipeMaps.HeatMixer.addRecipe1(false,64,80,OP.dust.mat(MT.Mo,0),FL.array(flList.Propanol.make(100),FL.Oxygen.make(50)),FL.array(flList.Propionaldehyde.make(100),FL.Water.make(100)));
        recipeMaps.HeatMixer.addRecipe1(false,64,80,OP.dust.mat(MT.Ag,0),FL.array(flList.Propanol.make(100),FL.Oxygen.make(50)),FL.array(flList.Propionaldehyde.make(100),FL.Water.make(100)));

        RM.Mixer.addRecipe0(false,64,20,FL.array(flList.Propanol.make(100),MT.H2SO4.liquid(2000,false)),FL.array(FL.Propylene.make(100),MT.H2SO4.liquid(1000,false)));


        RM.Mixer.addRecipe0(false,64,20,FL.array(FL.BioEthanol.make(100),MT.H2SO4.liquid(2000,false)),FL.array(FL.Ethylene.make(100),MT.H2SO4.liquid(1000,false)));

        //Coal Boiling
        RM.Centrifuge.addRecipe0(false,64,200,FL.array(flList.CoalTar.make(100)),FL.array(flList.Benzene.make(30),FL.Hydrogen.make(20),FL.Nitrogen.make(10),flList.CarbonMonoxide.make(70)),matList.Naphthalene.getDustTiny(1),matList.Naphthalene.getDustTiny(1));

        RM.Centrifuge.addRecipe0(false,64,100,flList.WoodTar.make(100),FL.array(FL.Methane.make(30),FL.CarbonDioxide.make(50),flList.CarbonMonoxide.make(20),FL.Hydrogen.make(5),FL.Ethylene.make(10),FL.Oil_Olive.make(60)));

        RM.SteamCracking.addRecipe1(F, 16,  64, OP.dust.mat(MT.Kaolinite,0),FL.array(FL.Steam.make(1000), flList.Naphtha.make(100)), FL.array(FL.Hydrogen.make( 10), flList.OilGas.make(22), FL.Petrol.make(47), FL.Lubricant.make(19), FL.Ethylene.make(80),FL.Methane.make(30),FL.Propylene.make(10)), ZL_IS);

        RM.SteamCracking.addRecipe1(F, 16,  64, OP.dust.mat(MT.Kaolinite,0),FL.array(FL.Steam.make(1000), FL.Diesel.make(100)), FL.array(FL.Hydrogen.make( 2), flList.OilGas.make(18), FL.Petrol.make(65), FL.Lubricant.make(90), FL.Ethylene.make(20),FL.Methane.make(10),FL.Propylene.make(10)), ZL_IS);

        RM.Centrifuge.addRecipe0(false,172,200,flList.OilGas.make(100),FL.array(FL.Propane.make(40),FL.Butane.make(40),FL.Nitrogen.make(5),FL.CarbonDioxide.make(5)));

        recipeMaps.HeatMixer.addRecipe0(false,32,40,FL.array(flList.OilGas.make(100),flList.Benzene.make(5),FL.Hydrogen.make(80)),FL.array(FL.Diesel.make(10),flList.CrackedOilGas.make(100)),ZL_IS);

        recipeMaps.SmallDistillTower.addRecipe0(false,256,60,FL.array(flList.OilGas.make(1000)),FL.array(FL.Methane.make(372),flList.Ethane.make(262),FL.Propane.make(188),FL.Butane.make(138),FL.CarbonDioxide.make(40)),ZL_IS);
        recipeMaps.DistillTower.addRecipe0(false,256,40,FL.array(flList.OilGas.make(1000)),FL.array(FL.Methane.make(372),flList.Ethane.make(262),FL.Propane.make(188),FL.Butane.make(138),FL.CarbonDioxide.make(40)),ZL_IS);

        recipeMaps.DistillTower.addRecipe0(false,256,40,FL.array(flList.CrackedOilGas.make(1000)),FL.array(flList.Benzene.make(42),flList.Toluene.make(8),flList.Phenol.make(11),FL.Methane.make(183),flList.Ethane.make(152),FL.Propane.make(133),FL.Butane.make(128),flList.Butadiene.make(134),FL.CarbonDioxide.make(134)),ZL_IS);

        FM.Burn         .addRecipe0(T, - 64,  5, flList.Naphtha.make(1), FL.Steam.make(80),FL.CarbonDioxide.make(5));
        FM.Engine       .addRecipe0(T, - 64,  7, flList.Naphtha.make(1), FL.Steam.make(80),FL.CarbonDioxide.make(5));

        FM.Burn         .addRecipe0(T, - 64,  12, flList.Toluene.make(1), FL.Steam.make(60),FL.CarbonDioxide.make(6));
        FM.Engine       .addRecipe0(T, - 64,  14, flList.Toluene.make(1), FL.Steam.make(60),FL.CarbonDioxide.make(6));

        FM.Burn         .addRecipe0(T, - 64,  27, flList.OilGas.make(5), FL.Steam.make(140), FL.CarbonDioxide.make(3));
        FM.Gas          .addRecipe0(T, - 64,  32, flList.OilGas.make(5), FL.Steam.make(140), FL.CarbonDioxide.make(3));

        FM.Burn         .addRecipe0(T, - 110,  24, flList.Ethane.make(5), FL.Steam.make(200), FL.CarbonDioxide.make(6));
        FM.Gas          .addRecipe0(T, - 110,  30, flList.Ethane.make(5), FL.Steam.make(200), FL.CarbonDioxide.make(6));

        FM.Burn         .addRecipe0(T, - 64,  9 ,flList.CarbonMonoxide.make(5), FL.CarbonDioxide.make(5), ZL_IS);
        FM.Gas          .addRecipe0(T, - 64,  13,flList.CarbonMonoxide.make(5), FL.CarbonDioxide.make(5), ZL_IS);

        FM.Burn         .addRecipe0(T, - 16, 48, flList.DesaltOilExtraHeavy.make(1)                   , FL.CarbonDioxide.make(1), dustTiny.mat(MT.DarkAsh,1));
        FM.Burn         .addRecipe0(T, - 16, 36, flList.DesaltOilHeavy.make(1)                        , FL.CarbonDioxide.make(1), dustTiny.mat(MT.DarkAsh,1));
        FM.Burn         .addRecipe0(T, - 16, 24, flList.DesaltOilMedium.make(1)                       , FL.CarbonDioxide.make(1), dustTiny.mat(MT.DarkAsh,1));
        FM.Burn         .addRecipe0(T, - 16, 18, flList.DesaltOilLight.make(1)                        , FL.CarbonDioxide.make(1), dustTiny.mat(MT.DarkAsh,1));
        FM.Burn         .addRecipe0(T, - 16, 24, flList.DesaltOilNormal.make(1)                       , FL.CarbonDioxide.make(1), dustTiny.mat(MT.DarkAsh,1));

        FM.Burn         .addRecipe0(T, - 16, 8, flList.WoodTar.make(1)                       , FL.CarbonDioxide.make(1), dustDiv72.mat(MT.DarkAsh,1));
        FM.Burn         .addRecipe0(T, - 16, 8, flList.CoalTar.make(1)                       , FL.CarbonDioxide.make(1), dustDiv72.mat(MT.DarkAsh,1));

        recipeMaps.HeatMixer.addRecipe0(F,280,160,FL.array(FL.Oxygen.make(1000),FL.Propane.make(1000)),FL.array(flList.GlacialAceticAcid.make(500),flList.Acetone.make(500)));

        recipeMaps.HeatMixer.addRecipe2(F, 40,160,ST.tag(0),OP.dust.mat(MT.Pt,0),FL.array(flList.Naphtha.make(1000),FL.Hydrogen.make(800)),FL.array(flList.CrackedNaphthaLow   .make(1000)));
        recipeMaps.HeatMixer.addRecipe2(F,120,150,ST.tag(1),OP.dust.mat(MT.Pt,0),FL.array(flList.Naphtha.make(1000),FL.Hydrogen.make(800)),FL.array(flList.CrackedNaphthaMedium.make(1000)));
        recipeMaps.HeatMixer.addRecipe2(F,420,140,ST.tag(2),OP.dust.mat(MT.Pt,0),FL.array(flList.Naphtha.make(1000),FL.Hydrogen.make(800)),FL.array(flList.CrackedNaphthaHigh  .make(1000)));
        recipeMaps.SmallDistillTower.addRecipe0(F,120,20,FL.array(flList.CrackedNaphthaLow   .make(1000)),FL.array(flList.Benzene.make(121),flList.Toluene.make(30 ),FL.Petrol.make(600),FL.Methane.make(212),flList.Ethane.make(182), FL.Hydrogen.make(882)));
        recipeMaps.SmallDistillTower.addRecipe0(F,120,20,FL.array(flList.CrackedNaphthaMedium.make(1000)),FL.array(flList.Benzene.make(151),flList.Toluene.make(43),FL.Petrol.make(500),FL.Methane.make(342),flList.Ethane.make(282),FL.Hydrogen.make(1282)));
        recipeMaps.SmallDistillTower.addRecipe0(F,120,20,FL.array(flList.CrackedNaphthaHigh  .make(1000)),FL.array(flList.Benzene.make(191),flList.Toluene.make(50),FL.Petrol.make(300),FL.Methane.make(602),flList.Ethane.make(352),FL.Hydrogen.make(1682)));
        recipeMaps.     DistillTower.addRecipe0(F,120,10,FL.array(flList.CrackedNaphthaLow   .make(1000)),FL.array(flList.Benzene.make(121),flList.Toluene.make(30 ),FL.Petrol.make(600),FL.Methane.make(212),flList.Ethane.make(182), FL.Hydrogen.make(882)));
        recipeMaps.     DistillTower.addRecipe0(F,120,10,FL.array(flList.CrackedNaphthaMedium.make(1000)),FL.array(flList.Benzene.make(151),flList.Toluene.make(43),FL.Petrol.make(500),FL.Methane.make(342),flList.Ethane.make(282),FL.Hydrogen.make(1282)));
        recipeMaps.     DistillTower.addRecipe0(F,120,10,FL.array(flList.CrackedNaphthaHigh  .make(1000)),FL.array(flList.Benzene.make(191),flList.Toluene.make(50),FL.Petrol.make(300),FL.Methane.make(602),flList.Ethane.make(352),FL.Hydrogen.make(1682)));

        recipeMaps.HeatMixer.addRecipe2(F, 40,160,ST.tag(0),OP.dust.mat(MT.Pt,0),FL.array(FL.Diesel.make(1000),FL.Hydrogen.make(800)),FL.array(flList.CrackedDieselLow   .make(1000)));
        recipeMaps.HeatMixer.addRecipe2(F,120,150,ST.tag(1),OP.dust.mat(MT.Pt,0),FL.array(FL.Diesel.make(1000),FL.Hydrogen.make(800)),FL.array(flList.CrackedDieselMedium.make(1000)));
        recipeMaps.HeatMixer.addRecipe2(F,420,140,ST.tag(2),OP.dust.mat(MT.Pt,0),FL.array(FL.Diesel.make(1000),FL.Hydrogen.make(800)),FL.array(flList.CrackedDieselHigh  .make(1000)));
        recipeMaps.SmallDistillTower.addRecipe0(F,120,20,FL.array(flList.CrackedDieselLow   .make(1000)),FL.array(FL.Lubricant.make(218),FL.Petrol.make(542),flList.OilGas.make(118),flList.CrackedOilGas.make(242)));
        recipeMaps.SmallDistillTower.addRecipe0(F,120,20,FL.array(flList.CrackedDieselMedium.make(1000)),FL.array(FL.Lubricant.make(162),FL.Petrol.make(423),flList.OilGas.make(247),flList.CrackedOilGas.make(392)));
        recipeMaps.SmallDistillTower.addRecipe0(F,120,20,FL.array(flList.CrackedDieselHigh  .make(1000)),FL.array(FL.Lubricant.make(92) ,FL.Petrol.make(321),flList.OilGas.make(301),flList.CrackedOilGas.make(542)));
        recipeMaps.     DistillTower.addRecipe0(F,120,10,FL.array(flList.CrackedDieselLow   .make(1000)),FL.array(FL.Lubricant.make(218),FL.Petrol.make(542),flList.OilGas.make(118),flList.CrackedOilGas.make(242)));
        recipeMaps.     DistillTower.addRecipe0(F,120,10,FL.array(flList.CrackedDieselMedium.make(1000)),FL.array(FL.Lubricant.make(162),FL.Petrol.make(423),flList.OilGas.make(247),flList.CrackedOilGas.make(392)));
        recipeMaps.     DistillTower.addRecipe0(F,120,10,FL.array(flList.CrackedDieselHigh  .make(1000)),FL.array(FL.Lubricant.make(92) ,FL.Petrol.make(321),flList.OilGas.make(301),flList.CrackedOilGas.make(542)));

        RM.Mixer.addRecipe1(false,64,20,ST.tag(1),FL.array(FL.Petrol.make(80),FL.Diesel.make(20)),FL.array(flList.BlendedFuel1.make(100)),ZL_IS);
        FM.Burn         .addRecipe0(T, - 128,  4, flList.BlendedFuel1.make(1), FL.Steam.make(120), FL.CarbonDioxide.make(5));
        FM.Engine       .addRecipe0(T, - 128,  6, flList.BlendedFuel1.make(1), FL.Steam.make(120), FL.CarbonDioxide.make(5));

        RM.Mixer.addRecipe1(false,64,20,ST.tag(2),FL.array(flList.Methanol.make(60),FL.Petrol.make(30),flList.Naphtha.make(8)),FL.array(flList.BlendedFuel2.make(70)),ZL_IS);
        FM.Burn         .addRecipe0(T, - 144,  4, flList.BlendedFuel2.make(1), FL.Steam.make(150), FL.CarbonDioxide.make(3));
        FM.Engine       .addRecipe0(T, - 144,  6, flList.BlendedFuel2.make(1), FL.Steam.make(150), FL.CarbonDioxide.make(3));

        RM.Mixer.addRecipe1(false,64,20,ST.tag(3),FL.array(FL.Petrol.make(70),flList.Propanol.make(25),flList.Methanol.make(7)),FL.array(flList.BlendedFuel3.make(65)),ZL_IS);
        FM.Burn         .addRecipe0(T, - 152,  6, flList.BlendedFuel3.make(1), FL.Steam.make(180),FL.CarbonDioxide.make(2));
        FM.Engine       .addRecipe0(T, - 152,  8, flList.BlendedFuel3.make(1), FL.Steam.make(180),FL.CarbonDioxide.make(2));

        RM.Mixer.addRecipe1(false,64,20,ST.tag(4),FL.array(flList.Toluene.make(20),FL.Petrol.make(40),flList.Methanol.make(65),flList.Naphtha.make(15)),FL.array(flList.BlendedFuel4.make(70)),ZL_IS);
        FM.Burn         .addRecipe0(T, - 164,  8 , flList.BlendedFuel4.make(1), FL.Nitrogen.make(1),FL.Steam.make(140),FL.CarbonDioxide.make(4));
        FM.Engine       .addRecipe0(T, - 164,  11, flList.BlendedFuel4.make(1), FL.Nitrogen.make(1),FL.Steam.make(140),FL.CarbonDioxide.make(4));

        RM.Mixer.addRecipe1(false,64,20,ST.tag(5),FL.array(flList.Toluene.make(20),FL.Petrol.make(40),flList.Methanol.make(65),MT.Glyceryl.fluid(U100, T),flList.Naphtha.make(20)),FL.array(flList.BlendedFuel5.make(55)),ZL_IS);
        FM.Burn         .addRecipe0(T, - 208,  8 , flList.BlendedFuel5.make(1), FL.Nitrogen.make(2),FL.Steam.make(100),FL.CarbonDioxide.make(5));
        FM.Engine       .addRecipe0(T, - 208,  12, flList.BlendedFuel5.make(1), FL.Nitrogen.make(2),FL.Steam.make(100),FL.CarbonDioxide.make(5));

        RM.Mixer.addRecipe1(false,64,20,ST.tag(1),FL.array(FL.BioEthanol.make(80),FL.Petrol.make(50)),FL.array(flList.BioFuel1.make(100)),ZL_IS);
        FM.Burn         .addRecipe0(T, - 108,  5, flList.BioFuel1.make(1), FL.Steam.make(200),FL.CarbonDioxide.make(4));
        FM.Engine       .addRecipe0(T, - 108,  6, flList.BioFuel1.make(1), FL.Steam.make(200),FL.CarbonDioxide.make(4));

        RM.Mixer.addRecipe1(false,64,20,ST.tag(2),FL.array(FL.BioEthanol.make(60),FL.Petrol.make(40),flList.Toluene.make(12)),FL.array(flList.BioFuel2.make(100)),ZL_IS);
        FM.Burn         .addRecipe0(T, - 120,  5, flList.BioFuel2.make(1), FL.Steam.make(260),FL.CarbonDioxide.make(5));
        FM.Engine       .addRecipe0(T, - 120,  6, flList.BioFuel2.make(1), FL.Steam.make(260),FL.CarbonDioxide.make(5));


    }
}

