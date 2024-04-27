/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.code;

import codechicken.lib.vec.BlockCoord;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;

public class BoundingBox {
    public final double minX,minY,minZ,maxX,maxY,maxZ;
    public BoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ){
        if (minX>maxX){double tmp=maxX;maxX=minX;minX=tmp;}
        if (minY>maxY){double tmp=maxY;maxY=minY;minY=tmp;}
        if (minZ>maxZ){double tmp=maxZ;maxZ=minZ;minZ=tmp;}
        this.maxX=maxX;this.maxY=maxY;this.maxZ=maxZ;this.minX=minX;this.minY=minY;this.minZ=minZ;
    }
    public BoundingBox(BlockCoord StartCoord,BlockCoord EndCoord){
        if (StartCoord.x>EndCoord.x){int tmp=EndCoord.x;EndCoord.x=StartCoord.x;StartCoord.x=tmp;}
        if (StartCoord.y>EndCoord.y){int tmp=EndCoord.y;EndCoord.y=StartCoord.y;StartCoord.y=tmp;}
        if (StartCoord.z>EndCoord.z){int tmp=EndCoord.z;EndCoord.z=StartCoord.z;StartCoord.z=tmp;}
        this.maxX=EndCoord.x;this.maxY=EndCoord.y;this.maxZ=EndCoord.z;this.minX=StartCoord.x;this.minY=StartCoord.y;this.minZ=StartCoord.z;
    }
    public BoundingBox(Vec3 StartCoord, Vec3 EndCoord){
        if (StartCoord.xCoord>EndCoord.xCoord){double tmp=EndCoord.xCoord;EndCoord.xCoord=StartCoord.xCoord;StartCoord.xCoord=tmp;}
        if (StartCoord.yCoord>EndCoord.yCoord){double tmp=EndCoord.yCoord;EndCoord.yCoord=StartCoord.yCoord;StartCoord.yCoord=tmp;}
        if (StartCoord.zCoord>EndCoord.zCoord){double tmp=EndCoord.zCoord;EndCoord.zCoord=StartCoord.zCoord;StartCoord.zCoord=tmp;}
        this.maxX=EndCoord.xCoord;this.maxY=EndCoord.yCoord;this.maxZ=EndCoord.zCoord;this.minX=StartCoord.xCoord;this.minY=StartCoord.yCoord;this.minZ=StartCoord.zCoord;
    }
    public boolean isCoordInBox(BlockCoord coord){
        return  minX<=coord.x&&coord.x<=maxX&&
                minY<=coord.y&&coord.y<=maxY&&
                minZ<=coord.z&&coord.z<=maxZ;
    }  
    public boolean isCoordInBox(ChunkCoordinates coord){
        return  minX<=coord.posX&&coord.posX<=maxX&&
                minY<=coord.posY&&coord.posY<=maxY&&
                minZ<=coord.posZ&&coord.posZ<=maxZ;
    }
    public boolean isXYZInBox(double x,double y,double z){
        return  minX<=x&&x<=maxX&&
                minY<=y&&y<=maxY&&
                minZ<=z&&z<=maxZ;
    }
}
