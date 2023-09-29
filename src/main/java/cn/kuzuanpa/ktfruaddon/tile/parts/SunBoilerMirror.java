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
        if (!isServerside&&getTimer()%2==0){
            updateRotates();
        //this.getWorld().setWorldTime(this.getWorld().getWorldTime()+1);
        //if (rotateVertical-rotateVerticalToMove>1)rotateVertical-=1;
        //if (rotateVertical-rotateVerticalToMove<-1)rotateVertical+=1;
        //if (rotateHorizontal-rotateHorizontalToMove>1)rotateHorizontal-=1;
        //if (rotateHorizontal-rotateHorizontalToMove<-1)rotateHorizontal+=1;
            rotateVertical=rotateVerticalToMove;
            rotateHorizontal=rotateHorizontalToMove;
        }
    }
    @SideOnly(Side.CLIENT)
public void updateRotates(){
        targetSunBoilerPos=new ChunkCoordinates(-425,151,671);
        long Ti=getWorldObj().getWorldTime()+6000;
        double Xn=0,Yn=0,Zn=0,T=24000;
        while (Ti>24000) Ti-=24000;

        int X2A = targetSunBoilerPos.posX - xCoord, Y2A = targetSunBoilerPos.posY - yCoord, Z2A = targetSunBoilerPos.posZ - zCoord;

        double L = Math.sqrt(X2A * X2A + Y2A * Y2A + Z2A * Z2A);
        double X2 = X2A / L, Y2 = Y2A / L, Z2 = Z2A / L;
        double alpha = ((4 * Ti - T) / (4 * T)) * 6.28, theta = 0, phi = 0;
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

        L = Math.sqrt((Xn * Xn + Yn * Yn + Zn * Zn));
        if (Yn < 0) L = -L;
        Xn = Xn / L;
        Yn = Yn / L;
        Zn = Zn / L;

        phi = Math.acos(Yn);
        rotateVerticalToMove = (float) (phi / 3.1415 * 180);

        theta = Math.asin(Zn / Math.sqrt(1 - Yn * Yn));
        if (Xn < 0) theta = -3.1415 - theta;
        rotateHorizontalToMove = (float) (theta / 3.1415 * 180);
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
