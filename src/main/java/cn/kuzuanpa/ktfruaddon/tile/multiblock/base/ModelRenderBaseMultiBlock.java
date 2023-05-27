/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.multiblock.base;

import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;

public abstract class ModelRenderBaseMultiBlock extends TileEntityBase10MultiBlockMachine {
    public boolean usingModelRender = false;
    public void setPartsTransparent(){
        usingModelRender=true;
        if (!this.checkStructure(true)){
            usingModelRender=false;
            this.checkStructure(true);
        }
    }
    @Override
    public boolean checkStructure2(){
        return checkStructure3(usingModelRender);
    }
    public abstract boolean checkStructure3(boolean shouldPartsTransparent);

}
