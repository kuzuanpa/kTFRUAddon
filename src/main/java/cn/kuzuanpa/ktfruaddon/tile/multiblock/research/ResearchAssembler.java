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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.research;

import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.IResearchDatabase;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import gregapi.cover.ICover;
import gregapi.data.CS;
import gregapi.data.LH;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.IMultiBlockInventory;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.WD;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.Collections;
import java.util.List;

import static gregapi.data.CS.SIDE_BOTTOM;
import static gregapi.data.CS.T;

public class ResearchAssembler extends MultiResearchBasicMachine implements IMultiBlockInventory {
    ChunkCoordinates lastFailedPos = null;
    static IStringBaseStructure structure = new LayerStructure(StructureContext.Axis.Y).layerRule("AB")
            .fixedLayer('A',
                    "WW",
                    " A",
                    "WW"
            ).fixedLayer('B',
                    "WW",
                    "BC",
                    "WW"
            )
            .where('A', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31500, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN)))
            .where('B', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31010, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN)))
            .where('C', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31020, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID)))
            .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31000, MultiTileEntityMultiBlockPart.ONLY_IN)))
            .setOffset(-1, 0, 0);

    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = structure.checkStructure(new StructureContext(this, (aPlayer != null || aInventory != null) ? StructureContext.StringBaseMode.SET : StructureContext.StringBaseMode.CHECK, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        return lastFailedPos == null;
    }

    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide()) return true;

        if (!mStructureOkay)
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED + LH.get(I18nHandler.STRUCTURE_ERR)));

        ItemStack equippedItem = aPlayer.getCurrentEquippedItem();
        if (equippedItem != null && equippedItem.getItem() instanceof ItemProjector) {
            structure.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }
        return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
    }

    public final short sizeX = 3, sizeY = 2, sizeZ = 2;
    public final short xMapOffset = -1;

    static {
        LH.add("ktfru.tooltip.multiblock.maskaligner.0.5", "Input LU from upside of Light Module, Input EU from anyside of Energy Module.");
        LH.add("ktfru.tooltip.multiblock.maskaligner.0.6", "Fluid inputs from anyblock in upside, Item input from upside of IO manager, output from backside.");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.maskaligner.0.5"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.maskaligner.0.6"));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return new BoundingBox(utils.getRealX(mFacing, xCoord, xMapOffset, 0), yCoord, utils.getRealZ(mFacing, zCoord, xMapOffset, 0), utils.getRealX(mFacing, utils.getRealX(mFacing, xCoord, xMapOffset, 0), sizeX, sizeZ), yCoord + sizeY, utils.getRealZ(mFacing, utils.getRealZ(mFacing, zCoord, xMapOffset, 0), sizeX, sizeZ)).isXYZInBox(aX, aY, aZ);
    }

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte aSide, Fluid aOutput) {
        return null;
    }

    @Override
    public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) {
        DelegatorTileEntity<TileEntity> te = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 2), this.yCoord + 1, this.getOffsetZN(this.mFacing, 2), this.mFacing, false);
        if (te == null || te.mTileEntity == null) return this.delegator(SIDE_BOTTOM);
        return new DelegatorTileEntity<>(te.mTileEntity, SIDE_BOTTOM);
    }

    @Override
    public DelegatorTileEntity<IInventory> getItemInputTarget(byte aSide) {
        TileEntity te = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 1), this.yCoord + 2, this.getOffsetZN(this.mFacing, 1), false);
        if (!(te instanceof IInventory)) return new DelegatorTileEntity<>(this, SIDE_BOTTOM);
        return new DelegatorTileEntity<>((IInventory) te, SIDE_BOTTOM);
    }

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte aSide) {
        return null;
    }

    public static IIconContainer
            sTextureSingle = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/single/sides"),
            sOverlaySingleFront = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/single/front"),
            sOverlayFront = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/overlay/front"),
            sOverlayFrontActive = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/overlay/front_active"),
            sOverlayFrontRunningGlow = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/overlay/front_running_glow"),
            sOverlayFrontActiveGlow = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/overlay/front_active_glow");

    @Override
    public boolean breakBlock() {
        setStateOnOff(T);
        CS.GarbageGT.trash(mTanksInput);
        CS.GarbageGT.trash(mTanksOutput);
        CS.GarbageGT.trash(mOutputItems);
        CS.GarbageGT.trash(mOutputFluids);
        return super.breakBlock();
    }

    @Override
    public boolean allowCover(byte aSide, ICover aCover) {
        return false;
    }

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (mStructureOkay) {
            switch (aRenderPass) {
                case 0:
                    return BlockTextureDefault.get(sTextureSingle, mRGBa);
                case 1:
                    return aSide == mFacing ? BlockTextureMulti.get(BlockTextureDefault.get(mActive ? sOverlayFrontActive : sOverlayFront), BlockTextureDefault.get(mActive ? sOverlayFrontActiveGlow : mRunning ? sOverlayFrontRunningGlow : null, true)) : null;

            }
        }
        return aShouldSideBeRendered[aSide] ? aSide == mFacing ? BlockTextureMulti.get(BlockTextureDefault.get(sTextureSingle, mRGBa), BlockTextureDefault.get(sOverlaySingleFront)) : BlockTextureMulti.get(BlockTextureDefault.get(sTextureSingle, mRGBa)) : null;
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.research.assembler";
    }

    @Override
    public List<IResearchDatabase> getDatabases() {
        TileEntity tile = WD.te(worldObj, new ChunkCoordinates(utils.getRealX(mFacing, xCoord, 0, 3), yCoord, utils.getRealZ(mFacing, zCoord, 0, 3)),false);
        System.out.println(tile);
        if(tile instanceof IResearchDatabase)return Collections.singletonList(((IResearchDatabase) tile));
        return Collections.emptyList();
    }
}
