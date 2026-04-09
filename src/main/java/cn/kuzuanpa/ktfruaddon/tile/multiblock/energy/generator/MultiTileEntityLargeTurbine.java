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


package cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.generator;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.mode.layer.LayerStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate.PartPredicate;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.item.items.itemTurbine;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.block.multitileentity.IWailaTile;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.gui.ContainerClientDefault;
import gregapi.gui.ContainerCommonDefault;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.old.Textures;
import gregapi.oredict.OreDictMaterial;
import gregapi.render.BlockTextureDefault;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.ITileEntityUnloadable;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.machines.ITileEntityRunningActively;
import gregapi.tileentity.machines.ITileEntityRunningPowerSaving;
import gregapi.tileentity.machines.ITileEntitySwitchableOnOff;
import gregapi.tileentity.multiblocks.*;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.IFluidHandler;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.*;

public abstract class MultiTileEntityLargeTurbine extends TileEntityBase10MultiBlockBase implements IMultiBlockFluidHandler,IMultiBlockEnergy, IFluidHandler, ITileEntitySwitchableOnOff, IMultiBlockInventory, IMultiTileEntity.IMTE_SyncDataByte, IWailaTile, ITileEntityRunningActively, ITileEntityRunningPowerSaving {
	public short mTurbineWalls = 18022;
	public long mEnergyStored=0,mRate=0,mRateMax=0,mTurbineDurability = 0;
	public float mTurbineEfficiency=0;
	public boolean mOverclock=false,mActive=false,mForcedStopped=false, isTurbineAboutToBreak=false, usingCheckedTurbine=false, outputting = false;
	public static final IIconContainer mTextureActive   = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/turbine_active");
	public static final IIconContainer mTextureInactive = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/turbine");
	protected TagData mEnergyTypeEmitted= TD.Energy.RU;

