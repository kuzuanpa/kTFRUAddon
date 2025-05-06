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

package cn.kuzuanpa.ktfruaddon.tile.multiblock;

import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.IMappedStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.fluid.FluidTankGT;
import gregapi.oredict.OreDictManager;
import gregapi.oredict.OreDictMaterialStack;
import gregapi.tileentity.data.ITileEntityTemperature;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;

import static gregapi.data.CS.*;

public class fuelDeburnFactory extends TileEntityBase10MultiBlockMachine implements IMappedStructure, ITileEntityTemperature {

    public TagData mEnergyTypeHeat = TD.Energy.HU;
    public long mTempMax = Integer.MAX_VALUE, mMassTotal=1,mMassSelf=1,maxStrictEUt=1024,mMassLast=1;
    public float mTemp = C, recipeBestTemp= C, recipeFactor = 0.1F;
    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if (mStopped) return 0;
        boolean tPositive = (aSize > 0);
        aSize = Math.abs(aSize);
        if (aEnergyType == mEnergyTypeHeat) {
            if (aDoInject) mTemp += (aSize * aAmount)*64F/mMassTotal;
            this.receivedEnergy.add(new MeterData(aEnergyType,aSize, aAmount));
            return aAmount;
        }
        if (aEnergyType == mEnergyTypeAccepted) {
            if(aDoInject && aSize > getEnergySizeInputMax(aEnergyType, aSide))overcharge(aSide,aEnergyType);
            if (aDoInject) mStateNew = tPositive;
            long tInput = Math.min(mInputMax - mEnergy, aSize * aAmount), tConsumed = Math.min(aAmount, (tInput/aSize) + (tInput%aSize!=0?1:0));
            if (aDoInject) mEnergy += tConsumed * aSize;
            this.receivedEnergy.add(new MeterData(aEnergyType, aSize, tConsumed));
            return tConsumed;
        }
        return 0;
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        mSpecialIsStartEnergy=false;
        if (aNBT.hasKey(NBT_ENERGY_ACCEPTED_2)) mEnergyTypeHeat = TagData.createTagData(aNBT.getString(NBT_ENERGY_ACCEPTED_2));
        if (aNBT.hasKey("ktfru.nbt.massSelf")) mMassTotal = mMassSelf = aNBT.getLong("ktfru.nbt.massSelf");
        if (aNBT.hasKey(NBT_TEMPERATURE+".max")) mTempMax = aNBT.getLong(NBT_TEMPERATURE+".max");

        if (aNBT.hasKey(NBT_TEMPERATURE)) mTemp = aNBT.getFloat(NBT_TEMPERATURE);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        aNBT.setFloat(NBT_TEMPERATURE, mTemp);
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(aTool.equals(TOOL_thermometer)){
            if(aChatReturn!=null)aChatReturn.add(LH.get(I18nHandler.TEMPERATURE) +": "+ mTemp + " / " + mTempMax);
            return 1;
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if(mInventoryChanged){
            updateMass();
        }
        super.onTick2(aTimer, aIsServerSide);
    }

    public void updateMass(){
        mMassLast = mMassTotal;
        mMassTotal = mMassSelf;
        for (int i = 0; i < invsize(); i++) {
            if(!slotHas(i) || OreDictManager.INSTANCE.getItemData(slot(i)) == null)continue;
            mMassTotal += (long) OreDictManager.INSTANCE.getItemData(slot(i)).getAllMaterialStacks().stream().mapToDouble(OreDictMaterialStack::weight).sum();
        }
        for (FluidTankGT tankGT:mTanksInput) {
            if(tankGT==null || tankGT.isEmpty())continue;
            mMassTotal += tankGT.fluid().getDensity()* tankGT.amount()/1000 ;
        }
        if(mMassLast<mMassTotal) {
            float deltaTemp = mTemp - C;
            cooldown( mTemp-(deltaTemp*mMassLast/mMassTotal+C));
        }
    }

