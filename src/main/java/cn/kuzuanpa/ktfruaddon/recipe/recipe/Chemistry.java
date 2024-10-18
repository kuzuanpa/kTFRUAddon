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
import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import cn.kuzuanpa.ktfruaddon.material.matList;
import cn.kuzuanpa.ktfruaddon.recipe.recipeMaps;
import gregapi.data.*;
import gregapi.util.ST;

import static gregapi.data.CS.*;

public class Chemistry {
    public static void init(){
        recipeMaps.FluidHeating.addRecipe0(F,80,400,FL.array(FL.DistW.make(100)),FL.array(FL.Steam.make(16000)));

        recipeMaps.FluidHeating.addRecipe0(F,20,10,FL.array(FL.Coolant_IC2.make(10)),FL.array(FL.Coolant_IC2_Hot.make(10)));
        recipeMaps.FluidHeating.addRecipe0(F,72,10,FL.array(flList.MoltenNaK.make(10)),FL.array(flList.HotMoltenNaK.make(10)));
        recipeMaps.FluidHeating.addRecipe0(F,30,10,FL.array(MT.Na.liquid(10*U144,false)),FL.array(FL.Hot_Molten_Sodium.make(10)));
        recipeMaps.FluidHeating.addRecipe0(F,40,10,FL.array(MT.Sn.liquid(10*U144,false)),FL.array(FL.Hot_Molten_Tin.make(10)));
        recipeMaps.FluidHeating.addRecipe0(F,15,10,FL.array(MT.LiCl.liquid(10*U144,false)),FL.array(FL.Hot_Molten_LiCl.make(10)));
        recipeMaps.FluidHeating.addRecipe0(F,50,10,FL.array(MT.D2O.liquid(U100,false)),FL.array(FL.Hot_Heavy_Water.make(10)));
        recipeMaps.FluidHeating.addRecipe0(F,40,10,FL.array(MT.HDO.liquid(U100,false)),FL.array(FL.Hot_Semi_Heavy_Water.make(10)));
        recipeMaps.FluidHeating.addRecipe0(F,60,10,FL.array(MT.T2O.liquid(U100,false)),FL.array(FL.Hot_Tritiated_Water.make(10)));
        recipeMaps.FluidHeating.addRecipe0(F,15,10,FL.array(MT.CO2.gas(U100,false)),FL.array(FL.Hot_Carbon_Dioxide.make(10)));
        recipeMaps.FluidHeating.addRecipe0(F,15,10,FL.array(MT.He.gas(U100,false)),FL.array(FL.Hot_Helium.make(10)));

        FM.Hot.addRecipe0(F,-360,2,FL.array(flList.HotMoltenNaK.make(10)),FL.array(flList.MoltenNaK.make(10)));

        recipeMaps.HeatMixer.addRecipe1(F,32,20,OP.dust.mat(MT.C,0),FL.array(FL.Vinegar_Rice.make(100)),FL.array(flList.GlacialAceticAcid.make(30),FL.Water.make(60)),OP.dustTiny.mat(MT.Sugar,1));
        recipeMaps.HeatMixer.addRecipe1(F,32,20,OP.dust.mat(MT.C,0),FL.array(FL.Vinegar_Apple.make(100)),FL.array(flList.GlacialAceticAcid.make(20),FL.Water.make(70)),OP.dustTiny.mat(MT.Sugar,1));
        recipeMaps.HeatMixer.addRecipe1(F,32,20,OP.dust.mat(MT.C,0),FL.array(FL.Vinegar_Cane.make(100)),FL.array(flList.GlacialAceticAcid.make(20),FL.Water.make(70)),OP.dustTiny.mat(MT.Sugar,2));
        recipeMaps.HeatMixer.addRecipe1(F,32,20,OP.dust.mat(MT.C,0),FL.array(FL.Vinegar_Grape.make(100)),FL.array(flList.GlacialAceticAcid.make(20),FL.Water.make(70)),OP.dustTiny.mat(MT.Sugar,1));

        RM.Mixer.addRecipeX(F,64,20,ST.array(OP.dust.mat(MT.Ge,1),OP.dust.mat(MT.In,1),OP.dust.mat(MT.Nb,1),OP.dust.mat(MT.Ti,1),OP.dust.mat(MT.Mg,1)),ZL_FS,ZL_FS,matList.HensSoPretty.getDust(5));
        RM.ImplosionCompressor.addRecipeX(F,64,1,ST.array(matList.HensSoPretty.getDust(9),ST.make(MD.MC,"tnt",16),ST.tag(0)),ZL_FS,ZL_FS, ST.make(MD.MC,"spawn_egg",1,93));

        //fuel battery
        recipeMaps.LaserCutter.addRecipe1(F,300,480,OP.plate.mat(MT.Ni,8),ZL_FS,ZL_FS,ItemList.BatteryPoleNickel.get(1));
        recipeMaps.LaserCutter.addRecipe1(F,300,480,OP.plate.mat(MT.Au,8),ZL_FS,ZL_FS,ItemList.BatteryPoleCarbon.get(1));
        recipeMaps.LaserCutter.addRecipe1(F,300,480,OP.plate.mat(MT.Pt,8),ZL_FS,ZL_FS,ItemList.BatteryPolePlatinum.get(1));
        RM.        BurnMixer  .addRecipe2(F,200,480,OP.dust.mat(MT.Ca,4),OP.dust.mat(MT.Ti,4),FL.array(FL.Oxygen.make(4000)),ZL_FS,ItemList.BatteryPoleCaTiO3.get(1));
        recipeMaps.HeatMixer  .addRecipe2(F,500,800,OP.dust.mat(MT.Y,4),OP.dust.mat(MT.Zr,4),FL.array(FL.Oxygen.make(4000)),FL.array(flList.YttriumZirconiumOxide.make(1000)));


        recipeMaps.FuelBattery.addRecipe2(F,-384,9, ItemList.BatteryPoleNickel.get(0),ItemList.BatteryPoleCaTiO3.get(0),FL.array(FL.Methane.make(20)           ,FL.Air.make(80),flList.YttriumZirconiumOxide.make(0)),FL.array(FL.CarbonDioxide.make(20), FL.DistW.make(40)),ZL_IS);
        recipeMaps.FuelBattery.addRecipe2(F,-166,9, ItemList.BatteryPoleNickel.get(0),ItemList.BatteryPoleCaTiO3.get(0),FL.array(flList.CarbonMonoxide.make(20),FL.Air.make(20),flList.YttriumZirconiumOxide.make(0)),FL.array(FL.CarbonDioxide.make(20)),ZL_IS);
        recipeMaps.FuelBattery.addRecipe2(F, -16,9, ItemList.BatteryPoleNickel.get(0),ItemList.BatteryPoleCaTiO3.get(0),FL.array(FL.Hydrogen.make(20)          ,FL.Air.make(20),flList.YttriumZirconiumOxide.make(0)),FL.array(FL.DistW.make(20)),ZL_IS);
        recipeMaps.FuelBattery.addRecipe2(F,-180,9, ItemList.BatteryPoleNickel.get(0),ItemList.BatteryPoleCaTiO3.get(0),FL.array(flList.Methanol.make(20)      ,FL.Air.make(60),flList.YttriumZirconiumOxide.make(0)),FL.array(FL.CarbonDioxide.make(20), FL.DistW.make(40)),ZL_IS);
        recipeMaps.FuelBattery.addRecipe2(F,-288,9, ItemList.BatteryPoleNickel.get(0),ItemList.BatteryPoleCaTiO3.get(0),FL.array(FL.BioEthanol.make(20)        ,FL.Air.make(120),flList.YttriumZirconiumOxide.make(0)),FL.array(FL.CarbonDioxide.make(40), FL.DistW.make(60)),ZL_IS);

        recipeMaps.FuelBattery.addRecipe2(F,-384,13, ItemList.BatteryPoleNickel.get(0),ItemList.BatteryPoleCaTiO3.get(0),FL.array(FL.Methane.make(20)           ,FL.Oxygen.make(40),flList.YttriumZirconiumOxide.make(0)),FL.array(FL.CarbonDioxide.make(20), FL.DistW.make(40)),ZL_IS);
        recipeMaps.FuelBattery.addRecipe2(F,-166,13, ItemList.BatteryPoleNickel.get(0),ItemList.BatteryPoleCaTiO3.get(0),FL.array(flList.CarbonMonoxide.make(20),FL.Oxygen.make(10),flList.YttriumZirconiumOxide.make(0)),FL.array(FL.CarbonDioxide.make(20)),ZL_IS);
        recipeMaps.FuelBattery.addRecipe2(F, -16,13, ItemList.BatteryPoleNickel.get(0),ItemList.BatteryPoleCaTiO3.get(0),FL.array(FL.Hydrogen.make(20)          ,FL.Oxygen.make(10),flList.YttriumZirconiumOxide.make(0)),FL.array(FL.DistW.make(20)),ZL_IS);
        recipeMaps.FuelBattery.addRecipe2(F,-180,13, ItemList.BatteryPoleNickel.get(0),ItemList.BatteryPoleCaTiO3.get(0),FL.array(flList.Methanol.make(20)      ,FL.Oxygen.make(30),flList.YttriumZirconiumOxide.make(0)),FL.array(FL.CarbonDioxide.make(20), FL.DistW.make(40)),ZL_IS);
        recipeMaps.FuelBattery.addRecipe2(F,-288,13, ItemList.BatteryPoleNickel.get(0),ItemList.BatteryPoleCaTiO3.get(0),FL.array(FL.BioEthanol.make(20)        ,FL.Oxygen.make(60),flList.YttriumZirconiumOxide.make(0)),FL.array(FL.CarbonDioxide.make(40), FL.DistW.make(60)),ZL_IS);

        recipeMaps.FuelBattery.addRecipe2(F, -16, 7, ItemList.BatteryPoleCarbon.get(0),ItemList.BatteryPolePlatinum.get(0),FL.array(FL.Hydrogen.make(20)          ,FL.Air.make(10),MT.H2SO4.fluid(0,false)),FL.array(FL.DistW.make(20)),ZL_IS);
        recipeMaps.FuelBattery.addRecipe2(F,-180, 7, ItemList.BatteryPoleCarbon.get(0),ItemList.BatteryPolePlatinum.get(0),FL.array(flList.Methanol.make(20)      ,FL.Air.make(30),MT.H2SO4.fluid(0,false)),FL.array(FL.CarbonDioxide.make(20), FL.DistW.make(40)),ZL_IS);
        recipeMaps.FuelBattery.addRecipe2(F,-288, 7, ItemList.BatteryPoleCarbon.get(0),ItemList.BatteryPolePlatinum.get(0),FL.array(FL.BioEthanol.make(20)        ,FL.Air.make(60),MT.H2SO4.fluid(0,false)),FL.array(FL.CarbonDioxide.make(40), FL.DistW.make(60)),ZL_IS);

        recipeMaps.FuelBattery.addRecipe2(F, -16, 10, ItemList.BatteryPoleCarbon.get(0),ItemList.BatteryPolePlatinum.get(0),FL.array(FL.Hydrogen.make(20)          ,FL.Oxygen.make(10),MT.H2SO4.fluid(0,false)),FL.array(FL.DistW.make(20)),ZL_IS);
        recipeMaps.FuelBattery.addRecipe2(F,-180, 10, ItemList.BatteryPoleCarbon.get(0),ItemList.BatteryPolePlatinum.get(0),FL.array(flList.Methanol.make(20)      ,FL.Oxygen.make(30),MT.H2SO4.fluid(0,false)),FL.array(FL.CarbonDioxide.make(20), FL.DistW.make(40)),ZL_IS);
        recipeMaps.FuelBattery.addRecipe2(F,-288, 10, ItemList.BatteryPoleCarbon.get(0),ItemList.BatteryPolePlatinum.get(0),FL.array(FL.BioEthanol.make(20)        ,FL.Oxygen.make(60),MT.H2SO4.fluid(0,false)),FL.array(FL.CarbonDioxide.make(40), FL.DistW.make(60)),ZL_IS);

    }
}
