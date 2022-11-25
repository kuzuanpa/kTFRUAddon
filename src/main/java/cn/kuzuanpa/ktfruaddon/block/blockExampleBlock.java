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
