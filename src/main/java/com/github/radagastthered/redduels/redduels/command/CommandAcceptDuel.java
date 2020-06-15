package com.github.radagastthered.redduels.redduels.command;

import com.github.radagastthered.redduels.redduels.object.SharedData;
import com.github.radagastthered.redduels.redduels.object.Duel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.logging.Logger;

public class CommandAcceptDuel implements CommandExecutor {

    private SharedData data;
    private Logger logger;

    public CommandAcceptDuel(SharedData sharedData) {
        this.data = sharedData;
        // logger for debugging
        this.logger = Bukkit.getLogger();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // ensure a player is calling this
        if (!(sender instanceof Player)) {
            logger.warning("/duel command was called by a non-player CommandSender");
            sender.sendMessage(data.cfg.formatError("This command can only be used by players"));
            return false;
        }
        // cast the sender to player
        Player challengedPlayer = (Player) sender;
        // ensure the sender is not involved in an active duel already
        if (Duel.involvedInDuel(data.ongoingDuels, challengedPlayer)) {
            challengedPlayer.sendMessage(data.cfg.formatError("You are already in an ongoing duel"));
            return true;
        }
        // ensure they have the appropriate permission
        if (!challengedPlayer.hasPermission("redduels.accept")) {
            challengedPlayer.sendMessage(data.cfg.formatError("You cannot accept duel requests"));
            return true;
        }
        return acceptDuel(challengedPlayer, args);
    }

    private boolean acceptDuel(Player challengedPlayer, String[] args) {
        // we only need to parse arguments if multiple duels have been offered to this player
        ArrayList<Player> callingPlayers = new ArrayList<Player>();
        for (Duel d : data.queuedDuels) {
            if (d.getChallengedPlayer() == challengedPlayer) callingPlayers.add(d.getCallingPlayer());
        }
        // if there are no challenges for this player, inform them
        if (callingPlayers.size() == 0) {
            challengedPlayer.sendMessage(data.cfg.formatError("Nobody has challenged you to a duel. Challenge others with /duel"));
            return true;
        }
        // if two or more duels challenging this player exist, we must grab their arguments
        Player callingPlayer = null;
        if (callingPlayers.size() >= 2) {
            // if no args at all have been provided, we tell the player this
            if (args.length == 0) {
                challengedPlayer.sendMessage(data.cfg.formatError("You have been challenged by multiple players:"));
                for (Player p : callingPlayers) {
                    challengedPlayer.sendMessage(data.cfg.formatError(" - " + p.getDisplayName()));
                }
                challengedPlayer.sendMessage(data.cfg.formatInfo("You must specify whose challenge you wish to accept with /acceptduel <player>"));
            }
            callingPlayer = Bukkit.getPlayer(args[0]);
            if (callingPlayer == null && !args[0].equals("")) {
                challengedPlayer.sendMessage(data.cfg.formatError(args[0] + " has not challenged you to a duel"));
                return true;
            } else if (callingPlayer == null) {
                challengedPlayer.sendMessage(data.cfg.formatError(args[0] + " is not online"));
                return true;
            }
        } else {
            callingPlayer = callingPlayers.get(0);
        }
        // time to begin the duel, since it looks like everything went well
        Duel duel = Duel.getDuel(data.queuedDuels, callingPlayer, challengedPlayer);
        if (duel == null) {
            logger.warning(challengedPlayer.getDisplayName() + " somehow accepted a non-existent duel");
            return false;
        } else {
            // mark the duel as accepted
            duel.accept();
            // move duel from accepted to ongoing
            data.queuedDuels.remove(duel);
            data.ongoingDuels.add(duel);
            // debugging
            logger.info("Duel started between " + duel.getCallingPlayer().getDisplayName() + " and " + duel.getChallengedPlayer().getDisplayName());
            // begin the duel!
            duel.beginDuel();
        }
        return true;
    }
}
