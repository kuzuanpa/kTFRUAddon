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
        return mDesign==1?255:0;
    }
    //When this part be hidden, This will make adjoining block's side rendering properly.
    @Override public boolean isSurfaceOpaque2       (byte aSide) {return mDesign!=1;}
    @Override
    public String getTileEntityName(){
        return "kfru.multitileentity.multiblock.commonpart";
    }
}
