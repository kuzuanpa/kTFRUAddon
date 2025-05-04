/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.api.tile.async;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncStructureManager {
    protected final static ConcurrentHashMap<UUID,StructureComputeData> taskList =new ConcurrentHashMap<>();
    protected final static HashMap<UUID,StructureComputeData> CompletedTaskList =new HashMap<>();
    final static ExecutorService executorService = Executors.newFixedThreadPool(1);
    final static int MaxCompletedTaskSaved = 32;
    public static UUID runningTask=null;
    static boolean isExecutorRunning=false;
    public final static byte STATE_NOT_COMPLETE=1, STATE_COMPLETED=2, STATE_NOT_FOUND=3, STATE_RUNNING=4;
    public static boolean isStructureCompleted(UUID taskID) throws AsyncStructureManager.NotCompletedException {
        if(getCheckState(taskID) != STATE_COMPLETED)throw new AsyncStructureManager.NotCompletedException();
        return CompletedTaskList.get(taskID).isStructureValid;
    }

    public static byte getCheckState(UUID taskID){
        if (CompletedTaskList.get(taskID) != null) return STATE_COMPLETED;
        if (taskList.get(taskID) != null) return STATE_NOT_COMPLETE;
        if(Objects.equals(runningTask,taskID))return STATE_RUNNING;
        return STATE_NOT_FOUND;
    }

    public static void removeCompletedTask(UUID taskID){
        CompletedTaskList.remove(taskID);
    }

    public static void addStructureComputeTask(UUID taskID, World world, IAsyncStructure structure, ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory){
        addStructureComputeTask(new StructureComputeData(taskID,world,structure, aClickedAt, aPlayer, aInventory));
    }
    public static void addStructureComputeTask(StructureComputeData data){
        taskList.put(data.uuid,data);
        if(!isExecutorRunning) {
            for (int i=0; i< taskList.size();i++) executorService.execute(() -> {
                if (taskList.isEmpty())return;
                long time= System.nanoTime();
                isExecutorRunning=true;
                UUID k = taskList.keys().nextElement();
                runningTask=k;
                try {
                    StructureComputeData v = taskList.get(k);
                    v.isStructureValid = v.structure.asyncCheckStructure(new WorldContainer(v.world), v.aClickedAt, v.aPlayer, v.aInventory);
                    System.out.println("Completed Structure Compute for: "+v.uuid+" "+v.desc+", takes"+(System.nanoTime()-time));
                    CompletedTaskList.put(k,v);
                    taskList.remove(k);
                    runningTask=null;
                    v.structure.onAsyncCheckStructureCompleted();
                }catch (Exception e){
                    System.out.println("ERROR occured when Structure Compute");
                    e.printStackTrace();
                } finally {
                    taskList.remove(k);
                    runningTask=null;
                    isExecutorRunning=false;
                }
            });
        }
    }
    public static class StructureComputeData {
        boolean isStructureValid;
        IAsyncStructure structure;
        World world;
        UUID uuid;
        String desc = "";
        public ChunkCoordinates aClickedAt;
        public Entity aPlayer;
        public IInventory aInventory;

        public StructureComputeData(UUID taskID, World world, IAsyncStructure structure, ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
            this.uuid = taskID;
            this.world = world;
            this.structure = structure;
            this.aClickedAt = aClickedAt;
            this.aPlayer = aPlayer;
            this.aInventory = aInventory;
        }

        public StructureComputeData setDesc(String desc) {
            this.desc = desc;
            return this;
        }
    }
    public static class WorldContainer {
        World world;
        public WorldContainer(World world){
            this.world=world;
        }
        public Block getBlock(int x,int y,int z){
            return world.getBlock(x,y,z);
        }
        public boolean isBlockExist(int x,int y,int z){return world.blockExists(x, y, z);}
        public TileEntity getTileEntity(int x,int y,int z){
            return world.getTileEntity(x,y,z);
        }
        public float getLightBrightness(int x,int y,int z){
            return world.getLightBrightness(x, y, z);
        }
    }

    public static class NotCompletedException extends Exception{}
}
