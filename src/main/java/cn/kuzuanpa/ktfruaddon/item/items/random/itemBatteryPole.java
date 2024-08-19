/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.item.items.random;

import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import gregapi.data.MT;
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;
import static gregapi.data.CS.NUM_SUB;

public class itemBatteryPole  extends MultiItemRandom {
    public itemBatteryPole() {
        super(MOD_ID, "ktfru.item.battery.pole");
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: Battery Pole", this,  MT.Ni.mID));
    }


    @Override
    public void addItems() {

        ItemList.BatteryPoleNickel.set(addItem(MT.Ni.mID, "Nickel Pole", ""));
        ItemList.BatteryPoleCaTiO3.set(addItem(MT.Fe.mID, "CaTiO"+NUM_SUB[3]+" Pole", ""));
        ItemList.BatteryPolePlatinum.set(addItem(MT.Pt.mID, "Platinum Pole", ""));
        ItemList.BatteryPoleCarbon.set(addItem(MT.C.mID, "Carbon Pole", ""));

    }
}
