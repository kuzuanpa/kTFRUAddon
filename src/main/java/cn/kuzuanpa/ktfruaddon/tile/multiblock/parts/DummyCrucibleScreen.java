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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.parts;

import cn.kuzuanpa.ktfruaddon.api.tile.crucible.IDummyCrucibleMaterialProvider;
import cn.kuzuanpa.ktfruaddon.api.tile.part.IMultiBlockPart;
import gregapi.GT_API;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.OP;
import gregapi.data.TD;
import gregapi.old.Textures;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictPrefix;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

import static gregapi.data.CS.*;

public class DummyCrucibleScreen extends TileEntityBase09FacingSingle implements IMultiBlockPart {
    IDummyCrucibleMaterialProvider.CrucibleOreDictMaterialStack mContent = null;
    OreDictPrefix createTo = OP.ingot;
    float mTemp = C;

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(aSide != mFacing)return false;
        if(aHitY < 0.1F){

            return true;
        }
        ITileEntityMultiBlockController controller = getTarget(true);
        if(controller instanceof IDummyCrucibleMaterialProvider){
            IDummyCrucibleMaterialProvider provider = (IDummyCrucibleMaterialProvider) controller;
            mContent = provider.extractMaterial(createTo.mAmount, null);
            mTemp = provider.getTemperature();
        }
        return true;
    }

    public OreDictPrefix getTargetPrefix(OreDictMaterial aMaterial){
        OreDictPrefix prefix = createTo;
        if (aMaterial.mTargetSolidifying.mMaterial.contains(TD.Processing.COOL2CRYSTAL)) {
            if (createTo == OP.plate    ) prefix = OP.plateGem;
            if (createTo == OP.plateTiny) prefix = OP.plateGemTiny;
        }
        return prefix;
    }
    public void solidifyContent(){
        if (mContent == null || mContent.stack == null || mContent.stack.mMaterial == null || mContent.stack.mMaterial.mMeltingPoint < mTemp) return;
        OreDictPrefix tPrefix = getTargetPrefix(mContent.stack.mMaterial);
        if (tPrefix != null && mContent != null && slot(0) == null && mContent.isEnough) {
            setInventorySlotContents(0, tPrefix.mat(mContent.stack.mMaterial.mTargetSolidifying.mMaterial, 1));
            mContent = null;
        }
    }
    public IIconContainer sTextureCommon, sOverlayFront;

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);

        if (aNBT.hasKey(NBT_TARGET)) mTargetPos = IMultiBlockPart.readTargetPosFromNBT(aNBT);
        if (aNBT.hasKey(NBT_DESIGN)) mDesign = UT.Code.unsignB(aNBT.getByte(NBT_DESIGN));

        if (CODE_CLIENT) {
            if (GT_API.sBlockIcons == null && aNBT.hasKey(NBT_TEXTURE)) {
                String tTextureName = aNBT.getString(NBT_TEXTURE);
                sTextureCommon= new Textures.BlockIcons.CustomIcon("machines/multiblockparts/transformer/"+tTextureName+"/common");
                sOverlayFront = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/transformer/"+tTextureName+"/front");


            } else {
                TileEntity tCanonicalTileEntity = MultiTileEntityRegistry.getCanonicalTileEntity(getMultiTileEntityRegistryID(), getMultiTileEntityID());
                if (tCanonicalTileEntity instanceof TransformerPart) {
                    sTextureCommon = ((TransformerPart) tCanonicalTileEntity).sTextureCommon;
                    sOverlayFront = ((TransformerPart) tCanonicalTileEntity).sOverlayFront;

                } else {
                    sTextureCommon = sOverlayFront = L6_IICONCONTAINER[0];
                }
            }
        }
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (aIsServerSide) {
            mTemp --;
            solidifyContent();
        }
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        IMultiBlockPart.writeToNBT(aNBT,mTargetPos,mDesign);
    }

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return BlockTextureMulti.get(BlockTextureDefault.get(sTextureCommon,mRGBa), aSide==mFacing? BlockTextureDefault.get(sOverlayFront) : null);
    }

    public ChunkCoordinates mTargetPos = null;
    public ITileEntityMultiBlockController mTarget = null;
    public int mDesign = 0;
    @Override public ITileEntityMultiBlockController getTarget2() {return mTarget;}
    @Override public void setTarget(ITileEntityMultiBlockController target) {mTarget = target;}
    @Override public ChunkCoordinates getTargetPos() {return mTargetPos;}
    @Override public void setTargetPos(ChunkCoordinates aCoords){mTargetPos=aCoords;}
    @Override public void setDesign(int aDesign) {this.mDesign = aDesign;}
    @Override public int getDesign(){return mDesign;}
    @Override
    public boolean breakBlock() {
        notifyTarget();
        return super.breakBlock();
    }
    @Override
    public int getLightOpacity(){
        return mDesign==1?255:0;
    }
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.part.dummy_crucible.common";
    }
    //Inventory
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[1];}
    @Override public boolean canDrop(int aInventorySlot) {return T;}

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    private static final int[] ACCESSIBLE_SLOTS = new int[] {0};

    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}

    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {
        return true;
    }

}
