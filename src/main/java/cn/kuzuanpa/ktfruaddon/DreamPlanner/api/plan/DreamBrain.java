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
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class DreamBrain {
    protected List<DreamPlanBase> plans = new ArrayList<>();
    public DreamPool dreamPool = new DreamPool();
    public Map<ITransmittable, List<DreamPlanBase>> PlanSearchPool = new HashMap<>();
    public AtomicBoolean PlanPoolLock = new AtomicBoolean(false);


    public boolean addPlan(DreamPlanBase plan){
        PlanPoolLock.set(true);
        plans.add(plan);
        plan.getResultList().forEach(transmittable -> addPlanSearchInfo(transmittable.getSingle(), plan));
        PlanPoolLock.set(false);
        return true;
    }

    public List<DreamPlanBase> searchItemPlan(ITransmittable requiredItem){
        return new ArrayList<>(PlanSearchPool.get(requiredItem.getSingle()));
    }

    public long tryMakeAbstractItem(boolean alwaysMake, ITransmittable requiredItem){
        List<ITransmittable> list = dreamPool.abstractTransmittableList.stream().filter(t-> t.getCondition().test(requiredItem)).collect(Collectors.toList());
        long requiredAmount = requiredItem.getAmount();
        for (ITransmittable absItem : list) {
            requiredAmount -= makeItem(alwaysMake, absItem);
            if(requiredAmount <= 0)break;
        }
        return requiredItem.getAmount() - requiredAmount;
    }

    public long makeItem(boolean alwaysMake, ITransmittable requiredItem){
        if(PlanPoolLock.get())return -2;
        List<DreamPlanBase> planList = searchItemPlan(requiredItem);

        if(planList.isEmpty()){
            if(!dreamPool.AbstractOverallCondition.test(requiredItem))return -1;
            return tryMakeAbstractItem(alwaysMake, requiredItem);
        }

        long requiredItemCount = requiredItem.getAmount();
        for (DreamPlanBase plan : planList) {
            long resultCount = plan.getResultNum(requiredItem);
            requiredItemCount -= makePlan(alwaysMake, plan, requiredItem, Math.floorDiv(requiredItemCount,resultCount)+1 ) * resultCount;
            if(requiredItemCount <=0)break;
        }

        if(requiredItemCount > 0 && dreamPool.AbstractOverallCondition.test(requiredItem))requiredItemCount -= tryMakeAbstractItem(alwaysMake, requiredItem.setAmount(requiredItemCount));
        return requiredItem.getAmount() - requiredItemCount;
    }

    public long makePlan(boolean alwaysMake, DreamPlanBase plan, ITransmittable requiredItem, long planCount){
        long planCountRemain = planCount;
        for (ITransmittable ing : plan.getIngredientList(requiredItem)) {
            long requiredItemCount = ing.getAmount() * planCountRemain;
            if(!alwaysMake)requiredItemCount -= dreamPool.itemAmount(ing.getSingle(), requiredItemCount);

            long makeAmount = makeItem(alwaysMake,ing.setAmount(requiredItemCount));
            if(makeAmount == -1)/*No plan found*/requiredItemCount -= dreamPool.itemAmount(ing.getSingle(), requiredItemCount);
            if(requiredItemCount > 0) planCountRemain -= Math.floorDiv(requiredItemCount, ing.getAmount()) + 1;
        }
        return planCount - planCountRemain;
    }

    public boolean removePlan(DreamPlanBase plan){
        PlanPoolLock.set(true);
        plans.remove(plan);
        plan.getResultList().forEach(transmittable -> removePlanSearchInfo(transmittable.getSingle(), plan));
        PlanPoolLock.set(false);
        return true;
    }
    protected boolean addPlanSearchInfo(ITransmittable singleTransmittable, DreamPlanBase plan){
        PlanSearchPool.putIfAbsent(singleTransmittable, new ArrayList<>());
        PlanSearchPool.get(singleTransmittable).add(plan);
        return true;
    }
    /**@return true if removed something, false otherwise**/
    protected boolean removePlanSearchInfo(ITransmittable singleTransmittable, @Nullable DreamPlanBase plan){
        List<DreamPlanBase> list = PlanSearchPool.get(singleTransmittable);
        if(list == null)return false;
        boolean result = list.remove(plan);
        if(list.isEmpty())PlanSearchPool.remove(singleTransmittable);
        return result;
    }

    public static class IngredientContainer{
        public final ITransmittable ingredient;
        public Map<Long, DreamPlanBase> planList = new HashMap<>();
        public IngredientContainer(ITransmittable ingredient){
            this.ingredient = ingredient;
        }
        public IngredientContainer(ITransmittable ingredient, Map<Long, DreamPlanBase> planList){
            this.ingredient = ingredient;
            this.planList = planList;
        }
    }
}
