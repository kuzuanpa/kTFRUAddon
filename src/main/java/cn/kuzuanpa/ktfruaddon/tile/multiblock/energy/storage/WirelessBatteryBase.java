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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.storage;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.ControllerPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.WirelessEnergyReceiver;
import gregapi.block.multitileentity.IWailaTile;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.OM;
import gregapi.util.UT;
import gregapi.util.WD;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static gregapi.data.CS.*;

public abstract class WirelessBatteryBase extends MultiBatteryBase implements IWailaTile {
    public static final String NBT_SEALED = "ktfru.wireless.online";
    public static final String NBT_LINKS = "ktfru.wireless.links";
    public static final String NBT_TARGET_DIM = "ktfru.target.dimension";
    public static final String NBT_TARGET_KIND = "ktfru.target.kind";

    public final List<WirelessLink> partLinks = new ArrayList<>();
    public boolean sealed = true;
    public int invSize = 8;
    public int mWall = 18006, mCoil = 18041, mCond = 31040, mBatt = 31041;

    protected IStringBaseStructure structure;
    protected ChunkCoordinates lastFailedPos = null;

    protected abstract IStringBaseStructure createStructure();
    protected abstract int getMaxLinks();
    protected abstract long getMaxRange();
    protected abstract boolean allowsCrossDimension();
    protected abstract boolean acceptsReceiverKind(boolean aQuantum);
    protected abstract boolean acceptsReceiver(WirelessEnergyReceiver aReceiver);
    protected abstract TagData getTransferType(WirelessEnergyReceiver aReceiver);

    protected float getLinkLoss(WirelessLink aLink) {
        long range = getMaxRange();
        if (range <= 0) return 0;
        double distance = Math.sqrt(distanceSquared(aLink));
        return 0.05F + 0.25F * (float)Math.min(1D, distance / range);
    }

    protected double distanceSquared(WirelessLink aLink) {
        double dx = xCoord - aLink.x;
        double dy = yCoord - aLink.y;
        double dz = zCoord - aLink.z;
        return dx * dx + dy * dy + dz * dz;
    }

    protected World getLinkWorld(WirelessLink aLink) {
        if (worldObj != null && worldObj.provider.dimensionId == aLink.dimension) return worldObj;
        return DimensionManager.getWorld(aLink.dimension);
    }

    protected boolean isLinkInRange(WirelessLink aLink) {
        long range = getMaxRange();
        return range <= 0 || distanceSquared(aLink) <= (double)range * range;
    }

