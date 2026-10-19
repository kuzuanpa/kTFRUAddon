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
import cn.kuzuanpa.ktfruaddon.api.tile.part.IMultiBlockPart;
import cn.kuzuanpa.ktfruaddon.api.tile.status.MachineStatusReport;
import gregapi.code.TagData;
import gregapi.tileentity.data.ITileEntityProgress;
import gregapi.tileentity.energy.ITileEntityEnergyDataCapacitor;
import gregapi.tileentity.machines.*;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.ST;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static gregapi.data.CS.F;
import static gregapi.data.CS.TOOL_unimeter;

/**放大镜 / WAILA / GUI / 停机原因的统一取数口：多方块部件回溯到目标控制器，其余直接读 GT 接口。
 * 控制面不另立接口，机器实现 GT 的开关 / 模式 / 进度 / 电量接口即可。**/
public final class MachineControl {
    private static final MachineControl NONE = new MachineControl(null);
    private final Object mTileEntity;

    private MachineControl(Object aTileEntity) {mTileEntity = aTileEntity;}

    /**@return 空控制（{@link #getTileEntity()} == null）表示这台东西什么都没有。**/
    public static MachineControl of(Object aTileEntity) {
        Object tTarget = resolve(aTileEntity);
        return tTarget == null ? NONE : new MachineControl(tTarget);
    }

    /**部件 → 目标控制器；不是部件就原样返回。**/
    public static Object resolve(Object aTileEntity) {
        if (aTileEntity instanceof MultiTileEntityMultiBlockPart) return ((MultiTileEntityMultiBlockPart) aTileEntity).getTarget(F);
        if (aTileEntity instanceof IMultiBlockPart) return ((IMultiBlockPart) aTileEntity).getTarget(F);
        return aTileEntity;
    }

    /**已解析到的机器本体，供显示层做自己的 instanceof 判断。**/
    public Object getTileEntity() {return mTileEntity;}

    /**只服务我们自己的显示层；GT 覆盖板的放板判断仍由 GT 接口决定。**/
    public boolean has(Capability aCapability) {
        switch (aCapability) {
            case STATUS:           return mTileEntity instanceof ITileEntityRunningPossible || mTileEntity instanceof ITileEntitySwitchableOnOff;
            case SWITCH:           return mTileEntity instanceof ITileEntitySwitchableOnOff;
            case MODE:             return mTileEntity instanceof ITileEntitySwitchableMode;
            case PROGRESS:         return mTileEntity instanceof ITileEntityProgress;
            case ENERGY_CAPACITOR: return mTileEntity instanceof ITileEntityEnergyDataCapacitor;
            case METER:            return mTileEntity instanceof IMachineMeter;
        }
        return false;
    }

    public boolean getStateOnOff() {
        if (mTileEntity instanceof ITileEntitySwitchableOnOff) return ((ITileEntitySwitchableOnOff) mTileEntity).getStateOnOff();
        return true;
    }

    public boolean setStateOnOff(boolean aOnOff) {
        if (mTileEntity instanceof ITileEntitySwitchableOnOff) return ((ITileEntitySwitchableOnOff) mTileEntity).setStateOnOff(aOnOff);
        return getStateOnOff();
    }

    public byte getStateMode() {
        if (mTileEntity instanceof ITileEntitySwitchableMode) return ((ITileEntitySwitchableMode) mTileEntity).getStateMode();
        return 0;
    }

    public byte setStateMode(byte aMode) {
        if (mTileEntity instanceof ITileEntitySwitchableMode) return ((ITileEntitySwitchableMode) mTileEntity).setStateMode(aMode);
        return getStateMode();
    }

    public long getProgressValue(byte aSide) {
        if (mTileEntity instanceof ITileEntityProgress) return ((ITileEntityProgress) mTileEntity).getProgressValue(aSide);
        return 0;
    }

    public long getProgressMax(byte aSide) {
        if (mTileEntity instanceof ITileEntityProgress) return ((ITileEntityProgress) mTileEntity).getProgressMax(aSide);
        return 0;
    }

    public long getEnergyStored(TagData aEnergyType, byte aSide) {
        if (mTileEntity instanceof ITileEntityEnergyDataCapacitor) return ((ITileEntityEnergyDataCapacitor) mTileEntity).getEnergyStored(aEnergyType, aSide);
        return 0;
    }

