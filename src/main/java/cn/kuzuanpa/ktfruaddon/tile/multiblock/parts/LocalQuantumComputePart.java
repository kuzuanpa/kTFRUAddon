/*
 * Part of kTFRUAddon. Distributed under the AGPLv3.
 */
package cn.kuzuanpa.ktfruaddon.tile.multiblock.parts;

import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import gregapi.data.LH;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Map;

/**
 * A compact Quantum compute part. It behaves as a normal cluster user:
 * it rents Normal compute from its bound cluster and exposes the registered
 * Quantum capacity to the host multiblock.
 */
public class LocalQuantumComputePart extends WirelessComputePart {
    @Override
    public Map<ComputePower, Long> getComputePowerNeeded() {
        return ComputePower.Normal.asMap(mRequested);
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(mType.prefixedDesc(mProvided));
        aList.add(ComputePower.Normal.prefixedDesc(mRequested));
        aList.add(LH.Chat.DGRAY + LH.get(LH.TOOL_TO_DETAIL_MAGNIFYINGGLASS));
    }
}
