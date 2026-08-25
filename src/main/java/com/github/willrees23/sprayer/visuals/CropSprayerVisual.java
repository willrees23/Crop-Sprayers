package com.github.willrees23.sprayer.visuals;

import com.github.willrees23.sprayer.CropSprayer;
import lombok.Getter;
import org.bukkit.entity.ArmorStand;

// the physical representation of a crop sprayer in the world
@Getter
public class CropSprayerVisual {

    private final ArmorStandVisual armorStand;
    private final PassiveParticleVisual passiveParticles;
    private final CropPlantParticleVisual cropPlantParticles;

    public CropSprayerVisual(CropSprayer sprayer) {
        this.armorStand = new ArmorStandVisual(sprayer);
        this.passiveParticles = new PassiveParticleVisual(sprayer, armorStand);

        // not an ongoing animation, one-time effect when crop is planted
        this.cropPlantParticles = new CropPlantParticleVisual(sprayer, armorStand);
    }

    public void spawn() {
        // stand first: the particles emit from its head
        armorStand.spawn();
        passiveParticles.spawn();
    }

    public void despawn() {
        passiveParticles.despawn();
        armorStand.despawn();
    }

    public ArmorStand getStand() {
        return armorStand.getStand();
    }
}
