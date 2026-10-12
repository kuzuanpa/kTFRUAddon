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

package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import gregapi.data.FL;

import static gregapi.data.CS.*;

/** Large steam turbine: every 170 mB of steam yields 1 mB of distilled water and 85 RU of energy. */
public class SteamTurbine {
    public static void init(){
        recipeMaps.SteamTurbine.addRecipe0(F, -85, 1, FL.array(FL.Steam.make(170)), FL.array(FL.DistW.make(1)));
        recipeMaps.SteamTurbine.addRecipe0(F, -85, 1, FL.array(FL.Steam_IC2.make(170)), FL.array(FL.DistW.make(1)));
        recipeMaps.SteamTurbine.addRecipe0(F, -85, 1, FL.array(FL.Steam_IC2_Superheated.make(170)), FL.array(FL.DistW.make(1)));
    }
}
