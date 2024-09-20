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
import com.bioxx.tfc.api.Crafting.CraftingManagerTFC;
import com.bioxx.tfc.api.Crafting.KilnCraftingManager;
import com.bioxx.tfc.api.Crafting.KilnRecipe;
import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFCItems;
import gregapi.data.CS;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.util.CR;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;


public class TFCRecipe {
    public static void init(){
        CraftingManagerTFC.getInstance().addRecipe(ItemList.ClayGlassBlockMold.get(1),  new Object[] { "     "," ### "," ### "," ### ","     ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});
        CraftingManagerTFC.getInstance().addRecipe(ItemList.ClayGlassBottleMold.get(1), new Object[] { "  #  "," ### "," ### "," ### ","     ", '#', new ItemStack(TFCItems.flatClay, 1, 1)});

        HeatRegistry.getInstance().addIndex(new HeatIndex(ItemList.ClayGlassBlockMold .get(1), 0.3, 600, ItemList.CeramicGlassBlockMold .get(1)));
        HeatRegistry.getInstance().addIndex(new HeatIndex(ItemList.ClayGlassBottleMold.get(1), 0.3, 600, ItemList.CeramicGlassBottleMold.get(1)));
        KilnCraftingManager.getInstance().addRecipe(new KilnRecipe(ItemList.ClayGlassBlockMold .get(1), 0, ItemList.CeramicGlassBlockMold .get(1)));
        KilnCraftingManager.getInstance().addRecipe(new KilnRecipe(ItemList.ClayGlassBottleMold.get(1), 0, ItemList.CeramicGlassBottleMold.get(1)));

        CR.shapeless(ItemList.CeramicGlassBlockMoldFull .get(1),new Object[]{ItemList.CeramicGlassBlockMold .get(1), OP.dust.dat(MT.Glass), OP.dust.dat(MT.Glass), OP.dust.dat(MT.Glass)});
        CR.shapeless(ItemList.CeramicGlassBottleMoldFull.get(1),new Object[]{ItemList.CeramicGlassBottleMold.get(1), OP.dust.dat(MT.Glass)});

        HeatRegistry.getInstance().addIndex(new HeatIndex(ItemList.CeramicGlassBlockMoldFull .get(1), 0.3, 800, ItemList.CeramicGlassBlockMoldComplete .get(1)));
        HeatRegistry.getInstance().addIndex(new HeatIndex(ItemList.CeramicGlassBottleMoldFull.get(1), 0.3, 800, ItemList.CeramicGlassBottleMoldComplete.get(1)));

        CR.shapeless(new ItemStack(Blocks.glass),        new Object[]{ItemList.CeramicGlassBlockMoldComplete .get(1), CS.OreDictToolNames.hammer});
        CR.shapeless(new ItemStack(TFCItems.glassBottle),new Object[]{ItemList.CeramicGlassBottleMoldComplete.get(1), CS.OreDictToolNames.hammer});

    }
}
