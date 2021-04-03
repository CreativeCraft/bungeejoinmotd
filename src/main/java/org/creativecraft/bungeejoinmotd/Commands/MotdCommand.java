package org.creativecraft.bungeejoinmotd.Commands;

import org.creativecraft.bungeejoinmotd.BungeeJoinMOTD;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import de.themoep.minedown.MineDown;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;

@CommandAlias("motd")
@Description("Retrieve the message of the day.")
public final class MotdCommand extends BaseCommand implements Listener {
  private final BungeeJoinMOTD plugin;

  public MotdCommand(BungeeJoinMOTD plugin) {
    this.plugin = plugin;
  }

  /**
   * Retrieve the message of the day.
   *
   * @param player The proxied player.
   */
  @Default
  @CommandPermission("motd.use")
  public void onMotdCommand(ProxiedPlayer player) {
    if (hasMotd()) {
      player.sendMessage(this.getMotd());
    }
  }

  /**
   * Reload the plugin configuration.
   *
   * @param player the proxied player.
   */
  @CommandAlias("reload")
  @CommandPermission("motd.admin")
  @Description("Reload the BungeeJoinMOTD plugin configuration.")
  @CommandCompletion("@nothing")
  public void onMotdReloadCommand(ProxiedPlayer player) {
    try {
      this.plugin.registerConfig();

      player.sendMessage(MineDown.parse(
        this.plugin.getConfig().getString("locale.reloadSuccess")
      ));
    } catch (Exception e) {
      player.sendMessage(MineDown.parse(
        this.plugin.getConfig().getString("locale.reloadFailed")
      ));
    }
  }

  /**
   * Determine if a valid message of the day exists.
   *
   * @return boolean
   */
  public boolean hasMotd() {
    return this.plugin.getConfig() != null &&
      this.plugin.getConfig().contains("motd") &&
      !this.plugin.getConfig().getString("motd").equals("");
  }

  /**
   * Retrieve the message of the day.
   *
   * @return BaseComponent[]
   */
  public BaseComponent[] getMotd() {
    return MineDown.parse(
      this.plugin.getConfig().getString("motd")
    );
  }

  /**
   * Send the message of the day to players after the login event.
   *
   * @param event The post login event.
   */
  @EventHandler
  public void onPostLogin(PostLoginEvent event) {
    ProxiedPlayer player = event.getPlayer();

    this.onMotdCommand(player);
  }
}
