package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.material.materialPreInit;
import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.data.RM;
import gregapi.oredict.OreDictMaterial;
import gregapi.recipes.Recipe;
import gregapi.util.OM;
import gregapi.util.ST;

import static cn.kuzuanpa.ktfruaddon.recipe.recipeManager.HeatMixer;
import static gregapi.data.CS.*;

public class OreProcessing {
    public OreProcessing() {
        //Cr processing
        Recipe.RecipeMap.RECIPE_MAPS.get("gt.recipe.electrolyzer").mRecipeList.remove("");
        RM.Bath.addRecipeX(T,0, 512 , ST.array(OM.dust(OreDictMaterial.get(9113), U*2), OM.dust(OreDictMaterial.get(30014), U*9)), FL.array(MT.H2SO4.liquid(6000,T)), FL.array(FL.Water.make(4000)), OM.dust(OreDictMaterial.get(30012), U*6), OM.dust(OreDictMaterial.get(30013), U*3));
        RM.Mixer.addRecipe0(T,64,82,FL.array(MT.NH3.liquid(3000,T),MT.H2SO4.liquid(1500,T)), FL.Water.make(1000),ST.array(OM.dust(OreDictMaterial.get(30014),U*9)));
        HeatMixer.addRecipe1(T,256,160,OM.dust(OreDictMaterial.get(30011)),FL.array(MT.O.gas(U *4,T )),FL.array(FL.Nitrogen.make(250)),OM.dust(MT.CrO2, U *1));
        //Al processing
        HeatMixer.addRecipeX(T,120,80,ST.array(OM.dust(MT.CaCO3,U),OM.dust(MT.Na2CO3,U *2), OM.dust(OreDictMaterial.get(9105),U)),ZL_FS,FL.CarbonDioxide.make(3000),OM.dust(OreDictMaterial.get(30020),U*2));
        HeatMixer.addRecipeX(T,120,80,ST.array(OM.dust(MT.NaOH,U * 4)),FL.array(FL.make("acidpickledbauxide",2000)),FL.array(),OM.dust(OreDictMaterial.get(30020),U*2));




















    }
}
