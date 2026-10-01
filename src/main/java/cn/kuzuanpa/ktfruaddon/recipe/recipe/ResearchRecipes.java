package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import cn.kuzuanpa.ktfruaddon.api.material.matList;
import cn.kuzuanpa.ktfruaddon.api.material.prefix.prefixList;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cpw.mods.fml.common.FMLLog;
import gregapi.data.*;
import gregapi.recipes.Recipe;
import gregapi.util.CR;
import gregapi.util.ST;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.*;

/** Explicit research recipe rewrite. No crafting-grid conversion is performed here. */
public final class ResearchRecipes {
    private ResearchRecipes() {}

    public static void init() {
        moveExplicitMapRecipes();
        registerGtShapedAssemblies();
        registerKtfruShapedAssemblies();
        addMissingResearchRecipes();
    }

    private static void moveExplicitMapRecipes() {
        move(recipeMaps.Assembler, recipeMaps.ResearchAssembler,
                ItemList.ComputerTF3386.get(1), ItemList.ComputerTF3386S.get(1), ItemList.ComputerBasicCircuits.get(1),
                ItemList.ComputerTF3586.get(1), ItemList.ComputerTF3586S.get(1), ItemList.ComputerGoodCircuits.get(1),
                ItemList.ComputerGT1000.get(1), ItemList.ComputerGT1090.get(1),
                ItemList.ComputerGT2000.get(1), ItemList.ComputerGT2090.get(1), ItemList.ComputerGT3660.get(1), ItemList.ComputerGT3680.get(1), ItemList.ComputerGT3699.get(1),
                ItemList.ComputerGT3660v2.get(1), ItemList.ComputerGT3680v2.get(1), ItemList.ComputerGT3699v2.get(1),
                ItemList.ComputerGT3660v3.get(1), ItemList.ComputerGT3680v3.get(1), ItemList.ComputerGT3699v3.get(1),
                ItemList.ComputerGT3660v4.get(1), ItemList.ComputerGT3680v4.get(1), ItemList.ComputerGT3699v4.get(1),
                ItemList.ComputerGT3680v3e.get(1), ItemList.ComputerGT3699v3e.get(1), ItemList.ComputerGT3680v4e.get(1), ItemList.ComputerGT3699v4e.get(1),
                ItemList.IntelligentCore.get(1), ItemList.Co60FlawDetectionCore.get(1),
                ktfru(30106), ktfru(30107), ktfru(30108), ktfru(30114), ktfru(30115), ktfru(31117), ktfru(31118),
                ktfru(30075), ktfru(30076), ktfru(30080), ktfru(31112), ktfru(31113), ktfru(31114),
                ktfru(31091), ktfru(31092), ktfru(31093), ktfru(31094), ktfru(31095),
                ktfru(30071), ktfru(30072), ktfru(30014), ktfru(30015),
                ktfru(31016), ktfru(31017), ktfru(31018), ktfru(31025), ktfru(31026), ktfru(31027));

        move(recipeMaps.CatalyticReactor, recipeMaps.ResearchReactor,
                ItemList.NickelCatalystCluster.get(1), ItemList.PlatinumPalladiumCatalystCluster.get(1),
                ItemList.ZeoliteCatalystCluster.get(1), ItemList.ZieglerNattaCatalystCluster.get(1),
                ItemList.PolycondensationCatalystCluster.get(1));
        move(recipeMaps.CNC, recipeMaps.ResearchReactor, ktfru(31111));
        move(recipeMaps.MirrorGoldQuantumization, recipeMaps.ResearchReactor, ktfru(31115), ktfru(31116));
        move(RM.Bath, recipeMaps.ResearchReactor, IL.Circuit_Basic.get(1));
        move(RM.Canner, recipeMaps.ResearchReactor,
                IL.Comp_Laser_Gas_He.get(1), IL.Comp_Laser_Gas_Ne.get(1), IL.Comp_Laser_Gas_Ar.get(1),
                IL.Comp_Laser_Gas_Kr.get(1), IL.Comp_Laser_Gas_Xe.get(1), IL.Comp_Laser_Gas_HeNe.get(1),
                IL.Comp_Laser_Gas_CO.get(1), IL.Comp_Laser_Gas_CO2.get(1));
        move(RM.LaserEngraver, recipeMaps.ResearchReactor, ItemList.GoodCircuitPartCore.get(1));
        move(RM.Extruder, recipeMaps.ResearchReactor, OP.plate.mat(matList.PEEK.mat, 1));
    }

