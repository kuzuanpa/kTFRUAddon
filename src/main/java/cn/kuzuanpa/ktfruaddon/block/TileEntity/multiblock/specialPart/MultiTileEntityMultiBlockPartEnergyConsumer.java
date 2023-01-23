package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.specialPart;

import cpw.mods.fml.common.FMLLog;
import gregapi.data.LH;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Level;

import java.util.List;

public class MultiTileEntityMultiBlockPartEnergyConsumer extends MultiTileEntityBasicMachine {

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.machine.part.electric";
    }
    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
    }
    @Override
    public boolean allowRightclick(Entity aEntity) {
        return false;
    }
}
