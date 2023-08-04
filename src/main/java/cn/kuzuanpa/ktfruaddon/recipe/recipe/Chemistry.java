/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import cn.kuzuanpa.ktfruaddon.recipe.recipeManager;
import gregapi.data.FL;

import static gregapi.data.CS.F;
import static gregapi.data.CS.ZL_IS;

public class Chemistry {
    public static void init(){
        recipeManager.FuelBattery.addRecipe2(F,-64,4, ItemList.BatteryGoldPole.get(0),ItemList.BatteryCopperPole.get(0),FL.array(FL.Hydrogen.make(20),FL.Oxygen.make(10),FL.DistW.make(0)),FL.array(FL.DistW.make(5)),ZL_IS);
    }
}
