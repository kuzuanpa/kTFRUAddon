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

import cn.kuzuanpa.ktfruaddon.tile.multiblock.fusionReactorTokamakT1;
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

public class ContainerCommonFusionTokamakT1 extends ContainerCommon {
	private Recipe.RecipeMap mRecipes;

	public ContainerCommonFusionTokamakT1(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, Recipe.RecipeMap aRecipes, int aGUIID) {
		super(aInventoryPlayer, aTileEntity, aGUIID);
		mRecipes = aRecipes;

	}
	
	@Override
	public int addSlots(InventoryPlayer aPlayerInventory) {
		mRecipes = ((fusionReactorTokamakT1)mTileEntity).mRecipes;
		int tIndex =0;
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++,  12, 76));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++,  30, 76));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++,  48, 76));

		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 112, 76));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 130, 76));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 148, 76));

		addSlotToContainer(new Slot_Render(mTileEntity, tIndex++, 12,94).setTooltip("Extract using a Tap or Nozzle", LH.Chat.WHITE));
		addSlotToContainer(new Slot_Render(mTileEntity, tIndex++, 30,94).setTooltip("Extract using a Tap or Nozzle", LH.Chat.WHITE));
		addSlotToContainer(new Slot_Render(mTileEntity, tIndex++, 48,94).setTooltip("Extract using a Tap or Nozzle", LH.Chat.WHITE));
		addSlotToContainer(new Slot_Render(mTileEntity, tIndex++, 12,112).setTooltip("Extract using a Tap or Nozzle", LH.Chat.WHITE));
		addSlotToContainer(new Slot_Render(mTileEntity, tIndex++, 30,112).setTooltip("Extract using a Tap or Nozzle", LH.Chat.WHITE));
		addSlotToContainer(new Slot_Render(mTileEntity, tIndex++, 48,112).setTooltip("Extract using a Tap or Nozzle", LH.Chat.WHITE));

		addSlotToContainer(new Slot_Render(mTileEntity, tIndex++, 112,94).setTooltip("Extract using a Tap or Nozzle", LH.Chat.WHITE));
		addSlotToContainer(new Slot_Render(mTileEntity, tIndex++, 130,94).setTooltip("Extract using a Tap or Nozzle", LH.Chat.WHITE));
		addSlotToContainer(new Slot_Render(mTileEntity, tIndex++, 148,94).setTooltip("Extract using a Tap or Nozzle", LH.Chat.WHITE));
		addSlotToContainer(new Slot_Render(mTileEntity, tIndex++, 112,112).setTooltip("Extract using a Tap or Nozzle", LH.Chat.WHITE));
		addSlotToContainer(new Slot_Render(mTileEntity, tIndex++, 130,112).setTooltip("Extract using a Tap or Nozzle", LH.Chat.WHITE));
		addSlotToContainer(new Slot_Render(mTileEntity, tIndex++, 148,112).setTooltip("Extract using a Tap or Nozzle", LH.Chat.WHITE));

		return 137;
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
			tUpdate.sendProgressBarUpdate(this, 0, ((fusionReactorTokamakT1)mTileEntity).mState);
			tUpdate.sendProgressBarUpdate(this, 1, ((fusionReactorTokamakT1)mTileEntity).mFieldStrength);
			tUpdate.sendProgressBarUpdate(this, 2, ((fusionReactorTokamakT1)mTileEntity).clientTemp());
			tUpdate.sendProgressBarUpdate(this, 3, ((fusionReactorTokamakT1)mTileEntity).clientProgress());
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
		}
	}

	@Override public int getStartIndex() {return 0;}
	@Override public int getSlotCount() {return 2;}
	@Override public int getShiftClickStartIndex() {return 0;}
	@Override public int getShiftClickSlotCount() {return 2;}
}
