/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

package cn.kuzuanpa.ktfruaddon.tile.multiblock.parts;

import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.ITexture;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

import static gregapi.data.CS.*;

public class ParallelDistributeTarget extends CommonPartGlow{
    public boolean isMachineValid = false;
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return aShouldSideBeRendered[aSide] ? BlockTextureMulti.get(BlockTextureDefault.get(mTextures[mDesign][FACES_TBS[aSide]], mRGBa), BlockTextureDefault.get(mTextures[mDesign][FACES_TBS[aSide]+3]), BlockTextureDefault.get(mTexturesGlow[mDesign][FACES_TBS[aSide]], isMachineValid)) : null;
    }
    public boolean updateMachineState(){
        for(byte i : ALL_SIDES_VALID){
            TileEntity tile = getAdjacentTileEntity(i).mTileEntity;
            if (tile instanceof ParallelDistributeWireSub)return ((ParallelDistributeWireSub) tile).updateMachineState(OPOS[i]);
            if(tile instanceof IInventory || tile instanceof IFluidHandler)return checkMachineState(tile);
        }
        return false;
    }

    public void inject(ItemStack itemStack, FluidStack fluidStack){

    }
    public static boolean checkMachineState(TileEntity tile){
        return false;
    }
}
