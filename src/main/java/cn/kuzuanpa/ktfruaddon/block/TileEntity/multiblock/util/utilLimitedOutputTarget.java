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

import gregapi.tileentity.base.TileEntityBase04MultiTileEntities;
import net.minecraftforge.fluids.Fluid;

import java.util.Arrays;

public class utilLimitedOutputTarget {
    //Storage matches of Source,Target and Fluid
    private static class matchS_T_F {
        private final String sourceName;
        private final matchT_F[] targetMatches;
        matchS_T_F(String sourceName, matchT_F[] targetMatches){
            this.sourceName=sourceName;
            this.targetMatches=targetMatches;
        }
    }
    private static class matchT_F {
        private final String targetName;
        private final String[] fluidNames;
        matchT_F(String targetName, String[] fluidNames){
            this.targetName=targetName;
            this.fluidNames=fluidNames;
        }
    }

    private static final String[] availSource = {"ktfru.multitileentity.multiblock"};

    private static final matchS_T_F[] availMatches = {
            new matchS_T_F("sourceA",new matchT_F[]{
                    new matchT_F("target",new String[]{"fluid","fluidA"}),
                    new matchT_F("targetB",new String[]{"fluidB","fluidAB"})
            }),
            new matchS_T_F("ktfru.multitileentity.multiblock",
                    new matchT_F[]{
                    new matchT_F("gt.multitileentity",new String[]{"water","fluidA"}),
                    new matchT_F("ktfru.multitileentity",new String[]{"fluidB","fluidAB"})
            }),
    };
    private static boolean result = false;
private static boolean isMatched(matchS_T_F match, TileEntityBase04MultiTileEntities OutputSource, TileEntityBase04MultiTileEntities OutputTarget, Fluid fluidToOutput) {
    if (!OutputSource.getTileEntityName().contains(match.sourceName)) return false;
    Arrays.stream(match.targetMatches).forEach(matchT_F -> result = OutputTarget.getTileEntityName().contains(matchT_F.targetName) && Arrays.stream(matchT_F.fluidNames).anyMatch(fluidName -> fluidToOutput.getUnlocalizedName().contains(fluidName)));
    return result;
}
    public static boolean canOutputFluid(TileEntityBase04MultiTileEntities Source, TileEntityBase04MultiTileEntities Target, Fluid fluidToOutput) {
        if (Arrays.stream(availSource).noneMatch(AvailSourceName -> Source.getTileEntityName().contains(AvailSourceName))) return true;
        if (Target==null||fluidToOutput==null) return false;
        return Arrays.stream(availMatches).anyMatch(match -> isMatched(match,Source,Target,fluidToOutput));
    }
}


