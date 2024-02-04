/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.nei;

import codechicken.nei.VisiblityData;
import codechicken.nei.api.INEIGuiAdapter;
import net.minecraft.client.gui.inventory.GuiContainer;

public class NeiHiddener extends INEIGuiAdapter {
    public NeiHiddener() {
    }
    public VisiblityData modifyVisiblity(GuiContainer gui, VisiblityData currentVisibility) {
        if (gui instanceof IHiddenNei) currentVisibility.showNEI = false;
        return currentVisibility;
    }
}
