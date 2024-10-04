/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.multiblock.parts;

import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.util.UT;
import gregapi.util.WD;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import static gregapi.data.CS.*;

public interface IMultiBlockPart {
    ChunkCoordinates getTargetPos();
    default void notifyTarget(){
        ITileEntityMultiBlockController tTarget = getTarget(false);
        if (tTarget != null) tTarget.onStructureChange();
    }
    void setTargetPos(ChunkCoordinates pos);

    static void writeToNBT(NBTTagCompound aNBT, ChunkCoordinates targetPos, int design){
        if (design != 0) aNBT.setByte(NBT_DESIGN, (byte)design);
        if (targetPos != null) {
            UT.NBT.setBoolean(aNBT, "gt.target", true);
            UT.NBT.setNumber(aNBT, "gt.target.x", targetPos.posX);
            UT.NBT.setNumber(aNBT, "gt.target.y", targetPos.posY);
            UT.NBT.setNumber(aNBT, "gt.target.z", targetPos.posZ);
        }
    }
    static ChunkCoordinates readTargetPosFromNBT(NBTTagCompound aNBT){
        return new ChunkCoordinates(UT.Code.bindInt(aNBT.getLong(NBT_TARGET_X)), UT.Code.bindInt(aNBT.getLong(NBT_TARGET_Y)), UT.Code.bindInt(aNBT.getLong(NBT_TARGET_Z)));
    }
    default ITileEntityMultiBlockController getTarget(boolean aCheckValidity) {
            if (getTargetPos() == null) {
                return null;
            } else {
                if (getTarget2() == null || getTarget2().isDead()) {
                    setTarget(null);
                    if (getWorld().blockExists(getTargetPos().posX, getTargetPos().posY, getTargetPos().posZ)) {
                        TileEntity tTarget = WD.te(getWorld(), getTargetPos(), true);
                        if (tTarget instanceof ITileEntityMultiBlockController && ((ITileEntityMultiBlockController)tTarget).isInsideStructure(getX(), getY(), getZ())) {
                            setTarget((ITileEntityMultiBlockController)tTarget);
                        } else {
                            setTarget(null);
                        }
                    }
                }

                return aCheckValidity && getTarget2() != null && !getTarget2().checkStructure(false) ? null : getTarget2();
            }

    };
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
}
