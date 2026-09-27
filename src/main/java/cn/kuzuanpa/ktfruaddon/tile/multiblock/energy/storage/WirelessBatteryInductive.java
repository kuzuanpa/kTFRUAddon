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
import net.minecraft.nbt.NBTTagCompound;

public class WirelessBatteryInductive extends WirelessBatteryBase {
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        structure = createStructure();
    }

    @Override
    protected IStringBaseStructure createStructure() {
        return new LayerStructure(StructureContext.Axis.Y).layerRule("AABBBCC")
                .fixedLayer('A', "XTTT", "TCCT", "TCCT", "TTTT")
                .fixedLayer('B', "OOOO", "OCCO", "OCCO", "OOOO")
                .fixedLayer('C', "WWWW", "WBBW", "WBBW", "WWWW")
                .where('X', new ControllerPredicate())
                .where('T', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, mCond)))
                .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, mWall, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN)))
                .where('C', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, mCond)))
                .where('O', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, mCoil)))
                .where('B', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, mBatt)))
                .setOffset(0, 0, 0);
    }

    @Override protected int getMaxLinks() {return 8;}
    @Override protected long getMaxRange() {return 64;}
    @Override protected boolean allowsCrossDimension() {return false;}
    @Override protected boolean acceptsReceiverKind(boolean aQuantum) {return !aQuantum;}
    @Override protected boolean acceptsReceiver(WirelessEnergyReceiver aReceiver) {return !aReceiver.mUniversal;}
    @Override protected TagData getTransferType(WirelessEnergyReceiver aReceiver) {return aReceiver.mUniversal ? null : TD.Energy.EU;}
    @Override public String getTileEntityName() {return "ktfru.multitileentity.multiblock.storage.wireless.inductive";}
}
