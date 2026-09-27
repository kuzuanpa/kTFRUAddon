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

package cn.kuzuanpa.ktfruaddon.item.behavior;

import cn.kuzuanpa.ktfruaddon.command.CommandKTFRUWireless;
import gregapi.data.LH;
import gregapi.item.multiitem.MultiItem;
import gregapi.item.multiitem.behaviors.IBehavior;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class Behavior_Wristband extends IBehavior.AbstractBehaviorDefault {
    public static final Behavior_Wristband INSTANCE = new Behavior_Wristband();

    static {
        LH.add("ktfru.wristband.activated", "无线设备通用命令行接口已激活, 使用 /kTFRUWireless 访问.");
        LH.add("ktfru.wristband.already", "你已经激活过命令行接口了.");
        LH.add("ktfru.wristband.tooltip", "右键佩戴后即可使用 /kTFRUWireless 命令行接口.");
    }

    @Override
    public ItemStack onItemRightClick(MultiItem aItem, ItemStack aStack, World aWorld, EntityPlayer aPlayer) {
        if (aWorld.isRemote || !(aPlayer instanceof EntityPlayerMP)) return aStack;

        NBTTagCompound wristband = CommandKTFRUWireless.getWristbandData(aPlayer);
        if (wristband.getBoolean(CommandKTFRUWireless.NBT_ACTIVE)) {
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.YELLOW + LH.get("ktfru.wristband.already")));
            return aStack;
        }

        wristband.setBoolean(CommandKTFRUWireless.NBT_ACTIVE, true);
        CommandKTFRUWireless.setWristbandData(aPlayer, wristband);
        aStack.stackSize--;
        aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN + LH.get("ktfru.wristband.activated")));
        return aStack;
    }
}