    private static void registerGtShapedAssemblies() {
        // Lightning processors: replace the tool-bearing grid with explicit consumables.
        ItemStack[] ironWires = {
                OP.wireGt01.mat(ANY.Iron, 4), OP.wireGt02.mat(ANY.Iron, 4),
                OP.wireGt04.mat(ANY.Iron, 4), OP.wireGt08.mat(ANY.Iron, 4)
        };
        for (int i = 0; i < 4; i++) {
            ItemStack out = gt(20501 + i);
            assemble(out, 64L << i, 200,
                    OP.casingMachine.mat(MT.DATA.Electric_T[i + 1], 1),
                    amount(OP.wireGt02.mat(ANY.Cu, 1), 2), ironWires[i]);
            removeCrafting(out);
        }

        // Autoclave
        ItemStack autoclave = gt(22004);
        assemble(autoclave, 128, 300,
                OP.casingMachineQuadruple.mat(MT.StainlessSteel, 1),
                OP.casingSmall.mat(MT.StainlessSteel, 2),
                OP.gearGtSmall.mat(MT.StainlessSteel, 2),
                OP.pipeSmall.mat(MT.StainlessSteel, 4));
        removeCrafting(autoclave);

        // Electric dynamos T1-T3
        assemble(gt(10111), 32, 200,
                OP.casingMachineDouble.mat(MT.DATA.Electric_T[1], 1),
                OP.screw.mat(MT.DATA.Electric_T[1], 3), OP.gearGt.mat(MT.DATA.Electric_T[1], 1),
                OP.stickLong.mat(MT.IronMagnetic, 1), OP.wireGt01.mat(ANY.Cu, 2));
        assemble(gt(10112), 128, 260,
                OP.casingMachineDouble.mat(MT.DATA.Electric_T[2], 1),
                OP.screw.mat(MT.DATA.Electric_T[2], 3), OP.gearGt.mat(MT.DATA.Electric_T[2], 1),
                OP.stickLong.mat(MT.SteelMagnetic, 1), OP.wireGt02.mat(ANY.Cu, 2));
        assemble(gt(10113), 512, 320,
                OP.casingMachineDouble.mat(MT.DATA.Electric_T[3], 1),
                OP.screw.mat(MT.DATA.Electric_T[3], 3), OP.gearGt.mat(MT.DATA.Electric_T[3], 1),
                OP.stickLong.mat(MT.SteelMagnetic, 1), OP.wireGt04.mat(MT.AnnealedCopper, 2));
        removeCrafting(gt(10111), gt(10112), gt(10113));

        // Electromagnets T1-T5
        ItemStack[][] magnetWires = {
                {OP.wireGt01.mat(ANY.Cu, 6)}, {OP.wireGt02.mat(ANY.Cu, 6)},
                {OP.wireGt04.mat(MT.AnnealedCopper, 6)}, {OP.wireGt08.mat(MT.AnnealedCopper, 6)},
                {OP.wireGt16.mat(MT.AnnealedCopper, 6)}
        };
        for (int i = 0; i < 5; i++) {
            ItemStack out = gt(10031 + i);
            assemble(out, 32L << i, 200, OP.casingMachine.mat(MT.DATA.Electric_T[i + 1], 1), magnetWires[i][0]);
            removeCrafting(out);
        }

        registerBatteries();
        registerCoolers();
        registerLaserEngravers();
        ItemStack emptyLaser = IL.Comp_Laser_Gas_Empty.get(1);
        assemble(emptyLaser, 64, 200,
                OP.cableGt02.mat(MT.Cu, 1), IL.Circuit_Basic.get(1), OP.plate.mat(MT.Ag, 1),
                OP.screw.mat(MT.StainlessSteel, 2), OP.paneGlass.mat(MT.Glass, 1));
        removeCrafting(emptyLaser);
        registerCrucibles();

        ItemStack reactorCore = gt(9200);
        assemble(reactorCore, 2048, 600,
                OP.casingMachineDense.mat(MT.Pb, 1), IL.Circuit_Master.get(2), IL.PISTONS[4].get(4));
        removeCrafting(reactorCore);

        ItemStack largeMassFab = gt(17199);
        assemble(largeMassFab, 32768, 2400,
                gt(18031), IL.FIELD_GENERATORS[5].get(8));
        removeCrafting(largeMassFab);

        registerMassFabricators();
        registerMolecularScanner();
        registerReplicators();
    }

