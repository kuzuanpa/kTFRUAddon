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
 * 一次检查要覆盖的世界盒子 + 需要哪些附加数据。
 *
 * <p>盒子必须包住 {@code check()} 会读到的<b>所有</b>位置（包括泛洪填充要看的 y-1 层）。
 * 盒子会被向上对齐到 16x16x16 段；只有与 [minY, minY+sizeY-1] 相交的 y 段会被复制。</p>
 */
public final class StructureBox {
    public final int minX, minY, minZ, sizeX, sizeY, sizeZ;
    /**是否复制部件 key（{@link StructureView#partKeyAt}）。需要判断"这里是哪种多方块部件"时打开。**/
    public final boolean wantParts;
    /**是否复制方块 metadata。**/
    public final boolean wantMeta;

    private StructureBox(int aMinX, int aMinY, int aMinZ, int aSizeX, int aSizeY, int aSizeZ, boolean aWantParts, boolean aWantMeta) {
        minX = aMinX; minY = aMinY; minZ = aMinZ; sizeX = aSizeX; sizeY = aSizeY; sizeZ = aSizeZ;
        wantParts = aWantParts; wantMeta = aWantMeta;
    }

    public int maxX() {return minX + sizeX - 1;}
    public int maxY() {return minY + sizeY - 1;}
    public int maxZ() {return minZ + sizeZ - 1;}
    public int volume() {return sizeX * sizeY * sizeZ;}

    public static Builder at(int aMinX, int aMinY, int aMinZ) {return new Builder(aMinX, aMinY, aMinZ);}

    public static final class Builder {
        private final int mMinX, mMinY, mMinZ;
        private int mSizeX = 1, mSizeY = 1, mSizeZ = 1;
        private boolean mWantParts, mWantMeta;

        private Builder(int aMinX, int aMinY, int aMinZ) {mMinX = aMinX; mMinY = aMinY; mMinZ = aMinZ;}

        public Builder size(int aSizeX, int aSizeY, int aSizeZ) {mSizeX = aSizeX; mSizeY = aSizeY; mSizeZ = aSizeZ; return this;}
        public Builder withParts() {mWantParts = true; return this;}
        public Builder withMeta() {mWantMeta = true; return this;}
        public StructureBox build() {return new StructureBox(mMinX, mMinY, mMinZ, mSizeX, mSizeY, mSizeZ, mWantParts, mWantMeta);}
    }
}
