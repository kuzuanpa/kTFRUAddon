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

import cn.kuzuanpa.ktfruaddon.item.items.itemFlywheel;
import cn.kuzuanpa.ktfruaddon.item.items.itemPrefixWithTooltip;
import cn.kuzuanpa.ktfruaddon.item.items.itemTurbine;
import cn.kuzuanpa.ktfruaddon.item.items.random.*;
import cn.kuzuanpa.ktfruaddon.material.prefix.prefixList;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregapi.item.prefixitem.PrefixItem;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_DATA;

public class itemPreInit {
   public static itemPrefixWithTooltip flywheel, turbineLargeGas, turbineLargeGasChecked, turbineLargeGasDamaged, turbineLargeSteam, turbineLargeSteamChecked, turbineLargeSteamDamaged;
    public static void init(FMLPreInitializationEvent aEvent){
        new itemParticle();
        new itemIT();
        new itemComputer();
        new itemBatteryPole();
        new itemCompact();
        new itemChemistry();
        new itemCover();

        new PrefixItem(MOD_DATA,"ktfru.item.meta.turbine.blade", prefixList.largeTurbineBlade);
        
        flywheel                 = new itemFlywheel(MOD_DATA,"ktfru.item.meta.flywheel", prefixList.flywheel);
        turbineLargeGas          = new itemTurbine(MOD_DATA,"ktfru.item.meta.turbine.gas", prefixList.turbineLargeGas);
        turbineLargeSteam        = new itemTurbine(MOD_DATA,"ktfru.item.meta.turbine.steam.checked", prefixList.turbineLargeSteamChecked);
        turbineLargeSteamChecked = new itemPrefixWithTooltip(MOD_DATA,"ktfru.item.meta.turbine.gas.damaged", prefixList.turbineLargeGasDamaged);
        turbineLargeGasChecked   = new itemTurbine(MOD_DATA,"ktfru.item.meta.turbine.steam", prefixList.turbineLargeSteam);
        turbineLargeGasDamaged   = new itemTurbine(MOD_DATA,"ktfru.item.meta.turbine.gas.checked", prefixList.turbineLargeGasChecked);
        turbineLargeSteamDamaged = new itemPrefixWithTooltip(MOD_DATA,"ktfru.item.meta.turbine.steam.damaged", prefixList.turbineLargeSteamDamaged);

    }
}
