/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.fluid.flList;
import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import cn.kuzuanpa.ktfruaddon.material.matList;
import cn.kuzuanpa.ktfruaddon.recipe.recipeMaps;
import gregapi.data.*;
import gregapi.util.CR;
import gregapi.util.ST;
import net.minecraft.init.Items;

import static gregapi.data.CS.*;
import static gregapi.util.CR.DEF_REV;

public class Circuits {
    public static void init(){

        CR.shaped(ItemList.ResistanceT1.get(2),DEF_REV,"RCh","LPL"," Cx", 'P',OP.plateTiny.mat(MT.Ceramic,1),'L',OP.wireFine.mat(MT.Constantan,1),'C',OP.plateTiny.mat(MT.Cu,1),'R',OP.foil.mat(MT.Rubber,1));
        CR.shaped(ItemList.ResistanceT2.get(2),DEF_REV,"RCh","LPL"," Cx", 'P',OP.plateTiny.mat(MT.Ceramic,1),'L',OP.wireFine.mat(MT.Nichrome,1),'C',OP.plateTiny.mat(MT.Cu,1),'R',OP.foil.mat(MT.Rubber,1));
        recipeMaps.Assembler.addRecipeX(F,16,120,ST.array(OP.plate.mat(MT.Cu,1),OP.wireFine.mat(MT.Constantan,10),OP.plate.mat(MT.Ceramic,1),OP.foil.mat(MT.Rubber,6)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.ResistanceT1.get(16));
        recipeMaps.Assembler.addRecipeX(F,180,90 ,ST.array(OP.plate.mat(MT.Cu,1),OP.wireFine.mat(MT.Nichrome,10),OP.plate.mat(MT.Ceramic,1),OP.foil.mat(MT.Rubber,8)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.ResistanceT2.get(24));
        recipeMaps.Assembler.addRecipeX(F,1000,60 ,ST.array(OP.plate.mat(MT.Cu,1),OP.plateTiny.mat(MT.Ru,1),OP.plate.mat(MT.Ceramic,1),OP.dust.mat(MT.Teflon,4)),FL.array(MT.SolderingAlloy.liquid(U10,F)),ZL_FS,ItemList.ResistanceT3.get(64));
        recipeMaps.Assembler.addRecipeX(F,4096,50 ,ST.array(OP.plate.mat(MT.Au,1),OP.plateTiny.mat(MT.Ru,1),OP.plate.mat(matList.EpoxyResin.mat, 1),OP.dust.mat(MT.Teflon,4)),FL.array(MT.SolderingAlloy.liquid(U10,F)),ZL_FS,ItemList.ResistanceT4.get(64));

        CR.shaped(ItemList.ResistanceT1.get(2),DEF_REV,"RCh","LPL"," Cx", 'P',OP.plateTiny.mat(MT.Ceramic,1),'L',OP.wireFine.mat(MT.Constantan,1),'C',OP.plateTiny.mat(MT.Cu,1),'R',OP.foil.mat(MT.Plastic,1));
        CR.shaped(ItemList.ResistanceT2.get(2),DEF_REV,"RCh","LPL"," Cx", 'P',OP.plateTiny.mat(MT.Ceramic,1),'L',OP.wireFine.mat(MT.Nichrome,1),'C',OP.plateTiny.mat(MT.Cu,1),'R',OP.foil.mat(MT.Plastic,1));
        recipeMaps.Assembler.addRecipeX(F,16,120,ST.array(OP.plate.mat(MT.Cu,1),OP.wireFine.mat(MT.Constantan,10),OP.plate.mat(MT.Ceramic,1),OP.foil.mat(MT.Plastic,6)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.ResistanceT1.get(16));
        recipeMaps.Assembler.addRecipeX(F,80,90 ,ST.array(OP.plate.mat(MT.Cu,1),OP.wireFine.mat(MT.Nichrome,10),OP.plate.mat(MT.Ceramic,1),OP.foil.mat(MT.Plastic,8)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.ResistanceT2.get(24));

        CR.shaped(ItemList.CoilT1.get(2),DEF_REV,"WCW","FSF","WxW", 'S',OP.bolt.mat(MT.ElectricalSteel,1),'W',OP.wireFine.mat(MT.Cu,1),'C',OP.plateTiny.mat(MT.Cu,1),'F',OP.foil.mat(MT.Rubber,1));
        CR.shaped(ItemList.CoilT2.get(2),DEF_REV,"WCW","FSF","WxW", 'S',OP.bolt.mat(MT.ElectricalSteel,1),'W',OP.wireFine.mat(MT.Au,1),'C',OP.plateTiny.mat(MT.Cu,1),'F',OP.foil.mat(MT.Rubber,1));
        recipeMaps.Assembler.addRecipeX(F,16,180,ST.array(OP.plate.mat(MT.Cu,1),OP.stick.mat(MT.ElectricalSteel,4),OP.wireFine.mat(MT.Cu,32),OP.foil.mat(MT.Rubber,8)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CoilT1.get(20));
        recipeMaps.Assembler.addRecipeX(F,80,140,ST.array(OP.plate.mat(MT.Cu,1),OP.stick.mat(MT.ElectricalSteel,4),OP.wireFine.mat(MT.Au,32),OP.foil.mat(MT.Rubber,8)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CoilT2.get(20));
        recipeMaps.Assembler.addRecipeX(F,1000,100,ST.array(OP.plate.mat(MT.Cu,1),OP.stick.mat(MT.ElectricalSteel,4),OP.wireFine.mat(MT.Pt,32),OP.dust.mat(MT.Teflon,4)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CoilT3.get(32));
        recipeMaps.Assembler.addRecipeX(F,4096,80,ST.array(OP.plate.mat(MT.Au,1),OP.stick.mat(MT.ElectricalSteel,4),OP.wireFine.mat(MT.Graphene,32),OP.dust.mat(MT.Teflon,4)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CoilT4.get(32));

        CR.shaped(ItemList.CoilT1.get(2),DEF_REV,"WCW","FSF","WxW", 'S',OP.bolt.mat(MT.ElectricalSteel,1),'W',OP.wireFine.mat(MT.Cu,1),'C',OP.plateTiny.mat(MT.Cu,1),'F',OP.foil.mat(MT.Plastic,1));
        CR.shaped(ItemList.CoilT2.get(2),DEF_REV,"WCW","FSF","WxW", 'S',OP.bolt.mat(MT.ElectricalSteel,1),'W',OP.wireFine.mat(MT.Au,1),'C',OP.plateTiny.mat(MT.Cu,1),'F',OP.foil.mat(MT.Plastic,1));
        recipeMaps.Assembler.addRecipeX(F,16,180,ST.array(OP.plate.mat(MT.Cu,1),OP.stick.mat(MT.ElectricalSteel,4),OP.wireFine.mat(MT.Cu,32),OP.foil.mat(MT.Plastic,8)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CoilT1.get(20));
        recipeMaps.Assembler.addRecipeX(F,80,140,ST.array(OP.plate.mat(MT.Cu,1),OP.stick.mat(MT.ElectricalSteel,4),OP.wireFine.mat(MT.Au,32),OP.foil.mat(MT.Plastic,8)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CoilT2.get(20));

        
        CR.shaped(ItemList.CapacitorT1.get(4),DEF_REV," PC","FPC"," xW", 'P', Items.paper,'W',OP.wireFine.mat(MT.Cu,1),'C',OP.foil.mat(MT.Cu,1),'F',OP.foil.mat(MT.Rubber,1));
        CR.shaped(ItemList.CapacitorT2.get(4),DEF_REV," PC","FPC"," xW", 'P',OP.dust.mat(MT.Al2O3,1),'W',OP.wireFine.mat(MT.Ag,1),'C',OP.foil.mat(MT.Al,1),'F',OP.foil.mat(MT.Rubber,1));
        recipeMaps.Assembler.addRecipeX(F,  16,120,ST.array(OP.foil.mat(MT.Cu,16),OP.foil.mat(MT.Rubber,8),OP.plate.mat(MT.Paper,4),OP.wireFine.mat(MT.Cu,8)), FL.array(MT.SolderingAlloy.liquid(U8,F)),ZL_FS,ItemList.CapacitorT1.get(32));
        recipeMaps.Assembler.addRecipeX(F, 140,100,ST.array(OP.foil.mat(MT.Al,16),OP.foil.mat(MT.Rubber,8),OP.dust.mat(MT.Al2O3,16),OP.wireFine.mat(MT.Ag,8)), FL.array(MT.SolderingAlloy.liquid(U8,F)),ZL_FS,ItemList.CapacitorT2.get(40));
        recipeMaps.Assembler.addRecipeX(F,1000, 80,ST.array(OP.plate.mat(MT.Cu,1),OP.wireFine.mat(MT.Pt,4),OP.foil.mat(MT.Nb,32),OP.dust.mat(MT.Teflon,4)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CapacitorT3.get(64));
        recipeMaps.Assembler.addRecipeX(F,1000, 80,ST.array(OP.plate.mat(MT.Cu,1),OP.wireFine.mat(MT.Pt,4),OP.foil.mat(MT.Ta,32),OP.dust.mat(MT.Teflon,4)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CapacitorT3.get(64));
        recipeMaps.Assembler.addRecipeX(F,1000, 80,ST.array(OP.plate.mat(MT.Cu,1),OP.wireFine.mat(MT.Graphene,4),OP.foil.mat(MT.Nb,32),OP.dust.mat(MT.Teflon,4)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CapacitorT3.get(64));
        recipeMaps.Assembler.addRecipeX(F,1000, 80,ST.array(OP.plate.mat(MT.Cu,1),OP.wireFine.mat(MT.Graphene,4),OP.foil.mat(MT.Ta,32),OP.dust.mat(MT.Teflon,4)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CapacitorT3.get(64));

        recipeMaps.Assembler.addRecipeX(F, 256,60,ST.array(OP.plateGem.mat(MT.Si,1),OP.dust.mat(MT.B,2),OP.dust.mat(MT.P,4),OP.foil.mat(MT.Ag,4),OP.dust.mat(MT.Teflon,1)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.DiodeT1.get(32));
        recipeMaps.Assembler.addRecipeX(F,1024,10,ST.array(ItemList.DiodeT2Part.get(4),OP.wireFine.mat(MT.Ag,1),OP.dustTiny.mat(MT.Teflon,1)), FL.array(MT.SolderingAlloy.liquid(U100,F)),ZL_FS,ItemList.DiodeT2.get(4));
        recipeMaps.Assembler.addRecipeX(F,4096,10,ST.array(ItemList.DiodeT3Part.get(4),OP.wireFine.mat(MT.Graphene,1),OP.dustTiny.mat(MT.Teflon,1)), FL.array(MT.SolderingAlloy.liquid(U100,F)),ZL_FS,ItemList.DiodeT3.get(4));




        RM.           Mixer    .addRecipe1(F,32,80,OP.dust.mat(MT.FeCl3,4),FL.array(MT.AquaRegia.liquid(U,T)),FL.array(flList.EtchingSolution.make(1000)));
        recipeMaps.Assembler.addRecipe2(F,16,80,OP.plate.mat(MT.Wood,1),OP.dust.mat(MT.SiO2,1),FL.array(FL.Glue.make(288)),ZL_FS,IL.Circuit_Plate_Empty.get(1));

        recipeMaps.Assembler.addRecipe1(F,16,80,OP.foil.mat(MT.Bakelite,4),FL.array(FL.Glue.make(288)),ZL_FS,ItemList.CircuitBoardEmptyT2.get(1));

        recipeMaps.Assembler.addRecipe2(F,16,80,OP.foil.mat(MT.Bakelite,6),OP.foil.mat(matList.EpoxyResin.mat,6),FL.array(FL.Glass.make(576)),ZL_FS,ItemList.CircuitBoardEmptyT3.get(1));

        RM.           Bath     .addRecipe2(F,16,80,IL.Circuit_Plate_Empty.get(1),OP.foil.mat(MT.Cu,2),FL.array(flList.EtchingSolution.make(200)),ZL_FS,IL.Circuit_Plate_Copper.get(1));
        RM.           Press    .addRecipe2(F,16,80,IL.Circuit_Plate_Empty.get(1),IL.Circuit_Wire_Copper.get(2),ZL_FS,ZL_FS,IL.Circuit_Plate_Copper.get(1));

        RM.           Bath     .addRecipe2(F,16,80,ItemList.CircuitBoardEmptyT2.get(1),OP.foil.mat(MT.Au,7),FL.array(flList.EtchingSolution.make(700)),ZL_FS,IL.Circuit_Plate_Gold.get(1));
        RM.           Press    .addRecipe2(F,16,80,ItemList.CircuitBoardEmptyT2.get(1),IL.Circuit_Wire_Gold.get(4),ZL_FS,ZL_FS,IL.Circuit_Plate_Gold.get(1));

        RM.           Bath     .addRecipe2(F,16,80,ItemList.CircuitBoardEmptyT3.get(1),OP.foil.mat(MT.Pt,11),FL.array(flList.EtchingSolution.make(1100)),ZL_FS,IL.Circuit_Plate_Platinum.get(1));
        RM.           Press    .addRecipe2(F,16,80,ItemList.CircuitBoardEmptyT3.get(1),IL.Circuit_Wire_Platinum.get(8),ZL_FS,ZL_FS,IL.Circuit_Plate_Platinum.get(1));

        CR.shapeless(ItemList.CircuitBoardBasicUncompleted.get(1),new Object[]{IL.Circuit_Part_Basic.get(1),IL.Circuit_Part_Basic.get(1),IL.Circuit_Plate_Copper.get(1),ItemList.CoilT1.get(1),ItemList.CapacitorT1.get(1),ItemList.CapacitorT1.get(1)});
        CR.shapeless(IL.Circuit_Board_Basic.get(1),new Object[]{ItemList.CircuitBoardBasicUncompleted.get(1),ItemList.ResistanceT1.get(1),ItemList.ResistanceT1.get(1),ItemList.ResistanceT1.get(1),ItemList.ResistanceT1.get(1)});

        CR.shapeless(ItemList.CircuitBoardGoodUncompleted1.get(1),new Object[]{IL.Circuit_Part_Good.get(1),IL.Circuit_Part_Good.get(1),IL.Circuit_Plate_Copper.get(1),ItemList.CoilT1.get(1),ItemList.CoilT1.get(1)});
        CR.shapeless(ItemList.CircuitBoardGoodUncompleted2.get(1),new Object[]{ItemList.CircuitBoardGoodUncompleted1.get(1),ItemList.CapacitorT1.get(1),ItemList.CapacitorT1.get(1),ItemList.CapacitorT1.get(1),ItemList.CapacitorT1.get(1),ItemList.CapacitorT1.get(1),ItemList.CapacitorT1.get(1),ItemList.CapacitorT1.get(1),ItemList.CapacitorT1.get(1)});
        CR.shapeless(IL.Circuit_Board_Good.get(1),new Object[]{ItemList.CircuitBoardGoodUncompleted2.get(1),ItemList.ResistanceT1.get(1),ItemList.ResistanceT1.get(1),ItemList.ResistanceT1.get(1),ItemList.ResistanceT1.get(1),ItemList.ResistanceT1.get(1),ItemList.ResistanceT1.get(1),ItemList.ResistanceT1.get(1),ItemList.ResistanceT1.get(1)});

        recipeMaps.Assembler.addRecipeX(F,16  ,120,ST.array(IL.Circuit_Part_Basic.get(2),   IL.Circuit_Plate_Copper.get(1),   ItemList.CoilT1.get(1),ItemList.CapacitorT1.get(2),ItemList.ResistanceT1.get(4)), FL.array(FL.Glue.make(72)),ZL_FS, IL.Circuit_Board_Basic.get(1));
        recipeMaps.Assembler.addRecipeX(F,64  ,120,ST.array(IL.Circuit_Part_Good.get(2),    IL.Circuit_Plate_Copper.get(1),   ItemList.CoilT1.get(2),ItemList.CapacitorT1.get(8),ItemList.ResistanceT1.get(8)), FL.array(FL.Glue.make(72)),ZL_FS, IL.Circuit_Board_Good.get(1));
        recipeMaps.Assembler.addRecipeX(F,128 ,120,ST.array(IL.Circuit_Part_Advanced.get(4),IL.Circuit_Plate_Gold.get(1),     ItemList.CoilT2.get(2),ItemList.CapacitorT2.get(6),ItemList.ResistanceT2.get(12),ItemList.DiodeT1.get(2)), FL.array(flList.MoltenBakelite.make(72)),ZL_FS, IL.Circuit_Board_Advanced.get(1));
        recipeMaps.Assembler.addRecipeX(F,512 ,120,ST.array(IL.Circuit_Part_Elite.get(4),   IL.Circuit_Plate_Gold.get(1),     ItemList.CoilT2.get(4),ItemList.CapacitorT2.get(8),ItemList.ResistanceT2.get(20),ItemList.DiodeT1.get(2)), FL.array(flList.MoltenBakelite.make(72)),ZL_FS, IL.Circuit_Board_Elite.get(1));
        recipeMaps.Assembler.addRecipeX(F,2048,120,ST.array(IL.Circuit_Part_Master.get(6),  IL.Circuit_Plate_Platinum.get(1), ItemList.CoilT3.get(8),ItemList.CapacitorT3.get(24),ItemList.ResistanceT3.get(64),ItemList.DiodeT2.get(8)), FL.array(flList.MoltenTeflon.make(144)),ZL_FS, IL.Circuit_Board_Master.get(1));
        recipeMaps.Assembler.addRecipeX(F,8192,120,ST.array(IL.Circuit_Part_Ultimate.get(8),IL.Circuit_Plate_Platinum.get(1), ItemList.CoilT4.get(8),ItemList.CapacitorT3.get(64),ItemList.ResistanceT4.get(128),ItemList.DiodeT3.get(16)), FL.array(flList.MoltenTeflon.make(144)),ZL_FS, IL.Circuit_Board_Ultimate.get(1));

        //downgrading parts
        CR.shapeless(ItemList.CapacitorT1.get(1),new Object[]{ItemList.CapacitorT2.get(1)});
        CR.shapeless(ItemList.ResistanceT1.get(1),new Object[]{ItemList.ResistanceT2.get(1)});
        CR.shapeless(ItemList.CoilT1.get(1),new Object[]{ItemList.CoilT2.get(1)});
        CR.shapeless(ItemList.CoilT2.get(1),new Object[]{ItemList.CoilT3.get(1)});
        CR.shapeless(ItemList.CoilT3.get(1),new Object[]{ItemList.CoilT4.get(1)});
        CR.shapeless(ItemList.DiodeT1.get(1),new Object[]{ItemList.DiodeT2.get(1)});
        CR.shapeless(ItemList.DiodeT2.get(1),new Object[]{ItemList.DiodeT3.get(1)});

        CR.shapeless(IL.Circuit_Part_Basic.get(1), new Object[]{IL.Circuit_Part_Good.get(1)});
        CR.shapeless(IL.Circuit_Part_Good.get(1), new Object[]{IL.Circuit_Part_Advanced.get(1)});
        CR.shapeless(IL.Circuit_Part_Advanced.get(1), new Object[]{IL.Circuit_Part_Elite.get(1)});
        CR.shapeless(IL.Circuit_Part_Elite.get(1), new Object[]{IL.Circuit_Part_Master.get(1)});
        CR.shapeless(IL.Circuit_Part_Master.get(1), new Object[]{IL.Circuit_Part_Ultimate.get(1)});


    }
}
