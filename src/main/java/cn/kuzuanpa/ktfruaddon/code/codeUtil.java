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

public class codeUtil {
    /**Turning CodeChicken BlockCoord to Minecraft ChunkCoordinates **/
    public static ChunkCoordinates CCCoord2MCCoord(BlockCoord coord){
        return new ChunkCoordinates(coord.x,coord.y,coord.z);
    }
    /**Turning Minecraft ChunkCoordinates to CodeChicken BlockCoord **/
    public static BlockCoord MCCoord2CCCoord(ChunkCoordinates coord){
        return new BlockCoord(coord.posX,coord.posY,coord.posZ);
    }

}
