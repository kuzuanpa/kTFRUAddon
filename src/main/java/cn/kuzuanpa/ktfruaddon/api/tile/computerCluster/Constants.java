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

public class Constants {
    public static final short EVENT_CLUSTER_DESTROY=0;
    public static final short EVENT_WRONG_UUID =1;
    public static final short EVENT_KICKING_FROM_CLUSTER =2;
    public static final short EVENT_A_CONTROLLER_LEFT =3;
    public static final short EVENT_CLUSTER_CREATED =4;
    public static final short EVENT_CONTROLLER_JOINED =5;
    public static final short EVENT_USER_JOINED =6;
    public static final short EVENT_USER_LEFT =7;
    public static final short EVENT_POWER_ALLOCATED =8;
    public static final short EVENT_POWER_RELEASED =9;
    public static final short EVENT_POWER_ALLOCATE_FAILED =10;
    public static final short EVENT_STATE_CHANGED =11;
    public static final short EVENT_REACHABILITY_OK =12;
    public static final short EVENT_REACHABILITY_WARNING =13;
    public static final short EVENT_REACHABILITY_FAILED =14;
    public static final byte STATE_OFFLINE=0;
    public static final byte STATE_NORMAL=1;
    public static final byte STATE_WARNING=2;
    public static final byte STATE_ERROR=3;
    public static final byte STATE_BELONG_ERR/*The controller owned by other cluster*/=4;

    public static String getControllerEventShortDesc(byte event){
        switch (event) {
            case EVENT_WRONG_UUID: return "UUID conflict";
            case EVENT_KICKING_FROM_CLUSTER: return "Removed";
            case EVENT_A_CONTROLLER_LEFT: return "Peer left";
            case EVENT_CONTROLLER_JOINED: return "Joined";
            case EVENT_STATE_CHANGED: return "State changed";
            case EVENT_REACHABILITY_OK: return "Reachability ok";
            case EVENT_REACHABILITY_WARNING: return "Reachability warn";
            case EVENT_REACHABILITY_FAILED: return "Reachability fail";
            case EVENT_CLUSTER_DESTROY: return "Destroyed";
            default: return "Event " + event;
        }
    }

    public static String getControllerEventDesc(byte event, String extra){
        switch (event) {
            case EVENT_WRONG_UUID: return "UUID conflict" + appendExtra(extra);
            case EVENT_KICKING_FROM_CLUSTER: return "Removed from cluster" + appendExtra(extra);
            case EVENT_A_CONTROLLER_LEFT: return "Peer controller left" + appendExtra(extra);
            case EVENT_CONTROLLER_JOINED: return "Joined cluster" + appendExtra(extra);
            case EVENT_STATE_CHANGED: return "State changed" + appendExtra(extra);
            case EVENT_REACHABILITY_OK: return "Reachability normal" + appendExtra(extra);
            case EVENT_REACHABILITY_WARNING: return "Reachability warning" + appendExtra(extra);
            case EVENT_REACHABILITY_FAILED: return "Reachability failed" + appendExtra(extra);
            case EVENT_CLUSTER_DESTROY: return "Cluster destroyed" + appendExtra(extra);
            default: return "Controller event " + event + appendExtra(extra);
        }
    }

    public static String getClusterEventShortDesc(byte event){
        switch (event) {
            case EVENT_CLUSTER_CREATED: return "Created";
            case EVENT_CLUSTER_DESTROY: return "Destroyed";
            case EVENT_CONTROLLER_JOINED: return "+ Controller";
            case EVENT_A_CONTROLLER_LEFT: return "- Controller";
            case EVENT_USER_JOINED: return "+ User";
            case EVENT_USER_LEFT: return "- User";
            case EVENT_POWER_ALLOCATED: return "- Power";
            case EVENT_POWER_RELEASED: return "+ Power";
            case EVENT_POWER_ALLOCATE_FAILED: return "x Power";
            case EVENT_STATE_CHANGED: return "*State";
            default: return "Event " + event;
        }
    }

    public static String getClusterEventDesc(byte event, String extra){
        switch (event) {
            case EVENT_CLUSTER_CREATED: return "Cluster created" + appendExtra(extra);
            case EVENT_CLUSTER_DESTROY: return "Cluster destroyed" + appendExtra(extra);
            case EVENT_CONTROLLER_JOINED: return "Controller joined" + appendExtra(extra);
            case EVENT_A_CONTROLLER_LEFT: return "Controller left" + appendExtra(extra);
            case EVENT_USER_JOINED: return "User joined" + appendExtra(extra);
            case EVENT_USER_LEFT: return "User left" + appendExtra(extra);
            case EVENT_POWER_ALLOCATED: return "Power allocated" + appendExtra(extra);
            case EVENT_POWER_RELEASED: return "Power released" + appendExtra(extra);
            case EVENT_POWER_ALLOCATE_FAILED: return "Power allocation failed" + appendExtra(extra);
            case EVENT_STATE_CHANGED: return "Cluster state changed" + appendExtra(extra);
            default: return "Cluster event " + event + appendExtra(extra);
        }
    }

    public static String getUserEventDesc(byte event, String extra){
        switch (event) {
            case EVENT_USER_JOINED: return "Bound to cluster" + appendExtra(extra);
            case EVENT_USER_LEFT: return "Left cluster" + appendExtra(extra);
            case EVENT_POWER_ALLOCATED: return "Power allocated" + appendExtra(extra);
            case EVENT_POWER_RELEASED: return "Power released" + appendExtra(extra);
            case EVENT_POWER_ALLOCATE_FAILED: return "Power allocation failed" + appendExtra(extra);
            default: return "User event " + event + appendExtra(extra);
        }
    }

    private static String appendExtra(String extra) {
        return extra == null || extra.isEmpty() ? "" : ": " + extra;
    }
}
