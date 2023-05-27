
/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.util;

import cn.kuzuanpa.ktfruaddon.tile.parts.MultiBlockPartComputeCluster;
import cn.kuzuanpa.ktfruaddon.tile.parts.MultiBlockPartEnergyConsumer;
import cpw.mods.fml.common.FMLLog;
import gregapi.tileentity.base.TileEntityBase04MultiTileEntities;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import org.apache.logging.log4j.Level;

public class utils {
    public utils() {
    }
    public static boolean checkAndSetTarget(ITileEntityMultiBlockController aController, int aX, int aY, int aZ, int aRegistryMeta, int aRegistryID, int aDesign, int aMode) {
        TileEntity tTileEntity = aController.getTileEntity(aX, aY, aZ);
        if (tTileEntity == aController) {
            return true;
        } else if (tTileEntity instanceof MultiTileEntityMultiBlockPart && ((MultiTileEntityMultiBlockPart) tTileEntity).getMultiTileEntityID() == aRegistryMeta && ((MultiTileEntityMultiBlockPart) tTileEntity).getMultiTileEntityRegistryID() == aRegistryID) {
            ITileEntityMultiBlockController tTarget = ((MultiTileEntityMultiBlockPart) tTileEntity).getTarget(false);
            if (tTarget != aController && tTarget != null) {
                return false;
            } else {
                ((MultiTileEntityMultiBlockPart) tTileEntity).setTarget(aController, aDesign, aMode);
                return true;
            }
        } else if (tTileEntity instanceof MultiBlockPartComputeCluster && ((MultiBlockPartComputeCluster) tTileEntity).getMultiTileEntityID() == aRegistryMeta && ((MultiBlockPartComputeCluster) tTileEntity).getMultiTileEntityRegistryID() == aRegistryID) {
            ITileEntityMultiBlockController tTarget = ((MultiBlockPartComputeCluster) tTileEntity).getTarget(false);
            if (tTarget != aController && tTarget != null) {
                return false;
            } else {
                ((MultiBlockPartComputeCluster) tTileEntity).setTarget(aController, aDesign, aMode);
                return true;
            }
        } else {
            return false;
        }
    }
    public static boolean resetTarget(ITileEntityMultiBlockController aController,int aX, int aY, int aZ, int aDesign, int aMode) {
        TileEntity tTileEntity = aController.getTileEntity(aX, aY, aZ);
        if (tTileEntity == aController) {
            return true;
        }
        try {
            ((MultiTileEntityMultiBlockPart) tTileEntity).setTarget(null, aDesign, aMode);
        } catch (Throwable ignored){}
        return true;
    }

