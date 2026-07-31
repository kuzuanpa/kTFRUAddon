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

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import gregapi.data.LH;

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
    public static final byte STATE_BELONG_ERR=4;

    public static String getControllerEventShortDesc(byte event){
        switch (event) {
            case EVENT_WRONG_UUID: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CONTROLLER_SHORT_WRONG_UUID);
            case EVENT_KICKING_FROM_CLUSTER: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CONTROLLER_SHORT_KICKED);
            case EVENT_A_CONTROLLER_LEFT: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CONTROLLER_SHORT_PEER_LEFT);
            case EVENT_CONTROLLER_JOINED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CONTROLLER_SHORT_JOINED);
            case EVENT_STATE_CHANGED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CONTROLLER_SHORT_STATE_CHANGED);
            case EVENT_REACHABILITY_OK: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CONTROLLER_SHORT_REACHABILITY_OK);
            case EVENT_REACHABILITY_WARNING: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CONTROLLER_SHORT_REACHABILITY_WARNING);
            case EVENT_REACHABILITY_FAILED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CONTROLLER_SHORT_REACHABILITY_FAILED);
            case EVENT_CLUSTER_DESTROY: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CONTROLLER_SHORT_DESTROYED);
            default: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_UNKNOWN) + " " + event;
        }
    }

    public static String getControllerEventDesc(byte event, String extra){
        switch (event) {
            case EVENT_WRONG_UUID: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CONTROLLER_WRONG_UUID) + appendExtra(extra);
            case EVENT_KICKING_FROM_CLUSTER: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CONTROLLER_KICKED) + appendExtra(extra);
            case EVENT_A_CONTROLLER_LEFT: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CONTROLLER_PEER_LEFT) + appendExtra(extra);
            case EVENT_CONTROLLER_JOINED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CONTROLLER_JOINED) + appendExtra(extra);
            case EVENT_STATE_CHANGED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CONTROLLER_STATE_CHANGED) + appendExtra(extra);
            case EVENT_REACHABILITY_OK: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CONTROLLER_REACHABILITY_OK) + appendExtra(extra);
            case EVENT_REACHABILITY_WARNING: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CONTROLLER_REACHABILITY_WARNING) + appendExtra(extra);
            case EVENT_REACHABILITY_FAILED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CONTROLLER_REACHABILITY_FAILED) + appendExtra(extra);
            case EVENT_CLUSTER_DESTROY: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CONTROLLER_DESTROYED) + appendExtra(extra);
            default: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_UNKNOWN) + " " + event + appendExtra(extra);
        }
    }

    public static String getClusterEventShortDesc(byte event){
        switch (event) {
            case EVENT_CLUSTER_CREATED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CLUSTER_SHORT_CREATED);
            case EVENT_CLUSTER_DESTROY: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CLUSTER_SHORT_DESTROYED);
            case EVENT_CONTROLLER_JOINED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CLUSTER_SHORT_CONTROLLER_JOINED);
            case EVENT_A_CONTROLLER_LEFT: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CLUSTER_SHORT_CONTROLLER_LEFT);
            case EVENT_USER_JOINED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CLUSTER_SHORT_USER_JOINED);
            case EVENT_USER_LEFT: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CLUSTER_SHORT_USER_LEFT);
            case EVENT_POWER_ALLOCATED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CLUSTER_SHORT_POWER_ALLOCATED);
            case EVENT_POWER_RELEASED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CLUSTER_SHORT_POWER_RELEASED);
            case EVENT_POWER_ALLOCATE_FAILED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CLUSTER_SHORT_POWER_ALLOCATE_FAILED);
            case EVENT_STATE_CHANGED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CLUSTER_SHORT_STATE_CHANGED);
            default: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_UNKNOWN) + " " + event;
        }
    }

    public static String getClusterEventDesc(byte event, String extra){
        switch (event) {
            case EVENT_CLUSTER_CREATED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CLUSTER_CREATED) + appendExtra(extra);
            case EVENT_CLUSTER_DESTROY: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CLUSTER_DESTROYED) + appendExtra(extra);
            case EVENT_CONTROLLER_JOINED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CLUSTER_CONTROLLER_JOINED) + appendExtra(extra);
            case EVENT_A_CONTROLLER_LEFT: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CLUSTER_CONTROLLER_LEFT) + appendExtra(extra);
            case EVENT_USER_JOINED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CLUSTER_USER_JOINED) + appendExtra(extra);
            case EVENT_USER_LEFT: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CLUSTER_USER_LEFT) + appendExtra(extra);
            case EVENT_POWER_ALLOCATED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CLUSTER_POWER_ALLOCATED) + appendExtra(extra);
            case EVENT_POWER_RELEASED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CLUSTER_POWER_RELEASED) + appendExtra(extra);
            case EVENT_POWER_ALLOCATE_FAILED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CLUSTER_POWER_ALLOCATE_FAILED) + appendExtra(extra);
            case EVENT_STATE_CHANGED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_CLUSTER_STATE_CHANGED) + appendExtra(extra);
            default: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_UNKNOWN) + " " + event + appendExtra(extra);
        }
    }

    public static String getUserEventDesc(byte event, String extra){
        switch (event) {
            case EVENT_USER_JOINED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_USER_BOUND) + appendExtra(extra);
            case EVENT_USER_LEFT: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_USER_LEFT) + appendExtra(extra);
            case EVENT_POWER_ALLOCATED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_USER_POWER_ALLOCATED) + appendExtra(extra);
            case EVENT_POWER_RELEASED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_USER_POWER_RELEASED) + appendExtra(extra);
            case EVENT_POWER_ALLOCATE_FAILED: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_USER_POWER_ALLOCATE_FAILED) + appendExtra(extra);
            default: return LH.get(I18nHandler.COMPUTE_CLUSTER_EVENT_UNKNOWN) + " " + event + appendExtra(extra);
        }
    }

    private static String appendExtra(String extra) {
        return extra == null || extra.isEmpty() ? "" : ": " + extra;
    }
}
