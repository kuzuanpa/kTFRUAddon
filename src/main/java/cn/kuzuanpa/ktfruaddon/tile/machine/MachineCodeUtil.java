/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.machine;

import cn.kuzuanpa.ktfruaddon.code.OreScanner;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class MachineCodeUtil extends MultiTileEntityBasicMachine {
    public OreScanner oreVeinScanner;
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        oreVeinScanner = new OreScanner(0,xCoord,yCoord,zCoord,worldObj,true,true);
    }
@Override
public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
    if (isServerSide()) {
       // openGUI(aPlayer, aSide);

        try {
            //for (int i=0;i<this.ACCESSIBLE_SLOTS.length;i++) FMLLog.log(Level.FATAL,""+ CodeTranslate.itemToCode(slot(i)));
            if(aPlayer.isSneaking()) oreVeinScanner.resetScanOres();
           // FMLLog.log(Level.FATAL,worldObj.getChunkFromChunkCoords(-28, 43).getBlock(5, 5,0).toString());

        }catch (Throwable ignored) {}
    }
    return false;
}
    public void onTick2(long aTimer, boolean isServerside){
        if(isServerside&&worldObj!=null)oreVeinScanner.startOrContinueScanOres();
        if(isServerSide()&&aTimer%100==0)oreVeinScanner.rendOres();

    }
@Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.machine.code";
    }
}
