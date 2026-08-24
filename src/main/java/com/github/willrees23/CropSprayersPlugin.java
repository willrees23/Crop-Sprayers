package com.github.willrees23;

import com.github.willrees23.command.CropSprayersCommand;
import com.github.willrees23.command.actions.SpawnSubCommand;
import com.github.willrees23.config.DefaultConfig;
import com.github.willrees23.config.MessagesConfig;
import com.github.willrees23.sprayer.CropSprayerManager;
import de.exlll.configlib.ConfigLib;
import de.exlll.configlib.YamlConfigurations;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

import java.nio.file.Path;

public class CropSprayersPlugin extends JavaPlugin {

    @Getter
    private static CropSprayersPlugin instance;
    @Getter
    private CropSprayerManager cropSprayerManager;

    @Getter
    private Lamp<BukkitCommandActor> lamp;
    @Getter
    private DefaultConfig defaultConfig;
    @Getter
    private MessagesConfig messagesConfig;

    @Override
    public void onEnable() {
        instance = this;

        reloadAllConfigs();

        lamp = BukkitLamp.builder(this).build();
        lamp.register(new CropSprayersCommand(), new SpawnSubCommand());

        PluginManager pm = getServer().getPluginManager();
        cropSprayerManager = new CropSprayerManager();
        pm.registerEvents(cropSprayerManager, this);
    }

    @Override
    public void onDisable() {
        if (lamp != null) {
            lamp.unregisterAllCommands();
        }
    }

    public void reloadAllConfigs() {
        reloadDefaultConfig();
        reloadMessagesConfig();
    }

    public void reloadDefaultConfig() {
        Path path = getDataFolder().toPath().resolve("config.yml");
        defaultConfig = YamlConfigurations.update(path, DefaultConfig.class, ConfigLib.BUKKIT_DEFAULT_PROPERTIES);
    }

    public void reloadMessagesConfig() {
        Path path = getDataFolder().toPath().resolve("messages.yml");
        messagesConfig = YamlConfigurations.update(path, MessagesConfig.class, ConfigLib.BUKKIT_DEFAULT_PROPERTIES);
    }

    public DefaultConfig sprayerConfig() {
        return defaultConfig;
    }
}
