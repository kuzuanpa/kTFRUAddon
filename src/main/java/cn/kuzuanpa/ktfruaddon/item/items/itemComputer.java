/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.item.items;

import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class itemComputer extends MultiItemRandom {
    public itemComputer() {
    super(MOD_ID, "ktfru.item.it.computer");
    setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: Computers", this, (short) 17));
}
    public static long getComputePowerFromID(int id){
        if (id > ComputePower.length||id<0) return 0;
        return ComputePower[id];
    }
    //Index:                                   0,1,2 ,3  ,4  ,5  ,6   ,7   ,8    ,9    ,10   ,11   ,12   ,13   ,14   ,15   ,16    ,17   ,18   ,19    ,20   ,21    ,22    ,23    ,24,25,26 ,27  ,28   ,29   ,30 ,31  ,32  ,33   ,34   ,35
    private static final long[] ComputePower ={4,6,84,102,653,816,3422,4037,10289,12554,18132,25633,30568,65130,45311,53312,110023,80632,91131,183077,93909,156657,102563,228567,1,102,416,3412,11239,32623,200,1000,3000,15000,50000,150000};
    @Override
    public void addItems() {
        ItemList.ComputerTF3386          .set(addItem(0 ,"TF3386 Computer"   ,"Computing Power: 4 MFLOPS"))                .registerOre("ktfruBasicComputer");
        ItemList.ComputerTF3386S         .set(addItem(1 ,"TF3386S Computer"  ,"Computing Power: 7 MFLOPS"))                .registerOre("ktfruBasicComputer");
        ItemList.ComputerTF3586          .set(addItem(2 ,"TF3586 Computer"   ,"Computing Power: 84 MFLOPS"))               .registerOre("ktfruBasicComputer");
        ItemList.ComputerTF3586S         .set(addItem(3 ,"TF3586S Computer"  ,"Computing Power: 102 MFLOPS"))              .registerOre("ktfruBasicComputer");
        ItemList.ComputerGT1000          .set(addItem(4 ,"GT1000 Computer"   ,"Computing Power: 653 MFLOPS"))              .registerOre(    "ktfruNoviceComputer");
        ItemList.ComputerGT1090          .set(addItem(5 ,"GT1090 Computer"   ,"Computing Power: 816 MFLOPS"))              .registerOre(    "ktfruNoviceComputer");
        ItemList.ComputerGT2000          .set(addItem(6 ,"GT2000 Computer"   ,"Computing Power: 3422 MFLOPS"))             .registerOre(        "ktfruModerateComputer");
        ItemList.ComputerGT2090          .set(addItem(7 ,"GT2090 Computer"   ,"Computing Power: 4037 MFLOPS"))             .registerOre(        "ktfruModerateComputer");
        ItemList.ComputerGT3660          .set(addItem(8 ,"GT3660 Computer"   ,"Computing Power: 10289 MFLOPS"))            .registerOre(        "ktfruModerateComputer");
        ItemList.ComputerGT3680          .set(addItem(9 ,"GT3680 Computer"   ,"Computing Power: 12554 MFLOPS"))            .registerOre(        "ktfruModerateComputer");
        ItemList.ComputerGT3699          .set(addItem(10,"GT3699 Computer"   ,"Computing Power: 18132 MFLOPS"))            .registerOre(            "ktfruAdvancedComputer");
        ItemList.ComputerGT3660v2        .set(addItem(11,"GT3660v2 Computer" ,"Computing Power: 25633 MFLOPS"))            .registerOre(            "ktfruAdvancedComputer");
        ItemList.ComputerGT3680v2        .set(addItem(12,"GT3680v2 Computer" ,"Computing Power: 30568 MFLOPS"))            .registerOre(            "ktfruAdvancedComputer");
        ItemList.ComputerGT3699v2        .set(addItem(13,"GT3699v2 Computer" ,"Computing Power: 65130 MFLOPS"))            .registerOre(                "ktfruEliteComputer");
        ItemList.ComputerGT3660v3        .set(addItem(14,"GT3660v3 Computer" ,"Computing Power: 45311 MFLOPS"))            .registerOre(            "ktfruAdvancedComputer");
        ItemList.ComputerGT3680v3        .set(addItem(15,"GT3680v3 Computer" ,"Computing Power: 53312 MFLOPS"))            .registerOre(                "ktfruEliteComputer");
        ItemList.ComputerGT3699v3        .set(addItem(16,"GT3699v3 Computer" ,"Computing Power: 110023 MFLOPS"))           .registerOre(                    "ktfruMasterComputer");
        ItemList.ComputerGT3660v4        .set(addItem(17,"GT3660v4 Computer" ,"Computing Power: 80632 MFLOPS"))            .registerOre(                    "ktfruMasterComputer");
        ItemList.ComputerGT3680v4        .set(addItem(18,"GT3680v4 Computer" ,"Computing Power: 91131 MFLOPS"))            .registerOre(                    "ktfruMasterComputer");
        ItemList.ComputerGT3699v4        .set(addItem(19,"GT3699v4 Computer" ,"Computing Power: 183077 MFLOPS"))           .registerOre(                        "ktfruUltimateComputer");

        ItemList.ComputerGT3680v3e       .set(addItem(20,"GT3680v3e Computer","Computing Power: 93909 MFLOPS"))            .registerOre(                    "ktfruMasterComputer");
        ItemList.ComputerGT3699v3e       .set(addItem(21,"GT3699v3e Computer","Computing Power: 156657 MFLOPS"))           .registerOre(                        "ktfruUltimateComputer");
        ItemList.ComputerGT3680v4e       .set(addItem(22,"GT3680v4e Computer","Computing Power: 102563 MFLOPS"))           .registerOre(                        "ktfruUltimateComputer");
        ItemList.ComputerGT3699v4e       .set(addItem(23,"GT3699v4e Computer","Computing Power: 228567 MFLOPS"))           .registerOre(                        "ktfruUltimateComputer");

        ItemList.ComputerBasicCircuits   .set(addItem(24,"Basic Circuits Computer","Computing Power: 1 MFLOPS"))           .registerOre("ktfruBasicComputer");
        ItemList.ComputerGoodCircuits    .set(addItem(25,"Good Circuits Computer","Computing Power: 102 MFLOPS"))          .registerOre("ktfruBasicComputer");
        ItemList.ComputerAdvancedCircuits.set(addItem(26,"Advanced Circuits Computer","Computing Power: 416 MFLOPS"))      .registerOre(    "ktfruNoviceComputer");
        ItemList.ComputerEliteCircuits   .set(addItem(27,"Elite Circuits Computer","Computing Power: 3412 MFLOPS"))        .registerOre(        "ktfruModerateComputer");
        ItemList.ComputerMasterCircuits  .set(addItem(28,"Master Circuits Computer","Computing Power: 11239 MFLOPS"))      .registerOre(        "ktfruModerateComputer");
        ItemList.ComputerUltimateCircuits.set(addItem(29,"Ultimate Circuits Computer","Computing Power: 32623 MFLOPS"))    .registerOre(            "ktfruAdvancedComputer");

        ItemList.UnderClockedNoviceComputer  .set(addItem(30,"Under Clocked Novice Computer","Computing Power: 200 MFLOPS"))     .registerOre("ktfruBasicComputer");
        ItemList.UnderClockedModerateComputer.set(addItem(31,"Under Clocked Moderate Computer","Computing Power: 1000 MFLOPS"))  .registerOre(    "ktfruNoviceComputer");
        ItemList.UnderClockedAdvancedComputer.set(addItem(32,"Under Clocked Advanced Computer","Computing Power: 3000 MFLOPS"))  .registerOre(        "ktfruModerateComputer");
        ItemList.UnderClockedEliteComputer   .set(addItem(33,"Under Clocked Elite Computer","Computing Power: 15000 MFLOPS"))    .registerOre(            "ktfruAdvancedComputer");
        ItemList.UnderClockedMasterComputer  .set(addItem(34,"Under Clocked Master Computer","Computing Power: 50000 MFLOPS"))   .registerOre(                "ktfruEliteComputer");
        ItemList.UnderClockedUltimateComputer.set(addItem(35,"Under Clocked Ultimate Computer","Computing Power: 150000 MFLOPS")).registerOre(                    "ktfruMasterComputer");

    }
}
