/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import cn.kuzuanpa.ktfruaddon.material.matList;
import cn.kuzuanpa.ktfruaddon.recipe.recipeMaps;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.*;
import gregapi.util.CR;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregapi.data.CS.*;

public class CompactItem {
    public static void init(){
        MultiTileEntityRegistry gRegistry = MultiTileEntityRegistry.getRegistry("gt.multitileentity");
        MultiTileEntityRegistry kRegistry = MultiTileEntityRegistry.getRegistry("ktfru.multitileentity");

        CR.shaped(ItemList.CrucibleModelInnerLayer.get(1),CR.DEF," I "," I "," f ",'I', OP.ingot.mat(MT.RedSteel,1));
        CR.shaped(ItemList.CrucibleModelInnerLayer.get(1),CR.DEF," I "," I "," f ",'I', OP.ingot.mat(MT.BlueSteel,1));

        recipeMaps.Assembler.addRecipe2(false, 372,80, OP.plateTiny.mat(matList.Ij.mat,1),OP.wireFine.mat(MT.Ti,4), FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.IntelligentCore.get(2));
        recipeMaps.Assembler.addRecipe1(false,1574,80,ItemList.CPUGT3660.get(1), FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.IntelligentCore.get(2));

        recipeMaps.Assembler.addRecipeX(false,16384,16384, ST.array(OP.nugget.mat(MT.DraconiumAwakened,2),OP.plateTiny.mat(MT.Ad,2),OP.wireFine.mat(MT.Terrasteel,64),OP.plate.mat(MT.VibraniumSteel,1),OP.plateGem.mat(MT.NetherStar,1)), ZL_FS,ZL_FS,ST.make(Items.skull,1,3, UT.NBT.make("SkullOwner","kuzuanpa")));

        RM.Extruder.addRecipe2(false,120,10, new ItemStack(Items.clay_ball,1),IL.Shape_SimpleEx_Ingot.get(0),new ItemStack(Items.brick,1));

        //GoldBlock TFC->Vanilla
        RM.Generifier.addRecipe1(false,0,1, ST.make(MD.TFC,"MetalBlock",1,8),ZL_FS,ZL_FS, OP.block.mat(MT.Au,1));

        CR.shaped(ItemList.CrucibleModelInnerLayer.get(1),CR.DEF," I "," I "," f ",'I', OP.ingot.mat(MT.RedSteel,1));


        //Engine Parts
        CR.shaped(ItemList.EngineCrankShaftManual1.get(1),CR.DEF,"f h","AAA"," w ",'A', OP.stickLong.mat(MT.Bronze       ,1));
        CR.shaped(ItemList.EngineCrankShaftManual2.get(1),CR.DEF,"f h","AAA"," w ",'A', OP.stickLong.mat(MT.ArsenicCopper,1));
        CR.shaped(ItemList.EngineCrankShaftManual3.get(1),CR.DEF,"f h","AAA"," w ",'A', OP.stickLong.mat(MT.ArsenicBronze,1));
        CR.shaped(ItemList.EngineCrankShaftManual4.get(1),CR.DEF,"f h","AAA"," w ",'A', OP.stickLong.mat(MT.Steel        ,1));
        CR.shaped(ItemList.EngineCrankShaftManual5.get(1),CR.DEF,"f h","AAA"," w ",'A', OP.stickLong.mat(MT.Invar        ,1));
        CR.shaped(ItemList.EngineCrankShaftManual6.get(1),CR.DEF,"f h","AAA"," w ",'A', OP.stickLong.mat(MT.Ti           ,1));
        CR.shaped(ItemList.EngineCrankShaftManual7.get(1),CR.DEF,"f h","AAA"," w ",'A', OP.stickLong.mat(MT.TungstenSteel,1));
        CR.shaped(ItemList.EngineCrankShaftManual8.get(1),CR.DEF,"f h","AAA"," w ",'A', OP.stickLong.mat(MT.Ir           ,1));

        CR.shaped(ItemList.EngineCylinderManual1.get(1),CR.DEF," P ","fIh"," S ",'I', OP.ingotDouble.mat(MT.Bronze       ,1),'P',OP.plateCurved.mat(MT.Bronze       ,1),'S',OP.stick.mat(MT.Bronze       ,1));
        CR.shaped(ItemList.EngineCylinderManual2.get(1),CR.DEF," P ","fIh"," S ",'I', OP.ingotDouble.mat(MT.ArsenicCopper,1),'P',OP.plateCurved.mat(MT.ArsenicCopper,1),'S',OP.stick.mat(MT.ArsenicCopper,1));
        CR.shaped(ItemList.EngineCylinderManual3.get(1),CR.DEF," P ","fIh"," S ",'I', OP.ingotDouble.mat(MT.ArsenicBronze,1),'P',OP.plateCurved.mat(MT.ArsenicBronze,1),'S',OP.stick.mat(MT.ArsenicBronze,1));
        CR.shaped(ItemList.EngineCylinderManual4.get(1),CR.DEF," P ","fIh"," S ",'I', OP.ingotDouble.mat(MT.Steel        ,1),'P',OP.plateCurved.mat(MT.Steel        ,1),'S',OP.stick.mat(MT.Steel        ,1));
        CR.shaped(ItemList.EngineCylinderManual5.get(1),CR.DEF," P ","fIh"," S ",'I', OP.ingotDouble.mat(MT.Invar        ,1),'P',OP.plateCurved.mat(MT.Invar        ,1),'S',OP.stick.mat(MT.Invar        ,1));
        CR.shaped(ItemList.EngineCylinderManual6.get(1),CR.DEF," P ","fIh"," S ",'I', OP.ingotDouble.mat(MT.Ti           ,1),'P',OP.plateCurved.mat(MT.Ti           ,1),'S',OP.stick.mat(MT.Ti           ,1));
        CR.shaped(ItemList.EngineCylinderManual7.get(1),CR.DEF," P ","fIh"," S ",'I', OP.ingotDouble.mat(MT.TungstenSteel,1),'P',OP.plateCurved.mat(MT.TungstenSteel,1),'S',OP.stick.mat(MT.TungstenSteel,1));
        CR.shaped(ItemList.EngineCylinderManual8.get(1),CR.DEF," P ","fIh"," S ",'I', OP.ingotDouble.mat(MT.Ir           ,1),'P',OP.plateCurved.mat(MT.Ir           ,1),'S',OP.stick.mat(MT.Ir           ,1));

        recipeMaps.CNCMachine.addRecipeX(F,70 ,600,ST.array(OP.stickLong.mat(MT.Bronze       ,3),ST.tag(0)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCrankShaft1.get(1), OP.dust.mat(MT.Bronze       ,1));
        recipeMaps.CNCMachine.addRecipeX(F,70 ,600,ST.array(OP.stickLong.mat(MT.ArsenicCopper,3),ST.tag(0)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCrankShaft2.get(1), OP.dust.mat(MT.ArsenicCopper,1));
        recipeMaps.CNCMachine.addRecipeX(F,74 ,600,ST.array(OP.stickLong.mat(MT.ArsenicBronze,3),ST.tag(0)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCrankShaft3.get(1), OP.dust.mat(MT.ArsenicBronze,1));
        recipeMaps.CNCMachine.addRecipeX(F,96 ,600,ST.array(OP.stickLong.mat(MT.Steel        ,3),ST.tag(0)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCrankShaft4.get(1), OP.dust.mat(MT.Steel        ,1));
        recipeMaps.CNCMachine.addRecipeX(F,128,600,ST.array(OP.stickLong.mat(MT.Invar        ,3),ST.tag(0)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCrankShaft5.get(1), OP.dust.mat(MT.Invar        ,1));
        recipeMaps.CNCMachine.addRecipeX(F,172,600,ST.array(OP.stickLong.mat(MT.Ti           ,3),ST.tag(0)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCrankShaft6.get(1), OP.dust.mat(MT.Ti           ,1));
        recipeMaps.CNCMachine.addRecipeX(F,256,600,ST.array(OP.stickLong.mat(MT.TungstenSteel,3),ST.tag(0)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCrankShaft7.get(1), OP.dust.mat(MT.TungstenSteel,1));
        recipeMaps.CNCMachine.addRecipeX(F,320,600,ST.array(OP.stickLong.mat(MT.Ir           ,3),ST.tag(0)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCrankShaft8.get(1), OP.dust.mat(MT.Ir           ,1));

        recipeMaps.CNCMachine.addRecipeX(F,70 ,200,ST.array(OP.ingotDouble.mat(MT.Bronze       ,1), OP.plateCurved.mat(MT.Bronze       ,1), OP.stick.mat(MT.Bronze       ,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCylinder1.get(1),OP.dustSmall.mat(MT.Bronze       ,4));
        recipeMaps.CNCMachine.addRecipeX(F,70 ,200,ST.array(OP.ingotDouble.mat(MT.ArsenicCopper,1), OP.plateCurved.mat(MT.ArsenicCopper,1), OP.stick.mat(MT.ArsenicCopper,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCylinder2.get(1),OP.dustSmall.mat(MT.ArsenicCopper,4));
        recipeMaps.CNCMachine.addRecipeX(F,74 ,200,ST.array(OP.ingotDouble.mat(MT.ArsenicBronze,1), OP.plateCurved.mat(MT.ArsenicBronze,1), OP.stick.mat(MT.ArsenicBronze,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCylinder3.get(1),OP.dustSmall.mat(MT.ArsenicBronze,4));
        recipeMaps.CNCMachine.addRecipeX(F,96 ,200,ST.array(OP.ingotDouble.mat(MT.Steel        ,1), OP.plateCurved.mat(MT.Steel        ,1), OP.stick.mat(MT.Steel        ,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCylinder4.get(1),OP.dustSmall.mat(MT.Steel        ,4));
        recipeMaps.CNCMachine.addRecipeX(F,128,200,ST.array(OP.ingotDouble.mat(MT.Invar        ,1), OP.plateCurved.mat(MT.Invar        ,1), OP.stick.mat(MT.Invar        ,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCylinder5.get(1),OP.dustSmall.mat(MT.Invar        ,4));
        recipeMaps.CNCMachine.addRecipeX(F,172,200,ST.array(OP.ingotDouble.mat(MT.Ti           ,1), OP.plateCurved.mat(MT.Ti           ,1), OP.stick.mat(MT.Ti           ,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCylinder6.get(1),OP.dustSmall.mat(MT.Ti           ,4));
        recipeMaps.CNCMachine.addRecipeX(F,256,200,ST.array(OP.ingotDouble.mat(MT.TungstenSteel,1), OP.plateCurved.mat(MT.TungstenSteel,1), OP.stick.mat(MT.TungstenSteel,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCylinder7.get(1),OP.dustSmall.mat(MT.TungstenSteel,4));
        recipeMaps.CNCMachine.addRecipeX(F,320,200,ST.array(OP.ingotDouble.mat(MT.Ir           ,1), OP.plateCurved.mat(MT.Ir           ,1), OP.stick.mat(MT.Ir           ,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCylinder8.get(1),OP.dustSmall.mat(MT.Ir           ,4));

        recipeMaps.CNCMachine.addRecipeX(F,70 ,200,ST.array(OP.rotor.mat(MT.Bronze       ,1), OP.plateCurved.mat(MT.Bronze       ,2), OP.plate.mat(MT.Bronze       ,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineTurbo1.get(1),OP.dustSmall.mat(MT.Bronze       ,4));
        recipeMaps.CNCMachine.addRecipeX(F,70 ,200,ST.array(OP.rotor.mat(MT.ArsenicCopper,1), OP.plateCurved.mat(MT.ArsenicCopper,2), OP.plate.mat(MT.ArsenicCopper,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineTurbo2.get(1),OP.dustSmall.mat(MT.ArsenicCopper,4));
        recipeMaps.CNCMachine.addRecipeX(F,74 ,200,ST.array(OP.rotor.mat(MT.ArsenicBronze,1), OP.plateCurved.mat(MT.ArsenicBronze,2), OP.plate.mat(MT.ArsenicBronze,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineTurbo3.get(1),OP.dustSmall.mat(MT.ArsenicBronze,4));
        recipeMaps.CNCMachine.addRecipeX(F,96 ,200,ST.array(OP.rotor.mat(MT.Steel        ,1), OP.plateCurved.mat(MT.Steel        ,2), OP.plate.mat(MT.Steel        ,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineTurbo4.get(1),OP.dustSmall.mat(MT.Steel        ,4));
        recipeMaps.CNCMachine.addRecipeX(F,128,200,ST.array(OP.rotor.mat(MT.Invar        ,1), OP.plateCurved.mat(MT.Invar        ,2), OP.plate.mat(MT.Invar        ,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineTurbo5.get(1),OP.dustSmall.mat(MT.Invar        ,4));
        recipeMaps.CNCMachine.addRecipeX(F,172,200,ST.array(OP.rotor.mat(MT.Ti           ,1), OP.plateCurved.mat(MT.Ti           ,2), OP.plate.mat(MT.Ti           ,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineTurbo6.get(1),OP.dustSmall.mat(MT.Ti           ,4));
        recipeMaps.CNCMachine.addRecipeX(F,256,200,ST.array(OP.rotor.mat(MT.TungstenSteel,1), OP.plateCurved.mat(MT.TungstenSteel,2), OP.plate.mat(MT.TungstenSteel,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineTurbo7.get(1),OP.dustSmall.mat(MT.TungstenSteel,4));
        recipeMaps.CNCMachine.addRecipeX(F,320,200,ST.array(OP.rotor.mat(MT.Ir           ,1), OP.plateCurved.mat(MT.Ir           ,2), OP.plate.mat(MT.Ir           ,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineTurbo8.get(1),OP.dustSmall.mat(MT.Ir           ,4));

        CR.shaped(ItemList.VibrateDetector.get(1),CR.DEF,"hR ","RBR"," Rw",'R', OP.ring.mat(MT.StainlessSteel,1),'B',OP.nugget.mat(MT.StainlessSteel   ,1));
        recipeMaps.Assembler.addRecipeX(F,320,200,ST.array(OP.bolt.mat(MT.Co_60,1), OP.plateDense.mat(MT.Pb           ,2)),FL.array(MT.SolderingAlloy.fluid(U,true)),ZL_FS, ItemList.Co60FlawDetectionCore.get(1));


        RM.CrystallisationCrucible.addRecipe1(T,16,72000,OP.dustDiv72.mat(MT.Steel,1),FL.array(FL.Helium.make(20000), MT.Steel.liquid(48*U,false)),FL.array(ZL_FS),ItemList.MonocrystallineSteel.get(1));
        RM.CrystallisationCrucible.addRecipe1(T,16,72000,OP.dustDiv72.mat(MT.StainlessSteel,1),FL.array(FL.Helium.make(20000), MT.StainlessSteel.liquid(48*U,false)),FL.array(ZL_FS),ItemList.MonocrystallineStainlessSteel.get(1));
        RM.CrystallisationCrucible.addRecipe1(T,16,72000,OP.dustDiv72.mat(MT.Ti,1),FL.array(FL.Helium.make(20000), MT.Ti.liquid(48*U,false)),FL.array(ZL_FS),ItemList.MonocrystallineTitanium.get(1));
        RM.CrystallisationCrucible.addRecipe1(T,16,72000,OP.dustDiv72.mat(MT.Trinitanium,1),FL.array(FL.Helium.make(20000), MT.Trinitanium.liquid(48*U,false)),FL.array(ZL_FS),ItemList.MonocrystallineTrinitanium.get(1));
        RM.CrystallisationCrucible.addRecipe1(T,16,72000,OP.dustDiv72.mat(MT.HSSG,1),FL.array(FL.Helium.make(20000), MT.HSSG.liquid(48*U,false)),FL.array(ZL_FS),ItemList.MonocrystallineHSSG.get(1));
        RM.CrystallisationCrucible.addRecipe1(T,16,72000,OP.dustDiv72.mat(MT.HSSE,1),FL.array(FL.Helium.make(20000), MT.HSSE.liquid(48*U,false)),FL.array(ZL_FS),ItemList.MonocrystallineHSSE.get(1));
        RM.CrystallisationCrucible.addRecipe1(T,16,72000,OP.dustDiv72.mat(MT.HSSS,1),FL.array(FL.Helium.make(20000), MT.HSSS.liquid(48*U,false)),FL.array(ZL_FS),ItemList.MonocrystallineHSSS.get(1));
        RM.CrystallisationCrucible.addRecipe1(T,16,72000,OP.dustDiv72.mat(MT.TungstenSteel,1),FL.array(FL.Helium.make(20000), MT.TungstenSteel.liquid(48*U,false)),FL.array(ZL_FS),ItemList.MonocrystallineTungstenSteel.get(1));
        RM.CrystallisationCrucible.addRecipe1(T,16,72000,OP.dustDiv72.mat(MT.W,1),FL.array(FL.Helium.make(20000), MT.W.liquid(48*U,false)),FL.array(ZL_FS),ItemList.MonocrystallineTungsten.get(1));
        RM.CrystallisationCrucible.addRecipe1(T,16,72000,OP.dustDiv72.mat(MT.Vibramantium,1),FL.array(FL.Helium.make(20000), MT.Vibramantium.liquid(48*U,false)),FL.array(ZL_FS),ItemList.MonocrystallineVibramantium.get(1));
    }
}























