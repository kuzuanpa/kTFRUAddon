/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.item.items;


import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import gregapi.item.multiitem.MultiItemRandom;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;
/**These Item is to resolve i18n problems in DidYouKnow fakeRecipe and more...**/
public class fakeItemGuiI18n extends MultiItemRandom {
    public fakeItemGuiI18n() {
        super(MOD_ID, "ktfru.item.fake.guii18n");
    }
    @Override
    public void addItems() {
        ItemList.fakeItemPhotomask.set(addItem(0, "学习各种科技事物来制作掩模版",""));
        ItemList.fakeItemBoule.set(addItem(1, "首先使用<>制作单晶硅", ""));
        ItemList.fakeItemLaserCutting.set(addItem(2, "激光切割单晶硅，获得平整的晶圆", ""));
        ItemList.fakeItemSiliconPlateCleaned.set(addItem(3, "使用<>清洗晶圆表面杂质", "将原材料放入<超净浸洗器>进行清洗"));
        ItemList.fakeItemSiliconPlateOxidized.set(addItem(4, "使用<超净炉>给晶圆表面氧化", ""));
        ItemList.fakeItemSiliconPlateCoated.set(addItem(5, "使用<晶圆涂胶器>给晶圆表面涂上光刻胶", ""));
        ItemList.fakeItemSiliconPlateSoftBaked.set(addItem(6, "使用<超净烘干器>软烘, 让表面光刻胶固化", ""));
        ItemList.fakeItemWafer.set(addItem(7, "使用<光刻机>光刻晶圆", ""));
        ItemList.fakeItemSiliconWaferDeveloped.set(addItem(8, "使用<>来显影，将光刻纹路映射到硅片上", "将原材料放入<超净浸洗器>进行显影"));
        ItemList.fakeItemSiliconWaferHardBaked.set(addItem(9, "使用<超净炉>硬烘晶圆，移除光刻胶等", ""));
        ItemList.fakeItemSiliconWaferDoped.set(addItem(10, "使用<>掺杂晶圆", "将原材料放入<超净浸洗器>进行掺杂"));
        ItemList.fakeItemSiliconWaferChecked.set(addItem(11, "使用<晶圆测试仪>测试晶圆可用性", ""));
        ItemList.fakeItemDie.set(addItem(12, "激光切割晶圆获得晶片", ""));
    }
}
