package com.github.willrees23.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.Getter;

@Getter
@Configuration
public final class DefaultConfig {

    @Comment({
            "Settings copied onto a sprayer at the moment it is created.",
            "Changing them only affects sprayers created afterwards - every existing",
            "sprayer carries its own copy in sprayers/<id>.yml, edit it there instead."
    })
    private SprayerSettings sprayerDefaults = new SprayerSettings();
}
