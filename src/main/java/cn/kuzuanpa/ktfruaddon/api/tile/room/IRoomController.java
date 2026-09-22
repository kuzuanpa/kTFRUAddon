package cn.kuzuanpa.ktfruaddon.api.tile.room;

import net.minecraft.world.World;

public interface IRoomController {
    World getWorld();

    int getX();

    int getY();

    int getZ();

    IRoomRegion getRoomRegion();

    long getProvidedCapabilities();

    boolean isRoomActive();

    RoomContext makeRoomContext();
}
