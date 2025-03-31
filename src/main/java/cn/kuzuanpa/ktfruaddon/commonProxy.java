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

import cn.kuzuanpa.ktfruaddon.api.fluid.fluidPreInit;
import cn.kuzuanpa.ktfruaddon.api.i18n.i18nPostInit;
import cn.kuzuanpa.ktfruaddon.api.material.materialPreInit;
import cn.kuzuanpa.ktfruaddon.api.network.PacketContainerButtonPressed;
import cn.kuzuanpa.ktfruaddon.api.network.PacketFxBlockOutline;
import cn.kuzuanpa.ktfruaddon.api.network.PacketSyncDataByteArrayLong;
import cn.kuzuanpa.ktfruaddon.api.network.PacketSyncDataByteArrayLongAndIDs;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.item.ItemPostInit;
import cn.kuzuanpa.ktfruaddon.item.itemPreInit;
import cn.kuzuanpa.ktfruaddon.loot.lootPostInit;
import cn.kuzuanpa.ktfruaddon.recipe.recipeInit;
import cn.kuzuanpa.ktfruaddon.tile.tileEntityInit0;
import cn.kuzuanpa.ktfruaddon.tile.tileEntityPreInit;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import gregapi.api.Abstract_Proxy;
import gregapi.network.NetworkHandler;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.common.MinecraftForge;

import static cn.kuzuanpa.ktfruaddon.EnvironmentHelper.updateTFRUEnvironment;
import static cn.kuzuanpa.ktfruaddon.ktfruaddon.*;

public class commonProxy extends Abstract_Proxy {
    public commonProxy() {
    }
    public void registerRenderers() {
    }
    public void preInit(FMLPreInitializationEvent aEvent) {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        updateTFRUEnvironment(aEvent);
        kNetworkHandler = new NetworkHandler(MOD_ID, "kAdd", new PacketFxBlockOutline(), new PacketContainerButtonPressed()
                , new PacketSyncDataByteArrayLong( 0), new PacketSyncDataByteArrayLong( 1), new PacketSyncDataByteArrayLong( 2), new PacketSyncDataByteArrayLong( 3), new PacketSyncDataByteArrayLong( 4), new PacketSyncDataByteArrayLong( 5), new PacketSyncDataByteArrayLong( 6), new PacketSyncDataByteArrayLong( 7)
                , new PacketSyncDataByteArrayLongAndIDs( 0), new PacketSyncDataByteArrayLongAndIDs( 1), new PacketSyncDataByteArrayLongAndIDs( 2), new PacketSyncDataByteArrayLongAndIDs( 3), new PacketSyncDataByteArrayLongAndIDs( 4), new PacketSyncDataByteArrayLongAndIDs( 5), new PacketSyncDataByteArrayLongAndIDs( 6), new PacketSyncDataByteArrayLongAndIDs( 7)
        );
        kNetworkHandler2 = new NetworkHandler(MOD_ID, "kAd2", new PacketFxBlockOutline(), new PacketContainerButtonPressed()
                , new PacketSyncDataByteArrayLong( 0), new PacketSyncDataByteArrayLong( 1), new PacketSyncDataByteArrayLong( 2), new PacketSyncDataByteArrayLong( 3), new PacketSyncDataByteArrayLong( 4), new PacketSyncDataByteArrayLong( 5), new PacketSyncDataByteArrayLong( 6), new PacketSyncDataByteArrayLong( 7)
                , new PacketSyncDataByteArrayLongAndIDs( 0), new PacketSyncDataByteArrayLongAndIDs( 1), new PacketSyncDataByteArrayLongAndIDs( 2), new PacketSyncDataByteArrayLongAndIDs( 3), new PacketSyncDataByteArrayLongAndIDs( 4), new PacketSyncDataByteArrayLongAndIDs( 5), new PacketSyncDataByteArrayLongAndIDs( 6), new PacketSyncDataByteArrayLongAndIDs( 7)
        );
      //  new prefixPreInit(aEvent);
        materialPreInit.init(aEvent);
        tileEntityPreInit.init(aEvent);
        itemPreInit.init(aEvent);
        fluidPreInit.init(aEvent);
    }



    public void init(FMLInitializationEvent aEvent) {
        tileEntityInit0.init(aEvent);
        PROXY.registerRenderers();
    }

    public void postInit(FMLPostInitializationEvent aEvent) {
        new i18nPostInit(aEvent);
        recipeInit.init(aEvent);
        lootPostInit.init(aEvent);
        ItemPostInit.init(aEvent);

    }

    public void serverStarting(FMLServerStartingEvent aEvent) {
    }

    public void serverStarted(FMLServerStartedEvent aEvent) {
        GTTileEntityRegistry.update();
    }

    public void serverStopping(FMLServerStoppingEvent aEvent) {
    }

    public void serverStopped(FMLServerStoppedEvent aEvent) {
    }
    @SubscribeEvent
    public void sendMessage(PlayerEvent.PlayerLoggedInEvent e){
        if(!EnvironmentHelper.TFRUVer.equalsIgnoreCase(EnvironmentHelper.checkedTFRUVer)) e.player.addChatComponentMessage(new ChatComponentTranslation("ktfru.msg.outdated"));
    }
}

