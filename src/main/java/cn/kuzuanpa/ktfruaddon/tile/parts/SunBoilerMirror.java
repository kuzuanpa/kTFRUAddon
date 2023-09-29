/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */


package cn.kuzuanpa.ktfruaddon.tile.parts;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregapi.block.multitileentity.MultiTileEntityContainer;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import static gregapi.data.CS.*;

public class SunBoilerMirror extends TileEntityBase09FacingSingle {
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.sunboiler.mirror";
    }
    public ChunkCoordinates targetSunBoilerPos;
    public float rotateHorizontal,rotateVertical,rotateHorizontalToMove,rotateVerticalToMove;

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_TARGET)) {targetSunBoilerPos = new ChunkCoordinates(UT.Code.bindInt(aNBT.getLong(NBT_TARGET_X)), UT.Code.bindInt(aNBT.getLong(NBT_TARGET_Y)), UT.Code.bindInt(aNBT.getLong(NBT_TARGET_Z)));}
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        if (targetSunBoilerPos != null) {
            UT.NBT.setBoolean(aNBT, NBT_TARGET, T);
            UT.NBT.setNumber(aNBT, NBT_TARGET_X, targetSunBoilerPos.posX);
            UT.NBT.setNumber(aNBT, NBT_TARGET_Y, targetSunBoilerPos.posY);
            UT.NBT.setNumber(aNBT, NBT_TARGET_Z, targetSunBoilerPos.posZ);
        }
    }
    @Override
    public boolean onPlaced(ItemStack aStack, EntityPlayer aPlayer, MultiTileEntityContainer aMTEContainer, World aWorld, int aX, int aY, int aZ, byte aSide, float aHitX, float aHitY, float aHitZ) {
        super.onPlaced(aStack, aPlayer, aMTEContainer, aWorld, aX, aY, aZ, aSide, aHitX, aHitY, aHitZ);

        return T;
    }
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {return null;}
    public boolean[] getValidSides() {return SIDES_BACK;}
    @Override
    public void onTick2(long aTimer, boolean isServerside){
        if (!isServerside&&getTimer()%10==0) {
            if (this.getWorld().getWorldTime() % 24000 < 13800) updateRotates();
            else rotateVerticalToMove=0;
        }
        if(!isServerside) {
            float f1 =rotateVertical - rotateVerticalToMove;
            float f2 =rotateHorizontal - rotateHorizontalToMove;

            if(f1>0.01)rotateVertical-=f1>10?1:(f1/10);
            if(f1<0.01)rotateVertical-=f1<-10?-1:(f1/10);
            if(f2>0.01)rotateHorizontal-=f2>10?1:(f2/10);
            if(f2<0.01)rotateHorizontal-=f2<-10?-1:(f2/10);
        }
    }
    @SideOnly(Side.CLIENT)
public void updateRotates(){
        targetSunBoilerPos=new ChunkCoordinates(-425,121,671);
        long Ti=getWorldObj().getWorldTime()+1800;
        double Xn=0,Yn=0,Zn=0,T=24000;
        Ti = Ti % 24000;

        Ti = Ti>15600?7800:Ti;

        int X2A = targetSunBoilerPos.posX - xCoord, Y2A = targetSunBoilerPos.posY - yCoord, Z2A = targetSunBoilerPos.posZ - zCoord;

        double L = Math.sqrt(X2A * X2A + Y2A * Y2A + Z2A * Z2A);
        double X2 = X2A / L, Y2 = Y2A / L, Z2 = Z2A / L;
        double alpha = Ti / 15600.0 * 3.14159, theta = 0, phi = 0;
        double X1 = Math.cos(alpha), Y1 = Math.sin(alpha);
        double[][] coefficient = {
                {Y1 * Z2, -X1 * Z2, (-Y1 * X2 + X1 * Y2)},
                {(X1 - X2), (Y1 - Y2), -Z2}
        };

        Matrix A = new Matrix(coefficient);
        Matrix mATA = A.transpose().times(A);
        EigenvalueDecomposition E = mATA.eig();

        //特征值
        Matrix mD = E.getD();
        //特征向量
        Matrix mV = E.getV();

        //检测哪一行特征值是0
        int index = 0;
        for (int i = 0; i < 3; i++) {
            double c = mD.get(i, i);
            if (c <= 1e-6 && c >= -1e-6) {
                index = i;
                break;
            }
        }

        Xn = mV.get(0, index);
        Yn = mV.get(1, index);
        Zn = mV.get(2, index);

/*        Xn = X1;
        Yn = Y1;
        Zn = 0;*/

        L = Math.sqrt((Xn * Xn + Yn * Yn + Zn * Zn));

        if (Yn < 0) L = -L;
        Xn = Xn / L;
        Yn = Yn / L;
        Zn = Zn / L;

        phi = Math.acos(Yn) * 180/3.14159 ;
        theta = Math.asin(Math.abs(Zn) / Math.sqrt(1 - Yn * Yn)) * 180/3.14159 ;

        if(Xn < 0) theta = 180 - theta;
        if(Zn < 0) theta = -theta;

        rotateVerticalToMove = (float) (phi);
        rotateHorizontalToMove = (float) (theta);
/*        rotateVerticalToMove = 45;
        rotateHorizontalToMove = 90;*/
}
    @Override
    public boolean onTickCheck(long aTimer) {
        super.onTickCheck(aTimer);

        if (!(worldObj.getBlock(xCoord,yCoord+1,zCoord)== Blocks.air)
                ||!(worldObj.getBlock(xCoord+1,yCoord,zCoord+1)== Blocks.air)
                ||!(worldObj.getBlock(xCoord+1,yCoord,zCoord)== Blocks.air)
                ||!(worldObj.getBlock(xCoord+1,yCoord,zCoord-1)== Blocks.air)
                ||!(worldObj.getBlock(xCoord,yCoord,zCoord+1)== Blocks.air)
                ||!(worldObj.getBlock(xCoord,yCoord,zCoord-1)== Blocks.air)
                ||!(worldObj.getBlock(xCoord-1,yCoord,zCoord+1)== Blocks.air)
                ||!(worldObj.getBlock(xCoord-1,yCoord,zCoord)== Blocks.air)
                ||!(worldObj.getBlock(xCoord-1,yCoord,zCoord-1)== Blocks.air)) {
            worldObj.setBlockToAir(xCoord,yCoord,zCoord);
            worldObj.spawnEntityInWorld(new EntityItem(worldObj,xCoord,yCoord,zCoord, MultiTileEntityRegistry.getRegistry("ktfru.multitileentity").getItem(31130)));
            return false;
        }
        return true;
    }
    @Override
    public boolean canDrop(int aSlot) {return true;}
}
