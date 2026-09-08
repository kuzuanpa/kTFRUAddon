/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 */
package cn.kuzuanpa.ktfruaddon.tile.computerCluster.controller;

import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputePart;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.ComputePartBase;

import java.util.List;

import static cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants.STATE_ERROR;
import static cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants.STATE_NORMAL;

/**
 * A quantum computer consumes locally installed Normal compute nodes for classical control,
 * readout, and error correction. The completed multiblock itself provides Quantum compute.
 */
public abstract class QuantumComputeController extends ControllerBase {
    private final long requiredNormalCompute;
    private final long quantumCompute;
    private final int requiredControlParts;

    protected QuantumComputeController(long requiredNormalCompute, long quantumCompute, int requiredControlParts) {
        this.requiredNormalCompute = requiredNormalCompute;
        this.quantumCompute = quantumCompute;
        this.requiredControlParts = requiredControlParts;
        mProvidedType = ComputePower.Quantum;
    }

    @Override
    public void updateComputeParts() {
        List<IComputePart> nodes = getComputeNodes();
        refreshLocalCompute(nodes);
        boolean valid = mStructureOkay && nodes.size() == requiredControlParts && hasRequiredNormalCompute(nodes);

        if (!valid) {
            stopComputeParts();
            mProvidedAmount = 0L;
            setControllerState(STATE_ERROR);
            return;
        }

        boolean allStarted = true;
        for (IComputePart node : nodes) {
            if (!node.isActive() && !node.tryStart(node.getComputePower())) allStarted = false;
        }
        mPartsStarted = true;
        setControllerState(allStarted ? STATE_NORMAL : STATE_ERROR);
        mProvidedAmount = allStarted ? quantumCompute : 0L;
    }

    private void refreshLocalCompute(List<IComputePart> nodes) {
        for (IComputePart node : nodes) {
            if (node instanceof ComputePartBase) ((ComputePartBase) node).updateComputePower();
        }
    }

    private boolean hasRequiredNormalCompute(List<IComputePart> nodes) {
        long total = 0L;
        for (IComputePart node : nodes) {
            if (node.getType() != ComputePower.Normal) return false;
            total += node.getComputePower();
        }
        return total >= requiredNormalCompute;
    }

    @Override
    public void updateProvidedComputePower() {
        // Quantum capacity is a property of the validated multiblock tier.
    }
}
