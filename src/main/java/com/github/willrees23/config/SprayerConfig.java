package com.github.willrees23.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;

/**
 * Backing model for config.yml. Configlib maps fields to YAML keys and
 * writes the @Comment text above each one. Field initialisers are the
 * defaults used when the file (or an individual key) is missing.
 */
@Configuration
public final class SprayerConfig {

    @Comment("Radius, in blocks, that a sprayer affects.")
    private int radius = 5;

    @Comment("Ticks between spray passes. 20 ticks = 1 second.")
    private long intervalTicks = 100L;

    @Comment("Show particles when a sprayer fires.")
    private boolean particles = true;

    public int radius() {
        return radius;
    }

    public long intervalTicks() {
        return intervalTicks;
    }

    public boolean particles() {
        return particles;
    }
}
