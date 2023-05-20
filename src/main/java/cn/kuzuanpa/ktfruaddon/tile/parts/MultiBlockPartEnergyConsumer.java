/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.parts;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import java.util.List;

public class MultiBlockPartEnergyConsumer extends CommonMachinePart {

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.machine.part.electric";
    }
    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
    }
    @Override
    public boolean allowRightclick(Entity aEntity) {
        return false;
    }

}
