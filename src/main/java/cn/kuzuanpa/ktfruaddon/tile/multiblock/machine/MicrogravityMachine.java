package cn.kuzuanpa.ktfruaddon.tile.multiblock.machine;

import cn.kuzuanpa.ktfruaddon.api.tile.room.IRoomAwareMachine;
import cn.kuzuanpa.ktfruaddon.api.tile.room.RoomCapability;
import gregapi.data.LH;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;

public abstract class MicrogravityMachine extends TileEntityBase10MultiBlockMachine implements IRoomAwareMachine {
    // Room
    @Override public long getRequiredRoomCapabilities() {return RoomCapability.mask(RoomCapability.MICROGRAVITY);}
    @Override public boolean doActive(long aTimer, long aEnergy) {return (worldObj.provider.dimensionId == -2 || isRoomEnvironmentValid()) && super.doActive(aTimer, aEnergy);}

    // I/O
    @Override public DelegatorTileEntity<IInventory> getItemInputTarget(byte aSide) {return null;}
    @Override public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) {return null;}
    @Override public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte aSide) {return null;}
    @Override public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte aSide, Fluid aOutput) {return null;}

    // Tooltip / Meta
    @Override public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + "Requires  MICROGRAVITY.");
        super.addToolTips(aList, aStack, aF3_H);
    }
}
