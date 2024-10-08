/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.tile.energy.generator;

import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.energy.IMeterDetectable;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.*;

public class DebugGenerator extends TileEntityBase09FacingSingle implements ITileEntityEnergy, IMeterDetectable {
    public long mRate = 0, mAmount = 1;
    public TagData mEnergyTypeEmitted = TD.Energy.EU;
    public static final TagData[] availEmitEnergy = {TD.Energy.EU,TD.Energy.RU,TD.Energy.KU,TD.Energy.HU,TD.Energy.LU,TD.Energy.QU,TD.Energy.CU,TD.Energy.MU,TD.Energy.TU,TD.Energy.NU,TD.Energy.AU, };
    public int emitPointer;

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT,NBT_OUTPUT, mRate);
        UT.NBT.setNumber(aNBT,NBT_OUTPUT+".amount",mAmount);
        UT.NBT.setNumber(aNBT,NBT_ENERGY_EMITTED,emitPointer);
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        mRate = aNBT.getLong(NBT_OUTPUT);
        mAmount = aNBT.getLong(NBT_OUTPUT+".amount");
        emitPointer = aNBT.getInteger(NBT_ENERGY_EMITTED);

        if(emitPointer < availEmitEnergy.length)mEnergyTypeEmitted=availEmitEnergy[emitPointer];
        else emitPointer=0;

    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if (aIsServerSide) {
            ITileEntityEnergy.Util.emitEnergyToNetwork(mEnergyTypeEmitted, mRate, mAmount, this);
        }
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        super.addToolTips(aList, aStack, aF3_H);
        aList.add("Debug use Only");
        aList.add("Tools:");
        aList.add("screw driver: change energy type");
        aList.add("soft hammer: rate = 0");
        aList.add("monkey wrench: rate += 1");
        aList.add("axe: rate += 100");
        aList.add("crowbar: rate += 1000");
        aList.add("cutter: rate -= 1");
        aList.add("chisel: rate -= 100");
        aList.add("knife: rate -= 1000");
        aList.add("saw: ampere += 1");
        aList.add("plunger: ampere -= 1");
    }

    public final static IIconContainer sTextureSides = new Textures.BlockIcons.CustomIcon("machines/generators/motor_manual/KU/colored/sides");
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return BlockTextureDefault.get(sTextureSides);
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        long rReturn = super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
        if (rReturn > 0) return rReturn;

        if (isClientSide()) return 0;

        if(aTool.equals(TOOL_screwdriver)){
            if(emitPointer < availEmitEnergy.length)emitPointer++;
            else emitPointer=0;
            mEnergyTypeEmitted=availEmitEnergy[emitPointer];
        }
        if(aTool.equals(TOOL_softhammer)){
            mRate=0;
        }
        if(aTool.equals(TOOL_monkeywrench)){
            mRate+=1;
        }
        if(aTool.equals(TOOL_axe)){
            mRate+=100;
        }
        if(aTool.equals(TOOL_crowbar)){
            mRate+=1000;
        }
        if(aTool.equals(TOOL_cutter)){
            mRate-=1;
        }
        if(aTool.equals(TOOL_chisel)){
            mRate-=100;
        }
        if(aTool.equals(TOOL_knife)){
            mRate-=1000;
        }
        if(aTool.equals(TOOL_saw)){
            mAmount+=1;
        }
        if(aTool.equals(TOOL_plunger)){
            mAmount-=1;
        }
        if (aChatReturn != null) {
            aChatReturn.add("Emitting " + mRate + " " + mEnergyTypeEmitted.getLocalisedChatNameShort() + LH.Chat.WHITE + " /A * " + mAmount + LH.Chat.CYAN + " A /t");
        }
        if (getFacingTool() != null && aTool.equals(getFacingTool())) {byte aTargetSide = UT.Code.getSideWrenching(aSide, aHitX, aHitY, aHitZ); if (getValidSides()[aTargetSide]) {byte oFacing = mFacing; mFacing = aTargetSide; updateClientData(); causeBlockUpdate(); onFacingChange(oFacing); return 10000;}}
        return 0;
    }

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        return aAmount;
    }

    @Override public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {return aEmitting && aEnergyType == mEnergyTypeEmitted;}

    @Override public boolean isEnergyAcceptingFrom(TagData aEnergyType, byte aSide, boolean aTheoretical) {return true; }
    @Override public boolean isEnergyEmittingTo(TagData aEnergyType, byte aSide, boolean aTheoretical) {return aSide == mFacing && super.isEnergyEmittingTo(aEnergyType, aSide, aTheoretical);}
    @Override public long getEnergyOffered(TagData aEnergyType, byte aSide, long aSize) {return mRate;}
    @Override public long getEnergySizeOutputRecommended(TagData aEnergyType, byte aSide) {return mRate;}
    @Override public long getEnergySizeOutputMin(TagData aEnergyType, byte aSide) {return mRate;}
    @Override public long getEnergySizeOutputMax(TagData aEnergyType, byte aSide) {return mRate;}
    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return mEnergyTypeEmitted.AS_LIST;}

    @Override public String getTileEntityName() {return "ktfru.multitileentity.generator.debug";}
    @Override
    public boolean canDrop(int aSlot) {
        return false;
    }
}
