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

import cn.kuzuanpa.ktfruaddon.api.network.PacketFxBlockOutline;
import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.IStructureMachine;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureBox;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureChangeList;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureParams;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureScheduler;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureScratch;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureStatus;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureView;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import cpw.mods.fml.common.network.NetworkRegistry;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.kNetworkHandler;

/**
 * 让一台 stringBased 机器接入快照 API 的适配器。机器侧只需要：
 * <pre>
 *   implements IStringStructureMachine
 *   IStringBaseStructure stringStructure() {return structure;}
 *   boolean isStructureOkay()               {return mStructureOkay;}
 *   void    setStructureOkay(boolean v)     {mStructureOkay = v;}
 *   boolean checkStructure2(...)            {return IStringStructureMachine.trigger(this, aClickedAt, aPlayer, aInventory);}
 * </pre>
 * 结构声明（<code>LayerStructure(...)</code>）与 <code>isInsideStructure</code> 一个字都不用改。
 *
 * <p>分工：周期性的 CHECK 走快照管线（worker 只比 key，主线程分帧 setTarget）；
 * 扳手搭建(SET)/投影(PROJECT)/拆除(RESET) 保持原来的同步路径；
 * 只要结构里有谓词还没迁完（{@code supportsAsync()==false}），就自动退回同步 CHECK。</p>
 */
public interface IStringStructureMachine extends IStructureMachine, ITileEntityMultiBlockController {
    IStringBaseStructure stringStructure();
    boolean isStructureOkay();
    void setStructureOkay(boolean aOkay);
    short getFacing();
    void updateClientData();

    /**触发点：机器在自己的 {@code checkStructure2} 里调这一行就行。**/
    static boolean trigger(IStringStructureMachine aMachine, ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        World world = aMachine.getWorld();
        if (world == null || world.isRemote) return aMachine.isStructureOkay();
        int x = aMachine.getX(), y = aMachine.getY(), z = aMachine.getZ();
        if (!world.blockExists(x, y, z)) return aMachine.isStructureOkay();
        IStringBaseStructure structure = aMachine.stringStructure();
        byte facing = (byte) aMachine.getFacing();

        if (aPlayer != null || aInventory != null) {                      // 扳手搭建: 即时反馈, 沿用同步 SET
            aMachine.setStructureOkay(structure.checkStructure(new StructureContext(aMachine, StructureContext.StringBaseMode.SET, world, x, y, z, facing, aPlayer, aInventory)) == null);
            return aMachine.isStructureOkay();
        }
        if (!structure.supportsAsync()) {                                 // 谓词没迁完: 退回同步 CHECK
            aMachine.setStructureOkay(structure.checkStructure(new StructureContext(aMachine, StructureContext.StringBaseMode.CHECK, world, x, y, z, facing, null, null)) == null);
            return aMachine.isStructureOkay();
        }
        if (!StructureScheduler.isBusy(aMachine)) StructureScheduler.request(aMachine, world, aMachine.describe());
        return aMachine.isStructureOkay();                                // 在飞期间不动状态
    }

    /**快照盒子 = 结构自己给的包围盒 {@link IStringBaseStructure#getWorldBounds}, 再各向外扩 1 格。**/
    @Override default StructureBox structureBox(World aWorld) {
        BoundingBox bounds = stringStructure().getWorldBounds(this, (byte) getFacing());
        // 外扩 1: 给"贴着结构边界"的判据留容错余量, 免得读到盒子外被判 NO_DATA 后永远 DEFER
        int minX = (int) Math.floor(bounds.minX) - 1, minY = (int) Math.floor(bounds.minY) - 1, minZ = (int) Math.floor(bounds.minZ) - 1;
        int maxX = (int) Math.ceil(bounds.maxX) + 1, maxY = (int) Math.ceil(bounds.maxY) + 1, maxZ = (int) Math.ceil(bounds.maxZ) + 1;
        return StructureBox.at(minX, minY, minZ).size(maxX - minX + 1, maxY - minY + 1, maxZ - minZ + 1).withParts().withMeta().build();
    }

    @Override default StructureParams freezeParams() {
        ChunkCoordinates size = stringStructure().getSize();
        return new StructureParams.Frozen(getFacing(), getX(), getY(), getZ(), size.posX, size.posY, size.posZ, 0, 0, 0);
    }

    @Override default int encodePart(TileEntity aTileEntity) {return PartKeys.encode(aTileEntity, getX(), getY(), getZ());}

    @Override default byte check(StructureView aView, StructureParams aParams, StructureScratch aScratch, StructureChangeList aOut) {
        StructureContext ctx = StructureContext.async(this, aView, aOut, getX(), getY(), getZ(), (byte) getFacing());
        ChunkCoordinates failed = stringStructure().checkStructure(ctx);
        if (ctx.deferred) return StructureStatus.DEFER;                   // 有格子看不到: 不判死, 下轮再来
        return failed == null ? StructureStatus.FORM : StructureStatus.NOT_FORM;
    }

    /**主线程逐条应用: 只处理 setTarget / resetTarget / 失败红框; SET(补方块)走的是同步路径, 不在这里。**/
    @Override default boolean applyChange(World aWorld, StructureChangeList aChanges, int aIndex) {
        int kind = aChanges.kind(aIndex);
        int x = aChanges.x(aIndex), y = aChanges.y(aIndex), z = aChanges.z(aIndex);
        if (kind == StructureChangeList.KIND_CUSTOM) {
            kNetworkHandler.sendToAllAround(new PacketFxBlockOutline(new ChunkCoordinates(x, y, z), 0xff0000, 4000, 1.0f), new NetworkRegistry.TargetPoint(aWorld.provider.dimensionId, x, y, z, 80));
            return true;
        }
        if (!aWorld.blockExists(x, y, z)) return false;
        if (kind == StructureChangeList.KIND_RESET_PART) return utils.resetTarget(this, x, y, z, aChanges.design(aIndex));
        TileEntity tile = aWorld.getTileEntity(x, y, z);
        if (tile == null) return false;
        return utils.setTarget(this, tile, aChanges.design(aIndex), PartKeys.usageOf(aChanges.usage(aIndex)), PartKeys.allowShareOf(aChanges.usage(aIndex)));
    }

    @Override default void onCheckFinished(World aWorld, byte aOutcome, StructureChangeList aChanges, int aFailedCount) {
        if (aOutcome == StructureStatus.DEFER) return;                    // 保持现状, 等重试
        boolean okay = aOutcome == StructureStatus.FORM && aFailedCount == 0;
        if (okay != isStructureOkay()) {setStructureOkay(okay); updateClientData();}
    }
}
