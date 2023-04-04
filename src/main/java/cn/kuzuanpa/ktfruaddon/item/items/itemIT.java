/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.item.items;

import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;

import static cn.kuzuanpa.ktfruaddon.item.util.ItemList.*;
import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class itemIT extends MultiItemRandom {
    public itemIT() {
        super(MOD_ID, "ktfru.item.it");
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: IT", this, (short) 9999));

    }

    @Override
    public void addItems() {
        SiliconBoulePure.set(addItem(9701,"Pure Silicon Boule","Ready to Cut into Plates"));
        MoO2Boule.set(addItem(9702,"MoO2 Boule","Ready to Cut into Plates"));
        MoO2BoulePure.set(addItem(9703,"Pure MoO2 Boule","Ready to Cut into Plates"));

        SiliconPlate8inchTier1.set(addItem(9800,"SiliconPlate8inchTier1","SiliconPlate8inchTier1"));
        SiliconPlate8inchTier2.set(addItem(9801,"SiliconPlate8inchTier2","SiliconPlate8inchTier2"));
        MoO2Plate8inchTier1.set(addItem(9802,"MoO2Plate8inchTier1","MoO2Plate8inchTier1"));
        MoO2Plate8inchTier2.set(addItem(9803,"MoO2Plate8inchTier2","MoO2Plate8inchTier2"));
        GraphitePlate8inchTier1.set(addItem(9804,"GraphitePlate8inchTier1","GraphitePlate8inchTier1"));
        GraphitePlate8inchTier2.set(addItem(9805,"GraphitePlate8inchTier2","GraphitePlate8inchTier2"));
        
        SiliconPlate8inchCleanedTier1.set(addItem(9810,"SiliconPlate8inchCleanedTier1","SiliconPlate8inchCleanedTier1"));
        SiliconPlate8inchCleanedTier2.set(addItem(9811,"SiliconPlate8inchCleanedTier2","SiliconPlate8inchCleanedTier2"));
        MoO2Plate8inchCleanedTier1.set(addItem(9812,"MoO2Plate8inchCleanedTier1","MoO2Plate8inchCleanedTier1"));
        MoO2Plate8inchCleanedTier2.set(addItem(9813,"MoO2Plate8inchCleanedTier2","MoO2Plate8inchCleanedTier2"));
        GraphitePlate8inchCleanedTier1.set(addItem(9814,"GraphitePlate8inchCleanedTier1","GraphitePlate8inchCleanedTier1"));
        GraphitePlate8inchCleanedTier2.set(addItem(9815,"GraphitePlate8inchCleanedTier2","GraphitePlate8inchCleanedTier2"));
        
        SiliconPlate8inchCoatedTier1.set(addItem(9820,"SiliconPlate8inchCoatedTier1","SiliconPlate8inchCoatedTier1"));
        SiliconPlate8inchCoatedTier2.set(addItem(9821,"SiliconPlate8inchCoatedTier2","SiliconPlate8inchCoatedTier2"));
        MoO2Plate8inchCoatedTier1.set(addItem(9822,"MoO2Plate8inchCoatedTier1","MoO2Plate8inchCoatedTier1"));
        MoO2Plate8inchCoatedTier2.set(addItem(9823,"MoO2Plate8inchCoatedTier2","MoO2Plate8inchCoatedTier2"));
        GraphitePlate8inchCoatedTier1.set(addItem(9824,"GraphitePlate8inchCoatedTier1","GraphitePlate8inchCoatedTier1"));
        GraphitePlate8inchCoatedTier2.set(addItem(9825,"GraphitePlate8inchCoatedTier2","GraphitePlate8inchCoatedTier2"));
        
        SiliconPlate8inchSoftBakedTier1.set(addItem(9830,"SiliconPlate8inchSoftBakedTier1","SiliconPlate8inchSoftBakedTier1"));
        SiliconPlate8inchSoftBakedTier2.set(addItem(9831,"SiliconPlate8inchSoftBakedTier2","SiliconPlate8inchSoftBakedTier2"));
        MoO2Plate8inchSoftBakedTier1.set(addItem(9832,"MoO2Plate8inchSoftBakedTier1","MoO2Plate8inchSoftBakedTier1"));
        MoO2Plate8inchSoftBakedTier2.set(addItem(9833,"MoO2Plate8inchSoftBakedTier2","MoO2Plate8inchSoftBakedTier2"));
        GraphitePlate8inchSoftBakedTier1.set(addItem(9834,"GraphitePlate8inchSoftBakedTier1","GraphitePlate8inchSoftBakedTier1"));
        GraphitePlate8inchSoftBakedTier2.set(addItem(9835,"GraphitePlate8inchSoftBakedTier2","GraphitePlate8inchSoftBakedTier2"));

        CPUPhotomask200um.set(addItem(9900,"CPU Photo Mask (200um)","CPU Ex"));
        CPUPhotomask72um.set(addItem(9901,"CPU Photo Mask (72um)","CPU Ex"));
        CPUPhotomask40um.set(addItem(9902,"CPU Photo Mask (40um)","CPU Ex"));
        CPUPhotomask8um.set(addItem(9903,"CPU Photo Mask (8um)","CPU Ex"));
        CPUPhotomask400nm.set(addItem(9904,"CPU Photo Mask (400nm)","CPU Ex"));
        CPUPhotomask80nm.set(addItem(9905,"CPU Photo Mask (80nm)","CPU Ex"));
        CPUPhotomask32nm.set(addItem(9906,"CPU Photo Mask (32nm)","CPU Ex"));
        CPUPhotomask14nm.set(addItem(9907,"CPU Photo Mask (14nm)","CPU Ex"));
        CPUPhotomask7nm.set(addItem(9908,"CPU Photo Mask (7nm)","CPU Ex"));
        CPUPhotomask5nm.set(addItem(9909,"CPU Photo Mask (5nm)","CPU Ex"));
        CPUPhotomask2nm.set(addItem(9910,"CPU Photo Mask (2nm)","CPU Ex"));


        CPUWafer200um.set(addItem(9950,"CPU Wafer (200um)","CPU Ex"));
        CPUWafer72um.set(addItem(9951,"CPU Wafer (72um)","CPU Ex"));
        CPUWafer40um.set(addItem(9952,"CPU Wafer (40um)","CPU Ex"));
        CPUWafer8um.set(addItem(9953,"CPU Wafer (8um)","CPU Ex"));
        CPUWafer400nm.set(addItem(9954,"CPU Wafer (400nm)","CPU Ex"));
        CPUWafer80nm.set(addItem(9955,"CPU Wafer (80nm)","CPU Ex"));
        CPUWafer32nm.set(addItem(9956,"CPU Wafer (32nm)","CPU Ex"));
        CPUWafer14nm.set(addItem(9957,"CPU Wafer (14nm)","CPU Ex"));
        CPUWafer7nm.set(addItem(9958,"CPU Wafer (7nm)","CPU Ex"));
        CPUWafer5nm.set(addItem(9959,"CPU Wafer (5nm)","CPU Ex"));
        CPUWafer2nm.set(addItem(9960,"CPU Wafer (2nm)","CPU Ex"));

        CPUWafer200umDeveloped.set(addItem(10000,"CPU Wafer200umDeveloped","CPU Wafer200umDeveloped"));
        CPUWafer72umDeveloped.set(addItem(10001,"CPU Wafer72umDeveloped","CPU Wafer72umDeveloped"));
        CPUWafer40umDeveloped.set(addItem(10002,"CPU Wafer40umDeveloped","CPU Wafer40umDeveloped"));
        CPUWafer8umDeveloped.set(addItem(10003,"CPU Wafer8umDeveloped","CPU Wafer8umDeveloped"));
        CPUWafer400nmDeveloped.set(addItem(10004,"CPU Wafer400nmDeveloped","CPU Wafer400nmDeveloped"));
        CPUWafer80nmDeveloped.set(addItem(10005,"CPU Wafer80nmDeveloped","CPU Wafer80nmDeveloped"));
        CPUWafer32nmDeveloped.set(addItem(10006,"CPU Wafer32nmDeveloped","CPU Wafer32nmDeveloped"));
        CPUWafer14nmDeveloped.set(addItem(10007,"CPU Wafer14nmDeveloped","CPU Wafer14nmDeveloped"));
        CPUWafer7nmDeveloped.set(addItem(10008,"CPU Wafer7nmDeveloped","CPU Wafer7nmDeveloped"));
        CPUWafer5nmDeveloped.set(addItem(10009,"CPU Wafer5nmDeveloped","CPU Wafer5nmDeveloped"));
        CPUWafer2nmDeveloped.set(addItem(10010,"CPU Wafer2nmDeveloped","CPU Wafer2nmDeveloped"));

        CPUWafer200umHardBaked.set(addItem(10050,"CPU Wafer200umHardBaked","CPU Wafer200umHardBaked"));
        CPUWafer72umHardBaked.set(addItem(10051,"CPU Wafer72umHardBaked","CPU Wafer72umHardBaked"));
        CPUWafer40umHardBaked.set(addItem(10052,"CPU Wafer40umHardBaked","CPU Wafer40umHardBaked"));
        CPUWafer8umHardBaked.set(addItem(10053,"CPU Wafer8umHardBaked","CPU Wafer8umHardBaked"));
        CPUWafer400nmHardBaked.set(addItem(10054,"CPU Wafer400nmHardBaked","CPU Wafer400nmHardBaked"));
        CPUWafer80nmHardBaked.set(addItem(10055,"CPU Wafer80nmHardBaked","CPU Wafer80nmHardBaked"));
        CPUWafer32nmHardBaked.set(addItem(10056,"CPU Wafer32nmHardBaked","CPU Wafer32nmHardBaked"));
        CPUWafer14nmHardBaked.set(addItem(10057,"CPU Wafer14nmHardBaked","CPU Wafer14nmHardBaked"));
        CPUWafer7nmHardBaked.set(addItem(10058,"CPU Wafer7nmHardBaked","CPU Wafer7nmHardBaked"));
        CPUWafer5nmHardBaked.set(addItem(10059,"CPU Wafer5nmHardBaked","CPU Wafer5nmHardBaked"));
        CPUWafer2nmHardBaked.set(addItem(10060,"CPU Wafer2nmHardBaked","CPU Wafer2nmHardBaked"));

        CPUDie200um.set(addItem(10100,"CPUDie200um","CPUDie200um"));
        CPUDie72um.set(addItem(10101,"CPUDie72um","CPUDie72um"));
        CPUDie40um.set(addItem(10102,"CPUDie40um","CPUDie40um"));
        CPUDie8um.set(addItem(10103,"CPUDie8um","CPUDie8um"));
        CPUDie400nm.set(addItem(10104,"CPUDie400nm","CPUDie400nm"));
        CPUDie80nm.set(addItem(10105,"CPUDie80nm","CPUDie80nm"));
        CPUDie32nm.set(addItem(10106,"CPUDie32nm","CPUDie32nm"));
        CPUDie14nm.set(addItem(10107,"CPUDie14nm","CPUDie14nm"));
        CPUDie7nm.set(addItem(10108,"CPUDie7nm","CPUDie7nm"));
        CPUDie5nm.set(addItem(10109,"CPUDie5nm","CPUDie5nm"));
        CPUDie2nm.set(addItem(10110,"CPUDie2nm","CPUDie2nm"));


        CPUTF386.set(addItem(10200,"CPU TF386","Ex"));
        CPUTF586.set(addItem(10201,"CPU TF586","Ex"));
        CPUAE300.set(addItem(10202,"CPU AE-300","Ex"));
        CPUAE740.set(addItem(10203,"CPU AE-740","Ex"));
        CPUGT200.set(addItem(10204,"CPU GT-200","Ex"));
        CPUGT3490.set(addItem(10205,"CPU GT-3490","Ex"));
        CPUGT6799.set(addItem(10206,"CPU GT-6799","Ex"));
        CPUGT9890.set(addItem(10207,"CPU GT-9890","Ex"));
        CPUGT13586.set(addItem(10208,"CPU GT-13586","Ex"));
        CPUIF2.set(addItem(10209,"CPU IF2","Ex"));
        CPUIF7.set(addItem(10210,"CPU IF7","Ex"));
        
        CPUTF386e.set(addItem(10250,"CPU TF386e","Ex"));
        CPUTF586e.set(addItem(10251,"CPU TF586e","Ex"));
        CPUAE300s.set(addItem(10252,"CPU AE-300s","Ex"));
        CPUAE740s.set(addItem(10253,"CPU AE-740s","Ex"));
        CPUGT200s.set(addItem(10254,"CPU GT-200s","Ex"));
        CPUGT3490s.set(addItem(10255,"CPU GT-3490s","Ex"));
        CPUGT6799s.set(addItem(10256,"CPU GT-6799s","Ex"));
        CPUGT9890s.set(addItem(10257,"CPU GT-9890s","Ex"));
        CPUGT13586s.set(addItem(10258,"CPU GT-13586s","Ex"));
    }
}