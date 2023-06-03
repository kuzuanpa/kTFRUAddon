/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.item.items;

import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;
/**
 * Notice: Don't change the order of codes and always append newline
 **/
public class itemIT extends MultiItemRandom {
    public itemIT() {
        super(MOD_ID, "ktfru.item.it");
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: IT", this, (short) 9999));

    }

    @Override
    public void addItems() {
        ItemList.SiliconBoulePure.set(addItem(9701, "Pure Silicon Boule", "Ready to Cut into Plates"));
        ItemList.MoO2Boule.set(addItem(9702, "MoO2 Boule", "Ready to Cut into Plates"));
        ItemList.MoO2BoulePure.set(addItem(9703, "Pure MoO2 Boule", "Ready to Cut into Plates"));

        ItemList.SiliconPlate8inchTier1.set(addItem(9800, "SiliconPlate8inchTier1", "SiliconPlate8inchTier1"));
        ItemList.SiliconPlate8inchTier2.set(addItem(9801, "SiliconPlate8inchTier2", "SiliconPlate8inchTier2"));
        ItemList.MoO2Plate8inchTier1.set(addItem(9802, "MoO2Plate8inchTier1", "MoO2Plate8inchTier1"));
        ItemList.MoO2Plate8inchTier2.set(addItem(9803, "MoO2Plate8inchTier2", "MoO2Plate8inchTier2"));
        ItemList.GraphitePlate8inch.set(addItem(9804, "GraphitePlate8inchTier1", "GraphitePlate8inchTier1"));

        ItemList.SiliconPlate8inchCleanedTier1.set(addItem(9810, "SiliconPlate8inchCleanedTier1", "SiliconPlate8inchCleanedTier1"));
        ItemList.SiliconPlate8inchCleanedTier2.set(addItem(9811, "SiliconPlate8inchCleanedTier2", "SiliconPlate8inchCleanedTier2"));
        ItemList.MoO2Plate8inchCleanedTier1.set(addItem(9812, "MoO2Plate8inchCleanedTier1", "MoO2Plate8inchCleanedTier1"));
        ItemList.MoO2Plate8inchCleanedTier2.set(addItem(9813, "MoO2Plate8inchCleanedTier2", "MoO2Plate8inchCleanedTier2"));
        ItemList.GraphitePlate8inchCleaned.set(addItem(9814, "GraphitePlate8inchCleanedTier1", "GraphitePlate8inchCleanedTier1"));

        ItemList.SiliconPlate8inchOxidizedTier1.set(addItem(9820, "SiliconPlate8inchOxidizedTier1", "SiliconPlate8inchOxidizedTier1"));
        ItemList.SiliconPlate8inchOxidizedTier2.set(addItem(9821, "SiliconPlate8inchOxidizedTier2", "SiliconPlate8inchOxidizedTier2"));
        ItemList.MoO2Plate8inchOxidizedTier1.set(addItem(9822, "MoO2Plate8inchOxidizedTier1", "MoO2Plate8inchOxidizedTier1"));
        ItemList.MoO2Plate8inchOxidizedTier2.set(addItem(9823, "MoO2Plate8inchOxidizedTier2", "MoO2Plate8inchOxidizedTier2"));
        ItemList.GraphitePlate8inchOxidized.set(addItem(9824, "GraphitePlate8inchOxidizedTier1", "GraphitePlate8inchOxidizedTier1"));

        ItemList.SiliconPlate8inchCoatedTier1.set(addItem(9830, "SiliconPlate8inchCoatedTier1", "SiliconPlate8inchCoatedTier1"));
        ItemList.SiliconPlate8inchCoatedTier2.set(addItem(9831, "SiliconPlate8inchCoatedTier2", "SiliconPlate8inchCoatedTier2"));
        ItemList.MoO2Plate8inchCoatedTier1.set(addItem(9832, "MoO2Plate8inchCoatedTier1", "MoO2Plate8inchCoatedTier1"));
        ItemList.MoO2Plate8inchCoatedTier2.set(addItem(9833, "MoO2Plate8inchCoatedTier2", "MoO2Plate8inchCoatedTier2"));
        ItemList.GraphitePlate8inchCoated.set(addItem(9834, "GraphitePlate8inchCoatedTier1", "GraphitePlate8inchCoatedTier1"));

        ItemList.SiliconPlate8inchSoftBakedTier1.set(addItem(9840, "SiliconPlate8inchSoftBakedTier1", "SiliconPlate8inchSoftBakedTier1"));
        ItemList.SiliconPlate8inchSoftBakedTier2.set(addItem(9841, "SiliconPlate8inchSoftBakedTier2", "SiliconPlate8inchSoftBakedTier2"));
        ItemList.MoO2Plate8inchSoftBakedTier1.set(addItem(9842, "MoO2Plate8inchSoftBakedTier1", "MoO2Plate8inchSoftBakedTier1"));
        ItemList.MoO2Plate8inchSoftBakedTier2.set(addItem(9843, "MoO2Plate8inchSoftBakedTier2", "MoO2Plate8inchSoftBakedTier2"));
        ItemList.GraphitePlate8inchSoftBaked.set(addItem(9844, "GraphitePlate8inchSoftBakedTier1", "GraphitePlate8inchSoftBakedTier1"));

        ItemList.CPUPhotomask200um.set(addItem(9900, "CPU Photo Mask (200um)", "CPU Ex"));
        ItemList.CPUPhotomask72um.set(addItem(9901, "CPU Photo Mask (72um)", "CPU Ex"));
        ItemList.CPUPhotomask40um.set(addItem(9902, "CPU Photo Mask (40um)", "CPU Ex"));
        ItemList.CPUPhotomask8um.set(addItem(9903, "CPU Photo Mask (8um)", "CPU Ex"));
        ItemList.CPUPhotomask400nm.set(addItem(9904, "CPU Photo Mask (400nm)", "CPU Ex"));
        ItemList.CPUPhotomask80nm.set(addItem(9905, "CPU Photo Mask (80nm)", "CPU Ex"));
        ItemList.CPUPhotomask32nm.set(addItem(9906, "CPU Photo Mask (32nm)", "CPU Ex"));
        ItemList.CPUPhotomask14nm.set(addItem(9907, "CPU Photo Mask (14nm)", "CPU Ex"));


        ItemList.CPUWafer200um.set(addItem(9950, "CPU Wafer (200um)", "CPU Ex"));
        ItemList.CPUWafer72um.set(addItem(9951, "CPU Wafer (72um)", "CPU Ex"));
        ItemList.CPUWafer40um.set(addItem(9952, "CPU Wafer (40um)", "CPU Ex"));
        ItemList.CPUWafer8um.set(addItem(9953, "CPU Wafer (8um)", "CPU Ex"));
        ItemList.CPUWafer400nm.set(addItem(9954, "CPU Wafer (400nm)", "CPU Ex"));
        ItemList.CPUWafer80nm.set(addItem(9955, "CPU Wafer (80nm)", "CPU Ex"));
        ItemList.CPUWafer32nm.set(addItem(9956, "CPU Wafer (32nm)", "CPU Ex"));
        ItemList.CPUWafer14nm.set(addItem(9957, "CPU Wafer (14nm)", "CPU Ex"));

        ItemList.CPUWafer200umDeveloped.set(addItem(10000, "CPU Wafer200umDeveloped", "CPU Wafer200umDeveloped"));
        ItemList.CPUWafer72umDeveloped.set(addItem(10001, "CPU Wafer72umDeveloped", "CPU Wafer72umDeveloped"));
        ItemList.CPUWafer40umDeveloped.set(addItem(10002, "CPU Wafer40umDeveloped", "CPU Wafer40umDeveloped"));
        ItemList.CPUWafer8umDeveloped.set(addItem(10003, "CPU Wafer8umDeveloped", "CPU Wafer8umDeveloped"));
        ItemList.CPUWafer400nmDeveloped.set(addItem(10004, "CPU Wafer400nmDeveloped", "CPU Wafer400nmDeveloped"));
        ItemList.CPUWafer80nmDeveloped.set(addItem(10005, "CPU Wafer80nmDeveloped", "CPU Wafer80nmDeveloped"));
        ItemList.CPUWafer32nmDeveloped.set(addItem(10006, "CPU Wafer32nmDeveloped", "CPU Wafer32nmDeveloped"));
        ItemList.CPUWafer14nmDeveloped.set(addItem(10007, "CPU Wafer14nmDeveloped", "CPU Wafer14nmDeveloped"));

        ItemList.CPUWafer200umHardBaked.set(addItem(10050, "CPU Wafer200umHardBaked", "CPU Wafer200umHardBaked"));
        ItemList.CPUWafer72umHardBaked.set(addItem(10051, "CPU Wafer72umHardBaked", "CPU Wafer72umHardBaked"));
        ItemList.CPUWafer40umHardBaked.set(addItem(10052, "CPU Wafer40umHardBaked", "CPU Wafer40umHardBaked"));
        ItemList.CPUWafer8umHardBaked.set(addItem(10053, "CPU Wafer8umHardBaked", "CPU Wafer8umHardBaked"));
        ItemList.CPUWafer400nmHardBaked.set(addItem(10054, "CPU Wafer400nmHardBaked", "CPU Wafer400nmHardBaked"));
        ItemList.CPUWafer80nmHardBaked.set(addItem(10055, "CPU Wafer80nmHardBaked", "CPU Wafer80nmHardBaked"));
        ItemList.CPUWafer32nmHardBaked.set(addItem(10056, "CPU Wafer32nmHardBaked", "CPU Wafer32nmHardBaked"));
        ItemList.CPUWafer14nmHardBaked.set(addItem(10057, "CPU Wafer14nmHardBaked", "CPU Wafer14nmHardBaked"));

        ItemList.CPUWafer200umDoped.set(addItem(10100, "CPU Wafer200umDoped", "CPU Wafer200umDoped"));
        ItemList.CPUWafer72umDoped.set(addItem(10101, "CPU Wafer72umDoped", "CPU Wafer72umDoped"));
        ItemList.CPUWafer40umDoped.set(addItem(10102, "CPU Wafer40umDoped", "CPU Wafer40umDoped"));
        ItemList.CPUWafer8umDoped.set(addItem(10103, "CPU Wafer8umDoped", "CPU Wafer8umDoped"));
        ItemList.CPUWafer400nmDoped.set(addItem(10104, "CPU Wafer400nmDoped", "CPU Wafer400nmDoped"));
        ItemList.CPUWafer80nmDoped.set(addItem(10105, "CPU Wafer80nmDoped", "CPU Wafer80nmDoped"));
        ItemList.CPUWafer32nmDoped.set(addItem(10106, "CPU Wafer32nmDoped", "CPU Wafer32nmDoped"));
        ItemList.CPUWafer14nmDoped.set(addItem(10107, "CPU Wafer14nmDoped", "CPU Wafer14nmDoped"));

        ItemList.CPUWafer200umChecked.set(addItem(10150, "CPU Wafer200umChecked", "CPU Wafer200umChecked"));
        ItemList.CPUWafer72umChecked.set(addItem(10151, "CPU Wafer72umChecked", "CPU Wafer72umChecked"));
        ItemList.CPUWafer40umChecked.set(addItem(10152, "CPU Wafer40umChecked", "CPU Wafer40umChecked"));
        ItemList.CPUWafer8umChecked.set(addItem(10153, "CPU Wafer8umChecked", "CPU Wafer8umChecked"));
        ItemList.CPUWafer400nmChecked.set(addItem(10154, "CPU Wafer400nmChecked", "CPU Wafer400nmChecked"));
        ItemList.CPUWafer80nmChecked.set(addItem(10155, "CPU Wafer80nmChecked", "CPU Wafer80nmChecked"));
        ItemList.CPUWafer32nmChecked.set(addItem(10156, "CPU Wafer32nmChecked", "CPU Wafer32nmChecked"));
        ItemList.CPUWafer14nmChecked.set(addItem(10157, "CPU Wafer14nmChecked", "CPU Wafer14nmChecked"));

        ItemList.CPUDieTF3386.set(addItem(10200, "CPUDieTF3386", "CPUDieTF3386"));
        ItemList.CPUDieTF3586.set(addItem(10201, "CPUDieTF3586", "CPUDieTF3586"));
        ItemList.CPUDieGT1000.set(addItem(10202, "CPUDieGT1000", "CPUDieGT1000"));
        ItemList.CPUDieGT2000.set(addItem(10203, "CPUDieGT2000", "CPUDieGT2000"));
        ItemList.CPUDieGT3660.set(addItem(10204, "CPUDieGT3660", "CPUDieGT3660"));
        ItemList.CPUDieGT3660v2.set(addItem(10205, "CPUDieGT3660v2", "CPUDieGT3660v2"));
        ItemList.CPUDieGT3660v3.set(addItem(10206, "CPUDieGT3660v3", "CPUDieGT3660v3"));
        ItemList.CPUDieGT3660v4.set(addItem(10207, "CPUDieGT3660v4", "CPUDieGT3660v4"));
        ItemList.CPUDieTF3386S.set(addItem(10208, "CPUDieTF3386S", "CPUDieTF3386S"));
        ItemList.CPUDieTF3586S.set(addItem(10209, "CPUDieTF3586S", "CPUDieTF3586S"));
        ItemList.CPUDieGT1090.set(addItem(10210, "CPUDieGT1090", "CPUDieGT1090"));
        ItemList.CPUDieGT2090.set(addItem(10211, "CPUDieGT2090", "CPUDieGT2090"));
        ItemList.CPUDieGT3680.set(addItem(10212, "CPUDieGT3680", "CPUDieGT3680"));
        ItemList.CPUDieGT3680v2.set(addItem(10213, "CPUDieGT3680v2", "CPUDieGT3680v2"));
        ItemList.CPUDieGT3680v3.set(addItem(10214, "CPUDieGT3680v3", "CPUDieGT3680v3"));
        ItemList.CPUDieGT3680v4.set(addItem(10215, "CPUDieGT3680v4", "CPUDieGT3680v4"));
        ItemList.CPUDieGT3699.set(addItem(10216, "CPUDieGT3699", "CPUDieGT3699"));
        ItemList.CPUDieGT3699v2.set(addItem(10217, "CPUDieGT3699v2", "CPUDieGT3699v2"));
        ItemList.CPUDieGT3699v3.set(addItem(10218, "CPUDieGT3699v3", "CPUDieGT3699v3"));
        ItemList.CPUDieGT3699v4.set(addItem(10219, "CPUDieGT3699v4", "CPUDieGT3699v4"));
        ItemList.CPUDieGT3680v3E.set(addItem(10220, "CPUDieGT3680v3E", "CPUDieGT3680v3E"));
        ItemList.CPUDieGT3680v4E.set(addItem(10221, "CPUDieGT3680v4E", "CPUDieGT3680v4E"));
        ItemList.CPUDieGT3699v3E.set(addItem(10222, "CPUDieGT3699v3E", "CPUDieGT3699v3E"));
        ItemList.CPUDieGT3699v4E.set(addItem(10223, "CPUDieGT3699v4E", "CPUDieGT3699v4E"));

        ItemList.CPUTF3386.set(addItem(10300, "CPUTF3386", "CPUTF3386"));
        ItemList.CPUTF3586.set(addItem(10301, "CPUTF3586", "CPUTF3586"));
        ItemList.CPUGT1000.set(addItem(10302, "CPUGT1000", "CPUGT1000"));
        ItemList.CPUGT2000.set(addItem(10303, "CPUGT2000", "CPUGT2000"));
        ItemList.CPUGT3660.set(addItem(10304, "CPUGT3660", "CPUGT3660"));
        ItemList.CPUGT3660v2.set(addItem(10305, "CPUGT3660v2", "CPUGT3660v2"));
        ItemList.CPUGT3660v3.set(addItem(10306, "CPUGT3660v3", "CPUGT3660v3"));
        ItemList.CPUGT3660v4.set(addItem(10307, "CPUGT3660v4", "CPUGT3660v4"));
        ItemList.CPUTF3386S.set(addItem(10308, "CPUTF3386S", "CPUTF3386S"));
        ItemList.CPUTF3586S.set(addItem(10309, "CPUTF3586S", "CPUTF3586S"));
        ItemList.CPUGT1090.set(addItem(10310, "CPUGT1090", "CPUGT1090"));
        ItemList.CPUGT2090.set(addItem(10311, "CPUGT2090", "CPUGT2090"));
        ItemList.CPUGT3680.set(addItem(10312, "CPUGT3680", "CPUGT3680"));
        ItemList.CPUGT3680v2.set(addItem(10313, "CPUGT3680v2", "CPUGT3680v2"));
        ItemList.CPUGT3680v3.set(addItem(10314, "CPUGT3680v3", "CPUGT3680v3"));
        ItemList.CPUGT3680v4.set(addItem(10315, "CPUGT3680v4", "CPUGT3680v4"));
        ItemList.CPUGT3699.set(addItem(10316, "CPUGT3699", "CPUGT3699"));
        ItemList.CPUGT3699v2.set(addItem(10317, "CPUGT3699v2", "CPUGT3699v2"));
        ItemList.CPUGT3699v3.set(addItem(10318, "CPUGT3699v3", "CPUGT3699v3"));
        ItemList.CPUGT3699v4.set(addItem(10319, "CPUGT3699v4", "CPUGT3699v4"));
        ItemList.CPUGT3680v3E.set(addItem(10320, "CPUGT3680v3E", "CPUGT3680v3E"));
        ItemList.CPUGT3680v4E.set(addItem(10321, "CPUGT3680v4E", "CPUGT3680v4E"));
        ItemList.CPUGT3699v3E.set(addItem(10322, "CPUGT3699v3E", "CPUGT3699v3E"));
        ItemList.CPUGT3699v4E.set(addItem(10323, "CPUGT3699v4E", "CPUGT3699v4E"));
//Circuits
        int i=20000;
        ItemList.ResistanceT1.set(addItem(i++, "Resistance (T1)",""));
        ItemList.DiodeT1     .set(addItem(i++, "Diode (T1)",""));
        ItemList.CapacitorT1 .set(addItem(i++, "Capacitor (T1)",""));
        ItemList.CoilT1      .set(addItem(i++, "Coil (T1)",""));
        ItemList.ResistanceT2.set(addItem(i++, "Resistance (T2)",""));
        ItemList.DiodeT2     .set(addItem(i++, "Diode (T2)",""));
        ItemList.CapacitorT2 .set(addItem(i++, "Capacitor (T2)",""));
        ItemList.CoilT2      .set(addItem(i++, "Coil (T2)",""));
        ItemList.ResistanceT3.set(addItem(i++, "Resistance (T3)",""));
        ItemList.DiodeT3     .set(addItem(i++, "Diode (T3)",""));
        ItemList.CapacitorT3 .set(addItem(i++, "Capacitor (T3)",""));
        ItemList.CoilT3      .set(addItem(i++, "Coil (T3)",""));
        ItemList.ResistanceT4.set(addItem(i++, "Resistance (T4)",""));
        ItemList.DiodeT4     .set(addItem(i++, "Diode (T4)",""));
        ItemList.CapacitorT4 .set(addItem(i++, "Capacitor (T4)",""));
        ItemList.CoilT4      .set(addItem(i++, "Coil (T4)",""));
    }
}