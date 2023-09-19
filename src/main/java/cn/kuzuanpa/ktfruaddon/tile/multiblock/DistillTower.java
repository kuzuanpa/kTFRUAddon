/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.multiblock;

import cn.kuzuanpa.ktfruaddon.tile.util.DistillationTowerUtil;
import cn.kuzuanpa.ktfruaddon.tile.util.utils;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
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
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;

import static gregapi.data.CS.*;

public class DistillTower extends TileEntityBase10MultiBlockMachine {
    public DistillTower() {
    }
    @Override
    public boolean checkStructure2() {
        int tX = this.getOffsetXN(this.mFacing);
        int tY = this.yCoord;
        int tZ = this.getOffsetZN(this.mFacing);
        short gRegistry = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);

        if (this.worldObj.blockExists(tX - 1, tY, tZ - 1) && this.worldObj.blockExists(tX + 1, tY, tZ - 1) && this.worldObj.blockExists(tX - 1, tY, tZ + 1) && this.worldObj.blockExists(tX + 1, tY, tZ + 1)) {
            boolean tSuccess = true;
            if (!utils.checkAndSetTarget(this, tX - 1, tY - 1, tZ - 1, 18101, gRegistry, 0, -3)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX, tY - 1, tZ - 1, 18101, gRegistry, 0, -3)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX + 1, tY - 1, tZ - 1, 18101, gRegistry, 0, -3)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX - 1, tY - 1, tZ, 18101, gRegistry, 0, -3)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX, tY - 1, tZ, 18101, gRegistry, 0, -3)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX + 1, tY - 1, tZ, 18101, gRegistry, 0, -3)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX - 1, tY - 1, tZ + 1, 18101, gRegistry, 0, -3)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX, tY - 1, tZ + 1, 18101, gRegistry, 0, -3)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX + 1, tY - 1, tZ + 1, 18101, gRegistry, 0, -3)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX - 1, tY, tZ - 1, 18102, gRegistry, 0, -61)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX, tY, tZ - 1, 18102, gRegistry, this.mFacing == 3 ? 1 : 0, -61)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX + 1, tY, tZ - 1, 18102, gRegistry, 0, -61)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX - 1, tY, tZ, 18102, gRegistry, this.mFacing == 5 ? 1 : 0, -61)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX, tY, tZ, 18102, gRegistry, 0, -61)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX + 1, tY, tZ, 18102, gRegistry, this.mFacing == 4 ? 1 : 0, -61)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX - 1, tY, tZ + 1, 18102, gRegistry, 0, -61)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX, tY, tZ + 1, 18102, gRegistry, this.mFacing == 2 ? 1 : 0, -61)) {
                tSuccess = false;
            }

            if (!utils.checkAndSetTarget(this, tX + 1, tY, tZ + 1, 18102, gRegistry, 0, -61)) {
                tSuccess = false;
            }

            for(int i = 1; i < 8; ++i) {
                if (!utils.checkAndSetTarget(this, tX - 1, tY + i, tZ - 1, 18102, gRegistry, 0, -5)) {
                    tSuccess = false;
                }

                if (!utils.checkAndSetTarget(this, tX, tY + i, tZ - 1, 18102, gRegistry, this.mFacing == 3 ? 1 : 0, -5)) {
                    tSuccess = false;
                }

                if (!utils.checkAndSetTarget(this, tX + 1, tY + i, tZ - 1, 18102, gRegistry, 0, -5)) {
                    tSuccess = false;
                }

                if (!utils.checkAndSetTarget(this, tX - 1, tY + i, tZ, 18102, gRegistry, this.mFacing == 5 ? 1 : 0, -5)) {
                    tSuccess = false;
                }

                if (!utils.checkAndSetTarget(this, tX, tY + i, tZ, 18102, gRegistry, 0, -5)) {
                    tSuccess = false;
                }

                if (!utils.checkAndSetTarget(this, tX + 1, tY + i, tZ, 18102, gRegistry, this.mFacing == 4 ? 1 : 0, -5)) {
                    tSuccess = false;
                }

                if (!utils.checkAndSetTarget(this, tX - 1, tY + i, tZ + 1, 18102, gRegistry, 0, -5)) {
                    tSuccess = false;
                }

                if (!utils.checkAndSetTarget(this, tX, tY + i, tZ + 1, 18102, gRegistry, this.mFacing == 2 ? 1 : 0, -5)) {
                    tSuccess = false;
                }

                if (!utils.checkAndSetTarget(this, tX + 1, tY + i, tZ + 1, 18102, gRegistry, 0, -5)) {
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
            if (tFluid != null && tTank.has()) {
                DelegatorTileEntity<TileEntity> tDelegator = null;
                if (FL.is(tFluid, DistillationTowerUtil.OutputFluidsLayer7)) {
                    tDelegator = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 3), this.yCoord + 7, this.getOffsetZN(this.mFacing, 3), this.mFacing, false);
                } else if (FL.is(tFluid, DistillationTowerUtil.OutputFluidsLayer6)) {
                    tDelegator = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 3), this.yCoord + 6, this.getOffsetZN(this.mFacing, 3), this.mFacing, false);
                } else if (FL.is(tFluid, DistillationTowerUtil.OutputFluidsLayer5)) {
                    tDelegator = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 3), this.yCoord + 5, this.getOffsetZN(this.mFacing, 3), this.mFacing, false);
                } else if (FL.is(tFluid, DistillationTowerUtil.OutputFluidsLayer4)) {
                    tDelegator = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 3), this.yCoord + 4, this.getOffsetZN(this.mFacing, 3), this.mFacing, false);
                } else if (FL.is(tFluid, DistillationTowerUtil.OutputFluidsLayer3)) {
                    tDelegator = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 3), this.yCoord + 3, this.getOffsetZN(this.mFacing, 3), this.mFacing, false);
                } else if (FL.is(tFluid, DistillationTowerUtil.OutputFluidsLayer2)) {
                    tDelegator = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 3), this.yCoord + 2, this.getOffsetZN(this.mFacing, 3), this.mFacing, false);
                } else if (FL.is(tFluid, DistillationTowerUtil.OutputFluidsLayer1)) {
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
        LH.add("ktfru.tooltip.multiblock.distilltower.2", "3x3x8 of Distillation Tower Parts");
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
        return aX >= tX - 1 && aY >= tY - 1 && aZ >= tZ - 1 && aX <= tX + 1 && aY <= tY + 7 && aZ <= tZ + 1;
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
