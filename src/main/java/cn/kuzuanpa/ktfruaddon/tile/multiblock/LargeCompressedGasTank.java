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


package cn.kuzuanpa.ktfruaddon.tile.multiblock;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.ICompressGasTank;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.AirPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.ControllerPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import gregapi.data.FL;
import gregapi.data.LH;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.WD;
import gregtech.tileentity.multiblocks.MultiTileEntityTank;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.util.List;

import static gregapi.data.CS.*;

public class LargeCompressedGasTank extends MultiTileEntityTank implements ICompressGasTank {
    /**A 3x3x3 Shell of Tank Walls in the Back of the Valve, the Hollow Center inside of it is the actual Tank.**/
    protected IStringBaseStructure structure;
    protected ChunkCoordinates lastFailedPos;

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        structure = new LayerStructure(StructureContext.Axis.Y).layerRule("ABC")
                .fixedLayer('A',
                        "WWW",
                        "WWW",
                        "WWW"
                ).fixedLayer('B',
                        "WWW",
                        "KEW",
                        "WWW"
                ).fixedLayer('C',
                        "WWW",
                        "WWW",
                        "WWW"
                )
                .where('W', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, mTankWalls, MultiTileEntityMultiBlockPart.ONLY_FLUID, 0)))
                .where('E', new AirPredicate())
                .where('K', new ControllerPredicate())
                .setOffset(-1,-1,0);
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.WHITE    + LH.get(I18nHandler.TANK_GAS_COMPRESSED_1));
        aList.add(LH.Chat.ORANGE    + LH.get(I18nHandler.TANK_GAS_COMPRESSED_2));
        aList.add(LH.Chat.ORANGE    + LH.get(I18nHandler.TANK_GAS_COMPRESSED_NEED_INPUTER));
        super.addToolTips(aList, aStack, aF3_H);
    }

    static {
        LH.add(I18nHandler.TANK_GAS_COMPRESSED_1,"Store gas at liquid state");
        LH.add(I18nHandler.TANK_GAS_COMPRESSED_2,"Can only store Gas!");
        LH.add(I18nHandler.TANK_GAS_COMPRESSED_NEED_INPUTER,"Needs Gas Compressor to work!");
    }
    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        if (structure == null || !worldObj.blockExists(xCoord, yCoord, zCoord)) return mStructureOkay;
        lastFailedPos = structure.checkStructure(new StructureContext(this, (aPlayer != null || aInventory != null)? StructureContext.StringBaseMode.SET : StructureContext.StringBaseMode.CHECK, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        return lastFailedPos == null;
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return structure != null && structure.isInsideStructure(this, mFacing, aX, aY, aZ);
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if (aIsServerSide && checkStructure(F)) {
            FluidStack tFluid = mTank.getFluid();
            if (tFluid != null && tFluid.amount > 0) {
                if (FL.temperature(mTank) >= mMaterial.mMeltingPoint && meltdown()) return;
                if (FL.gas(mTank)) {
                    if (FL.move(mTank, getAdjacentTileEntity(mFacing)) > 0) updateInventory();
                }
            }
        }
    }

    public boolean meltdown() {
        int tX = getOffsetXN(mFacing), tY = getOffsetYN(mFacing), tZ = getOffsetZN(mFacing);
        for (int i = -1; i <= 1; i++) for (int j = -1; j <= 1; j++) for (int k = -1; k <= 1; k++) {
            WD.burn(worldObj, tX+i, tY+j, tZ+k, F, F);
            if (rng(4) == 0) worldObj.setBlock(tX+i, tY+j, tZ+k, Blocks.fire, 0, 3);
        }
        if (FL.lava(mTank) && mTank.drainAll(1000)) worldObj.setBlock(tX, tY, tZ, Blocks.flowing_lava, 0, 3);
        GarbageGT.trash(mTank);
        setToFire();
        return T;
    }

    @Override
    public boolean allowFluid(FluidStack aFluid) {
        return !FL.powerconducting(aFluid) && FL.temperature(aFluid) < mMaterial.mMeltingPoint && FL.gas(aFluid);
    }

    @Override protected IFluidTank getFluidTankFillable(MultiTileEntityMultiBlockPart aPart, byte aSide, FluidStack aFluidToFill) {
        return null;
    }

    public int fillCompressedGas(FluidStack fluid){return mTank.fill(fluid);}
    @Override public String getTileEntityName() {return "ktfru.multitileentity.multiblock.tank.gas.compressed";}

}


