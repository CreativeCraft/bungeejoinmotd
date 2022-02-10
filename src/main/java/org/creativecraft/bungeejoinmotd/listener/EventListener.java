package org.creativecraft.bungeejoinmotd.listener;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.creativecraft.bungeejoinmotd.BungeeJoinMotdPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class EventListener implements Listener {
    private final BungeeJoinMotdPlugin plugin;
    private final Set<UUID> players;
    private final List<String> motd;

    /**
     * Initialize the event listener instance.
     */
    public EventListener(BungeeJoinMotdPlugin plugin) {
        this.plugin = plugin;
        this.players = new HashSet<UUID>();
        this.motd = plugin.getConfig().getStringList("motd");
    }

    /**
     * Register the the post login event.
     *
     * @param event The post login event.
     */
    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        if (motd.isEmpty()) {
            return;
        }

        players.add(event.getPlayer().getUniqueId());
    }

    /**
     * Register the player disconnect event.
     *
     * @param event The player disconnect event.
     */
    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        if (motd.isEmpty()) {
            return;
        }

        players.remove(event.getPlayer().getUniqueId());
    }

    /**
     * Register the server connected event.
     *
     * @param event The server connected event.
     */
    @EventHandler
    public void onServerConnected(ServerConnectedEvent event) {
        if (motd.isEmpty()) {
            return;
        }

        if (players.contains(event.getPlayer().getUniqueId())) {
            plugin.getProxy().getScheduler().schedule(plugin, new Runnable() {
                @Override
                public void run() {
                    motd.forEach(string -> plugin.sendRawMessage(event.getPlayer(), string));
                }
            }, Math.max(0, Math.min(5000, plugin.getConfig().getInt("delay"))), TimeUnit.MILLISECONDS);
        }

        players.remove(event.getPlayer().getUniqueId());
    }
}
