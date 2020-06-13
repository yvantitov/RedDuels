package com.github.radagastthered.redduels.redduels.object;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Duel {

    // expiration time for the duel, in milliseconds
    // 180,000 milliseconds = 3 minutes
    public static final long EXPIRE_TIME = 180000;

    private long timeCreated;
    private Player callingPlayer;
    private Player challengedPlayer;
    private boolean accepted = false;
    public Duel (Player callingPlayer, Player challengedPlayer) {
        this.timeCreated = System.currentTimeMillis();
        this.callingPlayer = callingPlayer;
        this.challengedPlayer = challengedPlayer;
    }
    public boolean isAccepted() {
        return accepted;
    }
    public void accept() {
        accepted = true;
    }
    public Player getCallingPlayer() {
        return callingPlayer;
    }
    public Player getChallengedPlayer() {
        return challengedPlayer;
    }
    // returns true if the Duel object has expired
    public boolean isExpired() {
        return ((System.currentTimeMillis() - timeCreated) >= EXPIRE_TIME);
    }
    // utility method for removing expired duels
    public static void removeExpiredDuels(ArrayList<Duel> duels) {
        for (int i = 0; i < duels.size(); i++) {
            Duel d = duels.get(i);
            if (d.isExpired()) {
                d.getCallingPlayer().sendMessage(ChatColor.RED + "Your duel offer to " + d.getChallengedPlayer().getDisplayName() + " has expired");
                duels.remove(i);
            }
        }
    }
    // utility method for getting a duel with specific players
    public static Duel getDuel(ArrayList<Duel> duels, Player callingPlayer, Player challengedPlayer) {
        for (Duel d : duels) {
            if (d.getCallingPlayer() == callingPlayer && d.getChallengedPlayer() == challengedPlayer) {
                return d;
            }
        }
        return null;
    }
    // utility method for deleting all where two players are involved
    public static void deleteDuelsBetween(ArrayList<Duel> duels, Player playerA, Player playerB) {
        for (int i = 0; i < duels.size(); i++){
            Duel d = duels.get(i);
            if ((d.getCallingPlayer() == playerA && d.getChallengedPlayer() == playerB) || (d.getCallingPlayer() == playerB && d.getChallengedPlayer() == playerA)) {
                duels.remove(i);
            }
        }
    }
    // utility method for checking whether a player is involved in a duel
    public static boolean involvedInDuel(ArrayList<Duel> duels, Player player) {
        for (Duel d : duels) {
            if (d.getCallingPlayer() == player || d.getChallengedPlayer() == player) {
                return true;
            }
        }
        return false;
    }
    // utility method for checking whether two players are in some way involved via the duel system
    // returns true if the players are involved in a duel object, returns false otherwise
    public static boolean bothInvolved(ArrayList<Duel> duels, Player playerA, Player playerB) {
        for (int i = 0; i < duels.size(); i++){
            Duel d = duels.get(i);
            if ((d.getCallingPlayer() == playerA && d.getChallengedPlayer() == playerB) || (d.getCallingPlayer() == playerB && d.getChallengedPlayer() == playerA)) {
                return true;
            }
        }
        return false;
    }
}
