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
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.List;

import static gregapi.data.CS.OPOS;
import static gregapi.data.CS.TOOL_magnifyingglass;

public class FlywheelBoxElec extends FlywheelBox implements IMultiTileEntity.IMTE_SyncDataByteArray {
    public byte capacityState=0;
    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (aTool.equals(TOOL_magnifyingglass) && isServerSide() && aChatReturn!=null) {
            aChatReturn.add(LH.get(kMessages.STORED_ENERGY)+": "+mEnergyStored);
            aChatReturn.add(LH.get(kMessages.CAPACITY)+": "+mCapacity);
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }
    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if(mCapacity==0)return 0;
        aSize = Math.abs(aSize);
        if (aSize > getEnergySizeInputMax(aEnergyType, aSide)) {
            if (aDoInject) overcharge(aSize, aEnergyType);
            return aAmount;
        }
        long canReceiveAmount = Math.min( (long) Math.ceil((mCapacity-mEnergyStored)*1F/aSize) , mMaxAmpere);
        if (aDoInject) {
            mEnergyStored += Math.min(canReceiveAmount,aAmount) * aSize;
            this.receivedEnergy.add(new MeterData(aEnergyType, aSize, aAmount));
            if(mEnergyStored>mCapacity){
                mEnergyStored=mCapacity;
            }
        }
        updateCurrentOutput();
        return Math.min(canReceiveAmount,aAmount);
    }

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (!aShouldSideBeRendered[aSide]) return null;
        return BlockTextureMulti.get(BlockTextureDefault.get(sTextures[aSide==mFacing||aSide==OPOS[mFacing]?0:1], mRGBa),BlockTextureDefault.get(sTextures[aSide==mFacing||aSide==OPOS[mFacing]?2:3]), capacityState>0&&aSide!=mFacing&&aSide!=OPOS[mFacing]?BlockTextureDefault.get(capacityState==1?sTextures[4]:sTextures[5], true):null);
    }

    // Icons
    private static final IIconContainer[] sTextures = new IIconContainer[] {
            new Textures.BlockIcons.CustomIcon("machines/energystorages/flywheel_box_elec/colored/axis"),
            new Textures.BlockIcons.CustomIcon("machines/energystorages/flywheel_box_elec/colored/side"),
            new Textures.BlockIcons.CustomIcon("machines/energystorages/flywheel_box_elec/overlay/axis"),
            new Textures.BlockIcons.CustomIcon("machines/energystorages/flywheel_box_elec/overlay/side"),
            new Textures.BlockIcons.CustomIcon("machines/energystorages/flywheel_box_elec/overlay/side_has"),
            new Textures.BlockIcons.CustomIcon("machines/energystorages/flywheel_box_elec/overlay/side_full")
    };

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        super.addToolTips(aList, aStack, aF3_H);
    }
    @Override
    public String getTileEntityName() {return "ktfru.multitileentity.energy.storage.flywheel_box.elec";}

    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        return aSendAll ? getClientDataPacketByteArray(aSendAll, (byte) UT.Code.getR(mRGBa), (byte)UT.Code.getG(mRGBa), (byte)UT.Code.getB(mRGBa), getVisualData(), getDirectionData(), (byte) (mEnergyStored>mCapacity*0.99?2:mEnergyStored>0?1:0)) : getClientDataPacketByte(aSendAll, getVisualData());
    }
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler){
        capacityState=aData[5];
        return super.receiveDataByteArray(aData,aNetworkHandler);
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if(aIsServerSide&&aTimer%200==0)updateClientData();
    }
}
