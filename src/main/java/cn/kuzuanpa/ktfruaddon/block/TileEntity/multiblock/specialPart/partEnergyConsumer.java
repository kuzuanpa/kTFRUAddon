package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.specialPart;

import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import net.minecraft.entity.Entity;

public class partEnergyConsumer extends MultiTileEntityBasicMachine {

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.machine.part.electric";
    }

    @Override
    public boolean allowRightclick(Entity aEntity) {
        return false;
    }
}
