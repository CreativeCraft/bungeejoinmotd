package org.creativecraft.bungeejoinmotd;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Config {
  private final BungeeJoinMOTD plugin;
  private Configuration config;

  public Config(BungeeJoinMOTD plugin) {
    this.plugin = plugin;
  }

  public void registerConfig() {
    if (!this.plugin.getDataFolder().exists()) {
      this.plugin.getDataFolder().mkdir();
    }

    File file = new File(this.plugin.getDataFolder(), "config.yml");

    if (!file.exists()) {
      try (InputStream in = this.plugin.getResourceAsStream("config.yml")) {
        Files.copy(in, file.toPath());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    try {
      this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(
        new File(this.plugin.getDataFolder(), "config.yml")
      );
    } catch (Exception e) {
      //
    }
  }

  public Configuration getConfig() {
    return this.config;
  }
}
