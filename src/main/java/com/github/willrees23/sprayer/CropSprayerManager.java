package com.github.willrees23.sprayer;

import com.github.willrees23.CropSprayersPlugin;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class CropSprayerManager implements Listener {

    private final CropSprayersPlugin plugin;

    @Getter
    private final List<CropSprayer> activeSprayers = new ArrayList<>();

    public CropSprayerManager() {
        this.plugin = CropSprayersPlugin.getInstance();
    }

    public CropSprayer getSprayerById(String id) {
        for (CropSprayer sprayer : activeSprayers) {
            if (sprayer.getId().equals(id)) {
                return sprayer;
            }
        }
        return null;
    }

    public boolean createSprayer(String id, CropType crop, Location location) {
        // check if sprayer with id already exists
        if (getSprayerById(id) != null) {
            plugin.getLogger().warning("Sprayer with id " + id + " already exists.");
            return false;
        }

        // check if location is valid (not null and in a world)
        if (location == null || location.getWorld() == null) {
            plugin.getLogger().warning("Invalid location for sprayer with id " + id + ".");
            return false;
        }

        // go top down in x and y to get all the blocks within the configured
        // radius around the sprayer, from +maxHeight down to -maxHeight
        int maxHeight = plugin.getDefaultConfig().getMaxSprayHeightDistance();
        int radius = plugin.getDefaultConfig().getSprayRadius();

        List<Location> targetBlocks = new ArrayList<>();
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = maxHeight; y >= -maxHeight; y--) {
                    Location targetLocation = location.clone().add(x, y, z);
                    targetBlocks.add(targetLocation);
                }
            }
        }

        CropSprayer sprayer = new CropSprayer(id, crop, location, radius, targetBlocks);
        activeSprayers.add(sprayer);
        plugin.getLogger().info("Created new sprayer with id " + id + " at location " + location.toString());
        return true;
    }
}
