/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.base;

import cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.specialPart.MultiTileEntityMultiBlockPartEnergyConsumer;
import cpw.mods.fml.common.FMLLog;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
import gregapi.util.UT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;

public abstract class TileEntityBase11MultiInputMachine extends TileEntityBase10MultiBlockMachine {
    public ArrayList<MultiTileEntityMultiBlockPartEnergyConsumer> MultiInputSubBlocks = new ArrayList<>();
    public boolean subSourceRunning =true;

    public void addInputSubSource(MultiTileEntityMultiBlockPartEnergyConsumer p){
        this.MultiInputSubBlocks.add(p);
    }
    public ArrayList<MultiTileEntityMultiBlockPartEnergyConsumer> getInputSubSource(){
      return this.MultiInputSubBlocks;
    }
    public void isSubSourceRunning(MultiTileEntityMultiBlockPartEnergyConsumer subSource){
        if (subSource.isInvalid()) subSourceRunning = false;
           // FMLLog.log(Level.FATAL,"b");
        //}
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
            //FMLLog.log(Level.FATAL, "a" + this.mStructureOkay + "/" + this.MultiInputSubBlocks.isEmpty() + "/" + subSourceRunning);
            return subSourceRunning;
        }else return false;
    }
}

