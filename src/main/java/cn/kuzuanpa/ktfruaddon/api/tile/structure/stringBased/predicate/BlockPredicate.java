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

package cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.predicate;

import cn.kuzuanpa.ktfruaddon.api.tile.structure.stringBased.StructureContext;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import cn.kuzuanpa.ktfruaddon.client.kTFRUAddonARProjectorCompact;
import net.minecraft.block.Block;
import net.minecraft.util.ChunkCoordinates;
import zmaster587.libVulpes.block.BlockMeta;

import static gregapi.data.CS.W;

public class BlockPredicate implements IStructurePredicate {
    private final Block expected;
    private final int expectedMeta;
    public BlockPredicate(Block expected) {
        this.expected = expected;
        this.expectedMeta = W;
    }
    public BlockPredicate(Block expected, int expectedMeta) {
        this.expected = expected;
        this.expectedMeta = expectedMeta;
    }

    @Override
    public boolean check(StructureContext ctx, int x, int y, int z) {
        return ctx.world.getBlock(x,y, z) == expected && (expectedMeta == W || ctx.world.getBlockMetadata(x,y, z) == expectedMeta);
    }
    @Override
    public boolean set(StructureContext ctx, int x, int y, int z) {
        return utils.tryPlaceBlock(expected, expectedMeta, ctx.world, new ChunkCoordinates(x, y, z), ctx.player, ctx.inventory);
    }

    @Override
    public boolean reset(StructureContext ctx, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean project(StructureContext ctx, int x, int y, int z) {
        kTFRUAddonARProjectorCompact.projectBlock(ctx.world, x,y,z,  new BlockMeta(expected, expectedMeta));
        return true;
    }
}

