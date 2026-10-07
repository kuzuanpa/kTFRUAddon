/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import cn.kuzuanpa.ktfruaddon.api.material.matList;
import cn.kuzuanpa.ktfruaddon.api.material.prefix.prefixList;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cn.kuzuanpa.ktfruaddon.item.items.random.itemIT;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.*;
import gregapi.oredict.OreDictManager;
import gregapi.util.ST;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static gregapi.data.CS.*;

public class ResearchRecipes {
    public static void init() {
        MultiTileEntityRegistry gt = GTTileEntityRegistry.gregtech, ktfru = GTTileEntityRegistry.ktfruaddon;

        registerGtResearchRecipes();

        // GT machine assemblies.
        add(gt.getItem(20501), 64, 200, OP.casingMachine.mat(MT.DATA.Electric_T[1], 1), OP.wireGt04.mat(MT.Sn, 2), OP.wireGt01.mat(ANY.Iron, 4));
        add(gt.getItem(20502), 128, 200, OP.casingMachine.mat(MT.DATA.Electric_T[2], 1), OP.wireGt04.mat(ANY.Cu, 2), OP.wireGt02.mat(ANY.Iron, 4));
        add(gt.getItem(20503), 256, 200, OP.casingMachine.mat(MT.DATA.Electric_T[3], 1), OP.wireGt04.mat(MT.Au, 2), OP.wireGt04.mat(ANY.Iron, 4));
        add(gt.getItem(20504), 512, 200, OP.casingMachine.mat(MT.DATA.Electric_T[4], 1), OP.wireGt04.mat(MT.Al, 2), OP.wireGt08.mat(ANY.Iron, 4));
        add(gt.getItem(22004), 128, 300, OP.casingMachineQuadruple.mat(MT.StainlessSteel, 1), OP.casingSmall.mat(MT.StainlessSteel, 2), OP.gearGtSmall.mat(MT.StainlessSteel, 2), OP.pipeSmall.mat(MT.StainlessSteel, 3));
        add(gt.getItem(10031), 32, 200, ST.tag(0),OP.casingMachine.mat(MT.DATA.Electric_T[1], 1), OP.wireGt01.mat(ANY.Cu, 6));
        add(gt.getItem(10032), 64, 200, ST.tag(0),OP.casingMachine.mat(MT.DATA.Electric_T[2], 1), OP.wireGt02.mat(ANY.Cu, 6));
        add(gt.getItem(10033), 128, 200, ST.tag(0),OP.casingMachine.mat(MT.DATA.Electric_T[3], 1), OP.wireGt04.mat(MT.AnnealedCopper, 6));
        add(gt.getItem(10034), 256, 200, ST.tag(0),OP.casingMachine.mat(MT.DATA.Electric_T[4], 1), OP.wireGt08.mat(MT.AnnealedCopper, 6));
        add(gt.getItem(10035), 512, 200, ST.tag(0),OP.casingMachine.mat(MT.DATA.Electric_T[5], 1), OP.wireGt16.mat(MT.AnnealedCopper, 6));
        add(gt.getItem(14000), 32, 200, OP.cableGt01.mat(MT.Pb, 1), OP.plate.mat(MT.BatteryAlloy, 1), IL.Battery_Lead_Acid_Cell_Filled.get(1));
        add(gt.getItem(14001), 64, 200, OP.cableGt01.mat(MT.Sn, 1), OP.plate.mat(MT.BatteryAlloy, 2), IL.Battery_Lead_Acid_Cell_Filled.get(2));
        add(gt.getItem(14002), 128, 200, OP.cableGt01.mat(ANY.Cu, 2), OP.plate.mat(MT.BatteryAlloy, 2), IL.Battery_Lead_Acid_Cell_Filled.get(3), IL.Circuit_Part_Good.get(1));
        add(gt.getItem(14003), 256, 200, OP.cableGt01.mat(MT.Au, 2), OP.plate.mat(MT.BatteryAlloy, 1), IL.Battery_Lead_Acid_Cell_Filled.get(4), IL.Circuit_Part_Advanced.get(1));
        add(gt.getItem(14004), 512, 200, OP.cableGt01.mat(MT.Al, 2), OP.plate.mat(MT.BatteryAlloy, 1), IL.Battery_Lead_Acid_Cell_Filled.get(5), IL.Circuit_Part_Elite.get(1));
        add(gt.getItem(10161), 32, 200, OP.casingMachine.mat(MT.DATA.Electric_T[1], 1), OP.cableGt01.mat(MT.Sn, 2), OP.plate.mat(MT.Si, 2), OP.plate.mat(ANY.Cu, 2));
        add(gt.getItem(10162), 64, 200, OP.casingMachine.mat(MT.DATA.Electric_T[2], 1), OP.cableGt01.mat(ANY.Cu, 2), OP.plateDouble.mat(MT.Si, 2), OP.plateDouble.mat(ANY.Cu, 2));
        add(gt.getItem(10163), 128, 200, OP.casingMachine.mat(MT.DATA.Electric_T[3], 1), OP.cableGt01.mat(MT.Au, 2), OP.plateTriple.mat(MT.Si, 2), OP.plateTriple.mat(ANY.Cu, 2));
        add(gt.getItem(10164), 256, 200, OP.casingMachine.mat(MT.DATA.Electric_T[4], 1), OP.cableGt01.mat(MT.Al, 2), OP.plateQuadruple.mat(MT.Si, 2), OP.plateQuadruple.mat(ANY.Cu, 2));
        add(gt.getItem(10165), 512, 200, OP.casingMachine.mat(MT.DATA.Electric_T[5], 1), OP.cableGt01.mat(MT.Pt, 2), OP.plateQuintuple.mat(MT.Si, 2), OP.plateQuintuple.mat(ANY.Cu, 2));
        add(gt.getItem(20321), 32, 200, OP.casingMachine.mat(MT.DATA.Electric_T[1], 1), OP.screw.mat(MT.DATA.Electric_T[1], 2), OP.gearGtSmall.mat(MT.DATA.Electric_T[1], 2), ST.make(Blocks.hardened_clay, 1, 0), IL.Circuit_Basic.get(2));
        add(gt.getItem(20322), 64, 200, OP.casingMachine.mat(MT.DATA.Electric_T[2], 1), OP.screw.mat(MT.DATA.Electric_T[2], 2), OP.gearGtSmall.mat(MT.DATA.Electric_T[2], 2), ST.make(Blocks.hardened_clay, 1, 0), IL.Circuit_Good.get(2));
        add(gt.getItem(20323), 128, 200, OP.casingMachine.mat(MT.DATA.Electric_T[3], 1), OP.screw.mat(MT.DATA.Electric_T[3], 2), OP.gearGtSmall.mat(MT.DATA.Electric_T[3], 2), ST.make(Blocks.hardened_clay, 1, 0), IL.Circuit_Advanced.get(2));
        add(gt.getItem(20324), 256, 200, OP.casingMachine.mat(MT.DATA.Electric_T[4], 1), OP.screw.mat(MT.DATA.Electric_T[4], 2), OP.gearGtSmall.mat(MT.DATA.Electric_T[4], 2), ST.make(Blocks.hardened_clay, 1, 0), IL.Circuit_Elite.get(2));
        add(gt.getItem(20325), 512, 200, OP.casingMachine.mat(MT.DATA.Electric_T[5], 1), OP.screw.mat(MT.DATA.Electric_T[5], 2), OP.gearGtSmall.mat(MT.DATA.Electric_T[5], 2), ST.make(Blocks.hardened_clay, 1, 0), IL.Circuit_Master.get(2));
        add(IL.Comp_Laser_Gas_Empty.get(1), 64, 150, OP.cableGt02.mat(MT.Cu, 1), IL.Circuit_Basic.get(1), OP.plate.mat(MT.Ag, 1), OP.screw.mat(MT.StainlessSteel, 2), OP.paneGlass.mat(MT.Glass, 1));
        add(IL.Comp_Laser_Gas_Empty.get(1), 64, 200, OP.cableGt02.mat(MT.Cu, 1), ST.make(MD.MO, "isolinear_circuit",1), OP.plate.mat(MT.Ag, 1), OP.screw.mat(MT.StainlessSteel, 2), OP.paneGlass.mat(MT.Glass, 1));
        add(gt.getItem(20251), 32, 300, OP.casingMachineDouble.mat(MT.DATA.Heat_T[1], 1), OP.plateDouble.mat(ANY.Cu, 2), ST.make(Blocks.brick_block, 2, 0), OP.pipeMedium.mat(MT.DATA.Heat_T[1], 2), gt.getItem(1005));
        add(gt.getItem(20252), 64, 300, OP.casingMachineDouble.mat(MT.DATA.Heat_T[2], 1), OP.plateDouble.mat(ANY.Cu, 2), ST.make(Blocks.brick_block, 2, 0), OP.pipeMedium.mat(MT.DATA.Heat_T[2], 2), gt.getItem(1039));
        add(gt.getItem(20253), 128, 300, OP.casingMachineDouble.mat(MT.DATA.Heat_T[3], 1), OP.plateDouble.mat(ANY.Cu, 2), ST.make(Blocks.brick_block, 2, 0), OP.pipeMedium.mat(MT.DATA.Heat_T[3], 2), gt.getItem(1039));
        add(gt.getItem(20254), 256, 300, OP.casingMachineDouble.mat(MT.DATA.Heat_T[4], 1), OP.plateDouble.mat(ANY.Cu, 2), ST.make(Blocks.brick_block, 2, 0), OP.pipeMedium.mat(MT.DATA.Heat_T[4], 2), gt.getItem(1039));
        add(gt.getItem(9200), 2048, 600, OP.casingMachineDense.mat(MT.Pb, 1), IL.Circuit_Master.get(4), IL.PISTONS[4].get(4));
        add(gt.getItem(17199), 32768, 2400, gt.getItem(18031), IL.FIELD_GENERATORS[5].get(8));
        add(gt.getItem(20411), 32, 300, ST.tag(1),OP.casingMachine.mat(MT.Osmiridium, 1), IL.Processor_Crystal_Ruby.get(2), IL.Processor_Crystal_Sapphire.get(2), IL.FIELD_GENERATORS[1].get(4));
        add(gt.getItem(20412), 64, 300, ST.tag(1),OP.casingMachine.mat(MT.Osmiridium, 1), IL.Processor_Crystal_Ruby.get(2), IL.Processor_Crystal_Sapphire.get(2), IL.FIELD_GENERATORS[2].get(4));
        add(gt.getItem(20413), 128, 300, ST.tag(1),OP.casingMachine.mat(MT.Osmiridium, 1), IL.Processor_Crystal_Ruby.get(2), IL.Processor_Crystal_Sapphire.get(2), IL.FIELD_GENERATORS[3].get(4));
        add(gt.getItem(20414), 256, 300, ST.tag(1),OP.casingMachine.mat(MT.Osmiridium, 1), IL.Processor_Crystal_Ruby.get(2), IL.Processor_Crystal_Sapphire.get(2), IL.FIELD_GENERATORS[4].get(4));
        add(gt.getItem(20415), 512, 300, ST.tag(1),OP.casingMachine.mat(MT.Osmiridium, 1), IL.Processor_Crystal_Ruby.get(2), IL.Processor_Crystal_Sapphire.get(2), IL.FIELD_GENERATORS[5].get(4));
        add(gt.getItem(20423), 512, 400, OP.casingMachine.mat(MT.Osmiridium, 1), IL.Processor_Crystal_Diamond.get(1), IL.Processor_Crystal_Emerald.get(1), IL.Processor_Crystal_Ruby.get(1), IL.Processor_Crystal_Sapphire.get(1), IL.FIELD_GENERATORS[3].get(2), IL.EMITTERS[3].get(1), IL.SENSORS[3].get(1));
        add(gt.getItem(20431), 32, 300, OP.casingMachine.mat(MT.Osmiridium, 1), IL.Processor_Crystal_Emerald.get(2), IL.Processor_Crystal_Sapphire.get(2), IL.EMITTERS[1].get(2), IL.FIELD_GENERATORS[1].get(2));
        add(gt.getItem(20432), 64, 300, OP.casingMachine.mat(MT.Osmiridium, 1), IL.Processor_Crystal_Emerald.get(2), IL.Processor_Crystal_Sapphire.get(2), IL.EMITTERS[2].get(2), IL.FIELD_GENERATORS[2].get(2));
        add(gt.getItem(20433), 128, 300, OP.casingMachine.mat(MT.Osmiridium, 1), IL.Processor_Crystal_Emerald.get(2), IL.Processor_Crystal_Sapphire.get(2), IL.EMITTERS[3].get(2), IL.FIELD_GENERATORS[3].get(2));
        add(gt.getItem(20434), 256, 300, OP.casingMachine.mat(MT.Osmiridium, 1), IL.Processor_Crystal_Emerald.get(2), IL.Processor_Crystal_Sapphire.get(2), IL.EMITTERS[4].get(2), IL.FIELD_GENERATORS[4].get(2));
        add(gt.getItem(20435), 512, 300, OP.casingMachine.mat(MT.Osmiridium, 1), IL.Processor_Crystal_Emerald.get(2), IL.Processor_Crystal_Sapphire.get(2), IL.EMITTERS[5].get(2), IL.FIELD_GENERATORS[5].get(2));

        // kTFRU machine assemblies.
        add(ktfru.getItem(23000),   32,  300, OP.screw.mat(MT.DATA.Electric_T[1], 2), ore("craftingLensYellow", 1), OP.gearGt.mat(MT.DATA.Electric_T[1], 2), ST.make(Blocks.hardened_clay, 1, 0), IL.Circuit_Basic.get(2), OP.casingMachine.mat(MT.DATA.Electric_T[1], 1));
        add(ktfru.getItem(23001),   64,  300, OP.screw.mat(MT.DATA.Electric_T[2], 2), ore("craftingLensYellow", 1), OP.gearGt.mat(MT.DATA.Electric_T[2], 2), ST.make(Blocks.hardened_clay, 1, 0), IL.Circuit_Good.get(2), OP.casingMachine.mat(MT.DATA.Electric_T[2], 1));
        add(ktfru.getItem(23002),  128,  300, OP.screw.mat(MT.DATA.Electric_T[3], 2), ore("craftingLensYellow", 1), OP.gearGt.mat(MT.DATA.Electric_T[3], 2), ST.make(Blocks.hardened_clay, 1, 0), IL.Circuit_Advanced.get(2), OP.casingMachine.mat(MT.DATA.Electric_T[3], 1));
        add(ktfru.getItem(23003),  256,  300, OP.screw.mat(MT.DATA.Electric_T[4], 2), ore("craftingLensYellow", 1), OP.gearGt.mat(MT.DATA.Electric_T[4], 2), ST.make(Blocks.hardened_clay, 1, 0), IL.Circuit_Elite.get(2), OP.casingMachine.mat(MT.DATA.Electric_T[4], 1));
        add(ktfru.getItem(23004),  512,  300, OP.screw.mat(MT.DATA.Electric_T[5], 2), ore("craftingLensYellow", 1), OP.gearGt.mat(MT.DATA.Electric_T[5], 2), ST.make(Blocks.hardened_clay, 1, 0), IL.Circuit_Master.get(2), OP.casingMachine.mat(MT.DATA.Electric_T[5], 1));
        add(ktfru.getItem(30003),  512,  600, OP.stickLong.mat(MT.Al, 2), IL.Comp_Laser_Gas_Ar.get(1), OP.wireGt02.mat(MT.Cu, 2), IL.Circuit_Advanced.get(2), OP.casingMachine.mat(MT.Al, 1));
        add(ktfru.getItem(30077),  512,  600, OP.lens.mat(MT.Glass, 4), IL.Circuit_Advanced.get(1), OP.wireFine.mat(MT.W, 16), OP.casingMachine.mat(MT.Al, 1), OP.plate.mat(MT.Si, 6));
        add(ktfru.getItem(30009), 2048,  800, OP.stickLong.mat(MT.StainlessSteel, 2), IL.Comp_Laser_Gas_Ar.get(1), OP.wireGt04.mat(MT.Cu, 2), ore("ktfruBasicComputer", 2), OP.casingMachine.mat(MT.StainlessSteel, 1));
        add(ktfru.getItem(30010), 8192, 1200, OP.stickLong.mat(MT.Cr, 2), IL.Comp_Laser_Gas_Ar.get(2), OP.wireGt04.mat(MT.Au, 4), IL.Circuit_Master.get(2), OP.casingMachine.mat(MT.Cr, 1));
        add(ktfru.getItem(30011),32768, 1800, OP.stickLong.mat(MT.TungstenSteel, 4), IL.Comp_Laser_Gas_Ar.get(4), OP.wireGt08.mat(MT.Au, 4), IL.Circuit_Ultimate.get(2), OP.casingMachine.mat(MT.W, 1));
        add(ktfru.getItem(30078), 2048,  800, OP.lens.mat(MT.Glass, 8), IL.Circuit_Elite.get(1), OP.wireFine.mat(MT.W, 32), OP.casingMachine.mat(MT.StainlessSteel, 2), OP.plate.mat(MT.Si, 12));
        add(ktfru.getItem(30012),  512,  600, OP.dust.mat(ANY.Glowstone, 2), OP.plate.mat(MT.Glass, 1), OP.wireFine.mat(MT.Ag, 2), OP.casingMachine.mat(MT.Al, 1), ore("ktfruBasicComputer", 1));
        add(ktfru.getItem(30020), 4096,  800, OP.blockPlate.mat(MT.Magnalium, 4), gt.getItem(18022), ItemList.VibrateDetector.get(1), IL.Circuit_Elite.get(2));
        add(ktfru.getItem(30021), 8192,  800, OP.blockPlate.mat(MT.Trinitanium, 4), gt.getItem(18026), ItemList.VibrateDetector.get(1), IL.Circuit_Master.get(2));
        add(ktfru.getItem(30022),16384,  800, OP.blockPlate.mat(MT.Graphene, 4), gt.getItem(18023), ItemList.VibrateDetector.get(1), IL.Circuit_Master.get(2));
        add(ktfru.getItem(30023),32768,  800, OP.blockPlate.mat(MT.Vibramantium, 4), gt.getItem(18025), ItemList.VibrateDetector.get(1), IL.Circuit_Master.get(2));
        add(ktfru.getItem(30058), 4096,  800, OP.plate.mat(MT.StainlessSteel, 4), IL.Sensor_EV.get(2), IL.Circuit_Elite.get(1), ktfru.getItem(32006), OP.wireFine.mat(MT.Graphene, 2));
        add(ktfru.getItem(30059),16384, 1200, OP.plate.mat(MT.StainlessSteel, 4), IL.Sensor_IV.get(2), IL.Emitter_IV.get(1), IL.Circuit_Master.get(2), ktfru.getItem(32005), OP.wireFine.mat(MT.Graphene, 2));
        add(ktfru.getItem(30060),65536, 1600, OP.plateDouble.mat(MT.StainlessSteel, 2), IL.Sensor_LuV.get(4), IL.Emitter_LuV.get(2), IL.Circuit_Ultimate.get(4), ktfru.getItem(32005), OP.wireFine.mat(MT.Nq_522, 2));
        add(ktfru.getItem(30120), 8192, 1200, OP.plateDouble.mat(MT.StainlessSteel, 4), ktfru.getItem(31200), IL.Circuit_Elite.get(3), IL.MOTORS[3].get(1));
        add(ktfru.getItem(30121),32768, 1800, OP.plateDouble.mat(MT.TungstenSteel, 4), ktfru.getItem(31200), IL.Circuit_Master.get(6), IL.MOTORS[4].get(1));
        add(ktfru.getItem(30065),32768, 2400, IL.Sensor_IV.get(2), IL.Circuit_Master.get(1), OP.plate.mat(MT.Nq_522, 2), ktfru.getItem(31066), OP.wireFine.mat(MT.Graphene, 2));
        add(ktfru.getItem(30066),32768, 2400, IL.Sensor_LuV.get(2), IL.Emitter_LuV.get(1), OP.plate.mat(MT.Nq_522, 2), ktfru.getItem(31070), OP.wireFine.mat(MT.Nq_522, 2));
        add(ktfru.getItem(30067),65536, 2400, IL.Sensor_LuV.get(4), IL.Emitter_LuV.get(2), OP.plateDouble.mat(MT.Nq_522, 2), ktfru.getItem(31074), OP.wireFine.mat(MT.Nq_522, 2));
        add(ktfru.getItem(30068),65536, 2400, IL.Sensor_LuV.get(8), IL.Emitter_LuV.get(4), OP.plateDouble.mat(MT.Nq_522, 2), ktfru.getItem(31078), OP.wireFine.mat(MT.Nq_522, 2));
        add(ktfru.getItem(30069),131072,2400, IL.Sensor_LuV.get(8), OP.plateDouble.mat(MT.Nq_522, 2), IL.Circuit_Ultimate.get(8), ktfru.getItem(31083), OP.wireFine.mat(MT.Nq_522, 2));
        add(ktfru.getItem(30070),131072,2400, IL.Sensor_LuV.get(16), IL.Emitter_LuV.get(8), IL.Circuit_Ultimate.get(16), OP.plateDouble.mat(MT.Trinaquadalloy, 2), ktfru.getItem(31087), OP.wireFine.mat(MT.Nq_522, 2));
        add(ktfru.getItem(30104),32768, 1600, OP.plateDouble.mat(MT.TungstenSteel, 4), IL.Circuit_Ultimate.get(3), OP.casingMachine.mat(MT.TungstenSteel, 1), IL.SENSORS[3].get(1));
        add(ktfru.getItem(30079),131072,2400, IL.Sensor_LuV.get(2), OP.plateDouble.mat(MT.Trinaquadalloy, 2), IL.Circuit_Ultimate.get(6), ktfru.getItem(32005), OP.wireFine.mat(MT.Nq_522, 2));
        add(ktfru.getItem(30073),32768, 1600, IL.Emitter_LuV.get(4), OP.plate.mat(matList.Ij.mat, 1), IL.Circuit_Ultimate.get(2), OP.casingMachine.mat(MT.Ir, 1));
        add(ktfru.getItem(30074),32768, 1600, IL.Emitter_LuV.get(4), prefixList.AbsolutelyPureDust.mat(matList.Ij.mat, 1), IL.Circuit_Ultimate.get(2), OP.casingMachine.mat(MT.Ir, 1));

        // Research-only items without an existing production recipe.
        add(ItemList.ArmorAirSealant.get(1), 128, 200, OP.foil.mat(MT.Rubber, 4), OP.plate.mat(MT.Teflon, 1), IL.Circuit_Good.get(1));
        add(ItemList.SpaceSuitCloth.get(1), 64, 160, ST.make(Blocks.wool, 4, 0), ST.make(net.minecraft.init.Items.string, 4, 0), OP.foil.mat(MT.Rubber, 2));
        add(ItemList.Tm170FlawDetectionCore.get(1), 512, 300, OP.plateDense.mat(MT.Pb, 2), OP.plate.mat(MT.Tm, 1), IL.SENSORS[2].get(1));

        initAssembling();
    }

