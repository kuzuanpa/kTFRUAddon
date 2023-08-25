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

        ItemList.SiliconPlate8inchT1.set(addItem(9800, "SiliconPlate8inchT1", "SiliconPlate8inchT1"));
        ItemList.SiliconPlate8inchT2.set(addItem(9801, "SiliconPlate8inchT2", "SiliconPlate8inchT2"));
        ItemList.MoO2Plate8inchT1.set(addItem(9802, "MoO2Plate8inchT1", "MoO2Plate8inchT1"));
        ItemList.MoO2Plate8inchT2.set(addItem(9803, "MoO2Plate8inchT2", "MoO2Plate8inchT2"));
        ItemList.GraphitePlate8inch.set(addItem(9804, "GraphitePlate8inchT1", "GraphitePlate8inchT1"));

        ItemList.SiliconPlate8inchCleanedT1.set(addItem(9810, "SiliconPlate8inchCleanedT1", "SiliconPlate8inchCleanedT1"));
        ItemList.SiliconPlate8inchCleanedT2.set(addItem(9811, "SiliconPlate8inchCleanedT2", "SiliconPlate8inchCleanedT2"));
        ItemList.MoO2Plate8inchCleanedT1.set(addItem(9812, "MoO2Plate8inchCleanedT1", "MoO2Plate8inchCleanedT1"));
        ItemList.MoO2Plate8inchCleanedT2.set(addItem(9813, "MoO2Plate8inchCleanedT2", "MoO2Plate8inchCleanedT2"));
        ItemList.GraphitePlate8inchCleaned.set(addItem(9814, "GraphitePlate8inchCleanedT1", "GraphitePlate8inchCleanedT1"));

        ItemList.SiliconPlate8inchOxidizedT1.set(addItem(9820, "SiliconPlate8inchOxidizedT1", "SiliconPlate8inchOxidizedT1"));
        ItemList.SiliconPlate8inchOxidizedT2.set(addItem(9821, "SiliconPlate8inchOxidizedT2", "SiliconPlate8inchOxidizedT2"));
        ItemList.MoO2Plate8inchOxidizedT1.set(addItem(9822, "MoO2Plate8inchOxidizedT1", "MoO2Plate8inchOxidizedT1"));
        ItemList.MoO2Plate8inchOxidizedT2.set(addItem(9823, "MoO2Plate8inchOxidizedT2", "MoO2Plate8inchOxidizedT2"));
        ItemList.GraphitePlate8inchOxidized.set(addItem(9824, "GraphitePlate8inchOxidizedT1", "GraphitePlate8inchOxidizedT1"));

        ItemList.SiliconPlate8inchCoatedT1.set(addItem(9830, "SiliconPlate8inchCoatedT1", "SiliconPlate8inchCoatedT1"));
        ItemList.SiliconPlate8inchCoatedT2.set(addItem(9831, "SiliconPlate8inchCoatedT2", "SiliconPlate8inchCoatedT2"));
        ItemList.MoO2Plate8inchCoatedT1.set(addItem(9832, "MoO2Plate8inchCoatedT1", "MoO2Plate8inchCoatedT1"));
        ItemList.MoO2Plate8inchCoatedT2.set(addItem(9833, "MoO2Plate8inchCoatedT2", "MoO2Plate8inchCoatedT2"));
        ItemList.GraphitePlate8inchCoated.set(addItem(9834, "GraphitePlate8inchCoatedT1", "GraphitePlate8inchCoatedT1"));

        ItemList.SiliconPlate8inchSoftBakedT1.set(addItem(9840, "SiliconPlate8inchSoftBakedT1", "SiliconPlate8inchSoftBakedT1"));
        ItemList.SiliconPlate8inchSoftBakedT2.set(addItem(9841, "SiliconPlate8inchSoftBakedT2", "SiliconPlate8inchSoftBakedT2"));
        ItemList.MoO2Plate8inchSoftBakedT1.set(addItem(9842, "MoO2Plate8inchSoftBakedT1", "MoO2Plate8inchSoftBakedT1"));
        ItemList.MoO2Plate8inchSoftBakedT2.set(addItem(9843, "MoO2Plate8inchSoftBakedT2", "MoO2Plate8inchSoftBakedT2"));
        ItemList.GraphitePlate8inchSoftBaked.set(addItem(9844, "GraphitePlate8inchSoftBakedT1", "GraphitePlate8inchSoftBakedT1"));

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
        ItemList.CircuitBoardEmptyT2.set(addItem(i++, "Plastic Circuit Board",""));
        ItemList.CircuitBoardEmptyT3.set(addItem(i++, "Epoxy Resin Circuit Board",""));
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
        ItemList.CoilT4      .set(addItem(i++, "Coil (T4)",""));

        ItemList.CircuitPartPhotomaskT3.set(addItem(20100, "CircuitPart Photo Mask (200um)", "CircuitPart Ex"));
        ItemList.CircuitPartPhotomaskT4.set(addItem(20101, "CircuitPart Photo Mask (72um)", "CircuitPart Ex"));
        ItemList.CircuitPartPhotomaskT5.set(addItem(20102, "CircuitPart Photo Mask (40um)", "CircuitPart Ex"));
        ItemList.CircuitPartPhotomaskT6.set(addItem(20103, "CircuitPart Photo Mask (8um)", "CircuitPart Ex"));

        ItemList.CircuitPartWaferT3.set(addItem(20110, "CircuitPart Wafer (200um)", "CircuitPart Ex"));
        ItemList.CircuitPartWaferT4.set(addItem(20111, "CircuitPart Wafer (72um)", "CircuitPart Ex"));
        ItemList.CircuitPartWaferT5.set(addItem(20112, "CircuitPart Wafer (40um)", "CircuitPart Ex"));
        ItemList.CircuitPartWaferT6.set(addItem(20113, "CircuitPart Wafer (8um)", "CircuitPart Ex"));

        ItemList.CircuitPartWaferT3Developed.set(addItem(20120, "CircuitPart Photo Mask (200um)", "CircuitPart Ex"));
        ItemList.CircuitPartWaferT4Developed.set(addItem(20121, "CircuitPart Photo Mask (72um)", "CircuitPart Ex"));
        ItemList.CircuitPartWaferT5Developed.set(addItem(20122, "CircuitPart Photo Mask (40um)", "CircuitPart Ex"));
        ItemList.CircuitPartWaferT6Developed.set(addItem(20123, "CircuitPart Photo Mask (8um)", "CircuitPart Ex"));

        ItemList.CircuitPartWaferT3HardBaked.set(addItem(20130, "CircuitPart Photo Mask (200um)", "CircuitPart Ex"));
        ItemList.CircuitPartWaferT4HardBaked.set(addItem(20131, "CircuitPart Photo Mask (72um)", "CircuitPart Ex"));
        ItemList.CircuitPartWaferT5HardBaked.set(addItem(20132, "CircuitPart Photo Mask (40um)", "CircuitPart Ex"));
        ItemList.CircuitPartWaferT6HardBaked.set(addItem(20133, "CircuitPart Photo Mask (8um)", "CircuitPart Ex"));

        ItemList.CircuitPartWaferT3Doped.set(addItem(20140, "CircuitPart Photo Mask (200um)", "CircuitPart Ex"));
        ItemList.CircuitPartWaferT4Doped.set(addItem(20141, "CircuitPart Photo Mask (72um)", "CircuitPart Ex"));
        ItemList.CircuitPartWaferT5Doped.set(addItem(20142, "CircuitPart Photo Mask (40um)", "CircuitPart Ex"));
        ItemList.CircuitPartWaferT6Doped.set(addItem(20143, "CircuitPart Photo Mask (8um)", "CircuitPart Ex"));

        ItemList.CircuitPartWaferT3Checked.set(addItem(20150, "CircuitPart Photo Mask (200um)", "CircuitPart Ex"));
        ItemList.CircuitPartWaferT4Checked.set(addItem(20151, "CircuitPart Photo Mask (72um)", "CircuitPart Ex"));
        ItemList.CircuitPartWaferT5Checked.set(addItem(20152, "CircuitPart Photo Mask (40um)", "CircuitPart Ex"));
        ItemList.CircuitPartWaferT6Checked.set(addItem(20153, "CircuitPart Photo Mask (8um)", "CircuitPart Ex"));


