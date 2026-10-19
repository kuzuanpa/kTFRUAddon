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


package cn.kuzuanpa.ktfruaddon.tile.multiblock.parts;

import cn.kuzuanpa.ktfruaddon.api.tile.part.IMultiBlockPart;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.util.UT;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

import java.util.List;

import static gregapi.data.CS.NBT_DESIGN;
import static gregapi.data.CS.NBT_TARGET;

public class CommonMachinePart extends MultiTileEntityBasicMachine implements IMultiTileEntity.IMTE_BreakBlock, IMultiBlockPart {
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        if (aNBT.hasKey(NBT_TARGET)) mTargetPos = IMultiBlockPart.readTargetPosFromNBT(aNBT);
        if (aNBT.hasKey(NBT_DESIGN)) mDesign = UT.Code.unsignB(aNBT.getByte(NBT_DESIGN));
        super.readFromNBT2(aNBT);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        IMultiBlockPart.writeToNBT(aNBT,mTargetPos,mDesign);
    }

    public ChunkCoordinates mTargetPos = null;
    public ITileEntityMultiBlockController mTarget = null;
    public int mDesign = 0;
    @Override public ITileEntityMultiBlockController getTarget2() {return mTarget;}
    @Override public void setTarget(ITileEntityMultiBlockController target) {mTarget = target;}
    @Override public ChunkCoordinates getTargetPos() {return mTargetPos;}
    @Override public void setTargetPos(ChunkCoordinates aCoords){mTargetPos=aCoords;}
    @Override public void setDesign(int aDesign) {this.mDesign = aDesign;}
    @Override public int getDesign(){return mDesign;}

    // MultiTileEntityBasicMachine owns concrete GT implementations, so the interface defaults need explicit delegation here.
    @Override public boolean getStateRunningPossible() {return machineControl().isRunningPossible();}
    @Override public boolean getStateRunningPassively() {return machineControl().isRunning();}
    @Override public boolean getStateRunningActively() {return machineControl().isProcessing();}
    @Override public boolean getStateRunningSuccessfully() {return machineControl().isSuccess();}
    @Override public boolean getStateRunningPowerSaving() {return machineControl().isPowerSaving();}
    @Override public boolean getStateOnOff() {return machineControl().getStateOnOff();}
    @Override public boolean setStateOnOff(boolean aOnOff) {return machineControl().setStateOnOff(aOnOff);}
    @Override public long getProgressValue(byte aSide) {return machineControl().getProgressValue(aSide);}
    @Override public long getProgressMax(byte aSide) {return machineControl().getProgressMax(aSide);}
    @Override public NBTTagCompound getWailaNBT(TileEntity te, NBTTagCompound aNBT) {return IMultiBlockPart.super.getWailaNBT(te, aNBT);}
    @Override public List<String> getWailaBody(List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {return IMultiBlockPart.super.getWailaBody(currentTip, accessor, config);}
    @Override public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (machineControl().handleMeterTool(aTool, aChatReturn)) return 10000;
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public boolean breakBlock() {
        notifyTarget();
        return super.breakBlock();
    }
    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        super.addToolTips(aList, aStack, aF3_H);
    }
    @Override
    public int getLightOpacity(){
        return mDesign==1?255:0;
    }
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.machine.part";
    }

}
