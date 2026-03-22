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

import java.util.ArrayList;
import java.util.List;

public class DreamPlanInternalAbstractCast extends DreamPlanBase{
    public List<TransferableStack> getResultList(){return new ArrayList<>();};
    public long getResultNum(ITransferable output) {return 0;};
    /**Get Recipe needed Ingredients from result**/
    public List<TransferableStack> getIngredientList(TransferableStack result){return new ArrayList<>();
    };
    public DreamPlanInternalAbstractCast(List<ITransferable> abstractItem, ITransferable realItem){
        super(null);
        this.absItem=abstractItem;
        this.realItem=realItem;
    }

    List<ITransferable> absItem;
    ITransferable realItem;
    @Override
    public String toString() {
        return "PI-AC."+absItem+"->"+realItem;
    }
}
