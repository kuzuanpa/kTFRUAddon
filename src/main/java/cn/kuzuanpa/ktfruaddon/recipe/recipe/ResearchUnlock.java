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

package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.api.code.SingleItemStack;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchItemManager;
import gregapi.util.ST;
import net.minecraft.item.ItemStack;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_DATA;

public class ResearchUnlock {
    public static void init() {
        ResearchItemManager.data.get((byte)0).forEach((id,unlockList)-> {
            ItemStack[] array = unlockList.stream().map(SingleItemStack::getStack).distinct().toArray(ItemStack[]::new);
            recipeMaps.ResearchUnlock.addRecipe1(false,4,4, ST.make(MOD_DATA,"ktfru.item.research.0",0,id), array);
        });
    }
}
