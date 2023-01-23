package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock;

import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import net.minecraft.tileentity.TileEntity;

public  class utilsMultiBlock {
    public utilsMultiBlock() {
    }
@SuppressWarnings("No belongings check, donot apply to blocks in corner")
    public static boolean checkAndSetTargetEnergyConsumerPermitted(ITileEntityMultiBlockController aController, int aX, int aY, int aZ, int aRegistryMeta, int aRegistryID, int aDesign, int aMode) {
        TileEntity tTileEntity = aController.getTileEntity(aX, aY, aZ);
        if (tTileEntity == null) return true;
        return ((MultiTileEntityBasicMachine) tTileEntity).getMultiTileEntityID() != aRegistryMeta || ((MultiTileEntityBasicMachine) tTileEntity).getMultiTileEntityRegistryID() != aRegistryID;
        }
    }

