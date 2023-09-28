/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon;

import cn.kuzuanpa.ktfruaddon.client.render.TileEntityRenderCircuitAssembler;
import cn.kuzuanpa.ktfruaddon.client.render.TileEntityRenderExampleMultiBlock;
import cn.kuzuanpa.ktfruaddon.client.render.TileEntityRenderSunBoilerMirror;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.model.circuitAssembler;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.model.exampleMachineModel;
import cn.kuzuanpa.ktfruaddon.tile.parts.SunBoilerMirror;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
        ClientRegistry.bindTileEntitySpecialRenderer(exampleMachineModel.class, new TileEntityRenderExampleMultiBlock());
        ClientRegistry.bindTileEntitySpecialRenderer(circuitAssembler.class, new TileEntityRenderCircuitAssembler());
        ClientRegistry.bindTileEntitySpecialRenderer(SunBoilerMirror.class, new TileEntityRenderSunBoilerMirror());
    }
}