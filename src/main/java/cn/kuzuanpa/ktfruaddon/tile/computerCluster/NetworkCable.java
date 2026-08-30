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

package cn.kuzuanpa.ktfruaddon.tile.computerCluster;

import cn.kuzuanpa.ktfruaddon.api.code.WorldPos;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterController;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterUser;
import codechicken.lib.vec.BlockCoord;
import gregapi.code.HashSetNoNulls;
import gregapi.code.TagData;
import gregapi.data.OP;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.ITexture;
import gregapi.tileentity.connectors.TileEntityBase10ConnectorRendered;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.util.UT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.*;

import static gregapi.data.CS.*;

/**TODO: finialize cable walk& reachable checks**/
public abstract class NetworkCable extends TileEntityBase10ConnectorRendered implements IWiredNetworkConnectable{
    /**Hard cap on a single network walk, an unbounded search over a huge cable network would stall the server tick.**/
    public static final int MAX_WALK_STEPS = 4096;

    public byte mRenderType = 0;
    /**Set while this cable is part of a network walk that already ran in the current tick.**/
    public boolean checkedThisTick = false;
    /**Controller UUIDs that claimed a channel on this network. Shared between every cable of the same network.**/
    public List<UUID> controllers = new ArrayList<>();

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        checkedThisTick = false;
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_PIPERENDER)) mRenderType = aNBT.getByte(NBT_PIPERENDER);
    }

    /**
     * Walks the cable network looking for {@code target}. Iterative on purpose, the recursive version
     * could blow the stack on a large network, and the step cap keeps a pathological network bounded.
     */
    public boolean canReach(WorldPos target, HashSetNoNulls<TileEntity> aAlreadyPassed) {
        if (target == null) return false;
        Queue<NetworkCable> queue = new ArrayDeque<>();
        queue.add(this);
        aAlreadyPassed.add(this);
        int steps = 0;
        while (!queue.isEmpty()) {
            if (++steps > MAX_WALK_STEPS) return false;
            NetworkCable cable = queue.poll();
            for (byte tSide : ALL_SIDES_VALID) if (cable.connected(tSide)) {
                TileEntity tDelegator = cable.getTileEntityAtSideAndDistance(tSide, 1);
                if (tDelegator == null || !aAlreadyPassed.add(tDelegator)) continue;
                if (tDelegator instanceof NetworkCable) {
                    queue.add((NetworkCable) tDelegator);
                    continue;
                }
                if ((tDelegator instanceof IComputerClusterUser || tDelegator instanceof IComputerClusterController) && target.equals(new BlockCoord(tDelegator.xCoord, tDelegator.yCoord, tDelegator.zCoord))) return true;
            }
        }
        return false;
    }

    @Override public float getBlockHardness() {
        return super.getBlockHardness();
    }


    @Override
    public boolean connect(byte aSide, boolean aNotify) {
        return super.connect(aSide, aNotify);
    }

    @Override public boolean canConnect(byte aSide, DelegatorTileEntity<TileEntity> aDelegator) {return aDelegator.mTileEntity instanceof IWiredNetworkConnectable;}

    @Override public boolean canDrop(int aInventorySlot) {return F;}

    @Override public ITexture getTextureSide                (byte aSide, byte aConnections, float aDiameter, int aRenderPass) {return mRenderType == 1 || mRenderType == 2 ? BlockTextureDefault.get(Textures.BlockIcons.INSULATION_FULL, isPainted()?mRGBa:UT.Code.getRGBInt(64, 64, 64)) : BlockTextureDefault.get(mMaterial, getIconIndexSide(aSide, aConnections, aDiameter, aRenderPass), F, mRGBa);}
    @Override public ITexture getTextureConnected           (byte aSide, byte aConnections, float aDiameter, int aRenderPass) {return mRenderType == 1 || mRenderType == 2 ? BlockTextureMulti.get(BlockTextureDefault.get(mMaterial, getIconIndexConnected(aSide, aConnections, aDiameter, aRenderPass), mIsGlowing), BlockTextureDefault.get(mRenderType==2?Textures.BlockIcons.INSULATION_BUNDLED:aDiameter<0.37F?Textures.BlockIcons.INSULATION_TINY:aDiameter<0.49F?Textures.BlockIcons.INSULATION_SMALL:aDiameter<0.74F?Textures.BlockIcons.INSULATION_MEDIUM:aDiameter<0.99F?Textures.BlockIcons.INSULATION_LARGE:Textures.BlockIcons.INSULATION_HUGE, isPainted()?mRGBa:UT.Code.getRGBInt(64, 64, 64))) : BlockTextureDefault.get(mMaterial, getIconIndexConnected(aSide, aConnections, aDiameter, aRenderPass), mIsGlowing, mRGBa);}

    @Override public int getIconIndexSide                   (byte aSide, byte aConnections, float aDiameter, int aRenderPass) {return OP.wire.mIconIndexBlock;}
    @Override public int getIconIndexConnected              (byte aSide, byte aConnections, float aDiameter, int aRenderPass) {return OP.wire.mIconIndexBlock;}

    @Override public Collection<TagData> getConnectorTypes  (byte aSide) {return IWiredNetworkConnectable.WIRE_NETWORK.AS_LIST;}

    @Override public String getFacingTool                   () {return TOOL_cutter;}

    @Override public String getTileEntityName               () {return "ktfru.multitileentity.computercluster.wire";}

    @Override
    public void onConnectionChange(byte aPreviousConnections) {
        for (byte tSide : ALL_SIDES_VALID) if (connected(tSide) || (aPreviousConnections & SBIT[tSide]) != 0) {
            TileEntity t = getTileEntityAtSideAndDistance(tSide, 1);
            if(t instanceof NetworkCable)((NetworkCable) t).initCheck();
        }
    }

    /**
     * Rebuilds the shared channel list of the whole network this cable belongs to and asks every attached
     * endpoint to re-check its own reachability. Every cable visited is marked for the current tick, so a
     * network wide change only walks the network once instead of once per cable.
     */
    public void initCheck(){
        if(checkedThisTick)return;
        Queue<NetworkCable> queue = new ArrayDeque<>();
        Set<NetworkCable> checkedCable = new HashSet<>();
        Set<IWiredNetworkConnectable> checkedConnectable = new HashSet<>();
        queue.add(this);
        checkedCable.add(this);
        int steps = 0;
        while(!queue.isEmpty()){
            if(++steps > MAX_WALK_STEPS)break;
            NetworkCable tile = queue.poll();
            tile.checkedThisTick = true;
            for (byte tSide : ALL_SIDES_VALID) if (tile.connected(tSide)) {
                TileEntity t = tile.getTileEntityAtSideAndDistance(tSide, 1);
                if(t instanceof NetworkCable){ if(checkedCable.add((NetworkCable) t))queue.add((NetworkCable) t);}
                else if(t instanceof IWiredNetworkConnectable)checkedConnectable.add((IWiredNetworkConnectable) t);
            }
        }
        List<UUID> sharedChannels = new ArrayList<>();
        checkedCable.forEach(c->c.controllers = sharedChannels);
        checkedConnectable.forEach(IWiredNetworkConnectable::fillChannel);
        checkedConnectable.forEach(IWiredNetworkConnectable::checkChannel);
    }



    @Override
    public boolean breakBlock() {
        for (byte tSide : ALL_SIDES_VALID) {
            TileEntity t = getTileEntityAtSideAndDistance(tSide, 1);
            if(t instanceof IWiredNetworkConnectable)((IWiredNetworkConnectable) t).fillChannel();
        }
        return super.breakBlock();
    }

    /**@param controllerUUID the controller claiming a channel on this network.**/
    public void takeChannel(UUID controllerUUID) {
        if(controllerUUID != null && !controllers.contains(controllerUUID))controllers.add(controllerUUID);
    }

}
