package cn.kuzuanpa.ktfruaddon.api.code;

import codechicken.lib.vec.BlockCoord;
import net.minecraft.util.ChunkCoordinates;

public class WorldPos {
    public int x;
    public int y;
    public int z;
    public int dim;

    public WorldPos(int x, int y, int z, int dim) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dim = dim;
    }
    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof WorldPos)) return false;

        WorldPos worldPos = (WorldPos) o;
        return x == worldPos.x && y == worldPos.y && z == worldPos.z && dim == worldPos.dim;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        result = 31 * result + dim;
        return result;
    }

    @Override
    public String toString() {
        return "Pos:(" + "x=" + x + ", y=" + y + ", z=" + z + ", dim=" + dim + ')';
    }

    public BlockCoord toBlockCoord(){
        return new BlockCoord(x,y,z);
    }
    public ChunkCoordinates toChunkCoord(){
        return new ChunkCoordinates(x,y,z);
    }
}
