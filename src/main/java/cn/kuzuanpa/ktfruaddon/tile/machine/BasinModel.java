/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.machine;

import cn.kuzuanpa.ktfruaddon.i18n.texts.kMessages;
import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.TileEntities.TEForge;
import com.bioxx.tfc.api.TFCBlocks;
import cpw.mods.fml.common.FMLLog;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.LH;
import gregapi.data.MD;
import gregapi.data.MT;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase07Paintable;
import gregapi.util.ST;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import org.apache.logging.log4j.Level;

import java.util.List;

import static gregapi.data.CS.*;

public class BasinModel extends TileEntityBase07Paintable{
    //0=nothing,1=placed clay,2=full clay,3=composed,4=completed
    public byte mState=0;
    public short mTimer=0;
    public short mTemperature =30;
    public final short REQUIRED_TIME=400;

    public static IIconContainer
            sTextureCommon= new Textures.BlockIcons.CustomIcon("machines/basinmodel/common"),
            sTextureClay= new Textures.BlockIcons.CustomIcon("machines/basinmodel/clay");
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey("ktfru.machine.basinmodel.state")) mState = aNBT.getByte("ktfru.machine.basinmodel.state");
        if (aNBT.hasKey("ktfru.machine.basinmodel.timer")) mTimer = aNBT.getByte("ktfru.machine.basinmodel.timer");
        if (aNBT.hasKey("ktfru.machine.basinmodel.temperature")) mTemperature = aNBT.getByte("ktfru.machine.basinmodel.temperature");
    }
    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        if (mState>0) aNBT.setByte("ktfru.machine.basinmodel.state",mState);
        if (mTimer>0) aNBT.setShort("ktfru.machine.basinmodel.timer",mTimer);
        if (mTemperature>50) aNBT.setShort("ktfru.machine.basinmodel.temperature",mTemperature);

    }
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        switch (aRenderPass){
            case 0:
            case 1:
            case 2:
            case 3:
            case 4: return BlockTextureDefault.get(sTextureCommon,mRGBa);
        }
        switch (mState){
            case 0: return null;
            case 2:
            case 1:
                if (aRenderPass == 5) {return BlockTextureDefault.get(sTextureClay);}
                return null;
            case 3:switch (aRenderPass) {
                case 6:
                case 7:
                case 8:
                case 9:
                    return BlockTextureDefault.get(sTextureClay);
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    return BlockTextureDefault.get(sTextureCommon, mRGBa);
            }
            case 4:switch (aRenderPass) {
                case 6:
                case 7:
                case 8:
                case 9: return BlockTextureDefault.get(sTextureCommon, MT.Ceramic.mRGBaSolid);
                case 10:
                case 11:
                case 12:
                case 13:
                case 14: return BlockTextureDefault.get(sTextureCommon, mRGBa);
             }
                default: return null;
        }
    }

    @Override
    public boolean setBlockBounds2(Block aBlock, int aRenderPass, boolean[] aShouldSideBeRendered) {
        switch (aRenderPass){
            case  0: return box(aBlock, PX_P[ 0]+0.001, PX_P[ 0], PX_P[ 0]+0.001, PX_P[16]-0.001, PX_P[ 1], PX_P[ 16]-0.001);
            case  1: return box(aBlock, PX_P[ 0], PX_P[ 0], PX_P[ 0], PX_P[16], PX_P[16], PX_P[ 1 ]);
            case  2: return box(aBlock, PX_P[ 0], PX_P[ 0], PX_P[ 0], PX_P[1 ], PX_P[16], PX_P[ 16]);
            case  3: return box(aBlock, PX_P[15], PX_P[ 0], PX_P[ 0], PX_P[16], PX_P[16], PX_P[ 16]);
            case  4: return box(aBlock, PX_P[ 0], PX_P[ 0], PX_P[15], PX_P[16], PX_P[16], PX_P[ 16]);

            case  5: return box(aBlock, PX_P[ 1], PX_P[ 2], PX_P[ 1], PX_P[15], PX_P[7 ], PX_P[ 15]);

            case  6: return box(aBlock, PX_P[ 1], PX_P[16]-0.02, PX_P[ 1], PX_P[15], PX_P[16]-0.01, PX_P[ 2 ]);
            case  7: return box(aBlock, PX_P[ 1], PX_P[16]-0.02, PX_P[ 1], PX_P[2 ], PX_P[16]-0.0101, PX_P[ 15]);
            case  8: return box(aBlock, PX_P[14], PX_P[16]-0.02, PX_P[ 1], PX_P[15], PX_P[16]-0.0102, PX_P[ 15]);
            case  9: return box(aBlock, PX_P[ 1], PX_P[16]-0.02, PX_P[14], PX_P[15], PX_P[16]-0.0103, PX_P[ 15]);

            case 10: return box(aBlock, PX_P[ 2], PX_P[ 3], PX_P[ 2], PX_P[14], PX_P[ 4], PX_P[ 14]);
            case 11: return box(aBlock, PX_P[ 2], PX_P[ 3], PX_P[ 2], PX_P[14], PX_P[16], PX_P[ 4 ]);
            case 12: return box(aBlock, PX_P[ 2], PX_P[ 3], PX_P[ 2], PX_P[4 ], PX_P[16], PX_P[ 14]);
            case 13: return box(aBlock, PX_P[12], PX_P[ 3], PX_P[ 3], PX_P[14], PX_P[16], PX_P[ 14]);
            case 14: return box(aBlock, PX_P[ 3], PX_P[ 3], PX_P[12], PX_P[14], PX_P[16], PX_P[ 14]);

        }
        return false;
    }
    @Override public boolean addDefaultCollisionBoxToList() {return F;}
    @Override
    public void addCollisionBoxesToList2(AxisAlignedBB aAABB, List<AxisAlignedBB> aList, Entity aEntity) {
        if(mState==0) {
            box(aAABB, aList, PX_P[ 0]+0.001, PX_P[ 0], PX_P[ 0]+0.001, PX_P[16]-0.001, PX_P[ 1], PX_P[ 16]-0.001);
            box(aAABB, aList, PX_P[ 0], PX_P[ 0], PX_P[ 0], PX_P[16], PX_P[16], PX_P[ 1 ]);
            box(aAABB, aList, PX_P[ 0], PX_P[ 0], PX_P[ 0], PX_P[1 ], PX_P[16], PX_P[ 16]);
            box(aAABB, aList, PX_P[15], PX_P[ 0], PX_P[ 0], PX_P[16], PX_P[16], PX_P[ 16]);
            box(aAABB, aList, PX_P[ 0], PX_P[ 0], PX_P[15], PX_P[16], PX_P[16], PX_P[ 16]);
        }else if(mState==1||mState==2){
            box(aAABB, aList, PX_P[ 0]+0.001, PX_P[ 0], PX_P[ 0]+0.001, PX_P[16]-0.001, PX_P[ 7], PX_P[ 16]-0.001);
            box(aAABB, aList, PX_P[ 0], PX_P[ 0], PX_P[ 0], PX_P[16], PX_P[16], PX_P[ 1 ]);
            box(aAABB, aList, PX_P[ 0], PX_P[ 0], PX_P[ 0], PX_P[1 ], PX_P[16], PX_P[ 16]);
            box(aAABB, aList, PX_P[15], PX_P[ 0], PX_P[ 0], PX_P[16], PX_P[16], PX_P[ 16]);
            box(aAABB, aList, PX_P[ 0], PX_P[ 0], PX_P[15], PX_P[16], PX_P[16], PX_P[ 16]);
        }else {
            box(aAABB, aList, PX_P[1], PX_P[0], PX_P[1], PX_P[15] , PX_P[16], PX_P[15]);

        }
    }
    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
       if (isServerSide()){
           FMLLog.log(Level.FATAL,mState+"/"+ mTemperature +"/"+mTimer);

           if(mTimer>REQUIRED_TIME) {
               mState=4;
               ST.set(slot(0),MultiTileEntityRegistry.getRegistry("gt.multitileentity").getItem(1755));
               updateClientData();
           }
           if (MD.TFC.mLoaded){
               if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) == TFCBlocks.forge)
               {
                   TEForge te = (TEForge) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
                   if (te.fireTemp > mTemperature)
                       mTemperature +=2;
                   if(mState==3&&mTemperature >1400)mTimer++;
                   FMLLog.log(Level.FATAL,"/"+ mTemperature +"/"+mTimer);
               }
               if(mTemperature > TFC_Climate.getHeightAdjustedTemp(worldObj, xCoord, yCoord, zCoord))
                   mTemperature--;
           }else mTimer++;
       }
    }
    @Override
    public int getRenderPasses2(Block aBlock, boolean[] aShouldSideBeRendered) {
        return 15;
    }

    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        return getClientDataPacketByteArray(aSendAll,mState);
    }

    @Override
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler) {
        mState=aData[0];
        return T;
    }
    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        FMLLog.log(Level.FATAL,"/"+ (aPlayer.inventory.getCurrentItem().getItem() == Items.clay_ball)+"/"+mState+"/"+slot(0));
        if(isServerSide()&&aPlayer.inventory.getCurrentItem().getItem()==Items.clay_ball&&(slot(0)==null||slot(0).stackSize<7)&&mState<2&&ST.move(aPlayer.inventory, this, aPlayer.inventory.currentItem, 0,1) > 0){
            mState=1;
            if (slot(0).stackSize>5)mState=2;
        }
        if(isServerSide()&&aPlayer.inventory.getCurrentItem().getItem()==ItemList.BasinModelInnerLayer.getItem()){
            if(mState==2&&ST.move(aPlayer.inventory, this, aPlayer.inventory.currentItem, 1,1) > 0)mState=3;
            else if(mState<2)aPlayer.addChatMessage(new ChatComponentText(LH.get(kMessages.BASIN_MODEL_0)+(6-slot(0).stackSize)+LH.get(kMessages.BASIN_MODEL_1)));
        }
        updateClientData();
        return T;
    }
    // Inventory Stuff
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[4];}
    @Override public boolean canDrop(int aInventorySlot) {return T;}

    private static final int[] ACCESSIBLE_SLOTS = new int[] {0, 1};

    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.basinmodel";
    }
}