package cn.kuzuanpa.ktfruaddon.api.tile.room;

public final class RoomContext {
    private final IRoomRegion region;
    private final long capabilities;
    private final int dimension;
    private final int controllerX, controllerY, controllerZ;
    private final boolean active;

    public RoomContext(IRoomRegion region, long capabilities, int dimension,
                       int controllerX, int controllerY, int controllerZ, boolean active) {
        this.region = region;
        this.capabilities = capabilities;
        this.dimension = dimension;
        this.controllerX = controllerX;
        this.controllerY = controllerY;
        this.controllerZ = controllerZ;
        this.active = active;
    }

    public IRoomRegion getRegion() {
        return region;
    }

    public long getCapabilities() {
        return capabilities;
    }

    public int getDimension() {
        return dimension;
    }

    public int getControllerX() {
        return controllerX;
    }

    public int getControllerY() {
        return controllerY;
    }

    public int getControllerZ() {
        return controllerZ;
    }

    public boolean isActive() {
        return active;
    }

    public boolean contains(int x, int y, int z) {
        return region != null && region.contains(x, y, z);
    }

    public boolean hasAll(long requiredCapabilities) {
        return RoomCapability.hasAll(capabilities, requiredCapabilities);
    }
}
