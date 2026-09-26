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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.research;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.research.task.IResearchTask;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.gui.ContainerClientDefault;
import gregapi.gui.ContainerCommonDefault;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.multiblocks.IMultiBlockEnergy;
import gregapi.tileentity.multiblocks.IMultiBlockInventory;
import gregapi.util.UT;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.NBT_ENERGY;

public abstract class MultiResearchItemScopeBase extends MultiResearchTableBase implements ITileEntityEnergy, IMultiBlockEnergy, IMultiBlockInventory {
    protected static final TagData ENERGY_TYPE = TD.Energy.EU;
    protected long mEnergyStored = 0;
    protected long mEnergyPerItem = 4096;
    protected long mEnergyCapacity = 131072;
    protected long mInputMin = 16;
    protected long mInputRecommended = 512;
    protected long mInputMax = 4096;
    protected ChunkCoordinates lastFailedPos;

    protected abstract Class<? extends IResearchTask> getAcceptedTaskType();
    protected abstract IStringBaseStructure getStructure();

    static {
        LH.add("ktfru.tooltip.research.scope.usb", "Right-click with a monitor-bound USB to link this machine to the research monitor.");
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_ENERGY)) mEnergyStored = aNBT.getLong(NBT_ENERGY);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergyStored);
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        updateMonitor();
        if (aIsServerSide) promoteResearchFromItems();
    }

    protected void promoteResearchFromItems() {
        if (!mStructureOkay || mEnergyStored < mEnergyPerItem || !slotHas(0) || getCurrentProject() == null) return;

        ItemStack input = slot(0);
        long affordableItems = mEnergyStored / mEnergyPerItem;
        int dryRunSize = (int)Math.min(affordableItems, input.stackSize);
        if (dryRunSize <= 0) return;

        ItemStack dryRunStack = input.copy();
        dryRunStack.stackSize = dryRunSize;
        long promotable = tryPromoteCurrentProjectProgress(getAcceptedTaskType(), dryRunStack, true);
        promotable = Math.min(promotable, affordableItems);
        if (promotable <= 0) return;

        boolean leavesRemainder = promotable < input.stackSize;
        if (leavesRemainder && slotHas(1)) return;

        ItemStack consumeStack = input.copy();
        consumeStack.stackSize = (int)promotable;
        long promoted = tryPromoteCurrentProjectProgress(getAcceptedTaskType(), consumeStack, false);
        if (promoted <= 0) return;

        mEnergyStored -= promoted * mEnergyPerItem;
        int remaining = input.stackSize - (int)promoted;
        if (remaining <= 0) {
            slotKill(0);
        } else {
            ItemStack remainder = input.copy();
            remainder.stackSize = remaining;
            setInventorySlotContents(1, remainder);
            slotKill(0);
        }
    }

    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        if (worldObj == null || !worldObj.blockExists(xCoord, yCoord, zCoord)) return mStructureOkay;
        lastFailedPos = getStructure().checkStructure(new StructureContext(this, (aPlayer != null || aInventory != null) ? StructureContext.StringBaseMode.SET : StructureContext.StringBaseMode.CHECK, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        return lastFailedPos == null;
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return getStructure().isInsideStructure(this, mFacing, aX, aY, aZ);
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide()) return true;
        if (!mStructureOkay) aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED + LH.get(I18nHandler.STRUCTURE_ERR)));

        ItemStack equippedItem = aPlayer.getCurrentEquippedItem();
        if (equippedItem != null && equippedItem.getItem() instanceof ItemProjector) {
            getStructure().checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }
        if (!aPlayer.isSneaking() && bindMonitorFromUSB(aPlayer)) return true;
        openGUI(aPlayer, aSide);
        return true;
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        aList.add(LH.Chat.DGRAY + LH.get("ktfru.tooltip.research.scope.usb"));
        LH.addEnergyToolTips(this, aList, ENERGY_TYPE, null, null, null);
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerClientDefault(aPlayer.inventory, this, aGUIID);
    }

    @Override
    public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerCommonDefault(aPlayer.inventory, this, aGUIID);
    }

    @Override
    public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {
        return new ItemStack[2];
    }

    @Override
    public boolean canDrop(int aSlot) {
        return true;
    }

    private static final int[] ACCESSIBLE_SLOTS = new int[] {0, 1};

    @Override
    public int[] getAccessibleSlotsFromSide2(byte aSide) {
        return ACCESSIBLE_SLOTS;
    }

    @Override
    public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {
        return aSlot == 0;
    }

    @Override
    public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {
        return aSlot == 1;
    }

    @Override
    public boolean isItemValidForSlot(int aSlot, ItemStack aStack) {
        return aSlot == 0;
    }

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        aSize = Math.abs(aSize);
        if (!ENERGY_TYPE.equals(aEnergyType) || aSize <= 0 || aAmount <= 0) return 0;
        if (aSize > mInputMax) {
            if (aDoInject) overcharge(aSide, aEnergyType);
            return 0;
        }
        long amountUntilFull = (mEnergyCapacity - mEnergyStored) / aSize;
        long amountAccepted = Math.min(aAmount, amountUntilFull);
        if (aDoInject && amountAccepted > 0) mEnergyStored += amountAccepted * aSize;
        return amountAccepted;
    }

    @Override
    public long getEnergyDemanded(TagData aEnergyType, byte aSide, long aSize) {
        if (!ENERGY_TYPE.equals(aEnergyType) || aSize <= 0) return 0;
        return Math.max(0, mEnergyCapacity - mEnergyStored) / aSize;
    }
    @Override
    public long getEnergyOffered(TagData aEnergyType, byte aSide, long aSize) {
        return 0;
    }

    @Override
    public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {
        return !aEmitting && ENERGY_TYPE.equals(aEnergyType);
    }

    @Override
    public boolean isEnergyAcceptingFrom(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return ENERGY_TYPE.equals(aEnergyType);
    }

    @Override
    public boolean isEnergyEmittingTo(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return false;
    }

    @Override
    public long getEnergySizeInputMin(TagData aEnergyType, byte aSide) {
        return mInputMin;
    }

    @Override
    public long getEnergySizeInputRecommended(TagData aEnergyType, byte aSide) {
        return mInputRecommended;
    }

    @Override
    public long getEnergySizeInputMax(TagData aEnergyType, byte aSide) {
        return mInputMax;
    }

    @Override
    public long getEnergySizeOutputMin(TagData aEnergyType, byte aSide) {
        return 0;
    }

    @Override
    public long getEnergySizeOutputRecommended(TagData aEnergyType, byte aSide) {
        return 0;
    }

    @Override
    public long getEnergySizeOutputMax(TagData aEnergyType, byte aSide) {
        return 0;
    }

    @Override
    public Collection<TagData> getEnergyTypes(byte aSide) {
        return ENERGY_TYPE.AS_LIST;
    }
}
