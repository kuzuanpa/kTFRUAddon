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

package cn.kuzuanpa.ktfruaddon.tile.research;

import cn.kuzuanpa.ktfruaddon.api.network.ITileReceiveContainerButtonClick;
import cn.kuzuanpa.ktfruaddon.api.network.ITileSyncByteArrayLong;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchItem;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchTree;
import cn.kuzuanpa.ktfruaddon.client.gui.ContainerClientResearchTreeMonitor;
import cn.kuzuanpa.ktfruaddon.client.gui.ContainerCommonResearchTreeMonitor;
import cn.kuzuanpa.ktfruaddon.ktfruaddon;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.tileentity.machines.MultiTileEntityBasicMachineElectric;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class ResearchTreeMonitor extends MultiTileEntityBasicMachineElectric implements ITileSyncByteArrayLong, ITileReceiveContainerButtonClick {
    public boolean treeNeedSync = false;
    @Override public String getTileEntityName() {return "ktfru.multitileentity.research.monitor";}
    public ResearchTree theTree = new ResearchTree((byte)0);
    public ResearchItem selectedItem = null;
    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerClientResearchTreeMonitor(aPlayer.inventory, this, aGUIID, mGUITexture);
    }
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerCommonResearchTreeMonitor(aPlayer.inventory, this,aGUIID);
    }
    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        return getClientDataPacketByteArrayLong(aSendAll, theTree.saveToArray());
    }

    @Override
    public INetworkHandler getNetworkHandler() {
        return ktfruaddon.kNetworkHandler;
    }

    @Override
    public INetworkHandler getNetworkHandlerNonOwned() {
        return ktfruaddon.kNetworkHandler2;
    }

    @Override
    public void receiveDataByteArrayLong(IBlockAccess aWorld, int aX, int aY, int aZ, byte[] aData, INetworkHandler aNetworkHandler) {
        theTree.loadFromArray(aData);
    }

    @Override
    public boolean onTickCheck(long aTimer) {
        return super.onTickCheck(aTimer) || treeNeedSync || rng(10)==0;
    }

    @Override
    public void onContainerButtonClick(int buttonID, byte @Nullable [] data) {
        try {
            if(data == null)return;
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            DataInputStream dis = new DataInputStream(bis);
            String id = dis.readUTF();
            selectedItem = theTree.allResearch.get(id);
        } catch (IOException e) {}
    }
}
