package cn.kuzuanpa.ktfruaddon.item.item;

import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class itemParticle extends MultiItemRandom {
    public itemParticle() {
        super(MOD_ID, "ktfru.item.particle");
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: Particles", this, (short) 1008));
    }


    @Override
    public void addItems() {
        //Copy From GT6U
        ItemList.Proton.set(addItem(1000, "Proton", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Anti_Proton.set(addItem(1001, "Anti Proton", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Electron.set(addItem(1002, "Electron", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Positron.set(addItem(1003, "Positron (Anti Electron)", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Neutron.set(addItem(1004, "Neutron", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Neutrino.set(addItem(1005, "Neutrino", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Anti_Neutrino.set(addItem(1006, "Anti Neutrino", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Alpha_Particle.set(addItem(1007, "Alpha Particle", "The nucleus of helium. "));
        ItemList.Higgs_Boson.set(addItem(1008, "Higgs-Boson", "A Standard Model particle. Origin of mass. "));
        ItemList.Kerr_Blackhole.set(addItem(1009, "Kerr Blackhole", "An extremely rare tiny blackhole that can be manually produced in particle collider "));

    };
}
