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
import cn.kuzuanpa.ktfruaddon.api.tile.util.kTileNBT;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.code.TagData;
import gregapi.data.FL;
import gregapi.data.FM;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.fluid.FluidTankGT;
import gregapi.gui.ContainerClientDefault;
import gregapi.gui.ContainerCommonDefault;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.old.Textures;
import gregapi.recipes.Recipe;
import gregapi.render.BlockTextureDefault;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.ITileEntityUnloadable;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.machines.ITileEntitySwitchableOnOff;
import gregapi.tileentity.multiblocks.*;
import gregapi.util.UT;
import gregapi.util.WD;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;
import zmaster587.libVulpes.items.ItemProjector;

import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.*;

public class ExpandedMotor extends TileEntityBase10MultiBlockBase implements IMultiBlockFluidHandler,IMultiBlockEnergy, IFluidHandler, ITileEntitySwitchableOnOff, IMultiBlockInventory, IMultiTileEntity.IMTE_SyncDataByte {
	public short mTurbineWalls = 18022;
	public long mEnergyStored=0,mRate=0,mRateMax=0, mAmpere = 0;
	public boolean mActive=false,mForcedStopped=false;
	public static final IIconContainer mTextureActive   = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/turbine_active");
	public static final IIconContainer mTextureInactive = new Textures.BlockIcons.CustomIcon("machines/multiblockmains/turbine");
	protected TagData mEnergyTypeEmitted= TD.Energy.RU;

	public FluidTankGT mInputTank = new FluidTankGT(), mTanksOutput[] = new FluidTankGT[] {new FluidTankGT(), new FluidTankGT(), new FluidTankGT()};
	public FluidTankGT[] mTanks = new FluidTankGT[] {mInputTank, mTanksOutput[0], mTanksOutput[1], mTanksOutput[2]};
	public Recipe.RecipeMap mRecipes = FM.Gas;
	public Recipe mLastRecipe = null;

