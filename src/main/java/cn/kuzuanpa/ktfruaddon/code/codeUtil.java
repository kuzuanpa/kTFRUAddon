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
import gregapi.code.ITagDataContainer;
import gregapi.code.TagData;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;


public class codeUtil {
    /**Turning CodeChicken BlockCoord to Minecraft ChunkCoordinates **/
    public static ChunkCoordinates CCCoord2MCCoord(BlockCoord coord){
        return new ChunkCoordinates(coord.x,coord.y,coord.z);
    }
    /**Turning Minecraft ChunkCoordinates to CodeChicken BlockCoord **/
    public static BlockCoord MCCoord2CCCoord(ChunkCoordinates coord){
        return new BlockCoord(coord.posX,coord.posY,coord.posZ);
    }
    public static String WXYZAToString(int world, int x, int y, int z) {
        return world + ":" + x + ":" + y + ":" + z;
    }
    public static String WCoordToString(int world, BlockCoord coord) {
        return world + ":" + coord.x + ":" + coord.y + ":" + coord.z;
    }
    /**range: 0-1800**/
    public static int getColorFromTemp(short temp) {
            //Define
            float color[] = new float[3];
            float temperature = ((temp * .0477f) + 1f); //0 -> 10 100 -> 57.7

            //Find red
            if(temperature < 66){
                color[0] = 0.2f+ (float)Math.pow(temperature,2)/64F;
                color[0] = MathHelper.clamp_float(color[0], 0f, 1f);
            }
            else {
                color[0] = temperature - 60;
                color[0] = 329.69f * (float)Math.pow(color[0], -0.1332f);
                color[0] = MathHelper.clamp_float(color[0]/255f, 0f, 1f);

            }

            //Calc Green
            if(temperature < 66) {
                color[1] = temperature;
                color[1] = (float) (99.47f * Math.log(color[1]) - 161.1f);
            }
            else {
                color[1] = temperature - 60;
                color[1] = 388f * (float)Math.pow(color[1], -0.07551);

            }
            color[1] = MathHelper.clamp_float(color[1]/255f, 0f, 1f);


            //Calculate Blue
            if(temperature > 67)
                color[2] = 1f;
            else if(temperature <= 19){
                color[2] = 0f;
            }
            else {
                color[2] = temperature - 10;
                color[2] = (float) (138.51f * Math.log(color[2]) - 305.04f);
                color[2] = MathHelper.clamp_float(color[2]/255f, 0f, 1f);
            }

            return new Color(color[0], color[1], color[2]).hashCode();
        }

        public static List<TagData> getAllTagData(ITagDataContainer container){
        return TagData.TAGS.stream().filter(container::contains).collect(Collectors.toList());
        }

}
