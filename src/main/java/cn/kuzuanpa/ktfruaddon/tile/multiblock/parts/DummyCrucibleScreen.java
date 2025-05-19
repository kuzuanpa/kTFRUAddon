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

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.crucible.IDummyCrucibleMaterialProvider;
import cn.kuzuanpa.ktfruaddon.api.tile.part.IMultiBlockPart;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.block.multitileentity.IWailaTile;
import gregapi.data.LH;
import gregapi.data.OP;
import gregapi.data.TD;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
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
import gregapi.util.WD;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

import java.util.List;

import static gregapi.data.CS.*;

public class DummyCrucibleScreen extends TileEntityBase09FacingSingle implements IMultiBlockPart, IMultiTileEntity.IMTE_SyncDataByteArray, IWailaTile {
    private IDummyCrucibleMaterialProvider.CrucibleOreDictMaterialStack mContent = null;
    private OreDictPrefix createTo = OP.ingot;
    public boolean clientMolten = false, clientMatChanged=false;
    public OreDictMaterial clientMat = null;
    float mTemp = C;

    public static IIconContainer
            sTextureCommon= new Textures.BlockIcons.CustomIcon("machines/multiblockparts/dummycruciblescreen/background"),
            sOverlayFront = new Textures.BlockIcons.CustomIcon("machines/multiblockparts/dummycruciblescreen/front");

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(aSide != mFacing)return false;
        if(aHitY < PX_P[1]){
            setContent(null);
            return true;
        }
        if(aPlayer.getCurrentEquippedItem() != null && updateCreateTo(aPlayer.getCurrentEquippedItem()))return true;

        ITileEntityMultiBlockController controller = getTarget(true);
        if(controller instanceof IDummyCrucibleMaterialProvider){
            IDummyCrucibleMaterialProvider provider = (IDummyCrucibleMaterialProvider) controller;
            setContent(provider.extractMaterial(getCreateTo().mAmount, null));
            if(this.mContent != null)mTemp = provider.getTemperature();
        }
        return true;
    }

    public OreDictPrefix getTargetPrefix(OreDictMaterial aMaterial){
        OreDictPrefix prefix = getCreateTo();
        if (aMaterial.mTargetSolidifying.mMaterial.contains(TD.Processing.COOL2CRYSTAL)) {
            if (getCreateTo() == OP.plate    ) prefix = OP.plateGem;
            if (getCreateTo() == OP.plateTiny) prefix = OP.plateGemTiny;
        }
        return prefix;
    }
    public void solidifyContent(){
        if (getContent() == null || getContent().stack == null || getContent().stack.mMaterial == null || getContent().stack.mMaterial.mMeltingPoint < mTemp) return;
        OreDictPrefix tPrefix = getTargetPrefix(getContent().stack.mMaterial);
        if (tPrefix != null && getContent() != null && slot(0) == null && getContent().isEnough) {
            setInventorySlotContents(0, tPrefix.mat(getContent().stack.mMaterial.mTargetSolidifying.mMaterial, 1));
            setContent(null);
        }
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);

        if (aNBT.hasKey(NBT_TARGET)) mTargetPos = IMultiBlockPart.readTargetPosFromNBT(aNBT);
        if (aNBT.hasKey(NBT_DESIGN)) mDesign = UT.Code.unsignB(aNBT.getByte(NBT_DESIGN));

    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (aIsServerSide) {
            mTemp = Math.max(WD.envTemp(worldObj, xCoord,yCoord,zCoord), mTemp - 8);
            solidifyContent();
        }
    }

    @Override
    public boolean onTickCheck(long aTimer) {
        boolean result = super.onTickCheck(aTimer) || clientMatChanged;
        clientMatChanged=false;
        return result;
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        IMultiBlockPart.writeToNBT(aNBT,mTargetPos,mDesign);
    }
//
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return BlockTextureMulti.get(aSide==mFacing? new BlockTextureDefault(sOverlayFront) : null);
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

    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        int prefixID = getCreateTo().mNameInternal.hashCode();
        short matID = (short) (getContent() == null? 0 : getContent().stack.mMaterial.mMeltingPoint < mTemp ? -getContent().stack.mMaterial.mID : getContent().stack.mMaterial.mID);
        return aSendAll ?
                getClientDataPacketByteArray(aSendAll,
                        getDirectionData(),
                        UT.Code.toByteS(matID, 0),
                        UT.Code.toByteS(matID, 1),
                        (byte)UT.Code.getR(mRGBa),
                        (byte)UT.Code.getG(mRGBa),
                        (byte)UT.Code.getB(mRGBa),
                        UT.Code.toByteI(prefixID, 0),
                        UT.Code.toByteI(prefixID, 1),
                        UT.Code.toByteI(prefixID, 2),
                        UT.Code.toByteI(prefixID, 3)
                )
                : getClientDataPacketByteArray(aSendAll, getDirectionData(),
                UT.Code.toByteS(matID, 0),
                UT.Code.toByteS(matID, 1));
    }

    public boolean updateCreateTo(ItemStack stack){
        for (OreDictPrefix value : OreDictPrefix.sPrefixes.values()) if(value.contains(stack))  {
            setCreateTo(value);
            updateClientData();
            return true;
        }
        return false;
    }
    @Override
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler) {
        setDirectionData(aData[0]);
        short matID = UT.Code.combine(aData[1],aData[2]);
        clientMolten = matID < 0;
        clientMat = OreDictMaterial.get(Math.abs(matID));
        if(aData.length == 10) {
            mRGBa = UT.Code.getRGBInt(new short[]{UT.Code.unsignB(aData[3]), UT.Code.unsignB(aData[4]), UT.Code.unsignB(aData[5])});
            int prefixID = UT.Code.combine(aData[6],aData[7],aData[8],aData[9]);
            receiveCreateTo(prefixID);
        }
        return T;
    }

    public void receiveCreateTo(int hashCode){
        setCreateTo(OreDictPrefix.sPrefixes.values().stream().filter(prefix-> prefix.mNameInternal.hashCode() == hashCode).findFirst().orElse(OP.ingot));
    }

    public OreDictPrefix getCreateTo() {
        return createTo;
    }

    public void setCreateTo(OreDictPrefix createTo) {
        this.createTo = createTo;
    }

    public IDummyCrucibleMaterialProvider.CrucibleOreDictMaterialStack getContent() {
        return mContent;
    }

    public void setContent(IDummyCrucibleMaterialProvider.CrucibleOreDictMaterialStack mContent) {
        clientMatChanged = true;
        this.mContent = mContent;
    }

    @Override
    public NBTTagCompound getWailaNBT(TileEntity te, NBTTagCompound aNBT) {
        IMultiBlockPart.super.getWailaNBT(te, aNBT);
        aNBT.setInteger("mTemp", (int)Math.floor(mTemp));
        return aNBT;
    }

    @Override
    public List<String> getWailaBody(List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        IMultiBlockPart.super.getWailaBody(currentTip, accessor, config);
        currentTip.add(LH.get(I18nHandler.TEMPERATURE+".part")+ " " + LH.Chat.WHITE + accessor.getNBTData().getInteger("mTemp"));
        return currentTip;
    }
}
