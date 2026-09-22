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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.example;

import cn.kuzuanpa.ktfruaddon.api.tile.base.TileEntityBaseRoom;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.room.BoxRoomRegion;
import cn.kuzuanpa.ktfruaddon.api.tile.room.IRoomRegion;
import cn.kuzuanpa.ktfruaddon.api.tile.room.RoomCapability;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.ControllerPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.LH;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;

import static gregapi.data.CS.SIDE_BOTTOM;

public class exampleMachineRoom extends TileEntityBaseRoom {
    MultiTileEntityRegistry k = GTTileEntityRegistry.ktfruaddon, g = GTTileEntityRegistry.gregtech;

    //change value there to set usage of every block.
    public int getUsage(int blockID ,MultiTileEntityRegistry registryID){
        if (blockID == 18002 && registryID == k) return MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
        if (blockID == 18002 || blockID == 18022 && registryID == g) return MultiTileEntityMultiBlockPart.ONLY_ENERGY_OUT;
        return MultiTileEntityMultiBlockPart.NOTHING;
    }

    //这是设置主方块的物品提示
    //controls tooltip of controller block
    static {
        LH.add("gt.tooltip.multiblock.example.complex.1", "5x5x2 of Stainless Steel Walls");
        LH.add("gt.tooltip.multiblock.example.complex.2", "Main Block centered on Side-Bottom and facing outwards");
        LH.add("gt.tooltip.multiblock.example.complex.3", "Input and Output at any Blocks");
    }

    @Override public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.1"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.2"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.3"));
        super.addToolTips(aList, aStack, aF3_H);
    }
    //下面四个是设置输入输出的地方,return null是任意面
    //controls where to I/O, return null=any side
    public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte aSide, Fluid aOutput) {return getAdjacentTank(SIDE_BOTTOM);}
    public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) {return getAdjacentTileEntity(SIDE_BOTTOM);}
    public DelegatorTileEntity<IInventory> getItemInputTarget(byte aSide) {return null;}
    public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte aSide) {return null;}

    // Room
    private static final IStringBaseStructure exampleStructure = new LayerStructure(StructureContext.Axis.Y)
            .layerRule("A")
            .fixedLayer('A', "C")
            .where('C', new ControllerPredicate());

    @Override protected IRoomRegion createRoomRegion() {
        return new BoxRoomRegion(xCoord - 4, yCoord - 1, zCoord - 4, xCoord + 4, yCoord + 4, zCoord + 4);
    }
    @Override protected IStringBaseStructure getRoomStructure() {return exampleStructure;}
    @Override public long getProvidedCapabilities() {return RoomCapability.mask(RoomCapability.MICROGRAVITY);}
    @Override public boolean isRoomActive() {return false;}

    // Meta
    @Override public String getTileEntityName() {return "ktfru.multitileentity.multiblock.room.test";}
    public TileDesc[] getAvailableTiles() {return new TileDesc[]{new TileDesc(k, 30102, MultiTileEntityMultiBlockPart.EVERYTHING, 0)};}
    public int[] getCheckRange2() {return new int[] {-4, -5, -6, 3, 3, 6};}
}