    @Override
    public boolean doActive(long aTimer, long aEnergy) {
        if(mTemp > mTempMax)overcharge(mInputMax,TD.Energy.HU);
        //Natural Cooldown
        cooldown(Math.min(10F*Math.abs(mTemp-C)/mMassTotal, Math.abs(mTemp-C)));

        //Promote extra progress when Temp is Suitable, and if not suitable decreases the progress
        if (mMaxProgress > 0 && !(mSpecialIsStartEnergy && mChargeRequirement > 0) && mProgress <= mMaxProgress) {
            mProgress += (long) (aEnergy * getTempFactor());
        }
        if(mProgress<0)mProgress=0;
        return super.doActive(aTimer, aEnergy);
    }


    @Override
    public int checkRecipe(boolean aApplyRecipe, boolean aUseAutoIO) {
        int i = super.checkRecipe(aApplyRecipe, aUseAutoIO);

        if(mCurrentRecipe!=null) {
            updateMass();
            recipeBestTemp = mCurrentRecipe.mSpecialValue;
            recipeFactor = 20.1F-20*Math.min(1F, mCurrentRecipe.mEUt*1F/maxStrictEUt);
        }
        return i;
    }

    public void cooldown(float value){
        if(Math.abs(mTemp-C)< value){
            mTemp=C;
            return;
        }
        value=Math.abs(value);
        mTemp -= mTemp>C ? value: -value;
    }
    public float getTempFactor() {
        float delta = Math.abs(mTemp - recipeBestTemp);
        return Math.min(128, ((256*recipeFactor / (float)Math.sqrt(delta)) - (float) Math.pow(delta, 1.5F) / recipeFactor )/recipeFactor);
    }

    @Override
    public long getTemperatureValue(byte aSide) {
        return (long) mTemp;
    }

    @Override
    public long getTemperatureMax(byte aSide) {
        return mTempMax;
    }
    //这是设置主方块的物品提示
    //controls tooltip of controller block
    static {
        LH.add("ktfru.multitileentity.multiblock.fuel_deburner.1", "Store Energy into Fuel.");
        LH.add("ktfru.multitileentity.multiblock.fuel_deburner.2", "Main Block centered on Side-Bottom and facing outwards");
        LH.add("ktfru.multitileentity.multiblock.fuel_deburner.3", "Input and Output at any Blocks");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.multitileentity.multiblock.fuel_deburner.1"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.multitileentity.multiblock.fuel_deburner.2"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.multitileentity.multiblock.fuel_deburner.3"));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte aSide, Fluid aOutput) {
        return getAdjacentTank(SIDE_BOTTOM);
    }

