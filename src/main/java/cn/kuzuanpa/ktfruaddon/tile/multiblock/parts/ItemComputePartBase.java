/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.tile.multiblock.parts;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.item.IComputerItem;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputeUserComponent;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterController;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterUser;
import gregapi.data.LH;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static gregapi.data.CS.*;

/**A compute part whose sources sit in its slots as items; cluster backed items make it a cluster user as well.**/
public abstract class ItemComputePartBase extends ComputePartBase implements IComputerClusterUser {
    public final ComputeUserComponent computeUser = new ComputeUserComponent(this, this, () -> Arrays.asList(getInventory()));

    public ItemComputePartBase(byte nodeCount) {super(nodeCount);}

    @Override
    public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {
        if (aSlot >= nodeCount || isActive()) return F;
        for (int i = 0; i < nodeCount; i++) if (slot(i) == null && aStack.getItem() instanceof IComputerItem) {
            if (i == aSlot) {
                mDisplaySlot[i] = 1;
                updateClientData();
                return T;
            }
        }
        return F;
    }

    @Override
    public void updateComputePower() {
        super.updateComputePower();
        computeUser.invalidate();
    }

    @Override
    public boolean tryStart(long needed) {
        if (needed > mComputePower || isActive()) return false;
        //the cluster reads getState() while allocating
        mState.set(1);
        if (!computeUser.requestCompute(getComputePowerNeeded())) {
            mState.set(0);
            return false;
        }
        if (!IComputerItem.tryStartAll(Arrays.asList(getInventory()), this, true)) {
            computeUser.release();
            mState.set(0);
            return false;
        }
        return true;
    }

    @Override
    public void stop() {
        IComputerItem.stopAll(Arrays.asList(getInventory()), this);
        computeUser.release();
        mState.set(0);
    }

    // IComputerClusterUser
    @Override public IComputerClusterController getController() {return computeUser.getController();}
    @Override public void setController(IComputerClusterController controller) {computeUser.setController(controller);}
    @Override public List<IComputerClusterController> getBackupControllers() {return computeUser.getBackupControllers();}
    @Override public void setBackupControllers(List<IComputerClusterController> list) {computeUser.setBackupControllers(list);}
    @Override public UUID getUUID() {return computeUser.getUUID();}
    @Override public void setUUID(UUID uuid) {computeUser.setUUID(uuid);}
    @Override public byte getState() {return isActive() ? Constants.STATE_NORMAL : Constants.STATE_OFFLINE;}
    @Override public Map<ComputePower, Long> getComputePowerNeeded() {return computeUser.getNeeded();}

    @Override
    public void onComputerPowerReleased() {
        computeUser.onReleased();
        boolean changed = mState.get() != 0;
        mState.set(0);
        if (changed) updateClientData();
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        computeUser.readFromNBT(aNBT);
        computeUser.invalidate();
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        computeUser.writeToNBT(aNBT);
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (isServerSide() && aPlayer.isSneaking() && computeUser.bindFromUSB(aPlayer)) return T;
        return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        super.addToolTips(aList, aStack, aF3_H);
        aList.add(LH.Chat.DGRAY + LH.get(I18nHandler.COMPUTE_TILE_STATUS) + " " + computeUser.getStatusText());
        Map<ComputePower, Long> needed = getComputePowerNeeded();
        if (!needed.isEmpty()) aList.add(LH.Chat.DGRAY + LH.get(I18nHandler.COMPUTE_TILE_NEEDED) + " " + ComputePower.getDescOneLine(needed));
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (aTool.equals(TOOL_magnifyingglass) && aChatReturn != null) {
            aChatReturn.add(LH.get(I18nHandler.COMPUTE_TILE_STATUS) + " " + computeUser.getStatusText());
            Map<ComputePower, Long> needed = getComputePowerNeeded();
            if (!needed.isEmpty()) aChatReturn.add(LH.get(I18nHandler.COMPUTE_TILE_NEEDED) + " " + ComputePower.getDescOneLine(needed));
            aChatReturn.add(LH.get(I18nHandler.COMPUTE_CLUSTER_MSG_JOIN_SUCCESS) + ": " + (getController() == null ? LH.get(I18nHandler.COMPUTE_CLUSTER_3) : getController().getPos().toString()));
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }
}
