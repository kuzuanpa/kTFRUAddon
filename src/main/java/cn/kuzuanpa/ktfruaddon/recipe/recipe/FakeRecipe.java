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
import cn.kuzuanpa.ktfruaddon.recipe.recipeManager;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.FL;

import static gregapi.data.CS.ZL_IS;

public class FakeRecipe {
    public static void init() {
        MultiTileEntityRegistry gRegistry = MultiTileEntityRegistry.getRegistry("gt.multitileentity");
        MultiTileEntityRegistry kRegistry = MultiTileEntityRegistry.getRegistry("ktfru.multitileentity");

        recipeManager.OilMiner.addRecipe1(false,false,true,false,true, 1, 1, kRegistry.getItem(30013), FL.Water.make(1), flList.AqueousOilExtraHeavy.make(1),ZL_IS);
        recipeManager.OilMiner.addRecipe1(false,false,true,false,true, 1, 1, kRegistry.getItem(30013), FL.Water.make(1), flList.AqueousOilHeavy.make(1),ZL_IS);
        recipeManager.OilMiner.addRecipe1(false,false,true,false,true, 1, 1, kRegistry.getItem(30013), FL.Water.make(1), flList.AqueousOilMedium.make(1),ZL_IS);
        recipeManager.OilMiner.addRecipe1(false,false,true,false,true, 1, 1, kRegistry.getItem(30013), FL.Water.make(1), flList.AqueousOilNormal.make(1),ZL_IS);
        recipeManager.OilMiner.addRecipe1(false,false,true,false,true, 1, 1, kRegistry.getItem(30013), FL.Water.make(1), flList.AqueousOilLight.make(1),ZL_IS);
        recipeManager.OilMiner.addRecipe1(false,false,true,false,true, 1, 1, kRegistry.getItem(30013), FL.Air.make(1), FL.Gas_Natural.make(1),ZL_IS);
    }
}
