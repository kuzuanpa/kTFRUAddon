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

import gregapi.random.IHasWorldAndCoords;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.machines.ITileEntityAdjacentOnOff;
import gregapi.util.UT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import static gregapi.data.CS.*;

/**机器持有的开关/模式记账组件：加一个字段，在机器实现的 GT 接口里把
 * {@code ITileEntitySwitchableOnOff} / {@code ITileEntitySwitchableMode} 委托过来即可。
 * 开关沿用 GT 的 {@code NBT_STOPPED} 语义（true = 停机），所以和 GT 机器互通。**/
public class MachineControlComponent {
    protected final IHasWorldAndCoords mHost;
    protected final Runnable mSyncToClient;
    protected boolean mSwitchedOn = true;
    protected byte mMode = 0;

    /**@param aSyncToClient 状态变化后同步客户端的回调，通常传 {@code this::updateClientData}**/
    public MachineControlComponent(IHasWorldAndCoords aHost, Runnable aSyncToClient) {
        mHost = aHost;
        mSyncToClient = aSyncToClient;
    }

    public MachineControlComponent(IHasWorldAndCoords aHost) {this(aHost, null);}

    public boolean getStateOnOff() {return mSwitchedOn;}

    public boolean setStateOnOff(boolean aOnOff) {
        if (mSwitchedOn != aOnOff) {
            mSwitchedOn = aOnOff;
            syncToClient();
            updateAdjacentOnOff();
        }
        return mSwitchedOn;
    }

    /** Restores a state that came from the server without echoing a packet or touching neighbours. **/
    public void applySyncedState(boolean aOnOff) {
        mSwitchedOn = aOnOff;
    }

    public byte getStateMode() {return mMode;}

    public byte setStateMode(byte aMode) {
        if (mMode != aMode) {
            mMode = aMode;
            syncToClient();
        }
        return mMode;
    }

    public void readFromNBT(NBTTagCompound aNBT) {
        if (aNBT.hasKey(NBT_STOPPED)) mSwitchedOn = !aNBT.getBoolean(NBT_STOPPED);
        if (aNBT.hasKey(NBT_MODE)) mMode = aNBT.getByte(NBT_MODE);
    }

    public void writeToNBT(NBTTagCompound aNBT) {
        UT.NBT.setBoolean(aNBT, NBT_STOPPED, !mSwitchedOn);
        UT.NBT.setNumber(aNBT, NBT_MODE, mMode);
    }

    /**给本机供能的相邻机器（{@link ITileEntityAdjacentOnOff}）跟随本机开关，与 GT 机器同一套行为。**/
    public void updateAdjacentOnOff() {
        if (mHost == null || mHost.getWorld() == null) return;
        for (byte tSide : ALL_SIDES_VALID) {
            DelegatorTileEntity<TileEntity> tDelegator = mHost.getAdjacentTileEntity(tSide);
            if (tDelegator.mTileEntity instanceof ITileEntityAdjacentOnOff) ((ITileEntityAdjacentOnOff) tDelegator.mTileEntity).setAdjacentOnOff(mSwitchedOn);
        }
    }

    protected void syncToClient() {
        if (mSyncToClient != null) mSyncToClient.run();
    }
}
