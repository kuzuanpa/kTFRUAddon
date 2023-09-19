/*
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
      LightMixer              = new Recipe.RecipeMap(null, "ktfru.recipe.lightmixer"             , "Light Mixer"               , "Light Mixer",                 0, 1, RES_PATH_GUI + "machines/LightMixer",               6, 3, 0, 6, 6, 0,  1,  1, "", 1, "", T, T, T, T, F, T, T)
    , PTFEMixer               = new Recipe.RecipeMap(null, "ktfru.recipe.ptfemixer"              , "PTFE Mixer"                , "PTFE Mixer",                  0, 1, RES_PATH_GUI + "machines/HeatMixer",                6, 2, 0, 6, 2, 0,  1,  1, "", 1, "", T, T, T, T, F, T, T)
    , AcidMixer               = new Recipe.RecipeMap(null, "ktfru.recipe.acidmixer"              , "Acid Mixer"                , "Acid Mixer",                  0, 1, RES_PATH_GUI + "machines/AcidMixer",                6, 2, 0, 6, 2, 0,  1,  1, "", 1, "", T, T, T, T, F, T, T)
    , AlkaliMixer             = new Recipe.RecipeMap(null, "ktfru.recipe.alkalimixer"            , "Alkali Mixer"              , "Alkali Mixer",                0, 1, RES_PATH_GUI + "machines/AlkaliMixer",              6, 2, 0, 6, 2, 0,  1,  1, "", 1, "", T, T, T, T, F, T, T)
    , HeatMixer               = new Recipe.RecipeMap(null, "ktfru.recipe.heatmixer"              , "Heat Mixer"                , "Heat Mixer",                  0, 1, RES_PATH_GUI + "machines/HeatMixer",                6, 2, 0, 6, 2, 0,  1,  1, "", 1, "", T, T, T, T, F, T, T)
    , AcidHeatMixer           = new Recipe.RecipeMap(null, "ktfru.recipe.acidheatmixer"          , "Acid Heat Mixer"           , "Acid Heat Mixer",             0, 1, RES_PATH_GUI + "machines/AcidHeatMixer",            6, 2, 0, 6, 2, 0,  1,  1, "", 1, "", T, T, T, T, F, T, T)
    , AlkaliHeatMixer         = new Recipe.RecipeMap(null, "ktfru.recipe.alkaliheatmixer"        , "Alkali Heat Mixer"         , "Alkali Heat Mixer",           0, 1, RES_PATH_GUI + "machines/AlkaliHeatMixer",          6, 2, 0, 6, 2, 0,  1,  1, "", 1, "", T, T, T, T, F, T, T)
    , PressureMixer           = new Recipe.RecipeMap(null, "ktfru.recipe.pressuremixer"          , "Pressure Mixer"            , "Pressure Mixer",              0, 1, RES_PATH_GUI + "machines/PressureMixer",            6, 2, 0, 6, 2, 0,  1,  1, "", 1, "", T, T, T, T, F, T, T)
    , AcidPressureMixer       = new Recipe.RecipeMap(null, "ktfru.recipe.acidpressuremixer"      , "Acid Pressure Mixer"       , "Acid Pressure Mixer",         0, 1, RES_PATH_GUI + "machines/AcidPressureMixer",        6, 2, 0, 6, 2, 0,  1,  1, "", 1, "", T, T, T, T, F, T, T)
    , AlkaliPressureMixer     = new Recipe.RecipeMap(null, "ktfru.recipe.alkalipressuremixer"    , "Alkali Pressure Mixer"     , "Alkali Pressure Mixer",       0, 1, RES_PATH_GUI + "machines/AlkaliPressureMixer",      6, 2, 0, 6, 2, 0,  1,  1, "", 1, "", T, T, T, T, F, T, T)
    , PTFEPressureMixer       = new Recipe.RecipeMap(null, "ktfru.recipe.ptfepressuremixer"      , "PTFE Pressure Mixer"       , "PTFE Pressure Mixer",         0, 1, RES_PATH_GUI + "machines/PTFEPressureMixer",        6, 2, 0, 6, 2, 0,  1,  1, "", 1, "", T, T, T, T, F, T, T)
    , PressureHeatMixer       = new Recipe.RecipeMap(null, "ktfru.recipe.pressureheatmixer"      , "Pressure Heat Mixer"       , "Pressure Heat Mixer",         0, 1, RES_PATH_GUI + "machines/PressureHeatMixer",        6, 2, 0, 6, 2, 0,  1,  1, "", 1, "", T, T, T, T, F, T, T)
    , AcidPressureHeatMixer   = new Recipe.RecipeMap(null, "ktfru.recipe.acidpressureheatmixer"  , "Acid Pressure Heat Mixer"  , "Acid Pressure Heat Mixer",    0, 1, RES_PATH_GUI + "machines/AcidPressureHeatMixer",    6, 2, 0, 6, 2, 0,  1,  1, "", 1, "", T, T, T, T, F, T, T)
    , AlkaliPressureHeatMixer = new Recipe.RecipeMap(null, "ktfru.recipe.alkalipressureheatmixer", "Alkali Pressure Heat Mixer", "Alkali Pressure Heat Mixer",  0, 1, RES_PATH_GUI + "machines/AlkaliPressureHeatMixer",  6, 2, 0, 6, 2, 0,  1,  1, "", 1, "", T, T, T, T, F, T, T)
    , Assembler               = new Recipe.RecipeMap(null, "ktfru.recipe.assembler"              , "Circuit Assembler"         , "Circuit Assembler",           0, 1, RES_PATH_GUI + "machines/Assembler",                9, 1, 1, 1, 0, 0,  1,  1, "", 1, "", T, T, T, T, F, F, F)
    , LaserCutter             = new Recipe.RecipeMap(null, "ktfru.recipe.lasercutter"            , "Laser Cutter"              , "Laser Cutter",                0, 1, RES_PATH_GUI + "machines/LaserCutter",              1, 3, 1, 1, 0, 0,  1,  1, "", 1, "", T, T, T, T, F, F, F)
    , MaskAligner             = new Recipe.RecipeMap(null, "ktfru.recipe.maskaligner"            , "Mask Aligner"              , "Mask Aligner",                2, 1, RES_PATH_GUI + "machines/MaskAligner",              3, 3, 1, 3, 3, 0,  1,  1, "", 1, "", T, T, T, T, F, F, F)
    , EDA                     = new Recipe.RecipeMap(null, "ktfru.recipe.eda"                    , "Electronics Designer"      , "Electronics Designer",        0, 1, RES_PATH_GUI + "machines/ElectronicsDesigner",      6, 3, 1, 3, 0, 0,  1,  1, "", 1, "", T, T, T, T, F, F, F)
    , WaferCoater             = new Recipe.RecipeMap(null, "ktfru.recipe.wafercoater"            , "Wafer Coater"              , "Wafer Coater",                0, 1, RES_PATH_GUI + "machines/WaferCoater",              3, 3, 0, 3, 0, 0,  1,  1, "", 1, "", T, T, T, T, F, F, F)
    , WaferTester             = new Recipe.RecipeMap(null, "ktfru.recipe.wafertester"            , "Wafer Tester"              , "Wafer Tester",                0, 1, RES_PATH_GUI + "machines/WaferTester",              3, 3, 0, 0, 0, 0,  1,  1, "", 1, "", T, T, T, T, F, F, F)
    , Ionizer                 = new Recipe.RecipeMap(null, "ktfru.recipe.ionizer"                , "Ionizer"                   , "Ionizer",                     0, 1, RES_PATH_GUI + "machines/Ionizer",                  6, 3, 0, 3, 3, 0,  1,  1, "", 1, "", T, T, T, T, F, F, F)
    , DistillTower            = new Recipe.RecipeMap(null, "ktfru.recipe.distilltower"           , "Distillation Tower"        , "Distillation Tower",          0, 1, RES_PATH_GUI + "machines/DistillationTower",        1, 3, 0, 1, 9, 0,  1,  1, "", 1, "", T, T, T, T, F, F, F)
    , SmallDistillTower       = new Recipe.RecipeMap(null, "ktfru.recipe.smalldistilltower"      , "Small Distillation Tower"  , "Small Distillation Tower",    0, 1, RES_PATH_GUI + "machines/SmallDistillationTower",   1, 3, 0, 1, 6, 0,  1,  1, "", 1, "", T, T, T, T, F, F, F)
    , TinyDistillTower        = new Recipe.RecipeMap(null, "ktfru.recipe.tinydistilltower"       , "Tiny Distillation Tower"   , "Tiny Distillation Tower",     0, 1, RES_PATH_GUI + "machines/TinyDistillationTower",    1, 3, 0, 1, 3, 0,  1,  1, "", 1, "", T, T, T, T, F, F, F)
    , CVD                     = new Recipe.RecipeMap(null, "ktfru.recipe.cvd"                    , "Chemical Vapor Depositor"  , "Chemical Vapor Depositor",    0, 1, RES_PATH_GUI + "machines/ChemicalVaporDepositor",   6, 3, 0, 1, 6, 0,  1,  1, "", 1, "", T, T, T, T, F, F, F)
    , Code                    = new Recipe.RecipeMap(null, "ktfru.recipe.code"                   , "Code(Internal)"            , "Code (Internal)",             0, 1, RES_PATH_GUI + "machines/code",                     9, 3, 0, 1, 1, 0,  1,  1, "", 1, "", T, T, T, T, F, F, F)
    , FuelBattery             = new Recipe.RecipeMap(null, "ktfru.recipe.fuelbattery"            , "Gas Battery"               , "Gas Battery",                 0, 1, RES_PATH_GUI + "machines/ChemicalVaporDepositor",   2, 0, 2, 3, 2, 2,  4,  1, "", 1, "", T, T, T, T, F, F, F)

    ;
    @Deprecated
    public static final Recipe.RecipeMap
            Fluidsolidifier        = new Recipe.RecipeMap(null, "ktfru.recipe.fluidsolidifier"    , "Fluid Solidifier"          , null, 0, 1, RES_PATH_GUI+"machines/Generifier"                ,  1, 1, 0,  1, 1, 0,  1,  1, "",    1, ""      , T, T, T, T, F, F, F)
            , ParticleCollider       = new Recipe.RecipeMap(null, "ktfru.recipe.particlecollider"   , "Particle Collider"         , "Particle Collider", 0, 1, RES_PATH_GUI + "machines/Fusion",  2, 6, 1,  2, 6, 0,  2,  1, "Start: ", 1, " EU", T, T, T, T, F, F, F)
;
}