    public long getEnergyCapacity(TagData aEnergyType, byte aSide) {
        if (mTileEntity instanceof ITileEntityEnergyDataCapacitor) return ((ITileEntityEnergyDataCapacitor) mTileEntity).getEnergyCapacity(aEnergyType, aSide);
        return 0;
    }

    public boolean isEnergyCapacitorType(TagData aEnergyType, byte aSide) {
        if (mTileEntity instanceof ITileEntityEnergyDataCapacitor) return ((ITileEntityEnergyDataCapacitor) mTileEntity).isEnergyCapacitorType(aEnergyType, aSide);
        return false;
    }

    public Collection<TagData> getEnergyCapacitorTypes(byte aSide) {
        if (mTileEntity instanceof ITileEntityEnergyDataCapacitor) return ((ITileEntityEnergyDataCapacitor) mTileEntity).getEnergyCapacitorTypes(aSide);
        return Collections.emptyList();
    }

    public IMeterDetectable.MeterData[] getMeterReceived() {
        if (mTileEntity instanceof IMachineMeter) return ((IMachineMeter) mTileEntity).getMeterReceived();
        return IMachineMeter.NO_READINGS;
    }

    public IMeterDetectable.MeterData[] getMeterEmitted() {
        if (mTileEntity instanceof IMachineMeter) return ((IMachineMeter) mTileEntity).getMeterEmitted();
        return IMachineMeter.NO_READINGS;
    }

    /**The generic electrometer path: machines call this from {@code onToolClick2} and return a positive value when it is true.**/
    public boolean handleMeterTool(String aTool, List<String> aChatReturn) {
        if (aChatReturn == null || !TOOL_unimeter.equals(aTool) || !has(Capability.METER)) return false;
        IMeterDetectable.MeterData[] tReceived = getMeterReceived(), tEmitted = getMeterEmitted();
        IMeterDetectable.sendReceiveEmitMessage(tReceived == null ? null : Arrays.asList(tReceived),
                tEmitted == null ? null : new ArrayList<>(Arrays.asList(tEmitted)), aChatReturn);
        return true;
    }

    public boolean isRunningPossible() {
        if (mTileEntity instanceof ITileEntityRunningPossible) return ((ITileEntityRunningPossible) mTileEntity).getStateRunningPossible();
        return false;
    }

    public boolean isRunning() {
        if (mTileEntity instanceof ITileEntityRunningPassively) return ((ITileEntityRunningPassively) mTileEntity).getStateRunningPassively();
        return false;
    }

    public boolean isProcessing() {
        if (mTileEntity instanceof ITileEntityRunningActively) return ((ITileEntityRunningActively) mTileEntity).getStateRunningActively();
        return false;
    }

    public boolean isSuccess() {
        if (mTileEntity instanceof ITileEntityRunningSuccessfully) return ((ITileEntityRunningSuccessfully) mTileEntity).getStateRunningSuccessfully();
        return false;
    }

    public boolean isPowerSaving() {
        if (mTileEntity instanceof ITileEntityRunningPowerSaving) return ((ITileEntityRunningPowerSaving) mTileEntity).getStateRunningPowerSaving();
        return false;
    }

    public MachineStatusReport getStatusReport() {return MachineStatusReport.of(mTileEntity);}

    /**一行停机原因，未知时为空串，可以直接往放大镜 / WAILA 列表里加。**/
    public String renderStatus() {return getStatusReport().renderStatus();}

    /**状态 API 的统一诊断出口：手持鲨鱼/鱼右键机器打印完整报告（wireshark 梗），已经打印过就返回 true。**/
    public static boolean onFishRightClick(Object aTileEntity, EntityPlayer aPlayer) {
        if (aPlayer == null) return false;
        String tName = ST.regName(aPlayer.getCurrentEquippedItem());
        if (tName == null || !(tName.contains("shark") || tName.contains("fish"))) return false;
        List<String> tReport = MachineControl.of(aTileEntity).getStatusReport().renderLines();
        if (tReport.isEmpty()) return false;
        for (String tLine : tReport) aPlayer.addChatMessage(new ChatComponentText(tLine));
        return true;
    }

    public enum Capability {STATUS, SWITCH, MODE, PROGRESS, ENERGY_CAPACITOR, METER}
}
