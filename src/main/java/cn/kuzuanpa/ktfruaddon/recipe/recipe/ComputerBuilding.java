/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */
package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.api.fluid.flList;
import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import cn.kuzuanpa.ktfruaddon.api.material.matList;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
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
        recipeMaps.EDA.addRecipeX(T,16,36000, ST.array(IL.Circuit_Good.get(0), gRegistry.getItem(10102,0),gRegistry.getItem(10112,0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),ZL_FS,ItemList.CPUPhotomask200um.get(1));
        recipeMaps.EDA.addRecipeX(T,16,18000, ST.array(IL.Circuit_Good.get(0),IL.Circuit_Part_Good.get(0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),FL.array(ZL_FS),ItemList.CircuitPartPhotomaskT3.get(1));
        recipeMaps.EDA.addRecipeX(T,16,27000, ST.array(IL.Circuit_Good.get(0),gRegistry.getItem(1,0),gRegistry.getItem(6007,0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),FL.array(ZL_FS),ItemList.RAMPhotomask200um.get(1));
        recipeMaps.EDA.addRecipeX(T,16,18000, ST.array(IL.Circuit_Good.get(0),OP.dust.mat(MT.Si, 0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),FL.array(ZL_FS),ItemList.DiodePhotomask200um.get(1));

        recipeMaps.EDA.addRecipeX(T,16,72000, ST.array(IL.Circuit_Advanced.get(0), gRegistry.getItem(10103,0),gRegistry.getItem(10113,0),gRegistry.getItem(10042,0),ItemList.CPUTF3386S.get(0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),ZL_FS,ItemList.CPUPhotomask72um.get(1));
        recipeMaps.EDA.addRecipeX(T,16,36000, ST.array(IL.Circuit_Advanced.get(0),IL.Circuit_Part_Advanced.get(0),ItemList.ResistanceT2.get(1),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),FL.array(ZL_FS),ItemList.CircuitPartPhotomaskT4.get(1));
        recipeMaps.EDA.addRecipeX(T,16,54000, ST.array(IL.Circuit_Advanced.get(0),gRegistry.getItem(11,0),gRegistry.getItem(6019,0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),FL.array(ZL_FS),ItemList.RAMPhotomask72um.get(1));
        recipeMaps.EDA.addRecipeX(T,16,36000, ST.array(IL.Circuit_Advanced.get(0),ItemList.SiliconBoulePure.get(0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),FL.array(ZL_FS),ItemList.DiodePhotomask28um.get(1));


        recipeMaps.EDA.addRecipeX(T,16,144000, ST.array(IL.Circuit_Elite.get(0), gRegistry.getItem(10104,0),gRegistry.getItem(10114,0),gRegistry.getItem(10043,0),ItemList.CPUTF3586S.get(0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),ZL_FS,ItemList.CPUPhotomask28um.get(1));
        recipeMaps.EDA.addRecipeX(T,16, 72000, ST.array(IL.Circuit_Elite.get(0),IL.Circuit_Part_Elite.get(0),ItemList.ResistanceT2.get(1),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),FL.array(ZL_FS),ItemList.CircuitPartPhotomaskT5.get(1));
        recipeMaps.EDA.addRecipeX(T,16,108000, ST.array(IL.Circuit_Elite.get(0),ST.make(MD.AE,"item.ItemMultiMaterial",0,10),gRegistry.getItem(6019,0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),FL.array(ZL_FS),ItemList.RAMPhotomask28um.get(1));


        recipeMaps.EDA.addRecipeX(T,16,288000, ST.array(IL.Circuit_Master.get(0), gRegistry.getItem(10105,0),gRegistry.getItem(10115,0),gRegistry.getItem(10044,0),ItemList.CPUGT1090.get(0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),ZL_FS,ItemList.CPUPhotomask8um.get(1));
        recipeMaps.EDA.addRecipeX(T,16,144000, ST.array(IL.Circuit_Master.get(0),IL.Circuit_Part_Master.get(0),ItemList.ResistanceT2.get(1),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),FL.array(ZL_FS),ItemList.CircuitPartPhotomaskT6.get(1));
        recipeMaps.EDA.addRecipeX(T,16,216000, ST.array(IL.Circuit_Master.get(0),ST.make(MD.AE,"item.ItemMultiMaterial",0,23),ST.make(MD.AE,"item.ItemMultiMaterial",0,24),ST.make(MD.AE,"item.ItemMultiMaterial",0,22),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),FL.array(ZL_FS),ItemList.RAMPhotomask8um.get(1));

//TODO Photomask 3660vX
        //recipeMaps.EDA.addRecipeX(T,16,288000, ST.array(IL.Circuit_Master.get(0), gRegistry.getItem(10105,0),gRegistry.getItem(10115,0),gRegistry.getItem(10044,0),ItemList.CPUGT1090.get(0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),ZL_FS,ItemList.CPUPhotomask400nm.get(1));
        //recipeMaps.EDA.addRecipeX(T,16,216000, ST.array(IL.Circuit_Master.get(0),ST.make(MD.AE,"item.ItemMultiMaterial",0,23),ST.make(MD.AE,"item.ItemMultiMaterial",0,24),ST.make(MD.AE,"item.ItemMultiMaterial",0,22),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),FL.array(ZL_FS),ItemList.RAMPhotomask400nm.get(1));

        //recipeMaps.EDA.addRecipeX(T,16,288000, ST.array(IL.Circuit_Master.get(0), gRegistry.getItem(10105,0),gRegistry.getItem(10115,0),gRegistry.getItem(10044,0),ItemList.CPUGT1090.get(0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),ZL_FS,ItemList.CPUPhotomask80nm.get(1));
        //recipeMaps.EDA.addRecipeX(T,16,216000, ST.array(IL.Circuit_Master.get(0),ST.make(MD.AE,"item.ItemMultiMaterial",0,23),ST.make(MD.AE,"item.ItemMultiMaterial",0,24),ST.make(MD.AE,"item.ItemMultiMaterial",0,22),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),FL.array(ZL_FS),ItemList.RAMPhotomask80nm.get(1));

        //recipeMaps.EDA.addRecipeX(T,16,288000, ST.array(IL.Circuit_Master.get(0), gRegistry.getItem(10105,0),gRegistry.getItem(10115,0),gRegistry.getItem(10044,0),ItemList.CPUGT1090.get(0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),ZL_FS,ItemList.CPUPhotomask32nm.get(1));
        //recipeMaps.EDA.addRecipeX(T,16,216000, ST.array(IL.Circuit_Master.get(0),ST.make(MD.AE,"item.ItemMultiMaterial",0,23),ST.make(MD.AE,"item.ItemMultiMaterial",0,24),ST.make(MD.AE,"item.ItemMultiMaterial",0,22),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),FL.array(ZL_FS),ItemList.RAMPhotomask32nm.get(1));

        //recipeMaps.EDA.addRecipeX(T,16,288000, ST.array(IL.Circuit_Master.get(0), gRegistry.getItem(10105,0),gRegistry.getItem(10115,0),gRegistry.getItem(10044,0),ItemList.CPUGT1090.get(0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),ZL_FS,ItemList.CPUPhotomask14nm.get(1));
        //recipeMaps.EDA.addRecipeX(T,16,216000, ST.array(IL.Circuit_Master.get(0),ST.make(MD.AE,"item.ItemMultiMaterial",0,23),ST.make(MD.AE,"item.ItemMultiMaterial",0,24),ST.make(MD.AE,"item.ItemMultiMaterial",0,22),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),FL.array(ZL_FS),ItemList.RAMPhotomask14nm.get(1));

        //recipeMaps.EDA.addRecipeX(T,16,288000, ST.array(IL.Circuit_Master.get(0), gRegistry.getItem(10105,0),gRegistry.getItem(10115,0),gRegistry.getItem(10044,0),ItemList.CPUGT1090.get(0),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),ZL_FS,ItemList.InterLayerPhotomask32nm.get(1));
        //recipeMaps.EDA.addRecipeX(T,16,216000, ST.array(IL.Circuit_Master.get(0),ST.make(MD.AE,"item.ItemMultiMaterial",0,23),ST.make(MD.AE,"item.ItemMultiMaterial",0,24),ST.make(MD.AE,"item.ItemMultiMaterial",0,22),OP.paneGlass.mat(MT.Black,1)), FL.array(MT.HF.gas(U100,F)),FL.array(ZL_FS),ItemList.InterLayerPhotomask14nm.get(1));

//Purify Silicon
    //Method1
        recipeMaps.HeatMixer.addRecipe2(F,80,10,OP.dust.mat(MT.Si,1),OP.dust.mat(MT.Mg,2),ZL_FS,ZL_FS,matList.MagnesiumSilicide.getDust(1));
        recipeMaps.HeatMixer.addRecipe1(F,120,60,OP.dust.mat(MT.NaCl,1),FL.array(MT.NH3.gas(U,false),FL.Water.make(1000)),ZL_FS,OP.dust.mat(MT.NaOH,1),matList.AmmoniumChloride.getDust(1));
        RM           .Mixer    .addRecipe2(F,150,20,OP.dust.mat(matList.MagnesiumSilicide.mat, 3),matList.AmmoniumChloride.getDust(12),ZL_FS,FL.array(MT.NH3.gas(12*U,false),flList.Silane.make(3000)),OP.dust.mat(MT.MgCl2,6));
    //Method2
        recipeMaps.HeatMixer.addRecipe0(F,290,10,FL.array(MT.H2SiF6.liquid(3*U,false)),FL.array(flList.SiliconTetrafluoride.make(1000),MT.HF.gas(2*U,false)),ZL_IS);
        RM           .Mixer    .addRecipe2(F,380,20,OP.dust.mat(MT.Na,1),OP.dust.mat(MT.Al,1),FL.array(FL.Hydrogen.make(2000)),FL.array(flList.SodiumAluminate.make(1000)),OP.dust.mat(matList.SodiumAluminiumHydride.mat, 1));
        recipeMaps.HeatMixer.addRecipe1(F,410,40,matList.SodiumAluminiumHydride.getDust(1),FL.array(flList.SiliconTetrafluoride.make(1000)),FL.array(flList.Silane.make(1000)), OP.dust.mat(MT.Na3AlF6,1));

//Make Boule 制作单晶硅 HU
        RM.CrystallisationCrucible.addRecipe1(T,16,36000,OP.dustDiv72.mat(MT.Si,1),FL.array(FL.Nitrogen.make(80000),MT.Si.liquid(U*4,false)),FL.array(ZL_FS),OP.bouleGt.mat(MT.Si,1));
        RM.CrystallisationCrucible.addRecipe1(T,16,36000,OP.dustDiv72.mat(MT.Si,1),FL.array(FL.Helium.make(20000),MT.Si.liquid(U*4,false)),FL.array(ZL_FS),OP.bouleGt.mat(MT.Si,1));
        RM.CrystallisationCrucible.addRecipe1(T,16,72000, new long[]{5000},OP.dustDiv72.mat(MT.Si,1),FL.array(FL.Helium.make(80000),flList.Silane.make(4000)),FL.array(ZL_FS),ItemList.SiliconBoulePure.get(1));
        RM.CrystallisationCrucible.addRecipe1(T,16,72000,OP.dustDiv72.mat(MT.Si,1),FL.array(MT.Kr.gas(20*U,false),flList.Silane.make(4000)),FL.array(ZL_FS),ItemList.SiliconBoulePure.get(1));

        recipeMaps.HeatMixer.addRecipe1(F,1440,120,OP.dust.mat(MT.Mo, 4),FL.array(FL.Oxygen.make(1000)),ZL_FS,matList.MolybdenumOxide.getDust(2));

        RM.CrystallisationCrucible.addRecipe1(T,16,144000, new long[]{5000},OP.dustDiv72.mat(matList.MolybdenumOxide.get(),1),FL.array(FL.Helium.make(80000),flList.MolybdenumOxide.make(4000)),FL.array(ZL_FS),ItemList.MoO2Boule.get(1));
        RM.CrystallisationCrucible.addRecipe1(T,16,144000,OP.dustDiv72.mat(matList.MolybdenumOxide.get(),1),FL.array(MT.Kr.gas(20*U,false),flList.MolybdenumOxide.make(4000)),FL.array(ZL_FS),ItemList.MoO2Boule.get(1));


//Cut Boule to plate 切割单晶硅 LU
        recipeMaps.LaserCutter.addRecipeX(T,96,2000, ST.array(OP.bouleGt.mat(MT.Si, 1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.SiliconPlateT1.get(11));
        recipeMaps.LaserCutter.addRecipeX(T,96,6000, ST.array(ItemList.SiliconBoulePure.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.SiliconPlateT2.get(9));

        recipeMaps.LaserCutter.addRecipeX(T,2048,2000, ST.array(ItemList.MoO2Boule.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.MoO2PlateT1.get(9));
        recipeMaps.LaserCutter.addRecipeX(T,2048,6000, ST.array(ItemList.MoO2BoulePure.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.MoO2PlateT2.get(9));

//Clean 清洗 TU
        //T1 skip clean
        RM.Bath.addRecipeX(T,0,800, ST.array( ItemList.SiliconPlateT2.get(1)),FL.array(FL.DistW.make(100)/*TODO*/),FL.array(ZL_FS), ItemList.SiliconPlateCleanedT2.get(1));

        RM.Bath.addRecipeX(T,0,1220, ST.array( ItemList.MoO2PlateT1.get(1)),FL.array(FL.DistW.make(100)/*TODO*/),FL.array(ZL_FS), ItemList.MoO2PlateCleanedT1.get(1));
        RM.Bath.addRecipeX(T,0,1460, ST.array( ItemList.MoO2PlateT2.get(1)),FL.array(FL.DistW.make(100)/*TODO*/),FL.array(ZL_FS), ItemList.MoO2PlateCleanedT2.get(1));

//Oxidize 氧化 HU
        //T1 skip oxidize
        RM.Autoclave.addRecipeX(T,0,1200, ST.array(ItemList.SiliconPlateCleanedT2.get(1)),FL.array(FL.Steam.make(40000)),FL.array(ZL_FS), ItemList.SiliconPlateOxidizedT2.get(1));

        RM.Autoclave.addRecipeX(T,0,1600, ST.array(ItemList.MoO2PlateCleanedT1.get(1)),FL.array(FL.Steam.make(40000)),FL.array(ZL_FS), ItemList.MoO2PlateOxidizedT1.get(1));
        RM.Autoclave.addRecipeX(T,0,2200, ST.array(ItemList.MoO2PlateCleanedT2.get(1)),FL.array(FL.Steam.make(40000)),FL.array(ZL_FS), ItemList.MoO2PlateOxidizedT2.get(1));

//Coat 涂胶 EU
        recipeMaps.WaferCoater.addRecipeX(T,30,200, ST.array(ItemList.SiliconPlateT1.get(1)),FL.array(flList.DNQPhotoresist.make(100)),FL.array(ZL_FS), ItemList.SiliconPlateCoatedT1.get(1));
        recipeMaps.WaferCoater.addRecipeX(T,260,200, ST.array(ItemList.SiliconPlateOxidizedT2.get(1)),FL.array(flList.DNQPhotoresist.make(100)),FL.array(ZL_FS), ItemList.SiliconPlateCoatedT2.get(1));

        recipeMaps.WaferCoater.addRecipeX(T,2050,200, ST.array(ItemList.MoO2PlateOxidizedT1.get(1)),FL.array(flList.PMMAPhotoresist.make(100)),FL.array(ZL_FS), ItemList.MoO2PlateCoatedT2.get(1));
        recipeMaps.WaferCoater.addRecipeX(T,8200,200, ST.array(ItemList.MoO2PlateOxidizedT2.get(1)),FL.array(flList.PMMAPhotoresist.make(100)),FL.array(ZL_FS), ItemList.MoO2PlateCoatedT2.get(1));

//SoftBake 固化光刻胶 HU
        //T1 skip softbake
        RM.Drying.addRecipeX(T,16,400, ST.array(ItemList.SiliconPlateCoatedT2.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.SiliconPlateSoftBakedT2.get(1));

        RM.Drying.addRecipeX(T,16,600, ST.array(ItemList.MoO2PlateCoatedT1.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.MoO2PlateSoftBakedT1.get(1));
        RM.Drying.addRecipeX(T,16,600, ST.array(ItemList.MoO2PlateCoatedT2.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.MoO2PlateSoftBakedT2.get(1));

//MaskAlign 光刻 EU+LU
        recipeMaps.MaskAligner.addRecipeX(T,   128, 4000, ST.array(ItemList.SiliconPlateCoatedT1   .get(1),ItemList.CPUPhotomask200um.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CPUWafer200um.get(1));
        recipeMaps.MaskAligner.addRecipeX(T,   256, 4000, ST.array(ItemList.SiliconPlateCoatedT1   .get(1),ItemList.CPUPhotomask72um .get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CPUWafer72um .get(1));
        recipeMaps.MaskAligner.addRecipeX(T,   768,12000, ST.array(ItemList.SiliconPlateSoftBakedT2.get(1),ItemList.CPUPhotomask28um .get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CPUWafer28um .get(1));
        recipeMaps.MaskAligner.addRecipeX(T,  1024,12000, ST.array(ItemList.SiliconPlateSoftBakedT2.get(1),ItemList.CPUPhotomask8um  .get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CPUWafer8um  .get(1));
        recipeMaps.MaskAligner.addRecipeX(T,  4096,16000, ST.array(ItemList.SiliconPlateSoftBakedT2.get(1),ItemList.CPUPhotomask400nm.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CPUWafer400nm.get(1));
        recipeMaps.MaskAligner.addRecipeX(T, 16384,16000, ST.array(ItemList.SiliconPlateSoftBakedT1.get(1),ItemList.CPUPhotomask80nm .get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CPUWafer80nm .get(1));
        recipeMaps.MaskAligner.addRecipeX(T, 65536,24000, ST.array(ItemList.SiliconPlateSoftBakedT2.get(1),ItemList.CPUPhotomask32nm .get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CPUWafer32nm .get(1));
        recipeMaps.MaskAligner.addRecipeX(T,262144,24000, ST.array(ItemList.SiliconPlateSoftBakedT2.get(1),ItemList.CPUPhotomask14nm .get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CPUWafer14nm .get(1));

        recipeMaps.MaskAligner.addRecipeX(T,    100,  3000, ST.array(ItemList.SiliconPlateCoatedT1   .get(1),ItemList.RAMPhotomask200um.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.RAMWafer200um.get(1));
        recipeMaps.MaskAligner.addRecipeX(T,    240,  3000, ST.array(ItemList.SiliconPlateCoatedT1   .get(1),ItemList.RAMPhotomask72um .get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.RAMWafer72um .get(1));
        recipeMaps.MaskAligner.addRecipeX(T,    642,  7000, ST.array(ItemList.SiliconPlateSoftBakedT2.get(1),ItemList.RAMPhotomask28um .get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.RAMWafer28um .get(1));
        recipeMaps.MaskAligner.addRecipeX(T,    968,  7000, ST.array(ItemList.SiliconPlateSoftBakedT2.get(1),ItemList.RAMPhotomask8um  .get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.RAMWafer8um  .get(1));
        recipeMaps.MaskAligner.addRecipeX(T,   3276, 10000, ST.array(ItemList.SiliconPlateSoftBakedT2.get(1),ItemList.RAMPhotomask400nm.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.RAMWafer400nm.get(1));
        recipeMaps.MaskAligner.addRecipeX(T,  13107, 10000, ST.array(ItemList.SiliconPlateSoftBakedT1.get(1),ItemList.RAMPhotomask80nm .get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.RAMWafer80nm .get(1));
        recipeMaps.MaskAligner.addRecipeX(T,  52428, 16000, ST.array(ItemList.SiliconPlateSoftBakedT2.get(1),ItemList.RAMPhotomask32nm .get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.RAMWafer32nm .get(1));
        recipeMaps.MaskAligner.addRecipeX(T, 209715, 16000, ST.array(ItemList.SiliconPlateSoftBakedT2.get(1),ItemList.RAMPhotomask14nm .get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.RAMWafer14nm .get(1));

        recipeMaps.MaskAligner.addRecipeX(T, 120, 3000, ST.array(ItemList.SiliconPlateCoatedT1.get(1),ItemList.CircuitPartPhotomaskT3.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CircuitPartWaferT3.get(1));
        recipeMaps.MaskAligner.addRecipeX(T, 180, 3000, ST.array(ItemList.SiliconPlateCoatedT1.get(1),ItemList.CircuitPartPhotomaskT4.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CircuitPartWaferT4.get(1));
        recipeMaps.MaskAligner.addRecipeX(T, 546, 9000, ST.array(ItemList.SiliconPlateSoftBakedT2.get(1),ItemList.CircuitPartPhotomaskT5.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CircuitPartWaferT5.get(1));
        recipeMaps.MaskAligner.addRecipeX(T, 842, 9000, ST.array(ItemList.SiliconPlateSoftBakedT2.get(1),ItemList.CircuitPartPhotomaskT6.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.CircuitPartWaferT6.get(1));
        //Interlayer
        recipeMaps.MaskAligner.addRecipeX(T,1024,12000, ST.array(ItemList.MoO2PlateSoftBakedT1.get(1),ItemList.InterLayerPhotomask32nm.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.InterLayerWafer32nm.get(1));
        recipeMaps.MaskAligner.addRecipeX(T,4906,12000, ST.array(ItemList.MoO2PlateSoftBakedT2.get(1),ItemList.InterLayerPhotomask14nm.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.InterLayerWafer14nm.get(1));

        recipeMaps.MaskAligner.addRecipeX(T, 180, 3000, ST.array(ItemList.SiliconPlateCoatedT1.get(1),ItemList.DiodePhotomask200um.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.DiodeWafer200um.get(1));
        recipeMaps.MaskAligner.addRecipeX(T, 842, 9000, ST.array(ItemList.SiliconPlateSoftBakedT2.get(1),ItemList.DiodePhotomask28um.get(0)), FL.array(ZL_FS), FL.array(ZL_FS), ItemList.DiodeWafer28um.get(1));

//Develop 显影 TU
        RM.Bath.addRecipeX(T,0, 400, ST.array(ItemList.CPUWafer200um.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.CPUWafer200umDeveloped.get(1));
        RM.Bath.addRecipeX(T,0, 400, ST.array(ItemList.CPUWafer72um.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.CPUWafer72umDeveloped.get(1));
        RM.Bath.addRecipeX(T,0,1200, ST.array(ItemList.CPUWafer28um.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.CPUWafer28umDeveloped.get(1));
        RM.Bath.addRecipeX(T,0,1200, ST.array(ItemList.CPUWafer8um.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.CPUWafer8umDeveloped.get(1));
        RM.Bath.addRecipeX(T,0,1200, ST.array(ItemList.CPUWafer8um  .get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.CPUWafer8umDeveloped.get(1));
        RM.Bath.addRecipeX(T,0,2000, ST.array(ItemList.CPUWafer400nm.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.CPUWafer400nmDeveloped.get(1));
        RM.Bath.addRecipeX(T,0,2000, ST.array(ItemList.CPUWafer80nm .get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.CPUWafer80nmDeveloped.get(1));
        RM.Bath.addRecipeX(T,0,2000, ST.array(ItemList.CPUWafer32nm .get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.CPUWafer32nmDeveloped.get(1));

        RM.Bath.addRecipeX(T,0, 400, ST.array(ItemList.RAMWafer200um.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.RAMWafer200umDeveloped.get(1));
        RM.Bath.addRecipeX(T,0, 400, ST.array(ItemList.RAMWafer72um.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.RAMWafer72umDeveloped.get(1));
        RM.Bath.addRecipeX(T,0,1200, ST.array(ItemList.RAMWafer28um.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.RAMWafer28umDeveloped.get(1));
        RM.Bath.addRecipeX(T,0,1200, ST.array(ItemList.RAMWafer8um.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.RAMWafer8umDeveloped.get(1));
        RM.Bath.addRecipeX(T,0,1200, ST.array(ItemList.RAMWafer8um  .get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.RAMWafer8umDeveloped.get(1));
        RM.Bath.addRecipeX(T,0,2000, ST.array(ItemList.RAMWafer400nm.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.RAMWafer400nmDeveloped.get(1));
        RM.Bath.addRecipeX(T,0,2000, ST.array(ItemList.RAMWafer80nm .get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.RAMWafer80nmDeveloped.get(1));
        RM.Bath.addRecipeX(T,0,2000, ST.array(ItemList.RAMWafer32nm .get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.RAMWafer32nmDeveloped.get(1));

        RM.Bath.addRecipeX(T,0,400, ST.array(ItemList.CircuitPartWaferT3.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT3Developed.get(1));
        RM.Bath.addRecipeX(T,0,400, ST.array(ItemList.CircuitPartWaferT4.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT4Developed.get(1));
        RM.Bath.addRecipeX(T,0,1200, ST.array(ItemList.CircuitPartWaferT5.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT5Developed.get(1));
        RM.Bath.addRecipeX(T,0,1200, ST.array(ItemList.CircuitPartWaferT6.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT6Developed.get(1));

        RM.Bath.addRecipeX(T,0,2000, ST.array(ItemList.InterLayerWafer32nm.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.InterLayerWafer32nmDeveloped.get(1));
        RM.Bath.addRecipeX(T,0,2000, ST.array(ItemList.InterLayerWafer14nm.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.InterLayerWafer14nmDeveloped.get(1));

        RM.Bath.addRecipeX(T,0,400 , ST.array(ItemList.DiodeWafer200um.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList.DiodeWafer200umDeveloped.get(1));
        RM.Bath.addRecipeX(T,0,1200, ST.array(ItemList. DiodeWafer28um.get(1)),FL.array(flList.NegativeColloid.make(100)),FL.array(ZL_FS), ItemList. DiodeWafer28umDeveloped.get(1));

//HardBake 除胶 HU
        RM.add_smelting(ItemList.CPUWafer200umDeveloped.get(1),ItemList.CPUWafer200umHardBaked.get(1),100);
        RM.add_smelting(ItemList. CPUWafer72umDeveloped.get(1),ItemList. CPUWafer72umHardBaked.get(1),150);
        RM.add_smelting(ItemList. CPUWafer28umDeveloped.get(1),ItemList. CPUWafer28umHardBaked.get(1),200);
        RM.add_smelting(ItemList.  CPUWafer8umDeveloped.get(1),ItemList.  CPUWafer8umHardBaked.get(1),250);
        RM.add_smelting(ItemList.CPUWafer400nmDeveloped.get(1),ItemList.CPUWafer400nmHardBaked.get(1),250);
        RM.add_smelting(ItemList. CPUWafer80nmDeveloped.get(1),ItemList. CPUWafer80nmHardBaked.get(1),250);
        RM.add_smelting(ItemList. CPUWafer32nmDeveloped.get(1),ItemList. CPUWafer32nmHardBaked.get(1),250);
        RM.add_smelting(ItemList. CPUWafer14nmDeveloped.get(1),ItemList. CPUWafer14nmHardBaked.get(1),250);

        RM.add_smelting(ItemList.RAMWafer200umDeveloped.get(1),ItemList.RAMWafer200umHardBaked.get(1),100);
        RM.add_smelting(ItemList. RAMWafer72umDeveloped.get(1),ItemList. RAMWafer72umHardBaked.get(1),150);
        RM.add_smelting(ItemList. RAMWafer28umDeveloped.get(1),ItemList. RAMWafer28umHardBaked.get(1),200);
        RM.add_smelting(ItemList.  RAMWafer8umDeveloped.get(1),ItemList.  RAMWafer8umHardBaked.get(1),250);
        RM.add_smelting(ItemList.RAMWafer400nmDeveloped.get(1),ItemList.RAMWafer400nmHardBaked.get(1),250);
        RM.add_smelting(ItemList. RAMWafer80nmDeveloped.get(1),ItemList. RAMWafer80nmHardBaked.get(1),250);
        RM.add_smelting(ItemList. RAMWafer32nmDeveloped.get(1),ItemList. RAMWafer32nmHardBaked.get(1),250);
        RM.add_smelting(ItemList. RAMWafer14nmDeveloped.get(1),ItemList. RAMWafer14nmHardBaked.get(1),250);

        RM.add_smelting(ItemList.CircuitPartWaferT3Developed.get(1), ItemList.CircuitPartWaferT3HardBaked.get(1),100);
        RM.add_smelting(ItemList.CircuitPartWaferT4Developed.get(1), ItemList.CircuitPartWaferT4HardBaked.get(1),150);
        RM.add_smelting(ItemList.CircuitPartWaferT5Developed.get(1), ItemList.CircuitPartWaferT5HardBaked.get(1),200);
        RM.add_smelting(ItemList.CircuitPartWaferT6Developed.get(1), ItemList.CircuitPartWaferT6HardBaked.get(1),250);

        RM.add_smelting(ItemList.InterLayerWafer32nmDeveloped.get(1), ItemList.InterLayerWafer32nmHardBaked.get(1),200);
        RM.add_smelting(ItemList.InterLayerWafer14nmDeveloped.get(1), ItemList.InterLayerWafer14nmHardBaked.get(1),200);

        RM.add_smelting(ItemList.DiodeWafer200umDeveloped.get(1), ItemList.DiodeWafer200umHardBaked.get(1),200);
        RM.add_smelting(ItemList. DiodeWafer28umDeveloped.get(1), ItemList. DiodeWafer28umHardBaked.get(1),250);

//Dope 掺杂 TU
        RM.Lightning.addRecipeX(T,32,400, ST.array(ItemList.CPUWafer200umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CPUWafer200umDoped.get(1));
        RM.Lightning.addRecipeX(T,32,400, ST.array(ItemList.CPUWafer72umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CPUWafer72umDoped.get(1));
        RM.Lightning.addRecipeX(T,32,320, ST.array(ItemList.CPUWafer28umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CPUWafer28umDoped.get(1));
        RM.Lightning.addRecipeX(T,32,280, ST.array(ItemList.CPUWafer8umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CPUWafer8umDoped.get(1));
        RM.Lightning.addRecipeX(T,32,280, ST.array(ItemList.CPUWafer400nmHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CPUWafer400nmDoped.get(1));
        RM.Lightning.addRecipeX(T,32,460, ST.array(ItemList. CPUWafer80nmHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList. CPUWafer80nmDoped.get(1));
        RM.Lightning.addRecipeX(T,32,870, ST.array(ItemList. CPUWafer32nmHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList. CPUWafer32nmDoped.get(1));
        RM.Lightning.addRecipeX(T,32,870, ST.array(ItemList. CPUWafer14nmHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList. CPUWafer14nmDoped.get(1));

        RM.Lightning.addRecipeX(T,32,400, ST.array(ItemList.RAMWafer200umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.RAMWafer200umDoped.get(1));
        RM.Lightning.addRecipeX(T,32,400, ST.array(ItemList.RAMWafer72umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.RAMWafer72umDoped.get(1));
        RM.Lightning.addRecipeX(T,32,320, ST.array(ItemList.RAMWafer28umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.RAMWafer28umDoped.get(1));
        RM.Lightning.addRecipeX(T,32,280, ST.array(ItemList.RAMWafer8umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.RAMWafer8umDoped.get(1));
        RM.Lightning.addRecipeX(T,32,280, ST.array(ItemList.RAMWafer400nmHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.RAMWafer400nmDoped.get(1));
        RM.Lightning.addRecipeX(T,32,460, ST.array(ItemList. RAMWafer80nmHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList. RAMWafer80nmDoped.get(1));
        RM.Lightning.addRecipeX(T,32,870, ST.array(ItemList. RAMWafer32nmHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList. RAMWafer32nmDoped.get(1));
        RM.Lightning.addRecipeX(T,32,870, ST.array(ItemList. RAMWafer14nmHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList. RAMWafer14nmDoped.get(1));

        RM.Lightning.addRecipeX(T,32,400, ST.array(ItemList.CircuitPartWaferT3HardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT3Doped.get(1));
        RM.Lightning.addRecipeX(T,32,400, ST.array(ItemList.CircuitPartWaferT4HardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT4Doped.get(1));
        RM.Lightning.addRecipeX(T,32,320, ST.array(ItemList.CircuitPartWaferT5HardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT5Doped.get(1));
        RM.Lightning.addRecipeX(T,32,280, ST.array(ItemList.CircuitPartWaferT6HardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.CircuitPartWaferT6Doped.get(1));

        RM.Lightning.addRecipeX(T,32,870, ST.array(ItemList.InterLayerWafer32nmHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.InterLayerWafer32nmDoped.get(1));
        RM.Lightning.addRecipeX(T,32,870, ST.array(ItemList.InterLayerWafer14nmHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.InterLayerWafer14nmDoped.get(1));

        RM.Lightning.addRecipeX(T,32,400, ST.array(ItemList.DiodeWafer200umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList.DiodeWafer200umDoped.get(1));
        RM.Lightning.addRecipeX(T,32,320, ST.array(ItemList. DiodeWafer28umHardBaked.get(1),OP.dustDiv72.mat(MT.B,1),OP.dustDiv72.mat(MT.P,1)),FL.array(FL.DistW.make(100)),FL.array(ZL_FS), ItemList. DiodeWafer28umDoped.get(1));

//Check 检测 EU
        recipeMaps.WaferTester.addRecipeX(T,60  ,400, ST.array(ItemList.CPUWafer200umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUWafer200umChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,150 ,400, ST.array(ItemList.CPUWafer72umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUWafer72umChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,250 ,400, ST.array(ItemList.CPUWafer28umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUWafer28umChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,800, 800, ST.array(ItemList.CPUWafer8umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUWafer8umChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,3600,9500, ST.array(ItemList.CPUWafer400nmDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUWafer400nmChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,3600,13200, ST.array(ItemList. CPUWafer80nmDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList. CPUWafer80nmChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,3600,16700, ST.array(ItemList. CPUWafer32nmDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList. CPUWafer32nmChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,3600,18800, ST.array(ItemList. CPUWafer14nmDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList. CPUWafer14nmChecked.get(1));

        recipeMaps.WaferTester.addRecipeX(T,52  ,400, ST.array(ItemList.RAMWafer200umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMWafer200umChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,140 ,400, ST.array(ItemList.RAMWafer72umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMWafer72umChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,210 ,400, ST.array(ItemList.RAMWafer28umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMWafer28umChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,670, 500, ST.array(ItemList.RAMWafer8umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMWafer8umChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,1500,1500, ST.array(ItemList.RAMWafer400nmDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMWafer400nmChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,1500,2000, ST.array(ItemList. RAMWafer80nmDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList. RAMWafer80nmChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,1500,2500, ST.array(ItemList. RAMWafer32nmDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList. RAMWafer32nmChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,1500,3000, ST.array(ItemList. RAMWafer14nmDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList. RAMWafer14nmChecked.get(1));

        recipeMaps.WaferTester.addRecipeX(T,46  ,400, ST.array(ItemList.CircuitPartWaferT3Doped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CircuitPartWaferT3Checked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,120 ,400, ST.array(ItemList.CircuitPartWaferT4Doped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CircuitPartWaferT4Checked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,200 ,400, ST.array(ItemList.CircuitPartWaferT5Doped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CircuitPartWaferT5Checked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,660 ,1000, ST.array(ItemList.CircuitPartWaferT6Doped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CircuitPartWaferT6Checked.get(1));

        recipeMaps.WaferTester.addRecipeX(T,900 ,4000, ST.array(ItemList.InterLayerWafer32nmDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.InterLayerWafer32nmChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,900 ,5000, ST.array(ItemList.InterLayerWafer14nmDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.InterLayerWafer14nmChecked.get(1));

        recipeMaps.WaferTester.addRecipeX(T,120 ,400, ST.array(ItemList.DiodeWafer200umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.DiodeWafer200umChecked.get(1));
        recipeMaps.WaferTester.addRecipeX(T,200 ,400, ST.array(ItemList. DiodeWafer28umDoped.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList. DiodeWafer28umChecked.get(1));

//Cut into Die 切割 LU
        recipeMaps.LaserCutter.addRecipeX(T, 120, 400,new long[]{3500,1200},    ST.array(ItemList.CPUWafer200umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUDieTF3386.get(10),ItemList.CPUDieTF3386S.get(7));
        recipeMaps.LaserCutter.addRecipeX(T, 250, 400,new long[]{2000,800},     ST.array(ItemList. CPUWafer72umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUDieTF3586.get(12),ItemList.CPUDieTF3586S.get(5));
        recipeMaps.LaserCutter.addRecipeX(T, 500, 400,new long[]{2500,900},     ST.array(ItemList. CPUWafer28umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUDieGT1000.get(12),ItemList.CPUDieGT1090.get(5));
        recipeMaps.LaserCutter.addRecipeX(T,1000,1000,new long[]{1700,650},     ST.array(ItemList.  CPUWafer8umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUDieGT2000.get(12),ItemList.CPUDieGT2090.get(5));
        recipeMaps.LaserCutter.addRecipeX(T,800,10000,new long[]{1400,450},     ST.array(ItemList.CPUWafer400nmChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUDieGT3660  .get(12),ItemList.CPUDieGT3680  .get(5));
        recipeMaps.LaserCutter.addRecipeX(T,800,24000,new long[]{1000,300,1600},ST.array(ItemList. CPUWafer80nmChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUDieGT3660v2.get( 8),ItemList.CPUDieGT3680v2.get(3));
        recipeMaps.LaserCutter.addRecipeX(T,780,56000,new long[]{ 700,150,1200},ST.array(ItemList. CPUWafer32nmChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUDieGT3660v3.get( 8),ItemList.CPUDieGT3680v3.get(3));
        recipeMaps.LaserCutter.addRecipeX(T,760,72000,new long[]{ 400,100,800}, ST.array(ItemList. CPUWafer14nmChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.CPUDieGT3660v4.get( 8),ItemList.CPUDieGT3680v4.get(3));

        recipeMaps.LaserCutter.addRecipeX(T,80 ,400,new long[]{5000}, ST.array(ItemList.RAMWafer200umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMDie2K.get(32));
        recipeMaps.LaserCutter.addRecipeX(T,210,400,new long[]{3000}, ST.array(ItemList.RAMWafer72umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMDie32K.get(32));
        recipeMaps.LaserCutter.addRecipeX(T,480,400,new long[]{4300}, ST.array(ItemList.RAMWafer28umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMDie256K.get(32));
        recipeMaps.LaserCutter.addRecipeX(T,940,1000,new long[]{3800}, ST.array(ItemList.RAMWafer8umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMDie2M.get(32));
        recipeMaps.LaserCutter.addRecipeX(T,560,6000,new long[]{3400}, ST.array(ItemList.RAMWafer400nmChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMDie16M.get(60));
        recipeMaps.LaserCutter.addRecipeX(T,540,7000,new long[]{3000}, ST.array(ItemList. RAMWafer80nmChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMDie128M.get(60));
        recipeMaps.LaserCutter.addRecipeX(T,500,8000,new long[]{2600}, ST.array(ItemList. RAMWafer32nmChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMDie768M.get(60));
        recipeMaps.LaserCutter.addRecipeX(T,430,9000,new long[]{2100}, ST.array(ItemList. RAMWafer14nmChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.RAMDie2G.get(60));

        recipeMaps.LaserCutter.addRecipeX(T,64 ,400,new long[]{4000,1000}, ST.array(ItemList.CircuitPartWaferT3Checked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), IL.Circuit_Part_Advanced.get(40),IL.Circuit_Part_Good.get(10));
        recipeMaps.LaserCutter.addRecipeX(T,170,400,new long[]{2400,1000}, ST.array(ItemList.CircuitPartWaferT4Checked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), IL.Circuit_Part_Elite.get(40),IL.Circuit_Part_Advanced.get(10));
        recipeMaps.LaserCutter.addRecipeX(T,440,400,new long[]{3200,1500}, ST.array(ItemList.CircuitPartWaferT5Checked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), IL.Circuit_Part_Master.get(40),IL.Circuit_Part_Elite.get(10));
        recipeMaps.LaserCutter.addRecipeX(T,860,1000,new long[]{2600,1900}, ST.array(ItemList.CircuitPartWaferT6Checked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), IL.Circuit_Part_Ultimate.get(40),IL.Circuit_Part_Master.get(10));

        recipeMaps.LaserCutter.addRecipeX(T,170,4000,new long[]{4800,4800}, ST.array(ItemList.InterLayerWafer32nmChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.InterLayerT1.get(21));
        recipeMaps.LaserCutter.addRecipeX(T,170,5000,new long[]{4800,4800}, ST.array(ItemList.InterLayerWafer14nmChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.InterLayerT2.get(21));

        recipeMaps.LaserCutter.addRecipeX(T,170,400,new long[]{4800,4800}, ST.array(ItemList.DiodeWafer200umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.DiodeT2Part.get(64), ItemList.DiodeT2Part.get(24));
        recipeMaps.LaserCutter.addRecipeX(T,440,400,new long[]{3200,3200}, ST.array(ItemList. DiodeWafer28umChecked.get(1)),FL.array(ZL_FS),FL.array(ZL_FS), ItemList.DiodeT3Part.get(64), ItemList.DiodeT3Part.get(24));

//Packaging 封装 EU
    //CPU
        recipeMaps.Assembler.addRecipeX(F,90 ,280,ST.array(ItemList.CPUBoardT1.get(1),ItemList.CPUDieTF3386 .get(1)),FL.array(MT.SolderingAlloy.liquid(U8,F)),ZL_FS,ItemList.CPUTF3386 .get(1));
        recipeMaps.Assembler.addRecipeX(F,128,280,ST.array(ItemList.CPUBoardT1.get(1),ItemList.CPUDieTF3386S.get(1)),FL.array(MT.SolderingAlloy.liquid(U8,F)),ZL_FS,ItemList.CPUTF3386S.get(1));
        recipeMaps.Assembler.addRecipeX(F,200,280,ST.array(ItemList.CPUBoardT1.get(1),ItemList.CPUDieTF3586 .get(1)),FL.array(MT.SolderingAlloy.liquid(U8,F)),ZL_FS,ItemList.CPUTF3586 .get(1));
        recipeMaps.Assembler.addRecipeX(F,240,280,ST.array(ItemList.CPUBoardT1.get(1),ItemList.CPUDieTF3586S.get(1)),FL.array(MT.SolderingAlloy.liquid(U8,F)),ZL_FS,ItemList.CPUTF3586S.get(1));
        recipeMaps.Assembler.addRecipeX(F,260,280,ST.array(ItemList.CPUBoardT2.get(1),ItemList.CPUDieGT1000.get(1)),FL.array(MT.SolderingAlloy.liquid(U8,F)),ZL_FS,ItemList.CPUGT1000.get(1));
        recipeMaps.Assembler.addRecipeX(F,280,280,ST.array(ItemList.CPUBoardT2.get(1),ItemList.CPUDieGT1090.get(1)),FL.array(MT.SolderingAlloy.liquid(U8,F)),ZL_FS,ItemList.CPUGT1090.get(1));
        recipeMaps.Assembler.addRecipeX(F,360,280,ST.array(ItemList.CPUBoardT2.get(1),ItemList.CPUDieGT2000.get(1)),FL.array(MT.SolderingAlloy.liquid(U8,F)),ZL_FS,ItemList.CPUGT2000.get(1));
        recipeMaps.Assembler.addRecipeX(F,420,280,ST.array(ItemList.CPUBoardT2.get(1),ItemList.CPUDieGT2090.get(1)),FL.array(MT.SolderingAlloy.liquid(U8,F)),ZL_FS,ItemList.CPUGT2090.get(1));

        recipeMaps.Assembler.addRecipeX(F,420,3200,ST.array(ItemList.CPUBoardT3.get(1),ItemList.CPUDieGT3660  .get(1)),FL.array(MT.SolderingAlloy.liquid(U8,F)),ZL_FS,ItemList.CPUGT3660  .get(1));
        recipeMaps.Assembler.addRecipeX(F,520,3700,ST.array(ItemList.CPUBoardT3.get(1),ItemList.CPUDieGT3660v2.get(1)),FL.array(MT.SolderingAlloy.liquid(U8,F)),ZL_FS,ItemList.CPUGT3660v2.get(1));
        recipeMaps.Assembler.addRecipeX(F,600,4500,ST.array(ItemList.CPUBoardT3.get(1),ItemList.CPUDieGT3660v3.get(1)),FL.array(MT.SolderingAlloy.liquid(U8,F)),ZL_FS,ItemList.CPUGT3660v3.get(1));
        recipeMaps.Assembler.addRecipeX(F,420,7200,ST.array(ItemList.CPUBoardT3.get(1),ItemList.CPUDieGT3660v4.get(1),ItemList.InterLayerT1.get(1)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CPUGT3660v4.get(1));

        recipeMaps.Assembler.addRecipeX(F,460,3600 ,ST.array(ST.tag(0), ItemList.CPUBoardT3.get(1),ItemList.CPUDieGT3680  .get(1)),FL.array(MT.SolderingAlloy.liquid(U8,F)),ZL_FS,ItemList.CPUGT3680  .get(1));
        recipeMaps.Assembler.addRecipeX(F,500,7800 ,ST.array(ST.tag(1), ItemList.CPUBoardT3.get(1),ItemList.CPUDieGT3680  .get(2)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CPUGT3699  .get(1));
        recipeMaps.Assembler.addRecipeX(F,500,4000 ,ST.array(ST.tag(0), ItemList.CPUBoardT3.get(1),ItemList.CPUDieGT3680v2.get(1)),FL.array(MT.SolderingAlloy.liquid(U8,F)),ZL_FS,ItemList.CPUGT3680v2.get(1));
        recipeMaps.Assembler.addRecipeX(F,580,8200 ,ST.array(ST.tag(1), ItemList.CPUBoardT3.get(1),ItemList.CPUDieGT3680v2.get(2)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CPUGT3699v2.get(1));
        recipeMaps.Assembler.addRecipeX(F,560,5200 ,ST.array(ST.tag(0), ItemList.CPUBoardT3.get(1),ItemList.CPUDieGT3680v3.get(1)),FL.array(MT.SolderingAlloy.liquid(U8,F)),ZL_FS,ItemList.CPUGT3680v3.get(1));
        recipeMaps.Assembler.addRecipeX(F,620,10800,ST.array(ST.tag(1), ItemList.CPUBoardT3.get(1),ItemList.CPUDieGT3680v3.get(2)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CPUGT3699v3.get(1));
        recipeMaps.Assembler.addRecipeX(F,700,7800 ,ST.array(ST.tag(0), ItemList.CPUBoardT3.get(1),ItemList.CPUDieGT3680v4.get(1),ItemList.InterLayerT1.get(1)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CPUGT3680v4.get(1));
        recipeMaps.Assembler.addRecipeX(F,780,16000,ST.array(ST.tag(1), ItemList.CPUBoardT3.get(1),ItemList.CPUDieGT3680v4.get(2),ItemList.InterLayerT1.get(1)),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.CPUGT3699v4.get(1));
        
        recipeMaps.Assembler.addRecipeX(F,700,12000,ST.array(ST.tag(10), ItemList.CPUBoardT3.get(1),ItemList.CPUDieGT3680v3.get(1),ItemList.InterLayerT1.get(1)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CPUGT3680v3E.get(1));
        recipeMaps.Assembler.addRecipeX(F,780,13000,ST.array(ST.tag(11), ItemList.CPUBoardT3.get(1),ItemList.CPUDieGT3680v3.get(2),ItemList.InterLayerT1.get(1)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CPUGT3699v3E.get(1));
        recipeMaps.Assembler.addRecipeX(F,700,18000,ST.array(ST.tag(10), ItemList.CPUBoardT3.get(1),ItemList.CPUDieGT3680v4.get(1),ItemList.InterLayerT2.get(1)),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.CPUGT3680v4E.get(1));
        recipeMaps.Assembler.addRecipeX(F,780,20000,ST.array(ST.tag(11), ItemList.CPUBoardT3.get(1),ItemList.CPUDieGT3680v4.get(2),ItemList.InterLayerT2.get(1)),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.CPUGT3699v4E.get(1));

    //RAM
        recipeMaps.Assembler.addRecipeX(F,42 ,80 ,ST.array(ST.tag(0),ItemList.RAMBoardT1.get(1),ItemList.RAMDie2K  .get(4 )),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.RAMBar2K4 .get(1));
        recipeMaps.Assembler.addRecipeX(F,50 ,300,ST.array(ST.tag(1),ItemList.RAMBoardT1.get(1),ItemList.RAMDie2K  .get(8 )),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.RAMBar2K8 .get(1));

        recipeMaps.Assembler.addRecipeX(F,128,80 ,ST.array(ST.tag(0),ItemList.RAMBoardT1.get(1),ItemList.RAMDie32K .get(4 )),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.RAMBar32K4.get(1));
        recipeMaps.Assembler.addRecipeX(F,150,300,ST.array(ST.tag(1),ItemList.RAMBoardT1.get(1),ItemList.RAMDie32K .get(8 )),FL.array(MT.SolderingAlloy.liquid(U ,F)),ZL_FS,ItemList.RAMBar32K8.get(1));

        recipeMaps.Assembler.addRecipeX(F,180,80 ,ST.array(ST.tag(0),ItemList.RAMBoardT2.get(1),ItemList.RAMDie256K.get(4 )),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.RAMBar256K4 .get(1));
        recipeMaps.Assembler.addRecipeX(F,220,300,ST.array(ST.tag(1),ItemList.RAMBoardT2.get(1),ItemList.RAMDie256K.get(8 )),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.RAMBar256K8 .get(1));
        recipeMaps.Assembler.addRecipeX(F,220,900,ST.array(ST.tag(2),ItemList.RAMBoardT2.get(1),ItemList.RAMDie256K.get(16)),FL.array(MT.SolderingAlloy.liquid(U ,F)),ZL_FS,ItemList.RAMBar256K16.get(1));

        recipeMaps.Assembler.addRecipeX(F,260,80 ,ST.array(ST.tag(0),ItemList.RAMBoardT2.get(1),ItemList.RAMDie2M  .get(4 )),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.RAMBar2M4 .get(1));
        recipeMaps.Assembler.addRecipeX(F,280,300,ST.array(ST.tag(1),ItemList.RAMBoardT2.get(1),ItemList.RAMDie2M  .get(8 )),FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.RAMBar2M8 .get(1));
        recipeMaps.Assembler.addRecipeX(F,280,900,ST.array(ST.tag(2),ItemList.RAMBoardT2.get(1),ItemList.RAMDie2M  .get(16)),FL.array(MT.SolderingAlloy.liquid(U ,F)),ZL_FS,ItemList.RAMBar2M16.get(1));

        recipeMaps.Assembler.addRecipeX(F,500,80  ,ST.array(ST.tag(0),ItemList.RAMBoardT2.get(1),ItemList.RAMDie16M  .get(4 )),FL.array(MT.SolderingAlloy.liquid(U4  ,F)),ZL_FS,ItemList.RAMBar16M4 .get(1));
        recipeMaps.Assembler.addRecipeX(F,540,300 ,ST.array(ST.tag(1),ItemList.RAMBoardT2.get(1),ItemList.RAMDie16M  .get(8 )),FL.array(MT.SolderingAlloy.liquid(U2  ,F)),ZL_FS,ItemList.RAMBar16M8 .get(1));
        recipeMaps.Assembler.addRecipeX(F,580,900 ,ST.array(ST.tag(2),ItemList.RAMBoardT2.get(1),ItemList.RAMDie16M  .get(16)),FL.array(MT.SolderingAlloy.liquid(U   ,F)),ZL_FS,ItemList.RAMBar16M16.get(1));
        recipeMaps.Assembler.addRecipeX(F,560,3200,ST.array(ST.tag(3),ItemList.RAMBoardT2.get(1),ItemList.RAMDie16M  .get(32)),FL.array(MT.SolderingAlloy.liquid(2*U ,F)),ZL_FS,ItemList.RAMBar16M32.get(1));

        recipeMaps.Assembler.addRecipeX(F,900,80  ,ST.array(ST.tag(0),ItemList.RAMBoardT2.get(1),ItemList.RAMDie128M  .get(4 )),FL.array(MT.SolderingAlloy.liquid(U4  ,F)),ZL_FS,ItemList.RAMBar128M4 .get(1));
        recipeMaps.Assembler.addRecipeX(F,950,300 ,ST.array(ST.tag(1),ItemList.RAMBoardT2.get(1),ItemList.RAMDie128M  .get(8 )),FL.array(MT.SolderingAlloy.liquid(U2  ,F)),ZL_FS,ItemList.RAMBar128M8 .get(1));
        recipeMaps.Assembler.addRecipeX(F,990,900 ,ST.array(ST.tag(2),ItemList.RAMBoardT2.get(1),ItemList.RAMDie128M  .get(16)),FL.array(MT.SolderingAlloy.liquid(U   ,F)),ZL_FS,ItemList.RAMBar128M16.get(1));
        recipeMaps.Assembler.addRecipeX(F,970,3200,ST.array(ST.tag(3),ItemList.RAMBoardT2.get(1),ItemList.RAMDie128M  .get(32)),FL.array(MT.SolderingAlloy.liquid(2*U ,F)),ZL_FS,ItemList.RAMBar128M32.get(1));

        recipeMaps.Assembler.addRecipeX(F,2000,80  ,ST.array(ST.tag(0),ItemList.RAMBoardT2.get(1),ItemList.RAMDie768M  .get(4 )),FL.array(MT.SolderingAlloy.liquid(U4 ,F)),ZL_FS,ItemList.RAMBar768M4 .get(1));
        recipeMaps.Assembler.addRecipeX(F,2300,300 ,ST.array(ST.tag(1),ItemList.RAMBoardT2.get(1),ItemList.RAMDie768M  .get(8 )),FL.array(MT.SolderingAlloy.liquid(U2 ,F)),ZL_FS,ItemList.RAMBar768M8 .get(1));
        recipeMaps.Assembler.addRecipeX(F,2700,900 ,ST.array(ST.tag(2),ItemList.RAMBoardT2.get(1),ItemList.RAMDie768M  .get(16)),FL.array(MT.SolderingAlloy.liquid(U  ,F)),ZL_FS,ItemList.RAMBar768M16.get(1));
        recipeMaps.Assembler.addRecipeX(F,2500,3200,ST.array(ST.tag(3),ItemList.RAMBoardT2.get(1),ItemList.RAMDie768M  .get(32)),FL.array(MT.SolderingAlloy.liquid(2*U,F)),ZL_FS,ItemList.RAMBar768M32.get(1));

        recipeMaps.Assembler.addRecipeX(F,4000,80  ,ST.array(ST.tag(0),ItemList.RAMBoardT2.get(1),ItemList.RAMDie2G  .get(4 )),FL.array(MT.SolderingAlloy.liquid(U4  ,F)),ZL_FS,ItemList.RAMBar2G4 .get(1));
        recipeMaps.Assembler.addRecipeX(F,4400,300 ,ST.array(ST.tag(1),ItemList.RAMBoardT2.get(1),ItemList.RAMDie2G  .get(8 )),FL.array(MT.SolderingAlloy.liquid(U2  ,F)),ZL_FS,ItemList.RAMBar2G8 .get(1));
        recipeMaps.Assembler.addRecipeX(F,4900,900 ,ST.array(ST.tag(2),ItemList.RAMBoardT2.get(1),ItemList.RAMDie2G  .get(16)),FL.array(MT.SolderingAlloy.liquid(U   ,F)),ZL_FS,ItemList.RAMBar2G16.get(1));
        recipeMaps.Assembler.addRecipeX(F,4800,3200,ST.array(ST.tag(3),ItemList.RAMBoardT2.get(1),ItemList.RAMDie2G  .get(32)),FL.array(MT.SolderingAlloy.liquid(2*U ,F)),ZL_FS,ItemList.RAMBar2G32.get(1));

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
        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM1M", 8),ItemList.CoilT2.get(8),ItemList.ResistanceT1.get(52),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));
        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM1M", 8),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));
        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM1M", 8),ItemList.CoilT3.get(16),ItemList.ResistanceT3.get(48),ItemList.CapacitorT3.get(32)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));

        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM2M", 4),ItemList.CoilT2.get(8),ItemList.ResistanceT1.get(52),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));
        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM2M", 4),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));
        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM2M", 4),ItemList.CoilT3.get(16),ItemList.ResistanceT3.get(48),ItemList.CapacitorT3.get(32)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));

        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM4M", 2),ItemList.CoilT2.get(8),ItemList.ResistanceT1.get(52),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));
        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM4M", 2),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));
        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM4M", 2),ItemList.CoilT3.get(16),ItemList.ResistanceT3.get(48),ItemList.CapacitorT3.get(32)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));

        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 1),ItemList.CoilT2.get(8),ItemList.ResistanceT1.get(52),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));
        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 1),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));
        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 1),ItemList.CoilT3.get(16),ItemList.ResistanceT3.get(48),ItemList.CapacitorT3.get(32)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1000.get(1));

        //GT1090
        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM1M", 12),ItemList.CoilT2.get(8),ItemList.ResistanceT1.get(52),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));
        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM1M", 12),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));
        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM1M", 12),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));

        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM2M", 6),ItemList.CoilT2.get(8),ItemList.ResistanceT1.get(52),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));
        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM2M", 6),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));
        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM2M", 6),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));

        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM4M", 3),ItemList.CoilT2.get(8),ItemList.ResistanceT1.get(52),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));
        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM4M", 3),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));
        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM4M", 3),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));

        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 2),ItemList.CoilT2.get(8),ItemList.ResistanceT1.get(52),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));
        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 2),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));
        recipeMaps.Assembler.addRecipeX(F,120,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT1090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 2),ItemList.CoilT2.get(8),ItemList.ResistanceT2.get(28),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT1090.get(1));

        //GT2000
        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 8),ItemList.CoilT2.get(12),ItemList.ResistanceT1.get(64),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));
        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 8),ItemList.CoilT2.get(12),ItemList.ResistanceT2.get(36),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));
        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 8),ItemList.CoilT3.get(4),ItemList.ResistanceT3.get(48),ItemList.CapacitorT3.get(36)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));

        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16M", 4),ItemList.CoilT2.get(12),ItemList.ResistanceT1.get(64),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));
        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16M", 4),ItemList.CoilT2.get(12),ItemList.ResistanceT2.get(36),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));
        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16M", 4),ItemList.CoilT3.get(4),ItemList.ResistanceT3.get(48),ItemList.CapacitorT3.get(36)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));

        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM32M", 2),ItemList.CoilT2.get(12),ItemList.ResistanceT1.get(64),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));
        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM32M", 2),ItemList.CoilT2.get(12),ItemList.ResistanceT2.get(36),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));
        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM32M", 2),ItemList.CoilT3.get(4),ItemList.ResistanceT3.get(48),ItemList.CapacitorT3.get(36)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));

        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM64M", 1),ItemList.CoilT2.get(12),ItemList.ResistanceT1.get(64),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));
        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM64M", 1),ItemList.CoilT2.get(12),ItemList.ResistanceT2.get(36),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));
        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2000.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM64M", 1),ItemList.CoilT3.get(4),ItemList.ResistanceT3.get(48),ItemList.CapacitorT3.get(36)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2000.get(1));

        //GT2090
        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 12),ItemList.CoilT2.get(12),ItemList.ResistanceT1.get(64),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));
        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 12),ItemList.CoilT2.get(12),ItemList.ResistanceT2.get(36),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));
        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM8M", 12),ItemList.CoilT3.get(4),ItemList.ResistanceT2.get(48),ItemList.CapacitorT2.get(36)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));

        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16M", 6),ItemList.CoilT2.get(12),ItemList.ResistanceT1.get(64),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));
        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16M", 6),ItemList.CoilT2.get(12),ItemList.ResistanceT2.get(36),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));
        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM16M", 6),ItemList.CoilT3.get(4),ItemList.ResistanceT2.get(48),ItemList.CapacitorT2.get(36)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));

        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM32M", 3),ItemList.CoilT2.get(12),ItemList.ResistanceT1.get(64),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));
        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM32M", 3),ItemList.CoilT2.get(12),ItemList.ResistanceT2.get(36),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));
        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM32M", 3),ItemList.CoilT3.get(4),ItemList.ResistanceT2.get(48),ItemList.CapacitorT2.get(36)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));

        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM64M", 2),ItemList.CoilT2.get(12),ItemList.ResistanceT1.get(64),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));
        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM64M", 2),ItemList.CoilT2.get(12),ItemList.ResistanceT2.get(36),ItemList.CapacitorT2.get(28)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));
        recipeMaps.Assembler.addRecipeX(F,200,60,ST.array(IL.Circuit_Plate_Gold.get(1),ItemList.CPUGT2090.get(1), OreDictManager.INSTANCE.getFirstOre("ktfruRAM64M", 2),ItemList.CoilT3.get(4),ItemList.ResistanceT2.get(48),ItemList.CapacitorT2.get(36)),FL.array(MT.SolderingAlloy.liquid(U,F)),ZL_FS,ItemList.ComputerGT2090.get(1));

//TODO Assemble Computer 3660vX


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
        CR.shapeless(ItemList.UnderClockedModerateComputer.get(1),new Object[]{"ktfruModerateComputer"});
        CR.shapeless(ItemList.UnderClockedAdvancedComputer.get(1),new Object[]{"ktfruAdvancedComputer"});
        CR.shapeless(ItemList.UnderClockedEliteComputer.get(1),new Object[]{"ktfruEliteComputer"});
        CR.shapeless(ItemList.UnderClockedMasterComputer.get(1),new Object[]{"ktfruMasterComputer"});
        CR.shapeless(ItemList.UnderClockedUltimateComputer.get(1),new Object[]{"ktfruUltimateComputer"});

    }
}