    /** GT6 cannot reference this addon's recipe maps directly, so mirror its locked Canner recipes explicitly. */
    private static void registerGtResearchRecipes() {
        ItemStack empty = IL.Comp_Laser_Gas_Empty.get(1);
        recipeMaps.ResearchAssembler.addRecipe1(T, 16, 128, empty, MT.He.gas(U, T), NF, IL.Comp_Laser_Gas_He.get(1));
        recipeMaps.ResearchAssembler.addRecipe1(T, 16, 128, empty, MT.Ne.gas(U, T), NF, IL.Comp_Laser_Gas_Ne.get(1));
        recipeMaps.ResearchAssembler.addRecipe1(T, 16, 128, empty, MT.Ar.gas(U, T), NF, IL.Comp_Laser_Gas_Ar.get(1));
        recipeMaps.ResearchAssembler.addRecipe1(T, 16, 128, empty, MT.Kr.gas(U, T), NF, IL.Comp_Laser_Gas_Kr.get(1));
        recipeMaps.ResearchAssembler.addRecipe1(T, 16, 128, empty, MT.Xe.gas(U, T), NF, IL.Comp_Laser_Gas_Xe.get(1));
        recipeMaps.ResearchAssembler.addRecipe1(T, 16, 128, empty, MT.HeNe.gas(U, T), NF, IL.Comp_Laser_Gas_HeNe.get(1));
        recipeMaps.ResearchAssembler.addRecipe1(T, 16, 128, empty, MT.CO.gas(U, T), NF, IL.Comp_Laser_Gas_CO.get(1));
        recipeMaps.Assembler.addRecipe1(T, 16, 128, empty, MT.CO2.gas(U, T), NF, IL.Comp_Laser_Gas_CO2.get(1));
    }

