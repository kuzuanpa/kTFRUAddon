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


package cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.storage;

import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.part.IMultiBlockPart;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.IStructureMachine;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureBox;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureChangeList;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureParams;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureScheduler;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureScratch;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureStatus;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureView;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.kTileNBT;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import codechicken.lib.vec.BlockCoord;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.FL;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.fluid.FluidTankGT;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.multiblocks.IMultiBlockFluidHandler;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import org.apache.logging.log4j.Level;

import java.util.*;

import static gregapi.data.CS.*;

public class LiquidBattery extends MultiAdaptiveOutputBattery implements IMultiBlockFluidHandler, IMultiTileEntity.IMTE_SyncDataByteArray, IStructureMachine {
    public short maxLayer = 8, maxRange=8, liquidYLevelRender=0, wallID=0, oldYLevel=0;
    public FluidTankGT mTank = new FluidTankGT();
    MultiTileEntityRegistry k = GTTileEntityRegistry.ktfruaddon;
    MultiTileEntityRegistry g = GTTileEntityRegistry.gregtech;
    public boolean isTankChanged = false, isStructureChanged =false, isStoredEnergyChanged=false, disableTESR=false;
    public final HashMap<Short,Long> layerLiquidCapacity = new HashMap<>();
    public static final int liquidAmountPerBlock = 8000;
    public HashMap<Short, List<BlockCoord>> layeredTESRRenderSpace = new HashMap<>();
    public int[] boundForSink = new int[]{Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE};
    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {

        aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE));
        aList.add(LH.Chat.WHITE + "3x2x1 "+g.getItem(wallID).getDisplayName());
        aList.add(LH.Chat.WHITE + LH.get("ktfru.text.multiblock.storage.liquid.1"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.text.multiblock.storage.liquid.2"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.text.multiblock.storage.liquid.3")+(maxRange*2)+"x"+(maxRange*2));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.text.multiblock.storage.liquid.4")+ maxLayer);
        aList.add(LH.Chat.WHITE + LH.get("ktfru.text.multiblock.storage.liquid.5"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.text.multiblock.storage.liquid.6"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.text.multiblock.storage.liquid.7"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.text.multiblock.storage.liquid.8"));
        super.addToolTips(aList, aStack, aF3_H);
    }
    static {
        LH.add("ktfru.text.multiblock.storage.liquid.1", "Main Block centered on Side-Bottom and facing outwards");
        LH.add("ktfru.text.multiblock.storage.liquid.2", "A big sink in back of main block");
        LH.add("ktfru.text.multiblock.storage.liquid.3", "max sink size: ");
        LH.add("ktfru.text.multiblock.storage.liquid.4", "max sink height: ");
        LH.add("ktfru.text.multiblock.storage.liquid.5", "Emit RU from front of main block");
        LH.add("ktfru.text.multiblock.storage.liquid.6", "Accept energies from wall layer 2");
        LH.add("ktfru.text.multiblock.storage.liquid.7", "Auto Output Liquid from bottom of main block");
        LH.add("ktfru.text.multiblock.storage.liquid.8", "Place a Vanilla Stone Brick 2m away from main block bottom to Disable TESR");
    }
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if(aNBT.hasKey(NBT_DESIGN)) wallID = aNBT.getShort(NBT_DESIGN);
        if(aNBT.hasKey(kTileNBT.MAX_LAYER)) maxLayer = aNBT.getShort(kTileNBT.MAX_LAYER);
        if(aNBT.hasKey(kTileNBT.MAX_RANGE)) maxRange = aNBT.getShort(kTileNBT.MAX_RANGE);
        mTank.readFromNBT(aNBT,NBT_TANK);
        mEnergyType= TD.Energy.RU;
        mEnergyTypeOut= TD.Energy.RU;
        mRenderSpaceDirty = true;
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        mTank.writeToNBT(aNBT,NBT_TANK);
    }

    /**结构可能变了(部件被拆/放): 客户端把它记成"渲染数据待重算", 不再每次流体同步都跑一遍泛洪。**/
    @Override
    public void onStructureChange() {
        mRenderSpaceDirty = true;
        super.onStructureChange();
    }

    @Override
    public boolean onTickCheck(long aTimer) {
        boolean result = super.onTickCheck(aTimer)|| isTankChanged || isStoredEnergyChanged;
        isTankChanged=false;
        isStoredEnergyChanged=false;
        return result;
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);

        if (!aIsServerSide) {
            if (aTimer > 5 && !disableTESR && mTank.has()) {
                if (aTimer % 200 == 0) mRenderSpaceDirty = true;                                        // 周期性自愈
                if (aTimer % 20 == 0 && Blocks.stonebrick.equals(worldObj.getBlock(xCoord, yCoord - 2, zCoord))) disableTESR = true;
                if (mRenderSpaceDirty) {mRenderSpaceDirty = false; StructureScheduler.checkLocally(this, worldObj);}
            }
            return;
        }

        if(FL.move(mTank, getAdjacentTank(SIDE_BOTTOM))>0) isTankChanged =true;

        if(!mTank.has()) {
            mCapacity=0;
            return;
        }

        long remainEnergy = mEnergyStored;
        float fluidDensity = mTank.fluid().getDensity()<=10? 0.01F : (mTank.fluid().getDensity() /1000F);
        //update Client Render stuff
        if(aTimer%4 == 0)for (short i = 0; i < maxLayer; i++) {
            Long layerCapacity = layerLiquidCapacity.get(i);
            if (layerCapacity == null || layerCapacity <= 0) break;

            long layerEnergy = (long) (layerCapacity*(i + 1)* fluidDensity);

            if(layerEnergy > remainEnergy){
                liquidYLevelRender = (short) (100 *(yCoord + i - 1 + remainEnergy*1F/layerEnergy));
                break;
            }
            liquidYLevelRender = (short) (100*(yCoord + i));
            remainEnergy -= layerEnergy;
        }
        if(Math.abs(oldYLevel-liquidYLevelRender)>0) {
            isStoredEnergyChanged=true;
            oldYLevel=liquidYLevelRender;
        }

        if(!isTankChanged)return;
        if(FL.acid(mTank) || FL.magic(mTank) || FL.temperature(mTank) > 573)explode();
        if(FL.gas(mTank) || FL.powerconducting(mTank))mTank.setEmpty();
        long remainFluidAmount = mTank.amount();
        mCapacity=0;
        for (short i = 0; i < maxLayer; i++) {
            Long layerCapacity = layerLiquidCapacity.get(i);
            if (layerCapacity == null || layerCapacity <= 0) break;

            if(layerCapacity > remainFluidAmount) {
                mCapacity+= remainFluidAmount * (i + 1)* fluidDensity;
                break;
            }else {
                mCapacity += layerCapacity*(i + 1)* fluidDensity;
            }
            remainFluidAmount-=layerCapacity;
        }
    }

    /**部件表: 0/1 = 主机位两层(g:wallID), 2..4 = 水槽墙(k:31034/31035/31036); 0x40 位 = 已正确指向本机。**/
    private static final int PART_TARGETED = 0x40, PART_COUNT = 5;
    private static final int CELL_FREE = 0, CELL_WALL = 1, CELL_UNKNOWN = 2;
    private static final int[] FOR_X = {1, -1, 0, 0}, FOR_Z = {0, 0, 1, -1};
    /**客户端渲染数据脏标记: 只在结构可能变化时重算, 不再跟着每次流体同步跑一遍泛洪。**/
    public boolean mRenderSpaceDirty = true;
    /**builder wand 上下文: 只给主线程应用"补方块"时用, 不进 worker。**/
    private transient Entity mBuilderPlayer;
    private transient IInventory mBuilderInventory;
    private transient StructureScratch mClientScratch;

    private static int partRegistryId(int aIndex) {return aIndex <= 1 ? GTTileEntityRegistry.gregtech.currentID() : GTTileEntityRegistry.ktfruaddon.currentID();}
    private static int partMetaId(int aIndex, int aWallID) {return aIndex <= 1 ? aWallID : 31034 + aIndex - 2;}
    private static int partUsage(int aIndex) {return aIndex == 0 ? MultiTileEntityMultiBlockPart.ONLY_FLUID : aIndex == 1 ? MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN : MultiTileEntityMultiBlockPart.ONLY_FLUID_IN;}
    /**(registry, meta) → 表号+1; 0 = 不是本机认得的部件。**/
    private static int partKeyOf(int aRegistryId, int aMetaId, int aWallID) {
        for (int i = 0; i < PART_COUNT; i++) if (partRegistryId(i) == aRegistryId && partMetaId(i, aWallID) == aMetaId) return i + 1;
        return 0;
    }
    private static int partIndex(int aKey) {return (aKey & PART_TARGETED - 1) - 1;}
    private static boolean partTargeted(int aKey) {return aKey != 0 && (aKey & PART_TARGETED) != 0;}
    private static boolean isWallKey(int aKey) {int i = partIndex(aKey); return aKey != 0 && i >= 2 && i < PART_COUNT;}
    private TileDesc partDesc(int aIndex) {return new TileDesc(aIndex <= 1 ? g : k, partMetaId(aIndex, wallID), partUsage(aIndex), 0);}

    /**几何: 全部由冻结参数推出, structureBox(主线程) 与 check(worker) 共用同一套坐标。**/
    private static final class Geo {
        int mapBaseX, mapBaseZ, mapMinY, mapMaxY;
        int sinkMinX, sinkMaxX, sinkMinY, sinkMaxY, sinkMinZ, sinkMaxZ;
        int boxMinX, boxMinY, boxMinZ, boxMaxX, boxMaxY, boxMaxZ;
        int seedX, seedZ;
    }

    private static void computeGeo(StructureParams aParams, int aMaxLayer, int aMaxRange, Geo aGeo) {
        short facing = aParams.facing();
        int x = aParams.x(), y = aParams.y(), z = aParams.z();
        aGeo.mapBaseX = utils.getRealX(facing, x, aParams.mapOffsetX(), -aParams.mapOffsetZ());
        aGeo.mapBaseZ = utils.getRealZ(facing, z, aParams.mapOffsetX(), -aParams.mapOffsetZ());
        aGeo.mapMinY = y + aParams.mapOffsetY();
        aGeo.mapMaxY = aGeo.mapMinY + sizeY - 1;

        ChunkCoordinates start = utils.getRealCoord((byte) facing, x, y, z, aMaxRange, -1, 2 * aMaxRange);
        ChunkCoordinates end = utils.getRealCoord((byte) facing, x, y, z, -aMaxRange, aMaxLayer, 0);
        aGeo.sinkMinX = Math.min(start.posX, end.posX); aGeo.sinkMaxX = Math.max(start.posX, end.posX);
        aGeo.sinkMinY = Math.min(start.posY, end.posY); aGeo.sinkMaxY = Math.max(start.posY, end.posY);
        aGeo.sinkMinZ = Math.min(start.posZ, end.posZ); aGeo.sinkMaxZ = Math.max(start.posZ, end.posZ);
        aGeo.seedX = utils.getRealX(facing, x, 0, 2); aGeo.seedZ = utils.getRealZ(facing, z, 0, 2);

        // 快照盒子 = 主机位区域 ∪ 水池允许范围, 再各向外扩 1 格: 泛洪需要读到紧贴边界的墙体
        int mapMinX = Integer.MAX_VALUE, mapMaxX = Integer.MIN_VALUE, mapMinZ = Integer.MAX_VALUE, mapMaxZ = Integer.MIN_VALUE;
        for (int mapZ = 0; mapZ < sizeZ; mapZ++) for (int mapX = 0; mapX < sizeX; mapX++) {
            int wx = utils.getRealX(facing, aGeo.mapBaseX, mapX, mapZ), wz = utils.getRealZ(facing, aGeo.mapBaseZ, mapX, mapZ);
            mapMinX = Math.min(mapMinX, wx); mapMaxX = Math.max(mapMaxX, wx);
            mapMinZ = Math.min(mapMinZ, wz); mapMaxZ = Math.max(mapMaxZ, wz);
        }
        aGeo.boxMinX = Math.min(mapMinX, aGeo.sinkMinX) - 1;
        aGeo.boxMaxX = Math.max(mapMaxX, aGeo.sinkMaxX) + 1;
        aGeo.boxMinY = Math.min(aGeo.mapMinY, aGeo.sinkMinY) - 1;
        aGeo.boxMaxY = Math.max(aGeo.mapMaxY, aGeo.sinkMaxY) + 1;
        aGeo.boxMinZ = Math.min(mapMinZ, aGeo.sinkMinZ) - 1;
        aGeo.boxMaxZ = Math.max(mapMaxZ, aGeo.sinkMaxZ) + 1;
    }

    /**冻结参数: worker 只能看到这一份, 不读机器活字段(facing/maxLayer/maxRange/wallID 都在这里)。**/
    public static final class Params implements StructureParams {
        private final short mFacing;
        private final int mX, mY, mZ, mSizeX, mSizeY, mSizeZ, mOffsetX, mOffsetY, mOffsetZ, mMaxLayer, mMaxRange, mWallID;
        private final boolean mBuilderMode;
        Params(short aFacing, int aX, int aY, int aZ, int aSizeX, int aSizeY, int aSizeZ, int aOffsetX, int aOffsetY, int aOffsetZ, int aMaxLayer, int aMaxRange, int aWallID, boolean aBuilderMode) {
            mFacing = aFacing; mX = aX; mY = aY; mZ = aZ;
            mSizeX = aSizeX; mSizeY = aSizeY; mSizeZ = aSizeZ;
            mOffsetX = aOffsetX; mOffsetY = aOffsetY; mOffsetZ = aOffsetZ;
            mMaxLayer = aMaxLayer; mMaxRange = aMaxRange; mWallID = aWallID; mBuilderMode = aBuilderMode;
        }
        @Override public short facing() {return mFacing;}
        @Override public int x() {return mX;}
        @Override public int y() {return mY;}
        @Override public int z() {return mZ;}
        @Override public int sizeX() {return mSizeX;}
        @Override public int sizeY() {return mSizeY;}
        @Override public int sizeZ() {return mSizeZ;}
        @Override public int mapOffsetX() {return mOffsetX;}
        @Override public int mapOffsetY() {return mOffsetY;}
        @Override public int mapOffsetZ() {return mOffsetZ;}
        int maxLayer() {return mMaxLayer;}
        int maxRange() {return mMaxRange;}
        int wallID() {return mWallID;}
        boolean builderMode() {return mBuilderMode;}
    }

    // ---- IStructureMachine: 主线程侧 ----

    @Override
    public StructureBox structureBox(World aWorld) {
        Params params = (Params) freezeParams();
        Geo geo = new Geo();
        computeGeo(params, params.maxLayer(), params.maxRange(), geo);
        return StructureBox.at(geo.boxMinX, geo.boxMinY, geo.boxMinZ)
                .size(geo.boxMaxX - geo.boxMinX + 1, geo.boxMaxY - geo.boxMinY + 1, geo.boxMaxZ - geo.boxMinZ + 1)
                .withParts().build();
    }

    @Override
    public StructureParams freezeParams() {
        return new Params(mFacing, xCoord, yCoord, zCoord, sizeX, sizeY, sizeZ, xMapOffset, 0, 0, maxLayer, maxRange, wallID, mBuilderPlayer != null || mBuilderInventory != null);
    }

    /**快照构建期(主线程): 把部件编码成"表号 + 是否已正确指向本机", 全部读 TE 的原始字段, 不触发任何世界访问。**/
    @Override
    public int encodePart(TileEntity aTileEntity) {
        int registryId, metaId, design, mode;
        ChunkCoordinates target;
        if (aTileEntity instanceof MultiTileEntityMultiBlockPart) {
            MultiTileEntityMultiBlockPart part = (MultiTileEntityMultiBlockPart) aTileEntity;
            registryId = part.getMultiTileEntityRegistryID(); metaId = part.getMultiTileEntityID();
            design = part.mDesign; mode = part.mMode; target = part.mTargetPos;
        } else if (aTileEntity instanceof IMultiBlockPart) {
            IMultiBlockPart part = (IMultiBlockPart) aTileEntity;
            registryId = part.getMultiTileEntityRegistryID(); metaId = part.getMultiTileEntityID();
            design = part.getDesign(); mode = -1; target = part.getTargetPos();
        } else return 0;
        int key = partKeyOf(registryId, metaId, wallID);
        if (key == 0) return 0;
        int index = key - 1;
        // mMode(usage) 不写进 NBT, 读档后一律为 0, 所以 0 当作"未知, 不参与比较", 免得每次读档都重发一遍全部墙体的变更
        boolean targeted = target != null && target.posX == xCoord && target.posY == yCoord && target.posZ == zCoord
                        && design == 0 && (mode <= 0 || mode == partUsage(index));
        return key | (targeted ? PART_TARGETED : 0);
    }

    @Override
    public boolean isStillValid(World aWorld) {return !isInvalid() && aWorld == worldObj;}

    @Override
    public boolean applyChange(World aWorld, StructureChangeList aChanges, int aIndex) {
        int x = aChanges.x(aIndex), y = aChanges.y(aIndex), z = aChanges.z(aIndex);
        int kind = aChanges.kind(aIndex), index = aChanges.key(aIndex);
        if (index < 0 || index >= PART_COUNT) return false;
        if (!aWorld.blockExists(x, y, z)) return false;                       // 复核: 区块还在
        if (kind == StructureChangeList.KIND_PLACE_BLOCK) {
            if (mBuilderPlayer == null && mBuilderInventory == null) return false;
            return utils.tryPlaceTile(partDesc(index), this, new ChunkCoordinates(x, y, z), mBuilderPlayer, mBuilderInventory);
        }
        if (kind == StructureChangeList.KIND_SET_PART) {
            TileEntity tile = aWorld.getTileEntity(x, y, z);
            if (tile == null) return false;                                   // 复核: 部件还在
            return utils.setTarget(this, tile, aChanges.design(aIndex), aChanges.usage(aIndex), false);
        }
        return false;
    }

    @Override
    public void onCheckFinished(World aWorld, byte aOutcome, StructureChangeList aChanges, int aFailedCount) {
        mBuilderPlayer = null; mBuilderInventory = null;
        if (aOutcome == StructureStatus.DEFER) return;                        // 数据不足: 保持现状, 等重试
        boolean okay = aOutcome == StructureStatus.FORM && aFailedCount == 0;

        boolean capacityChanged = false;
        if (aChanges.resultSlot(2) != 0) {                                    // 主机位通过才更新容量(与旧实现一致)
            long total = aChanges.resultSlot(0);
            int layers = (int) aChanges.resultSlot(1);
            layerLiquidCapacity.clear();                                      // 旧代码会残留上层旧值, 让 mCapacity 虚高
            for (int i = 0; i < layers; i++) layerLiquidCapacity.put((short) aChanges.resultSlot(3 + 2 * i), aChanges.resultSlot(4 + 2 * i));
            capacityChanged = mTank.capacity() != total;
            if (capacityChanged) {
                mTank.setCapacity(total);
                if (mTank.amount() > total) {mTank.remove(mTank.amount() - total); isTankChanged = true;}
            }
        }
        boolean okayChanged = mStructureOkay != okay;
        mStructureOkay = okay;
        if (capacityChanged || okayChanged) {isStructureChanged = true; updateClientData();}
    }

    @Override
    public void onLocalCheckFinished(World aWorld, byte aOutcome, StructureView aView) {
        Params params = (Params) freezeParams();
        Geo geo = new Geo();
        computeGeo(params, params.maxLayer(), params.maxRange(), geo);
        if (mClientScratch == null) mClientScratch = new StructureScratch(aView.sizeX() * aView.sizeY() * aView.sizeZ());
        layeredTESRRenderSpace.clear();
        int layers = 0;
        LayerResult result = new LayerResult();
        for (short layer = 0; layer < params.maxLayer(); layer++) {
            ArrayList<BlockCoord> space = new ArrayList<>();
            if (floodLayer(aView, mClientScratch, geo, params.wallID(), params.y() + layer, layer, result, null, false, space) != 0) break;
            layeredTESRRenderSpace.put((short) (layer + params.y()), space);
            layers++;
        }
        boundForSink[0] = geo.sinkMinX; boundForSink[1] = params.y(); boundForSink[3] = geo.sinkMaxX; boundForSink[4] = params.y() + layers; boundForSink[2] = geo.sinkMinZ; boundForSink[5] = geo.sinkMaxZ;
    }

    // ---- IStructureMachine: worker 侧 ----

    @Override
    public byte check(StructureView aView, StructureParams aParams, StructureScratch aScratch, StructureChangeList aOut) {
        if (!(aParams instanceof Params)) return StructureStatus.DEFER;
        Params params = (Params) aParams;
        Geo geo = new Geo();
        computeGeo(params, params.maxLayer(), params.maxRange(), geo);

        boolean deferred = false, malformed = false;

        // 主机位 3x2x1
        for (int mapY = 0; mapY < sizeY; mapY++) for (int mapZ = 0; mapZ < sizeZ; mapZ++) for (int mapX = 0; mapX < sizeX; mapX++) {
            int wx = utils.getRealX(params.facing(), geo.mapBaseX, mapX, mapZ);
            int wy = geo.mapMinY + mapY;
            int wz = utils.getRealZ(params.facing(), geo.mapBaseZ, mapX, mapZ);
            int key = cellKey(aView, wx, wy, wz);
            if (key < 0) {deferred = true; continue;}
            int expected = mapY == 0 ? 0 : 1;
            if (partIndex(key) != expected) {
                if (params.builderMode()) aOut.add(wx, wy, wz, StructureChangeList.KIND_PLACE_BLOCK, expected, 0, partUsage(expected));
                else malformed = true;
            } else if (!partTargeted(key)) {
                aOut.add(wx, wy, wz, StructureChangeList.KIND_SET_PART, expected, 0, partUsage(expected));
            }
        }

        // 水槽逐层泛洪
        long[] slots = aOut.resultSlots(3 + 2 * params.maxLayer());
        LayerResult result = new LayerResult();
        long total = 0;
        int layers = 0;
        for (int layer = 0; layer < params.maxLayer(); layer++) {
            int status = floodLayer(aView, aScratch, geo, params.wallID(), params.y() + layer, layer, result, aOut, params.builderMode(), null);
            if (status != 0) {if (status == CELL_UNKNOWN) deferred = true; break;}
            total += result.capacity * liquidAmountPerBlock;
            slots[3 + 2 * layers] = layer;
            slots[4 + 2 * layers] = result.capacity * liquidAmountPerBlock;
            layers++;
        }
        slots[0] = total;
        slots[1] = layers;
        slots[2] = malformed ? 0 : 1;                 // 主机位是否通过: 与旧实现一致, 主机位没过就不动罐子容量

        if (deferred) return StructureStatus.DEFER;
        return malformed || total <= 0 ? StructureStatus.NOT_FORM : StructureStatus.FORM;
    }

    /**一层水池的泛洪判定; 与旧 checkSink2 等价(包含"下方已访问过就不必再验下方墙"的语义), 但没有 O(n^2) 判重。**/
    private static int floodLayer(StructureView aView, StructureScratch aScratch, Geo aGeo, int aWallID, int aLayerY, int aLayer,
                                  LayerResult aResult, StructureChangeList aOut, boolean aBuilderMode, List<BlockCoord> aInteriorOut) {
        int width = aGeo.boxMaxX - aGeo.boxMinX + 1, depth = aGeo.boxMaxZ - aGeo.boxMinZ + 1;
        byte[] cur = aScratch.buffer(0, width * depth), prev = aScratch.buffer(1, width * depth);
        Arrays.fill(cur, (byte) 0);
        if (aLayer == 0) Arrays.fill(prev, (byte) 0);
        int[] queue = aScratch.queue();
        aResult.capacity = 0;

        if (aGeo.seedX < aGeo.boxMinX || aGeo.seedX > aGeo.boxMaxX || aGeo.seedZ < aGeo.boxMinZ || aGeo.seedZ > aGeo.boxMaxZ) return CELL_UNKNOWN;
        int seedKey = cellKey(aView, aGeo.seedX, aLayerY, aGeo.seedZ);
        if (seedKey < 0) return CELL_UNKNOWN;
        if (isWallKey(seedKey)) return CELL_WALL;                 // 起点本身是墙: 结构错误(与旧代码一致)
        int seed = (aGeo.seedX - aGeo.boxMinX) * depth + (aGeo.seedZ - aGeo.boxMinZ);
        cur[seed] = 1;
        aResult.capacity = 1;
        if (aInteriorOut != null) aInteriorOut.add(new BlockCoord(aGeo.seedX, aLayerY, aGeo.seedZ));

        int head = 0, tail = 1;
        queue[0] = seed;
        while (head < tail) {
            int cell = queue[head++];
            int cx = aGeo.boxMinX + cell / depth, cz = aGeo.boxMinZ + cell % depth;
            if (cx < aGeo.sinkMinX || cx > aGeo.sinkMaxX || cz < aGeo.sinkMinZ || cz > aGeo.sinkMaxZ) return CELL_WALL;   // 越界: 该层不成立
            if (prev[cell] == 0) {                                 // 下方那格不在上一层的水里 → 必须是墙
                int belowKey = cellKey(aView, cx, aLayerY - 1, cz);
                if (belowKey < 0) return CELL_UNKNOWN;
                if (!isWallKey(belowKey)) return CELL_WALL;
                emitWall(aOut, aBuilderMode, cx, aLayerY - 1, cz, belowKey);
            }
            for (int d = 0; d < 4; d++) {
                int nx = cx + FOR_X[d], nz = cz + FOR_Z[d];
                if (nx < aGeo.boxMinX || nx > aGeo.boxMaxX || nz < aGeo.boxMinZ || nz > aGeo.boxMaxZ) continue;
                int next = (nx - aGeo.boxMinX) * depth + (nz - aGeo.boxMinZ);
                if (cur[next] != 0) continue;
                cur[next] = 1;
                int key = cellKey(aView, nx, aLayerY, nz);
                if (key < 0) return CELL_UNKNOWN;
                if (isWallKey(key)) {emitWall(aOut, aBuilderMode, nx, aLayerY, nz, key); continue;}
                aResult.capacity++;
                if (aInteriorOut != null) aInteriorOut.add(new BlockCoord(nx, aLayerY, nz));
                queue[tail++] = next;
            }
        }
        System.arraycopy(cur, 0, prev, 0, cur.length);             // 这一层的水面成为下一层的"下方已确认"集合
        return CELL_FREE;
    }

    /**@return 部件 key, 或 -1 表示这个位置的数据不可用(未加载/盒子外)。**/
    private static int cellKey(StructureView aView, int aX, int aY, int aZ) {
        int status = aView.statusAt(aX, aY, aZ);
        if (status == StructureStatus.UNLOADED || status == StructureStatus.NO_DATA) return -1;
        return aView.partKeyAt(aX, aY, aZ);
    }

    /**把"边界上确实是本机部件、但还没指向本机"的位置记进变更列表(idempotent, 已正确的会跳过)。**/
    private static void emitWall(StructureChangeList aOut, boolean aBuilderMode, int aX, int aY, int aZ, int aKey) {
        if (aOut == null || partTargeted(aKey)) return;
        int index = partIndex(aKey);
        aOut.add(aX, aY, aZ, StructureChangeList.KIND_SET_PART, index, 0, partUsage(index));
    }

    private static final class LayerResult {
        int capacity;
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(!isServerSide())return 0;
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.storage.liquid";
    }

    public TileDesc[] getAvailableTiles() {
        return new TileDesc[]{
                new TileDesc(k,31034, MultiTileEntityMultiBlockPart.ONLY_FLUID_IN, 0),
                new TileDesc(k,31035, MultiTileEntityMultiBlockPart.ONLY_FLUID_IN, 0),
                new TileDesc(k,31036, MultiTileEntityMultiBlockPart.ONLY_FLUID_IN, 0)
        };
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return true;
    }
    @Override public boolean isInput(byte aSide) {return true;}
    protected IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {isTankChanged =true; return mTank;}
    protected IFluidTank getFluidTankDrainable2(byte aSide, FluidStack aFluidToDrain) {isTankChanged =true; return mTank;}
    protected IFluidTank[] getFluidTanks2(byte aSide) {isTankChanged =true;return mTank.AS_ARRAY;}

    @Override public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (!aShouldSideBeRendered[aSide]) return null;
        return BlockTextureMulti.get(BlockTextureDefault.get(sColoreds[aSide==mFacing||aSide==OPOS[mFacing]?0:1], mRGBa));
    }

    // Icons
    private static IIconContainer sColoreds[] = new IIconContainer[] {
            new Textures.BlockIcons.CustomIcon("machines/energystorages/flywheel_box/colored/axis"),
            new Textures.BlockIcons.CustomIcon("machines/energystorages/flywheel_box/colored/side"),
    };

    @Override
    public double getMaxRenderDistanceSquared() {
        return 65536;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(boundForSink[0],boundForSink[1],boundForSink[2],boundForSink[3],boundForSink[4],boundForSink[5]);
    }

    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        IPacket packet = aSendAll ?
                mTank.getFluid() == null ?
                getClientDataPacketByteArray(aSendAll, (byte) UT.Code.getR(mRGBa), (byte)UT.Code.getG(mRGBa), (byte)UT.Code.getB(mRGBa), getVisualData(), getDirectionData())
                :
                getClientDataPacketByteArray(aSendAll, (byte) UT.Code.getR(mRGBa), (byte)UT.Code.getG(mRGBa), (byte)UT.Code.getB(mRGBa), getVisualData(), getDirectionData(),
                        UT.Code.toByteS(liquidYLevelRender,0),UT.Code.toByteS(liquidYLevelRender,1),
                        UT.Code.toByteI(mTank.getFluid().getFluidID(),0), UT.Code.toByteI(mTank.getFluid().getFluidID(),1), UT.Code.toByteI(mTank.getFluid().getFluidID(),2), UT.Code.toByteI(mTank.getFluid().getFluidID(),3)
                )

            : mTank.getFluid() == null ?
                super.getClientDataPacket(aSendAll)
                :
                getClientDataPacketByteArray(aSendAll, getVisualData(),
                UT.Code.toByteS(liquidYLevelRender,0),UT.Code.toByteS(liquidYLevelRender,1),
                UT.Code.toByteI(mTank.getFluid().getFluidID(),0), UT.Code.toByteI(mTank.getFluid().getFluidID(),1), UT.Code.toByteI(mTank.getFluid().getFluidID(),2), UT.Code.toByteI(mTank.getFluid().getFluidID(),3),
                (byte) (isStoredEnergyChanged?1:0)
        );
        if(isStructureChanged) isStructureChanged =false;
        return packet;
    }

    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler){
        Fluid fluid;
        if(aData.length == 11){
            super.receiveDataByteArray(aData, aNetworkHandler);
            liquidYLevelRender = UT.Code.combine(aData[5],aData[6]);
            fluid = FluidRegistry.getFluid(UT.Code.combine(aData[7],aData[8],aData[9],aData[10]));
            if(fluid == null)fluid = FL.Water.fluid();
            mTank.fill(new FluidStack(fluid,1));
            isStructureChanged=true;
            return true;
        } else if (aData.length == 8) {
            liquidYLevelRender = UT.Code.combine(aData[1],aData[2]);
            fluid = FluidRegistry.getFluid(UT.Code.combine(aData[3],aData[4],aData[5],aData[6]));
            if(fluid == null)fluid = FL.Water.fluid();
            mTank.fill(new FluidStack(fluid,1));
            if(aData[7]==1) isStructureChanged=true;
            return super.receiveDataByte(aData[0],aNetworkHandler);
        }else if (aData.length == 5) {
            super.receiveDataByteArray(aData, aNetworkHandler);
            isStructureChanged=true;
            return true;
        }else return super.receiveDataByte(aData[0],aNetworkHandler);
    }

    public final static short sizeX = 3, sizeY = 2, sizeZ = 1;
    public final static short xMapOffset = -1;

    //change value there to set usage of every block.

    public TileDesc[] getTileDescs(int mapX, int mapY, int mapZ) {
        return new TileDesc[]{ new TileDesc(getRegistryID(mapX, mapY, mapZ), getBlockID(mapX, mapY, mapZ),getUsage(mapX, mapY, mapZ))};
    }

    public int getUsage(int mapX, int mapY, int mapZ){
        if(mapY==0)return MultiTileEntityMultiBlockPart.ONLY_FLUID;
        return MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
    }

    public int getBlockID(int checkX, int checkY, int checkZ){return wallID;}
    public boolean isIgnored(int checkX, int checkY, int checkZ){return false;}
    public MultiTileEntityRegistry getRegistryID(int x,int y,int z){return g;}

    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        if (!isServerSide()) return mStructureOkay;                     // 客户端渲染数据见 onTick2 的本地检查
        if (aPlayer != null || aInventory != null) {mBuilderPlayer = aPlayer; mBuilderInventory = aInventory;}
        if (!StructureScheduler.isBusy(this)) StructureScheduler.request(this, worldObj, getTileEntityName());
        return mStructureOkay;                                         // 在飞期间不动状态, 免得和 GT6 的翻转逻辑互相覆盖
    }
}
