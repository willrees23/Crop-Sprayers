package com.github.willrees23.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.Getter;

@Getter
@Configuration
public class SprayerSettings {

    @Comment("The maximum height gap between the sprayer and the crop to be sprayed.")
    private int maxSprayHeightDistance = 5;

    @Comment("Horizontal radius that a sprayer covers.")
    private int sprayRadius = 3;

    @Comment("Ticks between spray attempts. 20 ticks = 1 second.")
    private int sprayRateTicks = 20 * 2;

    @Comment({"", "The particles that drift above an idle sprayer."})
    private PassiveParticleSettings passiveParticles = new PassiveParticleSettings();

    @Comment({"", "The beam of particles drawn to a crop the moment it is planted."})
    private CropPlantParticleSettings cropPlantParticles = new CropPlantParticleSettings();
}