        public static boolean checkAndSetTarget(ITileEntityMultiBlockController aController, ChunkCoordinates coord, int aRegistryMeta, int aRegistryID, int aDesign, int aMode) {
        TileEntity tTileEntity = aController.getTileEntity(coord);
        if (tTileEntity == aController) {
            return true;
        } else if (tTileEntity instanceof MultiTileEntityMultiBlockPart && ((MultiTileEntityMultiBlockPart) tTileEntity).getMultiTileEntityID() == aRegistryMeta && ((MultiTileEntityMultiBlockPart) tTileEntity).getMultiTileEntityRegistryID() == aRegistryID) {
            ITileEntityMultiBlockController tTarget = ((MultiTileEntityMultiBlockPart) tTileEntity).getTarget(false);
            if (tTarget != aController && tTarget != null) {
                return false;
            } else {
                ((MultiTileEntityMultiBlockPart) tTileEntity).setTarget(aController, aDesign, aMode);
                FMLLog.log(Level.FATAL,"Out of range:"+coord+aRegistryMeta);
                return true;
            }
        } else if (tTileEntity instanceof MultiBlockPartComputeCluster && ((MultiBlockPartComputeCluster) tTileEntity).getMultiTileEntityID() == aRegistryMeta && ((MultiBlockPartComputeCluster) tTileEntity).getMultiTileEntityRegistryID() == aRegistryID) {
            ITileEntityMultiBlockController tTarget = ((MultiBlockPartComputeCluster) tTileEntity).getTarget(false);
            if (tTarget != aController && tTarget != null) {
                return false;
            } else {
                ((MultiBlockPartComputeCluster) tTileEntity).setTarget(aController, aDesign, aMode);
                return true;
            }
        } else {
            return false;
        }
    }
    public static boolean resetTarget(ITileEntityMultiBlockController aController,ChunkCoordinates coord, int aDesign, int aMode) {
        TileEntity tTileEntity = aController.getTileEntity(coord.posX, coord.posY, coord.posZ);
        if (tTileEntity == aController) {
            return true;
        }
        try {
            ((MultiTileEntityMultiBlockPart) tTileEntity).setTarget(null, aDesign, aMode);
        } catch (Throwable ignored){}
        return true;
    }   public static boolean checkAndSetTargetEnergyConsumerPermitted(ITileEntityMultiBlockController aController, int aX, int aY, int aZ, int aRegistryMeta, int aRegistryID, int aDesign, int aMode) {
        TileEntity tTileEntity = aController.getTileEntity(aX, aY, aZ);
        if (tTileEntity == null) return false;
        if (tTileEntity == aController) return true;
        else if (tTileEntity instanceof MultiBlockPartEnergyConsumer && ((MultiBlockPartEnergyConsumer) tTileEntity).getMultiTileEntityID() == aRegistryMeta && ((MultiBlockPartEnergyConsumer) tTileEntity).getMultiTileEntityRegistryID() == aRegistryID) {
            ITileEntityMultiBlockController tTarget = ((MultiBlockPartEnergyConsumer) tTileEntity).getTarget(false);
            if (tTarget != aController && tTarget != null) return false;
            else {
                ((MultiBlockPartEnergyConsumer) tTileEntity).setTarget(aController, aDesign, aMode);
                return true;
            }
        } else return false;
    }

    public static String getTargetTileEntityName(TileEntity tile) {
        if (tile==null||tile.isInvalid())return "null";
        try {
            return ((TileEntityBase04MultiTileEntities)tile).getTileEntityName();
        }catch (ClassCastException e){
            if (tile.getClass().getName().contains("net.minecraft.tileentity")) return tile.getClass().getName().replace("net.minecraft.tileentity","minecraft");
            return tile.getClass().getName();
        }
    }
    //todo: allow getCheckX&Z can be used in controller blocks that facing up and down
@SuppressWarnings("currently can't be used in controller blocks that facing up and down")
    public static int getCheckX(int Facing, int tX, int addX, int addZ) {
        int[] result = {0, 0, tX - addX, tX + addX, tX + addZ, tX - addZ, 0, 0};
        return result[Facing];
    }
    @SuppressWarnings("currently can't be used in controller blocks that facing up and down")
    public static int getCheckZ(int Facing, int tZ, int addX, int addZ) {
        int[] result = {0, 0, tZ + addZ, tZ - addZ, tZ + addX, tZ - addX, 0, 0};
        return result[Facing];
    }
    /**
     * @author kuzuanpa
     * <pre></pre>
     * <pre>ABC about Real Reflection</pre>
     * <pre>Using this could keep every block exractly same whether Facing.</pre>
     * <pre>使用这个可以保持所有方块完全相同，无论主方块的朝向</pre>
     * <pre></pre>
     * <pre>Currently not be fully done.</pre>
     * <pre>  1: cannot be used in machines that main block facing up and down.</pre>
     * <pre>  2: Could anyone tell me is this have any negatives such as proformance loss or something?</pre>
     * <pre></pre>
     * <pre> getRealCoord(...) needs to input a coord that didn't depend on Facing. When crotrollerBlock facing -x, then every coord is exractly what you need.</pre>
     * <pre>It will return a Real WorldCoord that can directly be used in checkAndSetTarget(...) or sth.</pre>
     * <pre>If you know Vanilla Commands, This method is very like ^ ^ ^, getRealCoord(...addX,addY,addZ) will return same Coord as ^addX ^addY ^-addZ</pre>
     * <pre></pre>
     * <pre>zh_CN:</pre>
     * <pre>getRealCoord(...) 需要输入一个不取决于主方块朝向的坐标，如果主方块正面向-x方向，目标方块的相对坐标即是你应填入该方法的坐标</pre>
     * <pre>它将返回一个真实的世界坐标，可以直接用于checkAndSetTarget(...)等方法的那种</pre>
     * <pre>如果你熟悉原版命令，这个方法很类似于^ ^ ^坐标表示法，getRealCoord(...addX,addY,addZ) 将返回与 ^addX ^addY ^-addZ相同的坐标</pre>
     * <pre></pre>
     * <pre>You can also use Dummy Structure Map,please turn to exampleMachine class to get a look into that.</pre>
     * <pre></pre>
    **/
    private static void dontSpamMyIDE(){/*long JavaDoc is annoying because it can fill whole screen when you hovered your mouse on it...*/};

