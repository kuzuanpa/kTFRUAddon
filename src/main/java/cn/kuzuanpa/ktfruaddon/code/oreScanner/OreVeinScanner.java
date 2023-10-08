/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.code.oreScanner;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class OreVeinScanner {
        public int range, xPos, yPos, zPos,timer;
        public World world;
        public List<discoveredOres> discoveredOres=new ArrayList<>();
        public OreVeinScanner(int range, int xPos, int yPos, int zPos, World world){
            this.range=range;
            this.xPos = xPos;
            this.yPos = yPos;
            this.zPos = zPos;
            this.world=world;
            this.timer=0;
        }

    /**
     * Start or Continue a scanning process.
     * @return is scanning finished.
     */
    public boolean startOrContinueScanOres(){
        if(Math.floorDiv(timer,2)>=range*range)return true;
        scanOres();
        timer++;
        return false;
    }

    public void resetScanOres(){timer=0;}
        public boolean scanOres() {
            int targetChunkX=world.getChunkFromBlockCoords(xPos, zPos).xPosition+(Math.floorDiv(timer,2)%range);
            int targetChunkZ=world.getChunkFromBlockCoords(xPos, zPos).zPosition+(Math.floorDiv(Math.floorDiv(timer,2),range));
            for (int x=7;x<10;x++){
                for (int z=7;z<10;z++){
                    if(timer%2==0) for (int y=0;y<Math.floorDiv(timer,2);y++){
                        Block block = world.getBlock(targetChunkX*16+x,y,targetChunkZ*16+z);
                        if (block.getUnlocalizedName().startsWith("gt.meta.ore.normal"))addDiscoveredOres(block.getDamageValue(world,targetChunkX*16+x,y,targetChunkZ*16+z),targetChunkX*16+x,y,targetChunkZ*16+z);
                    }
                    else for (int y = (int) Math.ceil(yPos/2.0f); y<yPos; y++){
                        Block block = world.getBlock(targetChunkX*16+x,y,targetChunkZ*16+z);
                        if (block.getUnlocalizedName().startsWith("gt.meta.ore.normal"))addDiscoveredOres(block.getDamageValue(world,targetChunkX*16+x,y,targetChunkZ*16+z),targetChunkX*16+x,y,targetChunkZ*16+z);
                    }
                }
            }
            return false;
        }
        public void addDiscoveredOres(int materialID, int firstDiscoveredX, int firstDiscoveredY, int firstDiscoveredZ){
            for (discoveredOres ore : discoveredOres) if (ore != null&&ore.materialID==materialID) {
                ore.amount+=1;
                return;
            }
            discoveredOres.add(new discoveredOres(materialID, firstDiscoveredX, firstDiscoveredY, firstDiscoveredZ,1));
        }
        public static class discoveredOres{
            public discoveredOres(int materialID, int firstDiscoveredX, int firstDiscoveredY, int firstDiscoveredZ, int amount){
                this.materialID=materialID;
                this.firstDiscoveredX=firstDiscoveredX;
                this.firstDiscoveredY=firstDiscoveredY;
                this.firstDiscoveredZ=firstDiscoveredZ;
                this.amount=amount;
            }
            public int materialID;
            public int firstDiscoveredX;
            public int firstDiscoveredY;
            public int firstDiscoveredZ;
            public int amount;
        }

}
