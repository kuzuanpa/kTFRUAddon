package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import cn.kuzuanpa.ktfruaddon.recipe.recipeManager;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.*;
import gregapi.util.ST;
import net.minecraft.item.Item;

import static gregapi.data.CS.*;

public class ComputerBuilding {
    public ComputerBuilding(){
        MultiTileEntityRegistry gRegistry = MultiTileEntityRegistry.getRegistry("gt.multitileentity");
        MultiTileEntityRegistry kRegistry = MultiTileEntityRegistry.getRegistry("ktfru.multitileentity");

        RM.DidYouKnow.addFakeRecipe(F, ST.array(
                ItemList.fakeItemPhotomask.get(1)
                , ItemList.fakeItemLaserCutting.get(1)
                , ItemList.fakeItemSiliconPlateCleaned.get(1)
                , ItemList.fakeItemSiliconPlateOxidized.get(1)
                , ItemList.fakeItemSiliconPlateCoated.get(1)
                , ItemList.fakeItemSiliconPlateSoftBaked.get(1)
        ), ST.array(
                ItemList.fakeItemWafer.get(1)
                , ItemList.fakeItemSiliconWaferDeveloped.get(1)
                , ItemList.fakeItemSiliconWaferHardBaked.get(1)
                , ItemList.fakeItemSiliconWaferDoped.get(1)
                , ItemList.fakeItemSiliconWaferChecked.get(1)
                , ItemList.fakeItemDie.get(1)
        ), null, ZL_LONG, FL.array(FL.Sap_Rainbow.make(250)),ZL_FS, 0, 0, 0);

        //addRecipeX(T,GUt,Duration, ST.array(),FL.array(),FL.array(), );

        recipeManager.ElectronicsDesigner.addRecipeX(T,32,36000, ST.array(IL.Circuit_Good.get(0), gRegistry.getItem(10102,0),gRegistry.getItem(10112,0), OP.plate.mat(MT.SiO2,4)), FL.array(ZL_FS),FL.array(ZL_FS),ItemList.CPUPhotomask200um.get(1));

        recipeManager.LaserCutter.addRecipeX(T,96,2000, ST.array(OP.bouleGt.mat(MT.Si, 1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.SiliconPlate8inchTier1.get(12));

        recipeManager.UltraCleanBath.addRecipeX(T,0,800, ST.array(),FL.array(),FL.array(), ItemList.SiliconPlate8inchCleanedTier1.get(1));

        recipeManager.UltraCleanFurnace.addRecipeX(T,110,1200, ST.array(),FL.array(),FL.array(), ItemList.SiliconPlate8inchOxidizedTier1.get(1));

        recipeManager.WaferCoater.addRecipeX(T,64,200, ST.array(),FL.array(),FL.array(), ItemList.SiliconPlate8inchCoatedTier1.get(1));

        recipeManager.UltraCleanDryer.addRecipeX(T,0,400, ST.array(),FL.array(),FL.array(), ItemList.SiliconPlate8inchSoftBakedTier1.get(1));

        recipeManager.MaskAligner.addRecipeX(T, 256, 4000, ST.array(ItemList.SiliconPlate8inchSoftBakedTier1.get(1),ItemList.CPUPhotomask200um.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CPUWafer200um.get(1));
        recipeManager.MaskAligner.addRecipeX(T, 512, 4000, ST.array(ItemList.SiliconPlate8inchSoftBakedTier2.get(1),ItemList.CPUPhotomask72um.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CPUWafer72um.get(1));

        recipeManager.UltraCleanBath.addRecipeX(T,0,400, ST.array(),FL.array(),FL.array(), ItemList.CPUWafer200umDeveloped.get(1));

        recipeManager.UltraCleanFurnace.addRecipeX(T,0,400, ST.array(),FL.array(),FL.array(), ItemList.CPUWafer200umHardBaked.get(1));

        recipeManager.UltraCleanBath.addRecipeX(T,0,400, ST.array(),FL.array(),FL.array(), ItemList.CPUWafer200umDoped.get(1));

        recipeManager.WaferTester.addRecipeX(T,0,400, ST.array(),FL.array(),FL.array(), ItemList.CPUWafer200umChecked.get(1));

        recipeManager.LaserCutter.addRecipeX(T,0,400,new long[]{4000,1200}, ST.array(),FL.array(),FL.array(), ItemList.CPUDieTF3386.get(37),ItemList.CPUDieTF3386.get(22));

    }
}
