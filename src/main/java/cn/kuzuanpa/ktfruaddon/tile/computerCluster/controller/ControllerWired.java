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
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

package cn.kuzuanpa.ktfruaddon.tile.computerCluster.controller;

import cn.kuzuanpa.ktfruaddon.api.code.WorldPos;
import cn.kuzuanpa.ktfruaddon.tile.computerCluster.IWiredNetworkConnectable;
import cn.kuzuanpa.ktfruaddon.tile.computerCluster.NetworkCable;
import gregapi.code.HashSetNoNulls;
import net.minecraft.tileentity.TileEntity;

import java.util.UUID;

import static gregapi.data.CS.ALL_SIDES_VALID;

public class ControllerWired extends ControllerBase implements IWiredNetworkConnectable {
    /**@return whether the given position is a direct neighbour or sits on the same cable network.**/
    public boolean canReachPos(WorldPos target) {
        if (target == null) return false;
        Boolean cached = getCachedReach(target);
        if (cached != null) return cached;
        return putCachedReach(target, walkToPos(target));
    }

    private boolean walkToPos(WorldPos target) {
        for (byte tSide : ALL_SIDES_VALID) {
            TileEntity tDelegator = getTileEntityAtSideAndDistance(tSide, 1);
            if (tDelegator == null) continue;
            if (target.equals(new WorldPos(tDelegator.xCoord, tDelegator.yCoord, tDelegator.zCoord,tDelegator.getWorldObj().provider.dimensionId))) return true;
        }
        for (byte tSide : ALL_SIDES_VALID) {
            TileEntity tDelegator = getTileEntityAtSideAndDistance(tSide, 1);
            if (tDelegator instanceof NetworkCable && ((NetworkCable) tDelegator).canReach(target, new HashSetNoNulls<>())) {
                ((NetworkCable) tDelegator).takeChannel(myUUID);
                return true;
            }
        }
        return false;
    }
    public String getTileEntityName() {
        return "ktfru.multitileentity.computecluster.controller.wired";
    }

    @Override
    public void fillChannel() {

    }

    @Override
    public void checkChannel() {

    }

    @Override
    public void takeChannel(UUID controllerUUID) {
        //A controller is an endpoint of the network, it does not forward channels.
    }
}
