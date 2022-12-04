package cn.kuzuanpa.ktfruaddon.recipe;

import gregapi.recipes.Recipe;

import static gregapi.data.CS.*;

public class recipeManager {
    public static final Recipe.RecipeMap
              HeatMixer    = new Recipe.RecipeMap(null, "ktfru.recipe.heatmixer", "Heat Mixer", null, 0, 1, RES_PATH_GUI + "machines/Mixer",/*IN-OUT-MIN-ITEM=*/ 6, 1, 0,/*IN-OUT-MIN-FLUID=*/ 6, 2, 0,/*MIN*/ 2,/*AMP=*/ 1, "", 1, "", T, T, T, T, F, T, T)
            , Assembler        = new Recipe.RecipeMap(null, "ktfru.recipe.assembler", "Assembler", null, 0, 1, RES_PATH_GUI + "machines/Assembler",/*IN-OUT-MIN-ITEM=*/ 2, 1, 1,/*IN-OUT-MIN-FLUID=*/ 1, 0, 0,/*MIN*/ 0,/*AMP=*/ 1, "", 1, "", T, F, F, T, F, T, T)
            , LaserCutter      = new Recipe.RecipeMap(null, "ktfru.recipe.lasercutter", "Laser Cutter", null, 0, 1, RES_PATH_GUI + "machines/LaserCutter",/*IN-OUT-MIN-ITEM=*/ 1, 3, 1,/*IN-OUT-MIN-FLUID=*/ 0, 0, 0,/*MIN*/ 0,/*AMP=*/ 1, "", 1, "", T, T, T, T, F, F, F)
            , ParticleCollider = new Recipe.RecipeMap(null, "ktfru.recipe.particlecollider", "Particle Collider", null, 0, 1, RES_PATH_GUI + "machines/Fusion",/*IN-OUT-MIN-ITEM=*/ 2, 6, 1,/*IN-OUT-MIN-FLUID=*/ 2, 6, 0,/*MIN*/ 2,/*AMP=*/ 1, "Start: ", 1, " EU", T, T, T, T, F, F, F)
            , MaskAligner      = new Recipe.RecipeMap(null, "ktfru.recipe.maskaligner", "Mask Aligner", null, 0, 1, RES_PATH_GUI + "machines/Fusion",/*IN-OUT-MIN-ITEM=*/ 2, 6, 1,/*IN-OUT-MIN-FLUID=*/ 2, 6, 0,/*MIN*/ 2,/*AMP=*/ 1, "Start: ", 1, " EU", T, T, T, T, F, F, F)
            , BioLab           = new Recipe.RecipeMap(null, "ktfru.recipe.biolab"                       , "Biochemical Research Lab"        , null, 0, 1, RES_PATH_GUI+"machines/BioLab"                    ,/*IN-OUT-MIN-ITEM=*/ 6, 6, 0,/*IN-OUT-MIN-FLUID=*/ 6, 6, 0,/*MIN*/ 1,/*AMP=*/ 1, "Disinfection: "      ,    1, " LU"   , T, T, T, T, F, F, F)

            ;

}
