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

public class enchantSharpnessTFC extends EnchantmentDamage {
    public final int damageType;
    public static enchantSharpnessTFC INSTANCE;

    public enchantSharpnessTFC() {
        super(Config.addIDConfig(ConfigCategories.IDs.enchantments, "SharpnessTFC", 29), 10, -1);
        LH.add(getName(), "Advanced Sharpness");
        INSTANCE = this;
        this.damageType = 0;
    }

    @Override
    public int getMinEnchantability(int aLevel) {
        return 5 + (aLevel - 1) * 6;
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
        return (float)p_152376_1_ * 75F ;
    }

    @Override
    public String getName() {
        return "enchantment.damage.sharpnessTFC";
    }
}
