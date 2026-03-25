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

package cn.kuzuanpa.ktfruaddon.DreamPlanner;


import cn.kuzuanpa.ktfruaddon.DreamPlanner.api.NetworkHandler;

public class DreamPlanner {
    public static final NetworkHandler networkHandler = new NetworkHandler();

    public static final String networkChannel = "kPlan";
    public void preInit(){

    }
    public void init(){
        networkHandler.init();
    }

    public void postInit(){
        networkHandler.postInit();
    }
}
