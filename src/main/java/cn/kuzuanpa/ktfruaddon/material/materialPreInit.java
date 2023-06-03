/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.material;

import cn.kuzuanpa.ktfruaddon.material.prefix.kOreConditions;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregapi.code.TagData;
import gregapi.data.MT;
import gregapi.data.TD;
import gregapi.item.prefixitem.PrefixItem;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictPrefix;

import static cn.kuzuanpa.ktfruaddon.code.Variables.Symbols;
import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;
import static gregapi.data.CS.NUM_SUB;
import static gregapi.data.CS.U;

public class materialPreInit {
    public materialPreInit(FMLPreInitializationEvent aEvent) {
        // If you want to make yourself a new OreDict Prefix for your Component Items or similar.
        final OreDictPrefix prefixUltraPure = OreDictPrefix.createPrefix("Analytical Prue"); // This newly created OreDict Prefix is named "exampleprefix", so an Aluminium Item with this Prefix would be named "exampleprefixAluminium" in the OreDict.
        prefixUltraPure.setCategoryName("Analytical Pure"); // That is what the Creative Tab of it would be named.
        prefixUltraPure.setLocalItemName("Analytical Pure ", ""); // Generic Items will follow this naming Guideline, so for example "Small Aluminium Example" for an Aluminium Item with that Prefix.
        final TagData[] mTags = new TagData[TD.Atomic.ALL.size()];
        prefixUltraPure.setCondition(kOreConditions.tagNor(TD.Properties.INVALID_MATERIAL,TD.Properties.DONT_SHOW_THIS_COMPONENT,TD.Properties.UNUSED_MATERIAL)); // The Condition under which Items of this Prefix should generate in general. In this case TRUE to have ALL the Items.
        prefixUltraPure.add(TD.Prefix.UNIFICATABLE); // OreDict Unification can apply to this Prefix.
        //tExamplePrefix.add(gregapi.data.TD.Prefix.RECYCLABLE); // Items of this can be recycled for Resources.
        prefixUltraPure.setMaterialStats(U); // Any Item of this example Prefix has the value of 1 Material Unit (U), this is exactly equal to one Ingot/Dust/Gem.
        //tExamplePrefix.aspects(gregapi.data.TC.FABRICO, 1); // Thaumcraft Aspects related to this Prefix.
        prefixUltraPure.setStacksize(64, 16); // Sets the Maximum ItemStack Size of this Prefix to 16, and allows the Config to go as far down as 8 when people manually select a StackSize using it.

        // If you want to make your Prefix an Item
        // Creates the generic Item for the new Prefix. Assets go into "/assets/insert_your_MOD_ID_here/textures/items/materialicons". And yes, every TextureSet for every Material Type has its own Folder.
        new PrefixItem(MOD_ID, "gregtech", "ktdru.item.analytical_pure", prefixUltraPure, OreDictMaterial.MATERIAL_ARRAY);

        // If you want to make your Prefix a Block
        // Creates the generic Block for the new Prefix. Assets go into "/assets/insert_your_MOD_ID_here/textures/blocks/materialicons". And yes, every TextureSet for every Material Type has its own Folder.
        //new gregapi.block.prefixblock.PrefixBlock_(MOD_ID, MOD_ID, "example.meta.block.exampleprefix", tExamplePrefix, null, null, null, null, net.minecraft.block.material.Material.rock, net.minecraft.block.Block.soundTypeStone, gregapi.data.CS.TOOL_pickaxe, 1.5F, 4.5F, 0, 0, 999, 0, 0, 0, 1, 1, 1, false, false, false, false, true, true, true, true, true, true, false, true, true, true, gregapi.oredict.OreDictMaterial.MATERIAL_ARRAY);
//
//        // If you want to make yourself a new OreDict Material. Please look up proper IDs. So replace 32766 with a Number inside YOUR own ID Range. (you can look that up in gregapi.oredict.OreDictMaterial.java)
//        //final OreDictMaterial tExamplium = OreDictMaterial.createMaterial(30000, "Examplium", "Examplium"); // Creates a Material called "Examplium".
//        tExamplium.setTextures(gregapi.render.TextureSet.SET_METALLIC); // gives this Material the Metallic Texture Set.
//        tExamplium.setRGBa(100, 100, 200, 0); // Sets the RGBa Color of the Material. In this case some random blue Color.
//        tExamplium.put(gregapi.data.TD.Processing.SMITHABLE); // This Material is smithable like regular Metal things.
//        tExamplium.put(gregapi.data.TD.Processing.MELTING); // This Material can melt.
//        tExamplium.put(gregapi.data.TD.Processing.FURNACE); // This Material can be molten in a regular Furnace.
//        tExamplium.put(gregapi.data.TD.Processing.CENTRIFUGE); // This Material can be centrifuged to recycle it.
//        tExamplium.put(gregapi.data.TD.Compounds.DECOMPOSABLE); // This Material can be decomposed in general.
//        tExamplium.put(gregapi.data.TD.ItemGenerator.G_INGOT_MACHINE_ORES); // This Material is a typical Ingot, that can be used for Machine Parts, and the Material can be found as Ore too.
//        tExamplium.heat(2000, 4000); // This Material melts at 2000 Kelvin and Boils at 4000 Kelvin.
//        //tExamplium.qual(3 // Type 3 = The Material can be used for every Type of Tool
//        //        , 6.0 // Speed is 6.0 what is as fast as Steel at mining stuff
//        //        , 512 // Durability is 512 what equals Steel too
//          //      , 3); // Quality is 3 for Diamond Tool Level
//        tExamplium.setMcfg(0, gregapi.data.MT.Steel, U); // This Material consists out of one Unit of Steel.
//        tExamplium.setOriginalMod(MOD_DATA); // Gives your Mod the credit for creating this Material.
//        //tExamplium.aspects(gregapi.data.TC.METALLUM, 3); // Thaumcraft Aspects related to this Material.
//
        //Don't change this value after release!
        int i=22000;
        matList.AmmoniumDichromate.register(i++, "AmmoniumDichromate", "Ammonium Dichromate",456, 460, 255, 160, 51, 0,  "Cr"+NUM_SUB[2]+"O"+NUM_SUB[7]+"(NH3)"+NUM_SUB[2]);
        matList.AmmoniumDichromate.mat.put(TD.ItemGenerator.DUSTS);

        matList.AmmoniumChromicSulfate.register(i++, "AmmoniumChromicSulfate", "Ammonium Chromic Sulfate",456, 460, 72, 0, 161, 0,  "NH"+NUM_SUB[4]+"Cr(SO"+NUM_SUB[4]+")"+NUM_SUB[2]);
        matList.AmmoniumChromicSulfate.mat.put(TD.ItemGenerator.DUSTS);

        matList.AmmoniumIronIIISulfate.register(i++, "AmmoniumIronIIISulfate", "Ammonium Iron(III) Sulfate", 368, 524, 230, 220, 242, 0, "NH"+NUM_SUB[4]+"Fe(SO"+NUM_SUB[4]+")"+NUM_SUB[2]);
        matList.AmmoniumIronIIISulfate.mat.put(TD.ItemGenerator.DUSTS);

        matList.AmmoniumSulfate.register(i++, "AmmoniumSulfate", "Ammonium Sulfate",509, 510, 251, 251, 216, 2, "(NH"+NUM_SUB[4]+")"+NUM_SUB[2]+"SO"+NUM_SUB[4]);
        matList.AmmoniumSulfate.mat.put(TD.ItemGenerator.DUSTS);

        matList.CookedBauxide .register(i++, "CookedBauxide", "Cooked Bauxide", 2370, 3234, 229, 141, 0, 4, null);
        matList.CookedBauxide.mat.put(TD.ItemGenerator.DUSTS);

        matList.AcidPickledBauxide.register(i++, "CookedBauxide", "Cooked Bauxide", 2370, 3234,200, 168, 0, 4, null);
        matList.AcidPickledBauxide.mat.put(TD.ItemGenerator.DUSTS);

        matList.BauxiteRedMud.register(i++, "BauxiteRedMud", "Bauxite Red Mud", 1790, 3234, 148, 0, 12, 0,null);
        matList.BauxiteRedMud.mat.put(TD.ItemGenerator.DUSTS);

        matList.LithiumCarbonate.register(i++, "LithiumCarbonate", "Lithium Carbonate",943, 1582, 248, 244, 248, 0,  "Li"+NUM_SUB[2]+"CO"+NUM_SUB[3]);
        matList.LithiumCarbonate.mat.put(TD.ItemGenerator.DUSTS);

        matList.MetatitanicAcid.register(i++, "MetatitanicAcid", "Metatitanic Acid", 748,749, 248, 244, 248, 0, "TiO(OH)"+NUM_SUB[2]);
        matList.MetatitanicAcid.mat.put(TD.ItemGenerator.DUSTS);
        //石油底渣
        matList.OilScarp.registerC(i++,"OilScarp","Oil Scarp",90,231,12,9,2,0,null);
        matList.OilScarp.mat.put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.DUSTS);
        //甲醇
        matList.Methanol.registerC(i++,"Methanol","Methanol",-98,64,90,80,210,140,"CH"+NUM_SUB[3]+"OH");
        matList.Methanol.mat.put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //乙醇
        //Already exists in GT
        //丙醇
        matList.Propanol.registerC(i++,"Propanol","Propanol",-127,97,60,60,150,140,"CH"+NUM_SUB[2]+"CH"+NUM_SUB[2]+"CH"+NUM_SUB[3]+"OH");
        matList.Propanol.mat.put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //丙二醇
        matList.Propanediol.registerC(i++,"Propanediol","Propanediol",-60,187,60,60,150,140,"CH"+NUM_SUB[2]+"CH"+NUM_SUB[2]+"CH"+NUM_SUB[3]+"OH");
        matList.Propanediol.mat.put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //丙酮
        matList.Acetone.register(i++,"Acetone","Acetone",143,293,210,210,210,140,"C"+NUM_SUB[3]+"H"+NUM_SUB[6]+"O");
        matList.Acetone.mat.put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //乙炔
        matList.Acetylene.register(i++,"Acetylene","Acetylene",147,217,210,210,210,180,"CH"+Symbols[0]+"CH");
        matList.Acetylene.mat.put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.GASES);
        //苯
        matList.Benzene.registerC(i++,"Benzene","Benzene",5,80,255,255,130,140,"C"+NUM_SUB[6]+"H"+NUM_SUB[6]);
        matList.Benzene.mat.put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.ItemGenerator.LIQUID);
        //甲醛
        matList.Formaldehyde.registerC(i++,"Formaldehyde","Formaldehyde",-15,97,255,255,255,255,"CH"+NUM_SUB[2]+"O");
        matList.Formaldehyde.mat.put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.ItemGenerator.GASES);
        //乙醛
        matList.Acetaldehyde.registerC(i++,"Acetaldehyde","Acetaldehyde",-125,21,255,255,255,255,"O=CHCH"+NUM_SUB[3]);
        matList.Acetaldehyde.mat.put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.ItemGenerator.GASES);
        //丙醛
        matList.Propionaldehyde.registerC(i++,"Propionaldehyde","Propionaldehyde",-81,46,255,255,255,255,"O=CHCH"+NUM_SUB[2]+"CH"+NUM_SUB[3]);
        matList.Propionaldehyde.mat.put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT,TD.ItemGenerator.GASES);
        //次氯酸
        matList.HypochlorousAcid.registerC(i++,"HypochlorousAcid","Hypochlorous Acid",0,100,180,255,180,150,"HClO");
        matList.HypochlorousAcid.mat.put(TD.Properties.ACID,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //氯酸
        matList.ChloricAcid.registerC(i++,"ChloricAcid","Chloric Acid",0,41,235,255,210,210,"HClO"+NUM_SUB[3]);
        matList.ChloricAcid.mat.put(TD.Properties.ACID,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //高氯酸
        matList.PerchloricAcid.registerC(i++,"PerchloricAcid","Perchloric Acid",-112,19,255,255,255,255,"HClO"+NUM_SUB[4]);
        matList.PerchloricAcid.mat.put(TD.Properties.ACID,TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //光气
        matList.Phosgene.registerC(i++,"Phosgene","Phosgene",-128,8,240,245,255,240,"COCl"+NUM_SUB[2]);
        matList.Phosgene.mat.put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.GASES);
        //三乙基铝
        matList.TriethylAluminium.registerC(i++,"TriethylAluminium","Triethyl Aluminium",-50,128,255,255,255,255,"Al(CH"+NUM_SUB[2]+"CH"+NUM_SUB[3]+")"+NUM_SUB[3]);
        matList.TriethylAluminium.mat.put(TD.Compounds.DECOMPOSABLE,TD.Properties.FLAMMABLE,TD.Properties.BURNING,TD.ItemGenerator.DUSTS);
        //二氯甲烷
        matList.Dichloromethane.registerC(i++,"Dichloromethane","Dichloromethane",-97,39,255,255,255,255,"CH"+NUM_SUB[2]+"Cl"+NUM_SUB[2]);
        matList.Dichloromethane.mat.put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.LIQUID);
        //醋酸钙
        matList.CalciumAcetate.registerHeatDecomp(i++,"CalciumAcetate","Calcium Acetate",396+273,245,246,245,0,"CH"+NUM_SUB[3]+"COOCaOOCCH"+NUM_SUB[3],1,new matList.decomposingTarget(MT.CaCO3,1));

        matList.BPA.registerC(i++,"BPA","BPA",158,226,255,255,255,10,"CH"+NUM_SUB[3]+"C"+"(PhOH)"+NUM_SUB[2]+"CH"+NUM_SUB[3]);
        matList.BPA.mat.put(TD.Compounds.DECOMPOSABLE);
        //DPC,碳酸二苯酯
        matList.DiphenylCarbonate.registerC(i++,"DiphenylCarbonate","Diphenyl Carbonate",82,301,255,255,255,10,"PhOCOOPh");
        matList.DiphenylCarbonate.mat.put(TD.Compounds.DECOMPOSABLE);
        //PC,环氧树脂
        matList.EpoxyResin.registerC(i++,"EpoxyResin","Epoxy Resin",115,252,255,255,255,10,"(C"+NUM_SUB[3]+"H"+NUM_SUB[5]+"ClO)");
        matList.EpoxyResin.mat.put(TD.Compounds.DECOMPOSABLE,TD.ItemGenerator.DUSTS);

    }
}
