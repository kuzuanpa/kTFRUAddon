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

import cn.kuzuanpa.ktfruaddon.api.client.fx.FxRenderBlockOutline;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.IBlockAccess;

public class PacketFxBlockOutline implements IPacket {
    protected int x, y, z,color,duration;
    protected float thickness;
    public PacketFxBlockOutline() {/**/}

    public PacketFxBlockOutline(int x, int y, int z, int color, int duration, float thickness) {
        this. x= x;
        this.y = y;
        this.z = z;
        this. color= color;
        this. duration= duration;
        this.thickness = thickness;
    }

    public PacketFxBlockOutline(ChunkCoordinates coord, int color, int duration, float thickness) {
        this.x= coord.posX;
        this.y = coord.posY;
        this.z = coord.posZ;
        this. color= color;
        this. duration= duration;
        this.thickness = thickness;
    }

        @Override
        public byte getPacketID() {
            return 16;
        }

        @Override
        public ByteArrayDataOutput encode() {
            ByteArrayDataOutput aData = ByteStreams.newDataOutput();
            aData.writeInt(this.x);
            aData.writeInt(this.y);
            aData.writeInt(this.z);
            aData.writeInt(this.color);
            aData.writeInt(this.duration);
            aData.writeFloat(this.thickness);
            return aData;
        }

        @Override
        public IPacket decode(ByteArrayDataInput aData) {
            return new PacketFxBlockOutline(aData.readInt(), aData.readInt(), aData.readInt(), aData.readInt(), aData.readInt(), aData.readFloat());
        }

        @Override
        public void process(IBlockAccess aWorld, INetworkHandler aNetworkHandler) {
            if(thickness < 0 || duration < 0)FxRenderBlockOutline.removeBlockOutlineToRender(new ChunkCoordinates(x,y,z));
            else FxRenderBlockOutline.addBlockOutlineToRender(new ChunkCoordinates(x,y,z), color, thickness,System.currentTimeMillis() + duration);
        }

}
