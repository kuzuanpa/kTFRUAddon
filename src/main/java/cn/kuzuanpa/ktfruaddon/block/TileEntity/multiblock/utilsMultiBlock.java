/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock;

import cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.specialPart.MultiTileEntityMultiBlockPartEnergyConsumer;
import cpw.mods.fml.common.FMLLog;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import net.minecraft.tileentity.TileEntity;

public class utilsMultiBlock {
    public utilsMultiBlock() {
    }

    public static boolean checkAndSetTarget(ITileEntityMultiBlockController aController, int aX, int aY, int aZ, int aRegistryMeta, int aRegistryID, int aDesign, int aMode) {
        TileEntity tTileEntity = aController.getTileEntity(aX, aY, aZ);
        if (tTileEntity == aController) {
            return true;
        } else if (tTileEntity instanceof MultiTileEntityMultiBlockPart && ((MultiTileEntityMultiBlockPart) tTileEntity).getMultiTileEntityID() == aRegistryMeta && ((MultiTileEntityMultiBlockPart) tTileEntity).getMultiTileEntityRegistryID() == aRegistryID) {
            ITileEntityMultiBlockController tTarget = ((MultiTileEntityMultiBlockPart) tTileEntity).getTarget(false);
            if (tTarget != aController && tTarget != null) {
                return false;
            } else {
                ((MultiTileEntityMultiBlockPart) tTileEntity).setTarget(aController, aDesign, aMode);
                return true;
            }
        } else {
            return false;
        }
    }

    public static boolean checkAndSetTargetEnergyConsumerPermitted(ITileEntityMultiBlockController aController, int aX, int aY, int aZ, int aRegistryMeta, int aRegistryID, int aDesign, int aMode) {
        TileEntity tTileEntity = aController.getTileEntity(aX, aY, aZ);
        if (tTileEntity == null) return false;
        if (tTileEntity == aController) return true;
        else if (tTileEntity instanceof MultiTileEntityMultiBlockPartEnergyConsumer && ((MultiTileEntityMultiBlockPartEnergyConsumer) tTileEntity).getMultiTileEntityID() == aRegistryMeta && ((MultiTileEntityMultiBlockPartEnergyConsumer) tTileEntity).getMultiTileEntityRegistryID() == aRegistryID) {
            ITileEntityMultiBlockController tTarget = ((MultiTileEntityMultiBlockPartEnergyConsumer) tTileEntity).getTarget(false);
            if (tTarget != aController && tTarget != null) return false;
            else {
                ((MultiTileEntityMultiBlockPartEnergyConsumer) tTileEntity).setTarget(aController, aDesign, aMode);
                return true;
            }
        } else return false;

    }
}

