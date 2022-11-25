package cn.kuzuanpa.ktfruaddon.material;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregapi.api.Abstract_Proxy;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_DATA;
import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class materialPreInit extends Abstract_Proxy {
    public materialPreInit(FMLPreInitializationEvent aEvent) {
        // If you want to make yourself a new OreDict Prefix for your Component Items or similar.
        final gregapi.oredict.OreDictPrefix tExamplePrefix = gregapi.oredict.OreDictPrefix.createPrefix("exampleprefix"); // This newly created OreDict Prefix is named "exampleprefix", so an Aluminium Item with this Prefix would be named "exampleprefixAluminium" in the OreDict.
        tExamplePrefix.setCategoryName("Examples"); // That is what the Creative Tab of it would be named.
        tExamplePrefix.setLocalItemName("Small ", " Example"); // Generic Items will follow this naming Guideline, so for example "Small Aluminium Example" for an Aluminium Item with that Prefix.
        tExamplePrefix.setCondition(gregapi.code.ICondition.TRUE); // The Condition under which Items of this Prefix should generate in general. In this case TRUE to have ALL the Items.
        tExamplePrefix.add(gregapi.data.TD.Prefix.UNIFICATABLE); // OreDict Unification can apply to this Prefix.
        tExamplePrefix.add(gregapi.data.TD.Prefix.RECYCLABLE); // Items of this can be recycled for Resources.
        tExamplePrefix.setMaterialStats(gregapi.data.CS.U); // Any Item of this example Prefix has the value of 1 Material Unit (U), this is exactly equal to one Ingot/Dust/Gem.
        tExamplePrefix.aspects(gregapi.data.TC.FABRICO, 1); // Thaumcraft Aspects related to this Prefix.
        tExamplePrefix.setStacksize(16, 8); // Sets the Maximum ItemStack Size of this Prefix to 16, and allows the Config to go as far down as 8 when people manually select a StackSize using it.

        // If you want to make yourself a new OreDict Material. Please look up proper IDs. So replace 32766 with a Number inside YOUR own ID Range. (you can look that up in gregapi.oredict.OreDictMaterial.java)
        final gregapi.oredict.OreDictMaterial tExamplium = gregapi.oredict.OreDictMaterial.createMaterial(32766, "Examplium", "Examplium"); // Creates a Material called "Examplium".
        tExamplium.setTextures(gregapi.render.TextureSet.SET_METALLIC); // gives this Material the Metallic Texture Set.
        tExamplium.setRGBa(100, 100, 200, 0); // Sets the RGBa Color of the Material. In this case some random blue Color.
        tExamplium.put(gregapi.data.TD.Processing.SMITHABLE); // This Material is smithable like regular Metal things.
        tExamplium.put(gregapi.data.TD.Processing.MELTING); // This Material can melt.
        tExamplium.put(gregapi.data.TD.Processing.FURNACE); // This Material can be molten in a regular Furnace.
        tExamplium.put(gregapi.data.TD.Processing.CENTRIFUGE); // This Material can be centrifuged to recycle it.
        tExamplium.put(gregapi.data.TD.Compounds.DECOMPOSABLE); // This Material can be decomposed in general.
        tExamplium.put(gregapi.data.TD.ItemGenerator.G_INGOT_MACHINE_ORES); // This Material is a typical Ingot, that can be used for Machine Parts, and the Material can be found as Ore too.
        tExamplium.heat(2000, 4000); // This Material melts at 2000 Kelvin and Boils at 4000 Kelvin.
        tExamplium.qual(3 // Type 3 = The Material can be used for every Type of Tool
                , 6.0 // Speed is 6.0 what is as fast as Steel at mining stuff
                , 512 // Durability is 512 what equals Steel too
                , 3); // Quality is 3 for Diamond Tool Level
        tExamplium.setMcfg(0, gregapi.data.MT.Steel, 1 * gregapi.data.CS.U); // This Material consists out of one Unit of Steel.
        tExamplium.setOriginalMod(MOD_DATA); // Gives your Mod the credit for creating this Material.
        tExamplium.aspects(gregapi.data.TC.METALLUM, 3); // Thaumcraft Aspects related to this Material.

        // If you want to make your Prefix an Item
        // Creates the generic Item for the new Prefix. Assets go into "/assets/insert_your_MOD_ID_here/textures/items/materialicons". And yes, every TextureSet for every Material Type has its own Folder.
        new gregapi.item.prefixitem.PrefixItem(MOD_ID, MOD_ID, "example.meta.item.exampleprefix", tExamplePrefix, gregapi.oredict.OreDictMaterial.MATERIAL_ARRAY);

        // If you want to make your Prefix a Block
        // Creates the generic Block for the new Prefix. Assets go into "/assets/insert_your_MOD_ID_here/textures/blocks/materialicons". And yes, every TextureSet for every Material Type has its own Folder.
        new gregapi.block.prefixblock.PrefixBlock_(MOD_ID, MOD_ID, "example.meta.block.exampleprefix", tExamplePrefix, null, null, null, null, net.minecraft.block.material.Material.rock, net.minecraft.block.Block.soundTypeStone, gregapi.data.CS.TOOL_pickaxe, 1.5F, 4.5F, 0, 0, 999, 0, 0, 0, 1, 1, 1, false, false, false, false, true, true, true, true, true, true, false, true, true, true, gregapi.oredict.OreDictMaterial.MATERIAL_ARRAY);

        // You may think that you don't want to add all the PrefixItems for all the Materials, since you only need certain ones yourself and don't want a clutter like the one GregTech itself causes.
        // No Problem, you can add single Items too, if you just need those.
        // Assets go into "/assets/insert_your_MOD_ID_here/textures/items/example.multiitem.resources/..."
        // The Textures themselves are just the IDs you insert down there. So "0.png" for the Tiny Pile of Examplium Dust.
        new gregapi.item.multiitem.MultiItemRandom(MOD_ID, "example.multiitem.resources") {
            @Override
            public void addItems() {
                // Did you know that you can use a variable from outside this Block by just making it "final"? I didn't, but now I know more and use tExamplium, even though it wouldn't be accessible otherwise.
                // And yes you can use all the 32766 possible Meta-IDs of this Item.
                addItem(0, "Tiny Pile of Examplium Dust", "", gregapi.data.OP.dustTiny.dat(tExamplium));
                addItem(1, "Small Pile of Examplium Dust", "", gregapi.data.OP.dustSmall.dat(tExamplium));
                addItem(2, "Examplium Dust", "", gregapi.data.OP.dust.dat(tExamplium));
                addItem(3, "Examplium Nugget", "", gregapi.data.OP.nugget.dat(tExamplium));
                addItem(4, "Examplium Chunk", "", gregapi.data.OP.chunkGt.dat(tExamplium));
                addItem(5, "Examplium Ingot", "", gregapi.data.OP.ingot.dat(tExamplium));
                addItem(6, "Examplium Plate", "", gregapi.data.OP.plate.dat(tExamplium));
                addItem(7, "Examplium Rod", "", gregapi.data.OP.stick.dat(tExamplium));
                addItem(8, "Examplium Ring", "", gregapi.data.OP.ring.dat(tExamplium));

                // Here would be a right place to add Crafting Recipes or Machine Recipes using your new Items.

                // Crafting the Dusts together.
                gregapi.util.CR.shapeless(gregapi.data.OP.dust.mat(tExamplium, 1), gregapi.util.CR.DEF, new Object[]{gregapi.data.OP.dustTiny.dat(tExamplium), gregapi.data.OP.dustTiny.dat(tExamplium), gregapi.data.OP.dustTiny.dat(tExamplium), gregapi.data.OP.dustTiny.dat(tExamplium), gregapi.data.OP.dustTiny.dat(tExamplium), gregapi.data.OP.dustTiny.dat(tExamplium), gregapi.data.OP.dustTiny.dat(tExamplium), gregapi.data.OP.dustTiny.dat(tExamplium), gregapi.data.OP.dustTiny.dat(tExamplium)});
                gregapi.util.CR.shapeless(gregapi.data.OP.dust.mat(tExamplium, 1), gregapi.util.CR.DEF, new Object[]{gregapi.data.OP.dustSmall.dat(tExamplium), gregapi.data.OP.dustSmall.dat(tExamplium), gregapi.data.OP.dustSmall.dat(tExamplium), gregapi.data.OP.dustSmall.dat(tExamplium)});

                // Smelting the Dusts into Ingots/Nuggets
                gregapi.data.RM.add_smelting(gregapi.util.ST.make(this, 1, 0), gregapi.util.ST.make(this, 1, 3));
                gregapi.data.RM.add_smelting(gregapi.util.ST.make(this, 1, 1), gregapi.util.ST.make(this, 1, 4));
                gregapi.data.RM.add_smelting(gregapi.util.ST.make(this, 1, 2), gregapi.util.ST.make(this, 1, 5));
            }
        };

        // This gives you your very own 32767 Machine IDs.
        new gregapi.block.multitileentity.MultiTileEntityRegistry("example.multitileentity");

        // Every Machine can have another Block, vanilla-material, vanilla-step-sound or Harvest Tool
        gregapi.block.multitileentity.MultiTileEntityBlock.getOrCreate(MOD_ID, "iron", net.minecraft.block.material.Material.iron, net.minecraft.block.Block.soundTypeMetal, gregapi.data.CS.TOOL_pickaxe, 0, 0, 15, false, false);
        gregapi.block.multitileentity.MultiTileEntityBlock.getOrCreate(MOD_ID, "machine", gregapi.block.MaterialMachines.instance, net.minecraft.block.Block.soundTypeMetal, gregapi.data.CS.TOOL_cutter, 0, 0, 15, false, false);
        gregapi.block.multitileentity.MultiTileEntityBlock.getOrCreate(MOD_ID, "machine", gregapi.block.MaterialMachines.instance, net.minecraft.block.Block.soundTypeMetal, gregapi.data.CS.TOOL_wrench, 0, 0, 15, false, false);
    }
}