    protected String getStoredEnergyName() {
        return mEnergyType == null ? LH.get("ktfru.wireless.unbound") : mEnergyType.getLocalisedChatNameShort();
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        sealed = !aNBT.hasKey(NBT_SEALED) || aNBT.getBoolean(NBT_SEALED);
        if (aNBT.hasKey(NBT_INV_SIZE)) invSize = aNBT.getInteger(NBT_INV_SIZE);
        if (aNBT.hasKey(NBT_DESIGN + ".wall")) mWall = aNBT.getInteger(NBT_DESIGN + ".wall");
        if (aNBT.hasKey(NBT_DESIGN + ".coil")) mCoil = aNBT.getInteger(NBT_DESIGN + ".coil");
        if (aNBT.hasKey(NBT_DESIGN + ".batt")) mBatt = aNBT.getInteger(NBT_DESIGN + ".batt");
        if (aNBT.hasKey(NBT_DESIGN + ".cond")) mCond = aNBT.getInteger(NBT_DESIGN + ".cond");

        partLinks.clear();
        NBTTagList links = aNBT.getTagList(NBT_LINKS, 10);
        for (int i = 0; i < links.tagCount(); i++) {
            WirelessLink link = WirelessLink.read(links.getCompoundTagAt(i));
            if (link != null) partLinks.add(link);
        }
        if (partLinks.isEmpty() && aNBT.hasKey("ktfru.partPosList")) {
            int[] legacy = aNBT.getIntArray("ktfru.partPosList");
            for (int i = 0; i + 2 < legacy.length; i += 3) {
                partLinks.add(new WirelessLink(worldObj == null ? 0 : worldObj.provider.dimensionId, legacy[i], legacy[i + 1], legacy[i + 2], false));
            }
        }
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setBoolean(aNBT, NBT_SEALED, sealed);
        NBTTagList links = new NBTTagList();
        for (WirelessLink link : partLinks) links.appendTag(link.write());
        aNBT.setTag(NBT_LINKS, links);
    }

    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        if (!worldObj.blockExists(xCoord, yCoord, zCoord)) return mStructureOkay;
        if (structure == null) structure = createStructure();
        lastFailedPos = structure.checkStructure(new StructureContext(this,
                aPlayer != null || aInventory != null ? StructureContext.StringBaseMode.SET : StructureContext.StringBaseMode.CHECK,
                worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        return lastFailedPos == null;
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide()) return true;
        if (!mStructureOkay) aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED + LH.get(I18nHandler.STRUCTURE_ERR)));

        ItemStack equippedItem = aPlayer.getCurrentEquippedItem();
        if (equippedItem != null && equippedItem.getItem() instanceof ItemProjector) {
            structure.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }
        readUSBData(aPlayer);
        openGUI(aPlayer, aSide);
        return true;
    }

    protected boolean readUSBData(EntityPlayer aPlayer) {
        ItemStack equippedItem = aPlayer.getCurrentEquippedItem();
        if (!OM.is(OD_USB_STICKS[0], equippedItem) || !equippedItem.hasTagCompound()) return false;
        NBTTagCompound data = equippedItem.getTagCompound().getCompoundTag(NBT_USB_DATA);
        if (!data.hasKey(NBT_TARGET_X) || !data.hasKey(NBT_TARGET_Y) || !data.hasKey(NBT_TARGET_Z)) {
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED + LH.get(I18nHandler.ERROR) + ": " + LH.Chat.YELLOW + LH.get(I18nHandler.ERROR_DATA_INVALID)));
            return false;
        }

        int dimension = data.hasKey(NBT_TARGET_DIM) ? data.getInteger(NBT_TARGET_DIM) : worldObj.provider.dimensionId;
        WirelessLink link = new WirelessLink(dimension, data.getInteger(NBT_TARGET_X), data.getInteger(NBT_TARGET_Y), data.getInteger(NBT_TARGET_Z),
                data.hasKey(NBT_TARGET_KIND) && data.getBoolean(NBT_TARGET_KIND));

        if (aPlayer.isSneaking()) {
            boolean removed = partLinks.remove(link);
            aPlayer.addChatMessage(new ChatComponentText((removed ? LH.Chat.CYAN : LH.Chat.RED) + LH.get(removed ? "ktfru.wireless.link.removed" : "ktfru.wireless.link.missing")));
            return removed;
        }
        if (!acceptsReceiverKind(link.quantum)) {
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED + LH.get("ktfru.wireless.link.incompatible")));
            return false;
        }
        if (!allowsCrossDimension() && dimension != worldObj.provider.dimensionId) {
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED + LH.get("ktfru.wireless.link.wrong_dimension")));
            return false;
        }
        if (partLinks.contains(link)) {
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.YELLOW + LH.get("ktfru.wireless.link.duplicate")));
            return false;
        }
        if (partLinks.size() >= getMaxLinks()) {
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED + LH.get("ktfru.wireless.link.full")));
            return false;
        }
        if (!validateLinkForAdd(aPlayer, link)) return false;

        partLinks.add(link);
        aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN + LH.get("ktfru.wireless.link.added")));
        return true;
    }

    protected boolean validateLinkForAdd(EntityPlayer aPlayer, WirelessLink aLink) {
        World linkWorld = getLinkWorld(aLink);
        if (linkWorld == null || !linkWorld.blockExists(aLink.x, aLink.y, aLink.z)) {
            if (allowsCrossDimension()) return true;
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED + LH.get("ktfru.wireless.link.unloaded")));
            return false;
        }
        TileEntity tile = WD.te(linkWorld, aLink.x, aLink.y, aLink.z, false);
        if (!(tile instanceof WirelessEnergyReceiver) || !acceptsReceiver((WirelessEnergyReceiver)tile)) {
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED + LH.get("ktfru.wireless.link.incompatible")));
            return false;
        }
        if (!isLinkInRange(aLink)) {
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED + LH.get("ktfru.wireless.link.out_of_range")));
            return false;
        }
        return true;
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (aTool.equals(TOOL_monkeywrench)) {
            sealed = !sealed;
            return 1;
        }
        if (aTool.equals(TOOL_magnifyingglass)) {
            if (!aSneaking) aChatReturn.add(LH.get("ktfru.wireless.msg.sneak.to.see.links"));
            else for (WirelessLink link : partLinks) aChatReturn.add(link.dimension + ": " + link.x + ", " + link.y + ", " + link.z);
            return 1;
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (aIsServerSide && sealed && mStructureOkay) transmitWirelessEnergy();
    }

    protected void transmitWirelessEnergy() {
        long remainingAmpere = mMaxAmpere;
        Iterator<WirelessLink> iterator = partLinks.iterator();
        while (iterator.hasNext() && remainingAmpere > 0 && mEnergyStored > 0) {
            WirelessLink link = iterator.next();
            if (!allowsCrossDimension() && link.dimension != worldObj.provider.dimensionId) {
                iterator.remove();
                continue;
            }
            if (!isLinkInRange(link)) continue;

            World linkWorld = getLinkWorld(link);
            if (linkWorld == null || !linkWorld.blockExists(link.x, link.y, link.z)) continue;
            TileEntity tile = WD.te(linkWorld, link.x, link.y, link.z, false);
            if (!(tile instanceof WirelessEnergyReceiver) || !acceptsReceiver((WirelessEnergyReceiver)tile)) {
                iterator.remove();
                continue;
            }

            WirelessEnergyReceiver receiver = (WirelessEnergyReceiver)tile;
            TagData type = getTransferType(receiver);
            if (type == null || receiver.mOutputVoltage <= 0) continue;

            float loss = Math.max(0F, Math.min(0.95F, getLinkLoss(link)));
            long grossPerAmpere = (long)Math.ceil(receiver.mOutputVoltage / (1D - loss));
            if (grossPerAmpere <= 0) continue;

            long amperes = Math.min(remainingAmpere, Math.min(receiver.mOutputAmpere, mEnergyStored / grossPerAmpere));
            if (amperes <= 0) continue;

            long used = receiver.doInject(type, SIDE_INSIDE, receiver.mOutputVoltage, amperes, true);
            if (used <= 0) continue;

            mEnergyStored -= used * grossPerAmpere;
            remainingAmpere -= used;
            mOutputVoltageLast = receiver.mOutputVoltage;
            mOutputAmpereLast += used;
        }
    }

    @Override protected void doOutputEnergy() { }

    @Override
    public boolean isEnergyAcceptingFrom(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return sealed && mStructureOkay && super.isEnergyAcceptingFrom(aEnergyType, aSide, aTheoretical);
    }

    @Override
    public void addEnergyToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.GREEN + LH.get(LH.ENERGY_INPUT) + ": " + LH.Chat.WHITE + mInputMin + " - " + mInputMax + " " + getStoredEnergyName() + LH.Chat.WHITE + "/A * max " + LH.Chat.CYAN + mMaxAmpere + "A/t");
        aList.add(LH.Chat.CYAN + LH.get("ktfru.wireless.tooltip.range") + ": " + (getMaxRange() <= 0 ? LH.get("ktfru.wireless.unlimited") : getMaxRange()));
        aList.add(LH.Chat.CYAN + LH.get("ktfru.wireless.tooltip.loss") + ": " + (getMaxRange() <= 0 ? "0%" : "5% - 30%"));
        aList.add(LH.Chat.CYAN + LH.get("ktfru.wireless.tooltip.links") + ": " + getMaxLinks());
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        super.addToolTips(aList, aStack, aF3_H);
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        aList.add(LH.Chat.GRAY + LH.get("ktfru.wireless.tooltip.usb"));
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return structure != null && structure.isInsideStructure(this, mFacing, aX, aY, aZ);
    }

    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[invSize];}
    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return UT.Code.getAscendingArray(invSize);}
    @Override public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {return aSlot < invSize && !sealed;}
    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {return !sealed;}
    @Override public boolean isUseableByPlayerGUI(EntityPlayer aPlayer) {return super.isUseableByPlayerGUI(aPlayer) && !sealed;}
    @Override public int getInventoryStackLimit() {return 64;}
    @Override public boolean isInput(byte aSide) {return true;}
    @Override public boolean isOutput(byte aSide) {return false;}
    @Override public boolean[] getValidSides() {return SIDES_HORIZONTAL;}

    public static IIconContainer sTextureCommon = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/transformer/common"),
            sOverlayFront = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/transformer/front");

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon, mRGBa), aSide == mFacing ? BlockTextureDefault.get(sOverlayFront) : null);
    }

    @Override public IWailaInfoProvider[] getWailaInfos() {return instanceInfoEnergyIORange.asArray();}

    @Override
    public NBTTagCompound getWailaNBT(TileEntity te, NBTTagCompound aNBT) {
        IWailaTile.super.getWailaNBT(te, aNBT);
        aNBT.setLong("capa", mCapacity / 1000);
        aNBT.setLong("stored", mEnergyStored / 1000);
        aNBT.setInteger("links", partLinks.size());
        aNBT.setBoolean("online", sealed);
        return aNBT;
    }

    @Override
    public List<String> getWailaBody(List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        IWailaTile.super.getWailaBody(currentTip, accessor, config);
        NBTTagCompound data = accessor.getNBTData();
        currentTip.add(LH.get(I18nHandler.STORED_ENERGY) + LH.Chat.WHITE + ": " + data.getLong("stored") + "k / " + data.getLong("capa") + "k " + getStoredEnergyName());
        currentTip.add(LH.Chat.CYAN + LH.get("ktfru.wireless.tooltip.links") + ": " + data.getInteger("links") + "/" + getMaxLinks());
        return currentTip;
    }

    public static class WirelessLink {
        public final int dimension, x, y, z;
        public final boolean quantum;

        public WirelessLink(int dimension, int x, int y, int z, boolean quantum) {
            this.dimension = dimension;
            this.x = x;
            this.y = y;
            this.z = z;
            this.quantum = quantum;
        }

        public NBTTagCompound write() {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("dim", dimension);
            tag.setInteger("x", x);
            tag.setInteger("y", y);
            tag.setInteger("z", z);
            tag.setBoolean("quantum", quantum);
            return tag;
        }

        public static WirelessLink read(NBTTagCompound aNBT) {
            if (!aNBT.hasKey("dim") || !aNBT.hasKey("x") || !aNBT.hasKey("y") || !aNBT.hasKey("z")) return null;
            return new WirelessLink(aNBT.getInteger("dim"), aNBT.getInteger("x"), aNBT.getInteger("y"), aNBT.getInteger("z"), aNBT.getBoolean("quantum"));
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof WirelessLink)) return false;
            WirelessLink link = (WirelessLink)object;
            return dimension == link.dimension && x == link.x && y == link.y && z == link.z && quantum == link.quantum;
        }

        @Override
        public int hashCode() {
            int result = dimension;
            result = 31 * result + x;
            result = 31 * result + y;
            result = 31 * result + z;
            result = 31 * result + (quantum ? 1 : 0);
            return result;
        }
    }
}
