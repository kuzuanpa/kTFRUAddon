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

package cn.kuzuanpa.ktfruaddon.DreamPlanner.network;

import cn.kuzuanpa.ktfruaddon.DreamPlanner.client.gui.GuiTerminal;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.ITransferable;
import cn.kuzuanpa.ktfruaddon.DreamPlanner.transmittable.TransferableStack;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class PacketDeltaSync extends AbstractPacket {
    public List<TransferableStack> changes;

    public PacketDeltaSync() {
        this.changes = new ArrayList<>();
    }

    public PacketDeltaSync(List<TransferableStack> changes) {
        this.changes = changes;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, ByteBuf buf) {
        buf.writeInt(changes.size());
        for (TransferableStack change : changes) {
            ByteBufUtils.writeTag(buf, ITransferable.save(change.type));
            buf.writeLong(change.amount);
        }
    }

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf buf) {
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            NBTTagCompound dummyStack = ByteBufUtils.readTag(buf);
            long newAmount = buf.readLong();

            if (dummyStack != null) {
                ITransferable entry = ITransferable.load(dummyStack);
                this.changes.add(entry.make(newAmount));
            }
        }

    }

    @Override
    public void handleClientSide(EntityPlayer player) {
        if(! (Minecraft.getMinecraft().currentScreen instanceof GuiTerminal))return;
        GuiTerminal gui = (GuiTerminal) Minecraft.getMinecraft().currentScreen;
        gui.onDeltaSyncReceived(changes);
    }

    @Override
    public void handleServerSide(EntityPlayer player) {
    }
}