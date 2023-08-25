package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.fluid.flList;
import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import cn.kuzuanpa.ktfruaddon.material.matList;
import cn.kuzuanpa.ktfruaddon.recipe.recipeManager;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.*;
import gregapi.oredict.OreDictManager;
import gregapi.util.CR;
import gregapi.util.ST;

import static gregapi.data.CS.*;

public class ComputerBuilding {
    public static void init(){
        MultiTileEntityRegistry gRegistry = MultiTileEntityRegistry.getRegistry("gt.multitileentity");
        MultiTileEntityRegistry kRegistry = MultiTileEntityRegistry.getRegistry("ktfru.multitileentity");

        //Photoresist
        RM.           Mixer    .addRecipeX(F,324,120, ST.array(OP.dust.mat(matList.Naphthalene.get(), 1)),FL.array(MT.Cl.gas(U5,false),FL.Water.make(200)),FL.array(MT.HCl.gas(U5,false)), matList.Naphthalenol.getDust(1));
        RM.           Mixer    .addRecipeX(F,196,80, ST.array(OP.dust.mat(matList.Naphthalenol.get(), 1),OP.dust.mat(MT.NaNO3,1)),FL.array(MT.HCl.gas(U5,false)),ZL_FS, matList.NaphthalenolAminoHydrochloride.getDust(1));
        RM.           Mixer    .addRecipe2(F,256,40,matList.NaphthalenolAminoHydrochloride.getDust(1),OP.dust.mat(MT.NaNO3,1),FL.array(MT.HCl.gas(0,false)),ZL_FS,matList.DiazoNaphthol.getDust(1));

        RM.           Mixer    .addRecipe0(F,430,120,FL.array(flList.Propanediol.make(100),flList.Methanol.make(100)),FL.array(flList.MethoxyPropanol.make(100)),ZL_IS);
        recipeManager.HeatMixer.addRecipe0(F,630,120,FL.array(flList.MethoxyPropanol.make(100),flList.GlacialAceticAcid.make(100)),FL.array(flList.PGMEA.make(100)),ZL_IS);

        recipeManager.HeatMixer.addRecipe2(F,1440,80,OP.dust.mat(MT.Bakelite,8),matList.DiazoNaphthol.getDust(8),FL.array(flList.PGMEA.make(1000)),FL.array(flList.DNQPhotoresist.make(1000)),ZL_IS);

        recipeManager.HeatMixer.addRecipe1(F,240,120,OP.dust.mat(MT.Pd,0),FL.array(flList.Phenol.make(100),FL.Hydrogen.make(100)),ZL_FS,matList.Cyclohexanol.getDust(1));
        recipeManager.HeatMixer.addRecipe2(F,240,120,OP.dust.mat(MT.Ag,0),matList.Cyclohexanone.getDust(1),FL.array(FL.Oxygen.make(100)),ZL_FS,matList.Cyclohexanol.getDust(1));

        RM.           Mixer    .addRecipe2(F,230,120,OP.dust.mat(matList.Acenaphthylene.mat, 1),OP.dust.mat(matList.AmmoniumNitrate.mat, 1),FL.array(flList.GlacialAceticAcid.make(0)),FL.array(MT.NH3.gas(U10,false),FL.Water.make(100)),matList.Nitroacenaphthene.getDust(1));

        recipeManager.HeatMixer.addRecipe2(F,256,120,matList.Nitroacenaphthene.getDust(8),matList.PolymethylMethacrylate.getDust(6),FL.array(flList.Cyclohexanone.make(1000)),FL.array(flList.PMMAPhotoresist.make(1000)),ZL_IS);


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
        ), null, ZL_LONG, FL.array(flList.PMMAPhotoresist.make(0)),ZL_FS, 0, 0, 0);

        //addRecipeX(T,GUt,Duration, ST.array(),FL.array(),FL.array(), );

        //EDA 设计电路 EU
        recipeManager.EDA.addRecipeX(T,16,36000, ST.array(IL.Circuit_Good.get(0), gRegistry.getItem(10102,0),gRegistry.getItem(10112,0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(400,F)),ZL_FS,ItemList.CPUPhotomask200um.get(1));
        recipeManager.EDA.addRecipeX(T,16,36000, ST.array(IL.Circuit_Good.get(0),IL.Circuit_Part_Good.get(0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(400,F)),FL.array(ZL_FS),ItemList.CircuitPartPhotomaskT4.get(1));

        recipeManager.EDA.addRecipeX(T,16,36000, ST.array(IL.Circuit_Advanced.get(0), gRegistry.getItem(10103,0),gRegistry.getItem(10113,0),gRegistry.getItem(10042,0),ItemList.CPUTF3386S.get(0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(400,F)),ZL_FS,ItemList.CPUPhotomask72um.get(1));
        recipeManager.EDA.addRecipeX(T,16,36000, ST.array(IL.Circuit_Advanced.get(0),IL.Circuit_Part_Advanced.get(0),ItemList.ResistanceT2.get(1),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(400,F)),FL.array(ZL_FS),ItemList.CircuitPartPhotomaskT5.get(1));

        //Cut Boule to plate 切割单晶硅 LU
        recipeManager.LaserCutter.addRecipeX(T,96,2000, ST.array(OP.bouleGt.mat(MT.Si, 1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.SiliconPlate8inchT1.get(12));

        //Clean 清洗 TU
        //T1 skip clean
        //recipeManager.UltraCleanBath.addRecipeX(T,0,800, ST.array( ItemList.SiliconPlate8inchT1.get(1)),FL.array(/*TODO*/),FL.array(ZL_FS), ItemList.SiliconPlate8inchCleanedT1.get(1));

        //Oxidize 氧化 HU
        //T1 skip oxidize
        //recipeManager.UltraCleanFurnace.addRecipeX(T,110,1200, ST.array(ItemList.SiliconPlate8inchCleanedT1.get(1)),FL.array(FL.Steam.make(40000),FL.Oxygen.make(1000)),FL.array(ZL_FS), ItemList.SiliconPlate8inchOxidizedT1.get(1));

        //Coat 涂胶 EU
        recipeManager.WaferCoater.addRecipeX(T,64,200, ST.array(ItemList.SiliconPlate8inchOxidizedT1.get(1)),FL.array(flList.PMMAPhotoresist.make(100)),FL.array(ZL_FS), ItemList.SiliconPlate8inchCoatedT1.get(1));

        //SoftBake 固化光刻胶 HU
        //T1 skip softbake
        //recipeManager.UltraCleanDryer.addRecipeX(T,0,400, ST.array(ItemList.SiliconPlate8inchCoatedT1.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.SiliconPlate8inchSoftBakedT1.get(1));

        //MaskAlign 光刻 EU+LU
        recipeManager.MaskAligner.addRecipeX(T, 128, 4000, ST.array(ItemList.SiliconPlate8inchSoftBakedT1.get(1),ItemList.CPUPhotomask200um.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CPUWafer200um.get(1));
        recipeManager.MaskAligner.addRecipeX(T, 512, 4000, ST.array(ItemList.SiliconPlate8inchSoftBakedT1.get(1),ItemList.CPUPhotomask72um.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CPUWafer72um.get(1));

        recipeManager.MaskAligner.addRecipeX(T, 100, 4000, ST.array(ItemList.SiliconPlate8inchSoftBakedT1.get(1),ItemList.RAMPhotomask200um.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.RAMWafer200um.get(1));
        recipeManager.MaskAligner.addRecipeX(T, 400, 4000, ST.array(ItemList.SiliconPlate8inchSoftBakedT1.get(1),ItemList.RAMPhotomask72um.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.RAMWafer72um.get(1));

        recipeManager.MaskAligner.addRecipeX(T, 120, 4000, ST.array(ItemList.SiliconPlate8inchSoftBakedT1.get(1),ItemList.CircuitPartPhotomaskT3.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CircuitPartWaferT3.get(1));
        recipeManager.MaskAligner.addRecipeX(T, 260, 4000, ST.array(ItemList.SiliconPlate8inchSoftBakedT1.get(1),ItemList.CircuitPartPhotomaskT4.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CircuitPartWaferT4.get(1));

        //Develop 显影 TU
        recipeManager.UltraCleanBath.addRecipeX(T,0,400, ST.array(ItemList.CPUWafer200um.get(1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CPUWafer200umDeveloped.get(1));
        recipeManager.UltraCleanBath.addRecipeX(T,0,400, ST.array(ItemList.CPUWafer200um.get(1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CPUWafer200umDeveloped.get(1));

        recipeManager.UltraCleanBath.addRecipeX(T,0,400, ST.array(ItemList.RAMWafer200um.get(1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.RAMWafer200umDeveloped.get(1));
        recipeManager.UltraCleanBath.addRecipeX(T,0,400, ST.array(ItemList.RAMWafer200um.get(1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.RAMWafer200umDeveloped.get(1));

        recipeManager.UltraCleanBath.addRecipeX(T,0,400, ST.array(ItemList.CircuitPartWaferT3.get(1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT3Developed.get(1));
        recipeManager.UltraCleanBath.addRecipeX(T,0,400, ST.array(ItemList.CircuitPartWaferT4.get(1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT4Developed.get(1));

        //HardBake 除胶 HU
        recipeManager.UltraCleanFurnace.addRecipeX(T,64,400, ST.array(ItemList.CPUWafer200umDeveloped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUWafer200umHardBaked.get(1));
        recipeManager.UltraCleanFurnace.addRecipeX(T,120,400, ST.array(ItemList.CPUWafer72umDeveloped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUWafer72umHardBaked.get(1));

        recipeManager.UltraCleanFurnace.addRecipeX(T,64,400, ST.array(ItemList.RAMWafer200umDeveloped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMWafer200umHardBaked.get(1));
        recipeManager.UltraCleanFurnace.addRecipeX(T,120,400, ST.array(ItemList.RAMWafer72umDeveloped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMWafer72umHardBaked.get(1));

        recipeManager.UltraCleanFurnace.addRecipeX(T,64,400, ST.array(ItemList.CircuitPartWaferT3Developed.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CircuitPartWaferT3HardBaked.get(1));
        recipeManager.UltraCleanFurnace.addRecipeX(T,120,400, ST.array(ItemList.CircuitPartWaferT4Developed.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CircuitPartWaferT4HardBaked.get(1));

        //Dope 掺杂 TU
        recipeManager.UltraCleanBath.addRecipeX(T,0,400, ST.array(ItemList.CPUWafer200umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CPUWafer200umDoped.get(1));
        recipeManager.UltraCleanBath.addRecipeX(T,0,400, ST.array(ItemList.CPUWafer72umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CPUWafer72umDoped.get(1));

        recipeManager.UltraCleanBath.addRecipeX(T,0,400, ST.array(ItemList.RAMWafer200umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.RAMWafer200umDoped.get(1));
        recipeManager.UltraCleanBath.addRecipeX(T,0,400, ST.array(ItemList.RAMWafer72umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.RAMWafer72umDoped.get(1));

        recipeManager.UltraCleanBath.addRecipeX(T,0,400, ST.array(ItemList.CircuitPartWaferT3HardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT3Doped.get(1));
        recipeManager.UltraCleanBath.addRecipeX(T,0,400, ST.array(ItemList.CircuitPartWaferT4HardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT4Doped.get(1));

        //Check 检测 EU
        recipeManager.WaferTester.addRecipeX(T,120,400, ST.array(ItemList.CPUWafer200umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUWafer200umChecked.get(1));
        recipeManager.WaferTester.addRecipeX(T,500,400, ST.array(ItemList.CPUWafer72umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUWafer72umChecked.get(1));

        recipeManager.WaferTester.addRecipeX(T,120,400, ST.array(ItemList.RAMWafer200umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMWafer200umChecked.get(1));
        recipeManager.WaferTester.addRecipeX(T,420,400, ST.array(ItemList.RAMWafer72umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMWafer72umChecked.get(1));

        recipeManager.WaferTester.addRecipeX(T,70,400, ST.array(ItemList.CircuitPartWaferT3Doped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CircuitPartWaferT3Checked.get(1));
        recipeManager.WaferTester.addRecipeX(T,300,400, ST.array(ItemList.CircuitPartWaferT4Doped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CircuitPartWaferT4Checked.get(1));

        //Cut into Die 切割 LU
        recipeManager.LaserCutter.addRecipeX(T,120,400,new long[]{4000,1200}, ST.array(ItemList.CPUWafer200umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUDieTF3386.get(37),ItemList.CPUDieTF3386.get(22));
        recipeManager.LaserCutter.addRecipeX(T,500,400,new long[]{4000,1200}, ST.array(ItemList.CPUWafer72umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUDieTF3386.get(37),ItemList.CPUDieTF3386.get(22));

        recipeManager.LaserCutter.addRecipeX(T,80,400,new long[]{6000}, ST.array(ItemList.RAMWafer200umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMDie2K.get(60));
        recipeManager.LaserCutter.addRecipeX(T,320,400,new long[]{6000}, ST.array(ItemList.RAMWafer72umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMDie2K.get(60));

        recipeManager.LaserCutter.addRecipeX(T,64,400,new long[]{7000,1000}, ST.array(ItemList.CircuitPartWaferT3Checked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), IL.Circuit_Part_Advanced.get(60),IL.Circuit_Part_Good.get(40));
        recipeManager.LaserCutter.addRecipeX(T,256,400,new long[]{4500,1500}, ST.array(ItemList.CircuitPartWaferT4Checked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), IL.Circuit_Part_Elite.get(60),IL.Circuit_Part_Advanced.get(40));

        //Packaging 封装 EU
        recipeManager.Assembler.addRecipeX(F,90,80,ST.array(ItemList.CPUBoardT1.get(1),ItemList.CPUDieTF3386.get(1)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CPUTF3386.get(1));
        recipeManager.Assembler.addRecipeX(F,128,80,ST.array(ItemList.CPUBoardT1.get(1),ItemList.CPUDieTF3386S.get(1)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CPUTF3386S.get(1));
        recipeManager.Assembler.addRecipeX(F,320,80,ST.array(ItemList.CPUBoardT1.get(1),ItemList.CPUDieTF3586.get(1)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CPUTF3586.get(1));
        recipeManager.Assembler.addRecipeX(F,350,80,ST.array(ItemList.CPUBoardT1.get(1),ItemList.CPUDieTF3586S.get(1)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CPUTF3586S.get(1));

        recipeManager.Assembler.addRecipeX(F,60,80,ST.array(ItemList.RAMBoardT1.get(1),ItemList.RAMDie2K.get(4)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.RAMBar2K4.get(1));
        recipeManager.Assembler.addRecipeX(F,64,160,ST.array(ItemList.RAMBoardT1.get(1),ItemList.RAMDie2K.get(8)),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.RAMBar2K8.get(1));
        recipeManager.Assembler.addRecipeX(F,120,80,ST.array(ItemList.RAMBoardT1.get(1),ItemList.RAMDie24K.get(4)),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.RAMBar24K4.get(1));
        recipeManager.Assembler.addRecipeX(F,128,160,ST.array(ItemList.RAMBoardT1.get(1),ItemList.RAMDie24K.get(8)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.RAMBar24K8.get(1));

        //Assemble to computer 组装电脑 EU
        //TF3386
        recipeManager.Assembler.addRecipeX(F,16,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8K", 8),ItemList.CoilT1.get(4),ItemList.ResistanceT1.get(20),ItemList.CapacitorT1.get(32)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386.get(1));
        recipeManager.Assembler.addRecipeX(F,16,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8K", 8),ItemList.CoilT1.get(4),ItemList.ResistanceT2.get(8),ItemList.CapacitorT1.get(32)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386.get(1));
        recipeManager.Assembler.addRecipeX(F,16,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8K", 8),ItemList.CoilT1.get(4),ItemList.ResistanceT1.get(20),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386.get(1));
        recipeManager.Assembler.addRecipeX(F,16,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8K", 8),ItemList.CoilT1.get(4),ItemList.ResistanceT2.get(8),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386.get(1));

        recipeManager.Assembler.addRecipeX(F,16,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16K", 4),ItemList.CoilT1.get(4),ItemList.ResistanceT1.get(20),ItemList.CapacitorT1.get(32)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386.get(1));
        recipeManager.Assembler.addRecipeX(F,16,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16K", 4),ItemList.CoilT1.get(4),ItemList.ResistanceT2.get(8),ItemList.CapacitorT1.get(32)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386.get(1));
        recipeManager.Assembler.addRecipeX(F,16,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16K", 4),ItemList.CoilT1.get(4),ItemList.ResistanceT1.get(20),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386.get(1));
        recipeManager.Assembler.addRecipeX(F,16,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16K", 4),ItemList.CoilT1.get(4),ItemList.ResistanceT2.get(8),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386.get(1));
        //TF3386S
        recipeManager.Assembler.addRecipeX(F,24,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8K", 8),ItemList.CoilT1.get(4),ItemList.ResistanceT1.get(16),ItemList.CapacitorT1.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386S.get(1));
        recipeManager.Assembler.addRecipeX(F,24,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8K", 8),ItemList.CoilT1.get(4),ItemList.ResistanceT2.get(6),ItemList.CapacitorT1.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386S.get(1));
        recipeManager.Assembler.addRecipeX(F,24,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8K", 8),ItemList.CoilT1.get(4),ItemList.ResistanceT1.get(16),ItemList.CapacitorT2.get(12)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386S.get(1));
        recipeManager.Assembler.addRecipeX(F,24,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8K", 8),ItemList.CoilT1.get(4),ItemList.ResistanceT2.get(6),ItemList.CapacitorT2.get(12)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386S.get(1));

        recipeManager.Assembler.addRecipeX(F,24,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16K", 4),ItemList.CoilT1.get(4),ItemList.ResistanceT1.get(16),ItemList.CapacitorT1.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386S.get(1));
        recipeManager.Assembler.addRecipeX(F,24,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16K", 4),ItemList.CoilT1.get(4),ItemList.ResistanceT2.get(6),ItemList.CapacitorT1.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386S.get(1));
        recipeManager.Assembler.addRecipeX(F,24,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16K", 4),ItemList.CoilT1.get(4),ItemList.ResistanceT1.get(16),ItemList.CapacitorT2.get(12)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386S.get(1));
        recipeManager.Assembler.addRecipeX(F,24,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16K", 4),ItemList.CoilT1.get(4),ItemList.ResistanceT2.get(6),ItemList.CapacitorT2.get(12)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386S.get(1));
        //TF3586
        recipeManager.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM96K", 4),ItemList.CoilT1.get(6),ItemList.ResistanceT1.get(32),ItemList.CapacitorT1.get(48)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeManager.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM96K", 4),ItemList.CoilT1.get(6),ItemList.ResistanceT2.get(16),ItemList.CapacitorT1.get(48)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeManager.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM96K", 4),ItemList.CoilT1.get(6),ItemList.ResistanceT1.get(32),ItemList.CapacitorT2.get(24)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeManager.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM96K", 4),ItemList.CoilT1.get(6),ItemList.ResistanceT2.get(16),ItemList.CapacitorT2.get(24)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));

        recipeManager.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM192K", 2),ItemList.CoilT1.get(6),ItemList.ResistanceT1.get(32),ItemList.CapacitorT1.get(48)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeManager.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM192K", 2),ItemList.CoilT1.get(6),ItemList.ResistanceT2.get(16),ItemList.CapacitorT1.get(48)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeManager.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM192K", 2),ItemList.CoilT1.get(6),ItemList.ResistanceT1.get(32),ItemList.CapacitorT2.get(24)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeManager.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM192K", 2),ItemList.CoilT1.get(6),ItemList.ResistanceT2.get(16),ItemList.CapacitorT2.get(24)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));

        recipeManager.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM192K", 2),ItemList.CoilT2.get(4),ItemList.ResistanceT1.get(32),ItemList.CapacitorT1.get(48)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeManager.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM192K", 2),ItemList.CoilT2.get(4),ItemList.ResistanceT2.get(16),ItemList.CapacitorT1.get(48)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeManager.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM192K", 2),ItemList.CoilT2.get(4),ItemList.ResistanceT1.get(32),ItemList.CapacitorT2.get(24)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeManager.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM192K", 2),ItemList.CoilT2.get(4),ItemList.ResistanceT2.get(16),ItemList.CapacitorT2.get(24)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        //TF3586S
        recipeManager.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM96K", 4),ItemList.CoilT1.get(6),ItemList.ResistanceT1.get(28),ItemList.CapacitorT1.get(40)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeManager.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM96K", 4),ItemList.CoilT1.get(6),ItemList.ResistanceT2.get(12),ItemList.CapacitorT1.get(40)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeManager.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM96K", 4),ItemList.CoilT1.get(6),ItemList.ResistanceT1.get(28),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeManager.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM96K", 4),ItemList.CoilT1.get(6),ItemList.ResistanceT2.get(12),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));

        recipeManager.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM192K", 2),ItemList.CoilT1.get(6),ItemList.ResistanceT1.get(28),ItemList.CapacitorT1.get(40)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeManager.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM192K", 2),ItemList.CoilT1.get(6),ItemList.ResistanceT2.get(12),ItemList.CapacitorT1.get(40)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeManager.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM192K", 2),ItemList.CoilT1.get(6),ItemList.ResistanceT1.get(28),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeManager.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM192K", 2),ItemList.CoilT1.get(6),ItemList.ResistanceT2.get(12),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));

        recipeManager.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM192K", 2),ItemList.CoilT2.get(4),ItemList.ResistanceT1.get(28),ItemList.CapacitorT1.get(40)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeManager.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM192K", 2),ItemList.CoilT2.get(4),ItemList.ResistanceT2.get(12),ItemList.CapacitorT1.get(40)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeManager.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM192K", 2),ItemList.CoilT2.get(4),ItemList.ResistanceT1.get(28),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeManager.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM192K", 2),ItemList.CoilT2.get(4),ItemList.ResistanceT2.get(12),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));



        recipeManager.LaserCutter.addRecipe1(F,64,140,IL.Circuit_Plate_Copper.get(1),ZL_FS,ZL_FS,ItemList.RAMBoardT1.get(14));
        recipeManager.LaserCutter.addRecipe1(F,64,400,IL.Circuit_Plate_Gold.get(1),ZL_FS,ZL_FS,ItemList.RAMBoardT2.get(14));
        recipeManager.LaserCutter.addRecipe1(F,64,1000,IL.Circuit_Plate_Platinum.get(1),ZL_FS,ZL_FS,ItemList.RAMBoardT3.get(14));

        recipeManager.LaserCutter.addRecipe1(F,64,140,IL.Circuit_Plate_Copper.get(1),ZL_FS,ZL_FS,ItemList.CPUBoardT1.get(25));
        recipeManager.LaserCutter.addRecipe1(F,64,400,IL.Circuit_Plate_Gold.get(1),ZL_FS,ZL_FS,ItemList.CPUBoardT2.get(25));
        recipeManager.LaserCutter.addRecipe1(F,64,1000,IL.Circuit_Plate_Platinum.get(1),ZL_FS,ZL_FS,ItemList.CPUBoardT3.get(25));


        recipeManager.Assembler.addRecipeX(F,16,80,ST.array(OP.dust.mat(MT.SteelGalvanized,4),OP.screw.mat(MT.Steel,16),IL.Circuit_Basic.get(4)),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.ComputerBasicCircuits.get(1));
        recipeManager.Assembler.addRecipeX(F,16,160,ST.array(OP.dust.mat(MT.SteelGalvanized,4),OP.screw.mat(MT.Steel,16),IL.Circuit_Good.get(4)),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.ComputerGoodCircuits.get(1));
        recipeManager.Assembler.addRecipeX(F,16,320,ST.array(OP.dust.mat(MT.SteelGalvanized,4),OP.screw.mat(MT.Steel,32),IL.Circuit_Advanced.get(4)),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.ComputerAdvancedCircuits.get(1));
        recipeManager.Assembler.addRecipeX(F,16,640,ST.array(OP.dust.mat(MT.StainlessSteel,4),OP.screw.mat(MT.Steel,32),IL.Circuit_Elite.get(4)),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.ComputerEliteCircuits.get(1));
        recipeManager.Assembler.addRecipeX(F,32,960,ST.array(OP.dust.mat(MT.StainlessSteel,4),OP.screw.mat(MT.StainlessSteel,64),IL.Circuit_Master.get(4)),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.ComputerMasterCircuits.get(1));
        recipeManager.Assembler.addRecipeX(F,32,1280,ST.array(OP.dust.mat(MT.TungstenSteel,4),OP.screw.mat(MT.StainlessSteel,64),IL.Circuit_Ultimate.get(4)),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.ComputerUltimateCircuits.get(1));
        //downgrading computer
        CR.shapeless(ItemList.ComputerTF3586.get(1), new Object[]{"ktfruNoviceComputer"});
        CR.shapeless(ItemList.ComputerGT1090.get(1),new Object[]{"ktfruModerateComputer"});
        CR.shapeless(ItemList.ComputerGT3680.get(1),new Object[]{"ktfruAdvancedComputer"});
        CR.shapeless(ItemList.ComputerGT3660v3.get(1),new Object[]{"ktfruEliteComputer"});
        CR.shapeless(ItemList.ComputerGT3680v3.get(1),new Object[]{"ktfruMasterComputer"});
        CR.shapeless(ItemList.ComputerGT3680v3e.get(1),new Object[]{"ktfruUltimateComputer"});

    }
}
