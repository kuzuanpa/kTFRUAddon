/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.item.item;

import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class itemIT extends MultiItemRandom {
    public static final String unlocalizedName = "ktfru.item.it";
    public itemIT() {
        super(MOD_ID, unlocalizedName);
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: IT", this, (short) 9999));

    }

    @Override
    public void addItems() {
        ItemList.Wafer200um.set(addItem(10000,"Wafer (200um)","Ex"));
        ItemList.Wafer72um.set(addItem(10001,"Wafer (72um)","Ex"));
        ItemList.Wafer40um.set(addItem(10002,"Wafer (40um)","Ex"));
        ItemList.Wafer8um.set(addItem(10003,"Wafer (8um)","Ex"));
        ItemList.Wafer400nm.set(addItem(10004,"Wafer (400nm)","Ex"));
        ItemList.Wafer80nm.set(addItem(10005,"Wafer (80nm)","Ex"));
        ItemList.Wafer32nm.set(addItem(10006,"Wafer (32nm)","Ex"));
        ItemList.Wafer14nm.set(addItem(10007,"Wafer (14nm)","Ex"));
        ItemList.Wafer7nm.set(addItem(10008,"Wafer (7nm)","Ex"));
        ItemList.Wafer5nm.set(addItem(10009,"Wafer (5nm)","Ex"));
        ItemList.Wafer2nm.set(addItem(10010,"Wafer (2nm)","Ex"));
        
        ItemList.CPUTF386.set(addItem(10100,"CPU TF386","Ex"));
        ItemList.CPUTF586.set(addItem(10101,"CPU TF586","Ex"));
        ItemList.CPUAE300.set(addItem(10102,"CPU AE-300","Ex"));
        ItemList.CPUAE740.set(addItem(10103,"CPU AE-740","Ex"));
        ItemList.CPUGT200.set(addItem(10104,"CPU GT-200","Ex"));
        ItemList.CPUGT3490.set(addItem(10105,"CPU GT-3490","Ex"));
        ItemList.CPUGT6799.set(addItem(10106,"CPU GT-6799","Ex"));
        ItemList.CPUGT9890.set(addItem(10107,"CPU GT-9890","Ex"));
        ItemList.CPUGT13586.set(addItem(10108,"CPU GT-13586","Ex"));
        ItemList.CPUTF386e.set(addItem(10200,"CPU TF386e","Ex"));
        ItemList.CPUTF586e.set(addItem(10201,"CPU TF586e","Ex"));
        ItemList.CPUAE300s.set(addItem(10202,"CPU AE-300s","Ex"));
        ItemList.CPUAE740s.set(addItem(10203,"CPU AE-740s","Ex"));
        ItemList.CPUGT200s.set(addItem(10204,"CPU GT-200s","Ex"));
        ItemList.CPUGT3490s.set(addItem(10205,"CPU GT-3490s","Ex"));
        ItemList.CPUGT6799s.set(addItem(10206,"CPU GT-6799s","Ex"));
        ItemList.CPUGT9890s.set(addItem(10207,"CPU GT-9890s","Ex"));
        ItemList.CPUGT13586s.set(addItem(10208,"CPU GT-13586s","Ex"));

        ItemList.BioCircuit.set(addItem(10998, "BioCircuit", "A Circuit that uses live brain. The \"Real Artificial Intelligence\""));
        ItemList.NeuralComputer.set(addItem(10999,"Neural network computer","Extremely fast and intelligent With \"Neural Network\" Technology"));
    }
}