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

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.multiblock.example;
//This is an example machine used to learn structures, grammars etc. It's based on large bath vat in gregtech6
//这是一个示例机器，用于学习多方块机器的结构，语法等，这个机器是基于gregtech6中的大浸洗器创建的

import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.control.MachineControl;
import cn.kuzuanpa.ktfruaddon.api.tile.part.IMultiBlockPart;
import cn.kuzuanpa.ktfruaddon.api.tile.status.IMachineStatus;
import cn.kuzuanpa.ktfruaddon.api.tile.status.MachineStatusReport;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.IStructureMachine;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureBox;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureChangeList;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureParams;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureScheduler;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureScratch;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureStatus;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureView;
import cn.kuzuanpa.ktfruaddon.api.tile.base.TileEntityBaseLimitedOutputMachine;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import gregapi.block.multitileentity.IWailaTile;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.LH;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;
import java.util.UUID;

import static gregapi.data.CS.*;

public class exampleAsyncStructureMachine extends TileEntityBaseLimitedOutputMachine implements IStructureMachine, IMachineStatus, IWailaTile {

    //决定机器大小
    //this controls the size of machine.
    public final static short sizeX = 5, sizeY = 1, sizeZ = 4;
    //决定结构检测的起始位置，默认情况下是从主方块起始
    //This controls where is the start point to check structure,Default is the position of controller block
    public final static short xMapOffset = -2, yMapOffset = 0, zMapOffset = 0;
    //映射表方向:
    //                 |
    //                 |
    //            ( tX ,tZ-1)
    //(tX-1, tZ );( tX , tZ );(tX+1, tZ )---->
    //            ( tX ,tZ+1)             x轴
    //                 | z轴
    //                 v
    //y轴为一张新的表格，代码中位于最上的表是最底下一层
    //默认情况(不改动偏移量)下主方块位于tX,tZ,如下所示
    // ^^^^
    //{main,part},
    //{part,part}
    //Map direction:
    //                 |
    //                 |
    //            ( tX ,tZ-1)
    //(tX-1, tZ );( tX , tZ );(tX+1, tZ )---->
    //            ( tX ,tZ+1)             axisX
    //                 | axisZ
    //                 v
    //In default (didn't modify offset),main block is on tX,tZ.For example:
    // ^^^^
    //{main,part},
    //{part,part}
    //这里决定每个参与构成本机器的方块的子id
    //Controls every block needed to build the machine
    public static int[][][] blockIDMap = {{
            {18002, 18006, 0, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
    }};
    //这是决定物品注册库（即来源mod）k是本mod,g是gregtech
    MultiTileEntityRegistry k = GTTileEntityRegistry.ktfruaddon;
    MultiTileEntityRegistry g = GTTileEntityRegistry.gregtech;
    public MultiTileEntityRegistry[][][] registryIDMap = {{
            {g, g, k, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
    }};
    //T是忽略此位置的方块 ,F是正常检测
    //T = ignore ,F = normally check
    public static boolean[][][] ignoreMap = {{
            {T, T, T, T, T},
            {F, F, F, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
    }};

    public TileDesc[] getTileDescs(int mapX, int mapY, int mapZ) {
        return new TileDesc[]{ new TileDesc(getRegistryID(mapX, mapY, mapZ), getBlockID(mapX, mapY, mapZ),getUsage(mapX, mapY, mapZ))};
    }

    public int getUsage(int mapX, int mapY, int mapZ) {
        MultiTileEntityRegistry registryID = getRegistryID(mapX,mapY,mapZ);
        int blockID = getBlockID(mapX, mapY, mapZ);
        if (blockID == 18002&&registryID==k) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
        } else if (blockID == 18002||blockID==18022&&registryID==g) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_OUT;
        }else{return MultiTileEntityMultiBlockPart.NOTHING;}
    }

    public int getBlockID(int checkX, int checkY, int checkZ){
        return blockIDMap[checkY][checkZ][checkX];
    }

    public  boolean isIgnored(int checkX, int checkY, int checkZ){
        return ignoreMap[checkY][checkZ][checkX];
    }
    public MultiTileEntityRegistry getRegistryID(int checkX, int checkY, int checkZ){return registryIDMap[checkY][checkZ][checkX];}

    // ======================= 结构检测(迁移到快照 API 后) =======================
    // 一个地图式机器要补的东西就下面这些: 部件编码 → 快照盒子 → 冻结参数 → check → applyChange → onCheckFinished → 触发点。
    /**允许自动搭建(builder wand)的上下文: 只在主线程应用变更时使用, 不交给 worker。**/
    private transient Entity mBuilderPlayer;
    private transient IInventory mBuilderInventory;

    /**部件 key = ((registryID+1)<<16) | (MTEid+1); 0 = 不是本机认得的部件。worker 只比较这个整数。**/
    private static int packPart(int aRegistryId, int aMteId) {return ((aRegistryId + 1) << 16) | (aMteId + 1);}
    private int expectedPart(int mapX, int mapY, int mapZ) {return packPart(getRegistryID(mapX, mapY, mapZ).currentID(), getBlockID(mapX, mapY, mapZ));}
    /**变更里只带地图坐标, 主线程再解析回 TileDesc(地图本身是常量, 不必塞进变更)。**/
    private static int mapKey(int mapX, int mapY, int mapZ) {return mapX | mapY << 8 | mapZ << 16;}

    @Override
    public int encodePart(TileEntity aTileEntity) {
        if (aTileEntity instanceof MultiTileEntityMultiBlockPart) return packPart(((MultiTileEntityMultiBlockPart) aTileEntity).getMultiTileEntityRegistryID(), ((MultiTileEntityMultiBlockPart) aTileEntity).getMultiTileEntityID());
        if (aTileEntity instanceof IMultiBlockPart) return packPart(((IMultiBlockPart) aTileEntity).getMultiTileEntityRegistryID(), ((IMultiBlockPart) aTileEntity).getMultiTileEntityID());
        return 0;
    }

    /**冻结参数: 尺寸/偏移是常量, 额外带上"允许自动搭建"标志(worker 只能看这一份, 不读机器字段)。**/
    public static final class Params extends StructureParams.Frozen {
        final boolean mBuilderMode;
        Params(short aFacing, int aX, int aY, int aZ, boolean aBuilderMode) {
            super(aFacing, aX, aY, aZ, sizeX, sizeY, sizeZ, xMapOffset, yMapOffset, zMapOffset);
            mBuilderMode = aBuilderMode;
        }
    }

    @Override
    public StructureParams freezeParams() {return new Params(mFacing, xCoord, yCoord, zCoord, mBuilderPlayer != null || mBuilderInventory != null);}

    /**快照盒子 = 地图覆盖到的世界坐标范围。**/
    @Override
    public StructureBox structureBox(World aWorld) {
        StructureParams params = freezeParams();
        short facing = params.facing();
        int baseX = utils.getRealX(facing, params.x(), params.mapOffsetX(), -params.mapOffsetZ());
        int baseZ = utils.getRealZ(facing, params.z(), params.mapOffsetX(), -params.mapOffsetZ());
        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, minZ = Integer.MAX_VALUE, maxZ = Integer.MIN_VALUE;
        for (int mapZ = 0; mapZ < sizeZ; mapZ++) for (int mapX = 0; mapX < sizeX; mapX++) {
            int wx = utils.getRealX(facing, baseX, mapX, mapZ), wz = utils.getRealZ(facing, baseZ, mapX, mapZ);
            minX = Math.min(minX, wx); maxX = Math.max(maxX, wx);
            minZ = Math.min(minZ, wz); maxZ = Math.max(maxZ, wz);
        }
        return StructureBox.at(minX, params.y() + params.mapOffsetY(), minZ).size(maxX - minX + 1, sizeY, maxZ - minZ + 1).withParts().build();
    }

    /**worker: 把地图走一遍, 只比整数 key + 产出变更, 不碰世界。**/
    @Override
    public byte check(StructureView aView, StructureParams aParams, StructureScratch aScratch, StructureChangeList aOut) {
        short facing = aParams.facing();
        int baseX = utils.getRealX(facing, aParams.x(), aParams.mapOffsetX(), -aParams.mapOffsetZ());
        int baseY = aParams.y() + aParams.mapOffsetY();
        int baseZ = utils.getRealZ(facing, aParams.z(), aParams.mapOffsetX(), -aParams.mapOffsetZ());
        boolean builder = ((Params) aParams).mBuilderMode;
        boolean deferred = false, malformed = false;
        for (int mapY = 0; mapY < sizeY; mapY++) for (int mapZ = 0; mapZ < sizeZ; mapZ++) for (int mapX = 0; mapX < sizeX; mapX++) {
            if (isIgnored(mapX, mapY, mapZ)) continue;
            int wx = utils.getRealX(facing, baseX, mapX, mapZ), wy = baseY + mapY, wz = utils.getRealZ(facing, baseZ, mapX, mapZ);
            int status = aView.statusAt(wx, wy, wz);
            if (status == StructureStatus.UNLOADED || status == StructureStatus.NO_DATA) {deferred = true; continue;}
            if (aView.partKeyAt(wx, wy, wz) == expectedPart(mapX, mapY, mapZ)) aOut.add(wx, wy, wz, StructureChangeList.KIND_SET_PART, mapKey(mapX, mapY, mapZ), 0, 0);
            else if (builder) aOut.add(wx, wy, wz, StructureChangeList.KIND_PLACE_BLOCK, mapKey(mapX, mapY, mapZ), 0, 0);
            else malformed = true;
        }
        if (deferred) return StructureStatus.DEFER;
        return malformed ? StructureStatus.NOT_FORM : StructureStatus.FORM;
    }

    /**主线程: 逐条复核后应用(区块还在、部件还在)。**/
    @Override
    public boolean applyChange(World aWorld, StructureChangeList aChanges, int aIndex) {
        int key = aChanges.key(aIndex);
        TileDesc desc = getTileDescs(key & 0xFF, key >> 8 & 0xFF, key >> 16 & 0xFF)[0];
        int x = aChanges.x(aIndex), y = aChanges.y(aIndex), z = aChanges.z(aIndex);
        if (!aWorld.blockExists(x, y, z)) return false;
        if (aChanges.kind(aIndex) == StructureChangeList.KIND_PLACE_BLOCK) {
            if (mBuilderPlayer == null && mBuilderInventory == null) return false;
            return utils.tryPlaceTile(desc, this, new ChunkCoordinates(x, y, z), mBuilderPlayer, mBuilderInventory);
        }
        TileEntity tile = aWorld.getTileEntity(x, y, z);
        if (tile == null) return false;
        return utils.setTarget(this, tile, desc.aDesign, desc.aUsage, false);
    }

    @Override
    public void onCheckFinished(World aWorld, byte aOutcome, StructureChangeList aChanges, int aFailedCount) {
        mBuilderPlayer = null; mBuilderInventory = null;
        if (aOutcome == StructureStatus.DEFER) return;                 // 数据不全: 保持现状, 等重试
        mStructureOkay = aOutcome == StructureStatus.FORM && aFailedCount == 0;
    }

    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        if (!isServerSide()) return mStructureOkay;
        if (aPlayer != null || aInventory != null) {mBuilderPlayer = aPlayer; mBuilderInventory = aInventory;}
        if (!StructureScheduler.isBusy(this)) StructureScheduler.request(this, worldObj, getTileEntityName());
        return mStructureOkay;
    }

