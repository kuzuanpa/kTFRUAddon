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

package cn.kuzuanpa.ktfruaddon.tile.machine;

import cn.kuzuanpa.ktfruaddon.api.code.CodeTranslate;
import cn.kuzuanpa.ktfruaddon.api.code.OreScanner;
import cn.kuzuanpa.ktfruaddon.api.tile.ICircuitChangeableTileEntity;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import cpw.mods.fml.common.FMLLog;
import gregapi.old.Textures;
import gregapi.render.IIconContainer;
import gregapi.tileentity.base.TileEntityBase01Root;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static gregapi.data.CS.PX_P;
import static gregapi.data.CS.SIDES_VALID;

public class MachineCodeUtil extends MultiTileEntityBasicMachine implements ICircuitChangeableTileEntity {
    public OreScanner oreVeinScanner;
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        setComputers(ICircuitChangeableTileEntity.loadCircuitInfo(aNBT));
       // oreVeinScanner = new OreScanner(0,xCoord,yCoord,zCoord, worldObj,true,true);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        ICircuitChangeableTileEntity.saveCircuitInfo(aNBT,getComputers());
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
    if (isServerSide()) {
        openGUI(aPlayer, aSide);
        try {
            for (int i=0;i<this.ACCESSIBLE_SLOTS.length;i++) FMLLog.log(Level.FATAL,""+ CodeTranslate.itemToCode(slot(i)));
            //for (ItemStack computer : getComputers()) {
            //    worldObj.spawnEntityInWorld(new EntityItem(worldObj,xCoord,yCoord + 2,zCoord,computer));
            //}
        }catch (Throwable ignored) {}
        FMLLog.log(Level.FATAL, slot(0)==null?"/": String.valueOf(Block.getBlockFromItem(slot(0).getItem()).getMaterial().equals(Material.ice)));
    }
    return false;
}

    @Override
    public NBTTagCompound writeItemNBT2(NBTTagCompound aNBT) {
        ICircuitChangeableTileEntity.saveCircuitInfo(aNBT,getComputers());
        return super.writeItemNBT2(aNBT);
    }

    public void onTick2(long aTimer, boolean isServerside){

    }
    public boolean setBlockBounds2(Block aBlock, int aRenderPass, boolean[] aShouldSideBeRendered) {
        switch (aRenderPass) {
            case 0:
                return box(aBlock, PX_P[0] + 0.001, PX_P[0], PX_P[0] + 0.001, PX_P[16] - 0.001, PX_P[5], PX_P[16] - 0.001);
            case 1:
                return box(aBlock, PX_P[0] + 0.0001, PX_P[6], PX_P[0], PX_P[16], PX_P[16], PX_P[16]);
        }
        return true;
    }
    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        super.addToolTips(aList, aStack, aF3_H);
        addCircuitTooltip(aList, aStack, aF3_H);
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.machine.code";
    }

    @Override
    public int getRenderPasses2(Block aBlock, boolean[] aShouldSideBeRendered) {
        return 2;
    }

    public void genMultiTileName(){
        TileEntity.classToNameMap.keySet().stream().forEach(clazz-> {
            try {
                Object obj = ((Class<?>)clazz).newInstance();
                if(!(obj instanceof ITileEntityMultiBlockController))return;
                TileEntityBase01Root tile = (TileEntityBase01Root)obj ;
                FMLLog.log(Level.FATAL, "S:"+tile.getTileEntityName()+"=");
            }catch (Throwable t){}
        });
    }
    public static IIconContainer
            sTextureCommon= new Textures.BlockIcons.CustomIcon("machines/cruciblemodel/common"),
            sTextureCommosn= new Textures.BlockIcons.CustomIcon("machines/cruciblemodel/commsson");

    List<ItemStack> computerList = new ArrayList<>();
    @Override
    public @NotNull List<ItemStack> getComputers() {
        return computerList;
    }

    @Override
    public @NotNull Map<ComputePower, Long> getComputePowerRequired() {
        return ComputePower.Normal.asMap(4000);
    }

    @Override
    public void setComputers(List<ItemStack> computers) {
        computerList = computers;
    }

    @Override
    public boolean[] getValidSides() {
        return SIDES_VALID;
    }
}
