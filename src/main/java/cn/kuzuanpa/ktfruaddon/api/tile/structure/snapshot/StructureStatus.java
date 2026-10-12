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

package cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot;

/**快照里的位置状态与检查结果常量。方块 id 合法范围是 0..4095，负数可以安全用作哨兵。**/
public final class StructureStatus {
    private StructureStatus() {
    }

    // ---- 位置状态（StructureView#statusAt / #blockIdAt 的返回值）----
    /**已知的空气。区块已加载且该 y 段为空（段数组为 null 也算）——这是"确定没有方块"，不是未知。**/
    public static final int AIR = 0;
    /**快照时该区块不在 {@code loadedChunkHashMap} 里。未知，交给机器决定怎么用。**/
    public static final int UNLOADED = -1;
    /**y 不在 0..255，或 |x|/|z| 超出 30000000。确定无效，机器通常应当判 NOT_FORM。**/
    public static final int OUT_OF_WORLD = -2;
    /**快照里没有这个位置的数据：请求盒子外、快照被预算截断、或请求已取消。一律当"不可用"。**/
    public static final int NO_DATA = -3;

    public static boolean isKnown(int aStatus) {return aStatus >= 0;}
    public static boolean isUnknown(int aStatus) {return aStatus == UNLOADED || aStatus == NO_DATA;}

    public static String describe(int aStatus) {
        switch (aStatus) {
            case UNLOADED:      return "UNLOADED";
            case OUT_OF_WORLD:  return "OUT_OF_WORLD";
            case NO_DATA:       return "NO_DATA";
            case AIR:           return "AIR";
            default:            return aStatus > 0 ? ("BLOCK#" + aStatus) : "?";
        }
    }

    // ---- 检查结果三态（IStructureMachine#check 的返回值）----
    /**结构成形。变更列表按 diff 应用。**/
    public static final byte FORM = 0;
    /**结构不成形。变更列表仍然会被应用（沿用"边走边 setTarget"的语义，机器自己决定往列表里放什么）。**/
    public static final byte NOT_FORM = 1;
    /**本次无法判定（数据不全/预算截断/异常）。变更列表会被丢弃，稍后重试，机器状态保持不变。**/
    public static final byte DEFER = 2;

    public static String describeOutcome(byte aOutcome) {
        switch (aOutcome) {
            case FORM:      return "FORM";
            case NOT_FORM:  return "NOT_FORM";
            case DEFER:     return "DEFER";
            default:        return "INVALID(" + aOutcome + ")";
        }
    }
}
