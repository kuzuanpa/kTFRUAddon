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

import cn.kuzuanpa.ktfruaddon.api.research.ResearchProject;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchTree;
import cn.kuzuanpa.ktfruaddon.api.research.task.ItemConsumeTaskSimple;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.util.ST;
import net.minecraft.init.Items;
import zmaster587.advancedRocketry.api.AdvancedRocketryItems;

public class ResearchTrees {
    public static void init() {
        ResearchTree.ResearchTreeTemplate.put((byte) 0, tree -> {

                    tree.rootItem = new ResearchProject(tree, "计算学", "算力的提升是万物的基础", -1);

                    ResearchProject a = new ResearchProject(tree, "芯片基础", "在经过了一系列磨难后，你终于在群峦星获得了安身之地。现在，你需要根据你的记忆和想象力，将巨大的电子管电路修改为硅基的集成电路.", AdvancedRocketryItems.itemIC, 0, 1)
                            .setPos(60, 130)
                            .addPrerequisite(tree.rootItem)
                            .addTask(new ItemConsumeTaskSimple(ST.make(Items.iron_ingot, 32, 0)));

                    ResearchProject b = new ResearchProject(tree, "投影", "你需要探索光学成像的原理，设计基础投影设备，来将你对机器的构想投射到世界中", AdvancedRocketryItems.itemSatellitePrimaryFunction, 0, 2)
                            .setPos(80, 20)
                            .addPrerequisite(tree.rootItem);

                    ResearchProject c = new ResearchProject(tree, "硅理论", "利用最初的芯片辅助你研究硅的特性，了解它的各项特性在芯片制造中的关键作用", Items.paper, 0, 3)
                            .setPos(180, 10)
                            .addPrerequisite(a);

                    ResearchProject d = new ResearchProject(tree, "结晶器", "分析晶体生长过程，思考如何获得整齐排布的分子晶体结构", OP.bouleGt.mat(MT.Si, 0).getItem(), MT.Si.mID, 4)
                            .setPos(180, 130)
                            .addPrerequisite(c);

                    ResearchProject f = new ResearchProject(tree, "基础电路设计", "利用计算器进一步改进电路，你认为你离真正的自动化控制不远了", Items.paper, 0, 5)
                            .setPos(340, 70)
                            .addPrerequisite(c).addPrerequisite(d);

                    ResearchProject g = new ResearchProject(tree, "电弧处理", "你的记忆中总能见到电弧，但如何利用它而不损坏材料，需要你进一步研究", Items.paper, 0, 6)
                            .setPos(340, 140)
                            .addPrerequisite(f);

                    ResearchProject h = new ResearchProject(tree, "高压容器", "许多处理需要高压环境，通过研究常见材料在高压下的变化来制造耐压容器", Items.paper, 0, 7)
                            .setPos(340, 0)
                            .addPrerequisite(f);

                    ResearchProject i = new ResearchProject(tree, "电磁感应", "你早已听闻电磁感应定律，只需要稍微总结一套方便的规律和程序即可制造大量的实用物品", Items.paper, 0, 8)
                            .setPos(440, 0)
                            .addPrerequisite(f);

                    ResearchProject j = new ResearchProject(tree, "电池原理", "利用已知的电解原理进行电力储存", Items.paper, 0, 9)
                            .setPos(440, 140)
                            .addPrerequisite(f);

                    ResearchProject k = new ResearchProject(tree, "半导体制冷", "探索半导体通过电流时的热学行为, 制作半导体制冷器", Items.paper, 0, 10)
                            .setPos(380, 220)
                            .addPrerequisite(f);

                    ResearchProject l = new ResearchProject(tree, "激光", "研究如何利用电力激发二氧化碳产生激光，激光可将能量集中于极小的一点，非常适合精确加工", Items.paper, 0, 11)
                            .setPos(440, 70)
                            .addPrerequisite(f);

                    ResearchProject m = new ResearchProject(tree, "精确制造", "研究如何利用激光极度精确集中的能量加工物品", Items.paper, 0, 12)
                            .setPos(540, 70)
                            .addPrerequisite(l);

                    ResearchProject n = new ResearchProject(tree, "单晶硅制造", "研究如何制作原子排列规整的单晶硅", Items.paper, 0, 13)
                            .setPos(640, 70)
                            .addPrerequisite(m);

                    ResearchProject o = new ResearchProject(tree, "低级电路设计", "利用性能更好的硅晶片制作算力更强的芯片", Items.paper, 0, 14)
                            .setPos(840, 70)
                            .addPrerequisite(n);

                    ResearchProject p = new ResearchProject(tree, "赛特斯石英应用", "研究异世界特有的独特晶体-赛特斯, 对赛特斯压缩物体的能力进行探索", Items.paper, 0, 15)
                            .setPos(840, 140)
                            .addPrerequisite(o);

                    ResearchProject q = new ResearchProject(tree, "智能赛特斯", "研究更为罕见的智金, 对其指导赛特斯流动的能力进行控制和规律利用", Items.paper, 0, 16)
                            .setPos(840, 220)
                            .addPrerequisite(p);

                    ResearchProject r = new ResearchProject(tree, "高压发电", "研究如何利用更高算力的电路来控制更高电压的发电机", Items.paper, 0, 17)
                            .setPos(940, 70)
                            .addPrerequisite(o);

                    ResearchProject s = new ResearchProject(tree, "光刻理论", "普通的方法已经达到极限, 你需要研究光刻及相关的设备以制造更精密的芯片", Items.paper, 0, 18)
                            .setPos(1040, 70)
                            .addPrerequisite(r);

                    ResearchProject t = new ResearchProject(tree, "计算机系统", "设计一套合理的输入输出标准, 以组装出完整的计算机", Items.paper, 0, 19)
                            .setPos(1140, 70)
                            .addPrerequisite(s);

                    ResearchProject u = new ResearchProject(tree, "数控车床", "通过计算机自动化精确控制电机制作数控车床", Items.paper, 0, 20)
                            .setPos(1140, 140)
                            .addPrerequisite(t);

                    ResearchProject v = new ResearchProject(tree, "太空基础理论", "火箭与太空探索所需的基本理论", Items.paper, 0, 21)
                            .setPos(1140, 0)
                            .addPrerequisite(t);

                    ResearchProject w = new ResearchProject(tree, "火箭基础", "研究火箭的气动外形，引擎推力的改进等基本内容", Items.paper, 0, 22)
                            .setPos(1240, -30)
                            .addPrerequisite(v);

                    ResearchProject x = new ResearchProject(tree, "空间概论", "研究太空中如何进行航行和维持生命等", Items.paper, 0, 23)
                            .setPos(1140, -30)
                            .addPrerequisite(v);

                    ResearchProject y = new ResearchProject(tree, "保护气应用", "研究如何利用保护气制作纯度更高，性能更好的物品", Items.paper, 0, 24)
                            .setPos(1240, 70)
                            .addPrerequisite(t);

                    ResearchProject z = new ResearchProject(tree, "入门计算机", "利用初代计算机的算力进一步优化电路设计，以提高算力制作下一代计算机", Items.paper, 0, 25)
                            .setPos(1340, 70)
                            .addPrerequisite(y);

                    ResearchProject aa = new ResearchProject(tree, "原子结构", "利用物质之间的反应初步确定分子，原子的结构，对不同原子的性质进行研究", Items.paper, 0, 26)
                            .setPos(1440, 70)
                            .addPrerequisite(z);

                    ResearchProject ab = new ResearchProject(tree, "原子核理论", "通过研究原子核在各种情况下的状态，提出可能实现裂变或聚变的理论", Items.paper, 0, 27)
                            .setPos(1540, 70)
                            .addPrerequisite(aa);

                    ResearchProject ac = new ResearchProject(tree, "裂变控制理论", "研究如何在宏观层面来监视和控制裂变反应", Items.paper, 0, 28)
                            .setPos(1640, 0)
                            .addPrerequisite(ab);

                    ResearchProject ad = new ResearchProject(tree, "放射成像", "研究如何利用强穿透性的放射线对物体内部进行成像", Items.paper, 0, 29)
                            .setPos(1540, 70)
                            .addPrerequisite(aa);

                    ResearchProject ae = new ResearchProject(tree, "叶片气动力学", "研究各种形状的叶片流过流体时对气流和叶片的影响", Items.paper, 0, 30)
                            .setPos(1340, 140)
                            .addPrerequisite(z);

                    ResearchProject ba = new ResearchProject(tree, "聚变材料", "研究能够允许内部物质发生聚变时保持结构强度的材料", Items.paper, 0, 40)
                            .setPos(1540, 140)
                            .addPrerequisite(ab);

                    return tree;
        }
        );
    }
}
