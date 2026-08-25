package com.github.willrees23.sprayer.visuals;

import com.github.willrees23.config.CropPlantParticleSettings;
import com.github.willrees23.sprayer.CropSprayer;
import org.bukkit.Location;

public class CropPlantParticleVisual {

    private final CropSprayer sprayer;
    private final ArmorStandVisual armorStand;

    public CropPlantParticleVisual(CropSprayer sprayer, ArmorStandVisual armorStand) {
        this.sprayer = sprayer;
        this.armorStand = armorStand;
    }

    public void spawn(Location cropLocation) {
        // no stand yet means its chunk is still unloaded, so there is nothing to fire from
        Location head = armorStand.getHeadLocation();
        if (head == null) {
            return;
        }

        CropPlantParticleSettings settings = sprayer.getSettings().getCropPlantParticles();

        int particleCount = settings.getParticleCount(); // number of particles to spawn
        if (particleCount <= 0) return;

        // draw a line of particles from the sprayer to the crop location
        Location start = head.add(0, settings.getHeightOffset(), 0); // slightly above the head
        Location end = cropLocation.clone().add(0.5, 0.5, 0.5); // center of the block

        for (int i = 0; i <= particleCount; i++) {
            double t = (double) i / particleCount;
            double x = start.getX() + (end.getX() - start.getX()) * t;
            double y = start.getY() + (end.getY() - start.getY()) * t;
            double z = start.getZ() + (end.getZ() - start.getZ()) * t;
            Location particleLocation = new Location(start.getWorld(), x, y, z);
            start.getWorld().spawnParticle(settings.getParticle(), particleLocation, 1, 0, 0, 0, 0);
        }
    }
}
