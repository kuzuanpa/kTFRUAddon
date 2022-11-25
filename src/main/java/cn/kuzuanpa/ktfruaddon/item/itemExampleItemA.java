package cn.kuzuanpa.ktfruaddon.item;

import cn.kuzuanpa.ktfruaddon.ktfruaddon;
import net.minecraft.item.Item;

public class itemExampleItemA extends Item {
    public itemExampleItemA() {
        super();
        this.setUnlocalizedName("ExampleItemA");
        this.setTextureName(ktfruaddon.MOD_ID + ".example_item_a");
    }
}
