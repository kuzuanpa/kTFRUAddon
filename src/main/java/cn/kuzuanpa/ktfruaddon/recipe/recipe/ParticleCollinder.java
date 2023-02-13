/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.util.ST;

import static cn.kuzuanpa.ktfruaddon.recipe.recipeManager.*;
import static gregapi.data.CS.*;

public class ParticleCollinder {
    public ParticleCollinder() {
        //Copied from GT6U
        ParticleCollider.addRecipe1(T, 8192, 20, new long[]{100, 100, 10, 200, 200, 50}, ST.tag(1), FL.Hydrogen.make(10), FL.Hydrogen.make(5), ItemList.Proton.get(1), ItemList.Anti_Proton.get(1), ItemList.Neutron.get(1), ItemList.Electron.get(1), ItemList.Positron.get(1), ItemList.Neutrino.get(1)).setSpecialNumber(10000000);
        ParticleCollider.addRecipe1(T, 8192, 40, new long[]{80, 80, 8, 160, 160, 30}, ST.tag(1), FL.Helium.make(10), FL.Helium.make(6), ItemList.Proton.get(1), ItemList.Anti_Proton.get(1), ItemList.Neutron.get(1), ItemList.Electron.get(1), ItemList.Positron.get(1), ItemList.Neutrino.get(1)).setSpecialNumber(10000000);
        ParticleCollider.addRecipeX(T, 32768, 20, new long[]{200, 200, 20, 5, 100, 1}, ST.array(ItemList.Electron.get(10), ItemList.Positron.get(10)), FL.array(MT.Xe.gas(U, T)), FL.array(MT.Xe.gas(U, T)), ItemList.Proton.get(10), ItemList.Anti_Proton.get(10), ItemList.Neutron.get(10), ItemList.Higgs_Boson.get(10), ItemList.Neutrino.get(10), ItemList.Kerr_Blackhole.get(10)).setSpecialNumber(10000000);
        ParticleCollider.addRecipeX(T, 32768, 40, new long[]{300, 300, 15, 5, 75, 1}, ST.array(ST.tag(1), ItemList.Proton.get(20)), FL.array(MT.Xe.gas(U, T)), FL.array(MT.Xe.gas(U, T)), ItemList.Electron.get(10), ItemList.Positron.get(10), ItemList.Neutron.get(10), ItemList.Higgs_Boson.get(10), ItemList.Neutrino.get(10), ItemList.Kerr_Blackhole.get(10)).setSpecialNumber(10000000);
    }
}
