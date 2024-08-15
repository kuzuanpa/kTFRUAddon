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

import cn.kuzuanpa.ktfruaddon.item.items.*;
import cn.kuzuanpa.ktfruaddon.material.prefix.prefixList;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregapi.item.prefixitem.PrefixItem;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_DATA;

public class itemPreInit {
    public static void init(FMLPreInitializationEvent aEvent){
        new itemParticle();
        new itemIT();
        new itemComputer();
        new itemBatteryPole();
        new itemCompact();
        new itemChemistry();
        new itemTechnological();

        new PrefixItem(MOD_DATA,"ktfru.item.meta.flywheel", prefixList.flywheel);
        new PrefixItem(MOD_DATA,"ktfru.item.meta.turbine.blade", prefixList.largeTurbineBlade);
        new PrefixItem(MOD_DATA,"ktfru.item.meta.turbine.gas", prefixList.gasLargeTurbine);
        new PrefixItem(MOD_DATA,"ktfru.item.meta.turbine.steam", prefixList.steamLargeTurbine);
        new PrefixItem(MOD_DATA,"ktfru.item.meta.turbine.gas.checked", prefixList.gasLargeTurbineChecked);
        new PrefixItem(MOD_DATA,"ktfru.item.meta.turbine.steam.checked", prefixList.steamLargeTurbineChecked);
        new PrefixItem(MOD_DATA,"ktfru.item.meta.turbine.gas.damaged", prefixList.gasLargeTurbineDamaged);
        new PrefixItem(MOD_DATA,"ktfru.item.meta.turbine.steam.damaged", prefixList.steamLargeTurbineDamaged);

    }
}