    private static void registerBatteries() {
        ItemStack[] outputs = {gt(14000), gt(14001), gt(14002), gt(14003), gt(14004)};
        ItemStack cell = IL.Battery_Lead_Acid_Cell_Filled.get(1);
        int[] wires = {1, 1, 2, 2, 1};
        int[] cells = {1, 2, 2, 3, 5};
        int[] plates = {1, 2, 2, 1, 1};
        for (int i = 0; i < outputs.length; i++) {
            List<ItemStack> inputs = new ArrayList<>();
            inputs.add(OP.cableGt01.mat(MT.DATA.Electric_T[i], wires[i]));
            inputs.add(amount(OP.plate.mat(MT.BatteryAlloy, 1), plates[i]));
            inputs.add(amount(cell, cells[i]));
            if (i >= 2) inputs.add(amount(IL.Circuit_Part_Good.get(1), 1));
            if (i >= 3) inputs.add(amount(IL.Circuit_Part_Advanced.get(1), 1));
            if (i >= 4) inputs.add(amount(IL.Circuit_Part_Elite.get(1), 1));
            assemble(outputs[i], 32L << i, 200, inputs.toArray(new ItemStack[0]));
            removeCrafting(outputs[i]);
        }
    }

    private static void registerCoolers() {
        for (int i = 0; i < 5; i++) {
            ItemStack out = gt(10161 + i);
            assemble(out, 32L << i, 200,
                    OP.casingMachine.mat(MT.DATA.Electric_T[i + 1], 1),
                    OP.cableGt01.mat(MT.DATA.Electric_T[i + 1], 2),
                    OP.plate.mat(MT.Si, i + 1), OP.plate.mat(ANY.Cu, i + 1));
            removeCrafting(out);
        }
    }

    private static void registerLaserEngravers() {
        ItemStack[] circuits = {IL.Circuit_Basic.get(1), IL.Circuit_Good.get(1), IL.Circuit_Advanced.get(1), IL.Circuit_Elite.get(1), IL.Circuit_Master.get(1)};
        for (int i = 0; i < 5; i++) {
            ItemStack out = gt(20321 + i);
            assemble(out, 32L << i, 200,
                    OP.casingMachine.mat(MT.DATA.Electric_T[i + 1], 1),
                    OP.screw.mat(MT.DATA.Electric_T[i + 1], 2),
                    OP.gearGtSmall.mat(MT.DATA.Electric_T[i + 1], 2),
                    ST.make(Blocks.hardened_clay, 1, 0), amount(circuits[i], 2));
            removeCrafting(out);
        }
    }

    private static void registerCrucibles() {
        for (int i = 0; i < 4; i++) {
            ItemStack out = gt(20251 + i);
            assemble(out, 32L << i, 300,
                    OP.casingMachineDouble.mat(MT.DATA.Heat_T[i + 1], 1),
                    OP.plateDouble.mat(ANY.Cu, 2), ST.make(Blocks.brick_block, 1, 0),
                    OP.pipeMedium.mat(MT.DATA.Heat_T[i + 1], 2),
                    gt(i == 0 ? 1005 : 1039));
            removeCrafting(out);
        }
    }

    private static void registerMassFabricators() {
        for (int i = 0; i < 5; i++) {
            ItemStack out = gt(20411 + i);
            assemble(out, 32L << i, 300,
                    OP.casingMachine.mat(MT.Osmiridium, 1), IL.Processor_Crystal_Ruby.get(2),
                    IL.Processor_Crystal_Sapphire.get(2), IL.FIELD_GENERATORS[i + 1].get(4));
            removeCrafting(out);
        }
    }

    private static void registerMolecularScanner() {
        ItemStack out = gt(20423);
        assemble(out, 512, 400,
                OP.casingMachine.mat(MT.Osmiridium, 1), IL.Processor_Crystal_Diamond.get(2),
                IL.Processor_Crystal_Emerald.get(2), IL.Processor_Crystal_Ruby.get(2),
                IL.Processor_Crystal_Sapphire.get(2), IL.FIELD_GENERATORS[3].get(4),
                IL.SENSORS[3].get(2));
        removeCrafting(out);
    }

