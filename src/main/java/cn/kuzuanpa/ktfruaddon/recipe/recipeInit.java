package cn.kuzuanpa.ktfruaddon.recipe;

import cn.kuzuanpa.ktfruaddon.recipe.recipe.*;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class recipeInit {
    public recipeInit(FMLInitializationEvent aEvent){
        new HeatMixer();
        new ParticleCollinder();
        new OreProcessing();
        new Chemistry();
    }
}
