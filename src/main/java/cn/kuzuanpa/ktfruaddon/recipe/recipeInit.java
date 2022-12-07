package cn.kuzuanpa.ktfruaddon.recipe;

import cn.kuzuanpa.ktfruaddon.recipe.recipe.Assembler;
import cn.kuzuanpa.ktfruaddon.recipe.recipe.HeatMixer;
import cn.kuzuanpa.ktfruaddon.recipe.recipe.OreProcessing;
import cn.kuzuanpa.ktfruaddon.recipe.recipe.ParticleCollinder;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class recipeInit {
    public recipeInit(FMLInitializationEvent aEvent){
        new HeatMixer();
        new ParticleCollinder();
        new OreProcessing();
    }
}
