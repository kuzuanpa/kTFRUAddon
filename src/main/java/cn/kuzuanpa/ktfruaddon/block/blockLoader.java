/**
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.block;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;


public class blockLoader
{
    public static Block exampleBlock = new blockExampleBlock();
    public blockLoader(FMLPreInitializationEvent event)
    {
        register(exampleBlock, "exampleBlock");
    }

    private static void register(Block block, String name){GameRegistry.registerBlock(block, name);}
}