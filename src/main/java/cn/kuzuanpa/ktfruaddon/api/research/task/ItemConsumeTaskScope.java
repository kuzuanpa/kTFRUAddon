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

import gregapi.data.LH;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemConsumeTaskScope extends ItemConsumeTaskBase{
    public ItemConsumeTaskScope(@NotNull ItemStack needItem){
        super(needItem);
    }
    public ItemConsumeTaskScope(@NotNull ItemStack needItem, long requiredCount){
        super(needItem, requiredCount);
    }
    @Override
    public String getDesc() {
        return String.format(LH.get("ktfru.research.task.item.scope"), item.getDisplayName(), requiredCount);
    }
}
