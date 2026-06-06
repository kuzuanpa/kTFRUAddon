package cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.types;

import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransferable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.TransferableDescriber;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransferablePack implements ITransferable {

    List<ITransferable> items= new ArrayList<>();

    public TransferablePack(ITransferable... items){
        this.items = Arrays.asList(items);
    }

    public TransferablePack(List<ITransferable> items){
        this.items.addAll(items);
    }
    @Override
    public ITransferable saveTo(NBTTagCompound nbt) {
        NBTTagList list = new NBTTagList();
        items.forEach(i-> list.appendTag(ITransferable.save(i)));
        nbt.setTag("content", list);
        return this;
    }

    @Override
    public ITransferable loadFrom(NBTTagCompound nbt) {
        items.clear();
        NBTTagList list = nbt.getTagList("content", 10);
        for (int i = 0; i < list.tagCount(); i++) {
            items.add(ITransferable.load(list.getCompoundTagAt(i)));
        }
        return this;
    }

    @Override
    public int typeID() {
        return -2;
    }

    @Override
    public TransferableDescriber describe() {
        return new TransferableDescriber(new ItemStack(Items.book));
    }
}