    /**
     * Detailed help can be found in utils.java
     * @param Facing controllerBlock's facing,usually this.mFacing
     * @param oX X Coord of the Start point
     * @param addX X offset in Dummy Coord
     * @return an int[] contains X,Y,Z in real world.
     */
    public static ChunkCoordinates getRealCoord(byte Facing, int oX, int oY, int oZ, int addX, int addY, int addZ) {
        int[] resultX = {0, 0, oX - addX, oX + addX, oX + addZ, oX - addZ, 0, 0};
        int[] resultZ = {0, 0, oZ + addZ, oZ - addZ, oZ + addX, oZ - addX, 0, 0};
        return new ChunkCoordinates(resultX[Facing],oY +addY,resultZ[Facing]);
    }
    public static int getRealX(byte Facing, int oX, int addX, int addZ){
        int[] resultX = {0, 0, oX - addX, oX + addX, oX + addZ, oX - addZ, 0, 0};
        return resultX[Facing];
    }
    public static int getRealZ(byte Facing, int oZ, int addX, int addZ){
        int[] resultZ = {0, 0, oZ + addZ, oZ - addZ, oZ + addX, oZ - addX, 0, 0};
        return resultZ[Facing];
    }
    public static Vec3 getRealCoordDouble(byte Facing, double oX, double oY, double oZ, double addX, double addY, double addZ) {
        double[] resultX = {0, 0, oX - addX, oX + addX, oX + addZ, oX - addZ, 0, 0};
        double[] resultZ = {0, 0, oZ + addZ, oZ - addZ, oZ + addX, oZ - addX, 0, 0};
        return Vec3.createVectorHelper(resultX[Facing],oY +addY,resultZ[Facing]);
    }
    public static double getRealXDouble(byte Facing, double oX, double oY, double oZ, double addX, double addY, double addZ){
        double[] resultX = {0, 0, oX - addX, oX + addX, oX + addZ, oX - addZ, 0, 0};
        return resultX[Facing];
    }
    public static double getRealZDouble(byte Facing, double oX, double oY, double oZ, double addX, double addY, double addZ){
        double[] resultZ = {0, 0, oZ + addZ, oZ - addZ, oZ + addX, oZ - addX, 0, 0};
        return resultZ[Facing];
    }

    public static int offsetX(byte Facing, int tX,int tZ,int offsetX,int offsetZ){
        int[] resultX = {0, 0, tX - offsetX, tX + offsetX, tX + offsetZ, tX - offsetZ, 0, 0};
        return resultX[Facing];
    }
    public static int offsetZ(byte Facing, int tX,int tZ,int offsetX,int offsetZ){
        int[] resultZ = {0, 0, tZ + offsetZ, tZ - offsetZ, tZ + offsetX, tZ - offsetX, 0, 0};
        return resultZ[Facing];
    }

    public static class GTTileEntity {
        public  int aRegistryMeta;
        public int aRegistryID;
        public int aDesign;
        public int aUsage;
        public GTTileEntity( int aRegistryID,int aRegistryMeta, int aDesign,int aUsage){
            this.aDesign=aDesign;
            this.aRegistryID=aRegistryID;
            this.aRegistryMeta=aRegistryMeta;
            this.aUsage=aUsage;
        }
    }

}

