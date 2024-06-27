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

import cn.kuzuanpa.ktfruaddon.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.fluid.flList;
import cn.kuzuanpa.ktfruaddon.i18n.texts.kMessages;
import cn.kuzuanpa.ktfruaddon.tile.util.utils;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.code.TagData;
import gregapi.data.FL;
import gregapi.data.LH;
import gregapi.fluid.FluidTankGT;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.multiblocks.*;
import gregapi.util.ST;
import gregapi.util.WD;
import gregtech.tileentity.misc.MultiTileEntityFluidSpring;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

import java.util.Collection;
import java.util.List;

import static cn.kuzuanpa.ktfruaddon.i18n.texts.kTooltips.*;
import static gregapi.data.CS.SIDE_BOTTOM;
import static gregapi.data.CS.SIDE_FRONT;
import static gregapi.data.CS.*;

public class oilMiner extends TileEntityBase10MultiBlockBase implements IMultiBlockFluidHandler, IMultiBlockInventory, IMultiBlockEnergy, ITileEntityEnergy, IFluidHandler {
    public int wallID=-1;
    public FluidTankGT mTank = new FluidTankGT(32000);
    public FluidTankGT mTankInput = new FluidTankGT(32000);

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if(aNBT.hasKey(NBT_DESIGN))wallID=aNBT.getInteger(NBT_DESIGN);
        if(aNBT.hasKey(NBT_INPUT_MAX))mInputMax=aNBT.getLong(NBT_INPUT_MAX);
        if(aNBT.hasKey(NBT_INPUT    ))mInput   =aNBT.getLong(NBT_INPUT);
        if(aNBT.hasKey(NBT_INPUT_MIN))mInputMin=aNBT.getLong(NBT_INPUT_MIN);
        if(aNBT.hasKey(NBT_ENERGY))   mEnergy  =aNBT.getLong(NBT_ENERGY);
        if(aNBT.hasKey(NBT_ENERGY_ACCEPTED)) mEnergyTypeAccepted = TagData.createTagData(aNBT.getString(NBT_ENERGY_ACCEPTED));
        mTank.readFromNBT(aNBT, NBT_TANK);
        mTankInput.readFromNBT(aNBT, NBT_TANK+".1");
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        aNBT.setInteger(NBT_DESIGN,wallID);
        aNBT.setLong(NBT_INPUT_MAX,mInputMax);
        aNBT.setLong(NBT_INPUT    ,mInput);
        aNBT.setLong(NBT_INPUT_MIN,mInputMin);
        aNBT.setLong(NBT_ENERGY   ,mEnergy);
        mTank.writeToNBT(aNBT, NBT_TANK);
        mTankInput.writeToNBT(aNBT, NBT_TANK+".1");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        super.addToolTips(aList, aStack, aF3_H);
        aList.add(LH.Chat.YELLOW+LH.get(LH.CHEAP_OVERCLOCKING));
        LH.addEnergyToolTips(this, aList, mEnergyTypeAccepted, null, null, null);
        aList.add(LH.Chat.CYAN+LH.get(LH.STRUCTURE)+": ");
        aList.add(LH.Chat.WHITE+LH.get(OIL_MINER_0));
        aList.add(LH.Chat.WHITE+LH.get(OIL_MINER_1));
        aList.add(LH.Chat.WHITE+LH.get(OIL_MINER_2));
        aList.add(LH.Chat.WHITE+LH.get(OIL_MINER_3));
    }

    static {
        LH.add(OIL_MINER_0,"Walls used in recipes placed on left and right side of main Block, energy input from them");
        LH.add(OIL_MINER_1,"2*3 Oil Miner Drill in back of main Block, springs should under these drills");
        LH.add(OIL_MINER_2,"a layer of 3*3 Walls used in recipes placed on top");
        LH.add(OIL_MINER_3,"fluid input at any top layer wall , fluid auto output at top of the wall below the main block");
    }
    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (aIsServerSide&&mEnergy<0)mEnergy=0;
        if (aIsServerSide&&mStructureOkay&& mEnergy>mInputMin){
            if(mTankInput.has()) {
            for (int x=0;x<3;x++) for (int z=1;z<3;z++){
                TileEntity t = getWorld().getTileEntity(utils.getRealX(mFacing,utils.getRealX(mFacing,xCoord,-1,0),x,z),yCoord-1,utils.getRealZ(mFacing,utils.getRealZ(mFacing,zCoord,-1,0),x,z));
                if(!(t instanceof MultiTileEntityFluidSpring))continue;
                int amount = (int) Math.min(mTankInput.amount(),mEnergy/16);
                if(amount<1)return;
                Fluid fl = getOutputFluid(mTankInput.get(),((MultiTileEntityFluidSpring) t).mFluid);
                if(fl == null)continue;
                mTankInput.remove(mTank.fill(new FluidStack(fl, amount)));
            }
            FL.move(mTank, WD.te(getWorld(),xCoord,yCoord+2,zCoord,SIDE_BOTTOM,false));
            }
            mEnergy=0;
        }
    }
    private Fluid getOutputFluid(FluidStack inputFluid,FluidStack springFluid) {
        if(inputFluid.getFluid().equals(FL.Water.fluid())) {
            if (springFluid.getFluid().equals(FL.Oil_ExtraHeavy.fluid())) return flList.AqueousOilExtraHeavy.fluid;
            if (springFluid.getFluid().equals(FL.Oil_Heavy.fluid())) return flList.AqueousOilHeavy.fluid;
            if (springFluid.getFluid().equals(FL.Oil_Medium.fluid())) return flList.AqueousOilMedium.fluid;
            if (springFluid.getFluid().equals(FL.Oil_Normal.fluid())) return flList.AqueousOilNormal.fluid;
            if (springFluid.getFluid().equals(FL.Oil_Light.fluid())) return flList.AqueousOilLight.fluid;
            if (FL.Lava.is(springFluid)) return null;
            if (!springFluid.getFluid().isGaseous())return springFluid.getFluid();
            return null;
        }
        if(inputFluid.getFluid().equals(FL.Air.fluid())) {
            if (springFluid.getFluid().isGaseous())return springFluid.getFluid();
            return null;
        }
        return null;
    }

    @Override
    public IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {
        return mTankInput;
    }
    @Override
    protected IFluidTank[] getFluidTanks2(byte aSide) {return mTankInput.AS_ARRAY;}

    @Override
    public void onMagnifyingGlass2(List<String> aChatReturn) {
        super.onMagnifyingGlass2(aChatReturn);
        aChatReturn.add(LH.get(kMessages.INPUT )+" "+LH.get(kMessages.TANK)+": "+ (mTankInput.isEmpty()? LH.get(kMessages.EMPTY) :mTankInput.fluid().getLocalizedName(mTankInput.get())+" "+mTankInput.amount()+"L"));
        aChatReturn.add(LH.get(kMessages.OUTPUT)+" "+LH.get(kMessages.TANK)+": "+ (mTank.isEmpty()? LH.get(kMessages.EMPTY) : mTank.fluid().getLocalizedName(mTank.get())+" "+mTank.amount()+"L"));
    }

    //Energy
    long mInputMin=-1,mInputMax=-1,mInput =-1,mEnergy=0;
    TagData mEnergyTypeAccepted;
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject ) {
        if (mStructureOkay&&aEnergyType == mEnergyTypeAccepted) {
            long tInput = Math.min(mInputMax - mEnergy, aSize * aAmount), tConsumed = Math.min(aAmount, (tInput/aSize) + (tInput%aSize!=0?1:0));
            if (aDoInject) mEnergy += tConsumed * aSize;
            return tConsumed;
        }
        return 0;
    }
    public boolean isEnergyAcceptingFrom(TagData aEnergyType, byte aSide, boolean aTheoretical) {return aEnergyType.equals(mEnergyTypeAccepted);}
    @Override public long getEnergySizeInputMin             (TagData aEnergyType, byte aSide) {return mInputMin;}
    @Override public long getEnergySizeInputRecommended     (TagData aEnergyType, byte aSide) {return mInput;}
    @Override public long getEnergySizeInputMax             (TagData aEnergyType, byte aSide) {return mInputMax;}
    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return mEnergyTypeAccepted.AS_LIST;}

    //Structure
    public final short machineX = 3, machineY = 2, machineZ = 3;
    public final short xMapOffset = -1, zMapOffset = 0;
    public int[][][] blockIDMap = {{
            { -1000,   0  , -1000},
            { 31014, 31014, 31014},
            { 31014, 31014, 31014}
    },{
            { -1000, -1000, -1000},
            { -1000, -1000, -1000},
            { -1000, -1000, -1000}
    }};

    short k = ST.id(MultiTileEntityRegistry.getRegistry("ktfru.multitileentity").mBlock);
    short g = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);
    public final short[][][] registryIDMap = {{
            {g, k, g},
            {k, k, k},
            {k, k, k}
    },{
            {g, g, g},
            {g, g, g},
            {g, g, g}
    }};

    public int getUsage(int blockID ,short registryID,int dX,int dY,int dZ){
        if (blockID == wallID&&registryID==g&&dY==0) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
        } else if (blockID == wallID&&registryID==g&&dY==1&&dX==1&&dZ==0) {
            return  MultiTileEntityMultiBlockPart.ONLY_FLUID_OUT;
        } else if (blockID == wallID&&registryID==g&&dY==1) {
            return  MultiTileEntityMultiBlockPart.ONLY_FLUID_IN;
        }else{return MultiTileEntityMultiBlockPart.NOTHING;}
    }

    public int getBlockID(int checkX, int checkY, int checkZ){
        return blockIDMap[checkY][checkZ][checkX] == -1000?wallID:blockIDMap[checkY][checkZ][checkX];
    }

    public  boolean isIgnored(int checkX, int checkY, int checkZ){
        return false;
    }
    public short getRegistryID(int checkX, int checkY, int checkZ){return registryIDMap[checkY][checkZ][checkX];}

    @Override
    public boolean checkStructure2() {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (worldObj.blockExists(tX, tY, tZ)) {
            boolean tSuccess = T;
            tX= utils.getRealX(mFacing,tX,xMapOffset,0);
            tZ=utils.getRealZ(mFacing,tZ,xMapOffset,0);
            int cX, cY, cZ;
            for (cY  = 0; cY < machineY&&tSuccess; cY++) {
                for (cZ = 0; cZ < machineZ&&tSuccess; cZ++) {
                    for (cX = 0; cX < machineX&&tSuccess; cX++) {
                        if(!isIgnored(cX,cY,cZ)) {
                            if (!utils.checkAndSetTarget(this, utils.getRealX(mFacing, tX, cX, cZ), tY + cY, utils.getRealZ(mFacing, tZ, cX, cZ), getBlockID(cX, cY, cZ), getRegistryID(cX, cY, cZ), 0, getUsage(getBlockID(cX, cY, cZ), getRegistryID(cX, cY, cZ),cX,cY,cZ)))
                                tSuccess = F;
                        }
                    }
                }
            }
            return tSuccess;

        }
        return mStructureOkay;
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.oilMiner";
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return new BoundingBox(utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset),yCoord,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset),utils.getRealX(mFacing,utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset),machineX,machineZ),yCoord+machineY,utils.getRealZ(mFacing,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset),machineX,machineZ)).isXYZInBox(aX,aY,aZ);
    }
    @Override public byte getDefaultSide() {return SIDE_FRONT;}
    @Override
    public boolean[] getValidSides() {
        return SIDES_HORIZONTAL;
    }
}
