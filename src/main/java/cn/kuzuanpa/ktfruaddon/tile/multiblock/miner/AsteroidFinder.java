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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.miner;

import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.IMeterDetectable;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.layerType.ExpandableLayer;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.SkyPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.kTileNBT;
import gregapi.block.multitileentity.IWailaTile;
import gregapi.code.ArrayListNoNulls;
import gregapi.code.TagData;
import gregapi.data.TD;
import gregapi.gui.ContainerClientDefault;
import gregapi.gui.ContainerCommonDefault;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.multiblocks.IMultiBlockEnergy;
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
import net.minecraft.util.ChunkCoordinates;
import zmaster587.advancedRocketry.api.Configuration;
import zmaster587.advancedRocketry.util.AsteroidSmall;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static gregapi.data.CS.*;

public class AsteroidFinder extends TileEntityBase10MultiBlockBase implements ITileEntityEnergy, IMultiBlockEnergy, IWailaTile {
    public final double asteroidTotalProbability = Configuration.asteroidTypes.values().stream().mapToDouble(AsteroidSmall::getProbability).sum();
    public boolean mStopped = false, mOverwrite = false;
    public long mEnergy = 0, mInput = 256, mInputMax = 1024;
    public int interval = 10000, progress = 0;
    public List<IMeterDetectable.MeterData> receivedEnergy = new ArrayList<>(), receivedEnergyLast = new ArrayList<>();
    public TagData mEnergyType = TD.Energy.EU;
    public AsteroidSmall findedAsteroid;

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if(aNBT.hasKey(NBT_ENERGY))mEnergy = aNBT.getLong(NBT_ENERGY);
        if(aNBT.hasKey(NBT_INPUT)) mInput = aNBT.getLong(NBT_INPUT);
        if(aNBT.hasKey(NBT_INPUT_MAX)) mInputMax = aNBT.getLong(NBT_INPUT_MAX);
        if(aNBT.hasKey(kTileNBT.INTERVAL)) interval = aNBT.getInteger(kTileNBT.INTERVAL);
        if(aNBT.hasKey("findedAsteroid")) findedAsteroid = Configuration.asteroidTypes.get(aNBT.getString("findedAsteroid"));
        if(aNBT.hasKey("progress")) progress = aNBT.getInteger("progress");
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergy);
        UT.NBT.setNumber(aNBT, "progress", progress);
        if(findedAsteroid!=null)aNBT.setString("findedAsteroid", findedAsteroid.ID);
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if(!aIsServerSide)return;

        receivedEnergyLast=receivedEnergy;
        receivedEnergy=new ArrayList<>();

        if(!mStructureOkay || mEnergy < mInput){
            progress =0;
            return;
        }
        if(worldObj.isRaining())return;

        mEnergy -= mInput;

        if(findedAsteroid != null){
            if(slotHas(0) && OM.is(OD_USB_STICKS[0],slot(0))){
                NBTTagCompound tag = slot(0).getTagCompound();
                if(tag == null) tag = new NBTTagCompound();
                if(!mOverwrite && tag.hasKey("findedAsteroid"))return;

                tag.setString("findedAsteroid", findedAsteroid.ID);
                tag.setString("findedAsteroidUUID", UUID.randomUUID().toString());
                slot(0).setTagCompound(tag);
                findedAsteroid = null;
            }
            return;
        }

        progress++;
        if(progress < interval)return;

        float result = rng((int) Math.round(asteroidTotalProbability*100))/100F;
        for (AsteroidSmall asteroid : Configuration.asteroidTypes.values()) {
            result -= asteroid.getProbability();
            if(result > 0)continue;
            findedAsteroid = asteroid;
        }
        progress =0;
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (!isServerSide())return true;
        openGUI(aPlayer, aSide);
        return true;
    }

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if (mStopped) return 0;
        aSize = Math.abs(aSize);
        if (aEnergyType == mEnergyType) {
            aSize = Math.abs(aSize);
            if (aSize > getEnergySizeInputMax(aEnergyType, aSide)) {
                if (aDoInject) overcharge(aSize, aEnergyType);
                return aAmount;
            }
            long canReceiveAmount = (long) Math.ceil((mInput -mEnergy)*1F/aSize);
            long receiveAmount = Math.min(canReceiveAmount,aAmount);
            if (aDoInject) {
                mEnergy += receiveAmount * aSize;
                this.receivedEnergy.add(new IMeterDetectable.MeterData(aEnergyType, aSize, receiveAmount));
            }
        }
        return 0;
    }

    @Override public boolean isEnergyType (TagData aEnergyType, byte aSide, boolean aEmitting) {return !aEmitting && aEnergyType == mEnergyType;}

    @Override public long getEnergySizeInputMin(TagData aEnergyType, byte aSide) {return mInput;}
    @Override public long getEnergySizeInputRecommended (TagData aEnergyType, byte aSide) {return mInput;}
    @Override public long getEnergySizeInputMax(TagData aEnergyType, byte aSide) {return mInputMax;}

    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return new ArrayListNoNulls<>(F, mEnergyType);}

    //Structure
    ChunkCoordinates lastFailedPos=null;
    IStringBaseStructure structure = new LayerStructure(StructureContext.Axis.Y).layerRule("AXBCD")
            .fixedLayer('A',
                    " XXX ",
                    "XXXCX",
                    " CCXX",
                    "XXXXX",
                    " XXX "
            )
            .layer('X',
                    new ExpandableLayer(6).variation(
                            " XXX ",
                                   "X   X",
                                   "X C X",
                                   "X   X",
                                   " XXX "
                            )
            ).fixedLayer('B',
                    " XXX ",
                    "XXXXX",
                    "GGGGG",
                    "XXXXX",
                    " XXX "
            ).fixedLayer('C',
                    "SSXSS",
                    "SXXXS",
                    "SSSSS",
                    "SXXXS",
                    "SSXSS"
            ).fixedLayer('D',
                    "  S  ",
                    " SXS ",
                    "     ",
                    " SXS ",
                    "  S  "
            ).fixedLayer('E',
                    "     ",
                    "  S  ",
                    "     ",
                    "  S  ",
                    "     "
            )
            .where('X', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech  , 18002)))
            .where('C', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18006)))
            .where('G', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, 18006)))
            .where('S', new SkyPredicate())
            .setOffset(-2,0,0)
            .setFastAutoBuild(true);
    @Override
    public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = structure.checkStructure(new StructureContext(this, worldObj, xCoord, yCoord, zCoord, mFacing, (aPlayer != null || aInventory != null), aPlayer, aInventory));
        return lastFailedPos==null;
    }
    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return true;
    }

    @Override
    public boolean[] getValidSides() {
        return SIDES_HORIZONTAL;
    }

    //inventory
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[1];}
    private static final int[] ACCESSIBLE_SLOTS = new int[] {0};
    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}
    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {return true;}
    @Override public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {return true;}


    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {return new ContainerClientDefault(aPlayer.inventory, this, aGUIID);}
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {return new ContainerCommonDefault(aPlayer.inventory, this, aGUIID);}

    @Override
    public boolean onTickCheck(long aTimer) {
        return super.onTickCheck(aTimer);
    }

    @Override
    public NBTTagCompound getWailaNBT(TileEntity te, NBTTagCompound aNBT) {
        IWailaTile.super.getWailaNBT(te, aNBT);
        return aNBT;
    }

    @Override
    public List<String> getWailaBody(List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        IWailaTile.super.getWailaBody(currentTip, accessor, config);
        return currentTip;
    }
    @Override public String getTileEntityName() {return "ktfru.multitileentity.multiblock.asteroid.finder";}
}
