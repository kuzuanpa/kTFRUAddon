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


package cn.kuzuanpa.ktfruaddon.item.items;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.item.items.itemPrefixWithTooltip;
import gregapi.code.ModData;
import gregapi.data.LH;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictPrefix;
import gregapi.util.ST;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.text.DecimalFormat;
import java.util.List;

public class itemTurbine extends itemPrefixWithTooltip {

    public itemTurbine(ModData aMod, String aNameInternal, OreDictPrefix aPrefix) {
        super(aMod, aNameInternal, aPrefix);
    }

    public static float getTurbineEfficiency(OreDictMaterial aMat){return (float) (Math.pow(aMat.mToolQuality, 2) + Math.pow(aMat.mToolSpeed, 2)/24D - aMat.mMass/200F);}
    public static long getTurbineDurability(OreDictMaterial aMat){return (long)(Math.pow(Math.max(0.5F,aMat.mToolQuality),1.5)*64*aMat.mToolDurability*65536L);}

    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
        super.addInformation(aStack, aPlayer, aList, aF3_H);
        OreDictMaterial tMat = this.getMaterial(ST.meta(aStack));
        DecimalFormat format = new DecimalFormat("0.00");
        aList.add(LH.Chat.WHITE+LH.get(I18nHandler.TURBINE_POWERRATE)+ ": " +LH.Chat.CYAN+ format.format(getTurbineEfficiency(tMat)));
        aList.add(LH.Chat.WHITE+LH.get(I18nHandler.TURBINE_DURABILITY)+ ": " +LH.Chat.GREEN+ getTurbineDurability(tMat)/(20*3600) + "RU * h");
    }
    @Override public int getItemStackLimit(ItemStack aStack) {return 1;}
}
