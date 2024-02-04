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

package cn.kuzuanpa.ktfruaddon.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregapi.gui.ContainerCommon;
import gregapi.tileentity.ITileEntityInventoryGUI;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import gregapi.util.UT;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;

import java.util.List;

import static gregapi.data.CS.T;

public class ContainerCommonDysonSphereMonitor extends ContainerCommon {

	public ContainerCommonDysonSphereMonitor(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, int aGUIID) {
		super(aInventoryPlayer, aTileEntity, aGUIID);
	}
	
	@Override
	public int addSlots(InventoryPlayer aPlayerInventory) {
		int tIndex = 0;

		//addSlotToContainer(new Slot_Normal(mTileEntity, 0, 49, 26));
		//addSlotToContainer(new Slot_Normal(mTileEntity, 1, 148, 5));
		return super.addSlots(aPlayerInventory);
	}
	public boolean doesBindPlayerInventory() {return false;}

	public short mProgressBar = 0;
	
	@Override
	@SuppressWarnings("unchecked")
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (ICrafting tUpdate : (List<ICrafting>)crafters) {
			if (((MultiTileEntityBasicMachine)mTileEntity).mSuccessful) {
				tUpdate.sendProgressBarUpdate(this, 0, Short.MAX_VALUE);
			} else if (((MultiTileEntityBasicMachine)mTileEntity).mMaxProgress > 0) {
				tUpdate.sendProgressBarUpdate(this, 0, (short)UT.Code.units(Math.min(((MultiTileEntityBasicMachine)mTileEntity).mMaxProgress, ((MultiTileEntityBasicMachine)mTileEntity).mProgress), ((MultiTileEntityBasicMachine)mTileEntity).mMaxProgress, Short.MAX_VALUE, T));
			} else {
				tUpdate.sendProgressBarUpdate(this, 0, -1);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int aIndex, int aValue) {
		super.updateProgressBar(aIndex, aValue);
		switch (aIndex) {
		case 0: mProgressBar = (short)aValue; break;
		}
	}

	@Override public int getStartIndex() {return 0;}
	@Override public int getSlotCount() {return 0;}
	@Override public int getShiftClickStartIndex() {return 0;}
	@Override public int getShiftClickSlotCount() {return 0;}
}
