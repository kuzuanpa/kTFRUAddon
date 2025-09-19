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

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Objects;

public class kItemStack implements ITransmittable{
    long amount =0;
    ItemType stack;
    public kItemStack(ItemStack stack){
        this.stack = new ItemType(stack.getItem(), stack.getItemDamage(), stack.stackTagCompound);
        this.amount = stack.stackSize;
    }
    public kItemStack(ItemType stack, long amount) {
        this.stack = new ItemType(stack.item, stack.meta, stack.nbt);
        this.amount = amount;
    }
    public kItemStack(ItemStack stack, long amount){
        this.stack = new ItemType(stack.getItem(), stack.getItemDamage(), stack.stackTagCompound);
        this.amount = amount;
    }
    public kItemStack(Item item,long amount, int meta, NBTTagCompound nbt){
        this.stack = new ItemType(item, meta, nbt);
        this.amount = amount;
    }

    public ItemStack getStack(){
        ItemStack is = new ItemStack(stack.item, (int)amount, stack.meta);
        is.setTagCompound(stack.nbt);
        return is;
    }
    @Override
    public ITransmittable initFrom(ITransmittableType type, long amount) {
        return new kItemStack(((ItemType) type), amount);
    }

    @Override
    public ITransmittableType getType() {
        return stack;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    public static class ItemType implements ITransmittableType{
        Item item;
        int meta;
        NBTTagCompound nbt;
        public ItemType(Item item, int meta, NBTTagCompound nbt){
            this.item=item;
            this.meta=meta;
            this.nbt=nbt;
        }

        @Override
        public ITransmittable make(long amount) {
            return new kItemStack(this,amount);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ItemType itemType = (ItemType) o;
            return meta == itemType.meta && Objects.equals(item, itemType.item) && Objects.equals(nbt, itemType.nbt);
        }

        @Override
        public int hashCode() {
            return Objects.hash(item, meta, nbt);
        }
    }
}
