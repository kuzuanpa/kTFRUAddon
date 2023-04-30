/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.util;

import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import gregapi.data.IL;
import gregapi.oredict.OreDictMaterial;
import net.minecraft.item.ItemStack;

/**
 * Translate ID and Names that Got from Minecraft Game to Codes
 */
public class CodeTranslate {
        public static String itemToCode(ItemStack stack){
            if(stack.getUnlocalizedName().startsWith("gt.multiitem.")) for (IL gItem: IL.values()) if (gItem!=null&&gItem.exists()&&gItem.get(1)!=null&& gItem.get(1).getItem()==stack.getItem()&&gItem.get(1).getItemDamage()==stack.getItemDamage()) return "IL."+gItem.toString()+".get("+stack.stackSize+")";
            if(stack.getUnlocalizedName().startsWith("ktfru.item.")) for (ItemList kItem:ItemList.values())if (kItem!=null&&kItem.get(1)!=null&&kItem.get(1).getItem()==stack.getItem()&&kItem.get(1).getItemDamage()==stack.getItemDamage()) return "ItemList."+kItem.toString()+".get("+stack.stackSize+")";
            if(stack.getItem().getUnlocalizedName().startsWith("gt.block.")) return stack.getUnlocalizedName();
            if(stack.getItem().getUnlocalizedName().startsWith("gt.multitileentity.")) return "gRegistry.getItem("+stack.getItemDamage()+")";
            if(stack.getItem().getUnlocalizedName().startsWith("gt.meta.")) return OreDickPrefixToCode(stack);
            return stack.getUnlocalizedName()+"/"+stack.getItem().getUnlocalizedName();
        }
        @SuppressWarnings("Not really finished")
        public static String materialIDToCode(int id){
        return "OreDictMaterial.get("+id+"/*"+OreDictMaterial.get(id).toString()+"*/)";
        }
        public static String OreDickPrefixToCode(ItemStack stack){return "OM."+stack.getItem().getUnlocalizedName().replace("gt.meta.","")+"("+materialIDToCode(stack.getItemDamage())+","+stack.stackSize+")";}
}
