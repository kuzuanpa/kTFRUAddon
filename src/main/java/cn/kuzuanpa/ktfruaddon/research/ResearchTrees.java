/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

package cn.kuzuanpa.ktfruaddon.research;

import cn.kuzuanpa.ktfruaddon.api.code.ItemType;
import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import cn.kuzuanpa.ktfruaddon.api.material.matList;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchProject;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchTree;
import cn.kuzuanpa.ktfruaddon.api.research.task.*;
import cn.kuzuanpa.ktfruaddon.api.research.task.minigame.MiniGameCurrentControlTask;
import cn.kuzuanpa.ktfruaddon.api.research.task.minigame.MiniGameFillTask;
import cn.kuzuanpa.ktfruaddon.api.research.task.minigame.MiniGameIdentifyTask;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import gregapi.data.*;
import gregapi.util.ST;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import zmaster587.advancedRocketry.api.AdvancedRocketryItems;

import java.util.ArrayList;
import java.util.List;

public class ResearchTrees {
    /**Init a tree on start to make other components to work properly**/
    public static List<ResearchTree> exampleTrees = new ArrayList<>();
    public static void init() {
        ResearchTree.ResearchTreeTemplate.put((byte) 0, tree -> {

                    tree.rootItem = new ResearchProject(tree, "科学", "万物的基础", 0).setPos(-5,70);

                    ResearchProject circuitBasic = new ResearchProject(tree, "芯片基础", "在经过了一系列磨难后，你终于在群峦星获得了安身之地。现在，你需要根据你的记忆和想象力，将巨大的电子管电路修改为硅基的集成电路.", AdvancedRocketryItems.itemIC, 0, 1)
                            .setPos(80, 70)
                            .addPrerequisite(tree.rootItem)
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.Cu,32)))
                            .addUnlockItem(new ItemType(ST.make(MD.GC_ADV_ROCKETRY, "circuitIC", 1,3)))
                            .addUnlockItem(new ItemType(ST.make(MD.GC_ADV_ROCKETRY, "circuitIC", 1,4)))
                            .addUnlockItem(new ItemType(ST.make(MD.GC_ADV_ROCKETRY, "circuitIC", 1,5)));

                    ResearchProject projector = new ResearchProject(tree, "投影", "在光下探索光学成像的原理，设计基础投影设备，来将你对机器的构想投射到世界中", AdvancedRocketryItems.itemSatellitePrimaryFunction, 0, 2)
                            .setPos(80, 0)
                            .addPrerequisite(tree.rootItem)
                            .addTask(new ItemConsumeTaskSimple(ST.make(Blocks.glass_pane, 8, 0)))
                            .addUnlockItem(new ItemType(ST.make(MD.VULPES, "item.holoProjector", 1)));

                    ResearchProject siliconBasic = new ResearchProject(tree, "硅理论", "利用最初的芯片辅助你研究硅的特性，了解它的各项特性在芯片制造中的关键作用", Items.paper, 0, 3)
                            .setPos(180, 70)
                            .addPrerequisite(circuitBasic)
                            .addTask(new ItemConsumeTaskSimple(OP.plateTiny.mat(MT.Si,24)))
                            .addUnlockItem(new ItemType(ItemList.SiliconBoulePure.get(1)))
                            .addUnlockItem(new ItemType(ItemList.SiliconPlateT1.get(1)))
                            .addUnlockItem(new ItemType(ItemList.SiliconPlateT2.get(1)));

