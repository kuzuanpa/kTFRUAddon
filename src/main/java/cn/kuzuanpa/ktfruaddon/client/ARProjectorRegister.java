/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.client;

import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.block.multitileentity.MultiTileEntityContainer;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.LH;
import net.minecraft.nbt.NBTTagCompound;
import zmaster587.libVulpes.api.IDummyMultiBlockRegisterer;
import zmaster587.libVulpes.block.BlockMeta;

import java.util.HashMap;
import java.util.Map;

public class ARProjectorRegister implements IDummyMultiBlockRegisterer {
    static MultiTileEntityRegistry g;
    static MultiTileEntityRegistry k;
    public ARProjectorRegister(){ g = MultiTileEntityRegistry.getRegistry("gt.multitileentity");k = MultiTileEntityRegistry.getRegistry("ktfru.multitileentity");}
    public static BlockMeta tile(MultiTileEntityRegistry registry,int id){
        MultiTileEntityContainer container = registry.getNewTileEntityContainer(id, new NBTTagCompound());
        ((IMultiTileEntity) container.mTileEntity).setShouldRefresh(false);
        return new BlockMeta(container.mBlock,container.mTileEntity);
    }
    @Override
    public Map<String, Object[][][]> getDummyMultiBlocks() {
        HashMap<String, Object[][][]> list = new HashMap<>();
        list.put(LH.get("ktfru.projector.fusionReactorTokamakExperiment"), TokamakTierExp());
        list.put(LH.get("ktfru.projector.fusionReactorTokamakT1"), new Object[][][]{TokamakTier1Layer13(),TokamakTier1Layer12(),TokamakTier1Layer11(),TokamakTier1Layer10(),TokamakTier1Layer9(),TokamakTier1Layer8(),TokamakTier1Layer7(),TokamakTier1Layer6(),TokamakTier1Layer5(),TokamakTier1Layer4(),TokamakTier1Layer3(),TokamakTier1Layer2(),TokamakTier1Layer1(),TokamakTier1Layer0()});
        return list;
    }

