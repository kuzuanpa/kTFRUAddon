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

package cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased;

import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureStatus;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

/**
 * stringBased 结构检查用的部件 key 约定：把 GT6 部件的 (registryID, MTEid) 打包成一个 int，
 * 再拿一个高位标记"这个部件是否已经正确指向本机"。异步模式只比较这个整数，不去碰方块实体。
 */
public final class PartKeys {
    private PartKeys() {}

    /**读不到数据(未加载区块 / 快照盒子外)的返回值。**/
    public static final int UNKNOWN = -1;
    /**bit31 = 已经正确指向本机。**/
    private static final int TARGETED = Integer.MIN_VALUE;
    public static final int MASK = 0x7FFFFFFF;
    private static final int ID_MASK = 0xFFFF;
    /**变更负载里 usage 的高位借用为"允许部件被多台机器共用"(对应 PartPredicate#allowShare), 低 16 位才是 usage。**/
    public static final int FLAG_ALLOW_SHARE = 0x10000;
    public static int usageOf(int aPayload) {return aPayload & 0xFFFF;}
    public static boolean allowShareOf(int aPayload) {return (aPayload & FLAG_ALLOW_SHARE) != 0;}

    public static int pack(int aRegistryId, int aMteId) {return (((aRegistryId + 1) << 16) & MASK) | ((aMteId + 1) & ID_MASK);}
    public static int registryId(int aKey) {return ((aKey & MASK) >>> 16) - 1;}
    public static int mteId(int aKey) {return (aKey & ID_MASK) - 1;}
    public static boolean isTargeted(int aKey) {return (aKey & TARGETED) != 0;}

    /**异步模式下取一个格子的部件 key；看不到就 defer 并返回 {@link #UNKNOWN}。**/
    public static int cellKey(StructureContext aContext, int aX, int aY, int aZ) {
        int status = aContext.view.statusAt(aX, aY, aZ);
        if (status == StructureStatus.UNLOADED || status == StructureStatus.NO_DATA) {aContext.defer(); return UNKNOWN;}
        return aContext.view.partKeyAt(aX, aY, aZ);
    }

    /**默认部件编码(TE → key)：GT6 多方块部件打包 (registryID, MTEid)，并标出是否已指向该控制器。**/
    public static int encode(TileEntity aTileEntity, int aControllerX, int aControllerY, int aControllerZ) {
        int registryId, metaId;
        ChunkCoordinates target;
        if (aTileEntity instanceof MultiTileEntityMultiBlockPart) {
            MultiTileEntityMultiBlockPart part = (MultiTileEntityMultiBlockPart) aTileEntity;
            registryId = part.getMultiTileEntityRegistryID(); metaId = part.getMultiTileEntityID(); target = part.mTargetPos;
        } else if (aTileEntity instanceof cn.kuzuanpa.ktfruaddon.api.tile.part.IMultiBlockPart) {
            cn.kuzuanpa.ktfruaddon.api.tile.part.IMultiBlockPart part = (cn.kuzuanpa.ktfruaddon.api.tile.part.IMultiBlockPart) aTileEntity;
            registryId = part.getMultiTileEntityRegistryID(); metaId = part.getMultiTileEntityID(); target = part.getTargetPos();
        } else return 0;
        int key = pack(registryId, metaId);
        boolean targeted = target != null && target.posX == aControllerX && target.posY == aControllerY && target.posZ == aControllerZ;
        return targeted ? (key | TARGETED) : key;
    }

    /**从变更里的 key 还原 TileDesc（主线程应用变更时用，例如 builder 补方块）。**/
    public static TileDesc descOf(int aKey, int aDesign, int aUsage) {
        if (aKey == 0) return null;
        MultiTileEntityRegistry registry = MultiTileEntityRegistry.getRegistry(registryId(aKey));
        return registry == null ? null : new TileDesc(registry, mteId(aKey), aUsage, aDesign);
    }
}