    private static void registerReplicators() {
        for (int i = 0; i < 5; i++) {
            ItemStack out = gt(20431 + i);
            assemble(out, 32L << i, 300,
                    OP.casingMachine.mat(MT.Osmiridium, 1), IL.Processor_Crystal_Emerald.get(2),
                    IL.Processor_Crystal_Sapphire.get(1), IL.EMITTERS[i + 1].get(3),
                    IL.FIELD_GENERATORS[i + 1].get(2));
            removeCrafting(out);
        }
    }

    private static void registerKtfruShapedAssemblies() {
        registerCircuitAssemblers();
        registerMicroscopes();
        registerCnc();
        registerSteamTurbineHousings();
        registerComputeControllers();
        registerCatalyticReactors();
        registerQuantumMachines();
    }

    private static void registerCircuitAssemblers() {
        ItemStack[] circuits = {IL.Circuit_Basic.get(1), IL.Circuit_Good.get(1), IL.Circuit_Advanced.get(1), IL.Circuit_Elite.get(1), IL.Circuit_Master.get(1)};
        for (int i = 0; i < 5; i++) {
            ItemStack out = ktfru(23000 + i);
            assemble(out, 32L << i, 300,
                    OP.screw.mat(MT.DATA.Electric_T[i + 1], 2), OP.lens.mat(MT.Glass, 1),
                    OP.gearGt.mat(MT.DATA.Electric_T[i + 1], 2), ST.make(Blocks.hardened_clay, 1, 0),
                    amount(circuits[i], 2), OP.casingMachine.mat(MT.DATA.Electric_T[i + 1], 1));
            removeCrafting(out);
        }
    }

    private static void registerMicroscopes() {
        ItemStack uv = ktfru(30003);
        assemble(uv, 512, 600,
                OP.stickLong.mat(MT.Al, 2), IL.Comp_Laser_Gas_Ar.get(1),
                OP.wireGt02.mat(MT.Cu, 2), IL.Circuit_Advanced.get(2), OP.casingMachine.mat(MT.Al, 1));
        removeCrafting(uv);

        ItemStack optical = ktfru(30077);
        assemble(optical, 512, 600,
                OP.lens.mat(MT.Glass, 2), IL.Circuit_Advanced.get(1), OP.wireFine.mat(MT.W, 4),
                OP.casingMachine.mat(MT.Al, 1), OP.plate.mat(MT.Si, 6));
        removeCrafting(optical);

        ItemStack uvPlus = ktfru(30009);
        assemble(uvPlus, 2048, 800,
                OP.stickLong.mat(MT.StainlessSteel, 2), IL.Comp_Laser_Gas_Ar.get(1),
                OP.wireGt04.mat(MT.Cu, 2), IL.Circuit_Master.get(2), OP.casingMachine.mat(MT.StainlessSteel, 1));
        removeCrafting(uvPlus);

        ItemStack duv = ktfru(30010);
        assemble(duv, 8192, 1200,
                OP.stickLong.mat(MT.Cr, 2), IL.Comp_Laser_Gas_Ar.get(2), OP.wireGt04.mat(MT.Au, 4),
                IL.Circuit_Master.get(2), OP.casingMachine.mat(MT.Cr, 1));
        removeCrafting(duv);

        ItemStack euv = ktfru(30011);
        assemble(euv, 32768, 1800,
                OP.stickLong.mat(MT.TungstenSteel, 4), IL.Comp_Laser_Gas_Ar.get(4), OP.wireGt08.mat(MT.Au, 4),
                IL.Circuit_Ultimate.get(2), OP.casingMachine.mat(MT.W, 1));
        removeCrafting(euv);

        ItemStack electron = ktfru(30078);
        assemble(electron, 2048, 800,
                OP.lens.mat(MT.Glass, 4), IL.Circuit_Elite.get(1), OP.wireFine.mat(MT.W, 16),
                OP.casingMachine.mat(MT.StainlessSteel, 1), OP.plate.mat(MT.Si, 4));
        removeCrafting(electron);
    }

    private static void registerCnc() {
        ItemStack out = ktfru(30012);
        assemble(out, 512, 600,
                OP.dust.mat(ANY.Glowstone, 2), OP.plate.mat(MT.Glass, 1), OP.wireFine.mat(MT.Ag, 2),
                OP.casingMachine.mat(MT.Al, 1), ktfru(32006));
        removeCrafting(out);
    }

