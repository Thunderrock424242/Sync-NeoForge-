package com.thunder.Sync.client.core;

import me.ichun.mods.ichunutil.common.iChunUtil;
import com.thunder.Sync.client.entity.EntityShellDestruction;
import com.thunder.Sync.client.render.RenderShellDestruction;
import com.thunder.Sync.client.render.TileRendererDualVertical;
import com.thunder.Sync.client.render.TileRendererTreadmill;
import me.ichun.mods.sync.common.Sync;
import me.ichun.mods.sync.common.core.ProxyCommon;
import me.ichun.mods.sync.common.tileentity.TileEntityDualVertical;
import me.ichun.mods.sync.common.tileentity.TileEntityTreadmill;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ProxyClient extends ProxyCommon
{
    @Override
    public void preInitMod()
    {
        super.preInitMod();

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDualVertical.class, new TileRendererDualVertical());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTreadmill.class, new TileRendererTreadmill());

        iChunUtil.proxy.registerMinecraftKeyBind(Minecraft.getMinecraft().gameSettings.keyBindUseItem);
        iChunUtil.proxy.registerMinecraftKeyBind(Minecraft.getMinecraft().gameSettings.keyBindAttack);

        Sync.eventHandlerClient = new EventHandlerClient();
        MinecraftForge.EVENT_BUS.register(Sync.eventHandlerClient);

        RenderingRegistry.registerEntityRenderingHandler(EntityShellDestruction.class, new RenderShellDestruction.RenderFactory());

        if (!Minecraft.getMinecraft().getFramebuffer().isStencilEnabled()) //We use stencil, make sure it's enabled
            Minecraft.getMinecraft().getFramebuffer().enableStencil();
    }
}
