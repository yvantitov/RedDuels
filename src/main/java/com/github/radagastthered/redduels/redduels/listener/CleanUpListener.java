package com.github.radagastthered.redduels.redduels.listener;

import com.github.radagastthered.redduels.redduels.object.Duel;
import com.github.radagastthered.redduels.redduels.object.SharedData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/*
This listener handles cleaning up a duel if something goes wrong during it
 */
public class CleanUpListener implements Listener {
    private SharedData data;
    public CleanUpListener(SharedData sharedData) {
        this.data = sharedData;
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // get the player
        Player player = event.getPlayer();
        // remove all queued duels this player is a part of
        Duel.deleteDuelsWhereInvolved(data.queuedDuels, player);
        // if they are in an ongoing duel, we do more stuff
        if (Duel.involvedInDuel(data.ongoingDuels, player)) {
            // get the specific duel
            for (int i = 0; i < data.ongoingDuels.size(); i++) {
                Duel d = data.ongoingDuels.get(i);
                if (player == d.getCallingPlayer()) {
                    d.victor = d.getChallengedPlayer();
                    d.loser = player;
                    d.endDuel();
                }
                if (player == d.getChallengedPlayer()) {
                    d.victor = d.getCallingPlayer();
                    d.loser = player;
                    d.endDuel();
                }
            }
        }
    }
}
