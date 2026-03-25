package cn.kuzuanpa.ktfruaddon.DreamPlanner.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
public abstract class AbstractPacket {
    public abstract void encode(ChannelHandlerContext ctx, ByteBuf buffer);
    public abstract void decode(ChannelHandlerContext ctx, ByteBuf buffer);
    public abstract void handleClientSide(EntityPlayer player);
    public abstract void handleServerSide(EntityPlayer player);
}