    private static void add(ItemStack aOutput, long aEUt, long aDuration, ItemStack... aInputs) {
        recipeMaps.ResearchAssembler.addRecipeX(F, aEUt, aDuration, ST.array(aInputs), ZL_FS, ZL_FS, aOutput);
    }

    private static ItemStack ore(String aOre, int aAmount) {
        ItemStack stack = OreDictManager.INSTANCE.getFirstOre(aOre, aAmount);
        if (stack == null) throw new IllegalStateException("Missing ore dictionary entry: " + aOre);
        return stack;
    }

    public static void initAssembling() {
        registerComputer(ItemList.CPUTF3386   .get(1), 1, 1,  16,  80, ItemList.ComputerTF3386   .get(1),mem(64L       , 1, 2),coil( 4, 1, 1), res(20, 1, 2), cap(32, 1, 2), diode(0, 0, 0));
        registerComputer(ItemList.CPUTF3386S  .get(1), 1, 1,  24,  80, ItemList.ComputerTF3386S  .get(1),mem(64L       , 1, 2),coil( 4, 1, 1), res(16, 1, 2), cap(28, 1, 2), diode(0, 0, 0));
        registerComputer(ItemList.CPUTF3586   .get(1), 1, 1,  70,  80, ItemList.ComputerTF3586   .get(1),mem(512L      , 2, 3),coil( 6, 1, 2), res(32, 1, 2), cap(48, 1, 2), diode(0, 0, 0));
        registerComputer(ItemList.CPUTF3586S  .get(1), 1, 1,  92,  80, ItemList.ComputerTF3586S  .get(1),mem(512L      , 2, 3),coil( 6, 1, 2), res(28, 1, 2), cap(40, 1, 2), diode(0, 0, 0));
        registerComputer(ItemList.CPUGT1000   .get(1), 1, 2, 120,  60, ItemList.ComputerGT1000   .get(1),mem(8192L     , 3, 4),coil( 8, 1, 2), res(52, 1, 2), cap(28, 1, 2), diode(0, 0, 0));
        registerComputer(ItemList.CPUGT1090   .get(1), 1, 2, 120,  60, ItemList.ComputerGT1090   .get(1),mem(12288L    , 3, 4),coil( 8, 1, 2), res(52, 1, 2), cap(28, 1, 2), diode(0, 0, 0));
        registerComputer(ItemList.CPUGT2000   .get(1), 1, 2, 200,  60, ItemList.ComputerGT2000   .get(1),mem(65536L    , 4, 5),coil( 8, 2, 3), res(36, 2, 3), cap(28, 1, 2), diode(0, 0, 0));
        registerComputer(ItemList.CPUGT2090   .get(1), 1, 2, 200,  60, ItemList.ComputerGT2090   .get(1),mem(98304L    , 4, 5),coil( 8, 2, 3), res(36, 2, 3), cap(28, 1, 2), diode(0, 0, 0));
        registerComputer(ItemList.CPUGT3660   .get(1), 2, 2, 200, 120, ItemList.ComputerGT3660   .get(1),mem(393216L   , 4, 5),coil(12, 2, 3), res(48, 2, 3), cap(36, 1, 3), diode(0, 0, 0));
        registerComputer(ItemList.CPUGT3680   .get(1), 2, 2, 200, 120, ItemList.ComputerGT3680   .get(1),mem(393216L   , 4, 5),coil(12, 2, 3), res(48, 2, 3), cap(36, 1, 3), diode(0, 0, 0));
        registerComputer(ItemList.CPUGT3699   .get(1), 1, 2, 200,  80, ItemList.ComputerGT3699   .get(1),mem(393216L   , 4, 5),coil(12, 2, 3), res(48, 2, 3), cap(32, 1, 3), diode(0, 0, 0));
        registerComputer(ItemList.CPUGT3660v2 .get(1), 2, 3, 120, 480, ItemList.ComputerGT3660v2 .get(1),mem(4194304L  , 5, 6),coil(24, 2, 4), res(28, 3, 4), cap(32, 2, 3), diode(0, 0, 0));
        registerComputer(ItemList.CPUGT3680v2 .get(1), 2, 3, 120, 480, ItemList.ComputerGT3680v2 .get(1),mem(4194304L  , 5, 6),coil(24, 2, 4), res(28, 3, 4), cap(32, 2, 3), diode(0, 0, 0));
        registerComputer(ItemList.CPUGT3699v2 .get(1), 1, 3, 120, 480, ItemList.ComputerGT3699v2 .get(1),mem(4194304L  , 5, 6),coil(16, 2, 4), res(16, 3, 4), cap(24, 2, 3), diode(0, 0, 0));
        registerComputer(ItemList.CPUGT3660v3 .get(1), 2, 3, 120, 480, ItemList.ComputerGT3660v3 .get(1),mem(25165824L , 6, 7),coil(16, 3, 4), res(32, 3, 4), cap(24, 2, 3), diode(0, 0, 0));
        registerComputer(ItemList.CPUGT3680v3 .get(1), 2, 3, 120, 480, ItemList.ComputerGT3680v3 .get(1),mem(25165824L , 6, 7),coil(16, 3, 4), res(32, 3, 4), cap(24, 2, 3), diode(0, 0, 0));
        registerComputer(ItemList.CPUGT3699v3 .get(1), 1, 3, 120, 480, ItemList.ComputerGT3699v3 .get(1),mem(25165824L , 6, 7),coil(12, 3, 4), res(28, 3, 4), cap(16, 2, 3), diode(0, 0, 0));
        registerComputer(ItemList.CPUGT3660v4 .get(1), 2, 3, 120, 480, ItemList.ComputerGT3660v4 .get(1),mem(25165824L , 6, 7),coil(16, 3, 4), res(32, 3, 4), cap(32, 2, 3), diode(0, 0, 0));
        registerComputer(ItemList.CPUGT3680v4 .get(1), 2, 3, 120, 480, ItemList.ComputerGT3680v4 .get(1),mem(25165824L , 6, 7),coil(16, 3, 4), res(32, 3, 4), cap(32, 2, 3), diode(0, 0, 0));
        registerComputer(ItemList.CPUGT3699v4 .get(1), 1, 3, 120, 480, ItemList.ComputerGT3699v4 .get(1),mem(25165824L , 6, 7),coil(12, 3, 4), res(24, 3, 4), cap(24, 2, 3), diode(0, 0, 0));
        registerComputer(ItemList.CPUGT3680v3E.get(1), 2, 3, 120, 480, ItemList.ComputerGT3680v3e.get(1),mem(67108864L , 8, 8),coil(12, 4, 4), res(32, 4, 4), cap(32, 3, 3), diode(0, 0, 0));
        registerComputer(ItemList.CPUGT3699v3E.get(1), 2, 3, 120, 480, ItemList.ComputerGT3699v3e.get(1),mem(67108864L , 8, 8),coil(16, 4, 4), res(32, 4, 4), cap(32, 3, 3), diode(0, 0, 0));
        registerComputer(ItemList.CPUGT3680v4E.get(1), 2, 3, 120, 480, ItemList.ComputerGT3680v4e.get(1),mem(268435456L, 8, 8),coil(20, 4, 4), res(32, 4, 4), cap(32, 3, 3), diode(0, 0, 0));
        registerComputer(ItemList.CPUGT3699v4E.get(1), 2, 3, 120, 480, ItemList.ComputerGT3699v4e.get(1),mem(268435456L, 8, 8),coil(24, 4, 4), res(32, 4, 4), cap(32, 3, 3), diode(0, 0, 0));

    }

