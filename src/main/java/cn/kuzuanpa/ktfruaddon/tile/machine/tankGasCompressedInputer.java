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

package cn.kuzuanpa.ktfruaddon.tile.machine;

import cn.kuzuanpa.ktfruaddon.i18n.texts.kTooltips;
import cn.kuzuanpa.ktfruaddon.tile.ICompressGasTank;
import gregapi.code.TagData;
import gregapi.data.FL;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.fluid.FluidTankGT;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.*;

public class tankGasCompressedInputer extends TileEntityBase09FacingSingle implements ITileEntityEnergy,IFluidHandler {
    public static TagData mEnergyTypeAccepted = TD.Energy.KU;
    public long mInput=0,mInputMin=0,mInputMax=0;
    FluidTankGT mTank = new FluidTankGT(64000);
    public static IIconContainer[] mTexturesMaterial = L6_IICONCONTAINER, mTexturesInactive = L6_IICONCONTAINER;

    static {
        mTexturesMaterial = new IIconContainer[]{
                new Textures.BlockIcons.CustomIcon("machines/tankgascompressedinputer/colored/bottom"),
                new Textures.BlockIcons.CustomIcon("machines/tankgascompressedinputer/colored/top"),
                new Textures.BlockIcons.CustomIcon("machines/tankgascompressedinputer/colored/left"),
                new Textures.BlockIcons.CustomIcon("machines/tankgascompressedinputer/colored/front"),
                new Textures.BlockIcons.CustomIcon("machines/tankgascompressedinputer/colored/right"),
                new Textures.BlockIcons.CustomIcon("machines/tankgascompressedinputer/colored/back")};
        mTexturesInactive = new IIconContainer[]{
                new Textures.BlockIcons.CustomIcon("machines/tankgascompressedinputer/overlay/bottom"),
                new Textures.BlockIcons.CustomIcon("machines/tankgascompressedinputer/overlay/top"),
                new Textures.BlockIcons.CustomIcon("machines/tankgascompressedinputer/overlay/left"),
                new Textures.BlockIcons.CustomIcon("machines/tankgascompressedinputer/overlay/front"),
                new Textures.BlockIcons.CustomIcon("machines/tankgascompressedinputer/overlay/right"),
                new Textures.BlockIcons.CustomIcon("machines/tankgascompressedinputer/overlay/back")};
    }
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);

        if (aNBT.hasKey(NBT_INPUT)) {mInput = aNBT.getLong(NBT_INPUT); mInputMin = mInput / 2; mInputMax = mInput * 2;}
        if (aNBT.hasKey(NBT_INPUT_MIN)) {mInputMin = aNBT.getLong(NBT_INPUT_MIN);}
        if (aNBT.hasKey(NBT_INPUT_MAX)) {mInputMax = aNBT.getLong(NBT_INPUT_MAX);}
        if (aNBT.hasKey(NBT_TANK_CAPACITY)) {mTank.setCapacity(aNBT.getLong(NBT_TANK_CAPACITY));}

    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        LH.addEnergyToolTips(this, aList, mEnergyTypeAccepted, null, null, null);
        aList.add(LH.Chat.CYAN    + LH.get(kTooltips.TANK_GAS_COMPRESSED_INPUTER));
        aList.add(LH.Chat.DGRAY    + LH.get(LH.TOOL_TO_DETAIL_MAGNIFYINGGLASS));
    }

    public void doOutputFluids(int amount) {
        DelegatorTileEntity<IFluidHandler> aTo = getAdjacentTank(OPOS[mFacing]);
        if (aTo == null||mTank.getFluid()==null)return;
        ICompressGasTank target = null;
        if (aTo.mTileEntity instanceof ICompressGasTank) target= (ICompressGasTank) aTo.mTileEntity;
        if ((aTo.mTileEntity instanceof MultiTileEntityMultiBlockPart)&& ((MultiTileEntityMultiBlockPart)aTo.mTileEntity).getTarget(F) instanceof ICompressGasTank) target=(ICompressGasTank)((MultiTileEntityMultiBlockPart)aTo.mTileEntity).getTarget(F);
        if (target==null) return;
        FluidStack fluidStack = mTank.getFluid().copy();
        fluidStack.amount=amount;
        int used = UT.Code.bindInt((target.fillCompressedGas(fluidStack)));
        if (used <= 0) return;
        mTank.drain(used, T);
        updateInventory();
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        return false;
    }

    @Override
    public IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {
        if (!FL.gas(aFluidToFill)) return null;
        if (mTank.isEmpty() || mTank.contains(aFluidToFill)) return mTank;
        return null;
    }
    public IFluidTank[] getFluidTanks2(byte aSide) {
        return mTank.AS_ARRAY;
    }
    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        aSize = Math.abs(aSize);
        if (aSize > getEnergySizeInputMax(aEnergyType, aSide)) {
            if (aDoInject) overcharge(aSize, aEnergyType);
            return aAmount;
        }
        if (aEnergyType == mEnergyTypeAccepted) {
            long tInput = Math.min(mInputMax, aSize * aAmount), tConsumed = Math.min(aAmount, (tInput/aSize) + (tInput%aSize!=0?1:0));
            if (aDoInject) doOutputFluids((int) (tConsumed * aSize * 10));
            return tConsumed;
        }
        return 0;
    }

    @Override public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return aShouldSideBeRendered[aSide] ? BlockTextureMulti.get(BlockTextureDefault.get(mTexturesMaterial[FACING_ROTATIONS[mFacing][aSide]], mRGBa), BlockTextureDefault.get(mTexturesInactive[FACING_ROTATIONS[mFacing][aSide]])) : null;
    }

    @Override public boolean isEnergyType                   (TagData aEnergyType, byte aSide, boolean aEmitting) {return  aEnergyType == mEnergyTypeAccepted;}
    @Override public long getEnergySizeInputMin             (TagData aEnergyType, byte aSide) {return mInputMin;}
    @Override public long getEnergySizeInputRecommended     (TagData aEnergyType, byte aSide) {return mInput;}
    @Override public long getEnergySizeInputMax             (TagData aEnergyType, byte aSide) {return mInputMax;}

    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return mEnergyTypeAccepted.AS_LIST;}

    @Override public String getTileEntityName() {return "ktfru.multitileentity.machine.compressedgasinputer";}

    @Override
    public boolean canDrop(int aSlot) {
        return false;
    }
}
