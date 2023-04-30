/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */
package cn.kuzuanpa.ktfruaddon.tile;

import cn.kuzuanpa.ktfruaddon.tile.multiblock.*;
import cn.kuzuanpa.ktfruaddon.tile.SpecialPart.*;
import cn.kuzuanpa.ktfruaddon.recipe.recipeManager;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import gregapi.block.multitileentity.MultiTileEntityBlock;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.*;
import gregapi.oredict.OreDictMaterial;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import gregapi.tileentity.machines.MultiTileEntityBasicMachineElectric;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.ST;
import gregapi.util.UT;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;
import static gregapi.data.CS.*;

public class tileEntityInit {
    public tileEntityInit(FMLInitializationEvent aEvent) {
        // Take the Machine Block from the Set, that you initialised above in @PreInit.
        MultiTileEntityBlock aMachine = MultiTileEntityBlock.getOrCreate(MOD_ID, "machine", gregapi.block.MaterialMachines.instance, net.minecraft.block.Block.soundTypeMetal, gregapi.data.CS.TOOL_wrench, 0, 0, 15, false, false);
        MultiTileEntityRegistry aRegistry = MultiTileEntityRegistry.getRegistry("ktfru.multitileentity");
        MultiTileEntityRegistry gRegistry = MultiTileEntityRegistry.getRegistry("gt.multitileentity");
        OreDictMaterial tExamplium = OreDictMaterial.get("Examplium");
        MultiTileEntityBlock tWireBlock = MultiTileEntityBlock.getOrCreate(MOD_ID, "machine", gregapi.block.MaterialMachines.instance, net.minecraft.block.Block.soundTypeMetal, gregapi.data.CS.TOOL_cutter, 0, 0, 15, F, F);
        MultiTileEntityBlock tMetalBlock = MultiTileEntityBlock.getOrCreate(MOD_ID, "iron", net.minecraft.block.material.Material.iron, net.minecraft.block.Block.soundTypeMetal, gregapi.data.CS.TOOL_pickaxe, 0, 0, 15, F, F);
        OreDictMaterial aMat; Class<? extends net.minecraft.tileentity.TileEntity> aClass;




        // Makes an Examplium Chest out of your Example Material.
        // Note: the Crafting Recipe only works in conjunction with GT since you don't have the Stick, Ring and Plate PrefixItems.
        //aRegistry.add(tExamplium.getLocal() + " Chest", "Chests", 0, 0, gregapi.block.multitileentity.example.MultiTileEntityChest.class, 0, 16, tMetalBlock, gregapi.util.UT.NBT.make(gregapi.data.CS.NBT_MATERIAL, tExamplium, gregapi.data.CS.NBT_INV_SIZE, 54, gregapi.data.CS.NBT_TEXTURE, "metalchest", gregapi.data.CS.NBT_HARDNESS, 6.0F, gregapi.data.CS.NBT_RESISTANCE, 6.0F, gregapi.data.CS.NBT_COLOR, gregapi.util.UT.Code.getRGBInt(tExamplium.fRGBaSolid)), "sPw", "RSR", "PPP", 'P', gregapi.data.OP.plate.dat(tExamplium), 'R', gregapi.data.OP.ring.dat(tExamplium), 'S', gregapi.data.OP.stick.dat(tExamplium));
        // The above just makes a vanilla Furnace that is made of Examplium, you cannot obtain the Examplium right now. For that you will need your own Recipe Handler.
        //Recipe.RecipeMap kTestRecipes = new Recipe.RecipeMap(null, "example.recipe.exampliumfurnace", "Examplium Furnace", null, 0, 1, MOD_ID + ":textures/gui/machines/ExampliumFurnace",/*IN-OUT-MIN-ITEM=*/ 1, 1, 1,/*IN-OUT-MIN-FLUID=*/ 0, 0, 0,/*MIN*/ 0,/*AMP=*/ 1, "", 1, "", true, true, true, true, false, true, true);
        // This Recipe will turn an Iron Ingot into an Examplium Ingot at 64 Units per Tick for 128 Ticks.
        //kTestRecipes.addRecipe1(true, 64, 128, gregapi.util.OM.ingot(gregapi.data.MT.Fe), gregapi.util.OM.ingot(tExamplium));
        // This Recipe will turn a Wrought Iron Ingot into an Examplium Ingot at 64 Units per Tick for 96 Ticks. So a slightly cheaper variant
        //kTestRecipes.addRecipe1(true, 64, 96, gregapi.util.OM.ingot(gregapi.data.MT.WroughtIron), gregapi.util.OM.ingot(tExamplium));
        // This Recipe will turn a Steel Ingot into an Examplium Ingot at 64 Units per Tick for 64 Ticks. So a cheaper variant
        //kTestRecipes.addRecipe1(true, 64, 64, gregapi.util.OM.ingot(gregapi.data.MT.Steel), gregapi.util.OM.ingot(tExamplium));

        // Makes an Examplium Furnace out of Iron.
        // Note: the Crafting Recipe only works in conjunction with GT since you don't have all the PrefixItems.
        // GUI has to be at: "/assets/insert_your_MOD_ID_here/textures/gui/machines/Oven.png"
        // Block Textures have to be at: "/assets/gregtech/textures/blocks/machines/basicmachines/exampliumfurnace/..." Yes that is not a Typo, it is actually the GregTech Mod-ID in that path. I noticed that flaw way too late to fix it. And look at how GT has the Textures for its Oven for Details.
        //tExampleRegistry.add("Examplium Furnace", "Example Mod", 2, 0, gregapi.tileentity.machines.MultiTileEntityBasicMachine.class, gregapi.data.MT.Fe.mToolQuality, 16, tMachineBlock, gregapi.util.UT.NBT.make(gregapi.data.CS.NBT_MATERIAL, gregapi.data.MT.Fe, gregapi.data.CS.NBT_HARDNESS, 6.0F, gregapi.data.CS.NBT_RESISTANCE, 6.0F, gregapi.data.CS.NBT_COLOR, gregapi.util.UT.Code.getRGBInt(gregapi.data.MT.Fe.fRGBaSolid), gregapi.data.CS.NBT_INPUT, 128, gregapi.data.CS.NBT_TEXTURE, "exampliumfurnace", gregapi.data.CS.NBT_GUI, MOD_ID + ":textures/gui/machines/ExampliumFurnace", gregapi.data.CS.NBT_ENERGY_ACCEPTED, gregapi.data.TD.Energy.HU, gregapi.data.CS.NBT_RECIPEMAP, kTestRecipes, gregapi.data.CS.NBT_EFFICIENCY, 10000, gregapi.data.CS.NBT_INV_SIDE_IN, gregapi.data.CS.SBIT_L, gregapi.data.CS.NBT_INV_SIDE_AUTO_IN, gregapi.data.CS.SIDE_LEFT, gregapi.data.CS.NBT_INV_SIDE_OUT, gregapi.data.CS.SBIT_R, gregapi.data.CS.NBT_INV_SIDE_AUTO_OUT, gregapi.data.CS.SIDE_RIGHT, gregapi.data.CS.NBT_ENERGY_ACCEPTED_SIDES, gregapi.data.CS.SBIT_D), "wMh", "BCB", 'M', gregapi.data.OP.casingMachineDouble.dat(gregapi.data.MT.Fe), 'B', gregapi.util.ST.make(net.minecraft.init.Blocks.brick_block, 1, gregapi.data.CS.W), 'C', gregapi.data.OP.plateDouble.dat(gregapi.data.ANY.Cu));

        // Makes an electric Examplium Furnace out of Steel.
        // Note: the Crafting Recipe only works in conjunction with GT since you don't have all the PrefixItems.
        //tExampleRegistry.add("Electric Examplium Furnace", "Example Mod", 3, 0, gregapi.tileentity.machines.MultiTileEntityBasicMachineElectric.class, gregapi.data.MT.Steel.mToolQuality, 16, tMachineBlock, gregapi.util.UT.NBT.make(gregapi.data.CS.NBT_MATERIAL, gregapi.data.MT.Steel, gregapi.data.CS.NBT_HARDNESS, 6.0F, gregapi.data.CS.NBT_RESISTANCE, 6.0F, gregapi.data.CS.NBT_COLOR, gregapi.util.UT.Code.getRGBInt(gregapi.data.MT.Steel.fRGBaSolid), gregapi.data.CS.NBT_INPUT, 128, gregapi.data.CS.NBT_TEXTURE, "exampliumfurnace", gregapi.data.CS.NBT_GUI, MOD_ID + ":textures/gui/machines/ExampliumFurnace", gregapi.data.CS.NBT_ENERGY_ACCEPTED, gregapi.data.TD.Energy.EU, gregapi.data.CS.NBT_RECIPEMAP, kTestRecipes, gregapi.data.CS.NBT_EFFICIENCY, 10000, gregapi.data.CS.NBT_INV_SIDE_IN, gregapi.data.CS.SBIT_L, gregapi.data.CS.NBT_INV_SIDE_AUTO_IN, gregapi.data.CS.SIDE_LEFT, gregapi.data.CS.NBT_INV_SIDE_OUT, gregapi.data.CS.SBIT_R, gregapi.data.CS.NBT_INV_SIDE_AUTO_OUT, gregapi.data.CS.SIDE_RIGHT, gregapi.data.CS.NBT_ENERGY_ACCEPTED_SIDES, gregapi.data.CS.SBIT_D), "wMh", "BCB", 'M', gregapi.data.OP.casingMachineDouble.dat(gregapi.data.MT.Steel), 'B', gregapi.util.ST.make(net.minecraft.init.Blocks.brick_block, 1, gregapi.data.CS.W), 'C', gregapi.data.MT.DATA.CIRCUITS[1]);

        // Makes a Flux Examplium Furnace out of Invar.
        // Note: the Crafting Recipe only works in conjunction with GT since you don't have all the PrefixItems.
        // Note: Since it takes RF, the Input has to be 4 times larger.
        //tExampleRegistry.add("Flux Examplium Furnace", "Example Mod", 4, 0, gregapi.tileentity.machines.MultiTileEntityBasicMachineFlux.class, gregapi.data.MT.Invar.mToolQuality, 16, tMachineBlock, gregapi.util.UT.NBT.make(gregapi.data.CS.NBT_MATERIAL, gregapi.data.MT.Invar, gregapi.data.CS.NBT_HARDNESS, 6.0F, gregapi.data.CS.NBT_RESISTANCE, 6.0F, gregapi.data.CS.NBT_COLOR, gregapi.util.UT.Code.getRGBInt(gregapi.data.MT.Invar.fRGBaSolid), gregapi.data.CS.NBT_INPUT, 512, gregapi.data.CS.NBT_TEXTURE, "exampliumfurnace", gregapi.data.CS.NBT_GUI, MOD_ID + ":textures/gui/machines/ExampliumFurnace", gregapi.data.CS.NBT_ENERGY_ACCEPTED, gregapi.data.TD.Energy.RF, gregapi.data.CS.NBT_RECIPEMAP, kTestRecipes, gregapi.data.CS.NBT_EFFICIENCY, 10000, gregapi.data.CS.NBT_INV_SIDE_IN, gregapi.data.CS.SBIT_L, gregapi.data.CS.NBT_INV_SIDE_AUTO_IN, gregapi.data.CS.SIDE_LEFT, gregapi.data.CS.NBT_INV_SIDE_OUT, gregapi.data.CS.SBIT_R, gregapi.data.CS.NBT_INV_SIDE_AUTO_OUT, gregapi.data.CS.SIDE_RIGHT, gregapi.data.CS.NBT_ENERGY_ACCEPTED_SIDES, gregapi.data.CS.SBIT_D), "wMh", "BCB", 'M', gregapi.data.OP.casingMachineDouble.dat(gregapi.data.MT.Invar), 'B', gregapi.util.ST.make(net.minecraft.init.Blocks.brick_block, 1, gregapi.data.CS.W), 'C', gregapi.data.MT.DATA.CIRCUITS[3]);

        // Makes a vanilla Furnace out of your Example Material.
        // Note: the Crafting Recipe only works in conjunction with GT since you don't have all the PrefixItems.
        // GUI has to be at: "/assets/insert_your_MOD_ID_here/textures/gui/machines/Oven.png"
        // Block Textures have to be at: "/assets/gregtech/textures/blocks/machines/basicmachines/oven/..." Yes that is not a Typo, it is actually the GregTech Mod-ID in that path. I noticed that flaw way too late to fix it. And look at how GT has the Textures for its Oven for Details.
        //kRegistry.add("Oven (" + tExamplium.getLocal() + ")", "Example Mod", 1, 0, gregapi.tileentity.machines.MultiTileEntityBasicMachine.class, tExamplium.mToolQuality, 16, tMachineBlock, gregapi.util.UT.NBT.make(gregapi.data.CS.NBT_MATERIAL, tExamplium, gregapi.data.CS.NBT_HARDNESS, 6.0F, gregapi.data.CS.NBT_RESISTANCE, 6.0F, gregapi.data.CS.NBT_COLOR, gregapi.util.UT.Code.getRGBInt(tExamplium.fRGBaSolid), gregapi.data.CS.NBT_INPUT, 128, gregapi.data.CS.NBT_TEXTURE, "oven", gregapi.data.CS.NBT_GUI, MOD_ID + ":textures/gui/machines/Oven", gregapi.data.CS.NBT_ENERGY_ACCEPTED, gregapi.data.TD.Energy.HU, gregapi.data.CS.NBT_RECIPEMAP, gregapi.data.RM.Furnace, gregapi.data.CS.NBT_EFFICIENCY, 10000, gregapi.data.CS.NBT_INV_SIDE_IN, gregapi.data.CS.SBIT_L, gregapi.data.CS.NBT_INV_SIDE_AUTO_IN, gregapi.data.CS.SIDE_LEFT, gregapi.data.CS.NBT_INV_SIDE_OUT, gregapi.data.CS.SBIT_R, gregapi.data.CS.NBT_INV_SIDE_AUTO_OUT, gregapi.data.CS.SIDE_RIGHT, gregapi.data.CS.NBT_ENERGY_ACCEPTED_SIDES, gregapi.data.CS.SBIT_D), "wMh", "BCB", 'M', gregapi.data.OP.casingMachine.dat(tExamplium), 'B', gregapi.util.ST.make(net.minecraft.init.Blocks.brick_block, 1, gregapi.data.CS.W), 'C', gregapi.data.OP.plateDouble.dat(gregapi.data.ANY.Cu));
        //gregapi.tileentity.connectors.MultiTileEntityPipeFluid.addFluidPipes(30, 0, 250, true, true, false, true, false, true, true, aRegistry, aMachine, gregapi.tileentity.connectors.MultiTileEntityPipeFluid.class, (long) (tExamplium.mMeltingPoint * 1.25), tExamplium);
        //gregapi.tileentity.connectors.MultiTileEntityWireElectric.addElectricWires(50, 0, gregapi.data.CS.VMAX[4], 1, 2, 1, true, false, true, aRegistry, tWireBlock, gregapi.tileentity.connectors.MultiTileEntityWireElectric.class, tExamplium);


        aClass = MultiTileEntityBasicMachine.class;
        //20000-29999,Single block machines
        //HU Machines,20000-20999

        //RU Machines,21000-21999

        //KU Machines,22000-22999

        //LU Machines,23000-23999
        //Assembler
        aMat = MT.DATA.Electric_T[1];   aRegistry.add("Circuit Assembler (T1)"                                   , "kTFRUAddon: Machines"                       , 23001, 20001, aClass, aMat.mToolQuality, 16, aMachine     , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   4.0F, NBT_RESISTANCE,   4.0F, NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_INPUT,   32, NBT_TEXTURE, "laserwelder", NBT_ENERGY_ACCEPTED, TD.Energy.LU, NBT_RECIPEMAP, recipeManager.Assembler, NBT_INV_SIDE_IN, SBIT_L, NBT_INV_SIDE_AUTO_IN, SIDE_LEFT, NBT_INV_SIDE_OUT, SBIT_R, NBT_INV_SIDE_AUTO_OUT, SIDE_RIGHT, NBT_TANK_SIDE_IN, SBIT_D|SBIT_L, NBT_TANK_SIDE_AUTO_IN, SIDE_BOTTOM, NBT_ENERGY_ACCEPTED_SIDES, SBIT_U), "TLT", "GPG", "CMC", 'M', OP.casingMachine.dat(aMat), 'P', IL.Comp_Laser_Gas_Ne, 'C', OD_CIRCUITS[1], 'L', DYE_OREDICTS_LENS[DYE_INDEX_Yellow], 'T', OP.screw.dat(aMat), 'G', OP.gearGt.dat(aMat));
        aMat = MT.DATA.Electric_T[2];   aRegistry.add("Circuit Assembler (T2)"                                   , "kTFRUAddon: Machines"                       , 23002, 20001, aClass, aMat.mToolQuality, 16, aMachine     , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   4.0F, NBT_RESISTANCE,   4.0F, NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_INPUT,  128, NBT_TEXTURE, "laserwelder", NBT_ENERGY_ACCEPTED, TD.Energy.LU, NBT_RECIPEMAP, recipeManager.Assembler, NBT_INV_SIDE_IN, SBIT_L, NBT_INV_SIDE_AUTO_IN, SIDE_LEFT, NBT_INV_SIDE_OUT, SBIT_R, NBT_INV_SIDE_AUTO_OUT, SIDE_RIGHT, NBT_TANK_SIDE_IN, SBIT_D|SBIT_L, NBT_TANK_SIDE_AUTO_IN, SIDE_BOTTOM, NBT_ENERGY_ACCEPTED_SIDES, SBIT_U), "TLT", "GPG", "CMC", 'M', OP.casingMachine.dat(aMat), 'P', IL.Comp_Laser_Gas_Ne, 'C', OD_CIRCUITS[2], 'L', DYE_OREDICTS_LENS[DYE_INDEX_Yellow], 'T', OP.screw.dat(aMat), 'G', OP.gearGt.dat(aMat));
        aMat = MT.DATA.Electric_T[3];   aRegistry.add("Circuit Assembler (T3)"                                   , "kTFRUAddon: Machines"                       , 23003, 20001, aClass, aMat.mToolQuality, 16, aMachine     , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   4.0F, NBT_RESISTANCE,   4.0F, NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_INPUT,  512, NBT_TEXTURE, "laserwelder", NBT_ENERGY_ACCEPTED, TD.Energy.LU, NBT_RECIPEMAP, recipeManager.Assembler, NBT_INV_SIDE_IN, SBIT_L, NBT_INV_SIDE_AUTO_IN, SIDE_LEFT, NBT_INV_SIDE_OUT, SBIT_R, NBT_INV_SIDE_AUTO_OUT, SIDE_RIGHT, NBT_TANK_SIDE_IN, SBIT_D|SBIT_L, NBT_TANK_SIDE_AUTO_IN, SIDE_BOTTOM, NBT_ENERGY_ACCEPTED_SIDES, SBIT_U), "TLT", "GPG", "CMC", 'M', OP.casingMachine.dat(aMat), 'P', IL.Comp_Laser_Gas_Ne, 'C', OD_CIRCUITS[3], 'L', DYE_OREDICTS_LENS[DYE_INDEX_Yellow], 'T', OP.screw.dat(aMat), 'G', OP.gearGt.dat(aMat));
        aMat = MT.DATA.Electric_T[4];   aRegistry.add("Circuit Assembler (T4)"                                   , "kTFRUAddon: Machines"                       , 23004, 20001, aClass, aMat.mToolQuality, 16, aMachine     , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   4.0F, NBT_RESISTANCE,   4.0F, NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_INPUT, 2048, NBT_TEXTURE, "laserwelder", NBT_ENERGY_ACCEPTED, TD.Energy.LU, NBT_RECIPEMAP, recipeManager.Assembler, NBT_INV_SIDE_IN, SBIT_L, NBT_INV_SIDE_AUTO_IN, SIDE_LEFT, NBT_INV_SIDE_OUT, SBIT_R, NBT_INV_SIDE_AUTO_OUT, SIDE_RIGHT, NBT_TANK_SIDE_IN, SBIT_D|SBIT_L, NBT_TANK_SIDE_AUTO_IN, SIDE_BOTTOM, NBT_ENERGY_ACCEPTED_SIDES, SBIT_U), "TLT", "GPG", "CMC", 'M', OP.casingMachine.dat(aMat), 'P', IL.Comp_Laser_Gas_Ne, 'C', OD_CIRCUITS[4], 'L', DYE_OREDICTS_LENS[DYE_INDEX_Yellow], 'T', OP.screw.dat(aMat), 'G', OP.gearGt.dat(aMat));
        aMat = MT.DATA.Electric_T[5];   aRegistry.add("Circuit Assembler (T5)"                                   , "kTFRUAddon: Machines"                       , 23005, 20001, aClass, aMat.mToolQuality, 16, aMachine     , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   4.0F, NBT_RESISTANCE,   4.0F, NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_INPUT, 8192, NBT_TEXTURE, "laserwelder", NBT_ENERGY_ACCEPTED, TD.Energy.LU, NBT_RECIPEMAP, recipeManager.Assembler, NBT_INV_SIDE_IN, SBIT_L, NBT_INV_SIDE_AUTO_IN, SIDE_LEFT, NBT_INV_SIDE_OUT, SBIT_R, NBT_INV_SIDE_AUTO_OUT, SIDE_RIGHT, NBT_TANK_SIDE_IN, SBIT_D|SBIT_L, NBT_TANK_SIDE_AUTO_IN, SIDE_BOTTOM, NBT_ENERGY_ACCEPTED_SIDES, SBIT_U), "TLT", "GPG", "CMC", 'M', OP.casingMachine.dat(aMat), 'P', IL.Comp_Laser_Gas_Ne, 'C', OD_CIRCUITS[5], 'L', DYE_OREDICTS_LENS[DYE_INDEX_Yellow], 'T', OP.screw.dat(aMat), 'G', OP.gearGt.dat(aMat));

        //Laser Cutter
        aMat = MT.DATA.Electric_T[1];   aRegistry.add("Laser Cutter (T1)"                                   , "kTFRUAddon: Machines"                      , 23011, 20001, aClass, aMat.mToolQuality, 16, aMachine     , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   4.0F, NBT_RESISTANCE,   4.0F, NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_INPUT,   32, NBT_TEXTURE, "lasercutter", NBT_ENERGY_ACCEPTED, TD.Energy.LU, NBT_RECIPEMAP, recipeManager.LaserCutter, NBT_INV_SIDE_IN, SBIT_L, NBT_INV_SIDE_AUTO_IN, SIDE_LEFT, NBT_INV_SIDE_OUT, SBIT_R, NBT_INV_SIDE_AUTO_OUT, SIDE_RIGHT, NBT_TANK_SIDE_IN, SBIT_D|SBIT_L, NBT_TANK_SIDE_AUTO_IN, SIDE_BOTTOM, NBT_ENERGY_ACCEPTED_SIDES, SBIT_U), "TLT", "GPG", "CMC", 'M', OP.casingMachine.dat(aMat), 'P', OD.craftingHardenedClay, 'C', OD_CIRCUITS[1], 'L', DYE_OREDICTS_LENS[DYE_INDEX_Blue], 'T', OP.screw.dat(aMat), 'G', OP.gearGtSmall.dat(aMat));
        aMat = MT.DATA.Electric_T[2];   aRegistry.add("Laser Cutter (T2)"                                   , "kTFRUAddon: Machines"                      , 23012, 20001, aClass, aMat.mToolQuality, 16, aMachine     , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   4.0F, NBT_RESISTANCE,   4.0F, NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_INPUT,   128, NBT_TEXTURE, "lasercutter", NBT_ENERGY_ACCEPTED, TD.Energy.LU, NBT_RECIPEMAP, recipeManager.LaserCutter, NBT_INV_SIDE_IN, SBIT_L, NBT_INV_SIDE_AUTO_IN, SIDE_LEFT, NBT_INV_SIDE_OUT, SBIT_R, NBT_INV_SIDE_AUTO_OUT, SIDE_RIGHT, NBT_TANK_SIDE_IN, SBIT_D|SBIT_L, NBT_TANK_SIDE_AUTO_IN, SIDE_BOTTOM, NBT_ENERGY_ACCEPTED_SIDES, SBIT_U), "TLT", "GPG", "CMC", 'M', OP.casingMachine.dat(aMat), 'P', OD.craftingHardenedClay, 'C', OD_CIRCUITS[2], 'L', DYE_OREDICTS_LENS[DYE_INDEX_Blue], 'T', OP.screw.dat(aMat), 'G', OP.gearGtSmall.dat(aMat));
        aMat = MT.DATA.Electric_T[3];   aRegistry.add("Laser Cutter (T3)"                                   , "kTFRUAddon: Machines"                      , 23013, 20001, aClass, aMat.mToolQuality, 16, aMachine     , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   4.0F, NBT_RESISTANCE,   4.0F, NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_INPUT,   512, NBT_TEXTURE, "lasercutter", NBT_ENERGY_ACCEPTED, TD.Energy.LU, NBT_RECIPEMAP, recipeManager.LaserCutter, NBT_INV_SIDE_IN, SBIT_L, NBT_INV_SIDE_AUTO_IN, SIDE_LEFT, NBT_INV_SIDE_OUT, SBIT_R, NBT_INV_SIDE_AUTO_OUT, SIDE_RIGHT, NBT_TANK_SIDE_IN, SBIT_D|SBIT_L, NBT_TANK_SIDE_AUTO_IN, SIDE_BOTTOM, NBT_ENERGY_ACCEPTED_SIDES, SBIT_U), "TLT", "GPG", "CMC", 'M', OP.casingMachine.dat(aMat), 'P', OD.craftingHardenedClay, 'C', OD_CIRCUITS[3], 'L', DYE_OREDICTS_LENS[DYE_INDEX_Blue], 'T', OP.screw.dat(aMat), 'G', OP.gearGtSmall.dat(aMat));
        aMat = MT.DATA.Electric_T[4];   aRegistry.add("Laser Cutter (T4)"                                   , "kTFRUAddon: Machines"                      , 23014, 20001, aClass, aMat.mToolQuality, 16, aMachine     , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   4.0F, NBT_RESISTANCE,   4.0F, NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_INPUT,   2048, NBT_TEXTURE, "lasercutter", NBT_ENERGY_ACCEPTED, TD.Energy.LU, NBT_RECIPEMAP, recipeManager.LaserCutter, NBT_INV_SIDE_IN, SBIT_L, NBT_INV_SIDE_AUTO_IN, SIDE_LEFT, NBT_INV_SIDE_OUT, SBIT_R, NBT_INV_SIDE_AUTO_OUT, SIDE_RIGHT, NBT_TANK_SIDE_IN, SBIT_D|SBIT_L, NBT_TANK_SIDE_AUTO_IN, SIDE_BOTTOM, NBT_ENERGY_ACCEPTED_SIDES, SBIT_U), "TLT", "GPG", "CMC", 'M', OP.casingMachine.dat(aMat), 'P', OD.craftingHardenedClay, 'C', OD_CIRCUITS[4], 'L', DYE_OREDICTS_LENS[DYE_INDEX_Blue], 'T', OP.screw.dat(aMat), 'G', OP.gearGtSmall.dat(aMat));
        aMat = MT.DATA.Electric_T[5];   aRegistry.add("Laser Cutter (T5)"                                   , "kTFRUAddon: Machines"                      , 23015, 20001, aClass, aMat.mToolQuality, 16, aMachine     , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   4.0F, NBT_RESISTANCE,   4.0F, NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_INPUT,   8192, NBT_TEXTURE, "lasercutter", NBT_ENERGY_ACCEPTED, TD.Energy.LU, NBT_RECIPEMAP, recipeManager.LaserCutter, NBT_INV_SIDE_IN, SBIT_L, NBT_INV_SIDE_AUTO_IN, SIDE_LEFT, NBT_INV_SIDE_OUT, SBIT_R, NBT_INV_SIDE_AUTO_OUT, SIDE_RIGHT, NBT_TANK_SIDE_IN, SBIT_D|SBIT_L, NBT_TANK_SIDE_AUTO_IN, SIDE_TOP, NBT_TANK_SIDE_OUT, SBIT_B|SBIT_R  , NBT_TANK_SIDE_AUTO_OUT, SIDE_BACK, NBT_ENERGY_ACCEPTED_SIDES, SBIT_U), "TLT", "GPG", "CMC", 'M', OP.casingMachine.dat(aMat), 'P', OD.craftingHardenedClay, 'C', OD_CIRCUITS[5], 'L', DYE_OREDICTS_LENS[DYE_INDEX_Blue], 'T', OP.screw.dat(aMat), 'G', OP.gearGtSmall.dat(aMat));

        //TU Machines,24000-24999

        //CU Machines,25000-25999

        //QU Machines,26000-26999

        //EU Machines,27000-27999
        aClass = MultiTileEntityBasicMachineElectric.class;
        //Heat Mixer
        aMat = MT.DATA.Electric_T[1];   aRegistry.add("Heat Mixer ("+VN[1]+")"                              , "kTFRUAddon: Machines" , 27001, 20001, aClass, aMat.mToolQuality, 16, aMachine     , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   4.0F, NBT_RESISTANCE,   4.0F, NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_INPUT,   32, NBT_TEXTURE, "heatmixer", NBT_ENERGY_ACCEPTED, TD.Energy.EU, NBT_RECIPEMAP, recipeManager.HeatMixer, NBT_INV_SIDE_IN, SBIT_U|SBIT_L, NBT_INV_SIDE_AUTO_IN, SIDE_LEFT, NBT_INV_SIDE_OUT, SBIT_R|SBIT_D, NBT_INV_SIDE_AUTO_OUT, SIDE_RIGHT, NBT_TANK_SIDE_IN, SBIT_U|SBIT_L, NBT_TANK_SIDE_AUTO_IN, SIDE_TOP, NBT_TANK_SIDE_OUT, SBIT_R|SBIT_D, NBT_TANK_SIDE_AUTO_OUT, SIDE_BOTTOM, NBT_ENERGY_ACCEPTED_SIDES, SBIT_B, NBT_USE_OUTPUT_TANK, T), "PMP", "XRX", "hSw", 'M', OP.casingMachine.dat(aMat), 'S', IL.MOTORS[1], 'R', OP.rotor.dat(MT.StainlessSteel), 'X', OP.wireGt02.dat(MT.Cupronickel),'P', OP.plate.dat(MT.StainlessSteel));
        aMat = MT.DATA.Electric_T[2];   aRegistry.add("Heat Mixer ("+VN[2]+")"                              , "kTFRUAddon: Machines" , 27002, 20001, aClass, aMat.mToolQuality, 16, aMachine     , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   4.0F, NBT_RESISTANCE,   4.0F, NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_INPUT,  128, NBT_TEXTURE, "heatmixer", NBT_ENERGY_ACCEPTED, TD.Energy.EU, NBT_RECIPEMAP, recipeManager.HeatMixer, NBT_INV_SIDE_IN, SBIT_U|SBIT_L, NBT_INV_SIDE_AUTO_IN, SIDE_LEFT, NBT_INV_SIDE_OUT, SBIT_R|SBIT_D, NBT_INV_SIDE_AUTO_OUT, SIDE_RIGHT, NBT_TANK_SIDE_IN, SBIT_U|SBIT_L, NBT_TANK_SIDE_AUTO_IN, SIDE_TOP, NBT_TANK_SIDE_OUT, SBIT_R|SBIT_D, NBT_TANK_SIDE_AUTO_OUT, SIDE_BOTTOM, NBT_ENERGY_ACCEPTED_SIDES, SBIT_B, NBT_USE_OUTPUT_TANK, T), "PMP", "XRX", "hSw", 'M', OP.casingMachine.dat(aMat), 'S', IL.MOTORS[2], 'R', OP.rotor.dat(MT.StainlessSteel), 'X', OP.wireGt04.dat(MT.Cupronickel),'P', OP.plateDouble.dat(MT.StainlessSteel));
        aMat = MT.DATA.Electric_T[3];   aRegistry.add("Heat Mixer ("+VN[3]+")"                              , "kTFRUAddon: Machines" , 27003, 20001, aClass, aMat.mToolQuality, 16, aMachine     , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   4.0F, NBT_RESISTANCE,   4.0F, NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_INPUT,  512, NBT_TEXTURE, "heatmixer", NBT_ENERGY_ACCEPTED, TD.Energy.EU, NBT_RECIPEMAP, recipeManager.HeatMixer, NBT_INV_SIDE_IN, SBIT_U|SBIT_L, NBT_INV_SIDE_AUTO_IN, SIDE_LEFT, NBT_INV_SIDE_OUT, SBIT_R|SBIT_D, NBT_INV_SIDE_AUTO_OUT, SIDE_RIGHT, NBT_TANK_SIDE_IN, SBIT_U|SBIT_L, NBT_TANK_SIDE_AUTO_IN, SIDE_TOP, NBT_TANK_SIDE_OUT, SBIT_R|SBIT_D, NBT_TANK_SIDE_AUTO_OUT, SIDE_BOTTOM, NBT_ENERGY_ACCEPTED_SIDES, SBIT_B, NBT_USE_OUTPUT_TANK, T), "PMP", "XRX", "hSw", 'M', OP.casingMachine.dat(aMat), 'S', IL.MOTORS[3], 'R', OP.rotor.dat(MT.StainlessSteel), 'X', OP.wireGt08.dat(MT.Cupronickel),'P', OP.plateTriple.dat(MT.StainlessSteel));
        aMat = MT.DATA.Electric_T[4];   aRegistry.add("Heat Mixer ("+VN[4]+")"                              , "kTFRUAddon: Machines" , 27004, 20001, aClass, aMat.mToolQuality, 16, aMachine     , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   4.0F, NBT_RESISTANCE,   4.0F, NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_INPUT, 2048, NBT_TEXTURE, "heatmixer", NBT_ENERGY_ACCEPTED, TD.Energy.EU, NBT_RECIPEMAP, recipeManager.HeatMixer, NBT_INV_SIDE_IN, SBIT_U|SBIT_L, NBT_INV_SIDE_AUTO_IN, SIDE_LEFT, NBT_INV_SIDE_OUT, SBIT_R|SBIT_D, NBT_INV_SIDE_AUTO_OUT, SIDE_RIGHT, NBT_TANK_SIDE_IN, SBIT_U|SBIT_L, NBT_TANK_SIDE_AUTO_IN, SIDE_TOP, NBT_TANK_SIDE_OUT, SBIT_R|SBIT_D, NBT_TANK_SIDE_AUTO_OUT, SIDE_BOTTOM, NBT_ENERGY_ACCEPTED_SIDES, SBIT_B, NBT_USE_OUTPUT_TANK, T), "PMP", "XRX", "hSw", 'M', OP.casingMachine.dat(aMat), 'S', IL.MOTORS[4], 'R', OP.rotor.dat(MT.StainlessSteel), 'X', OP.wireGt08.dat(MT.Nichrome),'P', OP.plateQuadruple.dat(MT.StainlessSteel));
        aMat = MT.DATA.Electric_T[5];   aRegistry.add("Heat Mixer ("+VN[5]+")"                              , "kTFRUAddon: Machines" , 27005, 20001, aClass, aMat.mToolQuality, 16, aMachine     , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   4.0F, NBT_RESISTANCE,   4.0F, NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_INPUT, 8192, NBT_TEXTURE, "heatmixer", NBT_ENERGY_ACCEPTED, TD.Energy.EU, NBT_RECIPEMAP, recipeManager.HeatMixer, NBT_INV_SIDE_IN, SBIT_U|SBIT_L, NBT_INV_SIDE_AUTO_IN, SIDE_LEFT, NBT_INV_SIDE_OUT, SBIT_R|SBIT_D, NBT_INV_SIDE_AUTO_OUT, SIDE_RIGHT, NBT_TANK_SIDE_IN, SBIT_U|SBIT_L, NBT_TANK_SIDE_AUTO_IN, SIDE_TOP, NBT_TANK_SIDE_OUT, SBIT_R|SBIT_D, NBT_TANK_SIDE_AUTO_OUT, SIDE_BOTTOM, NBT_ENERGY_ACCEPTED_SIDES, SBIT_B, NBT_USE_OUTPUT_TANK, T), "PMP", "XRX", "hSw", 'M', OP.casingMachine.dat(aMat), 'S', IL.MOTORS[5], 'R', OP.rotor.dat(MT.StainlessSteel), 'X', OP.wireGt12.dat(MT.Nichrome),'P', OP.plateQuadruple.dat(MT.StainlessSteel));

        //RF Machines,28000-28999

    //Multiblock
        //testMachine
        //aRegistry.add("ktest Steel Wall"     , "Example Mod", 12000, 0, MultiTileEntityMultiBlockPart.class , MT.StainlessSteel.mToolQuality, 64, aMachine   , UT.NBT.make(NBT_MATERIAL, MT.StainlessSteel, NBT_HARDNESS,   6.0F, NBT_RESISTANCE,   6.0F, NBT_TEXTURE, "kmetalwall"               , NBT_DESIGNS, 7), "wPP", "hPP"       , 'P', OP.plate.dat(MT.StainlessSteel)); RM.Welder.addRecipe2(F, 16, 256, OP.plate.mat(MT.StainlessSteel, 4), ST.tag(10), aRegistry.getItem());
        aRegistry.add("ktest Large Bathing Vat" , "Example Mod", 12001, 0, exampleMachineComplex.class , MT.StainlessSteel.mToolQuality, 16, aMachine   , UT.NBT.make(NBT_MATERIAL, MT.StainlessSteel, NBT_HARDNESS,   6.0F, NBT_RESISTANCE,   6.0F, NBT_TEXTURE, "klargebath"               , NBT_INPUT,    1                      , NBT_ENERGY_ACCEPTED, TD.Energy.TU, NBT_RECIPEMAP, RM.Bath     , NBT_INV_SIDE_AUTO_OUT, SIDE_BOTTOM, NBT_TANK_SIDE_AUTO_OUT, SIDE_BOTTOM                           , NBT_PARALLEL,  64                          , NBT_NO_CONSTANT_POWER, T), "CRC", "PMP", "APA", 'M', aRegistry.getItem(12000), 'R', IL.Processor_Crystal_Ruby, 'C', OD_CIRCUITS[6], 'P', OP.plateDense.dat(MT.StainlessSteel), 'A', IL.ROBOT_ARMS[2]);

        aMat = MT.StainlessSteel;  aRegistry.add("Stainless Steel Huge Dynamo Main Housing", "kTFRUAddon: Multiblock", 30001, 17200, hugeDynamo.class, aMat.mToolQuality, 16, aMachine, UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS, 6.0F, NBT_RESISTANCE, 6.0F,NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_TEXTURE, "hugedynamo", NBT_INPUT, 49152,NBT_INPUT_MIN , 32768, NBT_INPUT_MAX,65536 , NBT_OUTPUT, 43008, NBT_WASTE_ENERGY, true, NBT_ENERGY_ACCEPTED, TD.Energy.RU, NBT_ENERGY_EMITTED, TD.Energy.EU),
                "SwS", "CMC", "SBS", 'M', aRegistry.getItem(18022), 'S', OP.stickLong.dat(aMat), 'C', CS.OD_CIRCUITS[6], 'B', "gt:re-battery1");
        aMat = MT.Osmiridium;      aRegistry.add("Particle Collider"  , "kTFRUAddon: MultiBlock", 30002, 17200, particleCollider.class    , aMat.mToolQuality, 16, aMachine   , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,  12.5F, NBT_RESISTANCE,  12.5F, NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_TEXTURE, "particlecollider"        , NBT_INPUT, 8192, NBT_INPUT_MIN,    1, NBT_INPUT_MAX,   524288  , NBT_ENERGY_ACCEPTED, TD.Energy.EU, NBT_RECIPEMAP, recipeManager.ParticleCollider     , NBT_ENERGY_ACCEPTED_2, TD.Energy.EU, NBT_SPECIAL_IS_START_ENERGY, T),
                "FFF", "FMF", "FFF", 'M', aRegistry.getItem(18014), 'F', IL.FIELD_GENERATORS[5]);
        aMat = MT.StainlessSteel;  aRegistry.add("TinyDistilltionTower", "kTFRUAddon: Multiblock", 30005, 17200, TinyDistillTower.class, aMat.mToolQuality, 16, aMachine, UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS, 6.0F, NBT_RESISTANCE, 6.0F,NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_TEXTURE, "hugedynamo", NBT_INPUT, 6080,NBT_INPUT_MIN , 4096, NBT_INPUT_MAX,8192, NBT_ENERGY_ACCEPTED, TD.Energy.EU, NBT_RECIPEMAP, recipeManager.ParticleCollider),
                "SwS", "CMC", "SBS", 'M', aRegistry.getItem(18022), 'S', OP.stickLong.dat(aMat), 'C', CS.OD_CIRCUITS[6], 'B', "gt:re-battery1");
        aMat = MT.Al;              aRegistry.add("Mask Aligner (UV)", "kTFRUAddon: Multiblock", 30004, 17200, maskAlignerUVScanning.class, aMat.mToolQuality, 16, aMachine, UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS, 6.0F, NBT_RESISTANCE, 6.0F,NBT_COLOR, UT.Code.getRGBInt(aMat.fRGBaSolid), NBT_TEXTURE, "maskAlignerUV", NBT_INPUT, 128,NBT_INPUT_MIN , 64, NBT_INPUT_MAX,256, NBT_ENERGY_ACCEPTED, TD.Energy.LU,NBT_RECIPEMAP, recipeManager.MaskAligner),
                "SLS", "XwX", "CMC", 'L',  DYE_OREDICTS_LENS[DYE_INDEX_Blue], 'S', OP.stickLong.dat(aMat), 'X', OP.wireGt02.dat(MT.Cu), 'C', CS.OD_CIRCUITS[2], 'M', OP.casingMachine.dat(aMat));

        //parts
        aMat = MT.TitaniumGold;    aRegistry.add("Transmission Shaft (Ti3Au)"     , "kTFRUAddon: Multiblock", 30100, 17200, MultiTileEntityMultiBlockPart.class , MT.TitaniumGold.mToolQuality, 64, aMachine   , UT.NBT.make(NBT_MATERIAL, MT.TitaniumGold, NBT_HARDNESS,   12.0F, NBT_RESISTANCE,   12.0F, NBT_TEXTURE, "shaft0" , NBT_DESIGNS, 7));

        aMat = MT.HSSE;            aRegistry.add("Transmission Shaft (HSSE)"     , "kTFRUAddon: Multiblock", 30101, 17200, MultiTileEntityMultiBlockPart.class , MT.HSSE.mToolQuality, 64, aMachine   , UT.NBT.make(NBT_MATERIAL, MT.HSSE, NBT_HARDNESS,   16.0F, NBT_RESISTANCE,   16.0F, NBT_TEXTURE, "shaft1"               , NBT_DESIGNS, 7));


        aMat = MT.Al;              aRegistry.add("Alumnium Wall"  , "kTFRUAddon: Multiblock", 30102, 17200, MultiTileEntityMultiBlockPart.class , MT.Al.mToolQuality, 64, aMachine   , UT.NBT.make(NBT_MATERIAL, MT.Al, NBT_HARDNESS,   12.0F, NBT_RESISTANCE,   12.0F, NBT_TEXTURE, "metalwall" , NBT_DESIGNS, 7),
                "wPP", "hPP", 'P', OP.plate.dat(aMat)); RM.Welder.addRecipe2(F, 16, 256, OP.plate.mat(aMat, 4), ST.tag(10), aRegistry.getItem());



        aMat = MT.Al;              aRegistry.add("Light Module (UV)"     , "kTFRUAddon: Multiblock", 30110, 17200, MultiTileEntityMultiBlockPart.class , aMat.mToolQuality, 16, aMachine   , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   6.0F, NBT_RESISTANCE,   6.0F, NBT_TEXTURE, "lightmodule1"   , NBT_DESIGNS, 7),
                "SwS", "PLP", "XMX", 'L',  IL.Comp_Laser_Gas_Ar , 'S', OP.stick.dat(aMat), 'X', OP.wireGt02.dat(MT.Cu), 'C', CS.OD_CIRCUITS[2], 'M', OP.casingMachine.dat(aMat),'P',OP.plate.dat(aMat));


        //aMat = ;     aRegistry.add("Light Module (DUV)"     , "kTFRUAddon: Multiblock", 30111, 17200, MultiTileEntityMultiBlockPart.class , aMat.mToolQuality, 16, aMachine   , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   6.0F, NBT_RESISTANCE,   6.0F, NBT_TEXTURE, "lightmodule1"   , NBT_DESIGNS, 7));

        //aMat = ;     aRegistry.add("Light Module (EUV)"     , "kTFRUAddon: Multiblock", 30112, 17200, MultiTileEntityMultiBlockPart.class , aMat.mToolQuality, 16, aMachine   , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   6.0F, NBT_RESISTANCE,   6.0F, NBT_TEXTURE, "lightmodule1"   , NBT_DESIGNS, 7));




        aMat = MT.Al;              aRegistry.add("Mask Aligner IO Manager (UV)"     , "kTFRUAddon: Multiblock", 30120, 17200, MultiTileEntityMultiBlockPart.class , aMat.mToolQuality, 16, aMachine   , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   6.0F, NBT_RESISTANCE,   6.0F, NBT_TEXTURE, "lightmodule1"   , NBT_DESIGNS, 7),
                "SCS", "YMY", "XwX", 'C', CS.OD_CIRCUITS[2], 'S', OP.pipeSmall.dat(aMat),'Y', IL.CONVEYERS[2], 'X', OP.wireGt02.dat(MT.Cu), 'C', CS.OD_CIRCUITS[2], 'M', OP.casingMachine.dat(aMat));


        //aMat = ;     aRegistry.add("Mask Aligner IO Manager (DUV)"     , "kTFRUAddon: Multiblock", 30121, 17200, MultiTileEntityMultiBlockPart.class , aMat.mToolQuality, 16, aMachine   , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   6.0F, NBT_RESISTANCE,   6.0F, NBT_TEXTURE, "lightmodule1"   , NBT_DESIGNS, 7));

        //aMat = ;     aRegistry.add("Mask Aligner IO Manager (EUV)"     , "kTFRUAddon: Multiblock", 30122, 17200, MultiTileEntityMultiBlockPart.class , aMat.mToolQuality, 16, aMachine   , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   6.0F, NBT_RESISTANCE,   6.0F, NBT_TEXTURE, "lightmodule1"   , NBT_DESIGNS, 7));



        //special part
        aMat = MT.Al;              aRegistry.add("Mask Aligner Energy Module (UV)"     , "kTFRUAddon: Multiblock", 31000, 17200, MultiBlockPartEnergyConsumer.class , MT.Al.mToolQuality, 16, aMachine   , UT.NBT.make(NBT_MATERIAL, MT.Al, NBT_HARDNESS,   6.0F, NBT_RESISTANCE,   6.0F, NBT_TEXTURE, "energymodule1"   , NBT_INPUT, 218, NBT_INPUT_MIN,    176, NBT_INPUT_MAX,   256  , NBT_ENERGY_ACCEPTED, TD.Energy.EU),
                "ShS", "CwC", "XTX" ,'T',gRegistry.getItem(10042), 'S', OP.stickLong.dat(aMat), 'X', OP.wireGt08.dat(MT.Cu), 'C', CS.OD_CIRCUITS[2], 'M', OP.casingMachine.dat(aMat));


        //aMat = ;     aRegistry.add("Mask Aligner Energy Module (DUV)"     , "kTFRUAddon: Multiblock", 31001, 17200, MultiTileEntityMultiBlockPartEnergyConsumer.class , aMat.mToolQuality, 16, aMachine   , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   6.0F, NBT_RESISTANCE,   6.0F, NBT_TEXTURE, "energymodule2"   , NBT_INPUT, 3072, NBT_INPUT_MIN,    2048, NBT_INPUT_MAX,   4096  , NBT_ENERGY_ACCEPTED, TD.Energy.EU));

        //aMat = ;     aRegistry.add("Mask Aligner Energy Module (EUV)"     , "kTFRUAddon: Multiblock", 31002, 17200, MultiTileEntityMultiBlockPartEnergyConsumer.class , aMat.mToolQuality, 16, aMachine   , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   6.0F, NBT_RESISTANCE,   6.0F, NBT_TEXTURE, "energymodule3"   , NBT_DESIGNS, 7));


        aMat = MT.Cr;  aRegistry.add("Compute Cluster"     , "kTFRUAddon: Multiblock", 32005, 17200, MultiBlockPartComputeCluster.class , aMat.mToolQuality, 16, aMachine   , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   6.0F, NBT_RESISTANCE,   6.0F, NBT_TEXTURE, "computercluster"   , NBT_DESIGNS, 7));

        aMat = MT.StainlessSteel;              aRegistry.add("Compute Cluster Simple"     , "kTFRUAddon: Multiblock", 32006, 17200, MultiBlockPartComputeClusterSimple.class , aMat.mToolQuality, 16, aMachine   , UT.NBT.make(NBT_MATERIAL, aMat, NBT_HARDNESS,   6.0F, NBT_RESISTANCE,   6.0F, NBT_TEXTURE, "computerclustersimple"   , NBT_DESIGNS, 7));


    }
}