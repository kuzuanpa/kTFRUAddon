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

import cn.kuzuanpa.ktfruaddon.DreamPlanner.api.pool.DreamItemPool;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.TransferableStack;

public class DreamerPool {
    public void requestMakeItem(DreamPlanBase plan, TransferableStack result, long required){
        try {
            DreamItemPool pool = new DreamItemPool();
            System.out.print("makeing plan: "+plan +"x"+required+"\n");

            for (int i = 0; i < required; i++) {
                plan.getIngredientList(result).forEach(ing -> pool.tryRemoveItem(ing.type, ing.amount));
                Thread.sleep(10);
                pool.requestAddItem(result.type, result.amount);
            }
            } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void doTreeNode(DreamBrain.PlanTreeNode node){
        if(!node.subNodes.isEmpty()) node.subNodes.forEach(this::doTreeNode);
        if(node.planRepeatCount <= 0)return;
        requestMakeItem(node.plan, node.resultItem.make(node.planRepeatCount), (long) Math.ceil(node.planRepeatCount *1F/node.plan.getResultNum(node.resultItem)));
    }
}
