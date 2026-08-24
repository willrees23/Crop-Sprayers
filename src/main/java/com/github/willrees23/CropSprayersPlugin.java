package com.github.willrees23;

import com.github.willrees23.command.CropSprayersCommand;
import com.github.willrees23.config.SprayerConfig;
import de.exlll.configlib.ConfigLib;
import de.exlll.configlib.YamlConfigurations;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

import java.nio.file.Path;

public class CropSprayersPlugin extends JavaPlugin {

    private Lamp<BukkitCommandActor> lamp;
    private SprayerConfig config;

    @Override
    public void onEnable() {
        reloadSprayerConfig();

        lamp = BukkitLamp.builder(this).build();
        lamp.register(new CropSprayersCommand(this));
    }

    @Override
    public void onDisable() {
        if (lamp != null) {
            lamp.unregisterAllCommands();
        }
    }

    /**
     * Reads config.yml, creating it from the defaults in {@link SprayerConfig}
     * if absent. {@code update} also writes back any keys added to the model
     * since the file was last saved, so upgrades do not need migration code.
     */
    public void reloadSprayerConfig() {
        Path path = getDataFolder().toPath().resolve("config.yml");
        config = YamlConfigurations.update(path, SprayerConfig.class, ConfigLib.BUKKIT_DEFAULT_PROPERTIES);
    }

    public SprayerConfig sprayerConfig() {
        return config;
    }
}
