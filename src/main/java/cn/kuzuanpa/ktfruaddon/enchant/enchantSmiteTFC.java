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
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.EnumCreatureAttribute;

public class enchantSmiteTFC extends EnchantmentDamage{
    public final int damageType;
    public static enchantSmiteTFC INSTANCE;

    public enchantSmiteTFC() {
        super(Config.addIDConfig(ConfigCategories.IDs.enchantments, "SmiteTFC", 30), 5, -1);
        LH.add(getName(), "Advanced Smite");
        INSTANCE = this;
        this.damageType = 0;
    }

    @Override
    public int getMinEnchantability(int aLevel) {
        return 5 + (aLevel - 1) * 7;
    }

    @Override
    public int getMaxEnchantability(int aLevel) {return this.getMinEnchantability(aLevel) + 20;}

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public float func_152376_a(int p_152376_1_, EnumCreatureAttribute p_152376_2_)
    {
        return  p_152376_2_ == EnumCreatureAttribute.UNDEAD ? (float)p_152376_1_ * 100.0F : 0.0F;
    }

    @Override
    public String getName() {
        return "enchantment.damage.smiteTFC";
    }
}
