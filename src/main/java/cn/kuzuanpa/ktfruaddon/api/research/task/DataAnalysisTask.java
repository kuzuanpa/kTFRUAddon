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

import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import gregapi.data.LH;
import net.minecraft.item.ItemStack;

/**Counts raw observation samples turned into analysable data by a quantum state analysis station.**/
public class DataAnalysisTask implements IResearchTask {
    public static final String IDENTIFIER = "QuantumStateAnalysis";

    static {
        LH.add("ktfru.research.task.analysis", "Analyse %d quantum observation sample(s)");
    }

    public long requiredAmount;
    public long finishedCount;

    public DataAnalysisTask(long requiredAmount) {
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
        if (!(consume instanceof Number)) return 0;
        long consumeAmount = Math.min(Math.max(0L, ((Number) consume).longValue()), requiredAmount - finishedCount);
        if (!dryRun) finishedCount += consumeAmount;
        return consumeAmount;
    }

    @Override
    public void setProgress(long progress) {
        finishedCount = progress;
    }

    @Override
    public ItemStack getIcon() {
        return ItemList.QuantumObservationData.get(1);
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public String getDesc() {
        return String.format(LH.get("ktfru.research.task.analysis"), requiredAmount);
    }
}
