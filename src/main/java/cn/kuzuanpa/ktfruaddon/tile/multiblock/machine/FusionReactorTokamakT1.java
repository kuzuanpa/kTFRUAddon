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

import cn.kuzuanpa.ktfruaddon.api.code.StateMgr;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.recipe.IKortexHandler;
import cn.kuzuanpa.ktfruaddon.api.recipe.KortexWorker;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.IMeterDetectable;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ICircuitChangeableTileEntity;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputePart;
import cn.kuzuanpa.ktfruaddon.api.tile.control.IMachineMeter;
import cn.kuzuanpa.ktfruaddon.api.tile.control.MachineControl;
import cn.kuzuanpa.ktfruaddon.api.tile.control.MachineControlComponent;
import cn.kuzuanpa.ktfruaddon.api.tile.status.IMachineStatus;
import cn.kuzuanpa.ktfruaddon.api.tile.status.MachineStatus;
import cn.kuzuanpa.ktfruaddon.api.tile.status.MachineStatusReport;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.SpecialPartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.special.ComputePartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.client.gui.ContainerClientFusionTokamakT1;
import cn.kuzuanpa.ktfruaddon.client.gui.ContainerCommonFusionTokamakT1;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.block.multitileentity.IWailaTile;
import gregapi.code.TagData;
import gregapi.data.FL;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.fluid.FluidTankGT;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.old.Textures;
import gregapi.recipes.Recipe;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.data.ITileEntityProgress;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.energy.ITileEntityEnergyDataCapacitor;
import gregapi.tileentity.machines.ITileEntitySwitchableOnOff;
import gregapi.tileentity.multiblocks.*;
import gregapi.util.ST;
import gregapi.util.UT;
import gregapi.util.WD;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.*;

import static cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler.HAS_PROJECTOR_STRUCTURE;
import static gregapi.data.CS.*;

/** Reference implementation for the control/status API; the plasma and power mechanics stay machine-specific. **/
public class FusionReactorTokamakT1 extends TileEntityBase10MultiBlockBase implements IMultiTileEntity.IMTE_SyncDataByteArray, ITileEntityEnergy, IMultiBlockEnergy, IMultiBlockEnergyDataCapacitor, IMultiBlockFluidHandler, IMultiBlockInventory, SpecialPartPredicate.IReceiveSpecialPart, IKortexHandler, IWailaTile, IMachineStatus, ITileEntitySwitchableOnOff, ITileEntityProgress, ITileEntityEnergyDataCapacitor, IMachineMeter {
    public static final byte STATE_STOPPED=0,STATE_CHARGING=1,STATE_RUNNING=2, STATE_ERROR=3,STATE_VOID_CHARGING=4;
    public static final short MAX_FIELD_STRENGTH=1000, KEEP_CHARGE_EUt=8192;
    public static final long MAX_CHARGE = 1073741824L;//1024^3
    public static final String WAILA_OUTPUT_RATE = "ktfru.output.rate", WAILA_OUTPUT_VOLTAGE = "ktfru.output.voltage", WAILA_OUTPUT_BUFFER = "ktfru.output.buffer";
    public StateMgr mState=new StateMgr();
    public final MachineControlComponent control = new MachineControlComponent(this, this::updateClientData);
    public boolean isComputePowerEnough=false;
    public boolean mSuccessful=false;
    public short mFieldStrength=0;
    public float dischargeRate=0.2F, progress =0;
    public long mEnergy=0 , mEnergyCharged = 0, mEnergyOutput = 0, mOutputRate = 0, mOutputVoltage = 0, mRate = 32768, mRateCharging =0,mRateChargingLast = 0,recipeChargeRequired=0, recipeEUt=0,recipeTotalTime=0, computePowerNeeded=8L*1024L;
    public TagData mEnergyTypeAccepted = TD.Energy.EU, mEnergyTypeCharging = TD.Energy.LU;
    public Recipe.RecipeMap mRecipes = recipeMaps.FusionTokamak;
    /** Recipe core owns the plasma transaction; the charge curve below stays this machine's own mechanic. */
    public KortexWorker kortex;
    public Recipe mCurrentRecipe=null;
    public FluidTankGT[] mTanks, mTanksInput, mTanksOutput;
    private List<ChunkCoordinates> computeNodesCoord= new ArrayList<>();
    private final List<IMeterDetectable.MeterData> mMeterReceived = new ArrayList<>(), mMeterReceivedLast = new ArrayList<>();

