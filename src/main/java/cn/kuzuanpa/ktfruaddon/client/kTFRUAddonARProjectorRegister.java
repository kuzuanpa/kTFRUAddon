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
import net.minecraft.init.Blocks;
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
        dummyStructures.add(new DummyTileMultiBlock(cncMachine3(),"ktfru.projector.cncMachine3"));
        dummyStructures.add(new DummyTileMultiBlock(MantleHeater(),"ktfru.projector.MantleHeater"));
        dummyStructures.add(new DummyTileMultiBlock(TokamakTierExp(),"ktfru.projector.fusionReactorTokamakExperiment"));
        //Because the 'Code Too Long...'
        dummyStructures.add(new DummyTileMultiBlock(new Object[][][]{TokamakTier1Layer13(),TokamakTier1Layer12(),TokamakTier1Layer11(),TokamakTier1Layer10(),TokamakTier1Layer9(),TokamakTier1Layer8(),TokamakTier1Layer7(),TokamakTier1Layer6(),TokamakTier1Layer5(),TokamakTier1Layer4(),TokamakTier1Layer3(),TokamakTier1Layer2(),TokamakTier1Layer1(),TokamakTier1Layer0()},"ktfru.projector.fusionReactorTokamakT1"));
        dummyStructures.add(new DummyTileMultiBlock(oilMiner(),"ktfru.projector.oilMiner"));
        dummyStructures.add(new DummyTileMultiBlock(FuelDeburnFactory(),"ktfru.projector.fuelDeburnFactory"));
        dummyStructures.add(new DummyTileMultiBlock(MaskAlignerUVPlus(),"ktfru.projector.maskAlignerUVPlus"));
        dummyStructures.add(new DummyTileMultiBlock(TidalWaveGenerater(),"ktfru.projector.tidalWaveGenerater"));
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
    static Object[][][] TidalWaveGenerater(){
        List<BlockMeta> main = new ArrayList<>();
        main.add(tile(k, 30033));
        main.add(tile(k, 30032));
        main.add(tile(k, 30031));
        BlockMeta wall = tile(g, 18002);
        BlockMeta blad = tile(k, 31045);
        List<BlockMeta> aair = new ArrayList<>();
        aair.add(new BlockMeta(Blocks.stained_glass,8,"ktfru.projector.block.must.air"));
        aair.add(new BlockMeta(Blocks.stained_glass,3,"ktfru.projector.block.must.liquid"));
        BlockMeta aliq = new BlockMeta(Blocks.stained_glass,3,"ktfru.projector.block.must.liquid");
        BlockMeta asol = new BlockMeta(Blocks.stone,0,"ktfru.projector.block.any.solid");
        return new Object[][][]{{
                {null, null, null},
                {null, null, null},
                {aair, aair, aair},
                {aair, aair, aair},
                {aair, aair, aair},
                {aair, aair, aair},
        },{
                {asol, asol, asol},
                {wall, main, wall},
                {wall, null, wall},
                {wall, null, wall},
                {wall, null, wall},
                {blad, blad, blad},
        },{
                {asol, asol, asol},
                {wall, wall, wall},
                {wall, null, wall},
                {wall, null, wall},
                {wall, null, wall},
                {blad, blad, blad},
        },{
                {asol, asol, asol},
                {wall, wall, wall},
                {wall, wall, wall},
                {blad, blad, blad},
                {blad, blad, blad},
                {blad, blad, blad},
        },{
                {null, null, null},
                {aliq, aliq, aliq},
                {aliq, aliq, aliq},
                {aliq, aliq, aliq},
                {aliq, aliq, aliq},
                {aliq, aliq, aliq},
        }
        };
    }
    static Object[][][] MaskAlignerUVPlus(){
        BlockMeta main = tile(k, 30009);
        BlockMeta ener = tile(k, 31501);
        BlockMeta IOMg = tile(k, 31021);
        BlockMeta plat = tile(k, 31006);
        BlockMeta driv = tile(k, 31005);
        BlockMeta ligh = tile(k, 31011);
        BlockMeta wall = tile(g, 18002);
        return new Object[][][]{{
            {ligh, ligh, ligh},
            {ligh, ligh, ligh},
            {ligh, ligh, ligh},
        },{
            {wall, driv, wall},
            {driv, plat, driv},
            {wall, driv, wall},
        },{
            {wall, main, wall},
            {wall, IOMg, wall},
            {wall, ener, wall},
        }
        };
    }
    static Object[][][] MantleHeater() {
        BlockMeta main = tile(k, 30028);
        BlockMeta wall = tile(g, 18004);
        BlockMeta dril = tile(k, 31039);
        BlockMeta pipe = tile(k, 31003);
        BlockMeta topl = tile(k, 31004);

        return new Object[][][]{{
                {null, null, wall, null, null},
                {null, wall, topl, wall, null},
                {wall, topl, topl, topl, wall},
                {null, wall, topl, wall, null},
                {null, null, wall, null, null},
        }, {
                {null, wall, wall, wall, null},
                {wall, pipe, pipe, pipe, wall},
                {wall, pipe, pipe, pipe, wall},
                {wall, pipe, pipe, pipe, wall},
                {null, wall, wall, wall, null},
        }, {
                {wall, wall, main, wall, wall},
                {wall, pipe, pipe, pipe, wall},
                {wall, pipe, pipe, pipe, wall},
                {wall, pipe, pipe, pipe, wall},
                {wall, wall, wall, wall, wall},
        }, {
                {wall, wall, wall, wall, wall},
                {wall, wall, wall, wall, wall},
                {wall, wall, dril, wall, wall},
                {wall, wall, wall, wall, wall},
                {wall, wall, wall, wall, wall},
        }};
    }

    static Object[][][] cncMachine3(){
        BlockMeta main = tile(k, 30012);
        BlockMeta wall = tile(k, 31000);
        BlockMeta plat = tile(k, 31007);
        BlockMeta moto = tile(k, 31008);
        BlockMeta head = tile(k, 31009);

        return new Object[][][]{
                {
                        {null,null,null,null,null},
                        {wall,wall,wall,head,null},
                        {null,null,null,null,null},
                },
                {
                        {wall,moto,null,null,null},
                        {wall,moto,null,null,null},
                        {wall,moto,null,null,null},
                },
                {
                        {wall,main,plat,plat,plat},
                        {wall,moto,plat,plat,plat},
                        {wall,moto,plat,plat,plat},
                },
        };
    }
    static Object[][][] oilMiner() {
        BlockMeta wall = tile(g, 18002);
        BlockMeta main = tile(k, 30013);
        BlockMeta dril = tile(k, 31014);

        return new Object[][][]{
            {
                    {wall,wall,wall},
                    {wall,wall,wall},
                    {wall,wall,wall},
            },
            {
                {wall,main,wall},
                {dril,dril,dril},
                {dril,dril,dril},
            },
        };
    }

    static Object[][] TokamakTier1Layer13(){

        BlockMeta CondT1= tile(k,31025);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,CondT1,CondT1, null , null , null ,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null },
            { null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null },
            { null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null },
            { null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null },
            { null , null , null , null , null , null , null , null , null ,CondT1,CondT1, null , null , null ,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
    };}

    static Object[][] TokamakTier1Layer12(){

        BlockMeta CondT1= tile(k,31025);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,CondT1,CondT1, null , null , null ,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null },
            {CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null },
            {CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null },
            {CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null },
            { null , null , null , null , null , null , null , null , null ,CondT1,CondT1, null , null , null ,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
    };}

    static Object[][] TokamakTier1Layer11(){

        BlockMeta CondT1= tile(k,31025);
        BlockMeta WallT1= tile(k,31027);
        BlockMeta CoilT1= tile(k,31026);
        BlockMeta Heator= tile(k,31028);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,CoilT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,CoilT1, null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,CondT1,CondT1,CondT1,WallT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            {CoilT1,CoilT1, null , null , null , null ,WallT1,WallT1,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,CoilT1,CoilT1,Heator,Heator},
            {CoilT1,CoilT1, null , null , null , null ,WallT1,WallT1,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,CoilT1,CoilT1,Heator,Heator},
            {CoilT1,CoilT1, null , null , null , null ,WallT1,WallT1,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,CoilT1,CoilT1,Heator,Heator},
            { null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,CondT1,CondT1,CondT1,WallT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null ,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,CoilT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,CoilT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
    };}

    static Object[][] TokamakTier1Layer10(){

        BlockMeta CondT1= tile(k,31025);
        BlockMeta WallT1= tile(k,31027);
        BlockMeta CoilT1= tile(k,31026);
        BlockMeta Heator= tile(k,31028);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CoilT1, null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null ,CoilT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CoilT1, null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null ,CoilT1, null , null , null , null , null , null },
            { null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,WallT1,WallT1, null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null ,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null ,WallT1,WallT1, null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null ,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator},
            {CoilT1,CoilT1, null , null ,WallT1,WallT1, null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null ,WallT1,WallT1,Heator,Heator,CoilT1,CoilT1,Heator,Heator},
            {CoilT1,CoilT1, null , null ,WallT1,WallT1, null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null ,WallT1,WallT1,Heator,Heator,CoilT1,CoilT1,Heator,Heator},
            {CoilT1,CoilT1, null , null ,WallT1,WallT1, null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null ,WallT1,WallT1,Heator,Heator,CoilT1,CoilT1,Heator,Heator},
            { null , null , null , null ,WallT1,WallT1, null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null ,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null ,WallT1,WallT1, null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null ,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null },
            { null , null , null , null ,CoilT1, null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null ,CoilT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CoilT1, null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null ,CoilT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][] TokamakTier1Layer9(){

        BlockMeta CondT1= tile(k,31025);
        BlockMeta WallT1= tile(k,31027);
        BlockMeta CoilT1= tile(k,31026);
        BlockMeta Heator= tile(k,31028);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CoilT1, null ,WallT1,WallT1,WallT1, null , null , null , null , null ,WallT1,WallT1,WallT1, null ,CoilT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CoilT1, null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null ,WallT1,WallT1, null ,CoilT1, null , null , null , null , null , null },
            { null , null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null , null },
            { null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            {CoilT1,CoilT1, null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,CoilT1,CoilT1,Heator,Heator},
            {CoilT1,CoilT1, null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,CoilT1,CoilT1,Heator,Heator},
            {CoilT1,CoilT1, null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,CoilT1,CoilT1,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null ,WallT1, null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null },
            { null , null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CoilT1, null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null ,WallT1,WallT1, null ,CoilT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CoilT1, null ,WallT1,WallT1,WallT1, null , null , null , null , null ,WallT1,WallT1,WallT1, null ,CoilT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][] TokamakTier1Layer8(){

        BlockMeta CondT1= tile(k,31025);
        BlockMeta WallT1= tile(k,31027);
        BlockMeta CoilT1= tile(k,31026);
        BlockMeta Heator= tile(k,31028);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CoilT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null ,WallT1,WallT1,CoilT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CoilT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1,CoilT1, null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            {CoilT1,CoilT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CoilT1,CoilT1,Heator,Heator},
            {CoilT1,CoilT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CoilT1,CoilT1,Heator,Heator},
            {CoilT1,CoilT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CoilT1,CoilT1,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null , null ,CoilT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1,CoilT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CoilT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null ,WallT1,WallT1,CoilT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][] TokamakTier1Layer7(){

        BlockMeta CondT1= tile(k,31025);
        BlockMeta WallT1= tile(k,31027);
        BlockMeta CoilT1= tile(k,31026);
        BlockMeta Heator= tile(k,31028);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CoilT1,WallT1, null , null , null , null , null , null , null , null , null , null , null ,WallT1,CoilT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CoilT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1,CoilT1, null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null },
            { null , null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator},
            { null , null ,WallT1, null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator},
            {CoilT1,CoilT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CoilT1,CoilT1,Heator,Heator},
            {CoilT1,CoilT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CoilT1,CoilT1,Heator,Heator},
            {CoilT1,CoilT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CoilT1,CoilT1,Heator,Heator},
            { null , null ,WallT1, null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator},
            { null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null , null ,CoilT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1,CoilT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CoilT1,WallT1, null , null , null , null , null , null , null , null , null , null , null ,WallT1,CoilT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][] TokamakTier1Layer6(){

        BlockMeta CondT1= tile(k,31025);
        BlockMeta WallT1= tile(k,31027);
        BlockMeta CoilT1= tile(k,31026);
        BlockMeta Heator= tile(k,31028);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CoilT1,WallT1, null , null , null , null , null , null , null , null , null , null , null ,WallT1,CoilT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CoilT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1,CoilT1, null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null },
            { null , null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator},
            { null , null ,WallT1, null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator},
            {CoilT1,CoilT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CoilT1,CoilT1,Heator,Heator},
            {CoilT1,CoilT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CoilT1,CoilT1,Heator,Heator},
            {CoilT1,CoilT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CoilT1,CoilT1,Heator,Heator},
            { null , null ,WallT1, null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator},
            { null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null , null ,CoilT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1,CoilT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CoilT1,WallT1, null , null , null , null , null , null , null , null , null , null , null ,WallT1,CoilT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][] TokamakTier1Layer5(){
        BlockMeta CondT1= tile(k,31025);
        BlockMeta WallT1= tile(k,31027);
        BlockMeta CoilT1= tile(k,31026);
        BlockMeta Heator= tile(k,31028);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CoilT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null ,WallT1,WallT1,CoilT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CoilT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1,CoilT1, null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            {CoilT1,CoilT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CoilT1,CoilT1,Heator,Heator},
            {CoilT1,CoilT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CoilT1,CoilT1,Heator,Heator},
            {CoilT1,CoilT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CoilT1,CoilT1,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null , null ,CoilT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1,CoilT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CoilT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null ,WallT1,WallT1,CoilT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][] TokamakTier1Layer4(){
        BlockMeta CoPump= tile(k,31024);
        BlockMeta CondT1= tile(k,31025);
        BlockMeta WallT1= tile(k,31027);
        BlockMeta CoilT1= tile(k,31026);
        BlockMeta Heator= tile(k,31028);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null ,CoPump, null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CoilT1, null ,WallT1,WallT1,WallT1, null , null , null , null , null ,WallT1,WallT1,WallT1, null ,CoilT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CoilT1, null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null ,WallT1,WallT1, null ,CoilT1, null , null , null , null , null , null },
            { null , null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null , null },
            { null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            {CoilT1,CoilT1, null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,CoilT1,CoilT1,Heator,Heator},
            {CoilT1,CoilT1, null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,CoilT1,CoilT1,Heator,Heator},
            {CoilT1,CoilT1, null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,CoilT1,CoilT1,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null ,WallT1, null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null },
            { null , null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CoilT1, null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null ,WallT1,WallT1, null ,CoilT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CoilT1, null ,WallT1,WallT1,WallT1, null , null , null , null , null ,WallT1,WallT1,WallT1, null ,CoilT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][] TokamakTier1Layer3(){
        
        BlockMeta VaPump= tile(k,31015);
        BlockMeta ToPipe= tile(k,31019);
        BlockMeta CoPump= tile(k,31024);
        BlockMeta CondT1= tile(k,31025);
        BlockMeta CoilT1= tile(k,31026);
        BlockMeta WallT1= tile(k,31027);
        BlockMeta Heator= tile(k,31028);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null ,ToPipe, null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,VaPump,VaPump,CoPump,ToPipe,CoPump,VaPump,VaPump,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,VaPump,VaPump,CoilT1,CoilT1,CoilT1,VaPump,VaPump,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,VaPump,VaPump,CoilT1,CoilT1,CoilT1,VaPump,VaPump,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CoilT1, null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null ,CoilT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CoilT1, null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null ,CoilT1, null , null , null , null , null , null },
            { null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,WallT1,WallT1, null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null ,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null ,WallT1,WallT1, null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null ,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator},
            {CoilT1,CoilT1, null , null ,WallT1,WallT1, null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null ,WallT1,WallT1,Heator,Heator,CoilT1,CoilT1,Heator,Heator},
            {CoilT1,CoilT1, null , null ,WallT1,WallT1, null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null ,WallT1,WallT1,Heator,Heator,CoilT1,CoilT1,Heator,Heator},
            {CoilT1,CoilT1, null , null ,WallT1,WallT1, null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null ,WallT1,WallT1,Heator,Heator,CoilT1,CoilT1,Heator,Heator},
            { null , null , null , null ,WallT1,WallT1, null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null ,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null ,WallT1,WallT1, null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null ,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null },
            { null , null , null , null ,CoilT1, null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null ,CoilT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CoilT1, null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null ,CoilT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][] TokamakTier1Layer2(){
        
        BlockMeta VaPump= tile(k,31015);
        BlockMeta ToPipe= tile(k,31019);
        BlockMeta CondT1= tile(k,31025);
        BlockMeta CoilT1= tile(k,31026);
        BlockMeta WallT1= tile(k,31027);
        BlockMeta Heator= tile(k,31028);
        BlockMeta Comput= tile(k,32005);
        BlockMeta StWall= tile(g,18002);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null ,ToPipe,ToPipe,StWall,Comput,StWall,ToPipe,ToPipe, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,ToPipe,ToPipe,StWall,StWall,StWall,ToPipe,ToPipe,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,ToPipe,ToPipe,CoilT1,CoilT1,CoilT1,ToPipe,ToPipe,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,ToPipe,ToPipe,CoilT1,CoilT1,CoilT1,ToPipe,ToPipe,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,ToPipe,ToPipe, null , null , null ,ToPipe,ToPipe, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,ToPipe,ToPipe, null , null , null ,ToPipe,ToPipe, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CoilT1, null , null , null ,ToPipe,ToPipe, null , null , null ,ToPipe,ToPipe, null , null , null ,CoilT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CoilT1, null , null , null , null ,ToPipe,ToPipe, null , null , null ,ToPipe,ToPipe, null , null , null , null ,CoilT1, null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,CondT1,CondT1,CondT1,WallT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            {CoilT1,CoilT1, null , null , null , null ,WallT1,WallT1,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,CoilT1,CoilT1,Heator,Heator},
            {CoilT1,CoilT1, null , null , null , null ,WallT1,WallT1,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,CoilT1,CoilT1,Heator,Heator},
            {CoilT1,CoilT1, null , null , null , null ,WallT1,WallT1,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,CoilT1,CoilT1,Heator,Heator},
            { null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,CondT1,CondT1,CondT1,WallT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null ,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,CoilT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,CoilT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CoilT1,CoilT1,CoilT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][] TokamakTier1Layer1(){
        
        BlockMeta VaPump= tile(k,31015);
        BlockMeta ToPipe= tile(k,31019);
        BlockMeta CondT1= tile(k,31025);
        BlockMeta Comput= tile(k,32005);
        BlockMeta StWall= tile(g,18002);
        BlockMeta MainBl= tile(k,30015);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null ,ToPipe,ToPipe,Comput,MainBl,Comput,ToPipe,ToPipe, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,ToPipe,ToPipe,StWall,Comput,StWall,ToPipe,ToPipe,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,ToPipe,ToPipe,CondT1,CondT1,CondT1,ToPipe,ToPipe,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,ToPipe,ToPipe,CondT1,CondT1,CondT1,ToPipe,ToPipe,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,ToPipe,ToPipe,CondT1,CondT1,CondT1,ToPipe,ToPipe, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,ToPipe,ToPipe,CondT1,CondT1,CondT1,ToPipe,ToPipe, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null ,ToPipe,ToPipe,CondT1,CondT1,CondT1,ToPipe,ToPipe, null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1,CondT1,CondT1, null , null ,ToPipe,ToPipe,CondT1,CondT1,CondT1,ToPipe,ToPipe, null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,CondT1,CondT1, null ,ToPipe,ToPipe,CondT1,CondT1,CondT1,ToPipe,ToPipe, null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,CondT1,CondT1,CondT1,ToPipe,ToPipe,CondT1,CondT1,CondT1,ToPipe,ToPipe,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CondT1,CondT1,CondT1,ToPipe,CondT1,CondT1,CondT1,ToPipe,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,CondT1,CondT1, null , null , null ,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null },
            {CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null },
            {CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null },
            {CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null },
            { null , null , null , null , null , null , null , null , null ,CondT1,CondT1, null , null , null ,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][] TokamakTier1Layer0(){
        
        BlockMeta VaPump= tile(k,31015);
        BlockMeta CondT1= tile(k,31025);
        BlockMeta Comput= tile(k,32005);
        BlockMeta StWall= tile(g,18002);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null ,StWall,Comput,StWall, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,VaPump,VaPump,StWall,StWall,StWall,VaPump,VaPump,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,VaPump,VaPump,VaPump,VaPump,VaPump,VaPump,VaPump,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,VaPump,VaPump,CondT1,CondT1,CondT1,VaPump,VaPump,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,CondT1,CondT1, null , null , null ,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null },
            { null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null },
            { null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null },
            { null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null },
            { null , null , null , null , null , null , null , null , null ,CondT1,CondT1, null , null , null ,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][][] TokamakTierExp() {

        BlockMeta VaPump= tile(k,31015);
        BlockMeta CondTe= tile(k,31016);
        BlockMeta CoilTe= tile(k,31017);
        BlockMeta WallTe= tile(k,31018);
        BlockMeta ToPipe= tile(k,31019);
        BlockMeta CoPump= tile(k,31024);
        BlockMeta Comput= tile(k,32005);
        BlockMeta StWall= tile(g,18002);
        BlockMeta MainBl= tile(k,30014);


        return new Object[][][] {{
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondTe, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondTe, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondTe, null , null , null , null , null , null , null , null },
            { null , null , null ,CondTe, null , null , null , null ,CondTe, null , null , null , null ,CondTe, null , null , null },
            { null , null , null , null ,CondTe, null , null , null ,CondTe, null , null , null ,CondTe, null , null , null , null },
            { null , null , null , null , null ,CondTe, null , null ,CondTe, null , null ,CondTe, null , null , null , null , null },
            { null , null , null , null , null , null ,CondTe, null ,CondTe, null ,CondTe, null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CondTe, null ,CondTe, null , null , null , null , null , null , null },
            {CondTe,CondTe,CondTe,CondTe,CondTe,CondTe,CondTe, null , null , null ,CondTe,CondTe,CondTe,CondTe,CondTe,CondTe,CondTe},
            { null , null , null , null , null , null , null ,CondTe, null ,CondTe, null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,CondTe, null ,CondTe, null ,CondTe, null , null , null , null , null , null },
            { null , null , null , null , null ,CondTe, null , null ,CondTe, null , null ,CondTe, null , null , null , null , null },
            { null , null , null , null ,CondTe, null , null , null ,CondTe, null , null , null ,CondTe, null , null , null , null },
            { null , null , null ,CondTe, null , null , null , null ,CondTe, null , null , null , null ,CondTe, null , null , null },
            { null , null , null , null , null , null , null , null ,CondTe, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondTe, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondTe, null , null , null , null , null , null , null , null },
    },{
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CoilTe, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null , null , null , null },
            { null , null , null ,CoilTe, null ,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe, null ,CoilTe, null , null , null },
            { null , null , null , null ,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe, null , null , null , null },
            { null , null , null ,WallTe,WallTe,WallTe,WallTe, null , null , null ,WallTe,WallTe,WallTe,WallTe, null , null , null },
            { null , null , null ,WallTe,WallTe,WallTe, null , null ,CondTe, null , null ,WallTe,WallTe,WallTe, null , null , null },
            { null , null ,WallTe,WallTe,WallTe, null , null ,CondTe,CondTe,CondTe, null , null ,WallTe,WallTe,WallTe, null , null },
            {CoilTe, null ,WallTe,WallTe,WallTe, null ,CondTe,CondTe, null ,CondTe,CondTe, null ,WallTe,WallTe,WallTe, null ,CoilTe},
            { null , null ,WallTe,WallTe,WallTe, null , null ,CondTe,CondTe,CondTe, null , null ,WallTe,WallTe,WallTe, null , null },
            { null , null , null ,WallTe,WallTe,WallTe, null , null ,CondTe, null , null ,WallTe,WallTe,WallTe, null , null , null },
            { null , null , null ,WallTe,WallTe,WallTe,WallTe, null , null , null ,WallTe,WallTe,WallTe,WallTe, null , null , null },
            { null , null , null , null ,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe, null , null , null , null },
            { null , null , null ,CoilTe, null ,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe, null ,CoilTe, null , null , null },
            { null , null , null , null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CoilTe, null , null , null , null , null , null , null , null },
    },{
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CoilTe, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null , null , null , null },
            { null , null , null , null , null ,WallTe,WallTe, null , null , null ,WallTe,WallTe, null , null , null , null , null },
            { null , null , null ,CoilTe,WallTe, null , null , null , null , null , null , null ,WallTe,CoilTe, null , null , null },
            { null , null , null ,WallTe, null , null , null , null , null , null , null , null , null ,WallTe, null , null , null },
            { null , null ,WallTe, null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null ,WallTe, null , null },
            { null , null ,WallTe, null , null , null ,WallTe, null ,CondTe, null ,WallTe, null , null , null ,WallTe, null , null },
            { null ,WallTe, null , null , null ,WallTe, null ,CondTe,CondTe,CondTe, null ,WallTe, null , null , null ,WallTe, null },
            {CoilTe,WallTe, null , null , null ,WallTe,CondTe,CondTe, null ,CondTe,CondTe,WallTe, null , null , null ,WallTe,CoilTe},
            { null ,WallTe, null , null , null ,WallTe, null ,CondTe,CondTe,CondTe, null ,WallTe, null , null , null ,WallTe, null },
            { null , null ,WallTe, null , null , null ,WallTe, null ,CondTe, null ,WallTe, null , null , null ,WallTe, null , null },
            { null , null ,WallTe, null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null ,WallTe, null , null },
            { null , null , null ,WallTe, null , null , null , null , null , null , null , null , null ,WallTe, null , null , null },
            { null , null , null ,CoilTe,WallTe, null , null , null , null , null , null , null ,WallTe,CoilTe, null , null , null },
            { null , null , null , null , null ,WallTe,WallTe, null , null , null ,WallTe,WallTe, null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CoilTe, null , null , null , null , null , null , null , null },
    },{
            { null , null , null , null , null , null , null , null ,CoPump, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CoilTe, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null , null , null , null },
            { null , null , null , null , null ,WallTe,WallTe, null , null , null ,WallTe,WallTe, null , null , null , null , null },
            { null , null , null ,CoilTe,WallTe, null , null , null , null , null , null , null ,WallTe,CoilTe, null , null , null },
            { null , null , null ,WallTe, null , null , null , null , null , null , null , null , null ,WallTe, null , null , null },
            { null , null ,WallTe, null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null ,WallTe, null , null },
            { null , null ,WallTe, null , null , null ,WallTe, null ,CondTe, null ,WallTe, null , null , null ,WallTe, null , null },
            { null ,WallTe, null , null , null ,WallTe, null ,CondTe,CondTe,CondTe, null ,WallTe, null , null , null ,WallTe, null },
            {CoilTe,WallTe, null , null , null ,WallTe,CondTe,CondTe, null ,CondTe,CondTe,WallTe, null , null , null ,WallTe,CoilTe},
            { null ,WallTe, null , null , null ,WallTe, null ,CondTe,CondTe,CondTe, null ,WallTe, null , null , null ,WallTe, null },
            { null , null ,WallTe, null , null , null ,WallTe, null ,CondTe, null ,WallTe, null , null , null ,WallTe, null , null },
            { null , null ,WallTe, null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null ,WallTe, null , null },
            { null , null , null ,WallTe, null , null , null , null , null , null , null , null , null ,WallTe, null , null , null },
            { null , null , null ,CoilTe,WallTe, null , null , null , null , null , null , null ,WallTe,CoilTe, null , null , null },
            { null , null , null , null , null ,WallTe,WallTe, null , null , null ,WallTe,WallTe, null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CoilTe, null , null , null , null , null , null , null , null },
    },{
            { null , null , null , null , null , null , null ,CoPump,ToPipe,CoPump, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CoPump,CoilTe,CoPump, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null , null , null , null },
            { null , null , null , null , null ,WallTe,WallTe, null , null , null ,WallTe,WallTe, null , null , null , null , null },
            { null , null , null ,CoilTe,WallTe, null , null , null , null , null , null , null ,WallTe,CoilTe, null , null , null },
            { null , null , null ,WallTe, null , null , null , null , null , null , null , null , null ,WallTe, null , null , null },
            { null , null ,WallTe, null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null ,WallTe, null , null },
            { null , null ,WallTe, null , null , null ,WallTe, null ,CondTe, null ,WallTe, null , null , null ,WallTe, null , null },
            { null ,WallTe, null , null , null ,WallTe, null ,CondTe,CondTe,CondTe, null ,WallTe, null , null , null ,WallTe, null },
            {CoilTe,WallTe, null , null , null ,WallTe,CondTe,CondTe, null ,CondTe,CondTe,WallTe, null , null , null ,WallTe,CoilTe},
            { null ,WallTe, null , null , null ,WallTe, null ,CondTe,CondTe,CondTe, null ,WallTe, null , null , null ,WallTe, null },
            { null , null ,WallTe, null , null , null ,WallTe, null ,CondTe, null ,WallTe, null , null , null ,WallTe, null , null },
            { null , null ,WallTe, null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null ,WallTe, null , null },
            { null , null , null ,WallTe, null , null , null , null , null , null , null , null , null ,WallTe, null , null , null },
            { null , null , null ,CoilTe,WallTe, null , null , null , null , null , null , null ,WallTe,CoilTe, null , null , null },
            { null , null , null , null , null ,WallTe,WallTe, null , null , null ,WallTe,WallTe, null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CoilTe, null , null , null , null , null , null , null , null },
    },{
            { null , null , null , null , null ,VaPump, null ,StWall,Comput,StWall, null ,VaPump, null , null , null , null , null },
            { null , null , null , null , null ,VaPump, null ,StWall,CoilTe,StWall, null ,VaPump, null , null , null , null , null },
            { null , null , null , null , null ,VaPump, null , null , null , null , null ,VaPump, null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null , null , null , null },
            { null , null , null ,CoilTe, null ,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe, null ,CoilTe, null , null , null },
            { null , null , null , null ,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe, null , null , null , null },
            { null , null , null ,WallTe,WallTe,WallTe,WallTe, null , null , null ,WallTe,WallTe,WallTe,WallTe, null , null , null },
            { null , null , null ,WallTe,WallTe,WallTe, null , null ,CondTe, null , null ,WallTe,WallTe,WallTe, null , null , null },
            { null , null ,WallTe,WallTe,WallTe, null , null ,CondTe,CondTe,CondTe, null , null ,WallTe,WallTe,WallTe, null , null },
            {CoilTe, null ,WallTe,WallTe,WallTe, null ,CondTe,CondTe, null ,CondTe,CondTe, null ,WallTe,WallTe,WallTe, null ,CoilTe},
            { null , null ,WallTe,WallTe,WallTe, null , null ,CondTe,CondTe,CondTe, null , null ,WallTe,WallTe,WallTe, null , null },
            { null , null , null ,WallTe,WallTe,WallTe, null , null ,CondTe, null , null ,WallTe,WallTe,WallTe, null , null , null },
            { null , null , null ,WallTe,WallTe,WallTe,WallTe, null , null , null ,WallTe,WallTe,WallTe,WallTe, null , null , null },
            { null , null , null , null ,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe, null , null , null , null },
            { null , null , null ,CoilTe, null ,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe, null ,CoilTe, null , null , null },
            { null , null , null , null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CoilTe, null , null , null , null , null , null , null , null },
    },{
            { null , null , null , null ,VaPump,ToPipe,VaPump,Comput,MainBl,Comput,VaPump,ToPipe,VaPump, null , null , null , null },
            { null , null , null , null ,VaPump,ToPipe,VaPump,StWall,CondTe,StWall,VaPump,ToPipe,VaPump, null , null , null , null },
            { null , null , null , null ,VaPump,ToPipe,VaPump, null ,CondTe, null ,VaPump,ToPipe,VaPump, null , null , null , null },
            { null , null , null , null , null ,ToPipe, null , null ,CondTe, null , null ,ToPipe, null , null , null , null , null },
            { null , null , null ,CondTe, null ,ToPipe, null , null ,CondTe, null , null ,ToPipe, null ,CondTe, null , null , null },
            { null , null , null , null ,CondTe,ToPipe, null , null ,CondTe, null , null ,ToPipe,CondTe, null , null , null , null },
            { null , null , null , null , null ,CondTe, null , null ,CondTe, null , null ,CondTe, null , null , null , null , null },
            { null , null , null , null , null , null ,CondTe, null ,CondTe, null ,CondTe, null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CondTe, null ,CondTe, null , null , null , null , null , null , null },
            {CondTe,CondTe,CondTe,CondTe,CondTe,CondTe,CondTe, null , null , null ,CondTe,CondTe,CondTe,CondTe,CondTe,CondTe,CondTe},
            { null , null , null , null , null , null , null ,CondTe, null ,CondTe, null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,CondTe, null ,CondTe, null ,CondTe, null , null , null , null , null , null },
            { null , null , null , null , null ,CondTe, null , null ,CondTe, null , null ,CondTe, null , null , null , null , null },
            { null , null , null , null ,CondTe, null , null , null ,CondTe, null , null , null ,CondTe, null , null , null , null },
            { null , null , null ,CondTe, null , null , null , null ,CondTe, null , null , null , null ,CondTe, null , null , null },
            { null , null , null , null , null , null , null , null ,CondTe, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondTe, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondTe, null , null , null , null , null , null , null , null },
    }};}

    static Object[][][] FuelDeburnFactory() {

        BlockMeta SFrame= tile(k,31037);
        BlockMeta ChPipe= tile(k,31038);
        BlockMeta StWall= tile(g,18002);
        BlockMeta MainBl= tile(k,30027);
        return new Object[][][]{
        {
                { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null , ChPipe,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
        }, {
                { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null , ChPipe,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
        }, {
                { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null , ChPipe,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null , ChPipe,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
        }, {
                { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null , ChPipe,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null , ChPipe,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null , ChPipe,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
        }, {
                { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null , SFrame, SFrame, SFrame, SFrame, SFrame, SFrame,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null }
                , { null , SFrame, SFrame, SFrame, SFrame, SFrame, SFrame,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
        }, {
                { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
        }, {
                { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null , ChPipe,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null , ChPipe, ChPipe, ChPipe, ChPipe,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame, ChPipe,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall, ChPipe,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall, ChPipe,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null , ChPipe,  null ,  null ,  null ,  null ,  null ,  null }
        },  {
                { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
        }, {
                { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null , ChPipe,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null , ChPipe, ChPipe, ChPipe, ChPipe,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame, ChPipe,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall, ChPipe,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall, ChPipe,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null , ChPipe,  null ,  null ,  null ,  null ,  null ,  null }
        }, {
                { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null , ChPipe,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , {ChPipe, StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , {ChPipe, StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , {ChPipe, SFrame, StWall, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null }
                , {ChPipe, ChPipe, ChPipe,  null , ChPipe,  null ,  null ,  null ,  null ,  null ,  null }
        }, {
                { null , SFrame, SFrame, SFrame, SFrame, SFrame,  null ,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null ,  null }
                , { null , SFrame, SFrame, SFrame, SFrame, SFrame,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null , ChPipe,  null ,  null ,  null ,  null ,  null ,  null }
        }, {
                { null , SFrame,  null ,  null ,  null , SFrame,  null ,  null ,  null ,  null ,  null }
                , { null ,  null , StWall, StWall, StWall,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null , StWall,  null , StWall,  null ,  null ,  null , SFrame, SFrame, SFrame}
                , { null ,  null , StWall, StWall, StWall,  null ,  null ,  null , SFrame, SFrame, SFrame}
                , { null , SFrame,  null , SFrame,  null , SFrame,  null ,  null , SFrame, SFrame, SFrame}
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null , SFrame, SFrame, SFrame}
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null , SFrame, SFrame, SFrame}
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null , SFrame, SFrame, SFrame}
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null , SFrame, SFrame, SFrame}
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null , SFrame, SFrame, SFrame}
                , {ChPipe, StWall,  null ,  null ,  null ,  null , StWall,  null , SFrame, SFrame, SFrame}
                , {ChPipe, StWall,  null ,  null ,  null ,  null , StWall,  null , SFrame, SFrame, SFrame}
                , {ChPipe, SFrame, StWall, StWall, StWall, StWall, SFrame,  null , SFrame, SFrame, SFrame}
                , {ChPipe, ChPipe, ChPipe,  null , ChPipe,  null ,  null ,  null ,  null ,  null ,  null }
        }, {
                { null , SFrame,  null , SFrame, SFrame, SFrame,  null ,  null ,  null , ChPipe,  null }
                , { null , SFrame, StWall, StWall, StWall,  null ,  null ,  null ,  null , ChPipe,  null }
                , { null , SFrame, StWall,  null , StWall, SFrame,  null ,  null , StWall, ChPipe, StWall}
                , { null ,  null , StWall, StWall, StWall, SFrame,  null ,  null ,  null , ChPipe,  null }
                , { null , SFrame, SFrame, ChPipe, SFrame, SFrame,  null ,  null , StWall, ChPipe, StWall}
                , { null ,  null ,  null , ChPipe,  null ,  null ,  null ,  null ,  null , ChPipe,  null }
                , { null ,  null ,  null , ChPipe,  null ,  null ,  null ,  null , StWall, ChPipe, StWall}
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null ,  null , ChPipe,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null , StWall, ChPipe, StWall}
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null , ChPipe,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null , StWall, ChPipe, StWall}
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null , ChPipe,  null }
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null , StWall, ChPipe, StWall}
                , { null ,  null ,  null ,  null , ChPipe,  null ,  null ,  null ,  null ,  null ,  null }
        }, {
                { null , SFrame,  null ,  null ,  null , SFrame,  null ,  null ,  null , ChPipe,  null }
                , { null ,  null , StWall, StWall, StWall,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null , StWall,  null , StWall,  null ,  null ,  null , StWall,  null , StWall}
                , { null ,  null , StWall, StWall, StWall,  null ,  null ,  null ,  null ,  null ,  null }
                , { null , SFrame,  null , SFrame,  null , SFrame,  null ,  null , StWall,  null , StWall}
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null , StWall,  null , StWall}
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null , StWall,  null , StWall}
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , {ChPipe, StWall,  null ,  null ,  null ,  null , StWall,  null , StWall,  null , StWall}
                , {ChPipe, StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , {ChPipe, SFrame, StWall, StWall, StWall, StWall, SFrame,  null , StWall,  null , StWall}
                , {ChPipe, ChPipe, ChPipe,  null , ChPipe,  null ,  null ,  null ,  null ,  null ,  null }
        }, {
                { null , SFrame, SFrame, SFrame,  null , SFrame,  null ,  null ,  null , ChPipe,  null }
                , { null ,  null , StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall,  null , StWall, SFrame,  null ,  null , StWall,  null , StWall}
                , { null , SFrame, StWall, StWall, StWall,  null ,  null ,  null ,  null ,  null ,  null }
                , { null , SFrame, SFrame, ChPipe, SFrame, SFrame,  null ,  null , StWall,  null , StWall}
                , { null ,  null ,  null , ChPipe,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null , ChPipe,  null ,  null ,  null ,  null , StWall,  null , StWall}
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null , StWall,  null , StWall}
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null , StWall,  null , StWall}
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null , StWall,  null , StWall}
                , { null ,  null ,  null ,  null , ChPipe,  null ,  null ,  null ,  null ,  null ,  null }
        }, {
                { null , SFrame,  null ,  null ,  null , SFrame,  null ,  null ,  null , ChPipe,  null }
                , { null ,  null , StWall, StWall, StWall,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null , StWall,  null , StWall,  null ,  null ,  null , StWall,  null , StWall}
                , { null ,  null , StWall, StWall, StWall,  null ,  null ,  null ,  null ,  null ,  null }
                , { null , SFrame,  null , SFrame,  null , SFrame,  null ,  null , StWall,  null , StWall}
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null , StWall,  null , StWall}
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null , StWall,  null , StWall}
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null , StWall,  null , StWall}
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null , StWall,  null , StWall}
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
        }, {
                { null , SFrame,  null , SFrame, SFrame, SFrame,  null ,  null ,  null , ChPipe,  null }
                , { null , SFrame, StWall, StWall, StWall,  null ,  null ,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall,  null , StWall, SFrame,  null ,  null , StWall,  null , StWall}
                , { null ,  null , StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null ,  null }
                , { null , SFrame, SFrame, ChPipe, SFrame, SFrame,  null ,  null , StWall,  null , StWall}
                , { null ,  null ,  null , ChPipe,  null ,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null ,  null , ChPipe,  null ,  null ,  null ,  null , StWall,  null , StWall}
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null , StWall,  null , StWall}
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null , StWall,  null , StWall}
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null ,  null ,  null }
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null , StWall,  null , StWall}
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
        }, {
                { null , SFrame,  null ,  null ,  null , SFrame, MainBl,  null ,  null , ChPipe,  null }
                , { null ,  null , StWall, StWall, StWall,  null ,  null ,  null ,  null ,  null ,  null }
                , { null ,  null , StWall,  null , StWall,  null ,  null ,  null , StWall, ChPipe, StWall}
                , { null ,  null , StWall, StWall, StWall,  null ,  null ,  null ,  null , ChPipe,  null }
                , { null , SFrame,  null , SFrame,  null , SFrame,  null ,  null , StWall, ChPipe, StWall}
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null , ChPipe,  null }
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null , StWall, ChPipe, StWall}
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null ,  null , ChPipe,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null , StWall, ChPipe, StWall}
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null , ChPipe,  null }
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null , StWall, ChPipe, StWall}
                , { null , StWall,  null ,  null ,  null ,  null , StWall,  null ,  null , ChPipe,  null }
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null , StWall, ChPipe, StWall}
                , { null ,  null ,  null ,  null , ChPipe, ChPipe, ChPipe, ChPipe, ChPipe, ChPipe,  null }
        }, {
                { null , SFrame, SFrame, SFrame, SFrame, SFrame, SFrame, SFrame, SFrame, SFrame, SFrame}
                , { null , SFrame, StWall, StWall, StWall, SFrame,  null ,  null , SFrame, SFrame, SFrame}
                , { null , SFrame, StWall, StWall, StWall, SFrame,  null ,  null , SFrame, SFrame, SFrame}
                , { null , SFrame, StWall, StWall, StWall, SFrame,  null ,  null , SFrame, SFrame, SFrame}
                , { null , SFrame, SFrame, SFrame, SFrame, SFrame,  null ,  null , SFrame, SFrame, SFrame}
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null , SFrame, SFrame, SFrame}
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null , SFrame, SFrame, SFrame}
                , { null , SFrame, SFrame, SFrame, SFrame, SFrame, SFrame, SFrame, SFrame, SFrame, SFrame}
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null , SFrame, SFrame, SFrame}
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null , SFrame, SFrame, SFrame}
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null , SFrame, SFrame, SFrame}
                , { null , SFrame, StWall, StWall, StWall, StWall, SFrame,  null , SFrame, SFrame, SFrame}
                , { null , SFrame, SFrame, SFrame, SFrame, SFrame, SFrame,  null , SFrame, SFrame, SFrame}
                , { null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null ,  null }
        }
    };
    }
}
