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
 * 一次检查的扁平快照。由 {@link SnapshotBuilder} 在主线程分帧填充，填满后只读地交给 worker。
 *
 * <p>布局：按 (区块 × y 段) 切成块，每块 {@code lsb}(4096 字节) + {@code msb}(2048 打包 nibble)
 * [+ metadata nibble] [+ 部件 key]，全部是原版 {@code ExtendedBlockStorage} 的原样拷贝
 * （整段 {@code System.arraycopy}，不做解码）。数组为 null 表示"该段不存在"，
 * 也就是<b>已知空气</b>——和 {@link StructureStatus#UNLOADED} 完全不同。</p>
 *
 * <p>注意：TFRU 的 1.7.10 方块 id 空间是 12 bit（原版 {@code ExtendedBlockStorage} 的 storage 字节 + nibble），
 * 而 GT6 的多方块部件号（如 18002）是 <b>MTE 登记号</b>不是方块 id。所以部件身份一律走
 * {@link IStructureMachine#encodePart} 从方块实体取，不要试图从方块 id 反推。</p>
 *
 * <p>未知性天然是区块级的，所以只用一个长期位图 {@code mUnknownMask} 记录，不占逐格空间。</p>
 *
 * <p>本类字段对同包开放（builder 需要写），对外只通过 {@link StructureView} 暴露；<b>不含任何 World 引用</b>，
 * 所以拿到它的 worker 线程没有任何途径去碰世界。</p>
 */
public final class StructureSnapshot implements StructureView {
    // ---- 盒子 ----
    final int mMinX, mMinY, mMinZ, mSizeX, mSizeY, mSizeZ;
    // ---- 区块网格 ----
    final int mMinChunkX, mMinChunkZ, mChunksX, mChunksZ;
    // ---- y 段 ----
    final int mMinSection, mSections;
    // ---- 数据（下标统一用 chunkSectionIndex）----
    final long[] mUnknownMask;
    final byte[][] mLsb;
    final byte[][] mMsb;
    final byte[][] mMeta;
    final short[][] mParts;

    private final int mDimensionId;
    private final long mRequestId;
    private final long mTick;
    int mUnknownCount;
    boolean mComplete;

    private StructureSnapshot(StructureBox aBox, int aDimensionId, long aRequestId, long aTick) {
        mMinX = aBox.minX; mMinY = aBox.minY; mMinZ = aBox.minZ;
        mSizeX = aBox.sizeX; mSizeY = aBox.sizeY; mSizeZ = aBox.sizeZ;
        mMinChunkX = aBox.minX >> 4;
        mMinChunkZ = aBox.minZ >> 4;
        mChunksX = (aBox.maxX() >> 4) - mMinChunkX + 1;
        mChunksZ = (aBox.maxZ() >> 4) - mMinChunkZ + 1;
        int maxSection = Math.min(15, aBox.maxY() >> 4);
        int minSection = Math.max(0, aBox.minY >> 4);
        mMinSection = minSection;
        mSections = Math.max(0, maxSection - minSection + 1);

        int chunkSections = mChunksX * mChunksZ * mSections;
        mUnknownMask = new long[(mChunksX * mChunksZ + 63) >> 6];
        mLsb = new byte[chunkSections][];
        mMsb = new byte[chunkSections][];
        mMeta = aBox.wantMeta ? new byte[chunkSections][] : null;
        if (aBox.wantParts) {
            mParts = new short[chunkSections][];
            for (int i = 0; i < chunkSections; i++) mParts[i] = new short[4096];
        } else mParts = null;

        mDimensionId = aDimensionId;
        mRequestId = aRequestId;
        mTick = aTick;
    }

    /**按盒子分配快照骨架（数组本体）。真正复制区块数据由 {@link SnapshotBuilder#step} 分帧完成。**/
    public static StructureSnapshot allocate(StructureBox aBox, int aDimensionId, long aRequestId, long aTick) {
        return new StructureSnapshot(aBox, aDimensionId, aRequestId, aTick);
    }

    public int dimensionId() {return mDimensionId;}
    public int chunkCount() {return mChunksX * mChunksZ;}
    public int sectionsPerChunk() {return mSections;}
    /**快照是否已经完整复制。调度器保证只把 complete 的快照交给 worker。**/
    public boolean isComplete() {return mComplete;}
    /**估算占用的字节数，用于日志/统计。**/
    public long estimatedBytes() {
        long perSection = 4096 + 2048 + (mMeta != null ? 2048 : 0) + (mParts != null ? 8192 : 0);
        return 64 + (long) chunkCount() * mSections * perSection;
    }

    // ---- 坐标映射（包内给 builder 用，也用于内部访问）----
    int chunkIndex(int aX, int aZ) {return ((aZ >> 4) - mMinChunkZ) * mChunksX + ((aX >> 4) - mMinChunkX);}
    int sectionIndexInChunk(int aY) {return (aY >> 4) - mMinSection;}
    int chunkSectionIndex(int aChunkIndex, int aSection) {return aChunkIndex * mSections + aSection;}
    static int local(int aX, int aY, int aZ) {return ((aY & 15) << 8) | ((aZ & 15) << 4) | (aX & 15);}

    void markChunkUnloaded(int aChunkIndex) {
        mUnknownMask[aChunkIndex >> 6] |= 1L << (aChunkIndex & 63);
        mUnknownCount++;
    }

    void markComplete() {mComplete = true;}

    // ---- StructureView ----
    @Override public int minX() {return mMinX;}
    @Override public int minY() {return mMinY;}
    @Override public int minZ() {return mMinZ;}
    @Override public int sizeX() {return mSizeX;}
    @Override public int sizeY() {return mSizeY;}
    @Override public int sizeZ() {return mSizeZ;}
    @Override public long requestedTick() {return mTick;}
    @Override public long requestId() {return mRequestId;}

    @Override public int index(int aX, int aY, int aZ) {
        return ((aZ - mMinZ) * mSizeY + (aY - mMinY)) * mSizeX + (aX - mMinX);
    }

    @Override public boolean inBox(int aX, int aY, int aZ) {
        return aX >= mMinX && aX < mMinX + mSizeX
            && aY >= mMinY && aY < mMinY + mSizeY
            && aZ >= mMinZ && aZ < mMinZ + mSizeZ;
    }

    @Override public int statusAt(int aX, int aY, int aZ) {
        if (aY < 0 || aY > 255) return StructureStatus.OUT_OF_WORLD;
        if (aX < -30000000 || aX >= 30000000 || aZ < -30000000 || aZ >= 30000000) return StructureStatus.OUT_OF_WORLD;
        if (!inBox(aX, aY, aZ) || mSections == 0) return StructureStatus.NO_DATA;
        int ci = chunkIndex(aX, aZ);
        if ((mUnknownMask[ci >> 6] >>> (ci & 63) & 1L) != 0) return StructureStatus.UNLOADED;
        int slot = chunkSectionIndex(ci, sectionIndexInChunk(aY));
        byte[] lsb = mLsb[slot];
        if (lsb == null) return StructureStatus.AIR;
        int li = local(aX, aY, aZ);
        int id = lsb[li] & 0xFF;
        byte[] msb = mMsb[slot];
        if (msb != null) id |= ((msb[li >> 1] >> ((li & 1) << 2)) & 15) << 8;
        return id;
    }

    @Override public int blockIdAt(int aX, int aY, int aZ) {return statusAt(aX, aY, aZ);}

    @Override public int metaAt(int aX, int aY, int aZ) {
        if (mMeta == null || !inBox(aX, aY, aZ) || aY < 0 || aY > 255 || mSections == 0) return 0;
        int ci = chunkIndex(aX, aZ);
        if ((mUnknownMask[ci >> 6] >>> (ci & 63) & 1L) != 0) return 0;
        byte[] meta = mMeta[chunkSectionIndex(ci, sectionIndexInChunk(aY))];
        if (meta == null) return 0;
        int li = local(aX, aY, aZ);
        return (meta[li >> 1] >> ((li & 1) << 2)) & 15;
    }

    @Override public int partKeyAt(int aX, int aY, int aZ) {
        if (mParts == null || !inBox(aX, aY, aZ) || aY < 0 || aY > 255 || mSections == 0) return 0;
        int ci = chunkIndex(aX, aZ);
        if ((mUnknownMask[ci >> 6] >>> (ci & 63) & 1L) != 0) return 0;
        short[] parts = mParts[chunkSectionIndex(ci, sectionIndexInChunk(aY))];
        return parts == null ? 0 : parts[local(aX, aY, aZ)];
    }

    @Override public int unknownChunkCount() {return mUnknownCount;}

    @Override public boolean isChunkUnknown(int aChunkX, int aChunkZ) {
        int cx = aChunkX - mMinChunkX, cz = aChunkZ - mMinChunkZ;
        if (cx < 0 || cz < 0 || cx >= mChunksX || cz >= mChunksZ) return true;
        int ci = cz * mChunksX + cx;
        return (mUnknownMask[ci >> 6] >>> (ci & 63) & 1L) != 0;
    }

    @Override public int collectUnknownChunks(int[] aOut) {
        int count = 0;
        int total = mChunksX * mChunksZ;
        for (int ci = 0; ci < total; ci++) {
            if ((mUnknownMask[ci >> 6] >>> (ci & 63) & 1L) == 0) continue;
            if (count * 2 + 1 >= aOut.length) break;
            aOut[count * 2] = mMinChunkX + ci % mChunksX;
            aOut[count * 2 + 1] = mMinChunkZ + ci / mChunksX;
            count++;
        }
        return count;
    }

    @Override public boolean subBoxHasUnknown(int aX, int aY, int aZ, int aSizeX, int aSizeY, int aSizeZ) {
        if (aSizeX <= 0 || aSizeY <= 0 || aSizeZ <= 0) return false;
        // 世界外/盒子外一律视为"不可用", 对机器而言和未知一样需要保守处理
        if (aX < mMinX || aZ < mMinZ || aY < mMinY) return true;
        if (aX + aSizeX - 1 > mMinX + mSizeX - 1 || aZ + aSizeZ - 1 > mMinZ + mSizeZ - 1) return true;
        if (aY + aSizeY - 1 > mMinY + mSizeY - 1) return true;
        int minCx = aX >> 4, maxCx = (aX + aSizeX - 1) >> 4;
        int minCz = aZ >> 4, maxCz = (aZ + aSizeZ - 1) >> 4;
        for (int cz = minCz; cz <= maxCz; cz++) for (int cx = minCx; cx <= maxCx; cx++) {
            int ci = (cz - mMinChunkZ) * mChunksX + (cx - mMinChunkX);
            if ((mUnknownMask[ci >> 6] >>> (ci & 63) & 1L) != 0) return true;
        }
        return false;
    }
}
