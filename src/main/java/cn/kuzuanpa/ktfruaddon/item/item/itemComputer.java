/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.item.item;

import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;
import net.minecraft.item.Item;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class itemComputer extends MultiItemRandom {
    public itemComputer() {
    super(MOD_ID, "ktfru.item.it.computer");
    setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: Computers", this, (short) 9999));
}
    public static long getComputePowerFromID(int id){
        if (id > ComputePower.length) return 0;
        return ComputePower[id];
    }
    private static final long[] ComputePower ={1,4,12,80,135};
    @Override
    public void addItems() {
        ItemList.ComputeNodeTF386.set(addItem(0,"TF386 Compute Node","Ex"));
        ItemList.ComputeNodeTF586.set(addItem(1,"TF586 Compute Node","Ex"));
        ItemList.ComputeNodeAE300.set(addItem(2,"AE-300 Compute Node","Ex"));
        ItemList.ComputeNodeAE740.set(addItem(3,"AE-740 Compute Node","Ex"));
        ItemList.ComputeNodeGT200.set(addItem(4,"GT-200 Compute Node","Ex"));
        ItemList.ComputeNodeGT3490.set(addItem(5,"GT-3490 Compute Node","Ex"));
        ItemList.ComputeNodeGT6799.set(addItem(6,"GT-6799 Compute Node","Ex"));
        ItemList.ComputeNodeGT9890.set(addItem(7,"GT-9890 Compute Node","Ex"));
        ItemList.ComputeNodeGT13586.set(addItem(8,"GT-13586 Compute Node","Ex"));
        ItemList.ComputeNodeIF2.set(addItem(9,"IF2 Compute Node","Ex"));
        ItemList.ComputeNodeIF7.set(addItem(10,"IF7 Compute Node","Ex"));
        ItemList.ComputeNodeTF386e.set(addItem(11,"Compute Node TF386e","Ex"));
        ItemList.ComputeNodeTF586e.set(addItem(12,"Compute Node TF586e","Ex"));
        ItemList.ComputeNodeAE300s.set(addItem(13,"Compute Node AE300s","Ex"));
        ItemList.ComputeNodeAE740s.set(addItem(14,"Compute Node AE740s","Ex"));
        ItemList.ComputeNodeGT200s.set(addItem(15,"Compute Node GT200s","Ex"));
        ItemList.ComputeNodeGT3490s.set(addItem(16,"Compute Node GT3490s","Ex"));
        ItemList.ComputeNodeGT6799s.set(addItem(17,"Compute Node GT6799s","Ex"));
        ItemList.ComputeNodeGT9890s.set(addItem(18,"Compute Node GT9890s","Ex"));
        ItemList.ComputeNodeGT13586s.set(addItem(19,"Compute Node GT13586s","Ex"));

    }
}
