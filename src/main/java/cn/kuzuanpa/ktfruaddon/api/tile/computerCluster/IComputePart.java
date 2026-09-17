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


package cn.kuzuanpa.ktfruaddon.api.tile.computerCluster;

import java.util.List;

public interface IComputePart {
    ComputePower getType();
    /**@return the total capacity of this node, per Compute Power type.**/
    long getComputePower();
    /**Try to hold the requested amounts on this node.*/
    boolean tryStart(long needed);
    /**Node is not used by any host.**/
    void stop();
    /**Node is powering a running host.**/
    boolean isActive();

    /**Whether tryStart(needed) can hold less than the node's full capacity and later adjust that amount.**/
    default boolean isPartiallyAllocatable() {return false;}

    /**
     * Starts the supplied nodes while distributing exactly {@code demand} across them.
     * Fixed-capacity nodes are reserved first so partially allocatable nodes such as wireless
     * providers only rent what local capacity could not cover. All matching nodes are still
     * activated; nodes which receive a zero share must stay active without holding power when possible.
     * @return true when every matching node started and the whole demand was covered.
     */
    static boolean tryStartDemand(List<IComputePart> nodes, ComputePower type, long demand) {
        if (nodes == null || nodes.isEmpty()) return demand <= 0L;
        long remaining = Math.max(0L, demand);
        boolean allStarted = true;

        // Reserve fixed local capacity first. Wireless or other partial providers then cover the remainder.
        for (int pass = 0; pass < 2; pass++) {
            boolean partialPass = pass == 1;
            for (IComputePart node : nodes) {
                if (node == null) continue;
                if (node.getType() != type) {
                    node.stop();
                    continue;
                }
                if (node.isPartiallyAllocatable() != partialPass) continue;

                long capacity = Math.max(0L, node.getComputePower());
                long share = Math.min(capacity, remaining);
                if (partialPass || !node.isActive()) {
                    if (!node.tryStart(share)) allStarted = false;
                    else remaining -= share;
                } else {
                    // Fixed nodes ignore the argument after starting, so only account for their capacity here.
                    remaining -= share;
                }
            }
        }

        return allStarted && remaining <= 0L;
    }
}
