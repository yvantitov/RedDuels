package com.github.radagastthered.redduels.redduels.command;

import com.github.radagastthered.redduels.redduels.object.SharedData;
import com.github.radagastthered.redduels.redduels.object.Duel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

public class CommandDuel implements CommandExecutor {

    private SharedData data;
    private Logger logger;

    public CommandDuel(SharedData sharedData) {
        this.data = sharedData;
        // get a logger for debugging purposes
        logger = Bukkit.getLogger();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // ensure a player is calling this
        if (!(sender instanceof Player)) {
            logger.warning("/duel command was called by a non-player CommandSender");
            sender.sendMessage(ChatColor.DARK_RED + "This command can only be used by players");
            return false;
        }
        // cast the sender to player
        Player callingPlayer = (Player) sender;
        // ensure the sender is not involved in an active duel already
        if (Duel.involvedInDuel(data.ongoingDuels, callingPlayer)) {
            callingPlayer.sendMessage(ChatColor.BOLD + "" + ChatColor.DARK_RED + "You are already in an ongoing duel");
            return true;
        }
        // initiates a duel
        return startDuel(callingPlayer, args);
    }

    private boolean startDuel(Player callingPlayer, String[] args) {
        // there must be at least one argument
        if (args.length == 0) {
            callingPlayer.sendMessage(ChatColor.DARK_RED + "You must specify a player you wish to challenge");
            return true;
        }
        // a player cannot challenge themselves
        if (args[0].equals(callingPlayer.getDisplayName())) {
            callingPlayer.sendMessage(ChatColor.DARK_RED + "You cannot challenge yourself to a duel");
            return true;
        }
        // try to get the other player
        Player challengedPlayer = Bukkit.getPlayer(args[0]);
        if (challengedPlayer == null) {
            callingPlayer.sendMessage(ChatColor.DARK_RED + args[0] + " is not online");
            return true;
        }
        // a player cannot challenge a player who has already challenged them
        if (Duel.bothInvolved(data.queuedDuels, callingPlayer, challengedPlayer)) {
            callingPlayer.sendMessage(ChatColor.DARK_RED + "There is already a duel offer between you and " + challengedPlayer.getDisplayName());
            return true;
        }
        // if everything has gone right, we tell the challenged player that they have been offered a duel
        // only if they are not muting messages, though!
        if (!data.mutingPlayers.contains(challengedPlayer)) {
            challengedPlayer.sendMessage(ChatColor.BOLD + "" + ChatColor.DARK_RED + callingPlayer.getDisplayName() + " offers you a duel!");
            challengedPlayer.sendMessage(ChatColor.GREEN + "Accept their request with /acceptduel or /aduel");
        }
        // some feedback for the sender
        callingPlayer.sendMessage(ChatColor.BOLD + "" + ChatColor.DARK_RED + "You have challenged " + challengedPlayer.getDisplayName() + " to a duel!");
        // we also add a new duel to the list
        data.queuedDuels.add(new Duel(callingPlayer, challengedPlayer));
        return true;
    }

}
