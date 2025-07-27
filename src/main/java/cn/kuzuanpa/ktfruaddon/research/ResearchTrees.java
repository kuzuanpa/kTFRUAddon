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
    public static ResearchTree main = new ResearchTree((byte) 0);
    static {

        main.rootItem = new ResearchProject(main,"计算学","算力的提升是万物的基础", -1);

        ResearchProject a = new ResearchProject(main, "芯片基础", "在经过了一系列磨难后，你终于在群峦星获得了安身之地。现在，你需要根据你的记忆和想象力，将巨大的电子管电路修改为硅基的集成电路.", AdvancedRocketryItems.itemIC, 0, 1)
                .setPos(60,130)
                .addPrerequisite(main.rootItem)
                .addTask(new ItemConsumeTaskSimple(ST.make(Items.iron_ingot, 32, 0)));

        ResearchProject b = new ResearchProject(main, "投影", "你需要探索光学成像的原理，设计基础投影设备，来将你对机器的构想投射到世界中", AdvancedRocketryItems.itemSatellitePrimaryFunction, 0, 2)
                .setPos(80,20)
                .addPrerequisite(main.rootItem);

        ResearchProject c = new ResearchProject(main, "芯片理论", "利用最初的芯片辅助你研究硅的特性，了解它的各项特性在芯片制造中的关键作用", Items.paper, 0, 3)
                .setPos(180,10)
                .addPrerequisite(a);

        ResearchProject d = new ResearchProject(main, "结晶器", "分析晶体生长过程，思考如何获得整齐排布的分子晶体结构", OP.bouleGt.mat(MT.Si,0).getItem(), MT.Si.mID, 4)
                .setPos(180,130)
                .addPrerequisite(a).addPrerequisite(b);

        ResearchProject f = new ResearchProject(main, "进阶电路设计", "利用计算器进一步改进电路，你认为你离真正的自动化控制不远了", Items.paper, 0, 5)
                .setPos(340,10)
                .addPrerequisite(c).addPrerequisite(d);

        ResearchProject g = new ResearchProject(main, "电弧处理", "你的记忆中总能见到电弧，但如何利用它而不损坏材料，需要你进一步研究", Items.paper, 0, 5)
                .setPos(340,10)
                .addPrerequisite(f);

        ResearchProject h = new ResearchProject(main, "高压容器", "许多处理需要高压环境，通过研究常见材料在高压下的变化来制造耐压容器", Items.paper, 0, 5)
                .setPos(340,10)
                .addPrerequisite(f);

        ResearchProject i = new ResearchProject(main, "电磁感应", "你早已听闻法拉第的大名和他的电磁感应定律，只需要稍微总结一套方便的规律和程序即可制造大量的实用物品", Items.paper, 0, 5)
                .setPos(340,10)
                .addPrerequisite(f);

        ResearchProject j = new ResearchProject(main, "电池原理", "利用已知的电解原理进行电力储存", Items.paper, 0, 5)
                .setPos(340,10)
                .addPrerequisite(f);

        ResearchProject k = new ResearchProject(main, "激光", "研究如何利用电力激发二氧化碳产生激光，激光可将能量集中于极小的一点，非常适合精确加工", Items.paper, 0, 5)
                .setPos(340,10)
                .addPrerequisite(f);

        ResearchProject l = new ResearchProject(main, "精确制造", "研究如何利用激光的极度精确集中的能量加工物品", Items.paper, 0, 5)
                .setPos(340,10)
                .addPrerequisite(f);


        ResearchProject m = new ResearchProject(main, "精确制造", "研究如何利用激光的极度精确集中的能量加工物品", Items.paper, 0, 5)
                .setPos(340,10)
                .addPrerequisite(f);

        main.init();
    }
}
