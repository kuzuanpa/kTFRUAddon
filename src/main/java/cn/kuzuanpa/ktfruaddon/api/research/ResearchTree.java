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

import cn.kuzuanpa.ktfruaddon.api.network.PacketUUIDAssignedData;
import cn.kuzuanpa.ktfruaddon.api.research.task.IResearchTask;
import cpw.mods.fml.common.FMLLog;
import gregapi.util.UT;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.*;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.kNetworkHandler;

public class ResearchTree{
    public ResearchProject getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(ResearchProject project) {
        if(this.currentProject == project || !project.isUnlocked || project.isCompleted)return;
        needUpdate = true;
        this.currentProject = project;
    }

    public long lastUpdateTick = 0;
    public void onResearchProjectUpdated(ResearchProject project){
        needUpdate = true;
    }
    public interface IResearchTreeTemplate{
        ResearchTree applyTemplate(ResearchTree tree);
    }

    public void sendDataToViewerPlayers(){
        for (EntityPlayerMP portableViewerPlayer : portableViewerPlayers) {
            sendTreeData(portableViewerPlayer, this);
        }
    }

    public static Map<Byte,IResearchTreeTemplate> ResearchTreeTemplate = new HashMap<>();
    public static Map<UUID, ResearchTree> allTreeUUIDsClient = new HashMap<>();
    public static Map<UUID, ResearchTree> allTreeUUIDsServer = new HashMap<>();
    public UUID uuid = UUID.randomUUID();
    public List<EntityPlayerMP> portableViewerPlayers = new ArrayList<>();
    public Map<String, ResearchProject> allResearch = new HashMap<>();
    public boolean needUpdate = false;

    protected ResearchProject currentProject = null;
    public byte id;
    public void addResearchItem(ResearchProject item) {
        allResearch.put(item.getId(), item);
    }

    public ResearchTree(){

    }

    public void update(){
        lastUpdateTick = MinecraftServer.getServer().getTickCounter();
    }
    public ResearchProject rootItem;

    public boolean applyTemplate(byte id){
        this.id = id;
        if (ResearchTreeTemplate.get(id) == null)return false;
        ResearchTreeTemplate.get(id).applyTemplate(this);
        onCreated();
        return true;
    }
    public boolean createFromTemplate(byte id){
        applyTemplate(id);
        init();
        return true;
    }

