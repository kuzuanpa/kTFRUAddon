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

package cn.kuzuanpa.ktfruaddon.tile.multiblock;
//This is an example machine used to learn structures, grammars etc. It's based on large bath vat in gregtech6
//这是一个示例机器，用于学习多方块机器的结构，语法等，这个机器是基于gregtech6中的大浸洗器创建的

import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.base.TileEntityBaseLimitedOutputMachine;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.layerType.FixedLayer;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import gregapi.data.LH;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;

import static gregapi.data.CS.SIDES_VALID;
import static gregapi.data.CS.SIDE_BOTTOM;

public class exampleMappedStructureMachine extends TileEntityBaseLimitedOutputMachine {
    IStringBaseStructure structure = new LayerStructure(StructureContext.Axis.Y).layerRule("AA")
            .layer( 'A',new FixedLayer()
                    .blockRule(
                            "CXXX",
                            "CXXX",
                            " XXX",
                            "CXXX",
                            "CXXX"
                    )
            )
            .where('X', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18002)))
            .where('C', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18006)))
            .setOffset(-2,0,0)
            .setFastAutoBuild(true);
    //决定机器大小
    //this controls the size of machine.
    public final short sizeX = 5, sizeY = 1, sizeZ = 4;
    //决定结构检测的起始位置，默认情况下是从主方块起始
    //This controls where is the start point to check structure,Default is the position of controller block
    public final short xMapOffset = -2, zMapOffset = 0;

    ChunkCoordinates lastFailedPos=null;
    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        return structure.checkStructure(new StructureContext(this, worldObj, xCoord, yCoord,zCoord,mFacing, aPlayer, aInventory), aPlayer != null || aInventory != null);

    }

    //这是设置主方块的物品提示
    //controls tooltip of controller block
    static {
        LH.add("gt.tooltip.multiblock.example.complex.1", "5x5x2 of Stainless Steel Walls");
        LH.add("gt.tooltip.multiblock.example.complex.2", "Main Block centered on Side-Bottom and facing outwards");
        LH.add("gt.tooltip.multiblock.example.complex.3", "Input and Output at any Blocks");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.1"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.2"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.3"));
        super.addToolTips(aList, aStack, aF3_H);
    }
    //这里是设置该机器的内部区域
    //controls areas inside the machine
    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return new BoundingBox(utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset),yCoord,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset),utils.getRealX(mFacing,utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset), sizeX, sizeZ),yCoord+ sizeY,utils.getRealZ(mFacing,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset), sizeX, sizeZ)).isXYZInBox(aX,aY,aZ);
    }
    //下面四个是设置输入输出的地方,return null是任意面
    //controls where to I/O, return null=any side
    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte aSide, Fluid aOutput) {
        return getAdjacentTank(SIDE_BOTTOM);
    }

    @Override
    public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) {
        return getAdjacentTileEntity(SIDE_BOTTOM);
    }

    @Override
    public DelegatorTileEntity<IInventory> getItemInputTarget(byte aSide) {
        return null;
    }

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte aSide) {
        return null;
    }

    @Override
    public boolean[] getValidSides() {
        return SIDES_VALID;
    }

    //这里填写多方块结构的名称
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.example.complex";
    }
}