    static Object[][] TokamakTier1Layer13(){
        
        BlockMeta VaPump= tile(k,31015);
        BlockMeta CondT1= tile(k,31025);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,CondT1,CondT1, null , null , null ,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null },
            { null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null },
            { null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null },
            { null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null },
            { null , null , null , null , null , null , null , null , null ,CondT1,CondT1, null , null , null ,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
    };}

    static Object[][] TokamakTier1Layer12(){

        BlockMeta CondT1= tile(k,31025);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,CondT1,CondT1, null , null , null ,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null },
            {CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null },
            {CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null },
            {CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null },
            { null , null , null , null , null , null , null , null , null ,CondT1,CondT1, null , null , null ,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
    };}

    static Object[][] TokamakTier1Layer11(){

        BlockMeta CondT1= tile(k,31025);
        BlockMeta WallT1= tile(k,31027);
        BlockMeta Heator= tile(k,31028);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,CondT1,CondT1,CondT1,WallT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            {CondT1,CondT1, null , null , null , null ,WallT1,WallT1,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,CondT1,CondT1,Heator,Heator},
            {CondT1,CondT1, null , null , null , null ,WallT1,WallT1,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,CondT1,CondT1,Heator,Heator},
            {CondT1,CondT1, null , null , null , null ,WallT1,WallT1,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,CondT1,CondT1,Heator,Heator},
            { null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,CondT1,CondT1,CondT1,WallT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
    };}

    static Object[][] TokamakTier1Layer10(){

        BlockMeta CondT1= tile(k,31025);
        BlockMeta WallT1= tile(k,31027);
        BlockMeta Heator= tile(k,31028);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1, null , null , null ,WallT1,WallT1, null , null , null , null , null ,WallT1,WallT1, null , null , null ,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,WallT1, null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator},
            {CondT1,CondT1, null , null ,WallT1, null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null ,WallT1,Heator,Heator,CondT1,CondT1,Heator,Heator},
            {CondT1,CondT1, null , null ,WallT1, null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null ,WallT1,Heator,Heator,CondT1,CondT1,Heator,Heator},
            {CondT1,CondT1, null , null ,WallT1, null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null ,WallT1,Heator,Heator,CondT1,CondT1,Heator,Heator},
            { null , null , null , null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null ,WallT1, null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1, null , null , null ,WallT1,WallT1, null , null , null , null , null ,WallT1,WallT1, null , null , null ,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][] TokamakTier1Layer9(){

        BlockMeta CondT1= tile(k,31025);
        BlockMeta WallT1= tile(k,31027);
        BlockMeta Heator= tile(k,31028);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null ,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1, null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1, null ,WallT1, null , null , null , null , null , null , null , null , null , null , null ,WallT1, null ,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            {CondT1,CondT1, null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,CondT1,CondT1,Heator,Heator},
            {CondT1,CondT1, null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,CondT1,CondT1,Heator,Heator},
            {CondT1,CondT1, null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,CondT1,CondT1,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null ,WallT1, null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1, null ,WallT1, null , null , null , null , null , null , null , null , null , null , null ,WallT1, null ,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null ,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1, null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][] TokamakTier1Layer8(){

        BlockMeta CondT1= tile(k,31025);
        BlockMeta WallT1= tile(k,31027);
        BlockMeta Heator= tile(k,31028);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null ,WallT1,WallT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            {CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,Heator,Heator},
            {CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,Heator,Heator},
            {CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null , null ,CondT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null ,WallT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null ,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][] TokamakTier1Layer7(){

        BlockMeta CondT1= tile(k,31025);
        BlockMeta WallT1= tile(k,31027);
        BlockMeta Heator= tile(k,31028);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,WallT1, null , null , null , null , null , null , null , null , null , null , null ,WallT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null },
            { null , null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator},
            { null , null ,WallT1, null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator},
            {CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,Heator,Heator},
            {CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,Heator,Heator},
            {CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,Heator,Heator},
            { null , null ,WallT1, null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator},
            { null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null , null ,CondT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,WallT1, null , null , null , null , null , null , null , null , null , null , null ,WallT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][] TokamakTier1Layer6(){

        BlockMeta CondT1= tile(k,31025);
        BlockMeta WallT1= tile(k,31027);
        BlockMeta Heator= tile(k,31028);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,WallT1, null , null , null , null , null , null , null , null , null , null , null ,WallT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null },
            { null , null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator},
            { null , null ,WallT1, null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator},
            {CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,Heator,Heator},
            {CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,Heator,Heator},
            {CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,Heator,Heator},
            { null , null ,WallT1, null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator},
            { null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null , null ,CondT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,WallT1, null , null , null , null , null , null , null , null , null , null , null ,WallT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][] TokamakTier1Layer5(){
        BlockMeta CondT1= tile(k,31025);
        BlockMeta WallT1= tile(k,31027);
        BlockMeta Heator= tile(k,31028);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null ,WallT1,WallT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            {CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,Heator,Heator},
            {CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,Heator,Heator},
            {CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null , null ,CondT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null ,WallT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null ,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][] TokamakTier1Layer4(){
        BlockMeta CoPump= tile(k,31024);
        BlockMeta CondT1= tile(k,31025);
        BlockMeta WallT1= tile(k,31027);
        BlockMeta Heator= tile(k,31028);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null ,CoPump, null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null ,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1, null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1, null ,WallT1, null , null , null , null , null , null , null , null , null , null , null ,WallT1, null ,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null , null ,WallT1, null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            {CondT1,CondT1, null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,CondT1,CondT1,Heator,Heator},
            {CondT1,CondT1, null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,CondT1,CondT1,Heator,Heator},
            {CondT1,CondT1, null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,CondT1,CondT1,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null ,WallT1, null , null , null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null ,WallT1, null , null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null },
            { null , null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1, null ,WallT1, null , null , null , null , null , null , null , null , null , null , null ,WallT1, null ,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null ,WallT1,WallT1, null , null , null , null , null , null , null ,WallT1,WallT1, null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][] TokamakTier1Layer3(){
        
        BlockMeta VaPump= tile(k,31015);
        BlockMeta ToPipe= tile(k,31019);
        BlockMeta CoPump= tile(k,31024);
        BlockMeta CondT1= tile(k,31025);
        BlockMeta WallT1= tile(k,31027);
        BlockMeta Heator= tile(k,31028);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null , null ,ToPipe, null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,VaPump,VaPump,CoPump,ToPipe,CoPump,ToPipe,VaPump,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,VaPump,VaPump,CondT1,CondT1,CondT1,VaPump,VaPump,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,VaPump,VaPump,CondT1,CondT1,CondT1,VaPump,VaPump,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1, null , null , null ,WallT1,WallT1, null , null , null , null , null ,WallT1,WallT1, null , null , null ,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,WallT1, null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator},
            {CondT1,CondT1, null , null ,WallT1, null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null ,WallT1,Heator,Heator,CondT1,CondT1,Heator,Heator},
            {CondT1,CondT1, null , null ,WallT1, null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null ,WallT1,Heator,Heator,CondT1,CondT1,Heator,Heator},
            {CondT1,CondT1, null , null ,WallT1, null , null , null ,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1, null , null , null ,WallT1,Heator,Heator,CondT1,CondT1,Heator,Heator},
            { null , null , null , null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null ,WallT1, null , null , null , null ,WallT1,CondT1,CondT1,CondT1,WallT1, null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null ,WallT1, null , null , null , null , null ,WallT1,WallT1,WallT1, null , null , null , null , null ,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null ,WallT1, null , null , null , null , null , null , null , null , null , null , null ,WallT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null , null ,WallT1,WallT1, null , null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1, null , null , null ,WallT1,WallT1, null , null , null , null , null ,WallT1,WallT1, null , null , null ,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][] TokamakTier1Layer2(){
        
        BlockMeta VaPump= tile(k,31015);
        BlockMeta ToPipe= tile(k,31019);
        BlockMeta CondT1= tile(k,31025);
        BlockMeta WallT1= tile(k,31027);
        BlockMeta Heator= tile(k,31028);
        BlockMeta Comput= tile(k,32005);
        BlockMeta StWall= tile(g,18002);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null ,ToPipe,ToPipe,StWall,Comput,StWall,ToPipe,ToPipe, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,ToPipe,ToPipe,StWall,StWall,StWall,ToPipe,ToPipe,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,ToPipe,ToPipe,CondT1,CondT1,CondT1,ToPipe,ToPipe,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,ToPipe,ToPipe,CondT1,CondT1,CondT1,ToPipe,ToPipe,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,ToPipe,ToPipe, null , null , null ,ToPipe,ToPipe, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,ToPipe,ToPipe, null , null , null ,ToPipe,ToPipe, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null ,ToPipe,ToPipe, null , null , null ,ToPipe,ToPipe, null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1, null , null , null , null ,ToPipe,ToPipe, null , null , null ,ToPipe,ToPipe, null , null , null , null ,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,CondT1,CondT1,CondT1,WallT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            {CondT1,CondT1, null , null , null , null ,WallT1,WallT1,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,CondT1,CondT1,Heator,Heator},
            {CondT1,CondT1, null , null , null , null ,WallT1,WallT1,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,CondT1,CondT1,Heator,Heator},
            {CondT1,CondT1, null , null , null , null ,WallT1,WallT1,WallT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,CondT1,CondT1,Heator,Heator},
            { null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,CondT1,CondT1,CondT1,CondT1,CondT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,CondT1,CondT1,CondT1,WallT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator,Heator},
            { null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1,WallT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null , null , null ,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][] TokamakTier1Layer1(){
        
        BlockMeta VaPump= tile(k,31015);
        BlockMeta ToPipe= tile(k,31019);
        BlockMeta CondT1= tile(k,31025);
        BlockMeta Comput= tile(k,32005);
        BlockMeta StWall= tile(g,18002);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null ,ToPipe,ToPipe,Comput,tile(k,0/**/),Comput,ToPipe,ToPipe, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,ToPipe,ToPipe,StWall,Comput,StWall,ToPipe,ToPipe,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,ToPipe,ToPipe,CondT1,CondT1,CondT1,ToPipe,ToPipe,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,ToPipe,ToPipe,CondT1,CondT1,CondT1,ToPipe,ToPipe,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,ToPipe,ToPipe,CondT1,CondT1,CondT1,ToPipe,ToPipe, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,ToPipe,ToPipe,CondT1,CondT1,CondT1,ToPipe,ToPipe, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null ,ToPipe,ToPipe,CondT1,CondT1,CondT1,ToPipe,ToPipe, null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1,CondT1,CondT1, null , null ,ToPipe,ToPipe,CondT1,CondT1,CondT1,ToPipe,ToPipe, null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,CondT1,CondT1, null ,ToPipe,ToPipe,CondT1,CondT1,CondT1,ToPipe,ToPipe, null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,CondT1,CondT1,CondT1,ToPipe,ToPipe,CondT1,CondT1,CondT1,ToPipe,ToPipe,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CondT1,CondT1,CondT1,ToPipe,CondT1,CondT1,CondT1,ToPipe,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,CondT1,CondT1, null , null , null ,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null },
            {CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null },
            {CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null },
            {CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null },
            { null , null , null , null , null , null , null , null , null ,CondT1,CondT1, null , null , null ,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    static Object[][] TokamakTier1Layer0(){
        
        BlockMeta VaPump= tile(k,31015);
        BlockMeta CondT1= tile(k,31025);
        BlockMeta Comput= tile(k,32005);
        BlockMeta StWall= tile(g,18002);

return new Object[][] {
            { null , null , null , null , null , null , null , null , null , null , null ,StWall,Comput,StWall, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,VaPump,VaPump,StWall,StWall,StWall,VaPump,VaPump,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,VaPump,VaPump,VaPump,VaPump,VaPump,VaPump,VaPump,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,VaPump,VaPump,VaPump,CondT1,CondT1,CondT1,VaPump,VaPump,VaPump, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null ,CondT1,CondT1, null , null , null ,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null },
            { null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null },
            { null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null },
            { null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null },
            { null , null , null , null , null , null , null , null , null ,CondT1,CondT1, null , null , null ,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null },
            { null , null , null , null , null ,CondT1, null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null ,CondT1, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null ,CondT1,CondT1,CondT1, null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            };}

    Object[][][] TokamakTierExp() {

        BlockMeta VaPump= tile(k,31015);
        BlockMeta CondTe= tile(k,31016);
        BlockMeta CoilTe= tile(k,31017);
        BlockMeta WallTe= tile(k,31018);
        BlockMeta ToPipe= tile(k,31019);
        BlockMeta CoPump= tile(k,31024);
        BlockMeta Comput= tile(k,32005);
        BlockMeta StWall= tile(g,18002);


        return new Object[][][] {{
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondTe, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondTe, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondTe, null , null , null , null , null , null , null , null },
            { null , null , null ,CondTe, null , null , null , null ,CondTe, null , null , null , null ,CondTe, null , null , null },
            { null , null , null , null ,CondTe, null , null , null ,CondTe, null , null , null ,CondTe, null , null , null , null },
            { null , null , null , null , null ,CondTe, null , null ,CondTe, null , null ,CondTe, null , null , null , null , null },
            { null , null , null , null , null , null ,CondTe, null ,CondTe, null ,CondTe, null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CondTe, null ,CondTe, null , null , null , null , null , null , null },
            {CondTe,CondTe,CondTe,CondTe,CondTe,CondTe,CondTe, null , null , null ,CondTe,CondTe,CondTe,CondTe,CondTe,CondTe,CondTe},
            { null , null , null , null , null , null , null ,CondTe, null ,CondTe, null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,CondTe, null ,CondTe, null ,CondTe, null , null , null , null , null , null },
            { null , null , null , null , null ,CondTe, null , null ,CondTe, null , null ,CondTe, null , null , null , null , null },
            { null , null , null , null ,CondTe, null , null , null ,CondTe, null , null , null ,CondTe, null , null , null , null },
            { null , null , null ,CondTe, null , null , null , null ,CondTe, null , null , null , null ,CondTe, null , null , null },
            { null , null , null , null , null , null , null , null ,CondTe, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondTe, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondTe, null , null , null , null , null , null , null , null },
    },{
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CoilTe, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null , null , null , null },
            { null , null , null ,CoilTe, null ,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe, null ,CoilTe, null , null , null },
            { null , null , null , null ,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe, null , null , null , null },
            { null , null , null ,WallTe,WallTe,WallTe,WallTe, null , null , null ,WallTe,WallTe,WallTe,WallTe, null , null , null },
            { null , null , null ,WallTe,WallTe,WallTe, null , null ,CondTe, null , null ,WallTe,WallTe,WallTe, null , null , null },
            { null , null ,WallTe,WallTe,WallTe, null , null ,CondTe,CondTe,CondTe, null , null ,WallTe,WallTe,WallTe, null , null },
            {CoilTe, null ,WallTe,WallTe,WallTe, null ,CondTe,CondTe, null ,CondTe,CondTe, null ,WallTe,WallTe,WallTe, null ,CoilTe},
            { null , null ,WallTe,WallTe,WallTe, null , null ,CondTe,CondTe,CondTe, null , null ,WallTe,WallTe,WallTe, null , null },
            { null , null , null ,WallTe,WallTe,WallTe, null , null ,CondTe, null , null ,WallTe,WallTe,WallTe, null , null , null },
            { null , null , null ,WallTe,WallTe,WallTe,WallTe, null , null , null ,WallTe,WallTe,WallTe,WallTe, null , null , null },
            { null , null , null , null ,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe, null , null , null , null },
            { null , null , null ,CoilTe, null ,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe, null ,CoilTe, null , null , null },
            { null , null , null , null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CoilTe, null , null , null , null , null , null , null , null },
    },{
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CoilTe, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null , null , null , null },
            { null , null , null , null , null ,WallTe,WallTe, null , null , null ,WallTe,WallTe, null , null , null , null , null },
            { null , null , null ,CoilTe,WallTe, null , null , null , null , null , null , null ,WallTe,CoilTe, null , null , null },
            { null , null , null ,WallTe, null , null , null , null , null , null , null , null , null ,WallTe, null , null , null },
            { null , null ,WallTe, null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null ,WallTe, null , null },
            { null , null ,WallTe, null , null , null ,WallTe, null ,CondTe, null ,WallTe, null , null , null ,WallTe, null , null },
            { null ,WallTe, null , null , null ,WallTe, null ,CondTe,CondTe,CondTe, null ,WallTe, null , null , null ,WallTe, null },
            {CoilTe,WallTe, null , null , null ,WallTe,CondTe,CondTe, null ,CondTe,CondTe,WallTe, null , null , null ,WallTe,CoilTe},
            { null ,WallTe, null , null , null ,WallTe, null ,CondTe,CondTe,CondTe, null ,WallTe, null , null , null ,WallTe, null },
            { null , null ,WallTe, null , null , null ,WallTe, null ,CondTe, null ,WallTe, null , null , null ,WallTe, null , null },
            { null , null ,WallTe, null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null ,WallTe, null , null },
            { null , null , null ,WallTe, null , null , null , null , null , null , null , null , null ,WallTe, null , null , null },
            { null , null , null ,CoilTe,WallTe, null , null , null , null , null , null , null ,WallTe,CoilTe, null , null , null },
            { null , null , null , null , null ,WallTe,WallTe, null , null , null ,WallTe,WallTe, null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CoilTe, null , null , null , null , null , null , null , null },
    },{
            { null , null , null , null , null , null , null , null ,CoPump, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CoilTe, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null , null , null , null },
            { null , null , null , null , null ,WallTe,WallTe, null , null , null ,WallTe,WallTe, null , null , null , null , null },
            { null , null , null ,CoilTe,WallTe, null , null , null , null , null , null , null ,WallTe,CoilTe, null , null , null },
            { null , null , null ,WallTe, null , null , null , null , null , null , null , null , null ,WallTe, null , null , null },
            { null , null ,WallTe, null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null ,WallTe, null , null },
            { null , null ,WallTe, null , null , null ,WallTe, null ,CondTe, null ,WallTe, null , null , null ,WallTe, null , null },
            { null ,WallTe, null , null , null ,WallTe, null ,CondTe,CondTe,CondTe, null ,WallTe, null , null , null ,WallTe, null },
            {CoilTe,WallTe, null , null , null ,WallTe,CondTe,CondTe, null ,CondTe,CondTe,WallTe, null , null , null ,WallTe,CoilTe},
            { null ,WallTe, null , null , null ,WallTe, null ,CondTe,CondTe,CondTe, null ,WallTe, null , null , null ,WallTe, null },
            { null , null ,WallTe, null , null , null ,WallTe, null ,CondTe, null ,WallTe, null , null , null ,WallTe, null , null },
            { null , null ,WallTe, null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null ,WallTe, null , null },
            { null , null , null ,WallTe, null , null , null , null , null , null , null , null , null ,WallTe, null , null , null },
            { null , null , null ,CoilTe,WallTe, null , null , null , null , null , null , null ,WallTe,CoilTe, null , null , null },
            { null , null , null , null , null ,WallTe,WallTe, null , null , null ,WallTe,WallTe, null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CoilTe, null , null , null , null , null , null , null , null },
    },{
            { null , null , null , null , null , null , null ,CoPump,ToPipe,CoPump, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CoPump,CoilTe,CoPump, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null , null , null , null },
            { null , null , null , null , null ,WallTe,WallTe, null , null , null ,WallTe,WallTe, null , null , null , null , null },
            { null , null , null ,CoilTe,WallTe, null , null , null , null , null , null , null ,WallTe,CoilTe, null , null , null },
            { null , null , null ,WallTe, null , null , null , null , null , null , null , null , null ,WallTe, null , null , null },
            { null , null ,WallTe, null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null ,WallTe, null , null },
            { null , null ,WallTe, null , null , null ,WallTe, null ,CondTe, null ,WallTe, null , null , null ,WallTe, null , null },
            { null ,WallTe, null , null , null ,WallTe, null ,CondTe,CondTe,CondTe, null ,WallTe, null , null , null ,WallTe, null },
            {CoilTe,WallTe, null , null , null ,WallTe,CondTe,CondTe, null ,CondTe,CondTe,WallTe, null , null , null ,WallTe,CoilTe},
            { null ,WallTe, null , null , null ,WallTe, null ,CondTe,CondTe,CondTe, null ,WallTe, null , null , null ,WallTe, null },
            { null , null ,WallTe, null , null , null ,WallTe, null ,CondTe, null ,WallTe, null , null , null ,WallTe, null , null },
            { null , null ,WallTe, null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null ,WallTe, null , null },
            { null , null , null ,WallTe, null , null , null , null , null , null , null , null , null ,WallTe, null , null , null },
            { null , null , null ,CoilTe,WallTe, null , null , null , null , null , null , null ,WallTe,CoilTe, null , null , null },
            { null , null , null , null , null ,WallTe,WallTe, null , null , null ,WallTe,WallTe, null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CoilTe, null , null , null , null , null , null , null , null },
    },{
            { null , null , null , null , null ,VaPump, null ,StWall,Comput,StWall, null ,VaPump, null , null , null , null , null },
            { null , null , null , null , null ,VaPump, null ,StWall,CoilTe,StWall, null ,VaPump, null , null , null , null , null },
            { null , null , null , null , null ,VaPump, null , null , null , null , null ,VaPump, null , null , null , null , null },
            { null , null , null , null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null , null , null , null },
            { null , null , null ,CoilTe, null ,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe, null ,CoilTe, null , null , null },
            { null , null , null , null ,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe, null , null , null , null },
            { null , null , null ,WallTe,WallTe,WallTe,WallTe, null , null , null ,WallTe,WallTe,WallTe,WallTe, null , null , null },
            { null , null , null ,WallTe,WallTe,WallTe, null , null ,CondTe, null , null ,WallTe,WallTe,WallTe, null , null , null },
            { null , null ,WallTe,WallTe,WallTe, null , null ,CondTe,CondTe,CondTe, null , null ,WallTe,WallTe,WallTe, null , null },
            {CoilTe, null ,WallTe,WallTe,WallTe, null ,CondTe,CondTe, null ,CondTe,CondTe, null ,WallTe,WallTe,WallTe, null ,CoilTe},
            { null , null ,WallTe,WallTe,WallTe, null , null ,CondTe,CondTe,CondTe, null , null ,WallTe,WallTe,WallTe, null , null },
            { null , null , null ,WallTe,WallTe,WallTe, null , null ,CondTe, null , null ,WallTe,WallTe,WallTe, null , null , null },
            { null , null , null ,WallTe,WallTe,WallTe,WallTe, null , null , null ,WallTe,WallTe,WallTe,WallTe, null , null , null },
            { null , null , null , null ,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe, null , null , null , null },
            { null , null , null ,CoilTe, null ,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe,WallTe, null ,CoilTe, null , null , null },
            { null , null , null , null , null , null , null ,WallTe,WallTe,WallTe, null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CoilTe, null , null , null , null , null , null , null , null },
    },{
            { null , null , null , null ,VaPump,ToPipe,VaPump,Comput,tile(k,0/**/),Comput,VaPump,ToPipe,VaPump, null , null , null , null },
            { null , null , null , null ,VaPump,ToPipe,VaPump,StWall,CondTe,StWall,VaPump,ToPipe,VaPump, null , null , null , null },
            { null , null , null , null ,VaPump,ToPipe,VaPump, null ,CondTe, null ,VaPump,ToPipe,VaPump, null , null , null , null },
            { null , null , null , null , null ,ToPipe, null , null ,CondTe, null , null ,ToPipe, null , null , null , null , null },
            { null , null , null ,CondTe, null ,ToPipe, null , null ,CondTe, null , null ,ToPipe, null ,CondTe, null , null , null },
            { null , null , null , null ,CondTe,ToPipe, null , null ,CondTe, null , null ,ToPipe,CondTe, null , null , null , null },
            { null , null , null , null , null ,CondTe, null , null ,CondTe, null , null ,CondTe, null , null , null , null , null },
            { null , null , null , null , null , null ,CondTe, null ,CondTe, null ,CondTe, null , null , null , null , null , null },
            { null , null , null , null , null , null , null ,CondTe, null ,CondTe, null , null , null , null , null , null , null },
            {CondTe,CondTe,CondTe,CondTe,CondTe,CondTe,CondTe, null , null , null ,CondTe,CondTe,CondTe,CondTe,CondTe,CondTe,CondTe},
            { null , null , null , null , null , null , null ,CondTe, null ,CondTe, null , null , null , null , null , null , null },
            { null , null , null , null , null , null ,CondTe, null ,CondTe, null ,CondTe, null , null , null , null , null , null },
            { null , null , null , null , null ,CondTe, null , null ,CondTe, null , null ,CondTe, null , null , null , null , null },
            { null , null , null , null ,CondTe, null , null , null ,CondTe, null , null , null ,CondTe, null , null , null , null },
            { null , null , null ,CondTe, null , null , null , null ,CondTe, null , null , null , null ,CondTe, null , null , null },
            { null , null , null , null , null , null , null , null ,CondTe, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondTe, null , null , null , null , null , null , null , null },
            { null , null , null , null , null , null , null , null ,CondTe, null , null , null , null , null , null , null , null },
    }};}

}