    private void removeChildRecursively(ResearchProject current, ResearchProject target) {
        List<ResearchProject> children = current.getPrerequisites();
        children.remove(target);
        for (ResearchProject child : new ArrayList<>(children)) {
            removeChildRecursively(child, target);
        }
    }
    public void onCreated(){
        rootItem.isUnlocked = true;
        rootItem.isCompleted = true;
        rootItem.onCompleted();
    }
    public void init(){
        if(cpw.mods.fml.common.FMLCommonHandler.instance().getEffectiveSide().isServer()) allTreeUUIDsServer.put(uuid, this);
    }
    public void dispose(){

    }
    public NBTTagCompound save(){
        NBTTagCompound tag = new NBTTagCompound();
        tag.setByte("tempID", id);
        tag.setLong("UUIDup", uuid.getMostSignificantBits());
        tag.setLong("UUIDdown", uuid.getLeastSignificantBits());
        if(getCurrentProject() != null)tag.setString("currentProjectID", getCurrentProject().id);
        allResearch.forEach(((name, item) -> {
            //ONLY save task progress when research not completed
            if(item.isCompleted){
                tag.setBoolean(name+".c", true);
                return;
            }
            if(item.getProgress() == 0)return;
            NBTTagCompound list = new NBTTagCompound();
            item.tasks.forEach(task-> UT.NBT.setNumber(list, task.getIdentifier(), task.getProgress()));
            tag.setTag(name, list);
        }));
        return tag;
    }
    public void load(NBTTagCompound tag){
        id = tag.getByte("tempID");
        applyTemplate(id);
        uuid = new UUID(tag.getLong("UUIDup"), tag.getLong("UUIDdown"));
        if(tag.hasKey("currentProjectID")) setCurrentProject(allResearch.get(tag.getString("currentProjectID")));
        init();
        allResearch.forEach(((name, item) -> {
            if(tag.hasKey(name+".c")) item.onCompleted();
            else if(tag.hasKey(name)){
                NBTTagCompound list = tag.getCompoundTag(name);
                item.tasks.forEach(task-> task.setProgress(list.getLong(task.getIdentifier())));
            }
        }));
    }
    public byte[] saveToArray() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos)){
            dos.writeByte(id);
            dos.writeLong(uuid.getMostSignificantBits());
            dos.writeLong(uuid.getLeastSignificantBits());
            dos.writeBoolean(getCurrentProject() != null);
            if(getCurrentProject() != null)dos.writeUTF(getCurrentProject().id);
            for (Map.Entry<String, ResearchProject> entry : allResearch.entrySet()) {
                String name = entry.getKey();
                ResearchProject item = entry.getValue();
                //ONLY save task progress when research not completed
                if (!item.isUnlocked) continue;
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
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bis)){
        id = dis.readByte();
        UUID uuid = new UUID(dis.readLong(), dis.readLong());
        this.uuid = uuid;
        ResearchTree tree = ResearchTree.allTreeUUIDsClient.get(uuid);
        if(tree == null) {
            applyTemplate(id);
            ResearchTree.allTreeUUIDsClient.put(uuid, this);
        }

        if(dis.readBoolean()) setCurrentProject(allResearch.get(dis.readUTF()));
        while (dis.available() > 0) {
            String name = dis.readUTF();
            ResearchProject item = allResearch.get(name);
            if(item == null)item = new ResearchProject(null, "","", -1);
            item.isUnlocked = true;
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
        @Override public long getRequiredProgress() {return 0;}
        @Override public long getProgress() {return 0;}
        @Override
        public long tryPromoteProgress(Object consumed, boolean dryRun) {return 0;}
        @Override
        public void setProgress(long progress) {}
        @Override public IIcon getIcon() {return null;}
        @Override public String getIdentifier() {return "d";}
        @Override public String getDesc() {return "";}
    }

    public static void receiveUUIDAssignedData(UUID uuid, byte @Nullable [] data) {
        if(data == null)return;

        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             DataInputStream dis = new DataInputStream(bis)){
            byte packetType = dis.readByte();
            if(packetType == -3) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Research Tree Not update for a while, your research viewer may unloaded or broken."));
                return;
            }
            if(packetType == -2) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Research Tree Not Found, your research viewer may unloaded or broken."));
                return;
            }
            if(packetType == -1){
                byte[] treeData = new byte[data.length-1];
                System.arraycopy(data, 1, treeData, 0, data.length -1);

                ResearchTree tree = ResearchTree.allTreeUUIDsClient.get(uuid);
                if(tree == null) tree = new ResearchTree();
                tree.loadFromArray(treeData);
                return;
            }

            ResearchTree researchTree = allTreeUUIDsServer.get(uuid);

            if(packetType == 1) {
                if(researchTree == null)return;
                researchTree.setCurrentProject(researchTree.allResearch.get(dis.readUTF()));
                return;
            }
            if(packetType < 2) return;
            String playerID = dis.readUTF();
            EntityPlayerMP playerMP = (EntityPlayerMP) MinecraftServer.getServer().getConfigurationManager().playerEntityList.stream().filter(p-> playerID.equals(((EntityPlayerMP) p).getCommandSenderName())).findFirst().orElse(null);
            if(playerMP == null)return;
            if(researchTree == null) {
                kNetworkHandler.sendToPlayer(new PacketUUIDAssignedData((byte) 0, uuid, (byte)-2), playerMP);
                return;
            }
            if(packetType == 2) sendTreeData(playerMP, researchTree);
            if(packetType == 3) {
                researchTree.portableViewerPlayers.add(playerMP);
                if(MinecraftServer.getServer().getTickCounter() - researchTree.lastUpdateTick > 20)kNetworkHandler.sendToPlayer(new PacketUUIDAssignedData((byte) 0, uuid, (byte)-3), playerMP);
            }
            if(packetType == 4) researchTree.portableViewerPlayers.remove(playerMP);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void sendTreeData(EntityPlayerMP playerMP, ResearchTree researchTree){
        byte[] researchTreeData = researchTree.saveToArray();
        byte[] dataList = new byte[researchTreeData.length+1];
        System.arraycopy(researchTreeData, 0, dataList, 1, researchTreeData.length);
        dataList[0] = -1;
        kNetworkHandler.sendToPlayer(new PacketUUIDAssignedData((byte) 0, researchTree.uuid, dataList), playerMP);
    }

    /**@param type 2: send data to current player, 3: add current player to viewer list, 4: remove player to viewer list**/
    public static void sendGetTreeDataPacket(String playerID, UUID treeUUID, byte type){
        try{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeByte(type);
            dos.writeUTF(playerID);
            dos.close();
            kNetworkHandler.sendToServer(new PacketUUIDAssignedData((byte) 0,treeUUID,bos.toByteArray()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void sendUpdateCurrentProjectPacket(String projectID){
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             DataOutputStream dos = new DataOutputStream(bos)){
            dos.writeByte(1);
            dos.writeUTF(projectID);
            dos.flush();
            kNetworkHandler.sendToServer(new PacketUUIDAssignedData((byte) 0,uuid,bos.toByteArray()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
