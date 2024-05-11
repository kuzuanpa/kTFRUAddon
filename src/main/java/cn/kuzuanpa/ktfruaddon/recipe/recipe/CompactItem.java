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
import cn.kuzuanpa.ktfruaddon.recipe.recipeManager;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.*;
import gregapi.util.CR;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.init.Items;

import static gregapi.data.CS.*;

public class CompactItem {
    public static void init(){
        MultiTileEntityRegistry gRegistry = MultiTileEntityRegistry.getRegistry("gt.multitileentity");
        MultiTileEntityRegistry kRegistry = MultiTileEntityRegistry.getRegistry("ktfru.multitileentity");

        CR.shaped(ItemList.CrucibleModelInnerLayer.get(1),CR.DEF," I "," I "," f ",'I', OP.ingot.mat(MT.RedSteel,1));
        CR.shaped(ItemList.CrucibleModelInnerLayer.get(1),CR.DEF," I "," I "," f ",'I', OP.ingot.mat(MT.BlueSteel,1));

        recipeManager.Assembler.addRecipe2(false, 372,80, OP.plateTiny.mat(matList.Ij.mat,1),OP.wireFine.mat(MT.Ti,4), FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.IntelligentCore.get(2));
        recipeManager.Assembler.addRecipe1(false,1574,80,ItemList.CPUGT3660.get(1), FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.IntelligentCore.get(2));

        recipeManager.Assembler.addRecipeX(false,16384,16384, ST.array(OP.nugget.mat(MT.DraconiumAwakened,2),OP.plateTiny.mat(MT.Ad,2),OP.wireFine.mat(MT.Terrasteel,64),OP.plate.mat(MT.VibraniumSteel,1),OP.plateGem.mat(MT.NetherStar,1)), ZL_FS,ZL_FS,ST.make(Items.skull,1,3, UT.NBT.make("SkullOwner","kuzuanpa")));

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

        recipeManager.CNCMachine.addRecipeX(F,70 ,600,ST.array(OP.stickLong.mat(MT.Bronze       ,3),ST.tag(0)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCrankShaft1.get(1), OP.dust.mat(MT.Bronze       ,1));
        recipeManager.CNCMachine.addRecipeX(F,70 ,600,ST.array(OP.stickLong.mat(MT.ArsenicCopper,3),ST.tag(0)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCrankShaft2.get(1), OP.dust.mat(MT.ArsenicCopper,1));
        recipeManager.CNCMachine.addRecipeX(F,74 ,600,ST.array(OP.stickLong.mat(MT.ArsenicBronze,3),ST.tag(0)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCrankShaft3.get(1), OP.dust.mat(MT.ArsenicBronze,1));
        recipeManager.CNCMachine.addRecipeX(F,96 ,600,ST.array(OP.stickLong.mat(MT.Steel        ,3),ST.tag(0)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCrankShaft4.get(1), OP.dust.mat(MT.Steel        ,1));
        recipeManager.CNCMachine.addRecipeX(F,128,600,ST.array(OP.stickLong.mat(MT.Invar        ,3),ST.tag(0)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCrankShaft5.get(1), OP.dust.mat(MT.Invar        ,1));
        recipeManager.CNCMachine.addRecipeX(F,172,600,ST.array(OP.stickLong.mat(MT.Ti           ,3),ST.tag(0)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCrankShaft6.get(1), OP.dust.mat(MT.Ti           ,1));
        recipeManager.CNCMachine.addRecipeX(F,256,600,ST.array(OP.stickLong.mat(MT.TungstenSteel,3),ST.tag(0)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCrankShaft7.get(1), OP.dust.mat(MT.TungstenSteel,1));
        recipeManager.CNCMachine.addRecipeX(F,320,600,ST.array(OP.stickLong.mat(MT.Ir           ,3),ST.tag(0)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCrankShaft8.get(1), OP.dust.mat(MT.Ir           ,1));

        recipeManager.CNCMachine.addRecipeX(F,70 ,200,ST.array(OP.ingotDouble.mat(MT.Bronze       ,1), OP.plateCurved.mat(MT.Bronze       ,1), OP.stick.mat(MT.Bronze       ,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCylinder1.get(1),OP.dustSmall.mat(MT.Bronze       ,4));
        recipeManager.CNCMachine.addRecipeX(F,70 ,200,ST.array(OP.ingotDouble.mat(MT.ArsenicCopper,1), OP.plateCurved.mat(MT.ArsenicCopper,1), OP.stick.mat(MT.ArsenicCopper,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCylinder2.get(1),OP.dustSmall.mat(MT.ArsenicCopper,4));
        recipeManager.CNCMachine.addRecipeX(F,74 ,200,ST.array(OP.ingotDouble.mat(MT.ArsenicBronze,1), OP.plateCurved.mat(MT.ArsenicBronze,1), OP.stick.mat(MT.ArsenicBronze,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCylinder3.get(1),OP.dustSmall.mat(MT.ArsenicBronze,4));
        recipeManager.CNCMachine.addRecipeX(F,96 ,200,ST.array(OP.ingotDouble.mat(MT.Steel        ,1), OP.plateCurved.mat(MT.Steel        ,1), OP.stick.mat(MT.Steel        ,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCylinder4.get(1),OP.dustSmall.mat(MT.Steel        ,4));
        recipeManager.CNCMachine.addRecipeX(F,128,200,ST.array(OP.ingotDouble.mat(MT.Invar        ,1), OP.plateCurved.mat(MT.Invar        ,1), OP.stick.mat(MT.Invar        ,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCylinder5.get(1),OP.dustSmall.mat(MT.Invar        ,4));
        recipeManager.CNCMachine.addRecipeX(F,172,200,ST.array(OP.ingotDouble.mat(MT.Ti           ,1), OP.plateCurved.mat(MT.Ti           ,1), OP.stick.mat(MT.Ti           ,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCylinder6.get(1),OP.dustSmall.mat(MT.Ti           ,4));
        recipeManager.CNCMachine.addRecipeX(F,256,200,ST.array(OP.ingotDouble.mat(MT.TungstenSteel,1), OP.plateCurved.mat(MT.TungstenSteel,1), OP.stick.mat(MT.TungstenSteel,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCylinder7.get(1),OP.dustSmall.mat(MT.TungstenSteel,4));
        recipeManager.CNCMachine.addRecipeX(F,320,200,ST.array(OP.ingotDouble.mat(MT.Ir           ,1), OP.plateCurved.mat(MT.Ir           ,1), OP.stick.mat(MT.Ir           ,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineCylinder8.get(1),OP.dustSmall.mat(MT.Ir           ,4));

        recipeManager.CNCMachine.addRecipeX(F,70 ,200,ST.array(OP.rotor.mat(MT.Bronze       ,1), OP.plateCurved.mat(MT.Bronze       ,2), OP.plate.mat(MT.Bronze       ,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineTurbo1.get(1),OP.dustSmall.mat(MT.Bronze       ,4));
        recipeManager.CNCMachine.addRecipeX(F,70 ,200,ST.array(OP.rotor.mat(MT.ArsenicCopper,1), OP.plateCurved.mat(MT.ArsenicCopper,2), OP.plate.mat(MT.ArsenicCopper,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineTurbo2.get(1),OP.dustSmall.mat(MT.ArsenicCopper,4));
        recipeManager.CNCMachine.addRecipeX(F,74 ,200,ST.array(OP.rotor.mat(MT.ArsenicBronze,1), OP.plateCurved.mat(MT.ArsenicBronze,2), OP.plate.mat(MT.ArsenicBronze,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineTurbo3.get(1),OP.dustSmall.mat(MT.ArsenicBronze,4));
        recipeManager.CNCMachine.addRecipeX(F,96 ,200,ST.array(OP.rotor.mat(MT.Steel        ,1), OP.plateCurved.mat(MT.Steel        ,2), OP.plate.mat(MT.Steel        ,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineTurbo4.get(1),OP.dustSmall.mat(MT.Steel        ,4));
        recipeManager.CNCMachine.addRecipeX(F,128,200,ST.array(OP.rotor.mat(MT.Invar        ,1), OP.plateCurved.mat(MT.Invar        ,2), OP.plate.mat(MT.Invar        ,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineTurbo5.get(1),OP.dustSmall.mat(MT.Invar        ,4));
        recipeManager.CNCMachine.addRecipeX(F,172,200,ST.array(OP.rotor.mat(MT.Ti           ,1), OP.plateCurved.mat(MT.Ti           ,2), OP.plate.mat(MT.Ti           ,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineTurbo6.get(1),OP.dustSmall.mat(MT.Ti           ,4));
        recipeManager.CNCMachine.addRecipeX(F,256,200,ST.array(OP.rotor.mat(MT.TungstenSteel,1), OP.plateCurved.mat(MT.TungstenSteel,2), OP.plate.mat(MT.TungstenSteel,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineTurbo7.get(1),OP.dustSmall.mat(MT.TungstenSteel,4));
        recipeManager.CNCMachine.addRecipeX(F,320,200,ST.array(OP.rotor.mat(MT.Ir           ,1), OP.plateCurved.mat(MT.Ir           ,2), OP.plate.mat(MT.Ir           ,1)),FL.array(FL.DistW.make(1000)),ZL_FS, ItemList.EngineTurbo8.get(1),OP.dustSmall.mat(MT.Ir           ,4));



    }
}

