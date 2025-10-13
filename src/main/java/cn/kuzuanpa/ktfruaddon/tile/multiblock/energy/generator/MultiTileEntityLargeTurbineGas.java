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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.generator;

import cn.kuzuanpa.ktfruaddon.item.items.itemTurbine;
import cn.kuzuanpa.ktfruaddon.api.material.prefix.prefixList;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.FL;
import gregapi.data.FM;
import gregapi.data.LH;
import gregapi.data.LH.Chat;
import gregapi.fluid.FluidTankGT;
import gregapi.oredict.OreDictMaterial;
import gregapi.recipes.Recipe;
import gregapi.recipes.Recipe.RecipeMap;
import gregapi.util.ST;
import gregapi.util.UT;
import gregapi.util.WD;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.util.List;

import static gregapi.data.CS.*;

public class MultiTileEntityLargeTurbineGas extends MultiTileEntityLargeTurbine {
	public FluidTankGT mInputTank = new FluidTankGT(), mTanksOutput[] = new FluidTankGT[] {new FluidTankGT(), new FluidTankGT(), new FluidTankGT()};
	public FluidTankGT[] mTanks = new FluidTankGT[] {mInputTank, mTanksOutput[0], mTanksOutput[1], mTanksOutput[2]};
	public RecipeMap mRecipes = FM.Gas;
	public Recipe mLastRecipe = null;

	@Override
	public void readFromNBT2(NBTTagCompound aNBT) {
		super.readFromNBT2(aNBT);
		if (aNBT.hasKey(NBT_FUELMAP)) mRecipes = RecipeMap.RECIPE_MAPS.get(aNBT.getString(NBT_FUELMAP));
		for (int i = 0; i < mTanksOutput.length; i++) mTanksOutput[i].readFromNBT(aNBT, NBT_TANK+"."+i).setCapacity(mRateMax*16);
		mInputTank.readFromNBT(aNBT, NBT_TANK).setCapacity(mRateMax*4);
	}
	
	@Override
	public void writeToNBT2(NBTTagCompound aNBT) {
		super.writeToNBT2(aNBT);
		for (int i = 0; i < mTanksOutput.length; i++) mTanksOutput[i].writeToNBT(aNBT, NBT_TANK+"."+i);
		mInputTank.writeToNBT(aNBT, NBT_TANK);
	}

	@Override
	public void transformTurbineItem() {
		int meta = slot(0).getItemDamage();
		mTurbineDurability = itemTurbine.getTurbineDurability(OreDictMaterial.get(meta));
		mTurbineEfficiency = itemTurbine.getTurbineEfficiency(OreDictMaterial.get(meta));
		usingCheckedTurbine = prefixList.turbineLargeGasChecked.contains(slot(0));
		ST.set(slot(0), prefixList.turbineLargeGasDamaged.mat(OreDictMaterial.get(meta),1));
	}

	static {
		LH.add("gt.tooltip.multiblock.gasturbine.1", "3x3x4 of 35 ");
		LH.add("gt.tooltip.multiblock.gasturbine.2", "Main centered on the 3x3 facing outwards");
		LH.add("gt.tooltip.multiblock.gasturbine.3", "Input only possible at frontal 3x3");
		LH.add("gt.tooltip.multiblock.gasturbine.4", "Exhaust Gas has to be removed!");
	}
	
