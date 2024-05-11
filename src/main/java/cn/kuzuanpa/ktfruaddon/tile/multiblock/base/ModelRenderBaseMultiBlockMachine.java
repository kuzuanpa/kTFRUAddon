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

import gregapi.data.CS;
import gregapi.render.ITexture;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
import net.minecraft.block.Block;

import static gregapi.data.CS.T;

public abstract class ModelRenderBaseMultiBlockMachine extends TileEntityBase10MultiBlockMachine {
    public boolean usingModelRender = false;
    @Override
    public boolean checkStructure2(){
        if (!checkStructure3(true)){
            resetParts();
            return false;
        }return true;
    }

    @Override public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return mStructureOkay?null:super.getTexture2(aBlock, aRenderPass, aSide, aShouldSideBeRendered);
    }
    public abstract boolean checkStructure3(boolean shouldPartsTransparent);

    @Override
    public boolean breakBlock() {
        setStateOnOff(T);
        CS.GarbageGT.trash(mTanksInput);
        CS.GarbageGT.trash(mTanksOutput);
        CS.GarbageGT.trash(mOutputItems);
        CS.GarbageGT.trash(mOutputFluids);
        breakBlock2();
        resetParts();
        return super.breakBlock();
    }
    public void breakBlock2(){}
    public abstract void resetParts();

}
