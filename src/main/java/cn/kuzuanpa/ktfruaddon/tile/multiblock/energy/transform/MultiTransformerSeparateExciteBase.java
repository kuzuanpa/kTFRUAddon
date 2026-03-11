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

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

import static gregapi.data.CS.*;

public abstract class MultiTransformerSeparateExciteBase extends MultiTransformerBase{
    public TagData mSeparateExciteEnergyType = TD.Energy.QU;
    public float mSeparateExcitePercent = 0.05F;
    public long mSeparateExciteEnergy = 0;
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_ENERGY_ACCEPTED_2)) mSeparateExciteEnergyType = TagData.createTagData(aNBT.getString(NBT_ENERGY_ACCEPTED_2));
        if (aNBT.hasKey("ktfru.separateExcitePercent")) mSeparateExcitePercent = aNBT.getFloat("ktfru.separateExcitePercent");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        super.addToolTips(aList, aStack, aF3_H);
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.ENERGY_NEED_TO_RUN)  + ": " + LH.Chat.WHITE + getMinStartEnergy() + mSeparateExciteEnergyType.getLocalisedChatNameShort() + LH.Chat.WHITE + "/t");
    }

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if(!mSeparateExciteEnergyType.equals(aEnergyType)) return super.doInject(aEnergyType, aSide, aSize, aAmount, aDoInject);
        long canReceiveAmount = Math.min( (long) Math.ceil((getMinStartEnergy()-mSeparateExciteEnergy)*1F/aSize) , mMaxAmpere);
        if(canReceiveAmount <=0)return 0;
        long receiveAmount = Math.min(canReceiveAmount,aAmount);
        if (aDoInject) {
            mSeparateExciteEnergy += receiveAmount * aSize;
            this.receivedEnergy.add(new MeterData(aEnergyType, aSize, receiveAmount));
        }
        return receiveAmount;
    }

    @Override
    protected void doOutputEnergy() {
        if(getMinStartEnergy() > mSeparateExciteEnergy){
            mSeparateExciteEnergy = 0;
            return;
        }
        mSeparateExciteEnergy -= getMinStartEnergy();
        super.doOutputEnergy();
    }
    @Override public boolean isEnergyType                   (TagData aEnergyType, byte aSide, boolean aEmitting) {return aEmitting ? aEnergyType == mEnergyTypeOut : aEnergyType == mEnergyType || aEnergyType == mSeparateExciteEnergyType;}


    @Override
    public long getEnergySizeInputMin(TagData aEnergyType, byte aSide) {
        if(mSeparateExciteEnergyType.equals(aEnergyType))return getMinStartEnergy();
        return super.getEnergySizeInputMin(aEnergyType, aSide);
    }

    public long getMinStartEnergy(){
        return (long) Math.floor(mInputMin * mSeparateExcitePercent);
    }
}
