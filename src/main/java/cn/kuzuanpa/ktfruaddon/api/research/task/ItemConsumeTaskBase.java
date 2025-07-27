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

package cn.kuzuanpa.ktfruaddon.api.research.task;

import gregapi.util.ST;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import org.jetbrains.annotations.NotNull;

public abstract class ItemConsumeTaskBase implements IResearchTask{
    public @NotNull ItemStack item;
    public long requiredCount;
    public long finishedCount;
    public ItemConsumeTaskBase(@NotNull ItemStack needItem){
        this.item = needItem;
        requiredCount = needItem.stackSize;
    }
    public ItemConsumeTaskBase(@NotNull ItemStack needItem, long requiredCount){
        this.item = needItem;
        this.requiredCount = requiredCount;
    }
    @Override
    public long getRequiredProgress() {
        return requiredCount;
    }

    @Override
    public long getProgress() {
        return finishedCount;
    }

    @Override
    public long tryPromoteProgress(Object consume, boolean dryRun) {
        if(!(consume instanceof ItemStack && isItemStackEqual(item, ((ItemStack) consume))))return 0;
        long consumeAmount = Math.min(((ItemStack) consume).stackSize, requiredCount - finishedCount);
        if(!dryRun)finishedCount += consumeAmount;
        return consumeAmount;
    }

    @Override
    public void setProgress(long progress) {
        finishedCount = progress;
    }

    @Override
    public IIcon getIcon() {
        return item.getIconIndex();
    }

    @Override
    public String getIdentifier() {
        return ST.id(item) +"."+ ST.meta(item);
    }

    public static boolean isItemStackEqual(ItemStack need, ItemStack received) {
        return need.isItemEqual(received) && ((!need.hasTagCompound()) || need.getTagCompound().equals(received.getTagCompound()));
    }


}
