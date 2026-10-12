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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.example;
//This is an example machine used to learn structures
//这是一个示例机器，用于学习多方块机器的结构

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringStructureMachine;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.layerType.ExpandableLayer;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import gregapi.data.LH;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.List;

public class exampleStructureMachine extends TileEntityBase10MultiBlockBase implements IStringStructureMachine {
    //Structure
    ChunkCoordinates lastFailedPos=null;
    static final IStringBaseStructure structure  = new LayerStructure(StructureContext.Axis.Y).layerRule("A")
            .layer('A',
                    new ExpandableLayer(6)
                            .variation("CXXX",
                                    "CXXX",
                                    " XXX",
                                    "CXXX",
                                    "CXXX"
                            ).variation("CCCC",
                                    "CXXX",
                                    " CXX",
                                    "CXXX",
                                    "CCCC"
                            )
            )
            .where('X', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18002)))
            .where('C', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18006)))
            .setOffset(-2,0,0);
    //结构检测接入快照 API: 只补下面这几行, 结构声明一个字段都不用改
    @Override public IStringBaseStructure stringStructure() {return structure;}
    @Override public boolean isStructureOkay() {return mStructureOkay;}
    @Override public void setStructureOkay(boolean aOkay) {mStructureOkay = aOkay;}
    @Override public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {return IStringStructureMachine.trigger(this, aClickedAt, aPlayer, aInventory);}
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
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return structure.isInsideStructure(this, mFacing, aX,aY,aZ);
    }
    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        super.addToolTips(aList, aStack, aF3_H);
    }

    //这里填写多方块结构的名称
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.example.structure";
    }
}
