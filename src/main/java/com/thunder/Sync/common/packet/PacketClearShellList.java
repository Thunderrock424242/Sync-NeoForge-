package com.thunder.Sync.common.packet;

import io.netty.buffer.ByteBuf;
import me.ichun.mods.ichunutil.common.core.network.AbstractPacket;
import com.thunder.Sync.common.Sync;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class PacketClearShellList extends AbstractPacket
{
    public PacketClearShellList(){}

    @Override
    public void writeTo(ByteBuf buffer)
    {
    }

    @Override
    public void readFrom(ByteBuf buffer)
    {
    }

    @Override
    public void execute(Side side, EntityPlayer player)
    {
        Sync.eventHandlerClient.shells.clear();
    }

    @Override
    public Side receivingSide()
    {
        return Side.CLIENT;
    }
}
