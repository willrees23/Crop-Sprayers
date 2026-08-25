package com.github.willrees23.sprayer.visuals;

import com.github.willrees23.CropSprayersPlugin;
import com.github.willrees23.sprayer.CropSprayer;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitTask;

public class PassiveParticleVisual {

    private static final Particle PARTICLE_TYPE = Particle.FIREWORK;
    private static final int RATE_TICKS = 5;
    private final CropSprayer sprayer;
    private final ArmorStandVisual armorStand;
    private BukkitTask particleTask;

    public PassiveParticleVisual(CropSprayer sprayer, ArmorStandVisual armorStand) {
        this.sprayer = sprayer;
        this.armorStand = armorStand;
    }

    public void spawn() {
        if (particleTask != null)
            return;

        CropSprayersPlugin plugin = CropSprayersPlugin.getInstance();
        particleTask = plugin.getServer().getScheduler().runTaskTimer(plugin, this::emit, 0L, RATE_TICKS);
    }

    public void despawn() {
        if (particleTask != null) {
            particleTask.cancel();
            particleTask = null;
        }
    }

    private void emit() {
        ArmorStand stand = armorStand.getStand();

        // the stand comes and goes with its chunk, so skip this tick rather
        if (stand == null || stand.isDead()) return;

        stand.getWorld().spawnParticle(
                PARTICLE_TYPE,
                stand.getLocation().add(0, 1, 0),
                2, // count
                0.4, 0.1, 0.4, // offsetX, offsetY, offsetZ
                0.001 // extra
        );
    }
}
