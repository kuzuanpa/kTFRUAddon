/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.multiblock.base;

import cn.kuzuanpa.ktfruaddon.tile.parts.MultiBlockPartEnergyConsumer;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;

import java.util.ArrayList;

public abstract class TileEntityBaseMultiInputMachine extends TileEntityBase10MultiBlockMachine {
    public ArrayList<MultiBlockPartEnergyConsumer> MultiInputSubBlocks = new ArrayList<>();
    public boolean subSourceRunning =true;


    public void addInputSubSource(MultiBlockPartEnergyConsumer p){
        this.MultiInputSubBlocks.add(p);
    }
    public ArrayList<MultiBlockPartEnergyConsumer> getInputSubSource(){
      return this.MultiInputSubBlocks;
    }
    public void isSubSourceRunning(MultiBlockPartEnergyConsumer subSource){
        if (subSource.isInvalid()) subSourceRunning = false;
        subSourceRunning = subSourceRunning && (subSource.getStateRunningPassively() || subSource.getStateRunningActively());
    }

    public boolean onTickCheck(long aTimer) {
        if (this.mStructureOkay) {
            subSourceRunning = true;
            if (this.MultiInputSubBlocks.isEmpty()) {
                this.setStateOnOff(false);
                return false;
            }
            this.MultiInputSubBlocks.forEach(this::isSubSourceRunning);
            if (this.getStateOnOff() != subSourceRunning) this.setStateOnOff(subSourceRunning);
            return subSourceRunning;
        }else return false;
    }
}

