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

package cn.kuzuanpa.ktfruaddon.api.code;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ItemType {
    public @NotNull final Item item;
    public final short meta;
    public @Nullable final NBTTagCompound nbt;
    public ItemType(@NotNull Item item, short meta, @Nullable NBTTagCompound nbt){
        this.item = item;
        this.meta = meta;
        this.nbt = nbt;
    }

    public ItemType(@NotNull Item item, short meta){
        this.item = item;
        this.meta = meta;
        this.nbt = null;
    }

    public ItemType(@NotNull Item item){
        this.item = item;
        this.meta = 0;
        this.nbt = null;
    }

    public ItemType(@NotNull ItemStack stack){
        this.item = stack.getItem();
        this.meta = (short) stack.getItemDamage();
        this.nbt = stack.getTagCompound();
    }
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemType)) return false;

        ItemType that = (ItemType) o;
        return meta == that.meta && item.equals(that.item) && Objects.equals(nbt, that.nbt);
    }

    public ItemStack getStack(){
        ItemStack stack = new ItemStack(item, 1, meta);
        stack.setTagCompound(nbt);
        return stack;
    }
    @Override
    public int hashCode() {
        int result = item.hashCode();
        result = 31 * result + meta;
        result = 31 * result + Objects.hashCode(nbt);
        return result;
    }
}
