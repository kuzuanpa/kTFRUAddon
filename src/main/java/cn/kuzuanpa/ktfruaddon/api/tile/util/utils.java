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

package cn.kuzuanpa.ktfruaddon.api.tile.util;

import cn.kuzuanpa.ktfruaddon.api.tile.part.IMultiBlockPart;
import cpw.mods.fml.common.FMLLog;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.block.multitileentity.MultiTileEntityContainer;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.code.ItemStackContainer;
import gregapi.code.ItemStackSet;
import gregapi.data.CS;
import gregapi.tileentity.base.TileEntityBase04MultiTileEntities;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.ST;
import gregapi.util.UT;
import gregapi.util.WD;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import zmaster587.libVulpes.block.BlockMeta;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static gregapi.data.CS.*;

public class utils {
    public static boolean resetTarget(ITileEntityMultiBlockController aController,int aX, int aY, int aZ, int aDesign) {
        TileEntity tTileEntity = aController.getTileEntity(aX, aY, aZ);
        if(tTileEntity == null) return false;
        else if (tTileEntity == aController) {
            return true;
        }
        try {
            if(tTileEntity instanceof MultiTileEntityMultiBlockPart&&((MultiTileEntityMultiBlockPart) tTileEntity).getTarget(false).equals(aController))((MultiTileEntityMultiBlockPart) tTileEntity).setTarget(null, aDesign, 0);
            if(tTileEntity instanceof IMultiBlockPart&& ((IMultiBlockPart) tTileEntity).getTarget(false).equals(aController))((IMultiBlockPart) tTileEntity).setTarget(null, aDesign, 0);
        } catch (Throwable ignored){}
        return true;
    }
    public static boolean checkAndSetTarget(ITileEntityMultiBlockController aController, int aX, int aY, int aZ, ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory, TileDesc[] availTiles, boolean allowPartShare) {
        return checkAndSetTarget(aController,new ChunkCoordinates(aX,aY,aZ), aClickedAt, aPlayer, aInventory, availTiles, allowPartShare);
    }
    public static boolean checkAndSetTarget(ITileEntityMultiBlockController aController, int aX, int aY, int aZ, ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory, MultiTileEntityRegistry registry, short registryMeta, int aDesign, int aUsage) {
        return checkAndSetTarget(aController,aX,aY,aZ, aClickedAt, aPlayer, aInventory, new TileDesc(registry, registryMeta, aUsage, aDesign));
    }
    public static boolean checkAndSetTarget(ITileEntityMultiBlockController aController, int aX, int aY, int aZ, ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory, TileDesc availTile) {
        return checkAndSetTarget(aController,new ChunkCoordinates(aX,aY,aZ), aClickedAt, aPlayer, aInventory, new TileDesc[]{availTile});
    }
    public static boolean checkAndSetTarget(ITileEntityMultiBlockController aController, int aX, int aY, int aZ, ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory, TileDesc[] availTiles) {
        return checkAndSetTarget(aController,new ChunkCoordinates(aX,aY,aZ), aClickedAt, aPlayer, aInventory, availTiles);
    }
    public static boolean checkAndSetTarget(ITileEntityMultiBlockController aController, ChunkCoordinates coord, ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory, TileDesc[] availTiles) {
        return checkAndSetTarget(aController,coord, aClickedAt, aPlayer, aInventory, availTiles, false);
    }

