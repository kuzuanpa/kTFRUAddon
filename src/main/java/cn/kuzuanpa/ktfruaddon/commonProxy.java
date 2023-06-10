/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon;

import cn.kuzuanpa.ktfruaddon.enchant.enchantInit;
import cn.kuzuanpa.ktfruaddon.fluid.fluidPreInit;
import cn.kuzuanpa.ktfruaddon.i18n.i18nInit;
import cn.kuzuanpa.ktfruaddon.item.itemPreInit;
import cn.kuzuanpa.ktfruaddon.material.materialPreInit;
import cn.kuzuanpa.ktfruaddon.material.prefix.prefixPreInit;
import cn.kuzuanpa.ktfruaddon.recipe.recipeInit;
import cn.kuzuanpa.ktfruaddon.tile.tileEntityInit;
import cn.kuzuanpa.ktfruaddon.tile.tileEntityPreInit;
import cpw.mods.fml.common.event.*;
import gregapi.api.Abstract_Proxy;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.PROXY;

public class commonProxy extends Abstract_Proxy {
    public commonProxy() {
    }
    public void registerRenderers() {
    }
    public void preInit(FMLPreInitializationEvent aEvent) {
        new prefixPreInit(aEvent);
        new materialPreInit(aEvent);
        new tileEntityPreInit(aEvent);
        new itemPreInit(aEvent);
        new fluidPreInit(aEvent);
    }



    public void init(FMLInitializationEvent aEvent) {
        new tileEntityInit(aEvent);
        new recipeInit(aEvent);
        new enchantInit();
        PROXY.registerRenderers();

    }

    public void postInit(FMLPostInitializationEvent aEvent) {
        new i18nInit(aEvent);
    }

    public void serverStarting(FMLServerStartingEvent aEvent) {
    }

    public void serverStarted(FMLServerStartedEvent aEvent) {
    }

    public void serverStopping(FMLServerStoppingEvent aEvent) {
    }

    public void serverStopped(FMLServerStoppedEvent aEvent) {
    }

}

