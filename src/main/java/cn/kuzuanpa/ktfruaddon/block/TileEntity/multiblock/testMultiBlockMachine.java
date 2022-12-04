package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock;

import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.LH;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
import gregapi.util.ST;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;

import static gregapi.data.CS.*;

public class testMultiBlockMachine extends TileEntityBase10MultiBlockMachine {
    MultiTileEntityRegistry gtRegistry = MultiTileEntityRegistry.getRegistry("gt.multitileentity");
    @Override
    public boolean checkStructure2() {
        int tX = getOffsetXN(mFacing, 2) - 2, tY = yCoord, tZ = getOffsetZN(mFacing, 2) - 2;
        if (worldObj.blockExists(tX - 2, tY, tZ - 2) && worldObj.blockExists(tX + 2, tY, tZ - 2) && worldObj.blockExists(tX - 2, tY, tZ + 2) && worldObj.blockExists(tX + 2, tY, tZ + 2)) {
            boolean tSuccess = T;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX, tY, tZ, 18002, ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 1, tY, tZ, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 2, tY, tZ, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 3, tY, tZ, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 4, tY, tZ, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX, tY, tZ + 1, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 1, tY, tZ + 1, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 2, tY, tZ + 1, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 3, tY, tZ + 1, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 4, tY, tZ + 1, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX, tY, tZ + 2, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 1, tY, tZ + 2, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 2, tY, tZ + 2, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 3, tY, tZ + 2, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 4, tY, tZ + 2, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX, tY, tZ + 3, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 1, tY, tZ + 3, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 2, tY, tZ + 3, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 3, tY, tZ + 3, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 4, tY, tZ + 3, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX, tY, tZ + 4, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 1, tY, tZ + 4, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 2, tY, tZ + 4, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 3, tY, tZ + 4, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, tX + 4, tY, tZ + 4, 18002,ST.id(gtRegistry.mBlock), 0, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID))
                tSuccess = F;
            return tSuccess;
        }
        return mStructureOkay;
    }

    static {
        LH.add("gt.tooltip.multiblock.bath.1", "5x5x2 of Stainless Steel Walls");
        LH.add("gt.tooltip.multiblock.bath.2", "Main Block centered on Side-Bottom and facing outwards");
        LH.add("gt.tooltip.multiblock.bath.3", "Input and Output at any Blocks");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.bath.1"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.bath.2"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.bath.3"));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        int tX = getOffsetXN(mFacing, 2), tY = yCoord, tZ = getOffsetZN(mFacing, 2);
        return aX >= tX - 2 && aY >= tY && aZ >= tZ - 2 && aX <= tX + 2 && aY <= tY  && aZ <= tZ + 2;
    }

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
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.bath";
    }
}