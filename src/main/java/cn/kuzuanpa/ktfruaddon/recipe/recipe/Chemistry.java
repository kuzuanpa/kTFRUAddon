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
import gregapi.data.*;
import gregapi.util.ST;

import static gregapi.data.CS.*;

public class Chemistry {
    public static void init(){
        recipeMaps.FluidHeating.addRecipe0(F,80,20,FL.array(FL.DistW.make(10)),FL.array(FL.Steam.make(1600)));

        recipeMaps.FluidHeating.addRecipe0(F,20,10,FL.array(FL.Coolant_IC2.make(10)),FL.array(FL.Coolant_IC2_Hot.make(10)));
        recipeMaps.FluidHeating.addRecipe0(F,72,10,FL.array(flList.MoltenNaK.make(10)),FL.array(flList.HotMoltenNaK.make(10)));
        recipeMaps.FluidHeating.addRecipe0(F,30,10,FL.array(MT.Na.liquid(10*U144,false)),FL.array(FL.Hot_Molten_Sodium.make(10)));
        recipeMaps.FluidHeating.addRecipe0(F,40,10,FL.array(MT.Sn.liquid(10*U144,false)),FL.array(FL.Hot_Molten_Tin.make(10)));
        recipeMaps.FluidHeating.addRecipe0(F,15,10,FL.array(MT.LiCl.liquid(10*U144,false)),FL.array(FL.Hot_Molten_LiCl.make(10)));
        recipeMaps.FluidHeating.addRecipe0(F,50,10,FL.array(MT.D2O.liquid(U100,false)),FL.array(FL.Hot_Heavy_Water.make(10)));
        recipeMaps.FluidHeating.addRecipe0(F,40,10,FL.array(MT.HDO.liquid(U100,false)),FL.array(FL.Hot_Semi_Heavy_Water.make(10)));
        recipeMaps.FluidHeating.addRecipe0(F,60,10,FL.array(MT.T2O.liquid(U100,false)),FL.array(FL.Hot_Tritiated_Water.make(10)));
        recipeMaps.FluidHeating.addRecipe0(F,20,10,FL.array(MT.CO2.gas(U100,false)),FL.array(FL.Hot_Carbon_Dioxide.make(10)));
        recipeMaps.FluidHeating.addRecipe0(F,30,10,FL.array(MT.He.gas(U100,false)),FL.array(FL.Hot_Helium.make(10)));

        FM.Hot.addRecipe0(F,-360,2,FL.array(flList.HotMoltenNaK.make(10)),FL.array(flList.MoltenNaK.make(10)));

        recipeMaps.HeatMixer.addRecipe1(F,32,20,OP.dust.mat(MT.C,0),FL.array(FL.Vinegar_Rice.make(100)),FL.array(flList.GlacialAceticAcid.make(30),FL.Water.make(60)),OP.dustTiny.mat(MT.Sugar,1));
        recipeMaps.HeatMixer.addRecipe1(F,32,20,OP.dust.mat(MT.C,0),FL.array(FL.Vinegar_Apple.make(100)),FL.array(flList.GlacialAceticAcid.make(20),FL.Water.make(70)),OP.dustTiny.mat(MT.Sugar,1));
        recipeMaps.HeatMixer.addRecipe1(F,32,20,OP.dust.mat(MT.C,0),FL.array(FL.Vinegar_Cane.make(100)),FL.array(flList.GlacialAceticAcid.make(20),FL.Water.make(70)),OP.dustTiny.mat(MT.Sugar,2));
        recipeMaps.HeatMixer.addRecipe1(F,32,20,OP.dust.mat(MT.C,0),FL.array(FL.Vinegar_Grape.make(100)),FL.array(flList.GlacialAceticAcid.make(20),FL.Water.make(70)),OP.dustTiny.mat(MT.Sugar,1));

        RM.Mixer.addRecipeX(F,64,20,ST.array(OP.dust.mat(MT.Ge,1),OP.dust.mat(MT.In,1),OP.dust.mat(MT.Nb,1),OP.dust.mat(MT.Ti,1),OP.dust.mat(MT.Mg,1)),ZL_FS,ZL_FS,matList.HensSoPretty.getDust(5));
        RM.ImplosionCompressor.addRecipeX(F,64,1,ST.array(matList.HensSoPretty.getDust(9),ST.make(MD.MC,"tnt",16),ST.tag(0)),ZL_FS,ZL_FS, ST.make(MD.MC,"spawn_egg",1,93));

        //fuel battery
        recipeMaps.LaserCutter.addRecipe1(F,300,480,OP.plate.mat(MT.Ni,8),ZL_FS,ZL_FS,ItemList.BatteryPoleNickel.get(1));
        recipeMaps.LaserCutter.addRecipe1(F,300,480,OP.plate.mat(MT.C,8),ZL_FS,ZL_FS,ItemList.BatteryPoleCarbon.get(1));
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

        //FuelDeburner配方表较特殊, 根据温度可加速最多128倍, 产物的热值应按该公式计算: 产物热值=原料热值+ 功率*耗时/128 + 原料的总质量*64*(最佳温度-常温), 视严格度(功率越高越严格)和耗时(耗时越长维持温度越困难)给予一定加成
        recipeMaps.FuelDeburner.addRecipe1(F,   24,  3000, ST.tag(0), FL.array(FL.Water.make(1000), FL.CarbonDioxide.make( 2000)), FL.array(flList.RecycledFuel0.make(1000))).setSpecialNumber(638);                         //Base:  61180 Bonus: * 2.2
        recipeMaps.FuelDeburner.addRecipe1(F,   80,  5000, ST.tag(1), FL.array(FL.Water.make(1000), FL.CarbonDioxide.make( 4000)), FL.array(flList.RecycledFuel1.make(1000))).setSpecialNumber(1022);                        //Base: 193125 Bonus: * 3.4
        recipeMaps.FuelDeburner.addRecipe1(F,  240,  5000, ST.tag(2), FL.array(FL.Water.make(2000), FL.CarbonDioxide.make( 6000)), FL.array(flList.RecycledFuel2.make(1000))).setSpecialNumber(1788);                        //Base: 612776 Bonus: * 3.6
        recipeMaps.FuelDeburner.addRecipe1(F,  800, 12800, ST.tag(3), FL.array(FL.Water.make(2000), FL.CarbonDioxide.make(12000), FL.Nitrogen.make(1000)), FL.array(flList.RecycledFuel3.make(1000))).setSpecialNumber(1464);//Base: 1045750 Bonus: * 6.2
        recipeMaps.FuelDeburner.addRecipe1(F, 1800, 25600, ST.tag(4), FL.array(FL.Water.make(4000), FL.CarbonDioxide.make(16000), FL.Nitrogen.make(4000)), FL.array(flList.RecycledFuel4.make(1000))).setSpecialNumber(1892);//Base: 2259356 Bonus: * 14.6

        FM.Engine.addRecipe0(F,-135  ,10,FL.array(flList.RecycledFuel0.make(10)),FL.array(FL.Steam.make(16000), FL.CarbonDioxide.make( 200)));
        FM.Engine.addRecipe0(F,-657  ,10,FL.array(flList.RecycledFuel1.make(10)),FL.array(FL.Steam.make(16000), FL.CarbonDioxide.make( 400)));
        FM.Engine.addRecipe0(F,-2205 ,10,FL.array(flList.RecycledFuel2.make(10)),FL.array(FL.Steam.make(32000), FL.CarbonDioxide.make( 600)));
        FM.Engine.addRecipe0(F,-6483 ,10,FL.array(flList.RecycledFuel3.make(10)),FL.array(FL.Steam.make(32000), FL.CarbonDioxide.make(1200), FL.Nitrogen.make(100)));
        FM.Engine.addRecipe0(F,-32986,10,FL.array(flList.RecycledFuel4.make(10)),FL.array(FL.Steam.make(64000), FL.CarbonDioxide.make(1600), FL.Nitrogen.make(400)));

        FM.Burn.addRecipe0(F,-135  ,8,FL.array(flList.RecycledFuel0.make(10)),FL.array(FL.Steam.make(16000), FL.CarbonDioxide.make( 200)));
        FM.Burn.addRecipe0(F,-657  ,8,FL.array(flList.RecycledFuel1.make(10)),FL.array(FL.Steam.make(16000), FL.CarbonDioxide.make( 400)));
        FM.Burn.addRecipe0(F,-2205 ,8,FL.array(flList.RecycledFuel2.make(10)),FL.array(FL.Steam.make(32000), FL.CarbonDioxide.make( 600)));
        FM.Burn.addRecipe0(F,-6483 ,8,FL.array(flList.RecycledFuel3.make(10)),FL.array(FL.Steam.make(32000), FL.CarbonDioxide.make(1200), FL.Nitrogen.make(100)));
        FM.Burn.addRecipe0(F,-32986,8,FL.array(flList.RecycledFuel4.make(10)),FL.array(FL.Steam.make(64000), FL.CarbonDioxide.make(1600), FL.Nitrogen.make(400)));

        //seawater main process
        recipeMaps. DistillTower.       addRecipe0(F, 64,  20, FL.array(FL.Ocean.make(1000)), FL.array(flList.Bittern.make(100), FL.DistW.make(500)), OP.dustSmall.mat(MT.NaCl, 1));
        recipeMaps. SmallDistillTower.  addRecipe0(F, 64,  30, FL.array(FL.Ocean.make(1000)), FL.array(flList.Bittern.make(100), FL.DistW.make(500)), OP.dustSmall.mat(MT.NaCl, 1));
        recipeMaps. TinyDistillTower.   addRecipe0(F, 64,  40, FL.array(FL.Ocean.make(1000)), FL.array(flList.Bittern.make(100), FL.DistW.make(500)), OP.dustSmall.mat(MT.NaCl, 1));
        RM.         Distillery.         addRecipe0(F, 64, 100, FL.array(FL.Ocean.make(400)), FL.array(flList.Bittern.make(50), FL.DistW.make(200)), OP.dustSmall.mat(MT.NaCl, 1));

        RM.         Bath.               addRecipe1(T, 0, 20, OP.dust.mat(MT.OREMATS.Zeolite, 0), FL.array(flList.Bittern.make(1000)), FL.array(flList.Brine.make(800), MT.SaltedWater.liquid(U5, T)));
        RM.         Bath.               addRecipe1(T, 0, 40, OP.dust.mat(MT.Quicklime, 1), FL.array(flList.Brine.make(1000)), FL.array(flList.BrineMgFree.make(1000)), OP.dust.mat(matList.MagnesiumDihydroxide.get(), 2));
        RM.         Bath.               addRecipe0(T, 0, 40, FL.array(FL.CarbonDioxide.make(1000), flList.BrineMgFree.make(1000)), FL.array(flList.BrineBrRich.make(1000)), OP.dust.mat(MT.CaCO3, 1));
        RM.         Bath.               addRecipe0(T, 0, 100, FL.array(MT.Cl.gas(U5, F), flList.BrineBrRich.make(1000)), FL.array(MT.Br.liquid(U5, F), flList.BrineWaste.make(1000)));

        // chemical recycling
        RM.         Bath.               addRecipe1(T, 0, 80, OP.dust.mat(matList.MagnesiumDihydroxide.get(), 5), FL.array(MT.HCl.gas(U * 2, false)), FL.array(FL.Water.make(6000)), OP.dust.mat(MT.MgCl2, 1));
        RM.         Roasting.           addRecipe1(F, 256, 100, OP.dust.mat(MT.CaCO3, 5), OP.dust.mat(MT.Quicklime, 2));

        //Graphene
        RM.        BurnMixer.addRecipe1(F, 120, 80, OP.dust.mat(MT.Cu, 0), FL.array(flList.Acetylene.make(1000), flList.Formaldehyde.make(2000)), FL.array(flList.Butynediol.make(1000)), ZL_IS);
        recipeMaps.HeatMixer.addRecipe1(F, 120, 80, OP.dust.mat(MT.Pd, 0), FL.array(flList.Butynediol.make(1000), FL.Hydrogen.make(1000)), FL.array(flList.Butanediol.make(1000)), ZL_IS);
        RM.        BurnMixer.addRecipe1(F, 120, 80, OP.dust.mat(MT.Cu, 0), FL.array(flList.Butanediol.make(1000)), FL.array(flList.Butyrolactone.make(1000), FL.Hydrogen.make(2000)), ZL_IS);
        recipeMaps.HeatMixer.addRecipe0(F, 120, 80, FL.array(MT.NH3.gas(U, true), flList.Methanol.make(1000)), FL.array(flList.Methylamine.make(1000), FL.Water.make(1000)), ZL_IS);
        recipeMaps.HeatMixer.addRecipe0(F, 120, 80, FL.array(flList.Methylamine.make(1000), flList.Butyrolactone.make(1000)), FL.array(flList.Methylpyrrolidone.make(1000), FL.Water.make(1000)), ZL_IS);
        RM.        Mixer    .addRecipe1(F, 400, 800,OP.dust.mat(MT.Graphite, 7), FL.array(flList.Methylpyrrolidone.make(4000)), FL.array(flList.Graphite_Methylpyrrolidone.make(4000)), ZL_IS);
        recipeMaps.UltrasonicMixer.addRecipe1(F, 800, 3600, ST.tag(0), FL.array(flList.Graphite_Methylpyrrolidone.make(4000)), FL.array(flList.Methylpyrrolidone.make(4000)), OP.dust.mat(MT.Graphite, 6), OP.foil.mat(MT.Graphene, 4));
        recipeMaps.UltrasonicMixer.addRecipe1(F, 600, 18000, ST.tag(1), FL.array(flList.Graphite_Methylpyrrolidone.make(16000)), FL.array(flList.Methylpyrrolidone.make(16000)), OP.dust.mat(MT.Graphite, 24), OP.plate.mat(MT.Graphene, 4));

        recipeMaps.CVD.addRecipe1(F, 500, 1200, ST.tag(0), FL.array(MT.CH4.gas( 3*U, true), FL.Hydrogen.make( 3000)), ZL_FS, OP.foil .mat(MT.Graphene, 1), OP.dust.mat(MT.Graphite, 2));
        recipeMaps.CVD.addRecipe1(F, 500, 4800, ST.tag(1), FL.array(MT.CH4.gas(12*U, true), FL.Hydrogen.make(12000)), ZL_FS, OP.plate.mat(MT.Graphene, 1), OP.dust.mat(MT.Graphite, 8));

        recipeMaps.LaserCutter.addRecipe1(F, 500, 200, OP.plate.mat(MT.Graphene, 1), ZL_FS, ZL_FS, OP.stick.mat(MT.Graphene, 2));

        RM.Mixer.addRecipe1(F, 80, 120, OP.dust.mat(MT.Al, 0), FL.array(MT.Cl.gas(3*U, true)), ZL_FS, matList.AluminumChloride.getDust(1));

        recipeMaps.HeatMixer.addRecipe1(F, 120, 80, matList.AluminumChloride.getDust(0), FL.array(flList.Benzene.make(200), flList.Chloromethane.make(100)), FL.array(flList.Toluene.make(200), MT.HCl.gas(U, false)), ZL_IS);

        recipeMaps.UltrasonicMixer.addRecipe1(F, 800, 80, OP.dust.mat(MT.OREMATS.Barite, 4), FL.array(FL.DistW.make(1000)), FL.array(flList.BariumSulfate.make(1000)), ZL_IS);

        recipeMaps.HeatMixer.addRecipe1(F, 480, 800, matList.SodiumAcetate.getDust(2), FL.array(flList.BariumSulfate.make(2000)), FL.array(flList.BariumAcetate.make(2000)), OP.dust.mat(MT.NaSO4, 1));

        recipeMaps.HeatMixer.addRecipe0(F, 1040, 80, FL.array(flList.Acetone.make(1000)), FL.array(flList.Ketene.make(400), flList.Diketene.make(600), FL.Methane.make(1000)), ZL_IS);
        recipeMaps.HeatMixer.addRecipe0(F, 200, 200, FL.array(flList.Diketene.make(1000)), FL.array(flList.Ketene.make(600), flList.Diketene.make(400)), ZL_IS);

        RM.        BurnMixer.addRecipe1(F, 120, 80, OP.dust.mat(MT.P, 2), FL.array(FL.Oxygen.make(2500)), ZL_FS, matList.PhosphorusPentoxide.getDust(3));

        recipeMaps.HeatMixer.addRecipe1(F, 440, 200, matList.PhosphorusPentoxide.getDust(0), FL.array(flList.GlacialAceticAcid.make(1000)), FL.array(flList.Ketene.make(900), flList.Diketene.make(100), FL.DistW.make(1000)), ZL_IS);

        recipeMaps.HeatMixer.addRecipe1(F, 2141, 80, OP.dust.mat(MT.TiO2,2), FL.array(MT.H2SO4.liquid(4*U, true)), ZL_FS, matList.TitanicSulfate.getDust(3));

        RM.Mixer.addRecipe1(F, 200, 240, matList.TitanicSulfate.getDust(2), FL.array(flList.Ketene.make(1500)), FL.array(flList.ImpureTitaniumAcylate.make(1000)), ZL_IS);

        recipeMaps.HeatMixer.addRecipe1(F, 450, 80, OP.dust.mat(MT.NaOH, 2), FL.array(flList.ImpureTitaniumAcylate.make(1000)), FL.array(flList.CrudeTitaniumAcylate.make(1000)), ZL_IS);

        RM.Distillery.addRecipe0(F, 250, 120, FL.array(flList.PurifyTitaniumAcylate.make(1000)), FL.array(flList.TitaniumAcylate.make(1000)), ZL_IS);

        recipeMaps.HeatMixer.addRecipe0(F, 480, 800, FL.array(flList.TitaniumAcylate.make(1000), flList.BariumAcetate.make(1000)), ZL_FS, matList.NanoBariumTitanate.getDust(1));

    }
}
