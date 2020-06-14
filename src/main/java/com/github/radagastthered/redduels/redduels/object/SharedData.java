package com.github.radagastthered.redduels.redduels.object;

import com.github.radagastthered.redduels.redduels.config.RedDuelsConfig;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class SharedData {
    // duels that have not begun
    public ArrayList<Duel> queuedDuels;
    // duels that are being fought at the moment
    public ArrayList<Duel> ongoingDuels;
    // duels that have been resolved
    public ArrayList<Duel> resolvedDuels;
    // players that are muting duel offers
    public ArrayList<Player> mutingPlayers;
    // the config loader
    public RedDuelsConfig cfg;
    // an instance of RedDuels
    public Plugin plugin;

    public SharedData(Plugin plugin) {
        this.queuedDuels = new ArrayList<Duel>();
        this.ongoingDuels = new ArrayList<Duel>();
        this.resolvedDuels = new ArrayList<Duel>();
        this.mutingPlayers = new ArrayList<Player>();
        this.plugin = plugin;

        // load the config
        this.cfg = RedDuelsConfig.getInstance(plugin);
    }
}
