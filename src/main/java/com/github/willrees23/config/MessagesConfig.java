package com.github.willrees23.config;

import de.exlll.configlib.Configuration;
import lombok.Getter;

@Getter
@Configuration
public final class MessagesConfig {

    private String configReloaded = "&aConfig files reloaded.";
    private String sprayersReloaded = "&aReloaded &e%count%&a sprayer(s).";;

    private String configReloadFailed = "&cFailed to reload config files: %error%";
    private String playerOnlyCommand = "&cPlayer only command.";
    private String sprayerNotFound = "&cNo sprayer with the id &e%id%&c.";
    private String sprayerRemoved = "&aRemoved sprayer &e%id%&a.";
    private String sprayerRemoveFailed = "&cFailed to remove sprayer &e%id%&c.";

    public String getSprayersReloaded(int count) {
        return sprayersReloaded.replace("%count%", String.valueOf(count));
    }

    public String getConfigReloadFailed(String error) {
        return configReloadFailed.replace("%error%", error);
    }

    public String getSprayerNotFound(String id) {
        return sprayerNotFound.replace("%id%", id);
    }

    public String getSprayerRemoved(String id) {
        return sprayerRemoved.replace("%id%", id);
    }

    public String getSprayerRemoveFailed(String id) {
        return sprayerRemoveFailed.replace("%id%", id);
    }
}
