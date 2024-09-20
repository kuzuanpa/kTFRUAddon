/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

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
import gregapi.oredict.OreDictItemData;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;
import static gregapi.data.CS.*;

public class itemCompact extends MultiItemRandom {
    public itemCompact() {
        super(MOD_ID, "ktfru.item.compact");
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: Compact", this, (short) 100));
    }


    @Override
    public void addItems() {
        ItemList.CrucibleModelInnerLayer .set(addItem(0, "Crucible Model Inner Layer", ""));
        ItemList.Co60FlawDetectionCore   .set(addItem(1, "Co-60 Flaw Detection Core", ""));
        ItemList.Tm170FlawDetectionCore  .set(addItem(2, "Tm-170 Flaw Detection Core", ""));
        ItemList.GoodCircuitPartCore     .set(addItem(3, "Good Circuit Part Core", ""));
        ItemList.TFRUCoin1               .set(addItem(4,  "1 TFRU Coin", ""));
        ItemList.TFRUCoin5               .set(addItem(5,  "5 TFRU Coin", ""));
        ItemList.TFRUCoin10              .set(addItem(6, "10 TFRU Coin", ""));
        ItemList.PreparedIronOre         .set(addItem(7, "Prepared Iron Ore", ""));
        //Clay Molds for glass and so on.
        ItemList.ClayGlassBlockMold                  .set(addItem(8,  "Clay Glass Block Mold", ""));
        ItemList.CeramicGlassBlockMold               .set(addItem(9,  "Ceramic Glass Block Mold", ""));
        ItemList.CeramicGlassBlockMoldFull           .set(addItem(10, "Ceramic Glass Block Mold Full", ""));
        ItemList.CeramicGlassBlockMoldComplete       .set(addItem(11, "Ceramic Glass Block Mold Complete ", ""));
        ItemList.ClayGlassBottleMold                 .set(addItem(12, "Clay Glass Bottle Mold", ""));
        ItemList.CeramicGlassBottleMold              .set(addItem(13, "Ceramic Glass Bottle Mold", ""));
        ItemList.CeramicGlassBottleMoldFull          .set(addItem(14, "Ceramic Glass Bottle Mold Full", ""));
        ItemList.CeramicGlassBottleMoldComplete      .set(addItem(15, "Ceramic Glass Bottle Mold Complete", ""));
        ItemList.NetherStarPlus                      .set(addItem(16, "Nether Star Plus", "PlaceHolder now"));

        ItemList.NaturalCore                         .set(addItem(18, "Natural Core", ""));
        ItemList.FlowerCluster                       .set(addItem(19, "Flower Cluster", ""));


        ItemList.IntelligentCore         .set(addItem(100, "Intelligent Core", ""));
        ItemList.RefinedStoragePart      .set(addItem(101, "Refined Storage Part", ""));
        ItemList.RefinedFluidStoragePart .set(addItem(102, "Refined Fluid Storage Part", ""));

        ItemList.ArmorAirSealant.set(addItem(1000, "Armor Sealant", ""));
        ItemList.SpaceSuitCloth .set(addItem(1001, "Space Suit Inner Cloth", ""));

        //To Translators: those tooltip can be translated to whatever you like.
        ItemList.EngineCrankShaftManual1 .set(addItem(2000, "Rough Engine Crank Shaft (Bronze)"        , "You wonder if that could even work"),new OreDictItemData(MT.Bronze       ,2*U));
        ItemList.EngineCrankShaftManual2 .set(addItem(2001, "Rough Engine Crank Shaft (Arsenic Copper)", "You wonder if that could even work"),new OreDictItemData(MT.ArsenicCopper,2*U));
        ItemList.EngineCrankShaftManual3 .set(addItem(2002, "Rough Engine Crank Shaft (Arsenic Bronze)", "You wonder if that could even work"),new OreDictItemData(MT.ArsenicBronze,2*U));
        ItemList.EngineCrankShaftManual4 .set(addItem(2003, "Rough Engine Crank Shaft (Steel)"         , "You wonder if that could even work"),new OreDictItemData(MT.Steel        ,2*U));
        ItemList.EngineCrankShaftManual5 .set(addItem(2004, "Rough Engine Crank Shaft (Invar)"         , "You wonder if that could even work"),new OreDictItemData(MT.Invar        ,2*U));
        ItemList.EngineCrankShaftManual6 .set(addItem(2005, "Rough Engine Crank Shaft (Titanium)"      , "You wonder if that could even work"),new OreDictItemData(MT.Ti           ,2*U));
        ItemList.EngineCrankShaftManual7 .set(addItem(2006, "Rough Engine Crank Shaft (Tungsten Steel)", "You wonder if that could even work"),new OreDictItemData(MT.TungstenSteel,2*U));
        ItemList.EngineCrankShaftManual8 .set(addItem(2007, "Rough Engine Crank Shaft (Iridium)"       , "You wonder if that could even work"),new OreDictItemData(MT.Ir           ,2*U));
        ItemList.EngineCrankShaftManualCr.set(addItem(2008, "Rough Engine Crank Shaft (Chromium)"      , "You wonder if that could even work"),new OreDictItemData(MT.Cr           ,2*U));

        ItemList.EngineCylinderManual1 .set(addItem(2100, "Rough Engine Cylinder (Bronze)"        , "It doesn't seal enough..."),new OreDictItemData(MT.Bronze       ,2*U+U2));
        ItemList.EngineCylinderManual2 .set(addItem(2101, "Rough Engine Cylinder (Arsenic Copper)", "It doesn't seal enough..."),new OreDictItemData(MT.ArsenicCopper,2*U+U2));
        ItemList.EngineCylinderManual3 .set(addItem(2102, "Rough Engine Cylinder (Arsenic Bronze)", "It doesn't seal enough..."),new OreDictItemData(MT.ArsenicBronze,2*U+U2));
        ItemList.EngineCylinderManual4 .set(addItem(2103, "Rough Engine Cylinder (Steel)"         , "It doesn't seal enough..."),new OreDictItemData(MT.Steel        ,2*U+U2));
        ItemList.EngineCylinderManual5 .set(addItem(2104, "Rough Engine Cylinder (Invar)"         , "It doesn't seal enough..."),new OreDictItemData(MT.Invar        ,2*U+U2));
        ItemList.EngineCylinderManual6 .set(addItem(2105, "Rough Engine Cylinder (Titanium)"      , "It doesn't seal enough..."),new OreDictItemData(MT.Ti           ,2*U+U2));
        ItemList.EngineCylinderManual7 .set(addItem(2106, "Rough Engine Cylinder (Tungsten Steel)", "It doesn't seal enough..."),new OreDictItemData(MT.TungstenSteel,2*U+U2));
        ItemList.EngineCylinderManual8 .set(addItem(2107, "Rough Engine Cylinder (Iridium)"       , "It doesn't seal enough..."),new OreDictItemData(MT.Ir           ,2*U+U2));
        ItemList.EngineCylinderManualCr.set(addItem(2108, "Rough Engine Cylinder (Chromium)"      , "It doesn't seal enough..."),new OreDictItemData(MT.Cr           ,2*U+U2));

        ItemList.EngineCrankShaft1 .set(addItem(2200, "Engine Crank Shaft (Bronze)"        , ""),new OreDictItemData(MT.Bronze       ,2*U));
        ItemList.EngineCrankShaft2 .set(addItem(2201, "Engine Crank Shaft (Arsenic Copper)", ""),new OreDictItemData(MT.ArsenicCopper,2*U));
        ItemList.EngineCrankShaft3 .set(addItem(2202, "Engine Crank Shaft (Arsenic Bronze)", ""),new OreDictItemData(MT.ArsenicBronze,2*U));
        ItemList.EngineCrankShaft4 .set(addItem(2203, "Engine Crank Shaft (Steel)"         , ""),new OreDictItemData(MT.Steel        ,2*U));
        ItemList.EngineCrankShaft5 .set(addItem(2204, "Engine Crank Shaft (Invar)"         , ""),new OreDictItemData(MT.Invar        ,2*U));
        ItemList.EngineCrankShaft6 .set(addItem(2205, "Engine Crank Shaft (Titanium)"      , ""),new OreDictItemData(MT.Ti           ,2*U));
        ItemList.EngineCrankShaft7 .set(addItem(2206, "Engine Crank Shaft (Tungsten Steel)", ""),new OreDictItemData(MT.TungstenSteel,2*U));
        ItemList.EngineCrankShaft8 .set(addItem(2207, "Engine Crank Shaft (Iridium)"       , ""),new OreDictItemData(MT.Ir           ,2*U));
        ItemList.EngineCrankShaftCr.set(addItem(2208, "Engine Crank Shaft (Chromium)"      , ""),new OreDictItemData(MT.Cr           ,2*U));

        ItemList.EngineCylinder1 .set(addItem(2300, "Engine Cylinder (Bronze)"        , ""),new OreDictItemData(MT.Bronze       ,2*U+U2));
        ItemList.EngineCylinder2 .set(addItem(2301, "Engine Cylinder (Arsenic Copper)", ""),new OreDictItemData(MT.ArsenicCopper,2*U+U2));
        ItemList.EngineCylinder3 .set(addItem(2302, "Engine Cylinder (Arsenic Bronze)", ""),new OreDictItemData(MT.ArsenicBronze,2*U+U2));
        ItemList.EngineCylinder4 .set(addItem(2303, "Engine Cylinder (Steel)"         , ""),new OreDictItemData(MT.Steel        ,2*U+U2));
        ItemList.EngineCylinder5 .set(addItem(2304, "Engine Cylinder (Invar)"         , ""),new OreDictItemData(MT.Invar        ,2*U+U2));
        ItemList.EngineCylinder6 .set(addItem(2305, "Engine Cylinder (Titanium)"      , ""),new OreDictItemData(MT.Ti           ,2*U+U2));
        ItemList.EngineCylinder7 .set(addItem(2306, "Engine Cylinder (Tungsten Steel)", ""),new OreDictItemData(MT.TungstenSteel,2*U+U2));
        ItemList.EngineCylinder8 .set(addItem(2307, "Engine Cylinder (Iridium)"       , ""),new OreDictItemData(MT.Ir           ,2*U+U2));
        ItemList.EngineCylinderCr.set(addItem(2308, "Engine Cylinder (Chromium)"      , ""),new OreDictItemData(MT.Cr           ,2*U+U2));

        ItemList.EngineTurbo1 .set(addItem(2400, "Engine Turbo (Bronze)"        , ""),new OreDictItemData(MT.Bronze       ,6*U+U4));
        ItemList.EngineTurbo2 .set(addItem(2401, "Engine Turbo (Arsenic Copper)", ""),new OreDictItemData(MT.ArsenicCopper,6*U+U4));
        ItemList.EngineTurbo3 .set(addItem(2402, "Engine Turbo (Arsenic Bronze)", ""),new OreDictItemData(MT.ArsenicBronze,6*U+U4));
        ItemList.EngineTurbo4 .set(addItem(2403, "Engine Turbo (Steel)"         , ""),new OreDictItemData(MT.Steel        ,6*U+U4));
        ItemList.EngineTurbo5 .set(addItem(2404, "Engine Turbo (Invar)"         , ""),new OreDictItemData(MT.Invar        ,6*U+U4));
        ItemList.EngineTurbo6 .set(addItem(2405, "Engine Turbo (Titanium)"      , ""),new OreDictItemData(MT.Ti           ,6*U+U4));
        ItemList.EngineTurbo7 .set(addItem(2406, "Engine Turbo (Tungsten Steel)", ""),new OreDictItemData(MT.TungstenSteel,6*U+U4));
        ItemList.EngineTurbo8 .set(addItem(2407, "Engine Turbo (Iridium)"       , ""),new OreDictItemData(MT.Ir           ,6*U+U4));
        ItemList.EngineTurboCr.set(addItem(2408, "Engine Turbo (Chromium)"      , ""),new OreDictItemData(MT.Cr           ,6*U+U4));

        ItemList.VibrateDetector.set(addItem(2500, "Vibrate Detector"       , ""),new OreDictItemData(MT.StainlessSteel,U+U9));

    }
}
