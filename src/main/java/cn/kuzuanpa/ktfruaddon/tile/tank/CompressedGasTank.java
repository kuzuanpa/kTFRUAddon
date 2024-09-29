/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.tank;

import cn.kuzuanpa.ktfruaddon.tile.ICompressGasTank;
import gregapi.data.FL;
import gregtech.tileentity.tanks.MultiTileEntityBarrelMetal;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public class CompressedGasTank extends MultiTileEntityBarrelMetal implements ICompressGasTank {
    @Override
    public int fill(ItemStack aStack, FluidStack aFluid, boolean aDoFill) {
        return 0;
    }
    @Override
    public int funnelFill(byte aSide, FluidStack aFluid, boolean aDoFill) {
        return 0;
    }
    protected IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {return null;}

    @Override
    public boolean allowFluid(FluidStack aFluid) {
        return !FL.powerconducting(aFluid) && FL.temperature(aFluid) < mMaterial.mMeltingPoint && FL.gas(aFluid);
    }
    public int fillCompressedGas(FluidStack fluid){return mTank.fill(fluid);}
    @Override public String getTileEntityName() {return "ktfru.multitileentity.tank.barrel.compressGas";}
}
