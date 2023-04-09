/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.block.TileEntity.multiblock.util;

public class utilLimitedOutputTarget {
    //Storage matches of Target and Fluid

    public static class matchT_F {
        public final String targetName;
        public final String[] fluidNames;
        public matchT_F(String targetName, String[] fluidNames){
            this.targetName=targetName;
            this.fluidNames=fluidNames;
        }
    }
}


