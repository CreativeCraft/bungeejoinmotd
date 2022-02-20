package org.creativecraft.bungeejoinmotd.commands;

import co.aikar.commands.CommandHelp;
import co.aikar.commands.HelpEntry;
import net.md_5.bungee.api.CommandSender;
import org.creativecraft.bungeejoinmotd.BungeeJoinMotdPlugin;
import net.md_5.bungee.api.plugin.Listener;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;

import java.util.List;

@CommandAlias("%motd")
@Description("View the message of the day.")
public final class MotdCommand extends BaseCommand implements Listener {
    private final BungeeJoinMotdPlugin plugin;

    public MotdCommand(BungeeJoinMotdPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Retrieve the plugin help.
     *
     * @param sender The command sender.
     */
    @HelpCommand
    @Syntax("[page]")
    @Description("View the plugin help.")
    @CommandCompletion("@nothing")
    public void onHelp(CommandSender sender, CommandHelp help) {
        plugin.sendRawMessage(sender, plugin.localize("messages.help.header"));

        for (HelpEntry entry : help.getHelpEntries()) {
            plugin.sendRawMessage(
                sender,
                plugin.localize("messages.help.format")
                    .replace("{command}", entry.getCommand())
                    .replace("{parameters}", entry.getParameterSyntax())
                    .replace("{description}", plugin.localize("messages." + entry.getCommand().split("\\s+")[1] + ".description"))
            );
        }

        plugin.sendRawMessage(sender, plugin.localize("messages.help.footer"));
    }

    /**
     * Retrieve the message of the day.
     *
     * @param sender The command sender.
     */
    @Default
    @Subcommand("view")
    @CommandPermission("bungeejoinmotd.use")
    @Description("View the message of the day.")
    public void onMotd(CommandSender sender) {
        List<String> motd = plugin.getConfig().getStringList("motd");

        if (motd.isEmpty()) {
            plugin.sendMessage(sender, plugin.localize("messages.view.not-set"));

            return;
        }

        plugin.sendMotd(sender);
    }

    /**
     * Set a line in the message of the day,
     *
     * @param sender The command sender
     * @param line   The line number.
     * @param value  The value.
     */
    @Subcommand("set")
    @Syntax("<line> <value>")
    @CommandPermission("bungeejoinmotd.admin")
    @Description("Set a line in the message of the day.")
    @CommandCompletion("@lines @values")
    public void onSet(CommandSender sender, Integer line, String value) {
        List<String> motd = plugin.getConfig().getStringList("motd");

        if (line > motd.size() - 1 || 0 > line) {
            plugin.sendMessage(
                sender,
                plugin
                    .localize("messages.set.invalid-line")
                    .replace("{size}", Integer.toString(motd.size() - 1))
            );

            return;
        }

        motd.set(line, value);

        try {
            plugin.getConfig().set("motd", motd);

            plugin.sendMessage(
                sender,
                plugin
                    .localize("messages.set.success")
                    .replace("{line}", Integer.toString(line))
                    .replace("{value}", value));

        } catch (Exception e) {
            plugin.sendMessage(
                sender,
                plugin
                    .localize("messages.set.failed")
                    .replace("{line}", Integer.toString(line))
            );
        }
    }

    /**
     * Add a line to the message of the day.
     *
     * @param sender The command sender.
     * @param value  The value.
     */
    @Subcommand("add")
    @Syntax("<value>")
    @CommandPermission("bungeejoinmotd.admin")
    @Description("Add a line to the message of the day.")
    @CommandCompletion("@nothing")
    public void onAdd(CommandSender sender, String value) {
        List<String> motd = plugin.getConfig().getStringList("motd");

        motd.add(value);

        try {
            plugin.getConfig().set("motd", motd);

            plugin.sendMessage(
                sender,
                plugin
                    .localize("messages.add.success")
                    .replace("{line}", Integer.toString(motd.size()))
                    .replace("{value}", value)
            );
        } catch (Exception e) {
            plugin.sendMessage(
                sender,
                plugin
                    .localize("messages.add.failed")
                    .replace("{line}", Integer.toString(motd.size()))
            );
        }
    }

    /**
     * Remove a line from the message of the day.
     *
     * @param sender The command sender.
     * @param line   The line number.
     */
    @Subcommand("remove")
    @Syntax("<line>")
    @CommandPermission("bungeejoinmotd.admin")
    @Description("Remove a line from the message of the day.")
    @CommandCompletion("@lines")
    public void onRemove(CommandSender sender, Integer line) {
        List<String> motd = plugin.getConfig().getStringList("motd");

        if (line > motd.size() || 0 > line) {
            plugin.sendMessage(
                sender,
                plugin.localize("messages.remove.invalid-line")
                    .replace("{line}", Integer.toString(line))
            );

            return;
        }

        motd.set(line, null);

        try {
            plugin.getConfig().set("motd", motd);

            plugin.sendMessage(
                sender,
                plugin.localize("messages.remove.success")
                    .replace("{line}", Integer.toString(line))
            );
        } catch (Exception e) {
            plugin.sendMessage(
                sender,
                plugin
                    .localize("messages.remove.failed")
                    .replace("{line}", Integer.toString(line))
            );
        }
    }

    /**
     * Reload the plugin configuration.
     *
     * @param sender The command sender.
     */
    @Subcommand("reload")
    @CommandPermission("bungeejoinmotd.admin")
    @Description("Reload the plugin configuration.")
    public void onReload(CommandSender sender) {
        try {
            plugin.getMessagesConfig().forceReload();
            plugin.getConfig().forceReload();

            plugin.sendMessage(sender, plugin.localize("messages.reload.success"));
        } catch (Exception e) {
            plugin.sendMessage(sender, plugin.localize("messages.reload.failed"));
        }
    }
}
