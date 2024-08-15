/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.material.prefix;

import cn.kuzuanpa.ktfruaddon.recipe.recipe.listener.recipePrefixItems;
import gregapi.code.ICondition;
import gregapi.data.TD;
import gregapi.oredict.OreDictPrefix;

import static gregapi.data.CS.U;
import static gregapi.data.TD.Atomic.ANTIMATTER;

public class prefixList {
    static final ICondition<?> conditionLargeTurbine = new ICondition.And<>(TD.Properties.HAS_TOOL_STATS, TD.ItemGenerator.MOLTEN, new ICondition.Or<>(TD.Atomic.METAL, TD.Compounds.ALLOY));


    public static final OreDictPrefix flywheel = create("flywheel", "Flywheels", "", " Flywheel").setMaterialStats(U).setCondition(new ICondition.And<>(TD.Properties.HAS_TOOL_STATS, TD.ItemGenerator.MOLTEN)).setMinStacksize(64L);
    public static final OreDictPrefix largeTurbineBlade = create("bladeLargeTurbine", "LargeTurbines", "Large ", " Turbine Blade").setMaterialStats(2 * U).setCondition(conditionLargeTurbine).setMinStacksize(1L);
    public static final OreDictPrefix gasLargeTurbine = create("turbineLargeGas", "LargeTurbines", "Large ", " Gas Turbine").setMaterialStats(72 * U).setCondition(conditionLargeTurbine).setMinStacksize(1L).setTextureSetName("largeTurbine");
    public static final OreDictPrefix gasLargeTurbineChecked = create("turbineLargeGasChecked", "LargeTurbines", "Checked Large ", " Gas Turbine").setMaterialStats(72 * U).setCondition(conditionLargeTurbine).setMinStacksize(1L).setTextureSetName("largeTurbine");
    public static final OreDictPrefix gasLargeTurbineDamaged = create("turbineLargeGasDamaged", "LargeTurbines", "Damaged Large ", " Gas Turbine").setMaterialStats(72 * U).setCondition(conditionLargeTurbine).setMinStacksize(1L).setTextureSetName("largeTurbine");
    public static final OreDictPrefix steamLargeTurbine = create("turbineLargeSteam", "LargeTurbines", "Large ", " Steam Turbine").setMaterialStats(48 * U).setCondition(conditionLargeTurbine).setMinStacksize(1L).setTextureSetName("largeTurbine");
    public static final OreDictPrefix steamLargeTurbineChecked = create("turbineLargeSteamChecked", "LargeTurbines", "Checked Large ", " Steam Turbine").setMaterialStats(48 * U).setCondition(conditionLargeTurbine).setMinStacksize(1L).setTextureSetName("largeTurbine");
    public static final OreDictPrefix steamLargeTurbineDamaged = create("turbineLargeSteamDamaged", "LargeTurbines", "Damaged Large ", " Steam Turbine").setMaterialStats(48 * U).setCondition(conditionLargeTurbine).setMinStacksize(1L).setTextureSetName("largeTurbine").addListener(new recipePrefixItems.Parts_Turbine(ANTIMATTER.NOT));
    private static OreDictPrefix create(String aName, String aCategory, String aPreMaterial, String aPostMaterial) {
        return OreDictPrefix.createPrefix(aName).setCategoryName(aCategory).setLocalPrefixName(aCategory).setLocalItemName(aPreMaterial, aPostMaterial);
    }
}
