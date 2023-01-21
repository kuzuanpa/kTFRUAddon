package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock;

import cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.specialPart.partEnergyConsumer;
import cpw.mods.fml.common.FMLLog;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;

public class TileEntityBase11MultiInputMachine extends TileEntityBase10MultiBlockMachine {
    public ArrayList<MultiTileEntityBasicMachine> MultiInputSubBlocks = new ArrayList<>();
    public boolean subSourceRunning =true;
    public boolean checkStructure2() {
        return false;
    }

    public DelegatorTileEntity<IInventory> getItemInputTarget(byte b) {
        return null;
    }

    public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte b) {
        return null;
    }

    public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte b) {
        return null;
    }

    public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte b, Fluid fluid) {
        return null;
    }

    public String getTileEntityName() {
        return null;
    }

    public boolean isInsideStructure(int i, int i1, int i2) {
        return false;
    }
    public void addInputSubSource(MultiTileEntityBasicMachine p){
        this.MultiInputSubBlocks.add(p);
    }
    public ArrayList getInputSubBlocks(){
      return this.MultiInputSubBlocks;
    }
    public void isSubSourceRunning(MultiTileEntityBasicMachine subSource){
        subSourceRunning = subSourceRunning && (subSource.getStateRunningPassively() || subSource.getStateRunningActively());
    }

    @Override
    public boolean onTickCheck(long aTimer) {
        subSourceRunning = true;
        this.MultiInputSubBlocks.forEach(this::isSubSourceRunning);
        //FMLLog.log(Level.FATAL,"potick"+subSourceRunning);
        if (this.getStateOnOff()!= subSourceRunning) this.setStateOnOff(subSourceRunning);
        return subSourceRunning;
    }
}

