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

import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransmittable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransmittableType;
import codechicken.lib.vec.BlockCoord;

import java.util.ArrayList;
import java.util.List;

public class DreamPlanInternalAbstractCast extends DreamPlanBase{
    public List<ITransmittable> getResultList(){return new ArrayList<>();};
    public long getResultNum(ITransmittableType output) {return 0;};
    /**Get Recipe needed Ingredients from result**/
    public List<ITransmittable> getIngredientList(ITransmittable result){return new ArrayList<>();
    };
    public DreamPlanInternalAbstractCast(List<ITransmittableType> abstractItem, ITransmittableType realItem){
        super(null);
        this.absItem=abstractItem;
        this.realItem=realItem;
    }

    List<ITransmittableType> absItem;
    ITransmittableType realItem;
    @Override
    public String toString() {
        return "PI-AC."+absItem+"->"+realItem;
    }
}
