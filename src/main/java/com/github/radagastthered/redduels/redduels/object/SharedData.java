package com.github.radagastthered.redduels.redduels.object;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SharedData {
    // duels that have not begun
    public ArrayList<Duel> queuedDuels;
    // duels that are being fought at the moment
    public ArrayList<Duel> ongoingDuels;
    // players that are muting duel offers
    public ArrayList<Player> mutingPlayers;

    public SharedData() {
        this.queuedDuels = new ArrayList<Duel>();
        this.ongoingDuels = new ArrayList<Duel>();
        this.mutingPlayers = new ArrayList<Player>();
    }
}
