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
package cn.kuzuanpa.ktfruaddon.api.tile.computerCluster;

import cn.kuzuanpa.ktfruaddon.api.code.SingleEntry;
import cn.kuzuanpa.ktfruaddon.api.code.WorldPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

public class ControllerData{
    public World world;
    public WorldPos pos;
    public byte state;
    @NotNull public Map.Entry<ComputePower, Long> power = new SingleEntry<>(ComputePower.Normal, 0L);
    public Queue<Byte> events = new ArrayDeque<>();
    public Queue<String> eventExtra = new ArrayDeque<>();
    public ControllerData(World world, WorldPos pos){
        this.world=world;
        this.pos=pos;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ControllerData)) return false;

        ControllerData that = (ControllerData) o;
        return pos.equals(that.pos);
    }

    @Override
    public int hashCode() {
        return pos.hashCode();
    }

    public ControllerData copy() {
        ControllerData data = new ControllerData(this.world,this.pos);
        data.state=this.state;
        data.power=this.power;
        data.events = new ArrayDeque<>(this.events);
        data.eventExtra = new ArrayDeque<>(this.eventExtra);
        return data;
    }


    public static byte[] serialize(ControllerData data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes;
        try (DataOutputStream dos = new DataOutputStream(baos)) {
            dos.writeInt(data.pos.dim);
            dos.writeInt(data.pos.x);
            dos.writeShort((short) data.pos.y);
            dos.writeInt(data.pos.z);
            dos.writeByte(data.state);
            dos.writeByte(data.power.getKey().ordinal());
            dos.writeLong(data.power.getValue());
            dos.writeInt(data.events.size());
            Iterator<Byte> eventIterator = data.events.iterator();
            Iterator<String> extraIterator = data.eventExtra.iterator();
            while (eventIterator.hasNext()) {
                dos.writeByte(eventIterator.next());
                dos.writeUTF(extraIterator.hasNext() ? extraIterator.next() : "");
            }
            dos.flush();
            bytes = baos.toByteArray();
        }
        baos.close();

        return bytes;
    }

    public static ControllerData deserialize(byte [] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bais);

        int worldID = dis.readInt();
        int posX = dis.readInt();
        short posY = dis.readShort();
        int posZ = dis.readInt();
        byte state = dis.readByte();
        byte powerType = dis.readByte();
        long powerAmount = dis.readLong();
        int eventCount = ComputerClusterClientData.checkedCount(dis.readInt());
        ControllerData data = new ControllerData(DimensionManager.getWorld(worldID),new WorldPos(posX,posY,posZ, worldID));
        data.state=state;
        data.power = new SingleEntry<>(ComputePower.getType(powerType),powerAmount);
        for (int i = 0; i < eventCount; i++) {
            data.events.add(dis.readByte());
            data.eventExtra.add(dis.readUTF());
        }
        dis.close();
        bais.close();
        return data;
    }
}
