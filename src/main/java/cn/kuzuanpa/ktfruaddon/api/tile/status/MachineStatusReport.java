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

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.recipe.KortexWorker;
import cn.kuzuanpa.ktfruaddon.api.tile.base.TileEntityBaseControlledMachine;
import cn.kuzuanpa.ktfruaddon.api.tile.base.TileEntityBaseRoom;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ICircuitChangeableTileEntity;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringStructureMachine;
import gregapi.block.multitileentity.IWailaTile;
import gregapi.data.LH;
import gregapi.tileentity.machines.ITileEntityRunningActively;
import gregapi.tileentity.machines.ITileEntityRunningPassively;
import gregapi.tileentity.machines.ITileEntityRunningPossible;
import gregapi.tileentity.machines.ITileEntitySwitchableOnOff;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static gregapi.data.CS.F;

/**一条主原因 + 若干明细行。放大镜 / WAILA / GUI / 鱼右键共用同一个出口。
 *
 * <p>原因优先级：机器覆写 → 被强制停机 → 结构 / 房间 / 条件部件 → 算力 → kortex → 运行态 → 未知。
 * 运行态按 processing → {@code RUNNING}、running → {@code STANDBY}、possible → {@code READY} 判定。
 * 内置来源不用机器自己写；机器只是想给出特殊原因时才覆写 {@link IMachineStatus#getStatusOverride()}。</p>
 *
 * <p>传进来的必须是已经解析到控制器的机器本体（部件请走 {@code MachineControl.of()}）。**/
public final class MachineStatusReport {
    /**没有任何一条来源给出原因，显示层不要输出这一行。**/
    public static final MachineStatusReport UNKNOWN = new MachineStatusReport(null, null);
    public static final String WAILA_KEY = "ktfru.status", WAILA_DETAIL_KEY = "ktfru.status.detail";

    private final MachineStatus mStatus;
    private final String mDetail;

    private MachineStatusReport(MachineStatus aStatus, String aDetail) {
        mStatus = aStatus;
        mDetail = aDetail;
    }

    public static MachineStatusReport of(MachineStatus aStatus) {return of(aStatus, null);}

    public static MachineStatusReport of(MachineStatus aStatus, String aDetail) {
        return aStatus == null ? UNKNOWN : new MachineStatusReport(aStatus, aDetail);
    }

    public static MachineStatusReport of(Object aTileEntity) {
        if (aTileEntity == null) return UNKNOWN;
        IMachineStatus tStatus = aTileEntity instanceof IMachineStatus ? (IMachineStatus) aTileEntity : null;
        String tDetail = tStatus == null ? null : tStatus.getStatusDetail();

        if (tStatus != null && tStatus.getStatusOverride() != null) return of(tStatus.getStatusOverride(), tDetail);
        if (isSwitchedOff(aTileEntity)) return of(MachineStatus.SWITCHED_OFF, tDetail);
        if (!isStructureOkay(aTileEntity)) return of(MachineStatus.STRUCTURE_NOT_FORMED, tDetail);
        if (!isRoomRegistered(aTileEntity)) return of(MachineStatus.ROOM_INVALID, tDetail);
        if (aTileEntity instanceof TileEntityBaseControlledMachine && !((TileEntityBaseControlledMachine) aTileEntity).isControllerAllValid()) return of(MachineStatus.CONDITION_PART_INVALID, tDetail);

        MachineStatus tCompute = computeStatus(aTileEntity);
        if (tCompute != null) return of(tCompute, tDetail);

        MachineStatus tKortex = kortexStatus(tStatus);
        if (tKortex != null) return of(tKortex, tDetail);

        if (isProcessing(aTileEntity)) return of(MachineStatus.RUNNING, tDetail);
        if (isRunning(aTileEntity)) return of(MachineStatus.STANDBY, tDetail);
        if (isRunningPossible(aTileEntity)) return of(MachineStatus.READY, tDetail);
        return UNKNOWN;
    }

    /**把状态写进 WAILA 的服务器 NBT；客户端 body 用 {@link #addWailaLine} 读回来。**/
    public static void writeWaila(Object aTileEntity, NBTTagCompound aNBT) {
        MachineStatusReport tReport = of(aTileEntity);
        if (tReport.isUnknown()) return;
        aNBT.setByte(WAILA_KEY, (byte) tReport.getStatus().ordinal());
        if (tReport.getDetail() != null) aNBT.setString(WAILA_DETAIL_KEY, tReport.getDetail());
    }

