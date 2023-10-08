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

import cn.kuzuanpa.ktfruaddon.code.oreScanner.OreVeinScanner;
import cpw.mods.fml.common.FMLLog;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.Level;

public class MachineCodeUtil extends MultiTileEntityBasicMachine {
    public OreVeinScanner oreVeinScanner;
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        oreVeinScanner = new OreVeinScanner(3,xCoord,yCoord,zCoord,worldObj);
    }
@Override
public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
    if (isServerSide()) {
        //openGUI(aPlayer, aSide);

        try {
            oreVeinScanner.discoveredOres.forEach((ore) ->FMLLog.log(Level.FATAL,ore.firstDiscoveredX+","+ore.firstDiscoveredY+","+ore.firstDiscoveredZ+","+ore.materialID));
            //for (int i=0;i<this.ACCESSIBLE_SLOTS.length;i++) FMLLog.log(Level.FATAL,""+CodeTranslate.itemToCode(slot(i)));
            //if(aPlayer.isSneaking()) CodeTranslate.genItemListAll();
           // FMLLog.log(Level.FATAL,worldObj.getChunkFromChunkCoords(-28, 43).getBlock(5, 5,0).toString());

        }catch (Throwable ignored) {}
    }
    return false;
}
    public void onTick2(long aTimer, boolean isServerside){
        if (isServerside&&aTimer%20==1) oreVeinScanner.startOrContinueScanOres();
    }
@Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.machine.code";
    }


}
