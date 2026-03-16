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
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransmittable;

public class DreamerPool {
    public void requestMakeItem(DreamPlanBase plan, ITransmittable result, long required){
        try {
            DreamItemPool pool = new DreamItemPool();
            System.out.print("makeing plan: "+plan +"x"+required+"\n");

            for (int i = 0; i < required; i++) {
                plan.getIngredientList(result).forEach(ing -> pool.requestRemoveItem(ing.getType(), ing.getAmount()));
                Thread.sleep(10);
                pool.requestAddItem(result.getType(), result.getAmount());
            }
            } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void doTreeNode(DreamBrain.PlanTreeNode node){
        if(!node.subNodes.isEmpty()) node.subNodes.forEach(this::doTreeNode);
        if(node.count <= 0)return;
        requestMakeItem(node.plan, node.resultItem.make(node.count), (long) Math.ceil(node.count*1F/node.plan.getResultNum(node.resultItem)));
    }
}
