package cn.kuzuanpa.ktfruaddon.enchant;


import net.minecraft.enchantment.Enchantment;

public class enchantInit {
        public static final Enchantment SharpnessTFC = new enchantSharpnessTFC();
        public static final Enchantment SmiteTFC = new enchantSmiteTFC();
        public static final Enchantment ProtectionAdvanced = new enchantProtectionAdvanced("AdvancedProtection",65);
        public static final Enchantment ProtectionExplodeAdvanced = new enchantExplodeProtectionAdvanced("AdvancedExplodeProtection",66);
        public static final Enchantment ProtectionArrowAdvanced = new enchantArrowProtectionAdvanced("AdvancedProjectileProtection",67);
}