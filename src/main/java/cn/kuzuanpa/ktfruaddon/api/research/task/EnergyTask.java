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

import gregapi.code.TagData;
import gregapi.data.IL;
import gregapi.data.LH;
import net.minecraft.item.ItemStack;

public class EnergyTask implements IResearchTask{
    public TagData type;
    public long requiredAmount;
    public long minVoltage;
    public long finishedAmount;
    public EnergyTask(TagData type, long requiredAmount, long minVoltage) {
        this.type = type;
        this.requiredAmount = requiredAmount;
        this.minVoltage = minVoltage;
    }
    @Override
    public long getRequiredProgress() {
        return requiredAmount;
    }

    @Override
    public long getProgress() {
        return finishedAmount;
    }

    @Override
    public long tryPromoteProgress(Object consume, boolean dryRun) {
        if(!(consume instanceof EnergyCompound && ((EnergyCompound) consume).type .equals(type)))return 0;
        EnergyCompound energy = (EnergyCompound) consume;
        if(energy.voltage < minVoltage)return 0;
        long consumeAmount = Math.min(energy.voltage * energy.ampere, requiredAmount - finishedAmount);
        if(!dryRun) finishedAmount += consumeAmount;
        return consumeAmount;
    }

    @Override
    public void setProgress(long progress) {
        finishedAmount = progress;
    }

    @Override
    public ItemStack getIcon() {
        return IL.Circuit_Elite.get(1);
    }

    @Override
    public String getIdentifier() {
        return String.valueOf(type.mTagID);
    }

    @Override
    public String getDesc() {
        return String.format(LH.get("ktfru.research.task.energy"), type.getLocalisedChatNameShort(), requiredAmount, minVoltage);
    }

    public static class EnergyCompound {
        public TagData type;
        public long voltage;
        public long ampere;
    }
}
