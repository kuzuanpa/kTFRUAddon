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

package cn.kuzuanpa.ktfruaddon.code;

import cn.kuzuanpa.ktfruaddon.client.render.FxRenderBlockOutline;
import gregapi.oredict.OreDictMaterial;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
public class OreScanner {
        public int range, xPos, yPos, zPos,timer,xChunkPos=0,zChunkPos=0,xChunkStart=0,zChunkStart=0;
        public World world;
        public boolean includeSmallOre=false,finished=false,willRend=false,includeBedRockOre=true;
        public List<discoveredOres> discoveredOres =new ArrayList<>();
        public List<discoveredOresToRend> discoveredOresToRend =new ArrayList<>();
    public OreScanner(int range, int xPos, int yPos, int zPos, World world,boolean includeSmallOre,boolean willRend){
        this.range=range;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        this.world=world;
        this.timer=0;
        this.includeSmallOre=includeSmallOre;
        this.willRend=willRend;
        if(world!=null)xChunkPos=xChunkStart=world.getChunkFromBlockCoords(xPos, zPos).xPosition-range;
        if(world!=null)zChunkPos=zChunkStart=world.getChunkFromBlockCoords(xPos, zPos).zPosition-range;
        includeBedRockOre=true;
    }
    public OreScanner(int range, int xPos, int yPos, int zPos, World world,boolean includeSmallOre,boolean willRend,boolean includeBedRockOre){
        this.range=range;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        this.world=world;
        this.timer=0;
        this.includeSmallOre=includeSmallOre;
        this.willRend=willRend;
        if(world!=null)xChunkPos=xChunkStart=world.getChunkFromBlockCoords(xPos, zPos).xPosition-range;
        if(world!=null)zChunkPos=zChunkStart=world.getChunkFromBlockCoords(xPos, zPos).zPosition-range;
        this.includeBedRockOre=includeBedRockOre;
    }

        /**
         * Start or Continue a scanning process.
         * @return is scanning finished.
         */
        public boolean startOrContinueScanOres(){
            if(finished)return true;
            if(timer!=0&&timer%yPos==0){
                xChunkPos++;
                if(xChunkPos>xChunkStart+range*2){
                    xChunkPos=xChunkStart;
                    zChunkPos++;
                    if(zChunkPos>zChunkStart+range*2)finished=true;
                    }
            }
            scanOres();
            timer++;
            return false;
        }
        public void resetScanOres(){timer=0;}
        public boolean scanOres() {
            for (int x=0;x<16;x++){
                for (int z=0;z<16;z++){
                    Block block = world.getBlock(xChunkPos*16+x,timer%yPos,zChunkPos*16+z);
                    if (includeBedRockOre&&block.getUnlocalizedName().startsWith("gt.meta.ore.normal.bedrock"))return addDiscoveredOres(block.getDamageValue(world,xChunkPos*16+x,timer%yPos,zChunkPos*16+z),xChunkPos*16+x,timer%yPos,zChunkPos*16+z,2);
                    if (includeBedRockOre&&block.getUnlocalizedName().startsWith("gt.meta.ore.small.bedrock"))return addDiscoveredOres(block.getDamageValue(world,xChunkPos*16+x,timer%yPos,zChunkPos*16+z),xChunkPos*16+x,timer%yPos,zChunkPos*16+z,3);
                    if (block.getUnlocalizedName().startsWith("gt.meta.ore.normal"))return addDiscoveredOres(block.getDamageValue(world,xChunkPos*16+x,timer%yPos,zChunkPos*16+z),xChunkPos*16+x,timer%yPos,zChunkPos*16+z,0);
                    if (includeSmallOre&&block.getUnlocalizedName().startsWith("gt.meta.ore.small"))return addDiscoveredOres(block.getDamageValue(world,xChunkPos*16+x,timer%yPos,zChunkPos*16+z),xChunkPos*16+x,timer%yPos,zChunkPos*16+z,1);
                }
            }
            return true;
        }
    /**You should call this method only on ServerSide because on client the meta of GT's ore will always be 0**/
    public boolean addDiscoveredOres(int materialID, int posX, int posY, int posZ,int oreType){
            if (oreType<0||oreType>2||discoveredOres.stream().anyMatch(ore-> ore.posX==posX&&ore.posY==posY&&ore.posZ==posZ))return false;
            discoveredOres.add(new discoveredOres(materialID, posX, posY, posZ,(short) oreType));
            if(willRend)discoveredOresToRend.add(new discoveredOresToRend(UT.Code.getRGBInt(OreDictMaterial.get(materialID).mRGBaSolid),posX,posY,posZ,(short)oreType));
            return true;
            //FxRenderBlockOutline.addBlockOutlineToRender(new ChunkCoordinates(posX,posY,posZ), UT.Code.getRGBInt(OreDictMaterial.get(materialID).mRGBaSolid),1);
        }

        public void rendOres(){
            discoveredOresToRend.forEach(oreToRend->FxRenderBlockOutline.addBlockOutlineToRender(new ChunkCoordinates(oreToRend.posX,oreToRend.posY,oreToRend.posZ), oreToRend.color,2));
        }
    /**oreType: 0=normal,1=small,2=bedrock normal,3=bedrock small**/
    public static class discoveredOres {
        public discoveredOres(int materialID, int posX, int posY, int posZ,short oreType){
            this.materialID=materialID;
            this.posX=posX;
            this.posY=posY;
            this.posZ=posZ;
            this.oreType=oreType;
        }
        public int materialID;
        public int posX;
        public int posY;
        public int posZ;
        public short oreType;
    }
    public static class discoveredOresToRend {
        public discoveredOresToRend(int color, int posX, int posY, int posZ,short oreType){
            this.color=color;
            this.posX=posX;
            this.posY=posY;
            this.posZ=posZ;
            this.oreType=oreType;
        }
        public int color;
        public int posX;
        public int posY;
        public int posZ;
        public short oreType;
    }

}
