package com.github.willrees23.sprayer.visuals;

import com.github.willrees23.sprayer.CropSprayer;
import org.bukkit.Location;
import org.bukkit.Particle;

public class CropPlantParticleVisual {

    private final CropSprayer sprayer;
    private final ArmorStandVisual armorStand;

    private static final Particle PARTICLE_TYPE = Particle.FIREWORK;

    public CropPlantParticleVisual(CropSprayer sprayer, ArmorStandVisual armorStand) {
        this.sprayer = sprayer;
        this.armorStand = armorStand;
    }

    public void spawn(Location cropLocation) {
        if (armorStand.getStand() == null) {
            return;
        }

        // draw a line of particles from the sprayer to the crop location
        Location start = sprayer.getVisual().getArmorStand().getHeadLocation().add(0, 0.7, 0); // slightly above the head
        Location end = cropLocation.add(0.5, 0.5, 0.5); // center of the block

        int particleCount = 20; // number of particles to spawn
        for (int i = 0; i <= particleCount; i++) {
            double t = (double) i / particleCount;
            double x = start.getX() + (end.getX() - start.getX()) * t;
            double y = start.getY() + (end.getY() - start.getY()) * t;
            double z = start.getZ() + (end.getZ() - start.getZ()) * t;
            Location particleLocation = new Location(start.getWorld(), x, y, z);
            start.getWorld().spawnParticle(PARTICLE_TYPE, particleLocation, 1, 0, 0, 0, 0);
        }
    }
}
