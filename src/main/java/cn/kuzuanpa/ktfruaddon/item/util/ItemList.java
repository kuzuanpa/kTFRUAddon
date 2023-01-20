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
    Proton, Anti_Proton, Electron, Positron, Neutron, Alpha_Particle, Neutrino, Anti_Neutrino, Higgs_Boson, Kerr_Blackhole,
    BioCircuit, NeuralComputer
    ;

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
    @SuppressWarnings("deprecation")
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

    @SuppressWarnings("deprecation") @Override public Item getItem() {return item();}
    @SuppressWarnings("deprecation") @Override public Block getBlock() {return block();}
}