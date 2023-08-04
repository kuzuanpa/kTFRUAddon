package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import cn.kuzuanpa.ktfruaddon.recipe.recipeManager;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.*;
import gregapi.util.ST;

import static gregapi.data.CS.*;

public class ComputerBuilding {
    public static void init(){
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
        ), null, ZL_LONG, FL.array(FL.Sap_Rainbow.make(U)),ZL_FS, 0, 0, 0);

        //addRecipeX(T,GUt,Duration, ST.array(),FL.array(),FL.array(), );

        //EDA 设计电路 EU
        recipeManager.EDA.addRecipeX(T,16,36000, ST.array(IL.Circuit_Good.get(0), gRegistry.getItem(10102,0),gRegistry.getItem(10112,0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(400,F)),FL.array(ZL_FS),ItemList.CPUPhotomask200um.get(1));
        recipeManager.EDA.addRecipeX(T,16,36000, ST.array(IL.Circuit_Good.get(0),IL.Circuit_Part_Good.get(0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(400,F)),FL.array(ZL_FS),ItemList.CircuitPartPhotomaskT3.get(1));

        //Cut Boule to plate 切割单晶硅 LU
        recipeManager.LaserCutter.addRecipeX(T,96,2000, ST.array(OP.bouleGt.mat(MT.Si, 1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.SiliconPlate8inchT1.get(12));

        //Clean 清洗 TU
        //T1 skipped clean
        //recipeManager.UltraCleanBath.addRecipeX(T,0,800, ST.array( ItemList.SiliconPlate8inchT1.get(1)),FL.array(/*TODO*/),FL.array(ZL_FS), ItemList.SiliconPlate8inchCleanedT1.get(1));

        //Oxidize 氧化 HU
        //T1 skipped oxidize
        //recipeManager.UltraCleanFurnace.addRecipeX(T,110,1200, ST.array(ItemList.SiliconPlate8inchCleanedT1.get(1)),FL.array(FL.Steam.make(40000),FL.Oxygen.make(1000)),FL.array(ZL_FS), ItemList.SiliconPlate8inchOxidizedT1.get(1));

        //Coat 涂胶 EU
        recipeManager.WaferCoater.addRecipeX(T,64,200, ST.array(ItemList.SiliconPlate8inchOxidizedT1.get(1)),FL.array(/*TODO*/),FL.array(ZL_FS), ItemList.SiliconPlate8inchCoatedT1.get(1));

        //SoftBake 固化光刻胶 HU
        //T1 skipped softbake
        //recipeManager.UltraCleanDryer.addRecipeX(T,0,400, ST.array(ItemList.SiliconPlate8inchCoatedT1.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.SiliconPlate8inchSoftBakedT1.get(1));

        //MaskAlign 光刻 EU+LU
        recipeManager.MaskAligner.addRecipeX(T, 120, 4000, ST.array(ItemList.SiliconPlate8inchSoftBakedT1.get(1),ItemList.CPUPhotomask200um.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CPUWafer200um.get(1));
        recipeManager.MaskAligner.addRecipeX(T, 120, 4000, ST.array(ItemList.SiliconPlate8inchSoftBakedT1.get(1),ItemList.CircuitPartPhotomaskT3.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CircuitPartWaferT3.get(1));

        recipeManager.MaskAligner.addRecipeX(T, 256, 4000, ST.array(ItemList.SiliconPlate8inchSoftBakedT1.get(1),ItemList.CPUPhotomask72um.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CPUWafer72um.get(1));

        //Develop 显影 TU
        recipeManager.UltraCleanBath.addRecipeX(T,0,400, ST.array(ItemList.CPUWafer200um.get(1)),FL.array(/*TODO*/),FL.array(ZL_FS), ItemList.CPUWafer200umDeveloped.get(1));
        recipeManager.UltraCleanBath.addRecipeX(T,0,400, ST.array(ItemList.CircuitPartWaferT3.get(1)),FL.array(/*TODO*/),FL.array(ZL_FS), ItemList.CircuitPartWaferT3Developed.get(1));

        //HardBake 除胶 HU
        recipeManager.UltraCleanFurnace.addRecipeX(T,0,400, ST.array(ItemList.CPUWafer200umDeveloped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUWafer200umHardBaked.get(1));
        recipeManager.UltraCleanFurnace.addRecipeX(T,0,400, ST.array(ItemList.CircuitPartWaferT3Developed.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CircuitPartWaferT3HardBaked.get(1));

        //Dope 掺杂 TU
        recipeManager.UltraCleanBath.addRecipeX(T,0,400, ST.array(ItemList.CPUWafer200umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CPUWafer200umDoped.get(1));
        recipeManager.UltraCleanBath.addRecipeX(T,0,400, ST.array(ItemList.CircuitPartWaferT3HardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT3Doped.get(1));

        //Check 检测 EU
        recipeManager.WaferTester.addRecipeX(T,0,400, ST.array(ItemList.CPUWafer200umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUWafer200umChecked.get(1));
        recipeManager.WaferTester.addRecipeX(T,0,400, ST.array(ItemList.CircuitPartWaferT3Doped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CircuitPartWaferT3Checked.get(1));

        //Cut into Die 切割 LU
        recipeManager.LaserCutter.addRecipeX(T,0,400,new long[]{4000,1200}, ST.array(ItemList.CPUWafer200umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUDieTF3386.get(37),ItemList.CPUDieTF3386.get(22));
        recipeManager.LaserCutter.addRecipeX(T,0,400,new long[]{8000,1000}, ST.array(ItemList.CircuitPartWaferT3Checked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), IL.Circuit_Part_Advanced.get(60),IL.Circuit_Part_Good.get(40));


    }
}
