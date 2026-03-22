package cn.kuzuanpa.ktfruaddon.DreamPlanner.api.pool;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.WorldEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DreamItemPoolManager {
    private static final DreamItemPoolManager INSTANCE = new DreamItemPoolManager();

    private final Map<UUID, DreamItemPool> pools = new HashMap<>();

    private File baseDir;
    private File backupDir;

    public static DreamItemPoolManager getInstance() { return INSTANCE; }

    public void init(File worldSaveDir) {
        baseDir = new File(DimensionManager.getCurrentSaveRootDirectory(), "DreamPlanner/ItemPools");
        if (!baseDir.exists()) baseDir.mkdirs();
        backupDir = new File(DimensionManager.getCurrentSaveRootDirectory(), "DreamPlanner/ItemPoolsBackup");
        if (!backupDir.exists()) backupDir.mkdirs();
    }
    @SubscribeEvent
    public void onServerSave(WorldEvent.Save event) {
        pools.forEach((uuid,pool) -> {
            File aTargetFile = new File(DimensionManager.getCurrentSaveRootDirectory() + "DreamPlanner/ItemPools", uuid.toString());

            if (!aTargetFile.exists()) {try {aTargetFile.createNewFile();} catch (Throwable e) {e.printStackTrace();}}

            pool.save(aTargetFile);
        });

    }

    @SubscribeEvent
    public void onServerLoad(WorldEvent.Load event) {
        try (Stream<Path> st = Files.list(Paths.get(DimensionManager.getCurrentSaveRootDirectory() + "DreamPlanner/ItemPools"))){
            st.forEach(file ->{
                DreamItemPool pool = DreamItemPool.load(file.toFile());
                pools.put(pool.uuid, pool);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void performBackup() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File backupFile = new File(backupDir, "backup_" + timestamp + ".dat");
        Path workDir = Paths.get(DimensionManager.getCurrentSaveRootDirectory() + "DreamPlanner/ItemPools");
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(backupFile.toPath()))) {
            Files.list(workDir).forEach(path -> {
                String entryName = workDir.relativize(path).toString().replace('\\', '/');

                ZipEntry zipEntry = new ZipEntry(entryName);
                try {
                    zos.putNextEntry(zipEntry);
                    Files.copy(path, zos);
                    zos.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
