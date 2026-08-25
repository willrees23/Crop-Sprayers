package com.github.willrees23.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.Getter;
import org.bukkit.Particle;

@Getter
@Configuration
public class PassiveParticleSettings {

    @Comment("The particle to emit.")
    private Particle particle = Particle.FIREWORK;

    @Comment("Ticks between emissions. 20 ticks = 1 second.")
    private int rateTicks = 5;

    @Comment("How many particles to emit each time.")
    private int count = 2;

    @Comment("Blocks above the armour stand to emit from.")
    private double heightOffset = 1;

    @Comment("Size of the box the particles are scattered through, in blocks.")
    private double offsetX = 0.4;
    private double offsetY = 0.1;
    private double offsetZ = 0.4;

    @Comment("Particle speed. What this does varies by particle type.")
    private double extra = 0.001;
}
