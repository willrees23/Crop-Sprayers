package com.github.willrees23.command.actions;

import com.github.willrees23.CropSprayersPlugin;
import com.github.willrees23.sprayer.CropType;
import org.bukkit.Location;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command({"cropsprayers", "cropspray", "csp"})
@CommandPermission("cropsprayers.admin")
public class ListSubCommand {

    private final CropSprayersPlugin plugin;

    public ListSubCommand() {
        this.plugin = CropSprayersPlugin.getInstance();
    }

    @Subcommand("list")
    @Description("List all sprayers.")
    public void list(BukkitCommandActor actor) {
        plugin.getCropSprayerManager().getActiveSprayers().forEach(sprayer -> {
            String id = sprayer.getId();
            CropType cropType = sprayer.getCropType();
            Location location = sprayer.getLocation();
            actor.reply("&aID: &f" + id + " &aCrop Type: &f" + cropType + " &aLocation: &f" + location);
        });
    }
}
