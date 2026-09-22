package cn.kuzuanpa.ktfruaddon.api.tile.room;

import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;

public final class BoxRoomRegion implements IRoomRegion {
    private final int minX, minY, minZ, maxX, maxY, maxZ;
    private final BoundingBox bounds;

    public BoxRoomRegion(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = Math.min(minX, maxX);
        this.minY = Math.min(minY, maxY);
        this.minZ = Math.min(minZ, maxZ);
        this.maxX = Math.max(minX, maxX);
        this.maxY = Math.max(minY, maxY);
        this.maxZ = Math.max(minZ, maxZ);
        this.bounds = new BoundingBox(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }

    public BoxRoomRegion(BoundingBox aBounds) {
        this((int)Math.floor(aBounds.minX), (int)Math.floor(aBounds.minY), (int)Math.floor(aBounds.minZ),
                (int)Math.floor(aBounds.maxX), (int)Math.floor(aBounds.maxY), (int)Math.floor(aBounds.maxZ));
    }

    @Override
    public BoundingBox getBounds() {
        return bounds;
    }

    @Override
    public boolean contains(int x, int y, int z) {
        return minX <= x && x <= maxX && minY <= y && y <= maxY && minZ <= z && z <= maxZ;
    }

    @Override
    public boolean intersects(IRoomRegion other) {
        return other != null && bounds.intersects(other.getBounds());
    }
}
