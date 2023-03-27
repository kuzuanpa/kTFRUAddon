/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */


package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.specialPart;

import gregapi.GT_API;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.CS;
import gregapi.data.LH;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase07Paintable;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.util.UT;
import gregapi.util.WD;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static gregapi.data.CS.*;
import static gregapi.data.CS.F;

public class MultiBlockPartComputeCluster extends TileEntityBase07Paintable {
    public ChunkCoordinates mTargetPos = null;
    public ITileEntityMultiBlockController mTarget = null;

    public long totalComputePower;
    public long getComputePower(){return totalComputePower;}
    protected IIconContainer[][] mTextures;
    public short mDesign;
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN     + LH.get(LH.RECIPES_MIXINGBOWL_USAGE));
        aList.add(LH.Chat.ORANGE   + LH.get(LH.NO_GUI_CLICK_TO_INTERACT)   + " (" + LH.get(LH.FACE_TOP) + ")");
        aList.add(LH.Chat.DGRAY    + LH.get(LH.TOOL_TO_DETAIL_MAGNIFYINGGLASS));
    }
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (aTool.equals(TOOL_magnifyingglass)) {
    if (aChatReturn != null) {
        boolean tmp = T;
        for (int i=0;i < 3;i++) if (!(slot(i).stackSize ==0)) {
            tmp = F;
            aChatReturn.add("Slot"+i+"has" + slot(i).getDisplayName());
        }
        if (tmp) aChatReturn.add("Contains no Compute Node");
    }
    return 0;
}
    return 0;}

    // Inventory Stuff
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[4];}
    @Override public boolean canDrop(int aInventorySlot) {return T;}

    private static final int[] ACCESSIBLE_SLOTS = new int[] {0, 1, 2, 3};

    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}


    @Override
    public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {
        if (aSlot >= 4) return F;
        for (int i = 0; i < 4; i++) if (slot(i)== null&&aStack.getItem().getUnlocalizedName().contains("ktfru.item.it.computer")) return i == aSlot;
        return F;
    }

    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {return aSlot >= 3;}
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey("gt.target")) {
            this.mTargetPos = new ChunkCoordinates(UT.Code.bindInt(aNBT.getLong("gt.target.x")), UT.Code.bindInt(aNBT.getLong("gt.target.y")), UT.Code.bindInt(aNBT.getLong("gt.target.z")));
        }
        if (aNBT.hasKey(NBT_DESIGN)) mDesign = UT.Code.unsignB(aNBT.getByte(NBT_DESIGN));

        if (CODE_CLIENT) {
            if (GT_API.sBlockIcons == null && aNBT.hasKey(NBT_TEXTURE)) {
                String tTextureName = aNBT.getString(NBT_TEXTURE);
                mTextures = new IIconContainer[UT.Code.bind8(aNBT.getShort(NBT_DESIGNS))+1][6];
                for (short i = 0; i < mTextures.length; i++) {mTextures[i] = new IIconContainer[] {
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+i+"/colored/bottom"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+i+"/colored/top"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+i+"/colored/side"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+i+"/overlay/bottom"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+i+"/overlay/top"),
                        new Textures.BlockIcons.CustomIcon("machines/multiblockparts/"+tTextureName+"/"+i+"/overlay/side")
                };}
            } else {
                TileEntity tCanonicalTileEntity = MultiTileEntityRegistry.getCanonicalTileEntity(getMultiTileEntityRegistryID(), getMultiTileEntityID());
                if (tCanonicalTileEntity instanceof MultiBlockPartComputeCluster) {
                    mTextures = ((MultiBlockPartComputeCluster)tCanonicalTileEntity).mTextures;
                }
            }
        }
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        if (mDesign != 0) aNBT.setByte(NBT_DESIGN, (byte)mDesign);
        if (this.mTargetPos != null) {
            UT.NBT.setBoolean(aNBT, "gt.target", true);
            UT.NBT.setNumber(aNBT, "gt.target.x", (long)this.mTargetPos.posX);
            UT.NBT.setNumber(aNBT, "gt.target.y", (long)this.mTargetPos.posY);
            UT.NBT.setNumber(aNBT, "gt.target.z", (long)this.mTargetPos.posZ);
        }
    }
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean @NotNull [] aShouldSideBeRendered) {
        return aShouldSideBeRendered[aSide] ? BlockTextureMulti.get(BlockTextureDefault.get(this.mTextures[this.mDesign][CS.FACES_TBS[aSide]], this.mRGBa), BlockTextureDefault.get(this.mTextures[this.mDesign][CS.FACES_TBS[aSide] + 3])) : null;
    }

    //Every thing from MultiBlockPart
    public ITileEntityMultiBlockController getTarget(boolean aCheckValidity) {
        if (this.mTargetPos == null) {
            return null;
        } else {
            if (this.mTarget == null || this.mTarget.isDead()) {
                this.mTarget = null;
                if (this.worldObj.blockExists(this.mTargetPos.posX, this.mTargetPos.posY, this.mTargetPos.posZ)) {
                    TileEntity tTarget = WD.te(this.worldObj, this.mTargetPos, true);
                    if (tTarget instanceof ITileEntityMultiBlockController && ((ITileEntityMultiBlockController)tTarget).isInsideStructure(this.xCoord, this.yCoord, this.zCoord)) {
                        this.mTarget = (ITileEntityMultiBlockController)tTarget;
                    } else {
                        this.mTargetPos = null;
                    }
                }
            }

            return aCheckValidity && this.mTarget != null && !this.mTarget.checkStructure(false) ? null : this.mTarget;
        }
    }

    public void setTarget(ITileEntityMultiBlockController aTarget, int aDesign, int aMode) {
        this.mTarget = aTarget;
        this.mTargetPos = this.mTarget == null ? null : this.mTarget.getCoords();
    }
    public boolean breakBlock() {
        ITileEntityMultiBlockController tTarget = this.getTarget(false);
        if (tTarget != null) {
            this.mTargetPos = null;
            this.mTarget = null;
            tTarget.onStructureChange();
        }
        return false;
    }
    @Override
    public String getTileEntityName() {
        return "ktfru.computenode";
    }
}