    private static final int COMPONENT_TIER_RATIO = 2;

    protected static MemorySpec mem(long aCapacityKb, int aMinGeneration, int aMaxGeneration) {
        return new MemorySpec(aCapacityKb, aMinGeneration, aMaxGeneration);
    }

    protected static ComponentSpec cap(int aCount, int aMinTier, int aMaxTier) {
        return component(ComponentType.CAPACITOR, aCount, aMinTier, aMaxTier);
    }

    protected static ComponentSpec res(int aCount, int aMinTier, int aMaxTier) {
        return component(ComponentType.RESISTOR, aCount, aMinTier, aMaxTier);
    }

    protected static ComponentSpec diode(int aCount, int aMinTier, int aMaxTier) {
        return component(ComponentType.DIODE, aCount, aMinTier, aMaxTier);
    }

    protected static ComponentSpec coil(int aCount, int aMinTier, int aMaxTier) {
        return component(ComponentType.COIL, aCount, aMinTier, aMaxTier);
    }

    private static ComponentSpec component(ComponentType aType, int aCount, int aMinTier, int aMaxTier) {
        if (aCount < 0 || (aCount > 0 && (aMinTier < 1 || aMaxTier < aMinTier || aMaxTier > aType.mMaxTier)))
            throw new IllegalArgumentException("Invalid component range for " + aType);
        return new ComponentSpec(aType, aCount, aMinTier, aMaxTier);
    }

