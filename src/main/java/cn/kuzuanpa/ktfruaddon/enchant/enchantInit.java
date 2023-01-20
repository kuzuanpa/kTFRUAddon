/**
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.enchant;


import net.minecraft.enchantment.Enchantment;

public class enchantInit {
        public static final Enchantment SharpnessTFC = new enchantSharpnessTFC();
        public static final Enchantment SmiteTFC = new enchantSmiteTFC();
        public static final Enchantment ProtectionAdvanced = new enchantProtectionAdvanced("AdvancedProtection",65);
        public static final Enchantment ProtectionExplodeAdvanced = new enchantExplodeProtectionAdvanced("AdvancedExplodeProtection",66);
        public static final Enchantment ProtectionArrowAdvanced = new enchantArrowProtectionAdvanced("AdvancedProjectileProtection",67);
}