package cn.kuzuanpa.ktfruaddon.material;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.data.TD;
import gregapi.oredict.OreDictMaterial;
import gregapi.render.TextureSet;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_DATA;

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
        tExamplium.setMcfg(0, gregapi.data.MT.Steel, 1 * gregapi.data.CS.U); // This Material consists out of one Unit of Steel.
        tExamplium.setOriginalMod(MOD_DATA); // Gives your Mod the credit for creating this Material.
        tExamplium.aspects(gregapi.data.TC.METALLUM, 3); // Thaumcraft Aspects related to this Material.





        //Cr Producing ID=30010-30019
        //Cr2O7(NH3)2
        final OreDictMaterial ammoniumDichromate = OreDictMaterial.createMaterial(30011, "AmmoniumDichromate", "Ammonium Dichromate");
        ammoniumDichromate.setTextures(TextureSet.SET_POWDER);
        ammoniumDichromate.setRGBa(255, 160, 51, 0);
        ammoniumDichromate.put(TD.ItemGenerator.DUSTS);
        ammoniumDichromate.heat(456, 460);
        ammoniumDichromate.setOriginalMod(MOD_DATA);


        //NH4Cr(SO4)2
        final OreDictMaterial ammoniumChromicSulfate = OreDictMaterial.createMaterial(30012, "AmmoniumChromicSulfate", "Ammonium Chromic Sulfate");
        ammoniumChromicSulfate.setTextures(TextureSet.SET_POWDER);
        ammoniumChromicSulfate.setRGBa(72, 0, 161, 0);
        ammoniumChromicSulfate.put(TD.ItemGenerator.DUSTS);
        ammoniumChromicSulfate.put(TD.Compounds.DECOMPOSABLE);
        ammoniumChromicSulfate.heat(456, 460);
        ammoniumChromicSulfate.setOriginalMod(MOD_DATA);

        //NH4Fe(SO4);2
        final OreDictMaterial ammoniumIronIIISulfate = OreDictMaterial.createMaterial(30013, "AmmoniumIronIIISulfate", "Ammonium Iron(III) Sulfate");
        ammoniumIronIIISulfate.setTextures(TextureSet.SET_POWDER);
        ammoniumIronIIISulfate.setRGBa(230, 220, 242, 0);
        ammoniumIronIIISulfate.put(TD.ItemGenerator.DUSTS);
        ammoniumIronIIISulfate.heat(368, 524);
        ammoniumIronIIISulfate.setOriginalMod(MOD_DATA);

        //(NH4)2SO4
        final OreDictMaterial Sulfanilamide = OreDictMaterial.createMaterial(30014, "Sulfanilamide", "Sulfanilamide");
        Sulfanilamide.setTextures(TextureSet.SET_POWDER);
        Sulfanilamide.setRGBa(251, 251, 216, 2);
        Sulfanilamide.put(TD.ItemGenerator.DUSTS);
        Sulfanilamide.put(TD.Compounds.DECOMPOSABLE);
        Sulfanilamide.heat(509, 510);
        Sulfanilamide.setOriginalMod(MOD_DATA);

        final OreDictMaterial CookedBauxide = OreDictMaterial.createMaterial(30020, "CookedBauxide", "Cooked Bauxide");
        CookedBauxide.setTextures(TextureSet.SET_METALLIC);
        CookedBauxide.setRGBa(229, 141, 0, 4);
        CookedBauxide.put(TD.ItemGenerator.DUSTS);
        CookedBauxide.put(TD.Compounds.DECOMPOSABLE);
        CookedBauxide.heat(2370, 3234);
        CookedBauxide.setOriginalMod(MOD_DATA);

        final OreDictMaterial AcidPickledBauxide = OreDictMaterial.createMaterial(30021, "CookedBauxide", "Cooked Bauxide");
        AcidPickledBauxide.setTextures(TextureSet.SET_METALLIC);
        AcidPickledBauxide.setRGBa(200, 168, 0, 4);
        AcidPickledBauxide.put(TD.ItemGenerator.DUSTS);
        AcidPickledBauxide.put(TD.Compounds.DECOMPOSABLE);
        AcidPickledBauxide.heat(2370, 3234);
        AcidPickledBauxide.setOriginalMod(MOD_DATA);
        FL.create("acidpickledbauxide"         , "Acid Pickled Bauxide"    , null                  , 1);
        FL.create("mixtureoffe2o3na2so4"         , "Mixure Of FE2O3 & Na2SO4"    , null                  , 1);
        FL.create("mixtureoffe2o3k2so4"         , "Mixure Of FE2O3 & K2SO4"    , null                  , 1);
        FL.create("sodiumaluminate","Sodium Aluminate",null,1);
        FL.create("potassiumaluminate","Potassium Aluminate",null,1);
        final OreDictMaterial BauxiteRedMud = OreDictMaterial.createMaterial(30022, "BauxiteRedMud", "Bauxite Red Mud");
        BauxiteRedMud.setTextures(TextureSet.SET_METALLIC);
        BauxiteRedMud.setRGBa(148, 0, 12, 0);
        BauxiteRedMud.put(TD.ItemGenerator.DUSTS);
        BauxiteRedMud.put(TD.Compounds.DECOMPOSABLE);
        BauxiteRedMud.heat(1790, 3234);
        BauxiteRedMud.setOriginalMod(MOD_DATA);
        FL.create("asodiumcarbonate","Sodium Carbonate",null,1);
        FL.create("potassiumcarbonate","Potassium Carbonate",null,1);
        FL.create("liquifiednaturalgas","Liquified Natural Gas",null,1);

    }
}
