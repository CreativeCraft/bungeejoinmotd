package org.creativecraft.bungeejoinmotd.listener;

import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.creativecraft.bungeejoinmotd.BungeeJoinMotdPlugin;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class EventListener implements Listener {
    private final BungeeJoinMotdPlugin plugin;

    /**
     * Initialize the event listener instance.
     */
    public EventListener(BungeeJoinMotdPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Register the server connected event.
     *
     * @param event The server connected event.
     */
    @EventHandler
    public void onServerConnected(ServerConnectedEvent event) {
        List<String> motd = plugin.getConfig().getStringList("motd");

        if (motd.isEmpty()) {
            return;
        }

        plugin.getProxy().getScheduler().schedule(plugin, new Runnable() {
            @Override
            public void run() {
                motd.forEach(string -> plugin.sendRawMessage(event.getPlayer(), string));
            }
        }, plugin.getConfig().getInt("delay", 0), TimeUnit.SECONDS);
    }
}
