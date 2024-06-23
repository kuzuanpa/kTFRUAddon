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

import cn.kuzuanpa.ktfruaddon.recipe.recipeManager;
import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.util.ST;

import static gregapi.data.CS.*;

public class Fusion {
    public static void init(){
        recipeManager.FusionTokamak .addRecipe1(F, 512,  400, ST.tag(0), FL.array(MT.D     .gas   (U*2, T)                        ), FL.array(MT.He_3  .gas   (  U2, F), MT.T     .gas   (  U2, F)                                                      ), ZL_IS             ).setSpecialNumber(  1024L*1024L);

    }
}
