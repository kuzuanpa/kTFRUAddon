/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.parts;

import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;


public class CommonPart extends MultiTileEntityMultiBlockPart {

    @Override
    public int getLightOpacity(){
        return mDesign==7?0:255;
    }
    @Override
    public String getTileEntityName(){
        return "kfru.multitileentity.multiblock.commonpart";
    }
}
