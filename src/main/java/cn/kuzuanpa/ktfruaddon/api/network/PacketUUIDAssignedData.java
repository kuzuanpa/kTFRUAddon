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
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import net.minecraft.world.IBlockAccess;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

public class PacketUUIDAssignedData implements IPacket {
    public static Map<Byte, BiConsumer<UUID,byte[]>> typeMap = new HashMap<>();
    protected UUID receiver;
    protected byte typeID;
    protected byte@Nullable [] data=null;
    public PacketUUIDAssignedData() {/**/}
    public PacketUUIDAssignedData(byte typeID, UUID receiver, byte @Nullable ... data) {
        this.receiver=receiver;
        this.typeID = typeID;
        this.data=data;
    }


    @Override
    public byte getPacketID() {
        return 18;
    }

    @Override
    public ByteArrayDataOutput encode() {
        ByteArrayDataOutput aData = ByteStreams.newDataOutput();
        aData.writeByte(this.typeID);
        aData.writeLong(this.receiver.getLeastSignificantBits());
        aData.writeLong(this.receiver.getMostSignificantBits());
        aData.writeBoolean(this.data == null);
        if(this.data == null)return aData;
        aData.writeInt(this.data.length);
        aData.write(this.data);
        return aData;
    }
    @Override
    public IPacket decode(ByteArrayDataInput aData) {
        byte typeID = aData.readByte();
        long UUIDDown = aData.readLong();
        long UUIDup = aData.readLong();
        if(aData.readBoolean())return new PacketUUIDAssignedData(typeID, new UUID(UUIDup, UUIDDown));
        int length = aData.readInt();
        byte[] data = new byte[length];
        aData.readFully(data);
        return new PacketUUIDAssignedData(typeID, new UUID(UUIDup, UUIDDown), data);
    }

    @Override
    public void process(IBlockAccess aWorld, INetworkHandler aNetworkHandler) {
        if(typeMap.get(typeID) != null)typeMap.get(typeID).accept(receiver, data);
    }
}
