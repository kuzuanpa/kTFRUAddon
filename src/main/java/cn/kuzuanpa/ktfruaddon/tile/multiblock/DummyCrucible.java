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

package cn.kuzuanpa.ktfruaddon.tile.multiblock;

import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.IMappedStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.IMeterDetectable;
import cn.kuzuanpa.ktfruaddon.api.tile.crucible.IDummyCrucibleMaterialProvider;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import gregapi.code.ArrayListNoNulls;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.OP;
import gregapi.data.TD;
import gregapi.oredict.OreDictItemData;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictMaterialStack;
import gregapi.tileentity.multiblocks.IMultiBlockEnergy;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import gregapi.util.OM;
import gregapi.util.UT;
import gregtech.tileentity.multiblocks.MultiTileEntityCrucible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.*;

public class DummyCrucible extends TileEntityBase10MultiBlockBase implements IMappedStructure, IMultiBlockEnergy, IDummyCrucibleMaterialProvider {
    public boolean mStopped = false;
    public long mEnergy = 0, mInputMax = 1024, mEnergyBaseConsume = 100, mMassSelf = 3200, mMassTotal = mMassSelf;
    public float mTemp = 0.0F, mTempMax = 32768.0F;
    public List<IMeterDetectable.MeterData> receivedEnergy = new ArrayList<>(), receivedEnergyLast = new ArrayList<>();
    public TagData mEnergyType = TD.Energy.EU;
    protected List<OreDictMaterialStack> mContent = new ArrayListNoNulls<>();
    public static long MAX_AMOUNT = 16*3*3*3*U;

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if(aNBT.hasKey(NBT_ENERGY))mEnergy = aNBT.getLong(NBT_ENERGY);
        if (aNBT.hasKey("ktfru.nbt.massSelf")) mMassTotal = mMassSelf = aNBT.getLong("ktfru.nbt.massSelf");
        if (aNBT.hasKey(NBT_TEMPERATURE+".max")) mTempMax = aNBT.getLong(NBT_TEMPERATURE+".max");
        if(aNBT.hasKey(NBT_INPUT_MAX))mInputMax = aNBT.getLong(NBT_INPUT_MAX);
        if(aNBT.hasKey(NBT_TEMPERATURE))mTemp = aNBT.getFloat(NBT_TEMPERATURE);
        mContent = OreDictMaterialStack.loadList(NBT_MATERIALS, aNBT);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergy);
        aNBT.setFloat(NBT_TEMPERATURE, mTemp);
        OreDictMaterialStack.saveList(NBT_MATERIALS, aNBT, mContent);
    }

    static {
        LH.add("gt.tooltip.multiblock.example.complex.1", "5x5x2 of Stainless Steel Walls");
        LH.add("gt.tooltip.multiblock.example.complex.2", "Main Block centered on Side-Bottom and facing outwards");
        LH.add("gt.tooltip.multiblock.example.complex.3", "Input and Output at any Blocks");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.1"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.2"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.3"));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        receivedEnergyLast = receivedEnergy;
        receivedEnergy = new ArrayList<>();

        if(mEnergy > mEnergyBaseConsume){
            mTemp += (mEnergy - mEnergyBaseConsume) * MultiTileEntityCrucible.KG_PER_ENERGY * 2F / mMassTotal;
        }
        mEnergy = 0;
        if(!slotHas(0))return;
        OreDictItemData tData = OM.anydata_(slot(0));
        long tTemperature = C;
        if(tData == null)return;
        if (tData.mPrefix == null) {
            List<OreDictMaterialStack> tList = new ArrayListNoNulls<>();
            for (OreDictMaterialStack tMaterial : tData.getAllMaterialStacks()) if (tMaterial.mAmount > 0) tList.add(tMaterial.clone());
            if (addMaterialStacks(tList, tTemperature)) decrStackSize(0, 1);
        } else if (tData.mPrefix == OP.oreRaw) {
            if (addMaterialStacks(Arrays.asList(OM.stack(tData.mMaterial.mMaterial.mTargetCrushing.mMaterial, tData.mMaterial.mMaterial.mTargetCrushing.mAmount * tData.mMaterial.mMaterial.mOreMultiplier     )), tTemperature)) decrStackSize(0, 1);
        } else if (tData.mPrefix == OP.blockRaw) {
            if (addMaterialStacks(Arrays.asList(OM.stack(tData.mMaterial.mMaterial.mTargetCrushing.mMaterial, tData.mMaterial.mMaterial.mTargetCrushing.mAmount * tData.mMaterial.mMaterial.mOreMultiplier *  9)), tTemperature)) decrStackSize(0, 1);
        } else if (tData.mPrefix == OP.crateGtRaw) {
            if (addMaterialStacks(Arrays.asList(OM.stack(tData.mMaterial.mMaterial.mTargetCrushing.mMaterial, tData.mMaterial.mMaterial.mTargetCrushing.mAmount * tData.mMaterial.mMaterial.mOreMultiplier * 16)), tTemperature)) decrStackSize(0, 1);
        } else if (tData.mPrefix == OP.crateGt64Raw) {
            if (addMaterialStacks(Arrays.asList(OM.stack(tData.mMaterial.mMaterial.mTargetCrushing.mMaterial, tData.mMaterial.mMaterial.mTargetCrushing.mAmount * tData.mMaterial.mMaterial.mOreMultiplier * 64)), tTemperature)) decrStackSize(0, 1);
        } else if (tData.mPrefix.contains(TD.Prefix.STANDARD_ORE)) {
            if (addMaterialStacks(Arrays.asList(OM.stack(tData.mMaterial.mMaterial.mTargetCrushing.mMaterial, tData.mMaterial.mMaterial.mTargetCrushing.mAmount * tData.mMaterial.mMaterial.mOreMultiplier     )), tTemperature)) decrStackSize(0, 1);
        } else if (tData.mPrefix.contains(TD.Prefix.DENSE_ORE)) {
            if (addMaterialStacks(Arrays.asList(OM.stack(tData.mMaterial.mMaterial.mTargetCrushing.mMaterial, tData.mMaterial.mMaterial.mTargetCrushing.mAmount * tData.mMaterial.mMaterial.mOreMultiplier *  2)), tTemperature)) decrStackSize(0, 1);
        } else {
            List<OreDictMaterialStack> tList = new ArrayListNoNulls<>();
            for (OreDictMaterialStack tMaterial : tData.getAllMaterialStacks()) if (tMaterial.mAmount > 0) tList.add(tMaterial.clone());
            if (addMaterialStacks(tList, tTemperature)) decrStackSize(0, 1);
        }
    }

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if (mStopped) return 0;
        aSize = Math.abs(aSize);
        if (aEnergyType == mEnergyType) {
            if(aSize > mInputMax && aDoInject){
                explode(false);
                return aAmount;
            }
            if (aDoInject) mEnergy += aSize * aAmount;
            this.receivedEnergy.add(new IMeterDetectable.MeterData(aEnergyType,aSize, aAmount));
            return aAmount;
        }
        return 0;
    }

    public boolean addMaterialStacks(List<OreDictMaterialStack> aList, long aTemperature) {
        if (checkStructure(F) && OM.total(mContent)+OM.total(aList) <= MAX_AMOUNT) {
            double tWeight1 = OM.weight(mContent)+mMaterial.getWeight(U*100), tWeight2 = OM.weight(aList);
            if (tWeight1+tWeight2 > 0) mTemp = aTemperature + (mTemp>aTemperature?+1:-1)*UT.Code.units((long) Math.abs(mTemp - aTemperature), (long)(tWeight1+tWeight2), (long)tWeight1, F);
            for (OreDictMaterialStack tMaterial : aList) {
                if (mTemp >= tMaterial.mMaterial.mMeltingPoint) {
                    if (aTemperature <  tMaterial.mMaterial.mMeltingPoint) {
                        OM.stack(tMaterial.mMaterial.mTargetSmelting.mMaterial, UT.Code.units_(tMaterial.mAmount, U, tMaterial.mMaterial.mTargetSmelting.mAmount, F)).addToList(mContent);
                    } else {
                        tMaterial.addToList(mContent);
                    }
                } else {
                    if (aTemperature >= tMaterial.mMaterial.mMeltingPoint) {
                        OM.stack(tMaterial.mMaterial.mTargetSolidifying.mMaterial, UT.Code.units_(tMaterial.mAmount, U, tMaterial.mMaterial.mTargetSolidifying.mAmount, F)).addToList(mContent);
                    } else {
                        tMaterial.addToList(mContent);
                    }
                }
            }
            return T;
        }
        return F;
    }

    @Override public boolean isEnergyType                   (TagData aEnergyType, byte aSide, boolean aEmitting) {return !aEmitting && aEnergyType == mEnergyType;}

    @Override public long getEnergySizeInputMin(TagData aEnergyType, byte aSide) {return 0;}
    @Override public long getEnergySizeInputRecommended     (TagData aEnergyType, byte aSide) {return mEnergyBaseConsume;}
    @Override public long getEnergySizeInputMax(TagData aEnergyType, byte aSide) {return mInputMax;}

    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return new ArrayListNoNulls<>(F, mEnergyType);}


    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.dummy_crucible";
    }

    //Structure

    public final short sizeX = 5, sizeY = 1, sizeZ = 3;
    public final short xMapOffset = -2, zMapOffset = 0;

    public static int[][][] blockIDMap = {{
            {31504, 31504,   0  , 31504, 31504},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
    }};
    short k = GTTileEntityRegistry.ktfruaddon;
    short g = GTTileEntityRegistry.gregtech;
    public short[][][] registryIDMap = {{
            {k, k, k, k, k},
            {g, g, g, g, g},
            {g, g, g, g, g},
    }};
    @Override
    public TileDesc[] getTileDescs(int mapX, int mapY, int mapZ) {
        return new TileDesc[]{ new TileDesc(getRegistryID(mapX, mapY, mapZ), getBlockID(mapX, mapY, mapZ),getUsage(mapX, mapY, mapZ))};
    }

    public int getUsage(int mapX, int mapY, int mapZ) {
        int registryID = getRegistryID(mapX,mapY,mapZ), blockID = getBlockID(mapX, mapY, mapZ);
        if (blockID == 18002&&registryID==k) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
        } else if (blockID == 18002||blockID==18022&&registryID==g) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_OUT;
        }else{return MultiTileEntityMultiBlockPart.NOTHING;}
    }

    public int getBlockID(int checkX, int checkY, int checkZ){
        return blockIDMap[checkY][checkZ][checkX];
    }

    public boolean isIgnored(int checkX, int checkY, int checkZ){
        return false;
    }
    public short getRegistryID(int checkX, int checkY, int checkZ){return registryIDMap[checkY][checkZ][checkX];}

    ChunkCoordinates lastFailedPos=null;
    @Override
    public boolean checkStructure2() {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = checkMappedStructure(null, sizeX, sizeY, sizeZ,xMapOffset,0,zMapOffset);
        return lastFailedPos==null;
    }
    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return new BoundingBox(utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset),yCoord,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset),utils.getRealX(mFacing,utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset), sizeX, sizeZ),yCoord+ sizeY,utils.getRealZ(mFacing,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset), sizeX, sizeZ)).isXYZInBox(aX,aY,aZ);
    }

    @Override
    public @Nullable CrucibleOreDictMaterialStack extractMaterial(long amount, @Nullable OreDictMaterial selectedMaterial) {
        OreDictMaterialStack rawMatStack = findValidMaterial(selectedMaterial);
        if(rawMatStack == null)return null;
        long requiredAmount = UT.Code.units(amount, U, rawMatStack.mMaterial.mTargetSolidifying.mAmount, T);
        if(rawMatStack.mAmount <= requiredAmount){
            mContent.remove(rawMatStack);
            return new CrucibleOreDictMaterialStack(rawMatStack, rawMatStack.mAmount == requiredAmount);
        }
        rawMatStack.mAmount -= requiredAmount;
        return new CrucibleOreDictMaterialStack(rawMatStack.copy(requiredAmount), true);
    }

    @Override
    public float getTemperature() {
        return mTemp;
    }

    //inventory
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[1];}

    private static final int[] ACCESSIBLE_SLOTS = new int[] {0};

    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}

    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {return true;}

    @Override
    public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {
        return true;
    }

    private OreDictMaterialStack findValidMaterial(@Nullable OreDictMaterial selectedMaterial){
        if(selectedMaterial != null) return mContent.stream().filter(stack -> stack.mMaterial.equals(selectedMaterial) && selectedMaterial.mMeltingPoint < mTemp).findFirst().orElse(null);
        return mContent.stream().filter(stack -> stack.mMaterial.mMeltingPoint < mTemp).findFirst().orElse(null);
    }


}
