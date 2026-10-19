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

package cn.kuzuanpa.ktfruaddon.api.tile.control;

import cn.kuzuanpa.ktfruaddon.api.tile.IMeterDetectable;

/**电表（TOOL_unimeter）的只读视图：分别报上一 tick 收到/发出的能量。
 * 机器在 {@code onToolClick2} 里调用 {@link MachineControl#handleMeterTool} 即可输出，不报就是没接。**/
public interface IMachineMeter {
    IMeterDetectable.MeterData[] NO_READINGS = new IMeterDetectable.MeterData[0];

    default IMeterDetectable.MeterData[] getMeterReceived() {return NO_READINGS;}

    default IMeterDetectable.MeterData[] getMeterEmitted() {return NO_READINGS;}
}
