package cn.kuzuanpa.ktfruaddon.api.tile.room;

import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;

/**
 * Describes a room volume without materializing every block position.
 */
public interface IRoomRegion {
    BoundingBox getBounds();

    boolean contains(int x, int y, int z);

    boolean intersects(IRoomRegion other);
}