    protected static void registerComputer(ItemStack aCpu, int aCpuCount, int aBoardTier, long aEut, long aDuration,
                                         ItemStack aOutput, ComputerPartSpec... aParts) {
        MemorySpec memory = null;
        List<ComponentSpec> components = new ArrayList<>();
        for (ComputerPartSpec part : aParts) {
            if (part instanceof MemorySpec) {
                if (memory != null) throw new IllegalArgumentException("Computer recipe has multiple memory specs");
                memory = (MemorySpec) part;
            } else {
                components.add((ComponentSpec) part);
            }
        }
        if (memory == null) throw new IllegalArgumentException("Computer recipe has no memory spec");

        ItemStack board = board(aBoardTier);
        ComponentSpec[] componentSpecs = components.toArray(new ComponentSpec[0]);
        List<int[]> combinations = new ArrayList<>();
        collectTierCombinations(componentSpecs, 0, new int[componentSpecs.length], combinations);
        for (ItemStack memoryStack : memoryStacks(memory)) {
            for (int[] tiers : combinations) {
                List<ItemStack> inputs = new ArrayList<>();
                inputs.add(board.copy());
                inputs.add(amount(aCpu, aCpuCount));
                inputs.add(memoryStack.copy());
                for (int i = 0; i < componentSpecs.length; i++) addComponent(inputs, componentSpecs[i], tiers[i]);
                recipeMaps.ResearchAssembler.addRecipeX(F, aEut, aDuration,
                        ST.array(inputs.toArray(new ItemStack[0])),
                        FL.array(MT.SolderingAlloy.liquid(U, F)), ZL_FS, aOutput.copy());
            }
        }
    }

