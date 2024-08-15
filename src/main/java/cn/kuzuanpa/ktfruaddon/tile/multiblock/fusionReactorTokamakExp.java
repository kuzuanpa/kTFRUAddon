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

import cn.kuzuanpa.ktfruaddon.client.gui.ContainerClientFusionTokamakExp;
import cn.kuzuanpa.ktfruaddon.client.gui.ContainerCommonFusionTokamakExp;
import cn.kuzuanpa.ktfruaddon.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.recipe.recipeMaps;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.IComputeNode;
import cn.kuzuanpa.ktfruaddon.tile.util.utils;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
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
import gregapi.tileentity.base.TileEntityBase01Root;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.multiblocks.*;
import gregapi.util.ST;
import gregapi.util.UT;
import gregapi.util.WD;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.util.*;

import static gregapi.data.CS.*;

public class fusionReactorTokamakExp extends TileEntityBase10MultiBlockBase implements IMultiTileEntity.IMTE_SyncDataByteArray, ITileEntityEnergy, IMultiBlockEnergy, IMultiBlockFluidHandler, IMultiBlockInventory, IMappedStructure {
    public static final byte STATE_STOPPED=0,STATE_CHARGING=1,STATE_RUNNING=2, STATE_ERROR=3,STATE_VOID_CHARGING=4;
    public static final short MAX_FIELD_STRENGTH=400, KEEP_CHARGE_EUt=512;
    public static final long MAX_CHARGE =16*1024L*1024L;
    public byte mState= STATE_STOPPED;
    public boolean isComputePowerEnough=false;
    public short mFieldStrength=0;
    public float dischargeRate=0.2F, progress =0;
    public long mEnergy=0 , mEnergyCharged = 0, mRate = 32, mRateCharging =0,mRateChargingLast = 0,recipeChargeRequired=0, recipeEUt=0,recipeTotalTime=0,computePowerNeeded=1024L;
    public TagData mEnergyTypeAccepted = TD.Energy.EU, mEnergyTypeCharging = TD.Energy.LU;
    public Recipe.RecipeMap mRecipes = recipeMaps.FusionTokamak;
    public Recipe mCurrentRecipe=null,lastRecipe=null;
    public ItemStack[] mOutputItems = ZL_IS;
    public FluidStack[] mOutputFluids = ZL_FS;
    public FluidTankGT[] mTanks = {new FluidTankGT(2000),new FluidTankGT(2000), new FluidTankGT(2000), new FluidTankGT(2000)},
            mTanksInput = {mTanks[0],mTanks[1]}, mTanksOutput ={ mTanks[2],mTanks[3]};
    private final ArrayList<ChunkCoordinates> computeNodesCoord= new ArrayList<>();

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_INPUT)) mRate = aNBT.getLong(NBT_INPUT);
        if (aNBT.hasKey(NBT_STATE)) mState = aNBT.getByte(NBT_STATE);
        if (aNBT.hasKey(NBT_ENERGY)) mEnergy = aNBT.getLong(NBT_ENERGY);
        if (aNBT.hasKey(NBT_ENERGY+".1")) mEnergyCharged = aNBT.getLong(NBT_ENERGY+".1");
        if (aNBT.hasKey(NBT_EFFICIENCY)) mFieldStrength = aNBT.getShort(NBT_EFFICIENCY);
        if (aNBT.hasKey(NBT_ENERGY_EU)) recipeChargeRequired = aNBT.getLong(NBT_ENERGY_EU);
        if (aNBT.hasKey(NBT_PROGRESS)) progress = aNBT.getFloat(NBT_PROGRESS);
        if (aNBT.hasKey(NBT_PROGRESS+".req")) recipeTotalTime = aNBT.getLong(NBT_PROGRESS+".req");
        if (aNBT.hasKey(NBT_INPUT_EU)) recipeEUt = aNBT.getLong(NBT_INPUT_EU);
        if (aNBT.hasKey(NBT_ENERGY_ACCEPTED)) mEnergyTypeAccepted = TagData.createTagData(aNBT.getString(NBT_ENERGY_ACCEPTED));
        if (aNBT.hasKey(NBT_ENERGY_ACCEPTED_2)) mEnergyTypeCharging = TagData.createTagData(aNBT.getString(NBT_ENERGY_ACCEPTED_2));
        if (aNBT.hasKey(NBT_FUELMAP)) mRecipes = Recipe.RecipeMap.RECIPE_MAPS.get(aNBT.getString(NBT_FUELMAP));

        mTanks[0].readFromNBT(aNBT, NBT_TANK+".0").setCapacity(2000);
        mTanks[1].readFromNBT(aNBT, NBT_TANK+".1").setCapacity(2000);
        mTanks[2].readFromNBT(aNBT, NBT_TANK+".2").setCapacity(2000);
        mTanks[3].readFromNBT(aNBT, NBT_TANK+".3").setCapacity(2000);


        mOutputFluids = new FluidStack[mRecipes.mOutputFluidCount];
        for (int i = 0; i < mOutputFluids.length; i++) mOutputFluids[i] = FL.load(aNBT, NBT_TANK_OUT+"."+i);
        mOutputItems = new ItemStack[mRecipes.mOutputItemsCount];
        for (int i = 0; i < mOutputItems.length; i++) mOutputItems[i] = ST.load(aNBT, NBT_INV_OUT+"."+i);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);

        aNBT.setByte(NBT_STATE, mState);
        UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergy);
        UT.NBT.setNumber(aNBT, NBT_ENERGY+".1", mEnergyCharged);
        aNBT.setShort(NBT_EFFICIENCY,mFieldStrength);
        UT.NBT.setNumber(aNBT, NBT_ENERGY_EU,recipeChargeRequired);
        aNBT.setFloat(NBT_PROGRESS, progress);
        aNBT.setLong(NBT_PROGRESS+".req", recipeTotalTime);
        UT.NBT.setNumber(aNBT, NBT_INPUT_EU,recipeEUt);
        aNBT.setByte(NBT_FACING, mFacing);
        aNBT.setByte(NBT_STATE, mState);
        mTanks[0].writeToNBT(aNBT, NBT_TANK+".0");
        mTanks[1].writeToNBT(aNBT, NBT_TANK+".1");
        mTanks[2].writeToNBT(aNBT, NBT_TANK+".2");
        mTanks[3].writeToNBT(aNBT, NBT_TANK+".3");

        for (int i = 0; i < mOutputFluids.length; i++) FL.save(aNBT, NBT_TANK_OUT+"."+i, mOutputFluids[i]);
        for (int i = 0; i < mOutputItems .length; i++) ST.save(aNBT, NBT_INV_OUT +"."+i, mOutputItems [i]);
    }
    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if (!aIsServerSide) return;
        if (!mStructureOkay){
            if(mEnergyCharged>0)onError();
            return;
        }
        isComputePowerEnough = (computeNodesCoord.stream().filter(coord->WD.te(worldObj,coord,true) instanceof IComputeNode).mapToLong(coord->((IComputeNode) WD.te(worldObj,coord,true)).getComputePower()).sum() >= computePowerNeeded);
        if(!isComputePowerEnough){
            mFieldStrength= (short) Math.max(-1,mFieldStrength-1);
            if(mFieldStrength<0&&mEnergyCharged>0)onError();
            return;
        }
        //refresh state
        if(mState==STATE_ERROR){
            mEnergyCharged=0;
            if(mEnergy < KEEP_CHARGE_EUt)return;
            mFieldStrength = (short) Math.min(mFieldStrength+4,MAX_FIELD_STRENGTH);
            setState(STATE_STOPPED);
        }
        //display fluids
        slot(2, FL.display(mTanksInput [0], T, T));
        slot(3, FL.display(mTanksInput [1], T, T));
        slot(4, FL.display(mTanksOutput[0], T, T));

        //If no plasma in reactor
        boolean isInputEmpty = Arrays.stream(mTanksInput).allMatch(FluidTankGT::isEmpty);
        if(isInputEmpty&&mState!=STATE_RUNNING){
            setState(STATE_STOPPED);
            mEnergyCharged=0;
            nullifyCurrentRecipe();
            return;
        }

        //receiveEnergy
        mEnergyCharged +=mRateCharging;
        if (mEnergyCharged < 0) mEnergyCharged = 0;
        if(mEnergyCharged > MAX_CHARGE) onError();
        mRateChargingLast=mRateCharging;
        if(mRateCharging==0)mEnergyCharged= (long) Math.max(0,Math.max(mEnergyCharged-100,mEnergyCharged*(1-dischargeRate)));
        mRateCharging=0;

        //AutoOutput
        if (mTanks[2]!=null) FL.move(mTanks[2], getAdjacentTank(mFacing));
        if (mTanks[3]!=null) FL.move(mTanks[3], getAdjacentTank(OPOS[mFacing]));

        //Field Handle
        if(mEnergy >= (recipeEUt==0? KEEP_CHARGE_EUt : recipeEUt))mFieldStrength= (short) Math.min(MAX_FIELD_STRENGTH,mFieldStrength+4);
        else if(mState==STATE_CHARGING||mState==STATE_RUNNING||mState==STATE_VOID_CHARGING)mFieldStrength= (short) Math.max(-1,mFieldStrength-1);
        mEnergy=0;
        if(mFieldStrength<0){
            if(mEnergyCharged>0) onError();
            return;
        }

        //do recipe
        if(mState==STATE_RUNNING) {
            progress += mEnergyCharged *1F / recipeChargeRequired;
            if (progress < recipeTotalTime) return;
            if(mOutputFluids!=null)Arrays.stream(mOutputFluids).forEach(fluid-> {for (FluidTankGT tank : mTanksOutput) if(tank.fill(fluid, true)>0)return;});
            if(mOutputItems!=null)Arrays.stream(mOutputItems).filter(Objects::nonNull).forEach(item-> addStackToSlot(1, item));
            nullifyCurrentRecipe();
            mState=STATE_STOPPED;
        }


        if(!isInputEmpty&& (mState==STATE_STOPPED||mState==STATE_VOID_CHARGING|| (mState==STATE_CHARGING&&mCurrentRecipe==null) )){
            Recipe tRecipe = mRecipes.findRecipe(this, lastRecipe, T, mRate, NI, mTanksInput, slot(0));
            if (tRecipe !=null&&tRecipe.isRecipeInputEqual(F,F,mTanksInput,slot(0)))setRecipe(tRecipe);
            else setState(STATE_VOID_CHARGING);
        }

        if (mEnergyCharged > recipeChargeRequired && mState==STATE_CHARGING && mCurrentRecipe!=null && mCurrentRecipe.isRecipeInputEqual(T,F,mTanksInput,slot(0)))setState(STATE_RUNNING);
    }

    protected void setState(byte state){
        mState=state;
        if(state==STATE_STOPPED||state==STATE_ERROR)computeNodesCoord.stream().map(coord->WD.te(worldObj,coord,true)).filter(tile-> tile instanceof IComputeNode).forEach(tile->((IComputeNode) tile).stop());
        else computeNodesCoord.stream().map(coord->WD.te(worldObj,coord,true)).filter(tile-> tile instanceof IComputeNode).forEach(tile->((IComputeNode) tile).active());
        updateClientData();
    }
    protected void setRecipe(Recipe recipe){
        setState(STATE_CHARGING);
        lastRecipe=recipe;
        mCurrentRecipe=recipe;
        recipeChargeRequired=recipe.mSpecialValue;
        recipeEUt=recipe.mEUt;
        recipeTotalTime=recipe.mDuration;
        mOutputFluids=recipe.mFluidOutputs;
        for (int i = 0; i < recipe.mOutputs.length; i++) {
            if(getRandomNumber(10000)<=recipe.getOutputChance(i))mOutputItems[i]=recipe.mOutputs[i];
        }
    }

    protected void nullifyCurrentRecipe(){
        progress =0;
        recipeChargeRequired=0;
        recipeEUt=0;
        recipeTotalTime=0;
        mCurrentRecipe=null;
        mOutputFluids = new FluidStack[mRecipes.mOutputFluidCount];
        mOutputItems = new ItemStack[mRecipes.mOutputItemsCount];
    }

    protected void onError(){
        ((TileEntityBase01Root) WD.te(worldObj, xCoord, yCoord + 4, zCoord, false)).explode(2);
        Arrays.stream(mTanks).forEach(FluidTankGT::setEmpty);
        mEnergyCharged=0;
        mFieldStrength=0;
        nullifyCurrentRecipe();
        setState(STATE_ERROR);
    }
    @Override
    protected IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {
        return mTanksInput[0].isEmpty()||aFluidToFill.isFluidEqual(mTanksInput[0].getFluid())?mTanksInput[0]:mTanksInput[1];
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
        if(aEnergyType==mEnergyTypeCharging&&mState!=STATE_STOPPED&&mState!=STATE_ERROR){
            mRateCharging += aSize*aAmount;
            return aAmount;
        }
        if(aEnergyType==mEnergyTypeAccepted&&mEnergy<mRate){
            if(aSize > mRate)return 0;
            long amountNeeded = (long)Math.ceil((mRate-mEnergy*1F)/aSize);
            mEnergy+=amountNeeded*aSize;
            return amountNeeded;
        }
        return 0;
    }

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (!aShouldSideBeRendered[aSide]) return null;
        if(aSide!=mFacing)return BlockTextureDefault.get(sTextureSides);
        switch(mState){
            case STATE_STOPPED      : return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides),BlockTextureDefault.get(sOverlayStop      ));
            case STATE_CHARGING     : return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides),BlockTextureDefault.get(sOverlayCharge    ));
            case STATE_RUNNING      : return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides),BlockTextureDefault.get(sOverlayRun       ));
            case STATE_ERROR        : return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides),BlockTextureDefault.get(sOverlayErr       ));
            case STATE_VOID_CHARGING: return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides),BlockTextureDefault.get(sOverlayVoidCharge));
        }
        return null;
    }

    @Override public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {
        return !aEmitting && (aEnergyType == mEnergyTypeAccepted||aEnergyType == mEnergyTypeCharging);}
    public long getEnergySizeInputRecommended(TagData aEnergyType, byte aSide) {return mRate;}
    public long getEnergySizeInputMin(TagData aEnergyType, byte aSide) {return 16;}
    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return mEnergyTypeAccepted.AS_LIST;}

    public short clientDensity(){
        return mState==STATE_RUNNING||mState==STATE_CHARGING?32767: (short) (((Arrays.stream(mTanksInput).mapToLong(FluidTankGT::amount).sum()*1D) / (Arrays.stream(mTanksInput).mapToLong(FluidTankGT::getCapacity).sum()))*32767);
    }
    public short clientTemp(){
        return (short) ((mEnergyCharged*1D/ MAX_CHARGE)*600);
    }
    public short clientProgress(){
        return mState==STATE_RUNNING ? (short) ((progress * 1D / recipeTotalTime) * 32767) : 0;
    }
    //client
    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        return getClientDataPacketByteArray(aSendAll, (byte)UT.Code.getR(mRGBa), (byte)UT.Code.getG(mRGBa), (byte)UT.Code.getB(mRGBa),getDirectionData(),mState);
    }

    @Override
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler) {
        mRGBa = UT.Code.getRGBInt(new short[] {UT.Code.unsignB(aData[0]), UT.Code.unsignB(aData[1]), UT.Code.unsignB(aData[2])});
        setDirectionData(aData[3]);
        mState=aData[4];
        return T;
    }

    //inventory
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[2+mRecipes.mInputFluidCount+mRecipes.mOutputFluidCount];}
    @Override public boolean canDrop(int aInventorySlot) {return T;}

    private static final int[] ACCESSIBLE_SLOTS = new int[] {0, 1};

    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}

    @Override
    public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {
        if (aSlot >= 2) return F;
        for (int i = 0; i < 2; i++) if (slot(i)== null) {
            return T;
        }
        return F;
    }
    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {
        return T;
    }
    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (isServerSide()) openGUI(aPlayer, aSide);
        return T;
    }

    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerClientFusionTokamakExp(aPlayer.inventory, this, mRecipes, aGUIID, "");
    }
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerCommonFusionTokamakExp(aPlayer.inventory, this, mRecipes, aGUIID);
    }

    public static final short machineX = 17, machineY = 7, machineZ = 18;
    public static final short xMapOffset = -8,yMapOffset = 0, zMapOffset = 0;

    short k = ST.id(MultiTileEntityRegistry.getRegistry("ktfru.multitileentity").mBlock);
    short g = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);

    @Override
    public int getDesign(int mapX, int mapY, int mapZ) {
        return 0;
    }

    @Override
    public int getUsage(int mapX, int mapY, int mapZ) {
        if (getRegistryID(mapX,mapY,mapZ)==g) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
        } else if (getBlockID(mapX,mapY,mapZ) == 31019) {
            return  MultiTileEntityMultiBlockPart.ONLY_FLUID;
        }else{return MultiTileEntityMultiBlockPart.NOTHING;}
    }

    public int getBlockID(int checkX, int checkY, int checkZ){
        return blockIDMap[checkY][checkZ][checkX];
    }

    @Override
    public boolean isIgnored(int mapX, int mapY, int mapZ) {
        return getBlockID(mapX,mapY,mapZ)==0?T:F;
    }

    @Override
    public short getRegistryID(int mapX, int mapY, int mapZ) {
        return getBlockID(mapX,mapY,mapZ)==18002?g:k;
    }

    @Override
    public List<ChunkCoordinates> getComputeNodesCoordList() {
        return computeNodesCoord;
    }

    private ChunkCoordinates lastFailedPos=null;
    @Override
    public boolean checkStructure2() {
        if (!worldObj.blockExists(xCoord, yCoord, zCoord)) return mStructureOkay;
        lastFailedPos = checkMappedStructure(lastFailedPos,machineX,machineY,machineZ,xMapOffset,yMapOffset,zMapOffset);
        return lastFailedPos==null;
    }

    static {

    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
        super.addToolTips(aList, aStack, aF3_H);
    }
    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return new BoundingBox(utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset),yCoord,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset),utils.getRealX(mFacing,utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset),machineX,machineZ),yCoord+machineY,utils.getRealZ(mFacing,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset),machineX,machineZ)).isXYZInBox(aX,aY,aZ);
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        long rReturn = super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
        if (rReturn > 0) return rReturn;
        if (isClientSide()) return 0;
        return 0;
    }

    public void onMagnifyingGlass2(List<String> aChatReturn) {
        aChatReturn.add("Structure is formed already!");
        if(!isComputePowerEnough)aChatReturn.add("Insufficient Compute Power!");
    }


    @Override public byte getDefaultSide() {return SIDE_FRONT;}
    @Override public boolean[] getValidSides() {return SIDES_HORIZONTAL;}
    @Override public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.fusion.tokamak.exp";
    }
    private final static int[][][] blockIDMap = {{
            {  0  ,  0  ,  0  ,  0  ,31015,31019,31015,32005,  0  ,32005,31015,31019,31015,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,31015,31019,31015,18002,31016,18002,31015,31019,31015,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,31015,31019,31015,  0  ,31016,  0  ,31015,31019,31015,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,31019,  0  ,  0  ,31016,  0  ,  0  ,31019,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31016,  0  ,31019,  0  ,  0  ,31016,  0  ,  0  ,31019,  0  ,31016,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,31016,31019,  0  ,  0  ,31016,  0  ,  0  ,31019,31016,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,31016,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,31016,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {31016,31016,31016,31016,31016,31016,31016,  0  ,  0  ,  0  ,31016,31016,31016,31016,31016,31016,31016},
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,31016,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,31016,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
    },{
            {  0  ,  0  ,  0  ,  0  ,  0  ,31015,  0  ,18002,32005,18002,  0  ,31015,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,31015,  0  ,18002,31017,18002,  0  ,31015,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,31015,  0  ,  0  ,  0  ,  0  ,  0  ,31015,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31017,  0  ,31018,31018,31018,31018,31018,31018,31018,  0  ,31017,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,31018,31018,31018,31018,31018,31018,31018,31018,31018,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31018,31018,31018,31018,  0  ,  0  ,  0  ,31018,31018,31018,31018,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,31016,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,  0  },
            {  0  ,  0  ,31018,31018,31018,  0  ,  0  ,31016,31016,31016,  0  ,  0  ,31018,31018,31018,  0  ,  0  },
            {31017,  0  ,31018,31018,31018,  0  ,31016,31016,  0  ,31016,31016,  0  ,31018,31018,31018,  0  ,31017},
            {  0  ,  0  ,31018,31018,31018,  0  ,  0  ,31016,31016,31016,  0  ,  0  ,31018,31018,31018,  0  ,  0  },
            {  0  ,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,31016,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31018,31018,31018,31018,  0  ,  0  ,  0  ,31018,31018,31018,31018,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,31018,31018,31018,31018,31018,31018,31018,31018,31018,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31017,  0  ,31018,31018,31018,31018,31018,31018,31018,  0  ,31017,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31017,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
    },{
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31024,31019,31024,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31024,31017,31024,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,31018,31018,  0  ,  0  ,  0  ,31018,31018,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31017,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,31017,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,  0  ,  0  ,  0  },
            {  0  ,  0  ,31018,  0  ,  0  ,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,  0  ,  0  ,31018,  0  ,  0  },
            {  0  ,  0  ,31018,  0  ,  0  ,  0  ,31018,  0  ,31016,  0  ,31018,  0  ,  0  ,  0  ,31018,  0  ,  0  },
            {  0  ,31018,  0  ,  0  ,  0  ,31018,  0  ,31016,31016,31016,  0  ,31018,  0  ,  0  ,  0  ,31018,  0  },
            {31017,31018,  0  ,  0  ,  0  ,31018,31016,31016,  0  ,31016,31016,31018,  0  ,  0  ,  0  ,31018,31017},
            {  0  ,31018,  0  ,  0  ,  0  ,31018,  0  ,31016,31016,31016,  0  ,31018,  0  ,  0  ,  0  ,31018,  0  },
            {  0  ,  0  ,31018,  0  ,  0  ,  0  ,31018,  0  ,31016,  0  ,31018,  0  ,  0  ,  0  ,31018,  0  ,  0  },
            {  0  ,  0  ,31018,  0  ,  0  ,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,  0  ,  0  ,31018,  0  ,  0  },
            {  0  ,  0  ,  0  ,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31017,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,31017,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,31018,31018,  0  ,  0  ,  0  ,31018,31018,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31017,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
    },{
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31024,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31017,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,31018,31018,  0  ,  0  ,  0  ,31018,31018,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31017,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,31017,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,  0  ,  0  ,  0  },
            {  0  ,  0  ,31018,  0  ,  0  ,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,  0  ,  0  ,31018,  0  ,  0  },
            {  0  ,  0  ,31018,  0  ,  0  ,  0  ,31018,  0  ,31016,  0  ,31018,  0  ,  0  ,  0  ,31018,  0  ,  0  },
            {  0  ,31018,  0  ,  0  ,  0  ,31018,  0  ,31016,31016,31016,  0  ,31018,  0  ,  0  ,  0  ,31018,  0  },
            {31017,31018,  0  ,  0  ,  0  ,31018,31016,31016,  0  ,31016,31016,31018,  0  ,  0  ,  0  ,31018,31017},
            {  0  ,31018,  0  ,  0  ,  0  ,31018,  0  ,31016,31016,31016,  0  ,31018,  0  ,  0  ,  0  ,31018,  0  },
            {  0  ,  0  ,31018,  0  ,  0  ,  0  ,31018,  0  ,31016,  0  ,31018,  0  ,  0  ,  0  ,31018,  0  ,  0  },
            {  0  ,  0  ,31018,  0  ,  0  ,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,  0  ,  0  ,31018,  0  ,  0  },
            {  0  ,  0  ,  0  ,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31017,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,31017,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,31018,31018,  0  ,  0  ,  0  ,31018,31018,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31017,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
    },{
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31017,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,31018,31018,  0  ,  0  ,  0  ,31018,31018,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31017,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,31017,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,  0  ,  0  ,  0  },
            {  0  ,  0  ,31018,  0  ,  0  ,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,  0  ,  0  ,31018,  0  ,  0  },
            {  0  ,  0  ,31018,  0  ,  0  ,  0  ,31018,  0  ,31016,  0  ,31018,  0  ,  0  ,  0  ,31018,  0  ,  0  },
            {  0  ,31018,  0  ,  0  ,  0  ,31018,  0  ,31016,31016,31016,  0  ,31018,  0  ,  0  ,  0  ,31018,  0  },
            {31017,31018,  0  ,  0  ,  0  ,31018,31016,31016,  0  ,31016,31016,31018,  0  ,  0  ,  0  ,31018,31017},
            {  0  ,31018,  0  ,  0  ,  0  ,31018,  0  ,31016,31016,31016,  0  ,31018,  0  ,  0  ,  0  ,31018,  0  },
            {  0  ,  0  ,31018,  0  ,  0  ,  0  ,31018,  0  ,31016,  0  ,31018,  0  ,  0  ,  0  ,31018,  0  ,  0  },
            {  0  ,  0  ,31018,  0  ,  0  ,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,  0  ,  0  ,31018,  0  ,  0  },
            {  0  ,  0  ,  0  ,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31017,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,31017,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,31018,31018,  0  ,  0  ,  0  ,31018,31018,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31017,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
    },{
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31017,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31017,  0  ,31018,31018,31018,31018,31018,31018,31018,  0  ,31017,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,31018,31018,31018,31018,31018,31018,31018,31018,31018,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31018,31018,31018,31018,  0  ,  0  ,  0  ,31018,31018,31018,31018,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,31016,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,  0  },
            {  0  ,  0  ,31018,31018,31018,  0  ,  0  ,31016,31016,31016,  0  ,  0  ,31018,31018,31018,  0  ,  0  },
            {31017,  0  ,31018,31018,31018,  0  ,31016,31016,  0  ,31016,31016,  0  ,31018,31018,31018,  0  ,31017},
            {  0  ,  0  ,31018,31018,31018,  0  ,  0  ,31016,31016,31016,  0  ,  0  ,31018,31018,31018,  0  ,  0  },
            {  0  ,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,31016,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31018,31018,31018,31018,  0  ,  0  ,  0  ,31018,31018,31018,31018,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,31018,31018,31018,31018,31018,31018,31018,31018,31018,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31017,  0  ,31018,31018,31018,31018,31018,31018,31018,  0  ,31017,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31018,31018,31018,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31017,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
    },{
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,31016,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,31016,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {31016,31016,31016,31016,31016,31016,31016,  0  ,  0  ,  0  ,31016,31016,31016,31016,31016,31016,31016},
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,31016,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,31016,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
            {  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,31016,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  },
    }
    };
}
