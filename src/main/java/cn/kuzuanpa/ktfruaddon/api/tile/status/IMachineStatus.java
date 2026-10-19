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
import gregapi.tileentity.machines.ITileEntityRunningPowerSaving;
import gregapi.tileentity.machines.ITileEntityRunningSuccessfully;

/**运行态与停机原因。实现它 = 这台机器愿意被放大镜 / WAILA / 检测器 / GUI / 鱼右键问状态。
 * 它同时是 GT 四个运行接口 + 省电接口的子接口，默认把 GT 方法桥接到下面的布尔值，
 * 所以实现一次就能被 GT 覆盖板和 WAILA 认出；停机原因不用自己写，内置来源见 {@link MachineStatusReport}。**/
public interface IMachineStatus extends ITileEntityRunningSuccessfully, ITileEntityRunningPowerSaving {
    KortexWorker[] NO_WORKERS = new KortexWorker[0];

    /**有料可跑。**/
    default boolean isRunningPossible() {return false;}

    /**已启动（含待机）。**/
    default boolean isRunning() {return isRunningPossible();}

    /**正在加工/输出。**/
    default boolean isProcessing() {return isRunning();}

    /**刚产出。默认 false：只覆写 running/processing 的机器不会被 Success 检测器当成持续产出。**/
    default boolean isSuccess() {return false;}

    default boolean isPowerSaving() {return false;}

    /**非 null 时它就是停机原因，优先级高于所有内置来源。**/
    default MachineStatus getStatusOverride() {return null;}

    /**附加明细行，例如「缺少聚焦阵列 3 个」；只有存在主原因时才会显示。**/
    default String getStatusDetail() {return null;}

    /**自报配方执行单元，供状态 API 读出 kortex 的停机原因；不报则这一项来源为空。**/
    default KortexWorker[] getKortexWorkers() {return NO_WORKERS;}

    // GT 运行接口桥接：机器只实现上面的布尔值即可。
    @Override default boolean getStateRunningPossible() {return isRunningPossible();}
    @Override default boolean getStateRunningPassively() {return isRunning();}
    @Override default boolean getStateRunningActively() {return isProcessing();}
    @Override default boolean getStateRunningSuccessfully() {return isSuccess();}
    @Override default boolean getStateRunningPowerSaving() {return isPowerSaving();}
}
