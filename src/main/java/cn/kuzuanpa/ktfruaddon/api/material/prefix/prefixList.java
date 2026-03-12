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


package cn.kuzuanpa.ktfruaddon.api.material.prefix;

import cn.kuzuanpa.ktfruaddon.recipe.recipe.listener.recipePrefixItems;
import gregapi.code.ICondition;
import gregapi.data.MT;
import gregapi.data.TD;
import gregapi.oredict.OreDictPrefix;

import static gregapi.data.CS.U;
import static gregapi.data.TD.Atomic.ANTIMATTER;

public class prefixList {
    static final ICondition<?> conditionLargeTurbine = new ICondition.And<>(TD.Properties.HAS_TOOL_STATS, TD.ItemGenerator.MOLTEN, new ICondition.Or<>(TD.Atomic.METAL, TD.Compounds.ALLOY), MT.AnnealedCopper.NOT, MT.WroughtIron.NOT /*Could not register Recipe*/, MT.Alumite.NOT);//it's too weak and have negative efficiency


    public static final OreDictPrefix flywheel = create("flywheel", "Flywheels", "", " Flywheel").setMaterialStats(8*U).setCondition(new ICondition.And<>(TD.Properties.HAS_TOOL_STATS, TD.Processing.SMITHABLE)).addListener(new recipePrefixItems.Parts_Flywheel(ANTIMATTER.NOT));
    public static final OreDictPrefix largeTurbineBlade = create("bladeLargeTurbine", "Large Gas Turbines", "Large ", " Turbine Blade").setMaterialStats(2 * U).setCondition(conditionLargeTurbine);
    public static final OreDictPrefix turbineLargeGas = create("turbineLargeGas", "Large Gas Turbines", "Large ", " Gas Turbine").setMaterialStats(72 * U).setCondition(conditionLargeTurbine).setTextureSetName("largeTurbine");
    public static final OreDictPrefix turbineLargeGasChecked = create("turbineLargeGasChecked", "Checked Large Gas Turbines", "Checked Large ", " Gas Turbine").setMaterialStats(72 * U).setCondition(conditionLargeTurbine).setTextureSetName("largeTurbine");
    public static final OreDictPrefix turbineLargeGasDamaged = create("turbineLargeGasDamaged", "Used Large Gas Turbines", "Used Large ", " Gas Turbine").setMaterialStats(72 * U).setCondition(conditionLargeTurbine).setTextureSetName("largeTurbine");
    public static final OreDictPrefix turbineLargeSteam = create("turbineLargeSteam", "Large Steam Turbines", "Large ", " Steam Turbine").setMaterialStats(48 * U).setCondition(conditionLargeTurbine).setTextureSetName("largeTurbine");
    public static final OreDictPrefix turbineLargeSteamChecked = create("turbineLargeSteamChecked", "Checked Large Steam Turbines", "Checked Large ", " Steam Turbine").setMaterialStats(48 * U).setCondition(conditionLargeTurbine).setTextureSetName("largeTurbine");
    public static final OreDictPrefix turbineLargeSteamDamaged = create("turbineLargeSteamDamaged", "Used Large Steam Turbines", "Used Large ", " Steam Turbine").setMaterialStats(48 * U).setCondition(conditionLargeTurbine).setTextureSetName("largeTurbine").addListener(new recipePrefixItems.Parts_Turbine(ANTIMATTER.NOT));

    public static final OreDictPrefix CommercialPureDust = create("commercialPureDust", "Commercial Pure Dust", "Commercial Pure ", " Dust").setMaterialStats(U).setCondition(new ICondition.Nor<>(TD.Properties.WOOD, TD.Properties.COAL, TD.Properties.STONE, TD.Properties.FOOD, TD.Properties.MEAT));
    public static final OreDictPrefix AnalyticalPureDust = create("analyticalPureDust", "Analytical Pure Dust", "Analytical Pure ", " Dust").setMaterialStats(U).setCondition(new ICondition.Nor<>(TD.Properties.WOOD, TD.Properties.COAL, TD.Properties.STONE, TD.Properties.FOOD, TD.Properties.MEAT));
    public static final OreDictPrefix AbsolutelyPureDust = create("absolutelyPureDust", "Absolutely Pure Dust", "Absolutely Pure ", " Dust").setMaterialStats(U).setCondition(new ICondition.Nor<>(TD.Properties.WOOD, TD.Properties.COAL, TD.Properties.STONE, TD.Properties.FOOD, TD.Properties.MEAT));
    private static OreDictPrefix create(String aName, String aCategory, String aPreMaterial, String aPostMaterial) {
        return OreDictPrefix.createPrefix(aName).setCategoryName(aCategory).setLocalPrefixName(aCategory).setLocalItemName(aPreMaterial, aPostMaterial);
    }
}
