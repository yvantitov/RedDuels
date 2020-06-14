package com.github.radagastthered.redduels.redduels.command;

import com.github.radagastthered.redduels.redduels.object.DuelType;
import com.github.radagastthered.redduels.redduels.object.SharedData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
This command provides a list of all registered Duel Types
 */
public class CommandDuelTypes implements CommandExecutor {
    private SharedData data;
    public CommandDuelTypes(SharedData data) {
        this.data = data;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // only works for players
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is only available to players");
            return false;
        }
        // cast the sender to a player
        Player player = (Player) sender;
        player.sendMessage(data.cfg.formatInfo("Available duel types: "));
        // loop through duel types. output the ones the player has access to
        for (DuelType d : data.cfg.duelTypes) {
            if (d.permission == null || player.hasPermission(d.permission)) {
                player.sendMessage(data.cfg.formatInfo(" - " + d.name + ": " + d.description, false));
            }
        }
        return true;
    }
}
