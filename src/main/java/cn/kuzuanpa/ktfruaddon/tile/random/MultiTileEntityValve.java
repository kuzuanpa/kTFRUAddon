/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

package cn.kuzuanpa.ktfruaddon.tile.random;

import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.block.multitileentity.IWailaTile;
import gregapi.data.FL;
import gregapi.data.IL;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.fluid.FluidTankGT;
import gregapi.fluid.FluidTankGTRateLimitedPowerConducting;
import gregapi.item.IItemRottable;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.ITileEntityFunnelAccessible;
import gregapi.tileentity.ITileEntityTapAccessible;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.util.UT;
import gregapi.util.WD;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

import java.util.List;

import static gregapi.data.CS.*;

public class MultiTileEntityValve extends TileEntityBase09FacingSingle implements IMultiTileEntity.IMTE_AddToolTips, IMultiTileEntity.IMTE_GetMaxStackSize, ITileEntityFunnelAccessible, ITileEntityTapAccessible, IFluidHandler, IFluidContainerItem, IItemRottable, IWailaTile {
    public FluidTankGT mTank = new FluidTankGTRateLimitedPowerConducting(16000);
    public long mMeltingPoint = Long.MAX_VALUE;
    public boolean mGasProof = F, mAcidProof = F, mPlasmaProof = F, mMagicProof = F;
    public int throttle, throttleMax = 16;

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.random.valve";
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_GASPROOF)) mGasProof = aNBT.getBoolean(NBT_GASPROOF);
        if (aNBT.hasKey(NBT_ACIDPROOF)) mAcidProof = aNBT.getBoolean(NBT_ACIDPROOF);
        if (aNBT.hasKey(NBT_MAGICPROOF)) mMagicProof = aNBT.getBoolean(NBT_MAGICPROOF);
        if (aNBT.hasKey(NBT_PLASMAPROOF)) mPlasmaProof = aNBT.getBoolean(NBT_PLASMAPROOF);
        if (aNBT.hasKey(NBT_CAPACITY_HU)) mMeltingPoint = aNBT.getLong(NBT_CAPACITY_HU); else mMeltingPoint = (long)(mMaterial.mMeltingPoint * 1.25);
        if (aNBT.hasKey("ktfru.throttle")) throttle = aNBT.getInteger("ktfru.throttle");
        if (aNBT.hasKey("ktfru.throttle.max")) throttleMax = aNBT.getInteger("ktfru.throttle.max");

        mTank.setCapacity(aNBT.getLong(NBT_TANK_CAPACITY)).readFromNBT(aNBT, NBT_TANK);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        mTank.writeToNBT(aNBT, NBT_TANK);
        aNBT.setInteger("ktfru.throttle", throttle);
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + mTank.contentcap());
        aList.add(LH.Chat.ORANGE   + LH.get(LH.NO_GUI_FUNNEL_TAP_TO_TANK));
        aList.add(LH.Chat.ORANGE   + LH.get(LH.POWER_CONDUCTING_FLUIDS_SLOW));
        if (mGasProof   ) aList.add(LH.Chat.ORANGE + LH.get(LH.TOOLTIP_GASPROOF));
        if (mAcidProof  ) aList.add(LH.Chat.ORANGE + LH.get(LH.TOOLTIP_ACIDPROOF));
        if (mPlasmaProof) aList.add(LH.Chat.ORANGE + LH.get(LH.TOOLTIP_PLASMAPROOF));
        if (mMagicProof ) aList.add(LH.Chat.ORANGE + LH.get(LH.TOOLTIP_MAGICPROOF));
        aList.add(LH.Chat.DRED     + LH.get(LH.HAZARD_MELTDOWN) + " (" + mMeltingPoint + " K)");
        aList.add(LH.Chat.DGRAY    + LH.get(LH.TOOL_TO_TOGGLE_AUTO_OUTPUTS_MONKEY_WRENCH));
        aList.add(LH.Chat.DGRAY    + LH.get(LH.TOOL_TO_TOGGLE_SOFT_HAMMER));
        aList.add(LH.Chat.DGRAY    + LH.get(LH.TOOL_TO_DETAIL_MAGNIFYINGGLASS));
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        long rReturn = super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
        if (rReturn > 0) return rReturn;
        if (isClientSide()) return 0;
        if (aTool.equals(TOOL_plunger)) {
            return GarbageGT.trash(mTank, 1000);
        }
        if(aTool.equals(TOOL_monkeywrench)) {
            if(aSneaking)throttle --;
            else throttle ++;
            if(throttle < 0)throttle = 0;
            if(throttle > throttleMax)throttle = throttleMax;
            aChatReturn.add(LH.get(LH.PIPE_STATS_BANDWIDTH) + (long)(Math.ceil(mTank.capacity() / 2F) * throttle / throttleMax));
        }
        if (aTool.equals(TOOL_thermometer)) {if (aChatReturn != null) aChatReturn.add("Temperature: " + FL.temperature(mTank) + "K"); return 10000;}
        if (aTool.equals(TOOL_magnifyingglass)) {
            if (aChatReturn != null) {
                aChatReturn.add(mTank.contentcap());
            }
            return 1;
        }
        return 0;
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (aIsServerSide) {
            FluidStack tFluid = mTank.getFluid();
            if (tFluid != null && tFluid.amount > 0) {
                if (FL.temperature(tFluid) >= mMeltingPoint && meltdown()) return;

                if (!mMagicProof && FL.magic(tFluid)) {
                    UT.Sounds.send(SFX.MC_FIZZ, this, F);
                    GarbageGT.trash(mTank);
                    WD.set(worldObj, xCoord, yCoord, zCoord, FL.gas(tFluid) ? IL.TC_Flux_Gas.block() : IL.TC_Flux_Goo.block(), IL.TC_Flux_Goo.exists() ? 7 : 0, 3);
                    return;
                }
                if (!mAcidProof && FL.acid(tFluid)) {
                    UT.Sounds.send(SFX.MC_FIZZ, this, F);
                    GarbageGT.trash(mTank);
                    setToAir();
                    return;
                }
                if (!mPlasmaProof && FL.plasma(tFluid)) {
                    UT.Sounds.send(SFX.MC_FIZZ, this, F);
                    GarbageGT.trash(mTank);
                } else
                if (!mGasProof && FL.gas(tFluid)) {
                    UT.Sounds.send(SFX.MC_FIZZ, this, F);
                    GarbageGT.trash(mTank);
                } else
                if (!allowFluid(tFluid)) {
                    UT.Sounds.send(SFX.MC_FIZZ, this, F);
                    GarbageGT.trash(mTank);
                }

                if(throttle>0)FL.move(mTank, getAdjacentTank(mFacing),(long)(Math.ceil(mTank.amount() / 2F) * throttle / throttleMax));
            }
        }
    }

    public boolean meltdown() {
        if (FL.lava(mTank) && mTank.has(1000)) {
            mTank.remove(1000);
            GarbageGT.trash(mTank);
            worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.flowing_lava, 0, 3);
        } else {
            GarbageGT.trash(mTank);
            setToFire();
        }
        WD.burn(worldObj, getCoords(), F, F);
        return T;
    }

    public boolean allowFluid(FluidStack aFluid) {
        return FL.temperature(aFluid) < mMeltingPoint;
    }

    @Override
    public FluidStack getFluid(ItemStack aStack) {
        return mTank.getFluid();
    }

    @Override
    public int getCapacity(ItemStack aStack) {
        return mTank.getCapacity();
    }

    @Override
    public int fill(ItemStack aStack, FluidStack aFluid, boolean aDoFill) {
        if (!allowFluid(aFluid)) return 0;
        if (!mGasProof && FL.gas(aFluid)) return 0;
        if (!mAcidProof && FL.acid(aFluid)) return 0;
        if (!mMagicProof && FL.magic(aFluid)) return 0;
        if (!mPlasmaProof && FL.plasma(aFluid)) return 0;
        int tFilled = mTank.fill(aFluid, aDoFill);
        if (tFilled > 0 && aDoFill) UT.NBT.set(aStack, writeItemNBT(aStack.hasTagCompound() ? aStack.getTagCompound() : UT.NBT.make()));
        return tFilled;
    }

    @Override
    public FluidStack drain(ItemStack aStack, int aMaxDrain, boolean aDoDrain) {
        FluidStack tDrained = mTank.drain(aMaxDrain, aDoDrain);
        if (tDrained != NF && aDoDrain) UT.NBT.set(aStack, writeItemNBT(aStack.hasTagCompound() ? aStack.getTagCompound() : UT.NBT.make()));
        return tDrained;
    }

    @Override
    public int funnelFill(byte aSide, FluidStack aFluid, boolean aDoFill) {
        return mTank.fill(aFluid, aDoFill);
    }

    @Override
    public FluidStack tapDrain(byte aSide, int aMaxDrain, boolean aDoDrain) {
        return mTank.drain(aMaxDrain, aDoDrain);
    }

    @Override public boolean canDrop(int aSlot) {return F;}

    @Override public byte getMaxStackSize(ItemStack aStack, byte aDefault) {return mTank.has() ? 1 : aDefault;}

    @Override protected IFluidTank getFluidTankFillable2 (byte aSide, FluidStack aFluidToFill ) {return aSide == OPOS[mFacing]? mTank : null;}
    @Override protected IFluidTank getFluidTankDrainable2(byte aSide, FluidStack aFluidToDrain) {return aSide == mFacing? mTank : null;}
    @Override protected IFluidTank[] getFluidTanks2(byte aSide) {return mTank.AS_ARRAY;}
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return aShouldSideBeRendered[aSide] ? BlockTextureMulti.get(BlockTextureDefault.get(sColoreds[mFacing == aSide? 1 : OPOS[mFacing] == aSide? 0 : 2], mRGBa, mMaterial.contains(TD.Properties.GLOWING)), BlockTextureDefault.get(sOverlays[mFacing == aSide? 1 : OPOS[mFacing] == aSide? 0 : 2])) : null;
    }

    public static IIconContainer sColoreds[] = new IIconContainer[] {
            new Textures.BlockIcons.CustomIcon("machines/valve/colored/back"),
            new Textures.BlockIcons.CustomIcon("machines/valve/colored/front"),
            new Textures.BlockIcons.CustomIcon("machines/valve/colored/side"),
    }, sOverlays[] = new IIconContainer[] {
            new Textures.BlockIcons.CustomIcon("machines/valve/overlay/back"),
            new Textures.BlockIcons.CustomIcon("machines/valve/overlay/front"),
            new Textures.BlockIcons.CustomIcon("machines/valve/overlay/side"),
    };

    @Override public ItemStack getRotten(ItemStack aStack) {return mMaterial.contains(TD.Properties.BETWEENLANDS) ? aStack : IItemRottable.RottingUtil.rotting(aStack, (IFluidContainerItem)aStack.getItem());}
    @Override public ItemStack getRotten(ItemStack aStack, World aWorld, int aX, int aY, int aZ) {return mMaterial.contains(TD.Properties.BETWEENLANDS) ? aStack : IItemRottable.RottingUtil.rotting(aStack, (IFluidContainerItem)aStack.getItem());}
    @Override
    public NBTTagCompound getWailaNBT(TileEntity te, NBTTagCompound aNBT) {
        IWailaTile.super.getWailaNBT(te,aNBT);

        mTank.writeToNBT(aNBT, NBT_TANK);
        return aNBT;
    }

    @Override
    public List<String> getWailaBody(List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        IWailaTile.super.getWailaBody(currentTip, accessor, config);

        NBTTagCompound aNBT = accessor.getNBTData();

        mTank.readFromNBT(aNBT, NBT_TANK);

        IWailaTile.addTankDesc(currentTip,LH.get(LH.CONTENT)+" ",mTank,"");
        return currentTip;
    }
}
