/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.recipe;

import cn.kuzuanpa.ktfruaddon.recipe.recipe.*;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

public class recipeInit {
    public static void init(FMLPostInitializationEvent aEvent){
        HeatMixer.init();
        ParticleCollinder.init();
        OreProcessing.init();
        Chemistry.init();
        Circuits.init();
        ComputerBuilding.init();
        OilProcessing.init();
        Plastic.init();
        CompactItem.init();
        Fusion.init();
        FakeRecipe.init();
        if(Loader.isModLoaded("terrafirmacraft"))TFCRecipe.init();
    }
}