    private static void collectTierCombinations(ComponentSpec[] aSpecs, int aIndex, int[] aCurrent, List<int[]> aOutput) {
        if (aIndex == aSpecs.length) {
            aOutput.add(aCurrent.clone());
            return;
        }
        for (int tier : aSpecs[aIndex].tiers()) {
            aCurrent[aIndex] = tier;
            collectTierCombinations(aSpecs, aIndex + 1, aCurrent, aOutput);
        }
    }

    private static void addComponent(List<ItemStack> aInputs, ComponentSpec aSpec, int aTier) {
        int count = aSpec.countForTier(aTier);
        if (count > 0) aInputs.add(aSpec.type.stack(aTier, count));
    }

    private static ItemStack board(int aTier) {
        switch (aTier) {
            case 1: return IL.Circuit_Plate_Copper.get(1);
            case 2: return IL.Circuit_Plate_Gold.get(1);
            case 3: return IL.Circuit_Plate_Platinum.get(1);
            default: throw new IllegalArgumentException("Unknown computer board tier: " + aTier);
        }
    }

    private static List<ItemStack> memoryStacks(MemorySpec aMemory) {
        List<ItemStack> stacks = new ArrayList<>();
        for (Map.Entry<ItemList, Long> entry : itemIT.kTFRURAMMeta.entrySet()) {
            long capacity = entry.getValue();
            if (capacity <= 0) continue;
            ItemStack stack = entry.getKey().get(1);
            if (!hasMemoryGeneration(stack, aMemory.mMinGeneration, aMemory.mMaxGeneration)) continue;
            long count = (aMemory.mCapacityKb + capacity - 1) / capacity;
            if (count > Integer.MAX_VALUE) throw new IllegalArgumentException("Memory count overflow: " + count);
            stacks.add(amount(stack, (int) count));
        }
        if (stacks.isEmpty()) throw new IllegalArgumentException(
                "No RAM bars for " + aMemory.mCapacityKb + "K generation " + aMemory.mMinGeneration + "-" + aMemory.mMaxGeneration);
        return stacks;
    }

