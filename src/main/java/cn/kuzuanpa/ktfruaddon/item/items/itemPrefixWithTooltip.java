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

import gregapi.code.ModData;
import gregapi.item.prefixitem.PrefixItem;
import gregapi.oredict.OreDictPrefix;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class itemPrefixWithTooltip extends PrefixItem {

    public List<String> tooltipList = new ArrayList<>();
    public itemPrefixWithTooltip(ModData aMod, String aNameInternal, OreDictPrefix aPrefix) {
        super(aMod, aNameInternal, aPrefix);
    }

    public itemPrefixWithTooltip addTooltips(String... tooltips){
        Collections.addAll(tooltipList, tooltips);
        return this;
    }
    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
        super.addInformation(aStack, aPlayer, aList, aF3_H);
        aList.addAll(tooltipList);
    }
}