    @Override
    public void onMagnifyingGlass(List<String> aChatReturn) {
        super.onMagnifyingGlass(aChatReturn);
        MachineControl.of(this).getStatusReport().renderLines(aChatReturn);
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (isServerSide() && MachineControl.onFishRightClick(this, aPlayer)) return true;
        return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public NBTTagCompound getWailaNBT(TileEntity te, NBTTagCompound aNBT) {
        super.getWailaNBT(te, aNBT);
        MachineStatusReport.writeWaila(this, aNBT);
        return aNBT;
    }

    @Override
    public List<String> getWailaBody(List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        super.getWailaBody(currentTip, accessor, config);
        MachineStatusReport.addWailaLine(currentTip, accessor.getNBTData());
        return currentTip;
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
        return new BoundingBox(utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset),yCoord + yMapOffset,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset),utils.getRealX(mFacing,utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset), sizeX, sizeZ),yCoord+ sizeY,utils.getRealZ(mFacing,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset), sizeX, sizeZ)).isXYZInBox(aX,aY,aZ);
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
    public boolean[] getValidSides() {
        return SIDES_HORIZONTAL;
    }

    @Override
    public DelegatorTileEntity<IInventory> getItemInputTarget(byte aSide) {
        return null;
    }

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte aSide) {
        return null;
    }
    //这里填写多方块结构的名称
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.example.complex";
    }
}
