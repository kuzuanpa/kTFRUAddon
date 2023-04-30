/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.material;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregapi.data.TD;
import gregapi.oredict.OreDictMaterial;

import static cn.kuzuanpa.ktfruaddon.code.Variables.Symbols;
import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_DATA;
import static gregapi.data.CS.NUM_SUB;
import static gregapi.data.CS.U;

public class materialPreInit {
    public materialPreInit(FMLPreInitializationEvent aEvent) {
        // If you want to make yourself a new OreDict Prefix for your Component Items or similar.
        //final OreDictPrefix tExamplePrefix = OreDictPrefix.createPrefix("kpeefix"); // This newly created OreDict Prefix is named "exampleprefix", so an Aluminium Item with this Prefix would be named "exampleprefixAluminium" in the OreDict.
        //tExamplePrefix.setCategoryName("kTFRUAddon"); // That is what the Creative Tab of it would be named.
        //tExamplePrefix.setLocalItemName("kAdd ", " kItem"); // Generic Items will follow this naming Guideline, so for example "Small Aluminium Example" for an Aluminium Item with that Prefix.
        //tExamplePrefix.setCondition(gregapi.code.ICondition.TRUE); // The Condition under which Items of this Prefix should generate in general. In this case TRUE to have ALL the Items.
        //tExamplePrefix.add(gregapi.data.TD.Prefix.UNIFICATABLE); // OreDict Unification can apply to this Prefix.
        //tExamplePrefix.add(gregapi.data.TD.Prefix.RECYCLABLE); // Items of this can be recycled for Resources.
        //tExamplePrefix.setMaterialStats(gregapi.data.CS.U); // Any Item of this example Prefix has the value of 1 Material Unit (U), this is exactly equal to one Ingot/Dust/Gem.
        //tExamplePrefix.aspects(gregapi.data.TC.FABRICO, 1); // Thaumcraft Aspects related to this Prefix.
        //tExamplePrefix.setStacksize(16, 8); // Sets the Maximum ItemStack Size of this Prefix to 16, and allows the Config to go as far down as 8 when people manually select a StackSize using it.

        // If you want to make your Prefix an Item
        // Creates the generic Item for the new Prefix. Assets go into "/assets/insert_your_MOD_ID_here/textures/items/materialicons". And yes, every TextureSet for every Material Type has its own Folder.
        //new gregapi.item.prefixitem.PrefixItem(MOD_ID, MOD_ID, "example.meta.item.exampleprefix", tExamplePrefix, gregapi.oredict.OreDictMaterial.MATERIAL_ARRAY);

        // If you want to make your Prefix a Block
        // Creates the generic Block for the new Prefix. Assets go into "/assets/insert_your_MOD_ID_here/textures/blocks/materialicons". And yes, every TextureSet for every Material Type has its own Folder.
        //new gregapi.block.prefixblock.PrefixBlock_(MOD_ID, MOD_ID, "example.meta.block.exampleprefix", tExamplePrefix, null, null, null, null, net.minecraft.block.material.Material.rock, net.minecraft.block.Block.soundTypeStone, gregapi.data.CS.TOOL_pickaxe, 1.5F, 4.5F, 0, 0, 999, 0, 0, 0, 1, 1, 1, false, false, false, false, true, true, true, true, true, true, false, true, true, true, gregapi.oredict.OreDictMaterial.MATERIAL_ARRAY);

        // If you want to make yourself a new OreDict Material. Please look up proper IDs. So replace 32766 with a Number inside YOUR own ID Range. (you can look that up in gregapi.oredict.OreDictMaterial.java)
        final OreDictMaterial tExamplium = OreDictMaterial.createMaterial(30000, "Examplium", "Examplium"); // Creates a Material called "Examplium".
        tExamplium.setTextures(gregapi.render.TextureSet.SET_METALLIC); // gives this Material the Metallic Texture Set.
        tExamplium.setRGBa(100, 100, 200, 0); // Sets the RGBa Color of the Material. In this case some random blue Color.
        tExamplium.put(gregapi.data.TD.Processing.SMITHABLE); // This Material is smithable like regular Metal things.
        tExamplium.put(gregapi.data.TD.Processing.MELTING); // This Material can melt.
        tExamplium.put(gregapi.data.TD.Processing.FURNACE); // This Material can be molten in a regular Furnace.
        tExamplium.put(gregapi.data.TD.Processing.CENTRIFUGE); // This Material can be centrifuged to recycle it.
        tExamplium.put(gregapi.data.TD.Compounds.DECOMPOSABLE); // This Material can be decomposed in general.
        tExamplium.put(gregapi.data.TD.ItemGenerator.G_INGOT_MACHINE_ORES); // This Material is a typical Ingot, that can be used for Machine Parts, and the Material can be found as Ore too.
        tExamplium.heat(2000, 4000); // This Material melts at 2000 Kelvin and Boils at 4000 Kelvin.
        //tExamplium.qual(3 // Type 3 = The Material can be used for every Type of Tool
        //        , 6.0 // Speed is 6.0 what is as fast as Steel at mining stuff
        //        , 512 // Durability is 512 what equals Steel too
          //      , 3); // Quality is 3 for Diamond Tool Level
        tExamplium.setMcfg(0, gregapi.data.MT.Steel, U); // This Material consists out of one Unit of Steel.
        tExamplium.setOriginalMod(MOD_DATA); // Gives your Mod the credit for creating this Material.
        tExamplium.aspects(gregapi.data.TC.METALLUM, 3); // Thaumcraft Aspects related to this Material.





        //Cr Producing ID=30010-30019
        //Cr2O7(NH3)2
        matList.AmmoniumDichromate.register(30011, "AmmoniumDichromate", "Ammonium Dichromate",456, 460, 255, 160, 51, 0,  "Cr"+NUM_SUB[2]+"O"+NUM_SUB[7]+"(NH3)"+NUM_SUB[2]);
        matList.AmmoniumDichromate.mat.put(TD.ItemGenerator.DUSTS);
        //NH4Cr(SO4)2
        matList.AmmoniumChromicSulfate.register(30012, "AmmoniumChromicSulfate", "Ammonium Chromic Sulfate",456, 460, 72, 0, 161, 0,  "NH"+NUM_SUB[4]+"Cr(SO"+NUM_SUB[4]+")"+NUM_SUB[2]);
        matList.AmmoniumChromicSulfate.mat.put(TD.ItemGenerator.DUSTS);
        //NH4Fe(SO4);2
        matList.AmmoniumIronIIISulfate.register(30013, "AmmoniumIronIIISulfate", "Ammonium Iron(III) Sulfate", 368, 524, 230, 220, 242, 0, "NH"+NUM_SUB[4]+"Fe(SO"+NUM_SUB[4]+")"+NUM_SUB[2]);
        matList.AmmoniumIronIIISulfate.mat.put(TD.ItemGenerator.DUSTS);
        //(NH4)2SO4
        matList.Sulfanilamide.register(30014, "Sulfanilamide", "Sulfanilamide",509, 510, 251, 251, 216, 2, "(NH"+NUM_SUB[4]+")"+NUM_SUB[2]+"SO"+NUM_SUB[4]);
        matList.Sulfanilamide.mat.put(TD.ItemGenerator.DUSTS);

        matList.CookedBauxide .register(30020, "CookedBauxide", "Cooked Bauxide", 2370, 3234, 229, 141, 0, 4, null);
        matList.CookedBauxide.mat.put(TD.ItemGenerator.DUSTS);

        matList.AcidPickledBauxide.register(30021, "CookedBauxide", "Cooked Bauxide", 2370, 3234,200, 168, 0, 4, null);
        matList.AcidPickledBauxide.mat.put(TD.ItemGenerator.DUSTS);

        matList.BauxiteRedMud.register(30022, "BauxiteRedMud", "Bauxite Red Mud", 1790, 3234, 148, 0, 12, 0,null);
        matList.BauxiteRedMud.mat.put(TD.ItemGenerator.DUSTS);

        matList.LithiumCarbonate.register(30030, "LithiumCarbonate", "Lithium Carbonate",943, 1582, 248, 244, 248, 0,  "Li"+NUM_SUB[2]+"CO"+NUM_SUB[3]);
        matList.LithiumCarbonate.mat.put(TD.ItemGenerator.DUSTS);

        matList.MetatitanicAcid.register(30040, "MetatitanicAcid", "Metatitanic Acid", 748,749, 248, 244, 248, 0, "TiO(OH)"+NUM_SUB[2]);
        matList.MetatitanicAcid.mat.put(TD.ItemGenerator.DUSTS);

        matList.Acetone.register(30050,"Acetone","Acetone",143,293,210,210,210,140,"C"+NUM_SUB[3]+"H"+NUM_SUB[6]+"O");
        matList.Acetone.mat.put(TD.Properties.FLAMMABLE,TD.Properties.TRANSPARENT);

        matList.Acetylene.register(30051,"Acetylene","Acetylene",147,217,210,210,210,180,"CH"+Symbols[0]+"CH");
        matList.Acetylene.mat.put(TD.Properties.FLAMMABLE,TD.Properties.EXPLOSIVE,TD.Properties.TRANSPARENT);

        matList.CalciumAcetate.register(30052,"CalciumAcetate","Calcium Acetate",396,397,245,246,245,0,"C"+NUM_SUB[4]+"H"+NUM_SUB[6]+"CaO"+NUM_SUB[4]);
    }
}
