/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.item.util;

import gregapi.api.Abstract_Mod;
import gregapi.code.IItemContainer;
import gregapi.code.TagData;
import gregapi.item.IItemEnergy;
import gregapi.oredict.OreDictItemData;
import gregapi.util.OM;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static gregapi.data.CS.*;

public enum ItemList implements IItemContainer{
    SiliconBoulePure, MoO2Boule, MoO2BoulePure,
    SiliconPlate8inchTier1,SiliconPlate8inchTier2, MoO2Plate8inchTier1,MoO2Plate8inchTier2, GraphitePlate8inchTier1,GraphitePlate8inchTier2,
    SiliconPlate8inchCleanedTier1,SiliconPlate8inchCleanedTier2, MoO2Plate8inchCleanedTier1,MoO2Plate8inchCleanedTier2, GraphitePlate8inchCleanedTier1,GraphitePlate8inchCleanedTier2,
    SiliconPlate8inchOxidizedTier1,SiliconPlate8inchOxidizedTier2, MoO2Plate8inchOxidizedTier1,MoO2Plate8inchOxidizedTier2, GraphitePlate8inchOxidizedTier1,GraphitePlate8inchOxidizedTier2,
    SiliconPlate8inchCoatedTier1,SiliconPlate8inchCoatedTier2, MoO2Plate8inchCoatedTier1,MoO2Plate8inchCoatedTier2, GraphitePlate8inchCoatedTier1,GraphitePlate8inchCoatedTier2,
    SiliconPlate8inchSoftBakedTier1,SiliconPlate8inchSoftBakedTier2, MoO2Plate8inchSoftBakedTier1,MoO2Plate8inchSoftBakedTier2, GraphitePlate8inchSoftBakedTier1,GraphitePlate8inchSoftBakedTier2,
    CPUPhotomask200um, CPUPhotomask72um, CPUPhotomask40um, CPUPhotomask8um, CPUPhotomask400nm, CPUPhotomask80nm, CPUPhotomask32nm, CPUPhotomask14nm, CPUPhotomask7nm, CPUPhotomask5nm, CPUPhotomask2nm,
    CPUWafer200um, CPUWafer72um, CPUWafer40um, CPUWafer8um, CPUWafer400nm, CPUWafer80nm, CPUWafer32nm, CPUWafer14nm, CPUWafer7nm, CPUWafer5nm, CPUWafer2nm,
    //Wafer200-40um Skipped PreBake
    //Wafer8umPreBaked, CPUWafer400nmPreBaked, CPUWafer80nmPreBaked, CPUWafer32nmPreBaked, CPUWafer14nmPreBaked, CPUWafer7nmPreBaked, CPUWafer5nmPreBaked, CPUWafer2nmPreBaked,
    CPUWafer200umDeveloped, CPUWafer72umDeveloped, CPUWafer40umDeveloped, CPUWafer8umDeveloped, CPUWafer400nmDeveloped, CPUWafer80nmDeveloped, CPUWafer32nmDeveloped, CPUWafer14nmDeveloped, CPUWafer7nmDeveloped, CPUWafer5nmDeveloped, CPUWafer2nmDeveloped,
    CPUWafer200umHardBaked, CPUWafer72umHardBaked, CPUWafer40umHardBaked, CPUWafer8umHardBaked, CPUWafer400nmHardBaked, CPUWafer80nmHardBaked, CPUWafer32nmHardBaked, CPUWafer14nmHardBaked, CPUWafer7nmHardBaked, CPUWafer5nmHardBaked, CPUWafer2nmHardBaked,
    CPUDie200um, CPUDie72um, CPUDie40um, CPUDie8um, CPUDie400nm, CPUDie80nm, CPUDie32nm, CPUDie14nm, CPUDie7nm, CPUDie5nm, CPUDie2nm,
    CPUTF386, CPUTF586, CPUAE300, CPUAE740, CPUGT200, CPUGT3490, CPUGT6799, CPUGT9890, CPUGT13586, CPUIF2, CPUIF7,
    CPUTF386e, CPUTF586e, CPUAE300s, CPUAE740s, CPUGT200s, CPUGT3490s, CPUGT6799s, CPUGT9890s, CPUGT13586s,
    ComputerTF3386,ComputerTF3386S,ComputerTF3586,ComputerTF3586S,ComputerGT1000,ComputerGT1090,ComputerGT2000,ComputerGT2090,ComputerGT2660,ComputerGT2680,ComputerGT2699,ComputerGT2660v2,ComputerGT2680v2,ComputerGT2699v2,ComputerGT2660v3,ComputerGT2680v3,ComputerGT2699v3,ComputerGT2660v4,ComputerGT2680v4,ComputerGT2699v4,ComputerGT2680v3e,ComputerGT2699v3e,ComputerGT2680v4e,ComputerGT2699v4e,

