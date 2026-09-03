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


package cn.kuzuanpa.ktfruaddon.api.tile.part;

import gregapi.block.multitileentity.IWailaTile;
import gregapi.data.LH;
import gregapi.tileentity.base.TileEntityBase01Root;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.util.UT;
import gregapi.util.WD;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import java.util.List;

import static gregapi.data.CS.*;

public interface IMultiBlockPart extends IWailaTile {
    ChunkCoordinates getTargetPos();
    default void notifyTarget(){
        ITileEntityMultiBlockController tTarget = getTarget(false);
        if (tTarget != null) tTarget.onStructureChange();
    }
    void setTargetPos(ChunkCoordinates pos);

    static void writeToNBT(NBTTagCompound aNBT, ChunkCoordinates targetPos, int design){
        if (design != 0) aNBT.setByte(NBT_DESIGN, (byte)design);
        if (targetPos != null) {
            UT.NBT.setBoolean(aNBT, NBT_TARGET, true);
            UT.NBT.setNumber(aNBT, NBT_TARGET_X, targetPos.posX);
            UT.NBT.setNumber(aNBT, NBT_TARGET_Y, targetPos.posY);
            UT.NBT.setNumber(aNBT, NBT_TARGET_Z, targetPos.posZ);
        }
    }
    static ChunkCoordinates readTargetPosFromNBT(NBTTagCompound aNBT){
        return new ChunkCoordinates(UT.Code.bindInt(aNBT.getLong(NBT_TARGET_X)), UT.Code.bindInt(aNBT.getLong(NBT_TARGET_Y)), UT.Code.bindInt(aNBT.getLong(NBT_TARGET_Z)));
    }
    /**@param aCheckValidity send checkStructure request to target to ensure structure is valid**/
    default ITileEntityMultiBlockController getTarget(boolean aCheckValidity) {
        if (getTargetPos() == null) {
            return null;
        } else {
            if (getTarget2() == null || getTarget2().isDead()) {
                setTarget(null);
                if (getWorld().blockExists(getTargetPos().posX, getTargetPos().posY, getTargetPos().posZ)) {
                    TileEntity tTarget = WD.te(getWorld(), getTargetPos(), true);
                    if (tTarget instanceof ITileEntityMultiBlockController && ((ITileEntityMultiBlockController) tTarget).isInsideStructure(getX(), getY(), getZ())) {
                        setTarget((ITileEntityMultiBlockController) tTarget);
                    } else {
                        setTarget(null);
                    }
                }
            }

            return aCheckValidity && getTarget2() != null && !getTarget2().checkStructure(false) ? null : getTarget2();
        }

    }
    void setTarget(ITileEntityMultiBlockController target);

    default void setTarget(ITileEntityMultiBlockController target, int aDesign, int aMode){
        setTarget(target);
        setTargetPos(target == null ? null : target.getCoords());
        setDesign(aDesign);
    }

    void setDesign(int design);
    int getDesign();
    ITileEntityMultiBlockController getTarget2();
    int getX();
    int getY();
    int getZ();
    World getWorld();
    short getMultiTileEntityID();
    short getMultiTileEntityRegistryID();

    default NBTTagCompound getWailaNBT(TileEntity te, NBTTagCompound aNBT) {
        if (getTargetPos() != null) {
            ITileEntityMultiBlockController tTileEntity = getTarget(F);
            if(!tTileEntity.checkStructure(F))return aNBT;

            UT.NBT.setBoolean(aNBT, "part."+NBT_TARGET, T);
            UT.NBT.setNumber (aNBT, "part."+NBT_TARGET_X, getTargetPos().posX);
            UT.NBT.setNumber (aNBT, "part."+NBT_TARGET_Y, getTargetPos().posY);
            UT.NBT.setNumber (aNBT, "part."+NBT_TARGET_Z, getTargetPos().posZ);

            if(!(tTileEntity instanceof IWailaTile))return aNBT;
            ((IWailaTile)tTileEntity).getWailaNBT((TileEntity) tTileEntity, aNBT);
        }
        return aNBT;
    }

    default List<String> getWailaBody(List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        NBTTagCompound aNBT = accessor.getNBTData();
        if (aNBT.hasKey("part."+NBT_TARGET)) {setTargetPos(new ChunkCoordinates(UT.Code.bindInt(aNBT.getLong("part."+NBT_TARGET_X)), UT.Code.bindInt(aNBT.getLong("part."+NBT_TARGET_Y)), UT.Code.bindInt(aNBT.getLong("part."+NBT_TARGET_Z))));}
        else setTargetPos(null);
        ITileEntityMultiBlockController tTileEntity = getTarget(F);
        if(tTileEntity instanceof TileEntityBase01Root) currentTip.add(LH.get(LH.FORMED)+ " " + LH.Chat.WHITE + LH.get(((TileEntityBase01Root) tTileEntity).getTileEntityName()));
        if(tTileEntity instanceof IWailaTile) ((IWailaTile)tTileEntity).getWailaBody(currentTip, accessor, config);
        return currentTip;
    }
}
