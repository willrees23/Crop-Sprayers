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
}
