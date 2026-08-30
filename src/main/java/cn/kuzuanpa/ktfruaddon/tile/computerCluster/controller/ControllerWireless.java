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

public class ControllerWireless extends ControllerBase{
    /**In blocks. TileEntity.getDistanceFrom returns the squared distance, so it gets squared before comparing.**/
    public static final int MAX_CONNECT_DISTANCE = 512;

    @Override
    public boolean canReachPos(WorldPos pos) {
        if (pos == null) return false;
        return isInRange(pos);
    }

    protected boolean isInRange(WorldPos target) {
        if (target == null) return false;
        return getDistanceFrom(target.x, target.y, target.z) <= (double) MAX_CONNECT_DISTANCE * MAX_CONNECT_DISTANCE;
    }

    public String getTileEntityName() {
        return "ktfru.multitileentity.computecluster.controller.wireless";
    }

}
