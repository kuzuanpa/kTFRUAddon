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
import gregapi.data.MT;
import gregapi.data.TD;
import gregapi.oredict.OreDictPrefix;

import static gregapi.data.CS.U;
import static gregapi.data.TD.Atomic.ANTIMATTER;

public class prefixList {
    static final ICondition<?> conditionLargeTurbine = new ICondition.And<>(TD.Properties.HAS_TOOL_STATS, TD.ItemGenerator.MOLTEN, new ICondition.Or<>(TD.Atomic.METAL, TD.Compounds.ALLOY), MT.Alumite.NOT);//Because it is too weak and have negative efficiency...


    public static final OreDictPrefix flywheel = create("flywheel", "Flywheels", "", " Flywheel").setMaterialStats(8*U).setCondition(new ICondition.And<>(TD.Properties.HAS_TOOL_STATS, TD.Processing.SMITHABLE)).addListener(new recipePrefixItems.Parts_Flywheel(ANTIMATTER.NOT));
    public static final OreDictPrefix largeTurbineBlade = create("bladeLargeTurbine", "Large Gas Turbines", "Large ", " Turbine Blade").setMaterialStats(2 * U).setCondition(conditionLargeTurbine);
    public static final OreDictPrefix turbineLargeGas = create("turbineLargeGas", "Large Gas Turbines", "Large ", " Gas Turbine").setMaterialStats(72 * U).setCondition(conditionLargeTurbine).setTextureSetName("largeTurbine");
    public static final OreDictPrefix turbineLargeGasChecked = create("turbineLargeGasChecked", "Checked Large Gas Turbines", "Checked Large ", " Gas Turbine").setMaterialStats(72 * U).setCondition(conditionLargeTurbine).setTextureSetName("largeTurbine");
    public static final OreDictPrefix turbineLargeGasDamaged = create("turbineLargeGasDamaged", "Damaged Large Gas Turbines", "Damaged Large ", " Gas Turbine").setMaterialStats(72 * U).setCondition(conditionLargeTurbine).setTextureSetName("largeTurbine");
    public static final OreDictPrefix turbineLargeSteam = create("turbineLargeSteam", "Large Steam Turbines", "Large ", " Steam Turbine").setMaterialStats(48 * U).setCondition(conditionLargeTurbine).setTextureSetName("largeTurbine");
    public static final OreDictPrefix turbineLargeSteamChecked = create("turbineLargeSteamChecked", "Checked Large Steam Turbines", "Checked Large ", " Steam Turbine").setMaterialStats(48 * U).setCondition(conditionLargeTurbine).setTextureSetName("largeTurbine");
    public static final OreDictPrefix turbineLargeSteamDamaged = create("turbineLargeSteamDamaged", "Damaged Large Steam Turbines", "Damaged Large ", " Steam Turbine").setMaterialStats(48 * U).setCondition(conditionLargeTurbine).setTextureSetName("largeTurbine").addListener(new recipePrefixItems.Parts_Turbine(ANTIMATTER.NOT));
    private static OreDictPrefix create(String aName, String aCategory, String aPreMaterial, String aPostMaterial) {
        return OreDictPrefix.createPrefix(aName).setCategoryName(aCategory).setLocalPrefixName(aCategory).setLocalItemName(aPreMaterial, aPostMaterial);
    }
}
