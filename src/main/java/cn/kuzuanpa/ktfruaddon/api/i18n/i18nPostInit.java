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



package cn.kuzuanpa.ktfruaddon.api.i18n;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.kUII18n;
import cn.kuzuanpa.ktfruaddon.item.itemPreInit;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import gregapi.data.LH;

public class i18nPostInit {
    public i18nPostInit(FMLPostInitializationEvent aEvent){
        LH.add(I18nHandler.SIDE_BACK,"back");
        LH.add(I18nHandler.SIDE_FRONT,"front");
        LH.add(I18nHandler.SIDE_TOP,"top");
        LH.add(I18nHandler.SIDE_BOTTOM,"bottom");
        LH.add(I18nHandler.SIDE_LEFT,"left");
        LH.add(I18nHandler.SIDE_RIGHT,"right");
        LH.add(I18nHandler.AUTO,"(auto)");
        LH.add(I18nHandler.HAS_PROJECTOR_STRUCTURE,"See Structure in Projector.");
        LH.add(I18nHandler.ALLOW_PART_SHARE,"Allow Part Share.");
        LH.add(I18nHandler.USE_MONKEY_WRENCH_CHANGE_STRUCTURE,"Use Monkey Wrench to Change Contents.");
        LH.add(I18nHandler.TANK_GAS_COMPRESSED_INPUTER,"Works with Compressed Gas Tank, Speed: (Input KU)*10 L/t.");
        LH.add(I18nHandler.TURBINE_UNCHECKED,"Unchecked, may break when spinning amd cause explode");
        LH.add(I18nHandler.TURBINE_DAMAGED,"Already Damaged, you can try to recycle it.");
        LH.add(I18nHandler.TURBINE_DURABILITY,"Turbine Durability: ");
        LH.add(I18nHandler.TURBINE_POWERRATE,"Turbine Power Rate: ");
        LH.add(I18nHandler.FLYWHEEL_STORAGE,"Can Store:");
        LH.add(I18nHandler.FLYWHEEL_MaxRPM,"Max RPM:");
        LH.add(I18nHandler.ORE_SCANNER_REQUIRE_PIPES,"Require Pipe");
        LH.add(I18nHandler.FUEL_BATTERY_0,"Electrolyte Required: %sL");
        LH.add(I18nHandler.TFC_WATERMILL_0,"You touched it slightly, felt the power of water is: ");
        LH.add(I18nHandler.TFC_WATERMILL_1,"You touched it slightly but it didn't move. some wood is broken.");
        LH.add(I18nHandler.TFC_WATERMILL_2,"It seems healthy and don't need more repair.");
        LH.add(I18nHandler.TFC_WATERMILL_3,"You successfully repaired that.");

        LH.add(I18nHandler.HAS_USB_IO_CLICK,"Click with USB stick to write or read Data.");
        LH.add(I18nHandler.REQUIRE_MANA_BURST,"Require Mana Burst To Work!");
        LH.add(I18nHandler.DONE_CHANGING_STRUCTURE,"Changing Contents");
        LH.add(I18nHandler.CHANGING_STRUCTURE,"Done Changing Contents");
        LH.add(I18nHandler.INPUT,"Input");
        LH.add(I18nHandler.OUTPUT,"Output");
        LH.add(I18nHandler.INVENTORY,"Inventory");
        LH.add(I18nHandler.SLOT,"Slot");
        LH.add(I18nHandler.TANK,"Tank");
        LH.add(I18nHandler.NULL,"Null");
        LH.add(I18nHandler.EMPTY,"Empty");
        LH.add(I18nHandler.NORMAL,"Normal");
        LH.add(I18nHandler.ERROR,"error");
        LH.add(I18nHandler.STORED_ENERGY,"Stored Energy");
        LH.add(I18nHandler.CAPACITY,"Capacity");
        LH.add(I18nHandler.OUTPUTTING,"Outputting");
        LH.add(I18nHandler.DATA_WRITE_TO_USB,"The Position of this block wrote to USB stick.");
        LH.add(I18nHandler.USB_ALREAY_HAVE_DATA,"There already have some data in USB stick, click again to overwrite it.");
        LH.add(I18nHandler.CRUCIBLE_MODEL_0,"Still need ");
        LH.add(I18nHandler.CRUCIBLE_MODEL_1," clay ball.");
        LH.add(I18nHandler.SUN_BOILER_MIRROR,"Successfully binding Sun Boiler target:");
        LH.add(I18nHandler.SUN_BOILER_MIRROR_ERR,"Some block above or around the mirror blocked the sunlight");
        LH.add(I18nHandler.SUN_BOILER_ERR,"There are some errors in structure.");
        LH.add(I18nHandler.COMPUTE_CLUSTER_0,"No computer inserted.");
        LH.add(I18nHandler.COMPUTE_CLUSTER_1,"Compute Cluster State: ");
        LH.add(I18nHandler.COMPUTE_CLUSTER_2,"Total Compute Power: ");
        LH.add(I18nHandler.COMPUTE_CLUSTER_3,"Power off");
        LH.add(I18nHandler.FILTERING_PROPERTIES,"Filtering Property:");
        LH.add(I18nHandler.FILTER_PROPERTIES_ALL,"All Properties: ");
        LH.add(I18nHandler.OVERCLOCKING,"Overclocking:");

        LH.add(I18nHandler.COMPUTE_POWER, "Compute Power");
        LH.add(I18nHandler.COMPUTE_POWER_NORMAL, "Normal");
        LH.add(I18nHandler.COMPUTE_POWER_BIOLOGY, "Biology");
        LH.add(I18nHandler.COMPUTE_POWER_QUANTUM, "Quantum");
        LH.add(I18nHandler.COMPUTE_POWER_TIMESPACE, "TimeSpace");
        LH.add(I18nHandler.COMPUTE_POWER_DESC,"%2$s U/t %1$s");
        LH.add(I18nHandler.COMPUTE_TILE_EMPTY,"Don't have any Computer");
        LH.add(I18nHandler.COMPUTE_TILE_COMPUTERS,"Computers: ");
        LH.add(I18nHandler.COMPUTE_TILE_SHIFT_SHOW_COMPUTERS,"Hold [LSHIFT] to show computers");

        LH.add(kUII18n.FUSION_TOKAMAK_STATE_STOPPED,"Stopped");
        LH.add(kUII18n.FUSION_TOKAMAK_STATE_CHARGING,"Charging");
        LH.add(kUII18n.FUSION_TOKAMAK_STATE_RUNNING,"Running");
        LH.add(kUII18n.FUSION_TOKAMAK_STATE_ERROR,"ERROR");
        LH.add(kUII18n.FUSION_TOKAMAK_STATE_VOIDCHARGE,"Void Charging");



        LH.add(kUII18n.TYPE ,"Type");
        LH.add(kUII18n.COMPUTECLUSTER_CLIENT              ,"Client");
        LH.add(kUII18n.COMPUTECLUSTER_CLUSTER             ,"Cluster");
        LH.add(kUII18n.COMPUTECLUSTER_CONTROLLER          ,"Controller");
        LH.add(kUII18n.COMPUTECLUSTER_CLUSTER_STATE_OFFLINE           ,"Offline");
        LH.add(kUII18n.COMPUTECLUSTER_CLUSTER_STATE_NORMAL            ,"§2Normal" );
        LH.add(kUII18n.COMPUTECLUSTER_CLUSTER_STATE_WARNING           ,"§eWarning");
        LH.add(kUII18n.COMPUTECLUSTER_CLUSTER_STATE_ERROR             ,"§4ERROR"  );
        LH.add(kUII18n.COMPUTECLUSTER_CLUSTER_OVERVIEW                ,"Cluster View");
        LH.add(kUII18n.COMPUTECLUSTER_CONTROLLER_STATE_OFFLINE        ,"Offline");
        LH.add(kUII18n.COMPUTECLUSTER_CONTROLLER_STATE_NORMAL         ,"§2Normal" );
        LH.add(kUII18n.COMPUTECLUSTER_CONTROLLER_STATE_WARNING        ,"§eWarning");
        LH.add(kUII18n.COMPUTECLUSTER_CONTROLLER_STATE_ERROR          ,"§4ERROR"  );
        LH.add(kUII18n.COMPUTECLUSTER_CONTROLLER_STATE_ERR_BELONG     ,"§cBelong Error");
        LH.add(kUII18n.COMPUTECLUSTER_CONTROLLER_INFO                 ,"Info");
        LH.add(kUII18n.COMPUTECLUSTER_CONTROLLER_PROVIDING            ,"Providing");
        LH.add(kUII18n.COMPUTECLUSTER_CONTROLLER_LIST                 ,"Controllers");
        LH.add(kUII18n.COMPUTECLUSTER_CLIENT_LIST                     ,"Clients");
        LH.add(kUII18n.COMPUTECLUSTER_RECENT_EVENTS                   ,"Events");


        LH.add(kUII18n.COMPUTE_POWER                   ,"Computer Power");
        LH.add(kUII18n.COMPUTE_POWER_LOGIC             ,"Logic");
        LH.add(kUII18n.COMPUTE_POWER_BIOLOGY           ,"Biology");
        LH.add(kUII18n.COMPUTE_POWER_QUANTUM           ,"Quantum");
        LH.add(kUII18n.COMPUTE_POWER_SPACETIME         ,"Spacetime");



        LH.add(kUII18n.RESEARCH_VIEWER_SELECTED_EMPTY ,"Select to view details");
        LH.add(kUII18n.RESEARCH_VIEWER_SELECTED       ,"Selected Project");
        LH.add(kUII18n.RESEARCH_VIEWER_CURRENT        ,"Current Project");
        LH.add(kUII18n.RESEARCH_VIEWER_CLICK_VIEW     ,"Click to view details");
        LH.add(kUII18n.RESEARCH_VIEWER_CLICK_RESEARCH ,"Click again to research");
        LH.add(kUII18n.RESEARCH_VIEWER_CLICK_LOCKED   ,"Complete others first");

        LH.add(kUII18n.RESEARCH_TABLE_FILL_TITLE ,"Fill this circuit! You will get Space point: ");
        LH.add(kUII18n.RESEARCH_TABLE_FILL_WIN   ,"The circuit is completed! You got Space point: ");
        LH.add(kUII18n.RESEARCH_TABLE_FILL_SCORES,"Amount of Space point: ");
        LH.add(kUII18n.RESEARCH_TABLE_FILL_RELOAD,"change another circuit");



        itemPreInit.turbineLargeGas         .addTooltips(LH.Chat.RED+LH.get(I18nHandler.TURBINE_UNCHECKED));
        itemPreInit.turbineLargeGasChecked  .addTooltips(LH.Chat.RED+LH.get(I18nHandler.TURBINE_UNCHECKED));
        itemPreInit.turbineLargeSteamChecked.addTooltips(LH.Chat.WHITE+LH.get(I18nHandler.TURBINE_DAMAGED));
        itemPreInit.turbineLargeSteamDamaged.addTooltips(LH.Chat.WHITE+LH.get(I18nHandler.TURBINE_DAMAGED));
    }
}
