/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.machine;

import cn.kuzuanpa.ktfruaddon.api.item.IComputerItem;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ICircuitChangeableTileEntity;
import gregapi.old.Textures;
import gregapi.data.LH;
import gregapi.render.IIconContainer;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

import static gregapi.data.CS.T;

/**Swaps the compute cores of a machine item: holding a board inserts it, an empty hand pulls one back out.**/
public class CircuitChanger extends MultiTileEntityBasicMachine {
    // Tick
    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {}

    // Inventory
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[1];}
    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return new int[] {0};}
    @Override public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {return aSlot == 0 && slot(0) == null;}
    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {return aSlot == 0;}

    // Interaction
    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide()) return true;
        ItemStack machine = slot(0);
        ItemStack held = aPlayer.getCurrentEquippedItem();
        if (machine == null) {
            if (held != null && ST.move(aPlayer.inventory, this, aPlayer.inventory.currentItem, 0, 1) > 0) {
                playClick();
                updateInventory();
            }
            return true;
        }
        if (held != null && held.getItem() instanceof IComputerItem) {
            insertBoard(aPlayer, machine, held);
            return true;
        }
        if (held == null) {
            extractBoard(aPlayer, machine);
            return true;
        }
        return true;
    }

    protected void insertBoard(EntityPlayer aPlayer, ItemStack machine, ItemStack held) {
        NBTTagCompound tag = circuitTag(machine);
        List<ItemStack> circuits = ICircuitChangeableTileEntity.loadCircuitInfo(tag);
        boolean merged = false;
        for (ItemStack existing : circuits) {
            if (!ST.equal(existing, held, true)) continue;
            existing.stackSize += held.stackSize;
            merged = true;
            break;
        }
        if (!merged) {
            if (circuits.size() >= ICircuitChangeableTileEntity.MAX_CIRCUITS) return;
            circuits.add(held.copy());
        }
        ICircuitChangeableTileEntity.saveCircuitInfo(tag, circuits);
        aPlayer.inventory.setInventorySlotContents(aPlayer.inventory.currentItem, null);
        playClick();
        updateInventory();
    }

    protected void extractBoard(EntityPlayer aPlayer, ItemStack machine) {
        NBTTagCompound tag = circuitTag(machine);
        List<ItemStack> circuits = ICircuitChangeableTileEntity.loadCircuitInfo(tag);
        if (!circuits.isEmpty()) {
            ItemStack board = circuits.remove(circuits.size() - 1);
            ICircuitChangeableTileEntity.saveCircuitInfo(tag, circuits);
            UT.Inventories.addStackToPlayerInventoryOrDrop(aPlayer, board, T, worldObj, xCoord + 0.5D, yCoord + 1.2D, zCoord + 0.5D);
            playClick();
            updateInventory();
            return;
        }
        if (UT.Inventories.addStackToPlayerInventoryOrDrop(aPlayer, machine, T, worldObj, xCoord + 0.5D, yCoord + 1.2D, zCoord + 0.5D)) {
            slotKill(0);
            updateInventory();
        }
    }

    protected static NBTTagCompound circuitTag(ItemStack machine) {
        if (!machine.hasTagCompound()) machine.setTagCompound(UT.NBT.make());
        return machine.getTagCompound();
    }

    // GUI / Tooltip
    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.machine.circuitchanger.0"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.machine.circuitchanger.1"));
        super.addToolTips(aList, aStack, aF3_H);
    }

    // Icons / Meta
    @Override public String getTileEntityName() {return "ktfru.multitileentity.machine.circuitchanger";}

    public static IIconContainer
            sTextureCommon= new Textures.BlockIcons.CustomIcon("machines/cruciblemodel/common"),
            sTextureCommosn= new Textures.BlockIcons.CustomIcon("machines/cruciblemodel/commsson");
}
