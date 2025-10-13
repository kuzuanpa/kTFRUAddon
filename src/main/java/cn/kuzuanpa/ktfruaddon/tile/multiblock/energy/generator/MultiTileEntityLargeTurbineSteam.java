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
import gregapi.data.LH;
import gregapi.data.LH.Chat;
import gregapi.fluid.FluidTankGT;
import gregapi.oredict.OreDictMaterial;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.util.List;

import static gregapi.data.CS.*;

public class MultiTileEntityLargeTurbineSteam extends MultiTileEntityLargeTurbine {
	public FluidTankGT[] mTanks = new FluidTankGT[] {new FluidTankGT(), new FluidTankGT()};
	public long mSteamCounter = 0, mEnergyProducedNextTick = 0; 
	public static final int STEAM_PER_WATER = 170;
	
	@Override
	public void readFromNBT2(NBTTagCompound aNBT) {
		super.readFromNBT2(aNBT);
		if (aNBT.hasKey(NBT_ENERGY_SU)) mSteamCounter = aNBT.getLong(NBT_ENERGY_SU);
		if (aNBT.hasKey(NBT_OUTPUT_SU)) mEnergyProducedNextTick = aNBT.getLong(NBT_OUTPUT_SU);

		for (int i = 0; i < mTanks.length; i++) mTanks[i].readFromNBT(aNBT, NBT_TANK+"."+i);
		mTanks[0].setCapacity(mRateMax*200);
		mTanks[1].setCapacity(mRateMax*4).setVoidExcess();
	}
	
	@Override
	public void writeToNBT2(NBTTagCompound aNBT) {
		super.writeToNBT2(aNBT);
		UT.NBT.setNumber(aNBT, NBT_ENERGY_SU, mSteamCounter);
		UT.NBT.setNumber(aNBT, NBT_OUTPUT_SU, mEnergyProducedNextTick);
		for (int i = 0; i < mTanks.length; i++) mTanks[i].writeToNBT(aNBT, NBT_TANK+"."+i);
	}
	
	static {
		LH.add("gt.tooltip.multiblock.steamturbine.1", "3x3x4 of 35 ");
		LH.add("gt.tooltip.multiblock.steamturbine.2", "Main centered on the 3x3 facing outwards");
		LH.add("gt.tooltip.multiblock.steamturbine.3", "Input only possible at frontal 3x3");
		LH.add("gt.tooltip.multiblock.steamturbine.4", "Distilled Water can be pumped out at Bottom Layer");
	}

	@Override
	public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
		aList.add(Chat.CYAN     + LH.get(LH.STRUCTURE) + ":");

		aList.add(Chat.WHITE    + LH.get("gt.tooltip.multiblock.steamturbine.1") + MultiTileEntityRegistry.getRegistry(getMultiTileEntityRegistryID()).getLocal(mTurbineWalls));
		aList.add(Chat.WHITE    + LH.get("gt.tooltip.multiblock.steamturbine.2"));
		aList.add(Chat.WHITE    + LH.get("gt.tooltip.multiblock.steamturbine.3"));
		aList.add(Chat.WHITE    + LH.get("gt.tooltip.multiblock.steamturbine.4"));
		super.addToolTips(aList, aStack, aF3_H);
	}

	@Override
	public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
		long rReturn = super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
		if (rReturn > 0) return rReturn;
		
		if (isClientSide()) return 0;
		
		if (aTool.equals(TOOL_plunger)) {
			if (mTanks[0].has()) return GarbageGT.trash(mTanks[0]);
			return GarbageGT.trash(mTanks[1]);
		}
		
		return 0;
	}
	
	@Override
	public void doConversion(long aTimer) {
		if (mEnergyProducedNextTick > 0) {
			mEnergyStored += mEnergyProducedNextTick;
			mEnergyProducedNextTick = 0;
		} else if (!mForcedStopped && slotHas(0) &&  mTanks[0].has(getEnergySizeInputMin(mEnergyTypeEmitted, SIDE_ANY) * 2)) {
			long tSteam = (long) Math.min(mRate*2*(mOverclock?mTurbineEfficiency:Math.min(mTurbineEfficiency,2)), mTanks[0].amount());
			mSteamCounter += tSteam;
			mEnergyStored += tSteam / 2;
			damageTurbine((long) (tSteam / (mOverclock?0.25F:2)),TURBINE_STEAM);
			if(isTurbineAboutToBreak&&getRandomNumber(20)==1)UT.Sounds.send(worldObj, SFX.IC_MACHINE_INTERRUPT, 1, 1, getCoords());
			mEnergyProducedNextTick += tSteam / 2;
			mTanks[0].remove(tSteam);
			if (mSteamCounter >= STEAM_PER_WATER) {
				mTanks[1].fillAll(FL.DistW.make(mSteamCounter / STEAM_PER_WATER));
				mSteamCounter %= STEAM_PER_WATER;
			}
		}
	}
	@Override
	public void transformTurbineItem() {
		int meta = slot(0).getItemDamage();
		mTurbineDurability = itemTurbine.getTurbineDurability(OreDictMaterial.get(meta));
		mTurbineEfficiency = itemTurbine.getTurbineEfficiency(OreDictMaterial.get(meta));
		usingCheckedTurbine=prefixList.turbineLargeSteamChecked.contains(slot(0));
		ST.set(slot(0), prefixList.turbineLargeSteamDamaged.mat(OreDictMaterial.get(meta),1));
	}
	@Override protected IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {return !mForcedStopped && FL.steam(aFluidToFill) ? mTanks[0] : null;}
	@Override protected IFluidTank getFluidTankDrainable2(byte aSide, FluidStack aFluidToDrain) {return mTanks[1];}
	@Override protected IFluidTank[] getFluidTanks2(byte aSide) {return mTanks;}

	@Override
	public boolean isItemValidForSlot(int aSlot, ItemStack aStack) {
		return super.isItemValidForSlot(aSlot, aStack) && (prefixList.turbineLargeSteam.contains(aStack) || prefixList.turbineLargeSteamChecked.contains(aStack));
	}

	@Override public String getTileEntityName() {return "ktfru.multitileentity.multiblock.turbine.steam";}
}
