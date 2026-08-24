package com.github.willrees23.sprayer;

import com.github.willrees23.CropSprayersPlugin;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.scheduler.BukkitTask;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class CropSprayer {

    private final String id;
    private final CropType crop;
    private final Location location;
    private final int radius;
    private final int rate = 20 * 2; // 20 ticks = 1 second
    private final List<Location> targetBlocks;
    private final BukkitTask task;
    private final CropSprayerVisual visual;

    public CropSprayer(String id, CropType crop, Location location, int radius, List<Location> targetBlocks) {
        this.id = id;
        this.crop = crop;
        this.location = location;
        this.radius = radius;
        this.targetBlocks = targetBlocks;
        this.task = CropSprayersPlugin.getInstance().getServer().getScheduler().runTaskTimer(CropSprayersPlugin.getInstance(), this::spray, 0L, rate);
        this.visual = new CropSprayerVisual(this);
        this.visual.spawn();
    }

    // ticks at the rate set above
    private void spray() {
        // find valid farmland blocks within target area
        List<Block> candidates = getCandidates();

        if (candidates.isEmpty()) return;

        // get random from options and plant it
        Block chosenBlock = candidates.get(ThreadLocalRandom.current().nextInt(candidates.size()));
        plant(chosenBlock);
    }

    private @NonNull List<Block> getCandidates() {
        List<Block> candidates = new ArrayList<>();

        for (Location location : targetBlocks) {
            Block block = location.getBlock();

            // ignore non-farmland
            if (block.getType() != Material.FARMLAND) continue;

            // ignore if block above is not air
            Block aboveBlock = block.getRelative(0, 1, 0);
            if (!aboveBlock.getType().isAir()) continue;

            candidates.add(aboveBlock);
        }
        return candidates;
    }

    // plant crop and set it to fully grown
    private void plant(Block block) {
        block.setType(crop.getMaterial());

        BlockData data = block.getBlockData();
        if (data instanceof Ageable ageable) {
            ageable.setAge(ageable.getMaximumAge());
            block.setBlockData(ageable);
        }
    }
}
