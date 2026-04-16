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

package cn.kuzuanpa.ktfruaddon.api.recipe;

import gregapi.fluid.FluidTankGT;
import gregapi.random.IHasWorldAndCoords;
import net.minecraft.item.ItemStack;

public interface IKortexHandler extends IHasWorldAndCoords {
    default int getMaxParallel(int kortexID, long eut, long duration){return Integer.MAX_VALUE;};

    default ItemStack getSpecialSlot(int kortexID){return null;};

    default void onRecipeStart(int kortexID, long eut, long duration, long parallel) {};

    default void onRecipeFinish(int kortexID, long eut, long duration, long parallel) {};

    /**
     * receives outputs. energy should also be received if the machine is a generator
     **/
    void receiveOutputs(FluidTankGT[] fluidTanks, ItemStack[] items);
}
