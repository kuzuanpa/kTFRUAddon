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


package cn.kuzuanpa.ktfruaddon.client.gui.research;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregapi.gui.ContainerCommon;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;

import java.util.List;

public class ContainerCommonResearchTreeMonitor extends ContainerCommon {

	public ContainerCommonResearchTreeMonitor(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, int aGUIID) {
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

		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int aIndex, int aValue) {
		super.updateProgressBar(aIndex, aValue);
		switch (aIndex) {
		}
	}

	@Override public int getStartIndex() {return 0;}
	@Override public int getSlotCount() {return 0;}
	@Override public int getShiftClickStartIndex() {return 0;}
	@Override public int getShiftClickSlotCount() {return 0;}
}
