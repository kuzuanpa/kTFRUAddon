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
import cn.kuzuanpa.ktfruaddon.api.tile.IResearchTable;
import cn.kuzuanpa.ktfruaddon.client.gui.research.ContainerClientResearchTreeMonitor;
import cn.kuzuanpa.ktfruaddon.client.gui.research.ContainerCommonResearchTreeMonitor;
import cn.kuzuanpa.ktfruaddon.ktfruaddon;
import gregapi.data.CS;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.IBlockAccess;
import org.jetbrains.annotations.Nullable;

import java.io.*;

public class ResearchTreeMonitor extends TileEntityBase09FacingSingle implements ITileSyncByteArrayLong, ITileReceiveContainerButtonClick, IResearchTable {
    @Override public boolean isUseableByPlayerGUI(EntityPlayer aPlayer) {return !isDead() && allowInteraction(aPlayer);}
    @Override public String getTileEntityName() {return "ktfru.multitileentity.research.monitor";}
    public ResearchTree theTree = new ResearchTree();

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        aNBT.setTag("researchTree",  theTree.save());
    }

    @Override
    public boolean[] getValidSides() {
        return CS.SIDES_HORIZONTAL;
    }

    @Override
    public byte getDefaultSide() {
        return CS.SIDE_FRONT;
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if(aNBT.hasKey("researchTree"))
            theTree.load(aNBT.getCompoundTag("researchTree"));
        else if(worldObj != null)
            theTree.createFromTemplate((byte)0);
    }

    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerClientResearchTreeMonitor(theTree);
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
    boolean treeNeedUpdate = false;
    @Override
    public boolean onTickCheck(long aTimer) {
        boolean result = super.onTickCheck(aTimer) || (theTree!=null && treeNeedUpdate);
        treeNeedUpdate = false;
        return result;
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if(aTimer % 40 == 0)theTree.needUpdate = true;
        theTree.update();
        if(theTree.needUpdate){
            treeNeedUpdate = true;
            theTree.sendDataToViewerPlayers();
            theTree.needUpdate = false;
        }
    }

    @Override
    public void onContainerButtonClick(int buttonID, byte @Nullable [] data) {
        try {
            if(data == null)return;
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            DataInputStream dis = new DataInputStream(bis);
            String id = dis.readUTF();
            ResearchProject project = theTree.allResearch.get(id);
            if(project == null || !project.isUnlocked)return;
            theTree.setCurrentProject(project);
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

    @Override
    public @Nullable ResearchTreeMonitor getMonitor() {
        return this;
    }
}
