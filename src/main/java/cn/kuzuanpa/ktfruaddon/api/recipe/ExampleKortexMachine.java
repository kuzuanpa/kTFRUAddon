package cn.kuzuanpa.ktfruaddon.api.recipe;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.material.prefix.prefixList;
import cn.kuzuanpa.ktfruaddon.item.items.itemTurbine;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.generator.MultiTileEntityLargeTurbine;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.code.TagData;
import gregapi.data.FL;
import gregapi.data.FM;
import gregapi.data.LH;
import gregapi.fluid.FluidTankGT;
import gregapi.oredict.OreDictMaterial;
import gregapi.recipes.Recipe;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.util.ST;
import gregapi.util.WD;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.util.List;

import static gregapi.data.CS.*;
import static gregapi.data.CS.OPOS;

public class ExampleKortexMachine extends TileEntityBase09FacingSingle  implements IKortexHandler {
    long mRate = 16;
        protected KortexWorker kortex;
        @Override
        public void readFromNBT2(NBTTagCompound aNBT) {
            super.readFromNBT2(aNBT);
            Recipe.RecipeMap mRecipes = FM.Gas;
            if (aNBT.hasKey(NBT_INPUT)) mRate = aNBT.getLong(NBT_INPUT);
            if (aNBT.hasKey(NBT_RECIPEMAP)) mRecipes = Recipe.RecipeMap.RECIPE_MAPS.get(aNBT.getString(NBT_RECIPEMAP));
            kortex = new KortexWorker(this, mRecipes).setTankSize(mRate*4,mRate*16).setEnergyCapacity(mRate);
            kortex.readFromNBT(aNBT);
        }

        @Override
        public void writeToNBT2(NBTTagCompound aNBT) {
            super.writeToNBT2(aNBT);
            if(kortex!=null) kortex.writeToNBT(aNBT);
        }

        public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
            if (!isServerSide())return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
            kortex.onPlayerRightClick(aPlayer);
            return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
        }

        @Override
        public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
            long rReturn = super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
            if (rReturn > 0) return rReturn;

            if (isClientSide()) return 0;

            if (aTool.equals(TOOL_plunger)) {
                kortex.resetStatus();
                return 1;
            }
            return 0;
        }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        kortex.run();
    }

        @Override protected IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {
            if(!kortex.recipeMap.containsInput(aFluidToFill, this, NI))return null;
            if (aFluidToFill == null) {
                for (int i = 0; i < kortex.fluidInputs.length; i++) if (kortex.fluidInputs[i].isEmpty()) return kortex.fluidOutputs[i];
            } else {
                for (int i = 0; i < kortex.fluidInputs.length; i++) if (kortex.fluidInputs[i].contains(aFluidToFill)) return kortex.fluidOutputs[i];
                //no tank found, return first empty tank
                for (int i = 0; i < kortex.fluidInputs.length; i++) if (kortex.fluidInputs[i].isEmpty()) return kortex.fluidOutputs[i];
            }
            return null;
        }

        @Override
        protected IFluidTank getFluidTankDrainable2(byte aSide, FluidStack aFluidToDrain) {
            if (aFluidToDrain == null) {
                for (int i = 0; i < kortex.fluidOutputs.length; i++) if (kortex.fluidOutputs[i].has()) return kortex.fluidOutputs[i];
            } else {
                for (int i = 0; i < kortex.fluidOutputs.length; i++) if (kortex.fluidOutputs[i].contains(aFluidToDrain)) return kortex.fluidOutputs[i];
            }
            return null;
        }

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        return super.doInject(aEnergyType, aSide, aSize, aAmount, aDoInject);
    }

    @Override
        public void receiveOutputs(FluidTankGT[] fluidTanks, ItemStack[] items) {
            for (FluidTankGT tank : fluidTanks) if (tank.has()) {
                ChunkCoordinates pos = getOffset(OPOS[mFacing], 4);
                pos.posY-=1;
                FL.move(tank, WD.te(worldObj,pos,mFacing,false));
                if (FL.gas(tank) && !WD.hasCollide(worldObj, pos)) tank.setEmpty();
            }
        }
    @Override
    public ITexture getTexture2(Block block, int i, byte b, boolean[] booleans) {
        return null;
    }

    @Override
    public boolean canDrop(int i) {
        return true;
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.te.example.kortex";
    }
}



