    private static boolean hasMemoryGeneration(ItemStack aStack, int aMinGeneration, int aMaxGeneration) {
        for (int generation = aMinGeneration; generation <= aMaxGeneration; generation++) {
            List<ItemStack> ores = OreDictManager.getOres("ktfruRAMT" + generation, false);
            for (ItemStack ore : ores) if (OreDictionary.itemMatches(aStack, ore, false)) return true;
        }
        return false;
    }

    private static ItemStack amount(ItemStack aStack, int aAmount) {
        ItemStack stack = aStack.copy();
        stack.stackSize = aAmount;
        return stack;
    }

    protected enum ComponentType {
        COIL(4),
        RESISTOR(4),
        CAPACITOR(3),
        DIODE(3);

        private final int mMaxTier;

        ComponentType(int aMaxTier) {
            mMaxTier = aMaxTier;
        }

        private ItemStack stack(int aTier, int aCount) {
            switch (this) {
                case COIL:
                    switch (aTier) {
                        case 1: return ItemList.CoilT1.get(aCount);
                        case 2: return ItemList.CoilT2.get(aCount);
                        case 3: return ItemList.CoilT3.get(aCount);
                        case 4: return ItemList.CoilT4.get(aCount);
                    }
                    break;
                case RESISTOR:
                    switch (aTier) {
                        case 1: return ItemList.ResistanceT1.get(aCount);
                        case 2: return ItemList.ResistanceT2.get(aCount);
                        case 3: return ItemList.ResistanceT3.get(aCount);
                        case 4: return ItemList.ResistanceT4.get(aCount);
                    }
                    break;
                case CAPACITOR:
                    switch (aTier) {
                        case 1: return ItemList.CapacitorT1.get(aCount);
                        case 2: return ItemList.CapacitorT2.get(aCount);
                        case 3: return ItemList.CapacitorT3.get(aCount);
                    }
                    break;
                case DIODE:
                    switch (aTier) {
                        case 1: return ItemList.DiodeT1.get(aCount);
                        case 2: return ItemList.DiodeT2.get(aCount);
                        case 3: return ItemList.DiodeT3.get(aCount);
                    }
                    break;
            }
            throw new IllegalArgumentException("Unknown " + this + " tier: " + aTier);
        }
    }

