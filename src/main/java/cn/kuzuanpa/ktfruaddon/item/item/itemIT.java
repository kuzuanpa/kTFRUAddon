package cn.kuzuanpa.ktfruaddon.item.item;

import cn.kuzuanpa.ktfruaddon.item.util.ItemLoader;
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class itemIT extends MultiItemRandom {
    public itemIT() {
        super(MOD_ID, "ktfru.item.it");
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: IT", this, (short) 9999));

    }

    @Override
    public void addItems() {
        ItemLoader.BioCircuit.set(addItem(9999, "BioCircuit", "A Circuit that uses live brain. The \"Real Artificial Intelligence\""));
        ItemLoader.NeuralComputer.set(addItem(10000,"Neural network computer","Extremely fast and intelligent With \"Neural Network\" Technology"));
    }
}