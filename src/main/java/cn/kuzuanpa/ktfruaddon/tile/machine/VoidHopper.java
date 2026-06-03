
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

package cn.kuzuanpa.ktfruaddon.tile.machine;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.util.kTileNBT;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.code.TagData;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;

import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.*;

public class VoidHopper extends TileEntityBase09FacingSingle implements ITileEntityEnergy {
    public TagData mEnergyTypeAccepted = TD.Energy.RF;
    public long mEnergyStored = 0, mInput = 0, mInputMin = 0, mInputMax = 0;
    public int mRange = 4;
    public boolean mFilterInverted = false;
    public byte mState = 0, mStateOld = 0;

    public static IIconContainer mTextureMaterial, mTextureFront, mTextureFrontActive;

    static {
        mTextureMaterial   = new Textures.BlockIcons.CustomIcon("machines/voidhopper/colored");
        mTextureFront      = new Textures.BlockIcons.CustomIcon("machines/voidhopper/front");
        mTextureFrontActive = new Textures.BlockIcons.CustomIcon("machines/voidhopper/front_active");
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_INPUT)) { mInput = aNBT.getLong(NBT_INPUT); mInputMin = mInput / 2; mInputMax = mInput * 2; }
        if (aNBT.hasKey(NBT_INPUT_MIN)) mInputMin = aNBT.getLong(NBT_INPUT_MIN);
        if (aNBT.hasKey(NBT_INPUT_MAX)) mInputMax = aNBT.getLong(NBT_INPUT_MAX);
        if (aNBT.hasKey(NBT_ENERGY_ACCEPTED)) mEnergyTypeAccepted = TagData.createTagData(aNBT.getString(NBT_ENERGY_ACCEPTED));
        if (aNBT.hasKey(kTileNBT.MAX_RANGE)) mRange = aNBT.getInteger(kTileNBT.MAX_RANGE);
        if (aNBT.hasKey(NBT_ENERGY)) mEnergyStored = aNBT.getLong(NBT_ENERGY);
        if (aNBT.hasKey("ktfru.nbt.voidhopper.filter_inverted")) mFilterInverted = aNBT.getBoolean("ktfru.nbt.voidhopper.filter_inverted");
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergyStored);
        aNBT.setBoolean("ktfru.nbt.voidhopper.filter_inverted", mFilterInverted);
    }

    /** Check if the given ItemStack passes the filter */
    public boolean passesFilter(ItemStack aStack) {
        if (aStack == null) return false;
        // Slot 0 is the filter slot
        if (!slotHas(0)) return !mFilterInverted;
        boolean matches = slot(0).getItem() == aStack.getItem() && slot(0).getItemDamage() == aStack.getItemDamage();
        return mFilterInverted ? !matches : matches;
    }

    /** Try to insert an ItemStack into the output inventory (slots 1..n) */
    public boolean tryInsertIntoNextFreeSlot(ItemStack stack) {
        for (int i = 1; i < invsize(); i++) {
            if (addStackToSlot(i, stack)) return true;
        }
        return false;
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if (!aIsServerSide) return;

        boolean hasPower = mEnergyStored >= mInputMin && (mCovers == null || !mCovers.mStopped);
        if (!hasPower) {
            if (mState != 0) { mState = 0; updateClientData(); }
            if (mEnergyStored < 0) mEnergyStored = 0;
            return;
        }

        // Consume energy
        mEnergyStored -= Math.min(mEnergyStored, mInputMax);
        if (mEnergyStored < 0) mEnergyStored = 0;

        // Absorb items in range
        AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
            xCoord - mRange, yCoord - mRange, zCoord - mRange,
            xCoord + mRange + 1, yCoord + mRange + 1, zCoord + mRange + 1
        );

        List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, aabb);
        boolean absorbed = false;
        for (EntityItem entityItem : items) {
            if (entityItem.isDead) continue;
            ItemStack entityStack = entityItem.getEntityItem();
            if (entityStack == null) continue;

            if (passesFilter(entityStack)) {
                ItemStack toInsert = entityStack.copy();
                if (tryInsertIntoNextFreeSlot(toInsert)) {
                    entityItem.setDead();
                    absorbed = true;
                }
            }
        }

        byte newState = absorbed ? (byte) 1 : (byte) 0;
        if (newState != mState) {
            mState = newState;
            updateClientData();
        }
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        // Right-click with an item to set the filter (slot 0)
        if (isServerSide() && aPlayer != null && aPlayer.getCurrentEquippedItem() != null) {
            ItemStack held = aPlayer.getCurrentEquippedItem();
            // Return old filter to player if exists
            if (slotHas(0)) {
                UT.Inventories.addStackToPlayerInventoryOrDrop(aPlayer, slot(0), true);
            }
            setInventorySlotContents(0, ST.amount(1, held));
            if (!aPlayer.capabilities.isCreativeMode) held.stackSize--;
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN + "Filter set to: " + slot(0).getDisplayName()));
            return true;
        }
        return false;
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        // Screwdriver: invert filter
        if (aTool.equals(TOOL_screwdriver)) {
            if (isServerSide()) {
                mFilterInverted = !mFilterInverted;
                if (aChatReturn != null) {
                    aChatReturn.add(LH.Chat.CYAN + "Filter mode: " + (mFilterInverted ? "Blacklist (Inverted)" : "Whitelist (Normal)"));
                }
            }
            return 1;
        }
        // Soft hammer: clear filter and reset
        if (aTool.equals(TOOL_softhammer)) {
            if (isServerSide()) {
                if (slotHas(0) && aPlayer instanceof EntityPlayer) {
                    UT.Inventories.addStackToPlayerInventoryOrDrop((EntityPlayer) aPlayer, slot(0), true);
                    slotKill(0);
                } else if (slotHas(0)) {
                    slotKill(0);
                }
                mFilterInverted = false;
                if (aChatReturn != null) {
                    aChatReturn.add(LH.Chat.CYAN + "Filter cleared and reset to Whitelist.");
                }
            }
            return 1;
        }
        // Magnifying glass: show filter info
        if (aTool.equals(TOOL_magnifyingglass)) {
            if (aChatReturn != null) {
                aChatReturn.add(LH.Chat.CYAN + "=== Void Hopper Info ===");
                aChatReturn.add(LH.Chat.YELLOW + "Range: " + mRange);
                aChatReturn.add(LH.Chat.YELLOW + "Filter Mode: " + (mFilterInverted ? "Blacklist (Inverted)" : "Whitelist (Normal)"));
                if (slotHas(0)) {
                    aChatReturn.add(LH.Chat.GREEN + "Filter Item: " + slot(0).getDisplayName());
                } else {
                    aChatReturn.add(LH.Chat.GRAY + "Filter Item: None (allows all items)");
                }
                aChatReturn.add(LH.Chat.DGRAY + "Right-click with item to set filter");
                aChatReturn.add(LH.Chat.DGRAY + "Screwdriver to invert filter");
                aChatReturn.add(LH.Chat.DGRAY + "Soft hammer to clear filter");
            }
            return 1;
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        LH.addEnergyToolTips(this, aList, mEnergyTypeAccepted, null, null, null);
        aList.add(LH.Chat.CYAN + String.format(LH.get(I18nHandler.VOID_HOPPER_0), mRange));
        aList.add(LH.Chat.DGRAY + "Right-click with item to set filter");
        aList.add(LH.Chat.DGRAY + LH.get(LH.TOOL_TO_TOGGLE_SCREWDRIVER) + " (Invert Filter)");
        aList.add(LH.Chat.DGRAY + LH.get(LH.TOOL_TO_RESET_SOFT_HAMMER) + " (Clear Filter)");
        aList.add(LH.Chat.DGRAY + LH.get(LH.TOOL_TO_DETAIL_MAGNIFYINGGLASS));
    }

    static {
        LH.add(I18nHandler.VOID_HOPPER_0, "Absorbs items within range: %s");
    }

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return aShouldSideBeRendered[aSide] ? BlockTextureMulti.get(
            BlockTextureDefault.get(mTextureMaterial, mRGBa),
            aSide == mFacing ? BlockTextureDefault.get(mState > 0 ? mTextureFrontActive : mTextureFront) : null
        ) : null;
    }

    @Override public byte getDefaultSide() { return SIDE_SOUTH; }
    @Override public boolean[] getValidSides() { return SIDES_HORIZONTAL; }

    // Energy
    @Override public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) { return aEnergyType == mEnergyTypeAccepted; }
    @Override public long getEnergySizeInputMin(TagData aEnergyType, byte aSide) { return mInputMin; }
    @Override public long getEnergySizeInputRecommended(TagData aEnergyType, byte aSide) { return mInput; }
    @Override public long getEnergySizeInputMax(TagData aEnergyType, byte aSide) { return mInputMax; }
    @Override public Collection<TagData> getEnergyTypes(byte aSide) { return mEnergyTypeAccepted.AS_LIST; }

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        aSize = Math.abs(aSize);
        if (aSize > getEnergySizeInputMax(aEnergyType, aSide) && !TD.Energy.ALL_SIZE_IRRELEVANT.contains(mEnergyTypeAccepted)) {
            if (aDoInject) overcharge(aSize, aEnergyType);
            return aAmount;
        }
        if (aEnergyType == mEnergyTypeAccepted) {
            long tInput = Math.min(mInputMax - mEnergyStored, aSize * aAmount);
            long tConsumed = Math.min(aAmount, (tInput / aSize) + (tInput % aSize != 0 ? 1 : 0));
            if (aDoInject) mEnergyStored += tConsumed * aSize;
            return tConsumed;
        }
        return 0;
    }

    @Override public String getTileEntityName() { return "ktfru.multitileentity.machine.voidhopper"; }
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) { return new ItemStack[10]; }
    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) { return aSide == SIDE_TOP ? new int[] {0} : new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9}; }

    @Override public boolean canDrop(int aSlot) { return true; }

    @Override
    public boolean onTickCheck(long aTimer) {
        if (mStateOld != mState) {
            mStateOld = mState;
            return true;
        }
        return false;
    }

    @Override
    public byte getVisualData() { return mState; }

    @Override
    public void setVisualData(byte aData) { mState = aData; }
}
