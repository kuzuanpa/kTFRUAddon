/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.item;

import cn.kuzuanpa.ktfruaddon.item.items.itemIT;
import cn.kuzuanpa.ktfruaddon.item.items.itemParticle;
import cn.kuzuanpa.ktfruaddon.item.items.fakeItemGuiI18n;
import cn.kuzuanpa.ktfruaddon.item.items.itemComputer;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class itemPreInit {
    public itemPreInit(FMLPreInitializationEvent aEvent){
        new itemParticle();
        new itemIT();
        new itemComputer();
        new fakeItemGuiI18n();
    }
}
