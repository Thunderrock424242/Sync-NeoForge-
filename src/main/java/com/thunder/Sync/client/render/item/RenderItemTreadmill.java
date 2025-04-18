package com.thunder.Sync.client.render.item;

import me.ichun.mods.sync.client.model.ModelTreadmill;
import com.thunder.Sync.client.render.TileRendererTreadmill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderItemTreadmill extends TileEntitySpecialRenderer<RenderItemTreadmill.ItemTreadmillRenderHack> {
    private static final ModelTreadmill modelTreadmill = new ModelTreadmill();

    @Override
    public void render(RenderItemTreadmill.ItemTreadmillRenderHack entity, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {

        GlStateManager.scale(0.33F, 0.33F, 0.33F);
        GlStateManager.translate(1F, 1F, 2F);
        GlStateManager.rotate(180, 1 ,0 ,0);

        Minecraft.getMinecraft().getTextureManager().bindTexture(TileRendererTreadmill.txTreadmill);

        modelTreadmill.render(0.0625F);
    }

    public static class ItemTreadmillRenderHack extends TileEntity {
    }
}
