/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */


package cn.kuzuanpa.ktfruaddon.api.tile.base;

import gregapi.data.CS;
import gregapi.render.ITexture;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ChunkCoordinates;

import static gregapi.data.CS.T;

public abstract class ModelRenderBaseMultiBlockMachine extends TileEntityBase10MultiBlockMachine {
    public boolean usingModelRender = false;
    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory){
        if (!checkStructure3(true, aClickedAt, aPlayer, aInventory)){
            resetParts();
            return false;
        }return true;
    }
    @Override public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }

    @Override public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return mStructureOkay?null:super.getTexture2(aBlock, aRenderPass, aSide, aShouldSideBeRendered);
    }
    public abstract boolean checkStructure3(boolean shouldPartsTransparent, ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory);

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
