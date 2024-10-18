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

import cn.kuzuanpa.ktfruaddon.i18n.texts.kMessages;
import cn.kuzuanpa.ktfruaddon.item.items.itemFlywheel;
import cn.kuzuanpa.ktfruaddon.material.prefix.prefixList;
import gregapi.code.TagData;
import gregapi.data.CS;
import gregapi.data.LH;
import gregapi.data.OP;
import gregapi.data.TD;
import gregapi.old.Textures;
import gregapi.oredict.OreDictMaterial;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

import static gregapi.data.CS.*;

public class FlywheelBox extends AdaptiveOutputBattery {
    public float mMaxRPM=0;
    public boolean isContentChanged=false,willFlywheelBreak=false;

    public FlywheelBox() {
        this.mEnergyType = TD.Energy.RU;
        this.mEnergyTypeOut = TD.Energy.RU;
    }
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        mInputMin=4;
        mOutputMin=1;
    }
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT, NBT_CAPACITY, mCapacity);
    }
    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        ItemStack aStack=aPlayer.getCurrentEquippedItem();
        if(aStack == null ) for (int i=this.invsize() - 1;i>-1;i--){
            if(!slotHas(i)||!canExtractItem(i,slot(i),SIDE_INSIDE)) continue;
            aPlayer.inventory.setInventorySlotContents(aPlayer.inventory.currentItem, slot(i));
            slotKill(i);
            return true;
        }else if (aStack.getItem() instanceof itemFlywheel) for (int i = 0; i < this.invsize(); i++) {
            if (!canInsertItem(i, aStack, SIDE_INSIDE)||slotHas(i)) continue;
            slot(i, aStack);
            aPlayer.inventory.setInventorySlotContents(aPlayer.inventory.currentItem, null);
            return T;
        }
        return F;
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (aTool.equals(TOOL_magnifyingglass) && isServerSide() && aChatReturn!=null) {
            aChatReturn.add(LH.get(kMessages.INVENTORY)+":");
            for (int i = 0; i < invsize(); i++) {
                if(slotHas(i))aChatReturn.add(slot(i).getDisplayName());
            }
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    protected void updateCurrentOutput() {
        super.updateCurrentOutput();
        mCurrentOutput= (long) Math.floor(Math.min(mMaxRPM,mCurrentOutput));
    }

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
                isContentChanged=false;
            }
        }
        super.onTick2(aTimer,aIsServerSide);
    }

    public boolean isInput (byte aSide) {return aSide==OPOS[mFacing];}

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if(mCapacity==0)return 0;
        aSize = Math.abs(aSize);
        if (aSize > getEnergySizeInputMax(aEnergyType, aSide) || aAmount>mMaxAmpere) {
            if (aDoInject) overcharge(aSize, aEnergyType);
            return aAmount;
        }
        if (aDoInject) {
            mEnergyStored += aAmount * aSize;
            this.receivedEnergy.add(new MeterData(aEnergyType, aSize, aAmount));
        }
        if(mEnergyStored>mCapacity){
            willFlywheelBreak=true;
            overcharge(aSize,aEnergyType);
        }
        return aAmount;
    }

    public void overcharge(long aVoltage, TagData aEnergyType) {
        //break flywheel
        if(willFlywheelBreak) {
            for (int i = 0; i < 4; i++) ST.place(worldObj, xCoord, yCoord, zCoord, OP.scrapGt.mat(OreDictMaterial.get(slot(0).getItemDamage()), 18));
            slotKill(0);
        }
        //break machine
        for (int i = 0; i < 4; i++) ST.place(worldObj,xCoord,yCoord,zCoord, OP.scrapGt.mat(mMaterial,getRandomNumber(18)));
        setToAir();
        UT.Sounds.send(worldObj, CS.SFX.IC_MACHINE_INTERRUPT , 1, 1, getCoords());
        DEB.println("Flywheel Box overcharged with: " + aVoltage + " " + aEnergyType.getLocalisedNameLong()+" capacity:"+mCapacity+" storedEnergy:"+mEnergyStored);
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
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.ORANGE + LH.get(LH.NO_GUI_CLICK_TO_INVENTORY));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override public boolean canInsertItem2 (int aSlot, ItemStack aStack, byte aSide) {isContentChanged=true; return !mActive && aStack != null && prefixList.flywheel.contains(aStack);}
    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {isContentChanged=true; return !mActive;}
    @Override
    public String getTileEntityName() {return "ktfru.multitileentity.energy.storage.flywheel_box";}
}
