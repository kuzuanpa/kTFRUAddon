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


import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.Objects;

public class kFluidStack implements ITransmittable{
    long amount =0;
    FluidType stack;
    public kFluidStack(FluidStack stack){
        this.stack = new FluidType(stack.getFluid(), stack.tag);
        this.amount = stack.amount;
    }
    public kFluidStack(FluidType stack, long amount) {
        this.stack = new FluidType(stack.Fluid, stack.nbt);
        this.amount = amount;
    }
    public kFluidStack(FluidStack stack, long amount){
        this.stack = new FluidType(stack.getFluid(), stack.tag);
        this.amount = amount;
    }
    public kFluidStack(Fluid Fluid, long amount, NBTTagCompound nbt){
        this.stack = new FluidType(Fluid, nbt);
        this.amount = amount;
    }

    @Override
    public ITransmittable initFrom(ITransmittableType type, long amount) {
        return new kFluidStack(((FluidType) type), amount);
    }

    @Override
    public ITransmittableType getType() {
        return stack;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    public static class FluidType implements ITransmittableType{
        Fluid Fluid;
        NBTTagCompound nbt;
        public FluidType(Fluid Fluid, NBTTagCompound nbt){
            this.Fluid=Fluid;
            this.nbt=nbt;
        }

        @Override
        public ITransmittable make(long amount) {
            return new kFluidStack(this,amount);
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FluidType fluidType = (FluidType) o;
            return Objects.equals(Fluid, fluidType.Fluid) && Objects.equals(nbt, fluidType.nbt);
        }

        @Override
        public int hashCode() {
            return Objects.hash(Fluid, nbt);
        }
    }
}
