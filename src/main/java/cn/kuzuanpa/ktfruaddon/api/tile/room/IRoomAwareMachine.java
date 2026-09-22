package cn.kuzuanpa.ktfruaddon.api.tile.room;

import net.minecraft.world.World;

public interface IRoomAwareMachine {
    World getWorld();

    int getX();

    int getY();

    int getZ();

    long getRequiredRoomCapabilities();

    default boolean isRoomEnvironmentValid() {
        return RoomManager.hasAll(getWorld(), getX(), getY(), getZ(), getRequiredRoomCapabilities());
    }
}
