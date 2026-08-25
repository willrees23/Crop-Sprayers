package com.github.willrees23.command.actions;

import com.github.willrees23.CropSprayersPlugin;
import com.github.willrees23.sprayer.CropType;
import org.bukkit.Location;
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
    public void spawn(BukkitCommandActor actor,
                      String name,
                      CropType cropType) {
        if (actor.isConsole()) {
            actor.reply(plugin.getMessagesConfig().getPlayerOnlyCommand());
            return;
        }
        Player player = actor.requirePlayer();
        Location startLocation = player.getLocation();

        boolean success = plugin.getCropSprayerManager().createSprayer(name, cropType, startLocation);
        if (!success) {
            actor.reply("&cFailed to spawn sprayer.");
            return;
        }
        actor.reply("&aSprayer spawned at your location!");
    }
}
