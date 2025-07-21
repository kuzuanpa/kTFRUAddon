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

import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.base.ModelRenderBaseMultiBlockMachine;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import gregapi.data.LH;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.WD;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.List;

import static cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler.HAS_PROJECTOR_STRUCTURE;
import static gregapi.data.CS.*;

public class  CNCMachine3 extends ModelRenderBaseMultiBlockMachine {

    public final short machineX = 5, machineY = 3, machineZ = 3;
    //values used by TESR
    public int processTime, proTime, headMoveToX, headMoveToZ;

    //Structure
    ChunkCoordinates lastFailedPos=null;
    static IStringBaseStructure structure = new LayerStructure(StructureContext.Axis.Y).layerRule("ABC")
            .fixedLayer('A',
                    "AAA",
                    " CC",
                    "BBB",
                    "BBB",
                    "BBB"
            )
            .fixedLayer('B',
                    "AAA",
                    "CCC",
                    "   ",
                    "   ",
                    "   "
            ).fixedLayer('C',
                    " A ",
                    " A ",
                    " A ",
                    " D ",
                    "   "
            )
            .where('A', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31000, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN, 1)))
            .where('B', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31007, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID_IN, 1)))
            .where('C', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31008, MultiTileEntityMultiBlockPart.NOTHING, 1)))
            .where('D', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31009, MultiTileEntityMultiBlockPart.NOTHING, 1)))
            .setOffset(-1,0,0) ;

    @Override
    public boolean checkStructure3(boolean shouldPartsTransparent, ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = structure.checkStructure(new StructureContext(this, (aPlayer != null || aInventory != null)? StructureContext.StringBaseMode.SET: StructureContext.StringBaseMode.CHECK, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        return lastFailedPos==null;
    }
    @Override
    public void resetParts() {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return;
        lastFailedPos = structure.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.RESET, worldObj, xCoord, yCoord, zCoord, mFacing, null,null));
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN+LH.get(HAS_PROJECTOR_STRUCTURE));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) { return new BoundingBox(utils.getRealX(mFacing,xCoord,-1,0),yCoord,utils.getRealZ(mFacing,zCoord,-1,0),utils.getRealX(mFacing,utils.getRealX(mFacing,xCoord,-1,0),machineX,machineZ),yCoord+machineY,utils.getRealZ(mFacing,utils.getRealZ(mFacing,zCoord,-1,0),machineX,machineZ)).isXYZInBox(aX,aY,aZ);}

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte aSide, Fluid aOutput) {
        return getAdjacentTank(SIDE_UP);
    }

    @Override
    public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) {
        DelegatorTileEntity<TileEntity> te = WD.te(this.worldObj, utils.getRealX(mFacing,xCoord,4,1), this.yCoord , utils.getRealZ(mFacing,zCoord,4,1), FACING_ROTATIONS[mFacing][SIDE_RIGHT], false);
        if(te == null || te.mTileEntity == null) return this.delegator(SIDE_INVALID);
        return new DelegatorTileEntity<>(te.mTileEntity,SIDE_INSIDE);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox((double)(this.xCoord - machineX), (double)(this.yCoord - 1), (double)(this.zCoord - machineZ), (double)(this.xCoord + machineX), (double)(this.yCoord + machineY), (double)(this.zCoord + machineZ));
    }
    @Override
    public double getMaxRenderDistanceSquared() {
        return 65536;
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
        return "ktfru.multitileentity.multiblock.specialRend.cncmachine3";
    }
}