    Proton, Anti_Proton, Electron, Positron, Neutron, Alpha_Particle, Neutrino, Anti_Neutrino, Higgs_Boson, Kerr_Blackhole,

    fakeItemPhotomask,fakeItemBoule,fakeItemLaserCutting,fakeItemSiliconPlateCleaned,fakeItemSiliconPlateOxidized,fakeItemSiliconPlateCoated,fakeItemSiliconPlateSoftBaked,fakeItemCPUWafer,fakeItemSiliconCPUWaferDeveloped,fakeItemSiliconCPUWaferHardBaked,fakeItemSiliconCPUWaferDoped,fakeItemSiliconCPUWaferChecked,fakeItemCPUDie
    ;

    //Copied from gt6
    private ItemStack mStack;
    private boolean mHasNotBeenSet = T;

    @Override
    public IItemContainer set(Item aItem) {
        mHasNotBeenSet = F;
        if (aItem == null) {
//          new Exception().printStackTrace(GT_Log.deb);
            return this;
        }
        mStack = ST.amount(1, ST.make(aItem, 1, 0));
        return this;
    }

    public IItemContainer set(Item aItem, long aMeta) {
        mHasNotBeenSet = F;
        if (aItem == null) {
//          new Exception().printStackTrace(GT_Log.deb);
            return this;
        }
        mStack = ST.amount(1, ST.make(aItem, 1, aMeta));
        return this;
    }

    @Override
    public IItemContainer set(ItemStack aStack) {
        mHasNotBeenSet = F;
        if (ST.invalid(aStack)) {
//          new Exception().printStackTrace(GT_Log.deb);
            return this;
        }
        mStack = ST.amount(1, aStack);
        return this;
    }

    public IItemContainer set(Item aItem, OreDictItemData aData, Object... aOreDict) {
        mHasNotBeenSet = F;
        if (aItem == null) {
//          new Exception().printStackTrace(GT_Log.deb);
            return this;
        }
        ItemStack aStack = ST.make(aItem, 1, 0);
        mStack = ST.amount(1, aStack);
        if (aData != null && !OM.reg(aData.toString(), ST.make(aItem, 1, W))) OM.data(ST.make(aItem, 1, W), aData);
        for (Object tOreDict : aOreDict) OM.reg(tOreDict, ST.make(aItem, 1, W));
        return this;
    }

    public IItemContainer set(ItemStack aStack, OreDictItemData aData, Object... aOreDict) {
        mHasNotBeenSet = F;
        if (ST.invalid(aStack)) {
//          new Exception().printStackTrace(DEB);
            return this;
        }
        mStack = ST.amount(1, aStack);
        if (aData != null && !OM.reg(aData.toString(), ST.amount(1, aStack))) OM.data(ST.amount(1, aStack), aData);
        for (Object tOreDict : aOreDict) OM.reg(tOreDict, ST.amount(1, aStack));
        return this;
    }

    @Override
    public Item item() {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (ST.invalid(mStack)) return null;
        return mStack.getItem();
    }

    @Override
    public Block block() {
        return ST.block(get(0));
    }

    @Override
    public boolean exists() {
        return ST.valid(mStack);
    }

    @Override
    public final boolean hasBeenSet() {
        return !mHasNotBeenSet;
    }

    @Override
    public boolean equal(Object aStackOrBlock) {
        return mStack != null && (aStackOrBlock instanceof Block ? aStackOrBlock != NB && ST.block_(mStack) == aStackOrBlock : equal(aStackOrBlock, F, F));
    }

