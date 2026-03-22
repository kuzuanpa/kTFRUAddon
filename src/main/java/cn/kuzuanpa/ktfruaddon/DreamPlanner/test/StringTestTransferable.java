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

import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransferable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.TransferableRenderDescriber;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.TransferableStack;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Objects;

public class StringTestTransferable implements ITransferable {
    String content = "";
    public StringTestTransferable(String content){
        this.content = content;
    }
    @Override
    public TransferableStack make(long amount) {
        return new TransferableStack(this,amount);
    }

    @Override
    public ITransferable saveTo(NBTTagCompound nbt) {
        nbt.setString("content", content);
        return this;
    }

    @Override
    public ITransferable loadFrom(NBTTagCompound nbt) {
        content = nbt.getString("content");
        return this;
    }

    @Override
    public int typeID() {
        return -1;
    }

    @Override
    public TransferableRenderDescriber getRenderDescriber() {
        return new TransferableRenderDescriber(new ItemStack(Items.apple)).setName("String Test").setDesc(content);
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringTestTransferable)) return false;

        StringTestTransferable that = (StringTestTransferable) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(content);
    }
}
