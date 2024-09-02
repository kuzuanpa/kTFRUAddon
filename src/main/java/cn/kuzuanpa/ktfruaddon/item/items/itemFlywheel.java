/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.item.items;

import cn.kuzuanpa.ktfruaddon.i18n.texts.kTooltips;
import gregapi.code.ModData;
import gregapi.data.LH;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictPrefix;
import gregapi.util.ST;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.text.DecimalFormat;
import java.util.List;

public class itemFlywheel extends itemPrefixWithTooltip{
    public static float getMaxRPM(int meta){
        OreDictMaterial mat = OreDictMaterial.get(meta);
        return (float) (Math.pow(Math.max(0.4,mat.mToolQuality),2.4) * mat.mToolSpeed*4);
    }
    public static long getMaxStorage(int meta){
        OreDictMaterial mat = OreDictMaterial.get(meta);
        return (long) Math.floor(mat.mMass*128*getMaxRPM(meta));
    }

    public itemFlywheel(ModData aMod, String aNameInternal, OreDictPrefix aPrefix) {
        super(aMod, aNameInternal, aPrefix);
    }
    @Override public int getItemStackLimit(ItemStack aStack) {return 1;}
    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
        super.addInformation(aStack, aPlayer, aList, aF3_H);
        DecimalFormat format = new DecimalFormat("0.00");
        aList.add(LH.Chat.WHITE+LH.get(kTooltips.FLYWHEEL_MaxRPM)+ " " +LH.Chat.ORANGE+ format.format(getMaxRPM(ST.meta(aStack)))+" RU/t");
        aList.add(LH.Chat.WHITE+LH.get(kTooltips.FLYWHEEL_STORAGE)+ " " +LH.Chat.GREEN+ getMaxStorage(ST.meta(aStack)) + " RU");
    }
}