    private static void registerSteamTurbineHousings() {
        ItemStack[] outs = {ktfru(30020), ktfru(30021), ktfru(30022), ktfru(30023)};
        ItemStack[] plates = {OP.blockPlate.mat(MT.Magnalium, 1), OP.blockPlate.mat(MT.Trinitanium, 1), OP.blockPlate.mat(MT.Graphene, 1), OP.blockPlate.mat(MT.Vibramantium, 1)};
        ItemStack[] circuits = {IL.Circuit_Elite.get(1), IL.Circuit_Master.get(1), IL.Circuit_Master.get(1), IL.Circuit_Master.get(1)};
        int[] designs = {18022, 18026, 18023, 18025};
        for (int i = 0; i < outs.length; i++) {
            assemble(outs[i], 4096L << i, 800,
                    plates[i], gt(designs[i]), ItemList.VibrateDetector.get(1), amount(circuits[i], 2));
            removeCrafting(outs[i]);
        }
    }

    private static void registerComputeControllers() {
        ItemStack small = ktfru(32006);
        assemble(small, 2048, 600,
                OP.plate.mat(MT.ElectricalSteel, 2), IL.Circuit_Good.get(1), ktfru(32005),
                OP.plate.mat(MT.StainlessSteel, 1), OP.wireFine.mat(MT.Cu, 4));
        removeCrafting(small);

        ItemStack c58 = ktfru(30058);
        assemble(c58, 4096, 800,
                OP.plate.mat(MT.StainlessSteel, 1), IL.Sensor_EV.get(2), IL.Circuit_Elite.get(2),
                ktfru(32006), OP.wireFine.mat(MT.Graphene, 4));
        removeCrafting(c58);

        ItemStack c59 = ktfru(30059);
        assemble(c59, 16384, 1200,
                OP.plate.mat(MT.StainlessSteel, 1), IL.Sensor_IV.get(2), IL.Emitter_IV.get(2),
                IL.Circuit_Master.get(2), ktfru(32005), OP.wireFine.mat(MT.Graphene, 4));
        removeCrafting(c59);

        ItemStack c60 = ktfru(30060);
        assemble(c60, 65536, 1600,
                OP.plateDouble.mat(MT.StainlessSteel, 1), IL.Sensor_LuV.get(4), IL.Emitter_LuV.get(4),
                IL.Circuit_Ultimate.get(4), ktfru(32005), OP.wireFine.mat(MT.Naquadria, 4));
        removeCrafting(c60);
    }

    private static void registerCatalyticReactors() {
        ItemStack ev = ktfru(30120);
        assemble(ev, 8192, 1200,
                OP.plateDouble.mat(MT.StainlessSteel, 4), ktfru(31200), IL.Circuit_Elite.get(3),
                IL.MOTORS[3].get(1));
        removeCrafting(ev);

        ItemStack iv = ktfru(30121);
        assemble(iv, 32768, 1800,
                OP.plateDouble.mat(MT.TungstenSteel, 4), ktfru(31200), IL.Circuit_Master.get(3),
                IL.MOTORS[4].get(1));
        removeCrafting(iv);
    }

    private static void registerQuantumMachines() {
        registerQuantumComputeArrays();
        registerQuantumParts();

        ItemStack cos = ktfru(30104);
        assemble(cos, 32768, 1600,
                OP.plateDouble.mat(MT.TungstenSteel, 2), IL.Circuit_Ultimate.get(2),
                OP.casingMachine.mat(MT.TungstenSteel, 1), IL.SENSORS[3].get(1));
        removeCrafting(cos);

        ItemStack center = ktfru(30079);
        assemble(center, 131072, 2400,
                IL.Sensor_LuV.get(2), OP.plateDouble.mat(MT.Trinaquadalloy, 1), IL.Circuit_Ultimate.get(4),
                ktfru(32005), OP.wireFine.mat(MT.Naquadria, 4));
        removeCrafting(center);

        ItemStack mirror = ktfru(30073);
        assemble(mirror, 32768, 1600,
                IL.Emitter_LuV.get(4), OP.plate.mat(matList.Ij.mat, 2), IL.Circuit_Ultimate.get(2),
                OP.casingMachine.mat(MT.Ir, 1));
        removeCrafting(mirror);

        ItemStack relay = ktfru(30074);
        assemble(relay, 32768, 1600,
                IL.Emitter_LuV.get(4), prefixList.AbsolutelyPureDust.mat(matList.Ij.mat, 2),
                IL.Circuit_Ultimate.get(2), OP.casingMachine.mat(MT.Ir, 1));
        removeCrafting(relay);
    }

