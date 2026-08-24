package com.github.willrees23.command;

import com.github.willrees23.CropSprayersPlugin;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command("cropsprayers")
@CommandPermission("cropsprayers.admin")
public final class CropSprayersCommand {

    private final CropSprayersPlugin plugin;

    public CropSprayersCommand(CropSprayersPlugin plugin) {
        this.plugin = plugin;
    }

    /*
     * Note: actor.reply(String) is the safe reply on Spigot. The
     * reply(ComponentLike) overload needs adventure at runtime, which
     * spigot-api does not ship.
     */
    @Subcommand("version")
    @Description("Shows the plugin version.")
    public void version(BukkitCommandActor actor) {
        actor.reply("&aCrop Sprayers &7v" + plugin.getDescription().getVersion());
    }

    @Subcommand("reload")
    @Description("Reloads config.yml from disk.")
    public void reload(BukkitCommandActor actor) {
        try {
            plugin.reloadSprayerConfig();
            actor.reply("&aConfig reloaded. &7radius=" + plugin.sprayerConfig().radius());
        } catch (Exception e) {
            actor.error("&cFailed to reload config: " + e.getMessage());
            plugin.getLogger().severe("Failed to reload config.yml: " + e);
        }
    }
}
