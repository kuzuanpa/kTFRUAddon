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
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class recipeInit {
    public recipeInit(FMLInitializationEvent aEvent){
        new HeatMixer();
        new ParticleCollinder();
        new OreProcessing();
        new Chemistry();
        new ComputerBuilding();
        new OilProcessing();
    }
}
