package cn.kuzuanpa.ktfruaddon.item.items;

import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;
import cn.kuzuanpa.ktfruaddon.item.util.ItemList;

public class fakeItemGuiI18n extends MultiItemRandom {
    public fakeItemGuiI18n() {
        super(MOD_ID, "ktfru.item.fake.guii18n");
    }
    @Override
    public void addItems() {
        ItemList.fakeItemPhotomask.set(addItem(0, "学习各种科技事物来制作掩模版",""));
        ItemList.fakeItemBoule.set(addItem(1, "首先使用<>制作单晶硅", ""));
        ItemList.fakeItemLaserCutting.set(addItem(2, "激光切割单晶硅，获得平整的晶圆", ""));
        ItemList.fakeItemSiliconPlateCleaned.set(addItem(3, "使用<>清洗晶圆表面杂质", ""));
        ItemList.fakeItemSiliconPlateOxidized.set(addItem(4, "使用<>给晶圆表面氧化", ""));
        ItemList.fakeItemSiliconPlateCoated.set(addItem(5, "使用<>给晶圆表面涂上光刻胶", ""));
        ItemList.fakeItemSiliconPlateSoftBaked.set(addItem(6, "使用<>让表面光刻胶固化", ""));
        ItemList.fakeItemCPUWafer.set(addItem(7, "使用<光刻机>光刻晶圆", ""));
        ItemList.fakeItemSiliconCPUWaferDeveloped.set(addItem(8, "使用<>来显影，将光刻纹路映射到硅片上", ""));
        ItemList.fakeItemSiliconCPUWaferHardBaked.set(addItem(9, "使用<>硬烘晶圆，移除光刻胶等", ""));
        ItemList.fakeItemSiliconCPUWaferDoped.set(addItem(10, "使用<>掺杂晶圆", ""));
        ItemList.fakeItemSiliconCPUWaferChecked.set(addItem(11, "使用<>测试晶圆可用性", ""));
        ItemList.fakeItemCPUDie.set(addItem(12, "激光切割晶圆获得晶片", ""));
    }
}
