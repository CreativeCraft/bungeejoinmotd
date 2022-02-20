package org.creativecraft.bungeejoinmotd;

import co.aikar.commands.BungeeCommandManager;
import co.aikar.commands.CommandReplacements;
import co.aikar.commands.MessageType;
import de.leonhard.storage.Config;
import de.themoep.minedown.MineDown;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.MetricsLite;
import org.creativecraft.bungeejoinmotd.commands.MotdCommand;
import org.creativecraft.bungeejoinmotd.config.MessagesConfig;
import org.creativecraft.bungeejoinmotd.config.SettingsConfig;
import org.creativecraft.bungeejoinmotd.listener.EventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        replacements.addReplacement("motd", getConfig().getString("command"));

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
     * @return Config
     */
    public Config getConfig() {
        return settingsConfig.getConfig();
    }

    /**
     * Retrieve the messages configuration.
     *
     * @return Config
     */
    public Config getMessagesConfig() {
        return messagesConfig.getMessages();
    }

    /**
     * Retrieve a localized message.
     *
     * @param  key The locale key.
     * @return String
     */
    public String localize(String key) {
        return ChatColor.translateAlternateColorCodes(
            '&',
            messagesConfig.getMessages().getString(key)
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

    /**
     * Send the message of the day.
     *
     * @param sender The command sender.
     */
    public void sendMotd(CommandSender sender) {
        String server = sender instanceof ProxiedPlayer ?
            plugin.getProxy().getPlayer(((ProxiedPlayer) sender).getUniqueId()).getServer().getInfo().getName() :
            plugin.getProxy().getName();

        plugin
            .getConfig()
            .getStringList("motd")
            .forEach(string -> plugin.sendRawMessage(
                sender,
                string
                    .replace("{server_name}", server)
                    .replace("{bungee_name}", plugin.getProxy().getName())
                    .replace("{player_name}", sender.getName())
                    .replace("{player_count}", String.valueOf(plugin.getProxy().getPlayers().size()))
                    .replace("{time}", new SimpleDateFormat(
                        getConfig().getString("placeholder.time-format")).format(new Date())
                    )
            ));
    }
}
