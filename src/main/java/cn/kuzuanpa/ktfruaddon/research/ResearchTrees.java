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
import cn.kuzuanpa.ktfruaddon.api.research.task.ComputeTask;
import cn.kuzuanpa.ktfruaddon.api.research.task.EnergyTask;
import cn.kuzuanpa.ktfruaddon.api.research.task.FluidConsumeTaskSimple;
import cn.kuzuanpa.ktfruaddon.api.research.task.ItemConsumeTaskSimple;
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
                            .addTask(new ItemConsumeTaskSimple(OP.plateTiny.mat(MT.Si,24)));

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
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.Sn,8)));

                    //CurrentControl:I/O流程控制; Cu导线+Si芯片+纸设计图纸
                    ResearchProject computerSystemTheory = new ResearchProject(tree, "计算机系统", "设计一套合理的输入输出标准, 以组装出完整的计算机", Items.paper, 0, 19)
                            .setPos(1180, 70)
                            .addPrerequisite(maskAlignTheory)
                            .addTask(new ComputeTask(ComputePower.Normal, 4096))
                            .addTask(new MiniGameCurrentControlTask(32))
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.Cu,32)))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Si,4)))
                            .addTask(new ItemConsumeTaskSimple(ST.make(Items.paper,16,0)));

                    //CurrentControl:电机精确控制; Steel结构+齿轮+Cu导电
                    ResearchProject CNCLathe = new ResearchProject(tree, "数控车床", "通过计算机自动化精确控制电机制作数控车床", Items.paper, 0, 20)
                            .setPos(1300, 0)
                            .addPrerequisite(computerSystemTheory)
                            .addTask(new EnergyTask(TD.Energy.RU, 4096, 16))
                            .addTask(new ComputeTask(ComputePower.Normal, 1024))
                            .addTask(new MiniGameCurrentControlTask(16))
                            .addTask(new ItemConsumeTaskSimple(OP.stick.mat(MT.Steel,8)))
                            .addTask(new ItemConsumeTaskSimple(OP.gear.mat(MT.Steel,4)))
                            .addTask(new ItemConsumeTaskSimple(OP.stick.mat(MT.Cu,4)));

                    //Fill:轨道/任务规划; Steel结构+Al轻质+Steel支撑
                    ResearchProject SpaceBasicTheory = new ResearchProject(tree, "太空基础理论", "火箭与太空探索所需的基本理论", Items.paper, 0, 21)
                            .setPos(1300, 140)
                            .addPrerequisite(computerSystemTheory)
                            .addTask(new ComputeTask(ComputePower.Normal, 4096))
                            .addTask(new MiniGameFillTask(32))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Steel,16)))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.Al,16)))
                            .addTask(new ItemConsumeTaskSimple(OP.stick.mat(MT.Steel,8)));

                    //CurrentControl:推力控制; 引擎零件+不锈钢壳体
                    ResearchProject rocketBasics = new ResearchProject(tree, "火箭基础", "研究火箭的气动外形，引擎推力的改进等基本内容", Items.paper, 0, 22)
                            .setPos(1400, 220)
                            .addPrerequisite(SpaceBasicTheory)
                            .addTask(new ComputeTask(ComputePower.Normal, 4096))
                            .addTask(new MiniGameCurrentControlTask(32))
                            .addTask(new ItemConsumeTaskSimple(ItemList.EngineCrankShaftManual1.get(1)))
                            .addTask(new ItemConsumeTaskSimple(ItemList.EngineCylinderManual1.get(1)))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.StainlessSteel,8)));

                    //Fill:空间站布局规划; 不锈钢结构+Al隔热+Glass密封管
                    ResearchProject spaceTheory = new ResearchProject(tree, "空间概论", "研究太空中如何进行航行和维持生命等", Items.paper, 0, 23)
                            .setPos(1480, 140)
                            .addPrerequisite(SpaceBasicTheory)
                            .addTask(new ComputeTask(ComputePower.Normal, 8192))
                            .addTask(new MiniGameFillTask(64))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.StainlessSteel,16)))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.Al,16)));

                    //CurrentControl:气体流量控制; Glass容器+Ar保护气+Steel容器
                    ResearchProject protectionUsage = new ResearchProject(tree, "保护气应用", "研究如何利用保护气制作纯度更高，性能更好的物品", Items.paper, 0, 24)
                            .setPos(1280, 70)
                            .addPrerequisite(computerSystemTheory)
                            .addTask(new MiniGameCurrentControlTask(16))
                            .addTask(new FluidConsumeTaskSimple(FL.Argon.fluid(), 8000))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.Steel,4)));

                    //CurrentControl+Fill:电路设计+布局; Si晶片+Au导线+PCB基板
                    ResearchProject computerT2 = new ResearchProject(tree, "入门计算机", "利用初代计算机的算力进一步优化电路设计，以提高算力制作下一代计算机", Items.paper, 0, 25)
                            .setPos(1380, 70)
                            .addPrerequisite(protectionUsage)
                            .addTask(new EnergyTask(TD.Energy.EU, 16384, 32))
                            .addTask(new ComputeTask(ComputePower.Normal, 8192))
                            .addTask(new MiniGameCurrentControlTask(64))
                            .addTask(new MiniGameFillTask(32))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Si,8)))
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.Au,32)))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Polycarbonate,4)));

                    //Fill:叶片形状规划; Ti耐热叶片+转子
                    ResearchProject aerodynamics = new ResearchProject(tree, "叶片气动力学", "研究各种形状的叶片流过流体时对气流和叶片的影响", Items.paper, 0, 26)
                            .setPos(1480, 0)
                            .addPrerequisite(computerT2)
                            .addTask(new EnergyTask(TD.Energy.RU, 8192, 16))
                            .addTask(new ComputeTask(ComputePower.Normal, 8192))
                            .addTask(new MiniGameFillTask(64))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Titanium,8)))
                            .addTask(new ItemConsumeTaskSimple(OP.rotor.mat(MT.Titanium,2)));

                    //Identify:辨别原子信号; U放射性源+Pb屏蔽+Au探测器
                    ResearchProject nuclearStructure = new ResearchProject(tree, "原子结构", "利用物质之间的反应初步确定分子，原子的结构，对不同原子的性质进行研究", Items.paper, 0, 27)
                            .setPos(1480, 70)
                            .addPrerequisite(computerT2)
                            .addTask(new EnergyTask(TD.Energy.EU, 16384, 16))
                            .addTask(new ComputeTask(ComputePower.Normal, 8192))
                            .addTask(new MiniGameIdentifyTask(16))
                            .addTask(new ItemConsumeTaskSimple(OP.dust.mat(MT.U_238,4)))
                            .addTask(new ItemConsumeTaskSimple(OP.dust.mat(MT.Pb,4)))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.Au,4)));

                    //Identify:辨别核反应; U235/U238/Th核燃料同位素
                    ResearchProject nuclearTheory = new ResearchProject(tree, "原子核理论", "通过研究原子核在各种情况下的状态，提出可能修改原子核的理论", Items.paper, 0, 28)
                            .setPos(1580, 70)
                            .addPrerequisite(nuclearStructure)
                            .addTask(new EnergyTask(TD.Energy.EU, 32768, 16))
                            .addTask(new ComputeTask(ComputePower.Normal, 16384))
                            .addTask(new MiniGameIdentifyTask(32))
                            .addTask(new ItemConsumeTaskSimple(OP.dust.mat(MT.U_235,4)))
                            .addTask(new ItemConsumeTaskSimple(OP.dust.mat(MT.U_238,4)))
                            .addTask(new ItemConsumeTaskSimple(OP.dust.mat(MT.Th,4)));

                    //CurrentControl+Fill:电路设计+布局; Si+Au+PCB
                    ResearchProject computerT3 = new ResearchProject(tree, "计算机T3", "解锁10系列计算机", Items.paper, 0, 29)
                            .setPos(1680, 70)
                            .addPrerequisite(nuclearTheory)
                            .addTask(new EnergyTask(TD.Energy.EU, 32768, 32))
                            .addTask(new ComputeTask(ComputePower.Normal, 32768))
                            .addTask(new MiniGameCurrentControlTask(64))
                            .addTask(new MiniGameFillTask(64))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Si,16)))
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.Au,64)))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Polycarbonate,8)));

                    //CurrentControl:链式反应控制; Cd控制棒+Pb屏蔽+U235燃料
                    ResearchProject fissionControl = new ResearchProject(tree, "裂变控制理论", "研究如何在宏观层面来监视和控制裂变反应", Items.paper, 0, 30)
                            .setPos(1780, 70)
                            .addPrerequisite(computerT3)
                            .addTask(new EnergyTask(TD.Energy.EU, 65536, 32))
                            .addTask(new ComputeTask(ComputePower.Normal, 32768))
                            .addTask(new MiniGameCurrentControlTask(96))
                            .addTask(new ItemConsumeTaskSimple(OP.stick.mat(MT.Cd,8)))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Pb,16)))
                            .addTask(new ItemConsumeTaskSimple(OP.dust.mat(MT.U_235,8)));

                    //Identify:辨别成像结果; Pb屏蔽+Co放射源+Glass透镜
                    ResearchProject rayPhoto = new ResearchProject(tree, "放射成像", "研究如何利用强穿透性的放射线对物体内部进行成像", Items.paper, 0, 31)
                            .setPos(1880, 70)
                            .addPrerequisite(fissionControl)
                            .addTask(new EnergyTask(TD.Energy.EU, 65536, 16))
                            .addTask(new ComputeTask(ComputePower.Normal, 32768))
                            .addTask(new MiniGameIdentifyTask(96))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Pb,16)))
                            .addTask(new ItemConsumeTaskSimple(OP.dust.mat(MT.Co,4)))
                            .addTask(new ItemConsumeTaskSimple(OP.lens.mat(MT.Glass,8)));

                    //Identify+CurrentControl:辨别微观结构+控制电子束; W灯丝+Glass透镜+Si样品
                    ResearchProject electronMicroscope = new ResearchProject(tree, "电子显微技术", "利用电子束对微观结构进行观察", Items.paper, 0, 32)
                            .setPos(1980, 70)
                            .addPrerequisite(rayPhoto)
                            .addTask(new EnergyTask(TD.Energy.EU, 131072, 16))
                            .addTask(new ComputeTask(ComputePower.Normal, 65536))
                            .addTask(new MiniGameIdentifyTask(128))
                            .addTask(new MiniGameCurrentControlTask(64))
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.W,32)))
                            .addTask(new ItemConsumeTaskSimple(OP.lens.mat(MT.Glass,8)))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Si,8)));

                    //CurrentControl+Fill+Identify:电路设计+布局+缺陷检测; Si+Au+PTFE绝缘
                    ResearchProject computerT4 = new ResearchProject(tree, "计算机T4", "解锁20, 36系列计算机", Items.paper, 0, 33)
                            .setPos(2080, 70)
                            .addPrerequisite(electronMicroscope)
                            .addTask(new EnergyTask(TD.Energy.EU, 131072, 32))
                            .addTask(new ComputeTask(ComputePower.Normal, 131072))
                            .addTask(new MiniGameCurrentControlTask(128))
                            .addTask(new MiniGameFillTask(128))
                            .addTask(new MiniGameIdentifyTask(32))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Si,32)))
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.Au,64)))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.PTFE,8)));

                    ResearchProject computerCluster = new ResearchProject(tree, "计算集群", "", Items.paper, 0, 33)
                            .setPos(2080, 70)//todo
                            .addPrerequisite(computerT4);

                    ResearchProject researchWithModel = new ResearchProject(tree, "数字建模研究", "通过对问题进行建模，依靠高算力进行仿真模拟研究", Items.paper, 0, 33)
                            .setPos(2080, 70)//todo
                            .addPrerequisite(computerT4);

                    ResearchProject catalyzerTheory = new ResearchProject(tree, "催化剂原理", "研究催化剂起作用的具体原理", Items.paper, 0, 34)
                            .setPos(2180, 70)
                            .addPrerequisite(researchWithModel)
                            .addTask(new ComputeTask(ComputePower.Normal, 131072))
                            .addTask(new MiniGameIdentifyTask(128))
                            .addTask(new ItemConsumeTaskSimple(OP.dust.mat(MT.Pt,4)))
                            .addTask(new ItemConsumeTaskSimple(OP.dust.mat(MT.Pd,4)))
                            .addTask(new ItemConsumeTaskSimple(OP.dust.mat(MT.Ni,8)));

                    //Fill:聚合物结构规划; PC工程塑料+PTFE含氟塑料+环氧纤维增强
                    ResearchProject advancedPlastic = new ResearchProject(tree, "高级塑料", "", Items.paper, 0, 35)
                            .setPos(2280, 70)
                            .addPrerequisite(catalyzerTheory)
                            .addTask(new ComputeTask(ComputePower.Normal, 131072))
                            .addTask(new MiniGameFillTask(128))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Polycarbonate,16)))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.PTFE,16)))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(matList.EpoxyResin.mat, 4)));

                    //CurrentControl+Fill+Identify:FinFET工艺; Si+Au+PTFE
                    ResearchProject computerT5 = new ResearchProject(tree, "计算机T5", "利用finfet工艺解锁36v2系列计算机", Items.paper, 0, 36)
                            .setPos(2380, 70)
                            .addPrerequisite(advancedPlastic)
                            .addTask(new EnergyTask(TD.Energy.EU, 262144, 32))
                            .addTask(new ComputeTask(ComputePower.Normal, 262144))
                            .addTask(new MiniGameCurrentControlTask(256))
                            .addTask(new MiniGameFillTask(192))
                            .addTask(new MiniGameIdentifyTask(64))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Si,64)))
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.Au,64)))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.PTFE,16)));

                    //Fill+CurrentControl:空间站布局+系统控制; Ti结构+不锈钢壳体+Ti齿轮
                    ResearchProject spaceStationTheory = new ResearchProject(tree, "空间站理论", "研究如何构建稳定运行的空间站", Items.paper, 0, 37)
                            .setPos(1580, 140)
                            .addPrerequisite(spaceTheory)
                            .addPrerequisite(computerT2)
                            .addTask(new ComputeTask(ComputePower.Normal, 32768))
                            .addTask(new MiniGameFillTask(64))
                            .addTask(new MiniGameCurrentControlTask(32))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Titanium,32)))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.StainlessSteel,32)))
                            .addTask(new ItemConsumeTaskSimple(OP.gear.mat(MT.Titanium,8)));

                    //Fill:微重力实验规划; Ti容器+Al轻质
                    ResearchProject lowGravityUsage = new ResearchProject(tree, "微重力应用", "研究微重力环境可能的用途", Items.paper, 0, 38)
                            .setPos(1780, 140)
                            .addPrerequisite(spaceStationTheory)
                            .addTask(new ComputeTask(ComputePower.Normal, 65536))
                            .addTask(new MiniGameFillTask(96))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Titanium,16)))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.Al,32)));

                    //Identify:辨别辐射信号; Pb屏蔽+钨钢结构+U放射源
                    ResearchProject universeRadio = new ResearchProject(tree, "宇宙辐射研究", "研究宇宙辐射和其对物体可能的用途", Items.paper, 0, 39)
                            .setPos(1980, 140)
                            .addPrerequisite(lowGravityUsage)
                            .addTask(new ComputeTask(ComputePower.Normal, 131072))
                            .addTask(new MiniGameIdentifyTask(128))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Lead,32)))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.TungstenSteel,8)))
                            .addTask(new ItemConsumeTaskSimple(OP.dust.mat(MT.U_238,8)));

                    //CurrentControl+Fill+Identify:微重力芯片工艺; Si+Au+PTFE
                    ResearchProject computerT6 = new ResearchProject(tree, "计算机T6", "微重力环境解锁36v3系列计算机", Items.paper, 0, 40)
                            .setPos(2480, 70)
                            .addPrerequisite(computerT5)
                            .addPrerequisite(universeRadio)
                            .addTask(new EnergyTask(TD.Energy.EU, 524288, 32))
                            .addTask(new ComputeTask(ComputePower.Normal, 524288))
                            .addTask(new MiniGameCurrentControlTask(384))
                            .addTask(new MiniGameFillTask(320))
                            .addTask(new MiniGameIdentifyTask(192))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Si,128)))
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.Au,128)))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.PTFE,32)));


                    ResearchProject fusionTokamakExp = new ResearchProject(tree, "托卡马克聚变", "实验性的托卡马克聚变堆", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(computerT6);


                    ResearchProject fusionTokamak = new ResearchProject(tree, "商用托卡马克", "托卡马克聚变堆", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(computerT6);

                    ResearchProject fusionLaser = new ResearchProject(tree, "激光聚变", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(computerT6);


                    ResearchProject naqudahTheory = new ResearchProject(tree, "硅岩性质理论", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(fusionLaser);


                    ResearchProject computerT7 = new ResearchProject(tree, "计算机T7", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(naqudahTheory);

                    ResearchProject AIResearch = new ResearchProject(tree, "人工智能研究", "制造人工智能，并借用人工智能来辅助科学研究", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(computerT7);


                    ResearchProject todo001 = new ResearchProject(tree, "镜金性质", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(computerT7);


                    ResearchProject todo002 = new ResearchProject(tree, "量子通信", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(todo001);

                    ResearchProject todo003 = new ResearchProject(tree, "镜金量子化", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(todo001);

                    ResearchProject todo004 = new ResearchProject(tree, "量金性质", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(todo003);

                    ResearchProject todo005 = new ResearchProject(tree, "量子观测", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(todo004);

                    ResearchProject todo006 = new ResearchProject(tree, "量子有机生产", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(todo004);

                    ResearchProject todo007 = new ResearchProject(tree, "量子元素制造", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(todo004);

                    ResearchProject todo008 = new ResearchProject(tree, "量子计算T1", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(todo005);

                    ResearchProject todo009 = new ResearchProject(tree, "跃迁理论", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(todo008);

                    ResearchProject todo010 = new ResearchProject(tree, "量子结构理论", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(todo008);

                    ResearchProject todo011 = new ResearchProject(tree, "刻金性质", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(todo010);

                    ResearchProject todo012 = new ResearchProject(tree, "量子计算微型化", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(todo011);

                    ResearchProject todo013 = new ResearchProject(tree, "强力理论", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(todo011);

                    ResearchProject todo014 = new ResearchProject(tree, "强力物质生产", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(todo013);

                    ResearchProject todo015 = new ResearchProject(tree, "零点场论", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(todo013);

                    ResearchProject todo016 = new ResearchProject(tree, "零点能量生成", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(todo015);

                    ResearchProject todo017 = new ResearchProject(tree, "物质能量转换", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(todo016);

                    ResearchProject todo018 = new ResearchProject(tree, "科魔统一理论", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(todo017);

                    ResearchProject todo019 = new ResearchProject(tree, "无尽", "", Items.paper, 0, 40)
                            .setPos(2480, 70)//todo
                            .addPrerequisite(todo018);
                    return tree;
        }
        );
        ResearchTree tree = new ResearchTree();
        tree.applyTemplate((byte)0);
        exampleTrees.add(tree);
    }
}
