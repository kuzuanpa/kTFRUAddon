package cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable;

import net.minecraft.nbt.NBTTagCompound;

public interface ITransferable {
    TransferableStack make(long amount);
    static NBTTagCompound save(ITransferable transferable){
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("typeID", transferable.typeID());
        transferable.saveTo(nbt);
        return nbt;
    };
    ITransferable saveTo(NBTTagCompound nbt);
    ITransferable loadFrom(NBTTagCompound nbt);
    static ITransferable load(NBTTagCompound nbt){
        return TransferableManager.getDefault(nbt.getInteger("typeID")).loadFrom(nbt);
    }
    int typeID();
    TransferableDescriber describe();
}
