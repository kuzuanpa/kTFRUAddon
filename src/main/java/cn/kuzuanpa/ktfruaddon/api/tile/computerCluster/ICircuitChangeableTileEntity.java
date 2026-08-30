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

package cn.kuzuanpa.ktfruaddon.api.tile.computerCluster;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.item.IComputerItem;
import gregapi.data.LH;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ICircuitChangeableTileEntity extends IComputeUser {
    String NBT_CIRCUITS = "ktfru.circuits";

    List<ItemStack> getComputers();

    Map<ComputePower, Long> getComputePowerRequired();

    void setComputers(List<ItemStack> computers);

    static void saveCircuitInfo(NBTTagCompound nbt, List<ItemStack> computers){
        NBTTagList tagList = new NBTTagList();
        for (ItemStack stack : computers) {
            NBTTagCompound data = IComputerItem.save(stack);
            if (data != null) tagList.appendTag(data);
        }
        nbt.setTag(NBT_CIRCUITS, tagList);
    }

    static List<ItemStack> loadCircuitInfo(NBTTagCompound nbt){
        NBTTagList list = nbt.getTagList(NBT_CIRCUITS, 10);
        List<ItemStack> stackList = new ArrayList<>();
        for (int i = 0; i < list.tagCount(); i++) {
            ItemStack stack = IComputerItem.load(list.getCompoundTagAt(i));
            if (stack != null) stackList.add(stack);
        }
        return stackList;
    }

    /**Sum of the Compute Power currently installed in this machine.**/
    default Map<ComputePower, Long> getComputePowerInstalled(){
        Map<ComputePower, Long> computePowers = new HashMap<>();
        getComputers().forEach(computer -> {
            Map.Entry<ComputePower, Long> e = IComputerItem.getComputePower(computer, this);
            computePowers.merge(e.getKey(), e.getValue(), Long::sum);
        });
        return computePowers;
    }

    default boolean tryStart(){
        Map<ComputePower, Long> installed = getComputePowerInstalled();
        for (Map.Entry<ComputePower, Long> required : getComputePowerRequired().entrySet()) {
            Long available = installed.get(required.getKey());
            if (available == null || available < required.getValue()) return false;
        }
        return IComputerItem.tryStartAll(getComputers(), this,true);
    }

    default void stop(boolean force){
        IComputerItem.stopAll(getComputers(), this);
    }
    default void addCircuitTooltip(List<String> aList, ItemStack aStack, boolean aF3_H){
        if(getComputers().isEmpty())aList.add(I18nHandler.COMPUTE_TILE_EMPTY);
        else if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            aList.add(LH.get(I18nHandler.COMPUTE_TILE_COMPUTERS));
            getComputers().forEach(computer -> aList.add(computer.getDisplayName() + " " + ComputePower.getDescOneLine(IComputerItem.getComputePower(computer, this))));
        }
        else aList.add(LH.get(I18nHandler.COMPUTE_TILE_SHIFT_SHOW_COMPUTERS));
    }
    /**
     * @param idsAndAmounts pairs of {@link IComputerItem#typeRegistry} id and stack size.
     * Ids are resolved to registry names when the registry is already populated, otherwise the id is
     * kept as-is and resolved on load.
     */
    static NBTBase addCircuit(int ... idsAndAmounts){
        if(idsAndAmounts.length % 2 == 1)throw new IllegalArgumentException("idsAndAmounts length must be even");
        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < idsAndAmounts.length; i+=2) {
            NBTTagCompound rNBT = UT.NBT.make();
            ItemStack template = IComputerItem.typeRegistry.get(idsAndAmounts[i]);
            if (template != null) rNBT.setString("regMeta", ST.regMeta(template));
            else UT.NBT.setNumber(rNBT, "type", idsAndAmounts[i]);
            UT.NBT.setNumber(rNBT, "Count", idsAndAmounts[i+1]);
            tagList.appendTag(rNBT);
        }
        return tagList;
    }
}
