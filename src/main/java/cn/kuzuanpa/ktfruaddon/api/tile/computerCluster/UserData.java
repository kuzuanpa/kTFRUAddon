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

package cn.kuzuanpa.ktfruaddon.api.tile.computerCluster;

import cn.kuzuanpa.ktfruaddon.api.code.WorldPos;

import java.io.*;
import java.util.*;

public class UserData {
    public IComputerClusterUser user;
    public byte state;
    /**Where the user sits, kept here because {@link #user} is null on the client. Null when unknown.**/
    public WorldPos pos;
    /**Server tick of the last {@link ComputerCluster#updateUserData} call, -1 when never updated.**/
    public long lastUpdatedTick = -1;
    public Map<ComputePower, Long> consumingPower = new HashMap<>();
    public Queue<Byte> events = new ArrayDeque<>();
    public Queue<String> eventExtra = new ArrayDeque<>();
    public UserData(IComputerClusterUser user){this.user = user;}

    public UserData copy() {
        UserData data = new UserData(user);
        data.state=this.state;
        data.pos=this.pos;
        data.lastUpdatedTick=this.lastUpdatedTick;
        data.consumingPower = new HashMap<>(this.consumingPower);
        data.events = new ArrayDeque<>(this.events);
        data.eventExtra = new ArrayDeque<>(this.eventExtra);
        return data;
    }

    public static byte[] serialize(UserData data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes;
        try (DataOutputStream dos = new DataOutputStream(baos)) {
            dos.writeByte(data.state);
            dos.writeBoolean(data.pos != null);
            if (data.pos != null) {
                dos.writeInt(data.pos.dim);
                dos.writeInt(data.pos.x);
                dos.writeShort((short) data.pos.y);
                dos.writeInt(data.pos.z);
            }
            dos.writeInt(data.consumingPower.size());
            for (Map.Entry<ComputePower, Long> entry : data.consumingPower.entrySet()) {
                ComputePower key = entry.getKey();
                Long amount = entry.getValue();
                dos.writeByte(key.ordinal());
                dos.writeLong(amount);
            }
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

    public static UserData deserialize(byte [] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bais);
        byte state = dis.readByte();
        WorldPos pos = null;
        int dimensionId = 0;
        if (dis.readBoolean()) {
            dimensionId = dis.readInt();
            pos = new WorldPos(dis.readInt(), dis.readShort(), dis.readInt(), dimensionId);
        }
        int length = ComputerClusterClientData.checkedCount(dis.readInt());
        Map<ComputePower, Long> consumingPower = new HashMap<>();
        for (int i = 0; i < length; i++) {
            consumingPower.put(ComputePower.getType(dis.readByte()), dis.readLong());
        }
        int eventCount = ComputerClusterClientData.checkedCount(dis.readInt());

        UserData data = new UserData(null);
        data.state=state;
        data.pos=pos;
        data.consumingPower = consumingPower;
        for (int i = 0; i < eventCount; i++) {
            data.events.add(dis.readByte());
            data.eventExtra.add(dis.readUTF());
        }
        dis.close();
        bais.close();
        return data;
    }
}
