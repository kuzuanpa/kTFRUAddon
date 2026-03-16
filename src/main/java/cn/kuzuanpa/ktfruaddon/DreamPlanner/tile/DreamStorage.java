package cn.kuzuanpa.ktfruaddon.DreamPlanner.tile;

import cn.kuzuanpa.ktfruaddon.DreamPlanner.api.pool.DreamItemPoolManager;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransmittable;
import net.minecraft.tileentity.TileEntity;

import java.io.File;

public class DreamStorage extends TileEntity {

    // 当外部设备（如管道）向此 TE 输入物品/流体时
    public void handleInput(ITransmittable input) {
        // 核心池的操作是线程安全的，可以在主线程或异步线程调用
        DreamItemPoolManager.getInstance().addTransmittable(input);
        this.markDirty();
    }

    @Override
    public void updateEntity() {
        // 仅在服务端逻辑运行
        if (!worldObj.isRemote && worldObj.getTotalWorldTime() % 1200 == 0) { // 每分钟保存一次
            DreamItemPoolManager.getInstance().saveData(new File("."));
        }
    }
}