	@Override
	public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
		aList.add(Chat.CYAN     + LH.get(LH.STRUCTURE) + ":");
		aList.add(Chat.WHITE    + LH.get("gt.tooltip.multiblock.gasturbine.1") + MultiTileEntityRegistry.getRegistry(getMultiTileEntityRegistryID()).getLocal(mTurbineWalls));
		aList.add(Chat.WHITE    + LH.get("gt.tooltip.multiblock.gasturbine.2"));
		aList.add(Chat.WHITE    + LH.get("gt.tooltip.multiblock.gasturbine.3"));
		aList.add(Chat.ORANGE   + LH.get("gt.tooltip.multiblock.gasturbine.4"));
		super.addToolTips(aList, aStack, aF3_H);
	}

	
	@Override
	public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
		long rReturn = super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
		if (rReturn > 0) return rReturn;
		
		if (isClientSide()) return 0;
		
		if (aTool.equals(TOOL_plunger)) {
			if (mTanksOutput[0].has()) return GarbageGT.trash(mTanksOutput[0]);
			if (mTanksOutput[1].has()) return GarbageGT.trash(mTanksOutput[1]);
			if (mTanksOutput[2].has()) return GarbageGT.trash(mTanksOutput[2]);
			return GarbageGT.trash(mInputTank);
		}
		
		return 0;
	}
	
	@Override
	public void doConversion(long aTimer) {
		for (FluidTankGT tank : mTanksOutput) if (tank.has()) {
			ChunkCoordinates pos = getOffset(OPOS[mFacing], 4);
			pos.posY-=1;
			FL.move(tank, WD.te(worldObj,pos,mFacing,false));
			if (FL.gas(tank) && !WD.hasCollide(worldObj, pos)) tank.setEmpty();
		}
		if (!mForcedStopped && mInputTank.has() && slotHas(0) && mTanksOutput[0].underHalf() && mTanksOutput[1].underHalf() && mTanksOutput[2].underHalf()) {
			Recipe tRecipe = mRecipes.findRecipe(this, mLastRecipe, F, Integer.MAX_VALUE, NI, mInputTank.AS_ARRAY, ZL_IS);
			if (tRecipe != null) {
				if(isTurbineAboutToBreak&&getRandomNumber(10)==1)UT.Sounds.send(worldObj, SFX.IC_MACHINE_INTERRUPT, 1, 1, getCoords());
				mLastRecipe = tRecipe;
				if (tRecipe.mEUt < 0 && tRecipe.mDuration > 0) {
					int tMax = UT.Code.bindInt(UT.Code.divup((long) ((mOverclock? mRate*mTurbineEfficiency : mRate*Math.min(mTurbineEfficiency,2)) - mEnergyStored), -tRecipe.mEUt * tRecipe.mDuration));
					int tParallel = tRecipe.isRecipeInputEqual(tMax, mInputTank.AS_ARRAY, ZL_IS);
					if (tParallel < tMax) mInputTank.setEmpty();
					if (tParallel > 0) {
						mEnergyStored -= tParallel * tRecipe.mEUt * tRecipe.mDuration;
						damageTurbine(tParallel * tRecipe.mEUt * tRecipe.mDuration,TURBINE_GAS);
						for (int i = 0; i < tRecipe.mFluidOutputs.length && i < mTanksOutput.length; i++) {
							if (!mTanksOutput[i].fillAll(tRecipe.mFluidOutputs[i], tParallel)) {
								mEnergyStored = 0;
							}
						}
						return;
					}
				}
			}
		}

		if (mEnergyStored < 0) mEnergyStored = 0;
	}
	
	@Override protected IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {return !mForcedStopped && mRecipes.containsInput(aFluidToFill, this, NI) ? mInputTank : null;}
	@Override protected IFluidTank[] getFluidTanks2(byte aSide) {return mTanks;}
	
	@Override
	protected IFluidTank getFluidTankDrainable2(byte aSide, FluidStack aFluidToDrain) {
		if (aFluidToDrain == null) {
			for (int i=0,j; i < mTanksOutput.length; i++) if (mTanksOutput[j = ((int)(SERVER_TIME/20)+i) % mTanksOutput.length].has()) return mTanksOutput[j];
		} else {
			for (int i = 0; i < mTanksOutput.length; i++) if (mTanksOutput[i].contains(aFluidToDrain)) return mTanksOutput[i];
		}
		return null;
	}

	@Override
	public boolean isItemValidForSlot(int aSlot, ItemStack aStack) {
		return super.isItemValidForSlot(aSlot, aStack) && (prefixList.turbineLargeGas.contains(aStack) || prefixList.turbineLargeGasChecked.contains(aStack));
	}

	@Override public String getTileEntityName() {return "ktfru.multitileentity.multiblock.turbine.gas";}

}
