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

import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import cn.kuzuanpa.ktfruaddon.api.recipe.IKortexHandler;
import cn.kuzuanpa.ktfruaddon.api.recipe.KortexWorker;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.fluid.FluidTankGT;
import gregapi.recipes.Recipe;
import gregapi.tileentity.behavior.TE_Behavior_Active_Trinary;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.multiblocks.IMultiBlockEnergy;
import gregapi.tileentity.multiblocks.IMultiBlockFluidHandler;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import gregapi.util.UT;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.util.Collection;
import java.util.List;

import static cn.kuzuanpa.ktfruaddon.api.tile.util.kTileNBT.WORKING_MODE;
import static gregapi.data.CS.*;

public abstract class HeaterBase extends TileEntityBase10MultiBlockBase implements IMultiBlockFluidHandler, ITileEntityEnergy, IMultiBlockEnergy, IKortexHandler {

    public short workingMode=0;
    public long mEnergyStored = 0;
    public TE_Behavior_Active_Trinary mActivity = null;
    /** Recipe core; the heat buffer doubles as its energy buffer, one progress tick per tick at the machine's rate. */
    public KortexWorker kortex;
    private TagData mEnergyTypeEmitted=TD.Energy.HU;

    public FluidTankGT[] mTanks = {new FluidTankGT(80000),new FluidTankGT(80000)};

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        mActivity = new TE_Behavior_Active_Trinary(this, aNBT);
        if (aNBT.hasKey(NBT_ENERGY)) mEnergyStored = aNBT.getLong(NBT_ENERGY);
        if (aNBT.hasKey(WORKING_MODE)) workingMode = aNBT.getShort(WORKING_MODE);
        mTanks[0].readFromNBT(aNBT, NBT_TANK+".0").setCapacity(80000);
        mTanks[1].readFromNBT(aNBT, NBT_TANK+".1").setCapacity(80000);
        kortex = new KortexWorker(this, 0, recipeMaps.FluidHeating, F).setMaxProgressPerTick(1);
        kortex.fluidInputs = new FluidTankGT[] {mTanks[0]};
        kortex.fluidOutputs = new FluidTankGT[] {mTanks[1]};
        kortex.readFromNBT(aNBT);
    }
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        mActivity.save(aNBT);
        UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergyStored);
        UT.NBT.setNumber(aNBT, WORKING_MODE, workingMode);
        mTanks[0].writeToNBT(aNBT, NBT_TANK+".0");
        mTanks[1].writeToNBT(aNBT, NBT_TANK+".1");
        kortex.writeToNBT(aNBT);
    }
    public abstract void doOutputEnergy();
    public abstract void doOutputFluid();

    public abstract long getCapacity();
    public abstract long getHotLiquidRecipeRate();

    public abstract void overheat();
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer,aIsServerSide);
        if (!aIsServerSide||!mStructureOkay) return;
        if (mEnergyStored > getCapacity()) {
            overheat();
            mEnergyStored = 0;
        }

        doOutputFluid();

        //Do Work
        if(workingMode==0) doOutputEnergy();
        if (workingMode==1) doRecipe();
    }

    /** Hot-liquid step; the worker spends the stored heat at this machine's fixed rate. */
    private void doRecipe() {
        kortex.mEnergyStored = mEnergyStored;
        kortex.mEnergyCapacity = getCapacity();
        kortex.run();
        mEnergyStored = kortex.mEnergyStored;
    }

    // Recipe bridge
    /** Only one-liquid recipes run, and only as many batches as the machine's heat rate can pay for per tick. */
    @Override
    public int getMaxParallel(int aKortexID, long aEUT, long aDuration) {
        long tRate = getHotLiquidRecipeRate();
        if (aEUT <= 0 || aEUT > tRate) return 0;
        Recipe tRecipe = kortex.findRecipe();
        if (tRecipe == null || tRecipe.mFluidInputs.length != 1 || tRecipe.mFluidOutputs.length != 1) return 0;
        long tMax = tRate / aEUT;
        FluidStack tOutput = tRecipe.mFluidOutputs[0];
        if (tOutput != null && tOutput.amount > 0) tMax = Math.min(tMax, (kortex.fluidOutputs[0].capacity() - kortex.fluidOutputs[0].amount()) / tOutput.amount);
        return (int) Math.min(tMax, Integer.MAX_VALUE);
    }

    @Override public void onRecipeStart(int aKortexID, long aEUT, long aDuration, long aParallel) {mActivity.mActive = T;}

    @Override public void onRecipeFinish(int aKortexID, long aEUT, long aDuration, long aParallel) {mActivity.mActive = F;}

    /** The worker writes into the output tank, so push it out right away to keep the next batch flowing. */
    @Override public void receiveOutputs(FluidTankGT[] fluidTanks, ItemStack[] items) {doOutputFluid();}

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (aTool.equals(TOOL_thermometer)) aChatReturn.add("Temperature: " + getTemperature()+"/"+(961+DEFAULT_ENVIRONMENT_TEMPERATURE)+"K");
        if (aTool.equals(TOOL_magnifyingglass)){
            if(mTanks[0].getFluid()!=null&&mTanks[1].getFluid()!=null)aChatReturn.add("Tanks: " + mTanks[0].getFluid().getUnlocalizedName()+":"+mTanks[0].getFluid().amount+"/"+mTanks[1].getFluid().getUnlocalizedName()+":"+mTanks[1].getFluid().amount);
            else if(mTanks[0].getFluid()!=null)aChatReturn.add("Tanks: " + mTanks[0].getFluid().getUnlocalizedName()+":"+mTanks[0].getFluid().amount);
        }
        if (aTool.equals(TOOL_screwdriver)) {
            workingMode= (short) (workingMode==0?1:0);
            aChatReturn.add(LH.Chat.ORANGE+"Working Mode:"+(workingMode==0?"Direct HU":"Hot Liquid"));
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    public float getTemperature(){return ((float) mEnergyStored /(float)getCapacity() *961)+DEFAULT_ENVIRONMENT_TEMPERATURE;}
    @Override protected IFluidTank[] getFluidTanks(MultiTileEntityMultiBlockPart aPart, byte aSide) {return mTanks;}
    @Override
    public IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {
        return mTanks[0] ;
    }
    @Override
    public IFluidTank getFluidTankDrainable2(byte aSide, FluidStack aFluidToDrain) {
        if (!mActivity.mActive && !mTanks[1].isEmpty()) return mTanks[1];
        return null;
    }
    @Override public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {
        return aEmitting && aEnergyType == mEnergyTypeEmitted;
    }
    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return mEnergyTypeEmitted.AS_LIST;}
}
