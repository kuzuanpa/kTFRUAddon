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
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.util.CR;

public class CompactItem {
    public static void init(){
        CR.shaped(ItemList.BasinModelInnerLayer.get(1),CR.DEF," I "," I "," f ",'I', OP.ingot.mat(MT.RedSteel,1));
        CR.shaped(ItemList.BasinModelInnerLayer.get(1),CR.DEF," I "," I "," f ",'I', OP.ingot.mat(MT.BlueSteel,1));
    }
}

