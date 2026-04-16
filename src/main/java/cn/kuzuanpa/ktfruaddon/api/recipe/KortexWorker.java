package cn.kuzuanpa.ktfruaddon.api.recipe;

import cn.kuzuanpa.ktfruaddon.api.code.StateMgr;
import gregapi.data.FL;
import gregapi.data.LH;
import gregapi.fluid.FluidTankGT;
import gregapi.recipes.Recipe;
import gregapi.recipes.Recipe.RecipeMap;
import gregapi.util.ST;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;
import java.util.Objects;

import static gregapi.data.CS.*;

/** cortex, 大脑皮层，这是一个封装了配方运行逻辑，方便各种机器运行各自的配方的配方执行单元**/
public class KortexWorker {
    public final boolean isInstant;
    public final int id;
    public final RecipeMap recipeMap;
    public final IKortexHandler machine;

    public FluidTankGT[] fluidInputs = ZL_FT, fluidOutputs = ZL_FT;
    public ItemStack[] itemInputs = ZL_IS, itemOutputs = ZL_IS;

    /**This is only used to display, inner logic doesn't use this**/
    public StateMgr mState = new StateMgr();
    public static final byte STATE_NO_RECIPE =0, STATE_ITEM_SHORTAGE=1, STATE_FLUID_SHORTAGE=2, STATE_ENERGY_SHORTAGE=3, STATE_OUTPUT_BLOCKED_FLUID=4, STATE_OUTPUT_BLOCKED_ITEM=5, STATE_OUTPUT_BLOCKED_ENERGY=6, STATE_RUNNING=7, STATE_MACHINE_LIMITED=8;
    protected boolean outputBlocked = false, resetProgressWhenPowerLost = true;
    public long mEnergyStored = 0, mEnergyCapacity = 0, mProgress = 0, mMaxProgress = 0, mCurrentParallel = 0, mRecipeEUt = 0, mInputTankSize = 16000,mOutputTankSize = 16000;
    protected ItemStack[] mRecipeOutputItems = ZL_IS;
    protected FluidStack[] mRecipeOutputFluids = ZL_FS;

    protected Recipe lastRecipe = null;

    public KortexWorker(IKortexHandler machine, int id, RecipeMap recipeMap, boolean isInstant) {
        this.recipeMap = recipeMap;
        this.isInstant = isInstant;
        this.machine = machine;
        this.id = id;
        setItemSlotsCount(recipeMap.mInputItemsCount, recipeMap.mOutputItemsCount);
        setFluidSlotCount(recipeMap.mInputFluidCount, recipeMap.mOutputFluidCount);
    }
    public KortexWorker(IKortexHandler machine, RecipeMap recipeMap){
        this(machine,0,recipeMap,false);
    }
    public KortexWorker setItemSlotsCount(int input, int output){
        itemInputs=new ItemStack[input];
        itemOutputs=new ItemStack[output];
        return this;
    }
    public KortexWorker setFluidSlotCount(int input, int output){
        fluidInputs=new FluidTankGT[input];
        fluidOutputs=new FluidTankGT[output];
        for (int i = 0; i < fluidInputs.length; i++) fluidInputs[i] = new FluidTankGT().setCapacity(mInputTankSize);
        for (int i = 0; i < fluidOutputs.length; i++) fluidOutputs[i] = new FluidTankGT().setCapacity(mOutputTankSize);
        return this;
    }
    public KortexWorker setTankSize(long input, long output){
        mInputTankSize = input;
        mOutputTankSize = output;
        return this;
    }
    public KortexWorker dontResetProgressWhenPowerLost(){
        resetProgressWhenPowerLost = false;
        return this;
    }
    public void run() {
        if(outputBlocked){
            machine.receiveOutputs(fluidOutputs, itemOutputs);
            outputBlocked = checkOutputBlocked();
            return;
        }
        if (mMaxProgress <= 0) mState.set(tryStartNewRecipe());

        if (mProgress < mMaxProgress) updateProgress();
        else finishRecipe();
    }

