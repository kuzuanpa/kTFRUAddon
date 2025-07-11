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

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.network.ITileSyncByteArrayLong;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.IMeterDetectable;
import cn.kuzuanpa.ktfruaddon.api.tile.crucible.IDummyCrucibleMaterialProvider;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.client.gui.ContainerClientElectromagnetCrucible;
import cn.kuzuanpa.ktfruaddon.ktfruaddon;
import gregapi.block.multitileentity.IWailaTile;
import gregapi.code.ArrayListNoNulls;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.OP;
import gregapi.data.TD;
import gregapi.gui.ContainerCommonDefault;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.oredict.OreDictItemData;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictMaterialStack;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.multiblocks.IMultiBlockEnergy;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import gregapi.util.OM;
import gregapi.util.UT;
import gregapi.util.WD;
import gregtech.tileentity.multiblocks.MultiTileEntityCrucible;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.IBlockAccess;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.*;

import static gregapi.data.CS.*;
import static gregapi.data.CS.SFX.MC_FIZZ;

public class ElectromagnetCrucible extends TileEntityBase10MultiBlockBase implements ITileEntityEnergy, IMultiBlockEnergy, IDummyCrucibleMaterialProvider, ITileSyncByteArrayLong, IWailaTile {
    public boolean mStopped = false, mContentChanged = true, mTempChanged = true;
    public long mEnergy = 0, mInputMax = 1024, mEnergyBaseConsume = 100, mMassSelf = 3200, mMassTotal = mMassSelf;
    public float mTemp = 0.0F, oldTemp = 0.0F, mTempMax = 32768.0F;
    public List<IMeterDetectable.MeterData> receivedEnergy = new ArrayList<>(), receivedEnergyLast = new ArrayList<>();
    public TagData mEnergyType = TD.Energy.EU;
    protected List<OreDictMaterialStack> mContent = new ArrayListNoNulls<>();
    public static long MAX_AMOUNT = 320*U;

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

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(isServerSide())openGUI(aPlayer, aSide);
        return T;
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if(!aIsServerSide)return;
        receivedEnergyLast = receivedEnergy;
        receivedEnergy = new ArrayList<>();
        mMassTotal = mMassSelf;

        for (OreDictMaterialStack tMaterial : mContent) mMassTotal += (long) tMaterial.weight();

        if(mEnergy < mEnergyBaseConsume){
            UT.Sounds.send(MC_FIZZ, (TileEntity) this);
            mTemp = WD.envTemp(worldObj, xCoord,yCoord,zCoord);
            mContent.clear();
            mContentChanged = true;
        }

        if(mEnergy > mEnergyBaseConsume && mTemp <= mTempMax){
            mTemp += (mEnergy - mEnergyBaseConsume) / ( 1 + mMassTotal * 1.66F / MultiTileEntityCrucible.KG_PER_ENERGY);
            mTempChanged=true;
        }
        mEnergy = 0;

        for (OreDictMaterialStack content : mContent) {
            if (content.mMaterial.mMeltingPoint < mTemp && content.mMaterial.mMeltingPoint > oldTemp) {
                content.mMaterial = content.mMaterial.mTargetSmelting.mMaterial;
                content.mAmount = UT.Code.units_(content.mAmount, U, content.mMaterial.mTargetSmelting.mAmount, F);
            }

            if (content.mMaterial.mMeltingPoint > mTemp && content.mMaterial.mMeltingPoint < oldTemp) {
                content.mMaterial = content.mMaterial.mTargetSolidifying.mMaterial;
                content.mAmount = UT.Code.units_(content.mAmount, U, content.mMaterial.mTargetSolidifying.mAmount, F);
            }
        }

        oldTemp=mTemp;

