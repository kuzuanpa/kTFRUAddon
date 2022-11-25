package cn.kuzuanpa.ktfruaddon.material;


import cpw.mods.fml.common.event.FMLInitializationEvent;
import gregapi.api.Abstract_Proxy;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class materialInit extends Abstract_Proxy{
    public materialInit (FMLInitializationEvent aEvent) {
        // Gets your initialised Registry.
        gregapi.block.multitileentity.MultiTileEntityRegistry tExampleRegistry = gregapi.block.multitileentity.MultiTileEntityRegistry.getRegistry("example.multitileentity");

        // Gets your Examplium by Name if you initialised it above.
        gregapi.oredict.OreDictMaterial tExamplium = gregapi.oredict.OreDictMaterial.get("Examplium");

        // YES this Registry Stuff can and should be in @Init. That way, all the OreDict Items needed for Crafting Recipes are available and registered.

        // Take the Metal Block from the Set, that you initialised above in @PreInit.
        gregapi.block.multitileentity.MultiTileEntityBlock tMetalBlock = gregapi.block.multitileentity.MultiTileEntityBlock.getOrCreate(MOD_ID, "iron", net.minecraft.block.material.Material.iron, net.minecraft.block.Block.soundTypeMetal, gregapi.data.CS.TOOL_pickaxe, 0, 0, 15, false, false);
        // Makes an Examplium Chest out of your Example Material.
        // Note: the Crafting Recipe only works in conjunction with GT since you don't have the Stick, Ring and Plate PrefixItems.
        tExampleRegistry.add(tExamplium.getLocal() + " Chest", "Chests", 0, 0, gregapi.block.multitileentity.example.MultiTileEntityChest.class, 0, 16, tMetalBlock, gregapi.util.UT.NBT.make(gregapi.data.CS.NBT_MATERIAL, tExamplium, gregapi.data.CS.NBT_INV_SIZE, 54, gregapi.data.CS.NBT_TEXTURE, "metalchest", gregapi.data.CS.NBT_HARDNESS, 6.0F, gregapi.data.CS.NBT_RESISTANCE, 6.0F, gregapi.data.CS.NBT_COLOR, gregapi.util.UT.Code.getRGBInt(tExamplium.fRGBaSolid)), "sPw", "RSR", "PPP", 'P', gregapi.data.OP.plate.dat(tExamplium), 'R', gregapi.data.OP.ring.dat(tExamplium), 'S', gregapi.data.OP.stick.dat(tExamplium));

        // Take the Machine Block from the Set, that you initialised above in @PreInit.
        gregapi.block.multitileentity.MultiTileEntityBlock tMachineBlock = gregapi.block.multitileentity.MultiTileEntityBlock.getOrCreate(MOD_ID, "machine", gregapi.block.MaterialMachines.instance, net.minecraft.block.Block.soundTypeMetal, gregapi.data.CS.TOOL_wrench, 0, 0, 15, false, false);
        // Makes a vanilla Furnace out of your Example Material.
        // Note: the Crafting Recipe only works in conjunction with GT since you don't have all the PrefixItems.
        // GUI has to be at: "/assets/insert_your_MOD_ID_here/textures/gui/machines/Oven.png"
        // Block Textures have to be at: "/assets/gregtech/textures/blocks/machines/basicmachines/oven/..." Yes that is not a Typo, it is actually the GregTech Mod-ID in that path. I noticed that flaw way too late to fix it. And look at how GT has the Textures for its Oven for Details.
        tExampleRegistry.add("Oven (" + tExamplium.getLocal() + ")", "Example Mod", 1, 0, gregapi.tileentity.machines.MultiTileEntityBasicMachine.class, tExamplium.mToolQuality, 16, tMachineBlock, gregapi.util.UT.NBT.make(gregapi.data.CS.NBT_MATERIAL, tExamplium, gregapi.data.CS.NBT_HARDNESS, 6.0F, gregapi.data.CS.NBT_RESISTANCE, 6.0F, gregapi.data.CS.NBT_COLOR, gregapi.util.UT.Code.getRGBInt(tExamplium.fRGBaSolid), gregapi.data.CS.NBT_INPUT, 128, gregapi.data.CS.NBT_TEXTURE, "oven", gregapi.data.CS.NBT_GUI, MOD_ID + ":textures/gui/machines/Oven", gregapi.data.CS.NBT_ENERGY_ACCEPTED, gregapi.data.TD.Energy.HU, gregapi.data.CS.NBT_RECIPEMAP, gregapi.data.RM.Furnace, gregapi.data.CS.NBT_EFFICIENCY, 10000, gregapi.data.CS.NBT_INV_SIDE_IN, gregapi.data.CS.SBIT_L, gregapi.data.CS.NBT_INV_SIDE_AUTO_IN, gregapi.data.CS.SIDE_LEFT, gregapi.data.CS.NBT_INV_SIDE_OUT, gregapi.data.CS.SBIT_R, gregapi.data.CS.NBT_INV_SIDE_AUTO_OUT, gregapi.data.CS.SIDE_RIGHT, gregapi.data.CS.NBT_ENERGY_ACCEPTED_SIDES, gregapi.data.CS.SBIT_D), "wMh", "BCB", 'M', gregapi.data.OP.casingMachine.dat(tExamplium), 'B', gregapi.util.ST.make(net.minecraft.init.Blocks.brick_block, 1, gregapi.data.CS.W), 'C', gregapi.data.OP.plateDouble.dat(gregapi.data.ANY.Cu));

        // The above just makes a vanilla Furnace that is made of Examplium, you cannot obtain the Examplium right now. For that you will need your own Recipe Handler.
        gregapi.recipes.Recipe.RecipeMap tRecipeMap = new gregapi.recipes.Recipe.RecipeMap(null, "example.recipe.exampliumfurnace", "Examplium Furnace", null, 0, 1, MOD_ID + ":textures/gui/machines/ExampliumFurnace",/*IN-OUT-MIN-ITEM=*/ 1, 1, 1,/*IN-OUT-MIN-FLUID=*/ 0, 0, 0,/*MIN*/ 0,/*AMP=*/ 1, "", 1, "", true, true, true, true, false, true, true);
        // This Recipe will turn an Iron Ingot into an Examplium Ingot at 64 Units per Tick for 128 Ticks.
        tRecipeMap.addRecipe1(true, 64, 128, gregapi.util.OM.ingot(gregapi.data.MT.Fe), gregapi.util.OM.ingot(tExamplium));
        // This Recipe will turn a Wrought Iron Ingot into an Examplium Ingot at 64 Units per Tick for 96 Ticks. So a slightly cheaper variant
        tRecipeMap.addRecipe1(true, 64, 96, gregapi.util.OM.ingot(gregapi.data.MT.WroughtIron), gregapi.util.OM.ingot(tExamplium));
        // This Recipe will turn a Steel Ingot into an Examplium Ingot at 64 Units per Tick for 64 Ticks. So a cheaper variant
        tRecipeMap.addRecipe1(true, 64, 64, gregapi.util.OM.ingot(gregapi.data.MT.Steel), gregapi.util.OM.ingot(tExamplium));

        // Makes an Examplium Furnace out of Iron.
        // Note: the Crafting Recipe only works in conjunction with GT since you don't have all the PrefixItems.
        // GUI has to be at: "/assets/insert_your_MOD_ID_here/textures/gui/machines/Oven.png"
        // Block Textures have to be at: "/assets/gregtech/textures/blocks/machines/basicmachines/exampliumfurnace/..." Yes that is not a Typo, it is actually the GregTech Mod-ID in that path. I noticed that flaw way too late to fix it. And look at how GT has the Textures for its Oven for Details.
        tExampleRegistry.add("Examplium Furnace", "Example Mod", 2, 0, gregapi.tileentity.machines.MultiTileEntityBasicMachine.class, gregapi.data.MT.Fe.mToolQuality, 16, tMachineBlock, gregapi.util.UT.NBT.make(gregapi.data.CS.NBT_MATERIAL, gregapi.data.MT.Fe, gregapi.data.CS.NBT_HARDNESS, 6.0F, gregapi.data.CS.NBT_RESISTANCE, 6.0F, gregapi.data.CS.NBT_COLOR, gregapi.util.UT.Code.getRGBInt(gregapi.data.MT.Fe.fRGBaSolid), gregapi.data.CS.NBT_INPUT, 128, gregapi.data.CS.NBT_TEXTURE, "exampliumfurnace", gregapi.data.CS.NBT_GUI, MOD_ID + ":textures/gui/machines/ExampliumFurnace", gregapi.data.CS.NBT_ENERGY_ACCEPTED, gregapi.data.TD.Energy.HU, gregapi.data.CS.NBT_RECIPEMAP, tRecipeMap, gregapi.data.CS.NBT_EFFICIENCY, 10000, gregapi.data.CS.NBT_INV_SIDE_IN, gregapi.data.CS.SBIT_L, gregapi.data.CS.NBT_INV_SIDE_AUTO_IN, gregapi.data.CS.SIDE_LEFT, gregapi.data.CS.NBT_INV_SIDE_OUT, gregapi.data.CS.SBIT_R, gregapi.data.CS.NBT_INV_SIDE_AUTO_OUT, gregapi.data.CS.SIDE_RIGHT, gregapi.data.CS.NBT_ENERGY_ACCEPTED_SIDES, gregapi.data.CS.SBIT_D), "wMh", "BCB", 'M', gregapi.data.OP.casingMachineDouble.dat(gregapi.data.MT.Fe), 'B', gregapi.util.ST.make(net.minecraft.init.Blocks.brick_block, 1, gregapi.data.CS.W), 'C', gregapi.data.OP.plateDouble.dat(gregapi.data.ANY.Cu));

        // Makes an electric Examplium Furnace out of Steel.
        // Note: the Crafting Recipe only works in conjunction with GT since you don't have all the PrefixItems.
        tExampleRegistry.add("Electric Examplium Furnace", "Example Mod", 3, 0, gregapi.tileentity.machines.MultiTileEntityBasicMachineElectric.class, gregapi.data.MT.Steel.mToolQuality, 16, tMachineBlock, gregapi.util.UT.NBT.make(gregapi.data.CS.NBT_MATERIAL, gregapi.data.MT.Steel, gregapi.data.CS.NBT_HARDNESS, 6.0F, gregapi.data.CS.NBT_RESISTANCE, 6.0F, gregapi.data.CS.NBT_COLOR, gregapi.util.UT.Code.getRGBInt(gregapi.data.MT.Steel.fRGBaSolid), gregapi.data.CS.NBT_INPUT, 128, gregapi.data.CS.NBT_TEXTURE, "exampliumfurnace", gregapi.data.CS.NBT_GUI, MOD_ID + ":textures/gui/machines/ExampliumFurnace", gregapi.data.CS.NBT_ENERGY_ACCEPTED, gregapi.data.TD.Energy.EU, gregapi.data.CS.NBT_RECIPEMAP, tRecipeMap, gregapi.data.CS.NBT_EFFICIENCY, 10000, gregapi.data.CS.NBT_INV_SIDE_IN, gregapi.data.CS.SBIT_L, gregapi.data.CS.NBT_INV_SIDE_AUTO_IN, gregapi.data.CS.SIDE_LEFT, gregapi.data.CS.NBT_INV_SIDE_OUT, gregapi.data.CS.SBIT_R, gregapi.data.CS.NBT_INV_SIDE_AUTO_OUT, gregapi.data.CS.SIDE_RIGHT, gregapi.data.CS.NBT_ENERGY_ACCEPTED_SIDES, gregapi.data.CS.SBIT_D), "wMh", "BCB", 'M', gregapi.data.OP.casingMachineDouble.dat(gregapi.data.MT.Steel), 'B', gregapi.util.ST.make(net.minecraft.init.Blocks.brick_block, 1, gregapi.data.CS.W), 'C', gregapi.data.MT.DATA.CIRCUITS[1]);

        // Makes a Flux Examplium Furnace out of Invar.
        // Note: the Crafting Recipe only works in conjunction with GT since you don't have all the PrefixItems.
        // Note: Since it takes RF, the Input has to be 4 times larger.
        tExampleRegistry.add("Flux Examplium Furnace", "Example Mod", 4, 0, gregapi.tileentity.machines.MultiTileEntityBasicMachineFlux.class, gregapi.data.MT.Invar.mToolQuality, 16, tMachineBlock, gregapi.util.UT.NBT.make(gregapi.data.CS.NBT_MATERIAL, gregapi.data.MT.Invar, gregapi.data.CS.NBT_HARDNESS, 6.0F, gregapi.data.CS.NBT_RESISTANCE, 6.0F, gregapi.data.CS.NBT_COLOR, gregapi.util.UT.Code.getRGBInt(gregapi.data.MT.Invar.fRGBaSolid), gregapi.data.CS.NBT_INPUT, 512, gregapi.data.CS.NBT_TEXTURE, "exampliumfurnace", gregapi.data.CS.NBT_GUI, MOD_ID + ":textures/gui/machines/ExampliumFurnace", gregapi.data.CS.NBT_ENERGY_ACCEPTED, gregapi.data.TD.Energy.RF, gregapi.data.CS.NBT_RECIPEMAP, tRecipeMap, gregapi.data.CS.NBT_EFFICIENCY, 10000, gregapi.data.CS.NBT_INV_SIDE_IN, gregapi.data.CS.SBIT_L, gregapi.data.CS.NBT_INV_SIDE_AUTO_IN, gregapi.data.CS.SIDE_LEFT, gregapi.data.CS.NBT_INV_SIDE_OUT, gregapi.data.CS.SBIT_R, gregapi.data.CS.NBT_INV_SIDE_AUTO_OUT, gregapi.data.CS.SIDE_RIGHT, gregapi.data.CS.NBT_ENERGY_ACCEPTED_SIDES, gregapi.data.CS.SBIT_D), "wMh", "BCB", 'M', gregapi.data.OP.casingMachineDouble.dat(gregapi.data.MT.Invar), 'B', gregapi.util.ST.make(net.minecraft.init.Blocks.brick_block, 1, gregapi.data.CS.W), 'C', gregapi.data.MT.DATA.CIRCUITS[3]);

        // Makes Examplium Fluid Pipes, which are as good as Stainless Steel ones of GT.
        gregapi.tileentity.connectors.MultiTileEntityPipeFluid.addFluidPipes(30, 0, 250, true, true, false, true, false, true, true, tExampleRegistry, tMachineBlock, gregapi.tileentity.connectors.MultiTileEntityPipeFluid.class, (long) (tExamplium.mMeltingPoint * 1.25), tExamplium);

        // Makes Iron Item Pipes, which are as good as Brass ones. Why not Examplium? Because the Recipes would conflict with the Fluid Pipe above in that case.
        gregapi.tileentity.connectors.MultiTileEntityPipeItem.addItemPipes(100, 0, 32768, 1, true, true, tExampleRegistry, tMetalBlock, gregapi.tileentity.connectors.MultiTileEntityPipeItem.class, gregapi.data.MT.Fe);

        // Take the Wire Block from the Set, that you initialised above in @PreInit.
        gregapi.block.multitileentity.MultiTileEntityBlock tWireBlock = gregapi.block.multitileentity.MultiTileEntityBlock.getOrCreate(MOD_ID, "machine", gregapi.block.MaterialMachines.instance, net.minecraft.block.Block.soundTypeMetal, gregapi.data.CS.TOOL_cutter, 0, 0, 15, false, false);

        // Makes Examplium Wires, which can carry twice the Voltage of Steel and have a lower loss.
        gregapi.tileentity.connectors.MultiTileEntityWireElectric.addElectricWires(50, 0, gregapi.data.CS.VMAX[4], 1, 2, 1, true, false, true, tExampleRegistry, tWireBlock, gregapi.tileentity.connectors.MultiTileEntityWireElectric.class, tExamplium);

    }
}
