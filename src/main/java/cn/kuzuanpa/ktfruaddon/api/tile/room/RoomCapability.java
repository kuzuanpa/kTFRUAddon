package cn.kuzuanpa.ktfruaddon.api.tile.room;

public enum RoomCapability {
    MICROGRAVITY(1L),
    HIGH_RADIATION_SHIELD(1L << 1);

    private final long mask;

    RoomCapability(long mask) {
        this.mask = mask;
    }

    public long getMask() {
        return mask;
    }

    public static long mask(RoomCapability... capabilities) {
        long result = 0L;
        if (capabilities != null) for (RoomCapability capability : capabilities) {
            if (capability != null) result |= capability.mask;
        }
        return result;
    }

    public static boolean hasAll(long actual, long required) {
        return (actual & required) == required;
    }

    public static boolean has(long actual, RoomCapability capability) {
        return capability != null && (actual & capability.mask) == capability.mask;
    }
}
