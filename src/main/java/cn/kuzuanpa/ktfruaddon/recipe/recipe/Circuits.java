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

import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import cn.kuzuanpa.ktfruaddon.recipe.recipeManager;
import gregapi.data.FL;
import gregapi.data.IL;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.util.CR;
import gregapi.util.ST;

import static gregapi.data.CS.*;
import static gregapi.util.CR.DEF_REV;

public class Circuits {
    public Circuits(){
        CR.shaped(ItemList.ResistanceT1.get(2),DEF_REV,"RCh","LPL"," Cx", 'P',OP.plateTiny.mat(MT.Ceramic,1),'L',OP.wireFine.mat(MT.Cupronickel,1),'C',OP.plateTiny.mat(MT.Cu,1),'R',OP.foil.mat(MT.Rubber,1));
        CR.shaped(ItemList.ResistanceT2.get(2),DEF_REV,"RCh","LPL"," Cx", 'P',OP.plateTiny.mat(MT.Ceramic,1),'L',OP.wireFine.mat(MT.Nichrome,1),'C',OP.plateTiny.mat(MT.Cu,1),'R',OP.foil.mat(MT.Rubber,1));
        recipeManager.Assembler.addRecipeX(F,16,120,ST.array(OP.plate.mat(MT.Cu,1),OP.wireFine.mat(MT.Cupronickel,10),OP.plate.mat(MT.Ceramic,1),OP.foil.mat(MT.Rubber,6)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.ResistanceT1.get(12));
        recipeManager.Assembler.addRecipeX(F,80,90 ,ST.array(OP.plate.mat(MT.Cu,1),OP.wireFine.mat(MT.Nichrome,10),OP.plate.mat(MT.Ceramic,1),OP.foil.mat(MT.Rubber,8)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.ResistanceT2.get(18));
        //recipeManager.Assembler.addRecipeX(F,1000,60 ,ST.array(OP.plate.mat(MT.Cu,1),OP.plateTiny.mat(MT.Ru,1),OP.plate.mat(MT.Ceramic,1)),FL.array(MT.SolderingAlloy.liquid(U10,F)/*TODO:这里应该有个塑料*/),ZL_FS,ItemList.ResistanceT3.get(32));

        CR.shaped(ItemList.ResistanceT1.get(2),DEF_REV,"RCh","LPL"," Cx", 'P',OP.plateTiny.mat(MT.Ceramic,1),'L',OP.wireFine.mat(MT.Cupronickel,1),'C',OP.plateTiny.mat(MT.Cu,1),'R',OP.foil.mat(MT.Plastic,1));
        CR.shaped(ItemList.ResistanceT2.get(2),DEF_REV,"RCh","LPL"," Cx", 'P',OP.plateTiny.mat(MT.Ceramic,1),'L',OP.wireFine.mat(MT.Nichrome,1),'C',OP.plateTiny.mat(MT.Cu,1),'R',OP.foil.mat(MT.Plastic,1));
        recipeManager.Assembler.addRecipeX(F,16,120,ST.array(OP.plate.mat(MT.Cu,1),OP.wireFine.mat(MT.Cupronickel,10),OP.plate.mat(MT.Ceramic,1),OP.foil.mat(MT.Plastic,6)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.ResistanceT1.get(12));
        recipeManager.Assembler.addRecipeX(F,80,90 ,ST.array(OP.plate.mat(MT.Cu,1),OP.wireFine.mat(MT.Nichrome,10),OP.plate.mat(MT.Ceramic,1),OP.foil.mat(MT.Plastic,8)),FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.ResistanceT2.get(18));

        CR.shaped(ItemList.CoilT1.get(2),DEF_REV,"WCW","FSF","WxW", 'S',OP.bolt.mat(MT.ElectricalSteel,1),'L',OP.wireFine.mat(MT.Cu,1),'C',OP.plateTiny.mat(MT.Cu,1),'F',OP.foil.mat(MT.Rubber,1));
        CR.shaped(ItemList.CoilT2.get(2),DEF_REV,"WCW","FSF","WxW", 'S',OP.bolt.mat(MT.ElectricalSteel,1),'L',OP.wireFine.mat(MT.Au,1),'C',OP.plateTiny.mat(MT.Cu,1),'F',OP.foil.mat(MT.Rubber,1));
        recipeManager.Assembler.addRecipeX(F,16,180,ST.array(OP.plate.mat(MT.Cu,1),OP.stick.mat(MT.ElectricalSteel,4),OP.wireFine.mat(MT.Cu,32),OP.foil.mat(MT.Rubber,8)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CoilT1.get(16));
        recipeManager.Assembler.addRecipeX(F,80,140,ST.array(OP.plate.mat(MT.Cu,1),OP.stick.mat(MT.ElectricalSteel,4),OP.wireFine.mat(MT.Au,32),OP.foil.mat(MT.Rubber,8)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CoilT2.get(20));
        //recipeManager.Assembler.addRecipeX(F,1000,100,ST.array(OP.plate.mat(MT.Cu,1),OP.stick.mat(MT.ElectricalSteel,4),OP.wireFine.mat(MT.Pt,32)/*TODO:这里应该有个塑料*/), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CoilT3.get(24));

        CR.shaped(ItemList.CoilT1.get(2),DEF_REV,"WCW","FSF","WxW", 'S',OP.bolt.mat(MT.ElectricalSteel,1),'L',OP.wireFine.mat(MT.Cu,1),'C',OP.plateTiny.mat(MT.Cu,1),'F',OP.foil.mat(MT.Plastic,1));
        CR.shaped(ItemList.CoilT2.get(2),DEF_REV,"WCW","FSF","WxW", 'S',OP.bolt.mat(MT.ElectricalSteel,1),'L',OP.wireFine.mat(MT.Au,1),'C',OP.plateTiny.mat(MT.Cu,1),'F',OP.foil.mat(MT.Plastic,1));
        recipeManager.Assembler.addRecipeX(F,16,180,ST.array(OP.plate.mat(MT.Cu,1),OP.stick.mat(MT.ElectricalSteel,4),OP.wireFine.mat(MT.Cu,32),OP.foil.mat(MT.Plastic,8)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CoilT1.get(16));
        recipeManager.Assembler.addRecipeX(F,80,140,ST.array(OP.plate.mat(MT.Cu,1),OP.stick.mat(MT.ElectricalSteel,4),OP.wireFine.mat(MT.Au,32),OP.foil.mat(MT.Plastic,8)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CoilT2.get(20));

        
        CR.shaped(ItemList.CapacitorT1.get(1),DEF_REV," PC","FPC"," xW", 'P',OP.plate.mat(MT.Paper,1),'W',OP.wireFine.mat(MT.Cu,1),'C',OP.foil.mat(MT.Cu,1),'F',OP.foil.mat(MT.Rubber,1));
        CR.shaped(ItemList.CapacitorT2.get(1),DEF_REV," PC","FPC"," xW", 'P',OP.plate.mat(MT.Al2O3,1),'W',OP.wireFine.mat(MT.Ag,1),'C',OP.foil.mat(MT.Cu,1),'F',OP.foil.mat(MT.Rubber,1));
        recipeManager.Assembler.addRecipeX(F,16,80,ST.array(OP.foil.mat(MT.Cu,32),OP.foil.mat(MT.Rubber,8),OP.plate.mat(MT.Paper,16),OP.wireFine.mat(MT.Cu,8)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CapacitorT1.get(8));
        recipeManager.Assembler.addRecipeX(F,80,70,ST.array(OP.foil.mat(MT.Al,32),OP.foil.mat(MT.Rubber,8),OP.plate.mat(MT.Al2O3,16),OP.wireFine.mat(MT.Ag,8)), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CapacitorT2.get(10));
        recipeManager.Assembler.addRecipeX(F,1000,50,ST.array(OP.plate.mat(MT.Cu,1),OP.wireFine.mat(MT.Pt,4),OP.foil.mat(MT.Nb,32)/*TODO:这里是聚四氟乙烯*/), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CapacitorT3.get(32));
        recipeManager.Assembler.addRecipeX(F,1000,50,ST.array(OP.plate.mat(MT.Cu,1),OP.wireFine.mat(MT.Pt,4),OP.foil.mat(MT.Ta,32)/*TODO:这里是聚四氟乙烯*/), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CapacitorT3.get(32));
        recipeManager.Assembler.addRecipeX(F,1000,50,ST.array(OP.plate.mat(MT.Cu,1),OP.wireFine.mat(MT.Graphene,4),OP.foil.mat(MT.Nb,32)/*TODO:这里是聚四氟乙烯*/), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CapacitorT3.get(32));
        recipeManager.Assembler.addRecipeX(F,1000,50,ST.array(OP.plate.mat(MT.Cu,1),OP.wireFine.mat(MT.Graphene,4),OP.foil.mat(MT.Ta,32)/*TODO:这里是聚四氟乙烯*/), FL.array(MT.SolderingAlloy.liquid(U4,F)),ZL_FS,ItemList.CapacitorT3.get(32));

        recipeManager.Assembler.addRecipeX(F,16  ,160,ST.array(IL.Circuit_Part_Basic.get(4),   IL.Circuit_Board_Basic.get(1),    ItemList.CoilT1.get(1),ItemList.CapacitorT1.get(2),ItemList.ResistanceT1.get(4)), FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS, IL.Circuit_Basic.get(1));
        recipeManager.Assembler.addRecipeX(F,16  ,160,ST.array(IL.Circuit_Part_Basic.get(4),   IL.Circuit_Board_Basic.get(1),    ItemList.CoilT2.get(1),ItemList.CapacitorT1.get(2),ItemList.ResistanceT1.get(4)), FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS, IL.Circuit_Basic.get(1));
        recipeManager.Assembler.addRecipeX(F,16  ,160,ST.array(IL.Circuit_Part_Basic.get(4),   IL.Circuit_Board_Basic.get(1),    ItemList.CoilT1.get(1),ItemList.CapacitorT2.get(2),ItemList.ResistanceT1.get(4)), FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS, IL.Circuit_Basic.get(1));
        recipeManager.Assembler.addRecipeX(F,16  ,160,ST.array(IL.Circuit_Part_Basic.get(4),   IL.Circuit_Board_Basic.get(1),    ItemList.CoilT2.get(1),ItemList.CapacitorT2.get(2),ItemList.ResistanceT1.get(4)), FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS, IL.Circuit_Basic.get(1));

        recipeManager.Assembler.addRecipeX(F,64  ,160,ST.array(IL.Circuit_Part_Good.get(4),    IL.Circuit_Board_Good.get(1),     ItemList.CoilT1.get(2),ItemList.CapacitorT1.get(8),ItemList.ResistanceT1.get(8)), FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS, IL.Circuit_Good.get(1));
        recipeManager.Assembler.addRecipeX(F,64  ,160,ST.array(IL.Circuit_Part_Good.get(4),    IL.Circuit_Board_Good.get(1),     ItemList.CoilT2.get(2),ItemList.CapacitorT1.get(8),ItemList.ResistanceT1.get(8)), FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS, IL.Circuit_Good.get(1));
        recipeManager.Assembler.addRecipeX(F,64  ,160,ST.array(IL.Circuit_Part_Good.get(4),    IL.Circuit_Board_Good.get(1),     ItemList.CoilT1.get(2),ItemList.CapacitorT2.get(6),ItemList.ResistanceT1.get(8)), FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS, IL.Circuit_Good.get(1));
        recipeManager.Assembler.addRecipeX(F,64  ,160,ST.array(IL.Circuit_Part_Good.get(4),    IL.Circuit_Board_Good.get(1),     ItemList.CoilT2.get(2),ItemList.CapacitorT2.get(6),ItemList.ResistanceT1.get(8)), FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS, IL.Circuit_Good.get(1));

        recipeManager.Assembler.addRecipeX(F,256 ,160,ST.array(IL.Circuit_Part_Advanced.get(4),IL.Circuit_Board_Advanced.get(1), ItemList.CoilT2.get(2),ItemList.CapacitorT2.get(6),ItemList.ResistanceT2.get(12),ItemList.DiodeT1.get(2)), FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS, IL.Circuit_Advanced.get(1));

        recipeManager.Assembler.addRecipeX(F,512 ,160,ST.array(IL.Circuit_Part_Elite.get(4),   IL.Circuit_Board_Elite.get(1),    ItemList.CoilT2.get(4),ItemList.CapacitorT2.get(8),ItemList.ResistanceT2.get(20),ItemList.DiodeT1.get(2)), FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS, IL.Circuit_Elite.get(1));

        recipeManager.Assembler.addRecipeX(F,2048,160,ST.array(IL.Circuit_Part_Master.get(4),  IL.Circuit_Board_Master.get(1),   ItemList.CoilT3.get(8),ItemList.CapacitorT3.get(24),ItemList.ResistanceT3.get(64),ItemList.DiodeT2.get(8)), FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS, IL.Circuit_Master.get(1));

        recipeManager.Assembler.addRecipeX(F,8192,160,ST.array(IL.Circuit_Part_Ultimate.get(4),IL.Circuit_Board_Ultimate.get(1), ItemList.CoilT4.get(8),ItemList.CapacitorT3.get(64),ItemList.ResistanceT4.get(128),ItemList.DiodeT3.get(16)), FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS, IL.Circuit_Ultimate.get(1));

    }
}
