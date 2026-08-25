package com.github.willrees23.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.Getter;
import org.bukkit.Particle;

@Getter
@Configuration
public class CropPlantParticleSettings {

    @Comment("The particle the beam is drawn with.")
    private Particle particle = Particle.FIREWORK;

    @Comment("How many particles the beam is split into. Higher = denser line.")
    private int particleCount = 20;

    @Comment("Blocks above the armour stand's head that the beam starts from.")
    private double heightOffset = 0.7;
}
