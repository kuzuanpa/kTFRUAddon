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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.machine;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.ControllerPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import gregapi.data.LH;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.List;

public class MicrogravityCrystalGrowthMachine extends MicrogravityMachine {
    private ChunkCoordinates lastFailedPos;

    // Structure
    private static final IStringBaseStructure structure = new LayerStructure(StructureContext.Axis.Y).layerRule("ABC")
            .fixedLayer('A',
                    "WCW",
                    "WWW",
                    "WWW")
            .fixedLayer('B',
                    "WIW",
                    "W W",
                    "WOW")
            .fixedLayer('C',
                    "WWW",
                    "WEW",
                    "WWW")
            .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, CentrifugalMicrogravityController.SHIELD_WALL, MultiTileEntityMultiBlockPart.NOTHING, 0)))
            .where('C', new ControllerPredicate())
            .where('I', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, CentrifugalMicrogravityController.ACCESS_HATCH, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID_IN, 0)))
            .where('O', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, CentrifugalMicrogravityController.ACCESS_HATCH, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID_OUT, 0)))
            .where('E', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, CentrifugalMicrogravityController.ENERGY_PORT, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN, 0)))
            .setOffset(0, 0, -1);

    static {
        LH.add("ktfru.tooltip.multiblock.microgravitycrystalgrowth.1", "3x3x3 high-shield growth chamber");
        LH.add("ktfru.tooltip.multiblock.microgravitycrystalgrowth.2", "Controller centered on the front-bottom wall; input, output and energy ports on the shell.");
    }

    @Override public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        if (!worldObj.blockExists(xCoord, yCoord, zCoord)) return mStructureOkay;
        lastFailedPos = structure.checkStructure(new StructureContext(this,
                (aPlayer != null || aInventory != null) ? StructureContext.StringBaseMode.SET : StructureContext.StringBaseMode.CHECK,
                worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        return lastFailedPos == null;
    }
    @Override public boolean isInsideStructure(int aX, int aY, int aZ) {return structure.isInsideStructure(this, mFacing, aX, aY, aZ);}

    @Override public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide()) return true;
        if (!mStructureOkay) aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED + LH.get(I18nHandler.STRUCTURE_ERR)));
        ItemStack equipped = aPlayer.getCurrentEquippedItem();
        if (equipped != null && equipped.getItem() instanceof ItemProjector) {
            structure.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT,
                    worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }
        return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
    }

    @Override public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.microgravitycrystalgrowth.1"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.microgravitycrystalgrowth.2"));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override public String getTileEntityName() {return "ktfru.multitileentity.machine.microgravitycrystalgrowth";}
}
