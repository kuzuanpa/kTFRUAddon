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

package cn.kuzuanpa.ktfruaddon.DreamPlanner.api.plan;

import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransferable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.TransferableStack;
import codechicken.lib.vec.BlockCoord;

import java.util.List;

public abstract class DreamPlanBase {
    public final BlockCoord InterfacePos;
    public int complexity;
    public abstract List<TransferableStack> getResultList();
    public abstract long getResultNum(ITransferable output);
    /**Get Recipe needed Ingredients from result**/
    public abstract List<TransferableStack> getIngredientList(TransferableStack result);
    public DreamPlanBase(BlockCoord interfacePos){
        InterfacePos = interfacePos;
    }
}
