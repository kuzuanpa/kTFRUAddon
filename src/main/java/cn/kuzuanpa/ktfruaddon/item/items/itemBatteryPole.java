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

import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import gregapi.data.MT;
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class itemBatteryPole  extends MultiItemRandom {
    public itemBatteryPole() {
        super(MOD_ID, "ktfru.item.battery.pole");
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: Battery Pole", this,  MT.Au.mID));
    }


    @Override
    public void addItems() {

        ItemList.BatteryPoleGold.set(addItem(MT.Au.mID, "Gold Pole", ""));
        ItemList.BatteryPolePlatinum.set(addItem(MT.Pt.mID, "Platinum Pole", ""));
        ItemList.BatteryPoleGraphene.set(addItem(MT.Graphene.mID, "Graphene Pole", ""));
        ItemList.BatteryPoleCarbon.set(addItem(MT.C.mID, "Carbon Pole", ""));

    }
}
