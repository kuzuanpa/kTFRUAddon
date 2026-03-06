/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */


package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import cn.kuzuanpa.ktfruaddon.api.material.matList;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.*;
import gregapi.oredict.OreDictMaterial;
import gregapi.util.CR;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregapi.data.CS.*;

public class CompactItem {
    public static void init() {
        MultiTileEntityRegistry gRegistry = MultiTileEntityRegistry.getRegistry("gt.multitileentity");
        MultiTileEntityRegistry kRegistry = MultiTileEntityRegistry.getRegistry("ktfru.multitileentity");

        CR.shaped(ItemList.CrucibleModelInnerLayer.get(1), CR.DEF, " I ", " I ", " f ", 'I', OP.ingot.mat(MT.RedSteel, 1));
        CR.shaped(ItemList.CrucibleModelInnerLayer.get(1), CR.DEF, " I ", " I ", " f ", 'I', OP.ingot.mat(MT.BlueSteel, 1));

        recipeMaps.Assembler.addRecipe2(false, 372, 80, OP.plateTiny.mat(matList.Ij.mat, 1), OP.wireFine.mat(MT.Ti, 4), FL.array(MT.SolderingAlloy.liquid(U2, F)), ZL_FS, ItemList.IntelligentCore.get(2));
        recipeMaps.Assembler.addRecipe1(false, 1574, 80, ItemList.CPUGT3660.get(1), FL.array(MT.SolderingAlloy.liquid(U2, F)), ZL_FS, ItemList.IntelligentCore.get(2));

        recipeMaps.Assembler.addRecipeX(false, 16384, 16384, ST.array(OP.nugget.mat(MT.DraconiumAwakened, 2), OP.plateTiny.mat(MT.Ad, 2), OP.wireFine.mat(MT.Terrasteel, 64), OP.plate.mat(MT.VibraniumSteel, 1), OP.plateGem.mat(MT.NetherStar, 1)), ZL_FS, ZL_FS, ST.make(Items.skull, 1, 3, UT.NBT.make("SkullOwner", "kuzuanpa")));

        RM.Extruder.addRecipe2(false, 32, 10, new ItemStack(Items.clay_ball, 1), IL.Shape_SimpleEx_Ingot.get(0), new ItemStack(Items.brick, 1));

        //Gold TFC->Vanilla
        RM.generify(ST.make(MD.TFC, "MetalBlock", 1, 8), OP.block.mat(MT.Au, 1));
        RM.generify(ST.make(MD.TFC, "item.Gold Ingot", 1), OP.ingot.mat(MT.Au, 1));

        CR.shaped(ItemList.CrucibleModelInnerLayer.get(1), CR.DEF, " I ", " I ", " f ", 'I', OP.ingot.mat(MT.RedSteel, 1));

        RM.LaserEngraver.addRecipe2(F, 32, 80, ST.tag(0), OP.plateGemTiny.mat(MT.Si, 1), ItemList.GoodCircuitPartCore.get(1));
        //Engine Parts
        {
            OreDictMaterial aMat;
            aMat = MT.Bronze;
            RM.Lathe.addRecipe1(F, 20, 600, OP.stickLong.mat(aMat, 3), ItemList.EngineCrankShaftManual1.get(1));
            CR.shaped(ItemList.EngineCylinderManual1.get(1), CR.DEF, " P ", "fIh", " S ", 'I', OP.ingotDouble.mat(aMat, 1), 'P', OP.plateCurved.mat(aMat, 1), 'S', OP.stick.mat(aMat, 1));
            recipeMaps.CNC.addRecipeX(F, 65, 600, ST.array(OP.stickLong  .mat(aMat, 3), ST.tag(0)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCrankShaft1.get(1), OP.dust.mat(aMat, 1));
            recipeMaps.CNC.addRecipeX(F, 65, 200, ST.array(OP.ingotDouble.mat(aMat, 1), OP.plateCurved.mat(aMat, 1), OP.stick.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCylinder1.get(1), OP.dustSmall.mat(aMat, 4));
            recipeMaps.CNC.addRecipeX(F, 65, 200, ST.array(OP.rotor      .mat(aMat, 1), OP.plateCurved.mat(aMat, 2), OP.plate.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineTurbo1.get(1),    OP.dustSmall.mat(aMat, 4));
            CR.shapeless(ItemList.EngineCrankShaftManual1.get(1),new Object[]{ItemList.EngineCrankShaft1.get(1)});
            CR.shapeless(ItemList.EngineCylinderManual1.get(1),new Object[]{ItemList.EngineCylinder1.get(1)});

            aMat = MT.ArsenicCopper;
            RM.Lathe.addRecipe1(F, 32, 600, OP.stickLong.mat(aMat, 3), ItemList.EngineCrankShaftManual2.get(1));
            CR.shaped(ItemList.EngineCylinderManual2.get(1), CR.DEF, " P ", "fIh", " S ", 'I', OP.ingotDouble.mat(aMat, 1), 'P', OP.plateCurved.mat(aMat, 1), 'S', OP.stick.mat(aMat, 1));
            recipeMaps.CNC.addRecipeX(F, 65, 600, ST.array(OP.stickLong  .mat(aMat, 3), ST.tag(0)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCrankShaft2.get(1), OP.dust.mat(aMat, 1));
            recipeMaps.CNC.addRecipeX(F, 65, 200, ST.array(OP.ingotDouble.mat(aMat, 1), OP.plateCurved.mat(aMat, 1), OP.stick.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCylinder2.get(1), OP.dustSmall.mat(aMat, 4));
            recipeMaps.CNC.addRecipeX(F, 65, 200, ST.array(OP.rotor      .mat(aMat, 1), OP.plateCurved.mat(aMat, 2), OP.plate.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineTurbo2.get(1), OP.dustSmall.mat(aMat, 4));
            CR.shapeless(ItemList.EngineCrankShaftManual2.get(1),new Object[]{ItemList.EngineCrankShaft2.get(1)});
            CR.shapeless(ItemList.EngineCylinderManual2.get(1),new Object[]{ItemList.EngineCylinder2.get(1)});

            aMat = MT.ArsenicBronze;
            RM.Lathe.addRecipe1(F, 32, 600, OP.stickLong.mat(aMat, 3), ItemList.EngineCrankShaftManual3.get(1));
            CR.shaped(ItemList.EngineCylinderManual3.get(1), CR.DEF, " P ", "fIh", " S ", 'I', OP.ingotDouble.mat(aMat, 1), 'P', OP.plateCurved.mat(aMat, 1), 'S', OP.stick.mat(aMat, 1));
            recipeMaps.CNC.addRecipeX(F, 65, 600, ST.array(OP.stickLong  .mat(aMat, 3), ST.tag(0)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCrankShaft3.get(1), OP.dust.mat(aMat, 1));
            recipeMaps.CNC.addRecipeX(F, 65, 200, ST.array(OP.ingotDouble.mat(aMat, 1), OP.plateCurved.mat(aMat, 1), OP.stick.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCylinder3.get(1), OP.dustSmall.mat(aMat, 4));
            recipeMaps.CNC.addRecipeX(F, 65, 200, ST.array(OP.rotor      .mat(aMat, 1), OP.plateCurved.mat(aMat, 2), OP.plate.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineTurbo3.get(1), OP.dustSmall.mat(aMat, 4));
            CR.shapeless(ItemList.EngineCrankShaftManual3.get(1),new Object[]{ItemList.EngineCrankShaft3.get(1)});
            CR.shapeless(ItemList.EngineCylinderManual3.get(1),new Object[]{ItemList.EngineCylinder3.get(1)});

            aMat = MT.Steel;
            RM.Lathe.addRecipe1(F, 48, 600, OP.stickLong.mat(aMat, 3), ItemList.EngineCrankShaftManual4.get(1));
            CR.shaped(ItemList.EngineCylinderManual4.get(1), CR.DEF, " P ", "fIh", " S ", 'I', OP.ingotDouble.mat(aMat, 1), 'P', OP.plateCurved.mat(aMat, 1), 'S', OP.stick.mat(aMat, 1));
            recipeMaps.CNC.addRecipeX(F, 96, 600, ST.array(OP.stickLong  .mat(aMat, 3), ST.tag(0)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCrankShaft4.get(1), OP.dust.mat(aMat, 1));
            recipeMaps.CNC.addRecipeX(F, 96, 200, ST.array(OP.ingotDouble.mat(aMat, 1), OP.plateCurved.mat(aMat, 1), OP.stick.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCylinder4.get(1), OP.dustSmall.mat(aMat, 4));
            recipeMaps.CNC.addRecipeX(F, 96, 200, ST.array(OP.rotor      .mat(aMat, 1), OP.plateCurved.mat(aMat, 2), OP.plate.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineTurbo4.get(1), OP.dustSmall.mat(aMat, 4));
            CR.shapeless(ItemList.EngineCrankShaftManual4.get(1),new Object[]{ItemList.EngineCrankShaft4.get(1)});
            CR.shapeless(ItemList.EngineCylinderManual4.get(1),new Object[]{ItemList.EngineCylinder4.get(1)});

            aMat = MT.Invar;
            RM.Lathe.addRecipe1(F, 48, 600, OP.stickLong.mat(aMat, 3), ItemList.EngineCrankShaftManual5.get(1));
            CR.shaped(ItemList.EngineCylinderManual5.get(1), CR.DEF, " P ", "fIh", " S ", 'I', OP.ingotDouble.mat(aMat, 1), 'P', OP.plateCurved.mat(aMat, 1), 'S', OP.stick.mat(aMat, 1));
            recipeMaps.CNC.addRecipeX(F, 128, 600, ST.array(OP.stickLong  .mat(aMat, 3), ST.tag(0)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCrankShaft5.get(1), OP.dust.mat(aMat, 1));
            recipeMaps.CNC.addRecipeX(F, 128, 200, ST.array(OP.ingotDouble.mat(aMat, 1), OP.plateCurved.mat(aMat, 1), OP.stick.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCylinder5.get(1), OP.dustSmall.mat(aMat, 4));
            recipeMaps.CNC.addRecipeX(F, 128, 200, ST.array(OP.rotor      .mat(aMat, 1), OP.plateCurved.mat(aMat, 2), OP.plate.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineTurbo5.get(1), OP.dustSmall.mat(aMat, 4));
            CR.shapeless(ItemList.EngineCrankShaftManual5.get(1),new Object[]{ItemList.EngineCrankShaft5.get(1)});
            CR.shapeless(ItemList.EngineCylinderManual5.get(1),new Object[]{ItemList.EngineCylinder5.get(1)});


            aMat = MT.Ti;
            RM.Lathe.addRecipe1(F, 64, 600, OP.stickLong.mat(aMat, 3), ItemList.EngineCrankShaftManual6.get(1));
            CR.shaped(ItemList.EngineCylinderManual6.get(1), CR.DEF, " P ", "fIh", " S ", 'I', OP.ingotDouble.mat(aMat, 1), 'P', OP.plateCurved.mat(aMat, 1), 'S', OP.stick.mat(aMat, 1));
            recipeMaps.CNC.addRecipeX(F, 172, 600, ST.array(OP.stickLong  .mat(aMat, 3), ST.tag(0)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCrankShaft6.get(1), OP.dust.mat(aMat, 1));
            recipeMaps.CNC.addRecipeX(F, 172, 200, ST.array(OP.ingotDouble.mat(aMat, 1), OP.plateCurved.mat(aMat, 1), OP.stick.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCylinder6.get(1), OP.dustSmall.mat(aMat, 4));
            recipeMaps.CNC.addRecipeX(F, 172, 200, ST.array(OP.rotor      .mat(aMat, 1), OP.plateCurved.mat(aMat, 2), OP.plate.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineTurbo6.get(1), OP.dustSmall.mat(aMat, 4));
            CR.shapeless(ItemList.EngineCrankShaftManual6.get(1),new Object[]{ItemList.EngineCrankShaft6.get(1)});
            CR.shapeless(ItemList.EngineCylinderManual6.get(1),new Object[]{ItemList.EngineCylinder6.get(1)});

            aMat = MT.TungstenSteel;
            RM.Lathe.addRecipe1(F, 96, 600, OP.stickLong.mat(aMat, 3), ItemList.EngineCrankShaftManual7.get(1));
            CR.shaped(ItemList.EngineCylinderManual7.get(1), CR.DEF, " P ", "fIh", " S ", 'I', OP.ingotDouble.mat(aMat, 1), 'P', OP.plateCurved.mat(aMat, 1), 'S', OP.stick.mat(aMat, 1));
            recipeMaps.CNC.addRecipeX(F, 256, 600, ST.array(OP.stickLong  .mat(aMat, 3), ST.tag(0)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCrankShaft7.get(1), OP.dust.mat(aMat, 1));
            recipeMaps.CNC.addRecipeX(F, 256, 200, ST.array(OP.ingotDouble.mat(aMat, 1), OP.plateCurved.mat(aMat, 1), OP.stick.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCylinder7.get(1), OP.dustSmall.mat(aMat, 4));
            recipeMaps.CNC.addRecipeX(F, 256, 200, ST.array(OP.rotor      .mat(aMat, 1), OP.plateCurved.mat(aMat, 2), OP.plate.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineTurbo7.get(1), OP.dustSmall.mat(aMat, 4));
            CR.shapeless(ItemList.EngineCrankShaftManual7.get(1),new Object[]{ItemList.EngineCrankShaft7.get(1)});
            CR.shapeless(ItemList.EngineCylinderManual7.get(1),new Object[]{ItemList.EngineCylinder7.get(1)});

            aMat = MT.Ir;
            RM.Lathe.addRecipe1(F, 128, 600, OP.stickLong.mat(aMat, 3), ItemList.EngineCrankShaftManual8.get(1));
            CR.shaped(ItemList.EngineCylinderManual8.get(1), CR.DEF, " P ", "fIh", " S ", 'I', OP.ingotDouble.mat(aMat, 1), 'P', OP.plateCurved.mat(aMat, 1), 'S', OP.stick.mat(aMat, 1));
            recipeMaps.CNC.addRecipeX(F, 256, 600, ST.array(OP.stickLong  .mat(aMat, 3), ST.tag(0)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCrankShaft8.get(1), OP.dust.mat(aMat, 1));
            recipeMaps.CNC.addRecipeX(F, 256, 200, ST.array(OP.ingotDouble.mat(aMat, 1), OP.plateCurved.mat(aMat, 1), OP.stick.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCylinder8.get(1), OP.dustSmall.mat(aMat, 4));
            recipeMaps.CNC.addRecipeX(F, 256, 200, ST.array(OP.rotor      .mat(aMat, 1), OP.plateCurved.mat(aMat, 2), OP.plate.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineTurbo8.get(1), OP.dustSmall.mat(aMat, 4));
            CR.shapeless(ItemList.EngineCrankShaftManual8.get(1),new Object[]{ItemList.EngineCrankShaft8.get(1)});
            CR.shapeless(ItemList.EngineCylinderManual8.get(1),new Object[]{ItemList.EngineCylinder8.get(1)});

            aMat = MT.Cr;
            RM.Lathe.addRecipe1(F, 96, 600, OP.stickLong.mat(aMat, 3), ItemList.EngineCrankShaftManualCr.get(1));
            CR.shaped(ItemList.EngineCylinderManualCr.get(1), CR.DEF, " P ", "fIh", " S ", 'I', OP.ingotDouble.mat(aMat, 1), 'P', OP.plateCurved.mat(aMat, 1), 'S', OP.stick.mat(aMat, 1));
            recipeMaps.CNC.addRecipeX(F, 256, 600, ST.array(OP.stickLong  .mat(aMat, 3), ST.tag(0)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCrankShaftCr.get(1), OP.dust.mat(aMat, 1));
            recipeMaps.CNC.addRecipeX(F, 256, 200, ST.array(OP.ingotDouble.mat(aMat, 1), OP.plateCurved.mat(aMat, 1), OP.stick.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCylinderCr.get(1), OP.dustSmall.mat(aMat, 4));
            recipeMaps.CNC.addRecipeX(F, 256, 200, ST.array(OP.rotor      .mat(aMat, 1), OP.plateCurved.mat(aMat, 2), OP.plate.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineTurboCr.get(1), OP.dustSmall.mat(aMat, 4));
            CR.shapeless(ItemList.EngineCylinderManualCr.get(1),new Object[]{ItemList.EngineCylinderCr.get(1)});
            CR.shapeless(ItemList.EngineCrankShaftManualCr.get(1),new Object[]{ItemList.EngineCrankShaftCr.get(1)});


            aMat = MT.Os;
            recipeMaps.CNC.addRecipeX(F, 256, 600, ST.array(OP.stickLong  .mat(aMat, 3), ST.tag(0)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCrankShaftOs.get(1), OP.dust.mat(aMat, 1));
            recipeMaps.CNC.addRecipeX(F, 256, 200, ST.array(OP.ingotDouble.mat(aMat, 1), OP.plateCurved.mat(aMat, 1), OP.stick.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCylinderOs.get(1), OP.dustSmall.mat(aMat, 4));
            recipeMaps.CNC.addRecipeX(F, 256, 200, ST.array(OP.rotor      .mat(aMat, 1), OP.plateCurved.mat(aMat, 2), OP.plate.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineTurboOs.get(1), OP.dustSmall.mat(aMat, 4));

            aMat = MT.Trinaquadalloy;
            recipeMaps.CNC.addRecipeX(F, 256, 600, ST.array(OP.stickLong  .mat(aMat, 3), ST.tag(0)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCrankShaft9.get(1), OP.dust.mat(aMat, 1));
            recipeMaps.CNC.addRecipeX(F, 256, 200, ST.array(OP.ingotDouble.mat(aMat, 1), OP.plateCurved.mat(aMat, 1), OP.stick.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineCylinder9.get(1), OP.dustSmall.mat(aMat, 4));
            recipeMaps.CNC.addRecipeX(F, 256, 200, ST.array(OP.rotor      .mat(aMat, 1), OP.plateCurved.mat(aMat, 2), OP.plate.mat(aMat, 1)), FL.array(FL.DistW.make(1000)), ZL_FS, ItemList.EngineTurbo9.get(1), OP.dustSmall.mat(aMat, 4));
        }
        CR.shaped(ItemList.VibrateDetector.get(1),CR.DEF,"hR ","RBR"," Rw",'R', OP.ring.mat(MT.StainlessSteel,1),'B',OP.nugget.mat(MT.StainlessSteel   ,1));
        recipeMaps.Assembler.addRecipeX(F,320,200,ST.array(OP.bolt.mat(MT.Co_60,1), OP.plateDense.mat(MT.Pb           ,2)),FL.array(MT.SolderingAlloy.liquid(U,true)),ZL_FS, ItemList.Co60FlawDetectionCore.get(1));

        recipeMaps.HeatMixer.addRecipe2(F, 240, 64, OP.dust.mat(MT.Ba, 1), OP.dust.mat(MT.TiO2, 1), FL.array(FL.CarbonDioxide.make(1000)), ZL_FS, OP.dust.mat(matList.BariumTitanate.get(), 1));
        RM.Lightning.addRecipe2(F, 240, 64, ST.tag(3), OP.plateTiny.mat(matList.BariumTitanate.get(), 1), FL.array(FL.DistW.make(100)), ZL_FS, ItemList.PiezoelectricCeramicPlate.get(1));
        recipeMaps.Assembler.addRecipe2(F, 240, 64, ItemList.PiezoelectricCeramicPlate.get(1), OP.wireFine.mat(MT.Ag, 1), FL.array(MT.SolderingAlloy.liquid(U10, true)), ZL_FS, ItemList.UltrasonicGenerator.get(1));

        RM.Canner.addRecipe2(F, 16, 16, OP.bolt.mat(MT.Co, 4), IL.Reactor_Rod_Empty.get(1), kRegistry.getItem(9980));
    }
}

