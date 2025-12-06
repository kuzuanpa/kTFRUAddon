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

import cn.kuzuanpa.ktfruaddon.api.code.SingleEntry;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import gregapi.data.IL;
import gregapi.data.LH;
import net.minecraft.item.ItemStack;

public class ComputeTask implements IResearchTask{
    public ComputePower type;
    public long requiredAmount;
    public long finishedCount;
    public ComputeTask(ComputePower type, long requiredAmount) {
        this.type = type;
        this.requiredAmount = requiredAmount;
    }
    @Override
    public long getRequiredProgress() {
        return requiredAmount;
    }

    @Override
    public long getProgress() {
        return finishedCount;
    }

    @Override
    public long tryPromoteProgress(Object consume, boolean dryRun) {
        if(!(consume instanceof SingleEntry && ((SingleEntry<?,?>) consume).getKey() instanceof ComputePower && ((SingleEntry<?,?>) consume).getValue() instanceof Long))return 0;
        long avail = (Long) ((SingleEntry<?, ?>) consume).getValue();
        long consumeAmount = Math.min(avail, requiredAmount - finishedCount);
        if(!dryRun)finishedCount += consumeAmount;
        return consumeAmount;
    }

    @Override
    public void setProgress(long progress) {
        finishedCount = progress;
    }

    @Override
    public ItemStack getIcon() {
        return IL.Circuit_Elite.get(1);
    }

    @Override
    public String getIdentifier() {
        return String.valueOf(type.ordinal());
    }

    @Override
    public String getDesc() {
        return String.format(LH.get("ktfru.research.task.compute"), LH.get("ktfru.text.compute.power."+type.ordinal()), requiredAmount);
    }
}
