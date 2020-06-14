package com.github.radagastthered.redduels.redduels.listener;

import com.github.radagastthered.redduels.redduels.object.Duel;
import com.github.radagastthered.redduels.redduels.object.SharedData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class CleanUpListener implements Listener {
    private SharedData data;
    public CleanUpListener(SharedData sharedData) {
        this.data = sharedData;
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // remove all queued duels this player is a part of
        Duel.deleteDuelsWhereInvolved(data.queuedDuels, event.getPlayer());
        // if they are in an ongoing duel, we do more stuff
        // TODO: make this bit
    }
}
