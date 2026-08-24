package com.github.willrees23;

import com.github.willrees23.command.CropSprayersCommand;
import com.github.willrees23.config.SprayerConfig;
import de.exlll.configlib.ConfigLib;
import de.exlll.configlib.YamlConfigurations;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

import java.nio.file.Path;

public class CropSprayersPlugin extends JavaPlugin {

    @Getter
    private static CropSprayersPlugin instance;
    @Getter
    private Lamp<BukkitCommandActor> lamp;
    @Getter
    private SprayerConfig config;

    @Override
    public void onEnable() {
        instance = this;

        reloadSprayerConfig();

        lamp = BukkitLamp.builder(this).build();
        lamp.register(new CropSprayersCommand());
    }

    @Override
    public void onDisable() {
        if (lamp != null) {
            lamp.unregisterAllCommands();
        }
    }

    public void reloadSprayerConfig() {
        Path path = getDataFolder().toPath().resolve("config.yml");
        config = YamlConfigurations.update(path, SprayerConfig.class, ConfigLib.BUKKIT_DEFAULT_PROPERTIES);
    }

    public SprayerConfig sprayerConfig() {
        return config;
    }
}
