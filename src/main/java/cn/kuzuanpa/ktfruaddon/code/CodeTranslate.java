/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.code;

import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import cpw.mods.fml.common.FMLLog;
import gregapi.data.IL;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictPrefix;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.util.Map;

/**
 * Translate ID and Names that Got from Minecraft Game to Codes
 */
public class CodeTranslate {
        public static String itemToCode(ItemStack stack){
            if(stack==null)return null;
            if(stack.getUnlocalizedName().startsWith("gt.multiitem.")) for (IL gItem: IL.values()) if (gItem!=null&&gItem.exists()&&gItem.get(1)!=null&& gItem.get(1).getItem()==stack.getItem()&&gItem.get(1).getItemDamage()==stack.getItemDamage()) return "IL."+gItem.toString()+".get("+stack.stackSize+")";
            if(stack.getUnlocalizedName().startsWith("ktfru.item.")) for (ItemList kItem:ItemList.values())if (kItem!=null&&kItem.get(1)!=null&&kItem.get(1).getItem()==stack.getItem()&&kItem.get(1).getItemDamage()==stack.getItemDamage()) return "ItemList."+kItem.toString()+".get("+stack.stackSize+")";
            if(stack.getItem().getUnlocalizedName().startsWith("gt.")) {
            String tmp= OreDictPrefixToCode(stack);
            if (tmp!=null)return tmp;
            if(stack.getItem().getUnlocalizedName().startsWith("gt.multitileentity.")) return "gRegistry.getItem("+stack.getItemDamage()+")";
            }
            return "No Translate found"+stack.getUnlocalizedName()+"/"+stack.getItem().getUnlocalizedName();
        }
        @SuppressWarnings("Not really finished")
        public static String materialIDToCode(int id){
        return "OreDictMaterial.get("+id+"/*"+OreDictMaterial.get(id).mNameInternal+"*/)";
        }
        public static String OreDictPrefixToCode(ItemStack stack){
            final Map<String, OreDictPrefix> sPrefixes = OreDictPrefix.sPrefixes;
            for (OreDictPrefix prefix:sPrefixes.values()){
                if (prefix.mat(OreDictMaterial.get(stack.getItemDamage()),1)!=null &&prefix.mat(OreDictMaterial.get(stack.getItemDamage()),1).getItem() ==stack.getItem())
                    return "OP."+prefix.mNameInternal+".mat("+materialIDToCode(stack.getItemDamage())+","+stack.stackSize+")";
            }
            return null;
        }
        /**Work with External Tools**/
        public static void genItemListAll() throws IOException {
            File output = new File("AllItems.list");
                if (output.exists()) output.delete();
                if (output.createNewFile()) {
                    //Get All OreDictPrefix Items:
                    final Map<String, OreDictPrefix> sPrefixes = OreDictPrefix.sPrefixes;
                    try (BufferedWriter out = new BufferedWriter(new PrintWriter(new BufferedOutputStream(new FileOutputStream(output, true))))) {
                        for (OreDictPrefix prefix : sPrefixes.values()) for (short i = 0; i < 32767; i++) if (OreDictMaterial.get(i) != null && prefix.mat(OreDictMaterial.get(i), 1) != null) {
                            out.write("OP." + prefix.mNameInternal + ".mat(" + materialIDToCode(i) + ",<StackSize>) <--> gregtech:" + prefix.mat(OreDictMaterial.get(i), 1).getItem().getUnlocalizedName() + ":" + i + "\n");
                        }
                        for (IL gItem : IL.values()) if (gItem != null && gItem.exists() && gItem.get(1) != null)
                            out.write("IL." + gItem.toString() + ".get(stackSize>) <--> gregtech:" + gItem.get(1).getItem().getUnlocalizedName() + ":" + gItem.get(1).getItemDamage() + "\n");

                        for (ItemList kItem : ItemList.values()) if (kItem != null && kItem.get(1) != null)
                            out.write("ItemList." + kItem.toString() + ".get(stackSize>) <-->  ktfruaddon:" + kItem.get(1).getItem().getUnlocalizedName() + ":" + kItem.get(1).getItemDamage() + "\n");
                    }
                    return;
                }
                FMLLog.log(Level.FATAL, "fail to create file"+output.getName()+",check stacktrace below");
            }
        }
