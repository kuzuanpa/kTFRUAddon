package cn.kuzuanpa.ktfruaddon;

import cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.MultiBlockMachineLoader;
import cn.kuzuanpa.ktfruaddon.block.TileEntity.tileEntityInit;
import cn.kuzuanpa.ktfruaddon.block.TileEntity.tileEntityPreInit;
import cn.kuzuanpa.ktfruaddon.material.materialPreInit;
import cpw.mods.fml.common.event.*;
import cn.kuzuanpa.ktfruaddon.item.itemLoader;
import cn.kuzuanpa.ktfruaddon.block.blockLoader;
import gregapi.api.Abstract_Proxy;

public class commonProxy extends Abstract_Proxy {
    public commonProxy() {
    }

    public void preInit(FMLPreInitializationEvent aEvent) {
        new materialPreInit(aEvent);
        new tileEntityPreInit(aEvent);
        new blockLoader(aEvent);
        new itemLoader(aEvent);

    }

    public void init(FMLInitializationEvent aEvent) {

        new tileEntityInit(aEvent);
        new MultiBlockMachineLoader();
    }

    public void postInit(FMLPostInitializationEvent aEvent) {
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

