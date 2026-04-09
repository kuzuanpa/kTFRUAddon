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

import cn.kuzuanpa.ktfruaddon.api.code.SingleItemStack;
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

                    tree.rootItem = new ResearchProject(tree, "科学", "万物的基础", -1).setPos(-5,70);

                    ResearchProject circuitBasic = new ResearchProject(tree, "芯片基础", "在经过了一系列磨难后，你终于在群峦星获得了安身之地。现在，你需要根据你的记忆和想象力，将巨大的电子管电路修改为硅基的集成电路.", AdvancedRocketryItems.itemIC, 0, 1)
                            .setPos(80, 70)
                            .addPrerequisite(tree.rootItem)
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.Cu,32)))
                            .addUnlockItem(new SingleItemStack(ST.make(MD.GC_ADV_ROCKETRY, "circuitIC", 1,3)))
                            .addUnlockItem(new SingleItemStack(ST.make(MD.GC_ADV_ROCKETRY, "circuitIC", 1,4)))
                            .addUnlockItem(new SingleItemStack(ST.make(MD.GC_ADV_ROCKETRY, "circuitIC", 1,5)));

                    ResearchProject projector = new ResearchProject(tree, "投影", "在光下探索光学成像的原理，设计基础投影设备，来将你对机器的构想投射到世界中", AdvancedRocketryItems.itemSatellitePrimaryFunction, 0, 2)
                            .setPos(80, 0)
                            .addPrerequisite(tree.rootItem)
                            .addTask(new ItemConsumeTaskSimple(ST.make(Blocks.glass_pane, 8, 0)))
                            .addUnlockItem(new SingleItemStack(ST.make(MD.VULPES, "item.holoProjector", 1)));

                    ResearchProject siliconBasic = new ResearchProject(tree, "硅理论", "利用最初的芯片辅助你研究硅的特性，了解它的各项特性在芯片制造中的关键作用", Items.paper, 0, 3)
                            .setPos(180, 70)
                            .addPrerequisite(circuitBasic)
                            .addTask(new ItemConsumeTaskSimple(OP.plateTiny.mat(MT.Si,24)));

                    ResearchProject crystallizer = new ResearchProject(tree, "结晶器", "分析晶体生长过程，思考如何获得整齐排布的分子晶体结构", OP.bouleGt.mat(MT.Si, 0).getItem(), MT.Si.mID, 4)
                            .setPos(280, 70)
                            .addPrerequisite(siliconBasic)
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.Cupronickel,32)))
                            .addTask(new ItemConsumeTaskSimple(OP.gem.mat(MT.NetherQuartz,8)))
                            .addUnlockItem(new SingleItemStack(ST.make(MD.GC_ADV_ROCKETRY, "crystallizer", 1)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(20251)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(20252)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(20253)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(20254)));

                    ResearchProject circuitDesignT1 = new ResearchProject(tree, "基础电路设计", "利用计算器进一步改进电路，你认为你离真正的自动化控制不远了", Items.paper, 0, 5)
                            .setPos(380, 70)
                            .addPrerequisite(crystallizer)
                            .addTask(new ItemConsumeTaskSimple(ST.make(MD.GC_ADV_ROCKETRY, "circuitIC", 2,3)))
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.Cu,32)))
                            .addTask(new ItemConsumeTaskSimple(OP.plateTiny.mat(MT.Si,32)))
                            .addUnlockItem(new SingleItemStack(IL.Circuit_Basic.get(1)));

                    ResearchProject lightningProcess = new ResearchProject(tree, "电弧处理", "你的记忆中总能见到电弧，但如何利用它而不损坏材料，需要你进一步研究", Items.paper, 0, 6)
                            .setPos(500, 220)
                            .addPrerequisite(circuitDesignT1)
                            .addTask(new EnergyTask(TD.Energy.EU, 16384, 16))
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.Cu,2)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(20501)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(20502)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(20503)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(20504)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(20504)));

                    ResearchProject pressureContainer = new ResearchProject(tree, "高压容器", "许多处理需要高压环境，通过研究常见材料在高压下的变化来制造耐压容器", Items.paper, 0, 7)
                            .setPos(560, 140)
                            .addPrerequisite(circuitDesignT1)
                            .addTask(new EnergyTask(TD.Energy.RU, 4096, 16))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.Steel,2)))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.Bronze,2)))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.StainlessSteel,2)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(22004)));

                    ResearchProject electromagneticInduction = new ResearchProject(tree, "电磁感应", "你早已听闻电磁感应定律，只需要稍微总结一套方便的规律和程序即可制造大量的实用物品", Items.paper, 0, 8)
                            .setPos(460, 0)
                            .addPrerequisite(circuitDesignT1)
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.Cu,4)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(10031)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(10032)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(10033)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(10034)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(10035)));

                    ResearchProject batteryBasic = new ResearchProject(tree, "电池原理", "利用已知的电解原理进行电力储存", Items.paper, 0, 9)
                            .setPos(460, 140)
                            .addPrerequisite(circuitDesignT1)
                            .addTask(new ItemConsumeTaskSimple(OP.stick.mat(MT.Pb,2)))
                            .addTask(new ItemConsumeTaskSimple(OP.stick.mat(MT.Au,2)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(14000)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(14001)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(14002)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(14003)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(14004)));

                    ResearchProject semiconductorCooling = new ResearchProject(tree, "半导体制冷", "探索半导体通过电流时的热学行为, 制作半导体制冷器", Items.paper, 0, 10)
                            .setPos(400, 220)
                            .addPrerequisite(circuitDesignT1)
                            .addTask(new EnergyTask(TD.Energy.EU, 4096, 16))
                            .addTask(new ItemConsumeTaskSimple(OP.plate.mat(MT.Si,2)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(10161)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(10162)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(10163)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(10164)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(10165)));

                    ResearchProject laserBasic = new ResearchProject(tree, "激光基础", "研究如何利用电力激发二氧化碳产生激光，激光可将能量集中于极小的一点，非常适合精确加工", Items.paper, 0, 11)
                            .setPos(480, 70)
                            .addPrerequisite(circuitDesignT1)
                            .addTask(new EnergyTask(TD.Energy.EU, 16384, 32))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.Sn,2)))
                            .addTask(new FluidConsumeTaskSimple(FL.CarbonDioxide.fluid(), 1000))
                            .addUnlockItem(new SingleItemStack(IL.Comp_Laser_Gas_Empty.get(1)));

                    ResearchProject laserCompose = new ResearchProject(tree, "精确激光控制", "研究如何精确控制激光的能量来加工物品", Items.paper, 0, 12)
                            .setPos(580, 70)
                            .addPrerequisite(laserBasic)
                            .addTask(new EnergyTask(TD.Energy.LU, 4096, 16))
                            .addTask(new MiniGameCurrentControlTask(8))
                            .addTask(new ItemConsumeTaskSimple(OP.foil.mat(MT.Sn,8)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(20231)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(20232)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(20233)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(20234)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(20235)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.ktfruaddon.getItem(23000)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.ktfruaddon.getItem(23001)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.ktfruaddon.getItem(23002)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.ktfruaddon.getItem(23003)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.ktfruaddon.getItem(23004)));

                    ResearchProject bouleMaking = new ResearchProject(tree, "单晶硅制造", "研究如何制作原子排列规整的单晶硅", Items.paper, 0, 13)
                            .setPos(680, 70)
                            .addPrerequisite(laserCompose)
                            .addTask(new EnergyTask(TD.Energy.LU, 4096, 16))
                            .addTask(new FluidConsumeTaskSimple(MT.Si.mLiquid.getFluid(), 1440))
                            .addTask(new ItemConsumeTaskSimple(OP.dustDiv72.mat(MT.Si,1)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(20251)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(20252)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(20253)))
                            .addUnlockItem(new SingleItemStack(GTTileEntityRegistry.gregtech.getItem(20254)));

                    ResearchProject circuitDesignT2 = new ResearchProject(tree, "低级电路设计", "利用性能更好的硅晶片制作算力更强的芯片", Items.paper, 0, 14)
                            .setPos(880, 70)
                            .addPrerequisite(bouleMaking)
                            .addTask(new MiniGameCurrentControlTask(16))
                            .addTask(new MiniGameFillTask(16))
                            .addTask(new ComputeTask(ComputePower.Normal, 512))
                            .addTask(new ItemConsumeTaskSimple(OP.plateTiny.mat(MT.Si,4)))
                            .addTask(new ItemConsumeTaskSimple(OP.wireFine.mat(MT.Cu,16)))
                            .addTask(new ItemConsumeTaskSimple(ST.make(Items.paper, 16,0)))
                            .addUnlockItem(new SingleItemStack(ItemList.GoodCircuitPartCore.get(1)));

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
                            .addUnlockItem(new SingleItemStack(ST.make(MD.AE, "item.ItemMultiMaterial", 1, 22)))
                            .addUnlockItem(new SingleItemStack(ST.make(MD.AE, "item.ItemMultiMaterial", 1, 23)))
                            .addUnlockItem(new SingleItemStack(ST.make(MD.AE, "item.ItemMultiMaterial", 1, 24)));

                    ResearchProject certusIntelligence = new ResearchProject(tree, "智能赛特斯", "研究更为罕见的智金, 对其指导赛特斯流动的能力进行控制和规律利用", Items.paper, 0, 16)
                            .setPos(1100, 220)
                            .addPrerequisite(certusUsage)
                            .addTask(new MiniGameCurrentControlTask(64))
                            .addTask(new MiniGameIdentifyTask(64))
                            .addTask(new ComputeTask(ComputePower.Normal, 163840))
                            .addTask(new ItemConsumeTaskSimple(OP.plateGem.mat(MT.CertusQuartz,64)))
                            .addTask(new ItemConsumeTaskSimple(OP.nugget.mat(matList.Ij.mat,16)))
                            .addUnlockItem(new SingleItemStack(ItemList.IntelligentCore.get(1)));

                    ResearchProject highVoltageBasics = new ResearchProject(tree, "高压发电", "研究如何利用更高算力的电路来控制更高电压的发电机", Items.paper, 0, 17)
                            .setPos(980, 70)
                            .addPrerequisite(circuitDesignT2);

                    ResearchProject maskAlignTheory = new ResearchProject(tree, "光刻理论", "普通的方法已经达到极限, 你需要研究光刻及相关的设备以制造更精密的芯片", Items.paper, 0, 18)
                            .setPos(1080, 70)
                            .addPrerequisite(highVoltageBasics);

                    ResearchProject computerSystemTheory = new ResearchProject(tree, "计算机系统", "设计一套合理的输入输出标准, 以组装出完整的计算机", Items.paper, 0, 19)
                            .setPos(1180, 70)
                            .addPrerequisite(maskAlignTheory);

                    ResearchProject CNCLathe = new ResearchProject(tree, "数控车床", "通过计算机自动化精确控制电机制作数控车床", Items.paper, 0, 20)
                            .setPos(1300, 0)
                            .addPrerequisite(computerSystemTheory);

                    ResearchProject SpaceBasicTheory = new ResearchProject(tree, "太空基础理论", "火箭与太空探索所需的基本理论", Items.paper, 0, 21)
                            .setPos(1300, 140)
                            .addPrerequisite(computerSystemTheory);

                    ResearchProject rocketBasics = new ResearchProject(tree, "火箭基础", "研究火箭的气动外形，引擎推力的改进等基本内容", Items.paper, 0, 22)
                            .setPos(1400, 220)
                            .addPrerequisite(SpaceBasicTheory);

                    ResearchProject spaceTheory = new ResearchProject(tree, "空间概论", "研究太空中如何进行航行和维持生命等", Items.paper, 0, 23)
                            .setPos(1480, 140)
                            .addPrerequisite(SpaceBasicTheory);

                    ResearchProject protectionUsage = new ResearchProject(tree, "保护气应用", "研究如何利用保护气制作纯度更高，性能更好的物品", Items.paper, 0, 24)
                            .setPos(1280, 70)
                            .addPrerequisite(computerSystemTheory);

                    ResearchProject computerT2 = new ResearchProject(tree, "入门计算机", "利用初代计算机的算力进一步优化电路设计，以提高算力制作下一代计算机", Items.paper, 0, 25)
                            .setPos(1380, 70)
                            .addPrerequisite(protectionUsage);

                    ResearchProject aerodynamics = new ResearchProject(tree, "叶片气动力学", "研究各种形状的叶片流过流体时对气流和叶片的影响", Items.paper, 0, 26)
                            .setPos(1480, 0)
                            .addPrerequisite(computerT2);

                    ResearchProject nuclearStructure = new ResearchProject(tree, "原子结构", "利用物质之间的反应初步确定分子，原子的结构，对不同原子的性质进行研究", Items.paper, 0, 27)
                            .setPos(1480, 70)
                            .addPrerequisite(computerT2);

                    ResearchProject nuclearTheory = new ResearchProject(tree, "原子核理论", "通过研究原子核在各种情况下的状态，提出可能修改原子核的理论", Items.paper, 0, 28)
                            .setPos(1580, 70)
                            .addPrerequisite(nuclearStructure);

                    ResearchProject computerT3 = new ResearchProject(tree, "计算机T3", "解锁10系列计算机", Items.paper, 0, 29)
                            .setPos(1680, 70)
                            .addPrerequisite(nuclearTheory);

                    ResearchProject fissionControl = new ResearchProject(tree, "裂变控制理论", "研究如何在宏观层面来监视和控制裂变反应", Items.paper, 0, 30)
                            .setPos(1780, 70)
                            .addPrerequisite(computerT3);

                    ResearchProject rayPhoto = new ResearchProject(tree, "放射成像", "研究如何利用强穿透性的放射线对物体内部进行成像", Items.paper, 0, 31)
                            .setPos(1880, 70)
                            .addPrerequisite(fissionControl);

                    ResearchProject electronMicroscope = new ResearchProject(tree, "电子显微技术", "利用电子束对微观结构进行观察", Items.paper, 0, 32)
                            .setPos(1980, 70)
                            .addPrerequisite(rayPhoto);

                    ResearchProject computerT4 = new ResearchProject(tree, "计算机T4", "解锁20, 36系列计算机", Items.paper, 0, 33)
                            .setPos(2080, 70)
                            .addPrerequisite(electronMicroscope);

                    ResearchProject catalyzerTheory = new ResearchProject(tree, "催化剂原理", "研究催化剂起作用的具体原理", Items.paper, 0, 34)
                            .setPos(2180, 70)
                            .addPrerequisite(computerT4);

                    ResearchProject advancedPlastic = new ResearchProject(tree, "高级塑料", "", Items.paper, 0, 35)
                            .setPos(2280, 70)
                            .addPrerequisite(catalyzerTheory);

                    ResearchProject computerT5 = new ResearchProject(tree, "计算机T5", "利用finfet工艺解锁36v2系列计算机", Items.paper, 0, 36)
                            .setPos(2380, 70)
                            .addPrerequisite(advancedPlastic);

                    ResearchProject spaceStationTheory = new ResearchProject(tree, "空间站理论", "研究如何构建稳定运行的空间站", Items.paper, 0, 37)
                            .setPos(1580, 140)
                            .addPrerequisite(spaceTheory)
                            .addPrerequisite(computerT2);

                    ResearchProject lowGravityUsage = new ResearchProject(tree, "微重力应用", "研究微重力环境可能的用途", Items.paper, 0, 38)
                            .setPos(1780, 140)
                            .addPrerequisite(spaceStationTheory);

                    ResearchProject universeRadio = new ResearchProject(tree, "宇宙辐射研究", "研究宇宙辐射和其对物体可能的用途", Items.paper, 0, 39)
                            .setPos(1980, 140)
                            .addPrerequisite(lowGravityUsage);

                    ResearchProject computerT6 = new ResearchProject(tree, "计算机T6", "微重力环境解锁36v3系列计算机", Items.paper, 0, 40)
                            .setPos(2480, 70)
                            .addPrerequisite(computerT5)
                            .addPrerequisite(universeRadio);

                    ResearchProject naquadahTheory = new ResearchProject(tree, "硅岩性质研究", "", Items.paper, 0, 41)
                            .setPos(2580, 70)
                            .addPrerequisite(computerT6);

                    ResearchProject mirroiteUsage = new ResearchProject(tree, "镜金应用", "", Items.paper, 0, 42)
                            .setPos(2680, 70)
                            .addPrerequisite(naquadahTheory);

                    ResearchProject computerT7 = new ResearchProject(tree, "计算机T7", "逻辑计算的绝唱, 解锁36v4系列计算机", Items.paper, 0, 43)
                            .setPos(2780, 70)
                            .addPrerequisite(mirroiteUsage);

                    ResearchProject fusionTheory = new ResearchProject(tree, "聚变基础理论", "", Items.paper, 0, 44)
                            .setPos(1780, 0)
                            .addPrerequisite(nuclearTheory);

                    ResearchProject bb = new ResearchProject(tree, "磁约束理论", "", Items.paper, 0, 45)
                            .setPos(1880, 0)
                            .addPrerequisite(fusionTheory);

                    ResearchProject bc = new ResearchProject(tree, "数据汇总-磁", "", Items.paper, 0, 46)
                            .setPos(1980, 0)
                            .addPrerequisite(bb);

                    ResearchProject bd = new ResearchProject(tree, "商用磁约束", "", Items.paper, 0, 47)
                            .setPos(2080, 0)
                            .addPrerequisite(bb);

                    ResearchProject be = new ResearchProject(tree, "惯性约束理论", "", Items.paper, 0, 48)
                            .setPos(1880, -70)
                            .addPrerequisite(fusionTheory);

                    ResearchProject bf = new ResearchProject(tree, "数据汇总-惯性", "", Items.paper, 0, 49)
                            .setPos(1980, -70)
                            .addPrerequisite(be);

                    ResearchProject bg = new ResearchProject(tree, "商用惯性约束", "", Items.paper, 0, 50)
                            .setPos(2080, -70)
                            .addPrerequisite(bf);

                    ResearchProject quantumizeMirroite = new ResearchProject(tree, "量子化镜金", "", Items.paper, 0, 51)
                            .setPos(2880, 70)
                            .addPrerequisite(bg)
                            .addPrerequisite(computerT7);

                    ResearchProject quantumiteUsage = new ResearchProject(tree, "量金性质应用", "", Items.paper, 0, 52)
                            .setPos(2980, 70)
                            .addPrerequisite(quantumizeMirroite);

                    ResearchProject quantumObserve = new ResearchProject(tree, "量子观测", "", Items.paper, 0, 53)
                            .setPos(3080, 70)
                            .addPrerequisite(quantumiteUsage);

                    ResearchProject quantumProcess = new ResearchProject(tree, "量子处理", "", Items.paper, 0, 54)
                            .setPos(3180, 70)
                            .addPrerequisite(quantumObserve);

                    ResearchProject quantumComputer = new ResearchProject(tree, "量子计算", "", Items.paper, 0, 55)
                            .setPos(3280, 70)
                            .addPrerequisite(quantumProcess);

                    ResearchProject ar = new ResearchProject(tree, "跃迁理论", "", Items.paper, 0, 56)
                            .setPos(3380, 140)
                            .addPrerequisite(quantumComputer);

                    ResearchProject biologicalQuantumProcess = new ResearchProject(tree, "有机量子处理", "", Items.paper, 0, 57)
                            .setPos(3380, 0)
                            .addPrerequisite(quantumComputer);

                    ResearchProject quantumStructure = new ResearchProject(tree, "量子结构理论", "研究夸克", Items.paper, 0, 58)
                            .setPos(3380, 70)
                            .addPrerequisite(quantumComputer);

                    ResearchProject au = new ResearchProject(tree, "刻金性质应用", "", Items.paper, 0, 59)
                            .setPos(3480, 70)
                            .addPrerequisite(quantumStructure);

                    ResearchProject microQuantumComputer = new ResearchProject(tree, "量子计算微型化", "", Items.paper, 0, 60)
                            .setPos(3580, 70)
                            .addPrerequisite(au);

                    ResearchProject av = new ResearchProject(tree, "强力物质", "", Items.paper, 0, 61)
                            .setPos(3680, 70)
                            .addPrerequisite(microQuantumComputer);

                    ResearchProject aw = new ResearchProject(tree, "虚空理论", "", Items.paper, 0, 62)
                            .setPos(3780, 70)
                            .addPrerequisite(av);

                    ResearchProject ax = new ResearchProject(tree, "零点场论", "", Items.paper, 0, 63)
                            .setPos(3880, 70)
                            .addPrerequisite(aw);

                    ResearchProject ay = new ResearchProject(tree, "零点能量生成", "", Items.paper, 0, 64)
                            .setPos(3980, 70)
                            .addPrerequisite(ax);

                    ResearchProject az = new ResearchProject(tree, "零点物质生成", "", Items.paper, 0, 65)
                            .setPos(4080, 70)
                            .addPrerequisite(ay);

                    ResearchProject ca = new ResearchProject(tree, "科魔统一理论", "", Items.paper, 0, 66)
                            .setPos(4180, 70)
                            .addPrerequisite(az);

                    ResearchProject cb = new ResearchProject(tree, "无尽", "", Items.paper, 0, 67)
                            .setPos(4280, 70)
                            .addPrerequisite(ca);

                    return tree;
        }
        );
        ResearchTree tree = new ResearchTree();
        tree.applyTemplate((byte)0);
        exampleTrees.add(tree);
    }
}
