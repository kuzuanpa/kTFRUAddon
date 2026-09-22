package cn.kuzuanpa.ktfruaddon.api.tile.room;

import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Server-side room index. Rooms are registered only in the chunk buckets their
 * bounding boxes cover, never in an all-coordinate membership map.
 */
public final class RoomManager {
    private static final Map<World, RoomWorldIndex> WORLD_INDEXES = new WeakHashMap<World, RoomWorldIndex>();

    private RoomManager() {
    }

    public static synchronized boolean update(IRoomController controller, RoomContext context) {
        if (controller == null || context == null || context.getRegion() == null) return false;
        World world = controller.getWorld();
        if (world == null) return false;
        RoomWorldIndex index = WORLD_INDEXES.get(world);
        if (index == null) {
            index = new RoomWorldIndex();
            WORLD_INDEXES.put(world, index);
        }
        return index.put(context);
    }

    public static synchronized void unregister(IRoomController controller) {
        if (controller == null) return;
        unregister(controller.getWorld(), controller.getX(), controller.getY(), controller.getZ());
    }

    public static synchronized void unregister(World world, int x, int y, int z) {
        if (world == null) return;
        RoomWorldIndex index = WORLD_INDEXES.get(world);
        if (index != null) index.remove(positionKey(x, y, z));
    }

    public static synchronized RoomContext getRoomAt(World world, int x, int y, int z) {
        if (world == null) return null;
        RoomWorldIndex index = WORLD_INDEXES.get(world);
        return index == null ? null : index.get(x, y, z);
    }

    public static boolean hasAll(World world, int x, int y, int z, long requiredCapabilities) {
        RoomContext room = getRoomAt(world, x, y, z);
        return room != null && room.isActive() && room.hasAll(requiredCapabilities);
    }

    private static long positionKey(int x, int y, int z) {
        return ((long)(x & 0x1FFFFF) << 42) | ((long)(y & 0x1FFFFF) << 21) | (long)(z & 0x1FFFFF);
    }

    private static long chunkKey(int x, int y, int z) {
        return ((long)((x >> 4) & 0x1FFFFF) << 42) | ((long)((y >> 4) & 0x1FFFFF) << 21) | (long)((z >> 4) & 0x1FFFFF);
    }

    private static final class RoomWorldIndex {
        private final Map<Long, List<RoomContext>> buckets = new HashMap<Long, List<RoomContext>>();
        private final Map<Long, RoomContext> controllers = new HashMap<Long, RoomContext>();

        boolean put(RoomContext context) {
            long controllerKey = positionKey(context.getControllerX(), context.getControllerY(), context.getControllerZ());
            remove(controllerKey);

            for (RoomContext existing : candidates(context.getRegion().getBounds())) {
                if (existing != context && existing.getRegion() != null && existing.getRegion().intersects(context.getRegion())) {
                    return false;
                }
            }

            for (Long bucketKey : bucketKeys(context.getRegion().getBounds())) {
                List<RoomContext> bucket = buckets.get(bucketKey);
                if (bucket == null) {
                    bucket = new ArrayList<RoomContext>();
                    buckets.put(bucketKey, bucket);
                }
                bucket.add(context);
            }
            controllers.put(controllerKey, context);
            return true;
        }

        void remove(long controllerKey) {
            RoomContext old = controllers.remove(controllerKey);
            if (old == null || old.getRegion() == null) return;
            for (Long bucketKey : bucketKeys(old.getRegion().getBounds())) {
                List<RoomContext> bucket = buckets.get(bucketKey);
                if (bucket == null) continue;
                bucket.remove(old);
                if (bucket.isEmpty()) buckets.remove(bucketKey);
            }
        }

        RoomContext get(int x, int y, int z) {
            List<RoomContext> bucket = buckets.get(chunkKey(x, y, z));
            if (bucket == null) return null;
            for (RoomContext context : bucket) {
                if (context.isActive() && context.contains(x, y, z)) return context;
            }
            return null;
        }

        private List<RoomContext> candidates(BoundingBox bounds) {
            List<RoomContext> result = new ArrayList<RoomContext>();
            Set<RoomContext> seen = new LinkedHashSet<RoomContext>();
            for (Long key : bucketKeys(bounds)) {
                List<RoomContext> bucket = buckets.get(key);
                if (bucket == null) continue;
                for (RoomContext context : bucket) {
                    if (seen.add(context)) result.add(context);
                }
            }
            return result;
        }
    }

    private static Set<Long> bucketKeys(BoundingBox bounds) {
        Set<Long> keys = new LinkedHashSet<Long>();
        int minX = ((int)Math.floor(bounds.minX)) >> 4;
        int maxX = ((int)Math.floor(bounds.maxX)) >> 4;
        int minY = ((int)Math.floor(bounds.minY)) >> 4;
        int maxY = ((int)Math.floor(bounds.maxY)) >> 4;
        int minZ = ((int)Math.floor(bounds.minZ)) >> 4;
        int maxZ = ((int)Math.floor(bounds.maxZ)) >> 4;
        for (int x = minX; x <= maxX; x++) for (int y = minY; y <= maxY; y++) for (int z = minZ; z <= maxZ; z++) {
            keys.add(chunkKey(x << 4, y << 4, z << 4));
        }
        return keys;
    }
}
