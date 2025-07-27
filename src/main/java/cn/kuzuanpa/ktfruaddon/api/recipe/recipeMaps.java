/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.api.recipe;

import gregapi.recipes.Recipe;

import static gregapi.data.CS.*;

public class recipeMaps {
    public static final Recipe.RecipeMap
              LightMixer              = new Recipe.RecipeMap(null, "ktfru.recipe.lightmixer"             , "Light Mixer"               , null, 0, 1, RES_PATH_GUI + "machines/LightMixer",               6, 3, 0, 6, 6, 0,  1,  1, "", 1, "", F, T, T, T, T, F, T, T)
            , HeatMixer               = new Recipe.RecipeMap(null, "ktfru.recipe.heatmixer"              , "Heat Mixer"                , null, 0, 1, RES_PATH_GUI + "machines/HeatMixer",                6, 3, 0, 6, 2, 0,  1,  1, "", 1, "", F, T, T, T, T, F, T, T)
            , UltrasonicMixer         = new Recipe.RecipeMap(null, "ktfru.recipe.ultrasonicmixer"        , "Ultrasonic Mixer"          , null, 0, 1, RES_PATH_GUI + "machines/UltrasonicMixer",          6, 3, 0, 6, 2, 0,  1,  1, "", 1, "", F, T, T, T, T, F, T, T)
            , Assembler               = new Recipe.RecipeMap(null, "ktfru.recipe.assembler"              , "Circuit Assembler"         , null, 0, 1, RES_PATH_GUI + "machines/Assembler",                9, 1, 1, 1, 0, 0,  1,  1, "", 1, "", F, T, T, T, T, F, T, T)
            , LaserCutter             = new Recipe.RecipeMap(null, "ktfru.recipe.lasercutter"            , "Laser Cutter"              , null, 0, 1, RES_PATH_GUI + "machines/LaserCutter",              2, 3, 1, 1, 0, 0,  1,  1, "", 1, "", F, T, T, T, T, F, T, T)
            , MaskAligner             = new Recipe.RecipeMap(null, "ktfru.recipe.maskaligner"            , "Mask Aligner"              , null, 2, 1, RES_PATH_GUI + "machines/MaskAligner",              3, 3, 1, 3, 0, 0,  1,  1, "", 1, "", F, T, T, T, T, F, T, T)
            , EDA                     = new Recipe.RecipeMap(null, "ktfru.recipe.eda"                    , "Electronics Designer"      , null, 0, 1, RES_PATH_GUI + "machines/ElectronicsDesigner",      6, 3, 1, 3, 0, 0,  1,  1, "", 1, "", F, T, T, T, T, F, T, T)
            , FlawDetector            = new Recipe.RecipeMap(null, "ktfru.recipe.flawdetector"           , "Flaw Detector"             , null, 0, 1, RES_PATH_GUI + "machines/FlawDetector",             3, 3, 1, 0, 0, 0,  1,  1, "", 1, "", F, T, T, T, T, F, T, T)
            , WaferCoater             = new Recipe.RecipeMap(null, "ktfru.recipe.wafercoater"            , "Wafer Coater"              , null, 0, 1, RES_PATH_GUI + "machines/WaferCoater",              3, 3, 0, 3, 0, 0,  1,  1, "", 1, "", F, T, T, T, T, F, T, T)
            , WaferTester             = new Recipe.RecipeMap(null, "ktfru.recipe.wafertester"            , "Wafer Tester"              , null, 0, 1, RES_PATH_GUI + "machines/WaferTester",              3, 3, 0, 0, 0, 0,  1,  1, "", 1, "", F, T, T, T, T, F, T, T)
            , Ionizer                 = new Recipe.RecipeMap(null, "ktfru.recipe.ionizer"                , "Ionizer"                   , null, 0, 1, RES_PATH_GUI + "machines/Ionizer",                  6, 3, 0, 3, 3, 0,  1,  1, "", 1, "", F, T, T, T, T, F, T, T)
            , DistillTower            = new Recipe.RecipeMap(null, "ktfru.recipe.distilltower"           , "Distillation Tower"        , null, 0, 1, RES_PATH_GUI + "machines/DistillationTower",        1, 3, 0, 1, 9, 0,  1,  1, "", 1, "", F, T, T, T, T, F, T, T)
            , SmallDistillTower       = new Recipe.RecipeMap(null, "ktfru.recipe.smalldistilltower"      , "Small Distillation Tower"  , null, 0, 1, RES_PATH_GUI + "machines/SmallDistillationTower",   1, 3, 0, 1, 6, 0,  1,  1, "", 1, "", F, T, T, T, T, F, T, T)
            , TinyDistillTower        = new Recipe.RecipeMap(null, "ktfru.recipe.tinydistilltower"       , "Tiny Distillation Tower"   , null, 0, 1, RES_PATH_GUI + "machines/TinyDistillationTower",    1, 3, 0, 1, 3, 0,  1,  1, "", 1, "", F, T, T, T, T, F, T, T)
            , CVD                     = new Recipe.RecipeMap(null, "ktfru.recipe.cvd"                    , "Chemical Vapor Depositor"  , null, 0, 1, RES_PATH_GUI + "machines/ChemicalVaporDepositor",   6, 3, 0, 1, 6, 0,  1,  1, "", 1, "", F, T, T, T, T, F, T, T)
            , Code                    = new Recipe.RecipeMap(null, "ktfru.recipe.code"                   , "Code(Internal)"            , null, 0, 1, RES_PATH_GUI + "machines/code",                     9, 3, 0, 1, 1, 0,  1,  1, "", 1, "", F, T, T, T, T, F, T, T)
            , FuelBattery             = new Recipe.RecipeMap(null, "ktfru.recipe.fuelbattery"            , "Fuel Battery"              , null, 0, 1, RES_PATH_GUI + "machines/FuelBattery",              2, 0, 2, 3, 2, 2,  4,  1, "", 1, "", F, T, T, T, T, F, T, T)
            , FluidHeating            = new Recipe.RecipeMap(null, "ktfru.recipe.fluidheating"           , "Fluid Heating"             , null, 0, 1, RES_PATH_GUI + "machines/FluidHeating",             0, 0, 0, 1, 1, 1,  1,  1, "", 1, "", F, T, T, T, T, F, T, T)
            , CNC                     = new Recipe.RecipeMap(null, "ktfru.recipe.cnc"                    , "CNC Processing"            , null, 0, 1, RES_PATH_GUI + "machines/CNC",                      3, 3, 1, 3, 0, 1,  1,  1, "", 1, "", F, T, T, T, T, F, T, T)
            , FuelDeburner            = new Recipe.RecipeMap(null, "ktfru.recipe.fueldeburner"           , "Fuel Deburner"             , null, 0, 1, RES_PATH_GUI + "machines/FusionTokamak",            6, 6, 0, 6, 6, 0,  1,  1, "Best: ", 1, "K", F, T, T, T, T, F, T, T)
            , OilMiner                = new Recipe.RecipeMap(null, "ktfru.recipe.fake.oilminer"          , "Oil Miner"                 , null, 0, 1, RES_PATH_GUI + "machines/OilMiner",                 1, 0, 0, 1, 1, 1,  1,  1, "", 1, "", F, F, T, F, F, F, F, F)
            , CrucibleModel           = new Recipe.RecipeMap(null, "ktfru.recipe.fake.cruciblemodel"     , "Crucible Model"            , null, 0, 1, RES_PATH_GUI + "machines/CrucibleModel",            2, 1, 2, 0, 0, 0,  2,  1, "", 1, "", F, F, T, F, F, F, F, F)
            , QuantumPetrochem        = new Recipe.RecipeMap(null, "ktfru.recipe.quantum.petrochem"      , "Quantum Petrochemical"     , null, 0, 1, RES_PATH_GUI + "machines/QuantumPetrochem",         1, 0, 0, 1, 6, 0,  1,  1, "", 1, "", F, F, T, F, F, F, T, T)
            , QuantumMoleculeOperator = new Recipe.RecipeMap(null, "ktfru.recipe.quantum.molecule"       , "Molecule Operator"         , null, 0, 1, RES_PATH_GUI + "machines/QuantumMolecule",          6, 0, 0, 6, 6, 0,  1,  1, "", 1, "", F, F, T, F, F, F, T, T)
            , FusionTokamak           = new Recipe.RecipeMap(null, "ktfru.recipe.fusion.tokamak"         , "Tokamak Fusion"            , null, 0, 1, RES_PATH_GUI + "machines/FusionTokamak",            3, 3, 0, 6, 6, 1,  1,  1, "Start: ", 1, "", F, T, T, T, T, F, F, F)
            , MagicConverter          = new Recipe.RecipeMap(null, "ktfru.recipe.magic.converter"        , "Magic Converter"           , null, 0, 1, RES_PATH_GUI + "machines/MagicConverter",           3, 3, 0, 6, 6, 1,  1,  1, "Start: ", 1, "", F, T, T, T, T, F, F, F)
            , RTG                     = new Recipe.RecipeMap(null, "ktfru.recipe.rtg"                    , "Radio Thermo Generator"    , null, 0, 1, RES_PATH_GUI + "machines/RTG",                      1, 2, 1, 0, 0, 0,  1,  1, "", 1, "", T, T, T, T, T, F, F, F)

    ;
    @Deprecated
    public static final Recipe.RecipeMap
            Fluidsolidifier        = new Recipe.RecipeMap(null, "ktfru.recipe.fluidsolidifier"    , "Fluid Solidifier"          , null, 0, 1, RES_PATH_GUI+"machines/Generifier"                ,  1, 1, 0,  1, 1, 0,  1,  1, "",    1, ""      , T, T, T, T, F, F, F)
            , ParticleCollider       = new Recipe.RecipeMap(null, "ktfru.recipe.particlecollider"   , "Particle Collider"         , null, 0, 1, RES_PATH_GUI + "machines/Fusion",  2, 6, 1,  2, 6, 0,  2,  1, "Start: ", 1, " EU", T, T, T, T, F, F, F)
;
}