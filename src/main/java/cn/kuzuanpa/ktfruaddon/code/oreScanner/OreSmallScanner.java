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

public class OreSmallScanner {
        public int range, xPos, yPos, zPos,timer;
        public World world;
        public List<discoveredOres> discoveredOres=new ArrayList<>();
        public OreSmallScanner(int range, int xPos, int yPos, int zPos, World world){
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
            if(Math.floorDiv(timer,yPos)>=range*range)return true;
            scanOres();
            timer++;
            return false;
        }
        public void resetScanOres(){timer=0;}
        public void scanOres() {
            int targetChunkX=world.getChunkFromBlockCoords(xPos, zPos).xPosition+(Math.floorDiv(timer,yPos)%range);
            int targetChunkZ=world.getChunkFromBlockCoords(xPos, zPos).zPosition+(Math.floorDiv(Math.floorDiv(timer,yPos),range));
            for (int x=0;x<16;x++){
                for (int z=0;z<16;z++){
                        Block block = world.getBlock(targetChunkX*16+x,timer,targetChunkZ*16+z);
                        if (block.getUnlocalizedName().startsWith("gt.meta.ore.small"))addDiscoveredOres(block.getDamageValue(world,targetChunkX*16+x,timer,targetChunkZ*16+z),targetChunkX*16+x,timer,targetChunkZ*16+z);
                }
            }
        }
        public void addDiscoveredOres(int materialID, int firstDiscoveredX, int firstDiscoveredY, int firstDiscoveredZ){
            for (discoveredOres ore : discoveredOres) if (ore != null&&ore.materialID==materialID) {
                ore.amount+=1;
                discoveredOres.add(new discoveredOres(materialID, firstDiscoveredX, firstDiscoveredY, firstDiscoveredZ,ore.amount));
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
