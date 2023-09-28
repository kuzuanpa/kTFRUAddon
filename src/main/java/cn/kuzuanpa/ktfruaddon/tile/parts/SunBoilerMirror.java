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
    public boolean onTickCheck(long aTimer) {
        super.onTickCheck(aTimer);
        getWorld().setWorldTime(getWorld().getWorldTime()+50);

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