    protected interface ComputerPartSpec { }

    protected static final class MemorySpec implements ComputerPartSpec {
        private final long mCapacityKb;
        private final int mMinGeneration;
        private final int mMaxGeneration;

        protected MemorySpec(long aCapacityKb, int aMinGeneration, int aMaxGeneration) {
            if (aCapacityKb < 1) throw new IllegalArgumentException("Invalid memory capacity: " + aCapacityKb);
            if (aMinGeneration < 1 || aMaxGeneration > 8 || aMinGeneration > aMaxGeneration)
                throw new IllegalArgumentException("Invalid memory generation range: " + aMinGeneration + "-" + aMaxGeneration);
            mCapacityKb = aCapacityKb;
            mMinGeneration = aMinGeneration;
            mMaxGeneration = aMaxGeneration;
        }
    }

    protected static final class ComponentSpec implements ComputerPartSpec {
        private final ComponentType type;
        private final int mCount;
        private final int mMinTier;
        private final int mMaxTier;

        protected ComponentSpec(ComponentType aType, int aCount, int aMinTier, int aMaxTier) {
            type = aType;
            mCount = aCount;
            mMinTier = aMinTier;
            mMaxTier = aMaxTier;
        }

        private int[] tiers() {
            if (mCount <= 0) return new int[]{0};
            int[] tiers = new int[mMaxTier - mMinTier + 1];
            for (int i = 0; i < tiers.length; i++) tiers[i] = mMinTier + i;
            return tiers;
        }

        private int countForTier(int aTier) {
            if (mCount <= 0 || aTier <= 0) return 0;
            int divisor = 1;
            for (int tier = mMinTier; tier < aTier; tier++) divisor *= COMPONENT_TIER_RATIO;
            return Math.max(1, (mCount + divisor - 1) / divisor);
        }
    }

}
