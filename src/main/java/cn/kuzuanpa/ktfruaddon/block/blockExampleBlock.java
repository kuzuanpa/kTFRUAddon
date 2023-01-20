/**
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 * 
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package cn.kuzuanpa.ktfruaddon.block;

import cn.kuzuanpa.ktfruaddon.ktfruaddon;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class blockExampleBlock extends Block {
    public blockExampleBlock(){
        super(Material.sand);
        this.setBlockName(ktfruaddon.MOD_ID + ".example_block");
    }


}
