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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.transform;

import cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.storage.MultiBatteryBase;
import gregapi.data.LH;
import gregapi.tileentity.energy.ITileEntityEnergy;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

import static gregapi.data.CS.*;

public abstract class MultiTransformerBase extends MultiBatteryBase {
    long mOutputMin, mOutputMax, mEfficiency;
    public void addEnergyToolTips(List<String> aList, ItemStack aStack, boolean aF3_H){
        aList.add(LH.Chat.YELLOW + LH.get(LH.EFFICIENCY)  + ": " + LH.Chat.WHITE + mEfficiency/100F + "%");
        aList.add(LH.Chat.GREEN + LH.get(LH.ENERGY_INPUT)  + ": " + LH.Chat.WHITE + mInputMin  + " - " +mInputMax  + mEnergyType.getLocalisedChatNameShort() + LH.Chat.WHITE + "/A * max " + LH.Chat.CYAN + mMaxAmpere + "A/t");
        aList.add(LH.Chat.RED   + LH.get(LH.ENERGY_OUTPUT) + ": " + LH.Chat.WHITE + mOutputMin + " - " +mOutputMax + mEnergyTypeOut.getLocalisedChatNameShort() + LH.Chat.WHITE + "/A * max " + LH.Chat.CYAN + mMaxAmpere + "A/t");
    }
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_OUTPUT_MIN)) mOutputMin = aNBT.getLong(NBT_OUTPUT_MIN);
        if (aNBT.hasKey(NBT_OUTPUT_MAX)) mOutputMax = aNBT.getLong(NBT_OUTPUT_MAX);

        if (aNBT.hasKey(NBT_EFFICIENCY)) mEfficiency = aNBT.getLong(NBT_EFFICIENCY);
        else mEfficiency = mOutputMax*10000/mInputMax;
        mCapacity = mInputMax * mMaxAmpere * 2;
    }

    protected void doOutputEnergy(){
        long energyConsume = (long) (mEnergyStored * mEfficiency/10000F);

        long amount = (long) Math.min(mMaxAmpere, Math.ceil(energyConsume*1F/mOutputMax));
        long outputAmpere = (mMode == 0 ? amount : Math.min(mMode, amount));
        if (outputAmpere > 0) {
            long outputVoltage = Math.min(mOutputMax, energyConsume / outputAmpere);
            long tAmountUsed = ITileEntityEnergy.Util.emitEnergyToSide(mEnergyTypeOut, getEmittingSide(), outputVoltage, outputAmpere, getEmittingTileEntity());
            mOutputAmpereLast = tAmountUsed;
            mOutputVoltageLast = outputVoltage;
            mEnergyStored -= outputVoltage * tAmountUsed * 10000 / mEfficiency;
        }
    }
    public abstract TileEntity getEmittingTileEntity();
    public abstract byte getEmittingSide();

}
