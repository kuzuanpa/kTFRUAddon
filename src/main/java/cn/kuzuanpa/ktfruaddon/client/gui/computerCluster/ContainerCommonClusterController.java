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

package cn.kuzuanpa.ktfruaddon.client.gui.computerCluster;

import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputerClusterClientData;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputerCluster;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterController;
import gregapi.gui.ContainerCommon;
import gregapi.gui.Slot_Render;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ContainerCommonClusterController extends ContainerCommon {

	public ContainerCommonClusterController(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, int aGUIID) {
		super(aInventoryPlayer, aTileEntity, aGUIID);
	}

	@Override
	public int addSlots(InventoryPlayer aPlayerInventory) {
		int tIndex =0;
		addSlotToContainer(new Slot_Render(mTileEntity, tIndex++, 80, 132));

		return 154;
	}

	protected static final int playerInvXOffset = 23;

	protected void bindPlayerInventory(InventoryPlayer aInventoryPlayer, int aOffset) {
		for (int i = 0; i < 3; i++) for (int j = 0; j < 9; j++) {
			addSlotToContainer(new Slot(aInventoryPlayer, j + i * 9 + 9, playerInvXOffset + 8 + j * 18, aOffset + i * 18));
		}
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(aInventoryPlayer, i, playerInvXOffset + 8 + i * 18, aOffset + 58));
		}
	}
	public ComputerClusterClientData.ControllerList   dataControllerList ;
	public ComputerClusterClientData.UserList         dataUserList;
	public ComputerClusterClientData.ClusterDetail    dataClusterDetail ;
	public ComputerClusterClientData.ControllerDetail dataControllerDetail ;

	public boolean updated = false;

	public @Nullable UUID getClusterUUID() {
		IComputerClusterController controller = (IComputerClusterController) mTileEntity;
		if (controller.getCluster() != null) return controller.getCluster().clusterUUID;
		return controller.getSavedClusterUUID();
	}

	public @Nullable UUID getControllerUUID() {
		return ((IComputerClusterController) mTileEntity).getUUID();
	}

	public void updateFromClientCache() {
		UUID clusterUUID = getClusterUUID();
		if (clusterUUID == null) return;
		ComputerClusterClientData.ClusterSnapshot snapshot = ComputerCluster.getClientSnapshot(clusterUUID);
		if (snapshot == null) return;
		dataControllerList = snapshot.controllerList;
		dataUserList = snapshot.userList;
		dataClusterDetail = snapshot.clusterDetail;
		dataControllerDetail = snapshot.controllerDetail;
		updated = true;
	}

	@Override public int getStartIndex() {return 0;}
	@Override public int getSlotCount() {return 1;}
	@Override public int getShiftClickStartIndex() {return 0;}
	@Override public int getShiftClickSlotCount() {return 1;}
}
