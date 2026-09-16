/*
 * Part of kTFRUAddon. Distributed under the AGPLv3.
 */
package cn.kuzuanpa.ktfruaddon.tile.computerCluster.controller;

import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputePart;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.special.ComputePartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.ComputePartBase;

import java.util.List;

import static cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants.STATE_ERROR;
import static cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants.STATE_NORMAL;

/**
 * A 7x7x7 Quantum compute aggregation center. It accepts local Quantum parts,
 * starts them as one group and exposes their summed capacity as a single
 * Quantum controller to its cluster.
 */
public class QuantumComputeCenterController extends ControllerBase {
    protected static final TileDesc WALL = new TileDesc(GTTileEntityRegistry.gregtech, 18002);
    protected static final TileDesc GLASS = new TileDesc(GTTileEntityRegistry.ktfruaddon, 31056);

    private static final IStringBaseStructure STRUCTURE = new LayerStructure(StructureContext.Axis.Y).layerRule("ABA")
            .fixedLayer('A',
                    "WWWWWWW",
                    "WGGGGGW",
                    "WGGGGGW",
                    "WGGGGGW",
                    "WGGGGGW",
                    "WGGGGGW",
                    "WWWWWWW")
            .fixedLayer('B',
                    "WNNWNNW",
                    "NW   WN",
                    "N     N",
                    "W     W",
                    "N     N",
                    "NW   WN",
                    "WNNWNNW")
            .where('W', new PartPredicate(WALL))
            .where('G', new PartPredicate(GLASS))
            .where('N', new ComputePartPredicate(WALL))
            .setOffset(-3, 0, 0);

    public QuantumComputeCenterController() {
        mProvidedType = ComputePower.Quantum;
    }

    @Override
    public void updateComputeParts() {
        List<IComputePart> nodes = getComputeNodes();
        for (IComputePart node : nodes) {
            if (node instanceof ComputePartBase) ((ComputePartBase) node).updateComputePower();
        }

        if (!mStructureOkay || nodes.isEmpty()) {
            stopComputeParts();
            mProvidedAmount = 0L;
            setControllerState(STATE_ERROR);
            return;
        }

        boolean allStarted = true;
        for (IComputePart node : nodes) {
            if (node.getType() != ComputePower.Quantum || (!node.isActive() && !node.tryStart(node.getComputePower()))) {
                allStarted = false;
            }
        }
        mPartsStarted = true;

        long total = 0L;
        if (allStarted) {
            for (IComputePart node : nodes) {
                if (node.isActive()) total += node.getComputePower();
            }
        }
        mProvidedAmount = total;
        setControllerState(allStarted && total > 0L ? STATE_NORMAL : STATE_ERROR);
    }

    @Override
    public void updateProvidedComputePower() {
        // updateComputeParts() owns the aggregate amount.
    }

    @Override
    public IStringBaseStructure getStructure() {
        return STRUCTURE;
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.quantum.compute.center";
    }
}
