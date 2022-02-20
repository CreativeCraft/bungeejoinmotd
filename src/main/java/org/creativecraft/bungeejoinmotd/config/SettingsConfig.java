package org.creativecraft.bungeejoinmotd.config;

import de.leonhard.storage.Config;
import de.leonhard.storage.internal.settings.ReloadSettings;
import org.creativecraft.bungeejoinmotd.BungeeJoinMotdPlugin;

import java.util.Arrays;
import java.util.List;

public class SettingsConfig {
    private final BungeeJoinMotdPlugin plugin;
    private Config config;

    /**
     * Initialize the settings config instance.
     *
     * @param plugin BungeeJoinMotdPlugin
     */
    public SettingsConfig(BungeeJoinMotdPlugin plugin) {
        this.plugin = plugin;

        registerConfig();
    }

    /**
     * Register the config configuration.
     */
    public void registerConfig() {
        config = new Config("config.yml", plugin.getDataFolder().getPath());
        config.setReloadSettings(ReloadSettings.MANUALLY);

        config.setDefault("command", "motd");
        config.setDefault("delay", 0);
        config.setDefault("placeholders.time-format", "hh:mmaa");
        config.setDefault("placeholders.date-format", "mm-dd-yyyy");
        config.setDefault("motd", Arrays.asList(
            "&a&m+&8&m                                              &a&m+&f",
            "Welcome to &a&lLorem&fIpsum",
            "",
            "New to &a&lLorem&fIpsum? Type [&a/faq](run_command=/faq hover=&fRun the &a/faq&f command)&f to get started!",
            "Type [&a/help](run_command=/help hover=&fRun the &a/help&f command)&f for a list of available commands.",
            "&a&m+&8&m                                              &a&m+&f"
        ));
    }

    /**
     * Retrieve the config configuration.
     *
     * @return Config
     */
    public Config getConfig() {
        return config;
    }
}
