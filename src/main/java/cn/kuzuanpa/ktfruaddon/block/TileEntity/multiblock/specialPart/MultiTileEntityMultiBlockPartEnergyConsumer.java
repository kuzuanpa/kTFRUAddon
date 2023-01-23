package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.specialPart;

import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.util.UT;
import gregapi.util.WD;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

import java.util.List;

public class MultiTileEntityMultiBlockPartEnergyConsumer extends MultiTileEntityBasicMachine {
    public ChunkCoordinates mTargetPos = null;
    public ITileEntityMultiBlockController mTarget = null;

public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey("gt.target")) {
            this.mTargetPos = new ChunkCoordinates(UT.Code.bindInt(aNBT.getLong("gt.target.x")), UT.Code.bindInt(aNBT.getLong("gt.target.y")), UT.Code.bindInt(aNBT.getLong("gt.target.z")));
        }
    }
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        if (this.mTargetPos != null) {
            UT.NBT.setBoolean(aNBT, "gt.target", true);
            UT.NBT.setNumber(aNBT, "gt.target.x", (long)this.mTargetPos.posX);
            UT.NBT.setNumber(aNBT, "gt.target.y", (long)this.mTargetPos.posY);
            UT.NBT.setNumber(aNBT, "gt.target.z", (long)this.mTargetPos.posZ);
        }
    }

    public ITileEntityMultiBlockController getTarget(boolean aCheckValidity) {
        if (this.mTargetPos == null) {
            return null;
        } else {
            if (this.mTarget == null || this.mTarget.isDead()) {
                this.mTarget = null;
                if (this.worldObj.blockExists(this.mTargetPos.posX, this.mTargetPos.posY, this.mTargetPos.posZ)) {
                    TileEntity tTarget = WD.te(this.worldObj, this.mTargetPos, true);
                    if (tTarget instanceof ITileEntityMultiBlockController && ((ITileEntityMultiBlockController)tTarget).isInsideStructure(this.xCoord, this.yCoord, this.zCoord)) {
                        this.mTarget = (ITileEntityMultiBlockController)tTarget;
                    } else {
                        this.mTargetPos = null;
                    }
                }
            }

            return aCheckValidity && this.mTarget != null && !this.mTarget.checkStructure(false) ? null : this.mTarget;
        }
    }

    public void setTarget(ITileEntityMultiBlockController aTarget, int aDesign, int aMode) {
        this.mTarget = aTarget;
        this.mTargetPos = this.mTarget == null ? null : this.mTarget.getCoords();
    }
    public boolean breakBlock() {
        ITileEntityMultiBlockController tTarget = this.getTarget(false);
        if (tTarget != null) {
            this.mTargetPos = null;
            this.mTarget = null;
            tTarget.onStructureChange();
        }

        return false;
    }
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.machine.part.electric";
    }
    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
    }
    @Override
    public boolean allowRightclick(Entity aEntity) {
        return false;
    }
}
