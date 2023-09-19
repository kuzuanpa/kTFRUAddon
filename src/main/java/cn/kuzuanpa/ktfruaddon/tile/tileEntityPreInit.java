/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.tile;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregapi.block.MaterialMachines;
import gregapi.block.MaterialScoopable;
import gregapi.block.multitileentity.MultiTileEntityBlock;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;
import static gregapi.data.CS.*;

public class tileEntityPreInit {
    public static void init(FMLPreInitializationEvent aEvent) {
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
