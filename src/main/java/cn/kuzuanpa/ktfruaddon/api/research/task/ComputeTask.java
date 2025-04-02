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
import net.minecraft.util.IIcon;

public class ComputeTask implements IResearchTask{
    public ComputePower type;
    public long requiredAmount;
    public long requiredPower;
    public long finishedCount;
    public ComputeTask(ComputePower type, long requiredAmount, long minimumPower) {
        this.type = type;
        this.requiredAmount = requiredAmount;
        this.requiredPower = minimumPower;
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
    public boolean tryPromoteProgress(Object consume) {
        boolean isEqual = consume instanceof SingleEntry && ((SingleEntry<?,?>) consume).getKey() instanceof ComputePower && ((SingleEntry<?,?>) consume).getValue() instanceof Long;
        if(isEqual)finishedCount += (Long) ((SingleEntry<?, ?>) consume).getValue();
        return isEqual;
    }

    @Override
    public void setProgress(long progress) {
        finishedCount = progress;
    }

    @Override
    public IIcon getIcon() {
        return IL.Circuit_Elite.getItem().getIconFromDamage(30304);
    }

    @Override
    public String getIdentifier() {
        return String.valueOf(type.ordinal());
    }
}
