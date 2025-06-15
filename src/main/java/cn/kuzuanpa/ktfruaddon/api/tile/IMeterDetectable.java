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

package cn.kuzuanpa.ktfruaddon.api.tile;

import gregapi.code.TagData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public interface IMeterDetectable {
    static void sendReceiveEmitMessage(@Nullable List<MeterData> receivedEnergyList, @Nullable ArrayList<MeterData> emittedEnergyList,@NotNull List<String> aChatReturn) {
        if (receivedEnergyList!=null && !receivedEnergyList.isEmpty()) {
            aChatReturn.add("Receiving Energies: ");
            receivedEnergyList.forEach(e->aChatReturn.add(e.mSize + " "+e.mEnergyType.getLocalisedChatNameShort()+"§r/A * "+e.mAmperage +" A/t"));
        }
        else aChatReturn.add("Not Receiving Power");

        if (emittedEnergyList!=null && !emittedEnergyList.isEmpty()) {
            aChatReturn.add("Emitting Energies: ");
            emittedEnergyList.forEach(e->aChatReturn.add(e.mSize + " "+e.mEnergyType.getLocalisedChatNameShort()+"§r/A * "+e.mAmperage +" A/t"));
        }
        else aChatReturn.add("Not Emitting Power");
    }

    static void sendReceiveEmitMessage(@Nullable List<MeterData> receivedEnergyList, @Nullable TagData mEnergyTypeEmitting, long mSizeEmitting, long mAmperageEmitting, @NotNull List<String> aChatReturn) {
        if (receivedEnergyList!=null && !receivedEnergyList.isEmpty()) {
            aChatReturn.add("Receiving Energies: ");
            receivedEnergyList.forEach(e->aChatReturn.add(e.mSize + " "+e.mEnergyType.getLocalisedChatNameShort()+"§r/A * "+e.mAmperage +" A/t"));
        }
        else aChatReturn.add("Not Receiving Power");

        if (mEnergyTypeEmitting!=null && mSizeEmitting!=0) aChatReturn.add("Emitting: "+mSizeEmitting+" "+mEnergyTypeEmitting.getLocalisedChatNameShort()+"§r/A * "+mAmperageEmitting + " A/t");
        else aChatReturn.add("Not Emitting Power");
    }
    static void sendTransferMessage(@Nullable List<MeterData> transferedEnergyList, @NotNull List<String> aChatReturn) {
        if (transferedEnergyList!=null && !transferedEnergyList.isEmpty()) {
            aChatReturn.add("Transferring Energies: ");
            transferedEnergyList.forEach(e->aChatReturn.add(e.mSize + " "+e.mEnergyType.getLocalisedChatNameShort()+"§r/A * "+e.mAmperage +" A/t"));
        }
        else aChatReturn.add("Not Transfer Power");
    }
    public class MeterData {
        public final long mSize;
        public final long mAmperage;
        public final TagData mEnergyType;

        public MeterData(TagData mEnergyType, long mSize, long mAmperage) {
            this.mSize = mSize;
            this.mAmperage = mAmperage;
            this.mEnergyType = mEnergyType;
        }
    }
}