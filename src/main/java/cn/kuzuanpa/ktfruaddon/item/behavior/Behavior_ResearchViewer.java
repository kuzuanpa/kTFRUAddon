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

import cn.kuzuanpa.ktfruaddon.tile.research.ResearchTreeMonitor;
import gregapi.item.multiitem.MultiItem;
import gregapi.item.multiitem.behaviors.IBehavior;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import static gregapi.data.CS.T;

public class Behavior_ResearchViewer extends IBehavior.AbstractBehaviorDefault {
    public static Behavior_ResearchViewer INSTANCE = new Behavior_ResearchViewer();
    public boolean canUse = true;
    @Override
    public boolean onItemUse(MultiItem aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, byte aSide, float hitX, float hitY, float hitZ) {
        if (aPlayer instanceof EntityPlayerMP) {
            if(aPlayer.isSneaking()) {
                if (aWorld.getTileEntity(aX, aY, aZ) instanceof ResearchTreeMonitor) bindStackToPos(aStack, aWorld, aX, aY, aZ);
                else if(aStack.hasTagCompound()) aStack.getTagCompound().removeTag("x");
            }
            return T;
        }
        return false;
    }

    @Override
    public ItemStack onItemRightClick(MultiItem aItem, ItemStack aStack, World aWorld, EntityPlayer aPlayer) {
        if (aPlayer instanceof EntityPlayerMP && !aPlayer.isSneaking()) tryOpenViewer(aPlayer, aStack);
        return aStack;
    }

    public void bindStackToPos(ItemStack aStack, World aWorld, int aX, int aY, int aZ){
        NBTTagCompound tag = aStack.getTagCompound();
        if(tag == null) tag = new NBTTagCompound();
        tag.setInteger("x", aX);
        tag.setInteger("y", aY);
        tag.setInteger("z", aZ);
        tag.setInteger("w", aWorld.provider.dimensionId);
        aStack.setTagCompound(tag);
    }

    public void tryOpenViewer(EntityPlayer aPlayer, ItemStack aStack){
        NBTTagCompound tag = aStack.getTagCompound();
        if(tag == null || !tag.hasKey("x")) return;

        int x = tag.getInteger("x");
        int y = tag.getInteger("y");
        int z = tag.getInteger("z");
        World w = DimensionManager.getWorld(tag.getInteger("w"));
        if (w == null)return;
        TileEntity tile = w.getTileEntity(x, y, z);
        if(!(tile instanceof ResearchTreeMonitor) || !((ResearchTreeMonitor) tile).allowInteraction(aPlayer)) return;

        ((ResearchTreeMonitor) tile).openGUI(aPlayer);
    }

}