	@Override
	public void readFromNBT2(NBTTagCompound aNBT) {
		super.readFromNBT2(aNBT);

		if (aNBT.hasKey(NBT_DESIGN)) mTurbineWalls = aNBT.getShort(NBT_DESIGN);
		if (aNBT.hasKey(NBT_OUTPUT)) mRate = aNBT.getLong(NBT_OUTPUT);
		if (aNBT.hasKey(NBT_OUTPUT_MAX)) mRateMax = aNBT.getLong(NBT_OUTPUT_MAX);
		else mRateMax=mRate;
		if (aNBT.hasKey(NBT_ENERGY)) mEnergyStored = aNBT.getLong(NBT_ENERGY);
		if (aNBT.hasKey("ktfru.turbine.duration")) mTurbineDurability = aNBT.getLong("ktfru.turbine.duration");
		if (aNBT.hasKey("ktfru.turbine.efficiency")) mTurbineEfficiency = aNBT.getLong("ktfru.turbine.efficiency") / 1000F;
		if (aNBT.hasKey("ktfru.turbine.checked")) usingCheckedTurbine = aNBT.getBoolean("ktfru.turbine.checked");
		if (aNBT.hasKey("ktfru.turbine.overclock")) mOverclock = aNBT.getBoolean("ktfru.turbine.overclock");
		structure = new LayerStructure(StructureContext.Axis.Y).layerRule("ABC")
				.fixedLayer('A',
						"AAAA",
						"AAAC",
						"AAAA"
				).fixedLayer('B',
						"AAAA",
						"AAAB",
						"AAAA"
				).fixedLayer('C',
						"AAAA",
						"AAAA",
						"AAAA"
				)
				.where('A', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, mTurbineWalls, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID)))
				.where('B', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, mTurbineWalls, MultiTileEntityMultiBlockPart.ONLY_ENERGY_OUT, 4)))
				.where('C', new PartPredicate(new TileDesc(GTTileEntityRegistry.ktfruaddon, mTurbineWalls, MultiTileEntityMultiBlockPart.ONLY_OUT, 7)))
				.setOffset(-1,-1,0) ;
	}
	@Override
	public void writeToNBT2(NBTTagCompound aNBT) {
		super.writeToNBT2(aNBT);
		UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergyStored);
		UT.NBT.setNumber(aNBT,"ktfru.turbine.duration", mTurbineDurability);
		UT.NBT.setNumber(aNBT,"ktfru.turbine.efficiency", (long)(mTurbineEfficiency * 1000L));
		UT.NBT.setBoolean(aNBT,"ktfru.turbine.checked",usingCheckedTurbine);
		UT.NBT.setBoolean(aNBT,"ktfru.turbine.overclock",mOverclock);
	}
	//Structure
	ChunkCoordinates lastFailedPos=null;
	IStringBaseStructure structure;
	@Override
	public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
		int tX = xCoord, tY = yCoord, tZ = zCoord;
		if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
		lastFailedPos = structure.checkStructure(new StructureContext(this, (aPlayer != null || aInventory != null)? StructureContext.StringBaseMode.SET: StructureContext.StringBaseMode.CHECK, worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
		return lastFailedPos==null;
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
	public boolean allowCovers(byte aSide) {
		return aSide != mFacing;
	}
	
	@Override
	public boolean isInsideStructure(int aX, int aY, int aZ) {
		return
		aX >= xCoord-(SIDE_X_NEG==mFacing?0:SIDE_X_POS==mFacing?3:1) &&
		aY >= yCoord-(SIDE_Y_NEG==mFacing?0:SIDE_Y_POS==mFacing?3:1) &&
		aZ >= zCoord-(SIDE_Z_NEG==mFacing?0:SIDE_Z_POS==mFacing?3:1) &&
		aX <= xCoord+(SIDE_X_POS==mFacing?0:SIDE_X_NEG==mFacing?3:1) &&
		aY <= yCoord+(SIDE_Y_POS==mFacing?0:SIDE_Y_NEG==mFacing?3:1) &&
		aZ <= zCoord+(SIDE_Z_POS==mFacing?0:SIDE_Z_NEG==mFacing?3:1);
	}
	
	@Override
	public int getRenderPasses2(Block aBlock, boolean[] aShouldSideBeRendered) {
		return mStructureOkay ? 2 : 1;
	}
	
	@Override
	public boolean setBlockBounds2(Block aBlock, int aRenderPass, boolean[] aShouldSideBeRendered) {
		if (aRenderPass == 1) switch(mFacing) {
		case SIDE_X_NEG: case SIDE_X_POS: return box(aBlock, -0.001, -0.999, -0.999,  1.001,  1.999,  1.999);
		case SIDE_Y_NEG: case SIDE_Y_POS: return box(aBlock, -0.999, -0.001, -0.999,  1.999,  1.001,  1.999);
		case SIDE_Z_NEG: case SIDE_Z_POS: return box(aBlock, -0.999, -0.999, -0.001,  1.999,  1.999,  1.001);
		}
		return F;
	}
	
	@Override
	public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
		return aRenderPass == 0 ? super.getTexture2(aBlock, aRenderPass, aSide, aShouldSideBeRendered) : aSide != mFacing ? null : BlockTextureDefault.get(mActive ? mTextureActive : mTextureInactive);
	}

	public abstract void doConversion(long aTimer);

	public void onTick2(long aTimer, boolean aIsServerSide) {
		super.onTick2(aTimer, aIsServerSide);
		if (!aIsServerSide)return;
		outputting = false;
		if (slot(0)== null) mTurbineDurability =0;

		if(!mStructureOkay || !slotHas(0) || mForcedStopped) {setActive(false); return;}
		updateClientData();
		if(mEnergyStored<0)mEnergyStored=0;
		if(!mActive&&mTurbineEfficiency==0&&slotHas(0)) mTurbineEfficiency = itemTurbine.getTurbineEfficiency(OreDictMaterial.get(slot(0).getItemDamage()));
		doConversion(aTimer);
		float factor = mOverclock? (float) (mTurbineEfficiency / Math.floor(mTurbineEfficiency)) :Math.min(mTurbineEfficiency,2);
		long ampere = mOverclock? (long) Math.floor(mTurbineEfficiency) :1L;
		if(mEnergyStored >= mRate*factor*ampere){
			setActive(true);
			long consumed = ITileEntityEnergy.Util.insertEnergyInto(mEnergyTypeEmitted, getEmittingSide(), (long) Math.min(mRate*factor,mEnergyStored), ampere, this, getEmittingTileEntity());
			if(consumed > 0)outputting = true;
			mEnergyStored-= (long) (mRate*factor*consumed);
		}else setActive(false);
	}
	public ITileEntityUnloadable mEmittingTo = null;

	public TileEntity getEmittingTileEntity() {if (mEmittingTo == null || mEmittingTo.isDead()) {mEmittingTo = null; TileEntity tTileEntity = getTileEntityAtSideAndDistance(OPOS[mFacing], 4); if (tTileEntity instanceof ITileEntityUnloadable) mEmittingTo = (ITileEntityUnloadable)tTileEntity;} return mEmittingTo == null ? this : (TileEntity) mEmittingTo;}
	public byte getEmittingSide() {return mFacing;}

	@Override public byte getDefaultSide() {return SIDE_FRONT;}
	@Override public boolean[] getValidSides() {return SIDES_VALID;}
	
	@Override public boolean isEnergyType                   (TagData aEnergyType, byte aSide, boolean aEmitting) {return aEmitting && mEnergyTypeEmitted.equals(aEnergyType);}
	@Override public boolean isEnergyAcceptingFrom          (TagData aEnergyType, byte aSide, boolean aTheoretical) {return F;}

	@Override public boolean isEnergyEmittingTo(TagData aEnergyType, byte aSide, boolean aTheoretical) {return isEnergyType(aEnergyType, aSide, T);}

	@Override public boolean canDrop(int aInventorySlot) {return T;}
	//inventory
	@Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[1];}

	private static final int[] ACCESSIBLE_SLOTS = new int[] {0};

	@Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}

	@Override
	public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {
		if (aSlot >= 1||! isItemValidForSlot(aSlot, aStack)) return F;
		if (slot(0)== null) {
			mTurbineDurability =0;
			return T;
		}
		return F;
	}

	@Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {return isTurbineAboutToBreak || mForcedStopped;}

	@Override
	public boolean canTakeOutOfSlotGUI(int aSlot) {
		return isTurbineAboutToBreak || !mActive;
	}

	@Override
	public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
		aList.add((LH.Chat.RED + LH.get("gt.lang.energy.output") + ": " + LH.Chat.WHITE + this.mRate + " " + LH.Chat.WHITE +(mRateMax > mRate ?"(up to "+mRateMax+" )":"") + mEnergyTypeEmitted.getLocalisedChatNameShort()+ LH.Chat.WHITE+"/A * "+ LH.Chat.CYAN + "?A/t"));
		aList.add(LH.Chat.GRAY+ LH.get(LH.TOOL_TO_TOGGLE_SCREWDRIVER));
		super.addToolTips(aList, aStack, aF3_H);
	}

	@Override
	public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
		if (aTool.equals(TOOL_screwdriver)) {
			mOverclock=!mOverclock;
			aChatReturn.add(LH.Chat.ORANGE+LH.get(I18nHandler.OVERCLOCKING)+": "+mOverclock);
			return 1;
		}

		if (aTool.equals(TOOL_softhammer)) {
			mForcedStopped = !mForcedStopped;
			aChatReturn.add(LH.Chat.ORANGE + LH.get(LH.STATE_STOPPED_FORCE) + ": " + mForcedStopped);
			return 1;
		}
		return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
	}

	public abstract void transformTurbineItem();

	static final byte TURBINE_STEAM=0,TURBINE_GAS=1;

	public void damageTurbine(long amount, byte turbineType){
		if(!usingCheckedTurbine&&mTimer%20==0&&getRandomNumber(1000)==1)explode(3);
		if(mTurbineDurability == 0) {
			transformTurbineItem();
			isTurbineAboutToBreak=false;
		}
		if(!isTurbineAboutToBreak && mTurbineDurability < -amount*1200){
			isTurbineAboutToBreak=true;
		}
		if(mTurbineDurability < 10) setStateOnOff(false);
		mTurbineDurability =Math.max(1, mTurbineDurability + amount*100);
	}

	@Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {
		return new ContainerClientDefault(aPlayer.inventory, this, aGUIID);
	}
	@Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {
		return new ContainerCommonDefault(aPlayer.inventory, this, aGUIID);
	}

	@Override public long getEnergySizeOutputRecommended(TagData aEnergyType, byte aSide) {return mRate;}
	@Override public long getEnergySizeOutputMin(TagData aEnergyType, byte aSide) {return mRate;}
	@Override public long getEnergySizeOutputMax(TagData aEnergyType, byte aSide) {return mRateMax;}
	@Override public Collection<TagData> getEnergyTypes(byte aSide) {return mEnergyTypeEmitted.AS_LIST;}

	@Override public boolean getStateRunningPowerSaving() {return mActive && !outputting;}
	@Override public boolean getStateRunningPossible() {return !mActive && mStructureOkay && mTurbineDurability > 0;}
	@Override public boolean getStateRunningPassively() {return mActive;}
	@Override public boolean getStateRunningActively() {return mActive;}

	@Override public boolean setStateOnOff(boolean b) {
		this.mForcedStopped=!b;
		if(mActive)setActive(!mForcedStopped);
		return b;
	}

	@Override
	public boolean getStateOnOff() {return !mForcedStopped;}

	public void setActive(boolean active) {
		this.mActive = active;
		updateClientData();
	}

	public IPacket getClientDataPacket(boolean aSendAll) {
		return aSendAll ? this.getClientDataPacketByteArray(aSendAll, (byte) UT.Code.getR(this.mRGBa), (byte) UT.Code.getG(this.mRGBa), (byte) UT.Code.getB(this.mRGBa), this.getVisualData(), this.getDirectionData(), (byte)(mActive?1:0)) : this.getClientDataPacketByte(aSendAll, this.getVisualData());
	}
	public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler){
		super.receiveDataByteArray(aData,aNetworkHandler);
		mActive= aData.length >= 6 && aData[5] == 1;
		return true;
	}

	@Override
	public List<IWailaInfoProvider> getWailaInfos(List<IWailaInfoProvider> current) {
		current.add(IWailaTile.instanceInfoState);
		current.add(IWailaTile.instanceInfoEnergyIORange);
		return current;
	}
}
