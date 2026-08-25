package com.github.willrees23.command.parameters;

import com.github.willrees23.CropSprayersPlugin;
import com.github.willrees23.sprayer.CropSprayer;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.exception.CommandErrorException;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.stream.MutableStringStream;

// lets commands take a CropSprayer directly, resolved from its id, with the
// active ids offered as tab completions
public class CropSprayerParameterType implements ParameterType<BukkitCommandActor, CropSprayer> {

    @Override
    public CropSprayer parse(MutableStringStream input, ExecutionContext<BukkitCommandActor> context) {
        String id = input.readString();

        CropSprayer sprayer = CropSprayersPlugin.getInstance().getCropSprayerManager().getSprayerById(id);
        if (sprayer == null) {
            throw new CommandErrorException(CropSprayersPlugin.getInstance().getMessagesConfig().getSprayerNotFound(id));
        }
        return sprayer;
    }

    @Override
    public SuggestionProvider<BukkitCommandActor> defaultSuggestions() {
        // resolved per keystroke rather than cached: the list changes as
        // sprayers are spawned and removed
        return context -> CropSprayersPlugin.getInstance().getCropSprayerManager()
                .getActiveSprayers()
                .stream()
                .map(CropSprayer::getId)
                .toList();
    }
}
