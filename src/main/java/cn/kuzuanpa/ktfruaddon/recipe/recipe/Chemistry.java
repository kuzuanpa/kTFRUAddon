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
import cn.kuzuanpa.ktfruaddon.recipe.recipeManager;
import gregapi.data.*;
import gregapi.util.ST;

import static gregapi.data.CS.*;

public class Chemistry {
    public static void init(){
        recipeManager.HeatMixer.addRecipe1(F,32,20,OP.dust.mat(MT.C,0),FL.array(FL.Vinegar_Rice.make(100)),FL.array(flList.GlacialAceticAcid.make(30),FL.Water.make(60)),OP.dustTiny.mat(MT.Sugar,1));
        recipeManager.HeatMixer.addRecipe1(F,32,20,OP.dust.mat(MT.C,0),FL.array(FL.Vinegar_Apple.make(100)),FL.array(flList.GlacialAceticAcid.make(20),FL.Water.make(70)),OP.dustTiny.mat(MT.Sugar,1));
        recipeManager.HeatMixer.addRecipe1(F,32,20,OP.dust.mat(MT.C,0),FL.array(FL.Vinegar_Cane.make(100)),FL.array(flList.GlacialAceticAcid.make(20),FL.Water.make(70)),OP.dustTiny.mat(MT.Sugar,2));
        recipeManager.HeatMixer.addRecipe1(F,32,20,OP.dust.mat(MT.C,0),FL.array(FL.Vinegar_Grape.make(100)),FL.array(flList.GlacialAceticAcid.make(20),FL.Water.make(70)),OP.dustTiny.mat(MT.Sugar,1));
        recipeManager.FluidHeating.addRecipe0(F,256,20,FL.array(FL.Water.make(1)),FL.array(FL.DistW.make(1)));

        RM.Mixer.addRecipeX(F,64,20,ST.array(OP.dust.mat(MT.Ge,1),OP.dust.mat(MT.In,1),OP.dust.mat(MT.Nb,1),OP.dust.mat(MT.Ti,1),OP.dust.mat(MT.Mg,1)),ZL_FS,ZL_FS,matList.HensSoPretty.getDust(5));
        RM.ImplosionCompressor.addRecipeX(F,64,1,ST.array(matList.HensSoPretty.getDust(9),ST.make(MD.MC,"tnt",16),ST.tag(0)),ZL_FS,ZL_FS, ST.make(MD.MC,"spawn_egg",1,93));

        recipeManager.LaserCutter.addRecipe1(F,512,80,OP.plate.mat(MT.Au,8),ZL_FS,ZL_FS,ItemList.BatteryPoleGold.get(1));
        recipeManager.LaserCutter.addRecipe1(F,2048,80,OP.plate.mat(MT.Au,8),ZL_FS,ZL_FS,ItemList.BatteryPoleCarbon.get(1));
        recipeManager.LaserCutter.addRecipe1(F,2048,80,OP.plate.mat(MT.Au,8),ZL_FS,ZL_FS,ItemList.BatteryPolePlatinum.get(1));
        recipeManager.LaserCutter.addRecipe1(F,8192,80,OP.plate.mat(MT.Au,8),ZL_FS,ZL_FS,ItemList.BatteryPoleGraphene.get(1));

        recipeManager.FuelBattery.addRecipe2(F,-150,10, ItemList.BatteryPoleGold.get(0),ItemList.BatteryPoleCarbon.get(0),FL.array(FL.Hydrogen.make(100),FL.Oxygen.make(50),flList.SolutionPotassiumHydroxide.make(0)),FL.array(FL.DistW.make(50)),ZL_IS);
        recipeManager.FuelBattery.addRecipe2(F,-155,10, ItemList.BatteryPolePlatinum.get(0),ItemList.BatteryPoleCarbon.get(0),FL.array(FL.Hydrogen.make(100),FL.Oxygen.make(50),flList.SolutionPotassiumHydroxide.make(0)),FL.array(FL.DistW.make(50)),ZL_IS);
        recipeManager.FuelBattery.addRecipe2(F,-160,10, ItemList.BatteryPolePlatinum.get(0),ItemList.BatteryPoleGraphene.get(0),FL.array(FL.Hydrogen.make(100),FL.Oxygen.make(50),flList.SolutionPotassiumHydroxide.make(0)),FL.array(FL.DistW.make(50)),ZL_IS);

        recipeManager.FuelBattery.addRecipe2(F,-374,10, ItemList.BatteryPoleGold.get(0),ItemList.BatteryPoleCarbon.get(0),FL.array(FL.Methane.make(10),FL.Oxygen.make(20),MT.K2CO3.liquid(0,false)),FL.array(FL.DistW.make(20),FL.CarbonDioxide.make(10)),ZL_IS);
        recipeManager.FuelBattery.addRecipe2(F,-379,10, ItemList.BatteryPolePlatinum.get(0),ItemList.BatteryPoleCarbon.get(0),FL.array(FL.Methane.make(10),FL.Oxygen.make(20),MT.K2CO3.liquid(0,false)),FL.array(FL.DistW.make(20),FL.CarbonDioxide.make(10)),ZL_IS);
        recipeManager.FuelBattery.addRecipe2(F,-384,10, ItemList.BatteryPolePlatinum.get(0),ItemList.BatteryPoleGraphene.get(0),FL.array(FL.Methane.make(10),FL.Oxygen.make(20),MT.K2CO3.liquid(0,false)),FL.array(FL.DistW.make(20),FL.CarbonDioxide.make(10)),ZL_IS);

    }
}
