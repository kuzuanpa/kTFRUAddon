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

import java.util.List;

public class DreamPlanSimple extends DreamPlanBase{
    public long complexity;
    List<ITransmittable> recipe, result;
    public List<ITransmittable> getResultList(){
        return result;
    };
    public long getResultNum(ITransmittableType output){
        return result.stream().filter(re-> re.getType().equals(output)).mapToLong(ITransmittable::getAmount).sum();
    }
    public List<ITransmittable> getIngredientList(ITransmittable result){
        return recipe;
    }
    public DreamPlanSimple(BlockCoord interfacePos, List<ITransmittable> recipe, List<ITransmittable> result){
        super(interfacePos);
        this.recipe=recipe;
        this.result=result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("P");
        for (ITransmittable iTransmittable : recipe) {
            sb.append(iTransmittable.toString());
        }
        sb.append("->");
        for (ITransmittable iTransmittable : result) {
            sb.append(iTransmittable);
        }
        return sb.toString();
    }
}