    /**Resolves the compute node parts of this structure, skipping the ones that are gone or not loaded.**/
    protected List<IComputePart> getComputeNodes(){
        List<IComputePart> nodes = new ArrayList<>();
        for (ChunkCoordinates coord : computeNodesCoord) {
            TileEntity tile = WD.te(worldObj, coord, true);
            if (tile instanceof IComputePart) nodes.add((IComputePart) tile);
        }
        return nodes;
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        control.readFromNBT(aNBT);
        if (aNBT.hasKey(NBT_STATE)) mState.set( aNBT.getByte(NBT_STATE));
        if (aNBT.hasKey(NBT_ENERGY)) mEnergy = aNBT.getLong(NBT_ENERGY);
        if (aNBT.hasKey(NBT_ENERGY+".1")) mEnergyCharged = aNBT.getLong(NBT_ENERGY+".1");
        if (!CODE_CLIENT && aNBT.hasKey(NBT_ENERGY+".out")) mEnergyOutput = aNBT.getLong(NBT_ENERGY+".out");
        if (aNBT.hasKey(NBT_ENERGY+".out.v")) mOutputVoltage = aNBT.getLong(NBT_ENERGY+".out.v");
        if (aNBT.hasKey(NBT_EFFICIENCY)) mFieldStrength = aNBT.getShort(NBT_EFFICIENCY);
        if (aNBT.hasKey(NBT_ENERGY_EU)) recipeChargeRequired = aNBT.getLong(NBT_ENERGY_EU);
        if (aNBT.hasKey(NBT_PROGRESS)) progress = aNBT.getFloat(NBT_PROGRESS);
        if (aNBT.hasKey(NBT_PROGRESS+".req")) recipeTotalTime = aNBT.getLong(NBT_PROGRESS+".req");
        if (aNBT.hasKey(NBT_INPUT_EU)) recipeEUt = aNBT.getLong(NBT_INPUT_EU);
        if (aNBT.hasKey(NBT_ENERGY_ACCEPTED)) mEnergyTypeAccepted = TagData.createTagData(aNBT.getString(NBT_ENERGY_ACCEPTED));
        if (aNBT.hasKey(NBT_ENERGY_ACCEPTED_2)) mEnergyTypeCharging = TagData.createTagData(aNBT.getString(NBT_ENERGY_ACCEPTED_2));
        if (aNBT.hasKey(NBT_FUELMAP)) mRecipes = Recipe.RecipeMap.RECIPE_MAPS.get(aNBT.getString(NBT_FUELMAP));

        Map<ComputePower, Long> requiredCompute = ICircuitChangeableTileEntity.readRequiredCompute(aNBT);
        if (requiredCompute.containsKey(ComputePower.Normal)) computePowerNeeded = requiredCompute.get(ComputePower.Normal);

        mTanks=new FluidTankGT[mRecipes.mInputFluidCount+mRecipes.mOutputFluidCount];
        for (int i = 0; i < mRecipes.mInputFluidCount+mRecipes.mOutputFluidCount; i++) mTanks[i]=new FluidTankGT().readFromNBT(aNBT, NBT_TANK+"."+i).setCapacity(8000);

        mTanksInput= new FluidTankGT[mRecipes.mInputFluidCount];
        System.arraycopy(mTanks, 0, mTanksInput, 0, mRecipes.mInputFluidCount);
        mTanksOutput= new FluidTankGT[mRecipes.mOutputFluidCount];
        System.arraycopy(mTanks, mRecipes.mInputFluidCount, mTanksOutput, 0, mRecipes.mOutputFluidCount);

        kortex = new KortexWorker(this, 0, mRecipes, F).setProgressDrivenExternally().dontResetProgressWhenPowerLost();
        kortex.fluidInputs = mTanksInput;
        kortex.readFromNBT(aNBT);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);

