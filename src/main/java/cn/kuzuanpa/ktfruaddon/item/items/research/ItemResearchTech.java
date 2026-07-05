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

package cn.kuzuanpa.ktfruaddon.item.items.research;

import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import gregapi.item.CreativeTab;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class ItemResearchTech extends ItemResearchBase {
    public ItemResearchTech() {
        super(MOD_ID, "ktfru.item.research.0");
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: Tech Research", this, (short) 0));
    }

    @Override
    public void addItems() {
        ItemList.TechResearchData0 .set(addItem(0 , "研究数据-科学",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData1 .set(addItem(1 , "研究数据-芯片基础",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData2 .set(addItem(2 , "研究数据-投影",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData3 .set(addItem(3 , "研究数据-硅理论",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData4 .set(addItem(4 , "研究数据-结晶器",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData5 .set(addItem(5 , "研究数据-基础电路设计",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData6 .set(addItem(6 , "研究数据-电弧处理",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData7 .set(addItem(7 , "研究数据-高压容器",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData8 .set(addItem(8 , "研究数据-电磁感应",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData9 .set(addItem(9 , "研究数据-电池原理",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData10.set(addItem(10, "研究数据-半导体制冷",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData11.set(addItem(11, "研究数据-激光基础",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData12.set(addItem(12, "研究数据-激光精确制造",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData13.set(addItem(13, "研究数据-单晶硅制造",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData14.set(addItem(14, "研究数据-低级电路设计",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData15.set(addItem(15, "研究数据-赛特斯应用",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData16.set(addItem(16, "研究数据-智能赛特斯",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData17.set(addItem(17, "研究数据-高压发电",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData18.set(addItem(18, "研究数据-光刻理论",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData19.set(addItem(19, "研究数据-计算机系统",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData20.set(addItem(20, "研究数据-数控车床",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData21.set(addItem(21, "研究数据-太空基础理论",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData22.set(addItem(22, "研究数据-火箭基础",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData23.set(addItem(23, "研究数据-空间概论",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData24.set(addItem(24, "研究数据-保护气应用",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData25.set(addItem(25, "研究数据-入门计算机",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData26.set(addItem(26, "研究数据-叶片气动力学",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData27.set(addItem(27, "研究数据-原子结构",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData28.set(addItem(28, "研究数据-原子核理论",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData29.set(addItem(29, "研究数据-计算机T3",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData30.set(addItem(30, "研究数据-裂变控制理论",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData31.set(addItem(31, "研究数据-放射成像",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData32.set(addItem(32, "研究数据-电子显微技术",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData33.set(addItem(33, "研究数据-计算机T4",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData34.set(addItem(34, "研究数据-催化剂原理",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData35.set(addItem(35, "研究数据-高级塑料",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData36.set(addItem(36, "研究数据-计算机T5",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData37.set(addItem(37, "研究数据-空间站理论",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData38.set(addItem(38, "研究数据-微重力应用",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData39.set(addItem(39, "研究数据-宇宙辐射研究",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData40.set(addItem(40, "研究数据-计算机T6",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData41.set(addItem(41, "研究数据-硅岩性质研究",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData42.set(addItem(42, "研究数据-镜金应用",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData43.set(addItem(43, "研究数据-计算机T7",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData44.set(addItem(44, "研究数据-聚变基础理论",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData45.set(addItem(45, "研究数据-磁约束理论",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData46.set(addItem(46, "研究数据-数据汇总-磁",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData47.set(addItem(47, "研究数据-商用磁约束",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData48.set(addItem(48, "研究数据-惯性约束理论",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData49.set(addItem(49, "研究数据-数据汇总-惯性",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData50.set(addItem(50, "研究数据-商用惯性约束",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData51.set(addItem(51, "研究数据-量子化镜金",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData52.set(addItem(52, "研究数据-量金性质应用",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData53.set(addItem(53, "研究数据-量子观测",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData54.set(addItem(54, "研究数据-量子处理",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData55.set(addItem(55, "研究数据-量子计算",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData56.set(addItem(56, "研究数据-跃迁理论",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData57.set(addItem(57, "研究数据-有机量子处理",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData58.set(addItem(58, "研究数据-量子结构理论",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData59.set(addItem(59, "研究数据-刻金性质应用",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData60.set(addItem(60, "研究数据-量子计算微型化",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData61.set(addItem(61, "研究数据-强力物质",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData62.set(addItem(62, "研究数据-虚空理论",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData63.set(addItem(63, "研究数据-零点场论",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData64.set(addItem(64, "研究数据-零点能量生成",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData65.set(addItem(65, "研究数据-零点物质生成",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData66.set(addItem(66, "研究数据-科魔统一理论",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));
        ItemList.TechResearchData67.set(addItem(67, "研究数据-无尽",  "spduvypsdpf9ibund9figspduvypsdpf9ibund9figspduvypsdpf9ibund9fig."));

        ItemList.TechResearchData  .set(addItem(32766, "Common Research Data",  "A common data, You shouldn't get this."));
    }

    @Override
    public byte getTreeId() {
        return 0;
    }
}
