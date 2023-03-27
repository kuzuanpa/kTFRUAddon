/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock;

import cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.util.DistillationTowerUtil;
import cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.util.utils;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.FL;
import gregapi.data.LH;
import gregapi.fluid.FluidTankGT;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.util.ST;
import gregapi.util.WD;
import gregtech.tileentity.multiblocks.MultiTileEntityDistillationTower;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;

import java.util.List;

public class TinyDistillTower extends MultiTileEntityDistillationTower {
    public TinyDistillTower() {
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

            for(int i = 1; i < 4; ++i) {
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
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.tinydistilltower.1"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.tinydistilltower.2"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.tinydistilltower.3"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.tinydistilltower.4"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.tinydistilltower.5"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.tinydistilltower.6"));
        super.addToolTips(aList, aStack, aF3_H);
    }
    public void doOutputFluids() {
        for (FluidTankGT tTank : this.mTanksOutput) {
            Fluid tFluid = tTank.fluid();
            if (tFluid != null && tTank.has()) {
                DelegatorTileEntity<TileEntity> tDelegator = null;
                if (FL.is(tFluid, DistillationTowerUtil.OutputFluidsLayer3)) {
                    tDelegator = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 3), this.yCoord + 4, this.getOffsetZN(this.mFacing, 3), this.mFacing, false);
                } else if (FL.is(tFluid, DistillationTowerUtil.OutputFluidsLayer2)) {
                    tDelegator = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 3), this.yCoord + 3, this.getOffsetZN(this.mFacing, 3), this.mFacing, false);
                } else if (FL.is(tFluid, DistillationTowerUtil.OutputFluidsLayer1)) {
                    tDelegator = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 3), this.yCoord + 2, this.getOffsetZN(this.mFacing, 3), this.mFacing, false);
                } else {
                    tDelegator = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 3), this.yCoord + 1, this.getOffsetZN(this.mFacing, 3), this.mFacing, false);
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
        LH.add("ktfru.tooltip.multiblock.tinydistilltower.1", "3x3 Base of Heat Transmitters");
        LH.add("ktfru.tooltip.multiblock.tinydistilltower.2", "3x3x4 of Distillation Tower Parts");
        LH.add("ktfru.tooltip.multiblock.tinydistilltower.3", "Main centered on Side-Bottom of Tower facing outwards");
        LH.add("ktfru.tooltip.multiblock.tinydistilltower.4", "Outputs automatically to the Holes on the Backside");
        LH.add("ktfru.tooltip.multiblock.tinydistilltower.5", "Bottom Hole is for outputting all Items");
        LH.add("ktfru.tooltip.multiblock.tinydistilltower.6", "Input only possible at Bottom Layer of Tower");
    }

}
