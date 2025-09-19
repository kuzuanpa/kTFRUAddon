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

import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.AbstractTransmittable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransmittable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransmittableType;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.kItemStack;
import codechicken.lib.vec.BlockCoord;
import gregapi.oredict.OreDictPrefix;
import gregapi.util.OM;

import java.util.Collections;
import java.util.List;

public class DreamPlanOreDictGT6 extends DreamPlanBase{
    public long complexity;
    GTOrePrefixTransmittable recipe, result;

    public List<ITransmittable> getResultList(){
        return Collections.singletonList(result);
    };
    public long getResultNum(ITransmittableType output){
        return result.getAmount();
    }
    public List<ITransmittable> getIngredientList(ITransmittable result){
        return Collections.singletonList(new kItemStack(recipe.oreDictPrefix.mat(OM.anydata(((kItemStack) result).getStack()).mMaterial, result.getAmount())));
    }
    public DreamPlanOreDictGT6(BlockCoord interfacePos, OreDictPrefix recipe, OreDictPrefix result){
        super(interfacePos);
        this.recipe=new GTOrePrefixTransmittable(recipe, 1);
        this.result=new GTOrePrefixTransmittable(result, 1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("POD");
            sb.append(recipe.toString());
        sb.append("->");
            sb.append(result.toString());
        return sb.toString();
    }

    public static class GTOrePrefixTransmittable extends AbstractTransmittable {
        public GTOrePrefixTransmittable(OreDictPrefix oreDictPrefix, long amount) {
            super(it-> it instanceof kItemStack && oreDictPrefix.contains(((kItemStack) it).getStack()), oreDictPrefix.mNameInternal, amount);
            this.oreDictPrefix = oreDictPrefix;
        }
        public OreDictPrefix oreDictPrefix;

    }
}
