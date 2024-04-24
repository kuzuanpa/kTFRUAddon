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
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class itemCompact extends MultiItemRandom {
    public itemCompact() {
        super(MOD_ID, "ktfru.item.compact");
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: Compact", this, (short) 100));
    }


    @Override
    public void addItems() {
        ItemList.CrucibleModelInnerLayer.set(addItem(0, "Crucible Model Inner Layer", ""));
        ItemList.IntelligentCore .set(addItem(100, "Intelligent Core", ""));

        ItemList.ArmorAirSealant.set(addItem(1000, "Armor Sealant", ""));
        ItemList.SpaceSuitCloth .set(addItem(1001, "Space Suit Inner Cloth", ""));

        //To Translators: those tooltip can be translate whatever you like.
        ItemList.EngineCrankShaftManual1.set(addItem(2000, "Rough Engine Crank Shaft (Bronze)", "You wonder if that could even work."));
        ItemList.EngineCrankShaftManual2.set(addItem(2001, "Rough Engine Crank Shaft (Arsenic Copper)", "You wonder if that could even work"));
        ItemList.EngineCrankShaftManual3.set(addItem(2002, "Rough Engine Crank Shaft (Arsenic Bronze)", "You wonder if that could even work"));
        ItemList.EngineCrankShaftManual4.set(addItem(2003, "Rough Engine Crank Shaft (Steel)", "You wonder if that could even work"));
        ItemList.EngineCrankShaftManual5.set(addItem(2004, "Rough Engine Crank Shaft (Invar)", "You wonder if that could even work"));
        ItemList.EngineCrankShaftManual6.set(addItem(2005, "Rough Engine Crank Shaft (Titanium)", "You wonder if that could even work"));
        ItemList.EngineCrankShaftManual7.set(addItem(2006, "Rough Engine Crank Shaft (Tungsten Steel)", "You wonder if that could even work"));
        ItemList.EngineCrankShaftManual8.set(addItem(2007, "Rough Engine Crank Shaft (Iridium)", "You wonder if that could even work"));

        ItemList.EngineCylinderManual1.set(addItem(2100, "Rough Engine Cylinder (Bronze)", "It doesn't seal enough..."));
        ItemList.EngineCylinderManual2.set(addItem(2101, "Rough Engine Cylinder (Arsenic Copper)", "It doesn't seal enough..."));
        ItemList.EngineCylinderManual3.set(addItem(2102, "Rough Engine Cylinder (Arsenic Bronze)", "It doesn't seal enough..."));
        ItemList.EngineCylinderManual4.set(addItem(2103, "Rough Engine Cylinder (Steel)", "It doesn't seal enough..."));
        ItemList.EngineCylinderManual5.set(addItem(2104, "Rough Engine Cylinder (Invar)", "It doesn't seal enough..."));
        ItemList.EngineCylinderManual6.set(addItem(2105, "Rough Engine Cylinder (Titanium)", "It doesn't seal enough..."));
        ItemList.EngineCylinderManual7.set(addItem(2106, "Rough Engine Cylinder (Tungsten Steel)", "It doesn't seal enough..."));
        ItemList.EngineCylinderManual8.set(addItem(2107, "Rough Engine Cylinder (Iridium)", "It doesn't seal enough..."));

        ItemList.EngineCrankShaft1.set(addItem(2300, "Engine Crank Shaft (Bronze)", ""));
        ItemList.EngineCrankShaft2.set(addItem(2301, "Engine Crank Shaft (Arsenic Copper)", ""));
        ItemList.EngineCrankShaft3.set(addItem(2302, "Engine Crank Shaft (Arsenic Bronze)", ""));
        ItemList.EngineCrankShaft4.set(addItem(2303, "Engine Crank Shaft (Steel)", ""));
        ItemList.EngineCrankShaft5.set(addItem(2304, "Engine Crank Shaft (Invar)", ""));
        ItemList.EngineCrankShaft6.set(addItem(2305, "Engine Crank Shaft (Titanium)", ""));
        ItemList.EngineCrankShaft7.set(addItem(2306, "Engine Crank Shaft (Tungsten Steel)", ""));
        ItemList.EngineCrankShaft8.set(addItem(2307, "Engine Crank Shaft (Iridium)", ""));

        ItemList.EngineCylinder1.set(addItem(2400, "Engine Cylinder (Bronze)", ""));
        ItemList.EngineCylinder2.set(addItem(2401, "Engine Cylinder (Arsenic Copper)", ""));
        ItemList.EngineCylinder3.set(addItem(2402, "Engine Cylinder (Arsenic Bronze)", ""));
        ItemList.EngineCylinder4.set(addItem(2403, "Engine Cylinder (Steel)", ""));
        ItemList.EngineCylinder5.set(addItem(2404, "Engine Cylinder (Invar)", ""));
        ItemList.EngineCylinder6.set(addItem(2405, "Engine Cylinder (Titanium)", ""));
        ItemList.EngineCylinder7.set(addItem(2406, "Engine Cylinder (Tungsten Steel)", ""));
        ItemList.EngineCylinder8.set(addItem(2407, "Engine Cylinder (Iridium)", ""));

    }
}
