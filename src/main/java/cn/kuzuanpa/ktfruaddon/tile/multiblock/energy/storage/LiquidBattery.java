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

import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.api.code.codeUtil;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.async.AsyncStructureManager;
import cn.kuzuanpa.ktfruaddon.api.tile.async.IAsyncStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.async.IMappedStructureAsync;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.kTileNBT;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import codechicken.lib.vec.BlockCoord;
import cpw.mods.fml.common.FMLLog;
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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import org.apache.logging.log4j.Level;

import java.util.*;
import java.util.stream.Collectors;

import static gregapi.data.CS.*;

public class LiquidBattery extends MultiAdaptiveOutputBattery implements IMultiBlockFluidHandler, IMultiTileEntity.IMTE_SyncDataByteArray, IMappedStructureAsync, IAsyncStructure {
    public short maxLayer = 8, maxRange=8, liquidYLevelRender=0, wallID=0, oldYLevel=0;
    public FluidTankGT mTank = new FluidTankGT();
    short k = GTTileEntityRegistry.ktfruaddon;
    short g = GTTileEntityRegistry.gregtech;
    public boolean isTankChanged = false, isStructureChanged =false, isStoredEnergyChanged=false, disableTESR=false;
    public final HashMap<Short,Long> layerLiquidCapacity = new HashMap<>();
    public static final int liquidAmountPerBlock = 8000;
    public HashMap<Short, List<BlockCoord>> layeredTESRRenderSpace = new HashMap<>();
    public int[] boundForSink = new int[]{Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE};
    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {

        aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE));
        aList.add(LH.Chat.WHITE + "3x2x1 "+MultiTileEntityRegistry.getRegistry(g).getItem(wallID).getDisplayName());
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.storage.liquid.1"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.storage.liquid.2"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.storage.liquid.3")+(maxRange*2)+"x"+(maxRange*2));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.storage.liquid.4")+ maxLayer);
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.storage.liquid.5"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.storage.liquid.6"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.storage.liquid.7"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.storage.liquid.8"));
        super.addToolTips(aList, aStack, aF3_H);
    }
    static {
        LH.add("ktfru.tooltip.multiblock.storage.liquid.1", "Main Block centered on Side-Bottom and facing outwards");
        LH.add("ktfru.tooltip.multiblock.storage.liquid.2", "A big sink in back of main block");
        LH.add("ktfru.tooltip.multiblock.storage.liquid.3", "max sink size: ");
        LH.add("ktfru.tooltip.multiblock.storage.liquid.4", "max sink height: ");
        LH.add("ktfru.tooltip.multiblock.storage.liquid.5", "Emit RU from front of main block");
        LH.add("ktfru.tooltip.multiblock.storage.liquid.6", "Accept energies from wall layer 2");
        LH.add("ktfru.tooltip.multiblock.storage.liquid.7", "Auto Output Liquid from bottom of main block");
        LH.add("ktfru.tooltip.multiblock.storage.liquid.8", "Place a Vanilla Stone Brick 2m away from main block bottom to Disable TESR");
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
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        mTank.writeToNBT(aNBT,NBT_TANK);
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

        if(!aIsServerSide && aTimer>5 && isStructureChanged && !disableTESR) checkStructure2(null,null,null); //Update Structure for TESR
        if(!aIsServerSide)return;

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

    public boolean checkSinkAndUpdateCapacity(AsyncStructureManager.WorldContainer worldContainer, ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory){
        final BlockCoord StartPoi = codeUtil.MCCoord2CCCoord(utils.getRealCoord(mFacing, xCoord, yCoord, zCoord, maxRange, -1, 2*maxRange));
        final BlockCoord EndPoi = codeUtil.MCCoord2CCCoord(utils.getRealCoord(mFacing, xCoord, yCoord, zCoord, -maxRange, maxLayer, 0));
        BoundingBox checkRange = new BoundingBox(StartPoi, EndPoi);

        ArrayList<BlockCoord> checkedBlock = new ArrayList<>();
        if (isServerSide()) {
            long tankCapacityOld = mTank.capacity();
            long tankCapacity = 0;
            for (short layer = 0; layer < maxLayer; layer++) {
                layerCapacity = 0;
                short fLayer = layer;
                checkedBlock.removeIf(coord->coord.y == yCoord+ fLayer -2);
                if (checkSink2(worldContainer, aClickedAt, aPlayer, aInventory, checkedBlock, utils.getRealX(mFacing, xCoord, 0, 2), utils.getRealZ(mFacing, zCoord, 0, 2), layer, checkRange)) {
                    tankCapacity += layerCapacity * liquidAmountPerBlock;
                    layerLiquidCapacity.put(layer, layerCapacity * liquidAmountPerBlock);
                }else break;
            }
            if (tankCapacityOld != tankCapacity) {
                mTank.setCapacity(tankCapacity);
                if(mTank.amount() > tankCapacity){
                    mTank.remove(mTank.amount() - tankCapacity);
                    isTankChanged=true;
                }
                isStructureChanged = true;
                updateClientData();
            }
            return tankCapacity>0;
        }
        if(disableTESR)return false;
        //Calculate everything for Client Render
        ArrayList<BlockCoord> spaceListForTESR = new ArrayList<>();
        for (short layer = 0; layer < maxLayer; layer++) {
            short finalLayer = layer;
            layerCapacity = 0;
            spaceListForTESR.removeIf(coord->coord.y == yCoord+ finalLayer -2);
            if (checkSink2(worldContainer, aClickedAt, aPlayer, aInventory, spaceListForTESR, utils.getRealX(mFacing, xCoord, 0, 2), utils.getRealZ(mFacing, zCoord, 0, 2), layer, checkRange)){
                layerLiquidCapacity.put(layer, layerCapacity * liquidAmountPerBlock);
                layeredTESRRenderSpace.put((short) (layer+yCoord), spaceListForTESR.stream().filter(coord->coord.y == finalLayer + yCoord).collect(Collectors.toList()));
            }
            else {
                if(layer == 0)return false;
                break;
            }
        }

        //Calculate Bounds
        layeredTESRRenderSpace.forEach((layer,list)-> list.forEach(blockCoord -> {
            boundForSink[0] = Math.min(boundForSink[0], blockCoord.x);
            boundForSink[2] = Math.min(boundForSink[2], blockCoord.z);
            boundForSink[3] = Math.max(boundForSink[3], blockCoord.x);
            boundForSink[5] = Math.max(boundForSink[5], blockCoord.z);
        }));
        boundForSink[1] = yCoord;
        boundForSink[4] = layeredTESRRenderSpace.size()+yCoord;

        mStructureOkay = true;
        isStructureChanged = false;
        return true;
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(!isServerSide())return 0;
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    final byte[] forX = {1, -1, 0, 0};
    final byte[] forZ = {0, 0, 1, -1};

    private long layerCapacity;
    public boolean checkSink2(AsyncStructureManager.WorldContainer worldContainer, ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory, List<BlockCoord> checkedAirList, int initX, int initZ, int layer, BoundingBox checkRange){
        Queue<BlockCoord> queue = new LinkedList<>();
        queue.add(new BlockCoord(initX, yCoord+layer,initZ));
        checkedAirList.add(new BlockCoord(initX, yCoord+layer,initZ));
        if (Arrays.stream(getAvailableTiles()).anyMatch(availTile ->IAsyncStructure.checkAndSetTarget(worldContainer,this, new ChunkCoordinates(initX, yCoord+layer, initZ), new TileDesc[]{ new TileDesc(availTile.aRegistryID, availTile.aRegistryMeta, availTile.aUsage, availTile.aDesign)}, false, aClickedAt, aPlayer, aInventory)))return false; //the start pos is a wall, Why you do that?
        //start pos is vaild, begin search.
        layerCapacity++;
        while (!queue.isEmpty()){
            BlockCoord coord = queue.poll();
            if(!checkRange.isCoordInBox(coord))return false;//Out Bound

            //check the block below is in sink || the below block is a valid wall
            if(!checkedAirList.contains(new BlockCoord(coord.x, yCoord+layer-1, coord.z)) && Arrays.stream(getAvailableTiles()).noneMatch(availTile -> IAsyncStructure.checkAndSetTarget(worldContainer, this, new ChunkCoordinates(coord.x, yCoord+layer-1, coord.z), new TileDesc[]{ new TileDesc(availTile.aRegistryID, availTile.aRegistryMeta, availTile.aUsage, availTile.aDesign)}, false, aClickedAt, aPlayer, aInventory))) return false;
            for (int i = 0; i < 4; i++) {
                BlockCoord coordNext = new BlockCoord(coord.x + forX[i], yCoord + layer, coord.z + forZ[i]);
                if(checkedAirList.contains(coordNext))continue;
                checkedAirList.add(coordNext);
                if (Arrays.stream(getAvailableTiles()).noneMatch(availTile ->IAsyncStructure.checkAndSetTarget(worldContainer,this, codeUtil.CCCoord2MCCoord(coordNext), new TileDesc[]{ new TileDesc(availTile.aRegistryID, availTile.aRegistryMeta, availTile.aUsage, availTile.aDesign)}, false, aClickedAt, aPlayer, aInventory))){
                    layerCapacity++;
                    queue.add(coordNext);
                }
            }
        }
        return true;
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

    @Override
    public TileDesc[] getTileDescs(int mapX, int mapY, int mapZ) {
        return new TileDesc[]{ new TileDesc(getRegistryID(mapX, mapY, mapZ), getBlockID(mapX, mapY, mapZ),getUsage(mapX, mapY, mapZ))};
    }

    public int getUsage(int mapX, int mapY, int mapZ){
        if(mapY==0)return MultiTileEntityMultiBlockPart.ONLY_FLUID;
        return MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
    }

    public int getBlockID(int checkX, int checkY, int checkZ){return wallID;}
    public boolean isIgnored(int checkX, int checkY, int checkZ){return false;}
    public short getRegistryID(int x,int y,int z){return g;}

    ChunkCoordinates lastFailedPos=null;
    UUID asyncTaskID = UUID.randomUUID();
    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        isStructureChanged=false;
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        if(!isServerSide()&& Blocks.stonebrick.equals(worldObj.getBlock(tX,tY-2,tZ)))disableTESR=true;
        if(!isServerSide() && disableTESR)return false;
        if(!isServerSide())mStructureOkay=false;//disable Client TESR to avoid Concurrent access to TESR data lists.
        if(AsyncStructureManager.getCheckState(asyncTaskID) == AsyncStructureManager.STATE_NOT_FOUND)AsyncStructureManager.addStructureComputeTask(new AsyncStructureManager.StructureComputeData(asyncTaskID,worldObj,this, aClickedAt, aPlayer, aInventory).setDesc(this.toString()+"/x:"+xCoord+"/y:"+yCoord+"/z:"+zCoord+"/isServerSide: "+isServerSide()));
        return false;
    }

    @Override
    public boolean asyncCheckStructure(AsyncStructureManager.WorldContainer worldContainer, ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        boolean isStructureComplete = false;
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (worldContainer.getBlock(tX,tY,tZ) == null) return mStructureOkay;
        lastFailedPos = checkMappedStructure(worldContainer, lastFailedPos, sizeX, sizeY, sizeZ,xMapOffset,0,0,false, aClickedAt, aPlayer, aInventory);
        if(lastFailedPos==null && checkSinkAndUpdateCapacity(worldContainer, aClickedAt, aPlayer, aInventory)) isStructureComplete = true;
        if(mStructureOkay != isStructureComplete) updateClientData();
        return isStructureComplete;
    }
    @Override
    public void onAsyncCheckStructureCompleted() {
        try {
            mStructureOkay = AsyncStructureManager.isStructureCompleted(asyncTaskID);
            FMLLog.log(Level.FATAL,"Successfully received Async Check:   "+mStructureOkay+this.toString()+"  /x:"+xCoord+"/y:"+yCoord+"/z:"+zCoord);
            AsyncStructureManager.removeCompletedTask(asyncTaskID);
        }catch (AsyncStructureManager.NotCompletedException e){}
    }
}
