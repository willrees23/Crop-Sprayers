package com.github.willrees23.sprayer.visuals;

import com.github.willrees23.CropSprayersPlugin;
import com.github.willrees23.config.PassiveParticleSettings;
import com.github.willrees23.sprayer.CropSprayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitTask;

public class PassiveParticleVisual {

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

        // a period of 0 or less would stop the task repeating, so floor it at one tick
        long rate = Math.max(1, settings().getRateTicks());

        CropSprayersPlugin plugin = CropSprayersPlugin.getInstance();
        particleTask = plugin.getServer().getScheduler().runTaskTimer(plugin, this::emit, 0L, rate);
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

        PassiveParticleSettings settings = settings();
        stand.getWorld().spawnParticle(
                settings.getParticle(),
                stand.getLocation().add(0, settings.getHeightOffset(), 0),
                settings.getCount(),
                settings.getOffsetX(), settings.getOffsetY(), settings.getOffsetZ(),
                settings.getExtra()
        );
    }

    private PassiveParticleSettings settings() {
        return sprayer.getSettings().getPassiveParticles();
    }
}