    /**把 WAILA NBT 里的状态渲染成「原因 | 明细」一行；没有状态时什么也不加。**/
    public static void addWailaLine(List<String> aList, NBTTagCompound aNBT) {
        if (!aNBT.hasKey(WAILA_KEY)) return;
        int tOrdinal = aNBT.getByte(WAILA_KEY) & 255;
        MachineStatus[] tStatuses = MachineStatus.values();
        if (tOrdinal >= tStatuses.length) return;
        MachineStatusReport tReport = of(tStatuses[tOrdinal], aNBT.hasKey(WAILA_DETAIL_KEY) ? aNBT.getString(WAILA_DETAIL_KEY) : null);
        MachineStatus tStatus = tReport.getStatus();
        if (tStatus == MachineStatus.READY || tStatus == MachineStatus.RUNNING || tStatus == MachineStatus.STANDBY || tStatus == MachineStatus.SWITCHED_OFF) return;
        aList.add(tReport.getDetail() == null ? tReport.getStatus().getLocalized() : tReport.getStatus().getLocalized() + " | " + tReport.getDetail());
    }

    /**状态行 provider：放在 state 行之后、能量行之前即可。**/
    public static final IWailaTile.IWailaInfoProvider WAILA_PROVIDER = new IWailaTile.IWailaInfoProvider() {
        @Override public List<String> getWailaBody(List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
            addWailaLine(currentTip, accessor.getNBTData());
            return currentTip;
        }
    };

    public boolean isUnknown() {return mStatus == null;}

    public MachineStatus getStatus() {return mStatus;}

    /**附加明细行，可能为 null。**/
    public String getDetail() {return mDetail;}

    /**主原因 + 明细的一行文本；未知时为空串，可以直接加进单行显示面。**/
    public String renderStatus() {
        if (isUnknown()) return "";
        String tLine = LH.get(I18nHandler.MACHINE_STATUS) + ": " + mStatus.getLocalized();
        return mDetail == null ? tLine : tLine + " | " + mDetail;
    }

    /**整份报告（主原因一行 + 明细一行），未知时什么都不加。**/
    public void renderLines(List<String> aList) {
        if (isUnknown()) return;
        aList.add(LH.get(I18nHandler.MACHINE_STATUS) + ": " + mStatus.getLocalized());
        if (mDetail != null) aList.add(mDetail);
    }

    public List<String> renderLines() {
        List<String> rList = new ArrayList<>();
        renderLines(rList);
        return rList;
    }

    private static boolean isSwitchedOff(Object aTileEntity) {
        if (aTileEntity instanceof ITileEntitySwitchableOnOff) return !((ITileEntitySwitchableOnOff) aTileEntity).getStateOnOff();
        return false;
    }

    private static boolean isStructureOkay(Object aTileEntity) {
        if (aTileEntity instanceof IStringStructureMachine) return ((IStringStructureMachine) aTileEntity).isStructureOkay();
        if (aTileEntity instanceof TileEntityBase10MultiBlockMachine) return ((TileEntityBase10MultiBlockMachine) aTileEntity).checkStructure(F);
        if (aTileEntity instanceof ITileEntityMultiBlockController) return ((ITileEntityMultiBlockController) aTileEntity).checkStructure(F);
        return true;
    }

    /**房间壳子成形却没能登记进 RoomManager（被一个重叠的房间先占了位）。
     * 房间注册只在服务端发生，客户端不上报；{@code isRoomActive()} 说的是「房间功能有没有升起来」，不当校验用。**/
    private static boolean isRoomRegistered(Object aTileEntity) {
        if (!(aTileEntity instanceof TileEntityBaseRoom)) return true;
        TileEntityBaseRoom tRoom = (TileEntityBaseRoom) aTileEntity;
        World tWorld = tRoom.getWorld();
        if (tWorld == null || tWorld.isRemote) return true;
        return tRoom.getRoomContext() != null;
    }

    private static MachineStatus computeStatus(Object aTileEntity) {
        if (!(aTileEntity instanceof ICircuitChangeableTileEntity)) return null;
        return MachineStatus.fromCompute(((ICircuitChangeableTileEntity) aTileEntity).getComputeUserComponent().getStatus());
    }

    private static MachineStatus kortexStatus(IMachineStatus aStatus) {
        if (aStatus == null) return null;
        MachineStatus rRunning = null;
        for (KortexWorker tWorker : aStatus.getKortexWorkers()) {
            if (tWorker == null) continue;
            MachineStatus tStatus = MachineStatus.fromKortex(tWorker.mState.get());
            if (tStatus == MachineStatus.RUNNING) rRunning = tStatus;
            else if (tStatus != null) return tStatus;
        }
        return rRunning;
    }

    private static boolean isProcessing(Object aTileEntity) {
        if (aTileEntity instanceof ITileEntityRunningActively) return ((ITileEntityRunningActively) aTileEntity).getStateRunningActively();
        return false;
    }

    private static boolean isRunning(Object aTileEntity) {
        if (aTileEntity instanceof ITileEntityRunningPassively) return ((ITileEntityRunningPassively) aTileEntity).getStateRunningPassively();
        return false;
    }

    private static boolean isRunningPossible(Object aTileEntity) {
        if (aTileEntity instanceof ITileEntityRunningPossible) return ((ITileEntityRunningPossible) aTileEntity).getStateRunningPossible();
        return false;
    }
}
