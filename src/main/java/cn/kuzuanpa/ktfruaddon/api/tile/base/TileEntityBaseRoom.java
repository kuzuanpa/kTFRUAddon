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

package cn.kuzuanpa.ktfruaddon.api.tile.base;

import cn.kuzuanpa.ktfruaddon.api.tile.room.IRoomController;
import cn.kuzuanpa.ktfruaddon.api.tile.room.IRoomRegion;
import cn.kuzuanpa.ktfruaddon.api.tile.room.RoomContext;
import cn.kuzuanpa.ktfruaddon.api.tile.room.RoomManager;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.IStringBaseStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ChunkCoordinates;

public abstract class TileEntityBaseRoom extends TileEntityBase10MultiBlockBase implements IRoomController {
    private IRoomRegion roomRegion; private RoomContext roomContext;
    protected ChunkCoordinates lastRoomFailedPos;
    protected abstract IRoomRegion createRoomRegion();
    protected abstract IStringBaseStructure getRoomStructure();

    // Room Registry
    @Override public IRoomRegion getRoomRegion() {return roomRegion;}
    public RoomContext getRoomContext() {return roomContext;}
    @Override public RoomContext makeRoomContext() {
        if (roomRegion == null || worldObj == null) return null;
        return new RoomContext(roomRegion, getProvidedCapabilities(), worldObj.provider.dimensionId,
                xCoord, yCoord, zCoord, isRoomActive());
    }
    protected boolean publishRoom() {
        if (worldObj == null || worldObj.isRemote) return false;
        RoomContext next = makeRoomContext();
        if (next == null || !RoomManager.update(this, next)) {roomContext = null; return false;}
        roomContext = next; return true;
    }
    protected void unregisterRoom() {
        RoomManager.unregister(this);
        roomContext = null;
    }

    // Structure
    @Override public boolean checkStructure2(ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        if (worldObj == null || !worldObj.blockExists(xCoord, yCoord, zCoord)) return mStructureOkay;
        IStringBaseStructure structure = getRoomStructure();
        IRoomRegion region = createRoomRegion();
        if (structure == null || region == null) {
            roomRegion = null;
            mStructureOkay = false;
            unregisterRoom();
            return false;
        }
        lastRoomFailedPos = structure.checkStructure(new StructureContext(this,
                (aPlayer != null || aInventory != null) ? StructureContext.StringBaseMode.SET : StructureContext.StringBaseMode.CHECK,
                worldObj, xCoord, yCoord, zCoord, mFacing, aPlayer, aInventory));
        if (lastRoomFailedPos != null) {
            roomRegion = null;
            mStructureOkay = false;
            unregisterRoom();
            return false;
        }
        roomRegion = region;
        mStructureOkay = true;
        publishRoom();
        return true;
    }
    @Override public boolean isInsideStructure(int aX, int aY, int aZ) {return roomRegion != null && roomRegion.contains(aX, aY, aZ);}
    @Override public void onStructureChange() {unregisterRoom(); super.onStructureChange();}
    @Override public void invalidate() {unregisterRoom(); super.invalidate();}
    @Override public void onChunkUnload() {unregisterRoom(); super.onChunkUnload();}
}
