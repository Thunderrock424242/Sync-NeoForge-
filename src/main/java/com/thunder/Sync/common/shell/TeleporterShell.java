package com.thunder.Sync.common.shell;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterShell extends Teleporter 
{

    public int dimension;
    public BlockPos pos;
    public float playerYaw;
    public float playerPitch;

    public TeleporterShell(WorldServer par1WorldServer)
    {
        super(par1WorldServer);
    }

    public TeleporterShell(WorldServer server, int dimensionId, BlockPos pos, float yaw, float pitch) {
        this(server);
        this.dimension = dimensionId;
        this.pos = pos;
        this.playerYaw = yaw;
        this.playerPitch = pitch;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw)
    {
        entityIn.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, playerYaw, playerPitch);
        entityIn.motionX = entityIn.motionY = entityIn.motionZ = 0.0D;
    }
    
    @Override
    public void removeStalePortalLocations(long par1)
    {
    }

}
