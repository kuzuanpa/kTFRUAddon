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
        if (id > ComputePower.length||id<0) return 0;
        return ComputePower[id];
    }
    private static final long[] ComputePower ={3,4,84,102,653,816,3422,4037,10289,12554,18132,25633,30568,65130,45311,53312,110023,70632,81131,183077,73909,156657,102563,238567};
    @Override
    public void addItems() {
        ItemList.ComputerTF3386.set(addItem(0,"TF3386 Computer","Computing Power: 4 MFLOPS"));
        ItemList.ComputerTF3386S.set(addItem(1,"TF3386S Computer","Computing Power: 7 MFLOPS"));
        ItemList.ComputerTF3586.set(addItem(2,"TF3586 Computer","Computing Power: 84 MFLOPS"));
        ItemList.ComputerTF3586S.set(addItem(3,"TF3586S Computer","Computing Power: 102 MFLOPS"));
        ItemList.ComputerGT1000.set(addItem(4,"GT1000 Computer","Computing Power: 653 MFLOPS"));
        ItemList.ComputerGT1090.set(addItem(5,"GT1090 Computer","Computing Power: 816 MFLOPS"));
        ItemList.ComputerGT2000.set(addItem(6,"GT2000 Computer","Computing Power: 3422 MFLOPS"));
        ItemList.ComputerGT2090.set(addItem(7,"GT2090 Computer","Computing Power: 4037 MFLOPS"));
        ItemList.ComputerGT2660.set(addItem(8,"GT2660 Computer","Computing Power: 10289 MFLOPS"));
        ItemList.ComputerGT2680.set(addItem(9,"GT2680 Computer","Computing Power: 12554 MFLOPS"));
        ItemList.ComputerGT2699.set(addItem(10,"GT2699 Computer","Computing Power: 18132 MFLOPS"));
        ItemList.ComputerGT2660v2.set(addItem(11,"GT2660v2 Computer","Computing Power: 25633 MFLOPS"));
        ItemList.ComputerGT2680v2.set(addItem(12,"GT2680v2 Computer","Computing Power: 30568 MFLOPS"));
        ItemList.ComputerGT2699v2.set(addItem(13,"GT2699v2 Computer","Computing Power: 65130 MFLOPS"));
        ItemList.ComputerGT2660v3.set(addItem(14,"GT2660v3 Computer","Computing Power: 45311 MFLOPS"));
        ItemList.ComputerGT2680v3.set(addItem(15,"GT2680v3 Computer","Computing Power: 53312 MFLOPS"));
        ItemList.ComputerGT2699v3.set(addItem(16,"GT2699v3 Computer","Computing Power: 110031 MFLOPS"));
        ItemList.ComputerGT2660v4.set(addItem(17,"GT2660v4 Computer","Computing Power: 70632 MFLOPS"));
        ItemList.ComputerGT2680v4.set(addItem(18,"GT2680v4 Computer","Computing Power: 81131 MFLOPS"));
        ItemList.ComputerGT2699v4.set(addItem(19,"GT2699v4 Computer","Computing Power: 183077 MFLOPS"));
        ItemList.ComputerGT2680v3e.set(addItem(20,"GT2680v3e Computer","Computing Power: 73909 MFLOPS"));
        ItemList.ComputerGT2699v3e.set(addItem(21,"GT2699v3e Computer","Computing Power: 156657 MFLOPS"));
        ItemList.ComputerGT2680v4e.set(addItem(22,"GT2680v4e Computer","Computing Power: 102563 MFLOPS"));
        ItemList.ComputerGT2699v4e.set(addItem(23,"GT2699v4e Computer","Computing Power: 228567 MFLOPS"));

    }
}
