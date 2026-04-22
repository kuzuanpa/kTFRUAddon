package cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.types;

import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransferable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.TransferableDescriber;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;

public class MessagePacket implements ITransferable {
    int packetType;
    ChunkCoordinates sourcePos;
    @Override
    public ITransferable saveTo(NBTTagCompound nbt) {
        nbt.setInteger("t", packetType);
        nbt.setInteger("x",sourcePos.posX);
        nbt.setInteger("y",sourcePos.posY);
        nbt.setInteger("z",sourcePos.posZ);
        return this;
    }

    @Override
    public ITransferable loadFrom(NBTTagCompound nbt) {
        packetType = nbt.getInteger("t");
        sourcePos = new ChunkCoordinates(nbt.getInteger("x"),nbt.getInteger("y"),nbt.getInteger("z"));
        return this;
    }

    @Override
    public int typeID() {
        return -3;
    }

    @Override
    public TransferableDescriber describe() {
        return new TransferableDescriber(new ItemStack(Items.painting));
    }
}