                    ResearchProject crystallizer = new ResearchProject(tree, "结晶器", "分析晶体生长过程，思考如何获得整齐排布的分子晶体结构", OP.bouleGt.mat(MT.Si, 0).getItem(), MT.Si.mID, 4)
                            .setPos(280, 70)
                            .addPrerequisite(siliconBasic)
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.Cupronickel,32)))
                            .addTask(new ItemConsumeTaskSimple(OP.gem.mat(MT.NetherQuartz,8)))
                            .addUnlockItem(new ItemType(ST.make(MD.GC_ADV_ROCKETRY, "crystallizer", 1)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(20251)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(20252)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(20253)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(20254)));

                    ResearchProject circuitDesignT1 = new ResearchProject(tree, "基础电路设计", "利用计算器进一步改进电路，你认为你离真正的自动化控制不远了", Items.paper, 0, 5)
                            .setPos(380, 70)
                            .addPrerequisite(crystallizer)
                            .addTask(new ItemConsumeTaskSimple(ST.make(MD.GC_ADV_ROCKETRY, "circuitIC", 2,3)))
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.Cu,32)))
                            .addTask(new ItemConsumeTaskSimple(OP.plateTiny.mat(MT.Si,32)))
                            .addUnlockItem(new ItemType(IL.Circuit_Basic.get(1)));

                    ResearchProject lightningProcess = new ResearchProject(tree, "电弧处理", "你的记忆中总能见到电弧，但如何利用它而不损坏材料，需要你进一步研究", Items.paper, 0, 6)
                            .setPos(500, 220)
                            .addPrerequisite(circuitDesignT1)
                            .addTask(new EnergyTask(TD.Energy.EU, 16384, 16))
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.Cu,2)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(20501)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(20502)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(20503)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(20504)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(20504)));

                    ResearchProject pressureContainer = new ResearchProject(tree, "高压容器", "许多处理需要高压环境，通过研究常见材料在高压下的变化来制造耐压容器", Items.paper, 0, 7)
                            .setPos(560, 140)
                            .addPrerequisite(circuitDesignT1)
                            .addTask(new EnergyTask(TD.Energy.RU, 4096, 16))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.Steel,2)))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.Bronze,2)))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.StainlessSteel,2)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(22004)));

                    ResearchProject electromagneticInduction = new ResearchProject(tree, "电磁感应", "你早已听闻电磁感应定律，只需要稍微总结一套方便的规律和程序即可制造大量的实用物品", Items.paper, 0, 8)
                            .setPos(480, 70)
                            .addPrerequisite(circuitDesignT1)
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.Cu,4)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(10111)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(10112)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(10031)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(10032)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(10033)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(10034)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(10035)));

                    ResearchProject batteryBasic = new ResearchProject(tree, "电池原理", "利用已知的电解原理进行电力储存", Items.paper, 0, 9)
                            .setPos(460, 140)
                            .addPrerequisite(circuitDesignT1)
                            .addTask(new ItemConsumeTaskSimple(OP.stick.mat(MT.Pb,2)))
                            .addTask(new ItemConsumeTaskSimple(OP.stick.mat(MT.Au,2)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(14000)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(14001)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(14002)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(14003)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(14004)));

                    ResearchProject semiconductorCooling = new ResearchProject(tree, "半导体制冷", "探索半导体通过电流时的热学行为, 制作半导体制冷器", Items.paper, 0, 10)
                            .setPos(400, 220)
                            .addPrerequisite(circuitDesignT1)
                            .addTask(new EnergyTask(TD.Energy.EU, 4096, 16))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Si,2)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(10161)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(10162)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(10163)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(10164)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(10165)));

                    ResearchProject laserBasic = new ResearchProject(tree, "激光基础", "研究如何利用电力激发二氧化碳产生激光，激光可将能量集中于极小的一点，非常适合精确加工", Items.paper, 0, 11)
                            .setPos(580, 70)
                            .addPrerequisite(electromagneticInduction)
                            .addTask(new EnergyTask(TD.Energy.EU, 16384, 32))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.Sn,2)))
                            .addTask(new FluidConsumeTaskSimple(FL.CarbonDioxide.fluid(), 1000))
                            .addUnlockItem(new ItemType(IL.Comp_Laser_Gas_Empty.get(1)));

                    ResearchProject laserCompose = new ResearchProject(tree, "精确激光控制", "研究如何精确控制激光的能量来加工物品", Items.paper, 0, 12)
                            .setPos(680, 70)
                            .addPrerequisite(laserBasic)
                            .addTask(new EnergyTask(TD.Energy.LU, 4096, 16))
                            .addTask(new MiniGameCurrentControlTask(8))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.Sn,8)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(20321)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(20322)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(20323)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(20324)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(20325)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(23000)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(23001)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(23002)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(23003)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(23004)));

                    ResearchProject bouleMaking = new ResearchProject(tree, "单晶硅制造", "研究如何制作原子排列规整的单晶硅", Items.paper, 0, 13)
                            .setPos(780, 70)
                            .addPrerequisite(laserCompose)
                            .addTask(new EnergyTask(TD.Energy.LU, 4096, 16))
                            .addTask(new FluidConsumeTaskSimple(MT.Si.mLiquid.getFluid(), 1440))
                            .addTask(new ItemConsumeTaskSimple(OP.dustDiv72.mat(MT.Si,1)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(20251)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(20252)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(20253)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(20254)));

                    ResearchProject circuitDesignT2 = new ResearchProject(tree, "低级电路设计", "利用性能更好的硅晶片制作算力更强的芯片", Items.paper, 0, 14)
                            .setPos(880, 70)
                            .addPrerequisite(bouleMaking)
                            .addTask(new MiniGameCurrentControlTask(16))
                            .addTask(new MiniGameFillTask(16))
                            .addTask(new ComputeTask(ComputePower.Normal, 512))
                            .addTask(new ItemConsumeTaskSimple(OP.plateTiny.mat(MT.Si,4)))
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.Cu,16)))
                            .addTask(new ItemConsumeTaskSimple(ST.make(Items.paper, 16,0)))
                            .addUnlockItem(new ItemType(ItemList.GoodCircuitPartCore.get(1)));

                    ResearchProject certusUsage = new ResearchProject(tree, "赛特斯应用", "研究异世界特有的独特晶体-赛特斯, 对赛特斯压缩物体的能力进行探索", Items.paper, 0, 15)
                            .setPos(1000, 140)
                            .addPrerequisite(circuitDesignT2)
                            .addTask(new MiniGameCurrentControlTask(64))
                            .addTask(new MiniGameIdentifyTask(8))
                            .addTask(new MiniGameFillTask(64))
                            .addTask(new ComputeTask(ComputePower.Normal, 20480))
                            .addTask(new ItemConsumeTaskSimple(OP.plateGem.mat(MT.Fluix,4)))
                            .addTask(new ItemConsumeTaskSimple(OP.plateGem.mat(MT.CertusQuartz,16)))
                            .addTask(new ItemConsumeTaskSimple(OP.bolt.mat(MT.Au,16)))
                            .addUnlockItem(new ItemType(ST.make(MD.AE, "item.ItemMultiMaterial", 1, 22)))
                            .addUnlockItem(new ItemType(ST.make(MD.AE, "item.ItemMultiMaterial", 1, 23)))
                            .addUnlockItem(new ItemType(ST.make(MD.AE, "item.ItemMultiMaterial", 1, 24)));

                    ResearchProject certusIntelligence = new ResearchProject(tree, "智能赛特斯", "研究更为罕见的智金, 对其指导赛特斯流动的能力进行控制和规律利用", Items.paper, 0, 16)
                            .setPos(1100, 220)
                            .addPrerequisite(certusUsage)
                            .addTask(new MiniGameCurrentControlTask(64))
                            .addTask(new MiniGameIdentifyTask(64))
                            .addTask(new ComputeTask(ComputePower.Normal, 163840))
                            .addTask(new ItemConsumeTaskSimple(OP.plateGem.mat(MT.Fluix,64)))
                            .addTask(new ItemConsumeTaskSimple(OP.nugget.mat(matList.Ij.mat,16)))
                            .addUnlockItem(new ItemType(ItemList.IntelligentCore.get(1)));

                    ResearchProject highVoltageBasics = new ResearchProject(tree, "高压发电", "研究如何利用更高算力的电路来控制更高电压的发电机", Items.paper, 0, 17)
                            .setPos(980, 70)
                            .addPrerequisite(circuitDesignT2)
                            .addTask(new MiniGameCurrentControlTask(32))
                            .addTask(new ComputeTask(ComputePower.Normal, 1024))
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.Au,64)))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.Rubber,8)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(10113)));

                    //Fill:光路/掩膜空间规划; Glass透镜原型+Si基底+Sn掩膜材料
                    ResearchProject maskAlignTheory = new ResearchProject(tree, "光刻理论", "普通的方法已经达到极限, 你需要研究光刻及相关的设备以制造更精密的芯片", Items.paper, 0, 18)
                            .setPos(1080, 70)
                            .addPrerequisite(highVoltageBasics)
                            .addTask(new EnergyTask(TD.Energy.EU, 16384, 16))
                            .addTask(new ComputeTask(ComputePower.Normal, 2048))
                            .addTask(new MiniGameFillTask(32))
                            .addTask(new ItemConsumeTaskSimple(OP.lens.mat(MT.Glass,4)))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Si,4)))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.Sn,8)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30003)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30077)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30009)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30010)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30011)));

                    //CurrentControl:I/O流程控制; Cu导线+Si芯片+纸设计图纸
                    ResearchProject computerSystemTheory = new ResearchProject(tree, "计算机系统", "光刻能造出芯片，但一堆芯片还不是计算机。设计一套合理的输入输出标准，让运算，存储与外设按同一套规矩对话，以组装出完整的计算机", Items.paper, 0, 19)
                            .setPos(1180, 70)
                            .addPrerequisite(maskAlignTheory)
                            .addTask(new ComputeTask(ComputePower.Normal, 4096))
                            .addTask(new MiniGameCurrentControlTask(32))
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.Cu,32)))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Si,4)))
                            .addTask(new ItemConsumeTaskSimple(ST.make(Items.paper,16,0)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(32006)))
                            .addUnlockItem(new ItemType(ItemList.ComputerTF3386.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ComputerTF3386S.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ComputerBasicCircuits.get(1)));

                    //CurrentControl:电机精确控制; Steel结构+齿轮+Cu导电
                    ResearchProject CNCLathe = new ResearchProject(tree, "数控车床", "通过计算机自动化精确控制电机制作数控车床", Items.paper, 0, 20)
                            .setPos(1300, 0)
                            .addPrerequisite(computerSystemTheory)
                            .addTask(new EnergyTask(TD.Energy.RU, 4096, 16))
                            .addTask(new ComputeTask(ComputePower.Normal, 1024))
                            .addTask(new MiniGameCurrentControlTask(16))
                            .addTask(new ItemConsumeTaskSimple(OP.stick.mat(MT.Steel,8)))
                            .addTask(new ItemConsumeTaskSimple(OP.gear.mat(MT.Steel,4)))
                            .addTask(new ItemConsumeTaskSimple(OP.stick.mat(MT.Cu,4)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30012)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31007)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31008)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31009)));

                    //Fill:轨道/任务规划; Steel结构+Al轻质+Steel支撑
                    ResearchProject SpaceBasicTheory = new ResearchProject(tree, "太空基础理论", "火箭与太空探索所需的基本理论", Items.paper, 0, 21)
                            .setPos(1300, 140)
                            .addPrerequisite(computerSystemTheory)
                            .addTask(new ComputeTask(ComputePower.Normal, 4096))
                            .addTask(new MiniGameFillTask(32))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Steel,16)))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.Al,16)))
                            .addTask(new ItemConsumeTaskSimple(OP.stick.mat(MT.Steel,8)))
                            .addUnlockItem(new ItemType(ItemList.ArmorAirSealant.get(1)))
                            .addUnlockItem(new ItemType(ItemList.SpaceSuitCloth.get(1)));

                    //CurrentControl:推力控制; 引擎零件+不锈钢壳体
                    ResearchProject rocketBasics = new ResearchProject(tree, "火箭基础", "研究火箭的气动外形，引擎推力的改进等基本内容", Items.paper, 0, 22)
                            .setPos(1400, 220)
                            .addPrerequisite(SpaceBasicTheory)
                            .addTask(new ComputeTask(ComputePower.Normal, 4096))
                            .addTask(new MiniGameCurrentControlTask(32))
                            .addTask(new ItemConsumeTaskSimple(ItemList.EngineCrankShaftManual1.get(1)))
                            .addTask(new ItemConsumeTaskSimple(ItemList.EngineCylinderManual1.get(1)))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.StainlessSteel,8)))
                            .addUnlockItem(new ItemType(ST.make(MD.GC_ADV_ROCKETRY, "rocketBuilder",1)));

                    //Fill:空间站布局规划; 不锈钢结构+Al隔热+Glass密封管
                    ResearchProject spaceTheory = new ResearchProject(tree, "空间概论", "研究太空中如何进行航行和维持生命等", Items.paper, 0, 23)
                            .setPos(1480, 140)
                            .addPrerequisite(SpaceBasicTheory)
                            .addTask(new ComputeTask(ComputePower.Normal, 8192))
                            .addTask(new MiniGameFillTask(64))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.StainlessSteel,16)))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.Al,16)))
                            .addUnlockItem(new ItemType(ST.make(MD.GC_ADV_ROCKETRY, "tile.guidanceComputer",1)));

                    //CurrentControl:气体流量控制; Glass容器+Ar保护气+Steel容器
                    ResearchProject protectionUsage = new ResearchProject(tree, "保护气应用", "研究如何利用保护气制作纯度更高，性能更好的物品", Items.paper, 0, 24)
                            .setPos(1280, 70)
                            .addPrerequisite(computerSystemTheory)
                            .addTask(new MiniGameCurrentControlTask(16))
                            .addTask(new FluidConsumeTaskSimple(FL.Argon.fluid(), 8000))
                            .addTask(new ItemConsumeTaskScope(OP.foil.mat(MT.Steel,4)))
                            .addUnlockItem(new ItemType(IL.Comp_Laser_Gas_He.get(1)))
                            .addUnlockItem(new ItemType(IL.Comp_Laser_Gas_Ne.get(1)))
                            .addUnlockItem(new ItemType(IL.Comp_Laser_Gas_Ar.get(1)))
                            .addUnlockItem(new ItemType(IL.Comp_Laser_Gas_Kr.get(1)))
                            .addUnlockItem(new ItemType(IL.Comp_Laser_Gas_Xe.get(1)))
                            .addUnlockItem(new ItemType(IL.Comp_Laser_Gas_HeNe.get(1)))
                            .addUnlockItem(new ItemType(IL.Comp_Laser_Gas_CO.get(1)))
                            .addUnlockItem(new ItemType(IL.Comp_Laser_Gas_CO2.get(1)));

                    //CurrentControl+Fill:电路设计+布局; Si晶片+Au导线+PCB基板
                    ResearchProject computerT2 = new ResearchProject(tree, "入门计算机", "利用初代计算机的算力进一步优化电路设计，以提高算力制作下一代计算机", Items.paper, 0, 25)
                            .setPos(1380, 70)
                            .addPrerequisite(protectionUsage)
                            .addTask(new EnergyTask(TD.Energy.EU, 16384, 32))
                            .addTask(new ComputeTask(ComputePower.Normal, 8192))
                            .addTask(new MiniGameCurrentControlTask(64))
                            .addTask(new MiniGameFillTask(32))
                            .addTask(new ItemConsumeTaskScope(OP.plate.mat(MT.Si,8)))
                            .addTask(new ItemConsumeTaskScope(OP.wireFine.mat(MT.Au,32)))
                            .addTask(new ItemConsumeTaskScope(OP.plate.mat(MT.Polycarbonate,4)))
                            .addUnlockItem(new ItemType(ItemList.ComputerTF3586.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ComputerTF3586S.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGoodCircuits.get(1)));

                    //Fill:叶片形状规划; Ti耐热叶片+转子
                    ResearchProject aerodynamics = new ResearchProject(tree, "叶片气动力学", "研究各种形状的叶片流过流体时对气流和叶片的影响", Items.paper, 0, 26)
                            .setPos(1480, 0)
                            .addPrerequisite(computerT2)
                            .addTask(new EnergyTask(TD.Energy.RU, 8192, 16))
                            .addTask(new ComputeTask(ComputePower.Normal, 8192))
                            .addTask(new MiniGameFillTask(64))
                            .addTask(new ItemConsumeTaskScope(OP.plate.mat(MT.Titanium,8)))
                            .addTask(new ItemConsumeTaskScope(OP.rotor.mat(MT.Titanium,2)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30020)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30021)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30022)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30023)));

                    //Identify:辨别原子信号; U放射性源+Pb屏蔽+Au探测器
                    ResearchProject nuclearStructure = new ResearchProject(tree, "原子结构", "利用物质之间的反应初步确定分子，原子的结构，对不同原子的性质进行研究", Items.paper, 0, 27)
                            .setPos(1480, 70)
                            .addPrerequisite(computerT2)
                            .addTask(new EnergyTask(TD.Energy.EU, 16384, 16))
                            .addTask(new ComputeTask(ComputePower.Normal, 8192))
                            .addTask(new MiniGameIdentifyTask(16))
                            .addTask(new ItemConsumeTaskScope(OP.dust.mat(MT.U_238,4)))
                            .addTask(new ItemConsumeTaskScope(OP.dust.mat(MT.Pb,4)))
                            .addTask(new ItemConsumeTaskScope(OP.foil.mat(MT.Au,4)));

                    //Identify:辨别核反应; U235/U238/Th核燃料同位素
                    ResearchProject nuclearTheory = new ResearchProject(tree, "原子核理论", "通过研究原子核在各种情况下的状态，提出可能修改原子核的理论", Items.paper, 0, 28)
                            .setPos(1580, 70)
                            .addPrerequisite(nuclearStructure)
                            .addTask(new EnergyTask(TD.Energy.EU, 32768, 16))
                            .addTask(new ComputeTask(ComputePower.Normal, 16384))
                            .addTask(new MiniGameIdentifyTask(32))
                            .addTask(new ItemConsumeTaskScope(OP.dust.mat(MT.U_235,4)))
                            .addTask(new ItemConsumeTaskScope(OP.dust.mat(MT.U_238,4)))
                            .addTask(new ItemConsumeTaskScope(OP.dust.mat(MT.Th,4)));

                    //CurrentControl+Fill:电路设计+布局; Si+Au+PCB
                    ResearchProject computerT3 = new ResearchProject(tree, "计算机T3", "原子核研究产生的海量数据迫使你重新规划指令流与缓存结构，解锁10系列计算机", Items.paper, 0, 29)
                            .setPos(1680, 70)
                            .addPrerequisite(nuclearTheory)
                            .addTask(new EnergyTask(TD.Energy.EU, 32768, 32))
                            .addTask(new ComputeTask(ComputePower.Normal, 32768))
                            .addTask(new MiniGameCurrentControlTask(64))
                            .addTask(new MiniGameFillTask(64))
                            .addTask(new ItemConsumeTaskScope(OP.plate.mat(MT.Si,16)))
                            .addTask(new ItemConsumeTaskScope(OP.wireFine.mat(MT.Au,64)))
                            .addTask(new ItemConsumeTaskScope(OP.plate.mat(MT.Polycarbonate,8)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(32005)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGT1000.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGT1090.get(1)));

                    //CurrentControl:链式反应控制; Cd控制棒+Pb屏蔽+U235燃料
                    ResearchProject fissionControl = new ResearchProject(tree, "裂变控制理论", "研究如何在宏观层面来监视和控制裂变反应", Items.paper, 0, 30)
                            .setPos(1780, 70)
                            .addPrerequisite(computerT3)
                            .addTask(new EnergyTask(TD.Energy.EU, 65536, 32))
                            .addTask(new ComputeTask(ComputePower.Normal, 32768))
                            .addTask(new MiniGameCurrentControlTask(96))
                            .addTask(new ItemConsumeTaskScope(OP.stick.mat(MT.Cd,8)))
                            .addTask(new ItemConsumeTaskScope(OP.plate.mat(MT.Pb,16)))
                            .addTask(new ItemConsumeTaskScope(OP.dust.mat(MT.U_235,8)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.gregtech.getItem(9200)));

                    //Identify:辨别成像结果; Pb屏蔽+Co放射源+Glass透镜
                    ResearchProject rayPhoto = new ResearchProject(tree, "放射成像", "研究如何利用强穿透性的放射线对物体内部进行成像", Items.paper, 0, 31)
                            .setPos(1880, 70)
                            .addPrerequisite(fissionControl)
                            .addTask(new EnergyTask(TD.Energy.EU, 65536, 16))
                            .addTask(new ComputeTask(ComputePower.Normal, 32768))
                            .addTask(new MiniGameIdentifyTask(96))
                            .addTask(new ItemConsumeTaskScope(OP.plate.mat(MT.Pb,16)))
                            .addTask(new ItemConsumeTaskScope(OP.dust.mat(MT.Co,4)))
                            .addTask(new ItemConsumeTaskScope(OP.lens.mat(MT.Glass,8)))
                            .addUnlockItem(new ItemType(ItemList.Co60FlawDetectionCore.get(1)))
                            .addUnlockItem(new ItemType(ItemList.Tm170FlawDetectionCore.get(1)));

                    //Identify+CurrentControl:辨别微观结构+控制电子束; W灯丝+Glass透镜+Si样品
                    ResearchProject electronMicroscope = new ResearchProject(tree, "电子显微技术", "利用电子束对微观结构进行观察", Items.paper, 0, 32)
                            .setPos(1980, 70)
                            .addPrerequisite(rayPhoto)
                            .addTask(new EnergyTask(TD.Energy.EU, 131072, 16))
                            .addTask(new ComputeTask(ComputePower.Normal, 65536))
                            .addTask(new MiniGameIdentifyTask(128))
                            .addTask(new MiniGameCurrentControlTask(64))
                            .addTask(new ItemConsumeTaskScope(OP.wireFine.mat(MT.W,32)))
                            .addTask(new ItemConsumeTaskScope(OP.lens.mat(MT.Glass,8)))
                            .addTask(new ItemConsumeTaskScope(OP.plate.mat(MT.Si,8)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30078)));

                    //CurrentControl+Fill+Identify:电路设计+布局+缺陷检测; Si+Au+PTFE绝缘
                    ResearchProject computerT4 = new ResearchProject(tree, "计算机T4", "电子显微技术让你第一次看清刻蚀留下的缺陷，良率随之提升，解锁20, 36系列计算机", Items.paper, 0, 33)
                            .setPos(2080, 70)
                            .addPrerequisite(electronMicroscope)
                            .addTask(new EnergyTask(TD.Energy.EU, 131072, 32))
                            .addTask(new ComputeTask(ComputePower.Normal, 65536))
                            .addTask(new MiniGameCurrentControlTask(128))
                            .addTask(new MiniGameFillTask(128))
                            .addTask(new MiniGameIdentifyTask(32))
                            .addTask(new ItemConsumeTaskEScope(ItemList.CPUDieGT1000.get(12)))
                            .addTask(new ItemConsumeTaskEScope(OP.plate.mat(MT.Si,32)))
                            .addTask(new ItemConsumeTaskEScope(OP.plate.mat(MT.PTFE,8)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGT2000.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGT2090.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGT3660.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGT3680.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGT3699.get(1)));

                    //CurrentControl:节点与交换调度; Fill:机柜/冷却/配电布局; 计算节点+PC机架+Au互联+Glass光纤
                    ResearchProject computerCluster = new ResearchProject(tree, "计算集群", "将计算节点，存储节点，交换设备，冷却与高压配电组装成一整座数据中心，让算力第一次成为可以扩建的生产资料", Items.paper, 0, 44)
                            .setPos(2180, 70)
                            .addPrerequisite(computerT4)
                            .addTask(new EnergyTask(TD.Energy.EU, 262144, 32))
                            .addTask(new ComputeTask(ComputePower.Normal, 131072))
                            .addTask(new MiniGameCurrentControlTask(64))
                            .addTask(new MiniGameFillTask(96))
                            .addTask(new ItemConsumeTaskScope(ItemList.ComputerGT3660.get(2)))
                            .addTask(new ItemConsumeTaskScope(OP.plate.mat(MT.Polycarbonate,16)))
                            .addTask(new ItemConsumeTaskScope(OP.wireFine.mat(MT.Au,64)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30058)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30059)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30060)));

                    //Fill:模型离散化; Identify:用实验校正模型; 自此算力与能源取代物质成为科研主要开销
                    ResearchProject researchWithModel = new ResearchProject(tree, "数字建模研究", "科研方式本身发生了改变：不再是实验得到结果，而是建模，计算，预测，再用实验修正模型。分子，材料，流体，核反应都可以先在集群中跑一遍，超级计算自此成为科研设备", Items.paper, 0, 46)
                            .setPos(2280, 70)
                            .addPrerequisite(computerCluster)
                            .addTask(new EnergyTask(TD.Energy.EU, 524288, 64))
                            .addTask(new ComputeTask(ComputePower.Normal, 2097152))
                            .addTask(new MiniGameFillTask(256))
                            .addTask(new MiniGameIdentifyTask(256));

                    //Identify:在模拟结果中辨认中间态; 建模之后只需极少量催化剂做验证
                    ResearchProject catalyzerTheory = new ResearchProject(tree, "催化剂原理", "模拟揭示了一件事：很多反应并非不能发生，只是能垒太高。研究反应路径，活化能，表面催化与中间态，你意识到催化剂并不提供能量，而是改变能量如何通过整个系统", Items.paper, 0, 34)
                            .setPos(2380, 70)
                            .addPrerequisite(researchWithModel)
                            .addTask(new EnergyTask(TD.Energy.EU, 524288, 64))
                            .addTask(new ComputeTask(ComputePower.Normal, 1048576))
                            .addTask(new MiniGameIdentifyTask(512))
                            .addTask(new ItemConsumeTaskEScope(OP.dustTiny.mat(MT.Pt,4)))
                            .addTask(new ItemConsumeTaskEScope(OP.dustTiny.mat(MT.Pd,4)))
                            .addTask(new ItemConsumeTaskEScope(OP.dustTiny.mat(MT.Ni,8)))
                            .addUnlockItem(new ItemType(ItemList.NickelCatalystCluster.get(1)))
                            .addUnlockItem(new ItemType(ItemList.PlatinumPalladiumCatalystCluster.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ZeoliteCatalystCluster.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ZieglerNattaCatalystCluster.get(1)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30120)));

                    //Fill:聚合物链结构规划; 用催化中间体验证PEEK链结构, 完成后再解锁缩聚催化团
                    ResearchProject advancedPlastic = new ResearchProject(tree, "终极塑料", "有了可控的催化路径和聚醚醚酮前体样本，你能按设计规划聚合物的链结构。PEEK将成为后续所有高强度、耐热与精密绝缘塑料的基体材料", Items.paper, 0, 35)
                            .setPos(2480, 70)
                            .addPrerequisite(catalyzerTheory)
                            .addTask(new EnergyTask(TD.Energy.EU, 1048576, 64))
                            .addTask(new ComputeTask(ComputePower.Normal, 2097152))
                            .addTask(new MiniGameFillTask(512))
                            .addTask(new ItemConsumeTaskEScope(OP.foil.mat(MT.PTFE,8)))
                            .addTask(new ItemConsumeTaskEScope(OP.dust.mat(matList.Difluorobenzophenone.mat,1)))
                            .addTask(new ItemConsumeTaskEScope(OP.dust.mat(matList.Hydroquinone.mat,4)))
                            .addUnlockItem(new ItemType(ItemList.PolycondensationCatalystCluster.get(1)))
                            .addUnlockItem(new ItemType(OP.plate.mat(matList.PEEK.mat,1)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30121)));

                    //CurrentControl+Fill+Identify:FinFET工艺; Si+Au+PTFE
                    ResearchProject computerT5 = new ResearchProject(tree, "计算机T5", "平面晶体管的漏电已无法忍受，改用立体的鳍式结构重新组织沟道，解锁36v2系列计算机", Items.paper, 0, 36)
                            .setPos(2580, 70)
                            .addPrerequisite(advancedPlastic)
                            .addTask(new EnergyTask(TD.Energy.EU, 4194304, 64))
                            .addTask(new ComputeTask(ComputePower.Normal, 8388608))
                            .addTask(new MiniGameCurrentControlTask(192))
                            .addTask(new MiniGameFillTask(256))
                            .addTask(new MiniGameIdentifyTask(512))
                            .addTask(new ItemConsumeTaskEScope(OP.dust.mat(MT.Si,32)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGT3660v2.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGT3680v2.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGT3699v2.get(1)));

                    //Fill+CurrentControl:空间站布局+系统控制; Ti结构+不锈钢壳体+Ti齿轮
                    ResearchProject spaceStationTheory = new ResearchProject(tree, "空间站理论", "研究如何构建稳定运行的空间站", Items.paper, 0, 37)
                            .setPos(1580, 140)
                            .addPrerequisite(spaceTheory)
                            .addPrerequisite(computerT2)
                            .addTask(new ComputeTask(ComputePower.Normal, 32768))
                            .addTask(new MiniGameFillTask(64))
                            .addTask(new MiniGameCurrentControlTask(32))
                            .addTask(new ItemConsumeTaskEScope(OP.plate.mat(MT.Titanium,32)))
                            .addTask(new ItemConsumeTaskEScope(OP.plate.mat(MT.StainlessSteel,32)))
                            .addTask(new ItemConsumeTaskEScope(OP.gear.mat(MT.Titanium,8)))
                            .addUnlockItem(new ItemType(ST.make(MD.GC_ADV_ROCKETRY, "tile.stationAssembler",1)));

                    //Fill:微重力实验规划; Ti容器+Al轻质
                    ResearchProject lowGravityUsage = new ResearchProject(tree, "微重力应用", "研究微重力环境可能的用途", Items.paper, 0, 38)
                            .setPos(1780, 140)
                            .addPrerequisite(spaceStationTheory)
                            .addTask(new ComputeTask(ComputePower.Normal, 65536))
                            .addTask(new MiniGameFillTask(96))
                            .addTask(new ItemConsumeTaskEScope(OP.plate.mat(MT.Titanium,16)))
                            .addTask(new ItemConsumeTaskEScope(OP.foil.mat(MT.Al,32)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30122)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30123)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31204)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31205)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31206)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31207)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31208)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30124)));

                    //Identify:辨别辐射信号; Pb屏蔽+钨钢结构+U放射源
                    ResearchProject universeRadio = new ResearchProject(tree, "宇宙辐射研究", "研究宇宙辐射和其对物体可能的用途", Items.paper, 0, 39)
                            .setPos(1980, 140)
                            .addPrerequisite(lowGravityUsage)
                            .addTask(new ComputeTask(ComputePower.Normal, 131072))
                            .addTask(new MiniGameIdentifyTask(128))
                            .addTask(new ItemConsumeTaskEScope(OP.plate.mat(MT.Lead,32)))
                            .addTask(new ItemConsumeTaskEScope(OP.plate.mat(MT.TungstenSteel,8)))
                            .addTask(new ItemConsumeTaskEScope(OP.dust.mat(MT.U_238,8)))
                            .addTask(new ItemConsumeTaskSimple(ItemList.CosmicRadiationBackgroundData.get(1), 32))
                            .addTask(new ItemConsumeTaskSimple(ItemList.CosmicRadiationSpectrumData.get(1), 32))
                            .addTask(new ItemConsumeTaskSimple(ItemList.HighEnergyCosmicParticleData.get(1), 16));

                    //CurrentControl+Fill+Identify:微重力芯片工艺; Si+Au+PTFE
                    ResearchProject computerT6 = new ResearchProject(tree, "计算机T6", "轨道上没有重力导致的熔体对流，也没有沉降，晶圆能长得更完美，解锁36v3系列计算机", Items.paper, 0, 40)
                            .setPos(2680, 70)
                            .addPrerequisite(computerT5)
                            .addPrerequisite(universeRadio)
                            .addTask(new EnergyTask(TD.Energy.EU, 2097152, 64))
                            .addTask(new ComputeTask(ComputePower.Normal, 8388608))
                            .addTask(new MiniGameCurrentControlTask(256))
                            .addTask(new MiniGameFillTask(192))
                            .addTask(new MiniGameIdentifyTask(128))
                            .addTask(new ItemConsumeTaskEScope(OP.plate.mat(MT.Si,48)))
                            .addTask(new ItemConsumeTaskEScope(OP.wireFine.mat(MT.Au,64)))
                            .addTask(new ItemConsumeTaskEScope(OP.plate.mat(MT.PTFE,16)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGT3660v3.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGT3680v3.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGT3699v3.get(1)));

                    //CurrentControl:等离子体位形与破裂抑制; MU:磁笼; CU:超导磁体冷却; 氘氚点火验证
                    ResearchProject fusionTokamakExp = new ResearchProject(tree, "实验托卡马克", "第一次把上亿度的等离子体关进磁笼。实验堆点火时间很短，能量收支勉强打平，但它证明了恒星的燃烧方式可以被搬进厂房里", Items.paper, 0, 45)
                            .setPos(2780, 0)
                            .addPrerequisite(computerT6)
                            .addTask(new EnergyTask(TD.Energy.EU, 4194304, 512))
                            .addTask(new EnergyTask(TD.Energy.MU, 1048576, 128))
                            .addTask(new EnergyTask(TD.Energy.CU, 262144, 64))
                            .addTask(new ComputeTask(ComputePower.Normal, 16777216))
                            .addTask(new MiniGameCurrentControlTask(256))
                            .addTask(new FluidConsumeTaskSimple(FL.Deuterium.fluid(), 16000))
                            .addTask(new FluidConsumeTaskSimple(FL.Tritium.fluid(), 4000))
                            .addTask(new ItemConsumeTaskEScope(OP.plate.mat(MT.TungstenSteel,32)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30014)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31016)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31017)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31018)));

                    //CurrentControl+Fill:长脉冲运行+氚增殖包层与偏滤器布局; 目标是并网而不是点火
                    ResearchProject fusionTokamak = new ResearchProject(tree, "商用托卡马克", "把实验堆变成能并入电网的工业能源设施：长时间持续运行，输出稳定，功率巨大，代价是对燃料供应与维护的苛刻要求。氘氚循环只是起点，更先进的燃料循环与更高温的等离子体会带来更高级的聚变电站", Items.paper, 0, 47)
                            .setPos(2880, 0)
                            .addPrerequisite(fusionTokamakExp)
                            .addTask(new EnergyTask(TD.Energy.EU, 16777216, 2048))
                            .addTask(new EnergyTask(TD.Energy.MU, 4194304, 512))
                            .addTask(new EnergyTask(TD.Energy.CU, 1048576, 128))
                            .addTask(new ComputeTask(ComputePower.Normal, 67108864))
                            .addTask(new MiniGameCurrentControlTask(384))
                            .addTask(new MiniGameFillTask(256))
                            .addTask(new FluidConsumeTaskSimple(FL.Deuterium.fluid(), 64000))
                            .addTask(new FluidConsumeTaskSimple(FL.Helium.fluid(), 32000))
                            .addTask(new ItemConsumeTaskEScope(OP.plate.mat(MT.Trinitanium,16)))
                            .addTask(new ItemConsumeTaskSimple(ItemList.TokamakPlasmaData.get(32), 1))
                            .addTask(new ItemConsumeTaskSimple(ItemList.TokamakNeutronData.get(32), 1))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30015)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31025)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31026)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31027)));

                    //LU:数百束激光同时压缩靶丸; Identify:在爆后碎片里辨认新核素; 先验证脉冲压缩与诊断链
                    ResearchProject fusionLaser = new ResearchProject(tree, "实验激光聚变", "用数百束激光同时压缩靶丸，验证激光能量能否在极短时间内转化为足够高的能量密度。实验靶室只负责建立聚变条件并采集压缩与中子诊断数据。", Items.paper, 0, 48)
                            .setPos(2780, 140)
                            .addPrerequisite(computerT6)
                            .addTask(new EnergyTask(TD.Energy.EU, 8388608, 1024))
                            .addTask(new EnergyTask(TD.Energy.LU, 4194304, 512))
                            .addTask(new ComputeTask(ComputePower.Normal, 33554432))
                            .addTask(new MiniGameCurrentControlTask(320))
                            .addTask(new MiniGameIdentifyTask(256))
                            .addTask(new FluidConsumeTaskSimple(FL.Deuterium.fluid(), 32000))
                            .addTask(new ItemConsumeTaskEScope(IL.Comp_Laser_Gas_CO2.get(16)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30071)));

                    ResearchProject fusionLaserCommercial = new ResearchProject(tree, "商用激光聚变", "把实验靶室放大为工业核合成炉。确认激光压缩与中子诊断数据后，工业靶室可以提高峰值脉冲并开始量产超重同位素、短寿命核素与人工元素。", Items.paper, 0, 69)
                            .setPos(2880, 140)
                            .addPrerequisite(fusionLaser)
                            .addTask(new EnergyTask(TD.Energy.EU, 8388608, 1024))
                            .addTask(new EnergyTask(TD.Energy.LU, 16777216, 2048))
                            .addTask(new ComputeTask(ComputePower.Normal, 67108864))
                            .addTask(new MiniGameCurrentControlTask(320))
                            .addTask(new MiniGameIdentifyTask(256))
                            .addTask(new ItemConsumeTaskSimple(ItemList.LaserFusionCompressionData.get(32), 1))
                            .addTask(new ItemConsumeTaskSimple(ItemList.LaserFusionNeutronData.get(32), 1))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30072)));

                    //NU:中子俘获通道测绘; Identify:异常核过程的能谱辨认
                    ResearchProject naqudahTheory = new ResearchProject(tree, "硅岩性质理论", "硅岩从来不只是燃料。研究它的晶格结构、中子俘获、同位素性质、裂变通道与高能态，你会发现它是一种能够进入异常核过程的媒介。", Items.paper, 0, 41)
                            .setPos(2980, 140)
                            .addPrerequisite(fusionLaserCommercial)
                            .addTask(new EnergyTask(TD.Energy.EU, 16777216, 2048))
                            .addTask(new EnergyTask(TD.Energy.NU, 1048576, 128))
                            .addTask(new ComputeTask(ComputePower.Normal, 134217728))
                            .addTask(new MiniGameIdentifyTask(384))
                            .addTask(new ItemConsumeTaskEScope(OP.dustTiny.mat(MT.Nq,8)))
                            .addTask(new ItemConsumeTaskEScope(OP.dustTiny.mat(MT.Nq_528,4)))
                            .addTask(new ItemConsumeTaskEScope(OP.plate.mat(MT.Pb,32)));

                    //CurrentControl+Fill+Identify:把经典范式榨到极限; 物质开销已可忽略, 瓶颈只剩算力与配电
                    ResearchProject computerT7 = new ResearchProject(tree, "计算机T7", "对经典计算的最后一次榨取：CPU集群、专用加速器与超高速互联全部推到极限，建成极限经典计算体系。", Items.paper, 0, 43)
                            .setPos(2980, 70)
                            .addPrerequisite(naqudahTheory)
                            .addPrerequisite(fusionTokamak)
                            .addTask(new EnergyTask(TD.Energy.EU, 67108864, 8192))
                            .addTask(new ComputeTask(ComputePower.Normal, 536870912))
                            .addTask(new MiniGameCurrentControlTask(384))
                            .addTask(new MiniGameFillTask(320))
                            .addTask(new MiniGameIdentifyTask(256))
                            .addTask(new ItemConsumeTaskEScope(OP.plate.mat(MT.Si,64)))
                            .addTask(new ItemConsumeTaskEScope(OP.wireFine.mat(MT.Graphene,64)))
                            .addTask(new ItemConsumeTaskEScope(OP.foil.mat(MT.Nq,16)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGT3660v4.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGT3680v4.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGT3699v4.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGT3680v3e.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGT3699v3e.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGT3680v4e.get(1)))
                            .addUnlockItem(new ItemType(ItemList.ComputerGT3699v4e.get(1)));

                    //纯算力节点: 训练过程只烧算力与电, 之后所有研究的实物需求都由智能体压到最低
                    ResearchProject AIResearch = new ResearchProject(tree, "人工智能研究", "在极限算力之上训练出能够自行提出假设、设计实验、归纳规律的智能体。科研第一次不再完全由你驱动，而是由你和机器共同推进", Items.paper, 0, 49)
                            .setPos(3080, 70)
                            .addPrerequisite(computerT7)
                            .addTask(new EnergyTask(TD.Energy.EU, 268435456, 8192))
                            .addTask(new ComputeTask(ComputePower.Normal, 4294967296L))
                            .addTask(new MiniGameIdentifyTask(256));
                    // TODO(Biology tree): move the Biology compute gate into the independent Biology research tree.


                    //智能体接手了假设与试错, 实物只剩下最后的样品验证; 此后各节点主要开销为算力与能源
                    ResearchProject todo001 = new ResearchProject(tree, "镜金性质", "镜金是一种能在宏观尺度上稳定保存量子关联的特殊材料，核心属性是量子相干性。", Items.paper, 0, 42)
                            .setPos(3180, 70)
                            .addPrerequisite(AIResearch)
                            .addPrerequisite(universeRadio)
                            .addTask(new EnergyTask(TD.Energy.EU, 536870912, 8192))
                            .addTask(new EnergyTask(TD.Energy.CU, 4194304, 512))
                            .addTask(new ComputeTask(ComputePower.Normal, 8589934592L))
                            .addTask(new MiniGameIdentifyTask(384))
                            .addTask(new ItemConsumeTaskEScope(OP.nugget.mat(matList.Ij.mat,8)))
                            .addTask(new ItemConsumeTaskEScope(OP.dustTiny.mat(MT.Pt,4)))
                            .addTask(new ItemConsumeTaskEScope(OP.dustTiny.mat(MT.Nq_522,4)));

                    //QU:纠缠信道; 通道无法窃听也无法复制, 代价是维持相干的能耗
                    ResearchProject todo002 = new ResearchProject(tree, "量子通信", "利用镜金保存下来的纠缠关系传递信息，通道既无法窃听也无法复制。同一套原理还能做出量子传感与超高精度测量设备", Items.paper, 0, 54)
                            .setPos(3280, 0)
                            .addPrerequisite(todo001)
                            .addTask(new EnergyTask(TD.Energy.QU, 1048576, 128))
                            .addTask(new EnergyTask(TD.Energy.CU, 8388608, 512))
                            .addTask(new ComputeTask(ComputePower.Normal, 17179869184L))
                            .addTask(new MiniGameIdentifyTask(512))
                            .addTask(new ItemConsumeTaskEScope(OP.nugget.mat(matList.Ij.mat,4)));

                    //QU:把被动保存的量子关系驱动起来; 退相干率随纯度逐级下降
                    ResearchProject todo003 = new ResearchProject(tree, "镜金量子化", "对镜金进行进一步的量子化处理，让材料内部的量子关系从被动保存变成可以被外部驱动的自由度。这是通往量金的最后一步加工", Items.paper, 0, 51)
                            .setPos(3280, 70)
                            .addPrerequisite(todo001)
                            .addTask(new EnergyTask(TD.Energy.EU, 1073741824, 32768))
                            .addTask(new EnergyTask(TD.Energy.QU, 4194304, 512))
                            .addTask(new EnergyTask(TD.Energy.CU, 16777216, 2048))
                            .addTask(new ComputeTask(ComputePower.Normal, 34359738368L))
                            .addTask(new MiniGameCurrentControlTask(512))
                            .addTask(new ItemConsumeTaskEScope(OP.nugget.mat(matList.Ij.mat,16)));

                    //QU:宏观量子操作的第一件工程材料; 对应GT6的QU级设备
                    ResearchProject todo004 = new ResearchProject(tree, "量金性质", "镜金保存量子关系，量金则把量子关系变成宏观可操作的工程对象。它是量子装置的核心、量子信号的介质、量子放大器与控制器的本体，也是QU级设备的基础材料。你第一次获得宏观量子操作能力", Items.paper, 0, 52)
                            .setPos(3380, 70)
                            .addPrerequisite(todo003)
                            .addTask(new EnergyTask(TD.Energy.QU, 16777216, 2048))
                            .addTask(new ComputeTask(ComputePower.Normal, 68719476736L))
                            .addTask(new MiniGameCurrentControlTask(512))
                            .addTask(new MiniGameIdentifyTask(384))
                            .addTask(new ItemConsumeTaskEScope(OP.dustTiny.mat(MT.Naquadria,4)));

                    //Identify:测量本身就会破坏被测的量子态, 没有它量子设备连自己算错了都不知道
                    ResearchProject todo005 = new ResearchProject(tree, "量子观测", "知道一个系统，和对系统进行测量，并不是一回事。研究量子态、测量、退相干、纠缠、叠加与量子概率，造出量子观测仪、量子态分析器、纠缠检测器与相干性稳定器——没有它们，量子设备连自己算错了都不会知道", Items.paper, 0, 53)
                            .setPos(3480, 70)
                            .addPrerequisite(todo004)
                            .addTask(new EnergyTask(TD.Energy.QU, 33554432, 4096))
                            .addTask(new EnergyTask(TD.Energy.CU, 33554432, 2048))
                            .addTask(new MiniGameIdentifyTask(768));

                    //新的计算范式而非更快的电脑: 初代机的开销几乎全在纠错与制冷
                    ResearchProject todo008 = new ResearchProject(tree, "量子计算T1", "真正意义上的量子计算终于实现，但初代机问题严重：退相干、高错误率、散热、昂贵的量子纠错，以及体积惊人的控制设备。它不是更快的电脑，而是一种新的计算范式——有些问题它一步得解，有些问题它比经典机还慢", Items.paper, 0, 55)
                            .setPos(3580, 70)
                            .addPrerequisite(todo005)
                            .addTask(new EnergyTask(TD.Energy.QU, 134217728, 8192))
                            .addTask(new EnergyTask(TD.Energy.CU, 134217728, 4096))
                            .addTask(new ComputeTask(ComputePower.Normal, 137438953472L))
                            .addTask(new MiniGameCurrentControlTask(768))
                            .addTask(new MiniGameIdentifyTask(512))
                            .addTask(new ItemConsumeTaskEScope(OP.nugget.mat(matList.Ij.mat,32)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30065)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31065)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31066)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31067)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31068)));

                    //Biology算力:分子装配的构型搜索; 从原子出发装配分子, 制造精度降到原子级
                    ResearchProject todo006 = new ResearchProject(tree, "量子有机生产", "传统路线是矿物到材料再到产品，现在你从原子出发装配分子，再把分子摆进指定的结构。高级聚合物、生物材料、特殊药物与自定义有机材料都可以按设计生产，制造精度第一次下降到原子级", Items.paper, 0, 57)
                            .setPos(3580, 0)
                            .addPrerequisite(todo004)
                            .addTask(new EnergyTask(TD.Energy.QU, 16777216, 2048))
                            .addTask(new ComputeTask(ComputePower.Quantum, 131072))
                            .addTask(new MiniGameFillTask(512))
                            .addTask(new ItemConsumeTaskEScope(ItemList.Proton.get(16)))
                            .addTask(new ItemConsumeTaskEScope(ItemList.Electron.get(16)));
                    // TODO(Biology tree): the Biology compute portion of quantum organic production belongs to the independent Biology tree.

                    //按需编排核子与电子, 合成自然界不存在的元素与同位素
                    ResearchProject todo007 = new ResearchProject(tree, "量子元素制造", "在量子层面直接编排核子与电子的排布，按需要合成元素与同位素，包括自然界中根本不存在的那些。元素周期表从此由你续写", Items.paper, 0, 50)
                            .setPos(3580, 140)
                            .addPrerequisite(todo004)
                            .addTask(new EnergyTask(TD.Energy.QU, 67108864, 4096))
                            .addTask(new EnergyTask(TD.Energy.NU, 16777216, 1024))
                            .addTask(new ComputeTask(ComputePower.Quantum, 524288))
                            .addTask(new MiniGameIdentifyTask(512))
                            .addTask(new ItemConsumeTaskEScope(ItemList.Neutron.get(32)))
                            .addTask(new ItemConsumeTaskEScope(ItemList.Alpha_Particle.get(16)));


                    //同一个跳跃逐级放大尺度: 电子->原子->能态->物态->宏观物体->空间; Spacetime算力首次介入
                    ResearchProject todo009 = new ResearchProject(tree, "跃迁理论", "从电子跃迁到原子跃迁，再到能态跃迁、物质状态跃迁、宏观物体跃迁，最后是空间跃迁。每一级都只是把同一个跳跃放大一个尺度，而终点是传送与星际航行", Items.paper, 0, 56)
                            .setPos(3680, 0)
                            .addPrerequisite(todo008)
                            .addTask(new EnergyTask(TD.Energy.QU, 268435456, 8192))
                            .addTask(new EnergyTask(TD.Energy.TU, 4194304, 512))
                            .addTask(new ComputeTask(ComputePower.Quantum, 2097152))
                            .addTask(new MiniGameIdentifyTask(768))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30066)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31069)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31070)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31071)));

                    //第二次世界观翻转: 量子之下仍有亚量子自由度
                    ResearchProject todo010 = new ResearchProject(tree, "量子结构理论", "你曾以为原子之下就是量子，现在却发现量子本身仍然存在更深层的结构。亚量子自由度、量子内部结构、亚量子耦合与异常量子态，这是整条科技树第二次世界观翻转", Items.paper, 0, 58)
                            .setPos(3680, 70)
                            .addPrerequisite(todo008)
                            .addTask(new EnergyTask(TD.Energy.QU, 536870912, 32768))
                            .addTask(new ComputeTask(ComputePower.Quantum, 4194304))
                            .addTask(new MiniGameIdentifyTask(1024))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30067)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31072)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31073)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31074)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31075)));

                    //多个亚量子自由度稳定耦合形成的奇异物质, 一切亚量子工程的基础材料
                    ResearchProject todo011 = new ResearchProject(tree, "刻金性质", "刻金是由多个亚量子自由度稳定耦合形成的奇异物质。镜金操纵量子之间的关系，刻金操纵量子内部更深层的结构，它是一切亚量子工程的基础材料", Items.paper, 0, 59)
                            .setPos(3780, 70)
                            .addPrerequisite(todo010)
                            .addTask(new EnergyTask(TD.Energy.QU, 1073741824, 32768))
                            .addTask(new ComputeTask(ComputePower.Quantum, 8388608))
                            .addTask(new MiniGameCurrentControlTask(1024))
                            .addTask(new ItemConsumeTaskEScope(OP.nugget.mat(matList.Ij.mat,64)))
                            .addTask(new ItemConsumeTaskEScope(OP.dustTiny.mat(MT.Naquadria,16)));

                    // TODO(Materials): add the 刻金 material and its recipes, then make the first Spacetime node consume it.
                    ResearchProject spacetimeComputing = new ResearchProject(tree, "时空计算", "刻金把可控自由度推进到量子内部结构。以此为基础，把场演化、尺度变换与因果约束纳入统一计算模型，制造能够处理时空变量的计算阵列。", Items.paper, 0, 68)
                            .setPos(3880, 70)
                            .addPrerequisite(todo011)
                            .addTask(new EnergyTask(TD.Energy.QU, 2147483648L, 65536))
                            .addTask(new ComputeTask(ComputePower.Quantum, 16777216))
                            .addTask(new MiniGameCurrentControlTask(1024))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30069)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30070)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31080)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31081)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31082)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31083)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31084)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31085)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31086)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31087)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31088)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31089)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31090)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(32040)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(32041)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(32042)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(32043)));

                    //集成化->模块化->微型化->工程化: 量子计算从科研设备变成工业基础设施
                    ResearchProject todo012 = new ResearchProject(tree, "微型量子计算", "初代量子计算机巨大、脆弱且昂贵。经过集成化、模块化、微型化与工程化，你得到量子处理芯片、量子控制器、集成量子模块与小型量子计算机——量子计算从科研设备变成工业基础设施", Items.paper, 0, 60)
                            .setPos(3880, 0)
                            .addPrerequisite(todo011)
                            .addTask(new EnergyTask(TD.Energy.QU, 2147483648L, 65536))
                            .addTask(new ComputeTask(ComputePower.Quantum, 16777216))
                            .addTask(new MiniGameCurrentControlTask(1024))
                            .addTask(new MiniGameFillTask(768))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30068)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(30079)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31076)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31077)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31078)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(31079)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(32040)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(32041)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(32042)))
                            .addUnlockItem(new ItemType(GTTileEntityRegistry.ktfruaddon.getItem(32043)));

                    //不再依赖化学键与晶格, 直接用强相互作用稳定核层面的结构
                    ResearchProject todo013 = new ResearchProject(tree, "强力物质理论", "不再依赖化学键、金属键与晶格结构，而是直接利用强相互作用稳定原子核层面的结构。材料性能从此不再受普通化学规律的约束", Items.paper, 0, 62)
                            .setPos(3880, 140)
                            .addPrerequisite(spacetimeComputing)
                            .addTask(new EnergyTask(TD.Energy.QU, 2147483648L, 65536))
                            .addTask(new EnergyTask(TD.Energy.NU, 134217728, 4096))
                            .addTask(new ComputeTask(ComputePower.Quantum, 33554432))
                            .addTask(new ComputeTask(ComputePower.Spacetime, 2097152))
                            .addTask(new MiniGameIdentifyTask(1024));

                    //性能直接来自材料本身, 而不再靠堆砌材料抵抗压力
                    ResearchProject todo014 = new ResearchProject(tree, "强力物质生产", "极高强度、极高耐热、极高抗辐射、极高密度、极端稳定。过去要靠堆砌大量材料来抵抗压力，现在性能直接来自材料本身，它将成为后期工业的基础建筑材料", Items.paper, 0, 61)
                            .setPos(3980, 210)
                            .addPrerequisite(todo013)
                            .addTask(new EnergyTask(TD.Energy.QU, 4294967296L, 65536))
                            .addTask(new EnergyTask(TD.Energy.NU, 536870912, 8192))
                            .addTask(new ComputeTask(ComputePower.Quantum, 67108864))
                            .addTask(new MiniGameFillTask(1024));

                    //研究对象从物质转向物质存在的背景本身
                    ResearchProject todo015 = new ResearchProject(tree, "零点场论", "真空并非真正意义上的空无。研究真空涨落、零点能、场、真空结构、虚粒子与真空极化——过去你研究物质，现在你研究物质存在的背景本身", Items.paper, 0, 63)
                            .setPos(3980, 70)
                            .addPrerequisite(todo013)
                            .addTask(new EnergyTask(TD.Energy.QU, 8589934592L, 262144))
                            .addTask(new ComputeTask(ComputePower.Quantum, 134217728))
                            .addTask(new ComputeTask(ComputePower.Spacetime, 8388608))
                            .addTask(new MiniGameIdentifyTask(1536));

                    //能量来源近乎无限, 但真正的限制变成了控制能力
                    ResearchProject todo016 = new ResearchProject(tree, "零点能量生成", "从真空中提取能量。但零点能不是免费电力：它需要极其复杂的场稳定与量子控制，需要镜金、量金、刻金与超级计算全力配合。能量来源近乎无限，不等于工业生产没有限制——真正的限制变成了控制能力", Items.paper, 0, 64)
                            .setPos(4080, 70)
                            .addPrerequisite(todo015)
                            .addTask(new EnergyTask(TD.Energy.QU, 34359738368L, 1048576))
                            .addTask(new ComputeTask(ComputePower.Quantum, 536870912))
                            .addTask(new ComputeTask(ComputePower.Spacetime, 33554432))
                            .addTask(new MiniGameCurrentControlTask(2048));

                    //代价用信息复杂度衡量: 后期的稀缺从矿石不够变成信息不够
                    ResearchProject todo017 = new ResearchProject(tree, "零点物质生成", "建立能量到物质的工业体系，只使用能量制造原子、同位素、材料、复杂分子与特殊晶体。", Items.paper, 0, 65)
                            .setPos(4180, 70)
                            .addPrerequisite(todo016)
                            .addTask(new EnergyTask(TD.Energy.QU, 137438953472L, 1048576))
                            .addTask(new ComputeTask(ComputePower.Quantum, 536870912L))
                            .addTask(new ComputeTask(ComputePower.Spacetime, 134217728))
                            .addTask(new MiniGameFillTask(2048));
                    // TODO(Biology tree): complex structure writing will consume Biology compute from the independent tree.

                    //科学描述规则, 工程利用规则, 魔法操作规则, 三者在此统一
                    ResearchProject todo018 = new ResearchProject(tree, "科魔统一理论", "所谓魔法从来没有违反物理规律，它只是直接调用现实底层自由度的一种技术。科学描述规则，工程利用规则，魔法操作规则，三者在此统一。于是出现全新的机器范式：规则编译器、因果控制器、现实场发生器、概念稳定器与物理规则接口——你不再只是制造物质，而开始制造能够改变物质行为的规则", Items.paper, 0, 66)
                            .setPos(4280, 70)
                            .addPrerequisite(todo017)
                            .addTask(new EnergyTask(TD.Energy.QU, 549755813888L, 4194304))
                            .addTask(new EnergyTask(TD.Energy.TU, 34359738368L, 1048576))
                            .addTask(new ComputeTask(ComputePower.Quantum, 2147483648L))
                            .addTask(new ComputeTask(ComputePower.Spacetime, 2147483648L))
                            .addTask(new MiniGameIdentifyTask(4096))
                            .addTask(new MiniGameCurrentControlTask(4096));

                    //资源, 能量, 制造, 物质, 信息全部自由: 你开始决定石头应该是什么
                    ResearchProject todo019 = new ResearchProject(tree, "无尽", "能源可以无限，物质可以从场中生成，信息可以计算，那么还剩下什么限制？答案是物质本身。无尽物质不是无限金属，而是能够在不依赖外部资源输入的条件下维持自身结构、并无限延伸其可制造状态空间的终极存在。资源、能量、制造、物质、信息，全部自由。当你已经从长河中寻找到每一块石头，就该由你决定什么是石头", Items.paper, 0, 67)
                            .setPos(4380, 70)
                            .addPrerequisite(todo018)
                            .addTask(new EnergyTask(TD.Energy.QU, 4398046511104L, 16777216))
                            .addTask(new EnergyTask(TD.Energy.TU, 274877906944L, 4194304))
                            .addTask(new ComputeTask(ComputePower.Normal, 4398046511104L))
                            .addTask(new ComputeTask(ComputePower.Quantum, 8589934592L))
                            .addTask(new ComputeTask(ComputePower.Spacetime, 17179869184L))
                            .addTask(new MiniGameFillTask(8192))
                            .addTask(new MiniGameIdentifyTask(8192))
                            .addTask(new MiniGameCurrentControlTask(8192));
                    // TODO(Biology tree): the endgame Biology requirement belongs to the independent tree.
                    return tree;
        }
        );
        ResearchTree tree = new ResearchTree();
        tree.applyTemplate((byte)0);
        exampleTrees.add(tree);
    }
}
