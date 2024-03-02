/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.accelerator;

import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.render.ITexture;
import gregapi.tileentity.ITileEntityFunnelAccessible;
import gregapi.tileentity.ITileEntityTapAccessible;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.behavior.TE_Behavior_Active_Trinary;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.machines.ITileEntityAdjacentOnOff;
import gregapi.tileentity.machines.ITileEntityRunningActively;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.*;

public class exampleMachine extends TileEntityBase09FacingSingle implements IFluidHandler, ITileEntityTapAccessible, ITileEntityFunnelAccessible, ITileEntityEnergy, ITileEntityRunningActively, ITileEntityAdjacentOnOff, IMultiTileEntity.IMTE_SyncDataByteArray {

    public boolean mStopped = F;
    public byte mItemAutoInputA,mItemAutoInputB,mItemAutoOutput;
    public long mEnergy = 0, mInput = 32, mInputMin = 16, mInputMax = 64;
    //输入的能源类型
    public static TagData mEnergyTypeAccepted = TD.Energy.KU;
    //存储该机器的状态,活跃/待机/停机等
    public TE_Behavior_Active_Trinary mActivity = null;

    @Override
    public ITexture getTexture2(Block block, int i, byte b, boolean[] booleans) {
        return null;
    }
    //读取NBT信息,有一部分是从tileEntityInit的地方弄来的,其他的一般就是保存在世界里的
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        mEnergy = aNBT.getLong(NBT_ENERGY);
        mActivity = new TE_Behavior_Active_Trinary(this, aNBT);
        if (aNBT.hasKey(NBT_STOPPED)) mStopped = aNBT.getBoolean(NBT_STOPPED);
        if (aNBT.hasKey(NBT_INPUT)) mInput = aNBT.getLong(NBT_INPUT);
        if (aNBT.hasKey(NBT_INPUT_MIN)) mInputMin = aNBT.getLong(NBT_INPUT_MIN);
        if (aNBT.hasKey(NBT_INPUT_MAX)) mInputMax = aNBT.getLong(NBT_INPUT_MAX);
        if (aNBT.hasKey(NBT_INV_SIDE_AUTO_IN)) mItemAutoInputA = aNBT.getByte(NBT_INV_SIDE_AUTO_IN);
        //Use tank auto input as inv input side B
        if (aNBT.hasKey(NBT_TANK_SIDE_AUTO_IN)) mItemAutoInputB = aNBT.getByte(NBT_TANK_SIDE_AUTO_IN);
        if (aNBT.hasKey(NBT_INV_SIDE_AUTO_OUT)) mItemAutoOutput = aNBT.getByte(NBT_INV_SIDE_AUTO_OUT);
        if (aNBT.hasKey(NBT_ENERGY_EMITTED)) mEnergyTypeAccepted = TagData.createTagData(aNBT.getString(NBT_ENERGY_EMITTED));
    }
    //保存NBT信息
    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergy);
        UT.NBT.setBoolean(aNBT, NBT_STOPPED, mStopped);
        mActivity.save(aNBT);
    }
    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.ORANGE   + LH.get(LH.NO_GUI_FUNNEL_TAP_TO_TANK));
        super.addToolTips(aList, aStack, aF3_H);
    }
    //每tick执行一次这个方法,一般这个机器的主要机制都在这里
    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if (aIsServerSide) {
            //AutoInput
            doInputItems();
            doOutputItems();
        }
    }

    //获取自动输入物品的对象,这里是获取预先定义好的输入面对着的那个容器
    public DelegatorTileEntity<IInventory> getItemInputTarget(byte aSide) {
        return getAdjacentInventory(aSide);
    }
    //获取自动输出物品的对象,同理
    public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) {
        return getAdjacentTileEntity(aSide);
    }
    //输入物品
    public void doInputItems() {
        //因为nbt那边定义的是"前面""背面"这样的,所以用这个结合机器的朝向(mFacing)来获取对应的面(东面,西面这样的)
        byte tAutoInput = FACING_TO_SIDE[mFacing][mItemAutoInputA];
        if (SIDES_VALID[tAutoInput]) ST.moveAll(getItemInputTarget(tAutoInput), delegator(tAutoInput));
        byte tAutoInputB = FACING_TO_SIDE[mFacing][mItemAutoInputB];
        if (SIDES_VALID[tAutoInputB]) ST.moveAll(getItemInputTarget(tAutoInputB), delegator(tAutoInput));
    }
    //输出物品
    public void doOutputItems() {
        byte tAutoOutput = FACING_TO_SIDE[mFacing][mItemAutoOutput];
        if (SIDES_VALID[tAutoOutput]) ST.moveAll(delegator(tAutoOutput), getItemOutputTarget(tAutoOutput));
    }
    //当工具点击时执行
    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        long rReturn = super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
        if (rReturn > 0) return rReturn;

        if (isClientSide()) return 0;

        if (aTool.equals(TOOL_magnifyingglass)) {
            if (aChatReturn != null) {
                aChatReturn.add("INV:");
                aChatReturn.add("0: "+(slot(0)==null?"empty":slot(0).getUnlocalizedName()));
                aChatReturn.add("1: "+(slot(1)==null?"empty":slot(1).getUnlocalizedName()));
                aChatReturn.add("2: "+(slot(2)==null?"empty":slot(2).getUnlocalizedName()));
                aChatReturn.add("3: "+(slot(3)==null?"empty":slot(3).getUnlocalizedName()));
            }
            return 1;
        }
        if(aTool.equals(TOOL_monkeywrench)){
            ST.move(this,0,2);
            ST.move(this,1,3);
        }
        if (getFacingTool() != null && aTool.equals(getFacingTool())) {byte aTargetSide = UT.Code.getSideWrenching(aSide, aHitX, aHitY, aHitZ); if (getValidSides()[aTargetSide]) {byte oFacing = mFacing; mFacing = aTargetSide; updateClientData(); causeBlockUpdate(); onFacingChange(oFacing); return 10000;}}
        return 0;
    }
    //检测帧,一般用于检查机器状态是否正常,结构是否正常(多方块结构)等
    public boolean onTickCheck(long aTimer) {
        return mActivity.check(mStopped) || super.onTickCheck(aTimer);
    }
    @Override public byte getDefaultSide() {return SIDE_FRONT;}
    //定义机器可能的朝向
    @Override public boolean[] getValidSides() {return SIDES_HORIZONTAL;}

    //Energy
    //下面基本是机械代码,复制粘贴就行
    //定义这个机器有关的能源
    @Override public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {return !aEmitting && aEnergyType == mEnergyTypeAccepted;}
    //最低输入
    public long getEnergySizeInputMin(TagData aEnergyType, byte aSide) {
        return this.mInputMin;
    }
    //推荐输入
    public long getEnergySizeInputRecommended(TagData aEnergyType, byte aSide) {return this.mInput;}
    //最高输入
    public long getEnergySizeInputMax(TagData aEnergyType, byte aSide) {return this.mInputMax;}
    @Override public Collection<TagData> getEnergyTypes(byte aSide) {
        return mEnergyTypeAccepted.AS_LIST;
    }
    @Override public boolean getStateRunningPassively() {
        return mActivity.mActive;
    }
    @Override public boolean getStateRunningPossible() {
        return mActivity.mActive;
    }
    @Override public boolean getStateRunningActively() {
        return mActivity.mActive;
    }
    @Override public boolean setAdjacentOnOff(boolean aOnOff) {mStopped = !aOnOff;return !mStopped;}
    @Override public boolean setStateOnOff(boolean aOnOff) {mStopped = !aOnOff;return !mStopped;}
    @Override public boolean getStateOnOff() {return !mStopped;}


    //物品栏有关的
    //定义默认物品栏大小
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[4];}
    //当被破坏时是否掉落物品
    @Override public boolean canDrop(int aInventorySlot) {
        return T;
    }
    private static final int[] ACCESSIBLE_SLOTS = new int[] {0, 1,2,3};
    //定义从某侧可访问的物品栏槽位
    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}
    //当漏斗,管道之类的输入物品时调用,检测物品能不能输入到某物品栏槽位
    @Override
    public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {
        if (aSlot >= 2) return F;
        for (int i = 0; i < 2; i++) if ((slot(i)==null||ST.equal(aStack, slot(i)))&&i==aSlot) return T;
        return F;
    }
    //同理
    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {
        return aSlot>1&&!mActivity.mActive;
    }
    //机器的ID,不能有重复的
    @Override public String getTileEntityName() {return "ktfru.multitileentity.accelerator.example";}

}
