package cn.kuzuanpa.ktfruaddon.tile.multiblock.base.async;

import cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.IMultiBlockPart;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

public interface IAsyncStructure {

    default void onStructureComputeCompleted() {};

    boolean asyncCheckStructure(AsyncStructureManager.WorldContainer worldContainer);

    static boolean checkAndSetTarget(AsyncStructureManager.WorldContainer worldContainer,ITileEntityMultiBlockController aController, ChunkCoordinates coord, int aRegistryMeta, int aRegistryID, int aDesign, int aMode) {
        TileEntity tTileEntity = worldContainer.getTileEntity(coord.posX,coord.posY,coord.posZ);
        if (tTileEntity == aController) {
            return true;
        } else if (tTileEntity instanceof MultiTileEntityMultiBlockPart && ((MultiTileEntityMultiBlockPart) tTileEntity).getMultiTileEntityID() == aRegistryMeta && ((MultiTileEntityMultiBlockPart) tTileEntity).getMultiTileEntityRegistryID() == aRegistryID) {
            ITileEntityMultiBlockController tTarget = ((MultiTileEntityMultiBlockPart) tTileEntity).getTarget(false);
            if (tTarget != aController && tTarget != null) return false;
            else {
                ((MultiTileEntityMultiBlockPart) tTileEntity).setTarget(aController, aDesign, aMode);
                return true;
            }
        } else if (tTileEntity instanceof IMultiBlockPart && ((IMultiBlockPart) tTileEntity).getMultiTileEntityID() == aRegistryMeta && ((IMultiBlockPart) tTileEntity).getMultiTileEntityRegistryID() == aRegistryID) {
            ITileEntityMultiBlockController tTarget = ((IMultiBlockPart) tTileEntity).getTarget(false);
            if (tTarget != aController && tTarget != null) return false;
            else {
                ((IMultiBlockPart) tTileEntity).setTarget(aController, aDesign, aMode);
                return true;
            }
        } else return false;
    }
}
