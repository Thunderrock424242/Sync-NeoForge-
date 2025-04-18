package com.thunder.Sync.common.core;

import me.ichun.mods.ichunutil.common.core.network.PacketChannel;
import me.ichun.mods.ichunutil.common.item.ItemGeneric;
import com.thunder.Sync.common.Sync;
import com.thunder.Sync.common.block.BlockDualVertical;
import com.thunder.Sync.common.block.EnumType;
import com.thunder.Sync.common.creativetab.CreativeTabSync;
import com.thunder.Sync.common.item.ItemShellBase;
import com.thunder.Sync.common.item.ItemTreadmill;
import me.ichun.mods.sync.common.packet.*;
import com.thunder.Sync.common.tileentity.TileEntityShellConstructor;
import com.thunder.Sync.common.tileentity.TileEntityShellStorage;
import com.thunder.Sync.common.tileentity.TileEntityTreadmill;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ProxyCommon
{
    public void preInitMod()
    {
        Sync.creativeTabSync = new CreativeTabSync();

        Sync.blockDualVertical = (new BlockDualVertical()).setRegistryName("sync", "block_multi").setLightLevel(0.5F).setHardness(2.0F).setTranslationKey("sync.block.multi");

        Sync.itemShellConstructor = new ItemShellBase(EnumType.CONSTRUCTOR).setRegistryName("sync", "item_shell_constructor").setTranslationKey("Sync_ShellConstructor").setCreativeTab(Sync.creativeTabSync);
        Sync.itemShellStorage = new ItemShellBase(EnumType.STORAGE).setRegistryName("sync", "item_shell_storage").setTranslationKey("Sync_ShellStorage").setCreativeTab(Sync.creativeTabSync);
        Sync.itemTreadmill = new ItemTreadmill().setRegistryName("sync", "item_treadmill").setTranslationKey("Sync_Treadmill").setCreativeTab(Sync.creativeTabSync);
        Sync.itemSyncCore = (new ItemGeneric()).setRegistryName("sync", "item_placeholder").setTranslationKey("Sync_SyncCore").setCreativeTab(Sync.creativeTabSync);

        GameRegistry.registerTileEntity(TileEntityShellConstructor.class, "Sync_TEShellConstructor");
        GameRegistry.registerTileEntity(TileEntityShellStorage.class, "Sync_TEShellStorage");
        GameRegistry.registerTileEntity(TileEntityTreadmill.class, "Sync_TETreadmill");

        Sync.eventHandlerServer = new EventHandlerServer();
        MinecraftForge.EVENT_BUS.register(Sync.eventHandlerServer);

        Sync.channel = new PacketChannel("Sync", PacketSyncRequest.class, PacketZoomCamera.class, PacketPlayerDeath.class, PacketUpdatePlayerOnZoomFinish.class, PacketPlayerEnterStorage.class, PacketShellDeath.class, PacketClearShellList.class, PacketShellState.class, PacketNBT.class);
    }

}
