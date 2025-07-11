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


package cn.kuzuanpa.ktfruaddon.tile.multiblock.machine;

import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import gregapi.data.FL;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.fluid.FluidTankGT;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.machines.ITileEntityAdjacentOnOff;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
import gregapi.util.ST;
import gregapi.util.WD;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;

import static gregapi.data.CS.*;

public class DistillTower extends TileEntityBase10MultiBlockMachine {
    public DistillTower() {
    }
    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        int tX = this.getOffsetXN(this.mFacing);
        int tY = this.yCoord;
        int tZ = this.getOffsetZN(this.mFacing);

        if (this.worldObj.blockExists(tX - 1, tY, tZ - 1) && this.worldObj.blockExists(tX + 1, tY, tZ - 1) && this.worldObj.blockExists(tX - 1, tY, tZ + 1) && this.worldObj.blockExists(tX + 1, tY, tZ + 1)) {
            boolean tSuccess = true;
            if (!utils.checkAndSetTarget(this, tX - 1, tY - 1, tZ - 1, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18101, 0, -3)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX, tY - 1, tZ - 1, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18101, 0, -3)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX + 1, tY - 1, tZ - 1, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18101, 0, -3)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX - 1, tY - 1, tZ, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18101, 0, -3)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX, tY - 1, tZ, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18101, 0, -3)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX + 1, tY - 1, tZ, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18101, 0, -3)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX - 1, tY - 1, tZ + 1, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18101, 0, -3)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX, tY - 1, tZ + 1, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18101, 0, -3)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX + 1, tY - 1, tZ + 1, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18101, 0, -3)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX - 1, tY, tZ - 1, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18102, 0, -61)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX, tY, tZ - 1, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18102, this.mFacing == 3 ? 1 : 0, -61)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX + 1, tY, tZ - 1, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18102, 0, -61)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX - 1, tY, tZ, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18102, this.mFacing == 5 ? 1 : 0, -61)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX, tY, tZ, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18102, 0, -61)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX + 1, tY, tZ, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18102, this.mFacing == 4 ? 1 : 0, -61)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX - 1, tY, tZ + 1, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18102, 0, -61)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX, tY, tZ + 1, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18102, this.mFacing == 2 ? 1 : 0, -61)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX + 1, tY, tZ + 1, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18102, 0, -61)) {
                tSuccess = false;
            }

            for(int i = 1; i < 9; ++i) {
                if (!utils.checkAndSetTarget(this, tX - 1, tY + i, tZ - 1, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18102, 0, -5)) {
                    tSuccess = false;
                }

                if (!utils.checkAndSetTarget(this, tX, tY + i, tZ - 1, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18102, this.mFacing == 3 ? 1 : 0, -5)) {
                    tSuccess = false;
                }

                if (!utils.checkAndSetTarget(this, tX + 1, tY + i, tZ - 1, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18102, 0, -5)) {
                    tSuccess = false;
                }

                if (!utils.checkAndSetTarget(this, tX - 1, tY + i, tZ, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18102, this.mFacing == 5 ? 1 : 0, -5)) {
                    tSuccess = false;
                }

                if (!utils.checkAndSetTarget(this, tX, tY + i, tZ, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18102, 0, -5)) {
                    tSuccess = false;
                }

                if (!utils.checkAndSetTarget(this, tX + 1, tY + i, tZ, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18102, this.mFacing == 4 ? 1 : 0, -5)) {
                    tSuccess = false;
                }

                if (!utils.checkAndSetTarget(this, tX - 1, tY + i, tZ + 1, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18102, 0, -5)) {
                    tSuccess = false;
                }

                if (!utils.checkAndSetTarget(this, tX, tY + i, tZ + 1, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18102, this.mFacing == 2 ? 1 : 0, -5)) {
                    tSuccess = false;
                }

                if (!utils.checkAndSetTarget(this, tX + 1, tY + i, tZ + 1, aClickedAt, aPlayer, aInventory, GTTileEntityRegistry.gregtech, (short)18102, 0, -5)) {
                    tSuccess = false;
                }
            }

            return tSuccess;
        } else {
            return this.mStructureOkay;
        }
    }

    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get("gt.lang.structure") + ":");
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.distilltower.1"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.distilltower.2"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.distilltower.3"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.distilltower.4"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.distilltower.5"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.distilltower.6"));
        super.addToolTips(aList, aStack, aF3_H);
    }
    public void doOutputFluids() {
        for (FluidTankGT tTank : this.mTanksOutput) {
            Fluid tFluid = tTank.fluid();
            if (tFluid != null && tTank.has()&&mLastRecipe!=null) {
                DelegatorTileEntity<TileEntity> tDelegator = null;
                if (mLastRecipe.mFluidOutputs.length>7&&FL.is(tFluid, mLastRecipe.mFluidOutputs[7].getUnlocalizedName().replaceFirst("fluid.", ""))) {
                    tDelegator = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 3), this.yCoord + 8, this.getOffsetZN(this.mFacing, 3), this.mFacing, false);
                }
                  else if (mLastRecipe.mFluidOutputs.length>6&&FL.is(tFluid, mLastRecipe.mFluidOutputs[6].getUnlocalizedName().replaceFirst("fluid.", ""))) {
                    tDelegator = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 3), this.yCoord + 7, this.getOffsetZN(this.mFacing, 3), this.mFacing, false);
                } else if (mLastRecipe.mFluidOutputs.length>5&&FL.is(tFluid, mLastRecipe.mFluidOutputs[5].getUnlocalizedName().replaceFirst("fluid.", ""))) {
                    tDelegator = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 3), this.yCoord + 6, this.getOffsetZN(this.mFacing, 3), this.mFacing, false);
                } else if (mLastRecipe.mFluidOutputs.length>4&&FL.is(tFluid, mLastRecipe.mFluidOutputs[4].getUnlocalizedName().replaceFirst("fluid.", ""))) {
                    tDelegator = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 3), this.yCoord + 5, this.getOffsetZN(this.mFacing, 3), this.mFacing, false);
                } else if (mLastRecipe.mFluidOutputs.length>3&&FL.is(tFluid, mLastRecipe.mFluidOutputs[3].getUnlocalizedName().replaceFirst("fluid.", ""))) {
                    tDelegator = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 3), this.yCoord + 4, this.getOffsetZN(this.mFacing, 3), this.mFacing, false);
                } else if (mLastRecipe.mFluidOutputs.length>2&&FL.is(tFluid, mLastRecipe.mFluidOutputs[2].getUnlocalizedName().replaceFirst("fluid.", ""))) {
                    tDelegator = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 3), this.yCoord + 3, this.getOffsetZN(this.mFacing, 3), this.mFacing, false);
                } else if (mLastRecipe.mFluidOutputs.length>1&&FL.is(tFluid, mLastRecipe.mFluidOutputs[1].getUnlocalizedName().replaceFirst("fluid.", ""))) {
                    tDelegator = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 3), this.yCoord + 2, this.getOffsetZN(this.mFacing, 3), this.mFacing, false);
                } else if (mLastRecipe.mFluidOutputs.length>0&&FL.is(tFluid, mLastRecipe.mFluidOutputs[0].getUnlocalizedName().replaceFirst("fluid.", ""))) {
                    tDelegator = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 3), this.yCoord + 1, this.getOffsetZN(this.mFacing, 3), this.mFacing, false);
                } else {
                    tDelegator = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 3), this.yCoord, this.getOffsetZN(this.mFacing, 3), this.mFacing, false);
                }

                if (FL.move(tTank, tDelegator) > 0L) {
                    this.updateInventory();
                }
            }
        }

    }    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.distillationtower";
    }

    static {
        LH.add("ktfru.tooltip.multiblock.distilltower.1", "3x3 Base of Heat Transmitters");
        LH.add("ktfru.tooltip.multiblock.distilltower.2", "3x3x9 of Distillation Tower Parts");
        LH.add("ktfru.tooltip.multiblock.distilltower.3", "Main centered on Side-Bottom of Tower facing outwards");
        LH.add("ktfru.tooltip.multiblock.distilltower.4", "Outputs automatically to the Holes on the Backside");
        LH.add("ktfru.tooltip.multiblock.distilltower.5", "Bottom Hole is for outputting all Items");
        LH.add("ktfru.tooltip.multiblock.distilltower.6", "Input only possible at Bottom Layer of Tower");
    }

    @Override
    public void addToolTipsSided(List<String> aList, ItemStack aStack, boolean aF3_H) {
        String tSideNames = ""; boolean temp = F;
        if (mEnergyTypeAccepted != TD.Energy.TU) {
            for (byte tSide : ALL_SIDES_VALID) if (FACE_CONNECTED[tSide][mEnergyInputs])    {tSideNames += (temp?", ":"")+LH.get(LH.FACES[tSide]); temp = T;}
            LH.addEnergyToolTips(this, aList, mEnergyTypeAccepted, null, tSideNames, null);
        }
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        int tX = getOffsetXN(mFacing), tY = yCoord, tZ = getOffsetZN(mFacing);
        return aX >= tX - 1 && aY >= tY - 1 && aZ >= tZ - 1 && aX <= tX + 1 && aY <= tY + 8 && aZ <= tZ + 1;
    }

    @Override
    public void updateAdjacentToggleableEnergySources() {
        int tX = getOffsetXN(mFacing) - 1, tZ = getOffsetZN(mFacing) - 1;
        for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) {
            DelegatorTileEntity<TileEntity> tDelegator = WD.te(worldObj, tX+i, yCoord-2, tZ+j, SIDE_TOP, F);
            if (tDelegator.mTileEntity instanceof ITileEntityAdjacentOnOff && tDelegator.mTileEntity instanceof ITileEntityEnergy && ((ITileEntityEnergy)tDelegator.mTileEntity).isEnergyEmittingTo(mEnergyTypeAccepted, tDelegator.mSideOfTileEntity, T)) {
                ((ITileEntityAdjacentOnOff)tDelegator.mTileEntity).setAdjacentOnOff(getStateOnOff());
            }
        }
    }

    @Override
    public void doOutputItems() {
        ST.moveAll(delegator(FACING_TO_SIDE[mFacing][mItemAutoOutput]), WD.te(worldObj, getOffsetXN(mFacing, 3), yCoord, getOffsetZN(mFacing, 3), mFacing, F));
    }

    @Override public DelegatorTileEntity<IInventory> getItemInputTarget(byte aSide) {return null;}
    @Override public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) {return null;}
    @Override public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte aSide) {return null;}
    @Override public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte aSide, Fluid aOutput) {return null;}

}
