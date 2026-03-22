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

package cn.kuzuanpa.ktfruaddon.DreamPlanner.test;

import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.IAbstractTransferable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransferable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.TransferableRenderDescriber;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.TransferableStack;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Objects;

public class AbstractStringTestTransferable implements IAbstractTransferable {
    String prefix = "";
    public AbstractStringTestTransferable(String prefix){
        this.prefix=prefix;
    }

    @Override
    public TransferableStack make(long amount) {
        return new TransferableStack(this, amount);
    }

    @Override
    public ITransferable saveTo(NBTTagCompound nbt) {
        nbt.setString("prefix",prefix);
        return this;
    }

    @Override
    public ITransferable loadFrom(NBTTagCompound nbt) {
        prefix= nbt.getString("prefix");
        return this;
    }

    @Override
    public int typeID() {
        return -2;
    }

    @Override
    public boolean isFit(ITransferable t) {
        return t instanceof StringTestTransferable && ((StringTestTransferable) t).content.startsWith(prefix);
    }

    @Override
    public TransferableRenderDescriber getRenderDescriber() {
        return new TransferableRenderDescriber(new ItemStack(Items.apple)).setName("Abstract String").setDesc(prefix);
    }
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractStringTestTransferable)) return false;

        AbstractStringTestTransferable that = (AbstractStringTestTransferable) o;
        return Objects.equals(prefix, that.prefix);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(prefix);
    }

    @Override
    public String toString() {
        return prefix;
    }
}
