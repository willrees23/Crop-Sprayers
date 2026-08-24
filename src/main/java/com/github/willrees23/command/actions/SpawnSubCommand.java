package com.github.willrees23.command.actions;

import com.github.willrees23.CropSprayersPlugin;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command({"cropsprayers", "cropspray", "csp"})
@CommandPermission("cropsprayers.admin")
public class SpawnSubCommand {

    private final CropSprayersPlugin plugin;

    public SpawnSubCommand() {
        this.plugin = CropSprayersPlugin.getInstance();
    }

    @Subcommand("reload")
    @Description("Reloads config files from disk.")
    public void reload(BukkitCommandActor actor) {
        try {
            plugin.reloadSprayerConfig();
            actor.reply("&aConfig files reloaded.");
        } catch (Exception e) {
            actor.error("&cFailed to reload config: " + e.getMessage());
            plugin.getLogger().severe("Failed to reload config.yml: " + e);
        }
    }
}
