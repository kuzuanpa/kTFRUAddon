/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon;

import cn.kuzuanpa.ktfruaddon.client.render.*;
import cn.kuzuanpa.ktfruaddon.nei.NeiHiddener;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.model.CNCMachine3;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.model.circuitAssembler;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.model.exampleMachineModel;
import cn.kuzuanpa.ktfruaddon.tile.parts.SunHeaterMirror;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.MinecraftForge;

public class clientProxy extends commonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }


    @SideOnly(Side.CLIENT)
    public void registerRenderers(){
        ClientRegistry.bindTileEntitySpecialRenderer(exampleMachineModel.class, new TESRExampleMultiBlock());
        ClientRegistry.bindTileEntitySpecialRenderer(circuitAssembler.class, new TESRCircuitAssembler());
        ClientRegistry.bindTileEntitySpecialRenderer(SunHeaterMirror.class, new TESRSunBoilerMirror());
        ClientRegistry.bindTileEntitySpecialRenderer(CNCMachine3.class, new TESRCNCMachine3());
        MinecraftForge.EVENT_BUS.register(new FxRenderBlockOutline());
        codechicken.nei.api.API.registerNEIGuiHandler(new NeiHiddener());

    }
}