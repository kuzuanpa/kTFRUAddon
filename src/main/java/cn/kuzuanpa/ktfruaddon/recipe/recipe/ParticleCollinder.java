package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.item.util.ItemLoader;
import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.util.ST;

import static cn.kuzuanpa.ktfruaddon.recipe.recipeManager.*;
import static gregapi.data.CS.*;

public class ParticleCollinder {
    public ParticleCollinder() {
        //Copied from GT6U
        ParticleCollider.addRecipe1(T, 8192, 20, new long[]{100, 100, 10, 200, 200, 50}, ST.tag(1), FL.Hydrogen.make(10), FL.Hydrogen.make(5), ItemLoader.Proton.get(1), ItemLoader.Anti_Proton.get(1), ItemLoader.Neutron.get(1), ItemLoader.Electron.get(1), ItemLoader.Positron.get(1), ItemLoader.Neutrino.get(1)).setSpecialNumber(10000000);
        ParticleCollider.addRecipe1(T, 8192, 40, new long[]{80, 80, 8, 160, 160, 30}, ST.tag(1), FL.Helium.make(10), FL.Helium.make(6), ItemLoader.Proton.get(1), ItemLoader.Anti_Proton.get(1), ItemLoader.Neutron.get(1), ItemLoader.Electron.get(1), ItemLoader.Positron.get(1), ItemLoader.Neutrino.get(1)).setSpecialNumber(10000000);
        ParticleCollider.addRecipeX(T, 32768, 20, new long[]{200, 200, 20, 5, 100, 1}, ST.array(ItemLoader.Electron.get(10), ItemLoader.Positron.get(10)), FL.array(MT.Xe.gas(U, T)), FL.array(MT.Xe.gas(U, T)), ItemLoader.Proton.get(10), ItemLoader.Anti_Proton.get(10), ItemLoader.Neutron.get(10), ItemLoader.Higgs_Boson.get(10), ItemLoader.Neutrino.get(10), ItemLoader.Kerr_Blackhole.get(10)).setSpecialNumber(10000000);
        ParticleCollider.addRecipeX(T, 32768, 40, new long[]{300, 300, 15, 5, 75, 1}, ST.array(ST.tag(1), ItemLoader.Proton.get(20)), FL.array(MT.Xe.gas(U, T)), FL.array(MT.Xe.gas(U, T)), ItemLoader.Electron.get(10), ItemLoader.Positron.get(10), ItemLoader.Neutron.get(10), ItemLoader.Higgs_Boson.get(10), ItemLoader.Neutrino.get(10), ItemLoader.Kerr_Blackhole.get(10)).setSpecialNumber(10000000);
    }
}
