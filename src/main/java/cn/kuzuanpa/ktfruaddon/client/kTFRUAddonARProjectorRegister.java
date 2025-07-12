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


package cn.kuzuanpa.ktfruaddon.client;

import cn.kuzuanpa.ktfruaddon.ktfruaddon;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.block.multitileentity.MultiTileEntityContainer;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import zmaster587.libVulpes.api.IDummyMultiBlockRegisterer;
import zmaster587.libVulpes.api.LibVulpesBlocks;
import zmaster587.libVulpes.block.BlockMeta;
import zmaster587.libVulpes.tile.TileSchematic;
import zmaster587.libVulpes.tile.multiblock.DummyTileMultiBlock;
import zmaster587.libVulpes.tile.multiblock.TilePlaceholder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class kTFRUAddonARProjectorRegister implements IDummyMultiBlockRegisterer {
    static MultiTileEntityRegistry g;
    static MultiTileEntityRegistry k;
    public static List<DummyTileMultiBlock> dummyStructures = new ArrayList<>();
    public kTFRUAddonARProjectorRegister(){
        g = MultiTileEntityRegistry.getRegistry("gt.multitileentity");
        k = ktfruaddon.kTileRegistry0;
    }
    public static BlockMeta tile(MultiTileEntityRegistry registry,int id){
        MultiTileEntityContainer container = registry.getNewTileEntityContainer(id, new NBTTagCompound());
        ((IMultiTileEntity) container.mTileEntity).setShouldRefresh(false);
        return new BlockMeta(container.mBlock,container.mTileEntity);
    }
    @Override
    public List<DummyTileMultiBlock> getDummyMultiBlocks() {
        return dummyStructures;
    }
    public static boolean setProjectBlock(World world, int x, int y, int z, BlockMeta block){
        return setProjectBlock(world, x, y, z, Collections.singletonList(block));
    }

    public static boolean setProjectBlock(World world, int x, int y, int z, List<BlockMeta> block){
        if(!(world.isAirBlock(x, y, z) || world.getBlock(x, y, z).isReplaceable(world, x, y, z)) && block.get(0).getBlock().getMaterial() != Material.air) return false;
        world.setBlock(x, y, z, LibVulpesBlocks.blockPhantom, block.get(0).getMeta(), 3);
        TileEntity newTile = world.getTileEntity(x, y, z);

        if(!(newTile instanceof TilePlaceholder))return false;

        ((TileSchematic)newTile).setReplacedBlock(block);
        ((TilePlaceholder)newTile).setReplacedTileEntity(block.get(0).getBlock().createTileEntity(null, 0));
        return true;
    }
}
