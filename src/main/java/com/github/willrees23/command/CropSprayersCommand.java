package com.github.willrees23.command;

import com.github.willrees23.CropSprayersPlugin;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command({"cropsprayers", "cropspray", "csp"})
@CommandPermission("cropsprayers.admin")
public class CropSprayersCommand {

    private final CropSprayersPlugin plugin;

    public CropSprayersCommand() {
        this.plugin = CropSprayersPlugin.getInstance();
    }

    @Subcommand("version")
    @Description("Shows the plugin version.")
    public void version(BukkitCommandActor actor) {
        actor.reply("&aCrop Sprayers &7v" + plugin.getDescription().getVersion());
    }

    @Subcommand("reload")
    @Description("Reloads config files from disk.")
    public void reload(BukkitCommandActor actor) {
        try {
            plugin.reloadDefaultConfig();
            actor.reply(plugin.getMessagesConfig().getConfigReloaded());
        } catch (Exception e) {
            actor.error(plugin.getMessagesConfig().getConfigReloadFailed(e.getMessage()));
            plugin.getLogger().severe("Failed to reload config.yml: " + e);
        }
    }
}
