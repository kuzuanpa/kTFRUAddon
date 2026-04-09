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

package cn.kuzuanpa.ktfruaddon.tile.machine;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import gregapi.data.FL;
import gregapi.data.LH;
import gregapi.fluid.FluidTankGT;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.old.Textures;
import gregapi.recipes.Recipe;
import gregapi.render.*;
import gregapi.util.UT;
import gregapi.util.WD;
import gregtech.tileentity.energy.reactors.MultiTileEntityReactorCore;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.util.List;

import static gregapi.data.CS.*;

public class NuclearAttachmentNeutronLiquidBreeder extends MultiTileEntityReactorCore {
    public long mNeutrons = 0;
    public boolean mForcedStopped = false;
    public FluidTankGT mTankOutput = new FluidTankGT(64000);
    public Recipe.RecipeMap mRecipes = recipeMaps.NeutronAbsorption;
    public Recipe mLastRecipe;
    public IWailaInfoProvider tankInfoI = new InfoTank(LH.get(I18nHandler.INPUT), "", mTanks[0]);
    public IWailaInfoProvider tankInfoO = new InfoTank(LH.get(I18nHandler.OUTPUT), "", mTanks[1], mTankOutput);

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        aNBT.setLong("neutrons", mNeutrons);
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        mNeutrons = aNBT.getLong("neutrons");
    }

    @Override
    protected IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {
        return mTanks[0];
    }

    public int funnelFill(byte aSide, FluidStack aFluid, boolean aDoFill) {
        return this.mTanks[0].fill(aFluid, aDoFill);
    }
    @Override
    public void onServerTickPost(boolean b) {
        if (mTankOutput.has()) FL.move(mTankOutput, WD.te(worldObj,xCoord,yCoord-1,zCoord,mFacing,false));

        checkAndDoRecipe();

        mNeutrons = 0;
    }
    public void checkAndDoRecipe(){
        if(mForcedStopped || mTanks[0].isEmpty() || mTanks[1].isFull() || mTankOutput.isFull() || mNeutrons == 0)return;
        Recipe tRecipe = mRecipes.findRecipe(this, mLastRecipe, F, Integer.MAX_VALUE, NI, mTanks[0].AS_ARRAY, ZL_IS);
        if (tRecipe == null) return;
        mLastRecipe = tRecipe;

        if (tRecipe.mEUt < 0 || tRecipe.mDuration < 0) return;

        if (!tRecipe.isRecipeInputEqual(F, F, mTanks[0].AS_ARRAY)) return;

        mLastRecipe = tRecipe;
        long parallel = Math.min(Math.min(Math.min(mNeutrons / (tRecipe.mEUt * tRecipe.mDuration), mTanks[0].amount() / tRecipe.mFluidInputs[0].amount ), (mTanks[1].capacity() - mTanks[1].amount()) / tRecipe.mFluidOutputs[0].amount), (mTankOutput.capacity() - mTankOutput.amount()) / tRecipe.mFluidOutputs[1].amount);
        if(parallel <= 0)return;

        mTanks[0].remove(tRecipe.mFluidInputs[0].amount * parallel);
        mTanks[1].add(tRecipe.mFluidOutputs[0].amount * parallel, tRecipe.mFluidOutputs[0]);
        mTankOutput.add(tRecipe.mFluidOutputs[1].amount * parallel, tRecipe.mFluidOutputs[1]);
    }

    @Override
    public int getReactorRodNeutronReflection(int aSlot, int aNeutrons, boolean aModerated) {
        if(aModerated)return 0;
        mNeutrons += aNeutrons;
        return 0;
    }

    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        if (aSendAll) return getClientDataPacketByteArray(aSendAll, (byte) UT.Code.getR(mRGBa), (byte)UT.Code.getG(mRGBa), (byte)UT.Code.getB(mRGBa), getVisualData(), getDirectionData(), UT.Code.toByteS(FL.id_(mTanks[0]), 0), UT.Code.toByteS(FL.id_(mTanks[0]), 1)
                 );
        return getClientDataPacketByte(aSendAll, getVisualData());
    }

    @Override
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler) {
        super.receiveDataByteArray(aData, aNetworkHandler);
        int i = 5;
        if (aData.length <= i) return T;
        mTanks[0].setFluid(FL.make(UT.Code.combine(aData[i++], aData[i++]), mTanks[0].getCapacity()));
         return T;
    }

    public ITexture mTextures[] = new ITexture[15];

    @Override
    public int getRenderPasses2(Block aBlock, boolean[] aShouldSideBeRendered) {
        mTextures[ 0] = BlockTextureMulti.get(BlockTextureDefault.get(sColoreds[0], mRGBa), BlockTextureDefault.get(sOverlays[0]));
        mTextures[ 1] = BlockTextureMulti.get(BlockTextureDefault.get(sColoreds[1], mRGBa), BlockTextureDefault.get(sOverlays[1]));
        mTextures[ 2] = BlockTextureMulti.get(BlockTextureDefault.get(sColoreds[2], mRGBa), BlockTextureDefault.get(sOverlays[2]));
        mTextures[ 3] = BlockTextureMulti.get(BlockTextureDefault.get(sColoreds[3], mRGBa), BlockTextureDefault.get(sOverlays[3]));
        mTextures[ 4] = BlockTextureMulti.get(BlockTextureDefault.get(sColoreds[4], mRGBa), BlockTextureDefault.get(sOverlays[4]));
        mTextures[ 5] = BlockTextureMulti.get(BlockTextureDefault.get(sColoreds[5], mRGBa), BlockTextureDefault.get(sOverlays[5]));
        mTextures[6] = (mTanks[0].has() ? BlockTextureFluid.get(mTanks[0]) : null);

        return 7;
    }

    @Override
    public boolean usesRenderPass2(int aRenderPass, boolean[] aShouldSideBeRendered) {
        return aRenderPass < 6 || (aRenderPass == 6 && mTanks[0].has());
    }

    @Override
    public boolean setBlockBounds2(Block aBlock, int aRenderPass, boolean[] aShouldSideBeRendered) {
        switch (aRenderPass) {
            case SIDE_X_NEG: return box(aBlock, PX_P[ 0], PX_P[ 0], PX_P[ 0], PX_N[14], PX_N[ 0], PX_N[ 0]);
            case SIDE_Y_NEG: return box(aBlock, PX_P[ 0], PX_P[ 0], PX_P[ 0], PX_N[ 0], PX_N[14], PX_N[ 0]);
            case SIDE_Z_NEG: return box(aBlock, PX_P[ 0], PX_P[ 0], PX_P[ 0], PX_N[ 0], PX_N[ 0], PX_N[14]);
            case SIDE_X_POS: return box(aBlock, PX_P[14], PX_P[ 0], PX_P[ 0], PX_N[ 0], PX_N[ 0], PX_N[ 0]);
            case SIDE_Y_POS: return box(aBlock, PX_P[ 0], PX_P[14], PX_P[ 0], PX_N[ 0], PX_N[ 0], PX_N[ 0]);
            case SIDE_Z_POS: return box(aBlock, PX_P[ 0], PX_P[ 0], PX_P[14], PX_N[ 0], PX_N[ 0], PX_N[ 0]);

            case 6: return box(aBlock, PX_P[ 2]+PX_OFFSET, PX_P[ 2], PX_P[ 2]+PX_OFFSET, PX_N[ 2]-PX_OFFSET, PX_N[ 2], PX_N[ 2]-PX_OFFSET);
        }
        return F;
    }

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return aRenderPass < 6 && !ALONG_AXIS[aRenderPass][aSide] ? null : aRenderPass == mFacing ? mTextures[4] : aRenderPass == mSecondFacing ? mTextures[5] : aRenderPass >= 6 || aRenderPass < 2 ? mTextures[SIDES_VERTICAL[aSide] && aRenderPass != 10 && aRenderPass > 1 ? aRenderPass+5 : aRenderPass] : mTextures[isCovered((byte)aRenderPass) ? 3 : 2];
    }

    public static IIconContainer sColoreds[] = new IIconContainer[] {
            new Textures.BlockIcons.CustomIcon("machines/generators/reactor_core_2x2/colored/bottom"),
            new Textures.BlockIcons.CustomIcon("machines/generators/reactor_core_2x2/colored/top"),
            new Textures.BlockIcons.CustomIcon("machines/generators/reactor_core_2x2/colored/side1"),
            new Textures.BlockIcons.CustomIcon("machines/generators/reactor_core_2x2/colored/side2"),
            new Textures.BlockIcons.CustomIcon("machines/generators/reactor_core_2x2/colored/face1"),
            new Textures.BlockIcons.CustomIcon("machines/generators/reactor_core_2x2/colored/face2")
    }, sOverlays[] = new IIconContainer[] {
            new Textures.BlockIcons.CustomIcon("machines/generators/reactor_core_2x2/overlay/bottom"),
            new Textures.BlockIcons.CustomIcon("machines/generators/reactor_core_2x2/overlay/top"),
            new Textures.BlockIcons.CustomIcon("machines/generators/reactor_core_2x2/overlay/side1"),
            new Textures.BlockIcons.CustomIcon("machines/generators/reactor_core_2x2/overlay/side2"),
            new Textures.BlockIcons.CustomIcon("machines/generators/reactor_core_2x2/overlay/face1"),
            new Textures.BlockIcons.CustomIcon("machines/generators/reactor_core_2x2/overlay/face2")
    };

    @Override
    public List<IWailaInfoProvider> getWailaInfos(List<IWailaInfoProvider> list) {
        list.add(tankInfoI);
        list.add(tankInfoO);
        return list;
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.reactor.attachment.neutron_liquid_breeder";
    }
}
