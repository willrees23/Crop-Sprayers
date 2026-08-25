package com.github.willrees23.command.actions;

import com.github.willrees23.CropSprayersPlugin;
import com.github.willrees23.sprayer.CropSprayer;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Named;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command({"cropsprayers", "cropspray", "csp"})
@CommandPermission("cropsprayers.admin")
public class RemoveSubCommand {

    private final CropSprayersPlugin plugin;

    public RemoveSubCommand() {
        this.plugin = CropSprayersPlugin.getInstance();
    }

    // the sprayer is resolved by CropSprayerParameterType, so an unknown id is
    // rejected before this method runs
    @Subcommand("remove")
    @Description("Remove a sprayer by its id.")
    public void remove(BukkitCommandActor actor, @Named("sprayer") CropSprayer sprayer) {
        String id = sprayer.getId();

        if (!plugin.getCropSprayerManager().removeSprayer(id)) {
            actor.error(plugin.getMessagesConfig().getSprayerRemoveFailed(id));
            return;
        }

        actor.reply(plugin.getMessagesConfig().getSprayerRemoved(id));
    }
}
