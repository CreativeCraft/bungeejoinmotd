package org.creativecraft.bungeejoinmotd.config;

import de.leonhard.storage.Config;
import de.leonhard.storage.internal.settings.ReloadSettings;
import org.creativecraft.bungeejoinmotd.BungeeJoinMotdPlugin;

public class MessagesConfig {
    private final BungeeJoinMotdPlugin plugin;
    private Config messages;

    /**
     * Initialize the messages config instance.
     *
     * @param plugin BungeeJoinMotdPlugin
     */
    public MessagesConfig(BungeeJoinMotdPlugin plugin) {
        this.plugin = plugin;

        registerConfig();
    }

    /**
     * Register the messages configuration.
     */
    public void registerConfig() {
        messages = new Config("messages.yml", plugin.getDataFolder().getPath());
        messages.setReloadSettings(ReloadSettings.MANUALLY);

        messages.setDefault("messages.generic.prefix", "&a&lBungeeJoin&fMOTD &8>&f ");

        messages.setDefault("messages.view.not-set", "The message of the day has not been set.");
        messages.setDefault("messages.view.description", "View the message of the day.");

        messages.setDefault("messages.add.success", "You have successfully added line &a{line}&f with value:\n{value}");
        messages.setDefault("messages.add.failed", "Failed to add line &a{line}&f.");
        messages.setDefault("messages.add.description", "Add a line to the message of the day.");

        messages.setDefault("messages.remove.success", "You have successfully removed line &a{line}&f.");
        messages.setDefault("messages.remove.failed", "Failed to remove line &a{line}&f.");
        messages.setDefault("messages.remove.invalid-line", "Line &a{line}&f does not exist.");
        messages.setDefault("messages.remove.description", "Remove a line from the message of the day.");

        messages.setDefault("messages.set.success", "You have &asuccessfully&f set line &a{line}&f to:\n{value}");
        messages.setDefault("messages.set.failed",  "Failed to set line &a{line}&f.");
        messages.setDefault("messages.set.invalid-line", "Line number must be between &a0&f and &a{size}&f.");
        messages.setDefault("messages.set.description", "Set a line in the message of the day.");

        messages.setDefault("messages.reload.success", "Plugin has been &asuccessfully&f reloaded.");
        messages.setDefault("messages.reload.failed", "Plugin &cfailed&f to reload.");
        messages.setDefault("messages.reload.description", "Reload the plugin configuration.");

        messages.setDefault("messages.help.header", "&a&m+&8&m                         &a&l BungeeJoin&fMOTD &8&m                         &a&m+");
        messages.setDefault("messages.help.format", "&8➝ &a/{command} &7{parameters} &f- {description}");
        messages.setDefault("messages.help.footer", "&a&m+&8&m                                                                          &a&m+");
        messages.setDefault("messages.help.description", "View the plugin help.");
    }

    /**
     * Retrieve the messages configuration.
     *
     * @return Config
     */
    public Config getMessages() {
        return messages;
    }
}


