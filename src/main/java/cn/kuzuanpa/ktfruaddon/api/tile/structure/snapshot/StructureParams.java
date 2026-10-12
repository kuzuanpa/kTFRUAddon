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
 * 机器在请求时冻结下来的参数。worker 只允许看到这份冻结值，不允许读机器自身的活字段
 * （否则检查期间被扳手转向就会拿到错配的结果）。
 *
 * <p>需要更多字段的机器自己实现这个接口（字段建议 final），或者直接用 {@link Frozen}。</p>
 */
public interface StructureParams {
    short facing();
    int x();
    int y();
    int z();
    /**机器自身结构尺寸（与请求盒子无关）。**/
    int sizeX();
    int sizeY();
    int sizeZ();
    int mapOffsetX();
    int mapOffsetY();
    int mapOffsetZ();

    /**
     * 默认实现：覆盖常见字段组合的不可变参数。
     * 需要额外字段的机器可以直接继承它（例如带"允许自动搭建"标志），只有字段特别多时才自己实现接口。
     */
    class Frozen implements StructureParams {
        private final short mFacing;
        private final int mX, mY, mZ, mSizeX, mSizeY, mSizeZ, mOffsetX, mOffsetY, mOffsetZ;

        public Frozen(short aFacing, int aX, int aY, int aZ, int aSizeX, int aSizeY, int aSizeZ, int aOffsetX, int aOffsetY, int aOffsetZ) {
            mFacing = aFacing; mX = aX; mY = aY; mZ = aZ;
            mSizeX = aSizeX; mSizeY = aSizeY; mSizeZ = aSizeZ;
            mOffsetX = aOffsetX; mOffsetY = aOffsetY; mOffsetZ = aOffsetZ;
        }

        @Override public short facing() {return mFacing;}
        @Override public int x() {return mX;}
        @Override public int y() {return mY;}
        @Override public int z() {return mZ;}
        @Override public int sizeX() {return mSizeX;}
        @Override public int sizeY() {return mSizeY;}
        @Override public int sizeZ() {return mSizeZ;}
        @Override public int mapOffsetX() {return mOffsetX;}
        @Override public int mapOffsetY() {return mOffsetY;}
        @Override public int mapOffsetZ() {return mOffsetZ;}
    }
}