    private static void registerQuantumComputeArrays() {
        ItemStack[] outs = {ktfru(30065), ktfru(30066), ktfru(30067), ktfru(30068), ktfru(30069), ktfru(30070)};
        ItemStack[][] parts = {
                {IL.Sensor_IV.get(2), IL.Circuit_Master.get(2), OP.plate.mat(MT.Nq_522, 2), ktfru(31066), OP.wireFine.mat(MT.Graphene, 2)},
                {IL.Sensor_LuV.get(2), IL.Emitter_LuV.get(1), IL.Circuit_Ultimate.get(2), ktfru(31070), OP.wireFine.mat(MT.Naquadria, 2)},
                {IL.Sensor_LuV.get(4), IL.Emitter_LuV.get(2), IL.Circuit_Ultimate.get(4), ktfru(31074), OP.wireFine.mat(MT.Naquadria, 4)},
                {IL.Sensor_LuV.get(8), IL.Emitter_LuV.get(4), IL.Circuit_Ultimate.get(8), ktfru(31078), OP.wireFine.mat(MT.Naquadria, 8)},
                {IL.Sensor_LuV.get(8), OP.plateDouble.mat(MT.Naquadria, 2), IL.Circuit_Ultimate.get(8), ktfru(31083), OP.wireFine.mat(MT.Naquadria, 8)},
                {IL.Sensor_LuV.get(16), IL.Emitter_LuV.get(8), IL.Circuit_Ultimate.get(16), ktfru(31087), OP.wireFine.mat(MT.Naquadria, 16)}
        };
        for (int i = 0; i < outs.length; i++) {
            assemble(outs[i], 32768L << (i / 2), 2400, parts[i]);
            removeCrafting(outs[i]);
        }
    }

    private static void registerQuantumParts() {
        ItemStack[] outs = {ktfru(31065), ktfru(31066), ktfru(31067), ktfru(31068), ktfru(31069), ktfru(31070), ktfru(31071), ktfru(31072), ktfru(31073), ktfru(31074), ktfru(31075)};
        ItemStack ij = OP.plate.mat(matList.Ij.mat, 1);
        ItemStack[][] inputs = {
                {amount(ij, 4), OP.plate.mat(MT.StainlessSteel, 2), IL.Circuit_Master.get(2), OP.casingMachine.mat(MT.StainlessSteel, 1)},
                {amount(ij, 4), OP.wireFine.mat(MT.Graphene, 2), OP.plate.mat(MT.Nq_522, 2), IL.Circuit_Master.get(1)},
                {amount(ij, 2), OP.pipeSmall.mat(MT.StainlessSteel, 4), IL.Circuit_Elite.get(2), OP.casingMachine.mat(MT.StainlessSteel, 1)},
                {amount(ij, 2), IL.Emitter_IV.get(4), OP.plate.mat(MT.Nq_522, 2), IL.Circuit_Master.get(1)},
                {amount(ij, 2), OP.plate.mat(MT.Naquadria, 4), IL.Circuit_Ultimate.get(2), OP.casingMachine.mat(MT.Naquadah, 1)},
                {amount(ij, 4), OP.plate.mat(MT.Naquadria, 4), IL.Circuit_Ultimate.get(1)},
                {amount(ij, 2), IL.Emitter_LuV.get(4), OP.plate.mat(MT.Naquadria, 2), IL.Circuit_Ultimate.get(1)},
                {amount(ij, 2), OP.plateDouble.mat(MT.Naquadria, 4), IL.Circuit_Ultimate.get(2), OP.casingMachineDouble.mat(MT.Naquadria, 1)},
                {amount(ij, 4), OP.wireFine.mat(MT.Naquadria, 2), OP.plate.mat(MT.Naquadria, 2), IL.Circuit_Ultimate.get(2)},
                {amount(ij, 2), IL.Emitter_LuV.get(4), OP.plate.mat(MT.Naquadria, 2), IL.Circuit_Ultimate.get(2)},
                {amount(ij, 4), OP.wireFine.mat(MT.Naquadria, 2), OP.plate.mat(MT.Naquadria, 2), IL.Circuit_Ultimate.get(2)}
        };
        for (int i = 0; i < outs.length; i++) {
            assemble(outs[i], 16384L << (i / 4), 1800, inputs[i]);
            removeCrafting(outs[i]);
        }
    }

