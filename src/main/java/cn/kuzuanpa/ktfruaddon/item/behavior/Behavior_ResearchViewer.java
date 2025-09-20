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

package cn.kuzuanpa.ktfruaddon.item.behavior;

import cn.kuzuanpa.ktfruaddon.api.research.ResearchTree;
import cn.kuzuanpa.ktfruaddon.client.gui.research.ContainerClientResearchTreeMonitor;
import cn.kuzuanpa.ktfruaddon.tile.research.ResearchTreeMonitor;
import cpw.mods.fml.common.FMLCommonHandler;
import gregapi.item.multiitem.MultiItem;
import gregapi.item.multiitem.behaviors.IBehavior;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.UUID;

import static gregapi.data.CS.T;

public class Behavior_ResearchViewer extends IBehavior.AbstractBehaviorDefault {
    public static Behavior_ResearchViewer INSTANCE = new Behavior_ResearchViewer();
    public boolean canUse = true;
    @Override
    public boolean onItemUse(MultiItem aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, byte aSide, float hitX, float hitY, float hitZ) {
        if (aPlayer instanceof EntityPlayerMP) {
            if(aPlayer.isSneaking()) {
                if (aWorld.getTileEntity(aX, aY, aZ) instanceof ResearchTreeMonitor) bindStackTo(aStack, (ResearchTreeMonitor) aWorld.getTileEntity(aX, aY, aZ));
                else if(aStack.hasTagCompound()) aStack.getTagCompound().removeTag("UUIDup");
            }
            return T;
        }
        return false;
    }

    @Override
    public ItemStack onItemRightClick(MultiItem aItem, ItemStack aStack, World aWorld, EntityPlayer aPlayer) {
        if (aWorld.isRemote && !aPlayer.isSneaking()) tryOpenViewer(aPlayer, aStack);
        return aStack;
    }

    public void bindStackTo(ItemStack aStack, ResearchTreeMonitor treeMonitor){
        NBTTagCompound tag = aStack.getTagCompound();
        if(tag == null) tag = new NBTTagCompound();
        if(treeMonitor.theTree == null)return;
        tag.setLong("UUIDup", treeMonitor.theTree.uuid.getMostSignificantBits());
        tag.setLong("UUIDdown", treeMonitor.theTree.uuid.getLeastSignificantBits());
        aStack.setTagCompound(tag);
    }

    public void tryOpenViewer(EntityPlayer aPlayer, ItemStack aStack){
        NBTTagCompound tag = aStack.getTagCompound();
        if(tag == null || !tag.hasKey("UUIDup")) return;

        long UUIDup = tag.getLong("UUIDup");
        long UUIDdown = tag.getLong("UUIDdown");
        UUID uuid = new UUID(UUIDup, UUIDdown);

        ResearchTree tree = ResearchTree.allTreeUUIDsClient.get(uuid);
        if(tree == null){
            ResearchTree.sendGetTreeDataPacket(aPlayer.getCommandSenderName(), uuid, (byte) 2);
            aPlayer.addChatComponentMessage(new ChatComponentText("Syncing Research Tree Data"));
            return;
        }
        ResearchTree.sendGetTreeDataPacket(aPlayer.getCommandSenderName(), uuid, (byte) 3);
        FMLCommonHandler.instance().showGuiScreen(new ContainerClientResearchTreeMonitor(tree));
    }

}
