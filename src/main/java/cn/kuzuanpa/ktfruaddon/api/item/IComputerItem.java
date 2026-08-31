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

package cn.kuzuanpa.ktfruaddon.api.item;

import cn.kuzuanpa.ktfruaddon.api.code.SingleEntry;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputeItemUser;
import cpw.mods.fml.common.FMLLog;
import gregapi.data.IL;
import gregapi.data.MD;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IComputerItem {
    @NotNull Map<String, Map.Entry<ComputePower, Long>> plainItemRegistry = new HashMap<>();
    @NotNull Map<Integer, ItemStack> typeRegistry = new HashMap<>();
    @NotNull Map.Entry<ComputePower, Long> getComputePower(int amount, int meta);

    boolean onStart(ItemStack stack, IComputeItemUser host);
    void onStop(ItemStack stack, @Nullable IComputeItemUser host);

    static boolean tryStartAll(List<ItemStack> stacks, @Nullable IComputeItemUser host, boolean autoStopOnFail) {
        if (stacks.stream().allMatch(s -> tryStart(s, host, false))) return true;

        if(autoStopOnFail)stacks.forEach(s->stop(s,host));
        return false;
    }

    static boolean tryStart(ItemStack stack, @Nullable IComputeItemUser host, boolean autoStopOnFail) {
        if (!ST.valid(stack)) return true;

        Item i = stack.getItem();
        if(!(i instanceof IComputerItem))return true;

        IComputerItem item = ((IComputerItem) i);
        if(!item.onStart(stack, host)){
            if(autoStopOnFail)item.onStop(stack,host);
            return false;
        }
        return true;
    }

    static void stopAll(List<ItemStack> stacks, @Nullable IComputeItemUser host){
        stacks.forEach(s->stop(s,host));
    }
    static void stop(ItemStack stack, @Nullable IComputeItemUser host) {
        if (!ST.valid(stack)) return;
        if (stack.getItem() instanceof IComputerItem) ((IComputerItem) stack.getItem()).onStop(stack, host);
    }

    static @NotNull Map.Entry<ComputePower, Long> getComputePower(ItemStack stack, @Nullable IComputeItemUser host) {
        if (!ST.valid(stack)) return new SingleEntry<>(ComputePower.Normal,0L);
        if(stack.getItem() instanceof IComputerItem) return ((IComputerItem) stack.getItem()).getComputePower(stack.stackSize, stack.getItemDamage());
        else return getComputePowerForPlainItem(stack);
    }
    //plain item usually is simple circuit and so on. starting them should have no cost and always success
    static @NotNull Map.Entry<ComputePower, Long> getComputePowerForPlainItem(ItemStack stack){
        Map.Entry<ComputePower, Long> map = plainItemRegistry.get(ST.regMeta(stack));
        return map == null? new SingleEntry<>(ComputePower.Normal,0L) : map;
    }
    static void setComputePowerForPlainItem(ItemStack stack, Map.Entry<ComputePower, Long> computePower){
        plainItemRegistry.put(ST.regMeta(stack), computePower);
    }
    static void putDefaultPlainItemValues(){
        setComputePowerForPlainItem(ST.make(MD.GC_ADV_ROCKETRY,"circuitIC",1, 2), ComputePower.Normal.asEntry(2));
        setComputePowerForPlainItem(IL.Circuit_Basic.get(1)                                       , ComputePower.Normal.asEntry(8));
        setComputePowerForPlainItem(IL.Circuit_Good.get(1)                                        , ComputePower.Normal.asEntry(150));
        setComputePowerForPlainItem(IL.Circuit_Advanced.get(1)                                    , ComputePower.Normal.asEntry(620));
        setComputePowerForPlainItem(IL.Circuit_Elite.get(1)                                       , ComputePower.Normal.asEntry(4600));
        setComputePowerForPlainItem(IL.Circuit_Master.get(1)                                      , ComputePower.Normal.asEntry(15674));
        setComputePowerForPlainItem(IL.Circuit_Ultimate.get(1)                                    , ComputePower.Normal.asEntry(45261));
    }

    static void putDefaultTypes(){
        typeRegistry.put(  0, ST.make(MD.GC_ADV_ROCKETRY,"circuitIC",1, 2));
        typeRegistry.put(  1, IL.Circuit_Basic.get(1));
        typeRegistry.put(  2, IL.Circuit_Good.get(1));
        typeRegistry.put(  3, IL.Circuit_Advanced.get(1));
        typeRegistry.put(  4, IL.Circuit_Elite.get(1));
        typeRegistry.put(  5, IL.Circuit_Master.get(1));
        typeRegistry.put(  6, IL.Circuit_Ultimate.get(1));
    }
    static NBTTagCompound save(ItemStack aStack) {
        if (!ST.valid(aStack)) return null;
        String regMeta = ST.regMeta(aStack);
        if (regMeta.isEmpty()) {
            FMLLog.log(Level.ERROR, "kTFRUAddon: cannot persist computer item without registry name: %s", aStack);
            return null;
        }
        NBTTagCompound rNBT = UT.NBT.make();
        rNBT.setString("regMeta", regMeta);
        UT.NBT.setNumber(rNBT, "Count", aStack.stackSize);
        if (aStack.hasTagCompound()) rNBT.setTag("tag", aStack.getTagCompound());
        return rNBT;
    }

    /**@return the computer stack stored in this NBT, or null if it cannot be resolved anymore.**/
    static @Nullable ItemStack load(NBTTagCompound aNBT) {
        if (aNBT == null) return null;
        ItemStack rStack = null;
        if (aNBT.hasKey("regMeta")) rStack = fromRegMeta(aNBT.getString("regMeta"));
        //legacy format: index into typeRegistry
        else if (aNBT.hasKey("type")) {
            ItemStack template = typeRegistry.get(aNBT.getInteger("type"));
            if (template != null) rStack = template.copy();
        }
        if (rStack == null) return null;
        rStack.stackSize = Math.max(1, aNBT.getInteger("Count"));
        if (aNBT.hasKey("tag")) rStack.setTagCompound(aNBT.getCompoundTag("tag"));
        return rStack;
    }

    /**@param regMeta as produced by {@link ST#regMeta(ItemStack)}, that is {@code modid:name:meta}**/
    static @Nullable ItemStack fromRegMeta(String regMeta) {
        if (regMeta == null) return null;
        int split = regMeta.lastIndexOf(':');
        if (split <= 0) return null;
        Item item = (Item) Item.itemRegistry.getObject(regMeta.substring(0, split));
        if (item == null) return null;
        int meta;
        try {
            meta = Integer.parseInt(regMeta.substring(split + 1));
        } catch (NumberFormatException e) {
            return null;
        }
        return new ItemStack(item, 1, meta);
    }
}
