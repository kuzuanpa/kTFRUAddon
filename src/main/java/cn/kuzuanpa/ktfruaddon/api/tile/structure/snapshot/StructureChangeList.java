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

/**
 * worker 产出的"待应用变更"列表：纯数据、平行 int 数组、无对象分配。
 *
 * <p>worker 线程填充，主线程分帧读取并逐条调用 {@link IStructureMachine#applyChange}。
 * 发布（交给调度器）之后 worker 不能再碰它。</p>
 *
 * <p>溢出保护：超出 {@link #MAX_CHANGES} 会置 {@link #overflowed()}，机器应当据此返回
 * {@link StructureStatus#DEFER}（说明它的检查范围或产出规模不合理），而不是继续塞。</p>
 */
public final class StructureChangeList {
    public static final int MAX_CHANGES = 200_000;

    /**给部件设置 design/usage/target（key 由机器自定义，常用 TileDesc 的登记项）。**/
    public static final int KIND_SET_PART = 1;
    /**把部件从本机解绑（等价于 setTarget(null, design, 0)）。**/
    public static final int KIND_RESET_PART = 2;
    /**机器自定义操作，参数含义由机器自己解释。**/
    public static final int KIND_CUSTOM = 3;
    /**
     * 把缺失的部件方块补上（builder wand 自动搭建）。
     * 只在机器声明了 builder 上下文时才会产出；应用期必须只由主线程执行，并且执行者要复核玩家/物品栏仍然可用。
     */
    public static final int KIND_PLACE_BLOCK = 4;

    private static final int[] EMPTY = new int[0];
    private int[] mX = EMPTY, mY = EMPTY, mZ = EMPTY, mKind = EMPTY, mKey = EMPTY, mDesign = EMPTY, mUsage = EMPTY;
    /**纯计算结果通道（例如水池每层容量），由 worker 写、主线程读；语义由机器自己定义。**/
    private long[] mSlots;
    private int mSize;
    private boolean mOverflowed;

    /**
     * 取一块 worker 可写的 long 结果槽（首次调用时分配并清零）。
     * 用途：把"不是变更、但要带回主线程"的纯计算结果（容量表之类）传出去。
     */
    public long[] resultSlots(int aCount) {
        if (mSlots == null || mSlots.length < aCount) mSlots = new long[aCount];
        return mSlots;
    }

    public int resultSlotCount() {return mSlots == null ? 0 : mSlots.length;}
    public long resultSlot(int aIndex) {return mSlots == null || aIndex >= mSlots.length ? 0L : mSlots[aIndex];}

    public int size() {return mSize;}
    public boolean overflowed() {return mOverflowed;}
    public void clear() {mSize = 0; mOverflowed = false;}

    public int x(int aIndex) {return mX[aIndex];}
    public int y(int aIndex) {return mY[aIndex];}
    public int z(int aIndex) {return mZ[aIndex];}
    public int kind(int aIndex) {return mKind[aIndex];}
    public int key(int aIndex) {return mKey[aIndex];}
    public int design(int aIndex) {return mDesign[aIndex];}
    public int usage(int aIndex) {return mUsage[aIndex];}
    /**主线程分帧应用时用的原始数组引用（长度可能大于 {@link #size()}）。**/
    public int[] rawX() {return mX;}
    public int[] rawY() {return mY;}
    public int[] rawZ() {return mZ;}
    public int[] rawKind() {return mKind;}
    public int[] rawKey() {return mKey;}
    public int[] rawDesign() {return mDesign;}
    public int[] rawUsage() {return mUsage;}

    public void add(int aX, int aY, int aZ, int aKind) {add(aX, aY, aZ, aKind, 0, 0, 0);}

    public void add(int aX, int aY, int aZ, int aKind, int aKey, int aDesign, int aUsage) {
        if (mSize >= MAX_CHANGES) {mOverflowed = true; return;}
        if (mSize >= mX.length) grow(mSize == 0 ? 64 : mSize * 2);
        mX[mSize] = aX; mY[mSize] = aY; mZ[mSize] = aZ;
        mKind[mSize] = aKind; mKey[mSize] = aKey; mDesign[mSize] = aDesign; mUsage[mSize] = aUsage;
        mSize++;
    }

    private void grow(int aCapacity) {
        int cap = Math.min(aCapacity, MAX_CHANGES);
        mX = java.util.Arrays.copyOf(mX, cap); mY = java.util.Arrays.copyOf(mY, cap); mZ = java.util.Arrays.copyOf(mZ, cap);
        mKind = java.util.Arrays.copyOf(mKind, cap); mKey = java.util.Arrays.copyOf(mKey, cap);
        mDesign = java.util.Arrays.copyOf(mDesign, cap); mUsage = java.util.Arrays.copyOf(mUsage, cap);
    }
}
