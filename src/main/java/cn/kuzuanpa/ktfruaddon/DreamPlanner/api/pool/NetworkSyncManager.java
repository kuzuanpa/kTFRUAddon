package cn.kuzuanpa.ktfruaddon.DreamPlanner.api.pool;

import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransferable;
import net.minecraft.server.MinecraftServer;

import java.util.*;
import java.util.stream.Collectors;

public class NetworkSyncManager {
    // 玩家上次同步的 Tick 时间戳
    protected Map<UUID, Integer> playerLastSyncTick = new HashMap<>();
    protected int lastCleanTime;

    private Map<ITransferable, Integer> changeLog = new HashMap<>();

    // 物品入网/出网时调用
    public void onChange(ITransferable id, int newAmount) {
        changeLog.put(id, MinecraftServer.getServer().getTickCounter());
    }

    // 获取增量更新列表
    public List<ITransferable> getChangesSince(long lastTick) {
        return changeLog.entrySet().stream()
                .filter(entry-> entry.getValue() > lastTick)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public void clearOldRecords(){
        int time = MinecraftServer.getServer().getTickCounter();
        if (lastCleanTime > time + 120) return;
        int oldestTime = Integer.MAX_VALUE;
        for (Map.Entry<UUID, Integer> entry : playerLastSyncTick.entrySet()) {
            Integer t = entry.getValue();
            oldestTime = Math.min(oldestTime, t);
        }
        int finalOldestTime = oldestTime;
        changeLog.entrySet().removeIf(entry -> entry.getValue() < finalOldestTime);

    }
}