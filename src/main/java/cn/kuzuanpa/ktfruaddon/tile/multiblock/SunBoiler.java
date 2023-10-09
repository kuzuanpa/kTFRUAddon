/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */


package cn.kuzuanpa.ktfruaddon.tile.multiblock;

import cn.kuzuanpa.ktfruaddon.i18n.texts.kMessages;
import cn.kuzuanpa.ktfruaddon.tile.parts.SunBoilerMirror;
import cn.kuzuanpa.ktfruaddon.tile.util.utils;
import cpw.mods.fml.common.FMLLog;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import gregapi.util.OM;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.*;

public class SunBoiler extends TileEntityBase10MultiBlockBase implements ITileEntityEnergy{
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.sunboiler";
    }

    public List<sourceMirror> sourceMirrors=new ArrayList<>();
    //决定机器大小
    //this controls the size of machine.
    public final short machineX = 5, machineYmax = 16, machineZ = 5;
    public int mRate=32,mEnergy=0;
    private TagData mEnergyTypeEmitted=TD.Energy.HU;
    public boolean clickDoubleCheck=false;
    public short machineY=0;
    //决定结构检测的起始位置，默认情况下是从主方块起始
    //This controls where is the start point to check structure,Default is the position of controller block
    public final short xMapOffset = -2, zMapOffset = 0;
    public static int[][][] blockIDMap = {{
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
    },{
            {18002, 18002,   0  , 18002, 18002},
            {18002,   0  ,   0  ,   0  , 18002},
            {18002,   0  ,   0  ,   0  , 18002},
            {18002,   0  ,   0  ,   0  , 18002},
            {18002, 18002, 18002, 18002, 18002},
    },{
            {18002, 18002, 18002, 18002, 18002},
            {18002,   0  ,   0  ,   0  , 18002},
            {18002,   0  ,   0  ,   0  , 18002},
            {18002,   0  ,   0  ,   0  , 18002},
            {18002, 18002, 18002, 18002, 18002},
    },{
            {31131, 31131, 31131, 31131, 31131},
            {31131, 31132, 31132, 31132, 31131},
            {31131, 31132,   0  , 31132, 31131},
            {31131, 31132, 31132, 31132, 31131},
            {31131, 31131, 31131, 31131, 31131},
    },{
            {31133, 31133, 31133, 31133, 31133},
            {31133, 31133, 31133, 31133, 31133},
            {31133, 31133, 31133, 31133, 31133},
            {31133, 31133, 31133, 31133, 31133},
            {31133, 31133, 31133, 31133, 31133},
    },};
    short k = ST.id(MultiTileEntityRegistry.getRegistry("ktfru.multitileentity").mBlock);
    short g = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);
    public short[][][] registryIDMap = {{
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
    },{
            {g, g, k, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
    },{
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
    },{
            {k, k, k, k, k},
            {k, k, k, k, k},
            {k, k, k, k, k},
            {k, k, k, k, k},
            {k, k, k, k, k},
    },{
            {k, k, k, k, k},
            {k, k, k, k, k},
            {k, k, k, k, k},
            {k, k, k, k, k},
            {k, k, k, k, k},
    }};
    public static boolean[][][] ignoreMap = {{
            {F, F, F, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
    }, {
            {F, F, T, F, F},
            {F, T, T, T, F},
            {F, T, T, T, F},
            {F, T, T, T, F},
            {F, F, F, F, F},
    },{
            {F, F, F, F, F},
            {F, T, T, T, F},
            {F, T, T, T, F},
            {F, T, T, T, F},
            {F, F, F, F, F},
    },{
            {F, F, F, F, F},
            {F, F, F, F, F},
            {F, F, T, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
    },{
            {F, F, F, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
    }};

    public int getUsage(int blockID ,short registryID){
        return MultiTileEntityMultiBlockPart.NOTHING;
    }

    @Override
    public boolean checkStructure2() {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (worldObj.blockExists(tX, tY, tZ)) {
            boolean tSuccess = T;
            if (getFacing() == (short) 2) {
                tZ += zMapOffset;
                tX -= xMapOffset;
            } else if (getFacing() == (short) 3) {
                tZ -= zMapOffset;
                tX += xMapOffset;
            } else if (getFacing() == (short) 4) {
                tX += zMapOffset;
                tZ += xMapOffset;
            } else if (getFacing() == (short) 5) {
                tX -= zMapOffset;
                tZ -= xMapOffset;
            } else {
                tSuccess = F;
            }
            int checkX, checkY, checkZ;
            for (checkY  = 0; checkY < 3 &&tSuccess; checkY++) {
                for (checkZ = 0; checkZ < machineZ&&tSuccess; checkZ++) {
                    for (checkX = 0; checkX < machineX&&tSuccess; checkX++) {
                        if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, utils.getRealX(mFacing, tX, checkX, checkZ), tY + checkY -1, utils.getRealZ(mFacing, tZ, checkX, checkZ), blockIDMap[checkY][checkZ][checkX], registryIDMap[checkY][checkZ][checkX], 0, getUsage( blockIDMap[checkY][checkZ][checkX], registryIDMap[checkY][checkZ][checkX]))) {
                            tSuccess = ignoreMap[checkY][checkZ][checkX];
                        }
                    }
                }
            }
            if(!tSuccess)return false;

            for (checkY  = 3; checkY < machineYmax &&tSuccess; checkY++) {

                for (checkZ = 0; checkZ < machineZ && tSuccess; checkZ++) {
                    for (checkX = 0; checkX < machineX && tSuccess; checkX++) {
                        if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, utils.getRealX(mFacing, tX, checkX, checkZ), tY + checkY - 1, utils.getRealZ(mFacing, tZ, checkX, checkZ), blockIDMap[3][checkZ][checkX], registryIDMap[3][checkZ][checkX], 0, getUsage(blockIDMap[3][checkZ][checkX], registryIDMap[3][checkZ][checkX]))) {
                            tSuccess = ignoreMap[3][checkZ][checkX];
                        }
                    }
                }
            }
            machineY = (short) (checkY - (tSuccess ? 3 : 4));
            if (!tSuccess) checkY--;
            tSuccess=T;
            FMLLog.log(Level.FATAL, "MachineY" + machineY);
            for (checkZ = 0; checkZ < machineZ && tSuccess; checkZ++) {
                for (checkX = 0; checkX < machineX && tSuccess; checkX++) {
                    if (!ITileEntityMultiBlockController.Util.checkAndSetTarget(this, utils.getRealX(mFacing, tX, checkX, checkZ), tY + checkY - 1, utils.getRealZ(mFacing, tZ, checkX, checkZ), blockIDMap[4][checkZ][checkX], registryIDMap[4][checkZ][checkX], 0, getUsage(blockIDMap[4][checkZ][checkX], registryIDMap[4][checkZ][checkX]))) {
                        tSuccess = ignoreMap[4][checkZ][checkX];
                    }
                }
            }
            sourceMirrors.removeIf(mirror -> !(worldObj.getTileEntity(mirror.posX, mirror.posY, mirror.posZ) instanceof SunBoilerMirror));
            return tSuccess;
        }
        return mStructureOkay;
    }

    static {
        LH.add("gt.tooltip.multiblock.example.complex.1", "5x5x2 of Stainless Steel Walls");
        LH.add("gt.tooltip.multiblock.example.complex.2", "Main Block centered on Side-Bottom and facing outwards");
        LH.add("gt.tooltip.multiblock.example.complex.3", "Input and Output at any Blocks");
    }
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (isServerSide()){
        ItemStack equippedItem=aPlayer.getCurrentEquippedItem();
        if (!(OM.is(OD_USB_STICKS[0],equippedItem))) return false;
        NBTTagCompound aNBT = UT.NBT.make();
        UT.NBT.setNumber(aNBT, NBT_TARGET_X, this.xCoord);
        UT.NBT.setNumber(aNBT, NBT_TARGET_Y, this.yCoord);
        UT.NBT.setNumber(aNBT, NBT_TARGET_Z, this.zCoord);

        if (equippedItem.hasTagCompound()) {
            if (clickDoubleCheck) {
                equippedItem.getTagCompound().setTag(NBT_USB_DATA, aNBT);
                equippedItem.getTagCompound().setByte(NBT_USB_TIER, (byte)1);
                aPlayer.addChatMessage(new ChatComponentText(LH.get(kMessages.SUN_BOILER_0)));
                clickDoubleCheck=false;
            } else {
                aPlayer.addChatMessage(new ChatComponentText(LH.get(kMessages.SUN_BOILER_1)));
                clickDoubleCheck=true;
            }
        }
        if (!equippedItem.hasTagCompound()){
            equippedItem.setTagCompound(UT.NBT.make());
            equippedItem.getTagCompound().setTag(NBT_USB_DATA, aNBT);
            equippedItem.getTagCompound().setByte(NBT_USB_TIER, (byte)1);
            aPlayer.addChatMessage(new ChatComponentText(LH.get(kMessages.SUN_BOILER_0)));
        }
        }
        return true;
    }
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer,aIsServerSide);
        if (aIsServerSide) {
            //collectEnergy
            sourceMirrors.forEach(mirror-> this.mEnergy+=mirror.tRate);
            //emitEnergy
            if (mEnergy >= mRate) {
                ITileEntityEnergy.Util.emitEnergyToNetwork(mEnergyTypeEmitted, mRate, 1,  this);
                mEnergy -= mRate;
            }
            if (mEnergy < 0) mEnergy = 0;
        }
    }
    @Override
    public boolean onTickCheck(long aTimer) {
        if (aTimer%100==0)clickDoubleCheck=false;
        return super.onTickCheck(aTimer);
    }
        @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.1"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.2"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.3"));
        super.addToolTips(aList, aStack, aF3_H);
    }
    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return aX >= xCoord - (SIDE_X_NEG == mFacing ? 0 : SIDE_X_POS == mFacing ? 3 : machineX) &&
                aY >= yCoord - (SIDE_Y_NEG == mFacing ? 0 : SIDE_Y_POS == mFacing ? 3 : machineYmax) &&
                aZ >= zCoord - (SIDE_Z_NEG == mFacing ? 0 : SIDE_Z_POS == mFacing ? 3 : machineZ) &&
                aX <= xCoord + (SIDE_X_POS == mFacing ? 0 : SIDE_X_NEG == mFacing ? 3 : machineX) &&
                aY <= yCoord + (SIDE_Y_POS == mFacing ? 0 : SIDE_Y_NEG == mFacing ? 3 : machineX) &&
                aZ <= zCoord + (SIDE_Z_POS == mFacing ? 0 : SIDE_Z_NEG == mFacing ? 3 : machineZ);
    }
    @Override public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {
        return aEmitting && aEnergyType == mEnergyTypeEmitted;}
    @Override public boolean isEnergyEmittingTo(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return aSide == SIDE_TOP && super.isEnergyEmittingTo(aEnergyType, aSide, aTheoretical);}
    @Override public long getEnergyOffered(TagData aEnergyType, byte aSide, long aSize) {
        return Math.min(mRate, mEnergy);}
    @Override public long getEnergySizeOutputRecommended(TagData aEnergyType, byte aSide) {return mRate;}
    @Override public long getEnergySizeOutputMin(TagData aEnergyType, byte aSide) {return mRate;}
    @Override public long getEnergySizeOutputMax(TagData aEnergyType, byte aSide) {return mRate;}
    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return mEnergyTypeEmitted.AS_LIST;}

    public static class sourceMirror {
        public sourceMirror(int posX,int posY,int posZ,int tRate) {
            this.posX=posX;
            this.posY=posY;
            this.posZ=posZ;
            this.tRate=tRate;
        }
        int posX,posY,posZ,tRate;
    }

}
