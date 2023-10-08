/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.item;

import gregapi.item.IItemEnergy;
import gregapi.item.multiitem.MultiItem;
import net.minecraft.item.ItemStack;

public class ItemOreScanner extends MultiItem {

    public ItemOreScanner(String aModID, String aUnlocalized) {
        super(aModID, aUnlocalized);
    }

    @Override
    public IItemEnergy getEnergyStats(ItemStack aStack) {
        return null;
    }

    @Override
    public Long[] getFluidContainerStats(ItemStack aStack) {
        return new Long[0];
    }
}
