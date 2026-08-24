package com.github.willrees23.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.Getter;

@Getter
@Configuration
public final class DefaultConfig {

    @Comment("The maximum height gap between the sprayer and the crop to be sprayed.")
    private int maxSprayHeightDistance = 5;

    @Comment({
            "Horizontal radius, in blocks, that a sprayer covers.",
            "Existing sprayers keep the radius they were created with until the server restarts."
    })
    private int sprayRadius = 5;
}