    private static void addMissingResearchRecipes() {
        ItemStack armorSealant = ItemList.ArmorAirSealant.get(1);
        assemble(armorSealant, 128, 200,
                OP.foil.mat(MT.Rubber, 4), OP.plate.mat(matList.PEEK.mat, 1), IL.Circuit_Good.get(1));

        ItemStack suitCloth = ItemList.SpaceSuitCloth.get(1);
        assemble(suitCloth, 64, 160,
                ST.make(Blocks.wool, 4, 0), ST.make(net.minecraft.init.Items.string, 4, 0), OP.foil.mat(MT.Rubber, 2));

        ItemStack tmCore = ItemList.Tm170FlawDetectionCore.get(1);
        assemble(tmCore, 512, 300,
                OP.plateDense.mat(MT.Pb, 2), OP.plate.mat(MT.Tm, 1), IL.SENSORS[2].get(1));
    }

    private static void assemble(ItemStack aOutput, long aEUt, long aDuration, ItemStack... aInputs) {
        recipeMaps.ResearchAssembler.addRecipeX(F, aEUt, aDuration, aInputs, ZL_FS, ZL_FS, aOutput.copy());
    }

    private static void removeCrafting(ItemStack... aOutputs) {
        for (ItemStack output : aOutputs) CR.delate(output);
    }

    private static ItemStack gt(int aId) {
        return GTTileEntityRegistry.gregtech.getItem(aId);
    }

    private static ItemStack ktfru(int aId) {
        return GTTileEntityRegistry.ktfruaddon.getItem(aId);
    }

    private static ItemStack amount(ItemStack aStack, int aAmount) {
        ItemStack stack = aStack.copy();
        stack.stackSize = aAmount;
        return stack;
    }

    private static boolean sameItem(ItemStack aFirst, ItemStack aSecond) {
        return aFirst != null && aSecond != null
                && aFirst.getItem() == aSecond.getItem()
                && aFirst.getItemDamage() == aSecond.getItemDamage();
    }

    private static void move(Recipe.RecipeMap aSource, Recipe.RecipeMap aTarget, ItemStack... aOutputs) {
        for (ItemStack output : aOutputs) {
            int moved = 0;
            for (Recipe recipe : new ArrayList<>(aSource.mRecipeList)) {
                if (!sameItem(recipe.getOutput(0), output)) continue;
                aSource.mRecipeList.remove(recipe);
                if (aTarget.add(recipe, false) == null) {
                    aSource.mRecipeList.add(recipe);
                    FMLLog.log(Level.WARN, "[kTFRUAddon] Failed moving recipe %s to %s", output.getDisplayName(), aTarget.mNameInternal);
                } else {
                    moved++;
                }
            }
            if (moved == 0) FMLLog.log(Level.WARN, "[kTFRUAddon] No recipe found in %s for %s", aSource.mNameInternal, output.getDisplayName());
            CR.delate(output);
        }
        rebuildIndexes(aSource);
    }

    private static void rebuildIndexes(Recipe.RecipeMap aMap) {
        aMap.mRecipeFluidMap.clear();
        aMap.mMinInputTankSizes.clear();
        aMap.mMaxFluidInputSize = 1000;
        aMap.mMaxFluidOutputSize = 1000;
        for (Recipe recipe : aMap.mRecipeList) {
            for (FluidStack fluid : recipe.mFluidInputs) {
                if (fluid == null) continue;
                String name = fluid.getFluid().getName();
                Collection<Recipe> recipes = aMap.mRecipeFluidMap.get(name);
                if (recipes == null) aMap.mRecipeFluidMap.put(name, recipes = new ArrayList<>());
                recipes.add(recipe);
                aMap.mMaxFluidInputSize = Math.max(aMap.mMaxFluidInputSize, fluid.amount);
                aMap.mMinInputTankSizes.merge(name, (long) fluid.amount, Math::max);
            }
            for (FluidStack fluid : recipe.mFluidOutputs) {
                if (fluid != null) aMap.mMaxFluidOutputSize = Math.max(aMap.mMaxFluidOutputSize, fluid.amount);
            }
        }
        aMap.reInit();
    }
}
