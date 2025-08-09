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


package cn.kuzuanpa.ktfruaddon.tile.multiblock.parts;

import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import net.minecraft.nbt.NBTTagCompound;

import static gregapi.data.CS.*;


public class CommonPart extends MultiTileEntityMultiBlockPart {
    public boolean isOpaque = T;

    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_OPAQUE)) isOpaque = aNBT.getBoolean(NBT_OPAQUE);
    }
    @Override
    public int getLightOpacity(){
        return mDesign==1?255:0;
    }
    //When this part be hidden, This will make adjoining block's side rendering properly.
    @Override public boolean isSurfaceOpaque2       (byte aSide) {return isOpaque && mDesign!=1;}
    @Override
    public String getTileEntityName(){
        return "kfru.multitileentity.multiblock.commonpart";
    }
}
