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

package cn.kuzuanpa.ktfruaddon.api.research.task.minigame;

import cn.kuzuanpa.ktfruaddon.api.research.ResearchGameTypes;
import cn.kuzuanpa.ktfruaddon.api.research.task.IResearchTask;
import net.minecraft.util.IIcon;

public class MiniGameFillTask implements IResearchTask {
    public long requiredAmount;
    public long finishedCount;
    public MiniGameFillTask(long requiredAmount) {
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
        long consumeAmount = Math.min((long)consume, requiredAmount - finishedCount);
        if(!dryRun)finishedCount += consumeAmount;
        return consumeAmount;
    }

    @Override
    public void setProgress(long progress) {
        finishedCount = progress;
    }

    @Override
    public IIcon getIcon() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return String.valueOf(ResearchGameTypes.FillPack.ordinal());
    }
}
