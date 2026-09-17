/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.tile.computerCluster.controller;

import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputePart;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.ComputePartBase;

import java.util.List;

import static cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants.STATE_ERROR;
import static cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants.STATE_NORMAL;

/** A self-contained Biology compute controller driven by local, installed Normal compute clusters. */
public abstract class BiologyComputeController extends ControllerBase {
    private final long requiredNormalCompute;
    private final long biologyCompute;
    private final int requiredControlParts;

    protected BiologyComputeController(long requiredNormalCompute, long biologyCompute, int requiredControlParts) {
        this.requiredNormalCompute = requiredNormalCompute;
        this.biologyCompute = biologyCompute;
        this.requiredControlParts = requiredControlParts;
        mProvidedType = ComputePower.Biology;
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

        boolean allStarted = IComputePart.tryStartDemand(nodes, ComputePower.Normal, requiredNormalCompute);
        mPartsStarted = true;
        setControllerState(allStarted ? STATE_NORMAL : STATE_ERROR);
        mProvidedAmount = allStarted ? biologyCompute : 0L;
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
        // Biology capacity is fixed by this controller tier, not by its local Normal hardware.
    }
}
