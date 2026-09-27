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

import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.ControllerPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.WirelessEnergyReceiver;
import gregapi.code.TagData;
import gregapi.data.TD;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.UT;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Collection;

import static gregapi.data.CS.NBT_DESIGN;
import static gregapi.data.CS.SIDE_INSIDE;

public class WirelessBatteryQuantum extends WirelessBatteryBase {
    public static final String NBT_STORED_ENERGY_TYPE = "ktfru.wireless.storedEnergyType";
    public int mChannel = 31113, mReadout = 31114;

    private static final String[] CORE_LAYER = {
            "SSSSS",
            "SPPPS",
            "SPRPS",
            "SPPPS",
            "SSSSS"
    };

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_DESIGN + ".channel")) mChannel = aNBT.getInteger(NBT_DESIGN + ".channel");
        if (aNBT.hasKey(NBT_DESIGN + ".readout")) mReadout = aNBT.getInteger(NBT_DESIGN + ".readout");
        if (aNBT.hasKey(NBT_STORED_ENERGY_TYPE)) {
            mEnergyType = TagData.createTagData(aNBT.getString(NBT_STORED_ENERGY_TYPE));
            mEnergyTypeOut = mEnergyType;
        } else {
            mEnergyType = null;
            mEnergyTypeOut = null;
        }
        structure = createStructure();
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        if (mEnergyType != null) aNBT.setString(NBT_STORED_ENERGY_TYPE, mEnergyType.mName);
    }

    @Override
    protected IStringBaseStructure createStructure() {
        return new LayerStructure(StructureContext.Axis.Y).layerRule("ABCCCDDDD")
                .fixedLayer('A', "XWWSS", "WSSSS", "SSSSS", "SSSSS", "SSSSS")
                .fixedLayer('B', "SSSSS", "SSSSS", "SSSSS", "SSSSS", "SSSSS")
                .fixedLayer('C', CORE_LAYER)
                .fixedLayer('D', "SSSSS", "SSSSS", "SSSSS", "SSSSS", "SSSSS")
                .where('X', new ControllerPredicate())
                .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, mWall, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN)))
                .where('S', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, mWall)))
                .where('P', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, mChannel)))
                .where('R', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, mReadout)))
                .setOffset(0, 0, 0);
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (aIsServerSide && mEnergyStored <= 0) clearStoredEnergyType();
    }

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if (!TD.Energy.ALL.contains(aEnergyType) || !sealed || !mStructureOkay || mCapacity <= 0) return 0;
        if (mEnergyStored > 0 && !aEnergyType.equals(mEnergyType)) return 0;

        long size = Math.abs(aSize);
        if (size == 0) return 0;
        long free = mCapacity - mEnergyStored;
        long canReceiveAmount = Math.min(free / size + (free % size == 0 ? 0 : 1), mMaxAmpere);
        long receiveAmount = Math.min(canReceiveAmount, Math.max(0, aAmount));
        if (aDoInject && receiveAmount > 0) {
            mEnergyType = aEnergyType;
            mEnergyTypeOut = aEnergyType;
            mEnergyStored += receiveAmount * size;
            receivedEnergy.add(new MeterData(aEnergyType, size, receiveAmount));
        }
        return receiveAmount;
    }

    protected void clearStoredEnergyType() {
        mEnergyType = null;
        mEnergyTypeOut = null;
    }

    @Override protected int getMaxLinks() {return 32;}
    @Override protected long getMaxRange() {return 0;}
    @Override protected boolean allowsCrossDimension() {return true;}
    @Override protected boolean acceptsReceiverKind(boolean aQuantum) {return aQuantum;}
    @Override protected boolean acceptsReceiver(WirelessEnergyReceiver aReceiver) {return aReceiver.mUniversal;}
    @Override protected TagData getTransferType(WirelessEnergyReceiver aReceiver) {return mEnergyType;}

    @Override public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {return isSupportedEnergy(aEnergyType);}
    @Override public boolean isEnergyCapacitorType(TagData aEnergyType, byte aSide) {return isSupportedEnergy(aEnergyType);}
    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return TD.Energy.ALL;}
    @Override public Collection<TagData> getEnergyCapacitorTypes(byte aSide) {return TD.Energy.ALL;}
    @Override public long getEnergySizeInputMin(TagData aEnergyType, byte aSide) {return 1;}
    @Override public long getEnergySizeInputRecommended(TagData aEnergyType, byte aSide) {return mInputMax;}
    @Override public long getEnergySizeInputMax(TagData aEnergyType, byte aSide) {return Long.MAX_VALUE;}

    protected boolean isSupportedEnergy(TagData aEnergyType) {
        return aEnergyType != null && TD.Energy.ALL.contains(aEnergyType)
                && (mEnergyStored <= 0 || mEnergyType == null || aEnergyType.equals(mEnergyType));
    }

    @Override public String getTileEntityName() {return "ktfru.multitileentity.multiblock.storage.wireless.quantum";}
}