//RAM
        ItemList.RAMPhotomask200um.set(addItem(30000, "RAM Photo Mask (200um)", "RAM Ex"));
        ItemList.RAMPhotomask72um.set(addItem(30001, "RAM Photo Mask (72um)", "RAM Ex"));
        ItemList.RAMPhotomask40um.set(addItem(30002, "RAM Photo Mask (40um)", "RAM Ex"));
        ItemList.RAMPhotomask8um.set(addItem(30003, "RAM Photo Mask (8um)", "RAM Ex"));
        ItemList.RAMPhotomask400nm.set(addItem(30004, "RAM Photo Mask (400nm)", "RAM Ex"));
        ItemList.RAMPhotomask80nm.set(addItem(30005, "RAM Photo Mask (80nm)", "RAM Ex"));
        ItemList.RAMPhotomask32nm.set(addItem(30006, "RAM Photo Mask (32nm)", "RAM Ex"));
        ItemList.RAMPhotomask14nm.set(addItem(30007, "RAM Photo Mask (14nm)", "RAM Ex"));


        ItemList.RAMWafer200um.set(addItem(30050, "RAM Wafer (200um)", "RAM Ex"));
        ItemList.RAMWafer72um.set(addItem(30051, "RAM Wafer (72um)", "RAM Ex"));
        ItemList.RAMWafer40um.set(addItem(30052, "RAM Wafer (40um)", "RAM Ex"));
        ItemList.RAMWafer8um.set(addItem(30053, "RAM Wafer (8um)", "RAM Ex"));
        ItemList.RAMWafer400nm.set(addItem(30054, "RAM Wafer (400nm)", "RAM Ex"));
        ItemList.RAMWafer80nm.set(addItem(30055, "RAM Wafer (80nm)", "RAM Ex"));
        ItemList.RAMWafer32nm.set(addItem(30056, "RAM Wafer (32nm)", "RAM Ex"));
        ItemList.RAMWafer14nm.set(addItem(30057, "RAM Wafer (14nm)", "RAM Ex"));

        ItemList.RAMWafer200umDeveloped.set(addItem(30100, "RAM Wafer200umDeveloped", "RAM Wafer200umDeveloped"));
        ItemList.RAMWafer72umDeveloped.set(addItem(30101, "RAM Wafer72umDeveloped", "RAM Wafer72umDeveloped"));
        ItemList.RAMWafer40umDeveloped.set(addItem(30102, "RAM Wafer40umDeveloped", "RAM Wafer40umDeveloped"));
        ItemList.RAMWafer8umDeveloped.set(addItem(30103, "RAM Wafer8umDeveloped", "RAM Wafer8umDeveloped"));
        ItemList.RAMWafer400nmDeveloped.set(addItem(30104, "RAM Wafer400nmDeveloped", "RAM Wafer400nmDeveloped"));
        ItemList.RAMWafer80nmDeveloped.set(addItem(30105, "RAM Wafer80nmDeveloped", "RAM Wafer80nmDeveloped"));
        ItemList.RAMWafer32nmDeveloped.set(addItem(30106, "RAM Wafer32nmDeveloped", "RAM Wafer32nmDeveloped"));
        ItemList.RAMWafer14nmDeveloped.set(addItem(30107, "RAM Wafer14nmDeveloped", "RAM Wafer14nmDeveloped"));

        ItemList.RAMWafer200umHardBaked.set(addItem(30150, "RAM Wafer200umHardBaked", "RAM Wafer200umHardBaked"));
        ItemList.RAMWafer72umHardBaked.set(addItem(30151, "RAM Wafer72umHardBaked", "RAM Wafer72umHardBaked"));
        ItemList.RAMWafer40umHardBaked.set(addItem(30152, "RAM Wafer40umHardBaked", "RAM Wafer40umHardBaked"));
        ItemList.RAMWafer8umHardBaked.set(addItem(30153, "RAM Wafer8umHardBaked", "RAM Wafer8umHardBaked"));
        ItemList.RAMWafer400nmHardBaked.set(addItem(30154, "RAM Wafer400nmHardBaked", "RAM Wafer400nmHardBaked"));
        ItemList.RAMWafer80nmHardBaked.set(addItem(30155, "RAM Wafer80nmHardBaked", "RAM Wafer80nmHardBaked"));
        ItemList.RAMWafer32nmHardBaked.set(addItem(30156, "RAM Wafer32nmHardBaked", "RAM Wafer32nmHardBaked"));
        ItemList.RAMWafer14nmHardBaked.set(addItem(30157, "RAM Wafer14nmHardBaked", "RAM Wafer14nmHardBaked"));

        ItemList.RAMWafer200umDoped.set(addItem(30200, "RAM Wafer200umDoped", "RAM Wafer200umDoped"));
        ItemList.RAMWafer72umDoped.set(addItem(30201, "RAM Wafer72umDoped", "RAM Wafer72umDoped"));
        ItemList.RAMWafer40umDoped.set(addItem(30202, "RAM Wafer40umDoped", "RAM Wafer40umDoped"));
        ItemList.RAMWafer8umDoped.set(addItem(30203, "RAM Wafer8umDoped", "RAM Wafer8umDoped"));
        ItemList.RAMWafer400nmDoped.set(addItem(30204, "RAM Wafer400nmDoped", "RAM Wafer400nmDoped"));
        ItemList.RAMWafer80nmDoped.set(addItem(30205, "RAM Wafer80nmDoped", "RAM Wafer80nmDoped"));
        ItemList.RAMWafer32nmDoped.set(addItem(30206, "RAM Wafer32nmDoped", "RAM Wafer32nmDoped"));
        ItemList.RAMWafer14nmDoped.set(addItem(30207, "RAM Wafer14nmDoped", "RAM Wafer14nmDoped"));

        ItemList.RAMWafer200umChecked.set(addItem(30250, "RAM Wafer200umChecked", "RAM Wafer200umChecked"));
        ItemList.RAMWafer72umChecked.set(addItem(30251, "RAM Wafer72umChecked", "RAM Wafer72umChecked"));
        ItemList.RAMWafer40umChecked.set(addItem(30252, "RAM Wafer40umChecked", "RAM Wafer40umChecked"));
        ItemList.RAMWafer8umChecked.set(addItem(30253, "RAM Wafer8umChecked", "RAM Wafer8umChecked"));
        ItemList.RAMWafer400nmChecked.set(addItem(30254, "RAM Wafer400nmChecked", "RAM Wafer400nmChecked"));
        ItemList.RAMWafer80nmChecked.set(addItem(30255, "RAM Wafer80nmChecked", "RAM Wafer80nmChecked"));
        ItemList.RAMWafer32nmChecked.set(addItem(30256, "RAM Wafer32nmChecked", "RAM Wafer32nmChecked"));
        ItemList.RAMWafer14nmChecked.set(addItem(30257, "RAM Wafer14nmChecked", "RAM Wafer14nmChecked"));

        ItemList.RAMDie2K.set(addItem(30300, "RAMDie2K", "Wafer200umChecked"));
        ItemList.RAMDie24K.set(addItem(30301, "RAMDie24K", "Wafer72umChecked"));
        ItemList.RAMDie256K.set(addItem(30302, "RAMDie256K", "Wafer40umChecked"));
        ItemList.RAMDie2M.set(addItem(30303, "RAMDie2M", "Wafer8umChecked"));
        ItemList.RAMDie16M.set(addItem(30304, "RAMDie16M", "Wafer400nmChecked"));
        ItemList.RAMDie128M.set(addItem(30305, "RAMDie128M", "Wafer80nmChecked"));
        ItemList.RAMDie768M.set(addItem(30306, "RAMDie768M", "Wafer32nmChecked"));
        ItemList.RAMDie2G.set(addItem(30307, "RAMDie2G", "Wafer14nmChecked"));
        
        ItemList.RAMBar2K4.set(addItem(30350, "RAMBar8K", "Composed by 4* 2K RAM Die")).registerOre("ktfruRAM8K");
        ItemList.RAMBar24K4.set(addItem(30351, "RAMBar96K", "Composed by 4* 24K RAM Die")).registerOre("ktfruRAM96K");
        ItemList.RAMBar256K4.set(addItem(30352, "RAMBar1M", "Composed by 4* 256K RAM Die")).registerOre("ktfruRAM1M");
        ItemList.RAMBar2M4.set(addItem(30353, "RAMBar8M", "Composed by 4* 2M RAM Die")).registerOre("ktfruRAM8M");
        ItemList.RAMBar16M4.set(addItem(30354, "RAMBar64M", "Composed by 4* 16M RAM Die")).registerOre("ktfruRAM64M");
        ItemList.RAMBar128M4.set(addItem(30355, "RAMBar512M", "Composed by 4* 128M RAM Die")).registerOre("ktfruRAM512M");
        ItemList.RAMBar768M4.set(addItem(30356, "RAMBar3G", "Composed by 4* 768M RAM Die")).registerOre("ktfruRAM3G");
        ItemList.RAMBar2G4.set(addItem(30357, "RAMBar8G", "Composed by 4* 2G RAM Die")).registerOre("ktfruRAM8G");

        ItemList.RAMBar2K8.set(addItem(30400, "RAMBar16K", "Composed by 8* 2K RAM Die")).registerOre("ktfruRAM16K");
        ItemList.RAMBar24K8.set(addItem(30401, "RAMBar192K", "Composed by 8* 24K RAM Die")).registerOre("ktfruRAM192K");
        ItemList.RAMBar256K8.set(addItem(30402, "RAMBar2M", "Composed by 8* 256K RAM Die")).registerOre("ktfruRAM2M");
        ItemList.RAMBar2M8.set(addItem(30403, "RAMBar16M", "Composed by 8* 2M RAM Die")).registerOre("ktfruRAM16M");
        ItemList.RAMBar16M8.set(addItem(30404, "RAMBar128M", "Composed by 8* 16M RAM Die")).registerOre("ktfruRAM128M");
        ItemList.RAMBar128M8.set(addItem(30405, "RAMBar1G", "Composed by 8* 128M RAM Die")).registerOre("ktfruRAM1G");
        ItemList.RAMBar768M8.set(addItem(30406, "RAMBar6G", "Composed by 8* 768M RAM Die")).registerOre("ktfruRAM6G");
        ItemList.RAMBar2G8.set(addItem(30407, "RAMBar16G", "Composed by 8* 2G RAM Die")).registerOre("ktfruRAM16G");
        
        ItemList.RAMBar256K16.set(addItem(30452, "RAMBar4M", "Composed by 16* 256K RAM Die")).registerOre("ktfruRAM4M");
        ItemList.RAMBar2M16.set(addItem(30453, "RAMBar32M", "Composed by 16* 2M RAM Die")).registerOre("ktfruRAM32M");
        ItemList.RAMBar16M16.set(addItem(30454, "RAMBar256M", "Composed by 16* 16M RAM Die")).registerOre("ktfruRAM256M");
        ItemList.RAMBar128M16.set(addItem(30455, "RAMBar2G", "Composed by 16* 128M RAM Die")).registerOre("ktfruRAM2G");
        ItemList.RAMBar768M16.set(addItem(30456, "RAMBar12G", "Composed by 16* 768M RAM Die")).registerOre("ktfruRAM12G");
        ItemList.RAMBar2G16.set(addItem(30457, "RAMBar32G", "Composed by 16* 2G RAM Die")).registerOre("ktfruRAM32G");
        
        ItemList.RAMBar16M32.set(addItem(30504, "RAMBar512M", "Composed by 32* 16M RAM Die")).registerOre("ktfruRAM512M");
        ItemList.RAMBar128M32.set(addItem(30505, "RAMBar4G", "Composed by 32* 128M RAM Die")).registerOre("ktfruRAM4G");
        ItemList.RAMBar768M32.set(addItem(30506, "RAMBar24G", "Composed by 32* 768M RAM Die")).registerOre("ktfruRAM24G");
        ItemList.RAMBar2G32.set(addItem(30507, "RAMBar64G", "Composed by 32* 2G RAM Die")).registerOre("ktfruRAM64G");


    }
}