    public boolean checkOutputBlocked(){
        if (mEnergyStored > mEnergyCapacity) {
            mState.set(STATE_OUTPUT_BLOCKED_ENERGY);
            return true;
        }
        if (!Arrays.stream(fluidOutputs).allMatch(FluidTankGT::isEmpty)){
            mState.set(STATE_OUTPUT_BLOCKED_FLUID);
            return true;
        }
        if (!Arrays.stream(itemOutputs).allMatch(Objects::isNull)) {
            mState.set(STATE_OUTPUT_BLOCKED_ITEM);
            return true;
        }
        return false;
    }
    private byte tryStartNewRecipe() {
        Recipe recipe = recipeMap.findRecipe(machine, lastRecipe, F, Integer.MAX_VALUE, machine.getSpecialSlot(id), fluidInputs, itemInputs);
        if (recipe == null || recipe.mDuration <= 0) return STATE_NO_RECIPE;

        lastRecipe = recipe;
        int parallel = calculateParallelAndConsume(recipe, machine.getMaxParallel(id, recipe.mEUt,recipe.mDuration), true);

        if (parallel <= 0) return mState.get();

        this.mRecipeEUt = recipe.mEUt;
        this.mMaxProgress = recipe.mDuration;
        this.mProgress = 0;
        this.mCurrentParallel = parallel;
        cacheRecipeOutputs(recipe, parallel);

        machine.onRecipeStart(id, mRecipeEUt, mMaxProgress, mCurrentParallel);

        if (isInstant) {
            mEnergyStored -= mRecipeEUt * mCurrentParallel * mMaxProgress;
            mProgress = mMaxProgress;
        }
        return STATE_RUNNING;
    }

    public long injectEnergy(long amount){
        long injected = Math.min(mEnergyCapacity-mEnergyStored, amount);
        mEnergyStored += injected;
        return injected;
    }

    private int calculateParallelAndConsume(Recipe aRecipe, int aMaxParallel, boolean aConsume) {
        if(aMaxParallel <= 0){
            mState.set(STATE_MACHINE_LIMITED);
            return 0;
        }
        int rPossibleParallel = (int) Math.min(aMaxParallel, Math.floorDiv(mEnergyCapacity - mEnergyStored, Math.abs(isInstant? aRecipe.mEUt * aRecipe.mDuration : aRecipe.mEUt)));// energy parallel limit
        if(rPossibleParallel <= 0){
            mState.set(aRecipe.mEUt >0?STATE_ENERGY_SHORTAGE:STATE_OUTPUT_BLOCKED_ENERGY);
            return 0;
        }
        //calculate fluid parallel limit
        if (aRecipe.mFluidInputs != null) for (FluidStack recipeFluid : aRecipe.mFluidInputs) {
            if (recipeFluid == null || recipeFluid.amount <= 0) continue;
            long totalAvailable = 0;
            for (FluidTankGT tank : fluidInputs) if (!tank.isEmpty() && FL.equal(tank.getFluid(),recipeFluid)) {
                totalAvailable += tank.amount();
            }
            rPossibleParallel = (int) Math.min(rPossibleParallel, totalAvailable / recipeFluid.amount);
            if (rPossibleParallel <= 0) {
                mState.set(STATE_FLUID_SHORTAGE);
                return 0;
            }
        }

        //calculate item parallel limit
        if (aRecipe.mInputs != null) for (ItemStack recipeStack : aRecipe.mInputs) {
            if (recipeStack == null || recipeStack.stackSize <= 0) continue;
            long totalAvailable = 0;
            for (ItemStack invStack : itemInputs) if (isItemStackEqual(recipeStack, invStack)) {
                totalAvailable += invStack.stackSize;
            }
            rPossibleParallel = (int) Math.min(rPossibleParallel, totalAvailable / recipeStack.stackSize);
            if (rPossibleParallel <= 0) {
                mState.set(STATE_ITEM_SHORTAGE);
                return 0;
            }
        }

        if (!aConsume) return rPossibleParallel;

        //remove fluid
        if (aRecipe.mFluidInputs != null) for (FluidStack recipeFluid : aRecipe.mFluidInputs) {
            if (recipeFluid == null) continue;
            long amountToDrain = (long) recipeFluid.amount * rPossibleParallel;
            for (FluidTankGT tank : fluidInputs) {
                if (tank.isEmpty() || !FL.equal(tank.getFluid(), recipeFluid)) continue;

                amountToDrain -= tank.remove(amountToDrain);
                if (amountToDrain <= 0) break;
            }
        }
        //remove item
        if (aRecipe.mInputs != null) for (ItemStack recipeStack : aRecipe.mInputs) {
            if (recipeStack == null) continue;
            int amountToRemove =  recipeStack.stackSize * rPossibleParallel;
            for (int i = 0; i < itemInputs.length; i++) {
                ItemStack invStack = itemInputs[i];
                if (!ST.equal(recipeStack, invStack, T)) continue;

                int canRemove = Math.min(amountToRemove, invStack.stackSize);
                invStack.stackSize -= canRemove;
                if(invStack.stackSize <= 0) itemInputs[i] = null;
                amountToRemove -= canRemove;
                if (amountToRemove <= 0) break;
            }
        }
        return rPossibleParallel;
    }
    private void updateProgress() {
        long requiredPerTick = mRecipeEUt * mCurrentParallel;

        if (mEnergyStored < requiredPerTick) {
            if(resetProgressWhenPowerLost) mProgress = 0;
            mState.set(STATE_ENERGY_SHORTAGE);
            return;
        }
        long speed = Math.min( (mEnergyStored / requiredPerTick), mMaxProgress - mProgress);

        if (speed <= 0) return;
        mEnergyStored -= speed * requiredPerTick;
        mProgress += speed;
    }

