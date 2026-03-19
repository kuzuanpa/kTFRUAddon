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
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.AbstractTransmittable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransmittable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransmittableType;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.kTestTrans;
import codechicken.lib.vec.BlockCoord;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class DreamBrain {
    protected List<DreamPlanBase> plans = new ArrayList<>();
    public DreamItemPool dreamItemPool = new DreamItemPool();
    public Map<ITransmittableType, List<DreamPlanBase>> PlanSearchPool = new HashMap<>();
    public AtomicBoolean PlanPoolLock = new AtomicBoolean(false);

    public List<DreamPlanBase> searchItemPlan(ITransmittableType requiredType){
        List<DreamPlanBase> list = PlanSearchPool.get(requiredType);
        return list==null?new ArrayList<>():list;
    }


    public byte tryMakeAbstractItem(ITransmittableType requiredItem, long amount, PlanTreeNode treeNode) {
        if(!dreamItemPool.AbstractOverallCondition.test(requiredItem))return -1;
        return tryMakeAbstractItem0(requiredItem, amount, treeNode);
    }

    public byte tryMakeAbstractItem0(ITransmittableType requiredItem, long amount, PlanTreeNode treeNode){
        List<ITransmittableType> list = dreamItemPool.abstractTransmittableList.stream().filter(t->t instanceof AbstractTransmittable.AbstractTransmittableType).map(t-> ((AbstractTransmittable.AbstractTransmittableType) t)).filter(t-> t.condition.test(requiredItem)).collect(Collectors.toList());
        if(list.isEmpty())return -1;

        for (ITransmittableType absItem : list) {
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

    protected byte makeItem0(ITransmittableType requiredItem, long amount, PlanTreeNode treeNode){
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

    public long makePlan(DreamPlanBase plan, long count, ITransmittable requiredItemReal, PlanTreeNode treeNode){
        treeNode.count = count;
        treeNode.plan = plan;
        treeNode.resultItem = requiredItemReal.getType();
        for (ITransmittable ing : plan.getIngredientList(requiredItemReal)) {
            long result = makeItem0(ing.getType(), ing.getAmount() * count,treeNode);
            if(result == -1) treeNode.reqItems.add(ing.initFrom(ing.getType(), ing.getAmount()*count));
        }
        return 0;
    }

    public static long ceilDiv(long a,long b){return (a+b-1)/b;}

    public void printTreeNode(PlanTreeNode treeNode, long depth){
        for (long i = 0; i < depth; i++) {
            out.print("|");
        }
        StringBuilder sb = new StringBuilder("-");
        for (ITransmittable reqItem : treeNode.reqItems) {
            sb.append(reqItem.toString());
        }
        out.print(treeNode.plan+"*"+treeNode.count+sb.toString()+"\n");
        for (PlanTreeNode subNode : treeNode.subNodes) {
            printTreeNode(subNode, depth +1);
        }
    }

    public static void main(String[] args){
        DreamBrain brain = new DreamBrain();
        brain.dreamItemPool.abstractTransmittableList.add(new DreamPlanTestAbstract.TestAbsTransmittable("Abs0-", 1).getType());
        brain.dreamItemPool.abstractTransmittableList.add(new DreamPlanTestAbstract.TestAbsTransmittable("Abs1-", 1).getType());
        brain.dreamItemPool.updateAbstractCondition();
        brain.addPlan(new DreamPlanTestAbstract(new BlockCoord(), "Abs0-", "Abs1-"));
        brain.addPlan(new DreamPlanSimple(new BlockCoord(), Collections.singletonList(new kTestTrans("Abs1-A", 3)), Collections.singletonList(new kTestTrans("A", 1))));
        brain.addPlan(new DreamPlanSimple(new BlockCoord(), Collections.singletonList(new kTestTrans("B", 1)), Collections.singletonList(new kTestTrans("Abs0-A", 1))));
        brain.addPlan(new DreamPlanSimple(new BlockCoord(), Lists.newArrayList(new kTestTrans("G", 1)), Collections.singletonList(new kTestTrans("D", 3))));
        brain.addPlan(new DreamPlanSimple(new BlockCoord(), Lists.newArrayList(new kTestTrans("D", 1), new kTestTrans("K", 11)), Collections.singletonList(new kTestTrans("B", 1))));
        brain.addPlan(new DreamPlanSimple(new BlockCoord(), Lists.newArrayList(new kTestTrans("V", 1), new kTestTrans("H", 17)), Collections.singletonList(new kTestTrans("G", 1))));
        PlanTreeNode treeNode = new PlanTreeNode(-1);
        brain.makeItem0(new kTestTrans("A", 1).getType(), 5, treeNode);
        brain.printTreeNode(treeNode, 0);

        DreamerPool pool = new DreamerPool();
        pool.doTreeNode(treeNode);
    }
    public boolean addPlan(DreamPlanBase plan){
        PlanPoolLock.set(true);
        plans.add(plan);
        plan.getResultList().forEach(transmittable -> addPlanSearchInfo(transmittable.getType(), plan));
        PlanPoolLock.set(false);
        return true;
    }
    public boolean removePlan(DreamPlanBase plan){
        PlanPoolLock.set(true);
        plans.remove(plan);
        plan.getResultList().forEach(item -> removePlanSearchInfo(item.getType(), plan));
        PlanPoolLock.set(false);
        return true;
    }
    protected boolean addPlanSearchInfo(ITransmittableType result, DreamPlanBase plan){
        PlanSearchPool.putIfAbsent(result, new ArrayList<>());
        PlanSearchPool.get(result).add(plan);
        return true;
    }
    /**@return true if removed something, false otherwise**/
    protected boolean removePlanSearchInfo(ITransmittableType output, @Nullable DreamPlanBase plan){
        List<DreamPlanBase> list = PlanSearchPool.get(output);
        if(list == null)return false;
        boolean result = list.remove(plan);
        if(list.isEmpty())PlanSearchPool.remove(output);
        return result;
    }

    public static class PlanTreeNode{
        public DreamPlanBase plan;
        public ITransmittableType resultItem;
        public long count;
        public List<PlanTreeNode> subNodes = new ArrayList<>();
        public List<ITransmittable> reqItems = new ArrayList<>();

        public PlanTreeNode() {
        }

        public PlanTreeNode(long signal) {
            count = signal;
        }
    }
}
