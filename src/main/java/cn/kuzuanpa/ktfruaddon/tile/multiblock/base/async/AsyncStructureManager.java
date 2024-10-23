package cn.kuzuanpa.ktfruaddon.tile.multiblock.base.async;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncStructureManager {
    protected final static ConcurrentHashMap<UUID,StructureComputeData> taskList =new ConcurrentHashMap<>();
    protected final static HashMap<UUID,StructureComputeData> CompletedTaskList =new HashMap<>();
    final static ExecutorService executorService = Executors.newFixedThreadPool(1);
    static boolean isExecutorRunning=false;
    public static boolean isStructureCompleted(UUID taskID) throws AsyncStructureManager.NotCompletedException {
        if(!isCheckCompleted(taskID))throw new AsyncStructureManager.NotCompletedException();
        return CompletedTaskList.get(taskID).isStructureValid;
    }

    public static boolean isCheckCompleted(UUID taskID){
        return CompletedTaskList.get(taskID) != null;
    };

    public static void addStructureComputeTask(UUID taskID, World world, IAsyncStructure structure){
        addStructureComputeTask(new StructureComputeData(taskID,world,structure));
    }
    public static void addStructureComputeTask(StructureComputeData data){
        taskList.put(data.uuid,data);
        if(!isExecutorRunning) {
            executorService.execute(() -> {
                if (taskList.isEmpty())return;
                long time= System.nanoTime();
                try {
                    isExecutorRunning=true;
                    UUID k = taskList.keys().nextElement();
                    StructureComputeData v = taskList.get(k);
                    v.isStructureValid = v.structure.asyncCheckStructure(new WorldContainer(v.world));
                    System.out.println("Completed Structure Compute for: "+v.uuid+", takes"+(System.nanoTime()-time));
                    CompletedTaskList.put(k,v);
                    taskList.remove(k);
                    v.structure.onStructureComputeCompleted();
                }catch (Exception e){
                    System.out.println("ERROR occured when Structure Compute");
                    e.printStackTrace();
                }finally {
                    isExecutorRunning=false;
                }
            });
        }
    }
    public static class StructureComputeData{
        boolean isStructureValid;
        IAsyncStructure structure;
        World world;
        UUID uuid;
        public StructureComputeData(UUID taskID, World world, IAsyncStructure structure){
            this.uuid=taskID;
            this.world=world;
            this.structure=structure;
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
        public TileEntity getTileEntity(int x,int y,int z){
            return world.getTileEntity(x,y,z);
        }
        public float getLightBrightness(int x,int y,int z){
            return world.getLightBrightness(x, y, z);
        }
    }

    public static class NotCompletedException extends Exception{}
}
