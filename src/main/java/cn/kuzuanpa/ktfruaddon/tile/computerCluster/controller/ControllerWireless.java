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

import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterController;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterUser;
import codechicken.lib.vec.BlockCoord;

public class ControllerWireless extends ControllerBase{
    long maxConnectDistance = 512;
    @Override
    public boolean canReachController(IComputerClusterController controller) {
        BlockCoord targetCoord = controller.getPos();
        return getDistanceFrom(targetCoord.x,targetCoord.y,targetCoord.z) < maxConnectDistance;
    }

    @Override
    public boolean canReachUser(IComputerClusterUser user) {
        return true;
    }
    public String getTileEntityName() {
        return "ktfru.multitileentity.computecluster.controller.wireless";
    }

}
