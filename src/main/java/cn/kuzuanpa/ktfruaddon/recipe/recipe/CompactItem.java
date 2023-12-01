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
import gregapi.util.UT;
import net.minecraft.init.Items;

import static gregapi.data.CS.*;

public class CompactItem {
    public static void init(){
        CR.shaped(ItemList.CrucibleModelInnerLayer.get(1),CR.DEF," I "," I "," f ",'I', OP.ingot.mat(MT.RedSteel,1));
        CR.shaped(ItemList.CrucibleModelInnerLayer.get(1),CR.DEF," I "," I "," f ",'I', OP.ingot.mat(MT.BlueSteel,1));

        recipeManager.Assembler.addRecipe1(false,6400,80,ItemList.CPUGT3660.get(1), FL.array(MT.SolderingAlloy.liquid(U2,F)),ZL_FS,ItemList.IntelligentCore.get(2));

        recipeManager.Assembler.addRecipeX(false,16384,16384, ST.array(OP.nugget.mat(MT.DraconiumAwakened,2),OP.plateTiny.mat(MT.Ad,2),OP.wireFine.mat(MT.Terrasteel,64),OP.plate.mat(MT.VibraniumSteel,1),OP.plateGem.mat(MT.NetherStar,1)), ZL_FS,ZL_FS,ST.make(Items.skull,1,3, UT.NBT.make("SkullOwner","kuzuanpa")));
    }
}

