package cn.kuzuanpa.ktfruaddon.item;

import cn.kuzuanpa.ktfruaddon.item.item.itemIT;
import cn.kuzuanpa.ktfruaddon.item.item.itemParticle;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class itemPreInit {
    public itemPreInit(FMLPreInitializationEvent aEvent){
        new itemParticle();
        new itemIT();
    }
}
