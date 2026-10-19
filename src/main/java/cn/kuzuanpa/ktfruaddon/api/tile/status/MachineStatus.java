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

package cn.kuzuanpa.ktfruaddon.api.tile.status;

import cn.kuzuanpa.ktfruaddon.api.recipe.KortexWorker;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputeUserComponent;
import gregapi.data.LH;

import java.util.Locale;

/**停机原因。前 9 个是 kortex 原有取值（顺序不动，旧 key {@code ktfru.text.kortex.state.*} 保留为别名），
 * 文案取 {@code ktfru.text.machine.status.<小写枚举名>}。**/
public enum MachineStatus {
    NO_RECIPE, ITEM_SHORTAGE, FLUID_SHORTAGE, ENERGY_SHORTAGE,
    OUTPUT_BLOCKED_FLUID, OUTPUT_BLOCKED_ITEM, OUTPUT_BLOCKED_ENERGY, RUNNING, MACHINE_LIMITED,
    STRUCTURE_NOT_FORMED, CONDITION_PART_INVALID, ROOM_INVALID,
    COMPUTE_UNBOUND, COMPUTE_OFFLINE, COMPUTE_INSUFFICIENT, COMPUTE_WAITING,
    SWITCHED_OFF, IGNITION_REQUIRED, CUSTOM,
    // 运行态但未加工 / 有料可跑但未启动
    STANDBY, READY;

    public String getLocalized() {
        return LH.get("ktfru.text.machine.status." + name().toLowerCase(Locale.ROOT));
    }

    /**只有 {@link #RUNNING} 算在动。**/
    public boolean isProgressing() {return this == RUNNING;}

    /**机器被拦下、什么都做不了的原因。**/
    public boolean isBlocking() {
        return this == SWITCHED_OFF || this == STRUCTURE_NOT_FORMED || this == CONDITION_PART_INVALID || this == ROOM_INVALID;
    }

    /**kortex 的 StateMgr 取值 → 状态，越界返回 null。**/
    public static MachineStatus fromKortex(byte aState) {
        switch (aState) {
            case KortexWorker.STATE_NO_RECIPE:            return NO_RECIPE;
            case KortexWorker.STATE_ITEM_SHORTAGE:        return ITEM_SHORTAGE;
            case KortexWorker.STATE_FLUID_SHORTAGE:       return FLUID_SHORTAGE;
            case KortexWorker.STATE_ENERGY_SHORTAGE:      return ENERGY_SHORTAGE;
            case KortexWorker.STATE_OUTPUT_BLOCKED_FLUID: return OUTPUT_BLOCKED_FLUID;
            case KortexWorker.STATE_OUTPUT_BLOCKED_ITEM:  return OUTPUT_BLOCKED_ITEM;
            case KortexWorker.STATE_OUTPUT_BLOCKED_ENERGY:return OUTPUT_BLOCKED_ENERGY;
            case KortexWorker.STATE_RUNNING:              return RUNNING;
            case KortexWorker.STATE_MACHINE_LIMITED:      return MACHINE_LIMITED;
            default:                                      return null;
        }
    }

    /**算力用户组件的状态 → 状态，不缺算力（运行中/无需求）返回 null。**/
    public static MachineStatus fromCompute(byte aState) {
        switch (aState) {
            case ComputeUserComponent.STATUS_UNBOUND:              return COMPUTE_UNBOUND;
            case ComputeUserComponent.STATUS_OFFLINE:              return COMPUTE_OFFLINE;
            case ComputeUserComponent.STATUS_INSTALLED_INSUFFICIENT:
            case ComputeUserComponent.STATUS_TYPE_MISMATCH:
            case ComputeUserComponent.STATUS_CLUSTER_INSUFFICIENT: return COMPUTE_INSUFFICIENT;
            case ComputeUserComponent.STATUS_WAITING:              return COMPUTE_WAITING;
            default:                                              return null;
        }
    }
}
