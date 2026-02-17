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



package cn.kuzuanpa.ktfruaddon.api.i18n;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.item.itemPreInit;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import gregapi.data.LH;

public class i18nPostInit {
    public static void init(FMLPostInitializationEvent aEvent){
        itemPreInit.turbineLargeGas         .addTooltips(LH.Chat.RED+LH.get(I18nHandler.TURBINE_UNCHECKED));
        itemPreInit.turbineLargeGasChecked  .addTooltips(LH.Chat.RED+LH.get(I18nHandler.TURBINE_UNCHECKED));
        itemPreInit.turbineLargeSteamChecked.addTooltips(LH.Chat.WHITE+LH.get(I18nHandler.TURBINE_DAMAGED));
        itemPreInit.turbineLargeSteamDamaged.addTooltips(LH.Chat.WHITE+LH.get(I18nHandler.TURBINE_DAMAGED));
    }
}
