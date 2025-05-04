/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

package cn.kuzuanpa.ktfruaddon.api.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.FMLLog;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.DimensionManager;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class PacketContainerButtonPressed implements IPacket {
    protected int dimID,x,y,z,id;
    protected byte@Nullable [] data=null;
    public PacketContainerButtonPressed() {/**/}
    public PacketContainerButtonPressed(int dimID, int x,int y,int z, int id, byte @Nullable ... data) {
        this.dimID=dimID;
        this.x=x;
        this.y=y;
        this.z=z;
        this.id=id;
        this.data=data;
    }


    @Override
    public byte getPacketID() {
        return 17;
    }

    @Override
    public ByteArrayDataOutput encode() {
        ByteArrayDataOutput aData = ByteStreams.newDataOutput();
        aData.writeInt(this.dimID);
        aData.writeInt(this.x);
        aData.writeInt(this.y);
        aData.writeInt(this.z);
        aData.writeInt(this.id);
        aData.writeBoolean(this.data == null);
        if(this.data == null)return aData;
        aData.writeInt(this.data.length);
        aData.write(this.data);
        return aData;
    }
    @Override
    public IPacket decode(ByteArrayDataInput aData) {
        int dimID = aData.readInt();
        int x = aData.readInt();
        int y = aData.readInt();
        int z = aData.readInt();
        int id = aData.readInt();
        if(aData.readBoolean())return new PacketContainerButtonPressed(dimID, x,y,z,id);
        int length = aData.readInt();
        byte[] data = new byte[length];
        aData.readFully(data);
        return new PacketContainerButtonPressed(dimID, x,y,z,id, data);
    }

    @Override
    public void process(IBlockAccess aWorld, INetworkHandler aNetworkHandler) {
        TileEntity t = DimensionManager.getWorld(dimID).getTileEntity(x,y,z);
        if(!(t instanceof ITileReceiveContainerButtonClick)){
            FMLLog.log(Level.ERROR,String.format("[ktfruaddon] Not ITileReceiveContainerButtonClick tile found in %s, %s, %s@%s, data: %s",x,y,z,dimID, Arrays.toString(data)));return;
        }
        try{((ITileReceiveContainerButtonClick) t).onContainerButtonClick(id, data);}catch (Throwable throwable){
            FMLLog.log(Level.ERROR, throwable, String.format("[ktfruaddon] Exception happened when doContainerButtonClick in %s, %s, %s@%s, data: %s", x,y,z,dimID, Arrays.toString(data)));
        }
    }
}
