package cn.kuzuanpa.ktfruaddon.block.TileEntity;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregapi.api.Abstract_Proxy;
import gregapi.block.multitileentity.MultiTileEntityBlock;
import gregapi.block.multitileentity.MultiTileEntityRegistry;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class tileEntityPreInit {
    public tileEntityPreInit(FMLPreInitializationEvent aEvent) {
        // This gives you your very own 32767 Machine IDs.
        new MultiTileEntityRegistry("ktfru.multitileentity");

        // Every Machine can have another Block, vanilla-material, vanilla-step-sound or Harvest Tool
        MultiTileEntityBlock.getOrCreate(
                MOD_ID, "iron", net.minecraft.block.material.Material.iron,
                net.minecraft.block.Block.soundTypeMetal, gregapi.data.CS.TOOL_pickaxe,
                0, 0, 15, false,
                false
        );
        MultiTileEntityBlock.getOrCreate(
                MOD_ID, "machine", gregapi.block.MaterialMachines.instance,
                net.minecraft.block.Block.soundTypeMetal, gregapi.data.CS.TOOL_cutter,
                0, 0, 15, false,
                false
        );
        MultiTileEntityBlock.getOrCreate(
                MOD_ID, "machine", gregapi.block.MaterialMachines.instance,
                net.minecraft.block.Block.soundTypeMetal, gregapi.data.CS.TOOL_wrench,
                0, 0, 15, false,
                false
        );



    }
}
