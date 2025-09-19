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

import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.*;
import codechicken.lib.vec.BlockCoord;
import gregapi.oredict.OreDictPrefix;
import gregapi.util.OM;

import java.util.Collections;
import java.util.List;

public class DreamPlanTestAbstract extends DreamPlanBase{
    public long complexity;
    TestAbsTransmittable recipe, result;

    public List<ITransmittable> getResultList(){
        return Collections.singletonList(result);
    };
    public long getResultNum(ITransmittableType output){
        return result.getAmount();
    }
    public List<ITransmittable> getIngredientList(ITransmittable result){
        return Collections.singletonList(new kTestTrans(recipe.prefix + ((kTestTrans) result).get().replaceFirst(this.result.prefix, ""), result.getAmount()));
    }
    public DreamPlanTestAbstract(BlockCoord interfacePos, String recipe, String result){
        super(interfacePos);
        this.recipe=new TestAbsTransmittable(recipe, 1);
        this.result=new TestAbsTransmittable(result, 1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PTA");
            sb.append(recipe.toString());
        sb.append("->");
            sb.append(result.toString());
        return sb.toString();
    }

    public static class TestAbsTransmittable extends AbstractTransmittable {
        public TestAbsTransmittable(String prefix, long amount) {
            super(it-> it instanceof kTestTrans.kTestTransType && ((kTestTrans.kTestTransType) it).string.startsWith(prefix), prefix, amount);
            this.prefix = prefix;
        }
        public String prefix;

        @Override
        public String toString() {
            return "P"+prefix;
        }
    }
}
