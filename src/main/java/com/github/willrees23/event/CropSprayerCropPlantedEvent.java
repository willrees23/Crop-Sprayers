package com.github.willrees23.event;

import com.github.willrees23.sprayer.CropSprayer;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

@Getter
public class CropSprayerCropPlantedEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final CropSprayer sprayer;
    private final Block cropBlock;

    public CropSprayerCropPlantedEvent(CropSprayer sprayer, Block cropBlock) {
        this.sprayer = sprayer;
        this.cropBlock = cropBlock;
    }

    public static @NonNull HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NonNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
