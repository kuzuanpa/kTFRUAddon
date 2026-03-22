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
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransferable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.TransferableStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class DreamBrain {
    protected List<DreamPlanBase> plans = new ArrayList<>();
    public DreamItemPool dreamItemPool = new DreamItemPool();
    public Map<ITransferable, List<DreamPlanBase>> PlanSearchPool = new HashMap<>();
    public AtomicBoolean PlanPoolLock = new AtomicBoolean(false);

    public List<DreamPlanBase> searchItemPlan(ITransferable requiredType){
        List<DreamPlanBase> list = PlanSearchPool.get(requiredType);
        return list==null?new ArrayList<>():list;
    }

    public byte tryMakeAbstractItem(ITransferable requiredItem, long amount, PlanTreeNode treeNode){
        List<ITransferable> list = dreamItemPool.abstractTransmittableList.stream().filter(t-> t.isFit(requiredItem)).collect(Collectors.toList());
        if(list.isEmpty())return -1;

        for (ITransferable absItem : list) {
            List<DreamPlanBase> planList = searchItemPlan(absItem);

            if(planList.isEmpty())return -1;

            for (DreamPlanBase plan : planList) {
                PlanTreeNode subNode = new PlanTreeNode();
                makePlan( plan,ceilDiv(amount,plan.getResultNum(requiredItem)) , requiredItem.make(amount), subNode);
                treeNode.subNodes.add(subNode);
            }
            return 0;
        }
        return 0;
    }

    public byte makeItem(ITransferable requiredItem, long amount, PlanTreeNode treeNode){
        if(PlanPoolLock.get())return -2;

        amount -= dreamItemPool.tryRemoveItem(requiredItem, amount);

        if(amount <= 0)return 0;

        List<DreamPlanBase> planList = searchItemPlan(requiredItem);

        if(planList.isEmpty()) return tryMakeAbstractItem(requiredItem,amount,  treeNode);

        for (DreamPlanBase plan : planList) {
            PlanTreeNode subNode = new PlanTreeNode();
            makePlan( plan,ceilDiv(amount,plan.getResultNum(requiredItem)) , requiredItem.make(amount), subNode);
            treeNode.subNodes.add(subNode);
        }

        tryMakeAbstractItem(requiredItem,amount,  treeNode);

        return 0;
    }

    public long makePlan(DreamPlanBase plan, long count, TransferableStack requiredItemReal, PlanTreeNode treeNode){
        treeNode.planRepeatCount = count;
        treeNode.plan = plan;
        treeNode.resultItem = requiredItemReal.type;
        for (TransferableStack ing : plan.getIngredientList(requiredItemReal)) {
            long result = makeItem(ing.type, ing.amount * count,treeNode);
            if(result == -1) treeNode.reqItems.add(ing.clone(ing.amount*count));
        }
        return 0;
    }

    public static long ceilDiv(long a,long b){return (a+b-1)/b;}

    public void printTreeNode(PlanTreeNode treeNode, long depth){
        for (long i = 0; i < depth; i++) {
            out.print("|");
        }
        StringBuilder sb = new StringBuilder("-");
        for (TransferableStack reqItem : treeNode.reqItems) {
            sb.append(reqItem.toString()).append(", ");
        }
        out.print(treeNode.planRepeatCount +" * "+ treeNode.plan + " Required Items: "+ sb +"\n");
        for (PlanTreeNode subNode : treeNode.subNodes) {
            printTreeNode(subNode, depth +1);
        }
    }

    public boolean addPlan(DreamPlanBase plan){
        PlanPoolLock.set(true);
        plans.add(plan);
        plan.getResultList().forEach(transmittable -> addPlanSearchInfo(transmittable.type, plan));
        PlanPoolLock.set(false);
        return true;
    }
    public boolean removePlan(DreamPlanBase plan){
        PlanPoolLock.set(true);
        plans.remove(plan);
        plan.getResultList().forEach(item -> removePlanSearchInfo(item.type, plan));
        PlanPoolLock.set(false);
        return true;
    }
    protected boolean addPlanSearchInfo(ITransferable result, DreamPlanBase plan){
        PlanSearchPool.putIfAbsent(result, new ArrayList<>());
        PlanSearchPool.get(result).add(plan);
        return true;
    }
    /**@return true if removed something, false otherwise**/
    protected boolean removePlanSearchInfo(ITransferable output, @Nullable DreamPlanBase plan){
        List<DreamPlanBase> list = PlanSearchPool.get(output);
        if(list == null)return false;
        boolean result = list.remove(plan);
        if(list.isEmpty())PlanSearchPool.remove(output);
        return result;
    }

    public static class PlanTreeNode{
        public DreamPlanBase plan;
        public ITransferable resultItem;
        public long planRepeatCount;
        public List<PlanTreeNode> subNodes = new ArrayList<>();
        public List<TransferableStack> reqItems = new ArrayList<>();

        public PlanTreeNode() {
        }

        public PlanTreeNode(long signal) {
            planRepeatCount = signal;
        }
    }
}
