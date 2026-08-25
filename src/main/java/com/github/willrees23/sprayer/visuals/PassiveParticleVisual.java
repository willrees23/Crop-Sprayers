package com.github.willrees23.sprayer.visuals;

import com.github.willrees23.sprayer.CropSprayer;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitTask;

public class PassiveParticleVisual {

    private final CropSprayer sprayer;
    private final ArmorStandVisual armorStand;
    private BukkitTask particleTask;

    private static final Particle PARTICLE_TYPE = Particle.FIREWORK;
    private static final int EMIT_INTERVAL_TICKS = 20;

    public PassiveParticleVisual(CropSprayer sprayer, ArmorStandVisual armorStand) {
        this.sprayer = sprayer;
        this.armorStand = armorStand;
    }

    public void spawn() {

    }

    public void despawn() {
        // TODO: cancel particleTask and null it out
    }

    private void emit() {
        // TODO: spawn the passive particles
    }
}
