package cn.kuzuanpa.ktfruaddon.DreamPlanner.event;

import cn.kuzuanpa.ktfruaddon.DreamPlanner.api.pool.DreamItemPoolManager;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraftforge.event.world.WorldEvent;

import java.io.File;

public class DreamPoolEventHandler {

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (!event.world.isRemote && event.world.provider.dimensionId == 0) {
            File worldDir = event.world.getSaveHandler().getWorldDirectory();
            DreamItemPoolManager.getInstance().init(worldDir);
        }
    }
    private int backupTimer = 0;

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            backupTimer++;
            if (backupTimer >= 144000) {
                backupTimer = 0;
                performFullBackup();
            }
        }
    }

    private void performFullBackup() {
        new Thread(() -> {
            //System.out.println("[DreamItemPool] Starting full backup...");
            DreamItemPoolManager.getInstance().performBackup();
        }).start();
    }
}
