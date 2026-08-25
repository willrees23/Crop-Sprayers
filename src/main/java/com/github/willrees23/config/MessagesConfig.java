package com.github.willrees23.config;

import de.exlll.configlib.Configuration;
import lombok.Getter;

@Getter
@Configuration
public final class MessagesConfig {

    private String configReloaded = "&aConfig files reloaded.";

    private String configReloadFailed = "&cFailed to reload config files: %error%";

    public String getConfigReloadFailed(String error) {
        return configReloadFailed.replace("%error%", error);
    }

    private String playerOnlyCommand = "&cPlayer only command.";

    private String sprayerNotFound = "&cNo sprayer with the id &7%id%&c.";

    public String getSprayerNotFound(String id) {
        return sprayerNotFound.replace("%id%", id);
    }

    private String sprayerRemoved = "&aRemoved sprayer &7%id%&a.";

    public String getSprayerRemoved(String id) {
        return sprayerRemoved.replace("%id%", id);
    }

    private String sprayerRemoveFailed = "&cFailed to remove sprayer &7%id%&c.";

    public String getSprayerRemoveFailed(String id) {
        return sprayerRemoveFailed.replace("%id%", id);
    }
}
