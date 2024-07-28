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

import cn.kuzuanpa.ktfruaddon.cover.CoverFilterProperties;
import gregapi.data.IL;
import gregapi.data.TC;
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class itemTechnological extends MultiItemRandom {
    public itemTechnological() {
        super(MOD_ID, "ktfru.item.cover");
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: Covers", this, (short) 0));
    }


    @Override
    public void addItems() {
        IL.Cover_Filter_Item.set(addItem(0, "Item Filter (Properties)", "Filters for an Item", new CoverFilterProperties(), TC.stack(TC.MACHINA, 1), TC.stack(TC.ORDO, 1), TC.stack(TC.ITER, 1)));
    }
}
