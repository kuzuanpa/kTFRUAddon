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
import cn.kuzuanpa.ktfruaddon.api.tile.util.kTileNBT;
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

/**A machine that installs its compute sources as items and rents whatever its boards declare as cluster backed.**/
public interface ICircuitChangeableTileEntity extends IComputerClusterUser, IComputeItemUser {
    String NBT_CIRCUITS = "ktfru.circuits";
    int MAX_CIRCUITS = 4;

    List<ItemStack> getComputers();

    Map<ComputePower, Long> getComputePowerRequired();

    void setComputers(List<ItemStack> computers);

    ComputeUserComponent getComputeUserComponent();

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

    static NBTBase addRequiredCompute(Object... typesAndAmounts){
        Map<ComputePower, Long> required = new HashMap<>();
        for (int i = 0; i + 1 < typesAndAmounts.length; i += 2) {
            if (!(typesAndAmounts[i] instanceof ComputePower) || !(typesAndAmounts[i+1] instanceof Number)) continue;
            long amount = ((Number) typesAndAmounts[i+1]).longValue();
            if (amount > 0L) required.merge((ComputePower) typesAndAmounts[i], amount, Long::sum);
        }
        return writeRequiredCompute(required);
    }

    static NBTBase writeRequiredCompute(Map<ComputePower, Long> required){
        NBTTagList tagList = new NBTTagList();
        required.forEach((type, amount) -> {
            if (type == null || amount == null || amount <= 0L) return;
            NBTTagCompound entry = UT.NBT.make();
            entry.setInteger("type", type.ordinal());
            entry.setLong("amount", amount);
            tagList.appendTag(entry);
        });
        return tagList;
    }

    static Map<ComputePower, Long> readRequiredCompute(NBTTagCompound aNBT){
        Map<ComputePower, Long> required = new HashMap<>();
        if (aNBT == null || !aNBT.hasKey(kTileNBT.COMPUTE_POWER_REQUIRED)) return required;
        NBTTagList list = aNBT.getTagList(kTileNBT.COMPUTE_POWER_REQUIRED, 10);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound entry = list.getCompoundTagAt(i);
            long amount = entry.getLong("amount");
            if (amount <= 0L) continue;
            required.merge(ComputePower.getType(entry.getInteger("type")), amount, Long::sum);
        }
        return required;
    }

    default Map<ComputePower, Long> getComputePowerInstalled(){
        return getComputeUserComponent().getInstalled(getComputePowerRequired());
    }

    default Map<ComputePower, Long> getComputePowerNeeded(){
        return getComputeUserComponent().getNeeded(getComputePowerRequired());
    }

    default boolean tryStart(){
        ComputeUserComponent user = getComputeUserComponent();
        if (!user.verifyInstalled(getComputePowerInstalled(), getComputePowerRequired())) return false;
        if (!user.requestCompute(getComputePowerNeeded())) return false;
        if (!IComputerItem.tryStartAll(getComputers(), this, true)) {
            user.failWith(ComputeUserComponent.STATUS_INSTALLED_INSUFFICIENT);
            return false;
        }
        return true;
    }

    default void stop(){
        IComputerItem.stopAll(getComputers(), this);
        getComputeUserComponent().release();
    }

    default void addCircuitTooltip(List<String> aList, ItemStack aStack, boolean aF3_H){
        if(getComputers().isEmpty())aList.add(I18nHandler.COMPUTE_TILE_EMPTY);
        else if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            aList.add(LH.get(I18nHandler.COMPUTE_TILE_COMPUTERS));
            getComputers().forEach(computer -> aList.add(computer.getDisplayName() + " " + ComputePower.getDescOneLine(IComputerItem.getProvidedComputePower(computer, computer.stackSize, this))
                    + (IComputerItem.isClusterBackedStack(computer) ? LH.get(I18nHandler.COMPUTE_TILE_SOURCE_CLUSTER) : LH.get(I18nHandler.COMPUTE_TILE_SOURCE_LOCAL))));
        }
        else aList.add(LH.get(I18nHandler.COMPUTE_TILE_SHIFT_SHOW_COMPUTERS));
        addComputePowerStatusTooltip(aList);
    }

    default void addComputePowerStatusTooltip(List<String> aList){
        aList.add(LH.get(I18nHandler.COMPUTE_TILE_STATUS) + " " +getComputeUserComponent().getStatusText());
        Map<ComputePower, Long> required = getComputePowerRequired();
        if(!required.isEmpty()) aList.add(LH.get(I18nHandler.COMPUTE_TILE_REQUIRED) + " " +ComputePower.getDescOneLine(required));
        Map<ComputePower, Long> installed = getComputePowerInstalled();
        if(!installed.isEmpty()) aList.add(LH.get(I18nHandler.COMPUTE_TILE_INSTALLED) + " " +ComputePower.getDescOneLine(installed));
        Map<ComputePower, Long> needed = getComputePowerNeeded();
        if(!needed.isEmpty()) aList.add(LH.get(I18nHandler.COMPUTE_TILE_NEEDED) + " " +ComputePower.getDescOneLine(needed));
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
