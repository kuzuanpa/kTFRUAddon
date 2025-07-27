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

import cn.kuzuanpa.ktfruaddon.api.tile.ICircuitChangeableTileEntity;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import gregapi.old.Textures;
import gregapi.render.IIconContainer;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CircuitChanger extends MultiTileEntityBasicMachine implements ICircuitChangeableTileEntity {

@Override
public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
    if (isServerSide()) {
        openGUI(aPlayer, aSide);
        try {
            for (int i=0;i<this.ACCESSIBLE_SLOTS.length;i++) {
                if(slot(i).hasTagCompound() && slot(i).getTagCompound().hasKey("ktfru.circuits")){
                    List<ItemStack> stackList = ICircuitChangeableTileEntity.loadCircuitInfo(slot(i).getTagCompound());
                    for (int j=0; j < slot(i).stackSize; j++ )for (ItemStack computer : stackList) {
                        worldObj.spawnEntityInWorld(new EntityItem(worldObj,xCoord,yCoord + 2,zCoord,computer));
                    }
                    ICircuitChangeableTileEntity.saveCircuitInfo(slot(i).getTagCompound(),new ArrayList<>());
                };
            }
        }catch (Throwable ignored) {}
    }
    return false;
}

    public void onTick2(long aTimer, boolean isServerside){

    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.machine.cchanger";
    }

    public static IIconContainer
            sTextureCommon= new Textures.BlockIcons.CustomIcon("machines/cruciblemodel/common"),
            sTextureCommosn= new Textures.BlockIcons.CustomIcon("machines/cruciblemodel/commsson");

    List<ItemStack> computerList = new ArrayList<>();
    @Override
    public List<ItemStack> getComputers() {
        return computerList;
    }

    @Override
    public Map<ComputePower, Long> getComputePowerRequired() {
        return ComputePower.Normal.asMap(4000);
    }

    @Override
    public void setComputers(List<ItemStack> computers) {
        computerList = computers;
    }
}
