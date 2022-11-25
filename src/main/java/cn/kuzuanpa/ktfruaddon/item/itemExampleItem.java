package cn.kuzuanpa.ktfruaddon.item;

import cn.kuzuanpa.ktfruaddon.ktfruaddon;
import net.minecraft.item.Item;

public class itemExampleItem extends Item {
    public itemExampleItem() {
        super();
        this.setUnlocalizedName("ExampleItem");
        this.setTextureName(ktfruaddon.MOD_ID + ".example_item");
    }
}
