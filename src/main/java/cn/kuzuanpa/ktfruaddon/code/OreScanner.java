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
import com.bioxx.tfc.Blocks.Terrain.BlockOre;
import com.bioxx.tfc.TileEntities.TEOre;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.TFCBlocks;
import gregapi.data.MD;
import gregapi.data.MT;
import gregapi.oredict.OreDictMaterial;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class OreScanner {
    public int range, xPos, yPos, zPos, timer, xChunkPos = 0, zChunkPos = 0, xChunkStart = 0, zChunkStart = 0;
    public World world;
    public boolean includeSmallOre = false, finished = false, includeBedRockOre = true;
    public List<discoveredOres> discoveredOres = new ArrayList<>();
    public OreScanner(int range, int xPos, int yPos, int zPos, World world, boolean includeSmallOre) {
        this.range = range;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        this.world = world;
        this.timer = 0;
        this.includeSmallOre = includeSmallOre;
        if (world != null) xChunkPos = xChunkStart = world.getChunkFromBlockCoords(xPos, zPos).xPosition - range;
        if (world != null) zChunkPos = zChunkStart = world.getChunkFromBlockCoords(xPos, zPos).zPosition - range;
        includeBedRockOre = true;
    }

    public OreScanner(int range, int xPos, int yPos, int zPos, World world, boolean includeSmallOre, boolean includeBedRockOre) {
        this.range = range;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        this.world = world;
        this.timer = 0;
        this.includeSmallOre = includeSmallOre;
        if (world != null) xChunkPos = xChunkStart = world.getChunkFromBlockCoords(xPos, zPos).xPosition - range;
        if (world != null) zChunkPos = zChunkStart = world.getChunkFromBlockCoords(xPos, zPos).zPosition - range;
        this.includeBedRockOre = includeBedRockOre;
    }

    /**
     * Start or Continue a scanning process.
     *
     * @return is scanning finished.
     */
    public boolean startOrContinueScanOres() {
        if (finished) return true;
        if (timer != 0 && timer % yPos == 0) {
            xChunkPos++;
            if (xChunkPos > xChunkStart + range * 2) {
                xChunkPos = xChunkStart;
                zChunkPos++;
                if (zChunkPos > zChunkStart + range * 2) finished = true;
            }
        }
        scanOres();
        timer++;
        return false;
    }

    public void resetScanOres() {
        clear();
        timer = 0;
    }

    public boolean scanOres() {
        if (world == null) return false;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Block block = world.getBlock(xChunkPos * 16 + x, timer % yPos, zChunkPos * 16 + z);
                if (MD.TFC.mLoaded&&isTFCOre(block,xChunkPos * 16 + x,timer % yPos, zChunkPos * 16 + z)) {
                    addDiscoveredOres(getMaterialIDForTFCOre(block,xChunkPos * 16 + x,timer % yPos, zChunkPos * 16 + z), xChunkPos * 16 + x, timer % yPos, zChunkPos * 16 + z, 4);
                    continue;
                }
                if (includeBedRockOre && block.getUnlocalizedName().startsWith("gt.meta.ore.normal.bedrock")) {
                    addDiscoveredOres(block.getDamageValue(world, xChunkPos * 16 + x, timer % yPos, zChunkPos * 16 + z), xChunkPos * 16 + x, timer % yPos, zChunkPos * 16 + z, 2);
                    continue;
                }
                if (includeBedRockOre && block.getUnlocalizedName().startsWith("gt.meta.ore.small.bedrock")) {
                    addDiscoveredOres(block.getDamageValue(world, xChunkPos * 16 + x, timer % yPos, zChunkPos * 16 + z), xChunkPos * 16 + x, timer % yPos, zChunkPos * 16 + z, 3);
                    continue;
                }
                if (block.getUnlocalizedName().startsWith("gt.meta.ore.normal")) {
                    addDiscoveredOres(block.getDamageValue(world, xChunkPos * 16 + x, timer % yPos, zChunkPos * 16 + z), xChunkPos * 16 + x, timer % yPos, zChunkPos * 16 + z, 0);
                    continue;
                }
                if (includeSmallOre && block.getUnlocalizedName().startsWith("gt.meta.ore.small")) {
                    addDiscoveredOres(block.getDamageValue(world, xChunkPos * 16 + x, timer % yPos, zChunkPos * 16 + z), xChunkPos * 16 + x, timer % yPos, zChunkPos * 16 + z, 1);
                    continue;
                }
            }
        }
        return true;
    }

    /**oreType: 0=normal,1=small,2=bedrock normal,3=bedrock small,4=TFCOre**/
    public boolean addDiscoveredOres(int materialID, int posX, int posY, int posZ, int oreType) {
        if (oreType < 0 || oreType > 2 || discoveredOres.stream().anyMatch(ore -> ore.posX == posX && ore.posY == posY && ore.posZ == posZ)) return false;
        discoveredOres.add(new discoveredOres(materialID, posX, posY, posZ, (short) oreType));
        return true;
        //FxRenderBlockOutline.addBlockOutlineToRender(new ChunkCoordinates(posX,posY,posZ), UT.Code.getRGBInt(OreDictMaterial.get(materialID).mRGBaSolid),1);
    }

    public List<discoveredOres> getDiscoveredOres() {
        return discoveredOres;
    }

    public void clear() {
        discoveredOres.clear();
    }

    public void rendOres() {
        discoveredOres.forEach(ore -> FxRenderBlockOutline.addBlockOutlineToRender(new ChunkCoordinates(ore.posX, ore.posY, ore.posZ), UT.Code.getRGBInt(OreDictMaterial.get(ore.materialID).mRGBaSolid), 2));
    }

    public boolean isTFCOre(Block block, int x, int y, int z) {
        return block!=null&&(block == TFCBlocks.ore || block == TFCBlocks.ore2 || block == TFCBlocks.ore3) && world.getTileEntity(x, y, z) instanceof TEOre;
    }
    public int getMaterialIDForTFCOre(Block block, int x, int y, int z) {
        TEOre te = (TEOre) world.getTileEntity(x, y, z);
        int meta=world.getBlockMetadata(x, y, z);
        if (block == TFCBlocks.ore)  meta = ((BlockOre) block).getOreGrade(te, meta);
        if (block == TFCBlocks.ore2) meta = meta + Global.ORE_METAL.length;
        if (block == TFCBlocks.ore3) meta = meta + Global.ORE_METAL.length + Global.ORE_MINERAL.length;
        switch (meta) {
            case 35:
            case 49:
            case 0: return MT.Cu.mID;
            case 36:
            case 50:
            case 1: return MT.Au.mID;
            case 37:
            case 51:
            case 2: return MT.Pt.mID;
            case 38:
            case 52:
            case 3: return MT.Fe2O3.mID;
            case 39:
            case 53:
            case 4: return MT.Ag.mID;
            case 40:
            case 54:
            case 5: return MT.OREMATS.Cassiterite.mID;
            case 41:
            case 55:
            case 6: return MT.OREMATS.Galena.mID;
            case 42:
            case 56:
            case 7: return MT.Bi.mID;
            case 43:
            case 57:
            case 8: return MT.OREMATS.Garnierite.mID;
            case 44:
            case 58:
            case 9: return MT.OREMATS.Malachite.mID;
            case 45:
            case 59:
            case 10:return MT.OREMATS.Magnetite.mID;
            case 46:
            case 60:
            case 11:return MT.OREMATS.YellowLimonite.mID;
            case 47:
            case 61:
            case 12:return MT.OREMATS.Sphalerite.mID;
            case 48:
            case 62:
            case 13:return MT.OREMATS.Tetrahedrite.mID;
            case 14:
            case 15:return MT.Coal.mID;
            case 16:return MT.Kaolinite.mID;
            case 17:return MT.Gypsum.mID;
            case 18:return MT.OREMATS.Trona.mID;
            case 19:return MT.OREMATS.Chromite.mID;
            case 20:return MT.Graphite.mID;
            case 21:return MT.Diamond.mID;
            case 22:return MT.PetrifiedWood.mID;
            case 23:return MT.S.mID;
            case 24:return MT.OREMATS.Stibnite.mID;
            case 25:return MT.MnO2.mID;
            case 26:return MT.OREMATS.Uraninite.mID;
            case 27:return MT.OREMATS.Cinnabar.mID;
            case 28:return MT.Cryolite.mID;
            case 29:return MT.Niter.mID;
            case 30:return MT.OREMATS.Malachite.mID;
            case 31:return MT.KCl.mID;
            case 32:return MT.OREMATS.Borax.mID;
            case 33:return MT.Olivine.mID;
            case 34:return MT.Lapis.mID;
            default:return 0;
        }
    }
    /**oreType: 0=normal,1=small,2=bedrock normal,3=bedrock small,4=TFCOre**/
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
}
