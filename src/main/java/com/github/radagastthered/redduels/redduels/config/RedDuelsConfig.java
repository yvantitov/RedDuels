package com.github.radagastthered.redduels.redduels.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

/*
Singleton class that loads the config for RedDuels
 */
public class RedDuelsConfig {
    private static RedDuelsConfig instance = null;
    private RedDuelsConfig(Plugin plugin) {
        // get the config
        FileConfiguration config = plugin.getConfig();
        // TODO: make this work
    }
    public static RedDuelsConfig getInstance(Plugin plugin) {
        if (instance == null) {
            instance = new RedDuelsConfig(plugin);
        }
        return instance;
    }
}
