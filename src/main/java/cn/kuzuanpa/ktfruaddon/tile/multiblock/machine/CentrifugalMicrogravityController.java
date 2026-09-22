package cn.kuzuanpa.ktfruaddon.tile.multiblock.machine;

import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.base.TileEntityBaseRoom;
import cn.kuzuanpa.ktfruaddon.api.tile.room.HollowBoxRoomRegion;
import cn.kuzuanpa.ktfruaddon.api.tile.room.IRoomRegion;
import cn.kuzuanpa.ktfruaddon.api.tile.room.RoomCapability;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.ControllerPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.multiblocks.IMultiBlockEnergy;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CentrifugalMicrogravityController extends TileEntityBaseRoom implements ITileEntityEnergy, IMultiBlockEnergy {
    public static final int SHIELD_WALL = 31204, MASS_RING = 31205, GYRO_ANCHOR = 31206, ACCESS_HATCH = 31207, ENERGY_PORT = 31208;
    public static final int SHELL_RADIUS = 4, SHELL_HEIGHT = 8;
    public static final long ENERGY_CAPACITY = 65536L, ENERGY_PER_TICK = 2048L;
    private long roomEnergy; private int spinProgress; private boolean roomActive;

    private static final IStringBaseStructure structure = new LayerStructure(StructureContext.Axis.Y)
            .layerRule("ABBBBBBBC")
            .fixedLayer('A',
                    "AWRWPWRWA",
                    "WWWWWWWWW",
                    "WWWWWWWWW",
                    "WWWWWWWWW",
                    "WWWWCWWWW",
                    "WWWWWWWWW",
                    "WWWWWWWWW",
                    "WWWWWWWWW",
                    "AWRWHWRWA")
            .fixedLayer('B',
                    "WWWWWWWWW",
                    "W       W",
                    "W       W",
                    "W       W",
                    "W       W",
                    "W       W",
                    "W       W",
                    "W       W",
                    "WWWWWWWWW")
            .fixedLayer('C',
                    "AWRWWWRWA",
                    "WWWWWWWWW",
                    "WWWWWWWWW",
                    "WWWWWWWWW",
                    "WWWWWWWWW",
                    "WWWWWWWWW",
                    "WWWWWWWWW",
                    "WWWWWWWWW",
                    "AWRWWWRWA")
            .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, SHIELD_WALL, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('R', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, MASS_RING, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('A', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, GYRO_ANCHOR, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('H', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, ACCESS_HATCH, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('P', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, ENERGY_PORT, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN, 0)))
            .where('C', new ControllerPredicate())
            .setOffset(-4, 0, -4);

    static {
        LH.add("ktfru.tooltip.multiblock.microgravityroom.1", "9x9x9 high-shield shell, controller centered on the bottom layer");
        LH.add("ktfru.tooltip.multiblock.microgravityroom.2", "Requires 8 mass rings, 8 gyro anchors, 1 access hatch and 1 energy port.");
        LH.add("ktfru.tooltip.multiblock.microgravityroom.3", "Provides MICROGRAVITY and HIGH_RADIATION_SHIELD after spin-up.");
    }

    // Room
    @Override protected IRoomRegion createRoomRegion() {
        return new HollowBoxRoomRegion(
                xCoord - SHELL_RADIUS, yCoord, zCoord - SHELL_RADIUS,
                xCoord + SHELL_RADIUS, yCoord + SHELL_HEIGHT, zCoord + SHELL_RADIUS,
                1);
    }
    @Override protected IStringBaseStructure getRoomStructure() {return structure;}
    @Override public long getProvidedCapabilities() {return RoomCapability.mask(RoomCapability.MICROGRAVITY, RoomCapability.HIGH_RADIATION_SHIELD);}
    @Override public boolean isRoomActive() {return roomActive && mStructureOkay;}

    // Tick
    @Override public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (!aIsServerSide) return;

        if ((aTimer & 19L) == 0L || mStructureChanged) checkStructure(false);

        if (!mStructureOkay) {
            spinProgress = 0;
            roomActive = false;
            unregisterRoom();
            return;
        }

        boolean powered = roomEnergy >= ENERGY_PER_TICK;
        if (powered) {
            roomEnergy -= ENERGY_PER_TICK;
            if (spinProgress < 600) spinProgress++;
        } else if (spinProgress > 0) {
            spinProgress = Math.max(0, spinProgress - 4);
        }

        boolean nextActive = spinProgress >= 600;
        if (nextActive != roomActive) {
            roomActive = nextActive;
            publishRoom();
            markDirty();
        }
    }

    // NBT
    @Override public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        roomEnergy = aNBT.getLong("ktfru.room.energy");
        spinProgress = aNBT.getInteger("ktfru.room.spin");
        roomActive = aNBT.getBoolean("ktfru.room.active") && spinProgress >= 600;
    }
    @Override public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        aNBT.setLong("ktfru.room.energy", roomEnergy);
        aNBT.setInteger("ktfru.room.spin", spinProgress);
        aNBT.setBoolean("ktfru.room.active", roomActive);
    }

    // Energy
    @Override public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {return aEnergyType == TD.Energy.EU;}
    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return Collections.singleton(TD.Energy.EU);}
    @Override public boolean isEnergyAcceptingFrom(TagData aEnergyType, byte aSide, boolean aTheoretical) {return aEnergyType == TD.Energy.EU;}
    @Override public boolean isEnergyEmittingTo(TagData aEnergyType, byte aSide, boolean aTheoretical) {return false;}
    @Override public long doEnergyInjection(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if (aEnergyType != TD.Energy.EU || aSize <= 0 || aAmount <= 0) return 0;
        long available = ENERGY_CAPACITY - roomEnergy;
        long offered = aSize * aAmount;
        long accepted = Math.min(available, offered);
        if (accepted < aSize) return 0;
        if (aDoInject) roomEnergy += accepted;
        return accepted / aSize;
    }
    @Override public long getEnergyDemanded(TagData aEnergyType, byte aSide, long aSize) {return aEnergyType == TD.Energy.EU ? Math.max(0, ENERGY_CAPACITY - roomEnergy) : 0;}
    @Override public long doEnergyExtraction(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoExtract) {return 0;}
    @Override public long getEnergyOffered(TagData aEnergyType, byte aSide, long aSize) {return 0;}
    @Override public long getEnergySizeInputMin(TagData aEnergyType, byte aSide) {return aEnergyType == TD.Energy.EU ? 1 : 0;}
    @Override public long getEnergySizeOutputMin(TagData aEnergyType, byte aSide) {return 0;}
    @Override public long getEnergySizeInputRecommended(TagData aEnergyType, byte aSide) {return aEnergyType == TD.Energy.EU ? ENERGY_PER_TICK : 0;}
    @Override public long getEnergySizeOutputRecommended(TagData aEnergyType, byte aSide) {return 0;}
    @Override public long getEnergySizeInputMax(TagData aEnergyType, byte aSide) {return aEnergyType == TD.Energy.EU ? 8192 : 0;}
    @Override public long getEnergySizeOutputMax(TagData aEnergyType, byte aSide) {return 0;}

    @Override public boolean isEnergyType(MultiTileEntityMultiBlockPart aPart, TagData aEnergyType, byte aSide, boolean aEmitting) {return isEnergyType(aEnergyType, aSide, aEmitting);}
    @Override public Collection<TagData> getEnergyTypes(MultiTileEntityMultiBlockPart aPart, byte aSide) {return getEnergyTypes(aSide);}
    @Override public boolean isEnergyAcceptingFrom(MultiTileEntityMultiBlockPart aPart, TagData aEnergyType, byte aSide, boolean aTheoretical) {return isEnergyAcceptingFrom(aEnergyType, aSide, aTheoretical);}
    @Override public boolean isEnergyEmittingTo(MultiTileEntityMultiBlockPart aPart, TagData aEnergyType, byte aSide, boolean aTheoretical) {return false;}
    @Override public long doEnergyInjection(MultiTileEntityMultiBlockPart aPart, TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {return doEnergyInjection(aEnergyType, aSide, aSize, aAmount, aDoInject);}
    @Override public long getEnergyDemanded(MultiTileEntityMultiBlockPart aPart, TagData aEnergyType, byte aSide, long aSize) {return getEnergyDemanded(aEnergyType, aSide, aSize);}
    @Override public long doEnergyExtraction(MultiTileEntityMultiBlockPart aPart, TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoExtract) {return 0;}
    @Override public long getEnergyOffered(MultiTileEntityMultiBlockPart aPart, TagData aEnergyType, byte aSide, long aSize) {return 0;}
    @Override public long getEnergySizeInputMin(MultiTileEntityMultiBlockPart aPart, TagData aEnergyType, byte aSide) {return getEnergySizeInputMin(aEnergyType, aSide);}
    @Override public long getEnergySizeOutputMin(MultiTileEntityMultiBlockPart aPart, TagData aEnergyType, byte aSide) {return 0;}
    @Override public long getEnergySizeInputRecommended(MultiTileEntityMultiBlockPart aPart, TagData aEnergyType, byte aSide) {return getEnergySizeInputRecommended(aEnergyType, aSide);}
    @Override public long getEnergySizeOutputRecommended(MultiTileEntityMultiBlockPart aPart, TagData aEnergyType, byte aSide) {return 0;}
    @Override public long getEnergySizeInputMax(MultiTileEntityMultiBlockPart aPart, TagData aEnergyType, byte aSide) {return getEnergySizeInputMax(aEnergyType, aSide);}
    @Override public long getEnergySizeOutputMax(MultiTileEntityMultiBlockPart aPart, TagData aEnergyType, byte aSide) {return 0;}

    // GUI
    @Override public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (isServerSide() && aPlayer != null) {
            String status = !mStructureOkay ? "Invalid room shell" : (roomActive ? "Microgravity field active" : (roomEnergy < ENERGY_PER_TICK ? "Insufficient EU" : "Spinning up: " + spinProgress + "/600"));
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN + "[Microgravity Room] " + LH.Chat.WHITE + status));
            ItemStack equipped = aPlayer.getCurrentEquippedItem();
            if (equipped != null && equipped.getItem() instanceof ItemProjector) {
                structure.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT,
                        worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
                return true;
            }
        }
        return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
    }
    @Override public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.microgravityroom.1"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.microgravityroom.2"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.microgravityroom.3"));
        super.addToolTips(aList, aStack, aF3_H);
    }
    @Override public String getTileEntityName() {return "ktfru.multitileentity.multiblock.microgravityroom";}
}
