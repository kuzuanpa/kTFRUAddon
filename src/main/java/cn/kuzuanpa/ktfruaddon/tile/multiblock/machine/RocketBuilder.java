/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.multiblock.machine;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import gregapi.data.LH;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
import gregapi.util.WD;
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

import java.util.List;

import static cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler.HAS_PROJECTOR_STRUCTURE;
import static gregapi.data.CS.*;

public class RocketBuilder extends TileEntityBase10MultiBlockMachine {

    //Structure
    ChunkCoordinates lastFailedPos=null;
    static IStringBaseStructure structure = new LayerStructure(StructureContext.Axis.Y).layerRule("ABC")
            .fixedLayer('A',
                    "AAA",
                    " CC",
                    "BBB",
                    "BBB",
                    "BBB",
                    "AAA"
            )
            .fixedLayer('B',
                    "AAA",
                    "CCC",
                    "   ",
                    "   ",
                    "   ",
                    "AAA"
            ).fixedLayer('C',
                    "   ",
                    "A A",
                    "   ",
                    "   ",
                    "   ",
                    "A A"
            )
            .where('A', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18006, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN)))
            .where('B', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18002, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID_IN)))
            .where('C', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31046, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN)))
            .setOffset(-1,0,0) ;

    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = structure.checkStructure(new StructureContext(this, (aPlayer != null || aInventory != null)? StructureContext.StringBaseMode.SET: StructureContext.StringBaseMode.CHECK, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        return lastFailedPos==null;
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN+LH.get(HAS_PROJECTOR_STRUCTURE));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return true;
    }

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte aSide, Fluid aOutput) {
        return getAdjacentTank(SIDE_UP);
    }

    @Override
    public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) {
        DelegatorTileEntity<TileEntity> te = WD.te(this.worldObj, utils.getRealX(mFacing,xCoord,5,1), this.yCoord , utils.getRealZ(mFacing,zCoord,5,1), FACING_ROTATIONS[mFacing][SIDE_RIGHT], false);
        if(te == null || te.mTileEntity == null) return this.delegator(SIDE_INVALID);
        return new DelegatorTileEntity<>(te.mTileEntity,SIDE_INSIDE);
    }

    @Override
    public DelegatorTileEntity<IInventory> getItemInputTarget(byte aSide) {
        return new DelegatorTileEntity<>(this,SIDE_UP);
    }

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte aSide) {
        return new DelegatorTileEntity<>(this,SIDE_UP);
    }
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide())return true;

        if(!mStructureOkay)aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED+LH.get(I18nHandler.STRUCTURE_ERR)));

        ItemStack equippedItem=aPlayer.getCurrentEquippedItem();
        if (equippedItem!=null && equippedItem.getItem() instanceof ItemProjector) {
            structure.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }
        return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
    }
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.rocket.builder";
    }
}