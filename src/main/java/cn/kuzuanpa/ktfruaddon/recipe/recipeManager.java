/**
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.recipe;

import gregapi.recipes.Recipe;

import static gregapi.data.CS.*;

public class recipeManager {
    public static final Recipe.RecipeMap
              HeatMixer        = new Recipe.RecipeMap(null, "ktfru.recipe.heatmixer", "Heat Mixer", null, 0, 1, RES_PATH_GUI + "machines/Mixer"            ,/*IN-OUT-MIN-ITEM=*/ 6, 1, 0,/*IN-OUT-MIN-FLUID=*/ 6, 2, 0,/*MIN*/ 2,/*AMP=*/ 1, "", 1, "", T, T, T, T, F, T, T)
            , Assembler        = new Recipe.RecipeMap(null, "ktfru.recipe.assembler","Circuit Assembler"  , null, 0, 1, RES_PATH_GUI+"machines/Assembler"  ,/*IN-OUT-MIN-ITEM=*/ 9, 1, 1,/*IN-OUT-MIN-FLUID=*/ 1, 0, 0,/*MIN*/ 1,/*AMP=*/ 1, "", 1, "", T, T, T, T, F, F, F)
            , LaserCutter      = new Recipe.RecipeMap(null, "ktfru.recipe.lasercutter", "Laser Cutter", null, 0, 1, RES_PATH_GUI + "machines/LaserCutter"  ,/*IN-OUT-MIN-ITEM=*/ 1, 3, 1,/*IN-OUT-MIN-FLUID=*/ 0, 0, 0,/*MIN*/ 0,/*AMP=*/ 1, "", 1, "", T, T, T, T, F, F, F)
            , ParticleCollider = new Recipe.RecipeMap(null, "ktfru.recipe.particlecollider", "Particle Collider", null, 0, 1, RES_PATH_GUI + "machines/Fusion",/*IN-OUT-MIN-ITEM=*/ 2, 6, 1,/*IN-OUT-MIN-FLUID=*/ 2, 6, 0,/*MIN*/ 2,/*AMP=*/ 1, "Start: ", 1, " EU", T, T, T, T, F, F, F)
            , MaskAligner      = new Recipe.RecipeMap(null, "ktfru.recipe.maskaligner", "Mask Aligner", null, 0, 1, RES_PATH_GUI + "machines/Fusion",/*IN-OUT-MIN-ITEM=*/ 2, 6, 1,/*IN-OUT-MIN-FLUID=*/ 2, 6, 0,/*MIN*/ 2,/*AMP=*/ 1, "Start: ", 1, " EU", T, T, T, T, F, F, F)
            , BioLab           = new Recipe.RecipeMap(null, "ktfru.recipe.biolab"                       , "Biochemical Research Lab"        , null, 0, 1, RES_PATH_GUI+"machines/BioLab"                    ,/*IN-OUT-MIN-ITEM=*/ 6, 6, 0,/*IN-OUT-MIN-FLUID=*/ 6, 6, 0,/*MIN*/ 1,/*AMP=*/ 1, "Disinfection: "      ,    1, " LU"   , T, T, T, T, F, F, F)
            , Fluidsolidifier  = new Recipe.RecipeMap(null, "ktfru.recipe.fluidsolidifier"              , "Fluid Solidifier"                , null, 0, 1, RES_PATH_GUI+"machines/Generifier"                ,/*IN-OUT-MIN-ITEM=*/ 1, 1, 0,/*IN-OUT-MIN-FLUID=*/ 1, 1, 0,/*MIN*/ 1,/*AMP=*/ 1, ""                    ,    1, ""      , T, T, T, T, F, F, F)
            , Ionizer          = new Recipe.RecipeMap(null, "ktfru.recipe.ionizer"                      , "Ionizer"                         , null, 0, 1, RES_PATH_GUI+"machines/Ionizer"                   ,/*IN-OUT-MIN-ITEM=*/ 1, 1, 0,/*IN-OUT-MIN-FLUID=*/ 1, 1, 0,/*MIN*/ 1,/*AMP=*/ 1, ""                    ,    1, ""      , T, T, T, T, F, F, F)
            ;
}