        control.writeToNBT(aNBT);
        aNBT.setByte(NBT_STATE, mState.get());
        UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergy);
        UT.NBT.setNumber(aNBT, NBT_ENERGY+".1", mEnergyCharged);
        UT.NBT.setNumber(aNBT, NBT_ENERGY+".out", mEnergyOutput);
        UT.NBT.setNumber(aNBT, NBT_ENERGY+".out.v", mOutputVoltage);
        aNBT.setShort(NBT_EFFICIENCY,mFieldStrength);
        UT.NBT.setNumber(aNBT, NBT_ENERGY_EU,recipeChargeRequired);
        aNBT.setFloat(NBT_PROGRESS, progress);
        aNBT.setLong(NBT_PROGRESS+".req", recipeTotalTime);
        UT.NBT.setNumber(aNBT, NBT_INPUT_EU,recipeEUt);
        aNBT.setByte(NBT_FACING, mFacing);
        for (int i = 0; i < 12; i++) mTanks[i].writeToNBT(aNBT, NBT_TANK+"."+i);
        kortex.writeToNBT(aNBT);
    }
    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if (!aIsServerSide) return;
        mSuccessful = false;
        mOutputRate = 0;
        mMeterReceivedLast.clear();
        mMeterReceivedLast.addAll(mMeterReceived);
        mMeterReceived.clear();
        if (!control.getStateOnOff()) {
            if (mState.get() != STATE_STOPPED || mEnergyCharged > 0 || mCurrentRecipe != null) stopForSwitchOff();
            return;
        }
        if (!mStructureOkay){
            if(mEnergyCharged>0)onError();
            return;
        }

        isComputePowerEnough = IComputePart.tryStartForDemand(getComputeNodes(), ComputePower.Normal, computePowerNeeded);

        if(!isComputePowerEnough){
            mFieldStrength= (short) Math.max(-1,mFieldStrength-1);
            if(mFieldStrength<0&&mEnergyCharged>0)onError();
            return;
        }
        //refresh state
        if(mState.is(STATE_ERROR)){
            mEnergyCharged=0;
            if(mEnergy < KEEP_CHARGE_EUt)return;
            mFieldStrength = (short) Math.min(mFieldStrength+4,MAX_FIELD_STRENGTH);
            setState(STATE_STOPPED);
        }
        //If no plasma in reactor
        boolean isInputEmpty = Arrays.stream(mTanksInput).allMatch(FluidTankGT::isEmpty);
        if(isInputEmpty&& !mState.is(STATE_RUNNING)){
            setState(STATE_STOPPED);
            mEnergyCharged=0;
            nullifyCurrentRecipe();
            return;
        }
        //display fluids
        for (int i = 0; i < mTanksInput .length; i++) slot(mRecipes.mInputItemsCount + mRecipes.mOutputItemsCount + i                       , FL.display(mTanksInput [i], T, T));
        for (int i = 0; i < mTanksOutput.length; i++) slot(mRecipes.mInputItemsCount + mRecipes.mOutputItemsCount + i + mTanksInput.length  , FL.display(mTanksOutput[i], T, T));


        //receiveEnergy
        mEnergyCharged +=mRateCharging;
        if (mEnergyCharged < 0) mEnergyCharged = 0;
        if(mEnergyCharged > MAX_CHARGE) onError();
        mRateChargingLast=mRateCharging;
        if(mRateCharging==0)mEnergyCharged= (long) Math.max(0,Math.max(mEnergyCharged-100,mEnergyCharged*(1-dischargeRate)));
        mRateCharging=0;

        doNormalRun(isInputEmpty);
}

    protected void doNormalRun(boolean isInputEmpty){
        //AutoOutput
        if (mTanks[2]!=null) FL.move(mTanks[2], getAdjacentTank(mFacing));

        //Field Handle
        if(mEnergy >= (recipeEUt==0? KEEP_CHARGE_EUt : recipeEUt))mFieldStrength= (short) Math.min(MAX_FIELD_STRENGTH,mFieldStrength+4);
        else if(mState.is(STATE_CHARGING)||mState.is(STATE_RUNNING)||mState.is(STATE_VOID_CHARGING))mFieldStrength= (short) Math.max(-1,mFieldStrength-1);
        mEnergy=0;
        if(mFieldStrength<0){
            if(mEnergyCharged>0) onError();
            return;
        }

        //do recipe
        if(mState.is(STATE_RUNNING)&&kortex.hasActiveRecipe()) {
            if (recipeEUt < 0) {
                mOutputRate = -recipeEUt;
                long tBufferMax = Math.max(1, Math.max(mRate, mOutputRate)) * 20;
                mEnergyOutput = Math.min(tBufferMax, mEnergyOutput + mOutputRate);
            }
            progress += mEnergyCharged *1F / recipeChargeRequired;
            boolean tCompleted = progress >= recipeTotalTime;
            // the worker keeps the exact float progress; it only tells us when the products are due
            kortex.mProgress = tCompleted ? kortex.mMaxProgress : Math.min(kortex.mMaxProgress, (long) progress);
            runWorker();
            if (tCompleted) {
                nullifyCurrentRecipe();
                mState.set(STATE_STOPPED);
            }
        }

        if(!isInputEmpty&& (mState.is(STATE_STOPPED)||mState.is(STATE_VOID_CHARGING)|| mState.is(STATE_CHARGING))){
            runWorker();
            if (kortex.hasActiveRecipe()) {progress = 0; setState(STATE_RUNNING);}
            else setState(mCurrentRecipe == null ? STATE_VOID_CHARGING : STATE_CHARGING);
        }

    }

    /** One worker step; the charge curve above drives the progress, the start charge is gated in getMaxParallel. */
    private void runWorker(){
        for (int i = 0; i < kortex.itemInputs.length && i < 3; i++) kortex.itemInputs[i] = slot(i) == null ? null : ST.copy(slot(i));
        // The reactor owns the charge gate and progress; the worker only matches inputs and caches outputs.
        kortex.mEnergyStored = 0;
        kortex.mEnergyCapacity = 0;
        kortex.run();
    }

    // Recipe bridge
    /** Records the recipe the machine is charging for; plasma ignites at or above that recipe's start charge. */
    @Override
    public int getMaxParallel(int aKortexID, long aEUT, long aDuration) {
        Recipe tRecipe = kortex.findRecipe();
        mCurrentRecipe = tRecipe;
        recipeEUt = tRecipe == null ? 0 : aEUT;
        if (tRecipe != null && tRecipe.mEUt < 0) mOutputVoltage = -tRecipe.mEUt;
        recipeTotalTime = tRecipe == null ? 0 : aDuration;
        recipeChargeRequired = tRecipe == null ? 0 : tRecipe.mSpecialValue;
        if (tRecipe == null) return 0;
        return mEnergyCharged >= tRecipe.mSpecialValue ? 1 : 0;
    }

    @Override
    public void receiveOutputs(FluidTankGT[] fluidTanks, ItemStack[] items) {
        for (int i = 0; i < kortex.itemInputs.length && i < 3; i++) {
            if (kortex.itemInputs[i] == null) slotKill(i); else slot(i, kortex.itemInputs[i]);
        }
        for (int i = 0; i < items.length; i++) {
            ItemStack tItem = items[i];
            if (tItem == null) continue;
            items[i] = null;
            if (mCurrentRecipe != null && getRandomNumber(10000) > mCurrentRecipe.getOutputChance(i)) continue;
            if (addStackToSlot(3 + i, tItem.copy())) mSuccessful = true;
        }
        for (FluidTankGT tTank : fluidTanks) if (tTank.has()) {
            for (FluidTankGT tTarget : mTanksOutput) if (tTarget.canFillAll(tTank.get()) && tTarget.fill(tTank.get(), true) > 0) {mSuccessful = true; break;}
            tTank.setEmpty();
        }
    }

    protected void setState(byte state){
        mState.set(state);
        if(state != STATE_STOPPED && state != STATE_ERROR && !IComputePart.tryStartForDemand(getComputeNodes(), ComputePower.Normal, computePowerNeeded)) {
            mState.set(STATE_ERROR);
        }

        if(state==STATE_STOPPED||state==STATE_ERROR) getComputeNodes().forEach(IComputePart::stop);
        updateClientData();
    }

    /** GT switch covers turn the plasma off; stored charge and the ignited recipe are discarded. **/
    protected void stopForSwitchOff() {
        if (mState.get() != STATE_STOPPED) setState(STATE_STOPPED);
        mEnergyCharged = 0;
        mFieldStrength = 0;
        mSuccessful = false;
        nullifyCurrentRecipe();
        getComputeNodes().forEach(IComputePart::stop);
    }

    protected void nullifyCurrentRecipe(){
        progress =0;
        recipeChargeRequired=0;
        recipeEUt=0;
        recipeTotalTime=0;
        mCurrentRecipe=null;
        if (kortex != null) kortex.resetStatus();
    }

    protected void onError(){
        explode(0);
        Arrays.stream(mTanks).forEach(FluidTankGT::setEmpty);
        mEnergyCharged=0;
        mEnergyOutput=0;
        mOutputRate=0;
        mFieldStrength=0;
        nullifyCurrentRecipe();
        setState(STATE_ERROR);
    }

    // GT control / status interfaces
    @Override
    public boolean getStateOnOff() {
        return control.getStateOnOff();
    }

    @Override
    public boolean setStateOnOff(boolean aOnOff) {
        boolean rState = control.setStateOnOff(aOnOff);
        if (!aOnOff && isServerSide()) stopForSwitchOff();
        return rState;
    }

    @Override
    public long getProgressValue(byte aSide) {
        if (mState.is(STATE_RUNNING)) return (long) progress;
        if (mState.is(STATE_CHARGING)) return mEnergyCharged;
        return 0;
    }

    @Override
    public long getProgressMax(byte aSide) {
        if (mState.is(STATE_RUNNING)) return recipeTotalTime;
        if (mState.is(STATE_CHARGING)) return recipeChargeRequired;
        return 0;
    }

    @Override
    public long getEnergyStored(TagData aEnergyType, byte aSide) {
        return aEnergyType == mEnergyTypeCharging ? mEnergyCharged : 0;
    }

    @Override
    public long getEnergyCapacity(TagData aEnergyType, byte aSide) {
        return aEnergyType == mEnergyTypeCharging ? MAX_CHARGE : 0;
    }

    @Override
    public boolean isEnergyCapacitorType(TagData aEnergyType, byte aSide) {
        return aEnergyType == mEnergyTypeCharging;
    }

    @Override
    public Collection<TagData> getEnergyCapacitorTypes(byte aSide) {
        return mEnergyTypeCharging.AS_LIST;
    }

    @Override
    public long getEnergyStored(MultiTileEntityMultiBlockPart aPart, TagData aEnergyType, byte aSide) {
        return getEnergyStored(aEnergyType, aSide);
    }

    @Override
    public long getEnergyCapacity(MultiTileEntityMultiBlockPart aPart, TagData aEnergyType, byte aSide) {
        return getEnergyCapacity(aEnergyType, aSide);
    }

    @Override
    public boolean isEnergyCapacitorType(MultiTileEntityMultiBlockPart aPart, TagData aEnergyType, byte aSide) {
        return isEnergyCapacitorType(aEnergyType, aSide);
    }

    @Override
    public Collection<TagData> getEnergyCapacitorTypes(MultiTileEntityMultiBlockPart aPart, byte aSide) {
        return getEnergyCapacitorTypes(aSide);
    }

    @Override
    public IMeterDetectable.MeterData[] getMeterReceived() {
        return mMeterReceivedLast.toArray(new IMeterDetectable.MeterData[0]);
    }

    @Override
    public IMeterDetectable.MeterData[] getMeterEmitted() {
        return mOutputRate <= 0 ? NO_READINGS : new IMeterDetectable.MeterData[]{new IMeterDetectable.MeterData(mEnergyTypeAccepted, mOutputRate, 1)};
    }

    @Override
    public boolean isRunningPossible() {
        return mStructureOkay && control.getStateOnOff() && !mState.is(STATE_ERROR) && mCurrentRecipe != null;
    }

    @Override
    public boolean isRunning() {
        return mState.is(STATE_RUNNING) && isComputePowerEnough;
    }

    @Override
    public boolean isProcessing() {
        return isRunning();
    }

    @Override
    public boolean isSuccess() {
        return mSuccessful;
    }

    @Override
    public KortexWorker[] getKortexWorkers() {
        if (kortex == null || mState.is(STATE_STOPPED) || mState.is(STATE_ERROR)) return new KortexWorker[0];
        return new KortexWorker[]{kortex};
    }

    /** The tokamak gates its own compute nodes, so the generic ComputeUserComponent source does not see them. **/
    @Override
    public MachineStatus getStatusOverride() {
        if (!mStructureOkay || !control.getStateOnOff()) return null;
        if (mState.is(STATE_ERROR)) return MachineStatus.CUSTOM;
        if (!isComputePowerEnough) return MachineStatus.COMPUTE_INSUFFICIENT;
        if (mState.is(STATE_CHARGING)) return MachineStatus.IGNITION_REQUIRED;
        if (mState.is(STATE_VOID_CHARGING)) return MachineStatus.NO_RECIPE;
        return null;
    }

    @Override
    public String getStatusDetail() {
        if (!mStructureOkay || !control.getStateOnOff()) return null;
        if (mState.is(STATE_ERROR)) return LH.get(I18nHandler.TOKAMAK_STATUS_ERROR);
        if (!isComputePowerEnough) return String.format(LH.get(I18nHandler.TOKAMAK_STATUS_COMPUTE), computePowerNeeded);
        if (mState.is(STATE_CHARGING)) return String.format(LH.get(I18nHandler.TOKAMAK_STATUS_IGNITION), mEnergyCharged, recipeChargeRequired);
        return null;
    }

    @Override
    public boolean onTickCheck(long aTimer) {
        return super.onTickCheck(aTimer) || mState.isChangedAndClear();
    }

    @Override
    protected IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {
        for (FluidTankGT fluidTankGT : mTanksInput) if (fluidTankGT.isEmpty() || aFluidToFill.isFluidEqual(fluidTankGT.getFluid())) return fluidTankGT;
        return null;
    }

    @Override
    protected IFluidTank getFluidTankDrainable2(byte aSide, FluidStack aFluidToDrain) {
        for (FluidTankGT tank:mTanks) if (!tank.isEmpty()) return tank;
        return null;
    }

    @Override
    protected IFluidTank[] getFluidTanks2(byte aSide) {
        return mTanks;
    }

    // Icons
    public final static IIconContainer
            sTextureSides     = new Textures.BlockIcons.CustomIcon("machines/fusion/tokamak/exp/base"),
            sOverlayStop      = new Textures.BlockIcons.CustomIcon("machines/fusion/tokamak/exp/overlay_stop"),
            sOverlayCharge    = new Textures.BlockIcons.CustomIcon("machines/fusion/tokamak/exp/overlay_charging"),
            sOverlayRun       = new Textures.BlockIcons.CustomIcon("machines/fusion/tokamak/exp/overlay_running"),
            sOverlayErr       = new Textures.BlockIcons.CustomIcon("machines/fusion/tokamak/exp/overlay_error"),
            sOverlayVoidCharge= new Textures.BlockIcons.CustomIcon("machines/fusion/tokamak/exp/overlay_void_charging");

    public long doInject (TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject ) {
        if (!control.getStateOnOff()) return 0;
        if(aEnergyType==mEnergyTypeCharging&&!mState.is(STATE_STOPPED)&&!mState.is(STATE_ERROR)){
            if (aDoInject) mMeterReceived.add(new IMeterDetectable.MeterData(aEnergyType, aSize, aAmount));
            mRateCharging += aSize*aAmount;
            return aAmount;
        }
        if(aEnergyType==mEnergyTypeAccepted&&mEnergy<mRate){
            if(aSize > mRate)return 0;
            long amountNeeded = (long)Math.ceil((mRate-mEnergy*1F)/aSize);
            if (aDoInject) mMeterReceived.add(new IMeterDetectable.MeterData(aEnergyType, aSize, amountNeeded));
            mEnergy+=amountNeeded*aSize;
            return amountNeeded;
        }
        return 0;
    }

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (!aShouldSideBeRendered[aSide]) return null;
        if(aSide!=mFacing)return BlockTextureDefault.get(sTextureSides);
        switch(mState.get()){
            case STATE_STOPPED      : return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides),BlockTextureDefault.get(sOverlayStop      ));
            case STATE_CHARGING     : return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides),BlockTextureDefault.get(sOverlayCharge    ));
            case STATE_RUNNING      : return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides),BlockTextureDefault.get(sOverlayRun       ));
            case STATE_ERROR        : return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides),BlockTextureDefault.get(sOverlayErr       ));
            case STATE_VOID_CHARGING: return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides),BlockTextureDefault.get(sOverlayVoidCharge));
        }
        return null;
    }

    @Override public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {
        if (aEmitting) return aEnergyType == mEnergyTypeAccepted;
        return aEnergyType == mEnergyTypeAccepted || aEnergyType == mEnergyTypeCharging;
    }
    public long getEnergySizeInputRecommended(TagData aEnergyType, byte aSide) {return mRate;}
    public long getEnergySizeInputMin(TagData aEnergyType, byte aSide) {return 16;}
    @Override public boolean isEnergyEmittingTo(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return (mOutputRate > 0 || mEnergyOutput > 0) && super.isEnergyEmittingTo(aEnergyType, aSide, aTheoretical);
    }
    @Override public long getEnergySizeOutputRecommended(TagData aEnergyType, byte aSide) {return Math.max(1, mOutputVoltage);}
    @Override public long getEnergySizeOutputMin(TagData aEnergyType, byte aSide) {return Math.max(1, mOutputVoltage);}
    @Override public long getEnergySizeOutputMax(TagData aEnergyType, byte aSide) {return Math.max(1, mOutputVoltage);}
    @Override public long getEnergyOffered(TagData aEnergyType, byte aSide, long aSize) {return aEnergyType == mEnergyTypeAccepted ? mEnergyOutput : 0;}
    @Override public long doExtract(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoExtract) {
        if (aEnergyType != mEnergyTypeAccepted || aSize <= 0 || aAmount <= 0 || mEnergyOutput <= 0) return 0;
        long tAmount = Math.min(aAmount, mEnergyOutput / aSize);
        if (aDoExtract) mEnergyOutput -= tAmount * aSize;
        return tAmount;
    }

    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return mEnergyTypeAccepted.AS_LIST;}

    public short clientDensity(){
        return mState.is(STATE_RUNNING)||mState.is(STATE_CHARGING)?32767: (short) (((Arrays.stream(mTanksInput).mapToLong(FluidTankGT::amount).sum()*1D) / (Arrays.stream(mTanksInput).mapToLong(FluidTankGT::getCapacity).sum()))*32767);
    }
    public short clientTemp(){
        return (short) ((mEnergyCharged*1D/ MAX_CHARGE)*1400);
    }
    public short clientProgress(){
        return mState.is(STATE_RUNNING )? (short) ((progress * 1D / recipeTotalTime) * 32767) : 0;
    }
    //client
    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        return getClientDataPacketByteArray(aSendAll, (byte)UT.Code.getR(mRGBa), (byte)UT.Code.getG(mRGBa), (byte)UT.Code.getB(mRGBa),getDirectionData(),mState.get(),(byte)(control.getStateOnOff()?1:0));
    }

    @Override
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler) {
        mRGBa = UT.Code.getRGBInt(new short[] {UT.Code.unsignB(aData[0]), UT.Code.unsignB(aData[1]), UT.Code.unsignB(aData[2])});
        setDirectionData(aData[3]);
        mState.set(aData[4]);
        if (aData.length > 5) control.applySyncedState(aData[5] == 1);
        return T;
    }

    //inventory
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[mRecipes.mInputItemsCount+mRecipes.mOutputItemsCount+mRecipes.mInputFluidCount+mRecipes.mOutputFluidCount];}
    @Override public boolean canDrop(int aInventorySlot) {return T;}

    private static final int[] ACCESSIBLE_SLOTS = new int[] {0, 1, 2, 3, 4, 5};

    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}

    @Override
    public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {
        if (aSlot >= 3) return F;
        for (int i = 0; i < 3; i++) if (slot(i)== null) {
            return T;
        }
        return F;
    }
    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {
        return T;
    }

    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerClientFusionTokamakT1(aPlayer.inventory, this, mRecipes, aGUIID, "");
    }
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerCommonFusionTokamakT1(aPlayer.inventory, this, mRecipes, aGUIID);
    }

    @Override
    public void onMagnifyingGlass(List<String> aChatReturn) {
        if (lastFailedPos != null) aChatReturn.add(LH.get(I18nHandler.STRUCTURE_LAST_FAILED_POS)+" "+ lastFailedPos.toString());
        MachineControl.of(this).getStatusReport().renderLines(aChatReturn);
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isClientSide() && MachineControl.of(this).handleMeterTool(aTool, aChatReturn)) return 10000;
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public List<IWailaInfoProvider> getWailaInfos(List<IWailaInfoProvider> current) {
        current.add(IWailaTile.instanceInfoState);
        current.add(MachineStatusReport.WAILA_PROVIDER);
        current.add(IWailaTile.instanceInfoEnergyIORange);
        current.add(WAILA_OUTPUT_PROVIDER);
        return current;
    }

    @Override
    public NBTTagCompound getWailaNBT(TileEntity te, NBTTagCompound aNBT) {
        IWailaTile.super.getWailaNBT(te, aNBT);
        MachineStatusReport.writeWaila(this, aNBT);
        aNBT.setLong(WAILA_OUTPUT_RATE, mState.is(STATE_RUNNING) ? mOutputVoltage : mOutputRate);
        aNBT.setLong(WAILA_OUTPUT_VOLTAGE, mOutputVoltage);
        aNBT.setLong(WAILA_OUTPUT_BUFFER, mEnergyOutput);
        return aNBT;
    }

    @Override
    public List<String> getWailaBody(List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return IWailaTile.super.getWailaBody(currentTip, accessor, config);
    }

    private static final IWailaInfoProvider WAILA_OUTPUT_PROVIDER = new IWailaInfoProvider() {
        @Override
        public List<String> getWailaBody(List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
            NBTTagCompound tNBT = accessor.getNBTData();
            long tVoltage = tNBT.getLong(WAILA_OUTPUT_VOLTAGE);
            if (tVoltage <= 0 || tNBT.getLong(WAILA_OUTPUT_RATE) <= 0 && tNBT.getLong(WAILA_OUTPUT_BUFFER) <= 0) return currentTip;
            IWailaTile.addEnergyFlowDesc(currentTip, LH.get(LH.ENERGY_OUTPUT) + " ", TD.Energy.EU, tVoltage, 1, "");
            return currentTip;
        }
    };

    //Structure
    ChunkCoordinates lastFailedPos=null;
    static final IStringBaseStructure structure = new LayerStructure(StructureContext.Axis.Y).layerRule("ABCDEFGGFEKLMM")
            .fixedLayer('A',
                    "                           ",
                    "             BBB           ",
                    "             BBB           ",
                    "             BBB           ",
                    "       B     BBB     B     ",
                    "      BBB    BBB    BBB    ",
                    "       BBB   BBB   BBB     ",
                    "        BBB  BBB  BBB      ",
                    " AAA     BBB BBB BBB       ",
                    " AAA      BBBBBBBBB        ",
                    " AAA       BB   BB         ",
                    "WWABBBBBBBBB     BBBBBBBBB ",
                    "HWABBBBBBBBB     BBBBBBBBB ",
                    "WWABBBBBBBBB     BBBBBBBBB ",
                    " AAA       BB   BB         ",
                    " AAA      BBBBBBBBB        ",
                    " AAA     BBB BBB BBB       ",
                    "        BBB  BBB  BBB      ",
                    "       BBB   BBB   BBB     ",
                    "      BBB    BBB    BBB    ",
                    "       B     BBB     B     ",
                    "             BBB           ",
                    "             BBB           ",
                    "             BBB           ",
                    "                           ",
                    "                           ",
                    "                           "
                    ).fixedLayer('B',
                    "             BBB           ",
                    "             BBB           ",
                    "             BBB           ",
                    "             BBB           ",
                    "       B     BBB     B     ",
                    "      BBB    BBB    BBB    ",
                    "       BBB   BBB   BBB     ",
                    "        BBB  BBB  BBB      ",
                    " AAA     BBB BBB BBB       ",
                    " CCCCCCCCCBBBBBBBBB        ",
                    " CCCCCCCCCCBB   BB         ",
                    "HWBBBBBBBBBB     BBBBBBBBBB",
                    " HBBBBBBBBBB     BBBBBBBBBB",
                    "HWBBBBBBBBBB     BBBBBBBBBB",
                    " CCCCCCCCCCBB   BB         ",
                    " CCCCCCCCCBBBBBBBBB        ",
                    " AAA     BBB BBB BBB       ",
                    "        BBB  BBB  BBB      ",
                    "       BBB   BBB   BBB     ",
                    "      BBB    BBB    BBB    ",
                    "       B     BBB     B     ",
                    "             BBB           ",
                    "             BBB           ",
                    "             BBB           ",
                    "             BBB           ",
                    "                           ",
                    "                           "
                    ).fixedLayer('C',
                    "             DDD           ",
                    "             DDD           ",
                    "                           ",
                    "                           ",
                    "       D             D     ",
                    "      D               D    ",
                    "           EEEEEEE         ",
                    "          EEEEEEEEE        ",
                    " AAA     EEEEEEEEEEE       ",
                    " CCCCCCCEEEEEBBBEEEEE      ",
                    " CCCCCCCEEEEBBBBBEEEE      ",
                    "WWDD    EEEBB   BBEEE    DD",
                    "HWDD    EEEBB   BBEEE    DD",
                    "WWDD    EEEBB   BBEEE    DD",
                    " CCCCCCCEEEEBBBBBEEEE      ",
                    " CCCCCCCEEEEEBBBEEEEE      ",
                    " AAA     EEEEEEEEEEE       ",
                    "          EEEEEEEEE        ",
                    "          FEEEEEEEF        ",
                    "      D   FFFFFFFFF   D    ",
                    "       D  FFFFFFFFF  D     ",
                    "          FFFFFFFFF        ",
                    "          FFFFFFFFF        ",
                    "          FFFDDDFFF        ",
                    "          FFFDDDFFF        ",
                    "          FFFFFFFFF        ",
                    "          FFFFFFFFF        "
                    ).fixedLayer('D',
                    "             DDD           ",
                    "             DDD           ",
                    "                           ",
                    "                           ",
                    "       D    EEEEE    D     ",
                    "      D   EEEEEEEEE   D    ",
                    "        EEE       EEE      ",
                    "        EE         EE      ",
                    " AAA   EE    EEE    EE     ",
                    " AAA   E    EBBBE    E     ",
                    " AAA  EE   EBBBBBE   EE    ",
                    " GDD  EE  EBB   BBE  EE  DD",
                    " CDD  EE  EBB   BBE  EE  DD",
                    " GDD  EE  EBB   BBE  EE  DD",
                    " AAA  EE   EBBBBBE   EE    ",
                    " AAA   E    EBBBE    E     ",
                    " AAA   EE    EEE    EE     ",
                    "        EE         EE      ",
                    "        EEE       EEE      ",
                    "      D   EEEEEEEEE   D    ",
                    "       D  FFEEEEEFF  D     ",
                    "          FFFFFFFFF        ",
                    "          FFFFFFFFF        ",
                    "          FFFDDDFFF        ",
                    "          FFFDDDFFF        ",
                    "          FFFFFFFFF        ",
                    "          FFFFFFFFF        "
                    ).fixedLayer('E',
                    "             DDD           ",
                    "             DDD           ",
                    "                           ",
                    "           EEEEEEE         ",
                    "       D EEE     EEE D     ",
                    "      D EE         EE D    ",
                    "       E             E     ",
                    "      EE             EE    ",
                    "      E      EEE      E    ",
                    "     EE     EBBBE     EE   ",
                    "     E     EBBBBBE     E   ",
                    "  DD E    EBB   BBE    E DD",
                    "  DD E    EBB   BBE    E DD",
                    "  DD E    EBB   BBE    E DD",
                    "     E     EBBBBBE     E   ",
                    "     EE     EBBBE     EE   ",
                    "      E      EEE      E    ",
                    "      EE             EE    ",
                    "       E             E     ",
                    "      D EE         EE D    ",
                    "       D EEE     EEE D     ",
                    "          FEEEEEEEF        ",
                    "          FFFFFFFFF        ",
                    "          FFFDDDFFF        ",
                    "          FFFDDDFFF        ",
                    "          FFFFFFFFF        ",
                    "          FFFFFFFFF        "
                    ).fixedLayer('F',
                    "             DDD           ",
                    "             DDD           ",
                    "             EEE           ",
                    "          EEE   EEE        ",
                    "       DEE         EED     ",
                    "      DE             ED    ",
                    "      E               E    ",
                    "      E               E    ",
                    "     E       EEE       E   ",
                    "     E      EBBBE      E   ",
                    "     E     EBBBBBE     E   ",
                    "  DDE     EBB   BBE     EDD",
                    "  DDE     EBB   BBE     EDD",
                    "  DDE     EBB   BBE     EDD",
                    "     E     EBBBBBE     E   ",
                    "     E      EBBBE      E   ",
                    "     E       EEE       E   ",
                    "      E               E    ",
                    "      E               E    ",
                    "      DE             ED    ",
                    "       DEE         EED     ",
                    "          EEE   EEE        ",
                    "          FFFEEEFFF        ",
                    "          FFFDDDFFF        ",
                    "          FFFDDDFFF        ",
                    "          FFFFFFFFF        ",
                    "          FFFFFFFFF        "
                    ).fixedLayer('G',
                    "             DDD           ",
                    "             DDD           ",
                    "           EEEEEEE         ",
                    "         EE       EE       ",
                    "       DE           ED     ",
                    "      DE             ED    ",
                    "      E               E    ",
                    "     E                 E   ",
                    "     E       EEE       E   ",
                    "    E       EBBBE       E  ",
                    "    E      EBBBBBE      E  ",
                    "  DDE     EBB   BBE     EDD",
                    "  DDE     EBB   BBE     EDD",
                    "  DDE     EBB   BBE     EDD",
                    "    E      EBBBBBE      E  ",
                    "    E       EBBBE       E  ",
                    "     E       EEE       E   ",
                    "     E                 E   ",
                    "      E               E    ",
                    "      DE             ED    ",
                    "       DE           ED     ",
                    "         EE       EE       ",
                    "          FEEEEEEEF        ",
                    "          FFFDDDFFF        ",
                    "          FFFDDDFFF        ",
                    "          FFFFFFFFF        ",
                    "          FFFFFFFFF        "
                    ).fixedLayer('K',
                    "             DDD           ",
                    "             DDD           ",
                    "                           ",
                    "                           ",
                    "       D    EEEEE    D     ",
                    "      D   EEEEEEEEE   D    ",
                    "        EEE       EEE      ",
                    "        EE         EE      ",
                    "       EE    EEE    EE     ",
                    "       E    EBBBE    E     ",
                    "      EE   EBBBBBE   EE    ",
                    "  DD  EE  EBB   BBE  EE  DD",
                    "  DD  EE  EBB   BBE  EE  DD",
                    "  DD  EE  EBB   BBE  EE  DD",
                    "      EE   EBBBBBE   EE    ",
                    "       E    EBBBE    E     ",
                    "       EE    EEE    EE     ",
                    "        EE         EE      ",
                    "        EEE       EEE      ",
                    "      D   EEEEEEEEE   D    ",
                    "       D  FFEEEEEFF  D     ",
                    "          FFFFFFFFF        ",
                    "          FFFFFFFFF        ",
                    "          FFFDDDFFF        ",
                    "          FFFDDDFFF        ",
                    "          FFFFFFFFF        ",
                    "          FFFFFFFFF        "
                    ).fixedLayer('L',
                    "             DDD           ",
                    "             DDD           ",
                    "                           ",
                    "                           ",
                    "       D             D     ",
                    "      D               D    ",
                    "           EEEEEEE         ",
                    "          EEEEEEEEE        ",
                    "         EEEEEEEEEEE       ",
                    "        EEEEEBBBEEEEE      ",
                    "        EEEEBBBBBEEEE      ",
                    "  DD    EEEBB   BBEEE    DD",
                    "  DD    EEEBB   BBEEE    DD",
                    "  DD    EEEBB   BBEEE    DD",
                    "        EEEEBBBBBEEEE      ",
                    "        EEEEEBBBEEEEE      ",
                    "         EEEEEEEEEEE       ",
                    "          EEEEEEEEE        ",
                    "          FEEEEEEEF        ",
                    "      D   FFFFFFFFF   D    ",
                    "       D  FFFFFFFFF  D     ",
                    "          FFFFFFFFF        ",
                    "          FFFFFFFFF        ",
                    "          FFFDDDFFF        ",
                    "          FFFDDDFFF        ",
                    "          FFFFFFFFF        ",
                    "          FFFFFFFFF        "
                    ).fixedLayer('M',
                    "             BBB           ",
                    "             BBB           ",
                    "             BBB           ",
                    "             BBB           ",
                    "       B     BBB     B     ",
                    "      BBB    BBB    BBB    ",
                    "       BBB   BBB   BBB     ",
                    "        BBB  BBB  BBB      ",
                    "         BBB BBB BBB       ",
                    "          BBBBBBBBB        ",
                    "           BB   BB         ",
                    "  BBBBBBBBBB     BBBBBBBBBB",
                    "  BBBBBBBBBB     BBBBBBBBBB",
                    "  BBBBBBBBBB     BBBBBBBBBB",
                    "           BB   BB         ",
                    "          BBBBBBBBB        ",
                    "         BBB BBB BBB       ",
                    "        BBB  BBB  BBB      ",
                    "       BBB   BBB   BBB     ",
                    "      BBB    BBB    BBB    ",
                    "       B     BBB     B     ",
                    "             BBB           ",
                    "             BBB           ",
                    "             BBB           ",
                    "             BBB           ",
                    "                           ",
                    "                           "
                    )

            .where('A', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31015, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN)))
            .where('B', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31025)))
            .where('C', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31019, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID_IN)))
            .where('D', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31026)))
            .where('E', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31027)))
            .where('F', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31028)))
            .where('G', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31024)))
            .where('H', new ComputePartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18002)))
            .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18002, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN)))
            .setOffset(-12,-1,0) ;
    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        computeNodesCoord.clear();
        lastFailedPos = structure.checkStructure(new StructureContext(this, (aPlayer != null || aInventory != null)? StructureContext.StringBaseMode.SET: StructureContext.StringBaseMode.CHECK, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        return lastFailedPos==null;
    }
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide())return true;
        if (MachineControl.onFishRightClick(this, aPlayer)) return true;

        if(!mStructureOkay)aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED+LH.get(I18nHandler.STRUCTURE_ERR)));

        ItemStack equippedItem=aPlayer.getCurrentEquippedItem();
        if (equippedItem!=null && equippedItem.getItem() instanceof ItemProjector) {
            structure.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }
        openGUI(aPlayer, aSide);
        return true;
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN+LH.get(HAS_PROJECTOR_STRUCTURE));
        aList.add(LH.Chat.RED+String.format(LH.get(I18nHandler.TOKAMAK_TOOLTIP_POWER), "2048 / 8192 / 16384"));
        aList.add(LH.Chat.YELLOW+String.format(LH.get(I18nHandler.TOKAMAK_TOOLTIP_START), "1048576 / 4194304"));
        aList.add(LH.Chat.DGRAY+String.format(LH.get(I18nHandler.TOKAMAK_TOOLTIP_COMPUTE), computePowerNeeded));
        super.addToolTips(aList, aStack, aF3_H);
    }
    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return structure.isInsideStructure(this, mFacing, aX, aY, aZ);
    }

    @Override public byte getDefaultSide() {return SIDE_FRONT;}
    @Override public boolean[] getValidSides() {return SIDES_HORIZONTAL;}
    @Override public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.fusion.tokamak.1";
    }

    @Override
    public void receiveSpecialPart(ChunkCoordinates partPos, TileEntity part) {
        computeNodesCoord.add(partPos);
    }
}
