/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */


package cn.kuzuanpa.ktfruaddon.i18n;

import cn.kuzuanpa.ktfruaddon.i18n.texts.kMessages;
import cn.kuzuanpa.ktfruaddon.i18n.texts.kTooltips;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import gregapi.data.LH;

public class i18nInit {
    public i18nInit(FMLPostInitializationEvent aEvent){
        LH.add(kTooltips.SIDE_BACK,"Back");
        LH.add(kTooltips.SIDE_FRONT,"Front");
        LH.add(kTooltips.SIDE_TOP,"top");
        LH.add(kTooltips.SIDE_BOTTOM,"bottom");
        LH.add(kTooltips.SIDE_LEFT,"left");
        LH.add(kTooltips.SIDE_RIGHT,"right");
        LH.add(kTooltips.AUTO,"(auto)");
        LH.add(kTooltips.FUEL_BATTERY_0,"Use Monkey Wrench to Change Contents.");

        LH.add(kMessages.FUEL_BATTERY_0,"Changing Contents");
        LH.add(kMessages.FUEL_BATTERY_1,"Done Changing Contents");
    }
}
