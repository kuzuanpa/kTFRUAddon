package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.fluid.flList;
import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import cn.kuzuanpa.ktfruaddon.material.matList;
import cn.kuzuanpa.ktfruaddon.recipe.recipeMaps;
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
        recipeMaps.HeatMixer.addRecipeX(F,32 ,120, ST.array(OP.dust.mat(matList.Naphthalene.get(), 1)),FL.array(MT.Cl.gas(U5,false),FL.Water.make(200)),FL.array(MT.HCl.gas(U5,false)), matList.Naphthalenol.getDust(1));
        RM.           Mixer    .addRecipeX(F,100,80, ST.array(OP.dust.mat(matList.Naphthalenol.get(), 1),OP.dust.mat(MT.NaNO3,1)),FL.array(MT.HCl.gas(U5,false)),ZL_FS, matList.DiazoNaphthol.getDust(1));

        RM.           Mixer    .addRecipe0(F, 50,70,FL.array(flList.Propanediol.make(100),flList.Methanol.make(100)),FL.array(flList.MethoxyPropanol.make(100)),ZL_IS);
        recipeMaps.HeatMixer.addRecipe0(F,60,70,FL.array(flList.MethoxyPropanol.make(100),flList.GlacialAceticAcid.make(100)),FL.array(flList.PGMEA.make(100)),ZL_IS);

        recipeMaps.HeatMixer.addRecipe2(F,160,90,OP.dust.mat(MT.Bakelite,8),matList.DiazoNaphthol.getDust(8),FL.array(flList.PGMEA.make(1000)),FL.array(flList.DNQPhotoresist.make(1000)),ZL_IS);

        recipeMaps.HeatMixer.addRecipe1(F,240,120,OP.dust.mat(MT.Pd,0),FL.array(flList.Phenol.make(100),FL.Hydrogen.make(100)),ZL_FS,matList.Cyclohexanol.getDust(1));
        recipeMaps.HeatMixer.addRecipe2(F,240,120,OP.dust.mat(MT.Ag,0),matList.Cyclohexanone.getDust(1),FL.array(FL.Oxygen.make(100)),ZL_FS,matList.Cyclohexanol.getDust(1));

        RM.           Mixer    .addRecipe2(F,230,120,OP.dust.mat(matList.Acenaphthylene.mat, 1),OP.dust.mat(matList.AmmoniumNitrate.mat, 1),FL.array(flList.GlacialAceticAcid.make(0)),FL.array(MT.NH3.gas(U10,false),FL.Water.make(100)),matList.Nitroacenaphthene.getDust(1));

        recipeMaps.HeatMixer.addRecipe2(F,1440,120,matList.Nitroacenaphthene.getDust(8),matList.PolymethylMethacrylate.getDust(6),FL.array(flList.Cyclohexanone.make(1000)),FL.array(flList.PMMAPhotoresist.make(1000)),ZL_IS);
