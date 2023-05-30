/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile;

import cn.kuzuanpa.ktfruaddon.code.CodeTranslate;
import cpw.mods.fml.common.FMLLog;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import net.minecraft.entity.player.EntityPlayer;
import org.apache.logging.log4j.Level;

import static gregapi.data.CS.T;

public class MachineCodeUtil extends MultiTileEntityBasicMachine {
@Override
public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
    if (isServerSide()) {
        openGUI(aPlayer, aSide);
        try {
            for (int i=0;i<this.ACCESSIBLE_SLOTS.length;i++) FMLLog.log(Level.FATAL,""+CodeTranslate.itemToCode(slot(i)));
        }catch (Throwable ignored) {}
    }
    return T;
}
@Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.machine.code";
    }

}
