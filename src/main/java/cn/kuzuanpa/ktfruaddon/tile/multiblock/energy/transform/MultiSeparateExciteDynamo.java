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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.transform;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import gregapi.data.LH;
import gregapi.tileentity.ITileEntityUnloadable;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.List;

import static gregapi.data.CS.*;

public class MultiSeparateExciteDynamo extends MultiTransformerSeparateExciteBase{
    public short mDynamoWalls = 18022;

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_DESIGN)) mDynamoWalls = aNBT.getShort(NBT_DESIGN);
        structure = new LayerStructure(StructureContext.Axis.Y).layerRule("AABAA")
                .fixedLayer('A',
                        "WCCCW",
                        "WCCCW",
                        "WCCCW",
                        "WCCCW",
                        "WCCCW"
                ).fixedLayer('B',
                        "WCCCW",
                        "WCCCW",
                        " WWWO",
                        "WCCCW",
                        "WCCCW"
                )
                .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, mDynamoWalls, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN)))
                .where('O', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, mDynamoWalls, MultiTileEntityMultiBlockPart.ONLY_ENERGY_OUT, 2)))
                .where('C', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18040, MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN)))
                .setOffset(-2,-2,0) ;
    }



//Structure
ChunkCoordinates lastFailedPos=null;
IStringBaseStructure structure ;
@Override
public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
    int tX = xCoord, tY = yCoord, tZ = zCoord;
    if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
    lastFailedPos = structure.checkStructure(new StructureContext(this, (aPlayer != null || aInventory != null)? StructureContext.StringBaseMode.SET: StructureContext.StringBaseMode.CHECK, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
    return lastFailedPos==null;
}
public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
    if (!isServerSide())return true;

    if(!mStructureOkay)aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED+LH.get(I18nHandler.STRUCTURE_ERR)));

    ItemStack equippedItem=aPlayer.getCurrentEquippedItem();
    if (equippedItem!=null && equippedItem.getItem() instanceof ItemProjector) {
        structure.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
        return true;
    }

    openGUI(aPlayer, aSide);
    return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
}

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return true;
    }

    static {
        LH.add("gt.tooltip.multiblock.dynamo.1", "Two 3x3s with 2m inbetween made of the Block you crafted this of");
        LH.add("gt.tooltip.multiblock.dynamo.2", "a 3x3x2 of 18 Large Copper Coils inbetween");
        LH.add("gt.tooltip.multiblock.dynamo.3", "Main centered on one of the 3x3s facing outwards");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN     + LH.get(LH.STRUCTURE) + ":");
        aList.add(LH.Chat.WHITE    + LH.get("gt.tooltip.multiblock.dynamo.1"));
        aList.add(LH.Chat.WHITE    + LH.get("gt.tooltip.multiblock.dynamo.2"));
        aList.add(LH.Chat.WHITE    + LH.get("gt.tooltip.multiblock.dynamo.3"));
        super.addToolTips(aList, aStack, aF3_H);
    }

    public ITileEntityUnloadable mEmitter = null;

    @Override public TileEntity getEmittingTileEntity() {if (mEmitter == null || mEmitter.isDead()) {mEmitter = null; TileEntity tTileEntity = getTileEntityAtSideAndDistance(OPOS[mFacing], 4); if (tTileEntity instanceof ITileEntityUnloadable) mEmitter = (ITileEntityUnloadable)tTileEntity;} return mEmitter == null ? this : (TileEntity)mEmitter;}
    @Override public byte getEmittingSide() {return OPOS[mFacing];}
    @Override public boolean isInput (byte aSide) {return aSide != OPOS[mFacing];}
    @Override public boolean isOutput(byte aSide) {return aSide == OPOS[mFacing];}

    @Override public byte getDefaultSide() {return SIDE_FRONT;}
    @Override public boolean[] getValidSides() {return SIDES_VALID;}

    @Override public String getTileEntityName() {return "ktfru.multitileentity.multiblock.dynamo.sp";}
}
