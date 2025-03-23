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

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemConsumeTask implements IResearchTask{
    public @NotNull Item item;
    public short meta = 0;
    public @Nullable NBTTagCompound nbt = null;
    public ItemConsumeTask(@NotNull Item needItem){
        this.item = needItem;
    }
    public ItemConsumeTask(@NotNull Item needItem, short meta){
        this.item = needItem;
        this.meta = meta;
    }
    @Override
    public long getMaxProgress() {
        return 0;
    }

    @Override
    public long getProgress() {
        return 0;
    }

    @Override
    public void setProgress(long progress) {

    }

    @Override
    public IIcon getIcon() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "";
    }
}
