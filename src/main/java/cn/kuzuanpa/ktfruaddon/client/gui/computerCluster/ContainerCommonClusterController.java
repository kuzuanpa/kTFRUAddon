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

import cn.kuzuanpa.ktfruaddon.api.code.codeUtil;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputerClusterClientData;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterController;
import cpw.mods.fml.common.FMLLog;
import gregapi.gui.ContainerCommon;
import gregapi.gui.Slot_Render;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.util.*;

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
	@Override
	@SuppressWarnings("unchecked")
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (ICrafting crafter : (List<ICrafting>)crafters) {
            try {
				if(timer % 80 == 1)sendData(crafter);
				timer++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	int[][] cachedIntArray = new int[4][];
	int timer = 0;

	public void sendData(ICrafting crafter) throws IOException{
		IComputerClusterController controller = (IComputerClusterController) mTileEntity;
		if(controller.getCluster() == null)return;
		for (byte sendType = 0; sendType < 4; sendType++) {
			byte[] bytes = null;
			switch (sendType){
				case 0:  bytes = ComputerClusterClientData.UserList.serialize(controller.getCluster().fetchClientDataUserList()); break;
				case 1:  bytes = ComputerClusterClientData.ControllerList.serialize(controller.getCluster().fetchClientDataControllerList()); break;
				case 2:  bytes = ComputerClusterClientData.ClusterDetail.serialize(controller.getCluster().fetchClientDataClusterDetail()); break;
				case 3:  bytes = ComputerClusterClientData.ControllerDetail.serialize(controller.getCluster().fetchClientDataControllerDetail(controller.getUUID())); break;
			}
			int[] data = codeUtil.compressToIntegerArray(bytes);
			//Saves bandwidth
			//if(Arrays.equals(cachedIntArray[sendType], data))continue;

			cachedIntArray[sendType] = data;

			if(data.length > 16777214) {
				StringBuilder sb = new StringBuilder("data: ");
				Arrays.stream(data).forEach(sb::append);
				FMLLog.log(Level.FATAL, sb.toString());
				throw new RuntimeException("Too large data in Computer Cluster! Please report this bug to author");
			}

            for (int i = 0; i < data.length; i++) {
                int datum = data[i];
                crafter.sendProgressBarUpdate(this, sendType<<24|i, datum);
            }
			crafter.sendProgressBarUpdate(this, 5<<24, sendType<<24|data.length);
			}
	}

	private int[] orderedValues(Map<Integer, Integer> map) {
		return new TreeMap<>(map).values().stream().mapToInt(v -> v).toArray();
	}
	public ComputerClusterClientData.ControllerList   dataControllerList ;
	public ComputerClusterClientData.UserList         dataUserList;
	public ComputerClusterClientData.ClusterDetail    dataClusterDetail ;
	public ComputerClusterClientData.ControllerDetail dataControllerDetail ;

	Map<Integer,Integer> receivedDataClientList       = new HashMap<>();
	Map<Integer,Integer> receivedDataControllerList   = new HashMap<>();
	Map<Integer,Integer> receivedDataClusterDetail    = new HashMap<>();
	Map<Integer,Integer> receivedDataControllerDetail = new HashMap<>();
	int[] dataMaxIndex = new int[4];

	@Override
	public void updateProgressBar(int aIndex, int aValue) {
		try {
			int type = aIndex >>> 24;
			int index = aIndex & 0x00FFFFFF;
			if (type == 5) {
				dataMaxIndex[aValue >>> 24] = aValue & 0x00FFFFFF;
				checkEOFAndExtractData(aValue >>> 24);
				return;
			}
			switch (type) {
				case 0:
					receivedDataClientList.put(index, aValue);
					break;
				case 1:
					receivedDataControllerList.put(index, aValue);
					break;
				case 2:
					receivedDataClusterDetail.put(index, aValue);
					break;
				case 3:
					receivedDataControllerDetail.put(index, aValue);
					break;
			}
			if(dataMaxIndex[type] > 0)checkEOFAndExtractData(type);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void checkEOFAndExtractData(int type) throws IOException{
		switch (type) {
			case 0:
				if(!checkEOF(receivedDataClientList,dataMaxIndex[type])) return;
				dataUserList = ComputerClusterClientData.UserList.deserialize(codeUtil.decompressFromIntegerArray(orderedValues(receivedDataClientList)));
				receivedDataClientList.clear();
				break;
			case 1:
				if(!checkEOF(receivedDataControllerList,dataMaxIndex[type])) return;
				dataControllerList = ComputerClusterClientData.ControllerList.deserialize(codeUtil.decompressFromIntegerArray(orderedValues(receivedDataControllerList)));
				receivedDataControllerList.clear();
				break;
			case 2:
				if(!checkEOF(receivedDataClusterDetail,dataMaxIndex[type])) return;
				dataClusterDetail = ComputerClusterClientData.ClusterDetail.deserialize(codeUtil.decompressFromIntegerArray(orderedValues(receivedDataClusterDetail)));
				receivedDataClusterDetail.clear();
				break;
			case 3:
				if(!checkEOF(receivedDataControllerDetail,dataMaxIndex[type])) return;
				dataControllerDetail = ComputerClusterClientData.ControllerDetail.deserialize(codeUtil.decompressFromIntegerArray(orderedValues(receivedDataControllerDetail)));
				receivedDataControllerDetail.clear();
				break;
		}
		dataMaxIndex[type] = 0;
	}

	public boolean checkEOF(Map<Integer,Integer> map, int maxIndex){
		if(map.size() < maxIndex)return false;
		for (int i = 0; i < maxIndex; i++) {
			if(!map.containsKey(i)||map.get(i)==null)return false;
		}
		updated = true;
		return true;
	}

	public boolean updated = false;

	@Override public int getStartIndex() {return 0;}
	@Override public int getSlotCount() {return 1;}
	@Override public int getShiftClickStartIndex() {return 0;}
	@Override public int getShiftClickSlotCount() {return 1;}
}
