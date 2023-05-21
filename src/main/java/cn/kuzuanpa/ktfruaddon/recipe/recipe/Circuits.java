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
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.util.CR;
import gregapi.util.ST;

import static gregapi.data.CS.*;
import static gregapi.util.CR.DEF_REV;

public class Circuits {
    public Circuits(){
        CR.shaped(ItemList.ResistanceT1.get(2),DEF_REV,"RCh","LPL"," Cs", 'P',OP.plateTiny.mat(MT.Ceramic,1),'L',OP.wireFine.mat(MT.Cupronickel,1),'C',OP.plateTiny.mat(MT.Cu,1),'R',OP.foil.mat(MT.Rubber,1));
        CR.shaped(ItemList.ResistanceT2.get(2),DEF_REV,"RCh","LPL"," Cs", 'P',OP.plateTiny.mat(MT.Ceramic,1),'L',OP.wireFine.mat(MT.Nichrome,1),'C',OP.plateTiny.mat(MT.Cu,1),'R',OP.foil.mat(MT.Rubber,1));
        recipeManager.Assembler.addRecipeX(F,16,120,ST.array(OP.plate.mat(MT.Cu,1),OP.wireFine.mat(MT.Cupronickel,10),OP.plate.mat(MT.Ceramic,1),OP.foil.mat(MT.Rubber,6)), FL.array(MT.SolderingAlloy.liquid(U8,F)),ZL_FS,ItemList.ResistanceT1.get(12));
        recipeManager.Assembler.addRecipeX(F,80,90 ,ST.array(OP.plate.mat(MT.Cu,1),OP.wireFine.mat(MT.Nichrome,10),OP.plate.mat(MT.Ceramic,1),OP.foil.mat(MT.Rubber,8)),FL.array(MT.SolderingAlloy.liquid(U8,F)),ZL_FS,ItemList.ResistanceT2.get(18));
        recipeManager.Assembler.addRecipeX(F,1000,60 ,ST.array(OP.plate.mat(MT.Cu,1),OP.plateTiny.mat(MT.Ru,1),OP.plate.mat(MT.Ceramic,1)),FL.array(MT.SolderingAlloy.liquid(U10,F)/*TODO:这里应该有个塑料*/),ZL_FS,ItemList.ResistanceT3.get(32));


    }
}
