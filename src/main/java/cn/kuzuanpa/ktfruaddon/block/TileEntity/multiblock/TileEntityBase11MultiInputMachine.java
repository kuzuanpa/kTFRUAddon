package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock;

import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;

import java.util.ArrayList;

public abstract class TileEntityBase11MultiInputMachine extends TileEntityBase10MultiBlockMachine {
    public ArrayList<MultiTileEntityBasicMachine> MultiInputSubBlocks = new ArrayList<>();
    public boolean subSourceRunning =true;
    public void addInputSubSource(MultiTileEntityBasicMachine p){
        this.MultiInputSubBlocks.add(p);
    }
    public ArrayList<MultiTileEntityBasicMachine> getInputSubSource(){
      return this.MultiInputSubBlocks;
    }
    public void isSubSourceRunning(MultiTileEntityBasicMachine subSource){
        subSourceRunning = subSourceRunning && (subSource.getStateRunningPassively() || subSource.getStateRunningActively());
    }

    @Override
    public boolean onTickCheck(long aTimer) {
        if (this.mStructureOkay) {
            subSourceRunning = true;
            this.MultiInputSubBlocks.forEach(this::isSubSourceRunning);
            //FMLLog.log(Level.FATAL,"isSubSourceEunning:"+subSourceRunning);
            if (this.getStateOnOff() != subSourceRunning) this.setStateOnOff(subSourceRunning);
        return subSourceRunning;}else return false;
    }
}

