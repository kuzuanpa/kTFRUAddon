/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.util.OM;

import static cn.kuzuanpa.ktfruaddon.recipe.recipeManager.HeatMixer;
import static gregapi.data.CS.*;

public class HeatMixer {
    public HeatMixer(){
        HeatMixer    .addRecipe1(T, 16,  354, OM.dust(MT.LiOH                ,U *18), FL.array(MT.Cl.gas(U * 6, T))                                                              , FL.array(MT.H2O.liquid(U * 9, F), MT.LiClO3.liquid(U*5, F)), OM.dust(MT.LiCl, U*10));
        HeatMixer    .addRecipe1(T, 16,   48, OM.dust(MT.S                   , U), MT.H           .gas  (U * 2, T)                                                            , MT.H2S            .gas  (U * 3, F), ZL_IS);
        HeatMixer    .addRecipe1(T, 16,   48, OM.dust(MT.Blaze               ,U9   ), MT.H           .gas  (U * 2, T)                                                            , MT.H2S            .gas  (U * 3, F), ZL_IS);
        HeatMixer    .addRecipe1(T, 16,  160, OM.dust(MT.WO3                 ,U * 4), MT.H           .gas  (U * 6, T)                                                            , MT.H2O            .liquid(U* 9, F), OM.dust(MT.W, U));
        HeatMixer    .addRecipe0(T, 16,   48, FL.array(MT.H2S.gas(U*2, T), MT.SO2.gas(U, T))                                                                                   , MT.H2O            .liquid(U* 2, F), OM.dust(MT.S, U));
        HeatMixer    .addRecipe1(T,  16, 186, OM.dust(MT.OREMATS.Uraninite, U), MT.HF.gas(U*8, T), MT.H2O.liquid(U*6, F), OM.dust(MT.UF4, U*5));





    }
}

