package com.github.willrees23.command.actions;

import com.github.willrees23.CropSprayersPlugin;
import org.bukkit.entity.Player;
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

    @Subcommand("spawn")
    @Description("Spawn a sprayer at your current location.")
    public void spawn(BukkitCommandActor actor) {
        if (actor.isConsole()) {
            actor.reply(plugin.getMessagesConfig().getPlayerOnlyCommand());
            return;
        }
        Player player = actor.requirePlayer();
    }
}
