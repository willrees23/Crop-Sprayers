package com.github.willrees23.sprayer;

import com.github.willrees23.CropSprayersPlugin;
import com.github.willrees23.config.SprayerSettings;
import com.github.willrees23.sprayer.storage.SprayerData;
import com.github.willrees23.sprayer.storage.SprayerStorage;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class CropSprayerManager implements Listener {

    private final CropSprayersPlugin plugin;
    private final SprayerStorage storage;

    @Getter
    private final List<CropSprayer> activeSprayers = new ArrayList<>();

    public CropSprayerManager() {
        this.plugin = CropSprayersPlugin.getInstance();
        this.storage = new SprayerStorage();
    }

    public CropSprayer getSprayerById(String id) {
        for (CropSprayer sprayer : activeSprayers) {
            if (sprayer.getId().equals(id)) {
                return sprayer;
            }
        }
        return null;
    }

    public int reloadSprayers() {
        stopSprayers();
        return loadSprayers();
    }

    // restores every saved sprayer. call once on enable, after the worlds exist
    public int loadSprayers() {
        int restored = 0;

        for (SprayerData data : storage.loadAll()) {
            if (getSprayerById(data.id()) != null) {
                plugin.getLogger().warning("Duplicate sprayer id " + data.id() + " on disk, skipping.");
                continue;
            }

            // the world may have been deleted or renamed since the file was written
            Location location = data.toLocation();
            if (location == null) {
                plugin.getLogger().warning("Sprayer " + data.id() + " refers to unknown world '" + data.world() + "', skipping.");
                continue;
            }

            SprayerSettings settings = data.settings();
            boolean backfill = settings == null;
            if (backfill) settings = plugin.getDefaultConfig().getSprayerDefaults();

            CropSprayer sprayer = spawnSprayer(data.id(), data.crop(), location, settings);
            if (backfill) {
                storage.save(SprayerData.from(sprayer));
                plugin.getLogger().info("Filled in the default settings for sprayer " + data.id() + ".");
            }
            restored++;
        }

        if (restored > 0) {
            plugin.getLogger().info("Restored " + restored + " sprayer(s) from disk.");
        }
        return restored;
    }

    public boolean createSprayer(String id, CropType crop, Location location) {
        // the id becomes a file name, so reject anything that could escape the folder
        if (!SprayerStorage.isValidId(id)) {
            plugin.getLogger().warning("Invalid sprayer id " + id + ".");
            return false;
        }

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

        // snapshot the config defaults now, so later config edits leave this one alone
        CropSprayer sprayer = spawnSprayer(id, crop, location, plugin.getDefaultConfig().getSprayerDefaults());
        storage.save(SprayerData.from(sprayer));

        plugin.getLogger().info("Created new sprayer with id " + id + " at location " + location);
        return true;
    }

    // stops the sprayer and forgets it permanently
    public boolean removeSprayer(String id) {
        CropSprayer sprayer = getSprayerById(id);
        if (sprayer == null) return false;

        sprayer.despawn();
        activeSprayers.remove(sprayer);
        storage.delete(id);

        plugin.getLogger().info("Removed sprayer with id " + id + ".");
        return true;
    }

    public void stopSprayers() {
        for (CropSprayer sprayer : activeSprayers) {
            sprayer.despawn();
        }
        activeSprayers.clear();
    }

    // builds the target area and starts the sprayer. deliberately does not touch
    // storage, so loading from disk does not immediately write the same file back
    private CropSprayer spawnSprayer(String id, CropType crop, Location location, SprayerSettings settings) {
        // go top down in x and y to get all the blocks within the sprayer's own
        // radius, from +maxHeight down to -maxHeight
        int maxHeight = settings.getMaxSprayHeightDistance();
        int radius = settings.getSprayRadius();

        List<Location> targetBlocks = new ArrayList<>();
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = maxHeight; y >= -maxHeight; y--) {
                    Location targetLocation = location.clone().add(x, y, z);
                    targetBlocks.add(targetLocation);
                }
            }
        }

        CropSprayer sprayer = new CropSprayer(id, crop, location, settings, targetBlocks);
        activeSprayers.add(sprayer);
        return sprayer;
    }
}
