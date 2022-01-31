package org.creativecraft.bungeejoinmotd;

import co.aikar.commands.BungeeCommandManager;
import co.aikar.commands.CommandReplacements;
import co.aikar.commands.MessageType;
import de.themoep.minedown.MineDown;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import org.bstats.bungeecord.MetricsLite;
import org.creativecraft.bungeejoinmotd.commands.MotdCommand;
import org.creativecraft.bungeejoinmotd.config.MessagesConfig;
import org.creativecraft.bungeejoinmotd.config.SettingsConfig;
import org.creativecraft.bungeejoinmotd.listener.EventListener;

import java.util.ArrayList;
import java.util.List;

public final class BungeeJoinMotdPlugin extends Plugin {
    public static BungeeJoinMotdPlugin plugin;
    private SettingsConfig settingsConfig;
    private MessagesConfig messagesConfig;

    /**
     * Enable the plugin.
     */
    @Override
    public void onEnable() {
        plugin = this;

        registerConfigs();
        registerListeners();
        registerCommands();

        new MetricsLite(this, 14135);
    }

    /**
     * Load the plugin.
     */
    @Override
    public void onLoad() {
        //
    }

    /**
     * Disable the plugin.
     */
    @Override
    public void onDisable() {
        //
    }

    /**
     * Register the plugin configuration.
     */
    public void registerConfigs() {
        settingsConfig = new SettingsConfig(this);
        messagesConfig = new MessagesConfig(this);
    }

    /**
     * Register the event listener.
     */
    public void registerListeners() {
        getProxy().getPluginManager().registerListener(this, new EventListener(this));
    }


    /**
     * Register the plugin commands.
     */
    public void registerCommands() {
        BungeeCommandManager commandManager = new BungeeCommandManager(this);
        CommandReplacements replacements = commandManager.getCommandReplacements();

        replacements.addReplacement("motd", getConfig().getString("command", "motd"));

        commandManager.setFormat(MessageType.ERROR, ChatColor.GREEN, ChatColor.WHITE, ChatColor.GRAY);
        commandManager.setFormat(MessageType.SYNTAX, ChatColor.GREEN, ChatColor.WHITE, ChatColor.GRAY);
        commandManager.setFormat(MessageType.HELP, ChatColor.GREEN, ChatColor.WHITE, ChatColor.GRAY);
        commandManager.setFormat(MessageType.INFO, ChatColor.GREEN, ChatColor.WHITE, ChatColor.GRAY);

        commandManager.getCommandCompletions().registerCompletion("lines", c -> {
            List<String> numbers = new ArrayList<String>();

            for (int count = 0; count < getConfig().getStringList("motd").size(); count = count + 1) {
                numbers.add(Integer.toString(count));
            }

            return numbers;
        });

        commandManager.getCommandCompletions().registerCompletion(
            "values",
            c -> getConfig().getStringList("motd")
        );

        commandManager.registerCommand(new MotdCommand(this));
        commandManager.enableUnstableAPI("help");
    }

    /**
     * Retrieve the plugin configuration.
     *
     * @return Configuration
     */
    public Configuration getConfig() {
        return settingsConfig.getConfig();
    }

    /**
     * Save the plugin configuration.
     */
    public void saveConfig() {
        settingsConfig.saveConfig();
    }

    /**
     * Retrieve the messages configuration.
     *
     * @return Configuration
     */
    public Configuration getMessages() {
        return messagesConfig.getMessages();
    }

    /**
     * Retrieve a localized message.
     *
     * @param  key The locale key.
     * @return String
     */
    public String localize(String key) {
        String message = messagesConfig.getMessages().getString(key);

        return ChatColor.translateAlternateColorCodes(
            '&',
            message == null ? key + " is missing." : message
        );
    }

    /**
     * Send a message formatted with MineDown.
     *
     * @param sender The command sender.
     * @param value  The message.
     */
    public void sendMessage(CommandSender sender, String value) {
        sender.sendMessage(
            MineDown.parse(messagesConfig.getMessages().getString("messages.generic.prefix") + value)
        );
    }

    /**
     * Send a raw message formatted with MineDown.
     *
     * @param sender The command sender.
     * @param value  The message.
     */
    public void sendRawMessage(CommandSender sender, String value) {
        sender.sendMessage(
            MineDown.parse(value)
        );
    }
}