    @Override
    public boolean equal(Object aStack, boolean aWildcard, boolean aIgnoreNBT) {
        return mStack != null && (aWildcard ? ST.item((ItemStack)aStack) == ST.item_(mStack) : ST.equal((ItemStack)aStack, mStack, aIgnoreNBT));
    }

    @Override
    public ItemStack get(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (ST.invalid(mStack)) return ST.copyFirst(aReplacements);
        return ST.amount(aAmount, OM.get_(mStack));
    }

    @Override
    public ItemStack getWildcard(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (ST.invalid(mStack)) return ST.copyFirst(aReplacements);
        return ST.copyAmountAndMeta(aAmount, W, OM.get_(mStack));
    }

    @Override
    public ItemStack wild(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (ST.invalid(mStack)) return ST.copyFirst(aReplacements);
        return ST.copyAmountAndMeta(aAmount, W, OM.get_(mStack));
    }

    @Override
    public ItemStack getUndamaged(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (ST.invalid(mStack)) return ST.copyFirst(aReplacements);
        return ST.copyAmountAndMeta(aAmount, 0, OM.get_(mStack));
    }

    @Override
    public ItemStack getAlmostBroken(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (ST.invalid(mStack)) return ST.copyFirst(aReplacements);
        return ST.copyAmountAndMeta(aAmount, mStack.getMaxDamage()-1, OM.get_(mStack));
    }

    @Override
    public ItemStack getWithName(long aAmount, String aDisplayName, Object... aReplacements) {
        ItemStack rStack = get(1, aReplacements);
        if (ST.invalid(rStack)) return null;
        rStack.setStackDisplayName(aDisplayName);
        return ST.amount(aAmount, rStack);
    }

    @Override
    public ItemStack getWithNameAndNBT(long aAmount, String aDisplayName, NBTTagCompound aNBT, Object... aReplacements) {
        ItemStack rStack = get(1, aReplacements);
        if (ST.invalid(rStack)) return null;
        UT.NBT.set(rStack, aNBT);
        if (aDisplayName != null) rStack.setStackDisplayName(aDisplayName);
        return ST.amount(aAmount, rStack);
    }

    @Override
    public ItemStack getWithCharge(long aAmount, long aEnergy, Object... aReplacements) {
        ItemStack rStack = get(1, aReplacements);
        if (ST.invalid(rStack)) return null;
        if (rStack.getItem() instanceof IItemEnergy) for (TagData tEnergyType : ((IItemEnergy)rStack.getItem()).getEnergyTypes(rStack)) ((IItemEnergy)rStack.getItem()).setEnergyStored(tEnergyType, rStack, aEnergy);
        return ST.amount(aAmount, rStack);
    }

    @Override
    public ItemStack getWithMeta(long aAmount, long aMetaValue, Object... aReplacements) {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (ST.invalid(mStack)) return ST.copyFirst(aReplacements);
        return ST.copyAmountAndMeta(aAmount, aMetaValue, OM.get_(mStack));
    }

    @Override
    public ItemStack getWithDamage(long aAmount, long aMetaValue, Object... aReplacements) {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (ST.invalid(mStack)) return ST.copyFirst(aReplacements);
        return ST.copyAmountAndMeta(aAmount, aMetaValue, OM.get_(mStack));
    }

    @Override
    public ItemStack getWithNBT(long aAmount, NBTTagCompound aNBT, Object... aReplacements) {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (ST.invalid(mStack)) return ST.copyFirst(aReplacements);
        ItemStack rStack = ST.amount(aAmount, OM.get_(mStack));
        UT.NBT.set(rStack, aNBT);
        return rStack;
    }

    @Override
    public IItemContainer registerOre(Object... aOreNames) {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        for (Object tOreName : aOreNames) OM.reg(tOreName, get(1));
        return this;
    }

    @Override
    public IItemContainer registerWildcardAsOre(Object... aOreNames) {
        if (mHasNotBeenSet && Abstract_Mod.sFinalized < Abstract_Mod.sModCountUsingGTAPI) ERR.println("The Enum '" + name() + "' has not been set to an Item at this time!");
        for (Object tOreName : aOreNames) OM.reg(tOreName, wild(1));
        return this;
    }

    @Override public Item getItem() {return item();}
    @Override public Block getBlock() {return block();}
}