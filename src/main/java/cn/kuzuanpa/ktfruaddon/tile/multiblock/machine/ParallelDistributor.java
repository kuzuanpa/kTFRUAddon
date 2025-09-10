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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.machine;

import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.ParallelDistributeTarget;
import gregapi.block.multitileentity.IWailaTile;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.multiblocks.IMultiBlockEnergy;
import gregapi.tileentity.multiblocks.IMultiBlockFluidHandler;
import gregapi.tileentity.multiblocks.IMultiBlockInventory;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import gregapi.util.WD;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class ParallelDistributor extends TileEntityBase10MultiBlockBase implements IMultiBlockInventory, IMultiBlockFluidHandler, ITileEntityEnergy, IMultiBlockEnergy, IWailaTile {
    public Map<Integer, ParallelDistributeTarget> targets = new HashMap<>();
    public int currentTarget = 0;
    public static final int MAX_LENGTH=32;
    public static final byte DMODE_NEAR=0, DMODE_NEAR_FORCE=1, DMODE_POLLING=2, DMODE_POLLING_FORCE=3, DMODE_RANDOM=4, DMODE_PRIORITY=5, RMODE_SIGN=-1, RMODE_REDSTONE=-2, RMODE_INV_DETECT=-3;
    public byte distributeMode = DMODE_NEAR, recipeEndMode = RMODE_SIGN;
    public boolean blockMode = true, receivedRedstone = false , isRecipeEnd= false;

    public ItemStack signItem = null;
    public void validateTargets(){
        Map<Integer, ParallelDistributeTarget> newTargets = new HashMap<>();
        for (int i = 1; i < MAX_LENGTH; i++) {
            TileEntity t = WD.te(worldObj, utils.getRealX(mFacing, xCoord, 0, i), yCoord, utils.getRealZ(mFacing, zCoord, 0, i), false);
            if(! (t instanceof IParallelDistributorPart))return;
            if (t instanceof ParallelDistributeTarget) newTargets.put(i, (ParallelDistributeTarget) t);
        }
        targets = newTargets;
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        validateTargets();
        if(blockMode)targets.forEach((id,target)->target.updateMachineState());
        if(currentTarget == 0 || isRecipeEnd)receivedRedstone = false;
    }

    public void inject(ItemStack itemStack, FluidStack fluidStack){
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            if(currentTarget == 0|| !targets.get(currentTarget).updateMachineState())updateCurrentTarget();
            checkRecipeEnd(itemStack);
            targets.get(currentTarget).inject(itemStack, fluidStack);
            checkRecipeEnd(itemStack);
        } finally {
            lock.unlock();
        }
    }
    protected void updateCurrentTarget(){

    }
    protected void checkRecipeEnd(ItemStack itemStack){
        switch (recipeEndMode){
            case RMODE_SIGN:
                if(itemStack.equals(signItem)) isRecipeEnd =true;
                return;
            case RMODE_REDSTONE:
                if(receivedRedstone)isRecipeEnd =true;
                receivedRedstone = false;
                return;
            case RMODE_INV_DETECT:
                return;
        }
        if(isRecipeEnd)updateCurrentTarget();

    }
    public interface IParallelDistributorPart {}

}
