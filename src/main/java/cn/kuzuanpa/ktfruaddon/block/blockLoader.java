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