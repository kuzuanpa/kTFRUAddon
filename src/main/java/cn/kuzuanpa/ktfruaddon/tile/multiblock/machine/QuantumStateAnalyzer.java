/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 */
package cn.kuzuanpa.ktfruaddon.tile.multiblock.machine;

import gregapi.data.LH;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import net.minecraft.item.ItemStack;

import java.util.List;

/** Q3-02. Single-block analyzer that turns raw observations into research control data. */
public class QuantumStateAnalyzer extends MultiTileEntityBasicMachine {
    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.quantumstateanalysis.1"));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.30081";
    }
}
