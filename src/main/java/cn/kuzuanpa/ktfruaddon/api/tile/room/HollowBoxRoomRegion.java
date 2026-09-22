package cn.kuzuanpa.ktfruaddon.api.tile.room;

import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;

public final class HollowBoxRoomRegion implements IRoomRegion {
    private final BoundingBox bounds;
    private final BoxRoomRegion interior;

    public HollowBoxRoomRegion(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int wallThickness) {
        this.bounds = new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
        int innerMinX = Math.min(minX, maxX) + Math.max(0, wallThickness);
        int innerMinY = Math.min(minY, maxY) + Math.max(0, wallThickness);
        int innerMinZ = Math.min(minZ, maxZ) + Math.max(0, wallThickness);
        int innerMaxX = Math.max(minX, maxX) - Math.max(0, wallThickness);
        int innerMaxY = Math.max(minY, maxY) - Math.max(0, wallThickness);
        int innerMaxZ = Math.max(minZ, maxZ) - Math.max(0, wallThickness);
        this.interior = innerMinX <= innerMaxX && innerMinY <= innerMaxY && innerMinZ <= innerMaxZ
                ? new BoxRoomRegion(innerMinX, innerMinY, innerMinZ, innerMaxX, innerMaxY, innerMaxZ) : null;
    }

    @Override
    public BoundingBox getBounds() {
        return bounds;
    }

    public BoundingBox getInteriorBounds() {
        return interior == null ? null : interior.getBounds();
    }

    @Override
    public boolean contains(int x, int y, int z) {
        return interior != null && interior.contains(x, y, z);
    }

    @Override
    public boolean intersects(IRoomRegion other) {
        return interior != null && interior.intersects(other);
    }
}
