package com.github.willrees23.sprayer.visuals;

import com.github.willrees23.sprayer.CropSprayer;
import lombok.Getter;
import org.bukkit.entity.ArmorStand;

// the physical representation of a crop sprayer in the world
@Getter
public class CropSprayerVisual {

    private final ArmorStandVisual armorStand;
    private final PassiveParticleVisual passiveParticles;

    public CropSprayerVisual(CropSprayer sprayer) {
        this.armorStand = new ArmorStandVisual(sprayer);
        this.passiveParticles = new PassiveParticleVisual(sprayer, armorStand);
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