    private void cacheRecipeOutputs(Recipe recipe, int parallel) {
        if (recipe.mOutputs != null) {
            mRecipeOutputItems = new ItemStack[recipe.mOutputs.length];
            for (int i = 0; i < recipe.mOutputs.length; i++) if (recipe.mOutputs[i] != null) {
                mRecipeOutputItems[i] = ST.copy(recipe.mOutputs[i]);
                mRecipeOutputItems[i].stackSize *= parallel;
            }
        }
        if (recipe.mFluidOutputs != null) {
            mRecipeOutputFluids = new FluidStack[recipe.mFluidOutputs.length];
            for (int i = 0; i < recipe.mFluidOutputs.length; i++) if (recipe.mFluidOutputs[i] != null){
                mRecipeOutputFluids[i] = recipe.mFluidOutputs[i].copy();
                mRecipeOutputFluids[i].amount *= parallel;
            }
        }
    }

    private void finishRecipe() {
        for (int i = 0; i < mRecipeOutputFluids.length && i < fluidOutputs.length; i++) {
            if (mRecipeOutputFluids[i] != null && fluidOutputs[i] != null) fluidOutputs[i].fill(mRecipeOutputFluids[i]);
        }
        for (int i = 0; i < mRecipeOutputItems.length && i < itemOutputs.length; i++) {
            if (mRecipeOutputItems[i] != null && itemOutputs[i] != null) itemOutputs[i] = mRecipeOutputItems[i];
        }
        machine.onRecipeFinish(id, mRecipeEUt, mMaxProgress, mCurrentParallel);
        resetStatus();
        machine.receiveOutputs(fluidOutputs, itemOutputs);
        outputBlocked = checkOutputBlocked();
    }

    public void resetStatus() {
        mProgress = mMaxProgress = mCurrentParallel = 0;
        mRecipeEUt = 0;
        mRecipeOutputItems = ZL_IS;
        mRecipeOutputFluids = ZL_FS;
    }
    /**itemToCheck.nbt is ignored if itemInRecipe.nbt == null**/
    public static boolean isItemStackEqual(ItemStack itemInRecipe, ItemStack itemToCheck) {
        if(itemInRecipe == null || itemToCheck == null)return false;
        return Objects.equals(itemInRecipe.getItem(), itemToCheck.getItem()) &&
                itemInRecipe.getItemDamage() == itemToCheck.getItemDamage() &&
                !itemInRecipe.hasTagCompound() || Objects.equals(itemInRecipe.getTagCompound(), itemToCheck.getTagCompound());
    }

    public String getLocalizedState(){
        return LH.get("ktfru.text.kortex.state."+mState.get());
    }

    public void writeToNBT(NBTTagCompound aNBT) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setLong("rEUt", mRecipeEUt);
        nbt.setLong("rProg", mProgress);
        nbt.setLong("rMax", mMaxProgress);
        nbt.setLong("rPara", mCurrentParallel);

        if (mRecipeOutputItems.length > 0) {
            NBTTagList list = new NBTTagList();
            for (ItemStack s : mRecipeOutputItems) if (s != null) list.appendTag(ST.save(s));
            nbt.setTag("rItems", list);
        }
        if (mRecipeOutputFluids.length > 0) {
            NBTTagList list = new NBTTagList();
            for (FluidStack s : mRecipeOutputFluids) if (s != null) list.appendTag(FL.save(s));
            nbt.setTag("rFluids", list);
        }
        aNBT.setTag("kortex"+id, nbt);
    }

    public void readFromNBT(NBTTagCompound aNBT) {
        NBTTagCompound nbt = aNBT.getCompoundTag("kortex"+id);
        mRecipeEUt = nbt.getLong("rEUt");
        mProgress = nbt.getLong("rProg");
        mMaxProgress = nbt.getLong("rMax");
        mCurrentParallel = nbt.getLong("rPara");

        if (nbt.hasKey("rItems")) {
            NBTTagList list = nbt.getTagList("rItems", Constants.NBT.TAG_COMPOUND);
            mRecipeOutputItems = new ItemStack[list.tagCount()];
            for (int i = 0; i < list.tagCount(); i++) mRecipeOutputItems[i] = ST.load(list.getCompoundTagAt(i));
        }
        if (nbt.hasKey("rFluids")) {
            NBTTagList list = nbt.getTagList("rFluids", Constants.NBT.TAG_COMPOUND);
            mRecipeOutputFluids = new FluidStack[list.tagCount()];
            for (int i = 0; i < list.tagCount(); i++) mRecipeOutputFluids[i] = FL.load(list.getCompoundTagAt(i));
        }
    }
}