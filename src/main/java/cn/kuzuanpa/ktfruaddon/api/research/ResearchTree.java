/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

package cn.kuzuanpa.ktfruaddon.api.research;

import cn.kuzuanpa.ktfruaddon.api.research.task.IResearchTask;
import cpw.mods.fml.common.FMLLog;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.util.UT;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import org.apache.logging.log4j.Level;
import zmaster587.advancedRocketry.api.AdvancedRocketryItems;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResearchTree {

    public Map<String, ResearchItem> allResearch = new HashMap<>();

    public byte id;
    public void addResearchItem(ResearchItem item) {
        allResearch.put(item.getId(), item);
    }

    public ResearchTree(byte id){
        this.id = id;
        putTestValues();
    }
    public void putTestValues(){

        ResearchItem a = new ResearchItem(this, "芯片基础", "在经过了一系列磨难后，你终于在群峦星获得了安身之地。现在，你需要根据你的记忆和想象力，找回地球上最实用的工具：芯片", AdvancedRocketryItems.itemIC, 0).setPos(60,130);
        ResearchItem b = new ResearchItem(this, "投影", "你需要探索光学成像的原理，设计基础投影设备，来将你对机器的构想投射到世界中", AdvancedRocketryItems.itemSatellitePrimaryFunction, 0).setPos(60,20);
        ResearchItem c = new ResearchItem(this, "芯片理论", "研究半导体特性，了解其在芯片制造中的关键作用", Items.paper, 0).setPos(180,10);
        ResearchItem d = new ResearchItem(this, "结晶器", "分析晶体生长过程，思考如何获得整齐排布的分子晶体结构", OP.bouleGt.mat(MT.Si,0).getItem(), MT.Si.mID).setPos(180,130);
        ResearchItem e = new ResearchItem(this, "半导体电路设计", "是时候设计一个基本的计算器了，它将你从繁重的笔算心算中解放出来", Items.paper, 0).setPos(320,130);
        ResearchItem f = new ResearchItem(this, "进阶电路设计", "利用计算器进一步改进电路，你认为你离真正的发电机不远了", Items.paper, 0).setPos(340,10);
        a.addPrerequisite(rootItem);
        b.addPrerequisite(rootItem);


        c.addPrerequisite(a);
        d.addPrerequisite(a);
        d.addPrerequisite(b);

        e.addPrerequisite(d);
        f.addPrerequisite(e);

        a.isUnlocked =true;
        b.isUnlocked =true;
        c.isUnlocked =true;

        a.tasks.add(new ResearchItem.TestTask(Items.iron_ingot));
        b.tasks.add(new ResearchItem.TestTask(Items.glowstone_dust));
        b.tasks.add(new ResearchItem.TestTask(Items.glass_bottle));
        c.tasks.add(new ResearchItem.TestTask(Items.glass_bottle));
        d.tasks.add(new ResearchItem.TestTask(Items.water_bucket));
        e.tasks.add(new ResearchItem.TestTask(Items.paper));
    }
    public ResearchItem rootItem = new ResearchItem(this,"计算学","算力的提升是万物的基础");

    private void removeChildRecursively(ResearchItem current, ResearchItem target) {
        List<ResearchItem> children = current.getPrerequisites();
        children.remove(target);
        for (ResearchItem child : new ArrayList<>(children)) {
            removeChildRecursively(child, target);
        }
    }
    public NBTTagCompound save(){
        NBTTagCompound tag = new NBTTagCompound();
        allResearch.forEach(((name, item) -> {
            //ONLY save task progress when research not completed
            if(item.getProgress() == 0)return;
            if(item.isCompleted){
                tag.setBoolean(name+".c", true);
                return;
            }
            NBTTagCompound list = new NBTTagCompound();
            item.tasks.forEach(task-> UT.NBT.setNumber(list, task.getIdentifier(), task.getProgress()));
            tag.setTag(name, list);
        }));
        return tag;
    }
    public void load(NBTTagCompound tag){
        allResearch.forEach(((name, item) -> {
            if(tag.hasKey(name+".c"))item.isCompleted = true;
            else if(tag.hasKey(name)){
                NBTTagCompound list = tag.getCompoundTag(name);
                item.tasks.forEach(task-> task.setProgress(list.getLong(task.getIdentifier())));
            }
        }));
    }
    public byte[] saveToArray() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeByte(id);
            for (Map.Entry<String, ResearchItem> entry : allResearch.entrySet()) {
                String name = entry.getKey();
                ResearchItem item = entry.getValue();
                //ONLY save task progress when research not completed
                if (!item.isUnlocked || item.getProgress() == 0) continue;
                dos.writeUTF(name);
                dos.writeShort(item.isCompleted ? -1 : item.tasks.size());
                if (item.isCompleted) continue;

                for (IResearchTask task : item.tasks) {
                    dos.writeUTF(task.getIdentifier());
                    long progress = task.getProgress();
                    if (progress > Integer.MAX_VALUE || progress < Integer.MIN_VALUE) {
                        dos.writeByte(3);
                        dos.writeLong(progress);
                    } else if (progress > Short.MAX_VALUE || progress < Short.MIN_VALUE) {
                        dos.writeByte(2);
                        dos.writeInt((int) progress);
                    } else {
                        dos.writeByte(1);
                        dos.writeShort((short) progress);
                    }
                }
            }
            dos.flush();
            return bos.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
            return new byte[0];
        }
    }
    public void loadFromArray(byte[] bytes) {
        try {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bis);
        id = dis.readByte();
        while (dis.available() > 0) {
            String name = dis.readUTF();
            ResearchItem item = allResearch.get(name);
            if(item == null)item = new ResearchItem(null, "","");
            short taskCount = dis.readShort();
            if (taskCount == -1) {
                item.isCompleted = true;
                continue;
            }
            for (int i = 0; i < taskCount; i++) {
                String taskName = dis.readUTF();
                IResearchTask task = item.tasks.stream().filter(t -> t.getIdentifier().equals(taskName)).findFirst().orElse(skippedDummyTask);
                byte progressType = dis.readByte();
                switch (progressType) {
                    case 1: task.setProgress(dis.readShort());break;
                    case 2: task.setProgress(dis.readInt());break;
                    case 3: task.setProgress(dis.readLong());break;
                    default: FMLLog.log(Level.ERROR, "Unknown progress type: " + progressType+", packet may corrupted");break;
                }
            }
        }
    }catch (IOException e){
        e.printStackTrace();
    }
    }
    public static DummyTask skippedDummyTask = new DummyTask();
    public static class DummyTask implements IResearchTask{
        public DummyTask(){}
        @Override public long getMaxProgress() {return 0;}
        @Override public long getProgress() {return 0;}

        @Override
        public boolean tryPromoteProgress(Object consumed) {
            return false;
        }

        @Override
        public void setProgress(long progress) {

        }

        @Override public IIcon getIcon() {return null;}
        @Override public String getIdentifier() {return "";}
    }
}
