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
import net.minecraft.init.Blocks;
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

public class LiquidBattery extends MultiAdaptiveOutputBattery implements IMultiBlockFluidHandler, IMultiTileEntity.IMTE_SyncDataByteArray, IMappedStructure {
    public short maxLayer = 16, maxRange=32, liquidYLevelRender=0, wallID=0, oldYLevel=0;
    public FluidTankGT mTank = new FluidTankGT();
    final short k = ST.id(MultiTileEntityRegistry.getRegistry("ktfru.multitileentity").mBlock);
    final short g = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);
    public boolean isTankChanged = false, isStructureChanged =false, isStoredEnergyChanged=false;
    public final HashMap<Short,Long> layerLiquidCapacity = new HashMap<>();
    public static final int liquidAmountPerBlock = 8000;
    public final ArrayList<BlockCoord> spaceListForTESR = new ArrayList<>();
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
    public boolean onTickCheck(long aTimer) {
        boolean result = super.onTickCheck(aTimer)|| isTankChanged || isStoredEnergyChanged;
        isTankChanged=false;
        isStoredEnergyChanged=false;
        return result;
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);

        if(!aIsServerSide && aTimer>5 && isStructureChanged) checkSinkAndUpdateCapacity();
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

    public boolean checkSinkAndUpdateCapacity(){
        final BlockCoord StartPoi = codeUtil.MCCoord2CCCoord(utils.getRealCoord(mFacing, xCoord, yCoord, zCoord, maxRange, -1, 2*maxRange));
        final BlockCoord EndPoi = codeUtil.MCCoord2CCCoord(utils.getRealCoord(mFacing, xCoord, yCoord, zCoord, -maxRange, maxLayer, 0));
        BoundingBox checkRange = new BoundingBox(StartPoi, EndPoi);

        if (isServerSide()) {
            ArrayList<BlockCoord> checkedBlock = new ArrayList<>();
            long tankCapacityOld = mTank.capacity();
            long tankCapacity = 0;
            for (short layer = 0; layer < maxLayer; layer++) {
                layerCapacity = 0;
                if (checkSink2(checkedBlock, utils.getRealX(mFacing, xCoord, 0, 2), utils.getRealZ(mFacing, zCoord, 0, 2), layer, checkRange)) {
                    tankCapacity += layerCapacity * liquidAmountPerBlock;
                    layerLiquidCapacity.put(layer, layerCapacity * liquidAmountPerBlock);
                } else break;
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

        //Calculate everything for Client Render
        spaceListForTESR.clear();
        for (short layer = 0; layer < maxLayer; layer++) {
            layerCapacity = 0;
            if (checkSink2(spaceListForTESR, utils.getRealX(mFacing, xCoord, 0, 2), utils.getRealZ(mFacing, zCoord, 0, 2), layer, checkRange))layerLiquidCapacity.put(layer, layerCapacity * liquidAmountPerBlock);
            else {
                if (layer == 0) {
                    spaceListForTESR.clear();
                    break;
                }
                int fLayer = layer;
                //remove spaces just leaked on top layer
                List<BlockCoord> spilledSpace = spaceListForTESR.stream().filter(coord -> coord.y == yCoord + fLayer).collect(Collectors.toList());
                spaceListForTESR.removeAll(spilledSpace);
                break;
            }
        }

        //Calculate Bounds
        for (BlockCoord blockCoord : spaceListForTESR) {
            boundForSink[0] = Math.min(boundForSink[0], blockCoord.x);
            boundForSink[1] = Math.min(boundForSink[1], blockCoord.y);
            boundForSink[2] = Math.min(boundForSink[2], blockCoord.z);
            boundForSink[3] = Math.max(boundForSink[3], blockCoord.x);
            boundForSink[4] = Math.max(boundForSink[4], blockCoord.y);
            boundForSink[5] = Math.max(boundForSink[5], blockCoord.z);
        }
        //only keep top space for render
        List<BlockCoord> coveredSpace = spaceListForTESR.stream().filter(coord -> spaceListForTESR.stream().anyMatch(coord1 -> coord1.equals(new BlockCoord(coord.x, coord.y + 1, coord.z)))).collect(Collectors.toList());
        spaceListForTESR.removeAll(coveredSpace);

        if (!spaceListForTESR.isEmpty()) mStructureOkay = true;
        isStructureChanged = false;
        return true;
    }

    private long layerCapacity;
    public boolean checkSink2(List<BlockCoord> checkedAirList,int x,int z, int layer, BoundingBox checkRange){
        if (!checkRange.isCoordInBox(new BlockCoord(x,yCoord+layer,z))) return false;//Out Bound
        //don't allow shapes like hopper, and check floor when layer=0
        if(!checkedAirList.contains(new BlockCoord(x, yCoord+layer-1, z)) && Arrays.stream(getAvailableTiles()).noneMatch(availTile -> utils.checkAndSetTarget(this, x, yCoord+layer-1, z, availTile.aRegistryMeta, availTile.aRegistryID, availTile.aDesign, availTile.aUsage))) return false;
        //check this block
        if (Arrays.stream(getAvailableTiles()).anyMatch(availTile -> utils.checkAndSetTarget(this, x , yCoord + layer, z, availTile.aRegistryMeta, availTile.aRegistryID, availTile.aDesign, availTile.aUsage))){return true;}
        //If this block isn't valid, add to checked Air list
        layerCapacity += 1;
        checkedAirList.add(new BlockCoord(x,yCoord+layer,z));
        //check blocks around
        final byte[] forX = {1, -1, 0, 0};
        final byte[] forZ = {0, 0, 1, -1};
        boolean result = true;
        for (int i = 0; i < 4; i++) {
            BlockCoord coord = new BlockCoord(x + forX[i], yCoord + layer, z + forZ[i]);
            if(checkedAirList.contains(coord))continue;
            result = result&&checkSink2(checkedAirList,coord.x,coord.z,layer, checkRange);
        }
        return result;
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

    protected IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {
        isTankChanged =true; return mTank;}
    protected IFluidTank getFluidTankDrainable2(byte aSide, FluidStack aFluidToDrain) {
        isTankChanged =true; return mTank;}
    protected IFluidTank[] getFluidTanks2(byte aSide) {
        isTankChanged =true;return mTank.AS_ARRAY;}

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

    public boolean loggedNullFluidErr = false;
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler){
        Fluid fluid = null;
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
            if(aData[7]==1) checkSinkAndUpdateCapacity();
            return super.receiveDataByte(aData[0],aNetworkHandler);
        }else if (aData.length == 5) {
            super.receiveDataByteArray(aData, aNetworkHandler);
            checkSinkAndUpdateCapacity();
            return true;
        }else return super.receiveDataByte(aData[0],aNetworkHandler);
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
        if(lastFailedPos==null && checkSinkAndUpdateCapacity()) isStructureComplete = true;
        if(mStructureOkay != isStructureComplete) updateClientData();
        return isStructureComplete;
    }
}