    public static boolean tryPlaceTile(TileDesc tTile, ITileEntityMultiBlockController aController, ChunkCoordinates coord, Entity aPlayer, IInventory aInventory){
        ItemStack aStack = ST.make(tTile.aRegistry.currentID(), 1, tTile.aRegistryMeta);
        if (!WD.easyRep(aController.getWorld(), coord.posX, coord.posY, coord.posZ) || !UT.Entities.canEdit(aPlayer, coord.posX, coord.posY, coord.posZ, aStack)) return false;
        if (aInventory == null || UT.Entities.hasInfiniteItems(aPlayer)) {// is Player in creative
            if (WD.set(aController.getWorld(), coord.posX, coord.posY, coord.posZ, aStack)) {
                UT.Sounds.send(CS.SFX.MC_XP, aController.getWorld(), coord.posX, coord.posY, coord.posZ);
                return true;
            }
        } else for (int i = aInventory.getSizeInventory() - 1; i >= 0; i--) {
            ItemStack tStack = aInventory.getStackInSlot(i);
            if (ST.equal(aStack, tStack, T) && ST.use(aPlayer, T, T, tStack, 1)) {
                if (WD.set(aController.getWorld(), coord.posX, coord.posY, coord.posZ, tStack) && aPlayer != null) {
                    UT.Sounds.send(CS.SFX.MC_XP, aController.getWorld(), coord.posX, coord.posY, coord.posZ);
                }
                return true;
            }
        }
        return false;
    }
    public static boolean tryPlaceBlock(Block block, World world, ChunkCoordinates coord, Entity aPlayer, IInventory aInventory) {
        return tryPlaceBlock(block, 0, world, coord, aPlayer, aInventory);
    }
    public static boolean tryPlaceBlock(Block block, int blockMeta, World world, ChunkCoordinates coord, Entity aPlayer, IInventory aInventory){
        ItemStack aStack = ST.make(block, 1, blockMeta);
        if (!WD.easyRep(world, coord.posX, coord.posY, coord.posZ) || !UT.Entities.canEdit(aPlayer, coord.posX, coord.posY, coord.posZ, aStack)) return false;
        if (aInventory == null || UT.Entities.hasInfiniteItems(aPlayer)) {// is Player in creative
            if (WD.set(world, coord.posX, coord.posY, coord.posZ, blockMeta == W ? ST.make(block, 1, 0) : aStack)) {
                UT.Sounds.send(CS.SFX.MC_XP, world, coord.posX, coord.posY, coord.posZ);
                return true;
            }
        } else for (int i = aInventory.getSizeInventory() - 1; i >= 0; i--) {
            ItemStack tStack = aInventory.getStackInSlot(i);
            if (ST.equal(aStack, tStack, T) && ST.use(aPlayer, T, T, tStack, 1)) {
                if (WD.set(world, coord.posX, coord.posY, coord.posZ, tStack) && aPlayer != null) {
                    UT.Sounds.send(CS.SFX.MC_XP, world, coord.posX, coord.posY, coord.posZ);
                }
                return true;
            }
        }
        return false;
    }
    public static boolean checkAndSetTarget(ITileEntityMultiBlockController aController, ChunkCoordinates coord, ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory, TileDesc[] availTiles, boolean allowPartShare) {
        TileEntity tTileEntity = aController.getTileEntity(coord);
        if (tTileEntity == aController) return true;
        TileDesc result = null;

        if (tTileEntity instanceof MultiTileEntityMultiBlockPart) for (TileDesc tTile : availTiles) {
            if (tTile.aRegistryMeta != ((MultiTileEntityMultiBlockPart) tTileEntity).getMultiTileEntityID() || tTile.aRegistry.currentID() != ((MultiTileEntityMultiBlockPart) tTileEntity).getMultiTileEntityRegistryID())continue;
            result = tTile;
            break;
        }
        else if (tTileEntity instanceof IMultiBlockPart) for (TileDesc tTile : availTiles) {
            if (tTile.aRegistryMeta != ((IMultiBlockPart) tTileEntity).getMultiTileEntityID() || tTile.aRegistry.currentID() != ((IMultiBlockPart) tTileEntity).getMultiTileEntityRegistryID()) continue;
            result = tTile;
            break;
        }

        if(result == null && (aInventory != null || aPlayer != null))for (TileDesc tTile : availTiles) {
            if(!tryPlaceTile(tTile, aController, coord, aPlayer, aInventory))continue;
            result = tTile;
            tTileEntity = aController.getTileEntity(coord);
            break;
        }

        if(result != null)return setTarget(aController, aClickedAt, aPlayer, aInventory, tTileEntity, result.aDesign, result.aUsage, allowPartShare);
        return false;
    }
    public static boolean setTarget(ITileEntityMultiBlockController aController, ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory, TileEntity tile, int aDesign, int aMode, boolean allowShare) {
        if(tile instanceof MultiTileEntityMultiBlockPart) {
            MultiTileEntityMultiBlockPart part = (MultiTileEntityMultiBlockPart)tile;
            ITileEntityMultiBlockController tTarget = part.getTarget(false);
            if (tTarget != aController && tTarget != null) {
                if(!allowShare) debugLog("not share");
                return allowShare;
            }

            part.setTarget(aController, aDesign, aMode);
            return true;
        }else if (tile instanceof IMultiBlockPart) {
            IMultiBlockPart part = (IMultiBlockPart)tile;
            ITileEntityMultiBlockController tTarget = part.getTarget(false);
            if (tTarget != aController && tTarget != null) {
                if(!allowShare) debugLog("not share");
                return allowShare;
            }

            part.setTarget(aController, aDesign, aMode);
            return true;
        }
        debugLog("not valid gt tile");
        return false;
    }
    public static final boolean debug = false;
    public static void debugLog(String str){
        if(debug)FMLLog.log(Level.FATAL,"[kTFRUAddon] Structure Check: "+str);
    }
    public static boolean resetTarget(ITileEntityMultiBlockController aController,ChunkCoordinates coord, int aDesign, int aMode) {
        return resetTarget(aController,coord.posX,coord.posY, coord.posZ, aDesign);
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
    private static void dontSpamMyIDE(){};

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
    public static int getRealX(short Facing, int oX, int addX, int addZ){
        int[] resultX = {0, 0, oX - addX, oX + addX, oX + addZ, oX - addZ, 0, 0};
        return resultX[Facing];
    }
    public static double getRealX(short Facing, double oX, double addX, double addZ){
        double[] resultX = {0, 0, oX - addX, oX + addX, oX + addZ, oX - addZ, 0, 0};
        return resultX[Facing];
    }
    public static int getRealZ(short Facing, int oZ, int addX, int addZ){
        int[] resultZ = {0, 0, oZ + addZ, oZ - addZ, oZ + addX, oZ - addX, 0, 0};
        return resultZ[Facing];
    }
    public static double getRealZ(short Facing, double oZ, double addX, double addZ){
        double[] resultZ = {0, 0, oZ + addZ, oZ - addZ, oZ + addX, oZ - addX, 0, 0};
        return resultZ[Facing];
    }


    public static Vec3 getRealCoord(byte Facing, double oX, double oY, double oZ, double addX, double addY, double addZ) {
        double[] resultX = {0, 0, oX - addX, oX + addX, oX + addZ, oX - addZ, 0, 0};
        double[] resultZ = {0, 0, oZ + addZ, oZ - addZ, oZ + addX, oZ - addX, 0, 0};
        return Vec3.createVectorHelper(resultX[Facing],oY +addY,resultZ[Facing]);
    }
    public static int getXOffset(byte Facing,int offsetX,int offsetZ){
        int[] resultX = {0, 0,  - offsetX,  + offsetX,  + offsetZ,  - offsetZ, 0, 0};
        return resultX[Facing];
    }
    public static double getXOffset(byte Facing,double offsetX,double offsetZ){
        double[] resultX = {0, 0,  - offsetX,  + offsetX,  + offsetZ,  - offsetZ, 0, 0};
        return resultX[Facing];
    }
    public static int getZOffset(byte Facing,int offsetX,int offsetZ){
        int[] resultZ = {0, 0, + offsetZ, - offsetZ, + offsetX, - offsetX, 0, 0};
        return resultZ[Facing];
    }
    public static double getZOffset(byte Facing,double offsetX,double offsetZ){
        double[] resultZ = {0, 0, + offsetZ, - offsetZ, + offsetX, - offsetX, 0, 0};
        return resultZ[Facing];
    }

    public static BlockMeta getProjectorTile(MultiTileEntityRegistry registry, int id){
        MultiTileEntityContainer container = registry.getNewTileEntityContainer(id, new NBTTagCompound());
        ((IMultiTileEntity) container.mTileEntity).setShouldRefresh(false);
        return new BlockMeta(container.mBlock,container.mTileEntity);
    }
    public static byte[] UTFToBytes(String utf) {
        try{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeUTF(utf);
        dos.close();
        return bos.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
        }
        return new byte[0];
    }
    public static int dimID(World world){
        return world.provider.dimensionId;
    }

    public static int put(ItemStack aStackFrom, @SuppressWarnings("rawtypes") DelegatorTileEntity aTo, ItemStackSet<ItemStackContainer> aFilter, boolean aIgnoreSideFrom, boolean aInvertFilter, boolean aEjectItems, int aMaxMove, int aMinMove) {
        if (aTo.mTileEntity != null) {
            if (ST.TE_PIPES && aTo.mTileEntity instanceof cofh.api.transport.IItemDuct) {
                if (aStackFrom != null && aMinMove <= aStackFrom.stackSize && (aFilter == null || aFilter.contains(aStackFrom, T) != aInvertFilter)) {
                    // Actually Moving the Stack
                    ItemStack tStackMoved = ST.amount(Math.min(aStackFrom.stackSize, aMaxMove), aStackFrom);
                    ItemStack rStackMoved = ((cofh.api.transport.IItemDuct)aTo.mTileEntity).insertItem(aTo.getForgeSideOfTileEntity(), ST.copy(tStackMoved));
                    int rMoved = (tStackMoved.stackSize - (rStackMoved == null ? 0 : rStackMoved.stackSize));
                    if (rMoved > 0) {
                        WD.mark(aTo);
                        return rMoved;
                    }
                }
                return 0;
            }
            if (ST.BC_PIPES && aTo.mTileEntity instanceof buildcraft.api.transport.IInjectable) {
                if (aStackFrom != null && aMinMove <= aStackFrom.stackSize && (aFilter == null || aFilter.contains(aStackFrom, T) != aInvertFilter) ) {
                    // Actually Moving the Stack
                    ItemStack tStackMoved = ST.amount(Math.min(aStackFrom.stackSize, aMaxMove), aStackFrom);
                    int rMoved = ((buildcraft.api.transport.IInjectable)aTo.mTileEntity).injectItem(ST.copy(tStackMoved), F, aTo.getForgeSideOfTileEntity(), null);
                    if (rMoved >= aMinMove) {
                        rMoved = (((buildcraft.api.transport.IInjectable)aTo.mTileEntity).injectItem(ST.amount(rMoved, tStackMoved), T, aTo.getForgeSideOfTileEntity(), null));
                        WD.mark(aTo);
                        return rMoved;
                    }
                }
                return 0;
            }
        }

        Block aBlock = aTo.getBlock();
        if (aBlock instanceof BlockRailBase) {
            // Do not eject shit onto Rails directly.
        } else if (aBlock.getMaterial() == Material.lava || aBlock instanceof BlockFire || (ST.invalid(aBlock) && aTo.mY < 1)) {
            if (aStackFrom != null && aMinMove <= aStackFrom.stackSize && (aFilter == null || aFilter.contains(aStackFrom, T) != aInvertFilter)) {
                // Actually Moving the Stack
                int rMoved = GarbageGT.trash(ST.amount(Math.min(aStackFrom.stackSize, aMaxMove), aStackFrom));
                return rMoved;
            }
        } else if (!WD.hasCollide(aTo.mWorld, aTo.mX, aTo.mY, aTo.mZ, aBlock)) {
            if (aEjectItems)
                if (aStackFrom != null && aMinMove <= aStackFrom.stackSize && (aFilter == null || aFilter.contains(aStackFrom, T) != aInvertFilter)) {
                    // Actually Moving the Stack
                    ItemStack tStack = ST.amount(Math.min(aStackFrom.stackSize, aMaxMove), aStackFrom);
                    ST.place(aTo.mWorld, aTo.mX+0.5, aTo.mY+0.5, aTo.mZ+0.5, tStack);
                    return tStack.stackSize;
                }
        }
        return 0;
    }
}

