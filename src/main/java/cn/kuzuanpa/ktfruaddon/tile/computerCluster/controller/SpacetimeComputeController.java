package cn.kuzuanpa.ktfruaddon.tile.computerCluster.controller;

import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputePart;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.ComputePartBase;

import java.util.List;

import static cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants.STATE_ERROR;
import static cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants.STATE_NORMAL;

/**
 * Spacetime arrays turn locally installed Quantum compute parts into fixed
 * Spacetime capacity. They are independent multiblocks, never cluster relays.
 */
public abstract class SpacetimeComputeController extends ControllerBase {
    private final long requiredQuantumCompute;
    private final long spacetimeCompute;
    private final int requiredQuantumParts;

    protected SpacetimeComputeController(long requiredQuantumCompute, long spacetimeCompute, int requiredQuantumParts) {
        this.requiredQuantumCompute = requiredQuantumCompute;
        this.spacetimeCompute = spacetimeCompute;
        this.requiredQuantumParts = requiredQuantumParts;
        mProvidedType = ComputePower.Spacetime;
    }

    @Override
    public void updateComputeParts() {
        List<IComputePart> nodes = getComputeNodes();
        refreshLocalCompute(nodes);
        boolean valid = mStructureOkay && nodes.size() == requiredQuantumParts && hasRequiredQuantumCompute(nodes);
        if (!valid) {
            stopComputeParts();
            mProvidedAmount = 0L;
            setControllerState(STATE_ERROR);
            return;
        }

        boolean allStarted = IComputePart.tryStartDemand(nodes, ComputePower.Quantum, requiredQuantumCompute);
        mPartsStarted = true;
        setControllerState(allStarted ? STATE_NORMAL : STATE_ERROR);
        mProvidedAmount = allStarted ? spacetimeCompute : 0L;
    }

    private void refreshLocalCompute(List<IComputePart> nodes) {
        for (IComputePart node : nodes) {
            if (node instanceof ComputePartBase) ((ComputePartBase) node).updateComputePower();
        }
    }

    private boolean hasRequiredQuantumCompute(List<IComputePart> nodes) {
        long total = 0L;
        for (IComputePart node : nodes) {
            if (node.getType() != ComputePower.Quantum) return false;
            total += node.getComputePower();
        }
        return total >= requiredQuantumCompute;
    }

    @Override
    public void updateProvidedComputePower() {
        // Spacetime capacity is fixed by the validated array tier.
    }
}
