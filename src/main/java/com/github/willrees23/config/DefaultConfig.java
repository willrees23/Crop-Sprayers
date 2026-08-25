package com.github.willrees23.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.Getter;

@Getter
@Configuration
public final class DefaultConfig {

    @Comment("The maximum height gap between the sprayer and the crop to be sprayed.")
    private int maxSprayHeightDistance = 5;

    @Comment("Horizontal radius that a sprayer covers.")
    private int sprayRadius = 3;
}
