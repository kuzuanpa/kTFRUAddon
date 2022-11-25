package cn.kuzuanpa.ktfruaddon.item;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class itemLoader
{
    public static Item exampleItem = new itemExampleItem();
    public static Item exampleItemA = new itemExampleItemA();
    public itemLoader(FMLPreInitializationEvent event)
    {
        register(exampleItem, "exampleItem");
        register(exampleItemA, "exampleItemA");
    }

    private static void register(Item item, String name){GameRegistry.registerItem(item, name);}
}