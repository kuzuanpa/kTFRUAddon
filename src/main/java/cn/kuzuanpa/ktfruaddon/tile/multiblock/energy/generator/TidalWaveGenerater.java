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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.generator;

import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.AirPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.LiquidPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.OpaqueCubePredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import gregapi.block.multitileentity.IWailaTile;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.machines.ITileEntityRunningActively;
import gregapi.tileentity.multiblocks.IMultiBlockEnergy;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.*;

public class TidalWaveGenerater extends TileEntityBase10MultiBlockBase implements ITileEntityEnergy, IMultiBlockEnergy, ITileEntityRunningActively, IWailaTile {
    private TagData mEnergyTypeEmitted=TD.Energy.RU;

    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (!aIsServerSide || !mStructureOkay) return;
        if (checkStructure(false) && getAdjacentTileEntity(SIDE_TOP) != null && getAdjacentTileEntity(SIDE_TOP).mTileEntity instanceof ITileEntityEnergy) {
            int mRate = (int) (Math.sin(aTimer/31.4f)*128) + 96;
            TileEntity tileToEmit = getAdjacentTileEntity(SIDE_TOP).mTileEntity;
            if (tileToEmit instanceof ITileEntityEnergy) ITileEntityEnergy.Util.insertEnergyInto(mEnergyTypeEmitted, SIDE_BOTTOM, mRate, 1, this, tileToEmit);
        }
    }

    @Override public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {return aEmitting && aEnergyType == mEnergyTypeEmitted;}
    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return mEnergyTypeEmitted.AS_LIST;}

    @Override public boolean getStateRunningPassively() {return checkStructure(false);}
    @Override public boolean getStateRunningActively() {return checkStructure(false);}
    @Override public boolean getStateRunningPossible() {return checkStructure(false);}

    //Structure
    ChunkCoordinates lastFailedPos=null;
    static IStringBaseStructure structure = new LayerStructure(StructureContext.Axis.Y).layerRule("ABCD")
            .fixedLayer('A',
                    "  LLLLLL",
                    "  LLLLLL",
                    "  LLLLLL",
                    "  LLLLLL",
                    "  LLLLLL"
            ).fixedLayer('B',
                    "OOLLLLLL",
                    "OWWBBBLL",
                    "OWWBBBLL",
                    "OWWBBBLL",
                    "OOLLLLLL"
            ).fixedLayer('C',
                    "OOLLLLLL",
                    "OWWWWBLL",
                    "OWAAABLL",
                    "OWWWWBLL",
                    "OOLLLLLL"
            ).fixedLayer('D',
                    "OOLLLLLL",
                    "OWWWWBLL",
                    "O AAABLL",
                    "OWWWWBLL",
                    "OOLLLLLL"
            )
            .where('A', new AirPredicate())
            .where('B', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31045)))
            .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18002)))
            .where('O', new OpaqueCubePredicate())
            .where('L', new LiquidPredicate())
            .setOffset(-2,-3,-1);
    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = structure.checkStructure(new StructureContext(this, (aPlayer != null || aInventory != null)? StructureContext.StringBaseMode.SET: StructureContext.StringBaseMode.CHECK, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        return lastFailedPos==null;
    }
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide())return true;

        if(!mStructureOkay){
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED+LH.get(I18nHandler.STRUCTURE_ERR)));
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.YELLOW+LH.get("ktfru.structure.complex.tip")));
        }

        ItemStack equippedItem=aPlayer.getCurrentEquippedItem();
        if (equippedItem!=null && equippedItem.getItem() instanceof ItemProjector) {
            structure.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }
        return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
    }

    public static final short sizeX = 3, sizeY = 3, sizeZ = 5;
    public final short xMapOffset = -1, zMapOffset = 0;

    @Override
    public boolean[] getValidSides() {
        return SIDES_HORIZONTAL;
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.ALLOW_PART_SHARE));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.tidalwave_generator";
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return new BoundingBox(utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset),yCoord,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset),utils.getRealX(mFacing,utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset), sizeX, sizeZ),yCoord+ sizeY,utils.getRealZ(mFacing,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset), sizeX, sizeZ)).isXYZInBox(aX,aY,aZ);
    }

    @Override
    public List<IWailaInfoProvider> getWailaInfos(List<IWailaInfoProvider> current) {
        IWailaTile.super.getWailaInfos(current);
        current.add(instanceInfoState);
        return current;
    }

    // Icons
    public final static IIconContainer
            sTextureSides     = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/tidalWaveGenerator/base"),
            sOverlayStop      = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/tidalWaveGenerator/front");

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (!aShouldSideBeRendered[aSide]) return null;
        if(aSide==SIDE_TOP) return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides, mRGBa),BlockTextureDefault.get(sOverlayStop ));
        return BlockTextureDefault.get(sTextureSides, mRGBa);
    }
}