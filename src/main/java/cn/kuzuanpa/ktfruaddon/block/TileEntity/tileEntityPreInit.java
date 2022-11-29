package cn.kuzuanpa.ktfruaddon.block.TileEntity;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregapi.api.Abstract_Proxy;
import gregapi.block.MaterialMachines;
import gregapi.block.MaterialScoopable;
import gregapi.block.multitileentity.MultiTileEntityBlock;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;
import static gregapi.data.CS.*;

public class tileEntityPreInit {
    public tileEntityPreInit(FMLPreInitializationEvent aEvent) {
        // This gives you your very own 32767 Machine IDs.
        new MultiTileEntityRegistry("ktfru.multitileentity");
        MultiTileEntityBlock.getOrCreate(MOD_ID, "iron"         , Material.iron             , Block.soundTypeMetal, TOOL_pickaxe, 0, 0, 15, F, F);
        MultiTileEntityBlock.getOrCreate(MOD_ID, "iron"         , Material.iron             , Block.soundTypeMetal, TOOL_shovel , 0, 0, 15, F, F);
        MultiTileEntityBlock.getOrCreate(MOD_ID, "machine"      , MaterialMachines.instance , Block.soundTypeMetal, TOOL_cutter , 0, 0, 15, F, F);
        MultiTileEntityBlock.getOrCreate(MOD_ID, "machine"      , MaterialMachines.instance , Block.soundTypeMetal, TOOL_wrench , 0, 0, 15, F, F);
        MultiTileEntityBlock.getOrCreate(MOD_ID, "wood"         , Material.wood             , Block.soundTypeWood , TOOL_axe    , 0, 0, 15, F, F);
        MultiTileEntityBlock.getOrCreate(MOD_ID, "leaves"       , Material.leaves           , Block.soundTypeGrass, TOOL_axe    , 0, 0, 15, F, F);
        MultiTileEntityBlock.getOrCreate(MOD_ID, "rock"         , Material.rock             , Block.soundTypeStone, TOOL_pickaxe, 0, 0, 15, F, F);
        MultiTileEntityBlock.getOrCreate(MOD_ID, "cloth"        , Material.cloth            , Block.soundTypeCloth, TOOL_shears , 0, 0, 15, F, F);
        MultiTileEntityBlock.getOrCreate(MOD_ID, "tnt"          , Material.tnt              , Block.soundTypeGrass, TOOL_pickaxe, 0, 0, 15, F, F);
        MultiTileEntityBlock.getOrCreate(MOD_ID, "redstoneLight", Material.redstoneLight    , Block.soundTypeMetal, TOOL_pickaxe, 0, 0, 15, F, F);
        MultiTileEntityBlock.getOrCreate(MOD_ID, "redstoneLight", Material.redstoneLight    , Block.soundTypeStone, TOOL_pickaxe, 0, 0, 15, F, F);
        MultiTileEntityBlock.getOrCreate(MOD_ID, "redstoneLight", Material.redstoneLight    , Block.soundTypeWood , TOOL_axe    , 0, 0, 15, F, F);
        MultiTileEntityBlock.getOrCreate(MOD_ID, "redstoneLight", Material.redstoneLight    , Block.soundTypeCloth, TOOL_shears , 0, 0, 15, F, F);
        MultiTileEntityBlock.getOrCreate(MOD_ID, "rock"         , MaterialScoopable.instance, Block.soundTypeWood , TOOL_scoop  , 0, 0, 15, F, F);


    }
}
