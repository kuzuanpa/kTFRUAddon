/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.space.dysonSphere;

import cn.kuzuanpa.ktfruaddon.client.gui.ContainerClientDysonSphereMonitor;
import cn.kuzuanpa.ktfruaddon.client.gui.ContainerCommonDysonSphereMonitor;
import gregapi.tileentity.machines.MultiTileEntityBasicMachineElectric;
import net.minecraft.entity.player.EntityPlayer;

public class dysonSphereMonitor extends MultiTileEntityBasicMachineElectric {
    @Override public String getTileEntityName() {return "ktfru.multitileentity.space.dysonSphere.monitor";}
    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerClientDysonSphereMonitor(aPlayer.inventory, this, mRecipes, aGUIID, mGUITexture);
    }
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerCommonDysonSphereMonitor(aPlayer.inventory, this, aGUIID);
    }
}
