package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock;

import gregapi.block.MaterialMachines;
import gregapi.block.MaterialScoopable;
import gregapi.block.multitileentity.MultiTileEntityBlock;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.*;
import gregapi.oredict.OreDictMaterial;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;
import static gregapi.data.CS.*;

public class MultiBlockMachineLoader implements Runnable {
    @Override
public void run() {

    MultiTileEntityRegistry aRegistry = MultiTileEntityRegistry.getRegistry("ktfru.multitileentity");

    MultiTileEntityBlock
            aMetal      = MultiTileEntityBlock.getOrCreate(MOD_ID, "iron"         , Material.iron             , Block.soundTypeMetal, TOOL_pickaxe, 0, 0, 15, F, F)
            , aMetalChips = MultiTileEntityBlock.getOrCreate(MOD_ID, "iron"         , Material.iron             , Block.soundTypeMetal, TOOL_shovel , 0, 0, 15, F, F)
            , aMetalWires = MultiTileEntityBlock.getOrCreate(MOD_ID, "machine"      , MaterialMachines.instance , Block.soundTypeMetal, TOOL_cutter , 0, 0, 15, F, F)
            , aMachine    = MultiTileEntityBlock.getOrCreate(MOD_ID, "machine"      , MaterialMachines.instance , Block.soundTypeMetal, TOOL_wrench , 0, 0, 15, F, F)
            , aWooden     = MultiTileEntityBlock.getOrCreate(MOD_ID, "wood"         , Material.wood             , Block.soundTypeWood , TOOL_axe    , 0, 0, 15, F, F)
            , aBush       = MultiTileEntityBlock.getOrCreate(MOD_ID, "leaves"       , Material.leaves           , Block.soundTypeGrass, TOOL_axe    , 0, 0, 15, F, F)
            , aStone      = MultiTileEntityBlock.getOrCreate(MOD_ID, "rock"         , Material.rock             , Block.soundTypeStone, TOOL_pickaxe, 0, 0, 15, F, F)
            , aWool       = MultiTileEntityBlock.getOrCreate(MOD_ID, "cloth"        , Material.cloth            , Block.soundTypeCloth, TOOL_shears , 0, 0, 15, F, F)
            , aTNT        = MultiTileEntityBlock.getOrCreate(MOD_ID, "tnt"          , Material.tnt              , Block.soundTypeGrass, TOOL_pickaxe, 0, 0, 15, F, F)
            , aUtilMetal  = MultiTileEntityBlock.getOrCreate(MOD_ID, "redstoneLight", Material.redstoneLight    , Block.soundTypeMetal, TOOL_pickaxe, 0, 0, 15, F, F)
            , aUtilStone  = MultiTileEntityBlock.getOrCreate(MOD_ID, "redstoneLight", Material.redstoneLight    , Block.soundTypeStone, TOOL_pickaxe, 0, 0, 15, F, F)
            , aUtilWood   = MultiTileEntityBlock.getOrCreate(MOD_ID, "redstoneLight", Material.redstoneLight    , Block.soundTypeWood , TOOL_axe    , 0, 0, 15, F, F)
            , aUtilWool   = MultiTileEntityBlock.getOrCreate(MOD_ID, "redstoneLight", Material.redstoneLight    , Block.soundTypeCloth, TOOL_shears , 0, 0, 15, F, F)
            , aHive       = MultiTileEntityBlock.getOrCreate(MOD_ID, "rock"         , MaterialScoopable.instance, Block.soundTypeWood , TOOL_scoop  , 0, 0, 15, F, F)
            ;

    multiblocks(aRegistry, aMetal, aMetalChips, aMetalWires, aMachine, aWooden, aBush, aStone, aWool, aTNT, aHive, aUtilMetal, aUtilStone, aUtilWood, aUtilWool, MT.NULL, null);
}
    private static void multiblocks (MultiTileEntityRegistry aRegistry, MultiTileEntityBlock
    aMetal, MultiTileEntityBlock aMetalChips, MultiTileEntityBlock aMetalWires, MultiTileEntityBlock
    aMachine, MultiTileEntityBlock aWooden, MultiTileEntityBlock aBush, MultiTileEntityBlock
    aStone, MultiTileEntityBlock aWool, MultiTileEntityBlock aTNT, MultiTileEntityBlock aHive, MultiTileEntityBlock
    aUtilMetal, MultiTileEntityBlock aUtilStone, MultiTileEntityBlock aUtilWood, MultiTileEntityBlock
    aUtilWool, OreDictMaterial aMat, Class < ? extends TileEntity > aClass){
        aClass = MultiTileEntityMultiBlockPart.class;
        aMat = MT.StainlessSteel;       aRegistry.add("ktest Steel Wall"     , "Example Mod", 12000, 0, aClass , aMat.mToolQuality, 64, aMachine   , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   6.0F, NBT_RESISTANCE,   6.0F, NBT_TEXTURE, "kmetalwall"               , NBT_DESIGNS, 7), "wPP", "hPP"       , 'P', OP.plate.dat(aMat)); RM.Welder.addRecipe2(F, 16, 256, OP.plate.mat(aMat, 4), ST.tag(10), aRegistry.getItem());
        aMat = MT.StainlessSteel;       aRegistry.add("ktest Large Bathing Vat" , "Example Mod", 12001, 0, testMultiBlockMachine.class , aMat.mToolQuality, 16, aMachine   , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   6.0F, NBT_RESISTANCE,   6.0F, NBT_TEXTURE, "klargebath"               , NBT_INPUT,    1                      , NBT_ENERGY_ACCEPTED, TD.Energy.TU, NBT_RECIPEMAP, RM.Bath     , NBT_INV_SIDE_AUTO_OUT, SIDE_BOTTOM, NBT_TANK_SIDE_AUTO_OUT, SIDE_BOTTOM                           , NBT_PARALLEL,  64                          , NBT_NO_CONSTANT_POWER, T), "CRC", "PMP", "APA", 'M', aRegistry.getItem(12000), 'R', IL.Processor_Crystal_Ruby, 'C', OD_CIRCUITS[6], 'P', OP.plateDense.dat(aMat), 'A', IL.ROBOT_ARMS[2]);
        aMat = MT.StainlessSteel;       aRegistry.add("ktest Large Bathing Vat controller" , "Example Mod", 12002, 0, testMultiBlockMachine.class , aMat.mToolQuality, 16, aMachine   , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   6.0F, NBT_RESISTANCE,   6.0F, NBT_TEXTURE, "klargebath"               , NBT_INPUT,    1                      , NBT_ENERGY_ACCEPTED, TD.Energy.TU, NBT_RECIPEMAP, RM.Bath     , NBT_INV_SIDE_AUTO_OUT, SIDE_BOTTOM, NBT_TANK_SIDE_AUTO_OUT, SIDE_BOTTOM                           , NBT_PARALLEL,  64                          , NBT_NO_CONSTANT_POWER, T), "CRC", "PMP", "APA", 'M', aRegistry.getItem(12000), 'R', IL.Processor_Crystal_Ruby, 'C', OD_CIRCUITS[6], 'P', OP.plateDense.dat(aMat), 'A', IL.ROBOT_ARMS[2]);

    }
}

