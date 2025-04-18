package com.thunder.Sync.common.packet;

import io.netty.buffer.ByteBuf;
import me.ichun.mods.ichunutil.common.core.network.AbstractPacket;
import com.thunder.Sync.common.Sync;
import me.ichun.mods.sync.common.shell.ShellState;
import com.thunder.Sync.common.tileentity.TileEntityDualVertical;
import com.thunder.Sync.common.tileentity.TileEntityShellStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketShellState extends AbstractPacket
{
    public boolean remove;
    public TileEntityDualVertical dv;

    public BlockPos pos;
    public int dim;

    public float buildProgress;
    public float powerReceived;

    public String name;
    public String dimName;
    public boolean isConstructor;
    public boolean isHome;

    public NBTTagCompound tag;

    public PacketShellState(){}

    public PacketShellState(TileEntityDualVertical dv1, boolean remove)
    {
        this.remove = remove;
        this.dv = dv1;
    }

    @Override
    public void writeTo(ByteBuf buffer)
    {
        buffer.writeBoolean(remove);
        dv.writeShellStateData(buffer);
    }

    @Override
    public void readFrom(ByteBuf buffer)
    {
        remove = buffer.readBoolean();

        //Create shell state
        pos = BlockPos.fromLong(buffer.readLong());
        dim = buffer.readInt();

        buildProgress = buffer.readFloat();
        powerReceived = buffer.readFloat();

        name = ByteBufUtils.readUTF8String(buffer);

        dimName = ByteBufUtils.readUTF8String(buffer);

        isConstructor = buffer.readBoolean();

        isHome = buffer.readBoolean();

        if(!isConstructor)
        {
            tag = ByteBufUtils.readTag(buffer);
        }
    }

    @Override
    public void execute(Side side, EntityPlayer player)
    {
        handleClient();
    }

    @Override
    public Side receivingSide()
    {
        return Side.CLIENT;
    }

    @SideOnly(Side.CLIENT)
    public void handleClient()
    {
        //Create shell state
        Minecraft mc = Minecraft.getMinecraft();

        ShellState state = new ShellState(pos, dim);

        if(remove)
        {
            //Remove shell state
            for(int i = Sync.eventHandlerClient.shells.size() - 1; i >= 0; i--)
            {
                ShellState state1 = Sync.eventHandlerClient.shells.get(i);
                if(state1.matches(state))
                {
                    Sync.eventHandlerClient.shells.remove(i);
                }
            }
        }
        else
        {
            state.buildProgress = buildProgress;
            state.powerReceived = powerReceived;

            state.name = name;

            state.dimName = dimName;

            state.isConstructor = isConstructor;

            state.isHome = isHome;

            boolean add = true;
            for(int i = Sync.eventHandlerClient.shells.size() - 1; i >= 0; i--)
            {
                ShellState state1 = Sync.eventHandlerClient.shells.get(i);
                if(state1.matches(state))
                {
                    Sync.eventHandlerClient.shells.remove(i);
                }
                if(!Sync.eventHandlerClient.shells.contains(state))
                {
                    Sync.eventHandlerClient.shells.add(i, state);
                }
                add = false;
            }

            if(add)
            {
                Sync.eventHandlerClient.shells.add(state);
            }

            state.playerState = TileEntityShellStorage.createPlayer(mc.world, mc.player.getUniqueID(), mc.player.getName());

            if(!state.isConstructor)
            {
                NBTTagCompound tag = this.tag;
                if(tag.hasKey("Inventory"))
                {
                    TileEntityDualVertical.addShowableEquipToPlayer(state.playerState, tag);
                }
            }
        }
    }
}
