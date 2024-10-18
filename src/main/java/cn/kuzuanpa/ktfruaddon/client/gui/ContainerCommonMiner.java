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

import gregapi.gui.ContainerCommon;
import gregapi.gui.Slot_Normal;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerCommonMiner extends ContainerCommon {

	public ContainerCommonMiner(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, int aGUIID) {
		super(aInventoryPlayer, aTileEntity, aGUIID);
	}
	
	@Override
	public int addSlots(InventoryPlayer aPlayerInventory) {
		int tIndex =0;
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 80, 4));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 8, 40));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 26, 40));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 44, 40));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 62, 40));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 80, 40));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 98, 40));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 116, 40));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 134, 40));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 152, 40));
		return super.addSlots(aPlayerInventory);
	}

	@Override public int getStartIndex() {return 0;}
	@Override public int getSlotCount() {return 10;}
	@Override public int getShiftClickStartIndex() {return 0;}
	@Override public int getShiftClickSlotCount() {return 10;}
}
