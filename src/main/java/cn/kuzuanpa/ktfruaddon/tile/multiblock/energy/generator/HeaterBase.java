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

public abstract class HeaterBase extends TileEntityBase10MultiBlockBase implements IMultiBlockFluidHandler, ITileEntityEnergy, IMultiBlockEnergy {

    public short workingMode=0;
    public long mEnergyStored = 0;
    public double timeRemains=-1;
    public TE_Behavior_Active_Trinary mActivity = null;
    public Recipe.RecipeMap mRecipes = recipeMaps.FluidHeating;
    public Recipe mLastRecipe = null;
    public FluidStack recipeOutput = null;
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
    }
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        mActivity.save(aNBT);
        UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergyStored);
        UT.NBT.setNumber(aNBT, WORKING_MODE, workingMode);
        mTanks[0].writeToNBT(aNBT, NBT_TANK+".0");
        mTanks[1].writeToNBT(aNBT, NBT_TANK+".1");
        NBTTagCompound tag = new NBTTagCompound();
        recipeOutput.writeToNBT(tag);
        aNBT.setTag("recipe.Output",tag);
        aNBT.setDouble("recipe.timeRemain",timeRemains);
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
        if (workingMode==1) {
            //doRecipe
            long mRateCurrent= getHotLiquidRecipeRate();
            if (mEnergyStored < mRateCurrent) return;
            if (timeRemains>0){
                if(mRateCurrent<(float)mLastRecipe.mEUt){
                    //Not Enough Energy
                    mActivity.mActive = F;
                    UT.Sounds.send(SFX.MC_FIZZ, (TileEntity) this);
                    timeRemains=mLastRecipe.mDuration;
                    return;
                }
                double speed=(mRateCurrent/(float)mLastRecipe.mEUt);
                if(timeRemains>=speed){
                    mEnergyStored -= Math.abs(mRateCurrent);
                    timeRemains-=speed;
                    return;
                } else {
                    //Done One Recipe
                    mEnergyStored -= (long) Math.abs(mRateCurrent*(timeRemains/speed));
                    timeRemains=0.0F;
                    mTanks[1].fill(recipeOutput);
                    mActivity.mActive = F;
                    return;
                }
            }
            Recipe tRecipe = mRecipes.findRecipe(this, mLastRecipe, T, Long.MAX_VALUE, NI, mTanks, ZL_IS);
            if (tRecipe == null||tRecipe.mFluidInputs.length != 1||tRecipe.mFluidOutputs.length != 1||tRecipe.mEUt>mRateCurrent){
                mLastRecipe=null;
                mActivity.mActive = F;
                return;
            }
            if(!mTanks[1].canFillAll(tRecipe.mFluidOutputs[0]))return;
            recipeOutput = tRecipe.mFluidOutputs[0].copy();
            if (tRecipe.isRecipeInputEqual(T, F, mTanks, ZL_IS)) {
                mActivity.mActive = T;
                timeRemains=tRecipe.mDuration;
                mLastRecipe = tRecipe;
                double speed=(mRateCurrent/(float)mLastRecipe.mEUt);
                while(speed > timeRemains){
                    if(!mTanks[1].canFillAll(tRecipe.mFluidOutputs[0]) || !tRecipe.isRecipeInputEqual(T, F, mTanks, ZL_IS))break;
                    timeRemains+=tRecipe.mDuration;
                    recipeOutput.amount += tRecipe.mFluidOutputs[0].amount;
                }

            }
        }
    }

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
