package com.github.willrees23.sprayer.storage;

import com.github.willrees23.CropSprayersPlugin;
import de.exlll.configlib.ConfigLib;
import de.exlll.configlib.YamlConfigurations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

// reads and writes one <id>.yml per sprayer under plugins/CropSprayers/sprayers
public class SprayerStorage {

    private static final Pattern VALID_ID = Pattern.compile("[a-zA-Z0-9_-]{1,64}");

    private static final String EXTENSION = ".yml";
    private static final String FOLDER = "sprayers";

    private final CropSprayersPlugin plugin;
    private final Path directory;

    public SprayerStorage() {
        this.plugin = CropSprayersPlugin.getInstance();
        this.directory = plugin.getDataFolder().toPath().resolve(FOLDER);
    }

    public static boolean isValidId(String id) {
        return id != null && VALID_ID.matcher(id).matches();
    }

    public List<SprayerData> loadAll() {
        List<SprayerData> loaded = new ArrayList<>();

        if (!Files.isDirectory(directory)) return loaded;

        List<Path> files;
        try (Stream<Path> stream = Files.list(directory)) {
            files = stream.filter(path -> path.getFileName().toString().endsWith(EXTENSION)).toList();
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to read the sprayers folder: " + e.getMessage());
            return loaded;
        }

        for (Path file : files) {
            try {
                loaded.add(YamlConfigurations.load(file, SprayerData.class, ConfigLib.BUKKIT_DEFAULT_PROPERTIES));
            } catch (Exception e) {
                plugin.getLogger().warning("Skipping unreadable sprayer file " + file.getFileName() + ": " + e.getMessage());
            }
        }
        return loaded;
    }

    public void save(SprayerData data) {
        if (!isValidId(data.id())) {
            plugin.getLogger().warning("Refusing to save sprayer with unsafe id " + data.id() + ".");
            return;
        }

        try {
            // ConfigLib creates the sprayers folder itself when writing
            YamlConfigurations.save(fileFor(data.id()), SprayerData.class, data, ConfigLib.BUKKIT_DEFAULT_PROPERTIES);
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to save sprayer " + data.id() + ": " + e.getMessage());
        }
    }

    public void delete(String id) {
        if (!isValidId(id)) return;

        try {
            Files.deleteIfExists(fileFor(id));
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to delete sprayer file for " + id + ": " + e.getMessage());
        }
    }

    private Path fileFor(String id) {
        return directory.resolve(id + EXTENSION);
    }
}
