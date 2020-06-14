package com.github.radagastthered.redduels.redduels;

import com.github.radagastthered.redduels.redduels.command.CommandAcceptDuel;
import com.github.radagastthered.redduels.redduels.command.CommandDuel;
import com.github.radagastthered.redduels.redduels.command.CommandMuteDuel;
import com.github.radagastthered.redduels.redduels.listener.CleanUpListener;
import com.github.radagastthered.redduels.redduels.object.SharedData;
import com.github.radagastthered.redduels.redduels.object.Duel;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class RedDuels extends JavaPlugin {

    SharedData sharedData;

    @Override
    public void onEnable() {
        // make a config file if it doesn't exist
        this.saveDefaultConfig();
        // initialize our shared data object
        sharedData = new SharedData(this);
        // schedule a repeating task that removes expired duels from queuedDuels
        new BukkitRunnable() {
            @Override
            public void run() {
                Duel.removeExpiredDuels(sharedData);
            }
        }.runTaskTimer(this, 40, 40);
        // register commands
        this.getCommand("duel").setExecutor(new CommandDuel(sharedData));
        this.getCommand("acceptduel").setExecutor(new CommandAcceptDuel(sharedData));
        this.getCommand("muteduel").setExecutor(new CommandMuteDuel(sharedData));
        // register listeners
        getServer().getPluginManager().registerEvents(new CleanUpListener(sharedData), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
