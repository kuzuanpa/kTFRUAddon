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

import gregapi.data.FL;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

public abstract class FluidConsumeTaskBase implements IResearchTask{
    public @NotNull Fluid fluid;
    public long requiredCount;
    public long finishedCount;
    public FluidConsumeTaskBase(@NotNull Fluid needItem, long requiredCount){
        this.fluid = needItem;
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
        if(!(consume instanceof FluidStack && ((FluidStack) consume).getFluid().equals(fluid)))return 0;
        long consumeAmount = Math.min(((FluidStack) consume).amount, requiredCount - finishedCount);
        if(!dryRun)finishedCount += consumeAmount;
        return consumeAmount;
    }

    @Override
    public void setProgress(long progress) {
        finishedCount = progress;
    }

    @Override
    public ItemStack getIcon() {
        return FL.display(fluid);
    }

    @Override
    public String getIdentifier() {
        return FL.regName(fluid);
    }
}
