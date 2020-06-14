package com.github.radagastthered.redduels.redduels.command;

import com.github.radagastthered.redduels.redduels.object.SharedData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

/*
This command toggles whether a player will receive duel requests
 */
public class CommandMuteDuel implements CommandExecutor {
    private SharedData data;
    private Logger logger;

    public CommandMuteDuel(SharedData sharedData) {
        this.data = sharedData;
        this.logger = Bukkit.getLogger();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // only players can use this command
        if(!(sender instanceof Player)) {
            logger.warning("/duelmute command was called by a non-player CommandSender");
            sender.sendMessage(data.cfg.formatError("This command can only be used by players"));
            return false;
        }
        // cast the sender to a player
        Player player = (Player) sender;
        // remove the player if they are already there
        if (data.mutingPlayers.contains(player)) {
            data.mutingPlayers.remove(player);
            player.sendMessage(data.cfg.formatInfo("You will now be notified of duel offers"));
            return true;
        }
        // otherwise, add them to the list
        data.mutingPlayers.add(player);
        player.sendMessage(data.cfg.formatInfo("You will no longer be notified of duel offers"));
        return true;
    }

    // returns true if a player wants to mute duel offers
    public boolean isMutingDuelOffers(Player player) {
        return data.mutingPlayers.contains(player);
    }

}
