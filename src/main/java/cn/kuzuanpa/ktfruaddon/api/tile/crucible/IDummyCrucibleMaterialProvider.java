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

package cn.kuzuanpa.ktfruaddon.api.tile.crucible;

import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictMaterialStack;
import org.jetbrains.annotations.Nullable;

public interface IDummyCrucibleMaterialProvider {
    /**@param selectMaterial Select what material to be extract, crucible won't return other material if this param != null**/
    @Nullable CrucibleOreDictMaterialStack extractMaterial(long amount, @Nullable OreDictMaterial selectMaterial);
    float getTemperature();
    class CrucibleOreDictMaterialStack{
        public OreDictMaterialStack stack;
        public boolean isEnough;
        /**@param isMaterialEnough You should set it in Provider side. mold won't check stack.mAmount anymore**/
        public CrucibleOreDictMaterialStack(OreDictMaterialStack stack,boolean isMaterialEnough){
            this. stack = stack;
            this.isEnough = isMaterialEnough;
        }
    }
}