        mTemp = Math.max(WD.envTemp(worldObj, xCoord,yCoord,zCoord), mTemp - 0.2F);
        if(!slotHas(0))return;
        OreDictItemData tData = OM.anydata_(slot(0));
        long tTemperature = C;
        if(tData == null)return;
        if (tData.mPrefix == null) {
            List<OreDictMaterialStack> tList = new ArrayListNoNulls<>();
            for (OreDictMaterialStack tMaterial : tData.getAllMaterialStacks()) if (tMaterial.mAmount > 0) tList.add(tMaterial.clone());
            if (addMaterialStacks(tList, tTemperature)) decrStackSize(0, 1);
        } else if (tData.mPrefix == OP.oreRaw) {
            if (addMaterialStacks(Collections.singletonList(OM.stack(tData.mMaterial.mMaterial.mTargetCrushing.mMaterial, tData.mMaterial.mMaterial.mTargetCrushing.mAmount * tData.mMaterial.mMaterial.mOreMultiplier)), tTemperature)) decrStackSize(0, 1);
        } else if (tData.mPrefix == OP.blockRaw) {
            if (addMaterialStacks(Collections.singletonList(OM.stack(tData.mMaterial.mMaterial.mTargetCrushing.mMaterial, tData.mMaterial.mMaterial.mTargetCrushing.mAmount * tData.mMaterial.mMaterial.mOreMultiplier * 9)), tTemperature)) decrStackSize(0, 1);
        } else if (tData.mPrefix == OP.crateGtRaw) {
            if (addMaterialStacks(Collections.singletonList(OM.stack(tData.mMaterial.mMaterial.mTargetCrushing.mMaterial, tData.mMaterial.mMaterial.mTargetCrushing.mAmount * tData.mMaterial.mMaterial.mOreMultiplier * 16)), tTemperature)) decrStackSize(0, 1);
        } else if (tData.mPrefix == OP.crateGt64Raw) {
            if (addMaterialStacks(Collections.singletonList(OM.stack(tData.mMaterial.mMaterial.mTargetCrushing.mMaterial, tData.mMaterial.mMaterial.mTargetCrushing.mAmount * tData.mMaterial.mMaterial.mOreMultiplier * 64)), tTemperature)) decrStackSize(0, 1);
        } else if (tData.mPrefix.contains(TD.Prefix.STANDARD_ORE)) {
            if (addMaterialStacks(Collections.singletonList(OM.stack(tData.mMaterial.mMaterial.mTargetCrushing.mMaterial, tData.mMaterial.mMaterial.mTargetCrushing.mAmount * tData.mMaterial.mMaterial.mOreMultiplier)), tTemperature)) decrStackSize(0, 1);
        } else if (tData.mPrefix.contains(TD.Prefix.DENSE_ORE)) {
            if (addMaterialStacks(Collections.singletonList(OM.stack(tData.mMaterial.mMaterial.mTargetCrushing.mMaterial, tData.mMaterial.mMaterial.mTargetCrushing.mAmount * tData.mMaterial.mMaterial.mOreMultiplier * 2)), tTemperature)) decrStackSize(0, 1);
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
            if(mTemp >= mTempMax && mEnergy > 0)return 0;
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
    public boolean isMaterialValid(OreDictMaterial aMaterial) {
        return aMaterial.containsAny(TD.Properties.INVALID_MATERIAL)?/*for anyIron, anySteel and so on*/
                aMaterial.mTargetSmelting.mMaterial.containsAny(TD.Compounds.ALLOY, TD.Atomic.METAL) :
                aMaterial.containsAny(TD.Compounds.ALLOY, TD.Atomic.METAL);

    }

    public boolean addMaterialStacks(List<OreDictMaterialStack> aList, long aTemperature) {
        if (checkStructure(F) && OM.total(mContent)+OM.total(aList) <= MAX_AMOUNT && aList.stream().anyMatch(matStack -> matStack.mMaterial.mMeltingPoint < mTemp && isMaterialValid(matStack.mMaterial) )) {
            double tWeight1 = OM.weight(mContent)+mMaterial.getWeight(U*100), tWeight2 = OM.weight(aList);
            if (tWeight1+tWeight2 > 0) mTemp = aTemperature + (mTemp>aTemperature?+1:-1)*UT.Code.units((long) Math.abs(mTemp - aTemperature), (long)(tWeight1+tWeight2), (long)tWeight1, F);
            for (OreDictMaterialStack tMaterial : aList) {
                mContentChanged = true;
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

    @Override public boolean isEnergyType (TagData aEnergyType, byte aSide, boolean aEmitting) {return !aEmitting && aEnergyType == mEnergyType;}

    @Override public long getEnergySizeInputMin(TagData aEnergyType, byte aSide) {return 0;}
    @Override public long getEnergySizeInputRecommended (TagData aEnergyType, byte aSide) {return mEnergyBaseConsume;}
    @Override public long getEnergySizeInputMax(TagData aEnergyType, byte aSide) {return mInputMax;}

    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return new ArrayListNoNulls<>(F, mEnergyType);}

    //Structure

    ChunkCoordinates lastFailedPos=null;
    IStringBaseStructure structure = new LayerStructure(StructureContext.Axis.Y).layerRule("ABBBC")
            .fixedLayer('A',
                    " PPP ",
                    "PXXXP",
                    " XXXP",
                    "PXXXP",
                    " PPP "
            ).fixedLayer('B',
                    " XXX ",
                    "XCCCX",
                    "XC CX",
                    "XCCCX",
                    " XXX "
            ).fixedLayer('C',
                    " XXX ",
                    "XXXXX",
                    "XXXXX",
                    "XXXXX",
                    " XXX "
            )
            .where('P', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, 31504)))
            .where('X', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18006)))
            .where('C', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18041)))
            .setOffset(-2,0,0);

    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = structure.checkStructure(new StructureContext(this, (aPlayer != null || aInventory != null)? StructureContext.StringBaseMode.SET: StructureContext.StringBaseMode.CHECK, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        return lastFailedPos==null;
    }
    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return true;
    }

    @Override
    public @Nullable OreDictMaterialStack extractMaterial(long amount, @Nullable OreDictMaterial selectedMaterial) {
        OreDictMaterialStack rawMatStack = findValidMaterial(selectedMaterial);
        if(rawMatStack == null)return null;
        long requiredAmount = UT.Code.units(amount, U, rawMatStack.mMaterial.mTargetSolidifying.mAmount, T);
        if(rawMatStack.mAmount <= requiredAmount){
            mContent.remove(rawMatStack);
            mContentChanged = true;
            return rawMatStack;
        }
        rawMatStack.mAmount -= requiredAmount;
        mContentChanged = true;
        return rawMatStack.copy(requiredAmount);
    }

    @Override public float getTemperature() {return mTemp;}

    @Override public byte getDefaultSide() {return SIDE_FRONT;}

    //inventory
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[1];}
    private static final int[] ACCESSIBLE_SLOTS = new int[] {0};
    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}
    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {return true;}
    @Override public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {return true;}

    private OreDictMaterialStack findValidMaterial(@Nullable OreDictMaterial selectedMaterial){
        if(selectedMaterial != null) return mContent.stream().filter(stack -> stack.mMaterial.equals(selectedMaterial) && selectedMaterial.mMeltingPoint < mTemp).findFirst().orElse(null);
        return mContent.stream().filter(stack -> stack.mMaterial.mMeltingPoint < mTemp).findFirst().orElse(null);
    }

    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {return new ContainerClientElectromagnetCrucible(aPlayer.inventory, this, aGUIID);}
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {return new ContainerCommonDefault(aPlayer.inventory, this, aGUIID);}
    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        return getClientDataPacketByteArrayLong(aSendAll, saveToArray(aSendAll));
    }

    @Override
    public INetworkHandler getNetworkHandler() {
        return ktfruaddon.kNetworkHandler;
    }

    @Override
    public INetworkHandler getNetworkHandlerNonOwned() {
        return ktfruaddon.kNetworkHandler2;
    }

    @Override
    public void receiveDataByteArrayLong(IBlockAccess aWorld, int aX, int aY, int aZ, byte[] aData, INetworkHandler aNetworkHandler) {
        loadFromArray(aData);
    }

    @Override
    public boolean onTickCheck(long aTimer) {
        return super.onTickCheck(aTimer)|| mContentChanged || mTempChanged;
    }

    public byte[] saveToArray(boolean aSendAll) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);

            dos.writeBoolean(aSendAll);
            if(aSendAll){
                dos.writeByte(UT.Code.getR(mRGBa));
                dos.writeByte(UT.Code.getG(mRGBa));
                dos.writeByte(UT.Code.getB(mRGBa));
                dos.writeByte(getDirectionData());
            }
            dos.writeByte(getVisualData());
            dos.writeFloat(mTemp);
            dos.writeBoolean(mContentChanged);
            if(mContentChanged){
            dos.writeInt(mContent.size());
            for (OreDictMaterialStack stack : mContent) {
                dos.writeShort(stack.mMaterial.mTargetSmelting.mMaterial.mID);
                short amount = (short)(100 * stack.mAmount / U);
                dos.writeShort(stack.mMaterial.mMeltingPoint < mTemp ? -amount : amount);
            }
            }
            mContentChanged = false;
            dos.flush();
            return bos.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
            return new byte[0];
        }
    }
    public Map<Short, Short> mDisplayContent = new HashMap<>();

    public void loadFromArray(byte[] bytes) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            DataInputStream dis = new DataInputStream(bis);
            boolean sendAll = dis.readBoolean();
            if(sendAll){
                mRGBa = UT.Code.getRGBInt(new short[] {UT.Code.unsignB(dis.readByte()), UT.Code.unsignB(dis.readByte()), UT.Code.unsignB(dis.readByte())});
                setDirectionData(dis.readByte());
            }
            setVisualData(dis.readByte());
            mTemp = dis.readFloat();
            if(dis.readBoolean()) {
                mDisplayContent.clear();
                int size = dis.readInt();

                for (int i = 0; i < size; i++) {
                    short materialID = dis.readShort();
                    short amountWithTempFlag = dis.readShort();
                    mDisplayContent.put(materialID, amountWithTempFlag);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public NBTTagCompound getWailaNBT(TileEntity te, NBTTagCompound aNBT) {
        IWailaTile.super.getWailaNBT(te, aNBT);
        aNBT.setInteger("mTemp.c", (int)Math.floor(mTemp));
        return aNBT;
    }

    @Override
    public List<String> getWailaBody(List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        IWailaTile.super.getWailaBody(currentTip, accessor, config);
        currentTip.add(LH.get(I18nHandler.TEMPERATURE+".core")+ " " + LH.Chat.WHITE + accessor.getNBTData().getInteger("mTemp.c"));
        return currentTip;
    }
    @Override public String getTileEntityName() {return "ktfru.multitileentity.multiblock.crucible.electromagnet";}
}
