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

import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

public class StructureContext {
    public final World world;
    public int addX,addY,addZ, oX,oY,oZ;
    public final byte facing;
    public final boolean tryAutoBuild;
    public final ITileEntityMultiBlockController controller;
    public final Entity player;
    public final IInventory inventory;
    public StructureContext(ITileEntityMultiBlockController controller, World world, int oX,int oY, int oZ, byte facing, boolean tryAutoBuild) {
        this(controller, world, oX, oY, oZ, facing, tryAutoBuild, null, null);
    }
    public StructureContext(ITileEntityMultiBlockController controller, World world, int oX,int oY, int oZ, byte facing, boolean tryAutoBuild, Entity player, IInventory inventory) {
        this.controller = controller;
        this.world = world;
        this.oX=oX;
        this.oY=oY;
        this.oZ=oZ;
        this.facing = facing;
        this.tryAutoBuild=tryAutoBuild;
        this.player = player;
        this.inventory = inventory;

    }

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

}
