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

import cn.kuzuanpa.ktfruaddon.tile.multiblock.machine.ParallelDistributor;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.IFluidHandler;

import static cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.ParallelDistributeTarget.checkMachineState;
import static gregapi.data.CS.ALL_SIDES_VALID_BUT;
import static gregapi.data.CS.OPOS;

public class ParallelDistributeWireSub extends CommonPart implements ParallelDistributor.IParallelDistributorPart {
    public boolean updateMachineState(byte mFaceIncome){
        for(byte i : ALL_SIDES_VALID_BUT[mFaceIncome]){
            TileEntity tile = getAdjacentTileEntity(i).mTileEntity;
            if (tile instanceof ParallelDistributeWireSub)return ((ParallelDistributeWireSub) tile).updateMachineState(OPOS[i]);
            if(tile instanceof IInventory || tile instanceof IFluidHandler)return checkMachineState(tile);
        }
        return false;
    }

}
