/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.storage;

import cn.kuzuanpa.ktfruaddon.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.code.codeUtil;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.IMappedStructure;
import cn.kuzuanpa.ktfruaddon.tile.util.utils;
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
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import org.apache.logging.log4j.Level;

import java.util.*;
import java.util.stream.Collectors;

import static gregapi.data.CS.*;

public class LiquidBattery extends MultiAdaptiveOutputBattery implements IMultiBlockFluidHandler, IMultiTileEntity.IMTE_SyncDataByteArray, IMappedStructure {
    public short maxLayer = 16, maxRange=32, liquidYLevelRender, wallID;
    public FluidTankGT mTank = new FluidTankGT();
    final short k = ST.id(MultiTileEntityRegistry.getRegistry("ktfru.multitileentity").mBlock);
    final short g = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);
    public boolean isTankChanged = false;
    public final HashMap<Short,Long> layerLiquidCapacity = new HashMap<>();

    public ArrayList<BlockCoord> spaceListForTESR = new ArrayList<>();
    public int[] boundForSink = new int[]{Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE};
    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE));

        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if(aNBT.hasKey(NBT_DESIGN)) wallID = aNBT.getShort(NBT_DESIGN);
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
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if(!aIsServerSide && aTimer <= 1) checkSink(true);
        if(!aIsServerSide)return;

        long tankCapacity = 0;
        for (short i = 0; i < maxLayer; i++) {
            Long layerCapacity = layerLiquidCapacity.get(i);
            if (layerCapacity == null || layerCapacity <= 0) break;
            tankCapacity += layerCapacity;
        }
        mTank.setCapacity(tankCapacity);

        if(FL.move(mTank, getAdjacentTank(SIDE_BOTTOM))>0) {
            isTankChanged=true;
            if(!mTank.has()) updateClientData();
        }
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
        updateClientData();

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
            }else mCapacity += layerCapacity*(i + 1)* fluidDensity;
            remainFluidAmount-=layerCapacity;
        }
    }

    public boolean checkSink(boolean isClient){
        final BlockCoord StartPoi = codeUtil.MCCoord2CCCoord(utils.getRealCoord(mFacing, xCoord, yCoord, zCoord, maxRange, -1, 2*maxRange));
        final BlockCoord EndPoi = codeUtil.MCCoord2CCCoord(utils.getRealCoord(mFacing, xCoord, yCoord, zCoord, -maxRange, maxLayer, 0));
        BoundingBox checkRange = new BoundingBox(StartPoi, EndPoi);
        if(isClient) {
            //Calculate everything for Client Render
            spaceListForTESR.clear();
            for (short layer = 0; layer < maxLayer; layer++) {
                layerCapacity = 0;
                if(checkSink2(spaceListForTESR,utils.getRealX(mFacing,xCoord,0,2), utils.getRealZ(mFacing,zCoord,0,2), layer, checkRange))layerLiquidCapacity.put(layer,layerCapacity*4000);
                else {
                    if(layer == 0){
                        spaceListForTESR.clear();
                        break;
                    }
                    int fLayer = layer;
                    //remove spaces just leaked on top layer
                    List<BlockCoord> spilledSpace = spaceListForTESR.stream().filter(coord-> coord.y == yCoord + fLayer).collect(Collectors.toList());
                    spaceListForTESR.removeAll(spilledSpace);
                    //Calculate Bounds
                    for (BlockCoord blockCoord : spaceListForTESR) {
                        boundForSink[0] = Math.min(boundForSink[0],blockCoord.x);
                        boundForSink[1] = Math.min(boundForSink[1],blockCoord.y);
                        boundForSink[2] = Math.min(boundForSink[2],blockCoord.z);
                        boundForSink[3] = Math.max(boundForSink[3],blockCoord.x);
                        boundForSink[4] = Math.max(boundForSink[4],blockCoord.y);
                        boundForSink[5] = Math.max(boundForSink[5],blockCoord.z);
                    }
                    //only keep top space for render
                    List<BlockCoord> coveredSpace = spaceListForTESR.stream().filter(coord-> spaceListForTESR.stream().anyMatch(coord1 -> coord1.equals(new BlockCoord(coord.x,coord.y+1,coord.z)))).collect(Collectors.toList());
                    spaceListForTESR.removeAll(coveredSpace);
                    break;
                }
            }
            if(!spaceListForTESR.isEmpty())mStructureOkay=true;
            return true;
        }
        //Server side: just check structure and save capacity for every layer
        ArrayList<BlockCoord> checkedBlock = new ArrayList<>();
        for (short layer = 0; layer < maxLayer; layer++) {
            layerCapacity=0;
            if(checkSink2(checkedBlock,utils.getRealX(mFacing,xCoord,0,2), utils.getRealZ(mFacing,zCoord,0,2), layer, checkRange))layerLiquidCapacity.put(layer,layerCapacity*4000);
            else break;
        }
        return !layerLiquidCapacity.isEmpty();
    }

    private long layerCapacity;
    public boolean checkSink2(List<BlockCoord> checkedBlock,int x,int z, int layer, BoundingBox checkRange){
        //don't allow shapes like hopper, and check floor when layer=0
        if(!checkedBlock.contains(new BlockCoord(x, yCoord+layer-1, z)) && (layer > 0 || Arrays.stream(getAvailableTiles()).noneMatch(availTile -> utils.checkAndSetTarget(this, x, yCoord+layer-1, z, availTile.aRegistryMeta, availTile.aRegistryID, availTile.aDesign, availTile.aUsage)))) return false;
        //check this layer
        for (int i = 0; i < 5; i++) {
            byte[] forX = {0, 1, -1, 0, 0};
            byte[] forZ = {0, 0, 0, 1, -1};
            BlockCoord checkingCoord = new BlockCoord(x + forX[i], yCoord + layer, z + forZ[i]);

            if (Arrays.stream(getAvailableTiles()).noneMatch(availTile -> utils.checkAndSetTarget(this, checkingCoord.x, checkingCoord.y, checkingCoord.z, availTile.aRegistryMeta, availTile.aRegistryID, availTile.aDesign, availTile.aUsage))) {
                if (!checkRange.isCoordInBox(checkingCoord)) return false;//Out Bound
                if (!checkedBlock.contains(checkingCoord)) {//Check next target
                    checkedBlock.add(checkingCoord);
                    layerCapacity += 1;
                    if(!checkSink2(checkedBlock,checkingCoord.x,checkingCoord.z,layer, checkRange)){//This layer failed, return
                        layerCapacity = -1;
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.storage.liquid";
    }

    public utils.GTTileEntity[] getAvailableTiles() {
        return new utils.GTTileEntity[]{
                new utils.GTTileEntity(k,31033,0, MultiTileEntityMultiBlockPart.ONLY_FLUID_IN),
                new utils.GTTileEntity(k,31034,0, MultiTileEntityMultiBlockPart.ONLY_FLUID_IN),
                new utils.GTTileEntity(k,31035,0, MultiTileEntityMultiBlockPart.ONLY_FLUID_IN),
                new utils.GTTileEntity(k,31036,0, MultiTileEntityMultiBlockPart.ONLY_FLUID_IN),
        };
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return true;
    }

    protected IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {isTankChanged=true; return mTank;}
    protected IFluidTank getFluidTankDrainable2(byte aSide, FluidStack aFluidToDrain) {isTankChanged=true; return mTank;}
    protected IFluidTank[] getFluidTanks2(byte aSide) {isTankChanged=true;return mTank.AS_ARRAY;}

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
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(boundForSink[0],boundForSink[1],boundForSink[2],boundForSink[3],boundForSink[4],boundForSink[5]);
    }

    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        return aSendAll ? mTank.getFluid() == null ?
                getClientDataPacketByteArray(aSendAll, (byte) UT.Code.getR(mRGBa), (byte)UT.Code.getG(mRGBa), (byte)UT.Code.getB(mRGBa), getVisualData(), getDirectionData())
                :
                getClientDataPacketByteArray(aSendAll, (byte) UT.Code.getR(mRGBa), (byte)UT.Code.getG(mRGBa), (byte)UT.Code.getB(mRGBa), getVisualData(), getDirectionData(),
                        UT.Code.toByteS(liquidYLevelRender,0),UT.Code.toByteS(liquidYLevelRender,1),
                        UT.Code.toByteI(mTank.getFluid().getFluidID(),0), UT.Code.toByteI(mTank.getFluid().getFluidID(),1), UT.Code.toByteI(mTank.getFluid().getFluidID(),2), UT.Code.toByteI(mTank.getFluid().getFluidID(),3)
        ) : getClientDataPacketByte(aSendAll, getVisualData());
    }

    public boolean loggedNullFluidErr = false;
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler){
        if(aData.length == 11){
            liquidYLevelRender = UT.Code.combine(aData[5],aData[6]);
            Fluid fluid = FluidRegistry.getFluid(UT.Code.combine(aData[7],aData[8],aData[9],aData[10]));
            if(fluid == null) {
                if(!loggedNullFluidErr)FMLLog.log(Level.ERROR,"Null Fluid Found for Liquid Battery, You may have mod difference between Server and Client");
                loggedNullFluidErr=true;
            }
            else mTank.fill(new FluidStack(fluid,1));
        }
        return super.receiveDataByteArray(aData, aNetworkHandler);
    }

    public final static short sizeX = 3, sizeY = 2, sizeZ = 1;
    public final static short xMapOffset = -1;

    //change value there to set usage of every block.

    @Override
    public int getUsage(int mapX,int mapY,int mapZ, int registryID, int blockID){
        if(mapY==0)return MultiTileEntityMultiBlockPart.ONLY_FLUID;
        return MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
    }

    @Override
    public int getDesign(int mapX, int mapY, int mapZ, int blockId, int registryID) {
        return 0;
    }

    public int getBlockID(int checkX, int checkY, int checkZ){return wallID;}
    public boolean isIgnored(int checkX, int checkY, int checkZ){return false;}
    public short getRegistryID(int x,int y,int z){return g;}

    @Override
    public List<ChunkCoordinates> getComputeNodesCoordList() {return null;}
    ChunkCoordinates lastFailedPos=null;
    @Override
    public boolean checkStructure2() {
        boolean isStructureComplete = false;
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = checkMappedStructure(null, sizeX, sizeY, sizeZ,xMapOffset,0,0);
        if(lastFailedPos==null && checkSink(false)) isStructureComplete = true;
        if(mStructureOkay != isStructureComplete) updateClientData();
        return isStructureComplete;
    }
}
