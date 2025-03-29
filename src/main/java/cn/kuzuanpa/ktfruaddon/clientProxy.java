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

package cn.kuzuanpa.ktfruaddon;

import cn.kuzuanpa.ktfruaddon.api.client.fx.FxRenderBlockOutline;
import cn.kuzuanpa.ktfruaddon.client.kTFRUAddonARProjectorRegister;
import cn.kuzuanpa.ktfruaddon.client.render.*;
import cn.kuzuanpa.ktfruaddon.api.nei.NeiHiddener;
import cn.kuzuanpa.ktfruaddon.tile.energy.generator.WaterMill;
import cn.kuzuanpa.ktfruaddon.tile.machine.TFCPresser;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.storage.LiquidBattery;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.specialRend.CNCMachine3;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.specialRend.DummyCrucible;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.specialRend.circuitAssembler;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.specialRend.exampleMachineModel;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.SunHeaterMirror;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.MinecraftForge;

import static cn.kuzuanpa.ktfruaddon.EnvironmentHelper.isAdvancedRocketryTFRU;

public class clientProxy extends commonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
        if(isAdvancedRocketryTFRU)try{
            zmaster587.libVulpes.LibVulpes.addDummyMultiBlockRegisterer(new kTFRUAddonARProjectorRegister());
        }catch (Exception ignored){}
    }

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }


    @SideOnly(Side.CLIENT)
    public void registerRenderers(){
        ClientRegistry.bindTileEntitySpecialRenderer(exampleMachineModel.class, new TESRExampleMultiBlock());
        ClientRegistry.bindTileEntitySpecialRenderer(circuitAssembler.class, new TESRCircuitAssembler());
        ClientRegistry.bindTileEntitySpecialRenderer(SunHeaterMirror.class, new TESRSunBoilerMirror());
        ClientRegistry.bindTileEntitySpecialRenderer(WaterMill.class, new TESRWaterMill());
        ClientRegistry.bindTileEntitySpecialRenderer(TFCPresser.class, new TESRTFCPresser());
        ClientRegistry.bindTileEntitySpecialRenderer(CNCMachine3.class, new TESRCNCMachine3());
        ClientRegistry.bindTileEntitySpecialRenderer(LiquidBattery.class, new TESRLiquidBattery());
        ClientRegistry.bindTileEntitySpecialRenderer(DummyCrucible.class, new TESRDumyCrucible());

        MinecraftForge.EVENT_BUS.register(new FxRenderBlockOutline());
        codechicken.nei.api.API.registerNEIGuiHandler(new NeiHiddener());
    }
}