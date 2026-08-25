package com.github.willrees23.sprayer.storage;

import com.github.willrees23.config.SprayerSettings;
import com.github.willrees23.sprayer.CropSprayer;
import com.github.willrees23.sprayer.CropType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public record SprayerData(String id, CropType crop, String world, double x, double y, double z,
                          SprayerSettings settings) {

    public static SprayerData from(CropSprayer sprayer) {
        Location location = sprayer.getLocation();
        World world = location.getWorld();
        return new SprayerData(
                sprayer.getId(),
                sprayer.getCropType(),
                world == null ? null : world.getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                sprayer.getSettings()
        );
    }

    // null if the world has since been deleted or renamed
    public Location toLocation() {
        if (world == null) return null;

        World bukkitWorld = Bukkit.getWorld(world);
        return bukkitWorld == null ? null : new Location(bukkitWorld, x, y, z);
    }
}
