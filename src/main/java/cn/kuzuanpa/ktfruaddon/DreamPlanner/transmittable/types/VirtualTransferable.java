package cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.types;

import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransferable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.TransferableDescriber;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class VirtualTransferable implements ITransferable {
    ITransferable real;
    int virtualType= 0;
    public VirtualTransferable(ITransferable realItem){
        this.real = realItem;
    }
    @Override
    public ITransferable saveTo(NBTTagCompound nbt) {
        nbt.setTag("c", ITransferable.save(real));
        nbt.setInteger("t", virtualType);
        return this;
    }

    @Override
    public ITransferable loadFrom(NBTTagCompound nbt) {
        real = ITransferable.load(nbt.getCompoundTag("c"));
        virtualType = nbt.getInteger("t");
        return this;
    }

    @Override
    public int typeID() {
        return 0;
    }

    @Override
    public TransferableDescriber describe() {
        return null;
    }
}
