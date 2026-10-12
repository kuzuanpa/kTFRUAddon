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

package cn.kuzuanpa.ktfruaddon.tile.machine;

import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputeUserComponent;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ICircuitChangeableTileEntity;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterController;
import cn.kuzuanpa.ktfruaddon.api.tile.util.kTileNBT;
import gregapi.old.Textures;
import gregapi.render.IIconContainer;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static gregapi.data.CS.PX_P;
import static gregapi.data.CS.SIDES_VALID;

public class MachineCodeUtil extends MultiTileEntityBasicMachine implements ICircuitChangeableTileEntity {
    public boolean mWantsCompute = false;
    protected Map<ComputePower, Long> mRequired = new HashMap<>();
    public final ComputeUserComponent computeUser = new ComputeUserComponent(this, this, this::getComputers);
    protected List<ItemStack> computerList = new ArrayList<>();

    // NBT
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        mWantsCompute = aNBT.getBoolean(kTileNBT.COMPUTE_POWER_ACTIVE);
        mRequired = ICircuitChangeableTileEntity.readRequiredCompute(aNBT);
        setComputers(ICircuitChangeableTileEntity.loadCircuitInfo(aNBT));
        computeUser.readFromNBT(aNBT);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setBoolean(aNBT, kTileNBT.COMPUTE_POWER_ACTIVE, mWantsCompute);
        aNBT.setTag(kTileNBT.COMPUTE_POWER_REQUIRED, ICircuitChangeableTileEntity.writeRequiredCompute(mRequired));
        ICircuitChangeableTileEntity.saveCircuitInfo(aNBT, getComputers());
        computeUser.writeToNBT(aNBT);
    }

    @Override
    public NBTTagCompound writeItemNBT2(NBTTagCompound aNBT) {
        UT.NBT.setBoolean(aNBT, kTileNBT.COMPUTE_POWER_ACTIVE, mWantsCompute);
        ICircuitChangeableTileEntity.saveCircuitInfo(aNBT, getComputers());
        return super.writeItemNBT2(aNBT);
    }

    // Tick
    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if (!aIsServerSide) return;
        if (!mWantsCompute) {
            stop();
            setComputeRunning(false);
            return;
        }
        setComputeRunning(tryStart());
    }

    protected void setComputeRunning(boolean running) {
        if (getStateOnOff() == running) return;
        setStateOnOff(running);
        updateClientData();
    }

    // Interaction
    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide()) return false;
        if (aPlayer.isSneaking() && computeUser.bindFromUSB(aPlayer)) return true;
        mWantsCompute = !mWantsCompute;
        if (mWantsCompute) setComputeRunning(tryStart());
        else {
            stop();
            setComputeRunning(false);
        }
        updateClientData();
        return true;
    }

    // Compute
    @Override public @NotNull Map<ComputePower, Long> getComputePowerRequired() {return mRequired;}
    @Override public @NotNull List<ItemStack> getComputers() {return computerList;}
    @Override public void setComputers(List<ItemStack> computers) {
        computerList = computers == null ? new ArrayList<ItemStack>() : computers;
        computeUser.invalidate();
    }
    @Override public ComputeUserComponent getComputeUserComponent() {return computeUser;}

    @Override public IComputerClusterController getController() {return computeUser.getController();}
    @Override public void setController(IComputerClusterController controller) {computeUser.setController(controller);}
    @Override public List<IComputerClusterController> getBackupControllers() {return computeUser.getBackupControllers();}
    @Override public void setBackupControllers(List<IComputerClusterController> list) {computeUser.setBackupControllers(list);}
    @Override public UUID getUUID() {return computeUser.getUUID();}
    @Override public void setUUID(UUID uuid) {computeUser.setUUID(uuid);}
    @Override public byte getState() {return mWantsCompute ? Constants.STATE_NORMAL : Constants.STATE_OFFLINE;}
    @Override public void onComputerPowerReleased() {computeUser.onReleased(); setComputeRunning(false);}

    // GUI / Tooltip
    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        super.addToolTips(aList, aStack, aF3_H);
        addCircuitTooltip(aList, aStack, aF3_H);
    }

    // Icons / Meta
    @Override public String getTileEntityName() {return "ktfru.multitileentity.machine.code";}
    @Override public int getRenderPasses2(Block aBlock, boolean[] aShouldSideBeRendered) {return 2;}
    @Override public boolean[] getValidSides() {return SIDES_VALID;}

    @Override
    public boolean setBlockBounds2(Block aBlock, int aRenderPass, boolean[] aShouldSideBeRendered) {
        switch (aRenderPass) {
            case 0:
                return box(aBlock, PX_P[0] + 0.001, PX_P[0], PX_P[0] + 0.001, PX_P[16] - 0.001, PX_P[5], PX_P[16] - 0.001);
            case 1:
                return box(aBlock, PX_P[0] + 0.0001, PX_P[6], PX_P[0], PX_P[16], PX_P[16], PX_P[16]);
        }
        return true;
    }

    public static IIconContainer
            sTextureCommon= new Textures.BlockIcons.CustomIcon("machines/cruciblemodel/common"),
            sTextureCommosn= new Textures.BlockIcons.CustomIcon("machines/cruciblemodel/commsson");
}
