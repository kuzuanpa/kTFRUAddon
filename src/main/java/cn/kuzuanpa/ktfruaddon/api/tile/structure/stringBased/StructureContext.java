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

package cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased;

import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureChangeList;
import cn.kuzuanpa.ktfruaddon.api.tile.structure.snapshot.StructureView;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class StructureContext {
    public final World world;
    /**异步模式的数据来源; 同步模式(SET/PROJECT/RESET/CHECK)下为 null。**/
    public final StructureView view;
    /**异步模式下记录"要改的部件"的出口; 同步模式下为 null。**/
    public final StructureChangeList changes;
    /**异步模式下有格子读取不到(未加载区块): 机器应当判 DEFER 而不是"结构不成立"。**/
    public boolean deferred;
    public int addX,addY,addZ, oX,oY,oZ;
    public final byte facing;
    public final ITileEntityMultiBlockController controller;
    public final Entity player;
    public final IInventory inventory;
    public StringBaseMode mode;
    public ChunkCoordinates failedPos;
    public StructureContext(ITileEntityMultiBlockController controller, StringBaseMode mode, World world, int oX,int oY, int oZ, byte facing) {
        this(controller, mode, world, oX, oY, oZ, facing, null, null);
    }
    public StructureContext(ITileEntityMultiBlockController controller, StringBaseMode mode, World world, int oX,int oY, int oZ, byte facing, Entity player, IInventory inventory) {
        this.controller = controller;
        this.world = world;
        this.view = null;
        this.changes = null;
        this.oX=oX;
        this.oY=oY;
        this.oZ=oZ;
        this.mode=mode;
        this.facing = facing;
        this.player = player;
        this.inventory = inventory;

    }
    /**异步(快照)模式: 没有 World, 只有快照视图与变更出口。**/
    private StructureContext(ITileEntityMultiBlockController controller, StructureView view, StructureChangeList changes, int oX,int oY, int oZ, byte facing) {
        this.controller = controller;
        this.world = null;
        this.view = view;
        this.changes = changes;
        this.oX=oX; this.oY=oY; this.oZ=oZ;
        this.mode = StringBaseMode.CHECK_ASYNC;
        this.facing = facing;
        this.player = null;
        this.inventory = null;
    }

    public static StructureContext async(ITileEntityMultiBlockController aController, StructureView aView, StructureChangeList aChanges, int aOX, int aOY, int aOZ, byte aFacing) {
        return new StructureContext(aController, aView, aChanges, aOX, aOY, aOZ, aFacing);
    }

    public boolean isAsync() {return mode == StringBaseMode.CHECK_ASYNC;}
    /**异步模式下"这个格子看不到" → 标记本轮无法判定。**/
    public void defer() {deferred = true;}
    /**异步模式下把"这里结构不对"的位置记下来, 由主线程去发红框提示(跨线程不能自己发包)。**/
    public void highlight(int aX, int aY, int aZ) {
        if (changes != null) changes.add(aX, aY, aZ, StructureChangeList.KIND_CUSTOM, HIGHLIGHT_MARK, 0, 0);
    }
    /**KIND_CUSTOM + 这个 key 约定为"发一次结构失败红框"。**/
    public static final int HIGHLIGHT_MARK = 1;

    public int[] getMapCoord() {
        return convertCoord(facing, oX, oY, oZ, addX, addY, addZ);
    }
    public static int[] convertCoord(byte facing, int oX, int oY, int oZ, int addX, int addY, int addZ) {
        int[] resultX = {oX + addX, oX + addX, oX - addX, oX + addX, oX + addZ, oX - addZ, 0};
        int[] resultY = {oY + addZ, oY - addZ, oY + addY, oY + addY, oY + addY, oY + addY, 0};
        int[] resultZ = {oZ + addY, oZ - addY, oZ + addZ, oZ - addZ, oZ + addX, oZ - addX, 0};
        return new int[]{resultX[facing],resultY[facing],resultZ[facing]};
    }
    public enum Axis {X, Y, Z}
    public enum StringBaseMode {
        CHECK, SET, PROJECT, RESET,
        /**快照模式: 只读 ctx.view, 需要改的部件写进 ctx.changes, 绝不碰世界。**/
        CHECK_ASYNC
    }
}
