/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */


package cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.generator;

import cn.kuzuanpa.ktfruaddon.i18n.texts.kMessages;
import cn.kuzuanpa.ktfruaddon.recipe.recipeMaps;
import cn.kuzuanpa.ktfruaddon.tile.util.utils;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.code.TagData;
import gregapi.data.FL;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.fluid.FluidTankGT;
import gregapi.recipes.Recipe;
import gregapi.tileentity.behavior.TE_Behavior_Active_Trinary;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.multiblocks.IMultiBlockFluidHandler;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import gregapi.util.OM;
import gregapi.util.ST;
import gregapi.util.UT;
import gregapi.util.WD;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.util.Collection;
import java.util.List;

import static cn.kuzuanpa.ktfruaddon.tile.util.kTileNBT.WORKING_MODE;
import static gregapi.data.CS.*;

public class SunHeater extends TileEntityBase10MultiBlockBase implements IMultiBlockFluidHandler, ITileEntityEnergy{
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.sunheater";
    }

    //决定机器大小
    //this controls the size of machine.
    public final short machineX = 5;
    public final short machineYmax = 16;
    public final short machineZ = 5;
    public short workingMode=0;
    public long mRate=32,mEnergy=0, maxEnergyStorePerLayer =5000000, maxEmitRatePerLayer =256;
    public double timeRemains=-1;
    public TE_Behavior_Active_Trinary mActivity = null;
    public Recipe.RecipeMap mRecipes = recipeMaps.FluidHeating;
    public Recipe mLastRecipe = null;
    private TagData mEnergyTypeEmitted=TD.Energy.HU;
    public long mTemperature=DEFAULT_ENVIRONMENT_TEMPERATURE;
    public boolean clickDoubleCheck=false;
    public short machineY=0;
    //决定结构检测的起始位置，默认情况下是从主方块起始
    //This controls where is the start point to check structure,Default is the position of controller block
    public final short xMapOffset = -2, zMapOffset = 0;
    public FluidTankGT[] mTanks = {new FluidTankGT(80000),new FluidTankGT(80000)};
    public static int[][][] blockIDMap = {{
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
    },{
            {18002, 18002,   0  , 18002, 18002},
            {18002,   0  ,   0  ,   0  , 18002},
            {18002,   0  ,   0  ,   0  , 18002},
            {18002,   0  ,   0  ,   0  , 18002},
            {18002, 18002, 18002, 18002, 18002},
    },{
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002,   0  , 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
    },{
            {31002, 31002, 31002, 31002, 31002},
            {31002, 31003, 31003, 31003, 31002},
            {31002, 31003,   0  , 31003, 31002},
            {31002, 31003, 31003, 31003, 31002},
            {31002, 31002, 31002, 31002, 31002},
    },{
            {31004, 31004, 31004, 31004, 31004},
            {31004, 31004, 31004, 31004, 31004},
            {31004, 31004, 31004, 31004, 31004},
            {31004, 31004, 31004, 31004, 31004},
            {31004, 31004, 31004, 31004, 31004},
    },};
    short k = ST.id(MultiTileEntityRegistry.getRegistry("ktfru.multitileentity").mBlock);
    short g = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);
    public short[][][] registryIDMap = {{
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
    },{
            {g, g, k, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
    },{
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
    },{
            {k, k, k, k, k},
            {k, k, k, k, k},
            {k, k, k, k, k},
            {k, k, k, k, k},
            {k, k, k, k, k},
    },{
            {k, k, k, k, k},
            {k, k, k, k, k},
            {k, k, k, k, k},
            {k, k, k, k, k},
            {k, k, k, k, k},
    }};
    public static boolean[][][] ignoreMap = {{
            {F, F, F, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
    }, {
            {F, F, T, F, F},
            {F, T, T, T, F},
            {F, T, T, T, F},
            {F, T, T, T, F},
            {F, F, F, F, F},
    },{
            {F, F, F, F, F},
            {F, F, F, F, F},
            {F, F, T, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
    },{
            {F, F, F, F, F},
            {F, F, F, F, F},
            {F, F, T, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
    },{
            {F, F, F, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
    }};

    public int getUsage(int blockID ,short registryID){
        if(blockID==18002&&registryID==g)return MultiTileEntityMultiBlockPart.ONLY_FLUID_IN;
        if(blockID==31004&&registryID==k)return MultiTileEntityMultiBlockPart.ONLY_FLUID_OUT;
        return MultiTileEntityMultiBlockPart.NOTHING;
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        mActivity = new TE_Behavior_Active_Trinary(this, aNBT);
        if (aNBT.hasKey(NBT_OUTPUT_MAX)) maxEmitRatePerLayer = aNBT.getLong(NBT_OUTPUT_MAX);
        if (aNBT.hasKey(NBT_ENERGY)) mEnergy = aNBT.getLong(NBT_ENERGY);
        if (aNBT.hasKey(WORKING_MODE)) workingMode = aNBT.getShort(WORKING_MODE);
        mTanks[0].readFromNBT(aNBT, NBT_TANK+".0").setCapacity(80000);
        mTanks[1].readFromNBT(aNBT, NBT_TANK+".1").setCapacity(80000);
    }
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        mActivity.save(aNBT);
        UT.NBT.setNumber(aNBT, NBT_OUTPUT_MAX, maxEmitRatePerLayer);
        UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergy);
        UT.NBT.setNumber(aNBT, WORKING_MODE, workingMode);
        mTanks[0].writeToNBT(aNBT, NBT_TANK+".0");
        mTanks[1].writeToNBT(aNBT, NBT_TANK+".1");
    }
        @Override
    public boolean checkStructure2() {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (worldObj.blockExists(tX, tY, tZ)) {
            boolean tSuccess = T;
            if (getFacing() == (short) 2) {
                tZ += zMapOffset;
                tX -= xMapOffset;
            } else if (getFacing() == (short) 3) {
                tZ -= zMapOffset;
                tX += xMapOffset;
            } else if (getFacing() == (short) 4) {
                tX += zMapOffset;
                tZ += xMapOffset;
            } else if (getFacing() == (short) 5) {
                tX -= zMapOffset;
                tZ -= xMapOffset;
            } else {
                tSuccess = F;
            }
            int checkX, checkY, checkZ;
            for (checkY  = 0; checkY < 3 &&tSuccess; checkY++) for (checkZ = 0; checkZ < machineZ&&tSuccess; checkZ++) for (checkX = 0; checkX < machineX&&tSuccess; checkX++) if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, utils.getRealX(mFacing, tX, checkX, checkZ), tY + checkY -1, utils.getRealZ(mFacing, tZ, checkX, checkZ), blockIDMap[checkY][checkZ][checkX], registryIDMap[checkY][checkZ][checkX], 0, getUsage( blockIDMap[checkY][checkZ][checkX], registryIDMap[checkY][checkZ][checkX]))) tSuccess = ignoreMap[checkY][checkZ][checkX];
            if(!tSuccess)return false;
            for (checkY  = 3; checkY < machineYmax &&tSuccess; checkY++) for (checkZ = 0; checkZ < machineZ && tSuccess; checkZ++) for (checkX = 0; checkX < machineX && tSuccess; checkX++) if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, utils.getRealX(mFacing, tX, checkX, checkZ), tY + checkY - 1, utils.getRealZ(mFacing, tZ, checkX, checkZ), blockIDMap[3][checkZ][checkX], registryIDMap[3][checkZ][checkX], 0, getUsage(blockIDMap[3][checkZ][checkX], registryIDMap[3][checkZ][checkX]))) tSuccess = ignoreMap[3][checkZ][checkX];
            machineY = (short) (checkY - (tSuccess ? 3 : 4));
            if (!tSuccess) checkY--;
            tSuccess=T;
            for (checkZ = 0; checkZ < machineZ && tSuccess; checkZ++) for (checkX = 0; checkX < machineX && tSuccess; checkX++)if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, utils.getRealX(mFacing, tX, checkX, checkZ), tY + checkY - 1, utils.getRealZ(mFacing, tZ, checkX, checkZ), blockIDMap[4][checkZ][checkX], registryIDMap[4][checkZ][checkX], 0, getUsage(blockIDMap[4][checkZ][checkX], registryIDMap[4][checkZ][checkX]))) tSuccess = ignoreMap[4][checkZ][checkX];
            return tSuccess;
        }
        return mStructureOkay;
    }

    static {
        LH.add("gt.tooltip.multiblock.example.complex.1", "5x5x2 of Stainless Steel Walls");
        LH.add("gt.tooltip.multiblock.example.complex.2", "Main Block centered on Side-Bottom and facing outwards");
        LH.add("gt.tooltip.multiblock.example.complex.3", "Input and Output at any Blocks");
    }
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (isServerSide()){
        if(!mStructureOkay)aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED+LH.get(kMessages.SUN_BOILER_ERR)));
        ItemStack equippedItem=aPlayer.getCurrentEquippedItem();
        if (!(OM.is(OD_USB_STICKS[0],equippedItem))) return false;
        NBTTagCompound aNBT = UT.NBT.make();
        UT.NBT.setNumber(aNBT, NBT_TARGET_X, this.xCoord);
        UT.NBT.setNumber(aNBT, NBT_TARGET_Y, this.yCoord);
        UT.NBT.setNumber(aNBT, NBT_TARGET_Z, this.zCoord);

        if (equippedItem.hasTagCompound()) {
            if (clickDoubleCheck) {
                equippedItem.getTagCompound().setTag(NBT_USB_DATA, aNBT);
                equippedItem.getTagCompound().setByte(NBT_USB_TIER, (byte)1);
                aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN+LH.get(kMessages.SUN_BOILER_0)));
                clickDoubleCheck=false;
            } else {
                aPlayer.addChatMessage(new ChatComponentText(LH.Chat.YELLOW+LH.get(kMessages.SUN_BOILER_1)));
                clickDoubleCheck=true;
            }
        }
        if (!equippedItem.hasTagCompound()){
            equippedItem.setTagCompound(UT.NBT.make());
            equippedItem.getTagCompound().setTag(NBT_USB_DATA, aNBT);
            equippedItem.getTagCompound().setByte(NBT_USB_TIER, (byte)1);
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN+LH.get(kMessages.SUN_BOILER_0)));
        }
        }
        return true;
    }

    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer,aIsServerSide);
        if (!aIsServerSide||!mStructureOkay) return;
        if (mEnergy > getMaxEnergyStore()) {
            overheat();
            mEnergy = 0;
        }
        if (mEnergy < 0) mEnergy = 0;
        //AutoOutput
        for (int x = -1; x < 2; x++) for (int z = 1; z < 4; z++) {
            FL.move(mTanks[1],WD.te(worldObj,utils.getRealX(mFacing, xCoord, x, z), yCoord + 3 + machineY, utils.getRealZ(mFacing, zCoord, x, z),SIDE_BOTTOM,false));
        }
        //Do Work
        if(workingMode==0) {
            //emitEnergy
            if (mEnergy <= mRate * 9) return;
            mRate = mEnergy > getMaxEnergyStore() * 0.2 ? getEmitRate() : (int) ((mEnergy / (getMaxEnergyStore() * 0.2f)) * getEmitRate());
            for (int x = -1; x < 2; x++) for (int z = 1; z < 4; z++) {
                TileEntity tileToEmit = worldObj.getTileEntity(utils.getRealX(mFacing, xCoord, x, z), yCoord + 3 + machineY, utils.getRealZ(mFacing, zCoord, x, z));
                if (tileToEmit instanceof ITileEntityEnergy) mEnergy -= mRate * ITileEntityEnergy.Util.insertEnergyInto(TD.Energy.HU, SIDE_BOTTOM, mRate, 1, this, tileToEmit);
            }
        }
        if (workingMode==1) {
            if (mEnergy<16) return;
            //doRecipe
            int mRateCurrent=(mEnergy > getMaxEnergyStore() * 0.2 ? getEmitRate() : (int) ((mEnergy / (getMaxEnergyStore() * 0.2f)) * getEmitRate()))*10;
            if (timeRemains>0){
                if(mRateCurrent<(float)mLastRecipe.mEUt){
                    //Not Enough Energy
                    mActivity.mActive = F;
                    UT.Sounds.send(SFX.MC_FIZZ, this);
                    timeRemains=mLastRecipe.mDuration;
                    return;
                }
                double speed=(mRateCurrent/(float)mLastRecipe.mEUt);
                if(timeRemains>=speed){
                    mEnergy -= Math.abs(mRateCurrent);
                    timeRemains-=speed;
                    return;
                } else {
                    //Done One Recipe
                    mEnergy -= Math.abs(mRateCurrent*(timeRemains/speed));
                    timeRemains=0.0F;
                    mTanks[1].fill(mLastRecipe.mFluidOutputs[0]);
                    mActivity.mActive = F;
                }
            }
            Recipe tRecipe = mRecipes.findRecipe(this, mLastRecipe, T, Long.MAX_VALUE, NI, mTanks, ZL_IS);
            if (tRecipe == null||tRecipe.mFluidInputs.length != 1||tRecipe.mFluidOutputs.length != 1||tRecipe.mEUt>mRateCurrent){
                mLastRecipe=null;
                mActivity.mActive = F;
                return;
            }
            if(!mTanks[1].canFillAll(tRecipe.mFluidOutputs[0]))return;
            if (tRecipe.isRecipeInputEqual(T, T, mTanks, ZL_IS)) {
                mActivity.mActive = T;
                timeRemains=tRecipe.mDuration;
                mLastRecipe = tRecipe;
            }
        }
    }
    @Override
    public boolean onTickCheck(long aTimer) {
        if (aTimer%60==0){
            clickDoubleCheck=false;
            mTemperature= (int) getTemperature();
        }
        return super.onTickCheck(aTimer);
    }
        @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
        aList.add(LH.Chat.YELLOW+LH.CHEAP_OVERCLOCKING);
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.1"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.2"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.3"));
        super.addToolTips(aList, aStack, aF3_H);
    }

    public void overheat() {
        for (int x = -2; x < machineX-2; x++)for(int z=0;z<machineZ;z++)for(int y=1;y<machineY+2;y++)worldObj.setBlock(utils.getRealX(mFacing,xCoord,x,z),yCoord+y,utils.getRealZ(mFacing,zCoord,x,z), Blocks.flowing_lava);
    }
    public int getEmitRate(){
        return mStructureOkay ? (int) (maxEmitRatePerLayer * machineY) : 0;
    }
    public long getMaxEnergyStore(){
        return mStructureOkay?maxEnergyStorePerLayer * machineY +10000000 :10000000;
    }
    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (aTool.equals(TOOL_thermometer)) aChatReturn.add("Temperature: " + getTemperature()+"/"+(961+DEFAULT_ENVIRONMENT_TEMPERATURE)+"K");
        if (aTool.equals(TOOL_magnifyingglass)){
            if(mTanks[0].getFluid()!=null)aChatReturn.add("Tanks: " + mTanks[0].getFluid().getUnlocalizedName()+":"+mTanks[0].getFluid().amount);
            if(mTanks[0].getFluid()!=null&&mTanks[1].getFluid()!=null)aChatReturn.add("Tanks: " + mTanks[0].getFluid().getUnlocalizedName()+":"+mTanks[0].getFluid().amount+"/"+mTanks[1].getFluid().getUnlocalizedName()+":"+mTanks[1].getFluid().amount);
        }
        if (aTool.equals(TOOL_monkeywrench)) workingMode= (short) (workingMode==0?1:0);
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    public float getTemperature(){return ((float)mEnergy/(float)getMaxEnergyStore() *961)+DEFAULT_ENVIRONMENT_TEMPERATURE;}
    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {return true;}
    @Override protected IFluidTank[] getFluidTanks(MultiTileEntityMultiBlockPart aPart, byte aSide) {return mTanks;}
    @Override
    public IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {
        return mTanks[0];
    }
    @Override
    public IFluidTank getFluidTankDrainable2(byte aSide, FluidStack aFluidToDrain) {
        if (!mActivity.mActive && !mTanks[1].isEmpty()) return mTanks[1];
        return null;
    }
    @Override public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {
        return aEmitting && aEnergyType == mEnergyTypeEmitted;}
    public boolean isEnergyAcceptingFrom(TagData aEnergyType, byte aSide, boolean aTheoretical) {return aEnergyType==TD.Energy.HU&&aSide==SIDE_BOTTOM&&mStructureOkay;}
    @Override public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {if (aDoInject) mEnergy += (aAmount * aSize); return aAmount;}

    @Override public boolean isEnergyEmittingTo(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return aSide == SIDE_TOP && super.isEnergyEmittingTo(aEnergyType, aSide, aTheoretical);}
    @Override public long getEnergyOffered(TagData aEnergyType, byte aSide, long aSize) {
        return Math.min(mRate, mEnergy);}
    @Override public long getEnergySizeOutputRecommended(TagData aEnergyType, byte aSide) {return mRate;}
    @Override public long getEnergySizeOutputMin(TagData aEnergyType, byte aSide) {return mRate;}
    @Override public long getEnergySizeOutputMax(TagData aEnergyType, byte aSide) {return mRate;}
    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return mEnergyTypeEmitted.AS_LIST;}


}
