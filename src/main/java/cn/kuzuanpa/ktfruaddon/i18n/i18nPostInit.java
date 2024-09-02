/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */


package cn.kuzuanpa.ktfruaddon.i18n;

import cn.kuzuanpa.ktfruaddon.i18n.texts.kMessages;
import cn.kuzuanpa.ktfruaddon.i18n.texts.kTooltips;
import cn.kuzuanpa.ktfruaddon.i18n.texts.kUserInterface;
import cn.kuzuanpa.ktfruaddon.item.itemPreInit;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import gregapi.data.LH;

public class i18nPostInit {
    public i18nPostInit(FMLPostInitializationEvent aEvent){
        LH.add(kTooltips.SIDE_BACK,"back");
        LH.add(kTooltips.SIDE_FRONT,"front");
        LH.add(kTooltips.SIDE_TOP,"top");
        LH.add(kTooltips.SIDE_BOTTOM,"bottom");
        LH.add(kTooltips.SIDE_LEFT,"left");
        LH.add(kTooltips.SIDE_RIGHT,"right");
        LH.add(kTooltips.AUTO,"(auto)");
        LH.add(kTooltips.HAS_PROJECTOR_STRUCTURE,"See Structure in Projector.");
        LH.add(kTooltips.USE_MONKEY_WRENCH_CHANGE_STRUCTURE,"Use Monkey Wrench to Change Contents.");
        LH.add(kTooltips.TANK_GAS_COMPRESSED_INPUTER,"Works with Compressed Gas Tank, Speed: (Input KU)*10 L/t.");
        LH.add(kTooltips.TURBINE_UNCHECKED,"Unchecked, may break when spinning amd cause explode");
        LH.add(kTooltips.TURBINE_DAMAGED,"Already Damaged, you can try to recycle it.");
        LH.add(kTooltips.TURBINE_DURABILITY,"Turbine Durability: ");
        LH.add(kTooltips.TURBINE_POWERRATE,"Turbine Power Rate: ");
        LH.add(kTooltips.FLYWHEEL_STORAGE,"Can Store:");
        LH.add(kTooltips.FLYWHEEL_MaxRPM,"Max RPM:");

        LH.add(kMessages.DONE_CHANGING_STRUCTURE,"Changing Contents");
        LH.add(kMessages.CHANGING_STRUCTURE,"Done Changing Contents");
        LH.add(kMessages.INPUT,"Input");
        LH.add(kMessages.OUTPUT,"Output");
        LH.add(kMessages.INVENTORY,"Inventory");
        LH.add(kMessages.SLOT,"Slot");
        LH.add(kMessages.TANK,"Tank");
        LH.add(kMessages.NULL,"Null");
        LH.add(kMessages.EMPTY,"Empty");
        LH.add(kMessages.NORMAL,"Normal");
        LH.add(kMessages.ERROR,"error");
        LH.add(kMessages.STORED_ENERGY,"Stored Energy");
        LH.add(kMessages.CAPACITY,"Capacity");
        LH.add(kMessages.OUTPUTTING,"Outputting");
        LH.add(kMessages.CRUCIBLE_MODEL_0,"Still need ");
        LH.add(kMessages.CRUCIBLE_MODEL_1," clay ball.");
        LH.add(kMessages.SUN_BOILER_MIRROR,"Successfully binding Sun Boiler target:");
        LH.add(kMessages.SUN_BOILER_MIRROR_ERR,"Some block above or around the mirror blocked the sunlight");
        LH.add(kMessages.SUN_BOILER_0,"The Position of this block wrote to USB stick.");
        LH.add(kMessages.SUN_BOILER_1,"There already have some data in USB stick, click again to overwrite it.");
        LH.add(kMessages.SUN_BOILER_ERR,"There are some errors in structure.");
        LH.add(kMessages.COMPUTE_CLUSTER_0,"No computer inserted.");
        LH.add(kMessages.COMPUTE_CLUSTER_1,"Compute Cluster State: ");
        LH.add(kMessages.COMPUTE_CLUSTER_2,"Total Compute Power: ");
        LH.add(kMessages.COMPUTE_CLUSTER_3,"Power off");
        LH.add(kMessages.FILTERING_PROPERTIES,"Filtering Property:");
        LH.add(kMessages.FILTER_PROPERTIES_ALL,"All Properties: ");
        LH.add(kMessages.OVERCLOCKING,"Overclocking:");

        LH.add(kUserInterface.FUSION_TOKAMAK_STATE_STOPPED,"Stopped");
        LH.add(kUserInterface.FUSION_TOKAMAK_STATE_CHARGING,"Charging");
        LH.add(kUserInterface.FUSION_TOKAMAK_STATE_RUNNING,"Running");
        LH.add(kUserInterface.FUSION_TOKAMAK_STATE_ERROR,"ERROR");
        LH.add(kUserInterface.FUSION_TOKAMAK_STATE_VOIDCHARGE,"Void Charging");


        itemPreInit.turbineLargeGas         .addTooltips(LH.Chat.RED+LH.get(kTooltips.TURBINE_UNCHECKED));
        itemPreInit.turbineLargeGasChecked  .addTooltips(LH.Chat.RED+LH.get(kTooltips.TURBINE_UNCHECKED));
        itemPreInit.turbineLargeSteamChecked.addTooltips(LH.Chat.WHITE+LH.get(kTooltips.TURBINE_DAMAGED));
        itemPreInit.turbineLargeSteamDamaged.addTooltips(LH.Chat.WHITE+LH.get(kTooltips.TURBINE_DAMAGED));
    }
}
