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
import cn.kuzuanpa.ktfruaddon.api.nei.NeiHiddener;
import cn.kuzuanpa.ktfruaddon.api.network.PacketUUIDAssignedData;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchTree;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputerCluster;
import cn.kuzuanpa.ktfruaddon.client.gui.research.ContainerClientResearchTreeMonitor;
import cn.kuzuanpa.ktfruaddon.client.kTFRUAddonARProjectorCompact;
import cn.kuzuanpa.ktfruaddon.client.render.*;
import cn.kuzuanpa.ktfruaddon.tile.energy.generator.WaterMill;
import cn.kuzuanpa.ktfruaddon.tile.machine.TFCPresser;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.generator.SunHeaterMirrorLarge;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.storage.LiquidBattery;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.machine.CNCMachine3;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.machine.ElectromagnetCrucible;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.machine.MaskAlignerUVPlus;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.miner.AsteroidMiner;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.DummyCrucibleScreen;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.SunHeaterMirror;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregapi.data.LH;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.common.MinecraftForge;

import java.util.UUID;

import static cn.kuzuanpa.ktfruaddon.EnvironmentHelper.isAdvancedRocketryTFRU;

public class clientProxy extends commonProxy {
    ResearchTree researchTree = null;
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        PacketUUIDAssignedData.typeMap.put((byte) 2, clientProxy::openResearchTreeGUI);
    }

    /**Opens the portable research tree GUI for the tree with the given UUID, syncing its data first when the client has not cached it yet. Triggered by the /kTFRUWireless research command server side.**/
    @SideOnly(Side.CLIENT)
    public static void openResearchTreeGUI(UUID treeUUID, byte[] ignored) {
        if (Minecraft.getMinecraft().thePlayer == null) return;
        ResearchTree tree = ResearchTree.allTreeUUIDsClient.get(treeUUID);
        if (tree == null) {
            ResearchTree.sendGetTreeDataPacket(Minecraft.getMinecraft().thePlayer.getCommandSenderName(), treeUUID, (byte) 2);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(LH.get("ktfru.wireless.cli.research.syncing")));
            return;
        }
        ResearchTree.sendGetTreeDataPacket(Minecraft.getMinecraft().thePlayer.getCommandSenderName(), treeUUID, (byte) 3);
        FMLCommonHandler.instance().showGuiScreen(new ContainerClientResearchTreeMonitor(tree));
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
        if(isAdvancedRocketryTFRU)try{
            zmaster587.libVulpes.LibVulpes.addDummyMultiBlockRegisterer(new kTFRUAddonARProjectorCompact());
        }catch (Exception ignored){}
    }

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    /**The client side cluster snapshots are static, they have to go when leaving a world or server.**/
    @SubscribeEvent
    public void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        ComputerCluster.clearClientData();
    }


    @SideOnly(Side.CLIENT)
    public void registerRenderers(){
        ClientRegistry.bindTileEntitySpecialRenderer(SunHeaterMirror.class, new TESRSunBoilerMirror());
        ClientRegistry.bindTileEntitySpecialRenderer(SunHeaterMirrorLarge.class, new TESRSunBoilerMirrorLarge());
        ClientRegistry.bindTileEntitySpecialRenderer(WaterMill.class, new TESRWaterMill());
        ClientRegistry.bindTileEntitySpecialRenderer(TFCPresser.class, new TESRTFCPresser());
        ClientRegistry.bindTileEntitySpecialRenderer(CNCMachine3.class, new TESRCNCMachine3());
        ClientRegistry.bindTileEntitySpecialRenderer(MaskAlignerUVPlus.class, new TESRMaskAlignerUVP());
        ClientRegistry.bindTileEntitySpecialRenderer(LiquidBattery.class, new TESRLiquidBattery());
        ClientRegistry.bindTileEntitySpecialRenderer(ElectromagnetCrucible.class, new TESRElectromagnetCrucible());
        ClientRegistry.bindTileEntitySpecialRenderer(AsteroidMiner.class, new TESRAsteroidMiner());
        ClientRegistry.bindTileEntitySpecialRenderer(DummyCrucibleScreen.class, new TESRDummyCrucibleScreen());

        MinecraftForge.EVENT_BUS.register(new FxRenderBlockOutline());
        codechicken.nei.api.API.registerNEIGuiHandler(new NeiHiddener());
    }

    @Override
    public void sendMessage(PlayerEvent.PlayerLoggedInEvent e) {
        if(!EnvironmentHelper.TFRUVer.equalsIgnoreCase(EnvironmentHelper.checkedTFRUVer))EnvironmentHelper.changelog.forEach(s->e.player.addChatComponentMessage(new ChatComponentText(s)));
        e.player.addChatComponentMessage(new ChatComponentTranslation("ktfru.msg.join.0"));
        e.player.addChatComponentMessage(new ChatComponentText(LH.get("ktfru.msg.join.1")+EnvironmentHelper.TFRUVer));
        e.player.addChatComponentMessage(new ChatComponentTranslation("ktfru.msg.join.2"));
        e.player.addChatComponentMessage(new ChatComponentTranslation("ktfru.msg.join.3"));
        e.player.addChatComponentMessage(new ChatComponentTranslation("ktfru.msg.join.4"));
        e.player.addChatComponentMessage(new ChatComponentTranslation("ktfru.msg.join.5"));
        if(EnvironmentHelper.checkedTFRUVer == null)e.player.addChatComponentMessage(new ChatComponentTranslation("ktfru.msg.join.6.check_fail"));
        else if(!EnvironmentHelper.TFRUVer.equalsIgnoreCase(EnvironmentHelper.checkedTFRUVer))e.player.addChatComponentMessage(new ChatComponentText(LH.get("ktfru.msg.join.6.outdated")+ EnvironmentHelper.checkedTFRUVer+LH.get("ktfru.msg.join.6.outdated.suffix")));
        else e.player.addChatComponentMessage(new ChatComponentTranslation("ktfru.msg.join.6"));
        e.player.addChatComponentMessage(new ChatComponentTranslation("ktfru.msg.join.7"));
        e.player.addChatComponentMessage(new ChatComponentTranslation("ktfru.msg.join.8"));
    }
}
