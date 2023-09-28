/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */


package cn.kuzuanpa.ktfruaddon.tile.multiblock;

import gregapi.tileentity.multiblocks.TileEntityBase11MultiBlockConverter;
import net.minecraft.tileentity.TileEntity;

import static gregapi.data.CS.SIDE_TOP;

public class SunBoiler extends TileEntityBase11MultiBlockConverter {
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.sunboiler";
    }

    @Override
    public TileEntity getEmittingTileEntity() {return null;}

    @Override
    public byte getEmittingSide() {return SIDE_TOP;}

    @Override
    public boolean checkStructure2() {
        return false;
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {return false;}
}
