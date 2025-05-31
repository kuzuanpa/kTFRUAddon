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
import cn.kuzuanpa.ktfruaddon.api.research.ResearchProject;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchTree;
import cn.kuzuanpa.ktfruaddon.client.gui.research.ContainerClientResearchTreMonitor;
import cn.kuzuanpa.ktfruaddon.client.gui.research.ContainerCommonResearchTreeMonitor;
import cn.kuzuanpa.ktfruaddon.ktfruaddon;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import org.jetbrains.annotations.Nullable;

import java.io.*;

public class ResearchTreeMonitor extends TileEntityBase09FacingSingle implements ITileSyncByteArrayLong, ITileReceiveContainerButtonClick {
    public boolean treeNeedSync = false;
    @Override public boolean isUseableByPlayerGUI(EntityPlayer aPlayer) {return !isDead() && allowInteraction(aPlayer);}
    @Override public String getTileEntityName() {return "ktfru.multitileentity.research.monitor";}
    public ResearchTree theTree = new ResearchTree((byte)0);
    public ResearchProject currentProject = null;
    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerClientResearchTreMonitor(aPlayer.inventory, this, aGUIID);
    }
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerCommonResearchTreeMonitor(aPlayer.inventory, this,aGUIID);
    }
    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (isServerSide() && !aPlayer.isSneaking()) {
            openGUI(aPlayer, aSide);
            return true;
        }
        return false;
    }
    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        byte[] data;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             DataOutputStream dos = new DataOutputStream(bos)){
            byte[] treeData = theTree.saveToArray();
            dos.writeInt(treeData.length);
            dos.write(treeData);
            dos.writeUTF(currentProject==null?"null":currentProject.id);

            dos.flush();
            data = bos.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
            data = new byte[0];
        }
        return getClientDataPacketByteArrayLong(aSendAll, data);
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
        try(ByteArrayInputStream bis = new ByteArrayInputStream(aData);
        DataInputStream dis = new DataInputStream(bis)){
            byte[] treeData = new byte[dis.readInt()];
            dis.readFully(treeData);
            theTree.loadFromArray(treeData);
            currentProject = theTree.allResearch.get(dis.readUTF());
        }catch (IOException e){
            e.printStackTrace();
        }
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
            currentProject = theTree.allResearch.get(id);
            if(currentProject != null && !currentProject.isUnlocked)currentProject = null;
        } catch (IOException e) {}
    }
    // Icons
    public final static IIconContainer
            sTextureSides     = new Textures.BlockIcons.CustomIcon("machines/research/monitor/base"),
            sOverlayStop      = new Textures.BlockIcons.CustomIcon("machines/research/monitor/front");


    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (!aShouldSideBeRendered[aSide]) return null;
        if(aSide==mFacing) return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides, mRGBa),BlockTextureDefault.get(sOverlayStop ));
        return BlockTextureDefault.get(sTextureSides, mRGBa);
    }
    @Override
    public boolean canDrop(int aSlot) {
        return false;
    }
}
