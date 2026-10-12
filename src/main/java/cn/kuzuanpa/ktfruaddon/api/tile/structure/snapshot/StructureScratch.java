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
 * 每个请求一份的可复用工作区，供泛洪填充之类的算法使用。
 *
 * <p>目的是把"每格 new 一个对象 / 用 ArrayList 做 visited"这类分配和 O(n²) 判重从上层实现里挤出去：
 * 用 {@link #isVisited(int)}/{@link #setVisited(int)} 配合 {@link #nextRound()} 做版本号标记，免每轮清零；
 * 队列直接用 {@link #queue()} 的原始 int 数组。</p>
 *
 * <p>数组在 worker 线程上<b>首次使用时才分配</b>（因此分配开销不落在主线程），分配后由本请求独占，
 * 不需要同步。体积对应请求盒子的体积，索引用 {@link StructureView#index}。</p>
 */
public final class StructureScratch {
    private final int mVolume;
    private int[] mStamp;
    private int[] mQueue;
    private byte[][] mBuffers;
    private int mStampValue = 1;
    private int mVisitedCount;

    public StructureScratch(int aVolume) {mVolume = aVolume;}

    public int volume() {return mVolume;}

    /**worker 线程可用：盒子体积大小的原始队列，泛洪填充直接当地址数组用。**/
    public int[] queue() {
        if (mQueue == null) mQueue = new int[mVolume];
        return mQueue;
    }

    /**开始新一轮标记。调用后 {@link #isVisited} 全部为 false。**/
    public void nextRound() {
        if (mStamp == null) mStamp = new int[mVolume];
        mVisitedCount = 0;
        if (++mStampValue == Integer.MAX_VALUE) {java.util.Arrays.fill(mStamp, 0); mStampValue = 1;}
    }

    public boolean isVisited(int aIndex) {return mStamp != null && mStamp[aIndex] == mStampValue;}

    public void setVisited(int aIndex) {
        if (mStamp == null) mStamp = new int[mVolume];
        if (mStamp[aIndex] != mStampValue) {mStamp[aIndex] = mStampValue; mVisitedCount++;}
    }

    /**本轮被标记过的格子数。用于观测上层实现的规模（会写进调度器统计）。**/
    public int visitedCount() {return mVisitedCount;}

    /**
     * 复用缓冲：按槽位取一块长度 {@code aSize} 的 byte[]，同一个槽位多次调用返回同一块（首次使用才分配）。
     * 内容不做清零（首次分配天然为 0），需要干净状态就自己 {@code java.util.Arrays.fill(buf, (byte)0)}。
     * 用来放"每层一张截面表"这类标记，避免上层实现每次 {@code new boolean[...]}。
     */
    public byte[] buffer(int aSlot, int aSize) {
        if (mBuffers == null) mBuffers = new byte[aSlot + 1][];
        if (aSlot >= mBuffers.length) mBuffers = java.util.Arrays.copyOf(mBuffers, aSlot + 1);
        byte[] buffer = mBuffers[aSlot];
        if (buffer == null || buffer.length < aSize) {
            buffer = new byte[aSize];
            mBuffers[aSlot] = buffer;
        }
        return buffer;
    }
}
