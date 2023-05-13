/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.tile.multiblock;

import cn.kuzuanpa.ktfruaddon.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.tile.SpecialPart.MultiBlockPartEnergyConsumer;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.base.TileEntityBaseMultiInputMachine;
import cn.kuzuanpa.ktfruaddon.tile.util.utils;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.LH;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.ST;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;

import static gregapi.data.CS.*;
public class maskAlignerUVScanning extends TileEntityBaseMultiInputMachine {
        public final short machineX = 3, machineY = 2, machineZ = 2;
        public final short xMapOffset = -1;
        public static final int[][][] blockIDMap = {{
                {30102,   0  , 30102},
                {30102, 31000, 30102},
        },{
                {30102, 30110, 30102},
                {30102, 30120, 30102},
        }};
    public int getCheckX(int Facing, int tX, int addX, int addZ) {
        int[] result = {0, 0, tX - addX, tX + addX, tX + addZ, tX - addZ, 0, 0};
        return result[Facing];
    }

    public int getCheckZ(int Facing, int tZ, int addX, int addZ) {
        int[] result = {0, 0, tZ + addZ, tZ - addZ, tZ + addX, tZ - addX, 0, 0};
        return result[Facing];
    }
    public short g = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);
    public short k = ST.id(MultiTileEntityRegistry.getRegistry("ktfru.multitileentity").mBlock);

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
    public boolean isSubSource(int blockID){
        return blockID == 31000;
    }
    public short getRegistryID(int x,int y,int z){return k;}

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
                            if (isSubSource(getBlockID(checkX,checkY,checkZ))) {
                                if (!utils.checkAndSetTargetEnergyConsumerPermitted(this, getCheckX(mFacing, tX, checkX, checkZ), tY + checkY, getCheckZ(mFacing, tZ, checkX, checkZ), getBlockID(checkX,checkY,checkZ), getRegistryID(checkX,checkY,checkZ), 0, getUsage(getBlockID(checkX,checkY,checkZ), getRegistryID(checkX, checkY, checkZ)))) tSuccess = isIgnored(checkX,checkY,checkZ);
                                if (tSuccess) this.addInputSubSource((MultiBlockPartEnergyConsumer) this.getTileEntity(getCheckX(mFacing, tX, checkX, checkZ), tY + checkY, getCheckZ(mFacing, tZ, checkX, checkZ)));
                            }
                            else if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, getCheckX(mFacing, tX, checkX, checkZ), tY + checkY, getCheckZ(mFacing, tZ, checkX, checkZ), getBlockID(checkX,checkY,checkZ), getRegistryID(checkX,checkY,checkZ), 0, getUsage(getBlockID(checkX,checkY,checkZ), getRegistryID(checkX,checkY,checkZ)))) tSuccess = isIgnored(checkX,checkY,checkZ);

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
        
    public int mRenderedRGBA = UNCOLORED;

    @Override
    public int getRenderPasses2(Block aBlock, boolean[] aShouldSideBeRendered) {
        return 1;
    }

    @Override
    public boolean setBlockBounds2(Block aBlock, int aRenderPass, boolean[] aShouldSideBeRendered) {
        if (mStructureOkay) {
            BoundingBox box = new BoundingBox(utils.getRealCoordD(mFacing,0.5,0.5,0.5,-1.501,-0.501,-0.501), utils.getRealCoordD(mFacing,0.5,0.5,0.5,1.501,1.501,1.501));
            box(aBlock, box.minX,box.minY,box.minZ,box.maxX,box.maxY,box.maxZ);
        }
        return T;
    }


    public static IIconContainer
            sTextureSides      = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/normal/sides"),
            sTextureFront       = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/normal/front"),
            sTextureTop         = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/normal/top"),
            sTextureBottom      = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/normal/bottom"),
            sTextureFrontActive       = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/active/front"),
            sTextureSidesSingle      = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/single/sides"),
            sTextureFrontSingle       = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/single/front"),
            sTextureTopSingle         = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/single/top"),
            sTextureBottomSingle      = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/single/bottom")
                    ;
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (mStructureOkay&&mActive) {
            return aSide == mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureFrontActive, mRGBa)):SIDE_TOP == aSide?BlockTextureMulti.get(BlockTextureDefault.get(sTextureTop, mRGBa)):SIDE_BOTTOM==aSide?BlockTextureMulti.get(BlockTextureDefault.get(sTextureBottom, mRGBa)): BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides, mRGBa));
        } else if (mStructureOkay) {
            return aSide == mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureFront, mRGBa)):SIDE_TOP == aSide?BlockTextureMulti.get(BlockTextureDefault.get(sTextureTop, mRGBa)):SIDE_BOTTOM==aSide?BlockTextureMulti.get(BlockTextureDefault.get(sTextureBottom, mRGBa)): BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides, mRGBa));
        }
        return aShouldSideBeRendered[aSide] ?aSide == mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureFrontSingle, mRGBa)):SIDE_TOP == aSide?BlockTextureMulti.get(BlockTextureDefault.get(sTextureTopSingle, mRGBa)):SIDE_BOTTOM==aSide?BlockTextureMulti.get(BlockTextureDefault.get(sTextureBottomSingle, mRGBa)): BlockTextureMulti.get(BlockTextureDefault.get(sTextureSidesSingle, mRGBa)) : null;
    } 
    @Override
        public String getTileEntityName() {
            return "ktfru.multitileentity.multiblock.maskaligner.uv";
        }
    }
