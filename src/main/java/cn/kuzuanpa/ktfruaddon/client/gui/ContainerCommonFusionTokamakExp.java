/**
 * Copyright (c) 2021 GregTech-6 Team
 *
 * This file is part of GregTech.
 *
 * GregTech is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GregTech is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with GregTech. If not, see <http://www.gnu.org/licenses/>.
 */

package cn.kuzuanpa.ktfruaddon.client.gui;

import cn.kuzuanpa.ktfruaddon.tile.multiblock.fusionReactorTokamakExp;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregapi.data.LH;
import gregapi.gui.ContainerCommon;
import gregapi.gui.Slot_Normal;
import gregapi.gui.Slot_Render;
import gregapi.recipes.Recipe;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;

import java.util.List;

public class ContainerCommonFusionTokamakExp extends ContainerCommon {
	private Recipe.RecipeMap mRecipes;

	public ContainerCommonFusionTokamakExp(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, Recipe.RecipeMap aRecipes, int aGUIID) {
		super(aInventoryPlayer, aTileEntity, aGUIID);
		mRecipes = aRecipes;
	}
	
	@Override
	public int addSlots(InventoryPlayer aPlayerInventory) {
		mRecipes = ((fusionReactorTokamakExp)mTileEntity).mRecipes;
		int tIndex =0;
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 151, 4));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 133, 62));

		addSlotToContainer(new Slot_Render(mTileEntity, tIndex++, 85,62).setTooltip("Extract using a Tap or Nozzle", LH.Chat.WHITE));
		addSlotToContainer(new Slot_Render(mTileEntity, tIndex++, 103,62).setTooltip("Extract using a Tap or Nozzle", LH.Chat.WHITE));
		addSlotToContainer(new Slot_Render(mTileEntity, tIndex++, 151,62).setTooltip("Extract using a Tap or Nozzle", LH.Chat.WHITE));

		return super.addSlots(aPlayerInventory);
	}

	public short mState = 0;
	public short mFieldStrength = 0;
	public short mTemperature = 0;
	public short mProgressBar = 0;

	public short mDensity = 0;

	@Override
	@SuppressWarnings("unchecked")
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (ICrafting tUpdate : (List<ICrafting>)crafters) {
			tUpdate.sendProgressBarUpdate(this, 0, ((fusionReactorTokamakExp)mTileEntity).mState);
			tUpdate.sendProgressBarUpdate(this, 1, ((fusionReactorTokamakExp)mTileEntity).mFieldStrength);
			tUpdate.sendProgressBarUpdate(this, 2, ((fusionReactorTokamakExp)mTileEntity).clientTemp());
			tUpdate.sendProgressBarUpdate(this, 3, ((fusionReactorTokamakExp)mTileEntity).clientProgress());
			tUpdate.sendProgressBarUpdate(this, 4, ((fusionReactorTokamakExp)mTileEntity).clientDensity());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int aIndex, int aValue) {
		super.updateProgressBar(aIndex, aValue);
		switch (aIndex) {
			case 0: mState = (short)aValue; break;
			case 1: mFieldStrength = (short)aValue; break;
			case 2: mTemperature = (short)aValue; break;
			case 3: mProgressBar = (short)aValue; break;
			case 4: mDensity = (short)aValue; break;
		}
	}

	@Override public int getStartIndex() {return 0;}
	@Override public int getSlotCount() {return 2;}
	@Override public int getShiftClickStartIndex() {return 0;}
	@Override public int getShiftClickSlotCount() {return 2;}
}
