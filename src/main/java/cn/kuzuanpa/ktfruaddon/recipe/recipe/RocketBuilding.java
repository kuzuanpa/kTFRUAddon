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
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import gregapi.data.*;
import gregapi.util.ST;

import static gregapi.data.CS.*;

public class RocketBuilding {
    public static void init(){
        recipeMaps.Assembler.addRecipeX(F,320,480, ST.array(OP.casingMachine.mat(MT.Ti,1), ItemList.ComputerGT1090.get(2), ST.make(MD.GC_ADV_ROCKETRY, "tile.loader", 1, 6), ST.make(MD.GC_ADV_ROCKETRY, "tile.loader", 1, 2)), FL.array(MT.SolderingAlloy.liquid(U,false)),ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(30035));
        recipeMaps.Assembler.addRecipeX(F,320,480, ST.array(OP.casingMachine.mat(MT.Ti,1), ItemList.ComputerGT1000.get(3), ST.make(MD.GC_ADV_ROCKETRY, "tile.loader", 1, 6), ST.make(MD.GC_ADV_ROCKETRY, "tile.loader", 1, 2)), FL.array(MT.SolderingAlloy.liquid(U,false)),ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(30035));
        recipeMaps.Assembler.addRecipeX(F,320,480, ST.array(OP.casingMachine.mat(MT.Ti,1), ItemList.UnderClockedModerateComputer.get(2), ST.make(MD.GC_ADV_ROCKETRY, "tile.loader", 1, 6), ST.make(MD.GC_ADV_ROCKETRY, "tile.loader", 1, 2)), FL.array(MT.SolderingAlloy.liquid(U,false)),ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(30035));

        recipeMaps.Assembler.addRecipeX(F,320,520, ST.array(OP.casingMachine.mat(MT.Ti,1), ItemList.ComputerGT1000.get(4), IL.SENSORS[4].get(4)), FL.array(MT.SolderingAlloy.liquid(U,false)),ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(30034));
        recipeMaps.Assembler.addRecipeX(F,320,520, ST.array(OP.casingMachine.mat(MT.Ti,1), ItemList.ComputerGT1090.get(3), IL.SENSORS[4].get(4)), FL.array(MT.SolderingAlloy.liquid(U,false)),ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(30034));
        recipeMaps.Assembler.addRecipeX(F,320,520, ST.array(OP.casingMachine.mat(MT.Ti,1), ItemList.UnderClockedModerateComputer.get(3), IL.SENSORS[4].get(4)), FL.array(MT.SolderingAlloy.liquid(U,false)),ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(30034));

        recipeMaps.Assembler.addRecipeX(F,320,560, ST.array(OP.casingMachine.mat(MT.Ti, 1), ItemList.ComputerGT1000.get(2), OP.wireFine.mat(MT.Ag, 4), IL.ROBOT_ARMS[4].get(4)), FL.array(MT.SolderingAlloy.liquid(U,false)),ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(30036));
        recipeMaps.Assembler.addRecipeX(F,320,560, ST.array(OP.casingMachine.mat(MT.Ti, 1), ItemList.ComputerGT1090.get(2), OP.wireFine.mat(MT.Ag, 4), IL.ROBOT_ARMS[4].get(4)), FL.array(MT.SolderingAlloy.liquid(U,false)),ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(30036));
        recipeMaps.Assembler.addRecipeX(F,320,560, ST.array(OP.casingMachine.mat(MT.Ti, 1), ItemList.UnderClockedModerateComputer.get(2), OP.wireFine.mat(MT.Ag, 4), IL.ROBOT_ARMS[4].get(4)), FL.array(MT.SolderingAlloy.liquid(U,false)),ZL_FS, GTTileEntityRegistry.ktfruaddon.getItem(30036));

        recipeMaps.RocketAssembler.addRecipeX(F,320,4800, ST.array(OP.casingMachine.mat(MT.StainlessSteel,1), ST.make(MD.GC_ADV_ROCKETRY, "fuelTank", 8),  ST.make(MD.GC_ADV_ROCKETRY, "rocketmotor", 5), ST.make(MD.GC_ADV_ROCKETRY, "tile.guidanceComputer", 1), GameRegistry.makeItemStack("IronChest:BlockIronChest", 2, 2, null), IL.MOTORS[3].get(2), OP.toolHeadDrill.mat(MT.RedSteel,     2)), FL.array(FL.make("rocketfuel", 16000)),ZL_FS, ItemList.AsteroidMinerRocketT1.get(1));
        recipeMaps.RocketAssembler.addRecipeX(F,320,4800, ST.array(OP.casingMachine.mat(MT.Ti,            1), ST.make(MD.GC_ADV_ROCKETRY, "fuelTank", 8),  ST.make(MD.GC_ADV_ROCKETRY, "rocketmotor", 5), ST.make(MD.GC_ADV_ROCKETRY, "tile.guidanceComputer", 1), GameRegistry.makeItemStack("IronChest:BlockIronChest", 2, 2, null), IL.MOTORS[4].get(2), OP.toolHeadDrill.mat(MT.Ti,           2)), FL.array(FL.make("rocketfuel", 16000)),ZL_FS, ItemList.AsteroidMinerRocketT2.get(1));
        recipeMaps.RocketAssembler.addRecipeX(F,320,4800, ST.array(OP.casingMachine.mat(MT.TitaniumGold,  1), ST.make(MD.GC_ADV_ROCKETRY, "fuelTank", 8),  ST.make(MD.GC_ADV_ROCKETRY, "rocketmotor", 5), ST.make(MD.GC_ADV_ROCKETRY, "tile.guidanceComputer", 1), GameRegistry.makeItemStack("IronChest:BlockIronChest", 2, 2, null), IL.MOTORS[5].get(2), OP.toolHeadDrill.mat(MT.TitaniumGold, 2)), FL.array(FL.make("rocketfuel", 16000)),ZL_FS, ItemList.AsteroidMinerRocketT3.get(1));
        recipeMaps.RocketAssembler.addRecipeX(F,320,4800, ST.array(OP.casingMachine.mat(MT.Os,            1), ST.make(MD.GC_ADV_ROCKETRY, "fuelTank", 8),  ST.make(MD.GC_ADV_ROCKETRY, "rocketmotor", 5), ST.make(MD.GC_ADV_ROCKETRY, "tile.guidanceComputer", 1), GameRegistry.makeItemStack("IronChest:BlockIronChest", 2, 2, null), IL.MOTORS[6].get(2), OP.toolHeadDrill.mat(MT.Os,           2)), FL.array(FL.make("rocketfuel", 16000)),ZL_FS, ItemList.AsteroidMinerRocketT4.get(1));
        recipeMaps.RocketAssembler.addRecipeX(F,320,4800, ST.array(OP.casingMachine.mat(MT.Trinitanium,   1), ST.make(MD.GC_ADV_ROCKETRY, "fuelTank", 8),  ST.make(MD.GC_ADV_ROCKETRY, "rocketmotor", 5), ST.make(MD.GC_ADV_ROCKETRY, "tile.guidanceComputer", 1), GameRegistry.makeItemStack("IronChest:BlockIronChest", 2, 2, null), IL.MOTORS[7].get(2), OP.toolHeadDrill.mat(MT.Trinitanium,  2)), FL.array(FL.make("rocketfuel", 16000)),ZL_FS, ItemList.AsteroidMinerRocketT5.get(1));

        recipeMaps.RocketAssembler.addRecipeX(F,320,8800, ST.array(OP.casingMachineDouble.mat(MT.StainlessSteel,1), GTTileEntityRegistry.gregtech.getItem(32742,4), ST.make(MD.GC_ADV_ROCKETRY, "tile.advRocket", 5), ST.make(MD.GC_ADV_ROCKETRY, "tile.guidanceComputer", 1), GameRegistry.makeItemStack("IronChest:BlockIronChest", 2, 2, null), IL.MOTORS[3].get(5), OP.toolHeadDrill.mat(MT.RedSteel,     5)), FL.array(FL.make("rocketfuel", 512000)),ZL_FS, ItemList.AsteroidMinerRocketT1Fast.get(1));
        recipeMaps.RocketAssembler.addRecipeX(F,320,8800, ST.array(OP.casingMachineDouble.mat(MT.Ti,            1), GTTileEntityRegistry.gregtech.getItem(32742,4), ST.make(MD.GC_ADV_ROCKETRY, "tile.advRocket", 5), ST.make(MD.GC_ADV_ROCKETRY, "tile.guidanceComputer", 1), GameRegistry.makeItemStack("IronChest:BlockIronChest", 2, 2, null), IL.MOTORS[4].get(5), OP.toolHeadDrill.mat(MT.Ti,           5)), FL.array(FL.make("rocketfuel", 512000)),ZL_FS, ItemList.AsteroidMinerRocketT2Fast.get(1));
        recipeMaps.RocketAssembler.addRecipeX(F,320,8800, ST.array(OP.casingMachineDouble.mat(MT.TitaniumGold,  1), GTTileEntityRegistry.gregtech.getItem(32742,4), ST.make(MD.GC_ADV_ROCKETRY, "tile.advRocket", 5), ST.make(MD.GC_ADV_ROCKETRY, "tile.guidanceComputer", 1), GameRegistry.makeItemStack("IronChest:BlockIronChest", 2, 2, null), IL.MOTORS[5].get(5), OP.toolHeadDrill.mat(MT.TitaniumGold, 5)), FL.array(FL.make("rocketfuel", 512000)),ZL_FS, ItemList.AsteroidMinerRocketT3Fast.get(1));
        recipeMaps.RocketAssembler.addRecipeX(F,320,8800, ST.array(OP.casingMachineDouble.mat(MT.Os,            1), GTTileEntityRegistry.gregtech.getItem(32742,4), ST.make(MD.GC_ADV_ROCKETRY, "tile.advRocket", 5), ST.make(MD.GC_ADV_ROCKETRY, "tile.guidanceComputer", 1), GameRegistry.makeItemStack("IronChest:BlockIronChest", 2, 2, null), IL.MOTORS[6].get(5), OP.toolHeadDrill.mat(MT.Os,           5)), FL.array(FL.make("rocketfuel", 512000)),ZL_FS, ItemList.AsteroidMinerRocketT4Fast.get(1));
        recipeMaps.RocketAssembler.addRecipeX(F,320,8800, ST.array(OP.casingMachineDouble.mat(MT.Trinitanium,   1), GTTileEntityRegistry.gregtech.getItem(32742,4), ST.make(MD.GC_ADV_ROCKETRY, "tile.advRocket", 5), ST.make(MD.GC_ADV_ROCKETRY, "tile.guidanceComputer", 1), GameRegistry.makeItemStack("IronChest:BlockIronChest", 2, 2, null), IL.MOTORS[7].get(5), OP.toolHeadDrill.mat(MT.Trinitanium,  5)), FL.array(FL.make("rocketfuel", 512000)),ZL_FS, ItemList.AsteroidMinerRocketT5Fast.get(1));


        recipeMaps.RocketAssembler.addRecipeX(F,320,4800, ST.array(ItemList.DeprecatedAsteroidMinerRocketT1.get(1), IL.MOTORS[3].get(1), OP.toolHeadDrill.mat(MT.RedSteel,     2)), FL.array(FL.make("rocketfuel", 16000)),ZL_FS, ItemList.AsteroidMinerRocketT1.get(1));
        recipeMaps.RocketAssembler.addRecipeX(F,320,4800, ST.array(ItemList.DeprecatedAsteroidMinerRocketT2.get(1), IL.MOTORS[4].get(1), OP.toolHeadDrill.mat(MT.Ti,           2)), FL.array(FL.make("rocketfuel", 16000)),ZL_FS, ItemList.AsteroidMinerRocketT2.get(1));
        recipeMaps.RocketAssembler.addRecipeX(F,320,4800, ST.array(ItemList.DeprecatedAsteroidMinerRocketT3.get(1), IL.MOTORS[5].get(1), OP.toolHeadDrill.mat(MT.TitaniumGold, 2)), FL.array(FL.make("rocketfuel", 16000)),ZL_FS, ItemList.AsteroidMinerRocketT3.get(1));
        recipeMaps.RocketAssembler.addRecipeX(F,320,4800, ST.array(ItemList.DeprecatedAsteroidMinerRocketT4.get(1), IL.MOTORS[6].get(1), OP.toolHeadDrill.mat(MT.Os,           2)), FL.array(FL.make("rocketfuel", 16000)),ZL_FS, ItemList.AsteroidMinerRocketT4.get(1));
        recipeMaps.RocketAssembler.addRecipeX(F,320,4800, ST.array(ItemList.DeprecatedAsteroidMinerRocketT5.get(1), IL.MOTORS[7].get(1), OP.toolHeadDrill.mat(MT.Trinitanium,  2)), FL.array(FL.make("rocketfuel", 16000)),ZL_FS, ItemList.AsteroidMinerRocketT5.get(1));

        recipeMaps.RocketAssembler.addRecipeX(F,320,8800, ST.array(ItemList.DeprecatedAsteroidMinerRocketT1Fast.get(1),IL.MOTORS[3].get(2), OP.toolHeadDrill.mat(MT.RedSteel,     5)), FL.array(FL.make("rocketfuel", 512000)),ZL_FS, ItemList.AsteroidMinerRocketT1Fast.get(1));
        recipeMaps.RocketAssembler.addRecipeX(F,320,8800, ST.array(ItemList.DeprecatedAsteroidMinerRocketT2Fast.get(1),IL.MOTORS[4].get(2), OP.toolHeadDrill.mat(MT.Ti,           5)), FL.array(FL.make("rocketfuel", 512000)),ZL_FS, ItemList.AsteroidMinerRocketT2Fast.get(1));
        recipeMaps.RocketAssembler.addRecipeX(F,320,8800, ST.array(ItemList.DeprecatedAsteroidMinerRocketT3Fast.get(1),IL.MOTORS[5].get(2), OP.toolHeadDrill.mat(MT.TitaniumGold, 5)), FL.array(FL.make("rocketfuel", 512000)),ZL_FS, ItemList.AsteroidMinerRocketT3Fast.get(1));
        recipeMaps.RocketAssembler.addRecipeX(F,320,8800, ST.array(ItemList.DeprecatedAsteroidMinerRocketT4Fast.get(1),IL.MOTORS[6].get(2), OP.toolHeadDrill.mat(MT.Os,           5)), FL.array(FL.make("rocketfuel", 512000)),ZL_FS, ItemList.AsteroidMinerRocketT4Fast.get(1));
        recipeMaps.RocketAssembler.addRecipeX(F,320,8800, ST.array(ItemList.DeprecatedAsteroidMinerRocketT5Fast.get(1),IL.MOTORS[7].get(2), OP.toolHeadDrill.mat(MT.Trinitanium,  5)), FL.array(FL.make("rocketfuel", 512000)),ZL_FS, ItemList.AsteroidMinerRocketT5Fast.get(1));

    }
}
