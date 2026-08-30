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

package cn.kuzuanpa.ktfruaddon.api.tile.computerCluster;

import cn.kuzuanpa.ktfruaddon.api.code.WorldPos;

public interface IReachabilityLimitedController extends IComputerClusterController{
    boolean canReachPos(WorldPos pos);

    @Override
    default boolean allocateUserComputePower(IComputerClusterUser user) {
        if(!canReachPos(user.getUserPos()))return false;
        return IComputerClusterController.super.allocateUserComputePower(user);
    }

    static boolean isUserReachable(IComputerClusterController controller, IComputerClusterUser user){
        if (!(controller instanceof IReachabilityLimitedController))return true;
        return ((IReachabilityLimitedController) controller).canReachPos(user.getUserPos());
    }
}
