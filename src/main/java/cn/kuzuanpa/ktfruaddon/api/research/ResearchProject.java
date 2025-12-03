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

package cn.kuzuanpa.ktfruaddon.api.research;

import cn.kuzuanpa.ktfruaddon.api.research.task.IResearchTask;
import gregapi.util.ST;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResearchProject {
    public final String id;
    public final String desc;
    public final Item iconItem;
    public final int iconItemMeta;
    public final ResearchTree tree;
    public int posX = 0;
    public int posY = 0;
    public int layer = 0;
    public final List<ResearchProject> prerequisites = new ArrayList<>();
    public final List<ResearchProject> postResearches = new ArrayList<>();
    public final List<IResearchTask> tasks = new ArrayList<>();
    public final short printItemMeta;
    public final List<ItemStack> unlockItems = new ArrayList<>();
    public boolean isUnlocked = false;
    public boolean isCompleted = false;

    public ResearchProject(ResearchTree tree, String id, String desc, int printItemMeta) {
        this(tree, id, desc,null,0, printItemMeta);
    }
    public ResearchProject(ResearchTree tree, String id, String desc, Item icon, int iconMeta, int printItemMeta) {
        this.id = id;
        this.desc = desc;
        this.iconItem = icon;
        this.iconItemMeta = iconMeta;
        this.printItemMeta=(short)printItemMeta;
        this.tree = tree;
        if(tree != null)tree.addResearchItem(this);
    }
    public ResearchProject setPos(int x, int y){
        this.posX=x;
        this.posY=y;
        return this;
    }

    @Nullable
    public IIcon getIcon(){
        return iconItem != null ? iconItem.getIconFromDamage(iconItemMeta) : null;
    }
    public String getId() {
        return id;
    }

    public ResearchProject addPrerequisite(ResearchProject... prerequisites) {
        for (ResearchProject prerequisite : prerequisites) {
            if(prerequisite.id.equals("root"))this.layer=Math.max(1,this.layer);
            else this.layer = Math.max(this.layer, prerequisite.layer+1);
            prerequisite.postResearches.add(this);
            this.prerequisites.add(prerequisite);
        }
        return this;
    }

    public ResearchProject addTask(IResearchTask task) {
        tasks.add(task);
        return this;
    }
    public ResearchProject addTasks(IResearchTask... tasks) {
        this.tasks.addAll(Arrays.asList(tasks));
        return this;
    }
    public ResearchProject addUnlockItem(ItemStack stack){
        stack.stackSize = 1;
        unlockItems.add(stack);
        ResearchItemManager.addItemData(tree.id, printItemMeta, stack);
        return this;
    }
    public boolean removePrerequisite(ResearchProject prerequisite) {
        return prerequisites.remove(prerequisite);
    }
    /**Range: 0~100, note the progress is ceiled**/
    public byte getProgress(){
        return (byte)Math.ceil(100 * tasks.stream().mapToLong(IResearchTask::getProgress).sum()*1.0f/(tasks.stream().mapToLong(IResearchTask::getRequiredProgress).sum()));
    }
    public float getProgressF(){
        return (100 * (tasks.stream().mapToLong(IResearchTask::getProgress).sum()*1.0f/(tasks.stream().mapToLong(IResearchTask::getRequiredProgress).sum())));
    }
    public List<ResearchProject> getPrerequisites() {
        return prerequisites;
    }


    public boolean removeTask(IResearchTask condition) {
        return tasks.remove(condition);
    }

    public List<IResearchTask> getTasks() {
        return tasks;
    }
    public boolean tryUnlock(){
        if(getPrerequisites().stream().allMatch(project -> project.isCompleted)){
            isUnlocked = true;
            return true;
        }
        return false;
    }
    public long tryPromoteResearchProgress(Class<? extends IResearchTask> taskType, Object consume, boolean dryRun) {
        if(this.isCompleted)return 0;
        long consumeAmount = 0;
        for (IResearchTask task : tasks) if (taskType.isInstance(task) && !task.isCompleted()) {
            consumeAmount = task.tryPromoteProgress(consume, dryRun);
            if(consumeAmount >0)tree.onResearchProjectUpdated(this);
            break;
        }
        if(consumeAmount > 0 && tasks.stream().allMatch(IResearchTask::isCompleted))onCompleted();
        return consumeAmount;
    }
    public void onCompleted(){
        isCompleted = true;
        postResearches.forEach(ResearchProject::tryUnlock);
    }

    public static class TestTask implements IResearchTask {

        public TestTask(Item item){
            this.item=item;
        }

        Item item;

        long progress = 20;

        @Override
        public long getRequiredProgress() {
            return 120L;
        }

        @Override
        public long getProgress() {
            return progress;
        }

        @Override
        public long tryPromoteProgress(Object consumed, boolean dryRun) {
            return 1;
        }

        @Override
        public void setProgress(long progress) {

        }

        @Override
        public String getIdentifier() {
            return String.valueOf(ST.id(item));
        }

        @Override
        public String getDesc() {
            return "test task";
        }

        @Override
        public IIcon getIcon() {
            return item.getIconFromDamage(0);
        }
    }
}