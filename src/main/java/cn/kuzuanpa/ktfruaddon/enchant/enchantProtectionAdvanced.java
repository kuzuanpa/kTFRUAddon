/**
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.enchant;

import gregapi.config.Config;
import gregapi.config.ConfigCategories;
import gregapi.data.LH;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;

public class enchantProtectionAdvanced extends EnchantmentProtection {

    public static enchantProtectionAdvanced INSTANCE;

    public enchantProtectionAdvanced(String name,int id) {
        super(Config.addIDConfig(ConfigCategories.IDs.enchantments, name, id), 2, -1);
        LH.add(getName(), name);
        INSTANCE = this;
    }

    @Override
    public int getMinEnchantability(int aLevel) {
        return 23 + (aLevel - 1) * 12;
    }

    @Override
    public int getMaxEnchantability(int aLevel) {return this.getMinEnchantability(aLevel) + 30;}

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int calcModifierDamage(int level, DamageSource p_77318_2_)
    {
        if (p_77318_2_.canHarmInCreative())
        {
            return 0;
        }
        else
        {
            float f = (float)(6 + level * level);
            return MathHelper.floor_float(f * 0.75F);
        }
    }
    @Override
    public String getName() {
        return "enchantment.protection.advanced";
    }
}
