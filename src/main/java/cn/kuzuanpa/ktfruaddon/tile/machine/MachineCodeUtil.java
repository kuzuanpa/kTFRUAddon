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

import cpw.mods.fml.common.FMLLog;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import org.apache.logging.log4j.Level;

public class MachineCodeUtil extends MultiTileEntityBasicMachine {
@Override
public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
    if (isServerSide()) {
        //openGUI(aPlayer, aSide);

        try {
            //for (int i=0;i<this.ACCESSIBLE_SLOTS.length;i++) FMLLog.log(Level.FATAL,""+CodeTranslate.itemToCode(slot(i)));
            //if(aPlayer.isSneaking())CodeTranslate.genItemListAll();
            getRotates(this,new ChunkCoordinates(-300,22,500));

        }catch (Throwable ignored) {}
    }
    return false;
}
    public void getRotates(TileEntity tile, ChunkCoordinates targetTilePos){
        float ti =  tile.getWorldObj().getWorldTime();
        while (ti>=24000) {ti-=24000;}
        float t =24000;
        float alpha = (ti -(t/4))/t*2*3.141592653589793f;
        double theta;
        float phi=-100;
        theta=Math.acos((tile.yCoord-targetTilePos.posY)/Math.cos(alpha));

        double targetA=(tile.yCoord-targetTilePos.posY)/Math.cos(alpha);
        double targetB=(tile.zCoord-targetTilePos.posZ)/Math.cos(alpha);
        FMLLog.log(Level.FATAL,ti+"/"+t+"/"+tile.xCoord+"/"+targetTilePos.posX+"/"+tile.yCoord+"/"+targetTilePos.posY+"/"+tile.zCoord+"/"+targetTilePos.posZ);
        getResolve( phi,theta,alpha,targetA,targetB);

    }
    public void getResolve(double phi,double theta,float alpha,double tA,double tB){
        double A = (Math.cos(phi)*Math.sin(theta)-Math.tan(alpha)*Math.sin(phi));
        double B = (Math.sin(phi)*Math.sin(theta)+Math.tan(alpha)*Math.cos(phi));
        phi+=1;
        FMLLog.log(Level.FATAL,"/"+(tA-A)+"/"+(tB-B)+"/"+alpha+"/"+theta+"/"+phi);
        if (phi>100)return;
        getResolve(phi,theta,alpha,tA,tB);
    }
@Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.machine.code";
    }


}
