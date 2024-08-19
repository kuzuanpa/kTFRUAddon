/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.energy.storage;

import cn.kuzuanpa.ktfruaddon.item.items.itemFlywheel;
import cn.kuzuanpa.ktfruaddon.material.prefix.prefixList;
import cpw.mods.fml.common.FMLLog;
import gregapi.code.TagData;
import gregapi.data.CS;
import gregapi.data.OP;
import gregapi.data.TD;
import gregapi.old.Textures;
import gregapi.oredict.OreDictMaterial;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.energy.TileEntityBase10EnergyBatBox;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.Level;

import static gregapi.data.CS.*;

public class FlywheelBox extends TileEntityBase10EnergyBatBox {
    public long mCapacity=0, mMaxAmpere=1;
    public float mMaxRPM=0,mCurrentRPM=0,mLossRate=0;
    public boolean isContentChanged=false;

    public FlywheelBox() {
        this.mEnergyType = TD.Energy.RU;
        this.mEnergyTypeOut = TD.Energy.RU;
    }
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey("ktfru.nbt.maxOutputAmpere")) mMaxAmpere = aNBT.getLong("ktfru.nbt.maxOutputAmpere");
        if (aNBT.hasKey("ktfru.nbt.lossRate")) mLossRate = aNBT.getFloat("ktfru.nbt.maxOutputAmpere");

        if (aNBT.hasKey(NBT_CAPACITY)) mCapacity = aNBT.getLong(NBT_CAPACITY);
        if (aNBT.hasKey("ktfru.nbt.maxRPM")) mMaxRPM = aNBT.getFloat("ktfru.nbt.maxRPM");

    }
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT, NBT_CAPACITY, mCapacity);
        aNBT.setFloat("ktfru.nbt.maxRPM",mMaxRPM);
    }
    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {return false;}

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if (aIsServerSide) {
            if(isContentChanged){
                mMaxRPM=Integer.MAX_VALUE;
                long energyPerRotate=0;
                boolean isMaxRPMChanged=false;
                for (int i = 0; i < this.invsize(); i++) if(slotHas(i)&&(slot(i).getItem() instanceof itemFlywheel)) {
                    float thisMaxRPM=itemFlywheel.getMaxRPM(ST.meta(slot(i)));
                    mMaxRPM=Math.min(thisMaxRPM,mMaxRPM);
                    isMaxRPMChanged=true;
                    energyPerRotate+= (long) (Math.floor(itemFlywheel.getMaxStorage(ST.meta(slot(i))) / thisMaxRPM));
                }
                if(!isMaxRPMChanged)mMaxRPM=0;
                mCapacity= (long) (energyPerRotate*mMaxRPM);
                mCurrentRPM=(mEnergy*1F/mCapacity)*mMaxRPM;
                isContentChanged=false;
            }

            FMLLog.log(Level.ERROR,"maxRPM"+mMaxRPM+"/currentRPM"+mCurrentRPM+"/capacity"+mCapacity+"/energy"+mEnergy);
            mActive = (mEnergy >= mOutput);

            mAmperageLastEmitting=0;
            if (mActive) {
                if (!mStopped) {
                    long amount = (long) Math.ceil(mCurrentRPM/getEnergySizeOutputMax(mEnergyTypeOut,mFacing));
                    long tSize = (long) Math.floor(mCurrentRPM/amount);
                    long tOutput = (mMode == 0 ? amount : Math.min(mMode, amount));
                    if (tOutput > 0) {
                        long tAmountUsed = ITileEntityEnergy.Util.emitEnergyToNetwork(mEnergyTypeOut, tSize, tOutput, this);
                        mEmitsEnergy = (tAmountUsed > 0);
                        mAmperageLastEmitting = tAmountUsed;
                        mEnergy -= tSize * tAmountUsed;
                    }
                }
                if (mTimer % 600 == 5) doDefaultStructuralChecks();
            }

            receivedEnergyLast.clear();
            receivedEnergyLast.addAll(receivedEnergy);
            receivedEnergy.clear();
        }
    }

    public boolean isInput (byte aSide) {return aSide==OPOS[mFacing];}

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if(mCapacity==0)return 0;
        aSize = Math.abs(aSize);
        if (aSize > getEnergySizeInputMax(aEnergyType, aSide)||aAmount>mMaxAmpere) {
            if (aDoInject) overcharge(aSize, aEnergyType);
            return aAmount;
        }
        if (aDoInject) {
            mEnergy += aAmount * aSize;
            this.receivedEnergy.add(new MeterData(aEnergyType, aSize, aAmount));
            if(mEnergy>mCapacity)overcharge(aSize,aEnergyType);
        }
        mCurrentRPM=(mEnergy*1F/mCapacity)*mMaxRPM;
        return aAmount;
    }

    public void overcharge(long aVoltage, TagData aEnergyType) {
        //flywheel break
        for (int i = 0; i < 4; i++) ST.place(worldObj,xCoord,yCoord,zCoord, OP.scrapGt.mat(OreDictMaterial.get(slot(0).getItemDamage()),18));
        slotKill(0);
        //machine break
        for (int i = 0; i < 4; i++) ST.place(worldObj,xCoord,yCoord,zCoord, OP.scrapGt.mat(mMaterial,getRandomNumber(18)));
        setToAir();
        UT.Sounds.send(worldObj, CS.SFX.IC_MACHINE_INTERRUPT , 1, 1, getCoords());
        DEB.println("Flywheel Box overcharged with: " + aVoltage + " " + aEnergyType.getLocalisedNameLong()+" capacity:"+mCapacity+" storedEnergy:"+mEnergy);
    }

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (!aShouldSideBeRendered[aSide]) return null;
        return BlockTextureMulti.get(BlockTextureDefault.get(sColoreds[aSide==mFacing||aSide==OPOS[mFacing]?0:1], mRGBa));
    }

    // Icons
    private static IIconContainer sColoreds[] = new IIconContainer[] {
            new Textures.BlockIcons.CustomIcon("machines/energystorages/flywheel_box/colored/axis"),
            new Textures.BlockIcons.CustomIcon("machines/energystorages/flywheel_box/colored/side"),
    };

    @Override
    public long getEnergySizeInputMin(TagData aEnergyType, byte aSide) {return 4;}

    @Override public boolean canInsertItem2 (int aSlot, ItemStack aStack, byte aSide) {isContentChanged=true; return aStack != null && prefixList.flywheel.contains(aStack);}
    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {isContentChanged=true; return !mActive;}
    @Override
    public String getTileEntityName() {return "ktfru.multitileentity.energy.storage.flywheel_box";}
}