//Colloid
        RM.           Mixer    .addRecipe2(F,120,120,OP.dust.mat(MT.NaOH, 4),OP.dust.mat(matList.OleicAcid.mat,4),FL.array(FL.DistW.make(100),flList.Toluene.make(1000)),FL.array(flList.NegativeColloid.make(1000)),ZL_IS);


        //EDA 设计电路 EU
        recipeMaps.EDA.addRecipeX(T,16,36000, ST.array(IL.Circuit_Good.get(0), gRegistry.getItem(10102,0),gRegistry.getItem(10112,0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(400,F)),ZL_FS,ItemList.CPUPhotomask200um.get(1));
        recipeMaps.EDA.addRecipeX(T,16,18000, ST.array(IL.Circuit_Good.get(0),IL.Circuit_Part_Good.get(0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(400,F)),FL.array(ZL_FS),ItemList.CircuitPartPhotomaskT3.get(1));
        recipeMaps.EDA.addRecipeX(T,16,27000, ST.array(IL.Circuit_Good.get(0),gRegistry.getItem(1,0),gRegistry.getItem(6007,0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(400,F)),FL.array(ZL_FS),ItemList.RAMPhotomask200um.get(1));

        recipeMaps.EDA.addRecipeX(T,16,72000, ST.array(IL.Circuit_Advanced.get(0), gRegistry.getItem(10103,0),gRegistry.getItem(10113,0),gRegistry.getItem(10042,0),ItemList.CPUTF3386S.get(0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(400,F)),ZL_FS,ItemList.CPUPhotomask72um.get(1));
        recipeMaps.EDA.addRecipeX(T,16,36000, ST.array(IL.Circuit_Advanced.get(0),IL.Circuit_Part_Advanced.get(0),ItemList.ResistanceT2.get(1),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(400,F)),FL.array(ZL_FS),ItemList.CircuitPartPhotomaskT4.get(1));
        recipeMaps.EDA.addRecipeX(T,16,54000, ST.array(IL.Circuit_Advanced.get(0),gRegistry.getItem(11,0),gRegistry.getItem(6019,0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(400,F)),FL.array(ZL_FS),ItemList.RAMPhotomask72um.get(1));

//Purify Silicon
    //Method1
        recipeMaps.HeatMixer.addRecipe2(F,80,10,OP.dust.mat(MT.Si,1),OP.dust.mat(MT.Mg,2),ZL_FS,ZL_FS,matList.MagnesiumSilicide.getDust(1));
        recipeMaps.HeatMixer.addRecipe1(F,120,60,OP.dust.mat(MT.NaCl,1),FL.array(MT.NH3.gas(U,false),FL.Water.make(1000)),ZL_FS,OP.dust.mat(MT.NaOH,1),matList.AmmoniumChloride.getDust(1));
        RM           .Mixer    .addRecipe2(F,150,20,OP.dust.mat(matList.MagnesiumSilicide.mat, 3),matList.AmmoniumChloride.getDust(12),ZL_FS,FL.array(MT.NH3.gas(12*U,false),flList.Silane.make(3000)),OP.dust.mat(MT.MgCl2,6));
    //Method2
        recipeMaps.HeatMixer.addRecipe0(F,290,10,FL.array(MT.H2SiF6.liquid(3*U,false)),FL.array(flList.SiliconTetrafluoride.make(1000),MT.HF.gas(2*U,false)),ZL_IS);
        RM           .Mixer    .addRecipe2(F,380,20,OP.dust.mat(MT.Na,1),OP.dust.mat(MT.Al,1),FL.array(FL.Hydrogen.make(2000)),FL.array(flList.SodiumAluminate.make(1000)),OP.dust.mat(matList.SodiumAluminiumHydride.mat, 1));
        recipeMaps.HeatMixer.addRecipe1(F,410,40,matList.SodiumAluminiumHydride.getDust(1),FL.array(flList.SiliconTetrafluoride.make(1000)),FL.array(flList.Silane.make(1000)/*TODO:1*NaAlF4*/),ZL_IS);

//Make Boule 制作单晶硅 HU
        RM.CrystallisationCrucible.addRecipe1(T,16,36000,OP.dustDiv72.mat(MT.Si,1),FL.array(FL.Nitrogen.make(80000),MT.Si.liquid(U*4,false)),FL.array(ZL_FS),OP.bouleGt.mat(MT.Si,1));
        RM.CrystallisationCrucible.addRecipe1(T,16,72000,OP.dustDiv72.mat(MT.Si,1),FL.array(FL.Helium.make(20000),flList.Silane.make(4000)),FL.array(ZL_FS),ItemList.SiliconBoulePure.get(1));

//Cut Boule to plate 切割单晶硅 LU
        recipeMaps.LaserCutter.addRecipeX(T,96,2000, ST.array(OP.bouleGt.mat(MT.Si, 1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.SiliconPlate8inchT1.get(12));
        recipeMaps.LaserCutter.addRecipeX(T,512,2000, ST.array(ItemList.SiliconBoulePure.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.SiliconPlate8inchT2.get(10));

//Clean 清洗 TU
        //T1 skip clean
        RM.Bath.addRecipeX(T,0,800, ST.array( ItemList.SiliconPlate8inchT2.get(1)),FL.array(FL.DistW.make(100)/*TODO*/),FL.array(ZL_FS), ItemList.SiliconPlate8inchCleanedT2.get(1));

//Oxidize 氧化 HU
        //T1 skip oxidize
        RM.Autoclave.addRecipeX(T,512,1200, ST.array(ItemList.SiliconPlate8inchCleanedT2.get(1)),FL.array(FL.Steam.make(40000)),FL.array(ZL_FS), ItemList.SiliconPlate8inchOxidizedT2.get(1));

//Coat 涂胶 EU
        recipeMaps.WaferCoater.addRecipeX(T,30,200, ST.array(ItemList.SiliconPlate8inchT1.get(1)),FL.array(flList.DNQPhotoresist.make(100)),FL.array(ZL_FS), ItemList.SiliconPlate8inchCoatedT1.get(1));
        recipeMaps.WaferCoater.addRecipeX(T,240,200, ST.array(ItemList.SiliconPlate8inchOxidizedT2.get(1)),FL.array(flList.DNQPhotoresist.make(100)),FL.array(ZL_FS), ItemList.SiliconPlate8inchCoatedT2.get(1));

//SoftBake 固化光刻胶 HU
        //T1 skip softbake
        RM.Drying.addRecipeX(T,0,400, ST.array(ItemList.SiliconPlate8inchCoatedT2.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.SiliconPlate8inchSoftBakedT2.get(1));

//MaskAlign 光刻 EU+LU
        recipeMaps.MaskAligner.addRecipeX(T, 128, 4000, ST.array(ItemList.SiliconPlate8inchCoatedT1.get(1),ItemList.CPUPhotomask200um.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CPUWafer200um.get(1));
        recipeMaps.MaskAligner.addRecipeX(T, 256, 4000, ST.array(ItemList.SiliconPlate8inchCoatedT1.get(1),ItemList.CPUPhotomask72um.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CPUWafer72um.get(1));
        recipeMaps.MaskAligner.addRecipeX(T, 768,12000, ST.array(ItemList.SiliconPlate8inchCoatedT2.get(1),ItemList.CPUPhotomask28um.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CPUWafer28um.get(1));
        recipeMaps.MaskAligner.addRecipeX(T,1024,12000, ST.array(ItemList.SiliconPlate8inchCoatedT2.get(1),ItemList.CPUPhotomask8um.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CPUWafer8um.get(1));

        recipeMaps.MaskAligner.addRecipeX(T, 100, 3000, ST.array(ItemList.SiliconPlate8inchCoatedT1.get(1),ItemList.RAMPhotomask200um.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.RAMWafer200um.get(1));
        recipeMaps.MaskAligner.addRecipeX(T, 240, 3000, ST.array(ItemList.SiliconPlate8inchCoatedT1.get(1),ItemList.RAMPhotomask72um.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.RAMWafer72um.get(1));
        recipeMaps.MaskAligner.addRecipeX(T, 642, 9000, ST.array(ItemList.SiliconPlate8inchCoatedT2.get(1),ItemList.RAMPhotomask28um.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.RAMWafer28um.get(1));
        recipeMaps.MaskAligner.addRecipeX(T, 968, 9000, ST.array(ItemList.SiliconPlate8inchCoatedT2.get(1),ItemList.RAMPhotomask8um.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.RAMWafer8um.get(1));

        recipeMaps.MaskAligner.addRecipeX(T, 120, 3000, ST.array(ItemList.SiliconPlate8inchCoatedT1.get(1),ItemList.CircuitPartPhotomaskT3.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CircuitPartWaferT3.get(1));
        recipeMaps.MaskAligner.addRecipeX(T, 180, 3000, ST.array(ItemList.SiliconPlate8inchCoatedT1.get(1),ItemList.CircuitPartPhotomaskT4.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CircuitPartWaferT4.get(1));
        recipeMaps.MaskAligner.addRecipeX(T, 546, 9000, ST.array(ItemList.SiliconPlate8inchCoatedT2.get(1),ItemList.CircuitPartPhotomaskT5.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CircuitPartWaferT5.get(1));
        recipeMaps.MaskAligner.addRecipeX(T, 842, 9000, ST.array(ItemList.SiliconPlate8inchCoatedT2.get(1),ItemList.CircuitPartPhotomaskT6.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CircuitPartWaferT6.get(1));

//Develop 显影 TU
        RM.Bath.addRecipeX(T,0,400, ST.array(ItemList.CPUWafer200um.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.CPUWafer200umDeveloped.get(1));
        RM.Bath.addRecipeX(T,0,400, ST.array(ItemList.CPUWafer72um.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.CPUWafer72umDeveloped.get(1));
        RM.Bath.addRecipeX(T,0,1200, ST.array(ItemList.CPUWafer28um.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.CPUWafer28umDeveloped.get(1));
        RM.Bath.addRecipeX(T,0,2000, ST.array(ItemList.CPUWafer8um.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.CPUWafer8umDeveloped.get(1));

        RM.Bath.addRecipeX(T,0,400, ST.array(ItemList.RAMWafer200um.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.RAMWafer200umDeveloped.get(1));
        RM.Bath.addRecipeX(T,0,400, ST.array(ItemList.RAMWafer72um.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.RAMWafer72umDeveloped.get(1));
        RM.Bath.addRecipeX(T,0,1200, ST.array(ItemList.RAMWafer28um.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.RAMWafer28umDeveloped.get(1));
        RM.Bath.addRecipeX(T,0,2000, ST.array(ItemList.RAMWafer8um.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.RAMWafer8umDeveloped.get(1));

        RM.Bath.addRecipeX(T,0,400, ST.array(ItemList.CircuitPartWaferT3.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT3Developed.get(1));
        RM.Bath.addRecipeX(T,0,400, ST.array(ItemList.CircuitPartWaferT4.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT4Developed.get(1));
        RM.Bath.addRecipeX(T,0,1200, ST.array(ItemList.CircuitPartWaferT5.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT5Developed.get(1));
        RM.Bath.addRecipeX(T,0,2000, ST.array(ItemList.CircuitPartWaferT6.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT6Developed.get(1));

//HardBake 除胶 HU
        RM.add_smelting(ItemList.CPUWafer200umDeveloped.get(1), ItemList.CPUWafer200umHardBaked.get(1),100);
        RM.add_smelting(ItemList. CPUWafer72umDeveloped.get(1), ItemList. CPUWafer72umHardBaked.get(1),150);
        RM.add_smelting(ItemList. CPUWafer28umDeveloped.get(1), ItemList. CPUWafer28umHardBaked.get(1),200);
        RM.add_smelting(ItemList.  CPUWafer8umDeveloped.get(1), ItemList.  CPUWafer8umHardBaked.get(1),250);

        RM.add_smelting(ItemList.RAMWafer200umDeveloped.get(1),ItemList.RAMWafer200umHardBaked.get(1),100);
        RM.add_smelting(ItemList. RAMWafer72umDeveloped.get(1), ItemList.RAMWafer72umHardBaked.get(1),150);
        RM.add_smelting(ItemList. RAMWafer28umDeveloped.get(1), ItemList.RAMWafer28umHardBaked.get(1),200);
        RM.add_smelting(ItemList.  RAMWafer8umDeveloped.get(1),  ItemList.RAMWafer8umHardBaked.get(1),250);

        RM.add_smelting(ItemList.CircuitPartWaferT3Developed.get(1), ItemList.CircuitPartWaferT3HardBaked.get(1),100);
        RM.add_smelting(ItemList.CircuitPartWaferT4Developed.get(1), ItemList.CircuitPartWaferT4HardBaked.get(1),150);
        RM.add_smelting(ItemList.CircuitPartWaferT5Developed.get(1), ItemList.CircuitPartWaferT5HardBaked.get(1),200);
        RM.add_smelting(ItemList.CircuitPartWaferT6Developed.get(1), ItemList.CircuitPartWaferT6HardBaked.get(1),250);

//Dope 掺杂 TU
        RM.Lightning.addRecipeX(T,0,400, ST.array(ItemList.CPUWafer200umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CPUWafer200umDoped.get(1));
        RM.Lightning.addRecipeX(T,0,400, ST.array(ItemList.CPUWafer72umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CPUWafer72umDoped.get(1));
        RM.Lightning.addRecipeX(T,0,320, ST.array(ItemList.CPUWafer28umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CPUWafer28umDoped.get(1));
        RM.Lightning.addRecipeX(T,0,280, ST.array(ItemList.CPUWafer8umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CPUWafer8umDoped.get(1));

        RM.Lightning.addRecipeX(T,0,400, ST.array(ItemList.RAMWafer200umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.RAMWafer200umDoped.get(1));
        RM.Lightning.addRecipeX(T,0,400, ST.array(ItemList.RAMWafer72umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.RAMWafer72umDoped.get(1));
        RM.Lightning.addRecipeX(T,0,320, ST.array(ItemList.RAMWafer28umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.RAMWafer28umDoped.get(1));
        RM.Lightning.addRecipeX(T,0,280, ST.array(ItemList.RAMWafer8umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.RAMWafer8umDoped.get(1));

        RM.Lightning.addRecipeX(T,0,400, ST.array(ItemList.CircuitPartWaferT3HardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT3Doped.get(1));
        RM.Lightning.addRecipeX(T,0,400, ST.array(ItemList.CircuitPartWaferT4HardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT4Doped.get(1));
        RM.Lightning.addRecipeX(T,0,320, ST.array(ItemList.CircuitPartWaferT5HardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT5Doped.get(1));
        RM.Lightning.addRecipeX(T,0,280, ST.array(ItemList.CircuitPartWaferT6HardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT6Doped.get(1));

//Check 检测 EU
        recipeMaps.WaferTester.addRecipeX(T,60  ,400, ST.array(ItemList.CPUWafer200umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUWafer200umChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,250 ,400, ST.array(ItemList.CPUWafer72umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUWafer72umChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,780 ,400, ST.array(ItemList.CPUWafer28umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUWafer28umChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,1000,1000, ST.array(ItemList.CPUWafer8umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUWafer8umChecked.get(1));

        recipeMaps.WaferTester.addRecipeX(T,52  ,400, ST.array(ItemList.RAMWafer200umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMWafer200umChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,230 ,400, ST.array(ItemList.RAMWafer72umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMWafer72umChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,620 ,400, ST.array(ItemList.RAMWafer28umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMWafer28umChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,920,1000, ST.array(ItemList.RAMWafer8umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMWafer8umChecked.get(1));

        recipeMaps.WaferTester.addRecipeX(T,46  ,400, ST.array(ItemList.CircuitPartWaferT3Doped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CircuitPartWaferT3Checked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,200 ,400, ST.array(ItemList.CircuitPartWaferT4Doped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CircuitPartWaferT4Checked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,480 ,400, ST.array(ItemList.CircuitPartWaferT5Doped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CircuitPartWaferT5Checked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,860 ,1000, ST.array(ItemList.CircuitPartWaferT6Doped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CircuitPartWaferT6Checked.get(1));

//Cut into Die 切割 LU
        recipeMaps.LaserCutter.addRecipeX(T,120,400,new long[]{3500,1200}, ST.array(ItemList.CPUWafer200umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUDieTF3386.get(10),ItemList.CPUDieTF3386S.get(7));
        recipeMaps.LaserCutter.addRecipeX(T,250,400,new long[]{2000,800}, ST.array(ItemList.CPUWafer72umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUDieTF3586.get(12),ItemList.CPUDieTF3586S.get(5));
        recipeMaps.LaserCutter.addRecipeX(T,720,400,new long[]{2500,900} , ST.array(ItemList.CPUWafer28umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUDieGT1000.get(12),ItemList.CPUDieGT1090.get(5));
        recipeMaps.LaserCutter.addRecipeX(T,1000,1000,new long[]{1400,550} , ST.array(ItemList.CPUWafer8umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUDieGT2000.get(12),ItemList.CPUDieGT2090.get(5));

        recipeMaps.LaserCutter.addRecipeX(T,80 ,400,new long[]{5000}, ST.array(ItemList.RAMWafer200umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMDie2K.get(40));
        recipeMaps.LaserCutter.addRecipeX(T,210,400,new long[]{3000}, ST.array(ItemList.RAMWafer72umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMDie32K.get(40));
        recipeMaps.LaserCutter.addRecipeX(T,700,400,new long[]{4500}, ST.array(ItemList.RAMWafer28umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMDie256K.get(40));
        recipeMaps.LaserCutter.addRecipeX(T,940,1000,new long[]{4000}, ST.array(ItemList.RAMWafer8umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMDie2M.get(40));

        recipeMaps.LaserCutter.addRecipeX(T,64 ,400,new long[]{4000,1000}, ST.array(ItemList.CircuitPartWaferT3Checked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), IL.Circuit_Part_Advanced.get(40),IL.Circuit_Part_Good.get(10));
        recipeMaps.LaserCutter.addRecipeX(T,170,400,new long[]{2400,1000}, ST.array(ItemList.CircuitPartWaferT4Checked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), IL.Circuit_Part_Elite.get(40),IL.Circuit_Part_Advanced.get(10));
        recipeMaps.LaserCutter.addRecipeX(T,640,400,new long[]{3200,1500}, ST.array(ItemList.CircuitPartWaferT5Checked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), IL.Circuit_Part_Master.get(40),IL.Circuit_Part_Elite.get(10));
        recipeMaps.LaserCutter.addRecipeX(T,860,1000,new long[]{2600,1900}, ST.array(ItemList.CircuitPartWaferT6Checked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), IL.Circuit_Part_Ultimate.get(40),IL.Circuit_Part_Master.get(10));

//Packaging 封装 EU
        recipeMaps.Assembler.addRecipeX(F,90 ,80,ST.array(ItemList.CPUBoardT1.get(1),ItemList.CPUDieTF3386.get(1)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CPUTF3386.get(1));
        recipeMaps.Assembler.addRecipeX(F,128,80,ST.array(ItemList.CPUBoardT1.get(1),ItemList.CPUDieTF3386S.get(1)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CPUTF3386S.get(1));
        recipeMaps.Assembler.addRecipeX(F,320,80,ST.array(ItemList.CPUBoardT1.get(1),ItemList.CPUDieTF3586.get(1)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CPUTF3586.get(1));
        recipeMaps.Assembler.addRecipeX(F,350,80,ST.array(ItemList.CPUBoardT1.get(1),ItemList.CPUDieTF3586S.get(1)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CPUTF3586S.get(1));

        recipeMaps.Assembler.addRecipeX(F,42 ,80 ,ST.array(ST.tag(0),ItemList.RAMBoardT1.get(1),ItemList.RAMDie2K.get(4)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.RAMBar2K4.get(1));
        recipeMaps.Assembler.addRecipeX(F,50 ,300,ST.array(ST.tag(1),ItemList.RAMBoardT1.get(1),ItemList.RAMDie2K.get(8)),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.RAMBar2K8.get(1));
        recipeMaps.Assembler.addRecipeX(F,128,80 ,ST.array(ST.tag(0),ItemList.RAMBoardT1.get(1),ItemList.RAMDie32K.get(4)),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.RAMBar32K4.get(1));
        recipeMaps.Assembler.addRecipeX(F,150,300,ST.array(ST.tag(1),ItemList.RAMBoardT1.get(1),ItemList.RAMDie32K.get(8)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.RAMBar32K8.get(1));

//Assemble 组装电脑 EU
        //TF3386
        recipeMaps.Assembler.addRecipeX(F,16,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8K", 8),ItemList.CoilT1.get(4),ItemList.ResistanceT1.get(20),ItemList.CapacitorT1.get(32)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386.get(1));
        recipeMaps.Assembler.addRecipeX(F,16,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8K", 8),ItemList.CoilT1.get(4),ItemList.ResistanceT2.get(8),ItemList.CapacitorT1.get(32)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386.get(1));
        recipeMaps.Assembler.addRecipeX(F,16,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8K", 8),ItemList.CoilT1.get(4),ItemList.ResistanceT1.get(20),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386.get(1));
        recipeMaps.Assembler.addRecipeX(F,16,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8K", 8),ItemList.CoilT1.get(4),ItemList.ResistanceT2.get(8),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386.get(1));

        recipeMaps.Assembler.addRecipeX(F,16,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16K", 4),ItemList.CoilT1.get(4),ItemList.ResistanceT1.get(20),ItemList.CapacitorT1.get(32)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386.get(1));
        recipeMaps.Assembler.addRecipeX(F,16,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16K", 4),ItemList.CoilT1.get(4),ItemList.ResistanceT2.get(8),ItemList.CapacitorT1.get(32)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386.get(1));
        recipeMaps.Assembler.addRecipeX(F,16,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16K", 4),ItemList.CoilT1.get(4),ItemList.ResistanceT1.get(20),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386.get(1));
        recipeMaps.Assembler.addRecipeX(F,16,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16K", 4),ItemList.CoilT1.get(4),ItemList.ResistanceT2.get(8),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386.get(1));

        recipeMaps.Assembler.addRecipeX(F,16,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM128K", 1),ItemList.CoilT1.get(4),ItemList.ResistanceT1.get(20),ItemList.CapacitorT1.get(32)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386.get(1));
        recipeMaps.Assembler.addRecipeX(F,16,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM128K", 1),ItemList.CoilT1.get(4),ItemList.ResistanceT2.get(8),ItemList.CapacitorT1.get(32)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386.get(1));
        recipeMaps.Assembler.addRecipeX(F,16,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM128K", 1),ItemList.CoilT1.get(4),ItemList.ResistanceT1.get(20),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386.get(1));
        recipeMaps.Assembler.addRecipeX(F,16,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM128K", 1),ItemList.CoilT1.get(4),ItemList.ResistanceT2.get(8),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386.get(1));
        //TF3386S
        recipeMaps.Assembler.addRecipeX(F,24,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8K", 8),ItemList.CoilT1.get(4),ItemList.ResistanceT1.get(16),ItemList.CapacitorT1.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386S.get(1));
        recipeMaps.Assembler.addRecipeX(F,24,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8K", 8),ItemList.CoilT1.get(4),ItemList.ResistanceT2.get(6),ItemList.CapacitorT1.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386S.get(1));
        recipeMaps.Assembler.addRecipeX(F,24,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8K", 8),ItemList.CoilT1.get(4),ItemList.ResistanceT1.get(16),ItemList.CapacitorT2.get(12)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386S.get(1));
        recipeMaps.Assembler.addRecipeX(F,24,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8K", 8),ItemList.CoilT1.get(4),ItemList.ResistanceT2.get(6),ItemList.CapacitorT2.get(12)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386S.get(1));

        recipeMaps.Assembler.addRecipeX(F,24,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16K", 4),ItemList.CoilT1.get(4),ItemList.ResistanceT1.get(16),ItemList.CapacitorT1.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386S.get(1));
        recipeMaps.Assembler.addRecipeX(F,24,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16K", 4),ItemList.CoilT1.get(4),ItemList.ResistanceT2.get(6),ItemList.CapacitorT1.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386S.get(1));
        recipeMaps.Assembler.addRecipeX(F,24,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16K", 4),ItemList.CoilT1.get(4),ItemList.ResistanceT1.get(16),ItemList.CapacitorT2.get(12)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386S.get(1));
        recipeMaps.Assembler.addRecipeX(F,24,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16K", 4),ItemList.CoilT1.get(4),ItemList.ResistanceT2.get(6),ItemList.CapacitorT2.get(12)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386S.get(1));

        recipeMaps.Assembler.addRecipeX(F,24,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM128K", 1),ItemList.CoilT1.get(4),ItemList.ResistanceT1.get(16),ItemList.CapacitorT1.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386S.get(1));
        recipeMaps.Assembler.addRecipeX(F,24,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM128K", 1),ItemList.CoilT1.get(4),ItemList.ResistanceT2.get(6),ItemList.CapacitorT1.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386S.get(1));
        recipeMaps.Assembler.addRecipeX(F,24,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM128K", 1),ItemList.CoilT1.get(4),ItemList.ResistanceT1.get(16),ItemList.CapacitorT2.get(12)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386S.get(1));
        recipeMaps.Assembler.addRecipeX(F,24,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3386S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM128K", 1),ItemList.CoilT1.get(4),ItemList.ResistanceT2.get(6),ItemList.CapacitorT2.get(12)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3386S.get(1));
        //TF3586
        recipeMaps.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM128K", 4),ItemList.CoilT1.get(6),ItemList.ResistanceT1.get(32),ItemList.CapacitorT1.get(48)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeMaps.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM128K", 4),ItemList.CoilT1.get(6),ItemList.ResistanceT2.get(16),ItemList.CapacitorT1.get(48)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeMaps.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM128K", 4),ItemList.CoilT1.get(6),ItemList.ResistanceT1.get(32),ItemList.CapacitorT2.get(24)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeMaps.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM128K", 4),ItemList.CoilT1.get(6),ItemList.ResistanceT2.get(16),ItemList.CapacitorT2.get(24)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));

        recipeMaps.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM256K", 2),ItemList.CoilT1.get(6),ItemList.ResistanceT1.get(32),ItemList.CapacitorT1.get(48)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeMaps.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM256K", 2),ItemList.CoilT1.get(6),ItemList.ResistanceT2.get(16),ItemList.CapacitorT1.get(48)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeMaps.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM256K", 2),ItemList.CoilT1.get(6),ItemList.ResistanceT1.get(32),ItemList.CapacitorT2.get(24)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeMaps.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM256K", 2),ItemList.CoilT1.get(6),ItemList.ResistanceT2.get(16),ItemList.CapacitorT2.get(24)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));

        recipeMaps.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM256K", 2),ItemList.CoilT2.get(4),ItemList.ResistanceT1.get(32),ItemList.CapacitorT1.get(48)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeMaps.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM256K", 2),ItemList.CoilT2.get(4),ItemList.ResistanceT2.get(16),ItemList.CapacitorT1.get(48)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeMaps.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM256K", 2),ItemList.CoilT2.get(4),ItemList.ResistanceT1.get(32),ItemList.CapacitorT2.get(24)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeMaps.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM256K", 2),ItemList.CoilT2.get(4),ItemList.ResistanceT2.get(16),ItemList.CapacitorT2.get(24)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));

        recipeMaps.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM1M", 1),ItemList.CoilT2.get(4),ItemList.ResistanceT1.get(32),ItemList.CapacitorT1.get(48)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeMaps.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM1M", 1),ItemList.CoilT2.get(4),ItemList.ResistanceT2.get(16),ItemList.CapacitorT1.get(48)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeMaps.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM1M", 1),ItemList.CoilT2.get(4),ItemList.ResistanceT1.get(32),ItemList.CapacitorT2.get(24)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        recipeMaps.Assembler.addRecipeX(F,70,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM1M", 1),ItemList.CoilT2.get(4),ItemList.ResistanceT2.get(16),ItemList.CapacitorT2.get(24)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586.get(1));
        //TF3586S
        recipeMaps.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM128K", 4),ItemList.CoilT1.get(6),ItemList.ResistanceT1.get(28),ItemList.CapacitorT1.get(40)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeMaps.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM128K", 4),ItemList.CoilT1.get(6),ItemList.ResistanceT2.get(12),ItemList.CapacitorT1.get(40)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeMaps.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM128K", 4),ItemList.CoilT1.get(6),ItemList.ResistanceT1.get(28),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeMaps.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM128K", 4),ItemList.CoilT1.get(6),ItemList.ResistanceT2.get(12),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));

        recipeMaps.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM256K", 2),ItemList.CoilT1.get(6),ItemList.ResistanceT1.get(28),ItemList.CapacitorT1.get(40)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeMaps.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM256K", 2),ItemList.CoilT1.get(6),ItemList.ResistanceT2.get(12),ItemList.CapacitorT1.get(40)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeMaps.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM256K", 2),ItemList.CoilT1.get(6),ItemList.ResistanceT1.get(28),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeMaps.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM256K", 2),ItemList.CoilT1.get(6),ItemList.ResistanceT2.get(12),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));

        recipeMaps.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM256K", 2),ItemList.CoilT2.get(4),ItemList.ResistanceT1.get(28),ItemList.CapacitorT1.get(40)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeMaps.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM256K", 2),ItemList.CoilT2.get(4),ItemList.ResistanceT2.get(12),ItemList.CapacitorT1.get(40)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeMaps.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM256K", 2),ItemList.CoilT2.get(4),ItemList.ResistanceT1.get(28),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeMaps.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM256K", 2),ItemList.CoilT2.get(4),ItemList.ResistanceT2.get(12),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));

        recipeMaps.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM1M", 1),ItemList.CoilT2.get(4),ItemList.ResistanceT1.get(28),ItemList.CapacitorT1.get(40)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeMaps.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM1M", 1),ItemList.CoilT2.get(4),ItemList.ResistanceT2.get(12),ItemList.CapacitorT1.get(40)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeMaps.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM1M", 1),ItemList.CoilT2.get(4),ItemList.ResistanceT1.get(28),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));
        recipeMaps.Assembler.addRecipeX(F,92,80,ST.array(IL.Circuit_Plate_Copper.get(1),ItemList.CPUTF3586S.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM1M", 1),ItemList.CoilT2.get(4),ItemList.ResistanceT2.get(12),ItemList.CapacitorT2.get(16)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerTF3586S.get(1));

        //GT1000
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM1M", 8),ItemList.CoilT2.get(8),ItemList.ResistanceT1.get(52),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM1M", 8),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM1M", 8),ItemList.CoilT3.get(16),ItemList.ResistanceT3.get(48),ItemList.CapacitorT3.get(32)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));

        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM2M", 4),ItemList.CoilT2.get(8),ItemList.ResistanceT1.get(52),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM2M", 4),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM2M", 4),ItemList.CoilT3.get(16),ItemList.ResistanceT3.get(48),ItemList.CapacitorT3.get(32)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));

        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM4M", 2),ItemList.CoilT2.get(8),ItemList.ResistanceT1.get(52),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM4M", 2),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM4M", 2),ItemList.CoilT3.get(16),ItemList.ResistanceT3.get(48),ItemList.CapacitorT3.get(32)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));

        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 1),ItemList.CoilT2.get(8),ItemList.ResistanceT1.get(52),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 1),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 1),ItemList.CoilT3.get(16),ItemList.ResistanceT3.get(48),ItemList.CapacitorT3.get(32)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));

        //GT1090
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM1M", 12),ItemList.CoilT2.get(8),ItemList.ResistanceT1.get(52),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM1M", 12),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM1M", 12),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));

        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM2M", 6),ItemList.CoilT2.get(8),ItemList.ResistanceT1.get(52),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM2M", 6),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM2M", 6),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));

        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM4M", 3),ItemList.CoilT2.get(8),ItemList.ResistanceT1.get(52),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM4M", 3),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM4M", 3),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));

        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 2),ItemList.CoilT2.get(8),ItemList.ResistanceT1.get(52),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 2),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 2),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));

        //GT2000
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 8),ItemList.CoilT2.get(12),ItemList.ResistanceT1.get(64),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 8),ItemList.CoilT2.get(12),ItemList.ResistanceT2.get(36),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 8),ItemList.CoilT3.get(4),ItemList.ResistanceT3.get(48),ItemList.CapacitorT3.get(36)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));

        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16M", 4),ItemList.CoilT2.get(12),ItemList.ResistanceT1.get(64),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16M", 4),ItemList.CoilT2.get(12),ItemList.ResistanceT2.get(36),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16M", 4),ItemList.CoilT3.get(4),ItemList.ResistanceT3.get(48),ItemList.CapacitorT3.get(36)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));

        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM32M", 2),ItemList.CoilT2.get(12),ItemList.ResistanceT1.get(64),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM32M", 2),ItemList.CoilT2.get(12),ItemList.ResistanceT2.get(36),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM32M", 2),ItemList.CoilT3.get(4),ItemList.ResistanceT3.get(48),ItemList.CapacitorT3.get(36)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));

        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM64M", 1),ItemList.CoilT2.get(12),ItemList.ResistanceT1.get(64),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM64M", 1),ItemList.CoilT2.get(12),ItemList.ResistanceT2.get(36),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM64M", 1),ItemList.CoilT3.get(4),ItemList.ResistanceT3.get(48),ItemList.CapacitorT3.get(36)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));

        //GT2090
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 12),ItemList.CoilT2.get(12),ItemList.ResistanceT1.get(64),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 12),ItemList.CoilT2.get(12),ItemList.ResistanceT2.get(36),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 12),ItemList.CoilT3.get(4),ItemList.ResistanceT2.get(48),ItemList.CapacitorT2.get(36)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));

        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16M", 6),ItemList.CoilT2.get(12),ItemList.ResistanceT1.get(64),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16M", 6),ItemList.CoilT2.get(12),ItemList.ResistanceT2.get(36),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16M", 6),ItemList.CoilT3.get(4),ItemList.ResistanceT2.get(48),ItemList.CapacitorT2.get(36)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));

        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM32M", 3),ItemList.CoilT2.get(12),ItemList.ResistanceT1.get(64),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM32M", 3),ItemList.CoilT2.get(12),ItemList.ResistanceT2.get(36),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM32M", 3),ItemList.CoilT3.get(4),ItemList.ResistanceT2.get(48),ItemList.CapacitorT2.get(36)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));

        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM64M", 2),ItemList.CoilT2.get(12),ItemList.ResistanceT1.get(64),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM64M", 2),ItemList.CoilT2.get(12),ItemList.ResistanceT2.get(36),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));
        recipeMaps.Assembler.addRecipeX(F,322,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM64M", 2),ItemList.CoilT3.get(4),ItemList.ResistanceT2.get(48),ItemList.CapacitorT2.get(36)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));




        //Boards
        recipeMaps.LaserCutter.addRecipe2(F,64,140 ,ST.tag(1),IL.Circuit_Plate_Copper.get(1),ZL_FS,ZL_FS,ItemList.RAMBoardT1.get(14));
        recipeMaps.LaserCutter.addRecipe2(F,64,400 ,ST.tag(1),IL.Circuit_Plate_Gold.get(1),ZL_FS,ZL_FS,ItemList.RAMBoardT2.get(14));
        recipeMaps.LaserCutter.addRecipe2(F,64,1000,ST.tag(1),IL.Circuit_Plate_Platinum.get(1),ZL_FS,ZL_FS,ItemList.RAMBoardT3.get(14));

        recipeMaps.LaserCutter.addRecipe2(F,64,140 ,ST.tag(0),IL.Circuit_Plate_Copper.get(1),ZL_FS,ZL_FS,ItemList.CPUBoardT1.get(25));
        recipeMaps.LaserCutter.addRecipe2(F,64,400 ,ST.tag(0),IL.Circuit_Plate_Gold.get(1),ZL_FS,ZL_FS,ItemList.CPUBoardT2.get(25));
        recipeMaps.LaserCutter.addRecipe2(F,64,1000,ST.tag(0),IL.Circuit_Plate_Platinum.get(1),ZL_FS,ZL_FS,ItemList.CPUBoardT3.get(25));



        //Computers made by Circuit
        recipeMaps.Assembler.addRecipeX(F,16,80,ST.array(OP.plate.mat(MT.SteelGalvanized,4),OP.screw.mat(MT.Steel,16),IL.Circuit_Basic.get(8)),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.ComputerBasicCircuits.get(1));
        recipeMaps.Assembler.addRecipeX(F,16,160,ST.array(OP.plate.mat(MT.SteelGalvanized,4),OP.screw.mat(MT.Steel,16),IL.Circuit_Good.get(8)),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.ComputerGoodCircuits.get(1));
        recipeMaps.Assembler.addRecipeX(F,16,320,ST.array(OP.plate.mat(MT.SteelGalvanized,4),OP.screw.mat(MT.Steel,32),IL.Circuit_Advanced.get(8)),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.ComputerAdvancedCircuits.get(1));
        recipeMaps.Assembler.addRecipeX(F,16,640,ST.array(OP.plate.mat(MT.StainlessSteel,4),OP.screw.mat(MT.Steel,32),IL.Circuit_Elite.get(8)),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.ComputerEliteCircuits.get(1));
        recipeMaps.Assembler.addRecipeX(F,32,960,ST.array(OP.plate.mat(MT.StainlessSteel,4),OP.screw.mat(MT.StainlessSteel,64),IL.Circuit_Master.get(8)),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.ComputerMasterCircuits.get(1));
        recipeMaps.Assembler.addRecipeX(F,32,1280,ST.array(OP.plate.mat(MT.TungstenSteel,4),OP.screw.mat(MT.StainlessSteel,64),IL.Circuit_Ultimate.get(8)),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.ComputerUltimateCircuits.get(1));

        //downgrading computer
        CR.shapeless(ItemList.UnderClockedNoviceComputer.get(1), new Object[]{"ktfruNoviceComputer"});
        CR.shapeless(ItemList.UnderClockedNoviceComputer.get(1),new Object[]{"ktfruModerateComputer"});
        CR.shapeless(ItemList.UnderClockedModerateComputer.get(1),new Object[]{"ktfruAdvancedComputer"});
        CR.shapeless(ItemList.UnderClockedAdvancedComputer.get(1),new Object[]{"ktfruEliteComputer"});
        CR.shapeless(ItemList.UnderClockedEliteComputer.get(1),new Object[]{"ktfruMasterComputer"});
        CR.shapeless(ItemList.UnderClockedMasterComputer.get(1),new Object[]{"ktfruUltimateComputer"});

    }
}
