/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock;
import cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.base.TileEntityBase11MultiInputMachine;
import cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.specialPart.MultiTileEntityMultiBlockPartEnergyConsumer;
import cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.util.utils;
import gregapi.data.LH;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;

import static gregapi.data.CS.*;
public class maskAlignerUV extends TileEntityBase11MultiInputMachine {
        public final short machineX = 3, machineY = 2, machineZ = 2;
        public final short xMapOffset = -1;
        public static final int[][][] blockIDMap = {{
                {30102,   0  , 30102},
                {30102, 31000, 30102},
        },{
                {30102, 30110, 30102},
                {30102, 30120, 30102},
        }};
        short k = getMultiTileEntityRegistryID();
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
            if (blockID == 30110&&registryID==k) {
                return MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
            }
            return MultiTileEntityMultiBlockPart.EVERYTHING;
        }

        public int getBlockID(int checkX, int checkY, int checkZ){
            return blockIDMap[checkY][checkZ][checkX];
        }
        public  boolean isIgnored(int checkX, int checkY, int checkZ){
            return false;
        }
        @Override
        public boolean checkStructure2() {
            int tX = xCoord, tY = yCoord, tZ = zCoord;
            if (worldObj.blockExists(tX, tY, tZ)) {
                boolean tSuccess = T;
                if (getFacing() == (short) 2) {
                    tX -= xMapOffset;
                } else if (getFacing() == (short) 3) {
                    tX += xMapOffset;
                } else if (getFacing() == (short) 4) {
                    tZ += xMapOffset;
                } else if (getFacing() == (short) 5) {
                    tZ -= xMapOffset;
                } else {
                    tSuccess = F;
                }
                int checkX, checkY, checkZ;
                for (checkY  = 0; checkY < machineY&&tSuccess; checkY++) {
                    for (checkZ = 0; checkZ < machineZ&&tSuccess; checkZ++) {
                        for (checkX = 0; checkX < machineX&&tSuccess; checkX++) {
                            if (getBlockID(checkX,checkY,checkZ) == 31000) {
                                if (!utils.checkAndSetTargetEnergyConsumerPermitted(this, getCheckX(mFacing, tX, checkX, checkZ), tY + checkY, getCheckZ(mFacing, tZ, checkX, checkZ), getBlockID(checkX,checkY,checkZ), getMultiTileEntityRegistryID(), 0, getUsage(getBlockID(checkX,checkY,checkZ), k))) tSuccess = isIgnored(checkX,checkY,checkZ);
                                if (tSuccess) this.addInputSubSource((MultiTileEntityMultiBlockPartEnergyConsumer) this.getTileEntity(getCheckX(mFacing, tX, checkX, checkZ), tY + checkY, getCheckZ(mFacing, tZ, checkX, checkZ)));
                            }
                            else if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, getCheckX(mFacing, tX, checkX, checkZ), tY + checkY, getCheckZ(mFacing, tZ, checkX, checkZ), getBlockID(checkX,checkY,checkZ), getMultiTileEntityRegistryID(), 0, getUsage(getBlockID(checkX,checkY,checkZ), k))) tSuccess = isIgnored(checkX,checkY,checkZ);
                            //FMLLog.log(Level.FATAL, "Checkpos" + mFacing + "/" + tX + "/" + tY + "/" + tZ + "/" + getCheckX(mFacing,tX,checkX,checkZ) + "/" + checkY + "/" +  getCheckZ(mFacing,tZ,checkX,checkZ)  + "/" + blockIDMap[checkY][checkZ][checkX]);

                        }
                    }
                }

               // FMLLog.log(Level.FATAL, "CheckposState"+tSuccess);
                return tSuccess;

            }
            return mStructureOkay;
        }

        //这是设置主方块的物品提示
        //controls tooltip of controller block
        static {
            LH.add("gt.tooltip.multiblock.maskaligner.uv.1", "2 set of 2x2x1 Al Wall. A 1m gap between them.");
            LH.add("gt.tooltip.multiblock.maskaligner.uv.2", "Main Block facing outwards, in side-bottom of the gap");
            LH.add("gt.tooltip.multiblock.maskaligner.uv.3", "Light Module is in top of Main Block, Energy Module is behind the Main Block.");
            LH.add("gt.tooltip.multiblock.maskaligner.uv.4", "The left 1 block space is IO Manager.");
            LH.add("gt.tooltip.multiblock.maskaligner.uv.5", "Input LU from upside of Light Module, Input EU from anyside of Energy Module.");
            LH.add("gt.tooltip.multiblock.maskaligner.uv.6", "Item and fluid inputs from anyblock in upside, output from MainBlock in bottom.");
        }

        @Override
        public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
            aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
            aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.maskaligner.uv.1"));
            aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.maskaligner.uv.2"));
            aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.maskaligner.uv.3"));
            aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.maskaligner.uv.4"));
            aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.maskaligner.uv.5"));
            aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.maskaligner.uv.6"));
            super.addToolTips(aList, aStack, aF3_H);
        }
        //这里设置该机器的内部区域
        //controls areas inside the machine
        @Override
        public boolean isInsideStructure(int aX, int aY, int aZ) {
            //FMLLog.log(Level.FATAL,"a"+(xCoord-(SIDE_X_NEG == mFacing ? 0 : SIDE_X_POS == mFacing ? 3 : machineX)));
            return aX >= xCoord - (SIDE_X_NEG == mFacing ? 0 : SIDE_X_POS == mFacing ? 3 : machineX) &&
                    aY >= yCoord - (SIDE_Y_NEG == mFacing ? 0 : SIDE_Y_POS == mFacing ? 3 : machineY) &&
                    aZ >= zCoord - (SIDE_Z_NEG == mFacing ? 0 : SIDE_Z_POS == mFacing ? 3 : machineZ) &&
                    aX <= xCoord + (SIDE_X_POS == mFacing ? 0 : SIDE_X_NEG == mFacing ? 3 : machineX) &&
                    aY <= yCoord + (SIDE_Y_POS == mFacing ? 0 : SIDE_Y_NEG == mFacing ? 3 : machineX) &&
                    aZ <= zCoord + (SIDE_Z_POS == mFacing ? 0 : SIDE_Z_NEG == mFacing ? 3 : machineZ);
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
            return getAdjacentInventory(SIDE_UP);
        }

        @Override
        public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte aSide) {
            return getAdjacentTank(SIDE_UP);
        }
        //这里填写多方块结构的名称
        @Override
        public String getTileEntityName() {
            return "ktfru.multitileentity.multiblock.maskaligner.uv";
        }
    }
