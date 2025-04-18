package com.thunder.Sync.client.render;

import me.ichun.mods.sync.client.entity.EntityShellDestruction;
import me.ichun.mods.sync.client.model.ModelPixel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class RenderShellDestruction extends Render<EntityShellDestruction>
{
    public HashMap<ResourceLocation, BufferedImage[]> restitchedSkins = new HashMap<>();
    public HashMap<ResourceLocation, int[]> restitchedSkinsId = new HashMap<>();
    public ModelPixel model;

    public RenderShellDestruction(RenderManager manager)
    {
        super(manager);
        model = new ModelPixel();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityShellDestruction entity)
    {
        return DefaultPlayerSkin.getDefaultSkinLegacy();
    }

    @Override
    public void doRender(EntityShellDestruction sd, double d, double d1, double d2, float f, float f1)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)d, (float)d1, (float)d2);
        
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        sd.model.rand.setSeed(sd.hashCode());
        
        BufferedImage[] skins = restitchedSkins.get(sd.txLocation);
        
        if(skins == null)
        {
            ITextureObject obj = Minecraft.getMinecraft().getTextureManager().getTexture(sd.txLocation);
            if(obj instanceof ThreadDownloadImageData)
            {
                try
                {
                    ThreadDownloadImageData imgDat = (ThreadDownloadImageData)obj;
                    BufferedImage img = ObfuscationReflectionHelper.getPrivateValue(ThreadDownloadImageData.class, imgDat, "field_110560_d");
                    if(img != null)
                    {
                        int[] imgId = new int[4];
                        skins = new BufferedImage[4];

                        int[] dimsX = new int[] { 4, 4, 8, 8 };
                        int[] dimsZ = new int[] { 4, 4, 4, 8 };
                        int[] dimsY = new int[] { 12, 12, 12, 8 };

                        int[] startX = new int[] { 0, 40, 16, 0 };
                        int[] startY = new int[] { 16, 16, 16, 0 };

                        for(int j = 0; j < dimsX.length; j++)
                        {
                            int[] dim = new int[] { dimsX[j], dimsY[j], dimsZ[j] };
                            int[] rots = new int[] { -90, 180, 0, 0, 90, 0, -90, 180, 				90 };
                            BufferedImage tmp = new BufferedImage(48, 24, 1);

                            Graphics2D gfx = tmp.createGraphics();

                            int[] xSource = new int[] { dim[2], dim[2], dim[2] + dim[0] + dim[2], 0, dim[2] + dim[0], dim[2] + dim[0], dim[2] + dim[0], dim[2] + dim[0], 				dim[2]};
                            int[] ySource = new int[] { 0, 0, dim[2], dim[2], 0, 0, 0, 0,  					0 };

                            int[] xCoord = new int[] { dim[0], dim[0] + dim[2] + dim[0] + dim[2], 0, dim[0] + dim[2] + dim[0] + dim[2] + dim[0], dim[0], dim[0] + dim[2], dim[0] + dim[2] + dim[0], dim[0] + dim[2] + dim[0] + dim[2], 				dim[2] + dim[0] + dim[2] };
                            int[] yCoord = new int[] { 0, 0, dim[2], dim[2], dim[2] + dim[1], dim[2] + dim[1], dim[2] + dim[1], dim[2] + dim[1], 				0 };

                            int[] dimX = new int[] { dim[0], dim[0], dim[0], dim[2], dim[0], dim[0], dim[0], dim[0], 					dim[0] };
                            int[] dimY = new int[] { dim[2], dim[2], dim[1], dim[1], dim[2], dim[2], dim[2], dim[2], 					dim[2] };

                            for(int i = 0; i < rots.length; i++)
                            {
                                if(i == rots.length - 1)
                                {
                                    gfx.drawImage(img, dim[0], 0, dim[0] + dim[2] + dim[0] + dim[2] + dim[0], dim[2] + dim[1], startX[j], startY[j], startX[j] + (2 * dim[0] + 2 * dim[2]), startY[j] + dim[1] + dim[2], null);
                                }

                                BufferedImage temp = img.getSubimage(startX[j] + xSource[i], startY[j] + ySource[i], dimX[i], dimY[i]); //new BufferedImage(img.getWidth(), img.getHeight(), 1);

                                BufferedImage temp1 = new BufferedImage(dimX[i], dimY[i], 1);

                                Graphics2D gfx1 = temp1.createGraphics();
                                gfx1.rotate(Math.toRadians(rots[i]), dimX[i] / 2, dimY[i] / 2);
    //    			                gfx1.drawImage(temp, 0, 0, (Math.abs(rots[i]) == 90) ? dimY[i] : dimX[i], (Math.abs(rots[i]) == 90) ? dimX[i] : dimY[i], 0, 0, (Math.abs(rots[i]) == 90) ? dimY[i] : dimX[i], (Math.abs(rots[i]) == 90) ? dimX[i] : dimY[i], (ImageObserver)null);
                                gfx1.drawImage(temp, 0, 0, dimX[i], dimY[i], 0, 0, dimX[i], dimY[i], null);
                                gfx1.dispose();

                                gfx.drawImage(temp1, xCoord[i], yCoord[i], xCoord[i] + dimX[i], yCoord[i] + dimY[i], 0, 0, dimX[i], dimY[i], null);
                            }

                            imgId[j] = TextureUtil.uploadTextureImage(TextureUtil.glGenTextures(), tmp);
                            skins[j] = tmp;
//			                try
//			                {
//			                    ImageIO.write(tmp, "png", new File(Minecraft.getMinecraft().mcDataDir, "test"+j+".png"));
//			                }
//			                catch (IOException ioexception)
//			                {
//			                    ioexception.printStackTrace();
//			                }
                        }

                        restitchedSkinsId.put(sd.txLocation, imgId);
                        restitchedSkins.put(sd.txLocation, skins);
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        
        sd.model.renderPlayer(sd.progress, sd.renderYaw, sd.yaw, sd.pitch, sd.limbSwingg, sd.limbSwinggAmount, 0.0625F, f1, restitchedSkinsId.get(sd.txLocation));

        GlStateManager.popMatrix();
    }

    public static class RenderFactory implements IRenderFactory<EntityShellDestruction>
    {
        @Override
        public Render<EntityShellDestruction> createRenderFor(RenderManager manager)
        {
            return new RenderShellDestruction(manager);
        }
    }

}
