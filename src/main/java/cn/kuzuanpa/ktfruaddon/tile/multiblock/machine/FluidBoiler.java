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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.machine;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.recipe.IKortexHandler;
import cn.kuzuanpa.ktfruaddon.api.recipe.KortexWorker;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import gregapi.block.multitileentity.IWailaTile;
import gregapi.data.FL;
import gregapi.data.FM;
import gregapi.data.LH;
import gregapi.fluid.FluidTankGT;
import gregapi.recipes.Recipe;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.machines.ITileEntitySwitchableOnOff;
import gregapi.tileentity.multiblocks.IMultiBlockFluidHandler;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import gregapi.util.UT;
import gregapi.util.WD;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.List;

import static cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler.HAS_PROJECTOR_STRUCTURE;
import static gregapi.data.CS.*;

public class FluidBoiler extends TileEntityBase10MultiBlockBase implements IMultiBlockFluidHandler, IFluidHandler, IKortexHandler, IWailaTile, ITileEntitySwitchableOnOff {
    public final short machineX = 5, machineY = 3, machineZ = 3;
    public long mRate = 16;
    public boolean mForcedStopped = false;
    public KortexWorker kortex;
    public IWailaInfoProvider tankInfoInput = new InfoTank(LH.get(I18nHandler.INPUT), "", ZL_FT);
    public IWailaInfoProvider tankInfoOutput = new InfoTank(LH.get(I18nHandler.OUTPUT), "", ZL_FT);
    public FluidTankGT[] mTanks = ZL_FT;
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        Recipe.RecipeMap mRecipes = FM.Hot;
        if (aNBT.hasKey(NBT_OUTPUT)) mRate = aNBT.getLong(NBT_OUTPUT);
        if (aNBT.hasKey(NBT_FUELMAP)) mRecipes = Recipe.RecipeMap.RECIPE_MAPS.get(aNBT.getString(NBT_FUELMAP));
        kortex = new KortexWorker(this, 0, mRecipes, true).setTankSize(mRate*4,mRate*16).setEnergyCapacity(mRate*2);
        mTanks = new FluidTankGT[] {new FluidTankGT(mRate*16), new FluidTankGT(mRate*640), new FluidTankGT(mRate*16)};
        tankInfoInput = new InfoTank(LH.get(I18nHandler.INPUT), "", mTanks[0], kortex.fluidInputs[0]);
        tankInfoOutput = new InfoTank(LH.get(I18nHandler.OUTPUT), "", mTanks[1], mTanks[2]);
        kortex.readFromNBT(aNBT);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        kortex.writeToNBT(aNBT);
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        kortex.run();
        // Convert Water to Steam
        long tConversions = Math.min(mTanks[1].capacity() / 2560, Math.min(kortex.mEnergyStored / 80, mTanks[0].amount()));
        if (tConversions > 0) {
            mTanks[0].remove(tConversions);
            mTanks[1].setFluid(FL.Steam.make(mTanks[1].amount() + UT.Code.units(tConversions, 10000, 10000 * 160, F)));
            kortex.mEnergyStored -= tConversions * 80;
        }
    }

    @Override
    public void receiveOutputs(FluidTankGT[] fluidTanks, ItemStack[] items) {
        int received = mTanks[2].fill(fluidTanks[0].get());
        fluidTanks[0].drain(received, true);
    }

    @Override
    protected IFluidTank[] getFluidTanks2(byte aSide) {
        return mTanks;
    }

    @Override protected IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {
        if(mForcedStopped)return null;
        if(kortex.recipeMap.containsInput(aFluidToFill, this, NI))return kortex.fluidInputs[0];
        if(FL.distw(aFluidToFill))return mTanks[0];
        return null;
    }

    @Override public boolean setStateOnOff(boolean b) {return mForcedStopped = b;}
    @Override public boolean getStateOnOff() {return mForcedStopped;}

    //Structure
    ChunkCoordinates lastFailedPos=null;
    static IStringBaseStructure structure = new LayerStructure(StructureContext.Axis.Y).layerRule("ABC")
            .fixedLayer('A',
                    "AAAAA",
                    "AAAAA",
                    "AAAAA",
                    "BBBBB",
                    "BBBBB",
                    "NBBBB"
            )
            .fixedLayer('B',
                    "CCCCC",
                    "CCCCC",
                    "CCCCC",
                    "BBDDD",
                    "BBDDD",
                    "BBDDD"
            ).fixedLayer('C',
                    "     ",
                    "     ",
                    "     ",
                    "EEDDD",
                    "EEDDD",
                    "EEDDD"
            )
            .where('A', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18006, MultiTileEntityMultiBlockPart.ONLY_IN)))
            .where('B', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18003, MultiTileEntityMultiBlockPart.ONLY_IN)))
            .where('C', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18106, MultiTileEntityMultiBlockPart.NOTHING)))
            .where('D', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18100)))
            .where('E', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18108)))
            .where('N', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18003, MultiTileEntityMultiBlockPart.ONLY_OUT, 7)))
            .setOffset(0,0,0) ;

    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = structure.checkStructure(new StructureContext(this, (aPlayer != null || aInventory != null)? StructureContext.StringBaseMode.SET: StructureContext.StringBaseMode.CHECK, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        return lastFailedPos==null;
    }

    static {
        LH.add("ktfru.tooltip.multiblock.fluid_boiler.0", "Input Item, fluid and Energy from Walls");
        LH.add("ktfru.tooltip.multiblock.fluid_boiler.1", "Output from right corner, item output to front side, fluid to right side.");
    }
    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN+LH.get(HAS_PROJECTOR_STRUCTURE));
        aList.add(LH.Chat.WHITE+LH.get("ktfru.tooltip.multiblock.fluid_boiler.0"));
        aList.add(LH.Chat.WHITE+LH.get("ktfru.tooltip.multiblock.fluid_boiler.1"));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public List<IWailaInfoProvider> getWailaInfos(List<IWailaInfoProvider> current) {
        current.add(tankInfoInput);
        current.add(tankInfoOutput);
        return current;
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) { return true;}

    public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte aSide, Fluid aOutput) {
        DelegatorTileEntity<TileEntity> te = WD.te(this.worldObj, utils.getRealX(mFacing,xCoord,6,0), this.yCoord , utils.getRealZ(mFacing,zCoord,6,0), mFacing, false);
        if(te == null || !(te.mTileEntity instanceof IFluidHandler)) return this.getAdjacentTank(SIDE_INVALID);
        return new DelegatorTileEntity<>((IFluidHandler)te.mTileEntity,FACING_TO_SIDE[mFacing][SIDE_LEFT]);
    }

    public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) {
        DelegatorTileEntity<TileEntity> te = WD.te(this.worldObj, utils.getRealX(mFacing,xCoord,5,-1), this.yCoord , utils.getRealZ(mFacing,zCoord,5,-1), mFacing, false);
        if(te == null || te.mTileEntity == null) return this.delegator(SIDE_INVALID);
        return new DelegatorTileEntity<>(te.mTileEntity,OPOS[mFacing]);
    }

    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide())return true;

        if(!mStructureOkay)aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED+LH.get(I18nHandler.STRUCTURE_ERR)));

        ItemStack equippedItem=aPlayer.getCurrentEquippedItem();
        if (equippedItem!=null && equippedItem.getItem() instanceof ItemProjector) {
            structure.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }
        return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
    }
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.fluid_boiler";
    }

}