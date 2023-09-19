/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.item;

import cn.kuzuanpa.ktfruaddon.item.items.*;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class itemPreInit {
    public static void init(FMLPreInitializationEvent aEvent){
        new itemParticle();
        new itemIT();
        new itemComputer();
        new itemBatteryPole();
        new itemCompact();
        new fakeItemGuiI18n();
        new itemChemistry();
    }
}
