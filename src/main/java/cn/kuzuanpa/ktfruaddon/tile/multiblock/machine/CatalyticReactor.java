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
import gregapi.data.LH;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
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

import static gregapi.data.CS.T;

public class CatalyticReactor extends TileEntityBase10MultiBlockMachine {
    private ChunkCoordinates lastFailedPos;

    private static final IStringBaseStructure structure = new LayerStructure(StructureContext.Axis.Y).layerRule("ABCDE")
            .fixedLayer('A',
                    "CCCCC",
                    "CCECC",
                    "CCCCC",
                    "CCCCC",
                    "CCCCC"
            ).fixedLayer('B',
                    "CCCCC",
                    "C   C",
                    "C B C",
                    "C   C",
                    "CCCCC"
            ).fixedLayer('C',
                    "CCCCC",
                    "C   C",
                    "C B C",
                    "C   C",
                    "CCCCC"
            ).fixedLayer('D',
                    "CCCCC",
                    "C   C",
                    "C B C",
                    "C   C",
                    "CCCCC"
            ).fixedLayer('E',
                    "CCCCC",
                    "C   C",
                    "C I C",
                    "C   C",
                    "CCCCC"
            )
            .where('C', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31200, MultiTileEntityMultiBlockPart.NOTHING)))
            .where('B', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31201, MultiTileEntityMultiBlockPart.NOTHING)))
            .where('E', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31202, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN)))
            .where('I', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31203, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID)))
            .setOffset(-2, -1, 0);

    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        if (!worldObj.blockExists(xCoord, yCoord, zCoord)) return mStructureOkay;
        lastFailedPos = structure.checkStructure(new StructureContext(this,
                (aPlayer != null || aInventory != null) ? StructureContext.StringBaseMode.SET : StructureContext.StringBaseMode.CHECK,
                worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        return lastFailedPos == null;
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide()) return true;
        if (!mStructureOkay) aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED + LH.get(I18nHandler.STRUCTURE_ERR)));

        ItemStack equippedItem = aPlayer.getCurrentEquippedItem();
        if (equippedItem != null && equippedItem.getItem() instanceof ItemProjector) {
            structure.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT,
                    worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }
        return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return structure.isInsideStructure(this, mFacing, aX, aY, aZ);
    }

    @Override
    public DelegatorTileEntity<IInventory> getItemInputTarget(byte aSide) {
        return null;
    }

    @Override
    public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) {
        return null;
    }

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte aSide) {
        return null;
    }

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte aSide, Fluid aOutput) {
        return null;
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.catalyticreactor.1"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.catalyticreactor.2"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.catalyticreactor.3"));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.catalyticreactor";
    }

    static {
        LH.add("ktfru.tooltip.multiblock.catalyticreactor.1", "5x5x5 Catalytic Reactor with a 3-layer catalyst bed core");
        LH.add("ktfru.tooltip.multiblock.catalyticreactor.2", "Main controller centered on the front-bottom wall");
        LH.add("ktfru.tooltip.multiblock.catalyticreactor.3", "Energy port at the bottom; item and fluid ports at the top");
    }
}
