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

package cn.kuzuanpa.ktfruaddon.DreamPlanner.test;

import cn.kuzuanpa.ktfruaddon.DreamPlanner.api.plan.DreamPlanBase;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.*;
import codechicken.lib.vec.BlockCoord;

import java.util.Collections;
import java.util.List;

public class DreamPlanTestAbstract extends DreamPlanBase {
    public long complexity;
    AbstractStringTestTransferable recipe, result;

    public List<TransferableStack> getResultList(){
        return Collections.singletonList(result.make(1));
    };
    public long getResultNum(ITransferable output){
        return 1;
    }
    public List<TransferableStack> getIngredientList(TransferableStack result){
        return Collections.singletonList(new StringTestTransferable(recipe.prefix + ((StringTestTransferable) result.type).content.replaceFirst(this.result.prefix, "")).make(1));
    }
    public DreamPlanTestAbstract(BlockCoord interfacePos, String recipe, String result){
        super(interfacePos);
        this.recipe=new AbstractStringTestTransferable(recipe);
        this.result=new AbstractStringTestTransferable(result);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PTA");
            sb.append(recipe.toString());
        sb.append("->");
            sb.append(result.toString());
        return sb.toString();
    }

}
