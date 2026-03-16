package cn.kuzuanpa.ktfruaddon.DreamPlanner.api.pool;

import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransmittable;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DreamItemPoolManager {
    private static final DreamItemPoolManager INSTANCE = new DreamItemPoolManager();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private final List<ITransmittable> pool = new CopyOnWriteArrayList<>();

    private File dataFile;
    private File backupDir;

    public static DreamItemPoolManager getInstance() { return INSTANCE; }

    public void init(File worldSaveDir) {
        File baseDir = new File(worldSaveDir, "dream_pool");
        if (!baseDir.exists()) baseDir.mkdirs();
        this.dataFile = new File(baseDir, "pool_data.dat");
        this.backupDir = new File(baseDir, "backups");
        if (!backupDir.exists()) backupDir.mkdirs();
        loadData();
    }
    // --- 存取接口 ---
    public void addTransmittable(ITransmittable trans) {
        lock.writeLock().lock();
        try {
            // 这里可以根据业务逻辑查找 pool 中是否有可合并的相同对象
            pool.add(trans);
        } finally {
            lock.writeLock().unlock();
        }
    }

    // --- 数据持久化 ---
    public void saveData(File dir) {
        lock.readLock().lock();
        NBTTagCompound root = new NBTTagCompound();
        NBTTagList list = new NBTTagList();

        try {
            for (ITransmittable tr : pool) {
                NBTTagCompound data = tr.save();
                list.appendTag(data);
            }
            root.setTag("PoolList", list);

            try (FileOutputStream fos = new FileOutputStream(dir)) {
                CompressedStreamTools.writeCompressed(root, fos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void loadData() {
        if (!dataFile.exists()) return;
        lock.writeLock().lock();
        try {
            NBTTagCompound root = CompressedStreamTools.readCompressed(new FileInputStream(dataFile));
            NBTTagList list = root.getTagList("PoolList", 10);

            pool.clear();
            for (int i = 0; i < list.tagCount(); i++) {
                ITransmittable tr = ITransmittable.load(list.getCompoundTagAt(i));
                if (tr != null) pool.add(tr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

    // --- 全量备份逻辑 ---
    public void performBackup() {
        lock.readLock().lock();
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File backupFile = new File(backupDir, "backup_" + timestamp + ".dat");

            saveData(backupFile);

        } finally {
            lock.readLock().unlock();
        }
    }
}