	@Override
	public void readFromNBT2(NBTTagCompound aNBT) {
		super.readFromNBT2(aNBT);

		if (aNBT.hasKey(NBT_DESIGN)) mTurbineWalls = aNBT.getShort(NBT_DESIGN);
		if (aNBT.hasKey(NBT_OUTPUT)) mRate = aNBT.getLong(NBT_OUTPUT);
		if (aNBT.hasKey(NBT_OUTPUT_MAX)) mRateMax = aNBT.getLong(NBT_OUTPUT_MAX);
		else mRateMax=mRate;
		if (aNBT.hasKey(kTileNBT.MAX_AMPERE)) mAmpere = aNBT.getLong(kTileNBT.MAX_AMPERE);
		if (aNBT.hasKey(NBT_ENERGY)) mEnergyStored = aNBT.getLong(NBT_ENERGY);
		if (aNBT.hasKey(NBT_FUELMAP)) mRecipes = Recipe.RecipeMap.RECIPE_MAPS.get(aNBT.getString(NBT_FUELMAP));
		for (int i = 0; i < mTanksOutput.length; i++) mTanksOutput[i].readFromNBT(aNBT, NBT_TANK+"."+i).setCapacity(mRateMax*16);
		mInputTank.readFromNBT(aNBT, NBT_TANK).setCapacity(mRateMax*4);
		structure = new LayerStructure(StructureContext.Axis.Y).layerRule("BA")
				.fixedLayer('A',
						"AAA",
						"AAA"
				).fixedLayer('B',
						"AAB",
						"AAA"
				)
				.where('A', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, mTurbineWalls, MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID)))
				.where('B', new PartPredicate(new TileDesc(GTTileEntityRegistry.gregtech, mTurbineWalls, MultiTileEntityMultiBlockPart.ONLY_ENERGY_OUT, 4)))
				.setOffset(0,0,0) ;
	}
	@Override
	public void writeToNBT2(NBTTagCompound aNBT) {
		super.writeToNBT2(aNBT);
		UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergyStored);
		for (int i = 0; i < mTanksOutput.length; i++) mTanksOutput[i].writeToNBT(aNBT, NBT_TANK+"."+i);
		mInputTank.writeToNBT(aNBT, NBT_TANK);
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

		return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
	}
	
	@Override
	public boolean allowCovers(byte aSide) {
		return aSide != mFacing;
	}

	@Override
	public boolean isInsideStructure(int aX, int aY, int aZ) {
		return true;
	}
	
	@Override
	public int getRenderPasses2(Block aBlock, boolean[] aShouldSideBeRendered) {
		return mStructureOkay ? 2 : 1;
	}
	
	@Override
	public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
		return aRenderPass == 0 ? super.getTexture2(aBlock, aRenderPass, aSide, aShouldSideBeRendered) : aSide != mFacing ? null : BlockTextureDefault.get(mActive ? mTextureActive : mTextureInactive);
	}


	public void onTick2(long aTimer, boolean aIsServerSide) {
		super.onTick2(aTimer, aIsServerSide);
		if (!aIsServerSide)return;
		if(!mStructureOkay || mForcedStopped) {setActive(false); return;}
		updateClientData();

		if(mEnergyStored<0)mEnergyStored=0;
		doConversion(aTimer);
		long ampere = 4;
		if(mEnergyStored >= mRate*ampere){
			setActive(true);
			long consumed = ITileEntityEnergy.Util.insertEnergyInto(mEnergyTypeEmitted, getEmittingSide(), Math.min(mRate,mEnergyStored), ampere, this, getEmittingTileEntity());
			mEnergyStored-= (long) (mRate*consumed);
		}else setActive(false);
	}

	public void doConversion(long aTimer) {
		for (FluidTankGT tank : mTanksOutput) if (tank.has()) {
			ChunkCoordinates pos = getOffset(OPOS[mFacing], 4);
			pos.posY-=1;
			FL.move(tank, WD.te(worldObj,pos,mFacing,false));
			if (FL.gas(tank) && !WD.hasCollide(worldObj, pos)) tank.setEmpty();
		}
		if (!mForcedStopped && mInputTank.has() && mTanksOutput[0].underHalf() && mTanksOutput[1].underHalf() && mTanksOutput[2].underHalf()) {
			Recipe tRecipe = mRecipes.findRecipe(this, mLastRecipe, F, Integer.MAX_VALUE, NI, mInputTank.AS_ARRAY, ZL_IS);
			if (tRecipe != null) {
				mLastRecipe = tRecipe;
				if (tRecipe.mEUt < 0 && tRecipe.mDuration > 0) {
					int tMax = UT.Code.bindInt(UT.Code.divup(mRate*mAmpere - mEnergyStored, -tRecipe.mEUt * tRecipe.mDuration));
					int tParallel = tRecipe.isRecipeInputEqual(tMax, mInputTank.AS_ARRAY, ZL_IS);
					if (tParallel < tMax) mInputTank.setEmpty();
					if (tParallel > 0) {
						mEnergyStored -= tParallel * tRecipe.mEUt * tRecipe.mDuration;
						for (int i = 0; i < tRecipe.mFluidOutputs.length && i < mTanksOutput.length; i++) {
							if (!mTanksOutput[i].fillAll(tRecipe.mFluidOutputs[i], tParallel)) {
								mEnergyStored = 0;
							}
						}
						return;
					}
				}
			}
		}

		if (mEnergyStored < 0) mEnergyStored = 0;
	}

	public ITileEntityUnloadable mEmittingTo = null;

	public TileEntity getEmittingTileEntity() {if (mEmittingTo == null || mEmittingTo.isDead()) {mEmittingTo = null; TileEntity tTileEntity = getTileEntityAtSideAndDistance(OPOS[mFacing], 3); if (tTileEntity instanceof ITileEntityUnloadable) mEmittingTo = (ITileEntityUnloadable)tTileEntity;} return mEmittingTo == null ? this : (TileEntity) mEmittingTo;}
	public byte getEmittingSide() {return mFacing;}

	@Override public byte getDefaultSide() {return SIDE_FRONT;}
	@Override public boolean[] getValidSides() {return SIDES_VALID;}
	
	@Override public boolean isEnergyType                   (TagData aEnergyType, byte aSide, boolean aEmitting) {return aEmitting && mEnergyTypeEmitted.equals(aEnergyType);}
	@Override public boolean isEnergyAcceptingFrom          (TagData aEnergyType, byte aSide, boolean aTheoretical) {return F;}

	@Override public boolean isEnergyEmittingTo(TagData aEnergyType, byte aSide, boolean aTheoretical) {return isEnergyType(aEnergyType, aSide, T);}

	@Override public boolean canDrop(int aInventorySlot) {return T;}
	@Override
	public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
		aList.add((LH.Chat.RED + LH.get("gt.lang.energy.output") + ": " + LH.Chat.WHITE + this.mRate + " " + LH.Chat.WHITE +(mRateMax > mRate ?"(up to "+mRateMax+" )":"") + mEnergyTypeEmitted.getLocalisedChatNameShort()+ LH.Chat.WHITE+"/A * "+ LH.Chat.CYAN + mAmpere +"A/t"));
		aList.add(LH.Chat.GRAY+ LH.get(LH.TOOL_TO_TOGGLE_SCREWDRIVER));
		super.addToolTips(aList, aStack, aF3_H);
	}

	@Override
	public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
		if (aTool.equals(TOOL_plunger)) {
			if (mTanksOutput[0].has()) return GarbageGT.trash(mTanksOutput[0]);
			if (mTanksOutput[1].has()) return GarbageGT.trash(mTanksOutput[1]);
			if (mTanksOutput[2].has()) return GarbageGT.trash(mTanksOutput[2]);
			return GarbageGT.trash(mInputTank);
		}
		return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
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

	@Override
	public boolean setStateOnOff(boolean b) {
		this.mForcedStopped=!b;
		if(mActive)setActive(!mForcedStopped);
		return b;
	}

	@Override
	public boolean getStateOnOff() {return !mForcedStopped;}

	public void setActive(boolean active) {
		boolean isStateChanged = mActive != active;
		this.mActive = active;
		if(isStateChanged) updateClientData();
	}

	@Override protected IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {return !mForcedStopped && mRecipes.containsInput(aFluidToFill, this, NI) ? mInputTank : null;}
	@Override protected IFluidTank[] getFluidTanks2(byte aSide) {return mTanks;}

	@Override
	protected IFluidTank getFluidTankDrainable2(byte aSide, FluidStack aFluidToDrain) {
		if (aFluidToDrain == null) {
			for (int i=0,j; i < mTanksOutput.length; i++) if (mTanksOutput[j = ((int)(SERVER_TIME/20)+i) % mTanksOutput.length].has()) return mTanksOutput[j];
		} else {
			for (int i = 0; i < mTanksOutput.length; i++) if (mTanksOutput[i].contains(aFluidToDrain)) return mTanksOutput[i];
		}
		return null;
	}
	@Override
	public String getTileEntityName() {
		return "ktfru.multitileentity.multiblock.motor.expanded";
	}

	public IPacket getClientDataPacket(boolean aSendAll) {
		return aSendAll ? this.getClientDataPacketByteArray(aSendAll, (byte) UT.Code.getR(this.mRGBa), (byte) UT.Code.getG(this.mRGBa), (byte) UT.Code.getB(this.mRGBa), this.getVisualData(), this.getDirectionData(), (byte)(mActive?1:0)) : this.getClientDataPacketByte(aSendAll, this.getVisualData());
	}
	public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler){
		super.receiveDataByteArray(aData,aNetworkHandler);
		mActive=aData[5]==1;
		return true;
	}
}
