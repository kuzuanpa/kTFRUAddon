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

package cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable;

import net.minecraftforge.fluids.FluidStack;

public class kFluidStack implements ITransmittable{

    long amount =0;
    FluidStack stack;
    public kFluidStack(FluidStack stack){
        this.stack = stack;
        this.amount = stack.amount;
    }
    public kFluidStack(FluidStack stack, long amount){
        this.stack = stack;
        this.amount = amount;
    }

    @Override
    public ITransmittable getSingle() {
        return new kFluidStack(stack, 1);
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public ITransmittable setAmount(long amount) {
        this.amount = amount;
        return this;
    }
}
