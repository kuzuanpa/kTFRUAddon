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
import gregapi.tileentity.connectors.MultiTileEntityWireElectric;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.util.WD;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import static gregapi.data.CS.ALL_SIDES_VALID;

public class ControllerElectric extends ControllerBase implements ITileEntityEnergy {
    /**Hard cap on the wire walk, an unbounded search over a big power grid would stall the server tick.**/
    public static final int MAX_WIRE_WALK_STEPS = 4096;
    public boolean canReachPos(WorldPos target){
        if(target == null)return false;
        Boolean cached = getCachedReach(target);
        if (cached != null) return cached;
        return putCachedReach(target, walkToPos(target));
    }

    private boolean walkToPos(WorldPos target){
        for (byte tSide : ALL_SIDES_VALID) {
            TileEntity t = getTileEntityAtSideAndDistance(tSide, 1);
            if(t == null)continue;
            if(target.equals(new WorldPos(t.xCoord,t.yCoord,t.zCoord, t.getWorldObj().provider.dimensionId)))return true;
            if(!(t instanceof MultiTileEntityWireElectric))continue;
            if(walkElectricWire(new WorldPos(t.xCoord,t.yCoord,t.zCoord, t.getWorldObj().provider.dimensionId), target))return true;
        }
        return false;
    }

    public boolean walkElectricWire(WorldPos startPos, WorldPos targetPos){
        TileEntity start = WD.te(getWorld(), startPos.toChunkCoord(),false);
        if(!(start instanceof MultiTileEntityWireElectric))return false;

        Queue<MultiTileEntityWireElectric> queue = new ArrayDeque<>();
        Set<WorldPos> visitedPos = new HashSet<>();
        visitedPos.add(startPos);
        queue.add((MultiTileEntityWireElectric) start);
        int steps = 0;
        while(!queue.isEmpty()){
            if(++steps > MAX_WIRE_WALK_STEPS)return false;
            MultiTileEntityWireElectric tile = queue.poll();
            for (byte tSide : ALL_SIDES_VALID) {
                TileEntity t = tile.getTileEntityAtSideAndDistance(tSide, 1);
                if(t == null)continue;
                WorldPos pos = new WorldPos(t.xCoord,t.yCoord,t.zCoord, t.getWorldObj().provider.dimensionId);
                if(pos.equals(targetPos))return true;
                if(t instanceof MultiTileEntityWireElectric && visitedPos.add(pos))queue.add((MultiTileEntityWireElectric) t);
            }
        }
        return false;
    }
    public String getTileEntityName() {
        return "ktfru.multitileentity.computecluster.controller.electric";
    }

}
