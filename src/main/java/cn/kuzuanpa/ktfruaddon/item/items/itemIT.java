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
        GraphitePlate8inch.set(addItem(9804,"GraphitePlate8inchTier1","GraphitePlate8inchTier1"));

        SiliconPlate8inchCleanedTier1.set(addItem(9810,"SiliconPlate8inchCleanedTier1","SiliconPlate8inchCleanedTier1"));
        SiliconPlate8inchCleanedTier2.set(addItem(9811,"SiliconPlate8inchCleanedTier2","SiliconPlate8inchCleanedTier2"));
        MoO2Plate8inchCleanedTier1.set(addItem(9812,"MoO2Plate8inchCleanedTier1","MoO2Plate8inchCleanedTier1"));
        MoO2Plate8inchCleanedTier2.set(addItem(9813,"MoO2Plate8inchCleanedTier2","MoO2Plate8inchCleanedTier2"));
        GraphitePlate8inchCleaned.set(addItem(9814,"GraphitePlate8inchCleanedTier1","GraphitePlate8inchCleanedTier1"));

        SiliconPlate8inchCoatedTier1.set(addItem(9820,"SiliconPlate8inchCoatedTier1","SiliconPlate8inchCoatedTier1"));
        SiliconPlate8inchCoatedTier2.set(addItem(9821,"SiliconPlate8inchCoatedTier2","SiliconPlate8inchCoatedTier2"));
        MoO2Plate8inchCoatedTier1.set(addItem(9822,"MoO2Plate8inchCoatedTier1","MoO2Plate8inchCoatedTier1"));
        MoO2Plate8inchCoatedTier2.set(addItem(9823,"MoO2Plate8inchCoatedTier2","MoO2Plate8inchCoatedTier2"));
        GraphitePlate8inchCoated.set(addItem(9824,"GraphitePlate8inchCoatedTier1","GraphitePlate8inchCoatedTier1"));

        SiliconPlate8inchSoftBakedTier1.set(addItem(9830,"SiliconPlate8inchSoftBakedTier1","SiliconPlate8inchSoftBakedTier1"));
        SiliconPlate8inchSoftBakedTier2.set(addItem(9831,"SiliconPlate8inchSoftBakedTier2","SiliconPlate8inchSoftBakedTier2"));
        MoO2Plate8inchSoftBakedTier1.set(addItem(9832,"MoO2Plate8inchSoftBakedTier1","MoO2Plate8inchSoftBakedTier1"));
        MoO2Plate8inchSoftBakedTier2.set(addItem(9833,"MoO2Plate8inchSoftBakedTier2","MoO2Plate8inchSoftBakedTier2"));
        GraphitePlate8inchSoftBaked.set(addItem(9834,"GraphitePlate8inchSoftBakedTier1","GraphitePlate8inchSoftBakedTier1"));

        CPUPhotomask200um.set(addItem(9900,"CPU Photo Mask (200um)","CPU Ex"));
        CPUPhotomask72um.set(addItem(9901,"CPU Photo Mask (72um)","CPU Ex"));
        CPUPhotomask40um.set(addItem(9902,"CPU Photo Mask (40um)","CPU Ex"));
        CPUPhotomask8um.set(addItem(9903,"CPU Photo Mask (8um)","CPU Ex"));
        CPUPhotomask400nm.set(addItem(9904,"CPU Photo Mask (400nm)","CPU Ex"));
        CPUPhotomask80nm.set(addItem(9905,"CPU Photo Mask (80nm)","CPU Ex"));
        CPUPhotomask32nm.set(addItem(9906,"CPU Photo Mask (32nm)","CPU Ex"));
        CPUPhotomask14nm.set(addItem(9907,"CPU Photo Mask (14nm)","CPU Ex"));


        CPUWafer200um.set(addItem(9950,"CPU Wafer (200um)","CPU Ex"));
        CPUWafer72um.set(addItem(9951,"CPU Wafer (72um)","CPU Ex"));
        CPUWafer40um.set(addItem(9952,"CPU Wafer (40um)","CPU Ex"));
        CPUWafer8um.set(addItem(9953,"CPU Wafer (8um)","CPU Ex"));
        CPUWafer400nm.set(addItem(9954,"CPU Wafer (400nm)","CPU Ex"));
        CPUWafer80nm.set(addItem(9955,"CPU Wafer (80nm)","CPU Ex"));
        CPUWafer32nm.set(addItem(9956,"CPU Wafer (32nm)","CPU Ex"));
        CPUWafer14nm.set(addItem(9957,"CPU Wafer (14nm)","CPU Ex"));

        CPUWafer200umDeveloped.set(addItem(10000,"CPU Wafer200umDeveloped","CPU Wafer200umDeveloped"));
        CPUWafer72umDeveloped.set(addItem(10001,"CPU Wafer72umDeveloped","CPU Wafer72umDeveloped"));
        CPUWafer40umDeveloped.set(addItem(10002,"CPU Wafer40umDeveloped","CPU Wafer40umDeveloped"));
        CPUWafer8umDeveloped.set(addItem(10003,"CPU Wafer8umDeveloped","CPU Wafer8umDeveloped"));
        CPUWafer400nmDeveloped.set(addItem(10004,"CPU Wafer400nmDeveloped","CPU Wafer400nmDeveloped"));
        CPUWafer80nmDeveloped.set(addItem(10005,"CPU Wafer80nmDeveloped","CPU Wafer80nmDeveloped"));
        CPUWafer32nmDeveloped.set(addItem(10006,"CPU Wafer32nmDeveloped","CPU Wafer32nmDeveloped"));
        CPUWafer14nmDeveloped.set(addItem(10007,"CPU Wafer14nmDeveloped","CPU Wafer14nmDeveloped"));

        CPUWafer200umHardBaked.set(addItem(10050,"CPU Wafer200umHardBaked","CPU Wafer200umHardBaked"));
        CPUWafer72umHardBaked.set(addItem(10051,"CPU Wafer72umHardBaked","CPU Wafer72umHardBaked"));
        CPUWafer40umHardBaked.set(addItem(10052,"CPU Wafer40umHardBaked","CPU Wafer40umHardBaked"));
        CPUWafer8umHardBaked.set(addItem(10053,"CPU Wafer8umHardBaked","CPU Wafer8umHardBaked"));
        CPUWafer400nmHardBaked.set(addItem(10054,"CPU Wafer400nmHardBaked","CPU Wafer400nmHardBaked"));
        CPUWafer80nmHardBaked.set(addItem(10055,"CPU Wafer80nmHardBaked","CPU Wafer80nmHardBaked"));
        CPUWafer32nmHardBaked.set(addItem(10056,"CPU Wafer32nmHardBaked","CPU Wafer32nmHardBaked"));
        CPUWafer14nmHardBaked.set(addItem(10057,"CPU Wafer14nmHardBaked","CPU Wafer14nmHardBaked"));

        CPUWafer200umDoped.set(addItem(10100,"CPU Wafer200umDoped","CPU Wafer200umDoped"));
        CPUWafer72umDoped.set(addItem(10101,"CPU Wafer72umDoped","CPU Wafer72umDoped"));
        CPUWafer40umDoped.set(addItem(10102,"CPU Wafer40umDoped","CPU Wafer40umDoped"));
        CPUWafer8umDoped.set(addItem(10103,"CPU Wafer8umDoped","CPU Wafer8umDoped"));
        CPUWafer400nmDoped.set(addItem(10104,"CPU Wafer400nmDoped","CPU Wafer400nmDoped"));
        CPUWafer80nmDoped.set(addItem(10105,"CPU Wafer80nmDoped","CPU Wafer80nmDoped"));
        CPUWafer32nmDoped.set(addItem(10106,"CPU Wafer32nmDoped","CPU Wafer32nmDoped"));
        CPUWafer14nmDoped.set(addItem(10107,"CPU Wafer14nmDoped","CPU Wafer14nmDoped"));
        
        CPUWafer200umChecked.set(addItem(10150,"CPU Wafer200umChecked","CPU Wafer200umChecked"));
        CPUWafer72umChecked.set(addItem(10151,"CPU Wafer72umChecked","CPU Wafer72umChecked"));
        CPUWafer40umChecked.set(addItem(10152,"CPU Wafer40umChecked","CPU Wafer40umChecked"));
        CPUWafer8umChecked.set(addItem(10153,"CPU Wafer8umChecked","CPU Wafer8umChecked"));
        CPUWafer400nmChecked.set(addItem(10154,"CPU Wafer400nmChecked","CPU Wafer400nmChecked"));
        CPUWafer80nmChecked.set(addItem(10155,"CPU Wafer80nmChecked","CPU Wafer80nmChecked"));
        CPUWafer32nmChecked.set(addItem(10156,"CPU Wafer32nmChecked","CPU Wafer32nmChecked"));
        CPUWafer14nmChecked.set(addItem(10157,"CPU Wafer14nmChecked","CPU Wafer14nmChecked"));
    }
}