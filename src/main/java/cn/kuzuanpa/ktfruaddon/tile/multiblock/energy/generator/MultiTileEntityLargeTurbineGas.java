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

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.material.prefix.prefixList;
import cn.kuzuanpa.ktfruaddon.api.recipe.IKortexHandler;
import cn.kuzuanpa.ktfruaddon.api.recipe.KortexWorker;
import cn.kuzuanpa.ktfruaddon.item.items.itemTurbine;
import cpw.mods.fml.common.FMLLog;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.FL;
import gregapi.data.FM;
import gregapi.data.LH;
import gregapi.data.LH.Chat;
import gregapi.fluid.FluidTankGT;
import gregapi.oredict.OreDictMaterial;
import gregapi.recipes.Recipe.RecipeMap;
import gregapi.util.ST;
import gregapi.util.WD;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import org.apache.logging.log4j.Level;

import java.util.List;

import static gregapi.data.CS.*;

public class MultiTileEntityLargeTurbineGas extends MultiTileEntityLargeTurbine implements IKortexHandler {
	protected KortexWorker kortex;
	public IWailaInfoProvider tankInfoInput = new InfoTank(LH.get(I18nHandler.INPUT), "", ZL_FT);
	public IWailaInfoProvider tankInfoOutput = new InfoTank(LH.get(I18nHandler.OUTPUT), "", ZL_FT);

	@Override
	public void readFromNBT2(NBTTagCompound aNBT) {
		super.readFromNBT2(aNBT);
		RecipeMap mRecipes = FM.Gas;
		if (aNBT.hasKey(NBT_FUELMAP)) mRecipes = RecipeMap.RECIPE_MAPS.get(aNBT.getString(NBT_FUELMAP));
		kortex = new KortexWorker(this, 0, mRecipes, true).setTankSize(mRateMax*4,mRateMax*16);
		tankInfoInput = new InfoTank(LH.get(I18nHandler.INPUT), "", kortex.fluidInputs);
		tankInfoOutput = new InfoTank(LH.get(I18nHandler.OUTPUT), "", kortex.fluidOutputs);
		kortex.readFromNBT(aNBT);
	}

	@Override
	public void writeToNBT2(NBTTagCompound aNBT) {
		super.writeToNBT2(aNBT);
		if(kortex!=null) kortex.writeToNBT(aNBT);
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

	public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
		if (!isServerSide())return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);

		ItemStack equippedItem=aPlayer.getCurrentEquippedItem();
		if(equippedItem==null)return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);

		FMLLog.log(Level.FATAL, ST.regName(equippedItem));
		if (ST.regName(equippedItem).contains("shark")||ST.regName(equippedItem).contains("fish")) {
			aPlayer.addChatMessage(new ChatComponentText("Kortex"+LH.get(LH.STATE)+": "+kortex.getLocalizedState()));
			return true;
		}

		openGUI(aPlayer, aSide);
		return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
	}

	@Override
	public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
		if(aTool.equals(TOOL_magnifyingglass)){
		}
		long rReturn = super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
		if (rReturn > 0) return rReturn;
		
		if (isClientSide()) return 0;
		
		if (aTool.equals(TOOL_plunger)) {
			kortex.resetStatus();
			return 1;
		}

		
		return 0;
	}
	
	@Override
	public void doConversion(long aTimer) {
		if (!mForcedStopped){
			kortex.mEnergyCapacity = (long) (mOverclock? mRate*mTurbineEfficiency : mRate*Math.min(mTurbineEfficiency,2)) * 2;
			kortex.run();
		}
	}
	
	@Override protected IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {return !mForcedStopped && kortex.recipeMap.containsInput(aFluidToFill, this, NI) ? kortex.fluidInputs[0] : null;}

	@Override
	protected IFluidTank getFluidTankDrainable2(byte aSide, FluidStack aFluidToDrain) {
		if (aFluidToDrain == null) {
			for (int i=0,j; i < kortex.fluidOutputs.length; i++) if (kortex.fluidOutputs[j = ((int)(SERVER_TIME/20)+i) % kortex.fluidOutputs.length].has()) return kortex.fluidOutputs[j];
		} else {
			for (int i = 0; i < kortex.fluidOutputs.length; i++) if (kortex.fluidOutputs[i].contains(aFluidToDrain)) return kortex.fluidOutputs[i];
		}
		return null;
	}

	@Override
	public boolean isItemValidForSlot(int aSlot, ItemStack aStack) {
		return super.isItemValidForSlot(aSlot, aStack) && (prefixList.turbineLargeGas.contains(aStack) || prefixList.turbineLargeGasChecked.contains(aStack));
	}
	@Override public boolean getStateRunningPossible() {return super.getStateRunningPossible() && !kortex.fluidInputs[0].isEmpty();}

	@Override public String getTileEntityName() {return "ktfru.multitileentity.multiblock.turbine.gas";}

	@Override
	public List<IWailaInfoProvider> getWailaInfos(List<IWailaInfoProvider> current) {
		super.getWailaInfos(current);
		current.add(tankInfoInput);
		current.add(tankInfoOutput);
		return current;
	}

	@Override
	public void onRecipeFinish(int kortexID, long eut,long duration, long parallel) {
		damageTurbine(parallel * eut * duration,TURBINE_GAS);
	}

	@Override
	public void receiveOutputs(FluidTankGT[] fluidTanks, ItemStack[] items) {
		for (FluidTankGT tank : fluidTanks) if (tank.has()) {
			ChunkCoordinates pos = getOffset(OPOS[mFacing], 4);
			pos.posY-=1;
			FL.move(tank, WD.te(worldObj,pos,mFacing,false));
			if (FL.gas(tank) && !WD.hasCollide(worldObj, pos)) tank.setEmpty();
		}
		long capacity = (long) (mOverclock? mRate*mTurbineEfficiency : mRate*Math.min(mTurbineEfficiency,2));
		long energyExtracted = Math.min(capacity * 2 - mEnergyStored,kortex.mEnergyStored);
		if(energyExtracted <= 0)return;
		mEnergyStored += energyExtracted;
		kortex.mEnergyStored -= energyExtracted;
	}
}
