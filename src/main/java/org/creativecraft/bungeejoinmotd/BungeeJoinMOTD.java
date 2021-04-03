package org.creativecraft.bungeejoinmotd;

import co.aikar.commands.BungeeCommandManager;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import org.creativecraft.bungeejoinmotd.Commands.MotdCommand;

public final class BungeeJoinMOTD extends Plugin {
  private Config config;

  @Override
  public void onEnable() {
    registerConfig();
    registerCommands();
    registerListener();
  }

  /**
   * Register the event listener.
   */
  public void registerListener() {
    getProxy().getPluginManager().registerListener(this, new MotdCommand(this));
  }

  /**
   * Register the plugin configuration.
   */
  public void registerConfig() {
    config = new Config(this);
    config.registerConfig();
  }

  /**
   * Register the plugin commands.
   */
  public void registerCommands() {
    BungeeCommandManager commandManager = new BungeeCommandManager(this);

    commandManager.registerCommand(new MotdCommand(this));
  }

  /**
   * Retrieve the plugin configuration.
   *
   * @return Configuration
   */
  public Configuration getConfig() {
    return config.getConfig();
  }
}
