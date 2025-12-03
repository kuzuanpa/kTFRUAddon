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
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.IMeterDetectable;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import gregapi.block.multitileentity.IWailaTile;
import gregapi.code.ArrayListNoNulls;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.OP;
import gregapi.data.TD;
import gregapi.gui.ContainerClientDefault;
import gregapi.gui.ContainerCommonDefault;
import gregapi.oredict.OreDictItemData;
import gregapi.oredict.OreDictMaterialStack;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.multiblocks.IMultiBlockEnergy;
import gregapi.tileentity.multiblocks.IMultiBlockInventory;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import gregapi.util.OM;
import gregapi.util.UT;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.*;

import static gregapi.data.CS.*;

public class QuantumStorage extends TileEntityBase10MultiBlockBase implements IInventory, IMultiBlockInventory, ITileEntityEnergy, IMultiBlockEnergy, IWailaTile {
    public boolean mStopped = false;
    public long mEnergy = 0, mInputMax = 1024, mEnergyBaseConsume = 100;
    public List<IMeterDetectable.MeterData> receivedEnergy = new ArrayList<>(), receivedEnergyLast = new ArrayList<>();
    public TagData mEnergyType = TD.Energy.EU;
    protected List<OreDictMaterialStack> mContent = new ArrayListNoNulls<>();
    public static long MAX_AMOUNT = 1024*64*U;

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if(aNBT.hasKey(NBT_ENERGY))mEnergy = aNBT.getLong(NBT_ENERGY);
        if(aNBT.hasKey(NBT_INPUT_MAX))mInputMax = aNBT.getLong(NBT_INPUT_MAX);
        mContent = OreDictMaterialStack.loadList(NBT_MATERIALS, aNBT);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergy);
        OreDictMaterialStack.saveList(NBT_MATERIALS, aNBT, mContent);
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        super.addToolTips(aList, aStack, aF3_H);
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
    }

    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide())return true;

        if(!mStructureOkay)aPlayer.addChatMessage(new ChatComponentText(LH.Chat.RED+LH.get(I18nHandler.STRUCTURE_ERR)));

        ItemStack equippedItem=aPlayer.getCurrentEquippedItem();
        if (equippedItem!=null && equippedItem.getItem() instanceof ItemProjector) {
            structure.checkStructure(new StructureContext(this, StructureContext.StringBaseMode.PROJECT, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, null));
            return true;
        }
        openGUI(aPlayer, aSide);
        return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
    }
    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if (!aIsServerSide) return;
        receivedEnergyLast = receivedEnergy;
        receivedEnergy = new ArrayList<>();
        mEnergy -= mEnergyBaseConsume;
        if (mEnergy > 0) return;

        mContent.clear();
    }
    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if (mStopped) return 0;
        aSize = Math.abs(aSize);
        if (aEnergyType == mEnergyType) {
            if(mEnergy > mEnergyBaseConsume * 20 * 60)return 0;
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
            .where('X', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18006, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID_ENERGY_IN)))
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

    @Override public byte getDefaultSide() {return SIDE_FRONT;}

    //inventory
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[27];}
    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return UT.Code.getAscendingArray(27);}
    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {return true;}
    @Override public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {return getStoreData(aStack) != null;}

    @Override public String getInventoryName() {return "Quantum Storages";}
    @Override public int getSizeInventory() {return 27;}


    public OreDictMaterialStack getStoreData(ItemStack stack){
        OreDictItemData tData = OM.anydata_(slot(0));
        if(tData == null)return null;
        //stack only have 1 material && stack is any dust
        if(tData.mPrefix != null && (tData.mPrefix == OP.dust || tData.mPrefix == OP.dustSmall || tData.mPrefix == OP.dustTiny || tData.mPrefix == OP.dustDiv72))return tData.mMaterial;
        return null;
    }

    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {return new ContainerClientDefault(aPlayer.inventory, this, aGUIID);}
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {return new ContainerCommonDefault(aPlayer.inventory, this, aGUIID);}
    @Override
    public boolean onTickCheck(long aTimer) {
        return super.onTickCheck(aTimer);
    }
    @Override
    public NBTTagCompound getWailaNBT(TileEntity te, NBTTagCompound aNBT) {
        IWailaTile.super.getWailaNBT(te, aNBT);
        aNBT.setInteger("mTemp.c", (int)Math.floor(0));
        return aNBT;
    }

    @Override
    public List<String> getWailaBody(List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        IWailaTile.super.getWailaBody(currentTip, accessor, config);
        currentTip.add(LH.get(I18nHandler.TEMPERATURE+".core")+ " " + LH.Chat.WHITE + accessor.getNBTData().getInteger("mTemp.c"));
        return currentTip;
    }
    @Override public String getTileEntityName() {return "ktfru.multitileentity.multiblock.storage.quantum";}
}
