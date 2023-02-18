/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.base;

import cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.specialPart.MultiTileEntityMultiBlockPartEnergyConsumer;
import cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.utilsMultiBlock;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.ST;

import static gregapi.data.CS.F;
import static gregapi.data.CS.T;

public abstract class TileEntityBase12MultiInputMachineComplex extends TileEntityBase11MultiInputMachine{
    public final static short machineX = 0, machineY = 0, machineZ = 0;
    public final static short xMapOffset = 0, zMapOffset=0;
    public int getCheckX(int Facing, int tX, int addX, int addZ) {
        int[] result = {0, 0, tX - addX, tX + addX, tX + addZ, tX - addZ, 0, 0};
        return result[Facing];
    }

    public int getCheckZ(int Facing, int tZ, int addX, int addZ) {
        int[] result = {0, 0, tZ + addZ, tZ - addZ, tZ + addX, tZ - addX, 0, 0};
        return result[Facing];
    }
    //change value there to set usage of every block.
    public int getUsage(int blockID ,short registryID){
        return MultiTileEntityMultiBlockPart.EVERYTHING;
    }
    public short g = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);
    public short k = ST.id(MultiTileEntityRegistry.getRegistry("ktfru.multitileentity").mBlock);

    public short getRegistryID(int checkX, int checkY, int checkZ){
        return g;
    }
    public int getBlockID(int checkX, int checkY, int checkZ){
        return 0;
    }
    public  boolean isIgnored(int checkX, int checkY, int checkZ){
        return false;
    }
    public boolean isSubSource(int blockID){
        return false;
    }

    @Override
    public boolean checkStructure2() {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (worldObj.blockExists(tX, tY, tZ)) {
            boolean tSuccess = T;
            if (getFacing() == (short) 2) {
                tZ += zMapOffset;
                tX -= xMapOffset;
            } else if (getFacing() == (short) 3) {
                tZ -= zMapOffset;
                tX += xMapOffset;
            } else if (getFacing() == (short) 4) {
                tX += zMapOffset;
                tZ += xMapOffset;
            } else if (getFacing() == (short) 5) {
                tX -= zMapOffset;
                tZ -= xMapOffset;
            } else {
                tSuccess = F;
            }
            int checkX, checkY, checkZ;
            for (checkY  = 0; checkY < machineY&&tSuccess; checkY++) {
                for (checkZ = 0; checkZ < machineZ&&tSuccess; checkZ++) {
                    for (checkX = 0; checkX < machineX&&tSuccess; checkX++) {
                        if (isSubSource(getBlockID(checkX,checkY,checkZ))) {
                            if (!utilsMultiBlock.checkAndSetTargetEnergyConsumerPermitted(this, getCheckX(mFacing, tX, checkX, checkZ), tY + checkY, getCheckZ(mFacing, tZ, checkX, checkZ), getBlockID(checkX,checkY,checkZ), getRegistryID(checkX,checkY,checkZ), 0, getUsage(getBlockID(checkX,checkY,checkZ), getRegistryID(checkX, checkY, checkZ)))) tSuccess = isIgnored(checkX,checkY,checkZ);
                            if (tSuccess) this.addInputSubSource((MultiTileEntityMultiBlockPartEnergyConsumer) this.getTileEntity(getCheckX(mFacing, tX, checkX, checkZ), tY + checkY, getCheckZ(mFacing, tZ, checkX, checkZ)));
                        }
                        else if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, getCheckX(mFacing, tX, checkX, checkZ), tY + checkY, getCheckZ(mFacing, tZ, checkX, checkZ), getBlockID(checkX,checkY,checkZ), getRegistryID(checkX,checkY,checkZ), 0, getUsage(getBlockID(checkX,checkY,checkZ), getRegistryID(checkX,checkY,checkZ)))) tSuccess = isIgnored(checkX,checkY,checkZ);
                        //FMLLog.log(Level.FATAL, "Checkpos" + mFacing + "/" + tX + "/" + tY + "/" + tZ + "/" + getCheckX(mFacing,tX,checkX,checkZ) + "/" + checkY + "/" +  getCheckZ(mFacing,tZ,checkX,checkZ)  + "/" + blockIDMap[checkY][checkZ][checkX]);

                    }
                }
            }

            // FMLLog.log(Level.FATAL, "CheckposState"+tSuccess);
            return tSuccess;

        }
        return mStructureOkay;
    }
}