    @Override
    public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) {
        return getAdjacentTileEntity(SIDE_BOTTOM);
    }

    @Override
    public DelegatorTileEntity<IInventory> getItemInputTarget(byte aSide) {
        return null;
    }

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte aSide) {
        return null;
    }


    //Structure
    public final short sizeX = 11, sizeY = 19, sizeZ = 14;
    public final short xMapOffset = -6,yMapOffset=-1, zMapOffset = 0;

    short k = GTTileEntityRegistry.ktfruaddon;
    short g = GTTileEntityRegistry.gregtech;
    @Override
    public TileDesc[] getTileDescs(int mapX, int mapY, int mapZ) {
        return new TileDesc[]{ new TileDesc(getRegistryID(mapX, mapY, mapZ), getBlockID(mapX, mapY, mapZ),getUsage(mapX, mapY, mapZ))};
    }

    public int getUsage(int mapX, int mapY, int mapZ) {
int registryID = getRegistryID(mapX,mapY,mapZ), blockID = getBlockID(mapX, mapY, mapZ);
        if(mapY == 1 && blockID == 18002)return MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
        if (blockID == 18002&&registryID==k) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
        } else if (blockID == 18002||blockID==18022&&registryID==g) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_OUT;
        }else{return MultiTileEntityMultiBlockPart.NOTHING;}
    }

    public int getBlockID(int checkX, int checkY, int checkZ){
        return blockIDMap[checkY][checkZ][checkX];
    }

    public  boolean isIgnored(int checkX, int checkY, int checkZ){
        return getBlockID(checkX,checkY,checkZ) == 0;
    }
    public short getRegistryID(int checkX, int checkY, int checkZ){return getBlockID(checkX,checkY,checkZ) == 18002?g:k;}

    ChunkCoordinates lastFailedPos=null;
    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = checkMappedStructure(null, sizeX, sizeY, sizeZ,xMapOffset,yMapOffset,zMapOffset, aClickedAt, aPlayer, aInventory);

        return lastFailedPos==null;
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return new BoundingBox(utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset),yCoord,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset),utils.getRealX(mFacing,utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset), sizeX, sizeZ),yCoord+ sizeY,utils.getRealZ(mFacing,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset), sizeX, sizeZ)).isXYZInBox(aX,aY,aZ);
    }

    public final static int[][][] blockIDMap = {
    {
           {  0  , 31037, 31037, 31037, 31037, 31037, 31037, 31037, 31037, 31037, 31037}
         , {  0  , 31037, 18002, 18002, 18002, 31037,   0  ,   0  , 31037, 31037, 31037}
         , {  0  , 31037, 18002, 18002, 18002, 31037,   0  ,   0  , 31037, 31037, 31037}
         , {  0  , 31037, 18002, 18002, 18002, 31037,   0  ,   0  , 31037, 31037, 31037}
         , {  0  , 31037, 31037, 31037, 31037, 31037,   0  ,   0  , 31037, 31037, 31037}
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  , 31037, 31037, 31037}
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  , 31037, 31037, 31037}
         , {  0  , 31037, 31037, 31037, 31037, 31037, 31037, 31037, 31037, 31037, 31037}
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  , 31037, 31037, 31037}
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  , 31037, 31037, 31037}
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  , 31037, 31037, 31037}
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  , 31037, 31037, 31037}
         , {  0  , 31037, 31037, 31037, 31037, 31037, 31037,   0  , 31037, 31037, 31037}
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
    }
         , {
           {  0  , 31037,   0  ,   0  ,   0  , 31037,0/*C*/,   0  ,   0  , 31038,   0  }
         , {  0  ,   0  , 18002, 18002, 18002,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  , 18002,   0  , 18002,   0  ,   0  ,   0  , 18002, 31038, 18002}
         , {  0  ,   0  , 18002, 18002, 18002,   0  ,   0  ,   0  ,   0  , 31038,   0  }
         , {  0  , 31037,   0  , 31037,   0  , 31037,   0  ,   0  , 18002, 31038, 18002}
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  , 31038,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  , 18002, 31038, 18002}
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  ,   0  , 31038,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  , 18002, 31038, 18002}
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  , 31038,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  , 18002, 31038, 18002}
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  , 31038,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  , 18002, 31038, 18002}
         , {  0  ,   0  ,   0  ,   0  , 31038, 31038, 31038, 31038, 31038, 31038,   0  }
    }
    , {
         {  0  , 31037,   0  , 31037, 31037, 31037,   0  ,   0  ,   0  , 31038,   0  }
         , {  0  , 31037, 18002, 18002, 18002,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002,   0  , 18002, 31037,   0  ,   0  , 18002,   0  , 18002}
         , {  0  ,   0  , 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 31037, 31038, 31037, 31037,   0  ,   0  , 18002,   0  , 18002}
         , {  0  ,   0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  , 18002,   0  , 18002}
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  , 18002,   0  , 18002}
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  , 18002,   0  , 18002}
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  , 18002,   0  , 18002}
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
    }
    , {
         {  0  , 31037,   0  ,   0  ,   0  , 31037,   0  ,   0  ,   0  , 31038,   0  }
         , {  0  ,   0  , 18002, 18002, 18002,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  , 18002,   0  , 18002,   0  ,   0  ,   0  , 18002,   0  , 18002}
         , {  0  ,   0  , 18002, 18002, 18002,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037,   0  , 31037,   0  , 31037,   0  ,   0  , 18002,   0  , 18002}
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  , 18002,   0  , 18002}
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  , 18002,   0  , 18002}
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  , 18002,   0  , 18002}
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  , 18002,   0  , 18002}
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
    }
    , {
         {  0  , 31037, 31037, 31037,   0  , 31037,   0  ,   0  ,   0  , 31038,   0  }
         , {  0  ,   0  , 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002,   0  , 18002, 31037,   0  ,   0  , 18002,   0  , 18002}
         , {  0  , 31037, 18002, 18002, 18002,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 31037, 31038, 31037, 31037,   0  ,   0  , 18002,   0  , 18002}
         , {  0  ,   0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  , 18002,   0  , 18002}
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  , 18002,   0  , 18002}
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  , 18002,   0  , 18002}
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  , 18002,   0  , 18002}
         , {  0  ,   0  ,   0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
    }
    , {
         {  0  , 31037,   0  ,   0  ,   0  , 31037,   0  ,   0  ,   0  , 31038,   0  }
         , {  0  ,   0  , 18002, 18002, 18002,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  , 18002,   0  , 18002,   0  ,   0  ,   0  , 18002,   0  , 18002}
         , {  0  ,   0  , 18002, 18002, 18002,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037,   0  , 31037,   0  , 31037,   0  ,   0  , 18002,   0  , 18002}
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  , 18002,   0  , 18002}
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  , 18002,   0  , 18002}
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {31038, 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  , 18002,   0  , 18002}
         , {31038, 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {31038, 31037, 18002, 18002, 18002, 18002, 31037,   0  , 18002,   0  , 18002}
         , {31038, 31038, 31038,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
    }
    , {
         {  0  , 31037,   0  , 31037, 31037, 31037,   0  ,   0  ,   0  , 31038,   0  }
         , {  0  , 31037, 18002, 18002, 18002,   0  ,   0  ,   0  ,   0  , 31038,   0  }
         , {  0  , 31037, 18002,   0  , 18002, 31037,   0  ,   0  , 18002, 31038, 18002}
         , {  0  ,   0  , 18002, 18002, 18002, 31037,   0  ,   0  ,   0  , 31038,   0  }
         , {  0  , 31037, 31037, 31038, 31037, 31037,   0  ,   0  , 18002, 31038, 18002}
         , {  0  ,   0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  , 31038,   0  }
         , {  0  ,   0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  , 18002, 31038, 18002}
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  ,   0  , 31038,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  , 18002, 31038, 18002}
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  , 31038,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  , 18002, 31038, 18002}
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  , 31038,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  , 18002, 31038, 18002}
         , {  0  ,   0  ,   0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
    }
    , {
         {  0  , 31037,   0  ,   0  ,   0  , 31037,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  , 18002, 18002, 18002,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  , 18002,   0  , 18002,   0  ,   0  ,   0  , 31037, 31037, 31037}
         , {  0  ,   0  , 18002, 18002, 18002,   0  ,   0  ,   0  , 31037, 31037, 31037}
         , {  0  , 31037,   0  , 31037,   0  , 31037,   0  ,   0  , 31037, 31037, 31037}
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  , 31037, 31037, 31037}
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  , 31037, 31037, 31037}
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  , 31037, 31037, 31037}
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  , 31037, 31037, 31037}
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  , 31037, 31037, 31037}
         , {31038, 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  , 31037, 31037, 31037}
         , {31038, 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  , 31037, 31037, 31037}
         , {31038, 31037, 18002, 18002, 18002, 18002, 31037,   0  , 31037, 31037, 31037}
         , {31038, 31038, 31038,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
    }
    , {
         {  0  , 31037, 31037, 31037, 31037, 31037,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 31037, 31037, 31037, 31037,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
    }
    , {
         {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {31038, 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {31038, 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {31038, 31037, 18002, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  }
         , {31038, 31038, 31038,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
    }
    , {
         {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  , 31038, 31038, 31038, 31038,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037, 31038,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002, 31038,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002, 31038,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
    }
    , {
         {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {31038, 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {31038, 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {31038, 31037, 18002, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  }
         , {31038, 31038, 31038,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
    }
    , {
         {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  , 31038, 31038, 31038, 31038,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037, 31038,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002, 31038,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002, 31038,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
    }
    , {
         {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {  0  , 18002,   0  ,   0  ,   0  ,   0  , 18002,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
    }
    , {
         {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 31037, 31037, 31037, 31037, 31037,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 18002, 18002, 18002, 18002, 31037,   0  ,   0  ,   0  ,   0  }
         , {  0  , 31037, 31037, 31037, 31037, 31037, 31037,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
    }
    , {
         {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
    }
    , {
         {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
    }
    , {
           {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
    }
    , {
           {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  , 31038,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
         , {  0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  ,   0  }
    }
    };
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.fuel_deburner";
    }
}